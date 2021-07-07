package org.minz.commons.springmvc.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author zhengmin
 * @created 2020/8/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

  private int code = 429;

  public BizException() {
  }

  public BizException(String message, int code) {
    super(message);
    this.code = code;
  }

}
