
package org.hashiohiro.fixedlengthchecker.core.definition;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 * Loads field definitions from a JSON file into a list of FixedFieldDefinition objects.
 * This class uses Jackson to parse the JSON definition file and convert it into a list.
 * </pre>
 */
public class DefinitionLoader {

    /**
     * <pre>
     * Loads field definitions from the specified JSON file path.
     * This method parses a JSON file and returns a list of field definitions for fixed-length file validation.
     * </pre>
     * @param path the path to the JSON definition file
     * @return a list of FixedFieldDefinition objects
     * @throws IOException if file reading or JSON parsing fails
     */
    public static List<FixedFieldDefinition> loadDefinitions(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), new TypeReference<List<FixedFieldDefinition>>() {
        });
    }
}
