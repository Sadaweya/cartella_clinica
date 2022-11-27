package unibs.Model;

/**
 * Created by Francesco on 08/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Gruppo
{
    private boolean RH;
    private int gruppo;
    private final String[] grp={"0", "A", "B", "AB"};

    public Gruppo()
    {}
    public Gruppo(String gru, String rh)
    {
        String inter=gru.toUpperCase();
        switch(inter)
        {
            case "0":
                gruppo=0;
            break;
            case "A":
                gruppo=1;
                break;
            case "B":
                gruppo=2;
                break;
            case "AB":
                gruppo=3;
                break;
            default:
                gruppo=3;
                break;
        }
        inter=rh;
        switch(inter)
        {
            case "+":
                RH=true;
                break;
            case "-":
                RH=false;
                break;
            default:
                break;
        }
    }

    public boolean isGroup(String test)
    {
        boolean ex=false;
        for(int i=0; i<4; i++)
        {
            if(grp[i].equalsIgnoreCase(test))
            {
                ex=true;
                i=4;
            }
        }
        return ex;
    }
    public boolean isRH(String test)
    {
        boolean ex=false;
        if(test.equals("+")||test.equals("-"))
        {
            ex=true;
        }
        return ex;
    }
    public static Gruppo file(String fromFile)
    {
        String[] split=fromFile.split(" ");
        return new Gruppo(split[1], split[2]);
    }
    public String toString()
    {
        String ret="Gruppo: ";
        ret+=grp[gruppo];
        if(RH)
            ret+=" +";
        else
            ret+=" -";
        return ret;
    }
}
