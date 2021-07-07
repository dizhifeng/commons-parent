package org.minz.commons.excel.typehandler;

import java.text.DecimalFormat;

/**
 * @author zhengmin 2019/1/3
 */
public class IntegerTypeHandler implements TypeHandler<Integer> {

  @Override
  public Integer convert(Object value) {
    Integer i = null;
    DecimalFormat df = new DecimalFormat("#.##");
    if (value != null && !String.valueOf(value).isEmpty()) {
      Double d = Double.valueOf(String.valueOf(value));
      String fmtVal = df.format(d);
      i = Integer.valueOf(fmtVal);
    }
    return i;
  }
}
