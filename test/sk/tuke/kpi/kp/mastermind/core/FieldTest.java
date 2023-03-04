package sk.tuke.kpi.kp.mastermind.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FieldTest {
    private Field field;

    public FieldTest() {
        this.field = new Field();
    }

    @Test
    void checkIsGameLost() {
        String guess;
        if(!field.getSecretCode().equals(List.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW))) {
            guess = "RGBY";
        } else {
           guess = "RGBI";
        }

        for(int i = 0; i < 10; i++) {
            field.makeGuess(guess);
        }

        assertSame(field.getFieldState(), FieldState.LOST, "10 guesses was executed. Game is over.");
    }

    @Test
    void checkIsGameWon() {
        List<Color> secret = field.getSecretCode();

        String guess = "" + secret.get(0).getCharColor(secret.get(0)) + secret.get(1).getCharColor(secret.get(1)) +
                secret.get(2).getCharColor(secret.get(2)) + secret.get(3).getCharColor(secret.get(3));

        System.out.println(guess);

        List<Color> converted = field.makeGuess(guess);

        assertSame(field.getFieldState(), FieldState.WON);
    }

    @Test
    void checkConvertingMakeGuess() {
        String guess = "RGBY";

        List<Color> guessColors = new ArrayList<>(4);
        guessColors.add(Color.RED); guessColors.add(Color.GREEN);
        guessColors.add(Color.BLUE); guessColors.add(Color.YELLOW);

        assertEquals(guessColors, field.makeGuess(guess), "The guess was converted correctly");
    }

    @Test
    void checkCalculateFeedback() {
        List<Color> secret = field.getSecretCode();

        List<KeyColor> feedback = field.calculateKeyColors(secret);

        assertEquals(List.of(KeyColor.BLACK, KeyColor.BLACK, KeyColor.BLACK, KeyColor.BLACK), feedback);
    }

}


