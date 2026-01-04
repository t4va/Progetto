package client;

public class ServerConfig {

    private static ServerConfig Instance = null;
    protected final int PORT = 9999;
    protected final String HOST = "localhost";

    public static ServerConfig getInstance() {
        if (Instance == null)
            Instance = new ServerConfig();
        return Instance;
    }

    public int getPORT() {
        return PORT;
    }

    public String getHOST() {
        return HOST;
    }

}
