package com.roy.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { ListValueConstraintValidator.class}) //校验注解使用哪个校验器进行校验的
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })  //校验注解可以标注在哪些位置
@Retention(RUNTIME) //校验注解的时机
public @interface ListValue {
    String message() default "{com.roy.common.valid.ListValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] value() default {};
}