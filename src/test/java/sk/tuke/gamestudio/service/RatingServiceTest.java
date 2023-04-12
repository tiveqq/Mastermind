package sk.tuke.gamestudio.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.server.GameStudioServer;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameStudioServer.class)

public class RatingServiceTest {
    @Autowired
    private RatingService ratingService;
//    @Test
//    public void testReset() {
//        ratingService.reset();
//        assertEquals(0, ratingService.getRating("mastermind", "Jaro"));
//    }

    @Test
    public void firstTestAddRating() {
        ratingService.reset();
        ratingService.setRating(new Rating("mastermind", "Jaro", 2, new Date()));

        int rating = ratingService.getRating("mastermind", "Jaro");

        assertEquals(2, rating);

    }

    @Test
    public void testAverageRating() {
        ratingService.reset();

        ratingService.setRating(new Rating("mastermind", "Jaro", 2, new Date()));
        ratingService.setRating(new Rating("mastermind", "Mero", 3, new Date()));
        ratingService.setRating(new Rating("mastermind", "Gero", 4, new Date()));

        int avgRating = ratingService.getAverageRating("mastermind");

        assertEquals(3, avgRating);
    }

    @Test
    public void testOnePlayerRatesTwice() {
        ratingService.reset();

        ratingService.setRating(new Rating("mastermind", "Jaro", 2, new Date()));
        ratingService.setRating(new Rating("mastermind", "Jaro", 3, new Date()));

        assertEquals(3, ratingService.getRating("mastermind", "Jaro"));
    }
}
