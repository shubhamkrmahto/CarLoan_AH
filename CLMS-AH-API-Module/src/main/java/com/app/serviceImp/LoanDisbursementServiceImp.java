package com.app.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.LoanDisbursement;
import com.app.model.SanctionLetter;
import com.app.repository.LoanDisbursementRepository;
import com.app.service.LoanDisbursementService;

@Service
public class LoanDisbursementServiceImp implements LoanDisbursementService{
	
	@Autowired LoanDisbursementRepository loanDisbursementRepository;

	@Override
	public void saveLoanDisbursement(SanctionLetter sanction, LoanDisbursement loandisbursement) {
		
		loandisbursement.setLoanNo(sanction.getSanctionLetterId());
		loandisbursement.setAgreementDate(sanction.getSanctionDate());
		loandisbursement.setTotalAmount(sanction.getLoanAmtountSanctioned());
		loanDisbursementRepository.save(loandisbursement);
		
		
	}

	
	
}
