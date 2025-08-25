// package com.examly.springapp.service;

// import jakarta.mail.MessagingException;
// import jakarta.mail.internet.MimeMessage;
// import lombok.RequiredArgsConstructor;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.MimeMessageHelper;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class NotificationService {

//     private final JavaMailSender mailSender;

//     @Async // Makes email sending asynchronous
//     public void sendEmail(String to, String subject, String text) {
//         try {
//             MimeMessage message = mailSender.createMimeMessage();
//             MimeMessageHelper helper = new MimeMessageHelper(message, true);

//             helper.setTo(to);
//             helper.setSubject(subject);
//             helper.setText(text, false);

//             mailSender.send(message);
//         } catch (MessagingException e) {
//             e.printStackTrace();
//         }
//     }

//     @Async
//     public void sendEmailToMultipleRecipients(List<String> recipients, String subject, String text) {
//         try {
//             MimeMessage message = mailSender.createMimeMessage();
//             MimeMessageHelper helper = new MimeMessageHelper(message, true);

//             helper.setTo(recipients.toArray(new String[0]));
//             helper.setSubject(subject);
//             helper.setText(text, false);

//             mailSender.send(message);
//         } catch (MessagingException e) {
//             e.printStackTrace();
//         }
//     }
// }



package com.examly.springapp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    @Async // Makes email sending asynchronous
    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailToMultipleRecipients(List<String> recipients, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(text, false);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}