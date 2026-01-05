package classi_S_C;

import java.io.Serializable;

public class Risposta implements Serializable {

    private int statusCode;
    private Object credenziali;

    public Risposta(int statusCode, Object payload) {
        this.statusCode = statusCode;
        this.credenziali = payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getCredenziali() {
        return credenziali;
    }
}
