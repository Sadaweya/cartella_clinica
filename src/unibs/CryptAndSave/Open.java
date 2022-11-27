package unibs.CryptAndSave;

import unibs.Model.CodFisc;
import unibs.Model.Utente;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.FileOutputStream;


/**
 * Created by Swag on 10/12/2016.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Open {

    //apre il file, decripta, controlla che la password corrisponda, manda come risposta un boolean. Se corrisponde la lista di righe contiene il file completo esclusa la password.

    public List<String> file;
    private String password;

    private static final String filename="utente.txt";
    private static final String exname="esami.txt";

    public boolean denied;
    private String fisc;

    public void decrypt()
    {
        List<BigInteger> cryptBI=
                file.stream()
                        //.forEach(k-> System.out.println(k.toString()));
                        .map(BigInteger::new)
                        .collect(Collectors.toList());
        file= cryptBI.stream()
                .map(RSA::decryptMessage)
                .collect(Collectors.toList());
        //file.stream().forEach(System.out::println);
    }

    public void Save(Utente utente)
    {
        List<String> toSave= utente.getList().stream()
                .collect(Collectors.toList());
        RSA rsa=new RSA(utente.getPassword(), utente.getCodiceFiscale().toString());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {
            toSave.stream()
                    .map(RSA::encryptString)
                    .map(b-> b.toString()+"\n")
                    .forEach(k-> {
                        try {
                            writer.write(k);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }catch (IOException e)
        {
            //noinspection ResultOfMethodCallIgnored
            e.toString();
        }
    }
    public Open() //default constructor, non dovrebbe essere mai usato
    {
        //controllo se i file esistono, se esistono bene, se non esistono li crea. In questo modo non c'è più il percorso assoluto, ma va bene ovunque
        try{
            new FileOutputStream(filename, true).close();
            new FileOutputStream(exname, true).close();
        }catch(Exception e){

            e.printStackTrace();

        }
    }
    public boolean ApriEsami()
    {
        boolean flag=true;
        try {
            Stream<String> stream = Files.lines(Paths.get(exname));
            file = stream.collect(Collectors.toList());
            if(file.isEmpty())
            {
                File filo=new File(exname);
                //noinspection ResultOfMethodCallIgnored
                filo.createNewFile();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }
    public void SalvaEsami(List<String> exams)
    {
        List<String> toSave= exams.stream()
                .collect(Collectors.toList());
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(exname))) {
            toSave.forEach(k -> {
                try {
                    writer.write(k + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e)
        {
            //noinspection ResultOfMethodCallIgnored
            e.toString();
        }
    }
    public Open(String pass, CodFisc fiscale)
    {
        this();
        this.password=pass;
        this.fisc=fiscale.toString();
    }

    public boolean Apri()
    {
        boolean opened=false;
        String chiave=password;
        String codiceFiscale=fisc;
        RSA rsa = new RSA(chiave, codiceFiscale);
        try  {
            Stream<String> stream = Files.lines(Paths.get(filename));
            file=stream.collect(Collectors.toList());
            if(!file.isEmpty()) {
                String f = RSA.decryptMessage(new BigInteger(file.get(0)));
                if (f.equals(password)) {
                    denied = false;
                    opened=true;
                }
                else {
                    denied = true;
                }
            }
            else
            {
                BigInteger b= RSA.encryptString(chiave);
                File file = new File(filename);
                String content = b.toString();

                try (FileOutputStream fop = new FileOutputStream(file)) {

                    // if file doesn't exists, then create it
                    if (!file.exists()) {
                        //noinspection ResultOfMethodCallIgnored
                        file.createNewFile();
                    }

                    // get the content in bytes
                    byte[] contentInBytes = content.getBytes();
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                denied=false;
                opened=false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            denied=true;
        }

        return opened;
    }
}
