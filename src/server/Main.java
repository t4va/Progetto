package server;

public class Main {
    public static void main(String[] args) {

        System.out.println("Avvio Server...");

        GestoreCSV db = GestoreCSV.getInstance();
        System.out.println("File CSV caricati. " + db.getProdotti().size() + " prodotti, " + db.getFornitori().size()
                + " fornitori.");
        for (Fornitore f : db.getFornitori()) {
            f.start();
        }
        System.out.println("Thread fornitori avviati.");
        new SocketSession();
    }
}