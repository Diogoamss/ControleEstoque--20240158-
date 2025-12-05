package com.controleestoque.api_estoque.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.controleestoque.api_estoque.dto.ClienteCreateDto;
import com.controleestoque.api_estoque.model.Cliente;

import com.controleestoque.api_estoque.model.Cliente;
import com.controleestoque.api_estoque.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    
    private final ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> getAllClientes(){
        return clienteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente createCliente(@Valid @RequestBody ClienteCreateDto dto){
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(
        @PathVariable Long id, @RequestBody Cliente clienteDetails){
            return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteDetails.getNome());
                    cliente.setEmail(clienteDetails.getEmail());
                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
