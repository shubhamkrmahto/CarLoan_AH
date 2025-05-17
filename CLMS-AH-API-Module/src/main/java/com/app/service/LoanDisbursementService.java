package com.app.service;

import java.util.List;

import com.app.model.Ledger;
import com.app.model.LoanDisbursement;
import com.app.model.SanctionLetter;

public interface LoanDisbursementService {
	
	public LoanDisbursement getById(Integer id);
	
	public List<LoanDisbursement> getAllLoanDisbursement();

	public String updateBankDetails(Integer id, LoanDisbursement ld);
	
	public String updateDownPayment(Integer id, LoanDisbursement ld);

	public String saveLoanDisbursement(SanctionLetter sanction);
	
	public String payEMI(Integer ldId,Integer month);

}