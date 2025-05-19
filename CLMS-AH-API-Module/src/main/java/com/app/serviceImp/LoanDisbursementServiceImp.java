package com.app.serviceImp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.enums.LoanEMIStatus;
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
	public List<LoanDisbursement> getAllLoanDisbursement() {
		
		return loanDisbursementRepository.findAll();
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
		
		List<Ledger> ledgerList = new ArrayList<>();
		
		LocalDate todayDate = LocalDate.now(); 
		
		loandisbursement.setSanctionId(sanction.getSanctionLetterId());
		loandisbursement.setAgreementDate(sanction.getSanctionDate());
		loandisbursement.setTotalAmount(sanction.getLoanAmount());
		
		LocalDate finalEMIDate = todayDate.plusMonths(sanction.getLoanTenureInMonth());
		
		for(int i=1;i<=sanction.getLoanTenureInMonth();i++) {
			
			LocalDate emiStartDate = todayDate.plusMonths(i);
			LocalDate emiEndDate = emiStartDate.plusDays(5);
			
			Ledger ledger = new Ledger();
			
			Double interestAmount = sanction.getLoanAmtountSanctioned()*(sanction.getRateOfInterest()/100);
			Double payableAmount = interestAmount+sanction.getLoanAmtountSanctioned();
			
			ledger.setMonthlyid(i);
			ledger.setLedgerCreatedDate(todayDate);
			ledger.setTotalLoanAmount(sanction.getLoanAmtountSanctioned());
			ledger.setPayableAmountWithInterest(payableAmount);
			ledger.setTenure(sanction.getLoanTenureInMonth());
			ledger.setMonthlyEMI(sanction.getMonthlyEMIAmount());
			ledger.setAmountPaidTillDate(00.00);
			ledger.setRemainingAmount(payableAmount);
			ledger.setNextEmiDateStart(emiStartDate);
			ledger.setNextEmiDateEnd(emiEndDate);
			ledger.setDefaulterCount(0);
			ledger.setPreviousEMIStatus(LoanEMIStatus.UNPAID);
			ledger.setCurrentMonthEMIStatus(LoanEMIStatus.UPCOMING);
			ledger.setLoanEndDate(finalEMIDate);
			ledger.setLoanStatus("NOT COMPLETED");
			
			ledgerList.add(ledger);
		
			}
		
		loandisbursement.setLedger(ledgerList);
		
		
		loanDisbursementRepository.save(loandisbursement);
		log.info("Loan Disbursement has been Saved");
		return "Loan Disbursement has been created.";
	}
	

	public String payEMI(Integer ldId, Integer month) {
	    LoanDisbursement loanDisbursement = getById(ldId);
	    List<Ledger> ledgerList = loanDisbursement.getLedger();

	    // Sort ledgers by monthlyId
	    ledgerList.sort(Comparator.comparing(Ledger::getMonthlyid, Comparator.nullsLast(Integer::compareTo)));

	    // Use the first ledger's payable amount (or from loanDisbursement if available)
	    double totalPayableAmount = 0.0;
	    if (!ledgerList.isEmpty()) {
	        totalPayableAmount = ledgerList.get(0).getPayableAmountWithInterest();
	    }

	    double cumulativePaid = 0.0;

	    for (Ledger ledger : ledgerList) {
	        Integer monthId = ledger.getMonthlyid();
	        if (monthId == null) continue;

	        if (monthId < month) {
	            cumulativePaid += ledger.getMonthlyEMI();
	            continue; // Skip past paid months
	        }

	        if (monthId == month) {
	            // Mark current EMI as paid
	            cumulativePaid += ledger.getMonthlyEMI();

	            ledger.setAmountPaidTillDate(cumulativePaid);
	            ledger.setRemainingAmount(totalPayableAmount - cumulativePaid);

	            ledger.setPreviousEMIStatus(LoanEMIStatus.UNPAID);
	            ledger.setCurrentMonthEMIStatus(LoanEMIStatus.PAID);
	        } else if (monthId == month + 1) {
	            // Next month
	            ledger.setAmountPaidTillDate(cumulativePaid);
	            ledger.setRemainingAmount(totalPayableAmount - cumulativePaid);

	            ledger.setPreviousEMIStatus(LoanEMIStatus.PAID);
	            ledger.setCurrentMonthEMIStatus(LoanEMIStatus.UPCOMING);
	        } else {
	            // Future months
	            ledger.setAmountPaidTillDate(cumulativePaid);
	            ledger.setRemainingAmount(totalPayableAmount - cumulativePaid);

	            ledger.setPreviousEMIStatus(LoanEMIStatus.UPCOMING);
	            ledger.setCurrentMonthEMIStatus(LoanEMIStatus.UNPAID);
	        }
	    }

	    loanDisbursement.setLedger(ledgerList);
	    loanDisbursementRepository.save(loanDisbursement);

	    return "EMI for month " + month + " marked as paid.";
	}

}
