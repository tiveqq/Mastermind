package sk.tuke.kpi.kp.mastermind.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.mastermind.entity.Rating;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceTest {
    private RatingService createService() {
        return new RatingServiceJDBC();
    }

    @Test
    public void testReset() {
        RatingService service = createService();
        service.reset();
        assertEquals(0, service.getRating("mastermind", "Jaro"));
    }

    @Test
    public void firstTestAddRating() {
        RatingService service = createService();
        service.reset();
        service.setRating(new Rating("mastermind", "Jaro", 2, new Date()));

        int rating = service.getRating("mastermind", "Jaro");

        assertEquals(2, rating);

    }

    @Test
    public void testAverageRating() {
        RatingService service = createService();
        service.reset();

        service.setRating(new Rating("mastermind", "Jaro", 2, new Date()));
        service.setRating(new Rating("mastermind", "Mero", 3, new Date()));
        service.setRating(new Rating("mastermind", "Gero", 4, new Date()));

        int avgRating = service.getAverageRating("mastermind");

        assertEquals(3, avgRating);
    }

    @Test
    public void testOnePlayerRatesTwice() {
        RatingService service = createService();
        service.reset();

        service.setRating(new Rating("mastermind", "Jaro", 2, new Date()));
        service.setRating(new Rating("mastermind", "Jaro", 3, new Date()));

        assertEquals(3, service.getRating("mastermind", "Jaro"));
    }
}
