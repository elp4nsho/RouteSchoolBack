package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.AsistenciaRepository;
import cl.rt.schl.repository.DetalleViajeRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.services.TransportistaServiceImpl;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;

class TransportistaServiceImplTest {

	@InjectMocks
	TransportistaServiceImpl service;
	@Mock
	TransportistaRepository repository;
	@Mock
	AuthFirebaseUtil auth;
	@Mock
	ApoderadoRepository repositoryA;
	@Mock
	AsistenciaRepository repositoryAs;
	@Mock
	DetalleViajeRepository repositorydV;
	@Mock
	NinoRepository repositoryN;

	HashMap<String, String> headers = new HashMap<String, String>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testVerificarRegistro() {
		Transportista t = new Transportista();

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(t);

		GenericResponse response = (GenericResponse) service.verificarRegistro(null).getBody();

		assertEquals("OK", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(t, response.getResponse());

	}

	@Test
	void testVerificarRegistroTokenInvalido() {
		Transportista t = new Transportista();

		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repository.findByUid(any())).thenReturn(t);

		GenericResponse response = (GenericResponse) service.verificarRegistro(null).getBody();

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}

	@Test
	void testVerificarRegistroUIDNoRegistrado() {
		Transportista t = new Transportista();

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(null);

		GenericResponse response = (GenericResponse) service.verificarRegistro(null).getBody();

		assertEquals("Registro Incompleto", response.getMessage());
		assertEquals(204, response.getCode());
		assertEquals("Registro Incompleto", response.getResponse());

	}

	@Test
	void testTerminarRegistro() throws FirebaseAuthException {
		Transportista t = new Transportista();
		t.setRut("11111111-1");
		t.setDireccion("-30.923243,70.3434");

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(t);

		GenericResponse response = (GenericResponse) service.terminarRegistro(headers, t).getBody();
		assertEquals("Ya se encuentra registrado", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals("Ya se encuentra registrado", response.getResponse());
	}

	@Test
	void testTerminarRegistroRutIncompleto() throws FirebaseAuthException {
		Transportista t = new Transportista();
		t.setRut("111111111");
		t.setDireccion("-30.923243,70.3434");

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(t);

		GenericResponse response = (GenericResponse) service.terminarRegistro(headers, t).getBody();
		assertEquals("Datos incompletos", response.getMessage());
		assertEquals(400, response.getCode());
		assertEquals("Datos incompletos", response.getResponse());
	}

	@Test
	void testApoderados() {
		Transportista t = new Transportista();

		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		Apoderado a1 = new Apoderado();
		Apoderado a2 = new Apoderado();

		apoderados.add(a1);
		apoderados.add(a2);

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(t);
		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.apoderados(null).getBody();
		assertEquals("apoderados", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(apoderados, response.getResponse());

	}

	@Test
	void testApoderadosVacio() {
		Transportista t = new Transportista();

		List<Apoderado> apoderados = new ArrayList<Apoderado>();

		when(auth.UIDByTokenHeader(any())).thenReturn("TESTUID");
		when(repository.findByUid(any())).thenReturn(t);
		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.apoderados(null).getBody();
		assertEquals("apoderados", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(apoderados, response.getResponse());

	}

	@Test
	void testApoderadosTokenInvalido() {
		Transportista t = new Transportista();

		List<Apoderado> apoderados = new ArrayList<Apoderado>();

		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repository.findByUid(any())).thenReturn(t);
		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.apoderados(null).getBody();
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}

	@Test
	void testEliminarApoderado() {
		Transportista t = new Transportista();

		Apoderado a1 = new Apoderado();
		Apoderado a2 = new Apoderado();

		a1.setRutAp("rutApoderado");
		Nino n1 = new Nino();

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		a1.setNino(ninos);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);

		when(repositoryA.findByRutT(any())).thenReturn(apoderados);
		when(repository.findByUid(any())).thenReturn(t);
		when(auth.UIDByTokenHeader(any())).thenReturn("");

		GenericResponse response = (GenericResponse) service.eliminarApoderado(headers, "rutApoderado").getBody();
		assertEquals("apoderados", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(apoderados, response.getResponse());

	}

	@Test
	void testEliminarApoderadoRutNoExiste() {
		Transportista t = new Transportista();

		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Nino n1 = new Nino();

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		a1.setNino(ninos);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);

		when(repositoryA.findByRutT(any())).thenReturn(apoderados);
		when(repository.findByUid(any())).thenReturn(t);
		when(auth.UIDByTokenHeader(any())).thenReturn("");

		GenericResponse response = (GenericResponse) service.eliminarApoderado(headers, "nulo").getBody();
		assertEquals("no existe", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals("no existe", response.getResponse());

	}

	@Test
	void testNinos() {

		Colegio c1 = new Colegio();
		c1.setIdColegio((long) 1);
		Colegio c2 = new Colegio();
		c2.setIdColegio((long) 2);

		Transportista t = new Transportista();
		t.setRut("rutT");
		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Apoderado a2 = new Apoderado();
		a1.setRutAp("rutApoderado2");
		Nino n1 = new Nino();
		n1.setNombre("nino1");
		n1.setColegio(c1);
		Nino n2 = new Nino();
		n2.setNombre("nino2");
		n2.setColegio(c2);

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		Set<Nino> ninos2 = new HashSet<Nino>();
		ninos2.add(n2);

		a1.setNino(ninos);
		a2.setNino(ninos2);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);

		ArrayList<Nino> arrayN = new ArrayList<Nino>();
		arrayN.add(n1);

		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repository.findByUid(any())).thenReturn(t);

		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.ninos(headers, (long) 1).getBody();
		assertEquals("ninos", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(arrayN, response.getResponse());

	}

	@Test
	void testNinosColegioSinNinos() {

		Colegio c1 = new Colegio();
		c1.setIdColegio((long) 1);
		Colegio c2 = new Colegio();
		c2.setIdColegio((long) 2);

		Transportista t = new Transportista();
		t.setRut("rutT");
		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Apoderado a2 = new Apoderado();
		a1.setRutAp("rutApoderado2");
		Nino n1 = new Nino();
		n1.setNombre("nino1");
		n1.setColegio(c1);
		Nino n2 = new Nino();
		n2.setNombre("nino2");
		n2.setColegio(c2);

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		Set<Nino> ninos2 = new HashSet<Nino>();
		ninos2.add(n2);

		a1.setNino(ninos);
		a2.setNino(ninos2);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);

		ArrayList<Nino> arrayN = new ArrayList<Nino>();

		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repository.findByUid(any())).thenReturn(t);

		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.ninos(headers, (long) 3).getBody();
		assertEquals("ninos", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(arrayN, response.getResponse());

	}

