import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSession {

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
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


        }
    }

}
