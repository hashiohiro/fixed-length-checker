package org.hashiohiro.fixedlengthchecker.core.definition;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;

import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * <pre>
 * Provides validation logic for field definitions to ensure correctness before use.
 * This class checks each field's properties, such as name, length, regex, and blank settings.
 * </pre>
 */
public class DefinitionValidator {

    /**
     * <pre>
     * Validates each field definition to ensure the configuration is correct.
     *
     * Throws an IllegalArgumentException if any field definition is invalid,
     * such as missing name, invalid length, or incorrect regex.
     * </pre>
     * @param definitions list of field definitions to validate
     * @throws IllegalArgumentException if an invalid field definition is found
     */
    public static void validate(List<FixedFieldDefinition> definitions) {
        for (FixedFieldDefinition def : definitions) {
            if (def.getName() == null || def.getName().isEmpty()) {
                throw new IllegalArgumentException("Field name is empty.");
            }

            if (def.getLength() <= 0) {
                throw new IllegalArgumentException("[" + def.getName() + "] length must be a positive integer.");
            }

            if (def.getFixedValue() != null && def.getFixedValue().length() != def.getLength()) {
                throw new IllegalArgumentException("[" + def.getName() + "] fixedValue length does not match length.");
            }

            if (def.getRegex() != null) {
                try {
                    java.util.regex.Pattern.compile(def.getRegex());
                } catch (PatternSyntaxException e) {
                    throw new IllegalArgumentException("[" + def.getName() + "] invalid regex: " + e.getDescription());
                }
            }

            if (def.isBlank() && def.getFixedValue() != null) {
                throw new IllegalArgumentException("[" + def.getName() + "] cannot have fixedValue when isBlank is true.");
            }
        }
    }
}
