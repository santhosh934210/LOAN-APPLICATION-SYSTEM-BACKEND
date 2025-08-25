// package com.examly.springapp.test;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;

// @SpringBootApplication
// public class EmailTestApp implements CommandLineRunner {

//     @Autowired
//     private JavaMailSender mailSender;

//     public static void main(String[] args) {
//         SpringApplication.run(EmailTestApp.class, args);
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         try {
//             SimpleMailMessage message = new SimpleMailMessage();
//             message.setTo("santhosh2005.me@gmail.com"); // change to your email
//             message.setSubject("Test Email");
//             message.setText("This is a test email from Spring Boot.");
//             mailSender.send(message);
//             System.out.println("Email sent successfully!");
//         } catch (Exception e) {
//             e.printStackTrace();
//             System.out.println("Failed to send email: " + e.getMessage());
//         }
//     }
// }



package com.examly.springapp.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class EmailTestApp implements CommandLineRunner {

    @Autowired
    private JavaMailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(EmailTestApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("loanappsystem@gmail.com"); // change to your email
            message.setSubject("Test Email");
            message.setText("This is a test email from Spring Boot.");
            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
