package com.tareas.api.controller;

import com.tareas.api.dto.PaginaResponseDTO;
import com.tareas.api.dto.TareaRequestDTO;
import com.tareas.api.dto.TareaResponseDTO;
import com.tareas.api.service.TareaService;

import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
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

    @GetMapping
    public ResponseEntity<PaginaResponseDTO<TareaResponseDTO>> verTodas(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(PaginaResponseDTO.de(servicio.verTodasLasTareas(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> verPorID(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<TareaResponseDTO> guardarTarea(@Valid @RequestBody TareaRequestDTO tarea) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.crearTarea(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> actualizarTarea(@PathVariable Long id,
            @Valid @RequestBody TareaRequestDTO tareaActualizada) {
        return ResponseEntity.ok(servicio.actualizarTarea(id, tareaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> completada(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.marcarComoCompletada(id));
    }
}