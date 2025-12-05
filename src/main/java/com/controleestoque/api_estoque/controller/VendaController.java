package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.controleestoque.api_estoque.model.Venda;
import com.controleestoque.api_estoque.repository.VendaRepository;
import com.controleestoque.api_estoque.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vendas")
@RequiredArgsConstructor
public class VendaController {
    
    private final VendaRepository vendaRepository;
    private final ClienteRepository clienteRepository;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Venda> createVenda(@RequestBody Venda venda){
        if (venda.getCliente() == null || venda.getCliente().getId() == 0){
            return ResponseEntity.badRequest().build();
        }
        clienteRepository.findById(venda.getCliente().getId())
                .ifPresent(venda::setCliente);
        
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
        vendaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
