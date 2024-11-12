package org.example.sistemaacad.service;

import org.example.sistemaacad.entity.Alumno;
import org.example.sistemaacad.entity.Calificacion;
import org.example.sistemaacad.entity.Materia;
import org.example.sistemaacad.repository.AlumnoRepository;
import org.example.sistemaacad.repository.CalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    public void crearCalificacion(Calificacion calificacion) {
        calificacionRepository.save(calificacion);
    }

    public List<Calificacion> obtenerTodasLasCalificaciones() {
        return calificacionRepository.findAll();
    }

    public Calificacion obtenerCalificacionPorId(Long id) {
        return calificacionRepository.findById(id).orElseThrow(() -> new RuntimeException("Calificación no encontrada"));
    }

    public void actualizarCalificacion(Long id, Calificacion detallesCalificacion) {
        Calificacion calificacion = this.obtenerCalificacionPorId(id);
        calificacion.setNota(detallesCalificacion.getNota());
        calificacion.setAlumno(detallesCalificacion.getAlumno());
        calificacion.setMateria(detallesCalificacion.getMateria());
        this.crearCalificacion(calificacion);
    }

    public void eliminarCalificacion(Long id) {
        Calificacion calificacion = this.obtenerCalificacionPorId(id);
        calificacionRepository.delete(calificacion);
    }

    public String calificacionesPorAlumno(Long legajo) {
        Alumno alumno = alumnoRepository.findById(legajo).orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        List<Calificacion> calificaciones = calificacionRepository.findByAlumnoLegajo(legajo);

        Map<Materia, List<Calificacion>> calificacionesPorMateria = calificaciones.stream()
                .collect(Collectors.groupingBy(Calificacion::getMateria));

        StringBuilder resultado = new StringBuilder();
        resultado.append("Calificaciones de ").append(alumno.getNombre()).append(" ").append(alumno.getApellido()).append(":\n");

        double sumaPromedios = 0;
        int cantidadMaterias = calificacionesPorMateria.size();

        for (Map.Entry<Materia, List<Calificacion>> entry : calificacionesPorMateria.entrySet()) {
            Materia materia = entry.getKey();
            List<Calificacion> calificacionesMateria = entry.getValue();
            double promedio = calificacionesMateria.stream()
                    .mapToDouble(Calificacion::getNota)
                    .average()
                    .orElse(0.0);

            resultado.append("Materia: ").append(materia.getNombre()).append("\n");
            resultado.append("Calificaciones: ").append(calificacionesMateria.stream()
                    .map(calificacion -> String.valueOf(calificacion.getNota()))
                    .collect(Collectors.joining(", "))).append("\n");
            resultado.append("Promedio: ").append(promedio).append("\n\n");

            sumaPromedios += promedio;
        }

        double promedioGeneral = cantidadMaterias > 0 ? sumaPromedios / cantidadMaterias : 0.0;
        resultado.append("Promedio General: ").append(promedioGeneral);

        return resultado.toString();
    }

}
