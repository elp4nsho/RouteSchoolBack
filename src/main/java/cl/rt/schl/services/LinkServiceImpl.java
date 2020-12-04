package cl.rt.schl.services;

import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Link;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.TransportistaRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @author Francisco
 *
 */


@Service
public class LinkServiceImpl implements LinkService {

	private AuthFirebaseUtil authFirebaseUtil = new AuthFirebaseUtil();

	@Autowired
	private LinkRepository linkRepository;
	@Autowired
	private TransportistaRepository transportistaRepository;

	/**<h1>generarLinkRegistro(headers)
	 * </h1>
	 * 
	 *<p>
	* Este metodo se encarga de generar una nueva entidad Link relacionado a un transportista para
	* luego ser devuelta en formato JSON
	* <p>
	* El metodo recibe los headers para luego buscar el token de autorizacion
	* valida el token y obtiene el id de transportista para generar el nuevo Link
	* el link es retornado en el atributo response de GenericResponse
	*
	* @param  headers  encabezados HTTP de la peticion
	* @return      GenericResponse conteniendo un objeto Link
	* @see         Link 
	* @see GenericResponse
	* @see AuthFirebaseUtil
	*/	
	@Override
	public ResponseEntity<GenericResponse> generarLinkRegistro(Map<String, String> headers) {
		GenericResponse genericResponse = new GenericResponse();
		String UIDPeticion = null;
		UIDPeticion = authFirebaseUtil.UIDByTokenHeader(headers);
		if (UIDPeticion == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			UUID uuid = UUID.randomUUID();
			Link nuevoLink = new Link();
			LocalDate date = LocalDate.now();
			String rutPeticion = transportistaRepository.findByUid(UIDPeticion).getRut();
			nuevoLink.setEstado("valido");
			nuevoLink.setFecha(date);
			nuevoLink.setLink(uuid.toString());
			nuevoLink.setRutT(rutPeticion);
			genericResponse.setCode(200);
			genericResponse.setMessage("Link creado");
			genericResponse.setResponse(linkRepository.save(nuevoLink));
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

}
