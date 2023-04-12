package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            long id = (long) entityManager.createQuery("SELECT id FROM Rating WHERE game = :game AND player = :player")
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer()).getSingleResult();
            rating.setId(id);
            entityManager.merge(rating);
        } catch (NoResultException e) {
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try {
            return (int) Math.round((Double) entityManager.createQuery("SELECT avg(rating) AS average_rating FROM Rating r WHERE r.game = :game")
                    .setParameter("game", game).getSingleResult());
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            return (int) entityManager.createQuery("SELECT rating FROM Rating WHERE game = :game AND player = :player")
                    .setParameter("game", game).setParameter("player", player).getSingleResult();
        } catch (NoResultException ignored) {}

        return -1;
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings");
    }
}
