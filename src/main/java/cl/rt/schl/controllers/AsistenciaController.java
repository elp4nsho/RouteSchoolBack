package cl.rt.schl.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.services.AsistenciaServiceImpl;
import cl.rt.schl.utils.GenericResponse;

@RestController
@RequestMapping("asistencia")
public class AsistenciaController {

	@Autowired
	private AsistenciaServiceImpl asistenciaService;

	@CrossOrigin(origins = "*")
	@PostMapping(path = "/{rutNino}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerTodos(@RequestHeader Map<String, String> headers,
			HttpServletResponse response, @RequestBody Asistencia asistencia, @PathVariable("rutNino") String rutNino) {
		return asistenciaService.crear(headers, asistencia, rutNino);
	}


}