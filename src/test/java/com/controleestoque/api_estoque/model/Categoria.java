package com.controleestoque.api_estoque.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String nome;

    // Relacionamento 1:N (one to many)

    @OneToMany(mappedBy= "categoria", cascade = CascadeType.ALL)
    private List<Produto> produtos;

    //construtores getters e setters
    public Categoria(){}

    public Categoria(String nome, List<Produto> produtos){
        this.nome = nome;
        this.produtos = produtos;
    }

    public long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }

}
