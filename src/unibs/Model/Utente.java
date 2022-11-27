package unibs.Model;

import javax.mail.internet.InternetAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Utente {


    public Utente()
    {}
    public Utente(String n, String c, String i, String m, Data dat, String l, Genere g, CodFisc cod, Gruppo s, String pass, String aidi)
    {
        nome=n;
        cognome=c;
        indirizzo=i;
        mail=m;
        nascita=dat;
        luogo=l;
        genere=g;
        codiceFiscale=cod;
        id=aidi;
        sangue=s;
        password=pass;
        setSanitario();
    }
    public Utente(String n, String c, String i, String m, Data dat, String l, Genere g, CodFisc cod, Gruppo s, String pass, String aidi, List<EsameUtente> listaEsami)
    {
        nome=n;
        cognome=c;
        indirizzo=i;
        mail=m;
        nascita=dat;
        luogo=l;
        genere=g;
        codiceFiscale=cod;
        id=aidi;
        sangue=s;
        password=pass;
        esame=listaEsami;
        setSanitario();
    }
    private static void setSanitario()
    {
        byte[] fisc=codiceFiscale.toString().getBytes(StandardCharsets.UTF_8);
        sanitario=0;
        //trasformo il byte array in un numero.
        for(int i=0; i<fisc.length; i++) {
            sanitario += fisc[i] * (fisc.length - i) * 256;
        }
    }
    private static double sanitario;
    private static String nome;
    private static String cognome;
    private static String indirizzo;
    private static String mail;
    private static Data nascita;
    private static String luogo;
    private static Genere genere;
    private static CodFisc codiceFiscale;
    private static String id;
    private static Gruppo sangue;
    private static String password;
    public List<EsameUtente> esame=new Vector<>();


    public String getNome()
    {
        return nome;
    }
    public String getCognome()
    {
        return cognome;
    }
    public String getIndirizzo()
    {
        return indirizzo;
    }
    public String getMail()
    {
        return mail;
    }
    public Data getDataNascita()
    {
        return nascita;
    }
    public String getLuogo()
    {
        return luogo;
    }
    public Genere getGenere()
    {
        return genere;
    }
    public CodFisc getCodiceFiscale()
    {
        return codiceFiscale;
    }
    public Gruppo getGruppo() { return sangue; }
    public String getPassword()
    {
        return password;
    }
    public String getCodiceSanitario()
    {
        return id;
    }

    public List<String> getEsami()
    {
        return esame.stream().map(EsameUtente::toString).collect(Collectors.toList());
    }
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    //Va bene anche per il cognome.
    public static boolean isValidName(String nome)
    {
        String evaluate=nome;
        evaluate=evaluate.toLowerCase();
        char[] arr=evaluate.toCharArray();
        boolean errore=false;
        int counter=0;
        while(!errore&&counter<arr.length)
        {
            if((arr[counter]>='a'&&arr[counter]<='z')||arr[counter]==' ')
            {
                counter++;
            }
            else
            {
                errore=true;
            }
        }
        return !errore;
    }

    public List<String> getList()
    {
        List<String> lista=new Vector<>();
        lista.add(password);
        lista.add(nome);
        lista.add(cognome);
        lista.add(indirizzo);
        lista.add(mail);
        lista.add(nascita.toString());
        lista.add(codiceFiscale.toString());
        lista.add(luogo);
        lista.add(genere.toString());
        lista.add(id);
        lista.add(sangue.toString());
        for(EsameUtente ex: esame)
        {
            ex.blocco().forEach(lista::add);
        }
        return lista;
    }
    public  Utente fromString(List<String> field)
    {
        List<String> file=field.stream().collect(Collectors.toList());
        String p=file.get(0);
        String n=file.get(1);
        String c=file.get(2);
        String i=file.get(3);
        String m=file.get(4);
        Data temp=new Data(file.get(5));
        CodFisc temp2=new CodFisc(file.get(6));
        String l=file.get(7);
        Genere g=new Genere(file.get(8));
        String i2=file.get(9);
        Gruppo gr=Gruppo.file(file.get(10));


        List<String> toDecapsule=file.stream().collect(Collectors.toList());
        List<String> block= new Vector<>();
        List<EsameUtente> esamiTemporeanei=new Vector<>();
        for(String s : toDecapsule)
        {
            if(s.equals("blockex"))
            {
                block=new Vector<>();
            }
            else {
                if (s.equals("xekclob"))
                {
                    EsameUtente gnu=new EsameUtente(block);
                    esamiTemporeanei.add(gnu);
                } else{
                    block.add(s);
                }
            }
        }
        return new Utente(n, c, i, m, temp, l, g, temp2, gr, p, i2, esamiTemporeanei);
    }
    public String toString()
    {

        String visualizza="Codice Sanitario: "+sanitario+"\n"+
                            "Nome: "+nome+"\n"+
                            "Cognome: "+cognome+"\n"+
                            "Indirizzo: "+indirizzo+"\n";
        if(!mail.isEmpty())
            visualizza+="Email: "+mail+"\n";
        visualizza+="Genere: "+genere.toString()+"\n"+
                "Codice Fiscale: "+codiceFiscale.toString()+"\n"+
                "nato il: "+nascita.toString()+"\n"+
                "a: "+luogo+"\n"+
                sangue.toString()+"\n";

        return visualizza;
    }
    public String toStringDetailed()
    {
        return this.toString();
    }
    public String toStringShort()
    {
        return "\nNome: "+nome+"\n"+
                "Cognome: "+cognome+"\n"+
                "Indirizzo: "+indirizzo+"\n"+
                "nato il: "+nascita.toString()+"\n";
    }
    public void addEsame(EsameUtente ex)
    {
        esame.add(ex);
    }
    public void addEsame(Esame ex)
    {
        esame.add(new EsameUtente(ex));
    }
}