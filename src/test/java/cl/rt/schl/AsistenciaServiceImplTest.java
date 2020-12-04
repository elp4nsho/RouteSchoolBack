package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.AsistenciaRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.services.AsistenciaServiceImpl;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.GenericResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;

class AsistenciaServiceImplTest {
	@InjectMocks
	AsistenciaServiceImpl service;
	@Mock
	AsistenciaRepository repository;
	@Mock
	NinoRepository repositoryN;
	@Mock
	AuthApoderado auth;
	@Mock
	ApoderadoRepository repositoryA;
	
	
	Apoderado apoderadoTest = new Apoderado();
	
	HashMap<String, String> headers = new HashMap<String, String>();

	GenericResponse respuesta(GenericResponse resp) {
		GenericResponse res = (GenericResponse) resp;
		return res;
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCrearAsistencia() {
		Nino n = new Nino();
		apoderadoTest.setRutAp("rutTest");
		n.setApoderado(apoderadoTest);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderadoTest);
		when(repositoryN.findByRutN(any())).thenReturn(n);
		Asistencia asistencia = new Asistencia();
		asistencia.setFecha(LocalDate.now().plusDays(1));
		asistencia.setEstado("2");
		when(repository.save(any())).thenReturn(asistencia);

		GenericResponse response = respuesta(service.crear(headers, asistencia, "rutN").getBody());
	

		assertEquals("Inasistencia creada", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(asistencia, response.getResponse());
		
	}
	
	@Test
	void testCrearAsistenciaFechaPasada() {
		Nino n = new Nino();
		apoderadoTest.setRutAp("rutTest");
		n.setApoderado(apoderadoTest);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderadoTest);
		when(repositoryN.findByRutN(any())).thenReturn(n);
		Asistencia asistencia = new Asistencia();
		asistencia.setFecha(LocalDate.now());
		asistencia.setEstado("2");
		when(repository.save(any())).thenReturn(asistencia);

		GenericResponse response = respuesta(service.crear(headers, asistencia, "rutN").getBody());
	

		assertEquals("Error de datos", response.getMessage());
		assertEquals(400, response.getCode());
		assertEquals("Error de datos", response.getResponse());
		
	}
	
	@Test
	void testCrearAsistenciaTokenInvalido() {
		Nino n = new Nino();
		apoderadoTest.setRutAp("rutTest");
		n.setApoderado(apoderadoTest);
		when(auth.validarApoderado(any(), any())).thenReturn(null);
		when(repositoryN.findByRutN(any())).thenReturn(n);
		Asistencia asistencia = new Asistencia();
		asistencia.setFecha(LocalDate.now());
		asistencia.setEstado("2");
		when(repository.save(any())).thenReturn(asistencia);

		GenericResponse response = respuesta(service.crear(headers, asistencia, "rutN").getBody());
	

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());
		
	}

	@Test
	void testCrearAsistenciaErrorRutN() {
		Nino n = new Nino();
		apoderadoTest.setRutAp("rutTest");
		n.setApoderado(apoderadoTest);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderadoTest);
		when(repositoryN.findByRutN(any())).thenReturn(null);
		Asistencia asistencia = new Asistencia();
		asistencia.setFecha(LocalDate.now());
		asistencia.setEstado("2");
		when(repository.save(any())).thenReturn(asistencia);

		GenericResponse response = respuesta(service.crear(headers, asistencia, "rutN").getBody());
	

		assertEquals("Niño no encontrado", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals("Niño no encontrado", response.getResponse());
		
	}
	

}
