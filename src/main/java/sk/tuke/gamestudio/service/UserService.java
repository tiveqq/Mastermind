package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.User;

public interface UserService {
    void register(User user);
    boolean login(String login, String password);

    User findByUsername(String username);
}
