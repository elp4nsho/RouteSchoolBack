package cl.rt.schl.utils;

import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import cl.rt.schl.entity.Apoderado;
/**
 * 
 * @author Francisco
 *
 */
public class AuthFirebaseUtil {

	/**<h1>validar(headers)</h1>
	*<p> Este metodo se encarga de validar un transportista
	* </p>
	* El metodo se encarga de obtener las credenciales que vienen por HTTP Bearer token
	* y consulta al servicio de firebase para obtener la identidad del usuario
	*
	* @param  headers encabezados HTTP de la peticion
	* 
	* @see         FirebaseToken
	* @see         UserRecord
	* @throws FirebaseAuthException
	*/
	public void validar(Map<String,String> headers) throws FirebaseAuthException {
		System.out.println(headers.get("authorization").replace("Bearer ", ""));
		String idToken = headers.get("authorization").replace("Bearer ", "");
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		String uid = decodedToken.getUid();

		UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
		// See the UserRecord reference doc for the contents of userRecord.
		System.out.println("Successfully fetched user data: " + userRecord.getUid());
		System.out.println("Successfully fetched user data: " + userRecord.getEmail()	);
		
	}
	/**<h1>UIDByTokenHeader(headers)</h1>
	*<p> Este metodo se encarga de obtener el UID del token de los headers HTTP de la peticion
	* </p>
	*
	* @param  headers encabezados HTTP de la peticion
	* @return      String uid encontrado en el token
	*/
	public String UIDByTokenHeader(Map<String,String> headers) {
			String UID = null;
			String idToken = null;
			try {
				idToken =headers.get("authorization").replace("Bearer ", "");
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
				UID = decodedToken.getUid();

			}catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
						
			return UID;
		
	}
	


	
	
}
