package unibs.CryptAndSave;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;


class RSA {
	private static BigInteger N, d, e;

	private int bitlen;
	private static Encrypt encryptor;
	private static Decrypt decryptor;

  /** Create an instance that can both encrypt and decrypt. */
  private RSA(int bits)
	{
	    bitlen = bits;
	    generateKeys();
	    decryptor=new Decrypt(d, N);
	}
	public RSA(String password, String fiscale)
	{
		bitlen=512;
		//password è D
		//codice fiscale è p, q è il primo successivo
		//e viene generato da D, N da codice fiscale.
		fiscale=fiscale.toUpperCase();
		byte[] fisc=fiscale.getBytes(StandardCharsets.UTF_8);
		double fiscNum=0;
		//trasformo il byte array in un numero.
		for(int i=0; i<fisc.length; i++) {
			fiscNum += fisc[i] * (fisc.length - i) * 256;
		}
		if(fiscNum%2==0)
			fiscNum++;
		while(isPrime(fiscNum))
		{
			fiscNum+=2;
		}

		BigInteger p= new BigDecimal(fiscNum).toBigInteger();
		fiscNum+=2;
		while(isPrime(fiscNum)) {
			fiscNum += 2;
		}
		BigInteger q= new BigDecimal(fiscNum).toBigInteger();
		for(int i=0; i<4; i++) {
			q = q.multiply(p);
			p = q.multiply(p);
		}
		while(!q.isProbablePrime(100)) {
			//System.out.println("Ciclo q");
			q=q.add(new BigInteger("2"));
		}
		while(!p.isProbablePrime(100))
			p=p.add(new BigInteger("2"));
		//System.out.println(p.toString());
		BigInteger N= p.multiply(q);
		BigInteger L = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		//System.out.println(L.toString());

		byte[] pass=password.getBytes(StandardCharsets.UTF_8);
		double psw=0;
		//trasformo il byte array in un numero.
		for(int i=0; i<pass.length; i++) {
			psw += pass[i] * (pass.length - i) * 256;
		}
		BigInteger d=  new BigDecimal(psw).toBigInteger();
		//System.out.println(d.toString());
		while (L.gcd(d).intValue() > 1) {
			d = d.add(new BigInteger("1"));
			//System.out.println(d.toString());
		}
		BigInteger e=  d.modInverse(L);

		encryptor=new Encrypt(e, N);
		decryptor=new Decrypt(d, N);
	}

	private boolean isPrime(double n) {
		//check if n is a multiple of 2
		if (n%2==0) return false;
		//if not, then just check the odds
		for(int i=3;i*i<=n;i+=2) {
			if(n%i==0)
				return false;
		}
		return true;
	}
	public RSA()
	{
		this(2048);
	}
	private void defineEnc(BigInteger pubblica, BigInteger modulo)
	{
		encryptor=new Encrypt(pubblica, modulo);
	}
	public static BigInteger encryptString(String message)
	{
		return Encrypt.go(message);
	}
	public static String decryptMessage(BigInteger message)
	{
		return Decrypt.go(message);
	}
  /** Generate a new public and private key set. */
	private synchronized void generateKeys()
	{
		SecureRandom r = new SecureRandom();
	    BigInteger p = new BigInteger((bitlen-1) / 2, 100, r);
	    BigInteger q = new BigInteger(bitlen / 2, 100, r);
	    N = p.multiply(q);
	    BigInteger L = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
	    e = new BigInteger("65537");
	    while (L.gcd(e).intValue() > 1) {
	    	e = e.add(new BigInteger("2"));

	    }
	    d = e.modInverse(L);
	    defineEnc(e, N);
	}
	public static BigInteger getEncrypt()
	{
		return e;
	}
	public static BigInteger getModulus()
	{
		return N;
	}
	public static BigInteger getDecrypt()
	{
		return d;
	}
}