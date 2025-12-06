package com.controleestoque.api_estoque.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/debug")
@RequiredArgsConstructor
public class DebugController {

    private final RequestMappingHandlerMapping mapping;

    @GetMapping("/mappings")
    public List<Map<String, Object>> mappings() {
        return mapping.getHandlerMethods().entrySet().stream().map(e -> {
            RequestMappingInfo info = e.getKey();
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("patterns", info.getPatternsCondition().getPatterns());
            m.put("methods", info.getMethodsCondition().getMethods());
            m.put("consumes", info.getConsumesCondition().getConsumableMediaTypes());
            m.put("produces", info.getProducesCondition().getProducibleMediaTypes());
            return m;
        }).collect(Collectors.toList());
    }
}
