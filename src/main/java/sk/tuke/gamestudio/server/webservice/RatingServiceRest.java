package sk.tuke.gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private final RatingService ratingService;


    public RatingServiceRest(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    //POST -> http://localhost:8080/api/rating
    @PostMapping
    public void setRating(@RequestBody Rating rating) throws RatingException {
        ratingService.setRating(rating);
    }

    //GET -> http://localhost:8080/api/rating/mastermind
    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) throws RatingException {
        return ratingService.getAverageRating(game);
    }

    //GET -> http://localhost:8080/api/rating/mastermind/karunnyi
    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game, @PathVariable String player) throws RatingException {
        return ratingService.getRating(game, player);
    }
}
