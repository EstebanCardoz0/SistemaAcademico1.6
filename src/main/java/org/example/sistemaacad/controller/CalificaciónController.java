package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.entity.Calificacion;
import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.service.AlumnoService;
import org.example.sistemaacad.service.CalificacionService;
import org.example.sistemaacad.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificacion")
public class CalificaciónController {

    @Autowired
    private CalificacionService calificacionService;
    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private MateriaService materiaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCalificacion(@RequestBody Calificacion calificacion) {
        try {
            Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(calificacion.getAlumno().getLegajo());
            Materia materia = materiaService.obtenerMateriaPorId(calificacion.getMateria().getId());
            calificacion.setAlumno(alumno);
            calificacion.setMateria(materia);
            calificacionService.crearCalificacion(calificacion);
            String mensaje = "La calificación de " + alumno.getNombre() + " " + alumno.getApellido()
                + " para " + materia.getNombre() + " fue creada correctamente";
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al crear la calificación: " + e.getMessage());
        }
    }

    @GetMapping("/traer")
    public ResponseEntity<?> obtenerTodasLasCalificaciones() {
        try {
            List<Calificacion> calificaciones = calificacionService.obtenerTodasLasCalificaciones();
            return ResponseEntity.ok(calificaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al obtener las calificaciones: " + e.getMessage());
        }
    }

    @GetMapping("/traer/{id}")
    public ResponseEntity<?> obtenerCalificacion(@PathVariable Long id) {
        try {
            Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
            return ResponseEntity.ok(calificacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al obtener la calificación: " + e.getMessage());
        }
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminarCalificacion(@PathVariable Long id) {
        try {
            Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
            String mensaje = "La calificación de " + calificacion.getAlumno().getNombre() + 
                " " + calificacion.getAlumno().getApellido() + 
                " para " + calificacion.getMateria().getNombre() + 
                " fue eliminada correctamente";
            calificacionService.eliminarCalificacion(id);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al eliminar la calificación: " + e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarCalificacion(@PathVariable Long id, @RequestBody Calificacion detallesCalificacion) {
        try {
            Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
            if (detallesCalificacion.getNota() != null) calificacion.setNota(detallesCalificacion.getNota());
            if (detallesCalificacion.getAlumno() != null) {
                Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(detallesCalificacion.getAlumno().getLegajo());
                calificacion.setAlumno(alumno);
            }
            if (detallesCalificacion.getMateria() != null) {
                Materia materia = materiaService.obtenerMateriaPorId(detallesCalificacion.getMateria().getId());
                calificacion.setMateria(materia);
            }
            calificacionService.actualizarCalificacion(id, calificacion);
            return ResponseEntity.ok(calificacionService.obtenerCalificacionPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al editar la calificación: " + e.getMessage());
        }
    }

    @GetMapping("/alumno/{legajo}")
    public ResponseEntity<?> obtenerCalificacionesYPromediosPorAlumno(@PathVariable Long legajo) {
        try {   
            String resultado = calificacionService.calificacionesPorAlumno(legajo);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error al obtener las calificaciones y promedios por alumno: " + e.getMessage());
        }
    }

}
