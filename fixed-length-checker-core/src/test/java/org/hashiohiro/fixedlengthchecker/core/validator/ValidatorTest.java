package org.hashiohiro.fixedlengthchecker.core.validator;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testFixedValueValidatorPass() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 3, "ABC", false, null, true);
        FixedValueValidator v = new FixedValueValidator();
        ValidationResult res = v.validate("FieldA", "ABC", def);
        assertTrue(res.isValid());
    }

    @Test
    void testFixedValueValidatorFail() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 3, "ABC", false, null, true);
        FixedValueValidator v = new FixedValueValidator();
        ValidationResult res = v.validate("FieldA", "DEF", def);
        assertFalse(res.isValid());
    }

    @Test
    void testBlankValidatorPass() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 3, null, true, null, true);
        BlankValidator v = new BlankValidator();
        ValidationResult res = v.validate("FieldA", "   ", def);
        assertTrue(res.isValid());
    }

    @Test
    void testBlankValidatorFail() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 3, null, true, null, true);
        BlankValidator v = new BlankValidator();
        ValidationResult res = v.validate("FieldA", "ABC", def);
        assertFalse(res.isValid());
    }

    @Test
    void testRegexValidatorPass() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, null, false, "\\d+", true);
        RegexValidator v = new RegexValidator();
        ValidationResult res = v.validate("FieldA", "12345", def);
        assertTrue(res.isValid());
    }

    @Test
    void testRegexValidatorFail() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, null, false, "\\d+", true);
        RegexValidator v = new RegexValidator();
        ValidationResult res = v.validate("FieldA", "ABCDE", def);
        assertFalse(res.isValid());
    }
}
