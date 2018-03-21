package findev.repository;

import findev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryUser extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
