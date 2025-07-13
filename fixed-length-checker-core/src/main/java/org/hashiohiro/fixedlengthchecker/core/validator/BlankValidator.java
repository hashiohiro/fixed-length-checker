package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;

/**
 * Validator that checks if a field value is blank when expected.
 * <pre>
 * Used for fields marked with isBlank=true to ensure they contain only whitespace or are empty.
 * </pre>
 */
public class BlankValidator implements FieldValidator {

    /**
     * Validates whether the value of a field is blank based on its definition.
     * <pre>
     * Returns a ValidationResult indicating success or failure with a descriptive message.
     * </pre>
     * @param fieldName name of the field being validated
     * @param value actual value to check
     * @param def field definition containing blank configuration
     * @return ValidationResult object with the validation outcome
     */
    @Override
    public ValidationResult validate(String fieldName, String value, FixedFieldDefinition def) {
        if (!def.isBlank()) return new ValidationResult(fieldName, true, "No blank check", value);
        boolean ok = value.isBlank();
        String msg = ok ? "Blank OK" : "❌ Not blank [" + value + "]";
        return new ValidationResult(fieldName, ok, msg, value);
    }
}
