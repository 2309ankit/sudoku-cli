package com.ankit.sudoku.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {

    @Test
    void shouldParsePlaceCommand() {
        Command command = CommandParser.parse("B3 7");

        assertEquals(Command.Type.PLACE, command.type());
        assertEquals(1, command.position().row());
        assertEquals(2, command.position().col());
        assertEquals(7, command.value());
    }

    @Test
    void shouldParseClearCommand() {
        Command command = CommandParser.parse("C5 clear");

        assertEquals(Command.Type.CLEAR, command.type());
        assertEquals(2, command.position().row());
        assertEquals(4, command.position().col());
    }

    @Test
    void shouldParseHintCommand() {
        Command command = CommandParser.parse("hint");
        assertEquals(Command.Type.HINT, command.type());
    }

    @Test
    void shouldRejectInvalidCommand() {
        Command command = CommandParser.parse("Z9 4");
        assertEquals(Command.Type.INVALID, command.type());
    }
}
