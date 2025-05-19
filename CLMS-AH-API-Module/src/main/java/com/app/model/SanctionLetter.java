package com.app.model;

import java.time.LocalDate;
import com.app.enums.SanctionLetterStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanctionLetter {

	private Integer sanctionLetterId;
	private LocalDate sanctionDate;
	private String applicantName;
	private Long contactDetails;
	private String applicantEmail;
	private Double loanAmtountSanctioned;
	private String interestType;
	private Integer rateOfInterest;
	private Integer loanTenureInMonth;
	private Double monthlyEMIAmount;
	private Double loanAmount;
	private String modeOfPayment;
	private String remarks;
	private String termsAndCondition;
	@Enumerated(EnumType.STRING)
	private SanctionLetterStatus status;
	private Integer cibilScore;

	
}

//{
//    "interestType":"FIXED",
//    "loanTenureInMonth":4,
//	"modeOfPayment":"ONLINE",
//	"remarks":"APPROVED BY CM",
//	"termsAndCondition":"abcdefghijklmnopqrstuvwxyz",
//    "status":"PENDING"
//}