package findev.service;

import findev.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private void sendMail(String toEmail, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

    private void sedEmailWithAttachment(String toEmail, String subject, String text, File attachment) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment("Payslip", attachment);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendWelcomeEmail(Employee e, String password) {
        String emailSubject = "FinDevSystem registration notification";
        String emailMessage = "Dear " + e.getFirstName() + " " + e.getLastName() + "," +
                "\n\nYou have been registered in findevsystem.\n\nYour current credentials:" +
                "\n\n\tusername: \t" + e.getUser().getUsername() +
                "\n\tpassword: \t" + password;
        sendMail(e.getEmail(),emailSubject,emailMessage);
    }

    /** Sent to the employee generated payslip with specified corresponding month.
     * @param e - payslip addressee
     * @param payslip - monthly payslip pdf document
     * @param month - paid month
     */
    public void sentPayslipEmail(Employee e, File payslip, String month) {
        String subject = "FinDevSystem payslip " + month;
        String text = "Dear " + e.getFirstName() + " " + e.getLastName() + "," +
                "\n\nPlease find your payslip for " + month + " attached.";
        sedEmailWithAttachment(e.getEmail(), subject, text, payslip);
    }

    public void sentNewPasswordEmail(Employee e, String newPassword) {
        String subject = "FinDevSystem credentials";
        String messsage = "Dear " + e.getFirstName() + " " + e.getLastName() + "," +
                "\n\nYour password has been reset." +
                "\n\nYour new password: \t" + newPassword;
        sendMail(e.getEmail(), subject, messsage);
    }
}