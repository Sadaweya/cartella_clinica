package unibs.Model;

import java.util.List;
import java.util.Vector;

/**
 * Created by Francesco on 21/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Esame {



    Esame() //default constructor, necessario per l'extends in EsameUtente
    {}
    public Esame(List<String> blocco)
    {
        nome=blocco.get(0);
        tipologia=new TipoEsame(blocco.get(1));
        valoreMin=Double.parseDouble(blocco.get(2));
        valoreMax=Double.parseDouble(blocco.get(3));
        String s;
        int k=4;
        while(k<blocco.size())
        {
            s=blocco.get(k++);
            preparazioni.add(s);
        }
        setError();
    }
    public Esame(String nomeEsame, String tipo, double min, double max, List<String> prep)
    {
        nome=nomeEsame;
        tipologia=new TipoEsame(tipo);
        valoreMin=min;
        valoreMax=max;
        preparazioni=prep;
        setError();
    }
    public Esame(String nomeEsame, String tipo, double min, double max, String prep)
    {
        nome=nomeEsame;
        tipologia=new TipoEsame(tipo);
        valoreMin=min;
        valoreMax=max;
        preparazioni.add(prep);
        setError();
    }
    Esame(Esame ex)
    {
        this.nome=ex.nome;
        this.tipologia=ex.tipologia;
        this.valoreMin=ex.valoreMin;
        this.valoreMax=ex.valoreMax;
        this.preparazioni=ex.preparazioni;
        setError();
    }

    private List<String> blocco(Esame item)
    {
        List<String> block=new Vector<>();
        block.add("blockex");
        block.add(item.nome);
        block.add(item.tipologia.toString());
        block.add(""+item.valoreMin);
        block.add(""+item.valoreMax);
        preparazioni.forEach(block::add);
        block.add("xekclob");
        return block;
    }
    public List<String> blocco()
    {
        return this.blocco(this);
    }
    public String getName()
    {
        return nome;
    }
    public TipoEsame getTipologia()
    {
        return tipologia;
    }
    private void setError()
    {
        errorMin=valoreMin/10;
        errorMax=valoreMax*10;
    }

    String nome;
    TipoEsame tipologia;
    double valoreMin;
    double valoreMax;
    private List<String> preparazioni=new Vector<>();
    private double errorMin;
    private double errorMax;
    public double getErrorMax()
    {
        setError();
        return errorMax;
    }
    public double getErrorMin()
    {
        setError();
        return errorMin;
    }
    public String toString()
    {
        String toRet=" ";
        toRet +=" Esame: " +nome+
                " \nTipo: "+ tipologia.toString();
        if(valoreMin!=valoreMax)
            toRet+="\nRange valori standard: "+valoreMin+ "-"+valoreMax;

        toRet+= "\nPreparazioni: "+ preparazioni;

        return toRet;
    }
    public String toStringShort()
    {
        return "Esame: "+nome;
    }
}
