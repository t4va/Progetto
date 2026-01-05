package server;

import java.io.*;
import java.util.ArrayList;

import classi_S_C.Cliente;
import classi_S_C.Prodotto;

public class GestoreCSV {

    private static GestoreCSV instance;
    private ArrayList<Prodotto> prodotti;
    private ArrayList<Fornitore> fornitori;
    private ArrayList<Cliente> clienti;
    private final String PATH = "src/server/filecsv/";

    private GestoreCSV() {
        prodotti = new ArrayList<>();
        fornitori = new ArrayList<>();
        clienti = new ArrayList<>();
        loadProdotti();
        caricaFornitori();
        loadClienti();
    }

    public static synchronized GestoreCSV getInstance() {
        if (instance == null) {
            instance = new GestoreCSV();
        }
        return instance;
    }

    public ArrayList<Prodotto> getProdotti() {
        return prodotti;
    }

    public ArrayList<Fornitore> getFornitori() {
        return fornitori;
    }

    public ArrayList<Cliente> getClienti() {
        return clienti;
    }

    public void addCliente(Cliente c) {
        clienti.add(c);
        saveClienti();
    }

    public Cliente findClienteByEmail(String email) {
        for (Cliente c : clienti) {
            if (c.getEmail().equalsIgnoreCase(email))
                return c;
        }
        return null;
    }

    private void loadProdotti() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH + "prodotti.csv"))) {
            String riga = br.readLine();
            while ((riga = br.readLine()) != null) {
                if (riga.trim().isEmpty())
                    continue;
                String[] c = riga.split(",");
                // id,disponibile,data_acquisto,prezzo,tipo,titolo,produttore,genere,anno,piattaforma,azienda,marca,modello
                int id = Integer.parseInt(c[0]);
                boolean disp = Boolean.parseBoolean(c[1]);
                String data = c[2];
                double prezzo = Double.parseDouble(c[3]);
                String tipo = c[4];
                String titolo = c[5];

                Prodotto p = null;

                if (tipo.equalsIgnoreCase("Film")) {
                    p = new Film(id, disp, data, prezzo, titolo, c[6], c[7], Integer.parseInt(c[8]));
                } else if (tipo.equalsIgnoreCase("Videogame")) {
                    p = new Videogame(id, disp, data, prezzo, titolo, c[9], c[10]);
                } else if (tipo.equalsIgnoreCase("Tv")) {
                    p = new Tv(id, disp, data, prezzo, titolo, c[11], c[12]);
                }

                if (p != null)
                    prodotti.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void caricaFornitori() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH + "fornitori.csv"))) {
            String riga = br.readLine();
            while ((riga = br.readLine()) != null) {
                if (riga.trim().isEmpty())
                    continue;
                String[] c = riga.split(",");
                String piva = c[0];
                String nome = c[1];
                String tel = c[2];
                String email = c[3];
                int idProd = Integer.parseInt(c[4]);

                Fornitore f = new Fornitore(piva, nome, tel, email, idProd);

                for (Prodotto p : prodotti) {
                    if (p.getId() == idProd) {
                        f.setProdottoRef(p);
                        break;
                    }
                }
                fornitori.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClienti() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH + "clienti.csv"))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                if (riga.trim().isEmpty())
                    continue;
                String[] c = riga.split(";");
                if (c.length < 10)
                    continue;

                // cf, nome, cognome, tel, email, password, via, civico, cap, comune
                Cliente cl = new Cliente(c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7], c[8], c[9]);
                clienti.add(cl);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File clienti.csv non trovato. Verrà creato.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveClienti() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH + "clienti.csv"))) {
            for (Cliente c : clienti) {
                bw.write(c.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cliente autenticazione(String email, String password) {
        for (Cliente c : clienti) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                return c;
            }
        }
        return null;
    }

    public boolean modificaPassword(String cf, String newPassword) {
        for (Cliente c : clienti) {
            if (c.getCf().equalsIgnoreCase(cf)) {
                c.setPassword(newPassword);
                saveClienti();
                return true;
            }
        }
        return false;
    }

    public Prodotto findProdottoByTitolo(String titolo) {
        for (Prodotto p : prodotti) {
            if (p.getTitolo().equalsIgnoreCase(titolo))
                return p;
        }
        return null;
    }
}
