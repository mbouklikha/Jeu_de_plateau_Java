import java.util.Scanner;

public class Jeu {

    public static boolean jouer(char[][] plateau, String j1, String j2) {
        Scanner sc = new Scanner(System.in);
        boolean finJeu = false; // Indique si la partie est terminée
        boolean tourBlancs = true; // Indique si c'est au tour des blancs (⛂)

        System.out.println(j1 + " joue avec les blancs (⛂)");
        System.out.println(j2 + " joue avec les noirs (⛀)");
        System.out.println();

        while (!finJeu) {
            // Détermine le joueur actuel et son pion
            String joueurActuel;
            char pionActuel;

            if (tourBlancs) {
                joueurActuel = j1;
                pionActuel = '⛂'; // C'est le tour des blancs
            } else {
                joueurActuel = j2;
                pionActuel = '⛀'; // C'est le tour des noirs
            }

            // Affiche le plateau
            Methodes.afficherPlateau(plateau);

            System.out.println("\n" + joueurActuel + ", c'est votre tour avec le pion " + pionActuel);
            System.out.println("Entrez les coordonnées du pion à déplacer (ligne et colonne) : ");
            System.out.print("Ligne départ : ");
            int l1 = sc.nextInt();
            System.out.print("Colonne départ : ");
            int c1 = sc.nextInt();

            // Vérifie si le pion choisi appartient bien au joueur actuel
            if (plateau[l1][c1] != pionActuel) {
                System.out.println("Vous ne pouvez déplacer que vos propres pions.");
                continue;
            }

            System.out.println("Entrez les coordonnées d'arrivée (ligne et colonne) : ");
            System.out.print("Ligne arrivée : ");
            int l2 = sc.nextInt();
            System.out.print("Colonne arrivée : ");
            int c2 = sc.nextInt();

            // Vérifie si une capture est obligatoire pour le joueur actuel
            boolean captureObligatoire = Methodes.forcerCapture(plateau, pionActuel);

            if (captureObligatoire) {
                // Vérifie si le déplacement demandé est une capture valide
                if (Methodes.verifieCapture(plateau, l1, c1, l2, c2)) {
                    int ligneMilieu = (l1 + l2) / 2;
                    int colonneMilieu = (c1 + c2) / 2;

                    plateau[ligneMilieu][colonneMilieu] = '□'; // Retire le pion capturé
                    plateau[l2][c2] = plateau[l1][c1]; // Déplace le pion
                    plateau[l1][c1] = '□'; // Vide la case de départ

                    // Capture multiple
                    Methodes.CaptureMultiple(plateau, l2, c2);
                    System.out.println("Capture effectuée !");
                    tourBlancs = !tourBlancs; // Alterne le tour après une capture réussie
                }
                else {
                    System.out.println("La capture est obligatoire ! Essayez à nouveau.");
                }
            }
            // Sinon, vérifie et effectue un déplacement simple
            else if (Methodes.verifieDeplacement(plateau, l1, c1, l2, c2, pionActuel)) {
                System.out.println("Déplacement réussi !");
                tourBlancs = !tourBlancs; // Alterne le tour après un déplacement réussi
            }
            // Si l'action est invalide
            else {
                System.out.println("Action invalide. Réessayez.");
            }

            // Vérifie si la partie est terminée
            if (Methodes.verifieFinDeJeu(plateau, '⛂', '⛀')) {
                System.out.println("Félicitations, " + joueurActuel + " a gagné !");
                finJeu = true;
            }
        }

        System.out.println("La partie est terminée.");
        return true;
    }


}