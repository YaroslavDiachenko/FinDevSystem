package findev.service;

import findev.model.EventType;
import findev.repository.IRepositoryEventType;
import findev.service.interfaces.IEventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventTypeService implements IEventTypeService {
    @Autowired private IRepositoryEventType repositoryEventType;

    @Override
    public boolean isExists(Long id) {
        return repositoryEventType.exists(id);
    }

    @Override
    public EventType getById(Long id) {
        return repositoryEventType.findOne(id);
    }

    @Override
    public EventType save(EventType eventType) {
        return repositoryEventType.save(eventType);
    }

    @Override
    public void delete(Long id) {
        repositoryEventType.delete(id);
    }

    @Override
    public List<EventType> getAll() {
        return repositoryEventType.findAll();
    }
}
