package com.controleestoque.api_estoque.model;

import javax.persistence.*;

@Entity
@Table(name = "tb_estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false )
    private Produto produto;

    public Estoque() {}
    
    public Estoque(Integer quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }
}
