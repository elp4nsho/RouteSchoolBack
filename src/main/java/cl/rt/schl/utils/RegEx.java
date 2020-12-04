package cl.rt.schl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

	public static boolean validaRUT(String rutInput) {
		boolean valido = false;

		try {

			Pattern p = Pattern.compile("^\\d{3,8}-[\\d|kK]{1}$");
			Matcher m = p.matcher(rutInput);
			String rut, dV;

			if (m.find()) {
				rut = rutInput.split("-")[0];
				dV = rutInput.split("-")[1];
				String dvCalculado = dv(rut);
				if (dvCalculado.equals(dV)) {
					valido = true;
				}

			}

		} catch (Exception e) {
			valido = false;
		}

		return valido;
	}

	public static boolean validaDireccion(String direccion) {
		boolean valido = false;
		try {
			Pattern p = Pattern.compile("^[\\d|.-]+,[\\d|.-]+$");
			Matcher m = p.matcher(direccion);
			valido = m.find();
		}catch (Exception e) {
			valido = false;
		}
		

		return valido;
	}

	public static String dv(String rut) {
		Integer M = 0, S = 1, T = Integer.parseInt(rut);
		for (; T != 0; T = (int) Math.floor(T /= 10))
			S = (S + T % 10 * (9 - M++ % 6)) % 11;
		return (S > 0) ? String.valueOf(S - 1) : "k";
	}
}
