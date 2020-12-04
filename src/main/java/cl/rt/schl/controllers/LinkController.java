package cl.rt.schl.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.rt.schl.services.LinkServiceImpl;
import cl.rt.schl.utils.GenericResponse;

@RestController
@RequestMapping("link")
public class LinkController {

	@Autowired
	private LinkServiceImpl linkService;

	@CrossOrigin(origins = "*")
	@GetMapping(path = "/", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerTodos(@RequestHeader Map<String, String> headers,
			HttpServletResponse response) {

		return linkService.generarLinkRegistro(headers);
	}

}