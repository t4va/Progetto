package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import classi_S_C.Cliente;
import classi_S_C.Prodotto;
import classi_S_C.Richiesta;
import classi_S_C.Risposta;
import classi_S_C.ServerConfig;

public class SocketSession {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Richiesta richiesta;
    private Risposta risposta;
    private static ArrayList<String[]> recoveryTokens = new ArrayList<>(); // [CF, TOKEN]

    public SocketSession() {
        init();
    }

    private void init() {
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.getInstance().getPORT())) {

            System.out.println("Server in ascolto sulla porta " + ServerConfig.getInstance().getPORT());
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connesso: " + clientSocket.getInetAddress());

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            String tipo;

            do {
                richiesta = (Richiesta) in.readObject();
                tipo = richiesta.getTipo();
                switch (tipo) {

                    case "REGISTER":
                        Cliente c = (Cliente) richiesta.getCredenziali();
                        GestoreCSV.getInstance().addCliente(c);
                        risposta = new Risposta(200, "Registrazione completata");
                        break;

                    case "LOGIN":
                        String[] creds = (String[]) richiesta.getCredenziali();
                        Cliente loggato = GestoreCSV.getInstance().autenticazione(creds[0], creds[1]);
                        if (loggato != null)
                            risposta = new Risposta(200, loggato);
                        else
                            risposta = new Risposta(401, "Credenziali non valide");
                        break;

                    case "RECOVER_REQ":
                        String email = (String) richiesta.getCredenziali();
                        Cliente rc = GestoreCSV.getInstance().findClienteByEmail(email);
                        if (rc != null) {
                            String token = "TOKEN-" + (int) (Math.random() * 10000);
                            recoveryTokens.add(new String[] { rc.getCf(), token });
                            String[] credenziali = new String[] { token, rc.getCf() };
                            risposta = new Risposta(200, credenziali);
                        } else {
                            risposta = new Risposta(404, "Email non trovata");
                        }
                        break;

                    case "RECOVER_CONFIRM":
                        String[] dati = (String[]) richiesta.getCredenziali(); // [CF, TOKEN, NEWPASS]
                        String cf = dati[0];
                        String token = dati[1];
                        String newPass = dati[2];

                        boolean valido = false;
                        for (int i = 0; i < recoveryTokens.size(); i++) {
                            if (recoveryTokens.get(i)[0].equals(cf) && recoveryTokens.get(i)[1].equals(token)) {
                                valido = true;
                                recoveryTokens.remove(i);
                                break;
                            }
                        }

                        if (valido) {
                            GestoreCSV.getInstance().modificaPassword(cf, newPass);
                            risposta = new Risposta(200, "Password aggiornata");
                        } else {
                            risposta = new Risposta(400, "Token non valido o scaduto");
                        }
                        break;

                    case "CERCA":
                        Prodotto p = cercaProdotto((String) richiesta.getCredenziali());
                        if (p != null)
                            risposta = new Risposta(200, p);
                        else
                            risposta = new Risposta(404, "Prodotto non trovato");
                        break;

                    case "ORDINA":
                        boolean ok = ordinaProdotto((Integer) richiesta.getCredenziali());
                        if (ok)
                            risposta = new Risposta(200, "Ordine completato");
                        else
                            risposta = new Risposta(400, "Prodotto non disponibile");
                        break;

                    case "EXIT":
                        risposta = new Risposta(200, "Arrivederci");
                        out.writeObject(risposta);
                        clientSocket.close();
                        return;
                }

                out.writeObject(risposta);

            } while (!tipo.equals("EXIT"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Prodotto cercaProdotto(String titolo) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("src/server/filecsv/prodotti.csv"))) {
            String riga;
            br.readLine();
            while ((riga = br.readLine()) != null) {
                String[] c = riga.split(",");
                if (c[5].equalsIgnoreCase(titolo)) {
                    int id = Integer.parseInt(c[0]);
                    boolean disp = Boolean.parseBoolean(c[1]);
                    String data = c[2];
                    double prezzo = Double.parseDouble(c[3]);
                    String tipo = c[4];

                    switch (tipo) {
                        case "Film":
                            return new Film(id, disp, data, prezzo, c[5], c[6], c[7], Integer.parseInt(c[8]));
                        case "Videogame":
                            return new Videogame(id, disp, data, prezzo, c[5], c[9], c[10]);
                        case "Tv":
                            return new Tv(id, disp, data, prezzo, c[5], c[11], c[12]);
                    }
                }
            }
        }
        return null;
    }

    private boolean ordinaProdotto(int idProdotto) throws Exception {
        ArrayList<String> righe = new ArrayList<>();
        boolean trovato = false;

        try (BufferedReader br = new BufferedReader(new FileReader("src/server/filecsv/prodotti.csv"))) {
            String riga = br.readLine();
            righe.add(riga);

            while ((riga = br.readLine()) != null) {
                String[] c = riga.split(",");
                if (Integer.parseInt(c[0]) == idProdotto) {
                    trovato = true;
                    if (!Boolean.parseBoolean(c[1])) {
                        c[1] = "true";
                        Thread.sleep(2000);
                    } else {
                        righe.add(riga);
                        continue;
                    }
                }
                righe.add(String.join(",", c));
            }
        }

        if (!trovato)
            return false;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/server/filecsv/prodotti.csv"))) {
            for (String s : righe) {
                bw.write(s);
                bw.newLine();
            }
        }

        return true;
    }
}
