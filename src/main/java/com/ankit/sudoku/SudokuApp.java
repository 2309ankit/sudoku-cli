package com.ankit.sudoku;

import com.ankit.sudoku.game.SudokuGame;
import com.ankit.sudoku.model.SudokuBoard;
import com.ankit.sudoku.parser.Command;
import com.ankit.sudoku.parser.CommandParser;
import com.ankit.sudoku.render.ConsoleRenderer;
import com.ankit.sudoku.service.SudokuGenerator;

import java.util.Scanner;

public class SudokuApp {

    public static void main(String[] args) {
        SudokuGenerator generator = new SudokuGenerator();
        SudokuBoard board = generator.generatePuzzle(30);
        SudokuGame game = new SudokuGame(board);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Sudoku!");
        System.out.println();
        System.out.println("Here is your puzzle:");
        ConsoleRenderer.printBoard(game.getBoard());

        while (!game.isFinished()) {
            System.out.println();
            System.out.print("Enter command (e.g., A3 4, C5 clear, hint, check, quit): ");
            String input = scanner.nextLine();

            Command command = CommandParser.parse(input);
            String message = game.handle(command);
            System.out.println(message);

            if (!game.isQuitRequested()) {
                System.out.println();
                System.out.println("Current grid:");
                ConsoleRenderer.printBoard(game.getBoard());
            }
        }

        if (game.isCompletedSuccessfully()) {
            System.out.println();
            System.out.println("You have successfully completed the Sudoku puzzle!");
        }
    }
}
