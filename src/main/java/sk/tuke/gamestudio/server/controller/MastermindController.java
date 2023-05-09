package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.mastermind.core.Color;
import sk.tuke.gamestudio.game.mastermind.core.Field;
import sk.tuke.gamestudio.game.mastermind.core.FieldState;
import sk.tuke.gamestudio.game.mastermind.core.KeyColor;
import sk.tuke.gamestudio.server.forms.GuessForm;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/mastermind")
public class MastermindController {


    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @Autowired
    private UserController userController;

    private Field field = new Field();

    public String getHtmlField() {
        var sb = new StringBuilder();
        sb.append("<table class='mastermindfield'>\n");
        for (var row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (var column = 0; column < field.getColCount(); column++) {
                var color = field.getColor(row, column);
                sb.append("<td class='" + getColorClass(color) + "'>\n");
                sb.append("<img style='width: 50px; height: 50px;' src='/images/" + getColorImage(color) + ".svg'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            for (var column = 0; column < field.getColCount(); column++) {
                var keyColor = field.getKeyColor(row, column);
                sb.append("<td class='" + getKeyColorClass(keyColor) + "'>\n");
                sb.append("<img style='width: 50px; height: 50px;' src='/images/" + getKeyColorImage(keyColor) + ".svg'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    public String getHtmlSecret() {
        var sb = new StringBuilder();
        var secret = field.getSecretCode();

        for(int i = 0; i < 4; i++) {
            sb.append("<img src=" + getSecretImg(secret.get(i)));
        }

        return sb.toString();
    }

    public String getSecretImg(Color color) {
        return switch (color) {
            case RED -> "/images/red-circle.svg class = 'secret-color'>";
            case GREEN -> "/images/green-circle.svg class = 'secret-color'>";
            case BLUE -> "/images/blue-circle.svg class = 'secret-color'>";
            case YELLOW -> "/images/yellow-circle.svg class = 'secret-color'>";
            case PINK -> "/images/pink-circle.svg class = 'secret-color'>";
            case CHOCOLATE -> "/images/brown-circle.svg class = 'secret-color'>";
            case ORANGE -> "/images/orange-circle.svg class = 'secret-color'>";
            case SILVER -> "/images/gray-circle.svg class = 'secret-color'>";
            case VIOLET -> "/images/purple-circle.svg class = 'secret-color'>";
            case NONE -> null;
        };
    }


    private String getColorClass(Color color) {
        return switch (color) {
            case RED -> "red";
            case GREEN -> "green";
            case BLUE -> "blue";
            case YELLOW -> "yellow";
            case PINK -> "pink";
            case CHOCOLATE -> "chocolate";
            case ORANGE -> "orange";
            case SILVER -> "silver";
            case VIOLET -> "violet";
            case NONE -> "color_none";
        };
    }

    public String getColorImage(Color color) {
        return switch (color) {
            case RED -> "red-circle";
            case GREEN -> "green-circle";
            case BLUE -> "blue-circle";
            case YELLOW -> "yellow-circle";
            case PINK -> "pink-circle";
            case CHOCOLATE -> "brown-circle";
            case ORANGE -> "orange-circle";
            case SILVER -> "gray-circle";
            case VIOLET -> "purple-circle";
            case NONE -> "none-circle";
        };
    }

    public String getKeyColorImage(KeyColor keyColor) {
        return switch (keyColor) {
            case WHITE -> "white-circle";
            case BLACK -> "black-circle";
            case NONE -> "none-circle";
        };
    }

    public String getKeyColorClass(KeyColor keyColor) {
        return switch (keyColor) {
            case WHITE -> "white";
            case BLACK -> "black";
            case NONE -> "key_none";
        };
    }

    @GetMapping("/new")
    public String newGame(Model model) {
        field = new Field();
        prepareModel(model);
        model.addAttribute("showCommentForm", false);
        return "mastermind";
    }

    private String getGuessString(GuessForm guessForm) {
        return guessForm.getColor1() +
                guessForm.getColor2() +
                guessForm.getColor3() +
                guessForm.getColor4();
    }

    @GetMapping
    public String guessForm(Model model) {
        model.addAttribute("guessForm", new GuessForm());
        return "mastermind";
    }

    @PostMapping("/comments")
    public String leaveComment(@RequestParam String comment, Model model) {
        if (userController.isLogged()) {
            commentService.addComment(new Comment(userController.getLoggedUser().getName(), "mastermind", comment, new Date()));
        }
        prepareModel(model);
        return "mastermind";
    }

    @PostMapping("/rate")
    public String rateGame(@RequestParam(value = "rating", defaultValue = "0") int rating, Model model) {
        if (userController.isLogged()) {
            ratingService.setRating(new Rating("mastermind", userController.getLoggedUser().getName(), rating, new Date()));
        }
        prepareModel(model);
        return "mastermind";
    }

    @PostMapping("/guess")
    public String guessSubmit(@ModelAttribute GuessForm guessForm, Model model) {
        try {
            String guessString = getGuessString(guessForm);
            System.out.println(guessString);
            if(guessString.length() != 4) {
                return "mastermind";
            }
            List<Color> colors = field.makeGuess(guessString);

        } catch (IndexOutOfBoundsException ignored) {}

        if (getFieldState() == FieldState.WON && userController.isLogged()) {
            scoreService.addScore(new Score("mastermind", userController.getLoggedUser().getName(), field.getScore(), new Date()));
        }

        prepareModel(model);
        return "mastermind";
    }

    public FieldState getFieldState() {
        return field.getFieldState();
    }

    public boolean statusGame() {
        return getFieldState() == FieldState.IN_PROGRESS;
    }

    public void prepareModel(Model model) {
        model.addAttribute("field", field);
        model.addAttribute("guessForm", new GuessForm());
        model.addAttribute("scores", scoreService.getTopScores("mastermind"));
        model.addAttribute("comments", commentService.getComments("mastermind"));
        model.addAttribute("avgRating", ratingService.getAverageRating("mastermind"));
    }

}
