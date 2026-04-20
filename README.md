# Sudoku CLI

A Java 17 + Maven command-line Sudoku game.

## Requirements
- Java 17+
- Maven 3.9+

## Run tests
```bash
mvn test
```

## Run application
```bash
mvn compile exec:java -Dexec.mainClass=com.ankit.sudoku.SudokuApp
```

## Commands
- `A3 4` places a number in a cell.
- `C5 clear` clears a non-fixed cell.
- `hint` fills one correct value automatically.
- `check` validates the current grid.
- `quit` exits the game.

## Hint behavior
When you enter `hint`, the game fills one editable cell with the correct value, then prints the updated board before showing the next prompt.

Example:
```text
Hint: Cell A1 = 8

Current grid:
    1 2 3 4 5 6 7 8 9
  A 8 1 _ 3 5 4 _ _ _
  B 5 _ _ _ 6 _ 3 _ 8
  C _ _ _ 7 _ _ 5 1 _
  D 2 _ _ _ 9 _ 1 _ 7
  E 4 _ 9 2 _ _ _ 3 _
  F _ _ _ _ _ 6 _ 5 2
  G 7 _ _ _ _ _ _ _ 3
  H 3 _ _ 6 _ _ _ 9 _
  I 6 _ _ 8 7 _ _ _ _
Enter command (e.g., A3 4, C5 clear, hint, check, quit):
```
