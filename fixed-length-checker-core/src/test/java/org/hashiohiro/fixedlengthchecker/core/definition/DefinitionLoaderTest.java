package org.hashiohiro.fixedlengthchecker.core.definition;

import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DefinitionLoaderTest {

    @Test
    void testLoadValidJsonDefinitionFile() throws Exception {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("valid_definitions.json")).getFile();
        File file = new File(filePath);
        List<FixedFieldDefinition> defs = DefinitionLoader.loadDefinitions(file.getAbsolutePath());
        assertFalse(defs.isEmpty(), "Definitions should be loaded");
    }

    @Test
    void testLoadValidYamlDefinitionFile() throws Exception {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("valid_definitions.yaml")).getFile();
        File file = new File(filePath);
        List<FixedFieldDefinition> defs = DefinitionLoader.loadDefinitions(file.getAbsolutePath());
        assertFalse(defs.isEmpty(), "Definitions should be loaded");
    }

    @Test
    void testFileNotExist() {
        Exception ex = assertThrows(Exception.class, () -> DefinitionLoader.loadDefinitions("src/test/resources/not_exists.json"));
        assertTrue(ex.getMessage().contains("FileNotFoundException") || ex.getMessage().contains("not_exists.json"));
    }

    @Test
    void testUnsupportedExtension() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("definitions.txt")).getFile();
            File file = new File(filePath);
            DefinitionLoader.loadDefinitions(file.getAbsolutePath());
        });
        assertTrue(ex.getMessage().contains("Unsupported definition file format"));
    }

    @Test
    void testMissingFieldsInJson() {
        Exception ex = assertThrows(Exception.class, () -> {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("missing_fields.json")).getFile();
            File file = new File(filePath);
            DefinitionLoader.loadDefinitions(file.getAbsolutePath());
        });
        assertNotNull(ex.getMessage());
    }

    @Test
    void testMalformedYaml() {
        Exception ex = assertThrows(Exception.class, () -> {
            String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("malformed.yaml")).getFile();
            File file = new File(filePath);
            DefinitionLoader.loadDefinitions(file.getAbsolutePath());
        });
        assertNotNull(ex.getMessage());
    }
}
