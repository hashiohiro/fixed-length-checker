package org.hashiohiro.fixedlengthchecker.core.service;

import org.hashiohiro.fixedlengthchecker.core.definition.DefinitionLoader;
import org.hashiohiro.fixedlengthchecker.core.definition.DefinitionValidator;
import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;
import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;
import org.hashiohiro.fixedlengthchecker.core.validator.FixedLengthValidator;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade service for validating fixed-length files using predefined field definitions.
 * <pre>
 * Provides methods to load definitions, validate files, and validate single lines.
 * </pre>
 */
public class FixedLengthChecker {

    /**
     * Charset used to read and interpret file contents.
     */
    private final Charset charset;

    /**
     * Flag to determine if length checks use byte length instead of character length.
     */
    private final boolean useByteLength;

    /**
     * Loaded list of field definitions for validation.
     */
    private List<FixedFieldDefinition> definitions;

    /**
     * Core validator instance for performing field-level checks.
     */
    private final FixedLengthValidator validator;

    /**
     * Constructs a new FixedLengthChecker with specified charset and length mode.
     * <pre>
     * Initializes the validator and prepares for file or line validations.
     * </pre>
     * @param charset charset to use for file reading
     * @param useByteLength true to use byte length, false for character length
     */
    public FixedLengthChecker(Charset charset, boolean useByteLength) {
        this.charset = charset;
        this.useByteLength = useByteLength;
        this.validator = new FixedLengthValidator(charset, useByteLength);
    }

    /**
     * Loads and validates field definitions from a JSON file.
     * <pre>
     * Must be called before any validation; validates definitions immediately after loading.
     * </pre>
     * @param jsonPath path to the JSON definition file
     * @throws Exception if loading or validation fails
     */
    public void loadDefinitions(String jsonPath) throws Exception {
        this.definitions = DefinitionLoader.loadDefinitions(jsonPath);
        DefinitionValidator.validate(this.definitions);
    }

    /**
     * Validates all lines in a fixed-length file and returns results for each line.
     * <pre>
     * Throws IllegalStateException if definitions are not loaded.
     * </pre>
     * @param filePath path to the file to validate
     * @return list of validation results per line
     * @throws Exception if file reading fails
     */
    public List<List<ValidationResult>> validateFile(String filePath) throws Exception {
        if (definitions == null) {
            throw new IllegalStateException("Definitions not loaded. Call loadDefinitions() first.");
        }

        List<String> lines = Files.readAllLines(Path.of(filePath), charset);
        List<List<ValidationResult>> allResults = new ArrayList<>();

        for (String line : lines) {
            List<ValidationResult> lineResults = validator.validateLine(line, definitions);
            allResults.add(lineResults);
        }

        return allResults;
    }

    /**
     * <pre>
     * Validates a single line string against loaded definitions.
     * Throws IllegalStateException if definitions are not loaded before calling.
     * </pre>
     * @param line line string to validate
     * @return list of validation results for the line
     */
    public List<ValidationResult> validateLine(String line) {
        if (definitions == null) {
            throw new IllegalStateException("Definitions not loaded. Call loadDefinitions() first.");
        }
        return validator.validateLine(line, definitions);
    }
}
