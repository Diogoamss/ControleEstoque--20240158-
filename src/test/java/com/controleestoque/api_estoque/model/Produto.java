package com.controleestoque.api_estoque.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private BigDecimal preco;
    
    //Relacionament 1:1 One to One
    //Mapeamento: um produto tem um registro de estoque e vice-versa
    //"mappedBy" indica que a chave estrangeira está na classe estoque 
    // cascade.all: operações (como salvar) em produto afetam estoque
    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;

    // relacionamento N:1 Many to One 
    //Mapeamento: muitos produtos tem UMA categoria 
    //é o lado 'N' (Muitos), que cotem a chave estrangeira (FK)
    @ManyToOne(fetch = FetchType.LAZY)// LAZY : Carrega a categoria apenas quando for solicitado 
    @JoinColumn(name = "categoria_id", nullable= false) // Define a FK na tabela tb_produtos 
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "tb_produto_fornecedor", //nome da tebela de junção 
        joinColumns = @JoinColumn(name = "Produto_id"), // FK desta entidade na tabela de junçã
        inverseJoinColumns= @JoinColumn(name = "fornecedor_id") // Fk da outra entidade
    )
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

