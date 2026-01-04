package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketSession {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Richiesta richiesta;
    private Risposta risposta;

    public SocketSession() {
        init();
    }

    private void init() {

        try (ServerSocket serverSocket =
                     new ServerSocket(ServerConfig.getInstance().getPORT())) {

            Socket clientSocket = serverSocket.accept();

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            do {

                richiesta = (Richiesta) in.readObject();

                if (richiesta.getTipo().equals("CERCA")) {

                    Prodotto p = cercaProdotto(richiesta.getTitolo());

                    if (p == null)
                        risposta = new Risposta(404, "Prodotto non trovato");
                    else
                        risposta = new Risposta(200, p);

                }

                if (richiesta.getTipo().equals("ORDINA")) {

                    boolean ok = ordinaProdotto(richiesta.getIdProdotto());

                    if (ok)
                        risposta = new Risposta(200, "Ordine completato");
                    else
                        risposta = new Risposta(400, "Prodotto non disponibile");
                }

                out.writeObject(risposta);

            } while (risposta.getStatusCode() != 200);

            clientSocket.close();

        } catch (Exception e) {
            try {
                risposta = new Risposta(500, e.getMessage());
                out.writeObject(risposta);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Prodotto cercaProdotto(String titolo) throws Exception {

        BufferedReader br =
                new BufferedReader(new FileReader("src/server/filecsv/prodotti.csv"));

        String riga = br.readLine();

        while ((riga = br.readLine()) != null) {

            String[] c = riga.split(",");

            if (c[5].equalsIgnoreCase(titolo)) {

                int id = Integer.parseInt(c[0]);
                boolean disp = Boolean.parseBoolean(c[1]);
                String data = c[2];
                double prezzo = Double.parseDouble(c[3]);
                String tipo = c[4];

                br.close();

                if (tipo.equals("Film")) {
                    return new Film(id, disp, data, prezzo,
                            c[5], c[6], c[7],
                            Integer.parseInt(c[8]));
                }

                if (tipo.equals("Videogame")) {
                    return new Videogame(id, disp, data, prezzo,
                            c[5], c[9], c[10]);
                }

                if (tipo.equals("Tv")) {
                    return new Tv(id, disp, data, prezzo,
                            c[5], c[11], c[12]);
                }
            }
        }

        br.close();
        return null;
    }

    private boolean ordinaProdotto(int idProdotto) throws Exception {

        ArrayList<String> righe = new ArrayList<>();

        BufferedReader br =
                new BufferedReader(new FileReader("src/server/filecsv/prodotti.csv"));

        String riga = br.readLine();
        righe.add(riga);

        boolean trovato = false;

        while ((riga = br.readLine()) != null) {

            String[] c = riga.split(",");

            if (Integer.parseInt(c[0]) == idProdotto) {

                trovato = true;

                if (Boolean.parseBoolean(c[1])) {
                    br.close();
                    return false;
                }

                Thread.sleep(2000);
                c[1] = "True";
                righe.add(String.join(",", c));

            } else {
                righe.add(riga);
            }
        }

        br.close();

        if (!trovato)
            return false;

        BufferedWriter bw =
                new BufferedWriter(new FileWriter("src/server/filecsv/prodotti.csv"));

        for (String s : righe) {
            bw.write(s);
            bw.newLine();
        }

        bw.close();
        return true;
    }
}
