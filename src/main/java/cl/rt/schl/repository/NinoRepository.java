package cl.rt.schl.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Nino;

import java.util.List;
import java.util.Optional;

public interface NinoRepository extends CrudRepository<Nino, String> {
    public Nino findByRutN(String rutN);
    
}