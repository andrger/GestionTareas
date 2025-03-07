package com.gestordetareas.GestionDeTareas.controller;

	import com.fasterxml.jackson.databind.ObjectMapper; 
	import com.gestordetareas.GestionDeTareas.model.EstadoTarea; 
	import com.gestordetareas.GestionDeTareas.model.Tarea; 
	import com.gestordetareas.GestionDeTareas.service.TareaService; 
	import org.junit.jupiter.api.Test; 
	import org.junit.jupiter.api.extension.ExtendWith; 
	import org.mockito.Mockito; 
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; 
	import org.springframework.boot.test.mock.mockito.MockBean; 
	import org.springframework.http.MediaType; 
	import org.springframework.test.context.junit.jupiter.SpringExtension; 
	import org.springframework.test.web.servlet.MockMvc;

	import java.util.Collections;
	import java.util.List;
	import java.util.Random;

	import static org.mockito.ArgumentMatchers.any; 
	import static org.mockito.ArgumentMatchers.eq; 
	import static org.mockito.Mockito.when; 
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete; 
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; 
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; 
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; 
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
	
	@ExtendWith(SpringExtension.class) @WebMvcTest(TareaController.class) 
	public class TareaControllerTest {
		
		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private TareaService tareaService;

		@Autowired
		private ObjectMapper objectMapper;

		@Test
		void testGetAllTareas() throws Exception {
		    Tarea tarea = new Tarea();
		    tarea.setId(Math.abs(new Random().nextLong()));
		    tarea.setTitulo("Tarea 1");
		    tarea.setDescripcion("Descripción 1");
		    tarea.setEstado(EstadoTarea.POR_HACER);
		    List<Tarea> tareas = Collections.singletonList(tarea);

		    when(tareaService.getAllTareas()).thenReturn(tareas);

		    mockMvc.perform(get("/tareas"))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$[0].titulo").value("Tarea 1"));
		}

		@Test
		void testGetTareaById() throws Exception {
		    Long id =Math.abs(new Random().nextLong());
		    Tarea tarea = new Tarea();
		    tarea.setId(id);
		    tarea.setTitulo("Tarea Test");
		    tarea.setDescripcion("Descripción Test");
		    tarea.setEstado(EstadoTarea.EN_PROGRESO);

		    when(tareaService.getTareaById(eq(id))).thenReturn(tarea);

		    mockMvc.perform(get("/tareas/{id}", id))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.titulo").value("Tarea Test"));
		}

		@Test
		void testCreateTarea() throws Exception {
		    Tarea tarea = new Tarea();
		    tarea.setId(Math.abs(new Random().nextLong())); // Generar un ID aleatorio
		    tarea.setTitulo("Nueva Tarea");
		    tarea.setDescripcion("Nueva Descripción");
		    tarea.setEstado(EstadoTarea.POR_HACER);

		    when(tareaService.createTarea(any(Tarea.class))).thenReturn(tarea);

		    mockMvc.perform(post("/tareas")
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(tarea)))
		            .andExpect(status().isCreated())
		            .andExpect(jsonPath("$.titulo").value("Nueva Tarea"));
		}


		@Test
		void testUpdateTarea() throws Exception {
		    Long id = Math.abs(new Random().nextLong());
		    Tarea updatedTarea = new Tarea();
		    updatedTarea.setId(id);
		    updatedTarea.setTitulo("Tarea Actualizada");
		    updatedTarea.setDescripcion("Descripción Actualizada");
		    updatedTarea.setEstado(EstadoTarea.COMPLETADA);

		    when(tareaService.updateTarea(eq(id), any(Tarea.class))).thenReturn(updatedTarea);

		    mockMvc.perform(put("/tareas/{id}", id)
		            .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(updatedTarea)))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.titulo").value("Tarea Actualizada"));
		}

		@Test
		void testDeleteTarea() throws Exception {
		    Long id = Math.abs(new Random().nextLong());
		    Mockito.doNothing().when(tareaService).deleteTarea(eq(id));

		    mockMvc.perform(delete("/tareas/{id}", id))
		            .andExpect(status().isNoContent());
		}
	}
