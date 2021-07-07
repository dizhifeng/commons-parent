package org.minz.commons.springmvc.advice;

import java.util.List;
import java.util.Set;
import javax.management.ServiceNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.minz.commons.springmvc.data.Response;
import org.minz.commons.springmvc.exception.AccessForbbidenException;
import org.minz.commons.springmvc.exception.BizException;
import org.minz.commons.springmvc.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author zhengmin
 * @created 18-9-13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public Response handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    String msg = "缺少请求参数！";
    log.error(msg, e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    String msg = e.getMessage();
    log.error("参数解析失败：", e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String msg = handleBindingResult(e.getBindingResult());
    log.error("方法参数无效: ", e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public Response handleBindException(BindException e) {
    String msg = handleBindingResult(e.getBindingResult());
    log.error("参数绑定失败:", e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public Response handleServiceException(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    String msg = violations.iterator().next().getMessage();
    log.error("参数验证失败:", e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 400 - Bad Request
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public Response handleValidationException(ValidationException e) {
    String msg = e.getMessage();
    log.error("参数验证失败：", e);
    return Response.failure(HttpStatus.BAD_REQUEST, msg, null);
  }

  /**
   * 401 - Unauthorized
   */
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public Response handleLoginException(UnauthorizedException e) {
    String msg = e.getMessage();
    log.error("认证失败异常：", e);
    return Response.failure(HttpStatus.UNAUTHORIZED, msg, null);
  }

  /**
   * 403 - Unauthorized
   */
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(AccessForbbidenException.class)
  public Response handleLoginException(AccessForbbidenException e) {
    String msg = e.getMessage();
    log.error("用户无权限：", e);
    return Response.failure(HttpStatus.FORBIDDEN, "用户无权限!", null);
  }

  /**
   * 405 - Method Not Allowed
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public Response handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    String msg = "不支持当前请求方法！";
    log.error(msg, e);
    return Response.failure(HttpStatus.METHOD_NOT_ALLOWED, msg, null);
  }

  /**
   * 415 - Unsupported Media Type
   */
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public Response handleHttpMediaTypeNotSupportedException(Exception e) {
    String msg = "不支持当前媒体类型！";
    log.error(msg, e);
    return Response.failure(HttpStatus.UNSUPPORTED_MEDIA_TYPE, msg, null);
  }

  /**
   * 422 - UNPROCESSABLE_ENTITY
   */
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public Response handleMaxUploadSizeExceededException(Exception e) {
    String msg = "所上传文件大小超过最大限制，上传失败！";
    log.error(msg, e);
    return Response.failure(HttpStatus.UNPROCESSABLE_ENTITY, msg, null);
  }

  /**
   * 500 - Internal Server Error
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ServiceNotFoundException.class)
  public Response handleServiceException(ServiceNotFoundException e) {
    String msg = "服务内部异常：" + e.getMessage();
    log.error(msg, e);
    return Response.failure(msg);
  }

  /**
   * 499 - 业务异常
   */
  @ExceptionHandler(BizException.class)
  public Response handleBizException(BizException e) {
    String msg = "业务失败异常！" + e.getMessage();
    log.error(msg, e);
    return Response.failure(e.getCode(), msg);
  }

  /**
   * 500 - Internal Server Error
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Response handleException(Exception e) {
    String msg = "服务内部异常！" + e.getMessage();
    log.error(msg, e);
    return Response.failure(msg);
  }

  /**
   * 处理参数绑定异常，并拼接出错的参数异常信息。
   * <p>
   * 创建人：leigq <br> 创建时间：2017年10月16日 下午9:09:22 <br>
   * <p>
   * 修改人： <br> 修改时间： <br> 修改备注： <br>
   * </p>
   */
  private String handleBindingResult(BindingResult result) {
    if (result.hasErrors()) {
      final List<FieldError> fieldErrors = result.getFieldErrors();
      return fieldErrors.iterator().next().getDefaultMessage();
    }
    return null;
  }
}
