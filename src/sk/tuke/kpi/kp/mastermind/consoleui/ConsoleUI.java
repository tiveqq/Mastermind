package sk.tuke.kpi.kp.mastermind.consoleui;

import sk.tuke.kpi.kp.mastermind.core.Color;
import sk.tuke.kpi.kp.mastermind.core.Field;
import sk.tuke.kpi.kp.mastermind.core.FieldState;

import java.util.Scanner;

public class ConsoleUI {
    private final Field field;
    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(Field field) {
        this.field = field;
    }

    public void startGame() {
        printField();
            do {
                handleInput();
                printField();
            } while (this.field.getFieldState() == FieldState.IN_PROGRESS);

            if (field.getFieldState() == FieldState.LOST) {
                System.out.println("You lost! The secret combination of colors was: " + field.getSecretCode());
            } else if (field.getFieldState() == FieldState.WON) {
                System.out.println("You won! The secret combination of colors was: " + field.getSecretCode());
            }
    }

    private void showHeader() {
        System.out.println("*" + "-".repeat(26) + "*");
        System.out.println("#" + " ".repeat(8) + "Mastermind" + " ".repeat(8) + "#");
        System.out.println("*" + "-".repeat(26) + "*");
    }

    private void printField() {
        showHeader();
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.println("|   " + (field.getBoardState()[i][0]).getCharColor(field.getBoardState()[i][0]) + "   "
                    + (field.getBoardState()[i][1]).getCharColor(field.getBoardState()[i][1]) + "   "
                    + (field.getBoardState()[i][2]).getCharColor(field.getBoardState()[i][2])
                    + "   " + (field.getBoardState()[i][3]).getCharColor(field.getBoardState()[i][3]) + "   | " + (field.getKeyColors()[i][0]).getCharKeyColor(field.getKeyColors()[i][0])
                    + (field.getKeyColors()[i][1]).getCharKeyColor(field.getKeyColors()[i][1]) + (field.getKeyColors()[i][2]).getCharKeyColor(field.getKeyColors()[i][2])
                    + (field.getKeyColors()[i][3]).getCharKeyColor(field.getKeyColors()[i][3]) + " |");
            if(i == field.getRowCount() - 1) {
                System.out.println("*" + "-".repeat(26) + "*");
            } else {
                System.out.println("*" + "-".repeat(19) + "*" + "-".repeat(6) + "*");
            }
        }
    }

    private String readLine() {
        String guess = scanner.nextLine();
        if(guess.length() < 4) {
            return null;
        }
        guess = guess.substring(0,4).toUpperCase();

        boolean repeat = false;

        for(int i = 0; i < guess.length(); i++) {
            for(int j = i + 1; j < guess.length(); j++) {
                if (guess.charAt(i) == guess.charAt(j)) {
                    repeat = true;
                    break;
                }
            }
            if(repeat) {
                return null;
            }
        }
        return isValid(guess) ? guess : null;
    }

    private boolean isValid(String guess) {
        char[] arrayGuess = guess.toCharArray();
        Color color = Color.NONE;

        for (char c : arrayGuess) {
            color = color.getColor(c);
            if (color == null) {
                return false;
            }
        }

        return true;
    }

    private void handleInput() {
        System.out.println("Enter a combination of 4 colors: (R)ed, (G)reen, (B)lue, (Y)ellow, " +
                "(O)range, (V)iolet, (M)int, (P)ink, (I)ndigo");
        String input = readLine();

        if(input != null) {
            field.makeGuess(input);
//            List<KeyColor> keyColors;
//            try {
//                keyColors = field.calculateKeyColors(colors);
//            } catch (NullPointerException e) {
//                System.out.println("Invalid value of your guess");
//                return;
//            }
//            System.out.println("Your colors: " + colors);
//            System.out.println("Secret: " + field.getSecretCode());
//            System.out.println("Feedback " + keyColors);
        } else {
            System.out.println("Invalid value! Try again!");
        }
    }
}
