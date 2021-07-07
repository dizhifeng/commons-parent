package org.minz.commons.excel.convertor;

/**
 * @author zhengmin 2019/1/5
 */
public class DefaultFieldConverter implements FieldConverter {

  @Override
  public String convert(Object value) {
    return value == null ? "" : value.toString();
  }
}
