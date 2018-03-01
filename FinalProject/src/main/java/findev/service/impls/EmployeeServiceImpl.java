package findev.service.impls;

import findev.model.Employee;
import findev.repository.EmployeeRepository;
import findev.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }
}
