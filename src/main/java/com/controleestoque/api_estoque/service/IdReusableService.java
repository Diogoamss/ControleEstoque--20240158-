package com.controleestoque.api_estoque.service;

import org.springframework.stereotype.Service;
import com.controleestoque.api_estoque.model.*;
import com.controleestoque.api_estoque.repository.*;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdReusableService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;
    private final FornecedorRepository fornecedorRepository;
    private final EstoqueRepository estoqueRepository;
    private final VendaRepository vendaRepository;
    private final ItemVendaRepository itemVendaRepository;

    /**
     * Encontra o próximo ID disponível para reutilização (inativo).
     * Se não houver IDs deletados, retorna null (banco gera novo ID automaticamente)
     */
    public Optional<Long> getNextAvailableId(String entityType) {
        if ("categoria".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(categoriaRepository.findAllInactive());
        } else if ("produto".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(produtoRepository.findAllInactive());
        } else if ("cliente".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(clienteRepository.findAllInactive());
        } else if ("fornecedor".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(fornecedorRepository.findAllInactive());
        } else if ("estoque".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(estoqueRepository.findAllInactive());
        } else if ("venda".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(vendaRepository.findAllInactive());
        } else if ("itemvenda".equalsIgnoreCase(entityType)) {
            return findFirstInactiveId(itemVendaRepository.findAllInactive());
        }
        return Optional.empty();
    }

    private Optional<Long> findFirstInactiveId(java.util.List<? extends BaseEntity> inactiveEntities) {
        if (inactiveEntities.isEmpty()) {
            return Optional.empty();
        }
        // Ordena por ID e retorna o primeiro
        return inactiveEntities.stream()
            .map(e -> e.getId())
            .sorted()
            .findFirst();
    }
}
