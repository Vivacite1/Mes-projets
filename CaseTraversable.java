import java.awt.*;

public abstract class CaseTraversable extends Case {
    private int chaleur;

    public CaseTraversable(int l, int c) {
        super(l, c);
        this.chaleur = 0;
    }

    public CaseTraversable(int l, int c, int chaleur) {
        super(l, c);
        this.chaleur = chaleur;
    }

    public int getChaleur() {
        return this.chaleur;
    }

    public boolean estTraversable() {
        return true;
    }

    public void augmenteChaleur(Terrain t) {
        //Calcul de la somme de chaleur des cases alentours
        int sumChaleur = 0;
        for(int i = this.lig - 1; i <= this.lig + 1; ++i) {
            for(int j = this.col - 1; j <= this.col + 1; ++j) {
                if (t.getCase(i, j).estTraversable()) {
                    sumChaleur += ((CaseTraversable)t.getCase(i, j)).getChaleur();
                }
            }
        }
        //comparaison avec nb aléatoire pour définir l'action
        int n = Furfeux.getRandomNumberInRange(0, 199);
        if (this.chaleur < 10 && n < sumChaleur) {
            ++this.chaleur;
        } else if (this.chaleur > 0 && n > 190) {
            --this.chaleur;
        }

    }

    @Override
    public Color getColor() {
    //blanc + teinte en fonction de chaleur
        return new Color(255,255-(255*getChaleur()/10),255-(255*getChaleur()/10));
    }
    public void entre(Joueur j) {
        // Vérifie si la case contient une clé
        if (this instanceof Hall && ((Hall) this).contientCle()) {
            // Ramasse la clé et met à jour la position du joueur
            j.prendCle((Hall)this);
        }
        j.setPosition(this);
        j.subit(this.chaleur);

        }


}
