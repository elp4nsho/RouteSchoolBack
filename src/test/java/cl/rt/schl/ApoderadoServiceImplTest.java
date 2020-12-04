package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.services.ApoderadoServiceImpl;
import cl.rt.schl.services.TransportistaServiceImpl;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.GenericResponse;

class ApoderadoServiceImplTest {
	@InjectMocks
	ApoderadoServiceImpl service;

	@Mock
	ApoderadoRepository repository;
	@Mock
	ColegioRepository repositoryC;
	@Mock
	LinkRepository repositoryL;
	@Mock
	NinoRepository repositoryN;
	@Mock
	AuthApoderado auth;
	final Link linkTest = new Link();
	final Colegio colegioTest = new Colegio();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		// Creando el colegio de pruebas
		colegioTest.setRutT("rut test");
		colegioTest.setIdColegio((long) 22);
		Optional<Colegio> co = Optional.of(colegioTest);
		when(repositoryC.findById(anyLong())).thenReturn(co);

		// Creando el link unico de pruebas
		linkTest.setLink("linkTest");
		linkTest.setRutT("rut test");
		when(repositoryL.findByLink(anyString())).thenReturn(linkTest);
	}

	@Test
	void testRegistrarApoderadoExitoso() {
		Apoderado apoderado = new Apoderado();
		apoderado.setClave("testing");
		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("-30.2323,70.34324");
		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);
		Optional<Apoderado> apoderadoOpt = Optional.of(apoderado);
		when(repository.findById(anyString())).thenReturn(apoderadoOpt);
		GenericResponse response = (GenericResponse) service.registrarApoderado("linkUnico", apoderado).getBody();
		assertEquals("ok", response.getResponse());
		assertEquals(200, response.getCode());
		assertEquals("Apoderado guardado", response.getMessage());

	}

	@Test
	void testRegistrarApoderadoLinkInvalido() {
		when(repositoryL.findByLink(anyString())).thenReturn(null);

		Apoderado apoderado = new Apoderado();
		apoderado.setClave("testing");
		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("-30.2323,70.34324");
		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);
		GenericResponse response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Unauthorized", response.getResponse());
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());

	}

	@Test
	void testRegistrarApoderadoDatosInvalidos() {

		Apoderado apoderado = new Apoderado();
		GenericResponse response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Rut invalido", response.getResponse());
		assertEquals("Rut invalido", response.getMessage());
		assertEquals(400, response.getCode());

		apoderado.setRutAp("asd-d");
		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Rut invalido", response.getResponse());
		assertEquals("Rut invalido", response.getMessage());
		assertEquals(400, response.getCode());

		apoderado.setRutAp("11111111-2");
		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Rut invalido", response.getResponse());
		assertEquals("Rut invalido", response.getMessage());
		assertEquals(400, response.getCode());

		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("mi casita");

		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Direccion incorrecta", response.getResponse());
		assertEquals("Direccion incorrecta", response.getMessage());
		assertEquals(400, response.getCode());

		apoderado.setDireccion("989889,909989");

		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("La clave debe contener almenos 8 caracteres", response.getResponse());
		assertEquals("La clave debe contener almenos 8 caracteres", response.getMessage());
		assertEquals(400, response.getCode());

		apoderado.setClave("12345678");

		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("Sin niños", response.getResponse());
		assertEquals("Sin niños", response.getMessage());
		assertEquals(400, response.getCode());

		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);

		response = (GenericResponse) service.registrarApoderado("link", apoderado).getBody();
		assertEquals("ok", response.getResponse());
		assertEquals("Apoderado guardado", response.getMessage());
		assertEquals(200, response.getCode());

	}

	@Test
	void testObtenerColegios() {

		List<Colegio> colegios = new ArrayList<Colegio>();

		Colegio c1 = new Colegio();
		c1.setNombre("colegio uno");
		Colegio c2 = new Colegio();
		c2.setNombre("colegio dos");
		colegios.add(c1);
		colegios.add(c2);
		when(repositoryC.findByRutT(anyString())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.obtenerColegios("link").getBody();

		assertEquals("Colegios registrados", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(colegios, response.getResponse());

	}

	@Test
	void testObtenerColegiosLinkInvalido() {
		when(repositoryL.findByLink(anyString())).thenReturn(null);

		GenericResponse response = (GenericResponse) service.obtenerColegios("link").getBody();

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}

	@Test
	void testObtenerColegiosSinColegios() {
		List<Colegio> colegios = new ArrayList<Colegio>();

		when(repositoryC.findByRutT(anyString())).thenReturn(colegios);

		GenericResponse response = (GenericResponse) service.obtenerColegios("link").getBody();

		assertEquals("Colegios registrados", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(colegios, response.getResponse());

	}

	@Test
	void testObtenerColegiosErrorDb() {
		when(repositoryC.findByRutT(anyString())).thenReturn(null);

		GenericResponse response = (GenericResponse) service.obtenerColegios("link").getBody();

		assertEquals("Problemas al obtener colegios", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals("Problemas al obtener colegios", response.getResponse());

	}

	@Test
	void testLoginApoderadoExito() {
		Apoderado apoderado = new Apoderado();
		apoderado.setClave("testing");
		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("-30.2323,70.34324");
		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);

		when(auth.validarApoderado(any(), any())).thenReturn(apoderado);
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("authorization", "Basic MTExMTExMTEtMTp0ZXN0aW5n");

		HttpServletResponse httpServletResponse = new MockHttpServletResponse();

		GenericResponse response = (GenericResponse) service.loginApoderado(headers, httpServletResponse).getBody();

		assertEquals("LOGIN OK", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(apoderado, response.getResponse());
	}

	@Test
	void testLoginApoderadoApoderadoNoEncontrado() {

		when(auth.validarApoderado(any(), any())).thenReturn(null);

		GenericResponse response = (GenericResponse) service.loginApoderado(null, null).getBody();

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());
	}

	@Test
	void testSalirApoderadoExitoso() {
		Apoderado apoderado = new Apoderado();
		apoderado.setClave("testing");
		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("-30.2323,70.34324");
		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderado);
		HttpServletResponse httpServletResponse = new MockHttpServletResponse();

		GenericResponse response = (GenericResponse) service.salirApoderado(null, httpServletResponse).getBody();
		
		assertEquals("salio", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals("salio", response.getResponse());
		assertEquals("credentials=; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT", httpServletResponse.getHeaders("Set-Cookie").toArray()[0]);

	}
	@Test
	void testSalirApoderadoInvalido() {
		when(auth.validarApoderado(any(), any())).thenReturn(null);

		GenericResponse response = (GenericResponse) service.salirApoderado(null, null).getBody();
		
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}
	@Test void testEditarCasoExito() { 
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("authorization", "Basic MTExMTExMTEtMTp0ZXN0aW5n");
		
		Apoderado apoderado = new Apoderado();
		apoderado.setClave("testing");
		apoderado.setRutAp("11111111-1");
		apoderado.setDireccion("-30.2323,70.34324");
		Nino ninoTest = new Nino();
		ninoTest.setRutN("22222222-2");
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderado);
		when(repository.save(any())).thenReturn(apoderado);

		
		GenericResponse response = (GenericResponse) service.editar(headers, apoderado).getBody();

		assertEquals("Editado", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(apoderado, response.getResponse());
		
	}
	@Test void testEditarCasoDatosInvalidos() { 
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("authorization", "Basic MTExMTExMTEtMTp0ZXN0aW5n");
		
		Apoderado apoderado = new Apoderado();
		apoderado.setDireccion("jj");
		Nino ninoTest = new Nino();
		ninoTest.setDireccion("-30.2323,70.34324");
		ninoTest.setColegio(colegioTest);
		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(ninoTest);
		apoderado.setNino(ninos);
		when(auth.validarApoderado(any(), any())).thenReturn(apoderado);
		when(repository.save(any())).thenReturn(apoderado);

		
		GenericResponse response = (GenericResponse) service.editar(headers, apoderado).getBody();

		assertEquals("Datos invalidos", response.getMessage());
		assertEquals(400, response.getCode());
		assertEquals("Datos invalidos", response.getResponse());
	}
	
}
