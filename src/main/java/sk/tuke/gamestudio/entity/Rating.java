package sk.tuke.gamestudio.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.NamedQuery;

import java.util.Date;

@Entity
@Table(name = "rating",
        uniqueConstraints = { @UniqueConstraint(columnNames = { "player", "game" } ) } )
@NamedQuery( name = "Rating.resetRatings",
        query = "DELETE FROM Rating ")
public class Rating {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "game")
    private String game;
    @Column(name = "player")
    private String player;

    private int rating;

    private Date ratedOn;
    public Rating() {

    }

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
