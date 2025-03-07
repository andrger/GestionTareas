package com.gestordetareas.GestionDeTareas.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import com.gestordetareas.GestionDeTareas.model.Tarea;
import com.gestordetareas.GestionDeTareas.repository.TareaRepository;
import com.gestordetareas.GestionDeTareas.exception.ResourceNotFoundException;



@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    public Tarea getTareaById(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada con id: " + id));
    }
    
    private Long generarIdAleatorio() {
        Random random = new Random();
        Long id;
        // Se repite la generación mientras exista una tarea con el mismo id
        do {
            id = Math.abs(random.nextLong());
        } while (tareaRepository.existsById(id));
        return id;
    }

    public Tarea createTarea(Tarea tarea) {
    	
    	tarea.setId(generarIdAleatorio());
    	
        return tareaRepository.save(tarea);
    }

    public Tarea updateTarea(Long id, Tarea updatedTarea) {
        Tarea existingTarea = getTareaById(id);
        existingTarea.setTitulo(updatedTarea.getTitulo());
        existingTarea.setDescripcion(updatedTarea.getDescripcion());
        existingTarea.setEstado(updatedTarea.getEstado());
        return tareaRepository.save(existingTarea);
    }

    public void deleteTarea(Long id) {
        Tarea existingTarea = getTareaById(id);
        tareaRepository.delete(existingTarea);
    }
}

