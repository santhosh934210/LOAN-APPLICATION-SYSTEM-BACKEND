package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.LoanType;
import com.examly.springapp.repository.LoanTypeRepository;

@Service
public class LoanTypeService {
   
    @Autowired
    private LoanTypeRepository loanTypeRepository;

    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    public LoanType getLoanTypeById(Long id) {
        return loanTypeRepository.findById(id).orElse(null);    
    }

    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    public void deleteLoanType(Long id) {
      loanTypeRepository.deleteById(id);
    }

}

