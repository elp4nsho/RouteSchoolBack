package cl.rt.schl.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import cl.rt.schl.entity.Transportista;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, String> {
	
	Transportista findByUid(String uid);
	List<Transportista> findByRut(String rut);

	


}