package com.tareas.api.dto;

import java.time.LocalDate;

public record TareaResponseDTO(
    Long id,
    String titulo,
    String descripcion,
    LocalDate fechaCreacion,
    LocalDate fechaActualizacion,
    boolean completada
) {}
