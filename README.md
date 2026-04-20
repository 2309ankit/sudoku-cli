# Sudoku CLI

A Java 17 + Maven command-line Sudoku game.

## Environment
- Windows, macOS, or Linux
- Java 17+
- Maven 3.9+

## Design
- `SudokuApp` owns the CLI loop and replay flow.
- `SudokuGame` contains game rules and state transitions.
- `SudokuGenerator` builds a solved board, removes cells, and starts each game with 30 pre-filled values.
- `SudokuValidator` checks rows, columns, and 3x3 subgrids and returns the first violation found.
- `ConsoleRenderer` formats the board for terminal output.

## Assumptions
- A `hint` fills one editable cell with the correct value immediately.
- `check` validates the current board and reports the first detected violation.
- Number entry is validated for editable cells and the `1-9` range.
- Moves are not rejected for Sudoku-rule conflicts at entry time; those conflicts are surfaced by `check`.
- Completing the final correct move ends the game and offers a replay prompt.

## Run tests
```bash
mvn test
```

JaCoCo coverage report:
```bash
target/site/jacoco/index.html
```

## Run application
```bash
mvn compile exec:java -Dexec.mainClass=com.ankit.sudoku.SudokuApp
```

## Automation
- GitHub Actions runs `mvn test` on every push to `main` and on pull requests.
- JaCoCo coverage reports are generated locally under `target/site/jacoco/`.
- Real-process smoke test scripts are available at `scripts/smoke-test.cmd` and `scripts/smoke-test.ps1`.

Run the smoke tests:
```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\smoke-test.ps1
```

Or on Windows Command Prompt:
```bat
scripts\smoke-test.cmd
```

## Commands
- `A3 4` places a number in a cell.
- `C5 clear` clears a non-fixed cell.
- `hint` fills one correct value automatically.
- `check` validates the current grid.
- `quit` exits the game.

## Gameplay notes
- The board starts with 30 pre-filled cells.
- Empty cells are displayed as `_`.
- After every non-quit command, the app reprints `Current grid:`.
- After a successful completion, the app prints `Press any key to play again...` and starts a new puzzle.

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

## Test coverage
- Unit tests cover command parsing, rule validation, and core game actions.
- CLI integration tests cover hint flow, invalid fixed-cell updates, row/column/subgrid violations, and replay after a successful completion.
