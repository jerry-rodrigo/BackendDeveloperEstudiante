package com.BackendDeveloper.service;

import com.BackendDeveloper.entity.Estudiante;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los métodos del servicio para la entidad Estudiante.
 */
public interface EstudianteService {

    /**
     * Obtiene una lista de todos los estudiantes.
     *
     * @return Lista de objetos {@link Estudiante}.
     */
    List<Estudiante> getAllEstudiantes();

    /**
     * Obtiene un estudiante por su ID.
     *
     * @param id ID del estudiante.
     * @return Un {@link Optional} que contiene el estudiante si es encontrado, o vacío si no lo es.
     */
    Optional<Estudiante> getEstudianteById(Integer id);

    /**
     * Crea un nuevo estudiante.
     *
     * @param estudiante El objeto {@link Estudiante} a ser creado.
     * @return El objeto {@link Estudiante} creado.
     */
    Estudiante createEstudiante(Estudiante estudiante);

    /**
     * Actualiza un estudiante existente.
     *
     * @param estudiante El objeto {@link Estudiante} con los nuevos datos.
     * @return El objeto {@link Estudiante} actualizado.
     */
    Estudiante updateEstudiante(Estudiante estudiante);

    /**
     * Elimina un estudiante por su ID.
     *
     * @param id ID del estudiante a eliminar.
     */
    void deleteEstudiante(Integer id);
}
