package com.examly.springapp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.exception.ResourceNotFoundException;
import com.examly.springapp.model.Document;
import com.examly.springapp.model.LoanApplication;
import com.examly.springapp.repository.DocumentRepository;
import com.examly.springapp.repository.LoanApplicationRepository;

@Service
public class DocumentService {


    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;  

    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document createDocument(Document document) {
         LoanApplication loanApp = loanApplicationRepository.findById(document.getLoanApplication().getId())
        .orElseThrow(() -> new ResourceNotFoundException("Loan Application not found"));

        // Set the full LoanApplication object
         document.setLoanApplication(loanApp);

        return documentRepository.save(document);
    }
    
    

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

}

