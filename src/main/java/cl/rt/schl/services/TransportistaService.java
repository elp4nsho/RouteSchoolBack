package cl.rt.schl.services;

import cl.rt.schl.entity.Transportista;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface TransportistaService {

    ResponseEntity verificarRegistro(Map<String,String> headers);
    ResponseEntity terminarRegistro(Map<String,String> headers,Transportista transportista);
    ResponseEntity apoderados(Map<String,String> headers);
    ResponseEntity ninos(Map<String,String> headers,Long idColegio);
    ResponseEntity asistentes(Map<String,String> headers,LocalDate fecha);

    
    

}
