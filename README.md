# FixedLengthChecker

A versatile fixed-length file validation tool 🚀

This tool validates the consistency of each field in fixed-length files based on JSON definition files.  
It can be used for file verification before/after output, pre-checks for batch processing, or as part of CI/CD
pipelines.

---

## Features

- Flexible field validation using external definition files
- Supports both byte length and character length modes (`--length-mode` option)
- Strategy pattern-based rule extension for future enhancements
- Usable as both a CLI tool and a Java library
- JSON definition support (YAML planned for future)

---

## Usage (CLI)

```shell
java -jar FixedLengthChecker.jar <dataFile> <definitionJson> [--charset <charset>] [--length-mode <bytes|chars>]
```

## CLI Options
| Option             | Description                                                       | Default    |
| ------------------ | ----------------------------------------------------------------- | ---------- |
| `<dataFile>`       | Path to the fixed-length data file to be validated.               | (required) |
| `<definitionJson>` | Path to the JSON definition file describing field rules.          | (required) |
| `--charset`        | Character encoding to read the file (e.g., UTF-8, MS932).         | UTF-8      |
| `--length-mode`    | Length mode: `bytes` for byte count, `chars` for character count. | bytes      |

## Example

```shell
java -jar FixedLengthChecker.jar data/output.dat config/definitions.json --charset MS932 --length-mode bytes
```

## Example Definition JSON

```json
[
  {
    "name": "Data Section",
    "length": 1,
    "fixedValue": "2",
    "blank": false,
    "regex": null,
    "required": true
  },
  {
    "name": "Inquiry Number",
    "length": 10,
    "fixedValue": null,
    "blank": false,
    "regex": "\\d+",
    "required": true
  }
]
```

## Example Output

```
----- Line 1 -----
✅ Data Section Check OK [2]
✅ Inquiry Number Check OK [0000000123]
----- Line 1 End -----

----- Line 2 -----
✅ Data Section Check OK [2]
❌ Inquiry Number Regex Mismatch [ABCDEFGHIJ]
----- Line 2 End -----
```

## Library Usage (Java)

```java
import org.hashiohiro.fixedlengthchecker.core.service.FixedLengthChecker;
import org.hashiohiro.fixedlengthchecker.core.result.ValidationResult;

import java.nio.charset.Charset;
import java.util.List;

public class SampleUsage {
    public static void main(String[] args) throws Exception {
        // Create a checker instance
        // The first parameter specifies the charset (e.g., MS932 or UTF-8)
        // The second parameter specifies whether to use byte length (true) or character length (false)
        FixedLengthChecker checker = new FixedLengthChecker(Charset.forName("MS932"), true);

        // Load field definitions from a JSON file
        checker.loadDefinitions("config/definitions.json");

        // Validate all lines in the data file
        List<List<ValidationResult>> results = checker.validateFile("data/output.dat");

        // Print validation results line by line
        for (int i = 0; i < results.size(); i++) {
            System.out.println("----- Line " + (i + 1) + " -----");
            for (ValidationResult res : results.get(i)) {
                System.out.println(res.getMessage() + " [" + res.getActualValue() + "]");
            }
            System.out.println("----- End of Line " + (i + 1) + " -----\n");
        }
    }
}

```

## License

This project is licensed under the Apache License 2.0. 

See the LICENSE file for details.

## Notice

This product includes software developed by FasterXML (Jackson).

This product includes software developed by Project Lombok.

## 🧩 Easter Egg

- Learn from the past,
- Embrace the present,
- Grow through challenges,
- Adapt with resilience,
- Create with passion,
- Yield consistent value.