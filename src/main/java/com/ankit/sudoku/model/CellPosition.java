package com.ankit.sudoku.model;

import java.util.Objects;

public class CellPosition {
    private final int row;
    private final int col;

    public CellPosition(int row, int col) {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new IllegalArgumentException("Row/Column must be between 0 and 8.");
        }
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public String display() {
        char rowLabel = (char) ('A' + row);
        return rowLabel + String.valueOf(col + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellPosition that)) return false;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
