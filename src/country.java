import java.util.LinkedList;
import java.util.List;

/**
 * Created by ammarkhan on 14/01/2017.
 */
public class country {





    String name;

    List<Human> humans = new LinkedList<Human>();

    public country(String name){
        this.name = name;


    }

    public List<Human> getHumans() {
        return humans;
    }

    public void setHumans(List<Human> flights) {
        this.humans = humans;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    void addHuman(Human human){
        humans.add(human);

    }
    void removeHuman(Human human){
        humans.remove(human);


    }

    public String toString(){
        return name;
    }



}
