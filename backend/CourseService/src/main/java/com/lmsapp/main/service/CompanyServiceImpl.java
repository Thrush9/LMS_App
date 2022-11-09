package com.lmsapp.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmsapp.main.exception.CompanyAlreadyExistsException;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	CompanyRepository companyRepo;
	
	public CompanyServiceImpl(CompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}

	@Override
	public Company registerCompany(Company company) throws CompanyAlreadyExistsException {
		Optional<Company> checkexisting = companyRepo.findCompanyByName(company.getName());
		if(!checkexisting.isEmpty())
			throw new CompanyAlreadyExistsException("Company already exists with name : " + company.getName());
		Company added = companyRepo.save(company);
		return added;
	}

	@Override
	public List<Company> getAllCompanies() {
		List<Company> companiesList = new ArrayList<>();
		companiesList = companyRepo.findAll();
		return companiesList;
	}

}
