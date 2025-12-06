package com.controleestoque.api_estoque.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Categoria;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.service.IdReusableService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    
    private final CategoriaRepository categoriaRepository;
    private final IdReusableService idReusableService;

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria createCategoria(@RequestBody Categoria categoria){
        // Tentar reutilizar ID deletado
        Optional<Long> reuseId = idReusableService.getNextAvailableId("categoria");
        if (reuseId.isPresent()) {
            Long idToReuse = reuseId.get();
            categoria.setId(idToReuse);
            categoria.setAtivo(true);
            categoria.setNome(categoria.getNome() != null ? categoria.getNome() : "");
            return categoriaRepository.save(categoria);
        }
        // Senão, deixar o banco gerar novo ID (não setar ID)
        categoria.setAtivo(true);
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
        categoriaRepository.findById(id).ifPresent(categoria -> {
            categoria.setAtivo(false);
            categoriaRepository.save(categoria);
        });
        return ResponseEntity.noContent().build();
    }

}
