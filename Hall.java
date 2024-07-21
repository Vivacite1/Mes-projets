import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class Hall extends CaseTraversable {
    private Color cle;


    public Hall(int l, int c,int chaleur) {
        super(l, c, chaleur);
        cle=null;
    }
    public Hall(int l, int c,int chaleur, Color cle) {
        super(l, c,chaleur);
        this.cle=cle;
    }

    public Hall(int l, int c,Color cle) {
        super(l, c);
        this.cle=cle;
    }

    boolean contientCle(){
        return (cle!=null);
    }
    public Color getCle() {
        return cle;

    }

    public void retireCle(){
        this.cle =null;
    }

}
