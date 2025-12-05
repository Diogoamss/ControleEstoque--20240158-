package com.controleestoque.api_estoque.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ClienteCreateDto {

    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    public ClienteCreateDto() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
