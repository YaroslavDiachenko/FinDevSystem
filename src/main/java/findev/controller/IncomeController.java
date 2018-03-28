package findev.controller;

import findev.model.User;
import findev.service.interfaces.IEventService;
import findev.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping(value = "/income")
public class IncomeController {
    @Autowired private IEventService eventService;
    @Autowired private IUserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODER')")
    @GetMapping("")
    public ResponseEntity<BigDecimal> getIncomePerEmployeePerPeriod(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam("dateTo")   @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
        BigDecimal income = eventService.getIncomePerEmployeePerPeriod(firstName, lastName, dateFrom, dateTo);
        if (income == null || income.compareTo(BigDecimal.ZERO) == 0)
            income = new BigDecimal(0.0);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/currentuser")
    public ResponseEntity<BigDecimal> getIncomeCurrentUserPerPeriod(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @RequestParam("dateTo")   @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
            Principal principal) {
        String currentUsername = principal.getName();
        User currentUser = userService.getByUsername(currentUsername);
        BigDecimal income = eventService.getIncomePerEmployeePerPeriod(
                currentUser.getEmployee().getFirstName(),
                currentUser.getEmployee().getLastName(),
                dateFrom, dateTo);
        if (income == null || income.compareTo(BigDecimal.ZERO) == 0)
            income = new BigDecimal(0.0);
        return new ResponseEntity<>(income, HttpStatus.OK);
    }
}
