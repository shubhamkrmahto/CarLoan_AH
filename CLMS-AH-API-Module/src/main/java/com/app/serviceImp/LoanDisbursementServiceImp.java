package com.app.serviceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Ledger;
import com.app.model.LoanDisbursement;
import com.app.model.SanctionLetter;
import com.app.repository.LoanDisbursementRepository;
import com.app.service.LoanDisbursementService;

@Service
public class LoanDisbursementServiceImp implements LoanDisbursementService{
	
	@Autowired LoanDisbursementRepository loanDisbursementRepository;
	
	private static final Logger log = LoggerFactory.getLogger(LoanDisbursementServiceImp.class);

	@Override
	public LoanDisbursement getById(Integer id) {
		
		Optional<LoanDisbursement> byId = loanDisbursementRepository.findById(id);
		
		if(byId.isPresent()) {
			log.info("Get LoanDisbursement");
			return byId.get();
		}
		return null;
	}
	

	@Override
	public String updateBankDetails(Integer id, LoanDisbursement ld) {
		
		LoanDisbursement loanDisbursement = getById(id);
	
		if(!loanDisbursement.equals(null)) {
		
		loanDisbursement.setBankName(ld.getBankName());
		loanDisbursement.setAccountNumber(ld.getAccountNumber());
		loanDisbursement.setIfscCode(ld.getIfscCode());
		loanDisbursement.setAccountType(ld.getAccountType());
		
		loanDisbursementRepository.save(loanDisbursement);
		log.info("Bank details has been updated Successfully.: "+id);
		return "Bank details has been updated Successfully.";
		}
		log.warn("Details Not present for this id.");
		return "Details Not present for this id.";
	}

	@Override
	public String updateDownPayment(Integer id, LoanDisbursement ld) {
		
		LoanDisbursement loanDisbursement = getById(id);
		
		if(!loanDisbursement.equals(null)) {
			
			loanDisbursement.setTransferAmount(ld.getTransferAmount());
			loanDisbursement.setAmountPaidDate(LocalDate.now());
			loanDisbursement.setPaymentStatus("PAID");
			loanDisbursement.setAmountPayType(ld.getAmountPayType());
			
			loanDisbursementRepository.save(loanDisbursement);
			log.info("First Amount Has been paid Successfully : "+id);
			return "First Amount Has been paid Successfully";
			}
			log.warn("ID not present. : "+id);
			return "ID not present.";
		
	}

	public String saveLoanDisbursement(SanctionLetter sanction) {
		
		LoanDisbursement loandisbursement = new LoanDisbursement();
		
		loandisbursement.setLoanNo(sanction.getSanctionLetterId());
		loandisbursement.setAgreementDate(sanction.getSanctionDate());
		loandisbursement.setTotalAmount(sanction.getLoanAmtountSanctioned());
		loandisbursement.setTotalAmount(sanction.getLoanAmount());
		
		
		loanDisbursementRepository.save(loandisbursement);
		log.info("Loan Disbursement has been Saved");
		return "Loan Disbursement has been created.";
	}
	
	public List<Ledger> generateLedger(Integer id) {
		
		LoanDisbursement loanDisbursement = getById(id);
		
		List<Ledger> ledgerList = new ArrayList<>();
		
		LocalDate ld = LocalDate.now();
		
		if(!loanDisbursement.equals(null)) {
		
			for(int i=1;i<=12;i++) {
			
			Ledger ledger = new Ledger();
			
			ledger.setLedgerCreatedDate(ld);
			ledger.setTotalLoanAmount(loanDisbursement.getTotalAmount()-loanDisbursement.getTransferAmount());
			ledger.setPayableAmountWithInterest(300000.00);
			ledger.setTenure(24);
			ledger.setMonthlyEMI(350000.00/18);
			ledger.setAmountPaidTillDate(00.00);
			ledger.setRemainingAmount(350000.00);
			ledger.setNextEmiDateStart(LocalDate.now());
			ledger.setNextEmiDateEnd(LocalDate.now());
			ledger.setDefaulterCount(0);
			ledger.setPreviousEMIStatus("PAID");
			ledger.setCurrentMonthEMIStatus("PENDING");
			ledger.setLoanEndDate(ld);
			ledger.setLoanStatus("NOT COMPLETED");
		
			ledgerList.add(ledger);
			
			}
			log.info("Ledger has been genrated for this id : "+id);			
	}
		
		loanDisbursement.setLedger(ledgerList);
		
		loanDisbursementRepository.save(loanDisbursement);
		
		return ledgerList;
	}

}
