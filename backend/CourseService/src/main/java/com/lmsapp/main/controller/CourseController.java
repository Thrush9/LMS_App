package com.lmsapp.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmsapp.main.exception.CourseAlreadyExistsException;
import com.lmsapp.main.exception.CourseNotFoundException;
import com.lmsapp.main.model.Course;
import com.lmsapp.main.service.CourseService;

@CrossOrigin(origins = "http://iiht-lmsapp.s3-website.ap-south-1.amazonaws.com", maxAge = 3600)
@RestController
@RequestMapping("/api/v1.0/lms/courses")
public class CourseController {

	@Autowired
	CourseService courseService;
	
	public CourseController(CourseService courseService) {
	  this.courseService = courseService;	
	}
	
	@PostMapping("/add/{coursename}")
	public ResponseEntity<?> addCourse(@RequestBody Course courseObj,@PathVariable("coursename") String name) throws CourseAlreadyExistsException{
		try {
			Course addedData = courseService.addCourse(courseObj);
			return new ResponseEntity<Course>(addedData, HttpStatus.CREATED);
		}
		catch(CourseAlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAllCourses(){
		try {
			List<Course> list = courseService.getAllCourses();
			return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{company}")
	public ResponseEntity<?> getAllCoursesByCompany(@PathVariable("company") String company){
		try {
			List<Course> list = courseService.getAllCoursesByCompany(company);
			return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/info/{technology}")
	public ResponseEntity<?> getAllCoursesByTechnology(@PathVariable("technology") String technology){
		try {
			List<Course> list = courseService.getAllCoursesByTechnology(technology);
			return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/get/{technology}/{durationFromRange}/{durationToRange}")
	public ResponseEntity<?> getCoursesBySearch(@PathVariable(required = false) String technology, 
			@PathVariable(required = false) Long durationFromRange,
			@PathVariable(required = false) Long durationToRange){
		try {
			durationFromRange = (durationFromRange == null) ? 0 : durationFromRange;
			durationToRange = (durationToRange == null) ? 1000 : durationToRange;
			List<Course> list = courseService.getCoursesBySearch(technology,durationFromRange,durationToRange);
			return new ResponseEntity<List<Course>>(list, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{coursename}")
	public ResponseEntity<?> deleteCourse(@PathVariable("coursename") String coursename) throws CourseNotFoundException{
		try {
			System.out.println(coursename);
			Boolean status = courseService.deleteCourse(coursename);
			return new ResponseEntity<Boolean>(status , HttpStatus.OK);
		}
		catch(CourseNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/update/{courseId}")
	public ResponseEntity<?> updateCourse(@RequestBody Course courseObj, @PathVariable("courseId") Long courseId) throws CourseNotFoundException{
		try {
			Course updated = courseService.updateCourse(courseId, courseObj);
			return new ResponseEntity<Course>(updated, HttpStatus.OK);
		}
		catch(CourseNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
}
