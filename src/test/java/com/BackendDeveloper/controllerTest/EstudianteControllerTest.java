package com.BackendDeveloper.controllerTest;

import com.BackendDeveloper.controller.EstudianteController;
import com.BackendDeveloper.entity.Estudiante;
import com.BackendDeveloper.service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EstudianteControllerTest {

    @InjectMocks
    private EstudianteController estudianteController;

    @Mock
    private EstudianteService estudianteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEstudiantes() {
        // Given
        Estudiante estudiante1 = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        Estudiante estudiante2 = new Estudiante(2, "Ana", "García", "ana@example.com", 25, 1, 90);
        when(estudianteService.getAllEstudiantes()).thenReturn(Arrays.asList(estudiante1, estudiante2));

        // When
        var estudiantes = estudianteController.getAllEstudiantes();

        // Then
        assertNotNull(estudiantes);
        assertEquals(2, estudiantes.size());
        verify(estudianteService, times(1)).getAllEstudiantes();
    }

    @Test
    void getEstudianteById_Existente() {
        // Given
        Estudiante estudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        when(estudianteService.getEstudianteById(1)).thenReturn(Optional.of(estudiante));

        // When
        ResponseEntity<Estudiante> response = estudianteController.getEstudianteById(1);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(estudiante, response.getBody());
        verify(estudianteService, times(1)).getEstudianteById(1);
    }

    @Test
    void getEstudianteById_NoExistente() {
        // Given
        when(estudianteService.getEstudianteById(99)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Estudiante> response = estudianteController.getEstudianteById(99);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(estudianteService, times(1)).getEstudianteById(99);
    }

    @Test
    void createEstudiante() {
        // Given
        Estudiante nuevoEstudiante = new Estudiante(null, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        Estudiante creadoEstudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        when(estudianteService.createEstudiante(any(Estudiante.class))).thenReturn(creadoEstudiante);

        // When
        ResponseEntity<Estudiante> response = estudianteController.createEstudiante(nuevoEstudiante);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(creadoEstudiante, response.getBody());
        verify(estudianteService, times(1)).createEstudiante(any(Estudiante.class));
    }

    @Test
    void updateEstudiante_Existente() {
        // Given
        Estudiante estudianteExistente = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        when(estudianteService.getEstudianteById(1)).thenReturn(Optional.of(estudianteExistente));
        when(estudianteService.updateEstudiante(any(Estudiante.class))).thenReturn(estudianteExistente);

        // When
        ResponseEntity<Estudiante> response = estudianteController.updateEstudiante(1, estudianteExistente);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(estudianteExistente, response.getBody());
        verify(estudianteService, times(1)).updateEstudiante(any(Estudiante.class));
    }

    @Test
    void updateEstudiante_NoExistente() {
        // Given
        Estudiante estudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        when(estudianteService.getEstudianteById(1)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Estudiante> response = estudianteController.updateEstudiante(1, estudiante);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(estudianteService, times(1)).getEstudianteById(1);
    }

    @Test
    void deleteEstudiante_Existente() {
        // Given
        Estudiante estudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        when(estudianteService.getEstudianteById(1)).thenReturn(Optional.of(estudiante));

        // When
        ResponseEntity<Object> response = estudianteController.deleteEstudiante(1);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        verify(estudianteService, times(1)).deleteEstudiante(1);
    }

    @Test
    void deleteEstudiante_NoExistente() {
        // Given
        when(estudianteService.getEstudianteById(99)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Object> response = estudianteController.deleteEstudiante(99);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        verify(estudianteService, never()).deleteEstudiante(anyInt());
    }
}