	@Test
	void testNinosTokenInvalido() {

		Colegio c1 = new Colegio();
		c1.setIdColegio((long) 1);
		Colegio c2 = new Colegio();
		c2.setIdColegio((long) 2);

		Transportista t = new Transportista();
		t.setRut("rutT");
		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Apoderado a2 = new Apoderado();
		a1.setRutAp("rutApoderado2");
		Nino n1 = new Nino();
		n1.setNombre("nino1");
		n1.setColegio(c1);
		Nino n2 = new Nino();
		n2.setNombre("nino2");
		n2.setColegio(c2);

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		Set<Nino> ninos2 = new HashSet<Nino>();
		ninos2.add(n2);

		a1.setNino(ninos);
		a2.setNino(ninos2);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);

		ArrayList<Nino> arrayN = new ArrayList<Nino>();

		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repository.findByUid(any())).thenReturn(t);

		when(repositoryA.findByRutT(any())).thenReturn(apoderados);

		GenericResponse response = (GenericResponse) service.ninos(headers, (long) 3).getBody();
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}

	@Test
	void testAsistentes() {

		Colegio c1 = new Colegio();
		c1.setIdColegio((long) 1);
		Colegio c2 = new Colegio();
		c2.setIdColegio((long) 2);

		Transportista t = new Transportista();
		t.setRut("rutT");
		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Apoderado a2 = new Apoderado();
		a1.setRutAp("rutApoderado2");
		Nino n1 = new Nino();
		n1.setNombre("nino1");
		n1.setColegio(c1);
		Nino n2 = new Nino();
		n2.setNombre("nino2");
		n2.setColegio(c2);

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		Set<Nino> ninos2 = new HashSet<Nino>();
		ninos2.add(n2);

		a1.setNino(ninos);
		a2.setNino(ninos2);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);
		
		ArrayList<Nino> arrayN = new ArrayList<Nino>();
		arrayN.add(n1);
		arrayN.add(n2);
		
		
		when(repositoryA.findByRutT(any())).thenReturn(apoderados);
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repository.findByUid(any())).thenReturn(t);

		
		GenericResponse response = (GenericResponse) service.asistentes(headers, LocalDate.now().plusDays(1)).getBody();
		assertEquals("ninos", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(arrayN, response.getResponse());

		
		
	}

	@Test
	void testAsistentesTokenInvalido() {

		Colegio c1 = new Colegio();
		c1.setIdColegio((long) 1);
		Colegio c2 = new Colegio();
		c2.setIdColegio((long) 2);

		Transportista t = new Transportista();
		t.setRut("rutT");
		Apoderado a1 = new Apoderado();
		a1.setRutAp("rutApoderado");
		Apoderado a2 = new Apoderado();
		a1.setRutAp("rutApoderado2");
		Nino n1 = new Nino();
		n1.setNombre("nino1");
		n1.setColegio(c1);
		Nino n2 = new Nino();
		n2.setNombre("nino2");
		n2.setColegio(c2);

		Set<Nino> ninos = new HashSet<Nino>();
		ninos.add(n1);

		Set<Nino> ninos2 = new HashSet<Nino>();
		ninos2.add(n2);

		a1.setNino(ninos);
		a2.setNino(ninos2);
		List<Apoderado> apoderados = new ArrayList<Apoderado>();
		apoderados.add(a1);
		apoderados.add(a2);
		
		ArrayList<Nino> arrayN = new ArrayList<Nino>();
		arrayN.add(n1);
		arrayN.add(n2);
		
		
		when(repositoryA.findByRutT(any())).thenReturn(apoderados);
		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repository.findByUid(any())).thenReturn(t);

		
		GenericResponse response = (GenericResponse) service.asistentes(headers, LocalDate.now().plusDays(1)).getBody();
		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

		
	}

}
