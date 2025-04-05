package uni.fmi.repository;

import uni.fmi.model.User;

public interface UserRepository {
    void save(User user);
    User findByUsername(String username);
    User findByEmail(String email);
}
