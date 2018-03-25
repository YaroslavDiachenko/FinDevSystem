package findev.service;

import findev.model.*;
import findev.repository.IRepositoryEmployee;
import findev.service.interfaces.IEmployeeService;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired private IRepositoryEmployee repositoryEmployee;
    @Autowired private IUserService userService;
    @Autowired private EmailService emailService;
    @Autowired private PositionService positionService;
    @Autowired private DepartmentService departmentService;
    @Autowired private StatusService statusService;

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


    /**
    * Get all employees having status 'Free', means ready to be involved in the event being creating at the moment.
     * Is called to check if listed in event employees are available.
     * @return - list of employees' ids
     */
    @Override
    public List<Long> getFreeEmployeesIds() {
        List<Long> listLong = new ArrayList<>();
        List<BigInteger> listBgInt = repositoryEmployee.getFreeEmployeesIds();
        for (BigInteger i : listBgInt) {
            listLong.add(i.longValue());
        }
        return listLong;
    }

    /**
     * Add new employee:
     * 1. Generate user ({@link #generateUser(Employee)}) with random password ({@link #randomPassword()}),
     * default user role and username (lowercase combination of first and last names of the employee).
     * 2. Save generated user to database ({@link #save(Employee)}).
     * 3. Send notification email to the added employee ({@link EmailService#sendWelcomeEmail(Employee, User)})
     * 4. Fetch data for class properties ({@link #fetchClassProperties(Employee)})
     * @param employee - employee entity
     */
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
        fetchClassProperties(employee);
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

    /**
     * Is used to fetch position, department and status names to show full persisted object after saving, since it temporarily cached.
     * @param employee - employee entity
     */
    @Override
    public void fetchClassProperties(Employee employee) {
        Position position = positionService.getById(employee.getPosition().getId());
        Department department = departmentService.getById(employee.getDepartment().getId());
        Status status = statusService.getById(employee.getStatus().getId());
        employee.setPosition(position);
        employee.setDepartment(department);
        employee.setStatus(status);
    }

    /** Change status to 'Busy' for the employee. Is applied for all employees been added to event.
     * @param employeeId - employee's id
     * */
    @Override
    public void changeStatusesFreeToBusy(Long employeeId) {
        repositoryEmployee.changeStatusesFreeToBusy(employeeId);
    }

    /** Is used by scheduler to set back each new day 'Busy' statuses to 'Free'. */
    @Override
    public void changeStatusesBusyToFree() {
        repositoryEmployee.changeStatusesBusyToFree();
    }


}