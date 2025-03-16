import java.util.*;

// Méthodes de l'IA pour le jeu de dames
public class DamesIA {
    public static int[] choisirDeplacement(char[][] plateau) {
        List<int[]> mouvementsValides = obtenirMouvementsValides(plateau, '⛀');
        if (mouvementsValides.isEmpty()) return null;

        // Vérifie s'il existe une capture obligatoire
        for (int[] mouvement : mouvementsValides) {
            if (Math.abs(mouvement[2] - mouvement[0]) == 2) { // Capture détectée
                return gererCapturesMultiples(plateau, mouvement, '⛀');
            }
        }

        // Stratégie avancée : maximiser les captures ou minimiser les pertes
        int[] meilleurMouvement = null;
        int meilleurScore = Integer.MIN_VALUE;

        for (int[] mouvement : mouvementsValides) {
            char[][] plateauTemp = copierPlateau(plateau);
            effectuerDeplacement(plateauTemp, mouvement[0], mouvement[1], mouvement[2], mouvement[3], '⛀');
            int score = evaluerPlateau(plateauTemp);

            if (score > meilleurScore) {
                meilleurScore = score;
                meilleurMouvement = mouvement;
            }
        }

        return meilleurMouvement;
    }

    public static int[] gererCapturesMultiples(char[][] plateau, int[] mouvement, char piece) {
        char[][] plateauTemp = copierPlateau(plateau);
        effectuerDeplacement(plateauTemp, mouvement[0], mouvement[1], mouvement[2], mouvement[3], piece);

        List<int[]> capturesSupplementaires = obtenirCapturesValides(plateauTemp, mouvement[2], mouvement[3], piece);
        while (!capturesSupplementaires.isEmpty()) {
            int[] prochaineCapture = capturesSupplementaires.get(0); // Prend la première capture disponible
            effectuerDeplacement(plateauTemp, prochaineCapture[0], prochaineCapture[1], prochaineCapture[2], prochaineCapture[3], piece);
            capturesSupplementaires = obtenirCapturesValides(plateauTemp, prochaineCapture[2], prochaineCapture[3], piece);
        }

        return mouvement;
    }

    public static List<int[]> obtenirCapturesValides(char[][] plateau, int ligneDepart, int colonneDepart, char piece) {
        List<int[]> captures = new ArrayList<>();
        int direction = 0;
        if (piece == '□') {
            direction = 1;
        } else if (piece == '⛀') {
            direction = -1;
        }

        for (int dx : new int[]{2 * direction}) {
            for (int dy : new int[]{-2, 2}) {
                int ligneArrivee = ligneDepart + dx;
                int colonneArrivee = colonneDepart + dy;
                if (deplacementValide(plateau, ligneDepart, colonneDepart, ligneArrivee, colonneArrivee, piece)) {
                    captures.add(new int[]{ligneDepart, colonneDepart, ligneArrivee, colonneArrivee});
                }
            }
        }

        return captures;
    }

    public static List<int[]> obtenirMouvementsValides(char[][] plateau, char piece) {
        List<int[]> mouvements = new ArrayList<>();
        int direction = 0;
        if (piece == '□') {
            direction = 1;
        } else if (piece == '⛀') {
            direction = -1;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == piece) {
                    for (int dx : new int[]{direction, 2 * direction}) {
                        for (int dy : new int[]{-1, 1}) {
                            int ligneArrivee = i + dx;
                            int colonneArrivee = j + 2 * dy;
                            if (deplacementValide(plateau, i, j, ligneArrivee, colonneArrivee, piece)) {
                                mouvements.add(new int[]{i, j, ligneArrivee, colonneArrivee});
                            }
                        }
                    }
                }
            }
        }
        return mouvements;
    }

    public static List<int[]> obtenirMouvementsValidesDames(char[][] plateau, char piece) {
        List<int[]> mouvements = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == piece) {
                    for (int dx : new int[]{-1, 1}) {
                        for (int dy : new int[]{-1, 1}) {
                            for (int pas = 1; pas < 8; pas++) {
                                int ligneArrivee = i + dx * pas;
                                int colonneArrivee = j + dy * pas;
                                if (!deplacementValideDame(plateau, i, j, ligneArrivee, colonneArrivee, piece)) {
                                    break;
                                }
                                mouvements.add(new int[]{i, j, ligneArrivee, colonneArrivee});
                            }
                        }
                    }
                }
            }
        }
        return mouvements;
    }

    public static boolean deplacementValide(char[][] plateau, int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, char piece) {
        if (ligneArrivee < 0 || ligneArrivee >= 8 || colonneArrivee < 0 || colonneArrivee >= 8) return false;
        if (plateau[ligneArrivee][colonneArrivee] != '□') return false;

        int direction = 0;
        if (piece == '□') {
            direction = 1;
        } else if (piece == '⛀') {
            direction = -1;
        }

        int deltaX = ligneArrivee - ligneDepart;
        int deltaY = Math.abs(colonneArrivee - colonneDepart);

        if (deltaX == direction && deltaY == 1) {
            return true; // Déplacement simple
        } else if (deltaX == 2 * direction && deltaY == 2) {
            // Vérifie si une prise est possible
            int ligneMilieu = (ligneDepart + ligneArrivee) / 2;
            int colonneMilieu = (colonneDepart + colonneArrivee) / 2;
            if (piece == '□') {
                return plateau[ligneMilieu][colonneMilieu] == '⛀';
            } else {
                return plateau[ligneMilieu][colonneMilieu] == '□';
            }
        }

        return false;
    }

    public static boolean deplacementValideDame(char[][] plateau, int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, char piece) {
        if (ligneArrivee < 0 || ligneArrivee >= 8 || colonneArrivee < 0 || colonneArrivee >= 8) return false;

        int deltaX = ligneArrivee - ligneDepart;
        int deltaY = colonneArrivee - colonneDepart;

        if (Math.abs(deltaX) != Math.abs(deltaY)) return false;

        int pasX = deltaX > 0 ? 1 : -1;
        int pasY = deltaY > 0 ? 1 : -1;
        for (int i = 1; i < Math.abs(deltaX); i++) {
            if (plateau[ligneDepart + i * pasX][colonneDepart + i * pasY] != '□') {
                return false;
            }
        }

        return plateau[ligneArrivee][colonneArrivee] == '□';
    }

    public static void effectuerDeplacement(char[][] plateau, int ligneDepart, int colonneDepart, int ligneArrivee, int colonneArrivee, char piece) {
        plateau[ligneDepart][colonneDepart] = '□';
        plateau[ligneArrivee][colonneArrivee] = piece;

        // Si une prise a été effectuée, supprimer la pièce capturée
        if (Math.abs(ligneArrivee - ligneDepart) == 2) {
            int ligneMilieu = (ligneDepart + ligneArrivee) / 2;
            int colonneMilieu = (colonneDepart + colonneArrivee) / 2;
            plateau[ligneMilieu][colonneMilieu] = '□';
        }
    }

    public static int evaluerPlateau(char[][] plateau) {
        int scoreIA = 0;
        int scoreJoueur = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == '⛀') {
                    scoreIA += 10;
                } else if (plateau[i][j] == '□') {
                    scoreJoueur += 10;
                }
            }
        }

        return scoreIA - scoreJoueur;
    }

    public static char[][] copierPlateau(char[][] plateau) {
        char[][] nouveauPlateau = new char[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(plateau[i], 0, nouveauPlateau[i], 0, 8);
        }
        return nouveauPlateau;
    }
}
