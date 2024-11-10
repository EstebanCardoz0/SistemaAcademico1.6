package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.service.MateriaService;
import org.example.sistemaacad.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;
    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/crear")
    public String crearMateria(@RequestBody Materia mate) {
        materiaService.crearMateria(mate);
        return "La materia " + mate.getNombre() + " fue creada correctamente";
    }

    @GetMapping("/traer")
    public List<Materia> obtenerTodasLasMaterias() {
        return materiaService.obtenerTodasLasMaterias();
    }

    @GetMapping("/traer/{id}")
    public Materia obtenerMateria(@PathVariable Long id) {
        return materiaService.obtenerMateriaPorId(id);
    }

    @DeleteMapping("/borrar/{id}")
    public String eliminarMateria(@PathVariable Long id) {
        String nombre = materiaService.obtenerMateriaPorId(id).getNombre();
        materiaService.eliminarMateria(id);
        return "La materia " + nombre + " fue eliminada correctamente";
    }

    @PutMapping("/editar/{id}")
    public Materia editarMateria(@PathVariable Long id, @RequestBody Materia detallesMateria) {
        Materia mate = materiaService.obtenerMateriaPorId(id);
        if (detallesMateria.getNombre() != null) mate.setNombre(detallesMateria.getNombre());
        if (detallesMateria.getDescripcion() != null) mate.setDescripcion(detallesMateria.getDescripcion());
        materiaService.actualizarMateria(id, mate);
        return materiaService.obtenerMateriaPorId(id);
    }

    @PostMapping("/agregarprofesor")
    public String agregarProfesorAMateria(@RequestParam Long materiaId, @RequestParam Long profesorId) {
        String materia = materiaService.obtenerMateriaPorId(materiaId).getNombre();
        String nombre = profesorService.obtenerProfesorPorId(profesorId).getNombre();
        String apellido = profesorService.obtenerProfesorPorId(profesorId).getApellido();
        materiaService.agregarProfesorAMateria(materiaId, profesorId);
        return "El profesor " + nombre + " " + apellido + " fue añadido a la materia " + materia + " correctamente";
    }
}
