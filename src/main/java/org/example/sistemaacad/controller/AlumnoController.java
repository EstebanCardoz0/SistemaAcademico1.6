package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.service.AlumnoService;
import org.example.sistemaacad.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;
    @Autowired
    private MateriaService materiaService;

    @PostMapping("/crear")
    public String crearAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoService.crearAlumno(alumno);
            return "El alumno " + alumno.getNombre() + " " + alumno.getApellido() + " fue creado correctamente";
        } catch (Exception e) {
            return "Error al crear el alumno: " + e.getMessage();
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<?> obtenerTodosLosAlumnos() {
        try {
            List<Alumno> alumnos = alumnoService.obtenerTodosLosAlumnos();
            return ResponseEntity.ok(alumnos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener los alumnos: " + e.getMessage());
        }
    }

    @GetMapping("/traer/{legajo}")
    public ResponseEntity<?> obtenerAlumno(@PathVariable Long legajo) {
        try {
            Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(legajo);
            return ResponseEntity.ok(alumno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener el alumno: " + e.getMessage());
        }
    }

    @DeleteMapping("/borrar/{legajo}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable Long legajo) {
        try {
            String nombre = alumnoService.obtenerAlumnoPorLegajo(legajo).getNombre();
            String apellido = alumnoService.obtenerAlumnoPorLegajo(legajo).getApellido();
            alumnoService.eliminarAlumno(legajo);
            return ResponseEntity.ok("El alumno " + nombre + " " + apellido + " fue eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al eliminar el alumno: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{legajo}")
    public ResponseEntity<?> editarAlumno(@PathVariable Long legajo, @RequestBody Alumno detallesAlumno) {
        try {
            Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(legajo);
            if (detallesAlumno.getNombre() != null) alumno.setNombre(detallesAlumno.getNombre());
            if (detallesAlumno.getApellido() != null) alumno.setApellido(detallesAlumno.getApellido());
            alumnoService.actualizarAlumno(legajo, alumno);
            return ResponseEntity.ok(alumnoService.obtenerAlumnoPorLegajo(legajo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al editar el alumno: " + e.getMessage());
        }
    }

    @PostMapping("/agregarmateria")
    public ResponseEntity<String> agregarMateriaAAlumno(@RequestParam Long legajo, @RequestParam Long materiaId) {
        try {
            String nombre = alumnoService.obtenerAlumnoPorLegajo(legajo).getNombre();
            String apellido = alumnoService.obtenerAlumnoPorLegajo(legajo).getApellido();
            String materia = materiaService.obtenerMateriaPorId(materiaId).getNombre();
            alumnoService.agregarMateriaAAlumno(legajo, materiaId);
            return ResponseEntity.ok("La materia " + materia + " fue añadida al alumno " + nombre + " " + apellido + " correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al agregar materia al alumno: " + e.getMessage());
        }
    }

}
