package unibs.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {

	public Data(String data)
	{
		String[] split=data.split("/");
		giorno=Integer.parseInt(split[0]);
		mese=Integer.parseInt(split[1]);
		anno=Integer.parseInt(split[2]);
		valid=isValidData(this.toString());
	}
	public Data(int gg, int mm, int aaaa)
	{
		giorno=gg;
		mese=mm;
		anno=aaaa;
		valid=isValidData(this.toString());
	}
	private boolean valid;
	private int anno;
	private int mese;
	private int giorno;
	public int getAnno()
	{
		return anno;
	}
	public int getAnnoCod()
	{
		int modAnno=anno;
		double firstTw=modAnno/100;
		firstTw=Math.floor(firstTw);
		firstTw*=100;
		firstTw=modAnno-firstTw;
		modAnno=(int)firstTw;
		return modAnno;
	}
	public int getMese()
	{
		return mese;
	}
	public int getGiorno()
	{
		return giorno;
	}

	public String toString()
	{
		return giorno+"/"+mese+"/"+anno;
	}

	private static boolean isValidData(String dateToValidate, String dateFormat)
	{
		if(dateToValidate == null)
		{
			return false;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try
		{
			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static boolean isValidData(String dateToValidate)
	{
		return isValidData(dateToValidate, "dd/MM/yyyy");
	}


}
