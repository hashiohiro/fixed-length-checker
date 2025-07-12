package org.hashiohiro.fixedlengthchecker.core.model;

import lombok.Data;

/**
 * <pre>
 * Represents the validation result for a single field in a fixed-length record.
 * Holds field name, validity status, error message, and actual value for detailed reporting.
 * </pre>
 */
@Data
public class ValidationResult {

    /**
     * The name of the field being validated.
     */
    private String fieldName;

    /**
     * True if the field passed validation; false otherwise.
     */
    private boolean valid;

    /**
     * The message describing validation success or failure details.
     */
    private String message;

    /**
     * The actual value found in the field during validation.
     */
    private String actualValue;

    /**
     * <pre>
     * Constructs a new ValidationResult with specified field information.
     * Initializes all properties to describe a single field's validation outcome.
     * </pre>
     * @param fieldName the name of the field
     * @param valid true if validation passed
     * @param message the validation result message
     * @param actualValue the actual value from the record
     */
    public ValidationResult(String fieldName, boolean valid, String message, String actualValue) {
        this.fieldName = fieldName;
        this.valid = valid;
        this.message = message;
        this.actualValue = actualValue;
    }
}
