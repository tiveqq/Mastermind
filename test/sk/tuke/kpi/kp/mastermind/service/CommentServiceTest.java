package sk.tuke.kpi.kp.mastermind.service;

import org.junit.jupiter.api.Test;
import sk.tuke.kpi.kp.mastermind.entity.Comment;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {
    private CommentService createService() {
        return new CommentServiceJDBC();
    }

    @Test
    public void testReset() {
        CommentService service = createService();
        service.addComment(new Comment("Jaro", "mastermind", "very good", new Date()));
        service.reset();
        assertEquals(0, service.getComments("mastermind").size());
    }

    @Test
    public void firstTestAddComment() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("Jaro", "mastermind", "very good", new Date()));

        List<Comment> scores = service.getComments("mastermind");

        assertEquals(1, scores.size());

        assertEquals("mastermind", scores.get(0).getGame());
        assertEquals("Jaro", scores.get(0).getPlayer());
        assertEquals("very good", scores.get(0).getComment());
        assertEquals(date, scores.get(0).getCommentedOn());
    }

    @Test
    public void secondTestAddComment() {
        CommentService service = createService();
        service.reset();
        Date date = new Date();
        service.addComment(new Comment("Fero", "mastermind", "good", date));
        service.addComment(new Comment("Jaro", "mastermind", "bad", date));
        service.addComment(new Comment("Jozo", "mastermind", "not good enough", date));


        List<Comment> comments = service.getComments("mastermind");

        System.out.println(comments);

        assertEquals(3, comments.size());

        assertEquals("mastermind", comments.get(0).getGame());
        assertEquals("Fero", comments.get(0).getPlayer());
        assertEquals("good", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("mastermind", comments.get(1).getGame());
        assertEquals("Jaro", comments.get(1).getPlayer());
        assertEquals("bad", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("mastermind", comments.get(2).getGame());
        assertEquals("Jozo", comments.get(2).getPlayer());
        assertEquals("not good enough", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }
}
