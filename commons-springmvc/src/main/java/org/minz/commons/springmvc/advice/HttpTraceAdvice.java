package org.minz.commons.springmvc.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import io.swagger.annotations.ApiOperation;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhengmin
 * @created 18-11-6
 */
@Slf4j
@Aspect
@Component
public class HttpTraceAdvice {

  private static SerializeConfig serializeConfig = new SerializeConfig();

  public HttpTraceAdvice() {
    serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
  }

  @Around("@within(org.springframework.web.bind.annotation.RestController)")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Object result = null;
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
    HttpServletRequest request = sra.getRequest();

    String url = request.getRequestURL().toString();
    String httpMethod = request.getMethod();
    String uri = request.getRequestURI();
    String queryString = request.getQueryString();

    Method method = MethodSignature.class.cast(pjp.getSignature()).getMethod();
    String apiName = "";
    Annotation[] methodAnnotations = method.getAnnotations();
    for (Annotation annotation : methodAnnotations) {
      if (annotation instanceof ApiOperation) {
        apiName = ((ApiOperation) annotation).value();
      }
    }
    String requestBody = null;
    Object[] args = pjp.getArgs();
    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    for (int argIndex = 0; argIndex < args.length; argIndex++) {
      for (Annotation paramAnnotation : parameterAnnotations[argIndex]) {
        if ((paramAnnotation instanceof RequestBody)) {
          requestBody = JSON.toJSONString(args[argIndex], serializeConfig);
        }
      }
    }

    log.trace("请求: name={},url={} ,method={},path={},querystring=[{}],requestbody={}", apiName,
        url, httpMethod, uri, queryString, requestBody);

    // result的值就是被拦截方法的返回值
    result = pjp.proceed();

    log.debug("响应: " + JSON.toJSONString(result, serializeConfig));

    return result;
  }
}
