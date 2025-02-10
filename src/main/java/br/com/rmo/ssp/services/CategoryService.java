package br.com.rmo.ssp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rmo.ssp.dto.CategoryDTO;
import br.com.rmo.ssp.entities.Category;
import br.com.rmo.ssp.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {

		List<Category> list = repository.findAll();

		// CONVERTENDO A LISTA DE CATEGORIES EM CATEGORYDTO VIA LAMBDA:
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

	}

}
