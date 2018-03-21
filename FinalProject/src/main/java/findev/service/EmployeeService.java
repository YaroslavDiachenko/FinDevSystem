package findev.service;

import findev.model.*;
import findev.repository.IRepositoryEmployee;
import findev.service.interfaces.IEmployeeService;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired
    private IRepositoryEmployee repositoryEmployee;
    @Autowired
    private IUserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StatusService statusService;

    // crud

    @Override
    public boolean isExists(Long id) {
        return repositoryEmployee.exists(id);
    }

    @Override
    public Employee getById(Long id) {
        return repositoryEmployee.findOne(id);
    }

    @Override
    public Employee save(Employee employee) {
        return repositoryEmployee.save(employee);
    }

    @Override
    public void delete(Long id) {
        repositoryEmployee.delete(id);
    }

    @Override
    public List<Employee> getAll() {
        return repositoryEmployee.findAll();
    }

    // extra

    @Override
    public List<Long> getFreeEmployeesIds() {
        return repositoryEmployee.getFreeEmployeesIds();
    }

    @Transactional
    @Override
    public void registerEmployee(Employee employee) {
        User user = generateUser(employee);
        User savedUser = userService.save(user);
        if (savedUser != null ) savedUser.setUsername(user.getUsername());
        employee.setUser(savedUser);
        Employee savedEmployee = save(employee);
        if (savedUser != null && savedEmployee != null) {
            emailService.sendWelcomeEmail(employee,user);
        }
    }

    @Override
    public User generateUser(Employee employee) {
        User user = new User();
        user.setUsername(employee.getFirstName().concat(employee.getLastName()).toLowerCase());
        user.setPassword(randomPassword());
        Role role = new Role();
        role.setId(3L);
        user.setRole(role);
        return user;
    }
    private String randomPassword() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) sb.append((char)(r.nextInt('z' - 'a') + 'a'));
            else sb.append(r.nextInt(10) + 1);
        }
        return sb.toString();
    }

    @Override
    public void setNestedData(Employee employee) {
        Position position = positionService.getById(employee.getPosition().getId());
        Department department = departmentService.getById(employee.getDepartment().getId());
        Status status = statusService.getById(employee.getStatus().getId());
        employee.setPosition(position);
        employee.setDepartment(department);
        employee.setStatus(status);
    }
}