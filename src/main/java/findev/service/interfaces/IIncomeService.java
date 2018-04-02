package findev.service.interfaces;

import findev.model.Employee;

import java.io.File;
import java.util.Date;

public interface IIncomeService {
    public void provideEmployeesWithSalaryInfo();

    public void provideEmployeeWithSalaryInfo(String firstName, String lastName, Date month);

    public File generateEmployeePayslip(Employee e, Date dateFrom, Date dateTo);
}
