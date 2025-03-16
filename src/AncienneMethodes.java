public class AncienneMethodes {

    public static boolean verifieDeplacement(char[][] plateau, int l1, int c1, int l2, int c2) {

        // Vérifie si les coordonnées sont valides
        if (l1 < 0 || l1 >= 8 || c1 < 0 || c1 >= 8 || l2 < 0 || l2 >= 8 || c2 < 0 || c2 >= 8) {
            return false;
        }

        // Vérifie si la case de départ contient un pion
        if (plateau[l1][c1] == ' ') {
            return false;
        }

        // Vérifie si la case d'arrivée est vide
        if (plateau[l2][c2] != '□') {
            return false;
        }

        // Vérifie si le déplacement est une capture
        if (Math.abs(l2 - l1) == 2 && Math.abs(c2 - c1) == 2) {
            int ligneMilieu = (l1 + l2) / 2;
            int colonneMilieu = (c1 + c2) / 2;

            // Vérifie si la case intermédiaire contient un pion adverse
            if ((plateau[ligneMilieu][colonneMilieu] == '⛀' && plateau[l1][c1] == '⛂') ||
                    (plateau[ligneMilieu][colonneMilieu] == '⛂' && plateau[l1][c1] == '⛀')) {

                // Effectue la capture
                plateau[ligneMilieu][colonneMilieu] = '□'; // Retire le pion capturé
                plateau[l2][c2] = plateau[l1][c1]; // Déplace le pion
                plateau[l1][c1] = '□'; // Vide la case de départ
                return true;
            } else {
                return false; // Pas de pion adverse à capturer
            }
        }

        // Vérifie si le déplacement est simple (diagonale d'une case)
        if (Math.abs(l2 - l1) == 1 && Math.abs(c2 - c1) == 1) {
            plateau[l2][c2] = plateau[l1][c1]; // Déplace le pion
            plateau[l1][c1] = '□'; // Vide la case de départ
            return true;
        }

        // Sinon, le déplacement est invalide
        return false;
    }
}
