package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.controleestoque.api_estoque.dto.VendaCreateDto;
import com.controleestoque.api_estoque.dto.ItemVendaDto;

import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.model.ItemVenda;
import com.controleestoque.api_estoque.model.Produto;
import com.controleestoque.api_estoque.model.Estoque;
import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.repository.ClienteRepository;
import com.controleestoque.api_estoque.repository.ProdutoRepository;
import com.controleestoque.api_estoque.repository.EstoqueRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    
    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    @GetMapping
    public List<Venda> getAllVendas(){
        return vendaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id){
        return vendaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Venda> createVenda(@RequestBody JsonNode root){
        // Accept two possible input shapes:
        // 1) { "clienteId": 1, "itens": [ { "produtoId": 4, "quantidade": 2 } ] }
        // 2) { "cliente": { "id": 1 }, "itemVenda": [ { "produto": { "id": 4 }, "quantidade": 2 } ] }
        Long clienteId = null;
        if (root.has("clienteId") && !root.get("clienteId").isNull()) {
            clienteId = root.get("clienteId").asLong();
        } else if (root.has("cliente") && root.get("cliente").has("id")) {
            clienteId = root.get("cliente").get("id").asLong();
        }

        java.util.List<ItemVendaDto> itensDto = new java.util.ArrayList<>();
        JsonNode itemsNode = null;
        if (root.has("itens")) itemsNode = root.get("itens");
        else if (root.has("itemVenda")) itemsNode = root.get("itemVenda");

        if (itemsNode != null && itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                Long produtoId = null;
                if (itemNode.has("produtoId") && !itemNode.get("produtoId").isNull()) {
                    produtoId = itemNode.get("produtoId").asLong();
                } else if (itemNode.has("produto") && itemNode.get("produto").has("id")) {
                    produtoId = itemNode.get("produto").get("id").asLong();
                }
                Integer quantidade = null;
                if (itemNode.has("quantidade") && !itemNode.get("quantidade").isNull()) {
                    quantidade = itemNode.get("quantidade").asInt();
                }
                if (produtoId != null && quantidade != null) {
                    itensDto.add(new ItemVendaDto(produtoId, quantidade));
                }
            }
        }

        VendaCreateDto dto = new VendaCreateDto();
        dto.setClienteId(clienteId);
        dto.setItens(itensDto);

        // Validate cliente
        if (dto.getClienteId() == null || dto.getClienteId() == 0L) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElse(null);
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validate and process itens
        if (dto.getItens() == null || dto.getItens().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Venda venda = new Venda();
        venda.setCliente(cliente);

        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
        java.util.Set<ItemVenda> processed = new java.util.HashSet<>();

        for (ItemVendaDto itemDto : dto.getItens()) {
            if (itemDto == null || itemDto.getProdutoId() == null || itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
                return ResponseEntity.badRequest().build();
            }

            Long produtoId = itemDto.getProdutoId();
            Produto produto = produtoRepository.findById(produtoId).orElse(null);
            if (produto == null) {
                return ResponseEntity.badRequest().build();
            }

            // Get estoque by produto
            Estoque estoque = estoqueRepository.findByProdutoId(produtoId).orElse(null);
            if (estoque == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
            if (estoque.getQuantidade() < itemDto.getQuantidade()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // Deduct stock
            estoque.setQuantidade(estoque.getQuantidade() - itemDto.getQuantidade());
            estoqueRepository.save(estoque);

            // Set item fields
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setVenda(venda);
            item.setQuantidade(itemDto.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());

            java.math.BigDecimal linha = produto.getPreco().multiply(new java.math.BigDecimal(itemDto.getQuantidade()));
            total = total.add(linha);
            processed.add(item);
        }

        venda.setItens(processed);
        venda.setTotal(total);

        Venda savedVenda = vendaRepository.save(venda);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> updateVenda(
        @PathVariable Long id, @RequestBody Venda vendaDetails){
            return vendaRepository.findById(id)
                .map(venda -> {
                    venda.setTotal(vendaDetails.getTotal());
                    venda.setData(vendaDetails.getData());
                    return ResponseEntity.ok(vendaRepository.save(venda));
                })
                .orElse(ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id){
        if(!vendaRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        vendaRepository.findById(id).ifPresent(venda -> {
            venda.setAtivo(false);
            vendaRepository.save(venda);
        });
        return ResponseEntity.noContent().build();
    }

}
