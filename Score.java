class Score implements Comparable<Score> {
    final String name;
   final  int value;
    private Score next;
    public Score(String name, int result) {
        this.name = name;
        this.value = result;
        this.next=null;

    }

    public String getName() {
        return name;
    }

    public Score getNext() {
        return next;
    }

    public int getValue() {
        return value;
    }

    public void setNext(Score s) {
        this.next = s;
    }

    public int compareTo(Score s) {
        return this.getValue()-s.getValue() ;
    }
}