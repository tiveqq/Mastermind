package sk.tuke.gamestudio.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.server.GameStudioServer;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameStudioServer.class)
public class ScoreServiceTest {
    @Autowired
    private ScoreService service;

    @Test
    public void testReset() {
        service.reset();
        assertEquals(0, service.getTopScores("mastermind").size());
    }

    @Test
    public void firstTestAddScore() {
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
        for (int i = 0; i < 20; i++)
            service.addScore(new Score("mastermind", "Jaro", 200, new Date()));
        assertEquals(10, service.getTopScores("mastermind").size());
    }

    @Test
    public void testPestistance() {
        service.reset();
        service.addScore(new Score("mastermind", "Jaro", 200, new Date()));

        assertEquals(1, service.getTopScores("mastermind").size());
    }

}
