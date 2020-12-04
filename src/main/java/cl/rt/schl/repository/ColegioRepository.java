package cl.rt.schl.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Colegio;

import java.util.List;
import java.util.Optional;

public interface ColegioRepository extends CrudRepository<Colegio, Long> {

	List<Colegio> findByRutT(String rutT);
}