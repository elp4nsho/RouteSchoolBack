package cl.rt.schl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.entity.Viaje;

public interface DetalleViajeRepository extends CrudRepository<DetalleViaje, Long> {

	DetalleViaje findByNinoAndViaje(Nino nino,Viaje viaje);
	boolean deleteByNino(Nino nino);

	
}