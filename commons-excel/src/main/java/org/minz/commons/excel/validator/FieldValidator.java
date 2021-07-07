package org.minz.commons.excel.validator;

/**
 * 字段验证期，验证导入的字段值是否符合验证器规则
 *
 * @author zhengmin 2018/1/3
 */
public interface FieldValidator {

  boolean validate(Object value);

  String getErrorMessage();
}
