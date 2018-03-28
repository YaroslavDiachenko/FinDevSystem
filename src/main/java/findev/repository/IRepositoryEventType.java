package findev.repository;

import findev.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryEventType extends JpaRepository<EventType, Long> {
    EventType findByName(String name);
}
