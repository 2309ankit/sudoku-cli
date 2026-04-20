package com.ankit.sudoku.parser;

import com.ankit.sudoku.model.CellPosition;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommandParser {
    private static final String ROW_GROUP = "([A-I])";
    private static final Pattern PLACE_PATTERN = Pattern.compile("^" + ROW_GROUP + "([1-9])\\s+([1-9])$", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLEAR_PATTERN = Pattern.compile("^" + ROW_GROUP + "([1-9])\\s+clear$", Pattern.CASE_INSENSITIVE);

    private CommandParser() {
    }

    public static Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Command.invalid("Invalid command.");
        }

        String trimmed = input.trim();
        String normalized = trimmed.toLowerCase(Locale.ROOT);

        if ("hint".equals(normalized)) {
            return Command.hint();
        }

        if ("check".equals(normalized)) {
            return Command.check();
        }

        if ("quit".equals(normalized)) {
            return Command.quit();
        }

        Matcher clearMatcher = CLEAR_PATTERN.matcher(trimmed);
        if (clearMatcher.matches()) {
            return Command.clear(toPosition(clearMatcher.group(1), clearMatcher.group(2)));
        }

        Matcher placeMatcher = PLACE_PATTERN.matcher(trimmed);
        if (placeMatcher.matches()) {
            CellPosition position = toPosition(placeMatcher.group(1), placeMatcher.group(2));
            int value = Integer.parseInt(placeMatcher.group(3));
            return Command.place(position, value);
        }

        return Command.invalid("Invalid command.");
    }

    private static CellPosition toPosition(String rowText, String colText) {
        int row = Character.toUpperCase(rowText.charAt(0)) - 'A';
        int col = Integer.parseInt(colText) - 1;
        return new CellPosition(row, col);
    }
}
