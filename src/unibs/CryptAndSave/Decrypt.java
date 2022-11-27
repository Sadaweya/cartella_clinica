package unibs.CryptAndSave;

import java.math.BigInteger;

final class Decrypt {
	private static BigInteger d;
	private static BigInteger N;
	public Decrypt(BigInteger privateKey, BigInteger modulo)
	{
		d=privateKey;
		N=modulo;
	}

	public static synchronized String go(BigInteger message) 
	{
	    BigInteger toDecr=goBI(message);
	    return new String(toDecr.toByteArray());
	}

	  /** Encrypt the given plaintext message. */
	private static synchronized BigInteger goBI(BigInteger message)
	{
    	return message.modPow(d, N);
	}

}
