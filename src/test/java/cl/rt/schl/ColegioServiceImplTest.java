package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.api.client.repackaged.com.google.common.base.Optional;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.services.ColegioServiceImpl;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;

class ColegioServiceImplTest {

	@InjectMocks
	ColegioServiceImpl service;
	@Mock
	ColegioRepository repository;
	@Mock
	TransportistaRepository repositoryT;
	@Mock
	AuthFirebaseUtil auth;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void testColegiosPorRutT() {
		HashMap<String, String> headers = new HashMap<String, String>();

		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		Colegio c2 = new Colegio();
		c2.setNombre("colegio dos");
		colegios.add(c1);
		colegios.add(c2);
		when(auth.UIDByTokenHeader(headers)).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.colegiosPorRutT(headers).getBody();

		assertEquals("colegios", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(colegios, response.getResponse());

	}

	@Test
	void testColegiosPorRutTInvalidoToken() {
		HashMap<String, String> headers = new HashMap<String, String>();

		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		Colegio c2 = new Colegio();
		c2.setNombre("colegio dos");
		colegios.add(c1);
		colegios.add(c2);
		when(auth.UIDByTokenHeader(headers)).thenReturn(null);
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.colegiosPorRutT(headers).getBody();

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}
	@Test
	void testRegistrarColegioPorRutT() {
		HashMap<String, String> headers = new HashMap<String, String>();
		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setDireccion("30.2332,70.8329");
		when(auth.UIDByTokenHeader(headers)).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.save(any())).thenReturn(c1);

		GenericResponse response = (GenericResponse) service.registrarColegioPorRutT(headers, c1).getBody();

		assertEquals("colegios", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(c1, response.getResponse());
		
	}
	@Test
	void testRegistrarColegioPorRutTDireccionIncorrecta() {
		HashMap<String, String> headers = new HashMap<String, String>();
		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		when(auth.UIDByTokenHeader(headers)).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		
		GenericResponse response = (GenericResponse) service.registrarColegioPorRutT(headers, c1).getBody();

		assertEquals("Direccion incorrecta", response.getMessage());
		assertEquals(400, response.getCode());
		assertEquals("Direccion incorrecta", response.getResponse());
		
	}
	@Test
	void testDelete() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setIdColegio((long) 2);
		colegios.add(c1);
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.delete(null, (long) 2).getBody();
		assertEquals("colegios", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals( colegios, response.getResponse());
		
	}
	@Test
	void testDeleteColegioAleatorio() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setIdColegio((long) 98962562);
		colegios.add(c1);
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.delete(null, (long) 23).getBody();
		assertEquals("recurso no encontrado", response.getMessage());
		assertEquals(403, response.getCode());
		assertEquals( "recurso no encontrado", response.getResponse());
		
	}
	@Test
	void testDeleteTokenInvalido() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setIdColegio((long) 2);
		colegios.add(c1);
		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.delete(null, (long) 2).getBody();
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals( "Unauthorized", response.getResponse());
		
	}
	@Test
	void testEditar() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setIdColegio((long) 2);
		colegios.add(c1);
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);
		when(repository.save(any())).thenReturn(c1);

		GenericResponse response = (GenericResponse) service.editar(null, (long) 2,c1).getBody();
		
		assertEquals("colegio", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals( c1, response.getResponse());
		
		c1.setDireccion("30.3543,-70.435345");

	}
	

	@Test
	void editarColegioConDireccionIncorrecta() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		c1.setIdColegio((long) 2);
		c1.setDireccion("direccion falsa");
		colegios.add(c1);
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repositoryT.findByUid(anyString())).thenReturn(new Transportista());
		when(repository.findByRutT(any())).thenReturn(colegios);
		when(repository.save(any())).thenReturn(c1);

		GenericResponse response = (GenericResponse) service.editar(null, (long) 2,c1).getBody();
		
		assertEquals("Error de datos", response.getMessage());
		assertEquals(400, response.getCode());
		assertEquals( "Error de datos", response.getResponse());
		
		

	}
	
}
