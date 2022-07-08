package com.redhatschool.customermanagement.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomerBillValidator implements ConstraintValidator<CustomerBill, Double> {

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		if (value != null)
			return value > 99 && value < 1000000;
			
		return false;
	}

}
