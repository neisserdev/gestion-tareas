package com.tareas.api.dto;

import java.util.List;

import org.springframework.data.domain.Page;

public record PaginaResponseDTO<T>(
    List<T> datos,
    int pagina,
    int tamano,
    long totalElementos,
    int totalPaginas
) {
    public static <T> PaginaResponseDTO<T> de(Page<T> pagina) {
        return new PaginaResponseDTO<>(
            pagina.getContent(),
            pagina.getNumber(),
            pagina.getSize(),
            pagina.getTotalElements(),
            pagina.getTotalPages()
        );
    }
}