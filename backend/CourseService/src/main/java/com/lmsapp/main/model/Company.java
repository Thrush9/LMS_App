package com.lmsapp.main.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
public class Company {
	
	@Id
	private String id;
	
	private String type;
	
	private String name;
	
	private String email;
	
	private String poc;
	
	private String contact;
	
	public Company() {
		super();
	}
	
	public Company(String id, String type, String name, String email,String poc,String contact) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.email = email;
		this.poc = poc;
		this.contact = contact;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPoc() {
		return poc;
	}

	public void setPoc(String poc) {
		this.poc = poc;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", type=" + type + ", name=" + name + ", email=" + email + ", poc=" + poc
				+ ", contact=" + contact + "]";
	}

	
}
