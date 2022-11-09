package com.lmsapp.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmsapp.main.exception.CompanyNotFoundException;
import com.lmsapp.main.exception.CourseAlreadyExistsException;
import com.lmsapp.main.exception.CourseNotFoundException;
import com.lmsapp.main.model.Company;
import com.lmsapp.main.model.Course;
import com.lmsapp.main.repository.CompanyRepository;
import com.lmsapp.main.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService{

	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	public CourseServiceImpl(CourseRepository courseRepo,CompanyRepository companyRepo) {
		this.courseRepo = courseRepo;
		this.companyRepo = companyRepo;
	}

	@Override
	public Course addCourse(Course course) throws CourseAlreadyExistsException {
		Optional<Course> checkexisting = courseRepo.findCourseByNameandCourseId(course.getName(), course.getCourseId());
		if(!checkexisting.isEmpty())
			throw new CourseAlreadyExistsException("Course already exists with name : " + course.getName());
		Course added = courseRepo.save(course);
		return added;
	}

	@Override
	public List<Course> getAllCourses() {
		List<Course> coursesList = new ArrayList<>();
		coursesList = courseRepo.findAll();
		return coursesList;
	}

	@Override
	public List<Course> getAllCoursesByCompany(String company) throws CompanyNotFoundException {
		List<Course> coursesList = new ArrayList<>();
		Optional<Company> companyOpt= companyRepo.findCompanyByName(company);
		if(companyOpt.isEmpty())
			throw new CompanyNotFoundException("No Course exists with Company : " + company);
		Company companyObj = companyOpt.get();
		coursesList = courseRepo.findCoursesByCompanyId(companyObj.getId());
		return coursesList;
	}

	@Override
	public List<Course> getAllCoursesByTechnology(String technology) {
		List<Course> coursesList = new ArrayList<>();
		coursesList = courseRepo.findCoursesByTechnology(technology);
		return coursesList;
	}

	@Override
	public Course updateCourse(Long courseId, Course course) throws CourseNotFoundException {
		Optional<Course> oldCourseOpt = courseRepo.findCourseByCourseId(courseId);
		if(oldCourseOpt.isEmpty())
			throw new CourseNotFoundException("No Course exists with Id : " + courseId);
		Course oldCourse = oldCourseOpt.get();
		oldCourse.setCourseId(course.getCourseId());
		oldCourse.setName(course.getName());
		oldCourse.setDescription(course.getDescription());
		oldCourse.setDuration(course.getDuration());
		oldCourse.setTechnology(course.getTechnology());
		oldCourse.setUrl(course.getUrl());
		Course added = courseRepo.save(oldCourse);
		return added;
	}

	@Override
	public Boolean deleteCourse(String coursename) throws CourseNotFoundException {
		Optional<Course> checkexisting = courseRepo.findByName(coursename);
		if(checkexisting.isEmpty())
			throw new CourseNotFoundException("No Course exists with Name : " + coursename);
		courseRepo.delete(checkexisting.get());
		return true;
	}

	@Override
	public List<Course> getCoursesBySearch(String technology, Long durationFromRange, Long durationToRange) {
		List<Course> coursesList = new ArrayList<>();
		if(!technology.equals("none")) 
			coursesList = courseRepo.findCoursesByTechnologyAndDurationBetween(technology, durationFromRange, durationToRange);
		else 
			coursesList = courseRepo.findCoursesByDurationBetween(durationFromRange, durationToRange);
		return coursesList;
	}
}
