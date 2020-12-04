package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.entity.Viaje;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.AsistenciaRepository;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.DetalleViajeRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.repository.ViajeRepository;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ViajeServiceImpl implements VIajeService {

	private AuthFirebaseUtil authFirebaseUtil = new AuthFirebaseUtil();

	@Autowired
	private LinkRepository linkRepository;
	@Autowired
	private TransportistaRepository transportistaRepository;
	@Autowired
	private ApoderadoRepository apoderadoRepository;
	@Autowired
	private NinoRepository ninoRepository;
	@Autowired
	private AsistenciaRepository asistenciaRepository;
	@Autowired
	private ViajeRepository viajeRepository;
	@Autowired
	private DetalleViajeRepository detalleViajeRepository;

	AuthApoderado authApoderado = new AuthApoderado();

	/**<h1>agregarViaje(headers,listaDetalleViajes)</h1>
	*<p> Este metodo se encarga de iniciar un viaje y rellenar los ninos asociados a este
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut del transportista
	* para generar un nuevo viaje en base a el, tambien recorre la lista de detalles
	* para rellenar los detalles del viaje(niños)
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  viaje  Array de detalledeviaje el cual contiene info de los niños esperados
	* @return      GenericResponse conteniendo un objeto de viaje
	* @see         DetalleViaje
	* @see         Viaje
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity<GenericResponse> agregarViaje(Map<String, String> headers, List<DetalleViaje> viaje) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		if (UIDPeticion != null) {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			LocalDate hoy = LocalDate.now();
			Viaje viajeO = new Viaje();
			viajeO.setRutT(rutPeticion);
			OffsetDateTime dateTime = OffsetDateTime.now();
			viajeO.setFechaInicio(dateTime);
			viajeO.setEstado("0");
			viajeO.setDia(hoy);
			Viaje viajeGuardado = viajeRepository.save(viajeO);

			for (DetalleViaje detViaje : viaje) {
				detViaje.setViaje(viajeGuardado);
				detViaje.setNino(ninoRepository.findByRutN(detViaje.getNino().getRutN()));
				detalleViajeRepository.save(detViaje);
			}
			genericResponse.setCode(200);
			genericResponse.setMessage("Viaje iniciado");
			genericResponse.setResponse(viajeGuardado);

		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}
	/**<h1>terminarViaje(headers,listaDetalleViajes,idViaje)</h1>
	*<p> Este metodo se encarga de terminar un viaje y rellenar los ninos asociados a este
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut del transportista
	* para terminar el viaje otorgado por el idViaje, este rellena los estados correspondientes
	* al resultado final del viaje
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  viaje  Array de detalledeviaje el cual contiene info de los niños esperados
	* @param  id del viaje que se quiere terminar
	* @return      GenericResponse conteniendo un objeto de viaje
	* @see         DetalleViaje
	* @see         Viaje
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity<GenericResponse> terminarViaje(Map<String, String> headers, List<DetalleViaje> viaje,
			Long id) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		if (UIDPeticion != null) {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			Optional<Viaje> v = viajeRepository.findById(id);
			if (v != null) {
				OffsetDateTime dateTime = OffsetDateTime.now();
				v.get().setFechaTermino(dateTime);
				v.get().setEstado("1");
				for (DetalleViaje detViaje : viaje) {
					DetalleViaje elDetalleViaje = detalleViajeRepository.findByNinoAndViaje(
							ninoRepository.findByRutN(detViaje.getNino().getRutN()),
							viajeRepository.findById(id).get());
					System.out.println(elDetalleViaje);
					elDetalleViaje.setAsistio(detViaje.getAsistio());
					detalleViajeRepository.save(elDetalleViaje);
				}
				genericResponse.setCode(200);
				genericResponse.setMessage("Viaje terminado");
				genericResponse.setResponse(viajeRepository.save(v.get()));
			} else {
				genericResponse.setCode(401);
				genericResponse.setMessage("Unauthorized");
				genericResponse.setResponse("viaje no existe");
			}

		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}
	/**<h1>datosPorMesAnioYRutT(headers,mes,anio)</h1>
	*<p> Este metodo se encarga de obtener los historicos de un mes completo 
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut del transportista
	* y obtener los viajes asociados al rut, mes y anio
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  mes mes de los registros
	* @param  anio año de los registros
	* @return      GenericResponse conteniendo un array de viaje
	* @see         Viaje
	* @see         GenericResponse
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity<GenericResponse> datosPorMesAnioYRutT(Map<String, String> headers, int mes, int anio) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		if (UIDPeticion != null) {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			genericResponse.setCode(200);
			genericResponse.setMessage("datos csv");
			genericResponse.setResponse(viajeRepository.getByMonthAndYearAndRutT(mes, anio, rutPeticion));

		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	

}
