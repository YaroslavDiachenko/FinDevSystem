package findev.repository;

import findev.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryPosition extends JpaRepository<Position, Long> {
}
