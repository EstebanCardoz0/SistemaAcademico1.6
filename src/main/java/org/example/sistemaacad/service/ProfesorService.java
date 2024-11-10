package org.example.sistemaacad.service;

import org.example.sistemaacad.entity.Profesor;
import org.example.sistemaacad.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> obtenerTodosLosProfesores() {
        return profesorRepository.findAll();
    }

    public Profesor obtenerProfesorPorId(Long id) {
        return profesorRepository.findById(id).orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }

    public void crearProfesor(Profesor profesor) {
        profesorRepository.save(profesor);
    }

    public void actualizarProfesor(Long id, Profesor detallesProfesor) {
        Profesor profesor = this.obtenerProfesorPorId(id);
        profesor.setNombre(detallesProfesor.getNombre());
        profesor.setApellido(detallesProfesor.getApellido());
        this.crearProfesor(profesor);
    }

    public void eliminarProfesor(Long id) {
        Profesor profesor = this.obtenerProfesorPorId(id);
        profesorRepository.delete(profesor);
    }
}
