package com.examly.springapp.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
    // Additional query methods can be defined here if needed

}
