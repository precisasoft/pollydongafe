package ec.com.vipsoft.ce.backend.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Stateless;
@Stateless
public class GeneradorClaveAcceso implements Serializable {

    private static final long serialVersionUID = -1L;

    public String generarClaveAccesoFactura(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca) {
        return generarClaveAcceso(ruc, sucursal, puntoEmision, enPruebas, numerocomprobante, secuenciaca, "01");
    }

    public String generarClaveAccesoNotaCredito(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca) {
        return generarClaveAcceso(ruc, sucursal, puntoEmision, enPruebas, numerocomprobante, secuenciaca, "04");
    }

    public String generarClaveAccesoNotaDebito(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca) {
        return generarClaveAcceso(ruc, sucursal, puntoEmision, enPruebas, numerocomprobante, secuenciaca, "05");
    }

    public String generarClaveAccesoGuiaRemision(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca) {
        return generarClaveAcceso(ruc, sucursal, puntoEmision, enPruebas, numerocomprobante, secuenciaca, "06");
    }

    public String generarClaveAccesoComprobanteRetencion(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca) {
        return generarClaveAcceso(ruc, sucursal, puntoEmision, enPruebas, numerocomprobante, secuenciaca, "07");
    }

    private String generarClaveAcceso(String ruc, Integer sucursal, Integer puntoEmision, boolean enPruebas, long numerocomprobante, int secuenciaca, String tipoDocumento) {
        StringBuilder sb = new StringBuilder(representacionTextoFechaActual());
        sb.append(tipoDocumento);
        sb.append(ruc);
        if (enPruebas) {
            sb.append("1");
        } else {
            sb.append("2");
        }
        sb.append(completarConCerosIzquierda(sucursal, 3));
        sb.append(completarConCerosIzquierda(puntoEmision, 3));
        sb.append(completarConCerosIzquierda(numerocomprobante, 9));
        sb.append(completarConCerosIzquierda(secuenciaca, 8));
        sb.append("1");
        sb.append(obtenerSumaPorDigitos(sb.toString()));
        return sb.toString();
    }

