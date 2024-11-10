package org.example.sistemaacad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double nota;
    @ManyToOne
    @JoinColumn(name = "alumno_legajo")
    @JsonIgnoreProperties("materias")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    @JsonIgnoreProperties({"alumnos", "profesores", "descripcion"})
    private Materia materia;

}
