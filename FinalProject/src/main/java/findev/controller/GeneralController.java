package findev.controller;

import findev.repository.IRepositoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping(value = "/general")
public class GeneralController {
    @Autowired
    public IRepositoryEvent repositoryEvent;

    @RequestMapping(value = "/{firstName}/{lastName}/{dateFrom}/{dateTo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BigDecimal> getEmployeeIncome(
            @PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @PathVariable("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @PathVariable("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {

        BigDecimal income = repositoryEvent.getEmployeeIncome(firstName, lastName, dateFrom, dateTo);
        if (income == null || income.compareTo(BigDecimal.ZERO) == 0)
            income = new BigDecimal(0.0);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }
}
