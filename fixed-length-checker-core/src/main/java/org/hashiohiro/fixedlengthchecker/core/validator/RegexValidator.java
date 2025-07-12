package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;

/**
 * Validator that checks if a field value matches a defined regular expression.
 * <pre>
 * Used when the field definition specifies a regex pattern to validate format or content.
 * </pre>
 */
public class RegexValidator implements FieldValidator {

    /**
     * Validates whether the given field value matches the regex in its definition.
     * <pre>
     * Returns a ValidationResult indicating success if the value matches the regex, or failure otherwise.
     * </pre>
     * @param fieldName name of the field being validated
     * @param value actual value to validate
     * @param def field definition containing the regex pattern
     * @return ValidationResult object indicating the result of the regex check
     */
    @Override
    public ValidationResult validate(String fieldName, String value, FixedFieldDefinition def) {
        if (def.getRegex() == null) return new ValidationResult(fieldName, true, "No regex", value);
        boolean ok = value.matches(def.getRegex());
        String msg = ok ? "Regex OK" : "❌ Regex mismatch [" + value + "]";
        return new ValidationResult(fieldName, ok, msg, value);
    }
}
