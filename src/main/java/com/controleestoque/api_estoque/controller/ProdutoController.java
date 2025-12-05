package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;
import com.controleestoque.api_estoque.repository.EstoqueRepository;
import com.controleestoque.api_estoque.dto.ProdutoCreateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Produto> getAllProdutos(){
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> createProduto(@RequestBody ProdutoCreateDto dto){
        // Validar entrada
        if (dto.getNome() == null || dto.getNome().trim().isEmpty() || 
            dto.getPreco() == null || dto.getCategoriaId() == null || 
            dto.getCategoriaId() == 0L) {
            return ResponseEntity.badRequest().build();
        }

        // Buscar e validar categoria
        com.controleestoque.api_estoque.model.Categoria categoria = 
            categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
        if (categoria == null) {
            return ResponseEntity.badRequest().build();
        }

        // Criar produto
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(categoria);

        // Resolver fornecedores
        if (dto.getFornecedorIds() != null && !dto.getFornecedorIds().isEmpty()) {
            java.util.Set<com.controleestoque.api_estoque.model.Fornecedor> fornecedores = new java.util.HashSet<>();
            dto.getFornecedorIds().forEach(fornecedorId -> {
                fornecedorRepository.findById(fornecedorId).ifPresent(fornecedores::add);
            });
            produto.setFornecedores(fornecedores);
        }

        // Salvar produto
        Produto savedProduto = produtoRepository.save(produto);

        // Criar estoque se quantidade foi fornecida
        if (dto.getEstoqueQuantidade() != null && dto.getEstoqueQuantidade() > 0) {
            com.controleestoque.api_estoque.model.Estoque estoque = new com.controleestoque.api_estoque.model.Estoque();
            estoque.setQuantidade(dto.getEstoqueQuantidade());
            // Reload produto from DB to ensure it's fully managed
            Produto managedProduto = produtoRepository.findById(savedProduto.getId()).orElse(savedProduto);
            estoque.setProduto(managedProduto);
            estoqueRepository.save(estoque);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody ProdutoCreateDto dto){
        return produtoRepository.findById(id)
            .map(produto -> {
                if (dto.getNome() != null && !dto.getNome().trim().isEmpty()) {
                    produto.setNome(dto.getNome());
                }
                if (dto.getPreco() != null) {
                    produto.setPreco(dto.getPreco());
                }
                if (dto.getCategoriaId() != null && dto.getCategoriaId() > 0) {
                    categoriaRepository.findById(dto.getCategoriaId())
                        .ifPresent(produto::setCategoria);
                }
                Produto updatedProduto = produtoRepository.save(produto);
                return ResponseEntity.ok(updatedProduto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        if (!produtoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
