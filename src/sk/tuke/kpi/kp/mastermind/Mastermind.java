package sk.tuke.kpi.kp.mastermind;

import sk.tuke.kpi.kp.mastermind.consoleui.ConsoleUI;
import sk.tuke.kpi.kp.mastermind.core.Field;

import java.util.Scanner;

public class Mastermind {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean playAgain = true;
        while (playAgain) {
            Field field = new Field();
            ConsoleUI consoleUI = new ConsoleUI(field);
            consoleUI.startGame();
            playAgain = isPlayAgain();
        }
    }

    private static boolean isPlayAgain() {
        String answer;

        do {
            System.out.println("Do you wish to play again? Y/N");
            answer = scanner.next();
            try {
                if (answer.toUpperCase().charAt(0) == 'N') {
                    return true;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Please, try again");
                scanner.next();
            }
        } while(answer.toUpperCase().charAt(0) != 'Y');
        return true;
    }
}
