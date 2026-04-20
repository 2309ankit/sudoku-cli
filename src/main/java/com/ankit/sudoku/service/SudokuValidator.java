package com.ankit.sudoku.service;

public class SudokuValidator {
    private static final int BOARD_SIZE = 9;
    private static final String NUMBER_PREFIX = "Number ";

    public String validate(int[][] grid) {
        String rowViolation = validateRows(grid);
        if (rowViolation != null) {
            return rowViolation;
        }

        String columnViolation = validateColumns(grid);
        if (columnViolation != null) {
            return columnViolation;
        }

        String boxViolation = validateBoxes(grid);
        if (boxViolation != null) {
            return boxViolation;
        }

        return "No rule violations detected.";
    }

    private String validateRows(int[][] grid) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            int[] seen = new int[10];
            for (int col = 0; col < BOARD_SIZE; col++) {
                int value = grid[row][col];
                if (value == 0) {
                    continue;
                }
                if (seen[value] > 0) {
                    char rowLabel = (char) ('A' + row);
                    return NUMBER_PREFIX + value + " already exists in Row " + rowLabel + ".";
                }
                seen[value]++;
            }
        }
        return null;
    }

    private String validateColumns(int[][] grid) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            int[] seen = new int[10];
            for (int row = 0; row < BOARD_SIZE; row++) {
                int value = grid[row][col];
                if (value == 0) {
                    continue;
                }
                if (seen[value] > 0) {
                    return NUMBER_PREFIX + value + " already exists in Column " + (col + 1) + ".";
                }
                seen[value]++;
            }
        }
        return null;
    }

    private String validateBoxes(int[][] grid) {
        for (int boxRow = 0; boxRow < BOARD_SIZE; boxRow += 3) {
            for (int boxCol = 0; boxCol < BOARD_SIZE; boxCol += 3) {
                String violation = validateSingleBox(grid, boxRow, boxCol);
                if (violation != null) {
                    return violation;
                }
            }
        }
        return null;
    }

    private String validateSingleBox(int[][] grid, int boxRow, int boxCol) {
        int[] seen = new int[10];
        for (int row = boxRow; row < boxRow + 3; row++) {
            for (int col = boxCol; col < boxCol + 3; col++) {
                int value = grid[row][col];
                if (value == 0) {
                    continue;
                }
                if (seen[value] > 0) {
                    return NUMBER_PREFIX + value + " already exists in the same 3x3 subgrid.";
                }
                seen[value]++;
            }
        }
        return null;
    }
}
