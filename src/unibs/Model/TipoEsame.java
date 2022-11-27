package unibs.Model;

/**
 * Created by Francesco on 21/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TipoEsame {
    private static final String periodico="Periodico";
    private static final String diagnostico="Diagnostico";
    private boolean tipo;
    public TipoEsame()
    {
    }
    public TipoEsame(boolean value)
    {
        tipo=value;
    }
    public TipoEsame(String value)
    {
        this(value.toUpperCase().equals(periodico.toUpperCase()));
    }
    public static boolean isValidTipo(String val)
    {
        return val.equals(periodico.toUpperCase())||val.equals(diagnostico.toUpperCase());
    }
    public String toString()
    {
        if(tipo)
            return periodico;
        else
            return diagnostico;
    }
}
