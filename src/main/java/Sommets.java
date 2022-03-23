import java.util.ArrayList;

public class Sommets {
    private char type;
    private String name;

    public Sommets(char type, String name){
        this.type = type;
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Sommets{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
