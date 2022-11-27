package unibs.Model;

public class Genere {

	private boolean gen;
	public Genere(boolean value)
	{
		gen=value;
	}
	public Genere(String value)
	{
		gen = value.equals("M");
	}
	public String toString()
	{
		if(gen) //1 maschio, 0 femmina
			return "M";
		else
			return "F";
	}
}
