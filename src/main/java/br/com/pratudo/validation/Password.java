package br.com.pratudo.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
// A senha deve conter ao menos 8 caracteres, uma letra maiúscula, uma letra minúscula e um caractere especial.
@Pattern(regexp = ".{8,}")
public @interface Password {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
