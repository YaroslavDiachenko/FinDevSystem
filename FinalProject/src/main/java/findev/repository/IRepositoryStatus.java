package findev.repository;

import findev.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryStatus extends JpaRepository<Status, Long> {
}
