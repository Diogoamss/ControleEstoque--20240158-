package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Estoque;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	
	@Query("SELECT e FROM Estoque e WHERE e.ativo = true")
	List<Estoque> findAll();
	
	@Query("SELECT e FROM Estoque e WHERE e.id = ?1 AND e.ativo = true")
	Optional<Estoque> findById(Long id);
	
	@Query("SELECT e FROM Estoque e WHERE e.ativo = false ORDER BY e.id ASC")
	List<Estoque> findAllInactive();
	
	java.util.Optional<Estoque> findByProdutoId(Long produtoId);
}