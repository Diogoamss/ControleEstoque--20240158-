package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Categoria;
import com.controleestoque.api_estoque.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> getAllCategorias(){
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id){
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria createCategoria(@RequestBody Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(
        @PathVariable Long id, @RequestBody Categoria categoriaDetails){
            return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaDetails.getNome());
                    return ResponseEntity.ok(categoriaRepository.save(categoria));
                })
                .orElse(ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){
        if(!categoriaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
