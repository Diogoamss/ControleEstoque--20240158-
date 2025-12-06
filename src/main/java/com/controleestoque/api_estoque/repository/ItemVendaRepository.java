package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.ItemVenda;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{

    @Query("SELECT iv FROM ItemVenda iv WHERE iv.ativo = true")
    List<ItemVenda> findAll();
    
    @Query("SELECT iv FROM ItemVenda iv WHERE iv.id = ?1 AND iv.ativo = true")
    Optional<ItemVenda> findById(Long id);
    
    @Query("SELECT iv FROM ItemVenda iv WHERE iv.ativo = false ORDER BY iv.id ASC")
    List<ItemVenda> findAllInactive();
}