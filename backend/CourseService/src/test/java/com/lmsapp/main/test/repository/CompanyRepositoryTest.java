package com.lmsapp.main.test.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lmsapp.main.model.Company;
import com.lmsapp.main.repository.CompanyRepository;


@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CompanyRepositoryTest {
	
	@Autowired
	CompanyRepository companyRepo;
	
	Company company;
	
	@BeforeEach
	public void setUp() {
		company = new Company();
		company.setId("6363865c7cfc2705b750d4zc");
		company.setEmail("Test@gmail.com");
		company.setName("Test Teachings");
		company.setType("Public");
		company.setPoc("Test Engineer");
		company.setContact("1234567890");
	}

	@AfterEach
	public void tearDown() throws Exception {
		companyRepo.delete(company);
	}
	
	@Test
	public void testFindCompanyByNameSuccess() {
		companyRepo.save(company);
		Optional<Company> companyOpt = companyRepo.findCompanyByName("Test Teachings");
		Company companyObj = companyOpt.get();
		assertEquals(companyObj.getName(),company.getName());
	}

}
