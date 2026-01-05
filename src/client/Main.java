package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocketSession session = new SocketSession();
        String scelta;

        do{
            System.out.println("\n--- MENU GINO S.R.L. ---");
            System.out.println("1. Login");
            System.out.println("2. Registrazione");
            System.out.println("3. Recupero Password");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    session.login(scanner);
                    break;
                case "2":
                    session.registrazione(scanner);
                    break;
                case "3":
                    session.recuperoPassword(scanner);
                    break;
                case "0":
                    System.out.println("Arrivederci!");
                default:
                    System.out.println("Scelta non valida.");
            }
        }while(scelta != "0");
        scanner.close();
    }
}
