// package com.examly.springapp.service;

// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

// @Service
// @Slf4j
// public class EmailService {

//     @Autowired
//     private JavaMailSender mailSender;

//     // Reusable method for sending any email
//      @Async
//     public void sendEmail(String toEmail, String subject, String body) {
//         try {
//             SimpleMailMessage message = new SimpleMailMessage();
//             message.setFrom("santhosh2005.me@gmail.com");
//             message.setTo(toEmail);
//             message.setSubject(subject);
//             message.setText(body);

//             mailSender.send(message);
//             log.info("Email sent successfully to {}", toEmail);
//         } catch (Exception e) {
//             log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
//             throw new RuntimeException("Error while sending email.");
//         }
//     }
// }



package com.examly.springapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("loanappsystem@gmail.com"); // ✅ must match spring.mail.username
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent successfully to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Error while sending email.");
        }
    }
}


// @Service
// @Slf4j
// public class EmailService {

//     @Autowired
//     private JavaMailSender mailSender;

//     // Reusable method for sending any email
//      @Async
//     public void sendEmail(String toEmail, String subject, String body) {
//         try {
//             SimpleMailMessage message = new SimpleMailMessage();
//             message.setTo(toEmail);
//             message.setSubject(subject);
//             message.setText(body);

//             mailSender.send(message);
//             log.info("Email sent successfully to {}", toEmail);
//         } catch (Exception e) {
//             log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
//             throw new RuntimeException("Error while sending email.");
//         }
//     }
// }
