package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;

/**
 * <pre>
 * Interface for field validators used in fixed-length file validation.
 * Implementations perform checks such as blank, fixed value, or regex validation for each field.
 * </pre>
 */
public interface FieldValidator {

    /**
     * <pre>
     * Validates a single field value based on its definition.
     * Returns a ValidationResult describing whether the field passes or fails the specific check.
     * </pre>
     * @param fieldName name of the field being validated
     * @param value actual value to validate
     * @param def field definition providing validation rules
     * @return ValidationResult object indicating the outcome of the validation
     */
    ValidationResult validate(String fieldName, String value, FixedFieldDefinition def);
}
