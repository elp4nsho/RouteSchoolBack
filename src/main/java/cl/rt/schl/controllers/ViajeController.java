package cl.rt.schl.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.services.ViajeServiceImpl;
import cl.rt.schl.utils.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("viaje")
public class ViajeController {

	@Autowired
	private ViajeServiceImpl viajeService;

	@PostMapping(path = "/iniciar", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerTodos(@RequestHeader Map<String, String> headers,
			@RequestBody List<DetalleViaje> viajes) {
		return viajeService.agregarViaje(headers, viajes);
	}

	@PostMapping(path = "/terminar/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerTodos(@PathVariable("id") Long id,@RequestHeader Map<String, String> headers,
			@RequestBody List<DetalleViaje> viajes) {
		return viajeService.terminarViaje(headers, viajes,id);
	}
	
	@GetMapping(path = "/datos/{anio}/{mes}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> datosPorMes(@PathVariable("mes") int mes,@PathVariable("anio") int anio,@RequestHeader Map<String, String> headers
			) {
		return viajeService.datosPorMesAnioYRutT(headers, mes,anio);
	}
	
}