package findev.service;

import findev.model.Status;
import findev.repository.IRepositoryStatus;
import findev.service.interfaces.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService implements IStatusService {
    @Autowired private IRepositoryStatus repositoryStatus;

    @Override
    public boolean isExists(Long id) {
        return repositoryStatus.exists(id);
    }
    @Override
    public Status getById(Long id) {
        return repositoryStatus.findOne(id);
    }
    @Override
    public Status save(Status s) {
        return repositoryStatus.save(s);
    }
    @Override
    public void delete(Long id) {
        repositoryStatus.delete(id);
    }
    @Override
    public List<Status> getAll() {
        return repositoryStatus.findAll();
    }
    @Override
    public Status getByName(String name) {
        return repositoryStatus.findByName(name);
    }

}
