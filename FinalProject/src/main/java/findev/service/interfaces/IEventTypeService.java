package findev.service.interfaces;

import findev.model.EventType;

public interface IEventTypeService extends ICrudService<EventType> {
    public EventType getByName(String name);
}
