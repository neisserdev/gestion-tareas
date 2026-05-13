package com.tareas.api.service;

import com.tareas.api.dto.TareaMapper;
import com.tareas.api.dto.TareaRequestDTO;
import com.tareas.api.dto.TareaResponseDTO;
import com.tareas.api.exception.TareaNotFoundException;
import com.tareas.api.model.Tarea;
import com.tareas.api.repository.TareaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository repositorio;
    private final TareaMapper mapper;

    @Cacheable(value = "tareas_list", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    @Transactional(readOnly = true)
    public Page<TareaResponseDTO> verTodasLasTareas(Pageable pageable) {
        return repositorio.findAll(pageable)
                .map(mapper::toResponseDTO);
    }

    @Cacheable(value = "tareas_id", key = "#id")
    @Transactional(readOnly = true)
    public TareaResponseDTO buscarPorID(Long id) {
        return repositorio.findById(id).map(mapper::toResponseDTO)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

    }

    @CacheEvict(value = "tareas_list", allEntries = true)
    @Transactional
    public TareaResponseDTO crearTarea(TareaRequestDTO tarea) {
        Tarea nuevaTarea = repositorio.save(mapper.toEntity(tarea));
        return mapper.toResponseDTO(nuevaTarea);
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @Transactional
    public TareaResponseDTO actualizarTarea(Long id, TareaRequestDTO tarea) {

        Tarea existente = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        existente.setTitulo(tarea.titulo());
        existente.setDescripcion(tarea.descripcion());

        return mapper.toResponseDTO(existente);
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @Transactional
    public TareaResponseDTO marcarComoCompletada(Long id) {
        Tarea existente = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        existente.setCompletada(true);

        return mapper.toResponseDTO(existente);
    }

    @CacheEvict(value = { "tareas_list", "tareas_id" }, allEntries = true)
    @Transactional
    public void eliminar(Long id) {
        Tarea tarea = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        repositorio.delete(tarea);
    }
}