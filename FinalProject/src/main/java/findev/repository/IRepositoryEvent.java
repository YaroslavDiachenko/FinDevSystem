package findev.repository;

import findev.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositoryEvent extends JpaRepository<Event, Long> {
}
