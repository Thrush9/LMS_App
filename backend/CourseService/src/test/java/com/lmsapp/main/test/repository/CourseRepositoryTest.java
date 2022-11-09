package com.lmsapp.main.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lmsapp.main.model.Course;
import com.lmsapp.main.repository.CourseRepository;


@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CourseRepositoryTest {

	@Autowired
	CourseRepository courseRepo;
	
	Course course;
	
	List<Course> coursesList;
	
	@BeforeEach
	public void setUp() {
		course = new Course();
		course.setId("12343865c7cfc2705b750d4zc");
		course.setCompanyId("6363865c7cfc2705b750d4zc");
		course.setCourseId(333L);
		course.setName("Test Teachings");
		course.setDescription("Teachings about Test Cases");
		course.setTechnology("Testing");
		course.setDuration(500L);
		course.setUrl("www.testorg.com");
		
		coursesList = new ArrayList<Course>();
		coursesList.add(course);
		System.out.println(coursesList);
	}

	@AfterEach
	public void tearDown() throws Exception {
		courseRepo.delete(course);
	}
	
	@Test
	public void testFindCourseByNameSuccess() {
		courseRepo.save(course);
		Optional<Course> courseOpt = courseRepo.findByName("Test Teachings");
		Course courseObj = courseOpt.get();
		assertEquals(courseObj.getName(),course.getName());
	}
	
	@Test
	public void testFindCourseByNameAndCourseIdSuccess() {
		courseRepo.save(course);
		Optional<Course> courseOpt = courseRepo.findCourseByNameandCourseId("Test Teachings",333L);
		Course courseObj = courseOpt.get();
		assertEquals(courseObj.getName(),course.getName());
		assertEquals(courseObj.getCourseId(),course.getCourseId());
	}
	
	@Test
	public void testFindCourseByCourseIdSuccess() {
		courseRepo.save(course);
		Optional<Course> courseOpt = courseRepo.findCourseByCourseId(333L);
		Course courseObj = courseOpt.get();
		assertEquals(courseObj.getCourseId(),course.getCourseId());
	}
	
	@Test
	public void testFindCoursesByCompanyIdSuccess() {
		courseRepo.save(course);
		List<Course> list = courseRepo.findCoursesByCompanyId("6363865c7cfc2705b750d4zc");
		assertEquals(list.size(),coursesList.size());
		assertEquals(list.get(0).getCompanyId(),coursesList.get(0).getCompanyId());
	}
	
	@Test
	public void testFindCoursesByTechnologySuccess() {
		courseRepo.save(course);
		List<Course> list = courseRepo.findCoursesByTechnology("Testing");
		assertEquals(list.size(),coursesList.size());
		assertEquals(list.get(0).getTechnology(),coursesList.get(0).getTechnology());
	}
	
	@Test
	public void testFindCoursesByTechnologyAndDurationBetweenSuccess() {
		courseRepo.save(course);
		List<Course> list = courseRepo.findCoursesByTechnologyAndDurationBetween("Testing",100L,800L);
		assertEquals(list.size(),coursesList.size());
		assertEquals(list.get(0).getTechnology(),coursesList.get(0).getTechnology());
		assertEquals(list.get(0).getDuration(),coursesList.get(0).getDuration());
	}
	
	@Test
	public void testFindCoursesByDurationBetweenSuccess() {
		courseRepo.save(course);
		List<Course> courses = courseRepo.findAll();
		List<Course> filteredcourses = courses.stream().filter(course -> course.getDuration() >= 100 && course.getDuration() <= 800).collect(Collectors.toList());
		List<Course> list = courseRepo.findCoursesByDurationBetween(100L,800L);
		assertEquals(list.size(),filteredcourses.size());
	}
	
	
}
