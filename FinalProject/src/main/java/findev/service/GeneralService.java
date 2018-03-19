package findev.service;

import findev.repository.IRepositoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class GeneralService {
    @Autowired
    private IRepositoryEvent repositoryEvent;
    @Autowired
    public void setRepositoryEvent(IRepositoryEvent repositoryEvent) {
        this.repositoryEvent = repositoryEvent;
    }

    public BigDecimal getEventsForPeriod(String firstName, String lastName, Date dateFrom, Date dateTo) {
        return repositoryEvent.getEmployeeIncome(firstName, lastName, dateFrom, dateTo);
    }
}
