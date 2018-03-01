package findev.service.security;

import findev.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
