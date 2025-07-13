# 📄 FixedLengthChecker

A versatile and developer-friendly tool for validating fixed-length files 🚀  
Ideal for batch jobs, pre/post file checks, and seamless CI/CD integrations — all in one lightweight utility.

---

## ✨ Features

- Flexible field validation using external definition files
- Easy integration into CI/CD pipelines
- Supports both byte length and character length modes (via the `--length-mode` option)
- Works as both a CLI tool and a Java library
- Easily extendable through a strategy-based validation design
- Supports JSON definitions (YAML also supported)

---

## 💻 Usage (CLI)

You can flexibly combine options to suit a variety of scenarios, from simple checks to advanced validations.

```shell
java -jar FixedLengthChecker.jar <dataFile> <definitionFile> [--charset <charset>] [--length-mode <bytes|chars>]
```

### ⚙️ CLI Options

| Option             | Description                                                  | Default    |
|--------------------|--------------------------------------------------------------|------------|
| `<dataFile>`       | Path to the fixed-length data file to be validated           | (required) |
| `<definitionFile>` | Path to the JSON or YAML file defining field rules           | (required) |
| `--charset`        | Character encoding for reading the file (e.g., UTF-8, MS932) | UTF-8      |
| `--length-mode`    | Use `bytes` for byte count or `chars` for character count    | bytes      |

---

### ✅ Example

```shell
java -jar FixedLengthChecker.jar data/input.dat config/definitions.json --charset MS932 --length-mode bytes
```

---

## 💾 Sample Input File (`input.dat`)

```
2123456789
2ABCDEFGHIJ
```

#### Explanation

> This example uses MS932 encoding.

> - **Line 1:** Valid — Data Section = `2`, Inquiry Number = `123456789` (numeric)
> - **Line 2:** Invalid — Inquiry Number = `ABCDEFGHIJ` (non-numeric)

---

## 🗂 Sample Definition (JSON)

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

#### Definition Fields

| Field        | Description                                   |
|--------------|-----------------------------------------------|
| `name`       | Logical name shown in logs and errors         |
| `length`     | Field length (byte or char depending on mode) |
| `fixedValue` | Expected fixed value (optional)               |
| `blank`      | If true, field must be blank                  |
| `regex`      | Regex pattern to validate content (optional)  |
| `required`   | Must be present and non-empty if true         |

---

#### YAML Definition

In addition to JSON, you can also define field rules in YAML format.  
YAML allows adding inline comments, making it more readable and easier to maintain with your team.

```yaml
- name: "Data Section"      # Logical name shown in logs and errors
  length: 1                 # Field length (byte or char depending on mode)
  fixedValue: "2"           # Expected fixed value (optional)
  blank: false              # If true, field must be blank
  regex: null               # Regex pattern to validate content (optional)
  required: true            # Must be present and non-empty if true

- name: "Inquiry Number"
  length: 10
  fixedValue: null
  blank: false
  regex: "\\d+"             # Must be numeric
  required: true
```

---

## 💬 Example Output

The output clearly indicates success or failure for each field, making it easy to locate and fix data issues.

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

---

## ☕ Library Usage (Java)

The following example shows how to integrate validation into your Java application, with potential points for customization and error handling.

```java
import org.hashiohiro.fixedlengthchecker.core.service.FixedLengthChecker;
import org.hashiohiro.fixedlengthchecker.core.result.ValidationResult;

import java.nio.charset.Charset;
import java.util.List;

public class SampleUsage {
    public static void main(String[] args) throws Exception {
        // Create a FixedLengthChecker instance
        // true = byte length mode, false = character length mode
        FixedLengthChecker checker = new FixedLengthChecker(Charset.forName("MS932"), true);

        // Load field definitions from a JSON file
        checker.loadDefinitions("config/definitions.json");

        // Validate all lines in the data file
        List<List<ValidationResult>> results = checker.validateFile("data/input.dat");

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

---

## ⚖️ License

This project is licensed under the MIT License.  
See the LICENSE file for details.

---

## 📄 Notice

For third-party software attributions, please refer to the [NOTICE](NOTICE) file.

---

## 🧩 A Small Message

- Learn from the past,
- Embrace the present,
- Grow through challenges,
- Adapt with resilience,
- Create with passion,
- Yield consistent value.
