package com.BackendDeveloper.serviceImplTest;

import com.BackendDeveloper.entity.Estudiante;
import com.BackendDeveloper.repository.EstudianteRepository;
import com.BackendDeveloper.service.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EstudianteServiceImplTest {

    @InjectMocks
    private EstudianteServiceImpl estudianteService;

    @Mock
    private EstudianteRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEstudiantes() {
        Estudiante estudiante1 = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        Estudiante estudiante2 = new Estudiante(2, "María", "Gómez", "maria@example.com", 25, 3, 90);

        when(repository.findAll()).thenReturn(Arrays.asList(estudiante1, estudiante2));

        List<Estudiante> estudiantes = estudianteService.getAllEstudiantes();

        assertEquals(2, estudiantes.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetEstudianteByIdFound() {
        Estudiante estudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);

        when(repository.findById(1)).thenReturn(Optional.of(estudiante));

        Optional<Estudiante> result = estudianteService.getEstudianteById(1);

        assertTrue(result.isPresent());
        assertEquals(estudiante, result.get());
    }

    @Test
    void testGetEstudianteByIdNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Optional<Estudiante> result = estudianteService.getEstudianteById(1);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreateEstudiante() {
        Estudiante estudiante = new Estudiante(null, "Juan", "Pérez", "juan@example.com", 30, 2, 85);
        Estudiante estudianteCreado = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);

        when(repository.save(estudiante)).thenReturn(estudianteCreado);

        Estudiante result = estudianteService.createEstudiante(estudiante);

        assertEquals(estudianteCreado, result);
        verify(repository, times(1)).save(estudiante);
    }

    @Test
    void testUpdateEstudiante() {
        Estudiante estudiante = new Estudiante(1, "Juan", "Pérez", "juan@example.com", 30, 2, 85);

        when(repository.save(estudiante)).thenReturn(estudiante);

        Estudiante result = estudianteService.updateEstudiante(estudiante);

        assertEquals(estudiante, result);
        verify(repository, times(1)).save(estudiante);
    }

    @Test
    void testDeleteEstudiante() {
        doNothing().when(repository).deleteById(1);

        estudianteService.deleteEstudiante(1);

        verify(repository, times(1)).deleteById(1);
    }
}

