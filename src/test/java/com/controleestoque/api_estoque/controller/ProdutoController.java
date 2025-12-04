package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

import com.controleestoque.api_estoque.repository.CategoriaRepository;
import com.controleestoque.api_estoque.repository.FornecedorRepository;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    //estoque é geralmente manipulado via produto ou separadamente

    //GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos(){
        return produtoRepository.findAll();
    }

    //get api/produtos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getCategoriaById(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST /api/produtos
    //neste metodo, assumimos que acategoria e os fornecedores já existem
    // e seus ids são passados no corpo da requisição (ProdutoDTO seria o ideal aqui)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto){
        // 1 gerenciamento do 1:N (categoria)
        //a categoria deve ser buscada para grantir que existe e estar no contexto de persistencia
        if (produto.getCategoria() == null || produto.getCategoria().getId() == 0){
            return ResponseEntity.badRequest().build();// Categoria é obrigatoria
        }
        categoriaRepository.findById(produto.getCategoria().getId())
                .ifPresent(produto::setCategoria); //associa a categoria gerenciada
        
        //2. Gerenciamento do N:M (Fornecedores)
        // busca todos os fornecedores pelos IDs fornecedores
        if(produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()){
            //cria um set para armazenar os forncedores gerenciados
            produto.getFornecedores().clear();
        }

        //aqui em um projeto real, voce buscaria os fornecedores um por um
        //ou usando um metodo customizado do repositorio.
        //exemplo simplificado
        if(produto.getFornecedores() != null) {
            produto.getFornecedores().forEach(fornecedor -> {
                fornecedorRepository.findById(fornecedor.getId())
                    .ifPresent(produto.getFornecedores()::add); //adiciona o fornecedor gerenciado
            });
        }

        //3. salva o produto (e o estoque, se o cascade estiver configurado)
        Produto savedProduto = produtoRepository.save(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }
 

    //put /api/produtos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails){
        //tenta encontrar o produto existente
        return produtoRepository.findById(id)
            .map(produto -> {
                //atualiza os dados do produto encontrada
                produto.setNome(produtoDetails.getNome());
                Produto updatedProduto = produtoRepository.save(produto);
                return ResponseEntity.ok(updatedProduto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    //delete /api/produtos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        //tenta encontrar e deletar
        if (!produtoRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // retorna codigo 204 (no content)
    }
}