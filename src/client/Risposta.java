package client;

import java.io.Serializable;

public class Risposta implements Serializable {

    private int statusCode;
    private Object payload;

    public Risposta(int statusCode, Object payload) {
        this.statusCode = statusCode;
        this.payload = payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getPayload() {
        return payload;
    }
}
