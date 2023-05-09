package sk.tuke.gamestudio.game.mastermind.consoleui;


import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mastermind.core.Color;
import sk.tuke.gamestudio.game.mastermind.core.Field;
import sk.tuke.gamestudio.game.mastermind.core.FieldState;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    public static final String GAME_NAME = "mastermind";
    private Field field;
    private static final Scanner scanner = new Scanner(System.in);
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    public ConsoleUI() {

    }

    public void startGame() {
        boolean playAgain = true;
        while(playAgain) {
            this.field = new Field();
            clearConsole();
            chooseOptions();
            clearConsole();
            printField();
            do {
                handleInput();
                clearConsole();
                printField();
            } while (field.getFieldState() == FieldState.IN_PROGRESS);

            if (field.getFieldState() == FieldState.LOST) {
                System.out.println("\nYou lost! The secret combination of colors was: " + field.getSecretCode());
            } else if (field.getFieldState() == FieldState.WON) {
                System.out.println("\nYou won! The secret combination of colors was: " + field.getSecretCode());
                saveScore();
            }
            rate();
            comment();
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

    private void chooseOptions() {
        printMenu();
        moveTo();
    }

    private static void printMenu() {
        System.out.print("#############################\n" +
                "#   " + "\u001B[31m" + "         MENU    " + "\u001B[0m" + "       #\n" +
                "#############################\n" +
                "#                           #\n" +
                "#    1) PLAY                #\n" +
                "#    2) SEE RATING          #\n" +
                "#    3) SEE AVERAGE RATING  #\n" +
                "#    4) SEE TOP SCORES      #\n" +
                "#    5) SEE COMMENTS        #\n" +
                "#    6) EXIT                #\n" +
                "#                           #\n" +
                "#############################\n");
    }

    private void moveTo() {
        boolean exitMenu = false;
        int num = 0;
        do {
            System.out.println("\nMove to: ");
            boolean done = false;
            while (!done) {
                try {
                    num = scanner.nextInt();
                    done = true;
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid value! Please try again.");
                    System.out.println("\nMove to: ");
                    scanner.next();
                }
            }

            if (destinationMenu(num)) exitMenu = true;
        } while (!exitMenu);
    }

    private boolean destinationMenu(int num) {
        switch (num) {
            case 1:
                return true;
            case 2:
                clearConsole();
                printMenu();
                System.out.println("\nEnter the name of the player whose rating you want to know :");
                String name = scanner.nextLine();
                boolean notFound = ratingService.getRating(GAME_NAME, name) == -1;
                System.out.println("\n" + name + " rated a game at " + (notFound ? 0 : ratingService.getRating(GAME_NAME, name)));
                break;
            case 3:
                clearConsole();
                printMenu();
                System.out.println("\nAverage rating of Mastermind game: " + ratingService.getAverageRating(GAME_NAME));
                break;
            case 4:
                clearConsole();
                printMenu();
                printTopScores();
                break;
            case 5:
                clearConsole();
                printMenu();
                printComments();
                break;
            case 6:
                clearConsole();
                System.exit(0);
                break;
        }
        return false;
    }

    private static void clearConsole() {
        System.out.print("\033\143");
    }

    private void rate() {
        String answer;
        do {
            System.out.println("Do you want to rate the game? Y/N");
            answer = scanner.next();
            try {
                if (answer.toUpperCase().charAt(0) == 'N') {
                    return;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Please, try again");
                scanner.next();
            }
        } while (answer.toUpperCase().charAt(0) != 'Y');
        System.out.println("Rate the game (1 - 5): ");
        float rating;
        boolean done = false;
        while (!done) {
            try {
                rating = scanner.nextFloat();
                if (rating >= 0 && rating <= 5) {
                    ratingService.setRating(new Rating(GAME_NAME, System.getProperty("user.name"),
                            Math.round(rating), new Date()));
                    done = true;
                } else {
                    System.out.println("Please, enter a value between 0 and 5.");
                }

                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid rating.");
                scanner.next();
            }
        }
    }


    private void comment() {
        String answer;
        do {
            System.out.println("Do you want to add a comment? Y/N");
            answer = scanner.next();
            try {
                if (answer.toUpperCase().charAt(0) == 'N') {
                    return;
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Please, try again");
                scanner.next();
            }
        } while (answer.toUpperCase().charAt(0) != 'Y');
        scanner.nextLine();
        System.out.println("Leave the comment: ");
        String comment = scanner.nextLine();
        if (comment.length() == 0) {
            System.out.println("Incorrect comment.");
            return;
        }

        commentService.addComment(new Comment(System.getProperty("user.name"), GAME_NAME,
                comment, new Date()));
    }

    private void saveScore() {
        scoreService.addScore(new Score(GAME_NAME, System.getProperty("user.name"),
                field.getScore(), new Date()));

    }

    private void printTopScores() {
        System.out.print("\n\n");
        var scores = scoreService.getTopScores(GAME_NAME);
        System.out.println("\u001B[31m" + "*-------------------------------------------------------------*" + "\u001B[0m");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. " + "\u001B[33m" + "%s" + "\u001B[0m" + " : %d\n", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("\u001B[31m" + "*-------------------------------------------------------------*" + "\u001B[0m");
    }

    private void printComments() {
        System.out.print("\n\n");
        var comments = commentService.getComments(GAME_NAME);
        System.out.println("\u001B[31m" + "*-------------------------------------------------------------*" + "\u001B[0m");
        for (int i = 0; i < comments.size(); i++) {
            var comment = comments.get(i);
            System.out.printf("%d. " + "\u001B[33m" + "%s" + "\u001B[0m" + " : %s\n", i + 1, comment.getPlayer(), comment.getComment());
        }
        System.out.println("\u001B[31m" + "*-------------------------------------------------------------*" + "\u001B[0m");
    }

    private void showHeader() {
        System.out.println("*" + "-".repeat(40) + "*");
        System.out.println("#" + " ".repeat(15) + "\u001B[31m" + "MASTERMIND" + "\u001B[0m" + " ".repeat(15) + "#");
        System.out.println("*" + "-".repeat(40) + "*");
    }

    private void printField() {
        showHeader();
        for (int i = 0; i < field.getRowCount(); i++) {
            printCurrentBoardState(i);
            if (i == field.getRowCount() - 1) {
                System.out.println("*" + "-".repeat(40) + "*");
            } else {
                System.out.println("*" + "-".repeat(23) + "*" + "-".repeat(16) + "*");
            }
        }
    }

    private void printCurrentBoardState(int i) {
        System.out.println("|   "
                + (field.getBoardState()[i][0]).getCharColor() + "   "
                + (field.getBoardState()[i][1]).getCharColor() + "   "
                + (field.getBoardState()[i][2]).getCharColor() + "   "
                + (field.getBoardState()[i][3]).getCharColor() + "   | "
                + (field.getKeyColors()[i][0]).getCharKeyColor()
                + (field.getKeyColors()[i][1]).getCharKeyColor()
                + (field.getKeyColors()[i][2]).getCharKeyColor()
                + (field.getKeyColors()[i][3]).getCharKeyColor() + "       |");
    }

    private String readLine() {
        String guess = scanner.nextLine();
        if (guess.length() < 4) {
            return null;
        }
        guess = guess.substring(0, 4).toUpperCase();

        boolean repeat = false;

        for (int i = 0; i < guess.length(); i++) {
            for (int j = i + 1; j < guess.length(); j++) {
                if (guess.charAt(i) == guess.charAt(j)) {
                    repeat = true;
                    break;
                }
            }
            if (repeat) {
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
        System.out.println("\nEnter a combination of 4 colors: (R)ed, (G)reen, (B)lue, (Y)ellow, " +
                "(O)range, (V)iolet, (S)ilver, (H)azel, (P)ink");
        String input = readLine();

        if (input != null) {
            List<Color> colors = field.makeGuess(input);
        } else {
            System.out.println("Invalid value! Try again!");
        }
    }


}
