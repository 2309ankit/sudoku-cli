package com.ankit.sudoku.service;

import com.ankit.sudoku.model.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuGeneratorTest {

    @Test
    void shouldGeneratePuzzleWithRequestedPrefilledCellCount() {
        SudokuGenerator generator = new SudokuGenerator(new Random(42));

        SudokuBoard board = generator.generatePuzzle(30);

        int fixedCount = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.isFixed(row, col)) {
                    fixedCount++;
                    assertTrue(board.getValue(row, col) >= 1 && board.getValue(row, col) <= 9);
                } else {
                    assertEquals(0, board.getValue(row, col));
                }
                assertTrue(board.getSolutionValue(row, col) >= 1 && board.getSolutionValue(row, col) <= 9);
            }
        }

        assertEquals(30, fixedCount);
    }

    @Test
    void shouldRejectInvalidPrefilledCellCounts() {
        SudokuGenerator generator = new SudokuGenerator(new Random(1));

        assertThrows(IllegalArgumentException.class, () -> generator.generatePuzzle(-1));
        assertThrows(IllegalArgumentException.class, () -> generator.generatePuzzle(82));
    }
}
