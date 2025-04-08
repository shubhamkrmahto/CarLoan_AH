package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.model.LoanDisbursement;
import com.app.model.SanctionLetter;
import com.app.service.LoanDisbursementService;

@RequestMapping("/AH")
@RestController
public class LoanDisbursementController {

	@Autowired LoanDisbursementService loanDisbursementService;
	
	@Autowired
	RestTemplate rt;
	
	@PostMapping("/loandisbursement")
	public String loanDisbursementStatus(@PathVariable("id") Integer id, @RequestBody LoanDisbursement loandisbursement) {
		String url ="http://localhost:3306/getSanction"+id;
		SanctionLetter sanction = rt.getForObject(url, SanctionLetter.class);
		loanDisbursementService.saveLoanDisbursement(sanction,loandisbursement);
		
		return null;
	}
	
	
	
}
