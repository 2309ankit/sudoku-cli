package com.ankit.sudoku.service;

import com.ankit.sudoku.model.SudokuBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
    private static final int BOARD_SIZE = 9;
    private static final int[] NO_EMPTY_CELL = new int[0];
    private final Random random;

    public SudokuGenerator() {
        this(new Random());
    }

    public SudokuGenerator(Random random) {
        this.random = random;
    }

    public SudokuBoard generatePuzzle(int prefilledCells) {
        if (prefilledCells < 0 || prefilledCells > 81) {
            throw new IllegalArgumentException("Prefilled cell count must be between 0 and 81.");
        }

        int[][] solution = new int[BOARD_SIZE][BOARD_SIZE];
        fillBoard(solution);

        int[][] puzzle = copy(solution);
        boolean[][] fixed = new boolean[BOARD_SIZE][BOARD_SIZE];

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);

        int cellsToRemove = (BOARD_SIZE * BOARD_SIZE) - prefilledCells;
        for (int i = 0; i < cellsToRemove; i++) {
            int index = indices.get(i);
            int row = index / BOARD_SIZE;
            int col = index % BOARD_SIZE;
            puzzle[row][col] = 0;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                fixed[row][col] = puzzle[row][col] != 0;
            }
        }

        return new SudokuBoard(puzzle, solution, fixed);
    }

    private boolean fillBoard(int[][] board) {
        int[] emptyCell = findEmptyCell(board);
        if (emptyCell.length == 0) {
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];
        for (int value : shuffledOneToNine()) {
            if (isSafe(board, row, col, value)) {
                board[row][col] = value;
                if (fillBoard(board)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private int[] findEmptyCell(int[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return NO_EMPTY_CELL;
    }

    private List<Integer> shuffledOneToNine() {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= BOARD_SIZE; i++) {
            values.add(i);
        }
        Collections.shuffle(values, random);
        return values;
    }

    private boolean isSafe(int[][] board, int row, int col, int value) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[row][i] == value || board[i][col] == value) {
                return false;
            }
        }

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[][] copy(int[][] source) {
        int[][] copy = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, BOARD_SIZE);
        }
        return copy;
    }
}
