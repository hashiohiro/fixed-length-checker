package org.hashiohiro.fixedlengthchecker.core.service;

import org.hashiohiro.fixedlengthchecker.core.definition.DefinitionLoader;
import org.hashiohiro.fixedlengthchecker.core.definition.DefinitionValidator;
import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FixedLengthCheckerTest {

    @Test
    void testFileWithAllValidLines() throws Exception {
        FixedLengthChecker checker = new FixedLengthChecker(StandardCharsets.UTF_8, true);

        List<FixedFieldDefinition> defs = DefinitionLoader.loadDefinitions("src/test/resources/valid_definitions.json");
        DefinitionValidator.validate(defs);
        checker.loadDefinitions("src/test/resources/valid_definitions.json");

        List<ValidationResult> lineResult = checker.validateLine("21234567890");
        assertTrue(lineResult.stream().allMatch(ValidationResult::isValid));
    }

    @Test
    void testFileWithInvalidLine() throws Exception {
        FixedLengthChecker checker = new FixedLengthChecker(StandardCharsets.UTF_8, true);
        checker.loadDefinitions("src/test/resources/valid_definitions.json");

        List<ValidationResult> lineResult = checker.validateLine("2ABCDEFGHIJ");
        assertTrue(lineResult.stream().anyMatch(r -> !r.isValid()));
    }

    @Test
    void testRunWithoutLoadingDefinitions() {
        FixedLengthChecker checker = new FixedLengthChecker(StandardCharsets.UTF_8, true);

        Exception ex = assertThrows(IllegalStateException.class, () -> checker.validateLine("21234567890"));
        assertTrue(ex.getMessage().contains("Definitions not loaded"));
    }
}
