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
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "materias")
    @JsonIgnoreProperties("materias")
    private List<Alumno> alumnos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "materia_profesor",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "profesor_id")
    )
    @JsonIgnoreProperties("materias")

    private List<Profesor> profesores = new ArrayList<>();
}
