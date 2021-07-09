package ru.dmitry.checkmydemo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.dmitry.checkmydemo.Entity.User;

@Service
public class MailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("@{spring.mail.username}")
    private String username;

    public void sendToActivateUser(User user){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("eroorofficial@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Verify your Account!");
        mailMessage.setText("Dear " + user.getUsername()
                + "to confirm the registration of your account, follow the link: http://localhost:8080/activate/"
                + user.getActivationToken());
        mailSender.send(mailMessage);
    }
}
