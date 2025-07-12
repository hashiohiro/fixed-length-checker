package org.hashiohiro.fixedlengthchecker.core.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * Represents a single field definition used in fixed-length file validation.
 * This model defines field name, length, fixed value, regex, and blank or required flags.
 * </pre>
 */
@Data
@RequiredArgsConstructor
public class FixedFieldDefinition {

    /**
     * The field name used for identification in validation and logging.
     */
    private String name;

    /**
     * The expected length of the field in characters or bytes.
     */
    private int length;

    /**
     * The fixed value to match, if applicable. Must match length when set.
     */
    private String fixedValue;

    /**
     * Indicates whether this field is expected to be blank.
     */
    private boolean blank;

    /**
     * The regex pattern for value validation. Compiled during definition validation.
     */
    private String regex;

    /**
     * Indicates if this field is required in the record.
     */
    private boolean required;
}
