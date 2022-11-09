package com.lmsapp.main.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.lmsapp.main.exception.CompanyAlreadyExistsException;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.repository.CompanyRepository;
import com.lmsapp.main.service.CompanyService;
import com.lmsapp.main.service.CompanyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

	@Mock
	private CompanyRepository companyRepo;

	private CompanyService companyService;

	private Company company;

	private List<Company> companyList;

	@InjectMocks
	private CompanyServiceImpl companyServiceImpl;

	@BeforeEach
	public void setUp() throws Exception {
		companyService = new CompanyServiceImpl(companyRepo);

		company = new Company("6363865c7cfc2705b750d4zc", "Public", "Test Teachings", "Test@gmail.com", "Test Engineer", "1234567890");
		companyList = new ArrayList<Company>();
		companyList.add(company);
	}
	
	@Test
	public void testRegisterCompanySuccess() throws CompanyAlreadyExistsException {
		lenient().when(companyRepo.findCompanyByName(company.getName())).thenReturn(Optional.empty());
		when(companyRepo.save(company)).thenReturn(company);
		Company added = companyService.registerCompany(company);
		assertEquals(company, added);
		assertEquals(company.getId(), added.getId());
		assertEquals(company.getName(), added.getName());
		assertEquals(company.getType(), added.getType());
		assertEquals(company.getEmail(), added.getEmail());
	}
	
	@Test
	public void testRegisterCompanyFailure()  {
		lenient().when(companyRepo.findCompanyByName(company.getName())).thenReturn(Optional.of(company));
		CompanyAlreadyExistsException exception = assertThrows(CompanyAlreadyExistsException.class, () -> companyService.registerCompany(company));
		 assertEquals("Company already exists with name : Test Teachings", exception.getMessage());
	}

	@Test
	public void testgetAllCompaniesSucess() {
		lenient().when(companyRepo.save(company)).thenReturn(company);
		when(companyRepo.findAll()).thenReturn(companyList);
		List<Company> companies = companyService.getAllCompanies();
		companies.forEach(comp -> System.out.println(comp.toString()));
		assertEquals(companyList, companies);
	}
}
