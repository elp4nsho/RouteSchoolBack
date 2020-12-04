package cl.rt.schl.services;

import java.util.*;
import java.util.stream.*;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Colegio;
import cl.rt.schl.entity.Credencial;
import cl.rt.schl.entity.Link;
import cl.rt.schl.entity.Nino;
import cl.rt.schl.repository.ApoderadoRepository;
import cl.rt.schl.repository.ColegioRepository;
import cl.rt.schl.repository.LinkRepository;
import cl.rt.schl.repository.NinoRepository;
import cl.rt.schl.repository.TransportistaRepository;
import cl.rt.schl.utils.AuthApoderado;
import cl.rt.schl.utils.AuthFirebaseUtil;
import cl.rt.schl.utils.GenericResponse;
import cl.rt.schl.utils.RegEx;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestHeader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * 
 * @author Francisco
 *
 */
@Service
public class ApoderadoServiceImpl implements ApoderadoService {

	private AuthFirebaseUtil authFirebaseUtil = new AuthFirebaseUtil();

	@Autowired
	private ApoderadoRepository apoderadoRepository;
	@Autowired
	private LinkRepository linkRepository;
	@Autowired
	private ColegioRepository colegioRepository;
	@Autowired
	private NinoRepository ninoRepository;
	@Autowired
	private TransportistaRepository transportistaRepository;
	private AuthApoderado authApoderado = new AuthApoderado();

