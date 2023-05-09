package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.User;

@Transactional
public class UserServiceJPA implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void register(User user) {
        entityManager.persist(user);
    }

    @Override
    public boolean login(String name, String password) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE name = :name AND password = :password", User.class)
                .setParameter("name", name).setParameter("password", password);
        try {
            query.getSingleResult();
            User user = query.getSingleResult();
            System.out.println("User found: " + user);
            return true;
        } catch (NoResultException ex) {
            System.out.println("User not found for name=" + name + " and password=" + password);

            return false;
        }
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
