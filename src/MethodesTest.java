import static org.junit.jupiter.api.Assertions.*;

import org.testng.annotations.Test;


public class MethodesTest {

    @Test
    public final void testInitialiserPlateau() {
        char[][] plateau = new char[8][8];
        Methodes.initialiserPlateau(plateau);

        // Vérification des lignes supérieures (pions noirs)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    assertEquals(' ', plateau[i][j], "Case blanche incorrecte en haut du plateau");
                } else {
                    assertEquals('⛀', plateau[i][j], "Pion noir incorrect en haut du plateau");
                }
            }
        }

        // Vérification des lignes du milieu (cases jouables)
        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    assertEquals(' ', plateau[i][j], "Case blanche incorrecte au milieu");
                } else {
                    assertEquals('□', plateau[i][j], "Case jouable incorrecte au milieu");
                }
            }
        }

        // Vérification des lignes inférieures (pions blancs)
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    assertEquals(' ', plateau[i][j], "Case blanche incorrecte en bas du plateau");
                } else {
                    assertEquals('⛂', plateau[i][j], "Pion blanc incorrect en bas du plateau");
                }
            }
        }
    }

    @Test
    public void testVerifieCapture() {
        char[][] plateau = new char[8][8];

        // Initialisation du plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                plateau[i][j] = '□'; // Cases vides par défaut
            }
        }

        // Scénario 1 : Capture valide
        plateau[3][3] = '⛂'; // Pion blanc à capturer
        plateau[2][2] = '⛀'; // Pion noir qui capture
        boolean result;
        result = Methodes.verifieCapture(plateau, 2, 2, 4, 4);
        assertTrue(result, "La capture devrait être valide.");

        // Scénario 2 : Pas de capture (case intermédiaire vide)
        plateau[3][3] = '□'; // La case intermédiaire est vide
        result = Methodes.verifieCapture(plateau, 2, 2, 4, 4);
        assertFalse(result, "Il ne devrait pas y avoir de capture si la case intermédiaire est vide.");

        // Scénario 3 : Pas de capture (case d'arrivée invalide)
        plateau[3][3] = '⛂'; // Pion blanc à capturer
        plateau[4][4] = '⛀'; // Case d'arrivée occupée
        result = Methodes.verifieCapture(plateau, 2, 2, 4, 4);
        assertFalse(result, "Il ne devrait pas y avoir de capture si la case d'arrivée est occupée.");

        // Scénario 4 : Pas de capture (pas de pion adverse à capturer)
        plateau[4][4] = '□'; // Case d'arrivée vide
        plateau[3][3] = '⛀'; // Même couleur que le pion qui capture
        result = Methodes.verifieCapture(plateau, 2, 2, 4, 4);
        assertFalse(result, "Il ne devrait pas y avoir de capture si la case intermédiaire ne contient pas un pion adverse.");
    }




    @Test
    public final void testVerifieFinDeJeu() {
        char[][] plateau = new char[8][8];
        Methodes.initialiserPlateau(plateau);

        // Cas où les deux camps ont encore des pions
        assertFalse(Methodes.verifieFinDeJeu(plateau, '⛂', '⛀'), "Fin de jeu détectée incorrectement");

        // Cas où les pions noirs sont tous capturés
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == '⛀') {
                    plateau[i][j] = '□';
                }
            }
        }
        assertTrue(Methodes.verifieFinDeJeu(plateau, '⛂', '⛀'), "Fin de jeu non détectée (pions noirs absents)");

        // Cas où les pions blancs sont tous capturés
        Methodes.initialiserPlateau(plateau);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (plateau[i][j] == '⛂') {
                    plateau[i][j] = '□';
                }
            }
        }
        assertTrue(Methodes.verifieFinDeJeu(plateau, '⛂', '⛀'), "Fin de jeu non détectée (pions blancs absents)");
    }





}
