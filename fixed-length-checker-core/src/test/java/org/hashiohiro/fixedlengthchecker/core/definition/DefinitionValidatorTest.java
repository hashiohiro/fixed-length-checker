package org.hashiohiro.fixedlengthchecker.core.definition;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefinitionValidatorTest {

    @Test
    void testValidDefinition() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, null, false, "\\d+", true);
        assertDoesNotThrow(() -> DefinitionValidator.validate(List.of(def)));
    }

    @Test
    void testEmptyName() {
        FixedFieldDefinition def = new FixedFieldDefinition("", 5, null, false, null, true);
        assertThrows(IllegalArgumentException.class, () -> DefinitionValidator.validate(List.of(def)));
    }

    @Test
    void testInvalidLength() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", -1, null, false, null, true);
        assertThrows(IllegalArgumentException.class, () -> DefinitionValidator.validate(List.of(def)));
    }

    @Test
    void testFixedValueLengthMismatch() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, "ABC", false, null, true);
        assertThrows(IllegalArgumentException.class, () -> DefinitionValidator.validate(List.of(def)));
    }

    @Test
    void testInvalidRegex() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, null, false, "[", true);
        assertThrows(IllegalArgumentException.class, () -> DefinitionValidator.validate(List.of(def)));
    }

    @Test
    void testBlankAndFixedValueConflict() {
        FixedFieldDefinition def = new FixedFieldDefinition("FieldA", 5, "ABCDE", true, null, true);
        assertThrows(IllegalArgumentException.class, () -> DefinitionValidator.validate(List.of(def)));
    }
}
