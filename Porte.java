import java.awt.*;

// Classe concrète représentant une porte
public class Porte extends CaseTraversable {
    private boolean estOuverte;
    private Color couleur;


    public Porte(int l, int c, boolean estOuverte,Color couleur) {
        super(l, c);
        this.estOuverte = estOuverte;
        this.couleur=couleur;

    }

    public boolean estOuverte() {
        return estOuverte;
    }

    public void ouvrir() {
        estOuverte = true;
    }

    public boolean estTraversable() {
        return true;
    }

    @Override
    public Color getColor() {
        if (estOuverte())
            return super.getColor();
        else
            return couleur;
    }
    public void entre(Joueur j){

        //test Porte est fermé ->  on lance utiliserCle
        if (estOuverte())
            super.entre(j);
        else {
            j.utiliserCle(this);
            }
        }


}
