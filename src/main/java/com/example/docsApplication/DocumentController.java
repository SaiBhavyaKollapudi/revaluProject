package com.example.docsApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.ResourceNotFoundException;
import com.example.models.Documents;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
class DocumentController {
	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	private final DocumentRepository repository;

	public DocumentController(DocumentRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/show_all")
	public List<Documents> getAllDocuments() {
		logger.info("Fetching all documents");
		return repository.findAll();
	}

	@GetMapping("/categories/{categoryName}")
	public List<Documents> getByCategory(@PathVariable("categoryName") String categoryName) {
		logger.info("Fetching documents for category: " + categoryName);
		List<Documents> result = repository.findByCategoriesName(categoryName);
		if (result.isEmpty()) {
			String errorMessage = "No documents found for category: " + categoryName;
			logger.warn(errorMessage);
			throw new ResourceNotFoundException(errorMessage);
		}
		logger.info("Fetched " + result.size() + " documents for category: " + categoryName);
		return result;
	}

	@GetMapping("/tags/{tagName}")
	public List<Documents> getByTag(@PathVariable(value = "tagName", required = false) String tagName) {
		logger.info("Fetching documents for tag: " + tagName);
		List<Documents> result;
		// Handle null or empty tagName
		if (tagName != null && !tagName.trim().isEmpty()) {
			result = repository.findByTagsContaining(tagName);
			logger.info("Searching for documents with tag: " + tagName);
		} else {
			result = repository.findByTagsNull();
			logger.info("No tag provided or empty tag, fetching documents where tags are null...");
		}
		if (result.isEmpty()) {
			String errorMessage = (tagName != null && !tagName.trim().isEmpty())
					? "No documents found for tag: " + tagName
					: "No documents found where tags are null";
			logger.warn(errorMessage);
			throw new ResourceNotFoundException(errorMessage);
		}
		logger.info("Fetched " + result.size() + " documents for tag: " + tagName);
		return result;
	}

	// Exception handler for ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException(ResourceNotFoundException e) {
		logger.warn(e.getMessage());
		return e.getMessage();
	}
}
