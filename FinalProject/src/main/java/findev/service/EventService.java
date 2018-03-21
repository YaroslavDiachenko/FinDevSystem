package findev.service;

import findev.model.Event;
import findev.repository.IRepositoryEvent;
import findev.service.interfaces.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class EventService implements IEventService {
    @Autowired
    private IRepositoryEvent repositoryEvent;

    @Override
    public BigDecimal getEmployeeIncome(String firstName, String lastName, Date dateFrom, Date dateTo) {
        return repositoryEvent.getEmployeeIncome(firstName, lastName, dateFrom, dateTo);
    }

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
}
