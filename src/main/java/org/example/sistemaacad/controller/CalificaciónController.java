package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.entity.Calificacion;
import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.service.AlumnoService;
import org.example.sistemaacad.service.CalificacionService;
import org.example.sistemaacad.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String crearCalificacion(@RequestBody Calificacion calificacion) {
        Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(calificacion.getAlumno().getLegajo());
        Materia materia = materiaService.obtenerMateriaPorId(calificacion.getMateria().getId());
        calificacion.setAlumno(alumno);
        calificacion.setMateria(materia);
        calificacionService.crearCalificacion(calificacion);
        return "La calificación de " + alumno.getNombre() + " " + alumno.getApellido()
                + " para " + materia.getNombre() + " fue creada correctamente";
    }


    @GetMapping("/traer")
    public List<Calificacion> obtenerTodasLasCalificaciones() {
        return calificacionService.obtenerTodasLasCalificaciones();
    }

    @GetMapping("/traer/{id}")
    public Calificacion obtenerCalificacion(@PathVariable Long id) {
        return calificacionService.obtenerCalificacionPorId(id);
    }


    @DeleteMapping("/borrar/{id}")
    public String eliminarCalificacion(@PathVariable Long id) {
        String nombre = calificacionService.obtenerCalificacionPorId(id).getAlumno().getNombre();
        String apellido = calificacionService.obtenerCalificacionPorId(id).getAlumno().getApellido();
        String materia = calificacionService.obtenerCalificacionPorId(id).getMateria().getNombre();
        calificacionService.eliminarCalificacion(id);
        return "La calificación de " + nombre + " " + apellido + " para " + materia + " fue eliminada correctamente";
    }

//    @PutMapping("/editar/{id}")
//    public Calificacion editarCalificacion(@PathVariable Long id,
//                                           @RequestBody Calificacion detallesCalificacion) {
//        Calificacion calificacion = calificacionService.obtenerCalificacionPorId(id);
//        if (detallesCalificacion.getNota() != null) calificacion.setNota(detallesCalificacion.getNota());
//        if (detallesCalificacion.getAlumno().getLegajo() != null) {
//            Alumno alumno = alumnoService.obtenerAlumnoPorLegajo(nuevoAlumnoId);
//            calificacion.setAlumno(alumno);
//        }
//        if (nuevaMateriaId != null) {
//            Materia materia = materiaService.obtenerMateriaPorId(nuevaMateriaId);
//            calificacion.setMateria(materia);
//        }
//        calificacionService.actualizarCalificacion(id, calificacion);
//        return calificacionService.obtenerCalificacionPorId(id);
//    }

    @PutMapping("/editar/{id}")
    public Calificacion editarCalificacion(@PathVariable Long id, @RequestBody Calificacion detallesCalificacion) {
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
        return calificacionService.obtenerCalificacionPorId(id);
    }

    @GetMapping("/alumno/{legajo}")
    public String obtenerCalificacionesYPromediosPorAlumno(@PathVariable Long legajo) {
        return calificacionService.calificacionesPorAlumno(legajo);
    }

}
