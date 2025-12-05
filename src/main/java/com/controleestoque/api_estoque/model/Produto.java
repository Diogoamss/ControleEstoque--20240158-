package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private BigDecimal preco;
    
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("produto-estoque")
    private Estoque estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable= false)
    @JsonBackReference("categoria-produto")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "tb_produto_fornecedor",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns= @JoinColumn(name = "fornecedor_id")
    )
    @JsonManagedReference("produto-fornecedor")
    private Set<Fornecedor> fornecedores;


    public Produto(){}

    public Produto(String nome, BigDecimal preco, Estoque estoque, Categoria categoria,
        Set<Fornecedor> fornecedores){
            this.nome = nome;
            this.preco = preco;
            this.estoque = estoque;
            this.categoria =categoria;
            this.fornecedores = fornecedores;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public BigDecimal getPreco() { return preco; }
        public void setPreco(BigDecimal preco) { this.preco = preco;}
        public Estoque getEstoque() { return estoque; }
        public void setEstoque(Estoque estoque) { this.estoque = estoque; }
        public Categoria getCategoria() {return categoria; }
        public void setCategoria(Categoria categoria ) { this.categoria = categoria; }
        public Set<Fornecedor> getFornecedores() { return fornecedores; }
        public void setFornecedores(Set<Fornecedor> fornecedores) { this.fornecedores = fornecedores; } 

}
