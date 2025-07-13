
package org.hashiohiro.fixedlengthchecker.core.definition;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hashiohiro.fixedlengthchecker.core.model.FixedFieldDefinition;

import java.io.File;
import java.util.List;

/**
 * Loads field definitions from a JSON file into a list of FixedFieldDefinition objects.
 * <pre>
 * This class uses Jackson to parse the JSON definition file and convert it into a list.
 * </pre>
 */
public class DefinitionLoader {

    /**
     * Load definitions from JSON or YAML file based on file extension.
     *
     * @param path Path to definition file (.json, .yml, .yaml)
     * @return List of FixedFieldDefinition
     * @throws Exception When parsing fails
     */
    public static List<FixedFieldDefinition> loadDefinitions(String path) throws Exception {
        if (path.endsWith(".json")) {
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.readValue(new File(path), new TypeReference<>() {
            });
        } else if (path.endsWith(".yml") || path.endsWith(".yaml")) {
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            return yamlMapper.readValue(new File(path), new TypeReference<>() {
            });
        } else {
            throw new IllegalArgumentException("Unsupported definition file format. Use .json, .yml, or .yaml");
        }
    }
}
