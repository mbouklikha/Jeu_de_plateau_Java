import java.util.Scanner;
import java.util.ArrayList;

public class Menu {
    public static void menu(){
        String joueur1;
        String joueur2;
        String joueur3;
        String Ia;
        ArrayList<String> liste = new ArrayList<>();
        int choix;

        Scanner saisie = new Scanner(System.in);

        do {
            System.out.println("  ");
            System.out.println("1. Jouer contre Joueur");
            System.out.println("2. Jouer contre ordinateur");
            System.out.println("3. Quitter ! ");


            System.out.print("Chosir un Menu : ");
            choix = saisie.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("Saisir les pseudo !");
                    System.out.println("Joueur 1 :");
                    Methodes.AjouterPseudo(liste);
                    System.out.println("Joueur 2 :");
                    Methodes.AjouterPseudo(liste);
                    char[][] plateau = Plateau.plt();
                    joueur1 = liste.get(0);
                    joueur2 = liste.get(1);
                    Jeu.jouer(plateau,joueur1,joueur2);
                    break;
                case 2:
                    System.out.println("Pseudo :");
                    Methodes.AjouterPseudo(liste);
                    char[][] plateau2 = Plateau.plt();
                    joueur3 = liste.get(0);
                    /*Ia = "Ordinateur";
                    Jeu.jouer(plateau2,joueur3,Ia);*/
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Erreur, veuillez ressaisir !");
                    break;
            }
        } while (choix != 3);
    }
}
