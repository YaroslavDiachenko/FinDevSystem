package findev.service;

import findev.errorshandling.customexceptions.NotFreeEmployeeException;
import findev.model.Employee;
import findev.model.Event;
import findev.model.EventType;
import findev.model.User;
import findev.repository.IRepositoryEvent;
import findev.service.interfaces.IEmployeeService;
import findev.service.interfaces.IEventService;
import findev.service.interfaces.IEventTypeService;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EventService implements IEventService {
    @Autowired private IRepositoryEvent repositoryEvent;
    @Autowired private IEmployeeService employeeService;
    @Autowired private IEventTypeService eventTypeService;
    @Autowired private IUserService userService;

    @Override
    public boolean isExists(Long id) {
        return repositoryEvent.exists(id);
    }
    @Override
    public Event getById(Long id) {
        return repositoryEvent.findOne(id);
    }
    @Override
    public Event save(Event event) {
        return repositoryEvent.save(event);
    }
    @Override
    public void delete(Long id) {
        repositoryEvent.delete(id);
    }
    @Override
    public List<Event> getAll() {
        return repositoryEvent.findAll();
    }


    /**
     * Create new event with the employees specified in the list involved.
     * @param event - employee entity
     * @throws NotFreeEmployeeException - if not all listed in event employees have 'Free' status
     */
    @Transactional
    @Override
    public void createEvent(Event event, List<Long> employeesIds) throws NotFreeEmployeeException{
        List<Long> freeEmployeesIds = employeeService.getFreeEmployeesIds();
        if (!freeEmployeesIds.containsAll(employeesIds))
            throw new NotFreeEmployeeException("Not all listed employees are free. Free employees " + freeEmployeesIds);
        Event savedEvent = save(event);
        if (savedEvent != null) {
            for (Employee employee : event.getEmployees()) {
                employeeService.changeStatusesFreeToBusy(employee.getId());
            }
        }
        fetchClassProperties(event);
    }

    /**
     * Is used to fetch event type name and employees last names to show full persisted object after saving, since it temporarily cached.
     * @param event - event entity
     */
    @Override
    public void fetchClassProperties(Event event) {
        EventType eventType = eventTypeService.getById(event.getEventType().getId());
        event.setEventType(eventType);
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : event.getEmployees()) {
            employees.add(employeeService.getById(employee.getId()));
        }
        event.setEmployees(employees);
    }

    /** Get income of the specified employee for the specified period of time.
     * @param firstName - employee's first name
     * @param lastName - employee's last name
     * @param dateFrom - start date of the income cover period
     * @param dateTo - end date of the income cover period
     * @return - employee's income amount ($)
     */
    @Override
    public BigDecimal getIncomePerEmployeePerPeriod(String firstName, String lastName, Date dateFrom, Date dateTo) {
        return repositoryEvent.getIncomePerEmployeePerPeriod(firstName, lastName, dateFrom, dateTo);
    }

    /** Get list of the events the employee participated in.
     * @param username - employee's username
     * @param dateFrom - start date of the events cover period
     * @param dateTo - end date of the events cover period
     * @return - list of the events the employee participated in
     */
    @Override
    public List<Event> getEventsPerEmployeePerPeriod(String username, Date dateFrom, Date dateTo) {
        User currentUser = userService.getByUsername(username);
        Long employeeId = currentUser.getEmployee().getId();
        return repositoryEvent.getEventsPerEmployeePerPeriod(employeeId, dateFrom, dateTo);
    }
}
