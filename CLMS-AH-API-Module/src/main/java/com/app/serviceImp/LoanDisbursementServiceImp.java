package com.app.serviceImp;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.LoanDisbursement;
import com.app.repository.LoanDisbursementRepository;
import com.app.service.LoanDisbursementService;

@Service
public class LoanDisbursementServiceImp implements LoanDisbursementService{
	
	@Autowired LoanDisbursementRepository loanDisbursementRepository;

	@Override
	public LoanDisbursement getById(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<LoanDisbursement> byId = loanDisbursementRepository.findById(id);
		
		if(byId.isPresent()) {
			
			return byId.get();
		}
		return null;
	}

	@Override
	public String updateBankDetails(Integer id, LoanDisbursement ld) {
		// TODO Auto-generated method stub
		
		LoanDisbursement loanDisbursement = getById(id);
	
		if(!loanDisbursement.equals(null)) {
		
		loanDisbursement.setBankName(ld.getBankName());
		loanDisbursement.setAccountNumber(ld.getAccountNumber());
		loanDisbursement.setIFSCCode(ld.getIFSCCode());
		loanDisbursement.setAccountType(ld.getAccountType());
		
		loanDisbursementRepository.save(loanDisbursement);
		
		return "Bank details has been updated Successfully.";
		}
		return "Details Not present for this id.";
	}

	@Override
	public String updateDownPayment(Integer id, Double amount) {
		// TODO Auto-generated method stub
		
		LoanDisbursement loanDisbursement = getById(id);
		
		if(!loanDisbursement.equals(null)) {
			
			loanDisbursement.setTransferAmount(amount);
			loanDisbursement.setAmountPaidDate(LocalDate.now());
			loanDisbursement.setPaymentStatus("PAID");
			
			loanDisbursementRepository.save(loanDisbursement);
			
			return "First Amount Has been paid Successfully";
			}
			return "ID not present.";
		
	}

}
