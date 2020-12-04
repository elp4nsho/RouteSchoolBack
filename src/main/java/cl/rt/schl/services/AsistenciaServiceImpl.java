package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.AsistenciaRepository;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author Francisco
 *
 */
@Service
public class AsistenciaServiceImpl implements AsistenciaService {

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

	AuthApoderado authApoderado = new AuthApoderado();

	/**
	 * <h1>crear(headers,Asistencia,rutNino)</h1>
	 * <p>
	 * Este metodo se encarga de registrar una inasistencia para un niño en
	 * especifico
	 * </p>
	 * El metodo se encarga de validar las credenciales y generar la inasistencia en
	 * base al rut otorgado
	 *
	 * @param headers    encabezados HTTP de la peticion
	 * @param asistencia objeto Asistencia usado para persistir en base de datos
	 * @param rutNino    rut del nino al cual se le quiere dejar la inasistencia
	 * @return GenericResponse conteniendo un objeto Asistencia
	 * @see Apoderado
	 * @see Asistencia
	 * @see Nino
	 * @see AuthApoderado
	 */
	public ResponseEntity<GenericResponse> crear(Map<String, String> headers, Asistencia asistencia, String rutNino) {
		GenericResponse genericResponse = new GenericResponse();

		Apoderado valido = authApoderado.validarApoderado(headers, apoderadoRepository.findAll());

		if (valido != null) {

			if (ninoRepository.findByRutN(rutNino) != null
					&& ninoRepository.findByRutN(rutNino).getApoderado().getRutAp().equals(valido.getRutAp())) {

				
				if(asistencia.getFecha().isAfter(LocalDate.now())) {

					asistencia.setNino(ninoRepository.findByRutN(rutNino));
					asistencia.setRutT(valido.getRutT());
					genericResponse.setCode(200);
					genericResponse.setMessage("Inasistencia creada");
					genericResponse.setResponse(asistenciaRepository.save(asistencia));
				}else {
					genericResponse.setCode(400);
					genericResponse.setMessage("Error de datos");
					genericResponse.setResponse("Error de datos");
				}
				
			

			} else {
				genericResponse.setCode(200);
				genericResponse.setMessage("Niño no encontrado");
				genericResponse.setResponse("Niño no encontrado");
			}

		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

}
