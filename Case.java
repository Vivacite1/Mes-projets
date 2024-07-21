import java.util.ArrayList;
import java.awt.Color;

public abstract class Case {
        public final int lig, col;
        public Case(int l, int c) {
            this.lig = l;
            this.col = c;
        }

        public abstract boolean estTraversable();
        public abstract Color getColor();
        public abstract void entre(Joueur j);

    public int getLig() {
        return lig;
    }

    public int getCol() {
        return col;
    }
}
