package org.minz.commons.excel.typehandler;

/**
 * @author zhengmin 2019/1/3
 */
public class DefaultTypeHandler implements TypeHandler<Object> {

  @Override
  public Object convert(Object value) {
    return value == null ? null : String.valueOf(value).trim();
  }
}
