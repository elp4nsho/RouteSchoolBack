package cl.rt.schl.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Colegio;

import java.util.List;
import java.util.Optional;

public interface ApoderadoRepository extends CrudRepository<Apoderado, String> {


    Apoderado findByRutApAndClave(String rut, String clave);
    

    
    List<Apoderado> findByRutT(String rutT);

}