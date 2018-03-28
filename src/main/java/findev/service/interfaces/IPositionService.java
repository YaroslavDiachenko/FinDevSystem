package findev.service.interfaces;

import findev.model.Position;

public interface IPositionService extends ICrudService<Position> {
    public Position getByName(String name);
}
