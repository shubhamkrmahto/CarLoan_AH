package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.service.LoanDisbursementService;

@RequestMapping("/AH")
@RestController
public class LoanDisbursementController {

	@Autowired LoanDisbursementService loanDisbursementService;
	
	
	
}