    private String completarConCerosIzquierda(Integer numero, Integer max) {
        String numeroEnLetras = String.valueOf(numero);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (max - numeroEnLetras.length()); i++) {
            sb.append("0");
        }
        sb.append(numeroEnLetras);
        return sb.toString();
    }

    private String completarConCerosIzquierda(Long numero, Integer max) {
        String numeroEnLetras = String.valueOf(numero);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (max - numeroEnLetras.length()); i++) {
            sb.append("0");
        }
        sb.append(numeroEnLetras);
        return sb.toString();
    }

    private String representacionTextoFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        return sdf.format(new Date());
    }

    public String generarClaveAccesoContingencia(String claveAccesoNormal) {
        StringBuilder sb = new StringBuilder();
        sb.append(claveAccesoNormal.substring(0, claveAccesoNormal.length() - 2));
        sb.append(obtenerSumaPorDigitos(sb.toString()));
        return sb.toString();
    }

    private int obtenerSumaPorDigitos(String cadena) {
        String invertida = invertirCadena(cadena);
        // String invertida="187654321100001000300100110001231006715021022071";
        int[] digitos_ = new int[48];
        int suma = 0;
        digitos_[0] = Integer.parseInt("" + invertida.subSequence(0, 1));
        suma += digitos_[0] * 2;
        digitos_[1] = Integer.parseInt("" + invertida.subSequence(1, 2));
        suma += digitos_[1] * 3;
        digitos_[2] = Integer.parseInt("" + invertida.subSequence(2, 3));
        suma += digitos_[2] * 4;
        digitos_[3] = Integer.parseInt("" + invertida.subSequence(3, 4));
        suma += digitos_[3] * 5;
        digitos_[4] = Integer.parseInt("" + invertida.subSequence(4, 5));
        suma += digitos_[4] * 6;
        digitos_[5] = Integer.parseInt("" + invertida.subSequence(5, 6));
        suma += digitos_[5] * 7;
        digitos_[6] = Integer.parseInt("" + invertida.subSequence(6, 7));
        suma += digitos_[6] * 2;
        digitos_[7] = Integer.parseInt("" + invertida.subSequence(7, 8));
        suma += digitos_[7] * 3;
        digitos_[8] = Integer.parseInt("" + invertida.subSequence(8, 9));
        suma += digitos_[8] * 4;
        digitos_[9] = Integer.parseInt("" + invertida.subSequence(9, 10));
        suma += digitos_[9] * 5;

        digitos_[10] = Integer.parseInt("" + invertida.subSequence(10, 11));
        suma += digitos_[10] * 6;
        digitos_[11] = Integer.parseInt("" + invertida.subSequence(11, 12));
        suma += digitos_[11] * 7;
        digitos_[12] = Integer.parseInt("" + invertida.subSequence(12, 13));
        suma += digitos_[12] * 2;
        digitos_[13] = Integer.parseInt("" + invertida.subSequence(13, 14));
        suma += digitos_[13] * 3;
        digitos_[14] = Integer.parseInt("" + invertida.subSequence(14, 15));
        suma += digitos_[14] * 4;
        digitos_[15] = Integer.parseInt("" + invertida.subSequence(15, 16));
        suma += digitos_[15] * 5;
        digitos_[16] = Integer.parseInt("" + invertida.subSequence(16, 17));
        suma += digitos_[16] * 6;
        digitos_[17] = Integer.parseInt("" + invertida.subSequence(17, 18));
        suma += digitos_[17] * 7;
        digitos_[18] = Integer.parseInt("" + invertida.subSequence(18, 19));
        suma += digitos_[18] * 2;
        digitos_[19] = Integer.parseInt("" + invertida.subSequence(19, 20));
        suma += digitos_[19] * 3;
        digitos_[20] = Integer.parseInt("" + invertida.subSequence(20, 21));
        suma += digitos_[20] * 4;
        digitos_[21] = Integer.parseInt("" + invertida.subSequence(21, 22));
        suma += digitos_[21] * 5;
        digitos_[22] = Integer.parseInt("" + invertida.subSequence(22, 23));
        suma += digitos_[22] * 6;
        digitos_[23] = Integer.parseInt("" + invertida.subSequence(23, 24));
        suma += digitos_[23] * 7;
        digitos_[24] = Integer.parseInt("" + invertida.subSequence(24, 25));
        suma += digitos_[24] * 2;
        digitos_[25] = Integer.parseInt("" + invertida.subSequence(25, 26));
        suma += digitos_[25] * 3;
        digitos_[26] = Integer.parseInt("" + invertida.subSequence(26, 27));
        suma += digitos_[26] * 4;
        digitos_[27] = Integer.parseInt("" + invertida.subSequence(27, 28));
        suma += digitos_[27] * 5;
        digitos_[28] = Integer.parseInt("" + invertida.subSequence(28, 29));
        suma += digitos_[28] * 6;
        digitos_[29] = Integer.parseInt("" + invertida.subSequence(29, 30));
        suma += digitos_[29] * 7;

        digitos_[30] = Integer.parseInt("" + invertida.subSequence(30, 31));
        suma += digitos_[30] * 2;
        digitos_[31] = Integer.parseInt("" + invertida.subSequence(31, 32));
        suma += digitos_[31] * 3;
        digitos_[32] = Integer.parseInt("" + invertida.subSequence(32, 33));
        suma += digitos_[32] * 4;
        digitos_[33] = Integer.parseInt("" + invertida.subSequence(33, 34));
        suma += digitos_[33] * 5;
        digitos_[34] = Integer.parseInt("" + invertida.subSequence(34, 35));
        suma += digitos_[34] * 6;
        digitos_[35] = Integer.parseInt("" + invertida.subSequence(35, 36));
        suma += digitos_[35] * 7;
        digitos_[36] = Integer.parseInt("" + invertida.subSequence(36, 37));
        suma += digitos_[36] * 2;
        digitos_[37] = Integer.parseInt("" + invertida.subSequence(37, 38));
        suma += digitos_[37] * 3;
        digitos_[38] = Integer.parseInt("" + invertida.subSequence(38, 39));
        suma += digitos_[38] * 4;
        digitos_[39] = Integer.parseInt("" + invertida.subSequence(39, 40));
        suma += digitos_[39] * 5;
        digitos_[40] = Integer.parseInt("" + invertida.subSequence(40, 41));
        suma += digitos_[40] * 6;
        digitos_[41] = Integer.parseInt("" + invertida.subSequence(41, 42));
        suma += digitos_[41] * 7;
        digitos_[42] = Integer.parseInt("" + invertida.subSequence(42, 43));
        suma += digitos_[42] * 2;
        digitos_[43] = Integer.parseInt("" + invertida.subSequence(43, 44));
        suma += digitos_[43] * 3;
        digitos_[44] = Integer.parseInt("" + invertida.subSequence(44, 45));
        suma += digitos_[44] * 4;
        digitos_[45] = Integer.parseInt("" + invertida.subSequence(45, 46));
        suma += digitos_[45] * 5;
        digitos_[46] = Integer.parseInt("" + invertida.subSequence(46, 47));
        suma += digitos_[46] * 6;
        digitos_[47] = Integer.parseInt("" + invertida.subSequence(47, 48));
        suma += digitos_[47] * 7;
        int dv = suma % 11;
        int salida = 11 - dv;
        if (salida == 11) {
            salida = 0;
        } else {
            if (salida == 10) {
                salida = 1;
            }
        }
        return salida;
    }

    private String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int x = cadena.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        }
        return cadenaInvertida;
    }

}
