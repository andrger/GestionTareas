package com.gestordetareas.GestionDeTareas.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enum que representa el estado de una tarea")
public enum EstadoTarea {
    POR_HACER,
    EN_PROGRESO,
    COMPLETADA
}
