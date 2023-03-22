package sk.tuke.kpi.kp.mastermind.core;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final int rowCount;
    private final int colCount;
    private int attemptsRemaining;
    private FieldState fieldState = FieldState.IN_PROGRESS;
    private final Color[][] boardState;
    private  List<Color> secretCode;
    private final KeyColor[][] keyColors;
    private long startMillis;

    public Field() {
        this.rowCount = 10;
        this.colCount = 4;
        this.secretCode = new ArrayList<>();
        this.attemptsRemaining = 10;
        this.boardState = new Color[10][4];
        this.keyColors = new KeyColor[10][4];
        generateField();
        generateSecretCode();
    }

    private void generateField() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                boardState[i][j] = Color.NONE;
            }
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                keyColors[i][j] = KeyColor.NONE;
            }
        }

        startMillis = System.currentTimeMillis();
    }

    private void generateSecretCode() {
//        int i = 0;
//        while (i < 4) {
//            int number = (int) (Math.random() * Color.values().length);
//            var colors = Color.values();
//            if (!secretCode.contains(colors[number]) && !colors[number].equals(Color.NONE)) {
//                secretCode.add(i, colors[number]);
//                i++;
//            }
//        }
        secretCode = List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
    }

    public void updateField(List<Color> guess, List<KeyColor> feedback) {
        for (int i = 0; i < colCount; i++) {
            boardState[attemptsRemaining - 1][i] = guess.get(i);
        }
        for (int j = 0; j < feedback.size(); j++) {
            keyColors[attemptsRemaining - 1][j] = feedback.get(j);
        }
    }


    public List<Color> makeGuess(String guess) {
        List<Color> colorGuess = new ArrayList<>(4);
        Color color = Color.NONE;

        for (int i = 0; i < guess.length(); i++) {
            color = color.getColor(guess.charAt(i));
            if ((color == null)) {
                return null;
            }
            colorGuess.add(color);
        }

        updateField(colorGuess, calculateKeyColors(colorGuess));

        this.attemptsRemaining--;

        if (attemptsRemaining <= 0) {
            this.fieldState = FieldState.LOST;
        } else if (colorGuess.equals(secretCode)) {
            this.fieldState = FieldState.WON;
        }

        return colorGuess;

    }


    public List<KeyColor> calculateKeyColors(List<Color> guess) {
        var keyColorList = new ArrayList<KeyColor>(4);
        for (Color color : guess) {
            if (this.secretCode.contains(color)) {
                keyColorList.add(KeyColor.WHITE);
            }
        }
        int i = 0;
        for (Color color : guess) {
            if (guess.indexOf(color) == secretCode.indexOf(color)) {
                keyColorList.set(i, KeyColor.BLACK);
                i++;
            }
        }

        return keyColorList;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public Color[][] getBoardState() {
        return boardState;
    }

    public KeyColor[][] getKeyColors() {
        return keyColors;
    }

    public List<Color> getSecretCode() {
        return secretCode;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < colCount; i++) {
            score += (4 - i) * 10;

        }
        return score * (10 + attemptsRemaining) - (int) (System.currentTimeMillis() - startMillis) / 1000;
    }
}
