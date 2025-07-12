package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;

/**
 * Validator that checks if a field value exactly matches a predefined fixed value.
 * <pre>
 * Used when the field definition specifies a fixed value to enforce strict matching.
 * </pre>
 */
public class FixedValueValidator implements FieldValidator {

    /**
     * Validates whether the given field value matches the fixed value in its definition.
     * <pre>
     * Returns a ValidationResult indicating success if values match, or failure otherwise.
     * </pre>
     * @param fieldName name of the field being validated
     * @param value actual value to validate
     * @param def field definition containing the fixed value
     * @return ValidationResult object indicating the result of the fixed value check
     */
    @Override
    public ValidationResult validate(String fieldName, String value, FixedFieldDefinition def) {
        if (def.getFixedValue() == null) return new ValidationResult(fieldName, true, "No fixed value", value);
        boolean ok = value.equals(def.getFixedValue());
        String msg = ok ? "Fixed value match" : "❌ Fixed value mismatch [" + value + "]";
        return new ValidationResult(fieldName, ok, msg, value);
    }
}
