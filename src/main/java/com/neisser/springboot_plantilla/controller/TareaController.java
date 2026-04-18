package com.neisser.springboot_plantilla.controller;

import com.neisser.springboot_plantilla.dto.PaginaResponseDTO;
import com.neisser.springboot_plantilla.dto.TareaRequestDTO;
import com.neisser.springboot_plantilla.dto.TareaResponseDTO;
import com.neisser.springboot_plantilla.service.TareaService;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaService servicio;

    @Cacheable(value = "tareas_list", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @GetMapping
    public ResponseEntity<PaginaResponseDTO<TareaResponseDTO>> verTodas(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(PaginaResponseDTO.de(servicio.verTodasLasTareas(pageable)));
    }

    @Cacheable(value = "tareas_id", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> verPorID(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscarPorID(id));
    }

    @CacheEvict(value = "tareas_list", allEntries = true)
    @PostMapping
    public ResponseEntity<TareaResponseDTO> guardarTarea(@Valid @RequestBody TareaRequestDTO tarea) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crearTarea(tarea));
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> actualizarTarea(@PathVariable Long id,
            @Valid @RequestBody TareaRequestDTO tareaActualizada) {
        return ResponseEntity.ok(servicio.actualizarTarea(id, tareaActualizada));
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> completada(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.marcarComoCompletada(id));
    }
}