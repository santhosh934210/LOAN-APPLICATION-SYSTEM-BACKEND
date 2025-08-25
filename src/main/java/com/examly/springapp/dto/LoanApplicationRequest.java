package com.examly.springapp.dto;


import lombok.Data;

@Data
public class LoanApplicationRequest {
    private Long userId;
    private Long loanTypeId;

    private String fullName;
    private String email;
    private String phoneNumber;
    private Double loanAmount;
    private String loanPurpose;
    private String employmentStatus;
    private Double annualIncome;
    private Integer termMonths;
}

