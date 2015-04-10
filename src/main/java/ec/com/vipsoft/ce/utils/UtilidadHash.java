package ec.com.vipsoft.ce.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilidadHash {
	public static String getEncryptedPassword(String clearTextPassword)   {  
		  
	    
	    try {
	      MessageDigest md = MessageDigest.getInstance("SHA-256");
	      md.update(clearTextPassword.getBytes());
	      return new sun.misc.BASE64Encoder().encode(md.digest());
	    } catch (NoSuchAlgorithmException e) {
	      //_log.error("Failed to encrypt password.", e);
	    }
	    return "";
	  }

}
