package unibs.Model;

import java.util.List;
import java.util.Vector;

/**
 * Created by Swag on 23/12/2016.
 */
//questo è l'oggetto Esame quando viene preso e utilizzato dall'utente.
@SuppressWarnings("DefaultFileTemplate")
public class EsameUtente extends Esame {
    private static final double NONPRESENTE = -3.14;
    private static final Data DATANONPRESENTE = new Data(1,2,420);
    private static final String ESITONONPRESENTE = "nullnullnullnullnerull";
    private static final Ora ORANONPRESENTE= new Ora("24:59");

    public List<String> blocco()
    {
        return blocco(this);
    }
    private List<String> blocco(EsameUtente item)
    {
        List<String> block=new Vector<>();
        block.add("blockex");
        block.add(item.nome);
        block.add(item.tipologia.toString());
        block.add(""+item.valoreMin);
        block.add(""+item.valoreMax);
        block.add("Inizio: "+inizio.toString()+" "+orario.toString());
        block.add("Consegna: "+fine.toString());
        if (tipologia.toString().toUpperCase().equals("PERIODICO")) {
                block.add("Esito periodico: " + esitoPeriodico);
        }
        else
            block.add("Esito diagnostico: "+esitoDiagnostico);

        block.add("xekclob");
        return block;
    }
    public EsameUtente() {
        super();
    }
    public EsameUtente(List<String> blocco)
    {
        nome=blocco.get(0);
        tipologia=new TipoEsame(blocco.get(1));
        valoreMin=Double.parseDouble(blocco.get(2));
        valoreMax=Double.parseDouble(blocco.get(3));
        //preparazioni non indicative nell'utente.
        //if(blocco.size()>4) {
            String start = blocco.get(4);
            String[] split = start.split(" ");
            if (split.length > 0)
                inizio = new Data(split[1]);
            if (split.length > 1)
                orario = new Ora(split[2]);
            String stop = blocco.get(5);
            split = stop.split(" ");
            if (split.length > 1) {
                fine = new Data(split[1]);
            }
            String esito = blocco.get(6);
            split = esito.split(" ");
            if (split[1].equals("periodico"))
                tipologia = new TipoEsame(true);
            else
                tipologia = new TipoEsame(false);
            if (split.length > 2) {
                if (tipologia.toString().equals("Periodico")) {
                    try {
                        esitoPeriodico = Double.parseDouble(split[2]);
                    } catch (Exception e) {
                        esitoPeriodico = NONPRESENTE;
                    }
                } else {
                    esitoDiagnostico=split[2];
                    for(int i=3; i<split.length; i++)
                    {
                        esitoDiagnostico+=" "+split[i];
                    }
                }
            }

    }
    public EsameUtente(Esame esame) {
        super(esame);
    }


    public boolean isInData(Data data)
    {
        return !data.equals(DATANONPRESENTE);
    }
    public boolean isInOra(Ora ora)
    {
        return !ora.equals(ORANONPRESENTE);
    }
    public boolean isInEsito(String esito)
    {
        return !esito.equals(ESITONONPRESENTE);
    }
    public boolean isInEsito(double esito)
    {
        return esito==NONPRESENTE;
    }

    public void setInizio(Data in) {
        inizio=in;
    }
    public void setFine(Data fin) {
        fine=fin;
    }
    public void setOrario(Ora or) {
        orario=or;
    }
    public void setEsitoDiagnostico(String es) {
        esitoDiagnostico=es;
    }
    public void setEsitoPeriodico(double es)
    {
        esitoPeriodico=es;
    }


    public Data getInizio()
    {

        return inizio;
    }
    public Data getFine()
    {
        return fine;
    }
    public Ora getOrario()
    {
        return orario;
    }
    public String getEsitoDiagnostico()
    {
        return esitoDiagnostico;
    }
    public double getEsitoPeriodico()
    {
        return esitoPeriodico;
    }

    private Data inizio=DATANONPRESENTE;
    private Data fine=DATANONPRESENTE;
    private Ora orario=ORANONPRESENTE;
    private String esitoDiagnostico=ESITONONPRESENTE;
    private double esitoPeriodico=NONPRESENTE;

    public String toString()
    {
        List<String> block=new Vector<>();
        block.add("Nome esame: "+this.nome);
        block.add("Tipologia: "+this.tipologia.toString());
        if(!(this.valoreMin==this.valoreMax)) {
            block.add("Minima: " + this.valoreMin);
            block.add("Massima " + this.valoreMax);
        }
        if(!inizio.toString().equals(DATANONPRESENTE.toString()))
            block.add("Inizio: "+inizio.toString()+" "+orario.toString());
        if(!fine.toString().equals(DATANONPRESENTE.toString()))
            block.add("Consegna: "+fine.toString());
        if (tipologia.toString().toUpperCase().equals("PERIODICO")) {
            if(!(esitoPeriodico==NONPRESENTE))
                block.add("Esito: " + esitoPeriodico);
        }
        else
            if(!esitoDiagnostico.equals(ESITONONPRESENTE))
                block.add("Esito: "+esitoDiagnostico);
        String toRet="";
        for(String s: block)
        {
            toRet+=s+"\n";
        }
        return toRet;
    }
    public String toStringShort() {
        List<String> block = new Vector<>();
        block.add("Nome esame: " + this.nome);
        if (tipologia.toString().toUpperCase().equals("PERIODICO")) {
            if (esitoPeriodico == NONPRESENTE)
                block.add("In corso");
            else
                block.add("Terminato");
        }
        else
        {
            if(esitoDiagnostico.equals(ESITONONPRESENTE))
                block.add("In corso");
            else
                block.add("Terminato");
        }
        String toRet="";
        for(String s: block)
        {
            toRet+=s+"\n";
        }
        return toRet;
    }
}
