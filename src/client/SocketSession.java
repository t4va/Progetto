package client;

import server.Richiesta;
import server.Risposta;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketSession {

    public void cercaProdotto(String titolo) {

        try {
            Socket socket = new Socket(
                    ServerConfig.getInstance().getHOST(),
                    ServerConfig.getInstance().getPORT());

            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());

            Richiesta r = new Richiesta("CERCA", titolo);
            out.writeObject(r);

            Risposta risposta = (Risposta) in.readObject();

            System.out.println("Status: " + risposta.getStatusCode());
            System.out.println("Dati: " + risposta.getPayload());

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
