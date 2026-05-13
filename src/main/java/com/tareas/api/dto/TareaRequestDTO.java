package com.tareas.api.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record TareaRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    String titulo,
    
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    String descripcion
) {}
