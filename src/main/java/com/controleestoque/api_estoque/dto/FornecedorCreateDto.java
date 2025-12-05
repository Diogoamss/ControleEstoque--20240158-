package com.controleestoque.api_estoque.dto;

import javax.validation.constraints.NotBlank;

public class FornecedorCreateDto {

    @NotBlank
    private String nome;

    public FornecedorCreateDto() {}

    public FornecedorCreateDto(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
