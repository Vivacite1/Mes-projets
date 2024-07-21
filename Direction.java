import java.util.Random;

public class  Direction {

    public enum Type {

        nord,
        sud,
        est,
        ouest;
    }

    private Type direction;

    //constructeur de la classe Direction

    public Direction(Type direction){
        this.direction=direction;
    }
    public Type getDirection(){
        return direction;
    }
    //change la direction
    public void setDirection(Type direction){
        this.direction=direction;
    }


    public static Direction getRandomDirection() {
        int rand = (int)(Math.random()*4);
        switch (rand) {
            case 0:  return new Direction(Type.nord);
            case 1:  return new Direction(Type.sud);
            case 2:  return new Direction(Type.est);
            default: return new Direction(Type.ouest);
        }
    }
    public String tostring(){
        switch(direction){
            case nord:
                return "nord";
            case sud:
                return "sud";
            case est:
                return "est";
            case ouest:
                return "ouest";
            default:
                return"";
        }
    }
}
