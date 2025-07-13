package org.hashiohiro.fixedlengthchecker.cli;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.catchSystemExit;
import static org.junit.jupiter.api.Assertions.*;

class CliTest {

    @Test
    void testInvalidLengthMode() throws Exception {
        String[] args = {"data.dat", "definitions.json", "--length-mode", "bits"};
        int statusCode = catchSystemExit(() -> Main.main(args));

        // ここで終了コードや標準出力内容を検証できる
        assertEquals(1, statusCode);
    }

    @Test
    void testMissingRequiredParams() throws Exception {
        String[] args = {};
        int statusCode = catchSystemExit(() -> Main.main(args));

        assertEquals(1, statusCode);
    }
}
