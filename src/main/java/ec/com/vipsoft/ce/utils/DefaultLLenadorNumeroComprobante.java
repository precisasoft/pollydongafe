package ec.com.vipsoft.ce.utils;

import java.util.StringTokenizer;

public class DefaultLLenadorNumeroComprobante implements
		LlenadorNumeroComprobante {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3601066058434444103L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.com.vipsoft.ce.utils.LlenadorNumeroComprobante#llenarNumeroDocumento
	 * (java.lang.String)
	 */
	@Override
	public String llenarNumeroDocumento(String numero) {
		StringTokenizer tokenizer = new StringTokenizer(numero, "-");
		String primero = null;
		String segundo = null;
		String tercero = null;
		if (!tokenizer.hasMoreTokens()) {
			primero = "001";
			segundo = "001";
			if (numero.length() < 9) {
				tercero = numero;
				StringBuilder sb = new StringBuilder();
				int longitud = tercero.length();
				for (int i = 0; i < 9 - longitud; i++) {
					sb.append("0");
				}
				sb.append(tercero);
				tercero = sb.toString();
			}

		} else {
			primero = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens())
				segundo = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens())
				tercero = tokenizer.nextToken();
			if (tercero == null) {
				if(segundo==null){
					tercero=primero;
					segundo="001";
					primero="001";
				}else{
					tercero = segundo;
					segundo = primero;
					primero = "001";	
				}
				
			}
			if (primero.length() < 3) {
				if (primero.length() == 2) {
					primero = "0" + primero;
				}
				if (primero.length() == 1) {
					primero = "00" + primero;
				}

			}
			if (segundo.length() < 3) {
				if (segundo.length() == 2) {
					segundo = "0" + segundo;
				}
				if (segundo.length() == 1) {
					segundo = "00" + segundo;
				}
			}
			if (tercero.length() < 9) {
				StringBuilder sb = new StringBuilder();
				int longitud = tercero.length();
				for (int i = 0; i < 9 - longitud; i++) {
					sb.append("0");
				}
				sb.append(tercero);
				tercero = sb.toString();
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(primero).append("-").append(segundo).append("-")
				.append(tercero);
		return sb.toString();
	}
}
