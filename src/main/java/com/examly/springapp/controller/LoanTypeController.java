package com.examly.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.LoanType;
import com.examly.springapp.service.LoanTypeService;

@RestController
@RequestMapping("/api/loantypes")
public class LoanTypeController {

    @Autowired
    private LoanTypeService loanTypeService;


    @GetMapping()
    public ResponseEntity<List<LoanType>> getAllLoanTypes() {
       return new ResponseEntity<>(loanTypeService.getAllLoanTypes(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LoanType> getLoanTypeById(@PathVariable Long id) {
        LoanType loanType = loanTypeService.getLoanTypeById(id);
        if (loanType != null) {
            return new ResponseEntity<>(loanType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }     
    
    @PostMapping
    public ResponseEntity<LoanType> createLoanType(@RequestBody LoanType loanType) {
        LoanType createdLoanType = loanTypeService.createLoanType(loanType);
        return new ResponseEntity<>(createdLoanType, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id) {
        LoanType loanType = loanTypeService.getLoanTypeById(id);
        if (loanType != null) {
            loanTypeService.deleteLoanType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
