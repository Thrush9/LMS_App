package com.lmsapp.main.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.lmsapp.main.model.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, Long>{

	public Optional<Company> findCompanyByName(String name);
}
