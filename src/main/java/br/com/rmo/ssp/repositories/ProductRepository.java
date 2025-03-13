package br.com.rmo.ssp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rmo.ssp.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
