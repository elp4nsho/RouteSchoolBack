package cl.rt.schl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Viaje;

public interface ViajeRepository extends CrudRepository<Viaje, Long> {

		List<Viaje> findByRutTAndDia(String rutT,LocalDate dia);
		
		@Query("select e from viaje e WHERE EXTRACT(MONTH FROM dia) = ?1 and EXTRACT(Year FROM dia) = ?2 and rut_t = ?3")List<Viaje> getByMonthAndYearAndRutT(int month,int year,String rutT);
		
}