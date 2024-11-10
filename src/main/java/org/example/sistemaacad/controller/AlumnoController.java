package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.service.AlumnoService;
import org.example.sistemaacad.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
        alumnoService.crearAlumno(alumno);
        return "El alumno " + alumno.getNombre() + " " + alumno.getApellido() + " fue creado correctamente";
    }

    @GetMapping("/traer")
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoService.obtenerTodosLosAlumnos();
    }

    @GetMapping("/traer/{legajo}")
    public Alumno obtenerAlumno(@PathVariable Long legajo) {
        return alumnoService.obtenerAlumnoPorLegajo(legajo);
    }

    @DeleteMapping("/borrar/{legajo}")
    public String eliminarAlumno(@PathVariable Long legajo) {
        String nombre = alumnoService.obtenerAlumnoPorLegajo(legajo).getNombre();
        String apellido = alumnoService.obtenerAlumnoPorLegajo(legajo).getApellido();
        alumnoService.eliminarAlumno(legajo);
        return "El alumno " + nombre + " " + apellido + " fue eliminado correctamente";
    }

    @PutMapping("/editar/{legajo}")
    public Alumno editarAlumno(@PathVariable Long legajo, @RequestBody Alumno detallesAlumno) {
        Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(legajo);
        if (detallesAlumno.getNombre() != null) alumno.setNombre(detallesAlumno.getNombre());
        if (detallesAlumno.getApellido() != null) alumno.setApellido(detallesAlumno.getApellido());
        alumnoService.actualizarAlumno(legajo, alumno);
        return alumnoService.obtenerAlumnoPorLegajo(legajo);
    }

    @PostMapping("/agregarmateria")
    public String agregarMateriaAAlumno(@RequestParam Long legajo, @RequestParam Long materiaId) {
        String nombre = alumnoService.obtenerAlumnoPorLegajo(legajo).getNombre();
        String apellido = alumnoService.obtenerAlumnoPorLegajo(legajo).getApellido();
        String materia = materiaService.obtenerMateriaPorId(materiaId).getNombre();
        alumnoService.agregarMateriaAAlumno(legajo, materiaId);
        return "La materia " + materia + " fue añadida al alumno " + nombre + " " + apellido + " correctamente";
    }


}
