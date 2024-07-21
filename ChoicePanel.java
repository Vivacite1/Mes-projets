import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChoicePanel extends JPanel  {
    private JButton retry;
    private JButton quit;



    public ChoicePanel(FenetreJeu j){
        retry = new JButton ("Rééssayer");
        quit = new JButton("Quitter");
        //Création de l'action relancer
        retry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                j.closeFrame();
                Furfeux.jeu();
            }});
        //Création de l'action quitter
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                j.closeFrame();
            }});
        add(retry);
        add(quit);

    }

}
