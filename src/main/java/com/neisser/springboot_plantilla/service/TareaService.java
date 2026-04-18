package com.neisser.springboot_plantilla.service;

import com.neisser.springboot_plantilla.dto.TareaMapper;
import com.neisser.springboot_plantilla.dto.TareaRequestDTO;
import com.neisser.springboot_plantilla.dto.TareaResponseDTO;
import com.neisser.springboot_plantilla.exception.TareaNotFoundException;
import com.neisser.springboot_plantilla.model.Tarea;
import com.neisser.springboot_plantilla.repository.TareaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository repositorio;
    private final TareaMapper mapper;

    @Transactional(readOnly = true)
    public Page<TareaResponseDTO> verTodasLasTareas(Pageable pageable) {
        return repositorio.findAll(pageable)
                .map(mapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public TareaResponseDTO buscarPorID(Long id) {
        return repositorio.findById(id).map(mapper::toResponseDTO)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

    }

    @Transactional
    public TareaResponseDTO crearTarea(TareaRequestDTO tarea) {
        Tarea nuevaTarea = repositorio.save(mapper.toEntity(tarea));
        return mapper.toResponseDTO(nuevaTarea);
    }

    @Transactional
    public TareaResponseDTO actualizarTarea(Long id, TareaRequestDTO tarea) {

        Tarea existente = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        existente.setTitulo(tarea.getTitulo());
        existente.setDescripcion(tarea.getDescripcion());

        return mapper.toResponseDTO(existente);
    }

    @Transactional
    public TareaResponseDTO marcarComoCompletada(Long id) {
        Tarea existente = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        existente.setCompletada(true);

        return mapper.toResponseDTO(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        Tarea tarea = repositorio.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con ID: " + id));

        repositorio.delete(tarea);
    }
}