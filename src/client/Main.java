public class Main {
    public static void main(String[] args) {
        // 1. Creation of Suppliers
        Fornitore sony = new Fornitore(1, "IT001", "Sony", "contact@sony.com");
        Fornitore nintendo = new Fornitore(2, "IT002", "Nintendo", "contact@nintendo.com");
        Fornitore samsung = new Fornitore(3, "IT003", "Samsung", "contact@samsung.com");

        // 2. Creation of Products
        // TV (Samsung)
        Tv tv1 = new Tv(101, "Smart TV 55 4K", 599.99, samsung.getIdFornitore(), "Samsung", "UE55AU7190");
        Tv tv2 = new Tv(102, "QLED 65", 899.99, samsung.getIdFornitore(), "Samsung", "QE65Q60A");

        // Videogames (Sony, Nintendo)
        Videogame ps5Game = new Videogame(201, "God of War Ragnarok", 79.99, sony.getIdFornitore(), "PS5", "Action",
                2022, "Sony Santa Monica");
        Videogame switchGame = new Videogame(202, "Zelda tears of the kingdom", 69.99, nintendo.getIdFornitore(),
                "Switch", "Adventure", 2023, "Nintendo");

        // Films (Generic/Sony)
        Film film1 = new Film(301, "Spider-Man: No Way Home", 14.99, sony.getIdFornitore(), "Jon Watts", "Action", 2021,
                "Sony Pictures");

        // 3. Assign Products to Suppliers (for them to supply)
        samsung.aggiungiProdotto(tv1);
        samsung.aggiungiProdotto(tv2);

        sony.aggiungiProdotto(ps5Game);
        sony.aggiungiProdotto(film1);

        nintendo.aggiungiProdotto(switchGame);

        // 4. Creation of Customers
        Cliente c1 = new Cliente("CF001", "Mario", "Rossi", "mario@email.com", "pass1");
        Cliente c2 = new Cliente("CF002", "Luigi", "Verdi", "luigi@email.com", "pass2");
        Cliente c3 = new Cliente("CF003", "Peach", "Toadstool", "peach@email.com", "pass3");

        // 5. Client orders
        c1.setProdottoDesiderato(tv1); // Mario wants a TV
        c2.setProdottoDesiderato(ps5Game); // Luigi wants GoW
        c3.setProdottoDesiderato(switchGame); // Peach wants Zelda

        // 6. Start Suppliers threads (they will supply products after a delay)
        sony.start();
        nintendo.start();
        samsung.start();

        // 7. Start Customer threads (they will try to buy)
        c1.start();
        c2.start();
        c3.start();

        try {
            // Wait for threads to finish (in a real app we might not join here or have a
            // better termination condition)
            c1.join();
            c2.join();
            c3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop suppliers (since they just run a loop over their products once in this
        // impl, they might be done already?
        // Actually Fornitore.run iterates once over list. So they finish naturally.)
        System.out.println("SIMULAZIONE TERMINATA.");
    }
}
