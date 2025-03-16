public class Plateau {

    public static char[][] plt() {
        int boardsize = 8;
        char[][] plateau = new char[boardsize][boardsize]; // Initialise le plateau au moment de la création de l'objet

        Methodes.initialiserPlateau(plateau); // Initialise le plateau avec des pions
        return plateau; // Retourne le plateau
    }
}