package org.example.sistemaacad.repository;

import org.example.sistemaacad.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByAlumnoLegajo(Long legajo);
}
