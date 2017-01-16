/**
 * Created by ammarkhan on 16/01/2017.
 */
public class AbstractEntity  {

    String name;
    String healthStatus;
    int movingDay;



    public AbstractEntity(String name, String healthStatus, int movingDay){

        this.name = name;
        this.healthStatus = healthStatus;
        this.movingDay = movingDay;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getMovingDay() {
        return movingDay;
    }

    public void setMovingDay(int movingDay) {
        this.movingDay = movingDay;
    }

    public String toString(){
        return name + " " + healthStatus;
    }
}
