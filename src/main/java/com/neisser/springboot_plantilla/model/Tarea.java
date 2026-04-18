package com.neisser.springboot_plantilla.model;

import jakarta.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "tareas")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @CreationTimestamp
    @Column(name="fecha_creacion", nullable=false, updatable=false)
    private LocalDate fechaCreacion;

    @UpdateTimestamp
    @Column(name="fecha_actualizacion")
    private LocalDate fechaActualizacion;

    @Column(name = "completada", nullable = false)
    private boolean completada=false;
}