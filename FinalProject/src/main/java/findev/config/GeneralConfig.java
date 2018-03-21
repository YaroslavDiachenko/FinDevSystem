package findev.config;

import findev.model.Employee;
import findev.model.Event;
import findev.model.EventType;
import findev.model.dto.EventDTOGet;
import findev.model.dto.EventDTOPost;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class GeneralConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Event.class, EventDTOGet.class).setPostConverter(
            context -> {
                List<Employee> employees = context.getSource().getEmployees();
                List<String> names = new ArrayList<>();
                for (Employee employee : employees) {
                    names.add(employee.getLastName());
                }
                context.getDestination().setEmployeesLastNames(names);
                Date date = context.getSource().getDate();
                context.getDestination().setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
                return context.getDestination();
            });

        modelMapper.createTypeMap(EventDTOPost.class,Event.class).setPostConverter(
            context -> {
                List<Long> employeesIds = context.getSource().getEmployeesIds();
                List<Employee> employees = new ArrayList<>();
                for (Long employeeId : employeesIds) {
                    Employee employee = new Employee();
                    employee.setId(employeeId);
                    employees.add(employee);
                }
                context.getDestination().setEmployees(employees);
                EventType eventType = new EventType();
                eventType.setId(context.getSource().getEventTypeId());
                context.getDestination().setEventType(eventType);
                return context.getDestination();
            });

        return modelMapper;
    }
}
