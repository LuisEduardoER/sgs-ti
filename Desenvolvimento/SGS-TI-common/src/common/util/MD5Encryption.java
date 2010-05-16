package common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encryption {
	
	public static String encript(String value){
		byte[] valueBytes = value.getBytes();
		
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(valueBytes);
			
			byte messageDigest[] = algorithm.digest();
		            
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<messageDigest.length;i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			
			algorithm.reset();
			
			return hexString.toString();
			
		}catch(NoSuchAlgorithmException nsae){
		    nsae.printStackTrace(); 
		    return null;
		}
	}
}
