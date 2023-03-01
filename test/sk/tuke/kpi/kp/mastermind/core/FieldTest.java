package sk.tuke.kpi.kp.mastermind.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    private Field field;

    public FieldTest() {
        this.field = new Field();
    }

    @Test
    void checkIsGameLost() {
        var colors = Color.values();
        Color color = Color.NONE;

        String guess = "RGBY";

        for(int i = 0; i < 10; i++) {
            field.makeGuess(guess);
        }

        assertSame(field.getFieldState(), FieldState.LOST, "10 guesses was executed. Game is over.");
    }

    @Test
    void checkMakeGuess() {
        String guess = "RGBY";

        List<Color> guessColors = new ArrayList<>(4);
        guessColors.add(Color.RED); guessColors.add(Color.GREEN);
        guessColors.add(Color.BLUE); guessColors.add(Color.YELLOW);

        assertEquals(guessColors, field.makeGuess(guess), "The guess was converted correctly");
    }
}


