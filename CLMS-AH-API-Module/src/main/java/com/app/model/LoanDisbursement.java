package com.app.model;

import java.time.LocalDate;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDisbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer	agreementId;
	private Integer loanNo;//from Sanction Letter Sanction ID
	private LocalDate agreementDate;//When the disbursement is generated
	private String amountPayType;//How the amount is being paid
	private Double totalAmount;//total amount to be paid
	private String bankName;//bank name of the dealer(patch)
	private Long accountNumber;//bank account number of the dealer(patch)
	private String IFSCCode;//bank IFSC Code(patch)
	private String accountType;//Type of account(patch)
	private Double transferAmount;//Initial payment or Down payment or the amount which is paid before starting of the emi
	private String paymentStatus;//Status of the payment Paid or unpaid
	private LocalDate amountPaidDate;//Date of when the amount is being paid 

}
