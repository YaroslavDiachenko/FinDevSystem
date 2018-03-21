package findev.service.interfaces;

import findev.model.Event;

import java.math.BigDecimal;
import java.util.Date;

public interface IEventService extends ICrudService<Event> {

    public BigDecimal getEmployeeIncome(String firstName, String lastName, Date dateFrom, Date dateTo);
}
