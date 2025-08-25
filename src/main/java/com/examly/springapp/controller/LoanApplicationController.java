package com.examly.springapp.controller;


import com.examly.springapp.dto.LoanApplicationRequest;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "https://loan-application-system-santhosh.vercel.app")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService service;

    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllApplications(
            @RequestParam(value = "status", required = false) String status) {
        return ResponseEntity.ok(service.getAllApplications(status));
    }

    @PostMapping
    public ResponseEntity<LoanApplication> createApplication(@Valid @RequestBody LoanApplication application) {
        return ResponseEntity.status(201).body(service.createApplication(application));
    }
    //dto
    @PostMapping("/post")
    public ResponseEntity<LoanApplication> createApplications(@RequestBody LoanApplicationRequest request) {
       LoanApplication created = service.createApplications(request);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
}


    @GetMapping("/{id}")
    public ResponseEntity<LoanApplication> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getApplicationById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LoanApplication> updateStatus(@PathVariable Long id,
                                                        @RequestBody StatusUpdate update) {
        return ResponseEntity.ok(service.updateApplicationStatus(id, update.status, update.rejectionReason));
    }

    static class StatusUpdate {
        public String status;
        public String rejectionReason;
    }
}
