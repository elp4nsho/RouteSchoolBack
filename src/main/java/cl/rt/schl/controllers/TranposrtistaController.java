package cl.rt.schl.controllers;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import cl.rt.schl.services.TransportistaServiceImpl;
import cl.rt.schl.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.TransportistaRepository;
@CrossOrigin(origins = "*")
@RestController()
@RequestMapping("transportista")
public class TranposrtistaController {

	@Autowired private TransportistaServiceImpl transportistaService;
	 
	@GetMapping(path = "/verificar" )
	public ResponseEntity<GenericResponse> verificarRegistro(@RequestHeader Map<String,String> headers){
		return transportistaService.verificarRegistro(headers);
	}
	@PostMapping(path = "/registrar" )
	public ResponseEntity<GenericResponse> terminarRegistro(@RequestHeader Map<String,String> headers,@RequestBody Transportista obj){
		return transportistaService.terminarRegistro(headers, obj);
	}

	@GetMapping(path = "/apoderados" )
	public ResponseEntity<GenericResponse> apoderados(@RequestHeader Map<String,String> headers){
		return transportistaService.apoderados(headers);
	}
	@RequestMapping(path = "/apoderado/{rutAp}",method = RequestMethod.DELETE )
	public ResponseEntity<GenericResponse> eliminarApoderado(@RequestHeader Map<String,String> headers,@PathVariable("rutAp") String rutApoderado){
		return transportistaService.eliminarApoderado(headers, rutApoderado);
	}

	@GetMapping(path = "/ninos/{idColegio}" )
	public ResponseEntity<GenericResponse> apoderados(@RequestHeader Map<String,String> headers,@PathVariable("idColegio") Long idColegio){
		return transportistaService.ninos(headers,idColegio);
	}

	@GetMapping(path = "/asistentes/{fecha}" )
	public ResponseEntity<GenericResponse> apoderados(@RequestHeader Map<String,String> headers,@PathVariable("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha){
		return transportistaService.asistentes(headers,fecha);
	}
	
}