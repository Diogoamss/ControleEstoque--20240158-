package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.EstoqueRepository;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/estoques")
@RequiredArgsConstructor
public class EstoqueController {
    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Estoque> getAllEstoques(){
        return estoqueRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> getEstoqueById(@PathVariable Long id){
        return estoqueRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> updateEstoques(
        @PathVariable Long id, @RequestBody Estoque estoqueDetails){
            return estoqueRepository.findById(id)
                    .map(estoque -> {
                        estoque.setProduto( estoqueDetails.getProduto());
                        estoque.setQuantidade(estoqueDetails.getQuantidade());
                        Estoque updatedEstoque = estoqueRepository.save(estoque);
                        return ResponseEntity.ok(updatedEstoque);
                    }).orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEstoque (@PathVariable Long id){
            if(!estoqueRepository.existsById(id)){
                return ResponseEntity.notFound().build();
            }
            estoqueRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}
