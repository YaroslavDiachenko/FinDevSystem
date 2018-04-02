package findev.service;

import findev.model.Employee;
import findev.model.Event;
import findev.service.interfaces.IIncomeService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class IncomeService implements IIncomeService {
    @Autowired private EmailService emailService;
    @Autowired private EmployeeService employeeService;
    @Autowired private EventService eventService;


    /** Automatically provides on first day of every month all employees with info about their income amounts for past month. */
    @Scheduled(cron = "0 0 12 1 * *")
    public void provideEmployeesWithSalaryInfo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String previousMonth = new SimpleDateFormat("MMMM YYYY").format(calendar.getTime());
        calendar.set(Calendar.DATE, 1);
        Date firstMonthDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastMonthDay = calendar.getTime();

        List<Employee> employees = employeeService.getAll();
        for (Employee e : employees) {
            File payslip = generateEmployeePayslip(e, firstMonthDay, lastMonthDay);
            emailService.sentPayslipEmail(e, payslip, previousMonth);
        }
    }


    /** Provide specified employee with info about his/her income amount for specified month.
     * @param firstName - employee's first name
     * @param lastName - employee's last name
     * @param month - month an income to be retrieved for
     */
    public void provideEmployeeWithSalaryInfo(String firstName, String lastName, Date month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        String monthName = new SimpleDateFormat("MMMM YYYY").format(month);
        calendar.set(Calendar.DATE, 1);
        Date firstMonthDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastMonthDay = calendar.getTime();
        Employee e = employeeService.getByFirstNameAndLastName(firstName, lastName);
        File payslip = generateEmployeePayslip(e, firstMonthDay, lastMonthDay);
        emailService.sentPayslipEmail(e, payslip, monthName);
        payslip.delete();
    }


    /** Generate employee's payslip for specified period of time.
     * @param e - employee
     * @param dateFrom - date from
     * @param dateTo - date to
     * @return - generated payslip
     */
    public File generateEmployeePayslip(Employee e, Date dateFrom, Date dateTo) {
        String month = new SimpleDateFormat("MMMM YYYY").format(dateFrom);
        BigDecimal income = eventService.getIncomePerEmployeePerPeriod(e.getFirstName(),e.getLastName(),dateFrom, dateTo);
        List<Event> events = eventService.getEventsByEmployeePerPeriod(e.getId(), dateFrom, dateTo);
        return generatePdfFileFromData(month, e, income, events);
    }


    /** Create pdf payslip file containing:
     *      - employee's details;
     *      - employee's monthly income amount;
     *      - listed events generated monthly income.
     * @param month - paid month
     * @param e - paid employee
     * @param income - monthly amount earned by employee
     * @param events - list of events generated employee's monthly income
     * @return - pdf document
     */
    private File generatePdfFileFromData(String month, Employee e, BigDecimal income, List<Event> events) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDFont font = PDType1Font.HELVETICA;

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);;
            contentStream.beginText();
            contentStream.setFont(font,15);
            contentStream.newLineAtOffset(180,700);
            contentStream.showText("FinDevSystem Payslip " + month);

            // employee's details header
            contentStream.newLineAtOffset(-110, -50);
            contentStream.setFont(font,12);
            contentStream.showText("Name");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Surname");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Department");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Position");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Email");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Hour rate");

            // employee's details data
            contentStream.newLineAtOffset(100, 75);
            contentStream.showText(e.getFirstName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(e.getLastName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(e.getDepartment().getName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(e.getPosition().getName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(e.getEmail());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("$" + e.getHourRate().toString());

            // employee's income
            contentStream.newLineAtOffset(-100, -30);
            contentStream.showText("Total monthly income:  $" + income);

            // employee's events
            contentStream.newLineAtOffset(0, -30);
            contentStream.showText("Income events per applicable month:");
            contentStream.newLineAtOffset(30, -30);
            // employee's events header
            contentStream.showText("Date");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Time spent");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Event type");
            contentStream.newLineAtOffset(-200, -20);
            // listed events
            for (Event event : events) {
                contentStream.showText(new SimpleDateFormat("yyyy-MM-dd").format(event.getDate()));
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(event.getHours().toString() + " hours");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(event.getEventType().getName());
                contentStream.newLineAtOffset(-200, -15);
            }
            contentStream.endText();
            contentStream.close();
            File file = new File("files\\payslip" + e.getLastName() + ".pdf");
            document.save(file);
            return file;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
