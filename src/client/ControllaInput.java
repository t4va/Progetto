package client;

public class ControllaInput {

    public static boolean emailValida(String email) {
        if (email == null) return false;

        int atIndex = email.indexOf('@');
        if (atIndex <= 0) return false;
        if (email.indexOf('@', atIndex + 1) != -1) return false;

        int dotIndex = email.indexOf('.', atIndex);
        if (dotIndex == -1 || dotIndex == atIndex + 1) return false;

        if (dotIndex >= email.length() - 2) return false;

        return true;
    }

    public static boolean passwordValida(String password) {
        if (password == null || password.length() < 6) return false;

        boolean haCifra = false;
        boolean haSpeciale = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (c >= '0' && c <= '9') haCifra = true;
            else if (!Character.isLetterOrDigit(c)) haSpeciale = true;
        }

        return haCifra && haSpeciale;
    }

    public static boolean telefonoValido(String tel) {
        if (tel == null || tel.length() < 8) return false;

        for (int i = 0; i < tel.length(); i++) {
            char c = tel.charAt(i);
            if (c < '0' || c > '9') return false;
        }

        return true;
    }
}
