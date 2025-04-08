package com.app.service;

import com.app.model.LoanDisbursement;

public interface LoanDisbursementService {
	
	public LoanDisbursement getById(Integer id);

	public String updateBankDetails(Integer id, LoanDisbursement ld);
	
	public String updateDownPayment(Integer id, Double amount);

}