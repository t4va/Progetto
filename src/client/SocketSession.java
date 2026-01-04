import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread principale che gestisce la logica di comunicazione con il client. Accetta la richiesta del client
 * e verifica se l'utente esiste e la password è corretta. Compone la risposta secondo in base al protocollo
 * di comunicazione stabilito e fino a che non verifica l'esistenza dell'utente aspetta richieste verso il
 * client.
 *
 * @author Prof. Ing. Emanuele De Bernardi ®COPYRIGHT 2025
 */
public class SocketSession {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private RicercaUtente ricercaUtente;
    private Richiesta richiesta;
    private Risposta risposta;

    public SocketSession() {
        init();
    }

    private void init() {
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.getInstance().getPORT())) {
            // accetto connessione in ingresso dal client
            Socket clientSocket = serverSocket.accept();
            // inizializzo oggetti
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ricercaUtente = new RicercaUtente();

            do {

                // attendo la richiesta del client
                richiesta = (Richiesta) objectInputStream.readObject();

                // verifico esistenza utente
                Utente utente = ricercaUtente.utenteVerificato(richiesta.getEmail(), richiesta.getPassword());
                if (utente == null) {
                    risposta = new Risposta(400, "Utente non riconosciuto");
                } else {
                    risposta = new Risposta(200, "Benvenuto " + utente.getNome() + "!");
                }

                // rispondo al client
                objectOutputStream.writeObject(risposta);

            } while (risposta.getStatusCode() == 400);

            // chiudo la socket
            serverSocket.close();
            clientSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            risposta = new Risposta(500, e.getMessage());
            try {
                objectOutputStream.writeObject(risposta);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
