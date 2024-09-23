package com.BackendDeveloper.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Entidad que representa a un Estudiante en la base de datos.
 * Contiene información como nombre, apellido, email, créditos, semestre y promedio.
 * Utiliza validaciones para garantizar la integridad de los datos ingresados.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "estudiantes")
public class Estudiante {

    /**
     * Identificador único del estudiante.
     * Se genera automáticamente mediante la estrategia {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del estudiante.
     * Debe tener entre 2 y 40 caracteres.
     */
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String nombre;

    /**
     * Apellido del estudiante.
     * Debe tener entre 2 y 40 caracteres.
     */
    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 2, max = 40, message = "El apellido debe tener entre 2 y 40 caracteres")
    @Column(length = 40, nullable = false)
    private String apellido;

    /**
     * Email único del estudiante.
     * Debe ser un email válido y no puede ser nulo.
     */
    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email debe ser válido")
    @Column(length = 40, nullable = false, unique = true)
    private String email;

    /**
     * Créditos acumulados por el estudiante.
     * No puede ser un valor negativo.
     */
    @NotNull(message = "Los créditos no pueden ser nulos")
    @Min(value = 0, message = "Los créditos no pueden ser negativos")
    @Column(nullable = false)
    private Integer creditos;

    /**
     * Semestre en el que está inscrito el estudiante.
     * Debe ser mayor o igual a 1.
     */
    @NotNull(message = "El semestre no puede ser nulo")
    @Min(value = 1, message = "El semestre debe ser mayor o igual a 1")
    @Column(nullable = false)
    private Integer semestre;

    /**
     * Promedio general del estudiante.
     * Debe estar entre 0 y 100.
     */
    @NotNull(message = "El promedio no puede ser nulo")
    @Min(value = 0, message = "El promedio no puede ser negativo")
    @Max(value = 100, message = "El promedio no puede ser mayor a 100")
    @Column(nullable = false)
    private Integer promedio;
}
