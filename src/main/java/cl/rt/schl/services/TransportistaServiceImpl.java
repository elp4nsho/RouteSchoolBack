package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.DetalleViaje;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.entity.Transportista;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.AsistenciaRepository;
import cl.rt.schl.repository.DetalleViajeRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;
import cl.rt.schl.utils.RegEx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author Francisco
 *
 */
@Service
public class TransportistaServiceImpl implements TransportistaService {

	private AuthFirebaseUtil authFirebaseUtil = new AuthFirebaseUtil();

	@Autowired
	private TransportistaRepository transportistaRepository;
	@Autowired
	private ApoderadoRepository apoderadoRepository;
	@Autowired
	private AsistenciaRepository asistenciaRepository;
	@Autowired
	private NinoRepository ninoRepository;
	@Autowired
	private DetalleViajeRepository detalleViajeRepository;

	/**<h1>verificarRegistro(headers)</h1>
	*<p> Este metodo se encarga de verificar si el UID de firebase proveniente
	*del token existe en la base de datos de RouterSchool
	* </p>
	* El metodo se encarga de validar las credenciales y realizar la consulta, para
	* retornar una respuesta asociada a si existe o no el registro
	*
	* @param  headers encabezados HTTP de la peticion
	* @return      GenericResponse conteniendo un objeto Transportista
	* @see         Transportista
	* @see AuthFirebaseUtil
	*/
	@Override
	public ResponseEntity verificarRegistro(Map<String, String> headers) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {

			if (transportistaRepository.findByUid(UIDPeticion) == null) {
				genericResponse.setCode(204);
				genericResponse.setMessage("Registro Incompleto");
				genericResponse.setResponse("Registro Incompleto");

			} else {
				genericResponse.setCode(200);
				genericResponse.setMessage("OK");
				genericResponse.setResponse(transportistaRepository.findByUid(UIDPeticion));
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
	/**<h1>terminarRegistro(headers,Transportista)</h1>
	*<p> Este metodo se encarga de registrar a un transportista que no exista en la base
	*de datos pero si este registrado en firebase
	* </p>
	* El metodo se encarga de validar las credenciales y realizar la consulta, para
	* luego decidir si persistir o no la entidad otorgada
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  transportista entidad usada para persistir
	* @return      GenericResponse conteniendo un objeto Transportista
	* @see         Transportista
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity terminarRegistro(Map<String, String> headers, Transportista transportista) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		UserRecord userRecord = null;
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {

			if (transportista.getRut() == null || !RegEx.validaRUT(transportista.getRut()) || !RegEx.validaDireccion(transportista.getDireccion())) {
				genericResponse.setCode(400);
				genericResponse.setMessage("Datos incompletos");
				genericResponse.setResponse("Datos incompletos");
			} else {
				try {
					
					if (transportistaRepository.findByUid(UIDPeticion) != null) {

						genericResponse.setCode(200);
						genericResponse.setMessage("Ya se encuentra registrado");
						genericResponse.setResponse("Ya se encuentra registrado");
					} else {
						userRecord = FirebaseAuth.getInstance().getUser(UIDPeticion);
						transportista.setEmail(userRecord.getEmail());
						transportista.setUid(UIDPeticion);

						genericResponse.setCode(201);
						genericResponse.setMessage("Creado");
						genericResponse.setResponse(transportistaRepository.save(transportista));
					}

				} catch (FirebaseAuthException e) {

					e.printStackTrace();
				}
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
	/**<h1>apoderados(headers)</h1>
	*<p> Este metodo se encarga de entregar un array de apoderados asociados al transportista
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut para consultar
	* los apoderados asociados al rut del transportista
	*
	* @param  headers encabezados HTTP de la peticion
	* @return      GenericResponse conteniendo un array de Apoderado
	* @see         Apoderado
	* @see         Transportista
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity apoderados(Map<String, String> headers) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		System.out.println();
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			genericResponse.setCode(200);
			genericResponse.setMessage("apoderados");
			genericResponse.setResponse(apoderadoRepository.findByRutT(rutPeticion));
		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
	/**<h1>eliminarApoderado(headers,rutApoderado)</h1>
	*<p> Este metodo se encarga de eliminar un apoderado
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut para elminar
	* el apoderado asociado al transportista
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  rutApoderado  rut del apoderado que se desea borrar
	* @return      GenericResponse conteniendo un array de Apoderado
	* @see         Apoderado
	* @see         Transportista
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity eliminarApoderado(Map<String, String> headers, String rutApoderado) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		System.out.println();
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			boolean borrado = false;
			for (Apoderado ap : apoderadoRepository.findByRutT(rutPeticion)) {
				if (ap.getRutAp().equals(rutApoderado)) {
					borrado = true;
						for (Nino n : ap.getNino()) {
							for (Asistencia as : asistenciaRepository.findAll()) {
								if (as.getNino().getRutN().equals(n.getRutN())) {
									asistenciaRepository.deleteById(as.getId());
								}
							}
							for (DetalleViaje dVi : detalleViajeRepository.findAll()) {
								if (dVi.getNino().getRutN().equals(n.getRutN())) {
									detalleViajeRepository.deleteById(dVi.getId());
								}
							}
							try{
								ninoRepository.deleteById(n.getRutN());
							}catch (Exception e) {
								// TODO: handle exception
							}
						}
					break;
				}
				
			}
			if (borrado) {
				apoderadoRepository.deleteById(rutApoderado);
				genericResponse.setCode(200);
				genericResponse.setMessage("apoderados");
				genericResponse.setResponse(apoderadoRepository.findByRutT(rutPeticion));
			} else {

				genericResponse.setCode(200);
				genericResponse.setMessage("no existe");
				genericResponse.setResponse("no existe");
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
	
	/**<h1>ninos(headers,idColegio)</h1>
	*<p> Este metodo se encarga de obtener los niños de un colegio asociado a un
	*transportista
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut del transportista
	* para validar el colegio y luego obtener los niños asociados
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  idColegio del colegio que se desean buscar los niños
	* @return      GenericResponse conteniendo un array de Nino
	* @see         Nino
	* @see         Colegio
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity ninos(Map<String, String> headers, Long idColegio) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		System.out.println();
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			genericResponse.setCode(200);
			genericResponse.setMessage("ninos");
			List<Nino> ninos = new ArrayList<Nino>();

			for (Apoderado ap : apoderadoRepository.findByRutT(rutPeticion)) {
				for (Nino ni : ap.getNino()) {
					if (ni.getColegio().getIdColegio().equals(idColegio)) {
						ninos.add(ni);
					}
				}
			}

			genericResponse.setResponse(ninos);
		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}
	/**<h1>asistentes(headers,fecha)</h1>
	*<p> Este metodo se encarga de obtener los niños asistentes de un dia otorgado
	* </p>
	* El metodo se encarga de validar las credenciales y obtener el rut del transportista
	* para validar el colegio, luego obtener los niños asociados y rellenar con 
	* los estados correspondientes de las inasistencias existentes
	*
	* @param  headers encabezados HTTP de la peticionW
	* @param  fecha dia del cual se quiere saber los asistentes
	* @return      GenericResponse conteniendo un array de Nino
	* @see         Nino
	* @see         Colegio
	* @see AuthFirebaseUtil
	*/
	public ResponseEntity asistentes(Map<String, String> headers, LocalDate fecha) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		System.out.println();
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			genericResponse.setCode(200);
			genericResponse.setMessage("ninos");
			List<Nino> ninos = new ArrayList<Nino>();

			for (Apoderado ap : apoderadoRepository.findByRutT(rutPeticion)) {
				for (Nino ni : ap.getNino()) {
					ninos.add(ni);
				}
			}

			for (Asistencia asi : asistenciaRepository.findByFecha(fecha)) {
				for (Nino ni : ninos) {
					if (asi.getNino().getRutN().equals(ni.getRutN())) {
						ni.setReglas(asi.getEstado());
					}
				}
			}

			genericResponse.setResponse(ninos);
		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}

}
