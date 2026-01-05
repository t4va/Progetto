package classi_S_C;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private String tipo; // REGISTER, LOGIN, RECOVER_REQ, RECOVER_CONFIRM, ORDINA, CERCA
    private Object credenziali;

    public Richiesta(String tipo, Object credenziali) {
        this.tipo = tipo;
        this.credenziali = credenziali;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getCredenziali() {
        return credenziali;
    }

    public void setCredenziali(Object credenziali) {
        this.credenziali = credenziali;
    }
}
