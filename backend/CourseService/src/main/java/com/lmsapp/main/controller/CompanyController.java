package com.lmsapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmsapp.main.exception.CompanyAlreadyExistsException;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.service.CompanyService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/lms/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
	  this.companyService = companyService;	
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerCompany(@RequestBody Company companyObj){
		try {
			System.out.println(companyObj.toString());
			Company addedData = companyService.registerCompany(companyObj);
			//System.out.println(addedData.toString());
			return new ResponseEntity<Company>(addedData, HttpStatus.CREATED);
		}
		catch(CompanyAlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/getAllCompanies")
	public ResponseEntity<?> getAllCompanies(){
		try {
			List<Company> list = companyService.getAllCompanies();
			return new ResponseEntity<List<Company>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
