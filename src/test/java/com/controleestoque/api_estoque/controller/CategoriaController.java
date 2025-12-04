package com.controleestoque.api_estoque.controller;

import com.controleestoque.api_estoque.model.Categoria;
import com.controleestoque.api_estoque.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/categorias")//define o caminho para o controller
@RequiredArgsConstructor //injeta automaticamente o CategoriaRepository via constructor
public class CategoriaController {
    
    private final CategoriaRepository categoriaRepository;

    //get /api/categorias
    @GetMapping
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id){
        //busca a categoria pelo id orElse para retornar 404 se não encontrar
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //post /api/categorias
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//retorna codigo 201(created)
    public Categoria createCategoria(@RequestBody Categoria categoria){
        //salva uma nova categoria no banco de dados
        return categoriaRepository.save(categoria);
    }

    //put /api/categoria/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(
        @PathVariable Long id, @RequestBody Categoria categoriaDetails){
            //Tenta encontrar a categoria existente
            return categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaDetails.getNome());
                    return ResponseEntity.ok(categoriaRepository.save(categoria));
                })
                .orElse(ResponseEntity.notFound().build());
        }

    //delete /api/categoria/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id){
        //tenta encontrar e deletar
        if(!categoriaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //retorna codigo 204 (no content)
    }

}


