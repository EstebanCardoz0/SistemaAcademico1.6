package org.example.sistemaacad.service;

import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.entity.Profesor;
import org.example.sistemaacad.repository.MateriaRepository;
import org.example.sistemaacad.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    public void crearMateria(Materia materia) {
        materiaRepository.save(materia);
    }

    public List<Materia> obtenerTodasLasMaterias() {
        return materiaRepository.findAll();
    }

    public Materia obtenerMateriaPorId(Long id) {
        return materiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Materia no encontrada"));
    }


    public void eliminarMateria(Long id) {
        Materia materia = this.obtenerMateriaPorId(id);
        materiaRepository.delete(materia);
    }

    public void actualizarMateria(Long id, Materia detallesMateria) {
        Materia materia = this.obtenerMateriaPorId(id);
        materia.setNombre(detallesMateria.getNombre());
        materia.setDescripcion(detallesMateria.getDescripcion());
        this.crearMateria(materia);
    }

    public void agregarProfesorAMateria(Long materiaId, Long profesorId) {
        Materia materia = this.obtenerMateriaPorId(materiaId);
        Profesor profesor = profesorRepository.findById(profesorId).orElseThrow(()
                -> new RuntimeException("Profesor no encontrado"));
        if (!materia.getProfesores().contains(profesor)) {
            materia.getProfesores().add(profesor);
            profesor.getMaterias().add(materia);
            this.crearMateria(materia);
            profesorRepository.save(profesor);
        } else {
            throw new RuntimeException("El profesor ya esta asignado a la materia");
        }
    }
}

