package com.gestordetareas.GestionDeTareas.service;

	import com.gestordetareas.GestionDeTareas.exception.ResourceNotFoundException;
	import com.gestordetareas.GestionDeTareas.model.EstadoTarea;
	import com.gestordetareas.GestionDeTareas.model.Tarea;
	import com.gestordetareas.GestionDeTareas.repository.TareaRepository;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.junit.jupiter.MockitoExtension;

	import java.util.*;

	import static org.junit.jupiter.api.Assertions.*;
	import static org.mockito.Mockito.*;

	@ExtendWith(MockitoExtension.class)
	class TareaServiceTest {

	    @Mock
	    private TareaRepository tareaRepository;

	    @InjectMocks
	    private TareaService tareaService;

	    private Tarea tarea;

	    @BeforeEach
	    void setUp() {
	        tarea = new Tarea();
	        tarea.setId(Math.abs(new Random().nextLong()));
	        tarea.setTitulo("Tarea de Prueba");
	        tarea.setDescripcion("Descripción de prueba");
	        tarea.setEstado(EstadoTarea.POR_HACER);
	    }

	    @Test
	    void testGetAllTareas() {
	        List<Tarea> tareas = new ArrayList<>();
	        tareas.add(tarea);
	        when(tareaRepository.findAll()).thenReturn(tareas);

	        List<Tarea> result = tareaService.getAllTareas();

	        assertEquals(1, result.size());
	        verify(tareaRepository, times(1)).findAll();
	    }

	    @Test
	    void testGetTareaByIdFound() {
	        Long id = tarea.getId();
	        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));

	        Tarea result = tareaService.getTareaById(id);

	        assertNotNull(result);
	        assertEquals("Tarea de Prueba", result.getTitulo());
	        verify(tareaRepository, times(1)).findById(id);
	    }

	    @Test
	    void testGetTareaByIdNotFound() {
	        Long id = Math.abs(new Random().nextLong());
	        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

	        assertThrows(ResourceNotFoundException.class, () -> tareaService.getTareaById(id));
	        verify(tareaRepository, times(1)).findById(id);
	    }

	    @Test
	    void testCreateTarea() {
	        when(tareaRepository.save(tarea)).thenReturn(tarea);

	        Tarea result = tareaService.createTarea(tarea);

	        assertNotNull(result);
	        assertEquals("Tarea de Prueba", result.getTitulo());
	        verify(tareaRepository, times(1)).save(tarea);
	    }

	    @Test
	    void testUpdateTarea() {
	        Long id = tarea.getId();
	        Tarea updatedTarea = new Tarea();
	        updatedTarea.setTitulo("Título Actualizado");
	        updatedTarea.setDescripcion("Descripción Actualizada");
	        updatedTarea.setEstado(EstadoTarea.EN_PROGRESO);

	        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
	        when(tareaRepository.save(any(Tarea.class))).thenReturn(updatedTarea);

	        Tarea result = tareaService.updateTarea(id, updatedTarea);

	        assertEquals("Título Actualizado", result.getTitulo());
	        assertEquals("Descripción Actualizada", result.getDescripcion());
	        assertEquals(EstadoTarea.EN_PROGRESO, result.getEstado());
	        verify(tareaRepository, times(1)).findById(id);
	        verify(tareaRepository, times(1)).save(any(Tarea.class));
	    }

	    @Test
	    void testDeleteTarea() {
	        Long id = tarea.getId();
	        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
	        doNothing().when(tareaRepository).delete(tarea);

	        tareaService.deleteTarea(id);

	        verify(tareaRepository, times(1)).findById(id);
	        verify(tareaRepository, times(1)).delete(tarea);
	    }
	}

