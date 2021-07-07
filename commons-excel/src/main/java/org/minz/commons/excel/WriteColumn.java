package org.minz.commons.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.minz.commons.excel.convertor.DefaultFieldConverter;
import org.minz.commons.excel.convertor.FieldConverter;

/**
 * excel导出字段映射注解
 *
 * @author zhengmin 2019/1/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface WriteColumn {

  /**
   * 导出字段序号
   */
  int value();

  /**
   * excel栏标题
   */
  String title();

  /**
   * 文字对齐方向
   */
  TextAlign align() default TextAlign.CENTER;

  /**
   * 字段转化器
   */
  Class<? extends FieldConverter> converter() default DefaultFieldConverter.class;
}
