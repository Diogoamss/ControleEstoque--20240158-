package com.controleestoque.api_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controleestoque.api_estoque.model.ItemVenda;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long>{

}