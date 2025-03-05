package com.example.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "epd_data")
public class Documents {
	@Id
	private String id;
	private String uuid;
	private String name;
	private List<Categories> categories;
	private List<String> tags;

	public Documents(String id, String uuid, String name, List<Categories> categories, List<String> tags) {
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.categories = categories;
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Categories> getCategories() {
		return categories;
	}

	public void setCategories(List<Categories> categories) {
		this.categories = categories;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}