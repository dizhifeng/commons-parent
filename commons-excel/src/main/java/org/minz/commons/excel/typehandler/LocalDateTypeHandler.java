package org.minz.commons.excel.typehandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * java8日期处理器
 *
 * @author zhengmin 2019/1/3
 */
public class LocalDateTypeHandler implements TypeHandler<LocalDate> {

  @Override
  public LocalDate convert(Object value) {
    LocalDate localDate = null;
    if (value != null && Pattern
        .matches("^20\\d{2}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", String.valueOf(value))) {
      localDate = LocalDate.parse(String.valueOf(value), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    return localDate;
  }
}
