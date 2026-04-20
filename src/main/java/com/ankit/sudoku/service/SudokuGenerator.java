package com.ankit.sudoku.service;

import com.ankit.sudoku.model.SudokuBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
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

        int[][] solution = new int[9][9];
        fillBoard(solution);

        int[][] puzzle = copy(solution);
        boolean[][] fixed = new boolean[9][9];

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);

        int cellsToRemove = 81 - prefilledCells;
        for (int i = 0; i < cellsToRemove; i++) {
            int index = indices.get(i);
            int row = index / 9;
            int col = index % 9;
            puzzle[row][col] = 0;
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fixed[row][col] = puzzle[row][col] != 0;
            }
        }

        return new SudokuBoard(puzzle, solution, fixed);
    }

    private boolean fillBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    List<Integer> candidates = shuffledOneToNine();
                    for (int value : candidates) {
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
            }
        }
        return true;
    }

    private List<Integer> shuffledOneToNine() {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            values.add(i);
        }
        Collections.shuffle(values, random);
        return values;
    }

    private boolean isSafe(int[][] board, int row, int col, int value) {
        for (int i = 0; i < 9; i++) {
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
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
