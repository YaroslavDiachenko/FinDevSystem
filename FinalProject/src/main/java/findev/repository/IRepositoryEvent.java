package findev.repository;

import findev.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface IRepositoryEvent extends JpaRepository<Event, Long> {

    @Query(value = "" +
            "SELECT sum(coeft * hours) * hourrate\n" +
            "FROM events\n" +
            "    JOIN events_employees\n" +
            "        ON events_employees.event_id = events.id\n" +
            "    JOIN employees\n" +
            "        ON employees.id = events_employees.employee_id\n" +
            "\tJOIN eventtypes\n" +
            "        ON events.eventtype_id = eventtypes.id\n" +
            "\tWHERE firstname = ?1 AND lastname = ?2 AND date BETWEEN ?3 AND ?4", nativeQuery = true)
    public BigDecimal getIncomePerEmployeePerPeriod(String firstName, String lastName, Date dateFrom, Date dateTo);

    @Query(value = "SELECT events.* FROM events\n" +
            "   JOIN events_employees\n" +
            "       ON events.id = events_employees.event_id\n" +
            "WHERE employee_id = ?1 AND date BETWEEN ?2 AND ?3", nativeQuery = true)
    public List<Event> getEventsPerEmployeePerPeriod(Long employeeId, Date dateFrom, Date dateTo);
}
