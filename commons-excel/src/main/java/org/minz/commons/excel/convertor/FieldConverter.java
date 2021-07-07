package org.minz.commons.excel.convertor;

/**
 * 导出excel字段转化器
 *
 * @author zhengmin 2019/1/5
 */
public interface FieldConverter {

  String convert(Object value);
}
