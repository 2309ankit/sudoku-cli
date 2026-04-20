package com.ankit.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SudokuBoardTest {

    @Test
    void shouldReturnDefensiveSnapshotCopy() {
        SudokuBoard board = createBoard();

        int[][] snapshot = board.snapshot();
        snapshot[0][0] = 9;

        assertNotEquals(9, board.getValue(0, 0));
    }

    @Test
    void shouldDetectIncompleteBoard() {
        SudokuBoard board = createBoard();

        assertFalse(board.isComplete());
        assertFalse(board.matchesSolution());
    }

    @Test
    void shouldDetectBoardThatMatchesSolution() {
        int[][] solution = solvedGrid();
        boolean[][] fixed = new boolean[9][9];
        SudokuBoard board = new SudokuBoard(solution, solution, fixed);

        assertTrue(board.isComplete());
        assertTrue(board.matchesSolution());
    }

    private SudokuBoard createBoard() {
        int[][] puzzle = solvedGrid();
        puzzle[0][0] = 0;

        boolean[][] fixed = new boolean[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fixed[row][col] = puzzle[row][col] != 0;
            }
        }

        return new SudokuBoard(puzzle, solvedGrid(), fixed);
    }

    private int[][] solvedGrid() {
        return new int[][]{
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
    }
}
