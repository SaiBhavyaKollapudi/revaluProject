package com.example.docsApplication;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.models.Documents;

@Repository
interface DocumentRepository extends MongoRepository<Documents, String> {
	List<Documents> findByCategoriesName(String name);

	List<Documents> findByTagsContaining(String tag);

	List<Documents> findAll();

	@Query("{ 'tags': { '$exists': false } }")
	List<Documents> findByTagsNull();

}
