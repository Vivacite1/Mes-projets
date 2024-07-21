import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class FenetreJeu extends JPanel implements KeyListener {
    private Terrain terrain;
    private int tailleCase = 36;
    private int hauteur, largeur;
    private JFrame frame;

    private JPanel ressources;
    private Image joueurNormalImage; // Ajout de l'image du joueur normal
    private Image joueurChaudImage;  // Ajout de l'image du joueur chaud


    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;
        try {
            // Chargement des images pour le joueur normal et le joueur chaud
            joueurNormalImage = ImageIO.read(new File("ressources/Smiley_sourire.png"));
            joueurChaudImage = ImageIO.read(new File("ressources/Smiley_chaleur.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        // initialisation de la fenêtre de jeu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        playSound("Demarrage.wav");

        // Ajoute le KeyListener à la fenêtre
        frame.addKeyListener(this);

    }
    public void affichageRessources(Joueur j) {
        //Création du JPanel servant d'inventaire
        JPanel res = new JPanel(new WrapLayout(WrapLayout.LEFT, 20, 10));
        // Ajout score
        JLabel score = new JLabel(j.getResistance()+"");
        score.setFont(new Font("Verdana", 1, 25));
        res.add(score);
        //Récupération des clés du joueur
        for (Color c : j.getCles()) {

            JLabel temp = new JLabel("Key");
            temp.setFont(new Font("Verdana", 1, 20));
            temp.setForeground(c);
            res.add(temp);


        }
        //Ajout a la fenetre de jeu
        frame.add(res,BorderLayout.SOUTH);
        res.revalidate();
    }
    public static void playSound(String soundFilePath) {
        try {
            // Charger le fichier audio depuis le chemin spécifié
            File soundFile = new File(soundFilePath);

            // Créer une URL à partir du fichier audio
            URL soundUrl = soundFile.toURI().toURL();

            // Ouvrir le flux audio
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundUrl);

            // Obtenir le format audio
            AudioFormat format = audioIn.getFormat();

            // Créer une source de données pour le flux audio
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);

            // Ouvrir le clip et charger les données audio depuis le flux audio
            clip.open(audioIn);

            // Jouer le son
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    // Méthodes de l'interface KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                terrain.getJoueur().bouge(terrain.getCase(terrain.getJoueur().getPosition().lig - 1, terrain.getJoueur().getPosition().col));
                break;
            case KeyEvent.VK_DOWN:
                terrain.getJoueur().bouge(terrain.getCase(terrain.getJoueur().getPosition().lig + 1, terrain.getJoueur().getPosition().col));
                break;
            case KeyEvent.VK_LEFT:
                terrain.getJoueur().bouge(terrain.getCase(terrain.getJoueur().getPosition().lig, terrain.getJoueur().getPosition().col - 1));
                break;
            case KeyEvent.VK_RIGHT:
                terrain.getJoueur().bouge(terrain.getCase(terrain.getJoueur().getPosition().lig, terrain.getJoueur().getPosition().col + 1));
                break;
        }
        repaint();  // Redessiner la fenêtre après le déplacement
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {}



    public void afficheCle(Graphics g,Hall h){


    }


    public Color chooseColor(int x,int y){
        // Determination de la classe de la cible afin d'y attribuer la bonne couleur
        //On test si on est hors map
        try {
            Case cible = terrain.getCase(x, y);
            //Cas CaseTraversable
               return cible.getColor();

        }catch( ArrayIndexOutOfBoundsException e ){
            return Color.LIGHT_GRAY;
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int centerX = terrain.getJoueur().getPosition().col;
        int centerY = terrain.getJoueur().getPosition().lig;
// Utilise l'image appropriée en fonction de l'état du joueur (chaud ou normal)
        Image joueurImage = terrain.getJoueur().estChaud() ? joueurChaudImage : joueurNormalImage;
        // Charge l'image du smiley
        Image smileyImage = null;
        try {
            smileyImage = ImageIO.read(new File("ressources/Smiley_sourire.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = centerY - 3; i <= centerY + 3; i++) {
            for (int j = centerX - 3; j <= centerX + 3; j++) {
                if (Math.pow((centerX - j), 2) + Math.pow((centerY - i), 2) <= 10) {
                    g.setColor(chooseColor(i, j));
                    g.fillRect((j - centerX + 3) * tailleCase, (i - centerY + 3) * tailleCase, tailleCase, tailleCase);

                    if (chooseColor(i, j) != Color.LIGHT_GRAY) {
                        if (terrain.getCase(i, j) instanceof Hall) {
                            if (((Hall) (terrain.getCase(i, j))).contientCle()) {
                                g.setColor(((Hall) terrain.getCase(i, j)).getCle());
                                g.fillRect((j - centerX + 3) * tailleCase + tailleCase / 4, (i - centerY + 3) * tailleCase + tailleCase / 4, tailleCase / 2, tailleCase / 2);
                            }
                        }
                    }
                }
            }
        }

        if (joueurImage != null) {
            g.drawImage(joueurImage, 3 * tailleCase, 3 * tailleCase, tailleCase, tailleCase, this);
        } else {
            // Si l'image n'a pas pu être chargée, dessine un cercle jaune en remplacement
            g.setColor(Color.YELLOW);
            g.fillOval(3 * tailleCase, 3 * tailleCase, tailleCase, tailleCase);
        }

        affichageRessources(terrain.getJoueur());
    }



    public void ecranFinal(int n) {
        //Clean
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        //Affichage Score
        JLabel label = new JLabel("Score " + n);
        label.setFont(new Font("Verdana", 1, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setSize(this.getSize());
        frame.getContentPane().add(label,BorderLayout.NORTH);
        //Affichage score + enregistrement
        ScorePanel s = new ScorePanel("ressources/scores.txt",n);
        frame.getContentPane().add(s,BorderLayout.CENTER);
        //Affichage rejouer/quitter
        ChoicePanel c= new ChoicePanel(this);
        frame.getContentPane().add(c,BorderLayout.SOUTH);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
    public void closeFrame(){
        frame.dispose();
    }

    public static void main(String[] args) {
        int tempo = 100;
        Furfeux jeu = new Furfeux("ressources/newManoir.txt");
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);

        Timer timer = new Timer(tempo, e -> {
            jeu.tour(graphic);
            graphic.repaint();
            if (jeu.partieFinie()) {
                graphic.ecranFinal(Math.max(0, jeu.joueur.getResistance()));
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();

    }
}