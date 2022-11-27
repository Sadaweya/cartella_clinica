package unibs.Control;
import java.util.*;

public class Helper {
	
	private Scanner sc;
	{
		sc=new Scanner(System.in);
		//sc.useDelimiter("\r\n");
	}
	//scrive una stringa
	public void scrivi(List<String> input)
	{
		input.forEach(System.out::println);
	}
	public void scrivi(String input)
	{
		System.out.println(input);
	}
	
	String getString(String frase)
	{
		scrivi(frase);
		return getString();
	}
	private String getString()
	{
		if(sc.hasNextLine())
			return sc.nextLine();
		else
			return getString();
	}
	//region "INTERI" 
	//override che permette l'inserimento di una frase. Un intero compreso tra min e max
	protected int minMaxInputInt(int min, int max, String frase)
	{
		scrivi(frase);
		return minMaxInputInt(min, max);
	}
	//double compreso tra min e max
	public int minMaxInputInt(int min, int max)
	{
		int num=inputInt();
		boolean rifare=false;
		if(num>max)
		{
			scrivi("Il numero inserito e' troppo elevato");
			rifare=true;
		}
		else
		{
			if(num<min)
			{
				scrivi("Il numero inserito e' troppo basso");
				rifare=true;
			}
		}
		if(rifare)
		{
			num=minMaxInputInt(min, max);
		}
		return num;
	}
	//ottiene un intero con valore massimo pari a max
    private int maxInputInt(int max)
	{
		int num=inputInt();
		if(num>max)
		{
			scrivi("Il numero inserito � troppo elevato");
			num=maxInputInt(max);
		}
		else
		{
			scrivi("Il numero inserito � valido");
		}
		return num;
	}
	//override del max, permette di inserire una frase
	protected int maxInputInt(int max, String frase)
	{
		scrivi(frase);
		return maxInputInt(max);
	}
	//min input riceve un intero con valore minimo pari a min
    private int minInputInt(int min)
	{
		int num=inputInt();
		if(num<min)
		{
			scrivi("Il numero inserito � troppo basso");
			num=maxInputInt(min);
		}
		else
		{
			scrivi("Il numero inserito � valido");
		}
		return num;
	}	
	//override di minInput, permette di inserire una frase
	protected int minInputInt(int min, String frase)
	{
		scrivi(frase);
		return minInputInt(min);
	}
	//input di un numero intero. Prende in ogni caso un numero intero.
    private int inputInt()
	{
		int numero=0;
		boolean bError=true;
		while (bError) 
		{
			try
			{
		        if (sc.hasNextInt())
		            numero=sc.nextInt();
		        else {
		        	scrivi("Non hai inserito un numero intero, riprova!");
		            sc.next();
		            continue;
		        }
		        bError = false;
			}
			catch(Exception e)
			{
				scrivi("Errore: "+e.toString());
				bError=true;
				sc.next();
			}
	    }
		return numero;
	}
	//override di inputInt, permette di inserire una frase
	protected int inputInt(String frase)
	{
		scrivi(frase);
		return inputInt();
	}
	//endregion "INTERI"
	
	//region "DOUBLE"
	//override che permette l'inserimento di una frase. Un double compreso tra min e max
		protected double minMaxInputDouble(double min, double max, String frase)
		{
			scrivi(frase);
			return minMaxInputDouble(min, max);
		}
		//double compreso tra min e max
        private double minMaxInputDouble(double min, double max)
		{
			double num=inputDouble();
			boolean rifare=false;
			if(num>max)
			{
				scrivi("Il numero inserito e' troppo elevato");
				rifare=true;
			}
			else
			{
				if(num<min)
				{
					scrivi("Il numero inserito e' troppo basso");
					rifare=true;
				}
			}
			if(rifare)
			{
				num=minMaxInputDouble(min, max);
			}
			return num;
		}
		//ottiene un double con valore massimo pari a max
        private double maxInputDouble(double max)
		{
			double num=inputDouble();
			if(num>max)
			{
				scrivi("Il numero inserito � troppo elevato");
				num=maxInputDouble(max);
			}
			else
			{
				scrivi("Il numero inserito � valido");
			}
			return num;
		}
		//override del max, permette di inserire una frase
		protected double maxInputDouble(double max, String frase)
		{
			scrivi(frase);
			return maxInputDouble(max);
		}
		//min input riceve un double con valore minimo pari a min
        private double minInputDouble(double min)
		{
			double num=inputDouble();
			if(num<min)
			{
				scrivi("Il numero inserito � troppo basso");
				num=maxInputDouble(min);
			}
			else
			{
				scrivi("Il numero inserito � valido");
			}
			return num;
		}	
		//override di minInput, permette di inserire una frase
		protected double minInputDouble(double min, String frase)
		{
			scrivi(frase);
			return minInputDouble(min);
		}
		//input di un numero. Prende in ogni caso un numero.
        private double inputDouble()
		{
			double numero=0;
			boolean bError=true;
			while (bError) 
			{
				try
				{
			        if (sc.hasNextDouble())
			            numero=sc.nextDouble();
			        else {
			        	scrivi("Non hai inserito un numero, riprova!");
			            sc.next();
			            continue;
			        }
			        bError = false;
				}
				catch(Exception e)
				{
					scrivi("Errore: "+e.toString());
					bError=true;
					sc.next();
				}
		    }
			return numero;
		}
		//override di inputDouble, permette di inserire una frase
        double inputDouble(String frase)
		{
			scrivi(frase);
			return inputDouble();
		}
		//endregion "DOUBLE"
}
