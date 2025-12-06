package com.controleestoque.api_estoque.model;

import javax.persistence.*;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tb_fornecedores")
public class Fornecedor extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "fornecedores")
    @JsonBackReference("produto-fornecedor")
    private Set<Produto> produtos;

    public Fornecedor(){}

    public Fornecedor(String nome, Set<Produto> produtos){
        this.nome = nome;
        this.produtos = produtos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Set<Produto> getProdutos() { return produtos; }
    public void setProdutos(Set<Produto> produtos) { this.produtos = produtos; }

}