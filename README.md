# 📄 FixedLengthChecker

A versatile, developer-friendly tool for validating fixed-length data files 🚀

This tool checks each field in fixed-length files based on external JSON definition files.
It is ideal for pre- and post-processing file checks, batch job validations, or as part of your CI/CD pipelines.

---

## ✨ Features

- Flexible field validation using external definition files
- Supports both byte length and character length modes (--length-mode option)
- Easy rule extension with strategy pattern
- Usable as both a CLI tool and a Java library
- JSON definition support (YAML support planned)

---

## 💻 Usage (CLI)

```shell
java -jar FixedLengthChecker.jar <dataFile> <definitionJson> [--charset <charset>] [--length-mode <bytes|chars>]
```

## ⚙️ CLI Options

| Option             | Description                                                       | Default    |
|--------------------|-------------------------------------------------------------------|------------|
| `<dataFile>`       | Path to the fixed-length data file to be validated.               | (required) |
| `<definitionJson>` | Path to the JSON definition file describing field rules.          | (required) |
| `--charset`        | Character encoding to read the file (e.g., UTF-8, MS932).         | UTF-8      |
| `--length-mode`    | Length mode: `bytes` for byte count, `chars` for character count. | bytes      |

## ✅ Example

```shell
java -jar FixedLengthChecker.jar data/output.dat config/definitions.json --charset MS932 --length-mode bytes
```

### 💾 Sample Input File (input.dat)

```text
2123456789
2ABCDEFGHIJ
```

#### Explanation:

- Line 1: Valid — Data Section = 2, Inquiry Number = 123456789 (numeric)
- Line 2: Invalid — Inquiry Number = ABCDEFGHIJ (non-numeric)

### 🗂 Sample Definition JSON

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

#### Definition Fields:

| Field        | Description                                   |
|--------------|-----------------------------------------------|
| `name`       | Logical name shown in logs and errors         |
| `length`     | Field length (byte or char depending on mode) |
| `fixedValue` | Expected fixed value (optional)               |
| `blank`      | If true, field must be blank                  |
| `regex`      | Regex pattern to validate content (optional)  |
| `required`   | Must be present and non-empty if true         |

## 💬 Example Output

```
----- Line 1 -----
✅ Data Section Check OK [2]
✅ Inquiry Number Check OK [123456789]
----- End of Line 1 -----

----- Line 2 -----
✅ Data Section Check OK [2]
❌ Inquiry Number Regex Mismatch [ABCDEFGHIJ]
----- End of Line 2 -----
```

## ☕ Library Usage (Java)

```java
import org.hashiohiro.fixedlengthchecker.core.service.FixedLengthChecker;
import org.hashiohiro.fixedlengthchecker.core.result.ValidationResult;

import java.nio.charset.Charset;
import java.util.List;

public class SampleUsage {
    public static void main(String[] args) throws Exception {
        // Create a FixedLengthChecker instance
        // true = byte length mode, false = char length mode
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

## ⚖️ License

This project is licensed under the MIT License.

See the LICENSE file for details.

## 📄 Notice

For third-party software attributions, please refer to the NOTICE file.

## 🧩 A Small Message

- Learn from the past,
- Embrace the present,
- Grow through challenges,
- Adapt with resilience,
- Create with passion,
- Yield consistent value.