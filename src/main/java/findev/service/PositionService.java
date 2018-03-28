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
        return repositoryPosition.exists(id);
    }
    @Override
    public Position getById(Long id) {
        return repositoryPosition.findOne(id);
    }
    @Override
    public Position save(Position p) {
        return repositoryPosition.save(p);
    }
    @Override
    public void delete(Long id) {
        repositoryPosition.delete(id);
    }
    @Override
    public List<Position> getAll() {
        return repositoryPosition.findAll();
    }
    @Override
    public Position getByName(String name) {
        return repositoryPosition.findByName(name);
    }
}
