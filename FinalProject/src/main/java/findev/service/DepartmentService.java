package findev.service;

import findev.model.Department;
import findev.repository.IRepositoryDepartment;
import findev.service.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IRepositoryDepartment repositoryDepartment;

    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public Department getById(Long id) {
        return repositoryDepartment.findOne(id);
    }

    @Override
    public Department save(Department department) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Department> getAll() {
        return null;
    }
}
