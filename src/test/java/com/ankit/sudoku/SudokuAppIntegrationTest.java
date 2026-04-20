package com.ankit.sudoku;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuAppIntegrationTest {

    @Test
    void shouldShowHintAndRefreshGridBeforeNextPrompt() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayInputStream input = new ByteArrayInputStream("hint\nquit\n".getBytes(StandardCharsets.UTF_8));

        try {
            System.setIn(input);
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            SudokuApp.main(new String[0]);

            String console = output.toString(StandardCharsets.UTF_8);

            assertAll(
                    () -> assertTrue(console.contains("Welcome to Sudoku!")),
                    () -> assertTrue(console.contains("Here is your puzzle:")),
                    () -> assertTrue(console.contains("Hint: Cell ")),
                    () -> assertTrue(console.contains("Current grid:")),
                    () -> assertTrue(console.contains("Enter command (e.g., A3 4, C5 clear, hint, check, quit): Goodbye!"))
            );
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
