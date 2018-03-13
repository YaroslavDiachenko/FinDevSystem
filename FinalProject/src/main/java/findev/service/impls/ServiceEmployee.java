package findev.service.impls;

import findev.controller.EmployeeController;
import findev.model.Employee;
import findev.repository.IRepositoryEmployee;
import findev.service.IServiceEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEmployee implements IServiceEmployee {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private IRepositoryEmployee repositoryEmployee;

    @Override
    public boolean isExists(Long id) {
        return repositoryEmployee.exists(id);
    }

    @Override
    public Employee getById(Long id) {
        return repositoryEmployee.findOne(id);
    }

    @Override
    public void save(Employee employee) {
        logger.info("ENTERED SAVE METHOD IN ServiceEmployee");
        repositoryEmployee.save(employee);
    }

    @Override
    public void delete(Long id) {
        repositoryEmployee.delete(id);
    }

    @Override
    public List<Employee> getAll() {
        return repositoryEmployee.findAll();
    }
}
