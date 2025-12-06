package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.Cliente;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    @Query("SELECT cl FROM Cliente cl WHERE cl.ativo = true")
    List<Cliente> findAll();
    
    @Query("SELECT cl FROM Cliente cl WHERE cl.id = ?1 AND cl.ativo = true")
    Optional<Cliente> findById(Long id);
    
    @Query("SELECT cl FROM Cliente cl WHERE cl.ativo = false ORDER BY cl.id ASC")
    List<Cliente> findAllInactive();
}