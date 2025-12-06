package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Categoria;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
    @Query("SELECT c FROM Categoria c WHERE c.ativo = true")
    List<Categoria> findAll();
    
    @Query("SELECT c FROM Categoria c WHERE c.id = ?1 AND c.ativo = true")
    Optional<Categoria> findById(Long id);
    
    @Query("SELECT c FROM Categoria c WHERE c.ativo = false ORDER BY c.id ASC")
    List<Categoria> findAllInactive();
}
