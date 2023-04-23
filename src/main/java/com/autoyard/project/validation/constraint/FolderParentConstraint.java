package com.autoyard.project.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FolderParentParametersValidator.class)
@Documented
public @interface FolderParentConstraint {
    String message() default "Folder-parent constraint violation";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}