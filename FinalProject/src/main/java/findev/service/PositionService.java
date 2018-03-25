package findev.service;

import findev.model.Position;
import findev.repository.IRepositoryPosition;
import findev.service.interfaces.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService implements IPositionService {
    @Autowired private IRepositoryPosition repositoryPosition;

    @Override
    public boolean isExists(Long id) {
        return false;
    }
    @Override
    public Position getById(Long id) {
        return repositoryPosition.findOne(id);
    }
    @Override
    public Position save(Position position) {
        return null;
    }
    @Override
    public void delete(Long id) {

    }
    @Override
    public List<Position> getAll() {
        return null;
    }
}
