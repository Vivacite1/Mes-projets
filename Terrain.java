import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Terrain {
    private int hauteur, largeur;
    private Case[][] carte;
    private Joueur joueur;

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }
    public Case getCase(int lig , int col){
        return carte[lig][col];
    }

    public Case[][] getCarte() {
        return carte;
    }

    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();
            this.largeur = sc.nextInt();
            sc.nextLine();
            int resistanceJoueur = sc.nextInt();
            //clés mtn inutile
            int cles = sc.nextInt();
            sc.nextLine();
            this.carte = new Case[hauteur][largeur];
            for (int l=0; l<hauteur; l++) {
                String line = sc.nextLine();
                for (int c=0; c<largeur; c++) {
                    Case cc;
                    Character ch = line.charAt(c);
                    switch (ch) {
                        //mur
                        case '#': cc = new Mur(l, c); break;
                        //hall vide
                        case ' ': cc = new Hall(l, c, 0); break;
                        //Hall avec chaleur
                        case '1': case '2': case '3': case '4':
                            cc = new Hall(l, c, (int)ch-(int)'0'); break;
                        //Sortie
                        case 'O': cc = new Sortie(l, c); break;
                        //Halls avec clés j,v ou m
                        case 'j': cc = new Hall(l, c,Color.YELLOW); break;
                        case 'm': cc = new Hall(l, c,Color.MAGENTA); break;
                        case 'v': cc = new Hall(l, c,Color.GREEN); break;
                        //Portes j v ou m
                        case 'J': cc = new Porte(l, c, false, Color.YELLOW);break;
                        case 'M': cc = new Porte(l, c, false, Color.MAGENTA);break;
                        case 'V': cc = new Porte(l, c, false, Color.GREEN);break;
                        case 'H':
                            if (this.joueur != null) throw new IllegalArgumentException("carte avec deux joueurs");
                            cc = new Hall(l, c,0);
                            this.joueur = new Joueur((CaseTraversable) cc, resistanceJoueur,new ArrayList<>());
                            joueur.setPosition((CaseTraversable)cc);
                            break;
                        default:  cc = null; break;
                    }
                    carte[l][c] = cc;
                }
            }
            sc.close();
        }
        catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }

    public Joueur getJoueur() { return this.joueur; }



    private boolean estPositionValide(int lig, int col) {
        return lig >= 0 && lig < hauteur && col >= 0 && col < largeur;
    }

}
