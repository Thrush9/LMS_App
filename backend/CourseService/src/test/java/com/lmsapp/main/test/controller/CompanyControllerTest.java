package com.lmsapp.main.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmsapp.main.controller.CompanyController;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CompanyService companyService;

	private Company company;

	private List<Company> companyList;

	@InjectMocks
	private CompanyController companyController;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
		companyController = new CompanyController(companyService);

		company = new Company("6363865c7cfc2705b750d4zc", "Public", "Test Teachings", "Test@gmail.com", "Test Engineer", "1234567890");
		companyList = new ArrayList<Company>();
		companyList.add(company);
	}

	@Test
	public void testRegisterCompanySuccess() throws Exception {
		lenient().when(companyService.registerCompany(company)).thenReturn(company);
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/lms/company/register")
				.content(asJsonString(company)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

//	@Test
//	public void testRegisterCompanyFailure() throws Exception {
//		lenient().when(companyService.registerCompany(company))
//				.thenThrow(new CompanyAlreadyExistsException("Company already exists with name : Test Teachings"));
//		MockHttpServletResponse response = mockMvc
//				.perform(MockMvcRequestBuilders.post("/api/v1.0/lms/company/register").content(asJsonString(company))
//						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//				.andReturn().getResponse();
//		System.out.println(response.getContentAsString());
//		assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
//	}

	@Test
	public void testFetchAllcompaniesSuccess() throws Exception {
		// given
		when(companyService.getAllCompanies()).thenReturn(companyList);
		// when
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1.0/lms/company/getAllCompanies")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getContentAsString(), asJsonString(companyList));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
