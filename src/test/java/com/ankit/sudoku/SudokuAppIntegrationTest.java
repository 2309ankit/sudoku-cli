package com.ankit.sudoku;

import com.ankit.sudoku.model.SudokuBoard;
import com.ankit.sudoku.service.SudokuGenerator;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuAppIntegrationTest {

    @Test
    void shouldShowHintAndRefreshGridBeforeNextPrompt() {
        String console = runApp("hint\nquit\n", fixedGenerator(classicBoard()));

        assertAll(
                () -> assertTrue(console.contains("Welcome to Sudoku!")),
                () -> assertTrue(console.contains("Here is your puzzle:")),
                () -> assertTrue(console.contains("Hint: Cell ")),
                () -> assertTrue(console.contains("Current grid:")),
                () -> assertTrue(console.contains("Enter command (e.g., A3 4, C5 clear, hint, check, quit): Goodbye!"))
        );
    }

    @Test
    void shouldRejectUpdateToPrefilledCellFromCli() {
        String console = runApp("A1 6\nquit\n", fixedGenerator(classicBoard()));

        assertTrue(console.contains("Invalid move. A1 is pre-filled."));
    }

    @Test
    void shouldRejectInvalidCommandFromCli() {
        String console = runApp("Z9 4\nquit\n", fixedGenerator(classicBoard()));

        assertTrue(console.contains("Invalid command."));
    }

    @Test
    void shouldReportRowViolationFromCli() {
        String console = runApp("A3 3\ncheck\nquit\n", fixedGenerator(classicBoard()));

        assertTrue(console.contains("Number 3 already exists in Row A."));
    }

    @Test
    void shouldReportColumnViolationFromCli() {
        String console = runApp("C1 5\ncheck\nquit\n", fixedGenerator(classicBoard()));

        assertTrue(console.contains("Number 5 already exists in Column 1."));
    }

    @Test
    void shouldReportSubgridViolationFromCli() {
        String console = runApp("B2 8\ncheck\nquit\n", fixedGenerator(classicBoard()));

        assertTrue(console.contains("Number 8 already exists in the same 3x3 subgrid."));
    }

    @Test
    void shouldReplayAfterSuccessfulCompletion() {
        String console = runApp("A1 5\n \nquit\n", fixedGenerator(almostSolvedBoard(), classicBoard()));

        assertAll(
                () -> assertTrue(console.contains("You have successfully completed the Sudoku puzzle!")),
                () -> assertTrue(console.contains("Press any key to play again...")),
                () -> assertTrue(occurrences(console, "Here is your puzzle:") >= 2),
                () -> assertTrue(console.contains("Goodbye!"))
        );
    }

    @Test
    void shouldExitImmediatelyAfterQuitWithoutPrintingCurrentGridAgain() {
        String console = runApp("quit\n", fixedGenerator(classicBoard()));

        assertAll(
                () -> assertTrue(console.contains("Goodbye!")),
                () -> assertEquals(0, occurrences(console, "Current grid:"))
        );
    }

    private String runApp(String input, SudokuGenerator generator) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner(input);
             PrintStream out = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            SudokuApp.run(scanner, out, generator);
        }
        return output.toString(StandardCharsets.UTF_8);
    }

    private SudokuGenerator fixedGenerator(SudokuBoard... boards) {
        return new FixedSudokuGenerator(boards);
    }

    private int occurrences(String text, String fragment) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(fragment, index)) >= 0) {
            count++;
            index += fragment.length();
        }
        return count;
    }

    private SudokuBoard classicBoard() {
        int[][] puzzle = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        int[][] solution = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        return board(puzzle, solution);
    }

    private SudokuBoard almostSolvedBoard() {
        int[][] puzzle = {
                {0, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        int[][] solution = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        return board(puzzle, solution);
    }

    private SudokuBoard board(int[][] puzzle, int[][] solution) {
        boolean[][] fixed = new boolean[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fixed[row][col] = puzzle[row][col] != 0;
            }
        }
        return new SudokuBoard(puzzle, solution, fixed);
    }

    private static final class FixedSudokuGenerator extends SudokuGenerator {
        private final Queue<SudokuBoard> boards;

        private FixedSudokuGenerator(SudokuBoard... boards) {
            this.boards = new ArrayDeque<>(Arrays.asList(boards));
        }

        @Override
        public SudokuBoard generatePuzzle(int prefilledCells) {
            if (boards.isEmpty()) {
                throw new IllegalStateException("No more boards configured for test.");
            }
            return boards.remove();
        }
    }
}
