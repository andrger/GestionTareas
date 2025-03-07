package com.gestordetareas.GestionDeTareas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa una tarea")
public class Tarea {

    @Id
    @Schema(description = "Identificador único de la tarea", example = "1")
    private Long id;

    @Schema(description = "Título de la tarea", example = "Revisión de código")
    private String titulo;

    @Schema(description = "Descripción de la tarea", example = "Revisar el código del último commit")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de la tarea", example = "POR_HACER")
    private EstadoTarea estado;

}

