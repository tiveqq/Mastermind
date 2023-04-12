package sk.tuke.gamestudio.game.mastermind;


import sk.tuke.gamestudio.game.mastermind.consoleui.ConsoleUI;

import java.util.Scanner;

public class Mastermind {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.startGame();
    }
}
