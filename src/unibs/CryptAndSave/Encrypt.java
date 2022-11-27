package unibs.CryptAndSave;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

final class Encrypt {
	private static BigInteger e;
	private static BigInteger N;
	public Encrypt(BigInteger publicKey, BigInteger modulo)
	{
		e=publicKey;
		N=modulo;
	}
	public static synchronized BigInteger go(String message) 
	{
		BigInteger toEncr = null;
		try 
		{
			toEncr = new BigInteger(message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    toEncr=go(toEncr);
	    return toEncr;
		
	}

	  /** Encrypt the given plaintext message. */
	private static synchronized BigInteger go(BigInteger message)
	{
    	return message.modPow(e, N);
	}

}
