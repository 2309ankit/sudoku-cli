package com.ankit.sudoku.game;

import com.ankit.sudoku.model.CellPosition;
import com.ankit.sudoku.model.SudokuBoard;
import com.ankit.sudoku.parser.Command;
import com.ankit.sudoku.service.SudokuValidator;

public class SudokuGame {
    private final SudokuBoard board;
    private final SudokuValidator validator;
    private boolean quitRequested;
    private boolean completedSuccessfully;

    public SudokuGame(SudokuBoard board) {
        this(board, new SudokuValidator());
    }

    public SudokuGame(SudokuBoard board, SudokuValidator validator) {
        this.board = board;
        this.validator = validator;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public boolean isQuitRequested() {
        return quitRequested;
    }

    public boolean isCompletedSuccessfully() {
        return completedSuccessfully;
    }

    public boolean isFinished() {
        return quitRequested || completedSuccessfully;
    }

    public String handle(Command command) {
        return switch (command.type()) {
            case PLACE -> place(command.position(), command.value());
            case CLEAR -> clear(command.position());
            case HINT -> hint();
            case CHECK -> check();
            case QUIT -> quit();
            case INVALID -> command.errorMessage();
        };
    }

    private String place(CellPosition position, int value) {
        if (board.isFixed(position.row(), position.col())) {
            return "Invalid move. " + position.display() + " is pre-filled.";
        }

        if (value < 1 || value > 9) {
            return "Invalid move. Number must be between 1 and 9.";
        }

        board.setValue(position.row(), position.col(), value);
        updateCompletionState();
        return "Move accepted.";
    }

    private String clear(CellPosition position) {
        if (board.isFixed(position.row(), position.col())) {
            return "Invalid move. " + position.display() + " is pre-filled.";
        }

        board.setValue(position.row(), position.col(), 0);
        return "Cell cleared.";
    }

    private String hint() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.isFixed(row, col)) {
                    continue;
                }

                int currentValue = board.getValue(row, col);
                int solutionValue = board.getSolutionValue(row, col);

                if (currentValue != solutionValue) {
                    board.setValue(row, col, solutionValue);
                    updateCompletionState();

                    char rowLabel = (char) ('A' + row);
                    return "Hint: Cell " + rowLabel + (col + 1) + " = " + solutionValue;
                }
            }
        }
        return "No hints available.";
    }

    private String check() {
        return validator.validate(board.snapshot());
    }

    private String quit() {
        quitRequested = true;
        return "Goodbye!";
    }

    private void updateCompletionState() {
        completedSuccessfully = board.isComplete() && board.matchesSolution();
    }
}
