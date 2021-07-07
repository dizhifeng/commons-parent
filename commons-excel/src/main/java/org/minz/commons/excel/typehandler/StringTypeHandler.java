package org.minz.commons.excel.typehandler;

/**
 * @author zhengmin 2019/1/3
 */
public class StringTypeHandler implements TypeHandler<String> {

  @Override
  public String convert(Object value) {
    return value == null ? null : String.valueOf(value).trim();
  }
}
