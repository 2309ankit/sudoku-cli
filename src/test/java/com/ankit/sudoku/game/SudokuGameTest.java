package com.ankit.sudoku.game;

import com.ankit.sudoku.model.CellPosition;
import com.ankit.sudoku.model.SudokuBoard;
import com.ankit.sudoku.parser.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGameTest {

    @Test
    void shouldRejectUpdateToFixedCell() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.place(new CellPosition(0, 0), 9));

        assertEquals("Invalid move. A1 is pre-filled.", result);
    }

    @Test
    void shouldAcceptValidMoveOnEditableCell() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.place(new CellPosition(0, 2), 4));

        assertEquals("Move accepted.", result);
        assertEquals(4, game.getBoard().getValue(0, 2));
    }

    @Test
    void shouldClearEditableCell() {
        SudokuGame game = new SudokuGame(createBoard());
        game.handle(Command.place(new CellPosition(0, 2), 4));

        String result = game.handle(Command.clear(new CellPosition(0, 2)));

        assertEquals("Cell cleared.", result);
        assertEquals(0, game.getBoard().getValue(0, 2));
    }

    @Test
    void shouldRevealHint() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.hint());

        assertTrue(result.startsWith("Hint: Cell "));
    }

    @Test
    void shouldRejectValueOutsideAllowedRange() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.place(new CellPosition(0, 2), 0));

        assertEquals("Invalid move. Number must be between 1 and 9.", result);
    }

    @Test
    void shouldRejectClearingFixedCell() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.clear(new CellPosition(0, 0)));

        assertEquals("Invalid move. A1 is pre-filled.", result);
    }

    @Test
    void shouldReturnCheckResultFromValidator() {
        SudokuGame game = new SudokuGame(createBoard());
        game.handle(Command.place(new CellPosition(0, 2), 3));

        String result = game.handle(Command.check());

        assertEquals("Number 3 already exists in Row A.", result);
    }

    @Test
    void shouldQuitGameWhenRequested() {
        SudokuGame game = new SudokuGame(createBoard());

        String result = game.handle(Command.quit());

        assertEquals("Goodbye!", result);
        assertTrue(game.isQuitRequested());
        assertTrue(game.isFinished());
    }

    @Test
    void shouldMarkGameCompleteWhenFinalValueIsPlaced() {
        SudokuGame game = new SudokuGame(createAlmostSolvedBoard());

        String result = game.handle(Command.place(new CellPosition(0, 0), 5));

        assertEquals("Move accepted.", result);
        assertTrue(game.isCompletedSuccessfully());
        assertTrue(game.isFinished());
    }

    @Test
    void shouldReturnNoHintsWhenBoardAlreadyMatchesSolution() {
        SudokuGame game = new SudokuGame(createSolvedBoard());

        String result = game.handle(Command.hint());

        assertEquals("No hints available.", result);
    }

    private SudokuBoard createBoard() {
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

        boolean[][] fixed = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixed[r][c] = puzzle[r][c] != 0;
            }
        }

        return new SudokuBoard(puzzle, solution, fixed);
    }

    private SudokuBoard createAlmostSolvedBoard() {
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

        return createBoard(puzzle, solvedGrid());
    }

    private SudokuBoard createSolvedBoard() {
        int[][] solution = solvedGrid();
        return createBoard(solution, solution);
    }

    private SudokuBoard createBoard(int[][] puzzle, int[][] solution) {
        boolean[][] fixed = new boolean[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                fixed[r][c] = puzzle[r][c] != 0;
            }
        }

        return new SudokuBoard(puzzle, solution, fixed);
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
