package com.BackendDeveloper.service.impl;

import com.BackendDeveloper.entity.Estudiante;
import com.BackendDeveloper.repository.EstudianteRepository;
import com.BackendDeveloper.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de la interfaz {@link EstudianteService} que proporciona
 * los servicios CRUD para la entidad {@link Estudiante}.
 */
@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository repository;

    /**
     * Obtiene la lista de todos los estudiantes registrados en la base de datos.
     *
     * @return Lista de estudiantes.
     */
    @Override
    public List<Estudiante> getAllEstudiantes() {
        return repository.findAll();
    }

    /**
     * Busca un estudiante por su ID.
     *
     * @param id Identificador del estudiante.
     * @return Un {@link Optional} con el estudiante si existe, de lo contrario, un Optional vacío.
     */
    @Override
    public Optional<Estudiante> getEstudianteById(Integer id) {
        return repository.findById(id);
    }

    /**
     * Crea un nuevo estudiante en la base de datos.
     *
     * @param estudiante Objeto {@link Estudiante} con la información del nuevo estudiante.
     * @return El estudiante creado.
     */
    @Override
    public Estudiante createEstudiante(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    /**
     * Actualiza la información de un estudiante existente.
     *
     * @param estudiante Objeto {@link Estudiante} con los datos actualizados.
     * @return El estudiante actualizado.
     */
    @Override
    public Estudiante updateEstudiante(Estudiante estudiante) {
        return repository.save(estudiante);
    }

    /**
     * Elimina un estudiante de la base de datos por su ID.
     *
     * @param id Identificador del estudiante a eliminar.
     */
    @Override
    public void deleteEstudiante(Integer id) {
        repository.deleteById(id);
    }
}