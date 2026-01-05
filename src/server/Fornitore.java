package server;

import classi_S_C.Prodotto;

public class Fornitore extends Thread {
    private String piva;
    private String nome;
    private String tel;
    private String email;
    private int idProdotto;
    private Prodotto prodottoRef;

    public Fornitore(String piva, String nome,
                     String tel, String email,
                     int idProdotto) {

        this.piva = piva;
        this.nome = nome;
        this.tel = tel;
        this.email = email;
        this.idProdotto = idProdotto;
    }

    public void setProdottoRef(Prodotto p) {
        this.prodottoRef = p;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5000 + (long) (Math.random() * 5000));
                if (prodottoRef != null && !prodottoRef.isDisponibile()) {
                    System.out.println("Fornitore " + nome + " sta rifornendo...");
                    prodottoRef.fornisci();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Fornitore " + nome + " interrotto.");
        }
    }

    @Override
    public String toString() {
        return piva + "," + nome + "," + tel + "," +
                email + "," + idProdotto;
    }
}
