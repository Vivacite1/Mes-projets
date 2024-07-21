import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Furfeux {
    Terrain terrain;
    Joueur joueur;



    public Furfeux(String f) {
        this.terrain = new Terrain(f);
        this.joueur = this.terrain.getJoueur();
    }

    static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.ints(min, max + 1).findFirst().getAsInt();
    }


    public void augmenteChaleurTerrain() {
        //Généralisation de augmenteChaleur
        for(int i = 0; i < this.terrain.getHauteur(); ++i) {
            for(int j = 0; j < this.terrain.getLargeur(); ++j) {
                if (this.terrain.getCase(i, j).estTraversable()) {
                    ((CaseTraversable)this.terrain.getCase(i, j)).augmenteChaleur(this.terrain);
                }
            }
        }

    }

    public void tour(FenetreJeu f) {
        this.augmenteChaleurTerrain();
    }



    public boolean partieFinie() {
        return joueur.getPosition() instanceof Sortie || joueur.getResistance() <= 0;
    }

    public  static void jeu() {
        // Transformation en fonction jeu pour la rappeler au menu de fin
        int tempo = 100;
        Furfeux jeu = new Furfeux("ressources/newManoir.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        Timer timer = new Timer(tempo, (e) -> {
            jeu.tour(graphic);
            graphic.repaint();
            if (jeu.partieFinie()) {
                graphic.ecranFinal(Math.max(0, jeu.joueur.getResistance()));
                ((Timer)e.getSource()).stop();
            }

        });
        timer.start();
    }
    public static void main(String[] args){
        Furfeux.jeu();

    }
}
