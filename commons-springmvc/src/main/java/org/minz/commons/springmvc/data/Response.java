package org.minz.commons.springmvc.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 响应对象。包含处理结果（Meta）和返回数据（Data）两部分，在 Controller 处理完请求后将此对象转换成 json 返回给前台。注意：
 * <ul>
 * <li>处理成功一般返回处理结果和返回数据，失败只返回处理结果。具体返回什么需看接口文档。</li>
 * <li>处理成功结果码一般是200，失败码具体看出了什么错，对照 HTTP 响应码填。</li>
 * <li>默认处理方法慎用，前台最想要拿到的还是具体的结果码和信息。</li>
 * </ul>
 */
public class Response<T> {

  /**
   * 默认成功响应码
   */
  static final Integer DEAFAULT_SUCCESS_CODE = HttpStatus.OK.value();
  /**
   * 默认成功响应信息
   */
  static final String DEAFAULT_SUCCESS_MSG = "处理成功！";
  /**
   * 默认失败响应码
   */
  private static final Integer DEAFAULT_FAILURE_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
  /**
   * 默认失败响应信息
   */
  private static final String DEAFAULT_FAILURE_MSG = "处理失败！";

  /**
   * 处理结果代码，与 HTTP 状态响应码对应
   */
  @ApiModelProperty("响应码。为200时表示请求处理成功")
  @Getter
  @Setter
  private Integer code;

  /**
   * 处理结果信息
   */
  @ApiModelProperty("响应信息。请求失败时会带提示信息")
  @Getter
  @Setter
  private String msg;

  @ApiModelProperty("返回的json数据，根据各接口定义")
  @Getter
  @Setter
  private T data;

  /**
   * 处理成功默认响应
   *
   * @return 响应对象
   */
  public static Response success() {
    Response response = new Response();
    response.setCode(DEAFAULT_SUCCESS_CODE);
    response.setMsg(DEAFAULT_SUCCESS_MSG);
    return response;
  }


  /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓成功↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

  /**
   * 处理成功响应，返回自定义响应码、信息和数据。
   *
   * @param msg 处理结果信息
   * @return 响应对象
   */
  public static <T> Response success(String msg) {
    Response<T> response = new Response<>();
    response.setCode(DEAFAULT_SUCCESS_CODE);
    response.setMsg(msg);
    return response;
  }

  /**
   * 处理成功响应，返回自定义响应码、信息和数据。
   *
   * @param data 返回数据
   * @return 响应对象
   */
  public static <T> Response<T> success(T data) {
    Response<T> response = new Response<>();
    response.setCode(DEAFAULT_SUCCESS_CODE);
    response.setMsg(DEAFAULT_SUCCESS_MSG);
    response.setData(data);
    return response;
  }

  /**
   * 处理成功响应，返回自定义响应码、信息和数据。
   *
   * @param msg 处理结果信息
   * @param data 返回数据
   * @return 响应对象
   */
  public static <T> Response<T> success(String msg, T data) {
    Response<T> response = new Response<>();
    response.setCode(DEAFAULT_SUCCESS_CODE);
    response.setMsg(msg);
    response.setData(data);
    return response;
  }

  /**
   * 处理成功响应，返回自定义响应码、信息和数据。
   *
   * @param httpStatus HTTP 响应码
   * @param msg 处理结果信息
   * @param data 返回数据
   * @return 响应对象
   */
  public static <T> Response<T> success(HttpStatus httpStatus, String msg, T data) {
    Response<T> response = new Response<>();
    response.setCode(httpStatus.value());
    response.setMsg(msg);
    response.setData(data);
    return response;
  }


  /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓失败↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

  /**
   * 处理失败响应，返回自定义响应码、信息和数据。
   *
   * @param msg 处理结果信息
   * @return 响应对象
   */
  public static Response failure(String msg) {
    Response response = new Response();
    response.setCode(DEAFAULT_FAILURE_CODE);
    response.setMsg(msg);
    return response;
  }

  /**
   * 处理失败响应，返回自定义响应码、信息和数据。
   *
   * @param data 返回数据
   * @return 响应对象
   */
  public <T> Response<T> failure(T data) {
    Response<T> response = new Response<>();
    response.setCode(DEAFAULT_FAILURE_CODE);
    response.setMsg(DEAFAULT_FAILURE_MSG);
    response.setData(data);
    return response;
  }

  /**
   * 业务失败
   *
   * @param code 业务错误代码
   * @param msg 失败信息
   * @return 响应对象
   */
  public static Response failure(int code, String msg) {
    Response response = new Response();
    response.setCode(code);
    response.setMsg(msg);
    return response;
  }

  /**
   * 处理失败响应，返回自定义响应码、信息和数据。
   *
   * @param msg 处理结果信息
   * @param data 返回数据
   * @return 响应对象
   */
  public static <T> Response<T> failure(String msg, T data) {
    Response<T> response = new Response<T>();
    response.setCode(DEAFAULT_FAILURE_CODE);
    response.setMsg(msg);
    response.setData(data);
    return response;
  }

  /**
   * 处理失败响应，返回自定义响应码、信息和数据。
   *
   * @param httpStatus HTTP 响应码
   * @param msg 处理结果信息
   * @param data 返回数据
   * @return 响应对象
   */
  public static <T> Response<T> failure(HttpStatus httpStatus, String msg, T data) {
    Response<T> response = new Response<>();
    response.setCode(httpStatus.value());
    response.setMsg(msg);
    response.setData(data);
    return response;
  }

}
