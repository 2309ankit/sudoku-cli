package com.ankit.sudoku.render;

import com.ankit.sudoku.model.SudokuBoard;

public final class ConsoleRenderer {

    private ConsoleRenderer() {
    }

    public static void printBoard(SudokuBoard board) {
        System.out.println("    1 2 3 4 5 6 7 8 9");
        for (int row = 0; row < 9; row++) {
            char rowLabel = (char) ('A' + row);
            StringBuilder line = new StringBuilder();
            line.append("  ").append(rowLabel).append(" ");
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                line.append(value == 0 ? "_" : value);
                if (col < 8) {
                    line.append(" ");
                }
            }
            System.out.println(line);
        }
    }
}
