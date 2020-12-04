package cl.rt.schl.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.services.ApoderadoServiceImpl;
import cl.rt.schl.utils.GenericResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")

@Controller
@RequestMapping("apoderado")
public class ApoderadoController {

	@Autowired
	private ApoderadoServiceImpl apoderadoService;

	

	@GetMapping(path = "/colegios/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> obtenerColegios(@RequestHeader Map<String, String> headers,
			@PathVariable("id") String id) {
		return apoderadoService.obtenerColegios(id);
	}

	@PostMapping(path = "/registrar/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> registrar(@RequestHeader Map<String, String> headers,
			@PathVariable("id") String id, @RequestBody Apoderado apoderado) {
		return apoderadoService.registrarApoderado(id, apoderado);
	}

	@PatchMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GenericResponse> editar(@RequestHeader Map<String, String> headers,
			@RequestBody Apoderado apoderado) {
		return apoderadoService.editar(headers, apoderado);
	}

	@GetMapping(path = "/", produces = "application/json")
	public ResponseEntity<GenericResponse> apoderadoDatos(@RequestHeader Map<String, String> headers,HttpServletResponse response) {
		return apoderadoService.loginApoderado(headers,response);
	}

	@GetMapping(path = "/salir", produces = "application/json")
	public ResponseEntity<GenericResponse> apoderadoSalir(@RequestHeader Map<String, String> headers,HttpServletResponse response) {
		return apoderadoService.salirApoderado(headers,response);
	}
	

	@RequestMapping("/formulario/{linkId}")
	public String loginForm(@PathVariable("linkId") String linkId){
		return apoderadoService.mostrarFormulario(linkId);
	}

	@GetMapping("/home")
	public String home(HttpServletRequest request){
		return apoderadoService.mostrarHome(request);
	}

	@GetMapping(path = "/login", produces = "application/json")
	public String login() {
		return apoderadoService.showlogin();
	}
	
	
	@GetMapping(path = "/monitoreo", produces = "application/json")
	public String monitoreo(HttpServletRequest request) {
		return apoderadoService.mostrarMonitoreo(request);
	}
	
	@GetMapping(path = "/editar", produces = "application/json")
	public String editarVista(HttpServletRequest request) {
		return apoderadoService.mostrarEditar(request);
	}
	
	@GetMapping(path = "/inasistencia", produces = "application/json")
	public String inasistencia(HttpServletRequest request) {
		return apoderadoService.mostrarInasistencia(request);
	}
	
	
	
	
	
	
	
	
	
}