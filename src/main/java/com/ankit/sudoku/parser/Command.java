package com.ankit.sudoku.parser;

import com.ankit.sudoku.model.CellPosition;

public class Command {

    public enum Type {
        PLACE,
        CLEAR,
        HINT,
        CHECK,
        QUIT,
        INVALID
    }

    private final Type type;
    private final CellPosition position;
    private final Integer value;
    private final String errorMessage;

    private Command(Type type, CellPosition position, Integer value, String errorMessage) {
        this.type = type;
        this.position = position;
        this.value = value;
        this.errorMessage = errorMessage;
    }

    public static Command place(CellPosition position, int value) {
        return new Command(Type.PLACE, position, value, null);
    }

    public static Command clear(CellPosition position) {
        return new Command(Type.CLEAR, position, null, null);
    }

    public static Command hint() {
        return new Command(Type.HINT, null, null, null);
    }

    public static Command check() {
        return new Command(Type.CHECK, null, null, null);
    }

    public static Command quit() {
        return new Command(Type.QUIT, null, null, null);
    }

    public static Command invalid(String errorMessage) {
        return new Command(Type.INVALID, null, null, errorMessage);
    }

    public Type type() {
        return type;
    }

    public CellPosition position() {
        return position;
    }

    public Integer value() {
        return value;
    }

    public String errorMessage() {
        return errorMessage;
    }
}
