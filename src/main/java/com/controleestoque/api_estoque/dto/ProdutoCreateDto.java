package com.controleestoque.api_estoque.dto;

import java.math.BigDecimal;
import java.util.Set;

public class ProdutoCreateDto {
    
    private String nome;
    private BigDecimal preco;
    private Long categoriaId;
    private Set<Long> fornecedorIds;
    private Integer estoqueQuantidade;

    public ProdutoCreateDto() {}

    public ProdutoCreateDto(String nome, BigDecimal preco, Long categoriaId, Set<Long> fornecedorIds, Integer estoqueQuantidade) {
        this.nome = nome;
        this.preco = preco;
        this.categoriaId = categoriaId;
        this.fornecedorIds = fornecedorIds;
        this.estoqueQuantidade = estoqueQuantidade;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
    
    public Set<Long> getFornecedorIds() { return fornecedorIds; }
    public void setFornecedorIds(Set<Long> fornecedorIds) { this.fornecedorIds = fornecedorIds; }
    
    public Integer getEstoqueQuantidade() { return estoqueQuantidade; }
    public void setEstoqueQuantidade(Integer estoqueQuantidade) { this.estoqueQuantidade = estoqueQuantidade; }
}
