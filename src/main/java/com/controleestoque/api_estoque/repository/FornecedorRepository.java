package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Fornecedor;
import java.util.List;
import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{
    
    @Query("SELECT f FROM Fornecedor f WHERE f.ativo = true")
    List<Fornecedor> findAll();
    
    @Query("SELECT f FROM Fornecedor f WHERE f.id = ?1 AND f.ativo = true")
    Optional<Fornecedor> findById(Long id);
    
    @Query("SELECT f FROM Fornecedor f WHERE f.ativo = false ORDER BY f.id ASC")
    List<Fornecedor> findAllInactive();
}
