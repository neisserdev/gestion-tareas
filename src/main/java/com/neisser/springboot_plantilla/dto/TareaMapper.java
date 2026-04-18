package com.neisser.springboot_plantilla.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.neisser.springboot_plantilla.model.Tarea;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    @Mapping(target = "completada", ignore = true)
     Tarea toEntity(TareaRequestDTO requestDTO);

    TareaResponseDTO toResponseDTO(Tarea tarea);
}