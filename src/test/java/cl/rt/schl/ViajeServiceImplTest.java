package cl.rt.schl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.entity.Viaje;
import cl.rt.schl.repository.DetalleViajeRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.repository.ViajeRepository;
import cl.rt.schl.services.ViajeServiceImpl;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;

class ViajeServiceImplTest {

	@InjectMocks
	ViajeServiceImpl service;

	@Mock
	ViajeRepository repository;
	@Mock
	DetalleViajeRepository repositoryD;
	@Mock
	TransportistaRepository repositoryT;
	@Mock
	AuthFirebaseUtil auth;
	@Mock
	NinoRepository repositoryN;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testAgregarViaje() {
		Transportista t = new Transportista();

		Nino n1 = new Nino();

		DetalleViaje dv1 = new DetalleViaje();
		dv1.setNino(n1);

		List<DetalleViaje> detalleViajes = new ArrayList<DetalleViaje>();
		detalleViajes.add(dv1);

		Viaje v1 = new Viaje();

		when(repositoryT.findByUid(any())).thenReturn(t);
		when(repository.save(any())).thenReturn(v1);

		when(auth.UIDByTokenHeader(any())).thenReturn("");

		GenericResponse response = (GenericResponse) service.agregarViaje(null, detalleViajes).getBody();

		assertEquals("Viaje iniciado", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(v1, response.getResponse());
	}

	@Test
	void testTerminarViaje() {
		Transportista t = new Transportista();
		t.setRut("rutT");

		Nino n1 = new Nino();

		DetalleViaje dv1 = new DetalleViaje();
		dv1.setNino(n1);

		List<DetalleViaje> detalleViajes = new ArrayList<DetalleViaje>();
		detalleViajes.add(dv1);

		Viaje v1 = new Viaje();
		v1.setIdViaje((long) 2);

		when(repositoryT.findByUid(any())).thenReturn(t);
		when(repository.findById(any())).thenReturn(Optional.of(v1));
		when(repositoryD.findByNinoAndViaje(any(),any())).thenReturn(dv1);
		when(repository.save(any())).thenReturn(v1);

		when(auth.UIDByTokenHeader(any())).thenReturn("");

		GenericResponse response = (GenericResponse) service.terminarViaje(null, detalleViajes, (long) 2).getBody();

		assertEquals("Viaje terminado", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(v1, response.getResponse());

	}

	@Test
	void testDatosPorMesAnioYRutT() {
		Transportista t = new Transportista();
		t.setRut("rutT");
		
		Nino n1 = new Nino();
		DetalleViaje dv1 = new DetalleViaje();
		dv1.setId((long) 33);
		dv1.setNino(n1);

		List<DetalleViaje> detalleViajes = new ArrayList<DetalleViaje>();
		detalleViajes.add(dv1);

		Viaje v1 = new Viaje();
		v1.setIdViaje((long) 2);
		
		List<Viaje> viajes = new ArrayList<Viaje>();
		viajes.add(v1);
		
		when(auth.UIDByTokenHeader(any())).thenReturn("");
		when(repositoryT.findByUid(any())).thenReturn(t);
		when(repository.getByMonthAndYearAndRutT(anyInt(), anyInt(),any())).thenReturn(viajes);

		
		GenericResponse response = (GenericResponse) service.datosPorMesAnioYRutT(null, 11,2020).getBody();

		assertEquals("datos csv", response.getMessage());
		assertEquals(200, response.getCode());
		assertEquals(viajes, response.getResponse());

	}

	@Test
	void testDatosPorMesAnioYRutTTokenInvalido() {
		Transportista t = new Transportista();
		t.setRut("rutT");
		
		Nino n1 = new Nino();
		DetalleViaje dv1 = new DetalleViaje();
		dv1.setId((long) 33);
		dv1.setNino(n1);

		List<DetalleViaje> detalleViajes = new ArrayList<DetalleViaje>();
		detalleViajes.add(dv1);

		Viaje v1 = new Viaje();
		v1.setIdViaje((long) 2);
		
		List<Viaje> viajes = new ArrayList<Viaje>();
		viajes.add(v1);
		
		when(auth.UIDByTokenHeader(any())).thenReturn(null);
		when(repositoryT.findByUid(any())).thenReturn(t);
		when(repository.getByMonthAndYearAndRutT(anyInt(), anyInt(),any())).thenReturn(viajes);

		
		GenericResponse response = (GenericResponse) service.datosPorMesAnioYRutT(null, 11,2020).getBody();

		assertEquals("Unauthorized", response.getMessage());
		assertEquals(401, response.getCode());
		assertEquals("Unauthorized", response.getResponse());

	}
}
