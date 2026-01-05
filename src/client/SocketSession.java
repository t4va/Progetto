package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import classi_S_C.Cliente;
import classi_S_C.Prodotto;
import classi_S_C.Richiesta;
import classi_S_C.Risposta;
import classi_S_C.ServerConfig;

public class SocketSession {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public SocketSession() {
        try {
            socket = new Socket(ServerConfig.getInstance().getHOST(),
                    ServerConfig.getInstance().getPORT());
            System.out.println("Connesso al server.");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(Scanner sc) {
        try {
            String email = leggiEmailValida(sc);
            System.out.print("Password: ");
            String password = sc.nextLine();
            String[] creds = new String[] { email, password };
            Richiesta req = new Richiesta("LOGIN", creds);
            inviaRichiesta(req);

            Risposta resp = (Risposta) in.readObject();
            if (resp.getStatusCode() == 200) {
                System.out.println("Login effettuato con successo!");
                Cliente c = (Cliente) resp.getCredenziali();
                menuOrdini(sc, c);
            } else {
                System.out.println("Errore login: " + resp.getCredenziali());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrazione(Scanner sc) {
        try {
            System.out.println("--- REGISTRAZIONE ---");
            System.out.print("CF: ");
            String cf = sc.nextLine();
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Cognome: ");
            String cognome = sc.nextLine();
            System.out.print("Tel: ");
            String tel = sc.nextLine();
            String email = leggiEmailValida(sc);
            System.out.print("Password: ");
            String password = sc.nextLine();
            System.out.print("Via: ");
            String via = sc.nextLine();
            System.out.print("Civico: ");
            String civico = sc.nextLine();
            System.out.print("CAP: ");
            String cap = sc.nextLine();
            System.out.print("Comune: ");
            String comune = sc.nextLine();

            Cliente c = new Cliente(cf, nome, cognome, tel, email, password, via, civico, cap, comune);
            Richiesta req = new Richiesta("REGISTER", c);
            Risposta resp = inviaRichiesta(req);

            System.out.println("Esito: " + resp.getCredenziali());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recuperoPassword(Scanner sc) {
        try {
            System.out.println("--- RECUPERO PASSWORD ---");
            String email = leggiEmailValida(sc);

            // invio richiesta recupero
            Richiesta req = new Richiesta("RECOVER_REQ", email);
            Risposta resp = inviaRichiesta(req);

            if (resp.getStatusCode() == 200) {
                String[] data = (String[]) resp.getCredenziali();
                String token = data[0];
                String cf = data[1];

                System.out.println("Token ricevuto: " + token);
                System.out.print("Inserisci token: ");
                String inputToken = sc.nextLine();
                System.out.print("Nuova password: ");
                String newPass = sc.nextLine();

                String[] conferma = new String[] { cf, inputToken, newPass };
                Richiesta reqConf = new Richiesta("RECOVER_CONFIRM", conferma);
                Risposta respConf = inviaRichiesta(reqConf);

                System.out.println("Esito: " + respConf.getCredenziali());
            } else {
                System.out.println("Errore: " + resp.getCredenziali());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void menuOrdini(Scanner sc, Cliente c) {
        try {
            while (true) {
                System.out.println("\n--- AREA CLIENTE: " + c.getNome() + " ---");
                System.out.println("1. Cerca prodotto");
                System.out.println("2. Ordina prodotto (ID)");
                System.out.println("3. Logout");
                System.out.print("Scelta: ");
                String scelta = sc.nextLine();

                if ("1".equals(scelta)) {
                    System.out.print("Titolo prodotto: ");
                    String titolo = sc.nextLine();
                    Richiesta req = new Richiesta("CERCA", titolo);
                    Risposta resp = inviaRichiesta(req);

                    if (resp.getStatusCode() == 200) {
                        Prodotto p = (Prodotto) resp.getCredenziali();
                        System.out.println("Trovato: " + p.getTitolo() + " (ID:" + p.getId() + ", Disp: "
                                + p.isDisponibile() + ")");
                    } else {
                        System.out.println("Prodotto non trovato.");
                    }
                } else if ("2".equals(scelta)) {
                    System.out.print("ID prodotto da ordinare: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Richiesta req = new Richiesta("ORDINA", id);
                    Risposta resp = inviaRichiesta(req);
                    System.out.println("Esito ordine: " + resp.getCredenziali());
                } else if ("3".equals(scelta)) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Risposta inviaRichiesta(Richiesta req) throws Exception {
        out.writeObject(req);
        return (Risposta) in.readObject();
    }

    private String leggiEmailValida(Scanner sc) {
        String email;
        do {
            System.out.print("Email: ");
            email = sc.nextLine();
        } while (!ControllaInput.emailValida(email));
        return email;
    }
}