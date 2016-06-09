package com.centauro.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.centauro.model.ShopModel;


@Component
public class ShopValidator implements Validator {
	
	private final static String EMPLOYEES_NUMBER = "emplNumber";

	@Override
	public boolean supports(Class<?> clazz) {
		return ShopModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ShopModel shop = (ShopModel) target;
		
		Integer emplNumber = shop.getEmplNumber();
		
		ValidationUtils.rejectIfEmpty(errors, "name", "shop.name.empty");
		ValidationUtils.rejectIfEmpty(errors, EMPLOYEES_NUMBER, "shop.emplNumber.empty");
		
		if (emplNumber != null && emplNumber < 1)
			errors.rejectValue(EMPLOYEES_NUMBER, "shop.emplNumber.lessThenOne");

	}

}
