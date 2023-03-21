package sk.tuke.kpi.kp.mastermind.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.mastermind.entity.Score;

import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest {
    private ScoreService createService() {
        return new ScoreServiceJDBC();
    }

    @Test
    public void testReset() {
        ScoreService service = createService();
        service.reset();
        assertEquals(0, service.getTopScores("mastermind").size());
    }

    @Test
    public void firstTestAddScore() {
        ScoreService service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("mastermind", "Jaro", 200, date));

        List<Score> scores = service.getTopScores("mastermind");

        assertEquals(1, scores.size());

        assertEquals("mastermind", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals(200, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void secondTestAddScore() {
        ScoreService service = createService();
        service.reset();
        Date date = new Date();
        service.addScore(new Score("mastermind", "Jaro", 200, date));
        service.addScore(new Score("mastermind", "Fero", 400, date));
        service.addScore(new Score("mastermind", "Jozo", 100, date));

        List<Score> scores = service.getTopScores("mastermind");

        assertEquals(3, scores.size());

        assertEquals("mastermind", scores.get(0).getGame());
        assertEquals("Fero", scores.get(0).getPlayer());
        assertEquals(400, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("mastermind", scores.get(1).getGame());
        assertEquals("Jaro", scores.get(1).getPlayer());
        assertEquals(200, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("mastermind", scores.get(2).getGame());
        assertEquals("Jozo", scores.get(2).getPlayer());
        assertEquals(100, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }

    @Test
    public void thirdTestAddScore() {
        ScoreService service = createService();
        for (int i = 0; i < 20; i++)
            service.addScore(new Score("mastermind", "Jaro", 200, new Date()));
        assertEquals(10, service.getTopScores("mastermind").size());
    }

    @Test
    public void testPestistance() {
        ScoreService service = createService();
        service.reset();
        service.addScore(new Score("mastermind", "Jaro", 200, new Date()));

        service = createService();
        assertEquals(1, service.getTopScores("mastermind").size());
    }

}
