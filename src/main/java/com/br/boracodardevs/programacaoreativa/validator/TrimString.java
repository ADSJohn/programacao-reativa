package com.br.boracodardevs.programacaoreativa.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = TrimStringValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrimString {

	String message() default "{field cannot have blank espaces at the beginning or at end}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
