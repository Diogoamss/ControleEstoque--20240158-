package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Produto;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
    @Query("SELECT p FROM Produto p WHERE p.ativo = true")
    List<Produto> findAll();
    
    @Query("SELECT p FROM Produto p WHERE p.id = ?1 AND p.ativo = true")
    Optional<Produto> findById(Long id);
    
    @Query("SELECT p FROM Produto p WHERE p.ativo = false ORDER BY p.id ASC")
    List<Produto> findAllInactive();
}
