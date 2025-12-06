package com.controleestoque.api_estoque.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    protected Boolean ativo = true;
    
    // Subclasses devem ter um campo @Id
    public abstract Long getId();

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
