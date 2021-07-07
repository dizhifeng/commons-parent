package org.minz.commons.excel.typehandler;

/**
 * @author zhengmin 2019/1/3
 */
public interface TypeHandler<T> {

  T convert(Object value);
}
