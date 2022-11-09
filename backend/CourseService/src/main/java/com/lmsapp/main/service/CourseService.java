package com.lmsapp.main.service;

import java.util.List;

import com.lmsapp.main.exception.CompanyNotFoundException;
import com.lmsapp.main.exception.CourseAlreadyExistsException;
import com.lmsapp.main.exception.CourseNotFoundException;
import com.lmsapp.main.model.Course;

public interface CourseService {
 
	public Course addCourse(Course course) throws CourseAlreadyExistsException;
	
	public List<Course> getAllCourses();
	
	public List<Course> getAllCoursesByCompany(String company) throws CompanyNotFoundException;
	
	public List<Course> getAllCoursesByTechnology(String technology);
	
	public Course updateCourse(Long courseId, Course course) throws CourseNotFoundException;
	
	public Boolean deleteCourse(String coursename) throws CourseNotFoundException;

	public List<Course> getCoursesBySearch(String technology, Long durationFromRange, Long durationToRange);
}
