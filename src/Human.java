/**
 * Created by ammarkhan on 14/01/2017.
 */
public class Human extends  AbstractEntity{



    String name;
    String healthStatus;
    int movingDay;




    public Human(String name, String healthStatus, int movingDay){

        super(name,healthStatus,movingDay);
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