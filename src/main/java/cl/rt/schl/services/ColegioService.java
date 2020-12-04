package cl.rt.schl.services;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Transportista;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface ColegioService {

    ResponseEntity colegiosPorRutT(Map<String,String> headers);
    ResponseEntity registrarColegioPorRutT(Map<String,String> headers,Colegio colegio);
    ResponseEntity delete(Map<String,String> headers,Long colegio);
    ResponseEntity editar(Map<String,String> headers,Long idColegio, Colegio colegio);

}
