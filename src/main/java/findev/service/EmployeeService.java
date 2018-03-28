package findev.service;

import findev.model.Employee;
import findev.model.User;
import findev.repository.IRepositoryEmployee;
import findev.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {
    @Autowired private IRepositoryEmployee repositoryEmployee;
    @Autowired private IUserService userService;
    @Autowired private IPositionService positionService;
    @Autowired private IDepartmentService departmentService;
    @Autowired private IStatusService statusService;
    @Autowired private EmailService emailService;

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
     * Create new employee:
     * 1. Generate user ({@link UserService#generateUser(Employee)}) with random password ({@link UserService#randomPassword()}),
     * default user role and username as lowercase combination of the employee's first and last names.
     * 2. Save generated user to database ({@link UserService#save(User)}).
     * 3. Send notification to email of the created new employee ({@link EmailService#sendWelcomeEmail(Employee, String)})
     * 4. Fetch data for class properties ({@link #fetchClassProperties(Employee)})
     * @param e - employee entity
     */
    @Transactional
    @Override
    public void registerEmployee(Employee e) {
        User u = userService.generateUser(e);
        String password = u.getPassword();
        User savedUser = userService.save(u);
        u.setId(savedUser.getId());
        e.setUser(u);
        Employee savedEmployee = save(e);
        if (savedEmployee != null) {
            emailService.sendWelcomeEmail(e, password);
        }
        fetchClassProperties(e);
    }

    /**
     * Update existing employee. Properties values of the existing object are assigned to updated one if missing in passed DTO object.
     * @param e2 - updated employee entity
     * @throws IllegalAccessException - if no access to object fields
     */
    @Override
    public void updateEmployee(Employee e2) throws IllegalAccessException {
        boolean isChangedFirstOrLastName = false;
        Employee e1 = getById(e2.getId());
        Field[] declaredFields = Employee.class.getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Object v2 = f.get(e2);
            if (v2 == null) {
                Object v1 = f.get(e1);
                f.set(e2,v1);
            }else if (f.getName().equals("firstName") || f.getName().equals("lastName"))
                isChangedFirstOrLastName = true;
        }
        e2.getUser().setId(e1.getUser().getId());
        save(e2);
        if (isChangedFirstOrLastName) {
            User u1 = userService.getById(e1.getUser().getId());
            String newUsername = userService.generateUser(e2).getUsername();
            u1.setUsername(newUsername);
            userService.save(u1);
        }
        fetchClassProperties(e2);
    }

    /**
     * Is used to fetch data from related tables since after saving employee is temporarily cached before being persisted.
     * @param e - employee entity
     */
    @Override
    public void fetchClassProperties(Employee e) {
        if (e.getStatus().getName() == null)
            e.setStatus(statusService.getById(e.getStatus().getId()));
        if (e.getPosition().getName() == null)
            e.setPosition(positionService.getById(e.getPosition().getId()));
        if (e.getDepartment().getName() == null)
            e.setDepartment(departmentService.getById(e.getDepartment().getId()));
        if (e.getUser().getUsername() == null)
            e.setUser(userService.getById(e.getUser().getId()));
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