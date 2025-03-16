public class Dames {
    public static boolean verifieDeplacementDames(char[][] plateau, int l1, int c1, int l2, int c2, char pionActuel) {
        // Vérifie si les coordonnées sont valides
        if (l1 < 0 || l1 >= 8 || c1 < 0 || c1 >= 8 || l2 < 0 || l2 >= 8 || c2 < 0 || c2 >= 8) {
            return false; // Coordonnées hors du plateau
        }

        // Vérifie si la case de départ contient une dame (pion promu)
        if (plateau[l1][c1] != '♔' && plateau[l1][c1] != '♚') {
            return false; // Pas de dame à déplacer
        }

        // Vérifie si la case d'arrivée est vide
        if (plateau[l2][c2] != '□') {
            return false; // Case d'arrivée non vide
        }

        // Vérifie si le déplacement est en diagonale
        if (Math.abs(l2 - l1) != Math.abs(c2 - c1)) {
            return false; // Déplacement non diagonal
        }

        // Vérifie qu'il n'y a pas d'obstacles sur le chemin
        int directionLigne = (l2 > l1) ? 1 : -1;
        int directionColonne = (c2 > c1) ? 1 : -1;
        int ligne = l1 + directionLigne;
        int colonne = c1 + directionColonne;

        while (ligne != l2 && colonne != c2) {
            if (plateau[ligne][colonne] != '□') {
                return false; // Obstacle sur le chemin
            }
            ligne += directionLigne;
            colonne += directionColonne;
        }

        // Déplace la dame
        plateau[l2][c2] = plateau[l1][c1];
        plateau[l1][c1] = '□';
        return true;
    }

    public static boolean verifieCaptureDames(char[][] plateau, int l1, int c1, int l2, int c2) {
        // Vérifie si le déplacement est une capture (diagonale avec une distance de plus de deux cases)
        if (Math.abs(l2 - l1) != Math.abs(c2 - c1)) {
            return false; // Déplacement non diagonal
        }

        // Vérifie la direction du déplacement
        int directionLigne = (l2 > l1) ? 1 : -1;
        int directionColonne = (c2 > c1) ? 1 : -1;

        // Vérifie qu'il n'y a qu'un seul pion adverse sur le chemin
        int ligne = l1 + directionLigne;
        int colonne = c1 + directionColonne;
        boolean pionAdverseTrouve = false;

        while (ligne != l2 && colonne != c2) {
            if (plateau[ligne][colonne] != '□') {
                if (!pionAdverseTrouve &&
                        ((plateau[ligne][colonne] == '⛀' && plateau[l1][c1] == '♚') ||
                                (plateau[ligne][colonne] == '⛂' && plateau[l1][c1] == '♔'))) {
                    pionAdverseTrouve = true; // Un pion adverse est trouvé
                } else {
                    return false; // Plus d'un pion ou obstacle sur le chemin
                }
            }
            ligne += directionLigne;
            colonne += directionColonne;
        }

        // Vérifie qu'un pion adverse a été trouvé
        return pionAdverseTrouve;
    }

    public static boolean forcerCapture(char[][] plateau, char pionActuel) {
        // Parcourt tout le plateau pour détecter les captures possibles
        for (int l1 = 0; l1 < plateau.length; l1++) {
            for (int c1 = 0; c1 < plateau[l1].length; c1++) {
                if (plateau[l1][c1] == pionActuel || plateau[l1][c1] == '♔' || plateau[l1][c1] == '♚') {
                    for (int l2 = 0; l2 < plateau.length; l2++) {
                        for (int c2 = 0; c2 < plateau[l2].length; c2++) {
                            if (verifieCaptureDames(plateau, l1, c1, l2, c2)) {
                                return true; // Une capture est possible
                            }
                        }
                    }
                }
            }
        }
        return false; // Aucune capture possible
    }

    public static void CaptureMultipleDames(char[][] plateau, int l1, int c1) {
        boolean capturePossible = true;
        while (capturePossible) {
            capturePossible = false;
            for (int l2 = 0; l2 < plateau.length; l2++) {
                for (int c2 = 0; c2 < plateau[l2].length; c2++) {
                    if (verifieCaptureDames(plateau, l1, c1, l2, c2)) {
                        // Effectue la capture
                        int directionLigne = (l2 > l1) ? 1 : -1;
                        int directionColonne = (c2 > c1) ? 1 : -1;
                        int ligneMilieu = l1 + directionLigne;
                        int colonneMilieu = c1 + directionColonne;

                        // Supprime le pion capturé
                        plateau[ligneMilieu][colonneMilieu] = '□';

                        // Déplace la dame
                        plateau[l2][c2] = plateau[l1][c1];
                        plateau[l1][c1] = '□';

                        // Met à jour la position de la dame
                        l1 = l2;
                        c1 = c2;

                        capturePossible = true; // Vérifie si d'autres captures sont possibles
                        break;
                    }
                }
                if (capturePossible) break;
            }
        }
    }

}
