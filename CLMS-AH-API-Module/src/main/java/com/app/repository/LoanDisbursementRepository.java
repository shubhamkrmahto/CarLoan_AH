package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.LoanDisbursement;

public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Integer> {

}
