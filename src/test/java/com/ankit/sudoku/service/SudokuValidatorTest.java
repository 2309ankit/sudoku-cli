package com.ankit.sudoku.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuValidatorTest {

    private final SudokuValidator validator = new SudokuValidator();

    @Test
    void shouldDetectRowViolation() {
        int[][] grid = emptyGrid();
        grid[0][0] = 3;
        grid[0][4] = 3;

        assertEquals("Number 3 already exists in Row A.", validator.validate(grid));
    }

    @Test
    void shouldDetectColumnViolation() {
        int[][] grid = emptyGrid();
        grid[0][0] = 5;
        grid[2][0] = 5;

        assertEquals("Number 5 already exists in Column 1.", validator.validate(grid));
    }

    @Test
    void shouldDetectSubgridViolation() {
        int[][] grid = emptyGrid();
        grid[0][0] = 8;
        grid[1][1] = 8;

        assertEquals("Number 8 already exists in the same 3x3 subgrid.", validator.validate(grid));
    }

    @Test
    void shouldReturnNoViolationWhenValid() {
        int[][] grid = emptyGrid();
        grid[0][0] = 5;
        grid[0][1] = 3;
        grid[1][0] = 6;

        assertEquals("No rule violations detected.", validator.validate(grid));
    }

    private int[][] emptyGrid() {
        return new int[9][9];
    }
}
