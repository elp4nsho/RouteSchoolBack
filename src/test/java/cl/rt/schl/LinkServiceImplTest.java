package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.services.LinkServiceImpl;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;

class LinkServiceImplTest {
	
	Link linkTest = new Link();
	
	@Mock
	LinkRepository repository;
	@Mock
	TransportistaRepository repositoryT;
	@Mock
	ApoderadoRepository repositoryA;
	@Mock
	AuthFirebaseUtil auth;
	
	@InjectMocks
	LinkServiceImpl service;
	
	HashMap<String, String> headers = new HashMap<String, String>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Transportista t = new Transportista();
		t.setRut("rutTest");
		when(repositoryT.findByUid(anyString())).thenReturn(t);
		when(repository.save(any())).thenReturn(linkTest);

	}
	
	GenericResponse respuesta(GenericResponse resp) {
		GenericResponse res = (GenericResponse) resp;
		return res;
	}

	@Test
	void generarLinkRegistroSinToken() {
		GenericResponse r = respuesta(service.generarLinkRegistro(headers).getBody());
		assertNotNull(r);
		assertEquals(GenericResponse.class, r.getClass());
		assertEquals("Unauthorized", r.getMessage());
		assertEquals("Unauthorized", r.getResponse());
		assertEquals(401, r.getCode());
	}
	@Test
	void generarLinkRegistroTokenmalformado() {	
		headers.put("Authorization", "asdasdas");
		GenericResponse r = respuesta(service.generarLinkRegistro(headers).getBody());
		assertNotNull(r);
		assertEquals(GenericResponse.class, r.getClass());
		assertEquals("Unauthorized", r.getMessage());
		assertEquals("Unauthorized", r.getResponse());
		assertEquals(401, r.getCode());
	}

	@Test
	void generarLinkRegistroTokenInvalido() {	
		headers.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjlhZDBjYjdjMGY1NTkwMmY5N2RjNTI0NWE4ZTc5NzFmMThkOWM3NjYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcm91dGVzY2hvb2xsb2dpbiIsImF1ZCI6InJvdXRlc2Nob29sbG9naW4iLCJhdXRoX3RpbWUiOjE2MDYxOTgyMTUsInVzZXJfaWQiOiJMVEZoRWtnT0lXWXVOU0Z5TlMwYVpiQmM2VTIzIiwic3ViIjoiTFRGaEVrZ09JV1l1TlNGeU5TMGFaYkJjNlUyMyIsImlhdCI6MTYwNjE5ODIxNiwiZXhwIjoxNjA2MjAxODE2LCJlbWFpbCI6ImZjaXN0ZXJuYXN1QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImZjaXN0ZXJuYXN1QGdtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.d3q8HDoOOg84f2ol-47WTcXdLu1dH8WdvNSuHSF5fpl7fMkZqRY-SEw_1B5hnHPX_en5V_QhIcgr7LLcQLYy1f53yFb-rrW16wNCsi-ZJO6qyF_skJYcG1QDhsULlZ15PXSx4OyQ1ip-gF82dbvfVOLad_E-atUErOCMqPppn-wh-s7dFj0Z10-TTlBMDiZn-41k6MrR-Ds0PFY10sfBFUxVBk_wO0Wlhj-mbMjeSEM8PRs-h8LoxruqtLpLYCXHb7HGTrPyh91DkyOsjpaZwaG_escIPe5jITRoexHknxB6WBJcpdAB7_FOsqfuLeO89VC5Hjq5SoGmtL3giMxsJg");
		GenericResponse r = respuesta(service.generarLinkRegistro(headers).getBody());
		assertNotNull(r);
		assertEquals(GenericResponse.class, r.getClass());
		assertEquals("Unauthorized", r.getMessage());
		assertEquals("Unauthorized", r.getResponse());
		assertEquals(401, r.getCode());
	}
	
	
	@Test
	void generarLinkRegistroCasoExitoso() {	
		when(auth.UIDByTokenHeader(anyMap())).thenReturn("UIDPRUEBA");
		GenericResponse r = respuesta(service.generarLinkRegistro(headers).getBody());
		assertNotNull(r);
		assertEquals(GenericResponse.class, r.getClass());
		assertEquals("Link creado", r.getMessage());
		assertEquals(linkTest, r.getResponse());
		assertEquals(200, r.getCode());
	}
	

}
