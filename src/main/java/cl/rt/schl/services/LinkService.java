package cl.rt.schl.services;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Transportista;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface LinkService {
    ResponseEntity generarLinkRegistro(Map<String,String> headers);

}
