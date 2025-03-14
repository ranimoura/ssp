package br.com.rmo.ssp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rmo.ssp.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
