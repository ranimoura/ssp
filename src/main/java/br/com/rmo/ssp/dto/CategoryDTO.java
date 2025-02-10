package br.com.rmo.ssp.dto;

import java.io.Serializable;

import br.com.rmo.ssp.entities.Category;

public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	public CategoryDTO() {

	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	// CONSTRUTOR QUE SERÁ UTILIZADO PELO CATEGORY-SERVICE:
	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
