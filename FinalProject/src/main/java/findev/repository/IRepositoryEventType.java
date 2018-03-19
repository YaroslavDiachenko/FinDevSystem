package findev.repository;

import findev.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryEventType extends JpaRepository<EventType, Long> {
}
