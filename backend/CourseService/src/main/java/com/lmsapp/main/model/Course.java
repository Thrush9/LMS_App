package com.lmsapp.main.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
public class Course {

	@Id
	private String id;
	
	private Long courseId;
	
	private String name;
	
	private String description;
	
	private String technology;
	
	private Long duration;
	
	private String url;
	
	private String companyId;

	public Course() {
		super();
	}

	public Course(String id,Long courseId, String name, String description, String technology, Long duration, String url, String companyId) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.name = name;
		this.description = description;
		this.technology = technology;
		this.duration = duration;
		this.url = url;
		this.companyId = companyId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", courseId=" + courseId + ", name=" + name + ", description=" + description
				+ ", technology=" + technology + ", duration=" + duration + ", url=" + url + ", companyId=" + companyId
				+ "]";
	}

	
	
}
