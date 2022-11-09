package com.lmsapp.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.lmsapp.main.model.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, Long>{
	
	public Optional<Course> findByName(String name);

	@Query(value = "{$and: [{'name': ?0 }, {'courseId' : ?1 } ]}")
	public Optional<Course> findCourseByNameandCourseId(String name, Long courseId);
	
	public Optional<Course> findCourseByCourseId(Long courseId);
	
	public List<Course> findCoursesByCompanyId(String companyId);
	
	public List<Course> findCoursesByTechnology(String technology);
	
	@Query(value = "{$and: [{'technology': ?0 }, {'duration' : { $gte : ?1, $lte : ?2} } ]}")
	public List<Course> findCoursesByTechnologyAndDurationBetween(String Technology, Long durationFromRange, Long durationToRange);

	@Query(value = "{'duration' : { $gte : ?0, $lte : ?1} } ")
	public List<Course> findCoursesByDurationBetween(Long durationFromRange, Long durationToRange);
	
}
