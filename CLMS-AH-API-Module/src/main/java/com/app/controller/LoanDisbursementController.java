package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.model.Ledger;
import com.app.model.LoanDisbursement;
import com.app.model.SanctionLetter;
import com.app.service.LoanDisbursementService;

@RequestMapping("/AH")
@RestController
public class LoanDisbursementController {

	@Autowired LoanDisbursementService loanDisbursementService;
	
	@Autowired
	RestTemplate rt;
	
	@PostMapping("/loandisbursement/{id}")

	public ResponseEntity<String> loanDisbursementStatus(@PathVariable("id") Integer id) {
		
		String url ="http://localhost:6000/CM/getSanction/"+id;
		
		SanctionLetter sanction = rt.getForObject(url, SanctionLetter.class);
		
		System.out.println(sanction);
		
		String msg = loanDisbursementService.saveLoanDisbursement(sanction);
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanDisbursement/{id}")
	public ResponseEntity<LoanDisbursement> getLoanDisbursement(@PathVariable("id") Integer id)
	{
		return new ResponseEntity<LoanDisbursement>(loanDisbursementService.getById(id), HttpStatus.OK);
	}
	
	
	@PatchMapping("/addBankAccountDetails/{id}")
	private ResponseEntity<String> updateBankDetails(@PathVariable("id") Integer id,@RequestBody LoanDisbursement ld)
	{
		
		return new ResponseEntity<String>(loanDisbursementService.updateBankDetails(id, ld), HttpStatus.OK); 
	}
	
	
	@PatchMapping("/payDownPayment/{id}")
	private ResponseEntity<String> payDownPayment(@PathVariable("id") Integer id,@RequestBody LoanDisbursement ld)
	{
		
		return new ResponseEntity<String>(loanDisbursementService.updateDownPayment(id, ld), HttpStatus.OK); 
	}
	
	@PatchMapping("/generateLedger/{id}")
	private ResponseEntity<List<Ledger>> generateLedger(@PathVariable("id") Integer id)
	{
		
		return new ResponseEntity<List<Ledger>>(loanDisbursementService.generateLedger(id), HttpStatus.OK); 
	}
	
}
