package unibs.Control;
/*
Dati in uso al momento
ama123
mdrfnc95s23b157w
23/11/1995
 */
import unibs.View.View;
import unibs.CryptAndSave.Open;
import unibs.Model.*;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public class Principale {

    private final Helper help;
    private Utente utente;
    private List<Esame> esami=new Vector<>();
    private static EsameUtente temp;

    //region METODI

    private void caricaEsami()
    {
        Open temp=new Open();
        //noinspection ResultOfMethodCallIgnored
        while(!temp.ApriEsami()){}
        List<String> toDecapsule = temp.file.stream().collect(Collectors.toList());
        List<String> block= new Vector<>();
        //Ogni esame è un blocco nella lista degli esami
        //Ogni blocco inizia con "blockex" e finisce con "xekclob"
        //Vengono inseriti automaticamente al salvataggio, worry not.
        //alg: controllo se la stringa è uguale a blockex. Se lo è, reinizializzo la lista blocco perdendo tutti i dati precedenti
        //controllo se la stringa è uguale a xekclob. Se lo è creo l'oggetto esame e lo aggiungo alla lista degli esami.
        //se non è nessuno dei due aggiungo la stringa al blocco
        for(String s : toDecapsule)
        {
            if(s.equals("blockex"))
            {
                block=new Vector<>();
            }
            else {
                if (s.equals("xekclob"))
                {
                    Esame gnu=new Esame(block);
                    esami.add(gnu);
                } else{
                    block.add(s);
                }
            }
        }
    }
    private int scegliEsamePerPrenotazione(List<Esame> lista)
    {
        if(lista.size()>1)
        {
            int count=1;
            for(Esame e: lista)
                help.scrivi(""+(count++)+") "+e.toStringShort());

            String input=help.getString("Scegli un esame inserendo il numero o un nome per filtrare gli esami");


            if(isNumber(input))
            {
                int number=Integer.parseInt(input)-1;
                if(number>=lista.size()||number<0)
                {
                    help.scrivi("Errore nella scelta, riprovare");
                    return scegliEsamePerPrenotazione(lista);
                }
                else {
                    return esami.indexOf(lista.get(number));
                }
            }
            else
            {
                return scegliEsamePerPrenotazione(lista.stream().filter(e-> e.getName().contains(input)).collect(Collectors.toList()));
            }
        }
        else
        {
            if (lista.size() == 1)
            {
                return esami.indexOf(lista.get(0));
            }
            else
            {
                help.scrivi("Esame non trovato");
                String scelta;
                boolean flag;
                do {
                    scelta=help.getString("Vuoi riprovare a scegliere un esame dalla lista? (S/N)");
                    scelta=scelta.toUpperCase();
                    flag = !scelta.equals("S") && !scelta.equals("N");
                }while(flag);
                if(scelta.equals("S"))
                {
                    return scegliEsamePerPrenotazione(esami);
                }
                else
                    return -1;
            }
        }
    }

    private int scegliEsame(List<EsameUtente> lista)
    {
        if(lista.size()>1)
        {
            int count=1;
            for(EsameUtente e: lista)
                help.scrivi(""+(count++)+") "+e.toStringShort());

            String input=help.getString("Scegli un esame inserendo il numero o un nome per filtrare gli esami");


            if(isNumber(input))
            {
                int number=Integer.parseInt(input)-1;
                if(number>=lista.size()||number<0)
                {
                    help.scrivi("Errore nella scelta, riprovare");
                    return scegliEsame(lista);
                }
                else {
                    return utente.esame.indexOf(lista.get(number));
                }
            }
            else
            {
                return scegliEsame(lista.stream().filter(e-> e.getName().contains(input)).collect(Collectors.toList()));
            }
        }
        else
        {
            if (lista.size() == 1)
            {
                return utente.esame.indexOf(lista.get(0));
            }
            else
            {
                help.scrivi("Esame non trovato");
                String scelta;
                boolean flag;
                do {
                    scelta=help.getString("Vuoi riprovare a scegliere un esame dalla lista? (S/N)");
                    scelta=scelta.toUpperCase();
                    flag = !scelta.equals("S") && !scelta.equals("N");
                }while(flag);
                if(scelta.equals("S"))
                {
                    return scegliEsame(utente.esame);
                }
                else
                    return -1;
            }
        }
    }
    private Optional<Esame> cercaEsame(String nome)
    {
        return esami.stream().filter(e-> e.getName().toUpperCase().equals(nome.toUpperCase())).findFirst();
    }
    private boolean aggiungiEsame() {
        boolean choosen = true;
        boolean stop= false;
        String nome;
        do {
            nome=help.getString("Cerca un esame da aggiungere: ");
            Optional<Esame> ex=cercaEsame(nome);
            if(!ex.isPresent())
            {
                String answer;
                do {
                    answer=help.getString("Esame non presente, vuoi interrompere la ricerca? (S/N)");
                    if(!(answer.toUpperCase().equals("S"))&&!(answer.toUpperCase().equals("N")))
                    {
                        help.scrivi("Valore inserito non corretto");
                    }
                    else
                    {
                        stop=answer.toUpperCase().equals("S");
                    }
                }while(!(answer.toUpperCase().equals("S"))&&!(answer.toUpperCase().equals("N")));
            }
            else
            {
                help.scrivi("Hai scelto: "+ex.get().toString());
                String answer;
                do {
                    answer=help.getString("Confermare la scelta? (S/N)");
                    if(!(answer.toUpperCase().equals("S"))&&!(answer.toUpperCase().equals("N")))
                    {
                        help.scrivi("Valore inserito non corretto");
                    }
                    else
                    {
                        choosen=answer.toUpperCase().equals("S");
                        if(choosen)
                        {
                            help.scrivi("Inserisci la data di inizio");
                            Data d=chiediData();
                            help.scrivi("Inserisci l'ora della prenotazione");
                            Ora o=chiediOrario();
                            temp=new EsameUtente(ex.get());
                            temp.setInizio(d);
                            temp.setOrario(o);
                        }
                    }
                }while(!(answer.toUpperCase().equals("S"))&&!(answer.toUpperCase().equals("N")));
            }
        }while(!stop&&!choosen);
        return stop;
    }
    private void eliminaEsame(int id)
    {
        utente.esame.remove(id);
    }
    private boolean prenotazioneEsame()
    {
        int id=scegliEsamePerPrenotazione(esami);
        if(id==-1)
            return false;
        else
        {
            temp=new EsameUtente(esami.get(id));
            return true;
        }
    }
    private void eliminaPrenotazione()
    {
        int id=scegliEsame(utente.esame);
        if(id!=-1)
        {
            help.scrivi("Esame da eliminare: "+utente.esame.get(id));
            eliminaEsame(id);
            //help.scrivi("Qua dovrebbe esserci l'eliminazione ma sono pigro e voglio testare");
        }
    }




    private boolean isNumber(String input)
    {
        char[] in=input.toCharArray();
        boolean flag=true;
        for(char c: in)
        {
            if(c<'0'||c>'9')
                flag=false;
        }
        return flag;
    }
    private Ora chiediOrario()
    {
        String hour=null;
        Ora ora=new Ora();
        boolean corretto=false;
        while(!corretto)
        {
            hour = help.getString("Inserisci orario (formato: hh:mm) ");
            if(ora.isValidOrario(hour)) {
                help.scrivi("Inserita correttamente!");
                corretto = true;
            }
            else
                help.scrivi("L'orario inserito non è valido");
        }
        return new Ora(hour);
    }
    private Genere chiediGenere()
    {
        String genere= null;
        boolean corretto=false;
        while(!corretto)
        {
            genere=help.getString("Insersci il genere (M/F)");
            genere=genere.toUpperCase();
            if(genere.equals("F")||genere.equals("M"))
            {
                help.scrivi("Inserito correttamente!");
                corretto=true;
            }
            else
            {
                help.scrivi("Il genere inserito non è valido");
            }
        }
        return new Genere(genere);
    }
    private CodFisc chiediFiscale()
    {
        return chiediFiscale(null);
    }
    private CodFisc chiediFiscale(Data date)
    {
        CodFisc codice=null;
        String data;

        String cod;
        boolean corretto=false;
        while(!corretto)
        {
            boolean codiceCorretto;
            do {
                cod = help.getString("Inserisci codice fiscale");
                codiceCorretto=new CodFisc(cod).isValid();
            }while(!codiceCorretto);
            Data dat=null;
            if(date!=null)
            {
                dat=date;
            }
            else {
                try {
                    data = help.getString("Inserisci data di nascita (formato: dd/mm/yyyy) ");
                    if (Data.isValidData(data)) {
                        help.scrivi("Inserita correttamente!");
                    } else
                        help.scrivi("La data inserita non è valida");
                    dat = new Data(data);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            codice=new CodFisc(cod, dat);
            if(codice.isValid())
            {
                help.scrivi("Hai inserito tutto correttamente");
                corretto=true;
            }
            else
                help.scrivi("Hai inserito un codice non valido oppure non corrispondente alla data di nascita");
        }
        return codice;
    }
    private Data chiediData()
    {
        String data= null;
        Data dat;
        boolean corretto=false;
        do
        {
            data = help.getString("Inserisci data (formato: dd/mm/yyyy) ");
            if(Data.isValidData(data)) {
                help.scrivi("Inserita correttamente!");
                corretto = true;
            }
            else
                help.scrivi("La data inserita non è valida");
        }while(!corretto);
        dat=new Data(data);
        return dat;

    }
    private String chiediEmail()
    {
        String email= null;
        boolean corretto=false;
        while(!corretto)
        {
            email = help.getString("Inserisci email ");
            if(Utente.isValidEmailAddress(email)) {
                help.scrivi("Inserita correttamente!");
                corretto = true;
            }
            else
                help.scrivi("L'email inserita è non valida");
        }
        return email;
    }
    private String chiediNome(boolean cognome)// true=cognome false=nome
    {
        String nome= null;
        String complete;
        if(cognome)
            complete="cognome";
        else
            complete="nome";
        boolean corretto=false;
        while(!corretto)
        {
            nome = help.getString("Inserisci "+complete+" ");
            if(Utente.isValidName(nome)) {
                help.scrivi("Inserito correttamente!");
                corretto = true;
            }
            else
                help.scrivi("Il "+complete+" è errato");
         }
        return nome;
    }
    private double chiediNum(String input)
    {
        return help.inputDouble(input);
    }
    private String chiediIndirizzo()
    {
        String indirizzo= null;
        boolean corretto=false;
        while(!corretto)
        {
            indirizzo = help.getString("Inserisci l'indirizzo ");
            corretto=true;
            help.scrivi("Inserito correttamente!");
        }
        return indirizzo;
    }
    private String chiediLuogo()
    {
        String luogo= null;
        boolean corretto=false;
        while(!corretto)
        {
            luogo = help.getString("Inserisci luogo di nascita ");
            corretto=true;
            help.scrivi("Inserito correttamente!");

        }
        return luogo;

    }
    private Gruppo chiediGruppo()
    {

        Gruppo gruppo=new Gruppo();
        boolean corretto=false;
        String grp=null;
        String rh=null;
        while(!corretto)
        {
            grp = help.getString("Inserisci un gruppo sanguigno (0, A, B, AB)");
            if(gruppo.isGroup(grp))
            {
                help.scrivi("Gruppo inserito!");
                corretto=true;
            }
            else
                help.scrivi("Hai inserito una gruppo non valido");
        }
        corretto=false;
        while(!corretto)
        {
            rh= help.getString("Inserisci un RH (+, -)");
            if(gruppo.isRH(rh))
            {
                help.scrivi("RH inserito!");
                corretto=true;
            }
        }
        gruppo=new Gruppo(grp, rh);
        help.scrivi(gruppo.toString());
        return gruppo;
    }
    private String chiediPassword()
    {
        boolean corretto=false;
        String password=null;
        while(!corretto)
        {
            password=help.getString("Inserisci la password per l'utente: ");
            String password2=help.getString("Inserisci nuovamente la password: ");
            if(password.equals(password2)&&!password.isEmpty())
            {
                help.scrivi("Password inserita correttamente! ");
                corretto=true;
            }
            else
            {
                help.scrivi("Le due password non coincidono oppure sono vuote");
            }
        }
        return password;
    }
    private String chiediTipologia()
    {
        String tipologia= null;
        boolean corretto=false;
        while(!corretto)
        {
            tipologia = help.getString("Periodico o diagnostico: ");
            tipologia=tipologia.toUpperCase();
            if(TipoEsame.isValidTipo(tipologia)) {
                help.scrivi("Inserito correttamente!");
                corretto = true;
            }
            else
                help.scrivi("La tipologia inserita è non valida");
        }
        return tipologia;
    }
    //endregion
    //region STATI
    private String menùIniziale()
    {
        String prossimo=null;
        int scelta=View.Menu(0);
        switch(scelta)
        {
            case 1:
                String password=help.getString("Inserisci la password");
                CodFisc fiscale=chiediFiscale();
                Open apertura= new Open(password, fiscale);
                if(!apertura.Apri())
                {
                    if(apertura.denied) {
                        help.scrivi("Password o codice fiscale errati");
                        prossimo="MenùIniziale";
                    }
                    else
                    {
                        help.scrivi("Sono stati riscontrati errori, verrà rivisualizzata la schermata precedente");
                        prossimo="MenùIniziale";
                    }
                }
                else
                {
                    apertura.decrypt();
                    utente=utente.fromString(apertura.file);
                    prossimo="MenùUtente";
                }
                break;
            case 2:
                try {
                    List<String> file;
                    Open open=new Open(" ", new CodFisc(" "));
                    open.Apri();
                    file = open.file.stream().collect(Collectors.toList());
                    if (file.isEmpty())
                    {
                        prossimo ="CreazioneUtente";
                    }
                    else
                    {
                        help.scrivi("Pare esista già un utente. Per proseguire inserire password e codice fiscale dell'utente esistente");
                        String passwor=help.getString("Inserisci la password");
                        CodFisc fiscal=chiediFiscale();
                        Open apertur= new Open(passwor, fiscal);
                        if(apertur.Apri())
                        {
                            help.scrivi("Verrà creato un nuovo utente");
                            prossimo="CreazioneUtente";
                        }
                        else
                        {
                            help.scrivi("Codice fiscale o password errati, verrà visualizzata nuovamente la schermata precedente");
                            prossimo="CreazioneUtente";
                        }
                    }
                }catch(Exception e)
                {
                    e.toString();
                }

                break;
            case 3:
                prossimo="MenùEsami";
                break;
            default:
                prossimo="Fine";
                break;
        }
        return prossimo;
    }
    private String menùEsami()
    {
        int scelta=View.Menu(3);
        String prossimo="Fine";
        switch(scelta)
        {
            case 1:
                prossimo="CreazioneEsame";
                break;
            case 2:
                esami.forEach(e -> help.scrivi(e.toString() + "\n"));
                prossimo="MenùEsami";
                break;
            case 3:
                prossimo="SalvataggioEsami";
                break;

        }
        return prossimo;
    }
    private String menùUtente()
    {
        /*"Benvenuto nel menù di scelta della cartella clinica\n"+
            "1)Visualizzare i dati del paziente\n"+
            "2)Modificare i dati del paziente\n"+
            "3)Visualizzare gli esami prenotati\n"+
            "4)Prenotare un esame\n"+
            "5)Inserire i risultati di un esame terminato\n"+
            "6)Modifica esame esistente\n"+
            "7)Elimina esame esistente\n"+
            "8)Salvare e uscire\n"+
            "9)uscire senza salvare \n";

         */
        int scelta=View.Menu(1);
        String prossimo="Fine";
        switch(scelta)
        {
            case 1:
                help.scrivi(utente.toString());
                prossimo="MenùUtente";
                break;
            case 2:
                prossimo="ModificaUtente";
                break;
            case 3:
                utente.esame.stream().map(EsameUtente::toStringShort).forEach(System.out::println);
                prossimo="MenùUtente";
                break;
            case 4:
                if(prenotazioneEsame())
                {
                    utente.addEsame(temp);
                    salvataggio();
                }

                prossimo="MenùUtente";
                break;
            case 5://modifica esame esistente
                modificaEsame();
                prossimo="MenùUtente";
                break;
            case 6: //eliminazione esame esistente
                eliminaPrenotazione();
                prossimo="MenùUtente";
                break;
            case 7:
                prossimo="SalvataggioEUscita";
                break;
            case 8:
                prossimo="MenùIniziale";
                break;
            default:
                break;
        }
        return prossimo;
    }
    private String menùModUtente()
    {

        String prossimo="Fine";
        int scelta=View.Menu(4);
        switch(scelta)
        {
            case 1:
                prossimo="visualizzaDati";
                break;
            case 2:
                prossimo="nome";
                break;
            case 3:
                prossimo="cognome";
                break;
            case 4:
                prossimo="indirizzo";
                break;
            case 5:
                prossimo="email";
                break;
            case 6:
                prossimo="genere";
                break;
            case 7:
                prossimo="codiceFiscale";
                break;
            case 8:
                prossimo="dataNascita";
                break;
            case 9:
                prossimo="luogo";
                break;
            case 10:
                prossimo="gruppo";
                break;
            case 11:
                prossimo="password";
                break;
            case 12:
                prossimo="Esci";
                break;
        }
        return prossimo;
    }
    private String menuModEsamiUt()
    {
        String prossimo="Fine";
        int scelta=View.Menu(5);
        switch(scelta) {
            case 1:
                prossimo = "visualizzaDati";
                break;
            case 2:
                prossimo = "inizio";
                break;
            case 3:
                prossimo = "fine";
                break;
            case 4:
                prossimo = "orario";
                break;
            case 5:
                prossimo = "esito";
                break;
            case 6:
                prossimo = "Esci";
                break;
        }
        return prossimo;
    }
    @SuppressWarnings("SameReturnValue")
    private String salvataggio() //lo stato cinque è il salvataggio. L'accesso è dato scontato.
    {
        Open open=new Open(utente.getPassword(), utente.getCodiceFiscale());
        open.Save(utente);
        salvataggioEsami();
        return "MenùUtente";
    }
    @SuppressWarnings("SameReturnValue")
    private String salvataggioEsami()
    {
        Open open=new Open();
        List<String> res= new Vector<>();
        esami.stream()
                .map(Esame::blocco) //trasformo ogni esame nella lista in una lista di stringhe.
                //ho quindi una lista di lista di stringhe
                .forEachOrdered(ls->ls.stream()
                        //stream su ogni oggetto lista di stringhe nella lista
                        .forEachOrdered(res::add)
                );
        open.SalvaEsami(res);
        return "MenùIniziale";
    }
    @SuppressWarnings("SameReturnValue")
    private String salvataggioEUscita()
    {
        salvataggio();
        return "MenùIniziale";
    }
    private String creazioneUtente() //Inserimento dati utente, l'accesso è dato scontato.
    {
        help.scrivi("Inserimento dati dell'utente");
        String nome=chiediNome(false);
        String cognome=chiediNome(true);
        String indirizzo=chiediIndirizzo();
        String mail=chiediEmail();
        Data nascita=chiediData();
        String luogo=chiediLuogo();
        Genere genere=chiediGenere();
        CodFisc codice=chiediFiscale(nascita);
        String codiceSanitario=codice.toString(); //sistemare, vuole un algoritmo a scelta.
        Gruppo gruppo=chiediGruppo();
        String password=chiediPassword();
        Utente ut=new Utente(nome, cognome, indirizzo, mail, nascita, luogo, genere, codice, gruppo, password, codiceSanitario);
        help.scrivi("Il nuovo utente creato è: "+ut.toString());
        String scelta;
        String ritorno="MenùIniziale";
        boolean corretto=false;
        do {
            scelta = help.getString("Vuoi salvare i dati? Ogni dato precedentemente scritto verrà sovrascritto! S/N");
            scelta = scelta.toUpperCase();
            corretto = scelta.equals("S") || scelta.equals("N");
            if (corretto) {
                if (scelta.equals("S")) {
                    //salva i dati
                    utente = ut;
                    salvataggio();
                    ritorno = "Salvataggio";
                    corretto = true;
                }
            }
            else
            {
                help.scrivi("Valore non inserito correttamente!");
            }
        }while(!corretto);
        return ritorno;
    }
    @SuppressWarnings("SameReturnValue")
    private String creazioneEsame()
    {
        String nome;
        boolean flag;
        boolean cont=true;
        nome=chiediNome(false);
        //help.scrivi(nome);
        boolean exists=false;
        for(Esame e: esami)
        {
            if(e.getName().equals(nome)) {
                exists = true;
                //help.scrivi(e.getName());
                break;
            }
        }
        if(!exists)
        {
            flag=false;
        }
        else
        {
            String scelta;
            do {
                scelta = help.getString("Esame già presente. Vuoi proseguire con l'inserimento dei dati? (S/N)");
            }while(!scelta.toUpperCase().equals("S")&&!scelta.toUpperCase().equals("N"));
            cont=scelta.toUpperCase().equals("S");
            flag=false;
        }
        if(!cont)
        {
            return "MenùEsami";
        }
        else {
            double valMin = 0;
            double valMax = 0;
            String tipologia = chiediTipologia();
            if(tipologia.equals("PERIODICO"))
            {
                do {
                    valMin = chiediNum("Inserisci valore minimo di norma ");
                    valMax = chiediNum("Inserisci valore massimo di norma ");
                    if (valMin > valMax) {
                        help.scrivi("Il valore minimo è maggiore del valore massimo, reinseriscili");
                    } else {
                        help.scrivi("Inseriti correttamente");
                    }
                } while (valMin > valMax);
            }
            String scelta;
            String s;

            List<String> preparazioni=new Vector<>();
            flag=true;
            do
            {
                s=help.getString("Inserire indicazioni o preparazioni varie");
                preparazioni.add(s);
                do {
                    scelta=help.getString("Vuoi continuare a inserire indicazioni? (S/N)");
                    if(!scelta.toUpperCase().equals("S")&&!scelta.toUpperCase().equals("N"))
                    {
                        help.scrivi("Valore non inserito correttamente");
                    }
                    else
                        flag=scelta.toUpperCase().equals("S");
                }while(!scelta.toUpperCase().equals("S")&&!scelta.toUpperCase().equals("N"));
            }while(flag);
            Esame nuevo=new Esame(nome, tipologia, valMin, valMax, preparazioni);
            List<Esame> temp= esami.stream().collect(Collectors.toList());
            temp.add(nuevo);
            esami=temp.stream().collect(Collectors.toList());
        }

        return "MenùEsami";
    }
    @SuppressWarnings("SameReturnValue")
    private String modificaUtente()// modifica dati utente, l'accesso è dato scontato.
    {
        String nome=utente.getNome();
        String cognome=utente.getCognome();
        String indirizzo=utente.getIndirizzo();
        String email=utente.getMail();
        String luogo=utente.getLuogo();
        Genere genere=utente.getGenere();
        CodFisc cf=utente.getCodiceFiscale();
        Data nascita=utente.getDataNascita();
        Gruppo gruppo= utente.getGruppo();
        String password=utente.getPassword();
        String codiceSanitario=utente.getCodiceSanitario();

        String visualizza;
        String scelta="avanti";

        while(!scelta.equals("Esci"))
        {
            scelta=menùModUtente();
            switch(scelta)
            {
                case "visualizzaDati":
                    visualizza="\nNome: "+nome+"\n"+
                            "Cognome: "+cognome+"\n"+
                            "Indirizzo: "+indirizzo+"\n";
                    if(!email.isEmpty())
                        visualizza+="Email: "+email+"\n";
                    visualizza+="Genere: "+genere.toString()+"\n"+
                            "Codice Fiscale: "+cf.toString()+"\n"+
                            "nato il: "+nascita.toString()+"\n"+
                            "a: "+luogo+"\n"+
                            gruppo.toString()+"\n";
                    help.scrivi(visualizza);
                    break;
                case "nome":
                    nome=chiediNome(false);
                    break;
                case "cognome":
                    cognome=chiediNome(true);
                    break;
                case "indirizzo":
                    indirizzo=chiediIndirizzo();
                    break;
                case "email":
                    email=chiediEmail();
                    break;
                case "genere":
                    genere=chiediGenere();
                    break;
                case "codiceFiscale":
                    help.scrivi("necessita modifica anche data di nascita");
                    nascita=chiediData();
                    cf=chiediFiscale(nascita);
                    break;
                case "dataNascita":
                    help.scrivi("necessita modifica anche codice fiscale");
                    nascita=chiediData();
                    cf=chiediFiscale(nascita);
                    break;
                case "luogo":
                    luogo=chiediLuogo();
                    break;
                case "gruppo":
                    gruppo=chiediGruppo();
                    break;
                case "password":
                    password=chiediPassword();
                default:
                    break;
            }
        }

       String salva;
        do {
            salva =  help.getString("Vuoi salvare le modifiche? (S/N)");
        }while(!salva.toUpperCase().equals("S")&&!salva.toUpperCase().equals("N"));
        if(salva.toUpperCase().equals("S"))
        {
            List<EsameUtente> tiemp=utente.esame.stream().collect(Collectors.toList());
            utente=new Utente(nome, cognome, indirizzo, email, nascita, luogo, genere, cf, gruppo, password, codiceSanitario, tiemp);
            salvataggio();
        }
        return "MenùUtente";
    }
    @SuppressWarnings("SameReturnValue")
    private void modificaEsame()
    {
        int id=scegliEsame(utente.esame);
        if(id!=-1)
        {
            EsameUtente temp1 = utente.esame.get(id);
            help.scrivi("Esame da modificare: " + temp1.toString());

            //get dei dati
            String nome = temp1.getName();
            TipoEsame tipologia = temp1.getTipologia();
            Data inizio = temp1.getInizio();
            Data fine = temp1.getFine();
            Ora orario = temp1.getOrario();
            double esitoPeriodico;
            String esitoDiagnostico;
            if (tipologia.toString().toUpperCase().equals("Periodico".toUpperCase())) {
                esitoPeriodico = temp1.getEsitoPeriodico();
                esitoDiagnostico = "";
            } else {
                esitoDiagnostico = temp1.getEsitoDiagnostico();
                esitoPeriodico = 0;
            }
            String visualizza;
            String scelta = "avanti";

            while (!scelta.equals("Esci")) {
                scelta = menuModEsamiUt();
                switch (scelta) {
                    case "visualizzaDati":
                        visualizza = "\n " + nome +
                                "\nTipologia Esame: " + tipologia;
                        if (temp1.isInData(inizio))
                            visualizza += "\nData Esame: " + inizio.toString();
                        else
                            visualizza += "\nData Esame: non pervenuta";

                        if (temp1.isInOra(orario))
                            visualizza += "\nOrario Esame: " + orario.toString();
                        else
                            visualizza += "\nOrario Esame: non pervenuto";

                        if (temp1.isInData(fine))
                            visualizza += "\nData Chiusura Pratica: " + fine.toString();
                        else
                            visualizza += "\nData Chiusura Pratica: non pervenuta";

                        if (tipologia.toString().equals("Periodico"))
                            if (temp1.isInEsito(esitoPeriodico))
                                visualizza += "\nEsito: " + esitoPeriodico;
                            else
                                visualizza += "\nEsito: non pervenuto";
                        else if (temp1.isInEsito(esitoDiagnostico))
                            visualizza += "\nEsito: " + esitoDiagnostico;
                        else
                            visualizza += "\nEsito: non pervenuto\n";

                        help.scrivi(visualizza);
                        break;
                    case "inizio":
                        inizio = chiediData();
                        temp1.setInizio(inizio);
                        break;
                    case "fine":
                        fine = chiediData();
                        temp1.setFine(fine);
                        break;
                    case "orario":
                        help.scrivi("Questa funzione ha problemi, tipo i gestionali");
                        //orario = chiediOrario();
                        break;
                    case "esito":
                        help.scrivi(tipologia.toString());
                        if (tipologia.toString().equals("Periodico")) {
                            boolean overValue;
                            do {
                                esitoPeriodico = help.inputDouble("Inserire esito numerico esame: ");
                                help.scrivi("Max"+ temp1.getErrorMax()+"   Min"+temp1.getErrorMin());
                                overValue=esitoPeriodico>=temp1.getErrorMax()||esitoPeriodico<=temp1.getErrorMin();
                                if(overValue)
                                {
                                    help.scrivi("Pare tu abbia fatto un errore d'inserimento, il valore è troppo fuori norma per essere accettato, reinseriscilo");
                                }
                            }while(overValue);
                            temp1.setEsitoPeriodico(esitoPeriodico);
                        } else {
                            esitoDiagnostico = help.getString("Inserire esito esame: ");
                            temp1.setEsitoDiagnostico(esitoDiagnostico);
                        }
                        break;
                }
            }
            String salva = "";
            while (!salva.toUpperCase().equals("S") && !salva.toUpperCase().equals("N")) {

                salva = help.getString("Desideri salvare le modifiche apportate? (S/N)");
            }
            if (salva.toUpperCase().equals("S")) {
                utente.esame.set(id,temp1);
                salvataggio();
            }
            else
            {
                help.scrivi("Non verrà salvato");
            }
        }
    }




    //endregion
    private Principale()
    {
        help=new Helper();
        utente=new Utente();
        caricaEsami();
        String prossimo=menùIniziale();
        while(!prossimo.equals("Fine"))
        {
            switch(prossimo)
            {

                case "MenùIniziale":
                    prossimo=menùIniziale();
                    break;
                case "MenùUtente":
                    prossimo=menùUtente();
                    break;
                case "CreazioneUtente":
                    prossimo=creazioneUtente();
                    break;
                case "ModificaUtente":
                    prossimo=modificaUtente();
                    break;
                case "MenùEsami":
                    prossimo=menùEsami();
                    break;
                case "Salvataggio":
                    prossimo=salvataggio();
                    break;
                case "SalvataggioEsami":
                    prossimo=salvataggioEsami();
                    break;
                case "SalvataggioEUscita":
                    prossimo=salvataggioEUscita();
                    break;
                case "CreazioneEsame":
                    prossimo=creazioneEsame();
                    break;
                default:
                    help.scrivi("Il programma è terminato perché c'è un problema col passaggio degli stati");
                    prossimo="Fine";
                    break;
            }
        }
    }

    public static void main(String[] args)
    {
        new Principale();
    }
}
