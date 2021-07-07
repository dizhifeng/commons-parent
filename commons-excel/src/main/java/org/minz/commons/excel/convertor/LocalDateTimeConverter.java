package org.minz.commons.excel.convertor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhengmin
 * @created 2019/10/8
 */
public class LocalDateTimeConverter implements FieldConverter {

  @Override
  public String convert(Object value) {
    if (value instanceof LocalDateTime) {
      return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    return "";
  }
}
