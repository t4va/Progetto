package server;

public class Cliente {
    private String cf;
    private String nome;
    private String cognome;
    private String tel;
    private String email;
    private String password;
    private String via;
    private String civico;
    private String cap;
    private String comune;

    public Cliente(String cf, String nome, String cognome, String tel,
                   String email, String password,
                   String via, String civico, String cap, String comune) {

        this.cf = cf;
        this.nome = nome;
        this.cognome = cognome;
        this.tel = tel;
        this.email = email;
        this.password = password;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
    }

    public String getCf() { return cf; }

    public String toCSV() {
        return cf + ";" + nome + ";" + cognome + ";" + tel + ";" +
                email + ";" + password + ";" +
                via + ";" + civico + ";" + cap + ";" + comune;
    }
}
