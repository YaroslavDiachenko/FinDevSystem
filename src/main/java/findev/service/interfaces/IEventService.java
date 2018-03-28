package findev.service.interfaces;

import findev.model.Event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IEventService extends ICrudService<Event> {

    public BigDecimal getIncomePerEmployeePerPeriod(String firstName, String lastName, Date dateFrom, Date dateTo);

    public void createEvent(Event event, List<Long> employeesIds);

    public List<Event> getEventsPerEmployeePerPeriod(String username, Date dateFrom, Date dateTo);

    public void fetchClassProperties(Event event);

}
