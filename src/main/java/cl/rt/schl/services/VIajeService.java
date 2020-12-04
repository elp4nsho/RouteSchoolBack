package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.entity.Viaje;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface VIajeService {

    ResponseEntity agregarViaje(Map<String,String> headers,List<DetalleViaje> viaje);
    
    ResponseEntity terminarViaje(Map<String,String> headers,List<DetalleViaje> viaje,Long id);
    ResponseEntity datosPorMesAnioYRutT(Map<String,String> headers,int mes,int anio);

}
