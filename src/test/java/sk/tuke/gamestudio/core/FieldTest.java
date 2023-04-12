package sk.tuke.gamestudio.core;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.mastermind.core.Color;
import sk.tuke.gamestudio.game.mastermind.core.Field;
import sk.tuke.gamestudio.game.mastermind.core.FieldState;
import sk.tuke.gamestudio.game.mastermind.core.KeyColor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FieldTest {
    private final Field field;

    public FieldTest() {
        this.field = new Field();
    }

    @Test
    void checkIsGameLost() {
        String guess;
        if (!field.getSecretCode().equals(List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW))) {
            guess = "RGBY";
        } else {
            guess = "RGBS";
        }

        for (int i = 0; i < 10; i++) {
            List<Color> converted = field.makeGuess(guess);
        }

        assertSame(field.getFieldState(), FieldState.LOST, "10 guesses was executed. Game is over.");
    }

    @Test
    void checkIsGameWon() {
        List<Color> secret = field.getSecretCode();

        String guess = "" + secret.get(0).getCharColorConverting() + secret.get(1).getCharColorConverting() +
                secret.get(2).getCharColorConverting() + secret.get(3).getCharColorConverting();

        List<Color> converted = field.makeGuess(guess);

        assertSame(FieldState.WON, field.getFieldState());
    }

    @Test
    void checkConvertingMakeGuess() {
        String guess = "RGBY";

        List<Color> guessColors = new ArrayList<>(4);
        guessColors.add(Color.RED);
        guessColors.add(Color.GREEN);
        guessColors.add(Color.BLUE);
        guessColors.add(Color.YELLOW);

        assertEquals(guessColors, field.makeGuess(guess), "The guess was converted correctly");
    }

    @Test
    void checkCalculateFeedback() {
        List<Color> secret = field.getSecretCode();

        List<KeyColor> feedback = field.calculateKeyColors(secret);

        assertEquals(List.of(KeyColor.BLACK, KeyColor.BLACK, KeyColor.BLACK, KeyColor.BLACK), feedback);
    }

}


