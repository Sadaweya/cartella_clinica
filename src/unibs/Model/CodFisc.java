package unibs.Model;


public class CodFisc {

	private String CodiceFiscale;
	private String upperCase;
	private boolean valid;
	private String nome="";
	private String cognome="";
	private String nomeMod;
	private String cognomeMod;
	private Data data;

	private boolean isConsonant(char c)
	{
		return !(c=='A'||c=='E'||c=='I'||c=='O'||c=='U');
	}

	private void setMod(String in, boolean nome)
	{
		String out="";
		char[] nchar = in.toCharArray();
		for(char c: nchar)
		{
			if(isConsonant(c))
			{
				out+=c;
			}
		}
        if(nome&&out.length()>3)
        {
            out=""+out.charAt(0)+""+out.charAt(2)+""+out.charAt(3);
        }
		if(out.length()<3)
		{
			for(char c: nchar)
			{
				if(!isConsonant(c))
				{
					out+=c;
				}
			}
		}
		out=out.substring(0,3);
		if(nome)
			nomeMod=out;
		else
			cognomeMod=out;
	}
	public void setVariable(String nom, String cognom)
	{
		setNome(nom);
		setCognome(cognom);
		setVariables();
		valid=valid&&isValidName()&&isValidSurname();
	}
	private void setVariables()
	{
		nomeMod="";
		cognomeMod="";
		setMod(nome, true);
		setMod(cognome, false);
	}
	private void setNome(String in)
	{
		nome=in.toUpperCase();
	}
	private void setCognome(String in){
		cognome=in.toUpperCase();
	}
	private boolean isValidName()
	{
		String firstTree=upperCase.substring(0,3);
		return firstTree.equals(nomeMod);
	}
	private boolean isValidSurname()
	{
		String secTree=upperCase.substring(3,6);
		return secTree.equals(cognomeMod);
	}
	public boolean isValid()
	{
		return valid;
	}

	public CodFisc(String codice, Data dat)
	{
		CodiceFiscale=codice;
		data=dat;
		upperCase=CodiceFiscale.toUpperCase();
		valid=controlloCF(upperCase)&&controlloData();
	}
	public CodFisc(String codice)
	{
		CodiceFiscale=codice;
		upperCase=CodiceFiscale.toUpperCase();
		valid=controlloCF(upperCase);
	}
	private boolean controlloData()
	{
		return controlloData(data, upperCase);
	}
	private boolean controlloData(Data dat, String cf)
	{
		int aa=Integer.parseInt(cf.charAt(6)+""+cf.charAt(7));
		int gg=Integer.parseInt(cf.charAt(9)+""+cf.charAt(10));
		int mm=0;
		switch(cf.charAt(8))
		{
			case 'A':
				mm=1;
				break;
			case 'B':
				mm=2;
				break;
			case 'C':
				mm=3;
				break;
			case 'D':
				mm=4;
				break;
			case 'E':
				mm=5;
				break;
			case 'H':
				mm=6;
				break;
			case 'L':
				mm=7;
				break;
			case 'M':
				mm=8;
				break;
			case 'P':
				mm=9;
				break;
			case 'R':
				mm=10;
				break;
			case 'S':
				mm=11;
				break;
			case 'T':
				mm=12;
				break;
			default:
				break;
		}
		return aa==dat.getAnnoCod()&&mm==dat.getMese()&&gg==dat.getGiorno();
	}
	public String toString()
	{
		return upperCase;
	}
	public void insertDate(Data dat)
	{
		data=dat;
		valid=valid&&controlloData();
	}
	//Codice Fiscale Validator
	private static boolean controlloCF(String cf) 
	{
	    int i, s, c;
	    int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
	        11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	   
	    if( cf.length() == 0 ) 
	    	return false;
	    if( cf.length() != 16 )
	        return false;
	    //System.out.println(cf);
	    for( i=0; i<16; i++ )
	    {
	        c = cf.charAt(i);
	        if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
	            return false;
	    }
	    s = 0;
	    for( i=1; i<=13; i+=2 )
	    {
	        c = cf.charAt(i);
	        if( c>='0' && c<='9' )
	            s = s + c - '0';
	        else
	            s = s + c - 'A';
	    }
	    for( i=0; i<=14; i+=2)
	    {
	        c = cf.charAt(i);
	        if( c>='0' && c<='9' )   
	        	c = c - '0' + 'A';
	        s = s + setdisp[c - 'A'];
	    }
		return s % 26 + 'A' == cf.charAt(15);
	}
}
