package br.com.rmo.ssp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rmo.ssp.dto.CategoryDTO;
import br.com.rmo.ssp.entities.Category;
import br.com.rmo.ssp.repositories.CategoryRepository;
import br.com.rmo.ssp.services.exceptions.DatabaseException;
import br.com.rmo.ssp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {

		Page<Category> list = repository.findAll(pageRequest);

		// CONVERTENDO A LISTA DE CATEGORIES EM CATEGORYDTO VIA LAMBDA
		return list.map(x -> new CategoryDTO(x));

	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {

		// IMPLEMENTANDO O OPTIONAL POR NECESSIDADE DO FRAMEWORK
		Optional<Category> obj = repository.findById(id);

		// TRABALHANDO A CHAMADA DE EXCEÇÃO:
		Category category = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));

		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {

		Category category = new Category();
		category.setName(dto.getName());
		category = repository.save(category);

		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {

		try {

			Category category = repository.getReferenceById(id);
			category.setName(dto.getName());
			category = repository.save(category);

			return new CategoryDTO(category);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not fount: " + id);

		}
	}

	public void delete(Long id) {

		try {

			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not fount: " + id);

		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}

	}

}
