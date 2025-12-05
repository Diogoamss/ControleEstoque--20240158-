package com.controleestoque.api_estoque.dto;

import java.util.List;

public class VendaCreateDto {
    
    private Long clienteId;
    private List<ItemVendaDto> itens;

    public VendaCreateDto() {}

    public VendaCreateDto(Long clienteId, List<ItemVendaDto> itens) {
        this.clienteId = clienteId;
        this.itens = itens;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    
    public List<ItemVendaDto> getItens() { return itens; }
    public void setItens(List<ItemVendaDto> itens) { this.itens = itens; }
}
