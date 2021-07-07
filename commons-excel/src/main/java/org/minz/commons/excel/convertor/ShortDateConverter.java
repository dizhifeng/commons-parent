package org.minz.commons.excel.convertor;

import java.time.LocalDateTime;

/**
 * @author zhengmin
 * @created 2019/4/4
 */
public class ShortDateConverter implements FieldConverter {


  @Override
  public String convert(Object value) {
    if (value instanceof LocalDateTime) {
      return ((LocalDateTime) value).toLocalDate().toString();
    }
    return "";
  }
}
