    import java.awt.*;
    import java.util.ArrayList;
    import java.util.List;
    public class Joueur {
        private CaseTraversable c;
        private int resistance;
        private ArrayList<Color> cles;


        public Joueur(CaseTraversable c, int r , ArrayList<Color> cles) {
            this.c = c;
            this.resistance = r;
            this.cles = cles;
        }
        public Joueur(CaseTraversable c, int r ) {
            this.c = c;
            this.resistance = r;
            this.cles = new ArrayList<Color>();
        }
        public int getResistance() {
            return resistance;
        }

        public ArrayList<Color> getCles() {
            return cles;
        }

        public void setPosition(CaseTraversable nouvellePosition) {
            this.c = nouvellePosition;
        }
        public Case getPosition(){
            return c;
        }

        public void bouge(Case cible) {
            //On gère le cas hors grille
            try {
                cible.entre(this);

            } catch (ArrayIndexOutOfBoundsException e) {
                return;
            }
        }
        public void prendCle(Hall c){
            this.getCles().add(c.getCle());
            c.retireCle();
        }

        public void utiliserCle(Porte porte) {
            if (cles.contains(porte.getColor())) {
                cles.remove(porte.getColor());
                porte.ouvrir();
            }
        }
        public boolean estChaud() {
            // Détermine si le joueur est "chaud" en fonction de la résistance
            return resistance < SEUIL_CHAUD;
        }

        private static final int SEUIL_CHAUD = 300; // Valeur à ajuster

        public void subit(int i){
            resistance-=i;
        }

        public static void main(String[] args){
            System.out.println("Test  method bouge()");
            Furfeux jeu = new Furfeux("bougeTest.txt");
            System.out.println(" Joueur sur case "+jeu.joueur.getPosition().lig+" "+jeu.joueur.getPosition().col);
            jeu.joueur.bouge(jeu.terrain.getCase(0,2));
            System.out.println("Test bouge contre mur (2,0) : doit rester en (1,2) : "+jeu.joueur.getPosition().lig+" "+jeu.joueur.getPosition().col);
            jeu.joueur.bouge(jeu.terrain.getCase(1,3));
            System.out.println("Test bouge vers hall (1,3) : "+jeu.joueur.getPosition().lig+" "+jeu.joueur.getPosition().col);
            System.out.println(" cles : "+jeu.joueur.getCles());
            jeu.joueur.bouge(jeu.terrain.getCase(1,1));;
            System.out.println("Test bouge vers porte(1,1) avec clé : "+jeu.joueur.getPosition().lig+" "+jeu.joueur.getPosition().col+" nb clés = "+jeu.joueur.getCles());
            jeu.joueur.bouge(jeu.terrain.getCase(1,0));
            System.out.println("Test bouge vers porte(1,0) sans clé : "+jeu.joueur.getPosition().lig+" "+jeu.joueur.getPosition().col);


        }

    }
