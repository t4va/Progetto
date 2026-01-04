package server;

public class Fornitore {
    private String piva;
    private String nome;
    private String tel;
    private String email;
    private int idProdotto;

    public Fornitore(String piva, String nome,
                     String tel, String email,
                     int idProdotto) {

        this.piva = piva;
        this.nome = nome;
        this.tel = tel;
        this.email = email;
        this.idProdotto = idProdotto;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    @Override
    public String toString() {
        return piva + "," + nome + "," + tel + "," +
                email + "," + idProdotto;
    }
}
