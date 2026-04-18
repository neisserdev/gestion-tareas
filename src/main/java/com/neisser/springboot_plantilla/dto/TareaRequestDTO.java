package com.neisser.springboot_plantilla.dto;

import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TareaRequestDTO {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String titulo;
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String descripcion;
}
