package org.hashiohiro.fixedlengthchecker.cli;

import org.hashiohiro.fixedlengthchecker.core.model.ValidationResult;
import org.hashiohiro.fixedlengthchecker.core.service.FixedLengthChecker;

import java.nio.charset.Charset;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java -jar FixedLengthChecker.jar <dataFile> <definitionJson> [--charset <charset>] [--length-mode <bytes|chars>]");
            return;
        }

        String dataFile = args[0];
        String defFile = args[1];

        Charset charset = Charset.forName("UTF-8");
        boolean useByteLength = true; // Default: bytes

        // Parse optional arguments
        for (int i = 2; i < args.length - 1; i++) {
            if ("--charset".equals(args[i])) {
                charset = Charset.forName(args[i + 1]);
            } else if ("--length-mode".equals(args[i])) {
                String mode = args[i + 1];
                if ("chars".equalsIgnoreCase(mode)) {
                    useByteLength = false;
                } else if ("bytes".equalsIgnoreCase(mode)) {
                    useByteLength = true;
                } else {
                    System.err.println("Invalid length-mode. Use 'bytes' or 'chars'.");
                    return;
                }
            }
        }

        FixedLengthChecker checker = new FixedLengthChecker(charset, useByteLength);

        checker.loadDefinitions(defFile);

        List<List<ValidationResult>> results = checker.validateFile(dataFile);

        for (int i = 0; i < results.size(); i++) {
            System.out.println("----- Line " + (i + 1) + " -----");
            for (ValidationResult res : results.get(i)) {
                System.out.println(res.getMessage() + " [" + res.getActualValue() + "]");
            }
            System.out.println("----- End of Line " + (i + 1) + " -----\n");
        }
    }
}
