package part3;

public interface Person {
    String getName();
    void setName(String name);
    void say(String phrase);
    void think(String thought);
    PersonTypes getType();
}
