package cl.rt.schl.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.Colegio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository extends CrudRepository<Asistencia, Long> {
    List<Asistencia> findByFecha(LocalDate fecha);
    
}