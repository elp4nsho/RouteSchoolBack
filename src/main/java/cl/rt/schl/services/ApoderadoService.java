package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface ApoderadoService {


	ResponseEntity registrarApoderado(String id, Apoderado apoderado);

	ResponseEntity obtenerColegios(String id);

	ResponseEntity loginApoderado(Map<String, String> headers, HttpServletResponse response);
	ResponseEntity salirApoderado(Map<String, String> headers, HttpServletResponse response);

	ResponseEntity editar(Map<String, String> headers, Apoderado apoderado);

	String showlogin();

	String mostrarFormulario(String linkId);

	String mostrarHome(HttpServletRequest request);

	String mostrarEditar(HttpServletRequest request);

	String mostrarMonitoreo(HttpServletRequest request);

	String mostrarInasistencia(HttpServletRequest request);

}
