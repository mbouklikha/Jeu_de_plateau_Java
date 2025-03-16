import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Methodes {

    // Initialise le plateau avec les pions et les cases
    public static void initialiserPlateau (char[][] plateau) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    plateau[i][j] = ' '; // Case blanche (non jouable)
                } else {
                    if (i < 3) {
                        plateau[i][j] = '⛀'; // Pions noirs
                    } else if (i > 4) {
                        plateau[i][j] = '⛂'; // Pions blancs
                    } else {
                        plateau[i][j] = '□'; // Case vide (jouable)
                    }
                }
            }
        }
    }

    // Affiche le plateau
    public static void afficherPlateau(char[][] plateau) {
        System.out.println("   0 1  2 3 4 5  6 7");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < 8; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Retourne le contenu d'une case
    public char getCase ( int ligne, int colonne, char[][] plateau){
        return plateau[ligne][colonne];
    }

    // Met à jour une case du plateau
    public void setCase ( int ligne, int colonne, char contenu, char[][] plateau){
        plateau[ligne][colonne] = contenu;
    }





    public static void AjouterPseudo(ArrayList<String> l){
        Scanner sc = new Scanner(System.in);
        String nom;
        do {
            System.out.print(" *** Entrez un pseudo entre 2 et 20 caractères : ");
            nom = sc.nextLine();
        } while(nom.length() < 2 || nom.length() > 20);
        if(l.contains(nom)){
            System.out.println(" *** Erreur pseudo déjà existant ! *** ");
        } else{
            l.add(nom);
        }
        System.out.println();

    }

    public static boolean verifieDeplacement(char[][] plateau, int l1, int c1, int l2, int c2, char pionActuel) {
        // Vérifie si les coordonnées sont valides
        if (l1 < 0 || l1 >= 8 || c1 < 0 || c1 >= 8 || l2 < 0 || l2 >= 8 || c2 < 0 || c2 >= 8) {
            return false; // Coordonnées hors du plateau
        }

        // Vérifie si la case de départ contient un pion
        if (plateau[l1][c1] != '⛀' && plateau[l1][c1] != '⛂') {
            return false; // Pas de pion à déplacer
        }

        // Vérifie si la case d'arrivée est vide
        if (plateau[l2][c2] != '□') {
            return false; // Case d'arrivée non vide
        }

        // Vérifie la direction pour éviter de reculer (⛀ avance en descendant, ⛂ en montant)
        if ((plateau[l1][c1] == '⛀' && l2 <= l1) || (plateau[l1][c1] == '⛂' && l2 >= l1)) {
            return false; // Mouvement en arrière interdit
        }

        // Vérifie si le déplacement est simple (diagonale d'une case)
        if (Math.abs(l2 - l1) == 1 && Math.abs(c2 - c1) == 1) {
            // Déplace le pion
            plateau[l2][c2] = plateau[l1][c1];
            plateau[l1][c1] = '□';
            return true;
        }
        else if (pionActuel == '⛂' && plateau[l1][c1] == '⛂' || pionActuel == '⛀' && plateau[l1][c1] == '⛀'){
            return false;
        }


        // Sinon, le déplacement est invalide
        return false;
    }

    public static boolean verifieCapture(char[][] plateau, int l1, int c1, int l2, int c2) {
        // Vérifie que le déplacement est une capture valide (diagonale de 2 cases)
        if (Math.abs(l2 - l1) != 2 || Math.abs(c2 - c1) != 2) {
            return false;
        }

        // Identifie la case intermédiaire
        int ligneMilieu = (l1 + l2) / 2;
        int colonneMilieu = (c1 + c2) / 2;
        char pionMilieu = plateau[ligneMilieu][colonneMilieu];

        // Vérifie que la case intermédiaire contient un pion adverse
        if (pionMilieu == '□' || (plateau[l1][c1] == '⛀' && pionMilieu != '⛂') ||
                (plateau[l1][c1] == '⛂' && pionMilieu != '⛀')) {
            return false;
        }

        // Vérifie que la case d'arrivée est vide
        if (plateau[l2][c2] != '□') {
            return false;
        }

        // Si tout est valide, retourne true
        return true;
    }


    public static boolean forcerCapture(char[][] plateau, char pionActuel) {
        // Parcourt tout le plateau pour détecter les captures possibles
        for (int l1 = 0; l1 < plateau.length; l1++) {
            for (int c1 = 0; c1 < plateau[l1].length; c1++) {
                if (plateau[l1][c1] == pionActuel) {
                    for (int l2 = 0; l2 < plateau.length; l2++) {
                        for (int c2 = 0; c2 < plateau[l2].length; c2++) {
                            // Vérifie si le déplacement est une capture et si le pion capturé est différent
                            if (verifieCapture(plateau, l1, c1, l2, c2)) {
                                int ligneMilieu = (l1 + l2) / 2;
                                int colonneMilieu = (c1 + c2) / 2;
                                char pionCapture = plateau[ligneMilieu][colonneMilieu];

                                if (pionCapture != pionActuel && pionCapture != '□') {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void CaptureMultiple(char[][] plateau, int l1, int c1) {
        boolean capturePossible = true;
        while (capturePossible) {
            capturePossible = false;
            for (int l2 = 0; l2 < plateau.length; l2++) {
                for (int c2 = 0; c2 < plateau[l2].length; c2++) {
                    if (verifieCapture(plateau, l1, c1, l2, c2)) {
                        l1 = l2; // Mise à jour de la position actuelle
                        c1 = c2;
                        capturePossible = true; // Vérifie si d'autres captures sont possibles
                        break;
                    }
                }
                if (capturePossible) break;
            }
        }
    }

    public static boolean verifieFinDeJeu(char[][] plateau, char pionBlanc, char pionNoir) {
        boolean blancTrouve = false;
        boolean noirTrouve = false;

        // Parcourt le plateau pour vérifier s'il reste des pions ⛀ ou ⛂
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == pionBlanc) {
                    blancTrouve = true;
                }
                if (plateau[i][j] == pionNoir) {
                    noirTrouve = true;
                }
            }
        }

        // Si l'un des deux pions n'est pas trouvé, la partie est terminée
        return !(blancTrouve && noirTrouve);
    }

}
