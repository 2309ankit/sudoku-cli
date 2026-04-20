package com.ankit.sudoku;

import com.ankit.sudoku.game.SudokuGame;
import com.ankit.sudoku.parser.Command;
import com.ankit.sudoku.parser.CommandParser;
import com.ankit.sudoku.render.ConsoleRenderer;
import com.ankit.sudoku.service.SudokuGenerator;

import java.io.PrintStream;
import java.util.Scanner;

public class SudokuApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            run(scanner, System.out, new SudokuGenerator());
        }
    }

    static void run(Scanner scanner, PrintStream out, SudokuGenerator generator) {
        out.println("Welcome to Sudoku!");
        out.println();

        boolean playAgain = true;
        while (playAgain) {
            SudokuGame game = new SudokuGame(generator.generatePuzzle(30));

            out.println("Here is your puzzle:");
            ConsoleRenderer.printBoard(game.getBoard(), out);

            while (!game.isFinished()) {
                out.println();
                out.print("Enter command (e.g., A3 4, C5 clear, hint, check, quit): ");
                if (!scanner.hasNextLine()) {
                    return;
                }

                String input = scanner.nextLine();
                Command command = CommandParser.parse(input);
                String message = game.handle(command);
                out.println(message);

                if (!game.isQuitRequested()) {
                    out.println();
                    out.println("Current grid:");
                    ConsoleRenderer.printBoard(game.getBoard(), out);
                }
            }

            if (game.isCompletedSuccessfully()) {
                out.println();
                out.println("You have successfully completed the Sudoku puzzle!");
                out.print("Press any key to play again...");
                if (!scanner.hasNextLine()) {
                    return;
                }
                scanner.nextLine();
                out.println();
            } else {
                playAgain = false;
            }
        }
    }
}