	/**
	 * <h1>registrarApoderado(link,Apoderado)</h1>
	 * 
	 * <p>
	 * Este metodo se encarga de registrar un apoderado, recibiendo un link valido y
	 * un objeto apoderado con niños
	 * <p>
	 * El metodo se encarga de validar los niños y colegios a los que pertenece,
	 * verifica el link y obtiene el rut de transportista asociado para enlazar el
	 * nuevo Apoderado
	 *
	 * @param id        link UID autogenerado de la clase Link
	 * @param apoderado apoderado
	 * @return GenericResponse conteniendo un objeto Apoderado
	 * @see Link
	 * @see Apoderado
	 * @see GenericResponse
	 * @see LinkRepository
	 */
	@Override
	public ResponseEntity<GenericResponse> registrarApoderado(String id, Apoderado apoderado) {
		GenericResponse genericResponse = new GenericResponse();
		boolean validado = true;

		if (linkRepository.findByLink(id) == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}

		else if (!RegEx.validaRUT(apoderado.getRutAp())) {
			genericResponse.setCode(400);
			genericResponse.setMessage("Rut invalido");
			genericResponse.setResponse("Rut invalido");
		} else if (!RegEx.validaDireccion(apoderado.getDireccion())) {
			genericResponse.setCode(400);
			genericResponse.setMessage("Direccion incorrecta");
			genericResponse.setResponse("Direccion incorrecta");
		} else if (apoderado.getClave() == null || apoderado.getClave().length() < 7) {
			genericResponse.setCode(400);
			genericResponse.setMessage("La clave debe contener almenos 8 caracteres");
			genericResponse.setResponse("La clave debe contener almenos 8 caracteres");
		} else if (apoderado.getNino() == null) {
			genericResponse.setCode(400);
			genericResponse.setMessage("Sin niños");
			genericResponse.setResponse("Sin niños");
		}

		else {

			for (Nino nino : apoderado.getNino()) {
				if (ninoRepository.findById(nino.getRutN()).isPresent()) {
					if (ninoRepository.findById(nino.getRutN()).get().getApoderado().getRutAp()
							.equals(apoderado.getRutAp())) {

					} else {
						genericResponse.setCode(401);
						genericResponse.setMessage("El niño ya esta asociado");
						genericResponse.setResponse("El niño ya esta asociado");
						validado = false;
						break;
					}
				}

				if (!colegioRepository.findById(nino.getColegio().getIdColegio()).isPresent()) {
					genericResponse.setCode(401);
					genericResponse.setMessage("Colegio no encontrado");
					genericResponse.setResponse("Colegio no encontrado");
					validado = false;
					break;
				}
				if (!RegEx.validaRUT(nino.getRutN()) || !RegEx.validaDireccion(nino.getDireccion())) {
					genericResponse.setCode(401);
					genericResponse.setMessage("Datos de niño incorrectos");
					genericResponse.setResponse("Datos de niño incorrectos");
					validado = false;
					break;
				}

				if (colegioRepository.findById(nino.getColegio().getIdColegio()).get().getRutT()
						.equals(linkRepository.findByLink(id).getRutT())) {

					validado = true;

				} else {
					genericResponse.setCode(401);
					genericResponse.setMessage("colegio no asociado");
					genericResponse.setResponse("colegio no asociado");
					validado = false;
					break;
				}
			}
			if (validado) {
				try {
					apoderado.setRutT(linkRepository.findByLink(id).getRutT());
					Set<Nino> listaNinosPeticion = apoderado.getNino();
					apoderado.setNino(null);
					String passwordEncriptada = BCrypt.hashpw(apoderado.getClave(), BCrypt.gensalt());
					apoderado.setClave(passwordEncriptada);
					apoderadoRepository.save(apoderado);
					for (Nino nino : listaNinosPeticion) {
						nino.setApoderado(apoderado);
						ninoRepository.save(nino);
					}
					linkRepository.deleteById(linkRepository.findByLink(id).getId());
					genericResponse.setCode(200);
					genericResponse.setMessage("Apoderado guardado");
					genericResponse.setResponse("ok");
				} catch (Exception e) {
					genericResponse.setCode(401);
					genericResponse.setMessage("Unauthorized");
					genericResponse.setResponse("Unauthorized");
				}

			}
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	/**
	 * <h1>obtenerColegios(link)</h1>
	 * 
	 * <p>
	 * Este metodo se encarga de retornar los colegios asociados al transportista
	 * para que el apoderado pueda registrar sus niños y asociarlos al colegio
	 * correspondiente
	 * <p>
	 * El metodo se encarga de obtener el rut del transportista con el uid del link
	 * ingresado como parametro y obtener los colegios registrados por ese
	 * Transportista
	 *
	 * @param id link UID autogenerado de la clase Link
	 * @return GenericResponse conteniendo un array de Colegio
	 * @see Colegio
	 * @see Link
	 * @see GenericResponse
	 * @see LinkRepository
	 * 
	 */
	@Override
	public ResponseEntity<GenericResponse> obtenerColegios(String id) {
		GenericResponse genericResponse = new GenericResponse();

		if (linkRepository.findByLink(id) == null) {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		} else {
			List<Colegio> colegios = colegioRepository.findByRutT(linkRepository.findByLink(id).getRutT());
			if (colegios == null) {
				genericResponse.setCode(200);
				genericResponse.setMessage("Problemas al obtener colegios");
				genericResponse.setResponse("Problemas al obtener colegios");
			} else {
				genericResponse.setCode(200);
				genericResponse.setMessage("Colegios registrados");
				genericResponse.setResponse(colegios);
			}

		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	/**
	 * <h1>loginApoderado(headers)</h1>
	 * 
	 * <p>
	 * Este metodo se encarga de autenticar a un usuario
	 * <p>
	 * El metodo se encarga de validar las credenciales obtenidas a traves de HTTP
	 * Basic y comparar los registros con alguno existente, retornando una cookie
	 * con las credenciales del usuario para el uso de otros servicios
	 *
	 * @param Map                 headers encabezados de la peticion HTTP
	 * @param HttpServletResponse response contenedora de la cookie expirada
	 * @return GenericResponse conteniendo un objeto de Apoderado
	 * @see HttpServletResponse
	 * @see AuthApoderado
	 */
	public ResponseEntity<GenericResponse> loginApoderado(Map<String, String> headers, HttpServletResponse response) {
		GenericResponse genericResponse = new GenericResponse();
		if (authApoderado.validarApoderado(headers, apoderadoRepository.findAll()) != null) {
			genericResponse.setCode(200);
			genericResponse.setMessage("LOGIN OK");
			Cookie cookie = new Cookie("credentials",
					headers.get("authorization") == null ? headers.get("Authorization").replace("Basic ", "")
							: headers.get("authorization").replace("Basic ", ""));
			cookie.setPath("/");
			response.addCookie(cookie);
			genericResponse.setResponse(authApoderado.validarApoderado(headers, apoderadoRepository.findAll()));
		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}

	/**
	 * <h1>salirApodeardo(headers)</h1>
	 * 
	 * <p>
	 * Este metodo cierra la sesion de un usuario
	 * <p>
	 * El metodo se encarga de eliminar la cookie del navegador del usuario, asi
	 * terminando la sesion
	 *
	 * @param headers  encabezados de la peticion HTTP
	 * @param response contenedora de la cookie expirada
	 * @return GenericResponse conteniendo un objeto de Apoderado
	 * @see HttpServletResponse
	 * @see AuthApoderado
	 */
	public ResponseEntity<GenericResponse> salirApoderado(Map<String, String> headers, HttpServletResponse response) {
		GenericResponse genericResponse = new GenericResponse();
		if (authApoderado.validarApoderado(headers, apoderadoRepository.findAll()) != null) {
			genericResponse.setCode(200);
			genericResponse.setMessage("salio");
			Cookie cookie = new Cookie("credentials", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			genericResponse.setResponse("salio");
		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);

	}

	/**
	 * <h1>editar(headers,Apoderado)</h1>
	 * 
	 * <p>
	 * Este metodo se encarga de editar a un apoderado
	 * <p>
	 * El metodo se encarga de validar las credenciales y obtener el apoderado a
	 * editar
	 *
	 * @param headers   encabezados de la peticion HTTP
	 * @param apoderado objeto Apoderado usado para editar el otro Apoderado por
	 *                  credenciales
	 * @return GenericResponse conteniendo un objeto de Apoderado
	 * @see Apoderado
	 * @see AuthApoderado
	 */
	public ResponseEntity<GenericResponse> editar(Map<String, String> headers, Apoderado apoderado) {
		GenericResponse genericResponse = new GenericResponse();
		Apoderado valido = authApoderado.validarApoderado(headers, apoderadoRepository.findAll());

		if (valido != null) {
			apoderado.setRutT(valido.getRutT());
			apoderado.setClave(valido.getClave());
			apoderado.setRutAp(valido.getRutAp());
			if (RegEx.validaDireccion(apoderado.getDireccion())) {
				boolean validoNino = true;
				for (Nino nino : apoderado.getNino()) {
					if(RegEx.validaDireccion(nino.getDireccion())) {
						nino.setApoderado(apoderado);
						ninoRepository.save(nino);
					}else {
						validoNino = false;
						break;
					}
					
				}
				if(validoNino) {
					genericResponse.setCode(200);
					genericResponse.setMessage("Editado");
					genericResponse.setResponse(apoderadoRepository.save(apoderado));
				}else {
					genericResponse.setCode(400);
					genericResponse.setMessage("Datos invalidos");
					genericResponse.setResponse("Datos invalidos");
				}
				
			

			} else {
				genericResponse.setCode(400);
				genericResponse.setMessage("Datos invalidos");
				genericResponse.setResponse("Datos invalidos");
			}

		} else {
			genericResponse.setCode(401);
			genericResponse.setMessage("Unauthorized");
			genericResponse.setResponse("Unauthorized");
		}
		return new ResponseEntity<>(genericResponse, HttpStatus.OK);
	}

	/**
	 * <h1>showLogin()</h1>
	 * <p>
	 * Este metodo se encarga de retornar un html que contiene la interfaz de login
	 * <p>
	 * El metodo se encarga de retornar un string con el valor login y se usara para
	 * buscar el html asociado
	 * 
	 * @return String login
	 */
	@Override
	public String showlogin() {

		return "login";
	}

	/**
	 * <h1>mostrarFormulario(link)</h1>
	 * <p>
	 * Este metodo se encarga mostrar el formulario de registro para el apoderado
	 * <p>
	 * El metodo se encarga de validar el link otorgado y mostrar el html
	 * correspondiente
	 *
	 * @param String link
	 * @return String "formulario"
	 * @see Apoderado
	 * @see Link
	 */
	@Override
	public String mostrarFormulario(String linkId) {
		if (linkRepository.findByLink(linkId) == null) {
			return "login";
		} else {
			return "formulario";
		}

	}

	/**
	 * <h1>mostrarHome(request)</h1>
	 * <p>
	 * Este metodo se encarga mostrar el home del apoderado
	 * </p>
	 * El metodo obtiene las credenciales de la peticion a traves de las Cookies, de
	 * esta manera es posible consultar si existe un usuario asociado a la Cookie y
	 * retornar el html correspondiente
	 * 
	 * @param request peticion HTTP del usuario
	 * @return String "home"
	 */
	@Override
	public String mostrarHome(HttpServletRequest request) {

		try {

			if (WebUtils.getCookie(request, "credentials").getValue() == null) {
				return "login";
			} else {
				System.out.println("asdasd");
				byte[] result = Base64.getDecoder().decode(WebUtils.getCookie(request, "credentials").getValue());
				String claves = new String(result);
				for (Apoderado apoderado : apoderadoRepository.findAll()) {
					if (apoderado.getRutAp().equals(claves.split(":")[0])) {
						if (BCrypt.checkpw(claves.split(":")[1], apoderado.getClave())) {
							return "home";
						}
					}
				}
				return "login";

			}
		} catch (Exception e) {
			return "login";
			// TODO: handle exception
		}

	}

	/**
	 * <h1>mostrarEditar(request)</h1>
	 * <p>
	 * Este metodo se encarga de mostrar un formulario de edicion para el apoderado
	 * </p>
	 * El metodo obtiene las credenciales de la peticion a traves de las Cookies, de
	 * esta manera es posible consultar si existe un usuario asociado a la Cookie y
	 * retornar el html correspondiente
	 * 
	 * @param request peticion HTTP del usuario
	 * @return String "editar"
	 */
	public String mostrarEditar(HttpServletRequest request) {

		try {

			if (WebUtils.getCookie(request, "credentials").getValue() == null) {
				return "login";
			} else {
				System.out.println("asdasd");
				byte[] result = Base64.getDecoder().decode(WebUtils.getCookie(request, "credentials").getValue());
				String claves = new String(result);
				for (Apoderado apoderado : apoderadoRepository.findAll()) {
					if (apoderado.getRutAp().equals(claves.split(":")[0])) {
						if (BCrypt.checkpw(claves.split(":")[1], apoderado.getClave())) {
							return "editar";
						}
					}
				}
				return "login";

			}
		} catch (Exception e) {
			return "login";
			// TODO: handle exception
		}

	}

	/**
	 * <h1>mostrarMonitoreo(request)</h1>
	 * <p>
	 * Este metodo se encarga de mostrar la pagina de monitoreo del apoderado
	 * </p>
	 * El metodo obtiene las credenciales de la peticion a traves de las Cookies, de
	 * esta manera es posible consultar si existe un usuario asociado a la Cookie y
	 * retornar el html correspondiente
	 * 
	 * @param request peticion HTTP del usuario
	 * @return String "monitorear"
	 */
	public String mostrarMonitoreo(HttpServletRequest request) {

		try {

			if (WebUtils.getCookie(request, "credentials").getValue() == null) {
				return "login";
			} else {
				System.out.println("asdasd");
				byte[] result = Base64.getDecoder().decode(WebUtils.getCookie(request, "credentials").getValue());
				String claves = new String(result);
				for (Apoderado apoderado : apoderadoRepository.findAll()) {
					if (apoderado.getRutAp().equals(claves.split(":")[0])) {
						if (BCrypt.checkpw(claves.split(":")[1], apoderado.getClave())) {
							return "monitorear";
						}
					}
				}
				return "login";

			}
		} catch (Exception e) {
			return "login";
			// TODO: handle exception
		}

	}

	/**
	 * <h1>mostrarHome(request)</h1>
	 * <p>
	 * Este metodo se encarga de mostrar el formulario de inasistencias para el
	 * apoderado
	 * </p>
	 * El metodo obtiene las credenciales de la peticion a traves de las Cookies, de
	 * esta manera es posible consultar si existe un usuario asociado a la Cookie y
	 * retornar el html correspondiente
	 * 
	 * @param request peticion HTTP del usuario
	 * @return String "inasistencia"
	 */
	public String mostrarInasistencia(HttpServletRequest request) {

		try {

			if (WebUtils.getCookie(request, "credentials").getValue() == null) {
				return "login";
			} else {
				System.out.println("asdasd");
				byte[] result = Base64.getDecoder().decode(WebUtils.getCookie(request, "credentials").getValue());
				String claves = new String(result);
				for (Apoderado apoderado : apoderadoRepository.findAll()) {
					if (apoderado.getRutAp().equals(claves.split(":")[0])) {
						if (BCrypt.checkpw(claves.split(":")[1], apoderado.getClave())) {
							return "inasistencia";
						}
					}
				}
				return "login";

			}
		} catch (Exception e) {
			return "login";
			// TODO: handle exception
		}

	}

}
