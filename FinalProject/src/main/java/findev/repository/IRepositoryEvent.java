package findev.repository;

import findev.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

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
            "\tWHERE firstname=?1 AND lastname=?2 AND date BETWEEN ?3 AND ?4", nativeQuery = true)
    public BigDecimal getEmployeeIncome(String firstName, String lastName, Date dateFrom, Date dateTo);
}
