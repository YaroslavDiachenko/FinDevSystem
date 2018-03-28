package findev.service.scheduled;

import findev.model.Employee;
import findev.service.EmailService;
import findev.service.EmployeeService;
import findev.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GeneralService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EventService eventService;

    @Scheduled(cron = "0 0 12 1 * *")
    public void mailEmployeesSalaries() {
        SimpleDateFormat format = new SimpleDateFormat("MMMM YYYY");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String previousMonth = format.format(calendar.getTime());
        calendar.set(Calendar.DATE, 1);
        Date firstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDay = calendar.getTime();

        String emailSubject = "Findevsystem payment info";

        List<Employee> employees = employeeService.getAll();
        for (Employee employee : employees) {
            BigDecimal salary = eventService.getIncomePerEmployeePerPeriod(
                    employee.getFirstName(),
                    employee.getLastName(),
                    firstDay, lastDay).setScale(2, RoundingMode.CEILING);
            String emailMessage = "Your salary for " + previousMonth + " is $" + salary;
            emailService.sendMail(employee.getEmail(), emailSubject, emailMessage);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void changeStatusesBusyToFree() {
        employeeService.changeStatusesBusyToFree();
    }
}
