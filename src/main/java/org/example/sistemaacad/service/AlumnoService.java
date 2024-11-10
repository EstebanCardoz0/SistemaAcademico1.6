package org.example.sistemaacad.service;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.repository.AlumnoRepository;
import org.example.sistemaacad.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    public void crearAlumno(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoRepository.findAll();
    }

    public Alumno obtenerAlumnoPorLegajo(Long legajo) {
        return alumnoRepository.findById(legajo).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    }

    public void eliminarAlumno(Long legajo) {
        Alumno alumno = this.obtenerAlumnoPorLegajo(legajo);
        alumnoRepository.delete(alumno);
    }

    public void actualizarAlumno(Long legajo, Alumno detallesAlumno) {
        Alumno alumno = this.obtenerAlumnoPorLegajo(legajo);
        alumno.setNombre(detallesAlumno.getNombre());
        alumno.setApellido(detallesAlumno.getApellido());
        this.crearAlumno(alumno);
    }

    public void agregarMateriaAAlumno(Long legajo, Long materiaId) {
        Alumno alumno = obtenerAlumnoPorLegajo(legajo);
        Materia materia = materiaRepository.findById(materiaId).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
        if (!alumno.getMaterias().contains(materia)) {
            alumno.getMaterias().add(materia);
            materia.getAlumnos().add(alumno);
            alumnoRepository.save(alumno);
            materiaRepository.save(materia);
        } else {
            throw new RuntimeException("El alumno ya tiene la materia");
        }
    }


}
