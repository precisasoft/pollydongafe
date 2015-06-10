package ec.com.vipsoft.cryptografia;


import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
/** 
 * Encryption and Decryption of String data; PBE(Password Based Encryption and Decryption)
 * @author Vikram
 */

public class CryptoUtil implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6976198055835096344L;
	Cipher ecipher;
    Cipher dcipher;
    // 8-byte Salt
    byte[] salt = {
        (byte) 0xA1, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x19, (byte) 0x78, (byte) 0x30, (byte) 0x11
    };
    // Iteration count
    int iterationCount = 17;
    public CryptoUtil() { 

    }
    
    public String decrypt(String plainText){
    	String retorno=null;
    	try {
			retorno=decrypt("palidonga",plainText);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| InvalidKeySpecException | NoSuchPaddingException
				| InvalidAlgorithmParameterException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return retorno;
    }

    public String encrypt(String planiText){
    	String retorno=null;
    	try {
			retorno= encrypt("palidonga",planiText);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return retorno;
    }
    
    
    /**
     * 
     * @param secretKey Key used to encrypt data
     * @param plainText Text input to be encrypted
     * @return Returns encrypted text
     * 
     */
     String encrypt(String secretKey, String plainText) 
            throws NoSuchAlgorithmException, 
            InvalidKeySpecException, 
            NoSuchPaddingException, 
            InvalidKeyException,
            InvalidAlgorithmParameterException, 
            UnsupportedEncodingException, 
            IllegalBlockSizeException, 
            BadPaddingException{
        //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
         // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        //Enc process
        ecipher = Cipher.getInstance(key.getAlgorithm());
       ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);      
        String charSet="UTF-8";       
        byte[] in = plainText.getBytes(charSet);
        byte[] out =ecipher.doFinal(in);
        String encStr=new sun.misc.BASE64Encoder().encode(out);
        return encStr;
    }
    /**
     * modificada para que devuelva el mismo texto... para poder probar que no rompa compatibilidad
     * @param secretKey
     * @param encryptedText
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    
   
//     /**     
//     * @param secretKey Key used to decrypt data
//     * @param encryptedText encrypted text input to decrypt
//     * @return Returns plain text after decryption
//     */
     String decrypt(String secretKey, String encryptedText)
     throws NoSuchAlgorithmException, 
            InvalidKeySpecException, 
            NoSuchPaddingException, 
            InvalidKeyException,
            InvalidAlgorithmParameterException, 
            UnsupportedEncodingException, 
            IllegalBlockSizeException, 
            BadPaddingException, 
            IOException{
         //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);        
         // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        //Decryption process; same key will be used for decr
        dcipher=Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
        byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet="UTF-8";     
        String plainStr = new String(utf8, charSet);
        return plainStr;
    }
    
    
    
//    public static void main(String[] args) throws Exception {
//    	if(args.length==1){
//    		CryptoUtil cryptoUtil=new CryptoUtil();
//            String key="palidonga";   
//            String plain=args[0];
//            String enc=cryptoUtil.encrypt(key, plain);
//            System.out.println("Original text: "+plain);
//            System.out.println("Encrypted text: "+enc);
//            String plainAfter=cryptoUtil.decrypt(key, enc);
//            System.out.println("Original text after decryption: "+plainAfter);	
//    	}
//        
//    }
}