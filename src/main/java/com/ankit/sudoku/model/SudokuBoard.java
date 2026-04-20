package com.ankit.sudoku.model;

public class SudokuBoard {
    private final int[][] current;
    private final int[][] solution;
    private final boolean[][] fixed;

    public SudokuBoard(int[][] puzzle, int[][] solution, boolean[][] fixed) {
        this.current = deepCopy(puzzle);
        this.solution = deepCopy(solution);
        this.fixed = copyFixed(fixed);
    }

    public int getValue(int row, int col) {
        return current[row][col];
    }

    public void setValue(int row, int col, int value) {
        current[row][col] = value;
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    public int getSolutionValue(int row, int col) {
        return solution[row][col];
    }

    public boolean isComplete() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (current[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean matchesSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (current[row][col] != solution[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] snapshot() {
        return deepCopy(current);
    }

    private static int[][] deepCopy(int[][] source) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 9);
        }
        return copy;
    }

    private static boolean[][] copyFixed(boolean[][] source) {
        boolean[][] copy = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
