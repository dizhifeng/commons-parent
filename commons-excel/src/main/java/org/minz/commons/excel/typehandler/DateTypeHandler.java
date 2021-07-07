package org.minz.commons.excel.typehandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 日期类型处理器
 *
 * @author zhengmin 2019/1/3
 */
public class DateTypeHandler implements TypeHandler<Date> {

  @Override
  public Date convert(Object value) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    if (value != null && Pattern
        .matches("^20\\d{2}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$", String.valueOf(value))) {
      try {
        date = df.parse(String.valueOf(value));
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return date;
  }
}
