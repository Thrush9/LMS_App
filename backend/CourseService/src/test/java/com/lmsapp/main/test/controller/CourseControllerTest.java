package com.lmsapp.main.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmsapp.main.controller.CourseController;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.model.Course;
import com.lmsapp.main.service.CourseService;

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

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CourseService courseService;

	private Course course;
	
	private Company company;

	private List<Course> courseList;
	@InjectMocks
	private CourseController courseController;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
		courseController = new CourseController(courseService);

		course = new Course("12343865c7cfc2705b750d4zc",333L, "Mockito Test Cases", "Teachings about Test Cases", "Testing",500L, "www.testorg.com","6363865c7cfc2705b750d4zc");
		company = new Company("6363865c7cfc2705b750d4zc", "Public", "Test Teachings", "Test@gmail.com", "Test Engineer", "1234567890");
		
		courseList = new ArrayList<Course>();
		courseList.add(course);
	}

	@Test
	public void testAddCourseSuccess() throws Exception {
		lenient().when(courseService.addCourse(course)).thenReturn(course);
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1.0/lms/courses/add/Mockito Test Cases")
				.content(asJsonString(course)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
	}

	@Test
	public void testGetAllCoursesSuccess() throws Exception {
		lenient().when(courseService.getAllCourses()).thenReturn(courseList);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1.0/lms/courses/getall")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getContentAsString(), asJsonString(courseList));
	}
	
	@Test
	public void testGetAllCoursesByCompanySuccess() throws Exception {
		lenient().when(courseService.getAllCoursesByCompany(company.getName())).thenReturn(courseList);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1.0/lms/courses/Test Teachings")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getContentAsString(), asJsonString(courseList));
	}
	
	@Test
	public void testGetAllCoursesByTechnologySuccess() throws Exception {
		lenient().when(courseService.getAllCoursesByTechnology(course.getTechnology())).thenReturn(courseList);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1.0/lms/courses/info/Testing")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getContentAsString(), asJsonString(courseList));
	}
	
	@Test
	public void testGetCoursesBySearchSuccess() throws Exception {
		lenient().when(courseService.getCoursesBySearch(course.getTechnology(),0L,1000L)).thenReturn(courseList);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1.0/lms/courses/get/Testing/0/1000")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertEquals(response.getContentAsString(), asJsonString(courseList));
	}
	
	@Test
	public void testDeleteCourseSuccess() throws Exception {
	  lenient().when(courseService.deleteCourse(course.getName())).thenReturn(true);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/v1.0/lms/courses/delete/Test Teachings")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	public void testUpdateCourseSuccess() throws Exception {
	  lenient().when(courseService.updateCourse(course.getCourseId(),course)).thenReturn(course);
		MockHttpServletResponse response = mockMvc
				.perform(MockMvcRequestBuilders.put("/api/v1.0/lms/courses/update/333")
						.content(asJsonString(course)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
