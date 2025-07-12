package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates each field in a fixed-length line using various validators.
 * <pre>
 * Supports byte-based or character-based length validation depending on configuration.
 * </pre>
 */
public class FixedLengthValidator {

    /**
     * Charset used to convert strings to bytes for byte-length validation.
     */
    private final Charset charset;

    /**
     * Flag to determine if length checks are performed using bytes instead of characters.
     */
    private final boolean useByteLength;

    /**
     * List of field validators to apply to each field.
     */
    private final List<FieldValidator> validators = Arrays.asList(
            new FixedValueValidator(),
            new BlankValidator(),
            new RegexValidator()
    );

    /**
     * Constructs a FixedLengthValidator with specified charset and length mode.
     * <pre>
     * Initializes validators for fixed value, blank, and regex checks.
     * </pre>
     * @param charset charset to use for byte conversions
     * @param useByteLength true for byte-based length checks; false for character-based
     */
    public FixedLengthValidator(Charset charset, boolean useByteLength) {
        this.charset = charset;
        this.useByteLength = useByteLength;
    }

    /**
     * Validates a single line against provided field definitions.
     * <pre>
     * Returns a list of validation results for each field in the line.
     * </pre>
     * @param line line string to validate
     * @param definitions list of field definitions
     * @return list of ValidationResult objects per field
     */
    public List<ValidationResult> validateLine(String line, List<FixedFieldDefinition> definitions) {
        List<ValidationResult> results = new ArrayList<>();
        int offset = 0;

        if (useByteLength) {
            byte[] lineBytes = line.getBytes(charset);

            for (FixedFieldDefinition def : definitions) {
                int length = def.getLength();

                if (lineBytes.length < offset + length) {
                    results.add(new ValidationResult(def.getName(), false, "❌ Insufficient field length", ""));
                    offset += length;
                    continue;
                }

                String value = new String(lineBytes, offset, length, charset);
                offset += length;

                appendFieldResult(results, def, value);
            }
        } else {
            for (FixedFieldDefinition def : definitions) {
                int length = def.getLength();

                if (line.length() < offset + length) {
                    results.add(new ValidationResult(def.getName(), false, "❌ Insufficient field length", ""));
                    offset += length;
                    continue;
                }

                String value = line.substring(offset, offset + length);
                offset += length;

                appendFieldResult(results, def, value);
            }
        }

        return results;
    }

    /**
     * Appends a validation result for a field using defined validators.
     * <pre>
     * Stops at first validation failure; otherwise adds a success result.
     * </pre>
     * @param results list to append results
     * @param def field definition
     * @param value actual value to validate
     */
    private void appendFieldResult(List<ValidationResult> results, FixedFieldDefinition def, String value) {
        boolean hasError = false;
        for (FieldValidator v : validators) {
            ValidationResult res = v.validate(def.getName(), value, def);
            if (!res.isValid()) {
                results.add(res);
                hasError = true;
                break;
            }
        }

        if (!hasError) {
            results.add(new ValidationResult(def.getName(), true, "✅ Check OK", value));
        }
    }
}
