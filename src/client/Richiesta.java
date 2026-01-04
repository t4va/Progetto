package client;

import java.io.Serializable;

public class Richiesta implements Serializable {

    private String tipo;
    private String titolo;
    private int idProdotto;

    public Richiesta(String tipo, String titolo) {
        this.tipo = tipo;
        this.titolo = titolo;
    }

    public Richiesta(String tipo, int idProdotto) {
        this.tipo = tipo;
        this.idProdotto = idProdotto;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getIdProdotto() {
        return idProdotto;
    }
}
