package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.controleestoque.api_estoque.dto.FornecedorCreateDto;

import com.controleestoque.api_estoque.model.Fornecedor;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {
    
    private final FornecedorRepository fornecedorRepository;

    @GetMapping
    public List<Fornecedor> getAllFornecedores(){
        return fornecedorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id){
        return fornecedorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Fornecedor criarFornecedor(@Valid @RequestBody FornecedorCreateDto fornecedorDto){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(fornecedorDto.getNome());
        return fornecedorRepository.save(fornecedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(
        @PathVariable Long id, @RequestBody Fornecedor fornecedorDetails){
            return fornecedorRepository.findById(id)
                    .map(fornecedor -> {
                        fornecedor.setNome(fornecedorDetails.getNome());
                        Fornecedor updatedFornecedor = fornecedorRepository.save(fornecedor);
                        return ResponseEntity.ok(updatedFornecedor);
                    })
                    .orElse(ResponseEntity.notFound().build());
        }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id){
        if (!fornecedorRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        fornecedorRepository.findById(id).ifPresent(fornecedor -> {
            fornecedor.setAtivo(false);
            fornecedorRepository.save(fornecedor);
        });
        return ResponseEntity.noContent().build();
    }
}
