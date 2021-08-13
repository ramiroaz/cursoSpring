package com.zavala.cursoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zavala.cursoSpring.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	
	
}
