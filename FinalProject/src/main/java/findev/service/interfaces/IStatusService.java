package findev.service.interfaces;

import findev.model.Status;

public interface IStatusService extends ICrudService<Status> {
    public Status getByName(String name);
}
