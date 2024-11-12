package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.service.MateriaService;
import org.example.sistemaacad.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> crearMateria(@RequestBody Materia mate) {
        try {
            materiaService.crearMateria(mate);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("La materia " + mate.getNombre() + " fue creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al crear la materia: " + e.getMessage());
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<?> obtenerTodasLasMaterias() {
        try {
            List<Materia> materias = materiaService.obtenerTodasLasMaterias();
            return ResponseEntity.ok(materias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener las materias: " + e.getMessage());
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerMateria(@PathVariable Long id) {
        try {
            Materia materia = materiaService.obtenerMateriaPorId(id);
            return ResponseEntity.ok(materia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al obtener la materia: " + e.getMessage());
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarMateria(@PathVariable Long id) {
        try {
            String nombre = materiaService.obtenerMateriaPorId(id).getNombre();
            materiaService.eliminarMateria(id);
            return ResponseEntity.ok("La materia " + nombre + " fue eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al eliminar la materia: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarMateria(@PathVariable Long id, @RequestBody Materia detallesMateria) {
        try {
            Materia mate = materiaService.obtenerMateriaPorId(id);
            if (detallesMateria.getNombre() != null) mate.setNombre(detallesMateria.getNombre());
            if (detallesMateria.getDescripcion() != null) mate.setDescripcion(detallesMateria.getDescripcion());
            materiaService.actualizarMateria(id, mate);
            return ResponseEntity.ok(materiaService.obtenerMateriaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al editar la materia: " + e.getMessage());
        }
    }

    @PostMapping("/agregarprofesor")
    public ResponseEntity<?> agregarProfesorAMateria(@RequestParam Long materiaId, @RequestParam Long profesorId) {
        try {
            String materia = materiaService.obtenerMateriaPorId(materiaId).getNombre();
            String nombre = profesorService.obtenerProfesorPorId(profesorId).getNombre();
            String apellido = profesorService.obtenerProfesorPorId(profesorId).getApellido();
            materiaService.agregarProfesorAMateria(materiaId, profesorId);
            return ResponseEntity.ok("El profesor " + nombre + " " + apellido + 
                " fue añadido a la materia " + materia + " correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al agregar profesor a la materia: " + e.getMessage());
        }
    }
}
