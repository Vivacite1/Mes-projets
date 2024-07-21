import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;

public class ScorePanel extends JPanel implements MouseListener {
    JButton button;
    JTextField text ;
    int scoreJoueur;
    ScoreList sl;
    JPanel register;
    String fileName;

    public ScorePanel(String file,int scoreJoueur){

        this.scoreJoueur=scoreJoueur;
        this.fileName=file;
        this.sl=new ScoreList(file);
        setLayout(new BorderLayout());
        add(getScore(6), BorderLayout.CENTER);
        // si le score du joueur est a 0 on décide de ne pas l'enregistrer
        if (scoreJoueur>0){
            this.register=new JPanel(new FlowLayout());
            this.button=new JButton("Enregistrer");
            this.text= new JTextField(7);
            register.add(text);
            register.add(button);
            button.addMouseListener(this);
            add(register, BorderLayout.SOUTH);
        }
        //add(button, BorderLayout.SOUTH);

    }



    private JPanel getScore(int n){
        //Affichage de la liste  en jLabels
        JPanel res = new JPanel(new GridLayout(n,0));
        int i = 0;
        for (Score s : sl) {
            if (i == n) break;

            JLabel temp = (new JLabel(s.getName() + " | " + s.getValue()));
            temp.setFont(new Font("Verdana", 1, 14));
            res.add(temp);
            i++;
        }


        return res;
    }


        @Override
        //Action pour clic sur enregistrer
        public void mouseClicked (MouseEvent mouseEvent){
        //on met un nom par defaut si pas de texte rentré
        String name = "Unknown";
        if (!text.getText().isEmpty())
            name = text.getText();
        sl.sortedAdd(new Score(name,scoreJoueur));
        sl.rewrite(fileName);
        removeAll();
        // on rafraichit le tableau des scores
        add(getScore(6), BorderLayout.CENTER);
        add(new JLabel("Enregistré", JLabel.CENTER), BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        }

        @Override
        public void mousePressed (MouseEvent mouseEvent){
        }
        @Override
        public void mouseReleased (MouseEvent mouseEvent){
        }
        @Override
        public void mouseEntered (MouseEvent mouseEvent){
        }
        @Override
        public void mouseExited (MouseEvent mouseEvent){
        }

        public static void main(String[] args) {
            try {
                FileWriter test = new FileWriter("ressources/scores.txt", false);
                test.write("");
                test.close();
            } catch (IOException e) { e.printStackTrace(); System.exit(1); }
        }

}

