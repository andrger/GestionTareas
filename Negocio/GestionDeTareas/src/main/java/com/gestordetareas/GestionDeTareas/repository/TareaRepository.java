package com.gestordetareas.GestionDeTareas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestordetareas.GestionDeTareas.model.Tarea;


@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
}

