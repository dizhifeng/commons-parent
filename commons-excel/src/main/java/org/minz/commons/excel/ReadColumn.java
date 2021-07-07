package org.minz.commons.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.minz.commons.excel.typehandler.DefaultTypeHandler;
import org.minz.commons.excel.typehandler.TypeHandler;
import org.minz.commons.excel.validator.DefaultFieldValidator;
import org.minz.commons.excel.validator.FieldValidator;

/**
 * excel导入字段映射注解
 *
 * @author zhengmin 2019/1/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ReadColumn {

  /**
   * 对应excel栏的序号，从0开始
   */
  int value();

  /**
   * 是否必填
   */
  boolean required() default false;

  /**
   * 类型处理器
   */
  Class<? extends TypeHandler> typeHandler() default DefaultTypeHandler.class;

  /**
   * 验证器
   */
  Class<? extends FieldValidator> validator() default DefaultFieldValidator.class;
}
