package com.neisser.springboot_plantilla.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TareaResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
    private boolean completada;
}
