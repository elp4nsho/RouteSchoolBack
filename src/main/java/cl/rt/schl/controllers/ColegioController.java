package cl.rt.schl.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.services.ColegioServiceImpl;
import cl.rt.schl.utils.GenericResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("colegio")
public class ColegioController {

	@Autowired
	private ColegioServiceImpl colegioService;

	@GetMapping(path = "/", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerTodos(@RequestHeader Map<String, String> headers,
			HttpServletResponse response) {
		return colegioService.colegiosPorRutT(headers);
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> guardar(@RequestHeader Map<String, String> headers,
			@RequestBody Colegio colegio) {
		return colegioService.registrarColegioPorRutT(headers, colegio);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> actualizar(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id, @RequestBody Colegio colegio) {
		return colegioService.editar(headers, id, colegio);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<GenericResponse> delete(@RequestHeader Map<String, String> headers,
			@PathVariable("id") Long id) {
		return colegioService.delete(headers, id);
	}

}