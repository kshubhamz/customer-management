package com.redhatschool.customermanagement.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CustomerBillValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerBill {
	String message() default "Invalid Bill Amount";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
