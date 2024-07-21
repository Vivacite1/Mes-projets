import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

class ScoreList implements Iterable<Score> {
    // List chainé perso car besoin d'un tri a l'insert
    Score head;
    Score tail;

    public ScoreList() {
        this.head = null;
        this.tail = null;
    }
    public ScoreList(String file) {
        //On va chercher les infos dans le fichier txt
        this();
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            while (sc.hasNext()) {
                this.addLast(new Score(sc.next(), sc.nextInt()));
            }
        } catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }
    public void addFirstElt(Score s) {
        this.head = s;
        this.tail = s;
    }
     public void addLast(Score s) {
        if (this.estVide())
            addFirstElt(s);
        else{
            this.tail.setNext(s);
            tail=s;
        }
    }




    public boolean estVide(){
        return (head==null);//tail==null);
    }
    public void sortedAdd(Score s) {
        //cas score nul
        if (s.getValue() <= 0) return;
        //Cas list null
        if (estVide())
            addFirstElt(s);
            //Cas positif
            //comparaison avec head
        else if (s.getValue() > head.getValue()) {
            System.out.println("s>head");
            s.setNext(head);
            head = s;

        } else {
            //Defilement jusqu'au bon current, on garde temp pour pouvoir intercaler s
            Score current = head;
            Score temp = null;
            while ( current.getValue() >= s.getValue()) {
                //Si on arrive a la fin on met s a la fin
                if (current.equals(tail)) {
                    this.addLast(s);
                    return;
                }
                System.out.println(" temp avant temp.getNext : "+current.getName());
                 temp=current;
                 current=current.getNext();
                System.out.println(" temp après temp.getNext : "+current.getName());
            }
            s.setNext(current);
            temp.setNext(s);


        }
    }

    public void rewrite(String file){
        // efface et remplit le fichier txt
        try {
            // on ecrit a partir du debut du fichier -> vidé
            FileWriter fw = new FileWriter(file, false);
            for (Score s : this) {
                fw.write(s.getName()+" "+s.getValue()+"\n");
            }
            fw.close();
        } catch (IOException e) { e.printStackTrace(); System.exit(1); }
    }


    @Override
    public Iterator<Score> iterator() {
        return new ScoreIterator(this);
    }

    class ScoreIterator implements Iterator<Score>{
        //Implémentation de la sous classe pour iterer facilement sur la liste
        Score current;

        public ScoreIterator(ScoreList list){
            current=head;
        }

        @Override
        public boolean hasNext() {
            return current!=null ;
        }

        @Override
        public Score next() {
            Score res = new Score(current.getName(), current.getValue());
            current=current.getNext();
            return res;
        }

        @Override
        public void remove() {
        }
    }
    public static void main(String[] args){
        ScoreList test= new ScoreList("ressources/scores.txt");
        for (Score s : test){
            System.out.println(s.getName() + " " + s.getValue());

        }
    }

}














