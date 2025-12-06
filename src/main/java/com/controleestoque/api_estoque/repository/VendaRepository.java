package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Venda;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>{

    @Query("SELECT v FROM Venda v WHERE v.ativo = true")
    List<Venda> findAll();
    
    @Query("SELECT v FROM Venda v WHERE v.id = ?1 AND v.ativo = true")
    Optional<Venda> findById(Long id);
    
    @Query("SELECT v FROM Venda v WHERE v.ativo = false ORDER BY v.id ASC")
    List<Venda> findAllInactive();
}