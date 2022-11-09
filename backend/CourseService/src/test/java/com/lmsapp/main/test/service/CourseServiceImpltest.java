package com.lmsapp.main.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.lmsapp.main.exception.CompanyNotFoundException;
import com.lmsapp.main.exception.CourseAlreadyExistsException;
import com.lmsapp.main.exception.CourseNotFoundException;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.model.Course;
import com.lmsapp.main.repository.CompanyRepository;
import com.lmsapp.main.repository.CourseRepository;
import com.lmsapp.main.service.CourseService;
import com.lmsapp.main.service.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImpltest {

	@Mock
	private CourseRepository courseRepo;
	
	@Mock 
    private CompanyRepository companyRepo;
	
	private CourseService courseService;

	private Course course;
	
	private Company company;

	private List<Course> courseList;

	@InjectMocks
	private CourseServiceImpl courseServiceImpl;

	@BeforeEach
	public void setUp() throws Exception {
		courseService = new CourseServiceImpl(courseRepo,companyRepo);

		course = new Course("12343865c7cfc2705b750d4zc",333L, "Mockito Test Cases", "Teachings about Test Cases", "Testing",500L, "www.testorg.com","6363865c7cfc2705b750d4zc");
		company = new Company("6363865c7cfc2705b750d4zc", "Public", "Test Teachings", "Test@gmail.com", "Test Engineer", "1234567890");
		
		courseList = new ArrayList<Course>();
		courseList.add(course);
	}
	
	@Test
	public void testaddCourseSuccess() throws CourseAlreadyExistsException {
		lenient().when(courseRepo.findCourseByNameandCourseId(course.getName(), course.getCourseId())).thenReturn(Optional.empty());
		when(courseRepo.save(course)).thenReturn(course);
		Course added = courseService.addCourse(course);
		assertEquals(course, added);
		assertEquals(course.getId(), added.getId());
	}
	
	@Test
	public void testRegisterCompanyFailure() throws CourseAlreadyExistsException  {
		lenient().when(courseRepo.findCourseByNameandCourseId(course.getName(), course.getCourseId())).thenReturn(Optional.of(course));
		CourseAlreadyExistsException exception = assertThrows(CourseAlreadyExistsException.class, () -> courseService.addCourse(course));
		 assertEquals("Course already exists with name : Mockito Test Cases", exception.getMessage());
	}
	
	@Test
	public void testGetAllCoursesSucdess() {
		lenient().when(courseRepo.save(course)).thenReturn(course);
		when(courseRepo.findAll()).thenReturn(courseList);
		List<Course> courses = courseService.getAllCourses();
		courses.forEach(course -> System.out.println(course.toString()));
		assertEquals(courseList, courses);
		assertEquals(courseList.get(0).toString(), courses.get(0).toString());
	}
	
	@Test
	public void testGetAllCoursesByCompanySuccess() throws CompanyNotFoundException {
		lenient().when(companyRepo.findCompanyByName(company.getName())).thenReturn(Optional.of(company));
		when(courseRepo.findCoursesByCompanyId(company.getId())).thenReturn(courseList);
		List<Course> courses = courseService.getAllCoursesByCompany(company.getName());
		courses.forEach(course -> System.out.println(course.toString()));
		assertEquals(courseList, courses);
		assertEquals(courseList.get(0).getCompanyId(), courses.get(0).getCompanyId());
	}
	
	@Test
	public void testGetAllCoursesByCompanyFailure() throws CompanyNotFoundException  {
		lenient().when(companyRepo.findCompanyByName(company.getName())).thenReturn(Optional.empty());
		CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class, () -> courseService.getAllCoursesByCompany(company.getName()));
		 assertEquals("No Course exists with Company : Test Teachings", exception.getMessage());
	}
	
	@Test
	public void testGetAllCoursesByTechnologySuccess() {
		when(courseRepo.findCoursesByTechnology(course.getTechnology())).thenReturn(courseList);
		List<Course> courses = courseService.getAllCoursesByTechnology(course.getTechnology());
		courses.forEach(course -> System.out.println(course.toString()));
		assertEquals(courseList, courses);
		assertEquals(courseList.get(0).getTechnology(), courses.get(0).getTechnology());
	}
	
	@Test
	public void testUpdateCourseSuccess() throws CourseNotFoundException {
		lenient().when(courseRepo.findCourseByCourseId(course.getCourseId())).thenReturn(Optional.of(course));
		course.setDuration(250L);
		when(courseRepo.save(course)).thenReturn(course);
		Course updated = courseService.updateCourse(course.getCourseId(),course);
		assertEquals(course, updated);
		assertEquals(course.getDuration(), updated.getDuration());
	}
	
	@Test
	public void testUpdateCourseFailure() throws CourseNotFoundException  {
		lenient().when(courseRepo.findCourseByCourseId(course.getCourseId())).thenReturn(Optional.empty());
		CourseNotFoundException exception = assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(course.getCourseId(),course));
		 assertEquals("No Course exists with Id : 333", exception.getMessage());
	}
	
	@Test
	public void testDeleteCourseSuccess() throws CourseNotFoundException {
		lenient().when(courseRepo.findByName(course.getName())).thenReturn(Optional.of(course));
		courseService.deleteCourse(course.getName());
	}
	
	@Test
	public void testDeleteCourseFailure() throws CourseNotFoundException  {
		lenient().when(courseRepo.findByName(course.getName())).thenReturn(Optional.empty());
		CourseNotFoundException exception = assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(course.getName()));
		 assertEquals("No Course exists with Name : Mockito Test Cases", exception.getMessage());
	}
	

	@Test
	public void testGetCoursesBySearchSuccess1() {
		when(courseRepo.findCoursesByTechnologyAndDurationBetween(course.getTechnology(), 0L, 1000L)).thenReturn(courseList);
		List<Course> courses = courseService.getCoursesBySearch(course.getTechnology(),0L,1000L);
		courses.forEach(course -> System.out.println(course.toString()));
		assertEquals(courseList, courses);
		assertEquals(course.getTechnology(),courses.get(0).getTechnology());
	}
	
	@Test
	public void testGetCoursesBySearchSuccess2() {
		when(courseRepo.findCoursesByDurationBetween(0L, 1000L)).thenReturn(courseList);
		List<Course> courses = courseService.getCoursesBySearch("none",0L,1000L);
		courses.forEach(course -> System.out.println(course.toString()));
		assertEquals(courseList, courses);
		assertTrue(courses.get(0).getDuration() < 600L);
	}
	
	
}
