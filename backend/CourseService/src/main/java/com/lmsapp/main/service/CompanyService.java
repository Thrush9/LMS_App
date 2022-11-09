package com.lmsapp.main.service;

import java.util.List;

import com.lmsapp.main.exception.CompanyAlreadyExistsException;
import com.lmsapp.main.model.Company;

public interface CompanyService {

	public Company registerCompany(Company comapny) throws CompanyAlreadyExistsException;
	
	public List<Company> getAllCompanies();
}
