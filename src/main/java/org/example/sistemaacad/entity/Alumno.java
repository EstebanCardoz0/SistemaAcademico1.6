package org.example.sistemaacad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long legajo;
    private String nombre;
    private String apellido;

    @ManyToMany
    @JoinTable(name = "alumno_materia",
            joinColumns = @JoinColumn(name = "alumno_legajo", referencedColumnName = "legajo"),
            inverseJoinColumns = @JoinColumn(name = "materia_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties({"alumnos", "profesores", "descripcion"})
    private List<Materia> materias = new ArrayList<>();
}
