package com.zavala.cursoSpring.repositories;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zavala.cursoSpring.domain.Categoria;
import com.zavala.cursoSpring.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
    //@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias ")
    //Page<Produto> search( @Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

	//opção utilizando nomes padrão do framework
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn( String nome, List<Categoria> categorias, Pageable pageRequest );
}
