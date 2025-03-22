package br.com.rmo.ssp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rmo.ssp.dto.CategoryDTO;
import br.com.rmo.ssp.dto.ProductDTO;
import br.com.rmo.ssp.entities.Category;
import br.com.rmo.ssp.entities.Product;
import br.com.rmo.ssp.repositories.CategoryRepository;
import br.com.rmo.ssp.repositories.ProductRepository;
import br.com.rmo.ssp.services.exceptions.DatabaseException;
import br.com.rmo.ssp.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable) {

		Page<Product> list = repository.findAll(pageable);

		// CONVERTENDO A LISTA DE PRODUTOS EM CATEGORYDTO VIA LAMBDA
		return list.map(x -> new ProductDTO(x));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		// IMPLEMENTANDO O OPTIONAL POR NECESSIDADE DO FRAMEWORK
		Optional<Product> obj = repository.findById(id);

		// TRABALHANDO A CHAMADA DE EXCEÇÃO:
		Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));

		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product product = new Product();

		copyDtoToProduct(dto, product);

		product = repository.save(product);

		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		try {

			Product product = repository.getReferenceById(id);

			copyDtoToProduct(dto, product);

			product = repository.save(product);

			return new ProductDTO(product);

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

	private void copyDtoToProduct(ProductDTO dto, Product product) {

		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());
		product.setDate(dto.getDate());

		// LIMPANDO AS CATEGORIAS QUE POSSAM EXISTIR NA ENTIDADE
		product.getCategories().clear();

		for (CategoryDTO catDto : dto.getCategories()) {

			// BUSCANDO AS CATEGORIAS REFERENTES AO PRODUTO, PERCORRENDO SUA LISTA ATRAVÉS
			// DO REPOSITORY
			Category category = categoryRepository.getReferenceById(catDto.getId());

			// INSERINDO AS CATEGORIAS:
			product.getCategories().add(category);
		}

	}

}
