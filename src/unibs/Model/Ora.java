package unibs.Model;

/**
 * Created by Swag on 26/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Ora {
    private int ora;
    private int minuto;
    public Ora()
    {}
    public Ora(String tempo)
    {
        String[] split=tempo.split(":");
        ora=Integer.parseInt(split[0]);
        minuto=Integer.parseInt(split[1]);
    }
    private Ora(int hour, int minute)
    {
        ora=hour;
        minuto=minute;
    }
    public boolean isValidOrario(String tempo)
    {
        String[] split=tempo.split(":");
        int ora=0, minuto=0;
        if(split.length<2)
            return false;
        else
        {
            System.out.println("Qua è entrato");
            boolean flag=true;
            try{
                ora=Integer.parseInt(split[0]);
                minuto=Integer.parseInt(split[1]);
            }catch(Exception e)
            {
                flag=false;
            }
            return(flag&&this.isValidOrario(new Ora(ora, minuto)));
        }
    }
    private boolean isValidOrario(Ora tempo)
    {
        return isValidOra(tempo.ora)&&isValidMinuto(tempo.minuto);
    }
    private boolean isValidOra(int hour)
    {
        return hour<=24&&hour>=0;
    }
    private boolean isValidMinuto(int minuto)
    {
        return minuto<60&&minuto>=0;
    }
    public int getOra()
    {
        return ora;
    }
    public int getMinuto()
    {
        return minuto;
    }
    public String toString()
    {
        return ora+":"+minuto;
    }

}
