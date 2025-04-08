package com.app.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repository.LoanDisbursementRepository;
import com.app.service.LoanDisbursementService;

@Service
public class LoanDisbursementServiceImp implements LoanDisbursementService{
	
	@Autowired LoanDisbursementRepository loanDisbursementRepository;

}
