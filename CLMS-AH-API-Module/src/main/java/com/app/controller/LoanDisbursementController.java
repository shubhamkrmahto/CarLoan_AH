package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.LoanDisbursement;
import com.app.service.LoanDisbursementService;

@RequestMapping("/AH")
@RestController
public class LoanDisbursementController {

	@Autowired LoanDisbursementService loanDisbursementService;
	
	@PatchMapping("/addBankAccountDetails/{id}")
	private ResponseEntity<String> updateBankDetails(@PathVariable("id") Integer id,@RequestBody LoanDisbursement ld)
	{
		
		return new ResponseEntity<String>(loanDisbursementService.updateBankDetails(id, ld), HttpStatus.OK); 
	}
	
	@PatchMapping("/payDownPayment/{id}/{amount}")
	private ResponseEntity<String> payDownPayment(@PathVariable("id") Integer id,@PathVariable("amount") Double totalAmount)
	{
		
		return new ResponseEntity<String>(loanDisbursementService.updateDownPayment(id, totalAmount), HttpStatus.OK); 
	}
}
