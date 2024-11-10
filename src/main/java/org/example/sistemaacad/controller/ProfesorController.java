package org.example.sistemaacad.controller;

import org.example.sistemaacad.entity.Profesor;
import org.example.sistemaacad.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping("/crear")
    public String crearProfesor(@RequestBody Profesor profesor) {
        profesorService.crearProfesor(profesor);
        return "El profesor "+profesor.getNombre()+" "+profesor.getApellido()+" fue creado correctamente";
    }

    @GetMapping("/traer")
    public List<Profesor> obtenerTodosLosProfesores() {
        return profesorService.obtenerTodosLosProfesores();
    }

    @GetMapping("/traer/{id}")
    public Profesor obtenerProfesor(@PathVariable Long id) {
        return profesorService.obtenerProfesorPorId(id);
    }

    @DeleteMapping("/borrar/{id}")
    public String eliminarProfesor(@PathVariable Long id) {
        String nombre=profesorService.obtenerProfesorPorId(id).getNombre();
        String apellido=profesorService.obtenerProfesorPorId(id).getApellido();
        profesorService.eliminarProfesor(id);
        return "El profesor "+nombre+" "+apellido+" fue eliminado correctamente";
    }

//    @PutMapping("/editar/{id}")
//    public Profesor editarProfesor(@PathVariable Long id,
//                                   @RequestParam(required = false, name = "nombre") String nuevoNombre,
//                                   @RequestParam(required = false, name = "apellido") String nuevoApellido) {
//        Profesor profesor = profesorService.obtenerProfesorPorId(id);
//        if (nuevoNombre != null) profesor.setNombre(nuevoNombre);
//        if (nuevoApellido != null) profesor.setApellido(nuevoApellido);
//        profesorService.actualizarProfesor(id, profesor);
//        return profesorService.obtenerProfesorPorId(id);
//    }

    @PutMapping("/editar/{id}")
    public Profesor editarProfesor(@PathVariable Long id, @RequestBody Profesor detallesProfesor) {
        Profesor profesor = profesorService.obtenerProfesorPorId(id);
        if (detallesProfesor.getNombre() != null) profesor.setNombre(detallesProfesor.getNombre());
        if (detallesProfesor.getApellido() != null) profesor.setApellido(detallesProfesor.getApellido());
        profesorService.actualizarProfesor(id, profesor);
        return profesorService.obtenerProfesorPorId(id);
    }

}
