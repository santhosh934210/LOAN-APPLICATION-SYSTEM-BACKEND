// package com.examly.springapp.service;

// import com.examly.springapp.dto.LoanApplicationRequest;
// import com.examly.springapp.exception.ResourceNotFoundException;
// import com.examly.springapp.exception.ValidationException;
// import com.examly.springapp.model.LoanApplication;
// import com.examly.springapp.model.LoanType;
// import com.examly.springapp.model.User;
// import com.examly.springapp.repository.LoanApplicationRepository;
// import com.examly.springapp.repository.LoanTypeRepository;
// import com.examly.springapp.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;

// import java.util.List;

// @Service
// public class LoanApplicationService {

//     @Autowired
//     private LoanApplicationRepository repo;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private LoanTypeRepository loanTypeRepository;

//     @Autowired
//     private NotificationService notificationService;

//     public LoanApplication createApplication(LoanApplication application) {
//         if (application.getLoanAmount() < 1000 || application.getLoanAmount() > 50000) {
//             throw new ValidationException("Loan amount must be between $1,000 and $50,000");
//         }

//         User user = userRepository.findById(application.getUser().getId())
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         LoanType loanType = loanTypeRepository.findById(application.getLoanType().getId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Loan type not found"));

//         application.setUser(user);
//         application.setLoanType(loanType);

//         LoanApplication savedApp = repo.save(application);

//         sendApplicationSubmittedNotifications(savedApp);

//         return savedApp;
//     }

//     public LoanApplication createApplications(LoanApplicationRequest request) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         String username = auth.getName();

//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found"));

//         LoanType loanType = loanTypeRepository.findById(request.getLoanTypeId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Loan type not found"));

//         if (request.getLoanAmount() < 1000 || request.getLoanAmount() > 50000) {
//             throw new ValidationException("Loan amount must be between $1,000 and $50,000");
//         }

//         LoanApplication app = LoanApplication.builder()
//                 .user(user)
//                 .loanType(loanType)
//                 .fullName(request.getFullName())
//                 .email(request.getEmail())
//                 .phoneNumber(request.getPhoneNumber())
//                 .loanAmount(request.getLoanAmount())
//                 .loanPurpose(request.getLoanPurpose())
//                 .employmentStatus(request.getEmploymentStatus())
//                 .annualIncome(request.getAnnualIncome())
//                 .termMonths(request.getTermMonths())
//                 .status("Pending")
//                 .build();

//         LoanApplication savedApp = repo.save(app);

//         sendApplicationSubmittedNotifications(savedApp);

//         return savedApp;
//     }

//     public List<LoanApplication> getAllApplications(String status) {
//         return (status == null) ? repo.findAll() : repo.findByStatus(status);
//     }

//     public LoanApplication getApplicationById(Long id) {
//         return repo.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
//     }

//     public LoanApplication updateApplicationStatus(Long id, String status, String rejectionReason) {
//         LoanApplication app = getApplicationById(id);

//         if (!"Approved".equalsIgnoreCase(status) && !"Rejected".equalsIgnoreCase(status)) {
//             throw new ValidationException("Invalid status. Must be Approved or Rejected");
//         }

//         if ("Rejected".equalsIgnoreCase(status) && (rejectionReason == null || rejectionReason.isBlank())) {
//             throw new ValidationException("Rejection reason is required for Rejected status");
//         }

//         app.setStatus(status);
//         app.setRejectionReason("Rejected".equalsIgnoreCase(status) ? rejectionReason : null);

//         LoanApplication updatedApp = repo.save(app);

//         sendStatusUpdateNotification(updatedApp);

//         return updatedApp;
//     }

//     /**
//      * ✅ Send notifications for new application
//      */
//     @Async
//     public void sendApplicationSubmittedNotifications(LoanApplication app) {
//         try {
//             // ✅ Email to Applicant
//             String applicantSubject = "Loan Application Submitted";
//             String applicantBody = "Hi " + app.getFullName() + ",\n\n" +
//                     "Your loan application for " + app.getLoanType().getName() +
//                     " has been submitted successfully.\nStatus: " + app.getStatus() +
//                     "\n\nThank you,\nLoan Team";
//             notificationService.sendEmail(app.getEmail(), applicantSubject, applicantBody);

//             // ✅ Get Admin & Agent emails dynamically from DB
//             List<User> adminsAndAgents = userRepository.findByRoleIn(List.of("ADMIN", "AGENT"));

//             for (User user : adminsAndAgents) {
//                 String subject = "New Loan Application Submitted";
//                 String body = "A new loan application has been submitted.\n\n" +
//                         "Applicant: " + app.getFullName() + "\n" +
//                         "Loan Type: " + app.getLoanType().getName() + "\n" +
//                         "Amount: $" + app.getLoanAmount() + "\n" +
//                         "Status: " + app.getStatus();
//                 notificationService.sendEmail(user.getEmail(), subject, body);
//             }

//         } catch (Exception e) {
//             System.err.println("Failed to send submission notifications: " + e.getMessage());
//         }
//     }

//     /**
//      * ✅ Send notification for status update
//      */
//     @Async
//     public void sendStatusUpdateNotification(LoanApplication app) {
//         try {
//             String subject = "Loan Application Status Updated";
//             String body = "Hi " + app.getFullName() + ",\n\n" +
//                     "Your loan application for " + app.getLoanType().getName() +
//                     " has been " + app.getStatus() +
//                     (app.getRejectionReason() != null ? "\nReason: " + app.getRejectionReason() : "") +
//                     "\n\nThank you,\nLoan Team";
//             notificationService.sendEmail(app.getEmail(), subject, body);
//         } catch (Exception e) {
//             System.err.println("Failed to send status update email: " + e.getMessage());
//         }
//     }
// }



package com.examly.springapp.service;

import com.examly.springapp.dto.LoanApplicationRequest;
import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.exception.ValidationException;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.model.LoanType;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.LoanApplicationRepository;
import com.examly.springapp.repository.LoanTypeRepository;
import com.examly.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Create loan application (Admin/Agent version)
     */
    public LoanApplication createApplication(LoanApplication application) {

        User user = userRepository.findById(application.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LoanType loanType = loanTypeRepository.findById(application.getLoanType().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan type not found"));

        // ✅ Validate dynamically based on LoanType maxAmount
        if (application.getLoanAmount() < 100000 || application.getLoanAmount() > loanType.getMaxAmount()) {
            throw new ValidationException(
                    "Loan amount must be between $1,00000 and " + loanType.getMaxAmount());
        }

        application.setUser(user);
        application.setLoanType(loanType);

        LoanApplication savedApp = repo.save(application);

        sendApplicationSubmittedNotifications(savedApp);

        return savedApp;
    }

    /**
     * Create loan application (User self-submission version using DTO)
     */
    public LoanApplication createApplications(LoanApplicationRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LoanType loanType = loanTypeRepository.findById(request.getLoanTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Loan type not found"));

        // ✅ Validate dynamically based on LoanType maxAmount
        if (request.getLoanAmount() < 100000 || request.getLoanAmount() > loanType.getMaxAmount()) {
            throw new ValidationException(
                    "Loan amount must be between $1,00000 and " + loanType.getMaxAmount());
        }

        LoanApplication app = LoanApplication.builder()
                .user(user)
                .loanType(loanType)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .loanAmount(request.getLoanAmount())
                .loanPurpose(request.getLoanPurpose())
                .employmentStatus(request.getEmploymentStatus())
                .annualIncome(request.getAnnualIncome())
                .termMonths(request.getTermMonths())
                .status("Pending")
                .build();

        LoanApplication savedApp = repo.save(app);

        sendApplicationSubmittedNotifications(savedApp);

        return savedApp;
    }

    /**
     * Get all applications (filter by status if provided)
     */
    public List<LoanApplication> getAllApplications(String status) {
        return (status == null) ? repo.findAll() : repo.findByStatus(status);
    }

    /**
     * Get application by ID
     */
    public LoanApplication getApplicationById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
    }

    /**
     * Update application status (Approved/Rejected)
     */
    public LoanApplication updateApplicationStatus(Long id, String status, String rejectionReason) {
        LoanApplication app = getApplicationById(id);

        if (!"Approved".equalsIgnoreCase(status) && !"Rejected".equalsIgnoreCase(status)) {
            throw new ValidationException("Invalid status. Must be Approved or Rejected");
        }

        if ("Rejected".equalsIgnoreCase(status) && (rejectionReason == null || rejectionReason.isBlank())) {
            throw new ValidationException("Rejection reason is required for Rejected status");
        }

        app.setStatus(status);
        app.setRejectionReason("Rejected".equalsIgnoreCase(status) ? rejectionReason : null);

        LoanApplication updatedApp = repo.save(app);

        sendStatusUpdateNotification(updatedApp);

        return updatedApp;
    }

    /**
     * ✅ Send notifications for new application
     */
    @Async
    public void sendApplicationSubmittedNotifications(LoanApplication app) {
        try {
            // ✅ Email to Applicant
            String applicantSubject = "Loan Application Submitted";
            String applicantBody = "Hi " + app.getFullName() + ",\n\n" +
                    "Your loan application for " + app.getLoanType().getName() +
                    " has been submitted successfully.\nStatus: " + app.getStatus() +
                    "\n\nThank you,\nLoan Team";
            notificationService.sendEmail(app.getEmail(), applicantSubject, applicantBody);

            // ✅ Notify all Admins & Agents
            List<User> adminsAndAgents = userRepository.findByRoleIn(List.of("ADMIN", "AGENT"));

            for (User user : adminsAndAgents) {
                String subject = "New Loan Application Submitted";
                String body = "A new loan application has been submitted.\n\n" +
                        "Applicant: " + app.getFullName() + "\n" +
                        "Loan Type: " + app.getLoanType().getName() + "\n" +
                        "Amount: $" + app.getLoanAmount() + "\n" +
                        "Status: " + app.getStatus();
                notificationService.sendEmail(user.getEmail(), subject, body);
            }

        } catch (Exception e) {
            System.err.println("Failed to send submission notifications: " + e.getMessage());
        }
    }

    /**
     * ✅ Send notification for status update
     */
    @Async
    public void sendStatusUpdateNotification(LoanApplication app) {
        try {
            String subject = "Loan Application Status Updated";
            String body = "Hi " + app.getFullName() + ",\n\n" +
                    "Your loan application for " + app.getLoanType().getName() +
                    " has been " + app.getStatus() +
                    (app.getRejectionReason() != null ? "\nReason: " + app.getRejectionReason() : "") +
                    "\n\nThank you,\nLoan Team";
            notificationService.sendEmail(app.getEmail(), subject, body);
        } catch (Exception e) {
            System.err.println("Failed to send status update email: " + e.getMessage());
        }
    }
}


