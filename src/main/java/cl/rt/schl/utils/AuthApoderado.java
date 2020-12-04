package cl.rt.schl.utils;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import cl.rt.schl.entity.Apoderado;
import cl.rt.schl.entity.Viaje;
import cl.rt.schl.repository.ApoderadoRepository;
/**
 * 
 * @author Francisco
 *
 */
public class AuthApoderado {
	/**<h1>validarApoderado(headers,apoderados)</h1>
	*<p> Este metodo se encarga de validar un apoderado
	* </p>
	* El metodo se encarga de obtener las credenciales que vienen por HTTP Basic
	* y realiza las transformaciones correspondientes para poder validar con las 
	* claves encriptadas en base de datos
	*
	* @param  headers encabezados HTTP de la peticion
	* @param  findAll lista de apoderados
	* 
	* @return      Apoderado
	* @see         Apoderado
	*/
	public Apoderado validarApoderado(Map<String, String> headers, Iterable<Apoderado> findAll) {
		Apoderado valido = null;

		try {

			byte[] result = Base64.getDecoder().decode(headers.get("authorization").replace("Basic ", ""));
			String claves = new String(result);
			System.out.println(claves);
			for (Apoderado apoderado : findAll) {
				System.out.println(BCrypt.checkpw(claves.split(":")[1], apoderado.getClave()));
				if (apoderado.getRutAp().equals(claves.split(":")[0])) {
					if (BCrypt.checkpw(claves.split(":")[1], apoderado.getClave())) {
						System.out.println(apoderado);
						valido = apoderado;
						break;
					}

				}

			}
		} catch (Exception e) {
			System.out.println("nullo");

			valido = null;
		}

		return valido;
	}

}
