package com.ankit.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellPositionTest {

    @Test
    void shouldFormatDisplayUsingBoardCoordinates() {
        CellPosition position = new CellPosition(1, 2);

        assertEquals("B3", position.display());
    }

    @Test
    void shouldRejectOutOfBoundsCoordinates() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CellPosition(-1, 9));

        assertEquals("Row/Column must be between 0 and 8.", exception.getMessage());
    }
}
