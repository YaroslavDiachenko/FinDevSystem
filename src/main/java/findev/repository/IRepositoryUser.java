package findev.repository;

import findev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryUser extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
