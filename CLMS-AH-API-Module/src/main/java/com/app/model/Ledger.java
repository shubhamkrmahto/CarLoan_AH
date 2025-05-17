package com.app.model;

import java.time.LocalDate;

import com.app.enums.LoanEMIStatus;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ledger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ledgerId;
	private Integer monthlyid;
	private LocalDate ledgerCreatedDate;
	private Double totalLoanAmount;
	private Double payableAmountWithInterest;
	private Integer tenure;
	private Double monthlyEMI;
	private Double amountPaidTillDate;
	private Double remainingAmount;
	private LocalDate nextEmiDateStart;
	private LocalDate nextEmiDateEnd;
	private Integer defaulterCount;
	
	@Enumerated(EnumType.STRING)
	private LoanEMIStatus previousEMIStatus;
	@Enumerated(EnumType.STRING)
	private LoanEMIStatus currentMonthEMIStatus;
	
	private LocalDate loanEndDate;
	private String loanStatus;
	
}
