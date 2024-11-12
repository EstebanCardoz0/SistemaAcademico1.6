package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Profesor;
import org.example.sistemaacad.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearProfesor(@RequestBody Profesor profesor) {
        try {
            profesorService.crearProfesor(profesor);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("El profesor " + profesor.getNombre() + " " + profesor.getApellido() + 
                    " fue creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al crear el profesor: " + e.getMessage());
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<?> obtenerTodosLosProfesores() {
        try {
            List<Profesor> profesores = profesorService.obtenerTodosLosProfesores();
            return ResponseEntity.ok(profesores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener los profesores: " + e.getMessage());
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerProfesor(@PathVariable Long id) {
        try {
            Profesor profesor = profesorService.obtenerProfesorPorId(id);
            return ResponseEntity.ok(profesor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al obtener el profesor: " + e.getMessage());
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarProfesor(@PathVariable Long id) {
        try {
            String nombre = profesorService.obtenerProfesorPorId(id).getNombre();
            String apellido = profesorService.obtenerProfesorPorId(id).getApellido();
            profesorService.eliminarProfesor(id);
            return ResponseEntity.ok("El profesor " + nombre + " " + apellido + 
                " fue eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al eliminar el profesor: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProfesor(@PathVariable Long id, @RequestBody Profesor detallesProfesor) {
        try {   
            Profesor profesor = profesorService.obtenerProfesorPorId(id);
            if (detallesProfesor.getNombre() != null) profesor.setNombre(detallesProfesor.getNombre());
            if (detallesProfesor.getApellido() != null) profesor.setApellido(detallesProfesor.getApellido());
            profesorService.actualizarProfesor(id, profesor);
            return ResponseEntity.ok(profesorService.obtenerProfesorPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al editar el profesor: " + e.getMessage());
        }
    }

}
