package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixedLengthValidatorTest {

    @Test
    void testAllFieldsValidInLine() {
        FixedFieldDefinition def1 = new FixedFieldDefinition("Field1", 2, "AB", false, null, true);
        FixedFieldDefinition def2 = new FixedFieldDefinition("Field2", 3, null, false, "\\d+", true);

        String line = "AB123";
        FixedLengthValidator validator = new FixedLengthValidator(StandardCharsets.UTF_8, true);
        List<ValidationResult> results = validator.validateLine(line, List.of(def1, def2));

        assertTrue(results.stream().allMatch(ValidationResult::isValid));
    }

    @Test
    void testLineLengthTooShort() {
        FixedFieldDefinition def1 = new FixedFieldDefinition("Field1", 5, null, false, null, true);

        String line = "ABC";
        FixedLengthValidator validator = new FixedLengthValidator(StandardCharsets.UTF_8, true);
        List<ValidationResult> results = validator.validateLine(line, List.of(def1));

        assertTrue(results.get(0).getMessage().contains("length error") || !results.get(0).isValid());
    }

    @Test
    void testMultipleFieldErrorsStopOnFirst() {
        FixedFieldDefinition def1 = new FixedFieldDefinition("Field1", 2, "XY", false, null, true);
        FixedFieldDefinition def2 = new FixedFieldDefinition("Field2", 3, null, false, "\\d+", true);

        String line = "ABabc";
        FixedLengthValidator validator = new FixedLengthValidator(StandardCharsets.UTF_8, true);
        List<ValidationResult> results = validator.validateLine(line, List.of(def1, def2));

        assertFalse(results.get(0).isValid());
    }

    @Test
    void testByteLengthMode() {
        FixedFieldDefinition def1 = new FixedFieldDefinition("Field1", 4, null, false, null, true);

        String line = "ＡＢ"; // Full-width chars (2 bytes each in MS932)
        FixedLengthValidator validator = new FixedLengthValidator(StandardCharsets.UTF_8, true);
        List<ValidationResult> results = validator.validateLine(line, List.of(def1));

        assertTrue(results.get(0).isValid());
    }

    @Test
    void testCharLengthMode() {
        FixedFieldDefinition def1 = new FixedFieldDefinition("Field1", 2, null, false, null, true);

        String line = "ＡＢ";
        FixedLengthValidator validator = new FixedLengthValidator(StandardCharsets.UTF_8, false);
        List<ValidationResult> results = validator.validateLine(line, List.of(def1));

        assertTrue(results.get(0).isValid());
    }
}
