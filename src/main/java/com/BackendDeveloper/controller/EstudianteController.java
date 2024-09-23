package com.BackendDeveloper.controller;

import com.BackendDeveloper.entity.Estudiante;
import com.BackendDeveloper.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    /**
     * Constructor para inyectar el servicio de Estudiante.
     *
     * @param estudianteService servicio que gestiona las operaciones de estudiantes
     */
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    /**
     * Obtiene una lista de todos los estudiantes registrados.
     *
     * @return lista de objetos {@link Estudiante}
     */
    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.getAllEstudiantes();
    }

    /**
     * Obtiene un estudiante por su ID.
     *
     * @param id identificador del estudiante a buscar
     * @return el estudiante encontrado o una respuesta con estado 404 si no se encuentra
     */
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Integer id) {
        return estudianteService.getEstudianteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo estudiante.
     *
     * @param estudiante objeto {@link Estudiante} con la información a crear
     * @return el estudiante recién creado
     */
    @PostMapping
    public ResponseEntity<Estudiante> createEstudiante(@Valid @RequestBody Estudiante estudiante) {
        Estudiante nuevoEstudiante = estudianteService.createEstudiante(estudiante);
        return ResponseEntity.ok(nuevoEstudiante);
    }

    /**
     * Actualiza un estudiante existente por su ID.
     *
     * @param id identificador del estudiante a actualizar
     * @param estudiante objeto {@link Estudiante} con la información actualizada
     * @return el estudiante actualizado o una respuesta con estado 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Integer id, @Valid @RequestBody Estudiante estudiante) {
        return estudianteService.getEstudianteById(id)
                .map(existingEstudiante -> {
                    estudiante.setId(id);
                    Estudiante updatedEstudiante = estudianteService.updateEstudiante(estudiante);
                    return ResponseEntity.ok(updatedEstudiante);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un estudiante por su ID.
     *
     * @param id identificador del estudiante a eliminar
     * @return una respuesta vacía con estado 200 si se elimina correctamente o 404 si no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEstudiante(@PathVariable Integer id) {
        return estudianteService.getEstudianteById(id)
                .map(estudiante -> {
                    estudianteService.deleteEstudiante(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
