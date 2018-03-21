package findev.service;

import findev.model.Employee;
import findev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    public void sendWelcomeEmail(Employee employee, User user) {
        String emailSubject = "Findevsystem registration notification";
        String emailMessage = "Dear " + employee.getFirstName() + " " + employee.getLastName() + "," +
                "\n\nYou have been registered in findevsystem.\n\nYour current credentials:" +
                "\n\n\tusername: \t" + user.getUsername() +
                "\n\tpassword: \t" + user.getPassword();
        sendMail(employee.getEmail(),emailSubject,emailMessage);
    }
}
