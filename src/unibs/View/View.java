package unibs.View;
import unibs.Control.Helper;
/**
 * Created by Swag on 10/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class View {
    private static Helper h=new Helper();
    //region "TABELLE"
    private static final String TABELLAUNO=
            "Benvenuto nel menù di scelta della cartella clinica\n"+
            "1)Visualizzare i dati del paziente\n"+
            "2)Modificare i dati del paziente\n"+
            "3)Visualizzare gli esami prenotati\n"+
            "4)Prenotare un esame\n"+
            "5)Modifica esame esistente\n"+
            "6)Elimina esame esistente\n"+
            "7)Salvare e uscire\n"+
            "8)uscire senza salvare \n";
    private static final String TABELLAZERO=
            "Benvenuto nella cartella clinica\n"+
                    "1)Caricare il file utente\n"+
                    "2)Creare un nuovo utente\n"+
                    "3)Gestione esami\n"+
                    "4)Uscire\n";

    private static final String TABELLAESAMI=
            "Benvenuto nel menù di gestione esami\n"+
                    "1)Creare un nuovo esame\n"+
                    "2)Visualizzare gli esami presenti\n"+
                    "3)Uscire";

    private static final String TABELLAMODUTENTE=
            "Menù modifica dati Utente\n"+
                    "1)Visualizza dati utente\n"+
                    "2)Modifica nome\n"+
                    "3)Modifica cognome\n"+
                    "4)Modifica indirizzo\n"+
                    "5)Modifica email\n"+
                    "6)Modifica genere\n"+
                    "7)Modifica codice fiscale\n"+
                    "8)Modifica data di nascita\n"+
                    "9)Modifica luogo di nascita\n"+
                    "10)Modifica gruppo sanguigno\n"+
                    "11)Modifica password utente\n"+
                    "12)ESCI";

    private static final String TABELLAMODESAMIUT=
            "Menù modifica esame utente\n"+
                    "1)Visualizza dati esame\n"+
                    "2)Modifica data di inizio esame\n"+
                    "3)Modifica data di fine esame\n"+
                    "4)Modifica orario esame\n"+
                    "5)Modifica esito esame\n"+
                    "6)ESCI\n";

    //endregion

    private static int Menu0()
    {
        h.scrivi(TABELLAZERO);
        return h.minMaxInputInt(1,4);
    }
    private static int Menu1()
    {
        h.scrivi(TABELLAUNO);
        return h.minMaxInputInt(1,8);
    }

    private static int MenuEsami()
    {
        h.scrivi(TABELLAESAMI);
        return h.minMaxInputInt(1,3);
    }
    private static int menuModUtente()
    {
        h.scrivi(TABELLAMODUTENTE);
        return h.minMaxInputInt(1,12);
    }
    private static int menuModEsami()
    {
        h.scrivi(TABELLAMODESAMIUT);
        return h.minMaxInputInt(1,6);
    }

    public static int Menu(int scelta)
    {
        int val=0;
        switch(scelta)
        {
            case 0:
                val=Menu0();
                break;
            case 1:
                val=Menu1();
                break;
            case 3:
                val=MenuEsami();
                break;
            case 4:
                val=menuModUtente();
                break;
            case 5:
                val=menuModEsami();
            default:
                break;
        }
        return val;
    }
}
