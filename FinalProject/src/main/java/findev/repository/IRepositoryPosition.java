package findev.repository;

import findev.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryPosition extends JpaRepository<Position, Long> {
    Position findByName(String name);
}
