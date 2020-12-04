package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface AsistenciaService {

    ResponseEntity crear(Map<String, String> headers,Asistencia aistencia,String rutNino);


}
