package com.autoyard.project.validation.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.UUID;


public class FolderParentParametersValidator implements ConstraintValidator<FolderParentConstraint, Object> {

    @Override
    @SneakyThrows
    public boolean isValid(Object catalog, ConstraintValidatorContext constraintValidatorContext) {
        String message = null;
        boolean folder = isFolder(catalog);
        UUID parentId = getParentId(catalog);

        if (!folder && parentId == null) {
            message = String.format(
                    "Parent id can't be null if cell is not folder. Now parentId = {%s}, folder  = {%s}",
                    parentId, folder);
        } else if (folder && parentId != null) {
            message = String.format(
                    "Parent id must be null if cell is folder. Now parentId = {%s}, folder  = {%s}",
                    parentId, folder);
        }

        if (message != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("parentId")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean isFolder(Object catalog) throws NoSuchFieldException, IllegalAccessException {
        Field field = catalog.getClass().getDeclaredField("folder");
        field.setAccessible(true);
        return (boolean) field.get(catalog);
    }

    private UUID getParentId(Object catalog) throws NoSuchFieldException, IllegalAccessException {
        Field field = catalog.getClass().getDeclaredField("parentId");
        field.setAccessible(true);
        return (UUID) field.get(catalog);
    }
}
