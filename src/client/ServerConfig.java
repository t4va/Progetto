/**
 * Questa classe rappresenta un oggetto Singleton che tramite la sua istanza creata la prima volta utilizzarà il puntatore
 * già inizializzato
 *
 * @author Prof. Ing. Emanuele De Bernardi
 */
public class ServerConfig {

    private static ServerConfig Instance = null;
    protected final int PORT = 9999;

    public static ServerConfig getInstance() {
        if (Instance == null)
            Instance = new ServerConfig();
        return Instance;
    }

    public int getPORT() {
        return PORT;
    }

}
