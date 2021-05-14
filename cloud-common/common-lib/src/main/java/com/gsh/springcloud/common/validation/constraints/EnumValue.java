package com.gsh.springcloud.common.validation.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author gsh
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValue.Validator.class)
public @interface EnumValue {

  String message() default "无效的值";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends Enum<?>> enumClass();

  String fieldName();


  class Validator implements ConstraintValidator<EnumValue, Object> {

    private Class<? extends Enum<?>> enumClass;

    private String fieldName;


    @Override
    public void initialize(EnumValue enumValue) {
      fieldName = enumValue.fieldName();
      enumClass = enumValue.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
      if (value == null) {
        return Boolean.TRUE;
      }
      if (enumClass == null || fieldName == null) {
        return Boolean.TRUE;
      }

      Class<?> valueClass = value.getClass();
      Enum[] possibleValues = enumClass.getEnumConstants();
      for (Enum possibleValue : possibleValues) {
        try {
          Field field = possibleValue.getClass().getDeclaredField(fieldName);
          field.setAccessible(true);
          Object fieldValue = field.get(possibleValue);
          if (Objects.equals(value, fieldValue)) {
            return true;
          }
        } catch (NoSuchFieldException | IllegalAccessException ex) {
          ex.printStackTrace();
          throw new RuntimeException(String.format("This [%s(%s)] field does not exist in the %s",
                  fieldName, valueClass, enumClass));
        }
      }
      return false;
    }
  }
}
