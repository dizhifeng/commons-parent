package org.minz.commons.excel.validator;

/**
 * @author zhengmin 2019/1/3
 */
public class DefaultFieldValidator implements FieldValidator {

  @Override
  public boolean validate(Object value) {
    return true;
  }

  @Override
  public String getErrorMessage() {
    return null;
  }
}
