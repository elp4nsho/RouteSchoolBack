package cl.rt.schl.services;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Asistencia;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;
import cl.rt.schl.utils.RegEx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.firebase.auth.UserRecord;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class ColegioServiceImpl implements ColegioService {

	private AuthFirebaseUtil authFirebaseUtil = new AuthFirebaseUtil();

	@Autowired
	private ColegioRepository colegioRepository;
	@Autowired
	private TransportistaRepository transportistaRepository;

	/**
	 * <h1>colegiosPorRutT(headers)</h1>
	 * <p>
	 * Este metodo se encarga de retornar una lista de colegios en base a un rut
	 * </p>
	 * El metodo se encarga de validar las credenciales del transportista y obtener
	 * la lista de colegios registrados por el
	 *
	 * @param headers encabezados HTTP de la peticion
	 * @return GenericResponse conteniendo un array de Colegio
	 * @see Transportista
	 * @see Colegio
	 * @see AuthFirebaseUtil
	 */
	@Override
	public ResponseEntity<GenericResponse> colegiosPorRutT(Map<String, String> headers) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		UserRecord userRecord = null;
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			if (transportistaRepository.findByUid(UIDPeticion) == null) {
				genericResponse.setCode(401);
				genericResponse.setMessage("Unauthorized");
				genericResponse.setResponse("error no existe usuario asociado");
			} else {
				String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
				genericResponse.setCode(200);
				genericResponse.setMessage("colegios");
				genericResponse.setResponse(colegioRepository.findByRutT(rutPeticion));
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	/**
	 * <h1>registrarColegioPorRutT(headers,Colegio)</h1>
	 * <p>
	 * Este metodo se encarga de registrar un colegio asociado a un transportista
	 * </p>
	 * El metodo se encarga de validar las credenciales y obtener el rut del
	 * transportista para registrar un colegio en base a su rut
	 *
	 * @param headers encabezados HTTP de la peticion
	 * @param colegio entindad usada para persistir en base de datos
	 * @return GenericResponse conteniendo un objeto Colegio que se acaba de crear
	 * @see Colegio
	 * @see Transportista
	 * @see AuthFirebaseUtil
	 */
	public ResponseEntity<GenericResponse> registrarColegioPorRutT(Map<String, String> headers, Colegio colegio) {

		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		UserRecord userRecord = null;
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			if (transportistaRepository.findByUid(UIDPeticion) != null) {

				if (RegEx.validaDireccion(colegio.getDireccion())) {
					String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
					colegio.setRutT(rutPeticion);
					genericResponse.setCode(200);
					genericResponse.setMessage("colegios");
					genericResponse.setResponse(colegioRepository.save(colegio));
				} else {
					genericResponse.setCode(400);
					genericResponse.setMessage("Direccion incorrecta");
					genericResponse.setResponse("Direccion incorrecta");
				}

			} else {
				genericResponse.setCode(401);
				genericResponse.setMessage("Unauthorized");
				genericResponse.setResponse("Unauthorized");
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.ACCEPTED);
	}

	/**
	 * <h1>delete(headers,idColegio)</h1>
	 * <p>
	 * Este metodo se encarga de eliminar un colegio
	 * </p>
	 * El metodo se encarga de validar las credenciales y obtener el rut del
	 * transportista para eliminar el colegio asociado a el
	 *
	 * @param headers encabezados HTTP de la peticion
	 * @param colegio id del colegio que se quiere eliminar
	 * 
	 * @return GenericResponse conteniendo un array de Colegio
	 * @see Colegio
	 * @see Transportista
	 * @see AuthFirebaseUtil
	 */
	public ResponseEntity<GenericResponse> delete(Map<String, String> headers, Long colegio) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		UserRecord userRecord = null;
		Colegio colegioElegido = null;

		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();

			for (Colegio co : colegioRepository.findByRutT(rutPeticion)) {

				if (co.getIdColegio().equals(colegio)) {
					colegioElegido = co;
					break;
				}
			}
			if (colegioElegido == null) {
				genericResponse.setCode(403);
				genericResponse.setMessage("recurso no encontrado");
				genericResponse.setResponse("recurso no encontrado");
			} else {
				colegioRepository.deleteById(colegio);

				genericResponse.setCode(200);
				genericResponse.setMessage("colegios");
				genericResponse.setResponse(colegioRepository.findByRutT(rutPeticion));
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	/**
	 * <h1>editar(headers,idColegio,Colegio)</h1>
	 * <p>
	 * Este metodo se encarga de editar un colegio
	 * </p>
	 * El metodo se encarga de validar las credenciales y obtener el rut del
	 * transportista para editar el colegio asociado
	 *
	 * @param headers   encabezados HTTP de la peticion
	 * @param idColegio colegio el cual se quiere editar
	 * @param colegio   entidad usada para editar la que existe en base de datos
	 * @return GenericResponse conteniendo un objeto Colegio
	 * @see Colegio
	 * @see Transportista
	 * @see AuthFirebaseUtil
	 */
	public ResponseEntity<GenericResponse> editar(Map<String, String> headers, Long idColegio, Colegio colegio) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		UserRecord userRecord = null;
		Colegio colegioElegido = null;
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			if (transportistaRepository.findByUid(UIDPeticion) != null) {
				String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
				colegio.setRutT(rutPeticion);
				colegio.setIdColegio(idColegio);

				for (Colegio co : colegioRepository.findByRutT(rutPeticion)) {
					if (co.getIdColegio().equals(idColegio)) {
						colegioElegido = co;
						break;
					}
				}
				if (colegioElegido == null) {
					genericResponse.setCode(403);
					genericResponse.setMessage("recurso no encontrado");
					genericResponse.setResponse("recurso no encontrado");
				} else {
					if(colegio.getDireccion()!=null) {
						if(RegEx.validaDireccion(colegio.getDireccion())) {
							genericResponse.setCode(200);
							genericResponse.setMessage("colegio");
							genericResponse.setResponse(colegioRepository.save(colegio));
						}else {
							genericResponse.setCode(400);
							genericResponse.setMessage("Error de datos");
							genericResponse.setResponse("Error de datos");
						}
						
					}else {
						genericResponse.setCode(200);
						genericResponse.setMessage("colegio");
						genericResponse.setResponse(colegioRepository.save(colegio));
					}
					
				}

			} else {
				genericResponse.setCode(401);
				genericResponse.setMessage("Unauthorized");
				genericResponse.setResponse("Unauthorized");
			}

		}

		return new ResponseEntity<>(genericResponse, HttpStatus.ACCEPTED);
	}

}
