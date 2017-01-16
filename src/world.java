    import java.util.*;

    /**
     * Created by ammarkhan on 14/01/2017.
     */
    public class world {
        Random rand = new Random();
        private static int gridSize;
        private int population;
        private double infectionRate;
        private double superHumanRate;
        private country[][] grid;
        private int humansPerCountry;
        public static final double transmissibilityRate = 0.40;
        public static final float dyingProbability = (float)0.25;

        public world(int gridSize, int population, double infectionRate, double superHumanRate){

            this.gridSize = gridSize;
            grid = new country[gridSize][gridSize];
            this.population = population;
            this.infectionRate = infectionRate;
            this.superHumanRate = superHumanRate;
            humansPerCountry = 0;



        }

        public void createCountries(){
            int count = 0;
            for (int i = 0; i < grid.length ; i++) {

                for (int j = 0; j < grid[i].length; j++) {

                    country country = new country("country"+(count+1));
                    grid[i][j] = country;
                    count++;

                }
            }
        }

        public List<Human> createHumans(){
            List<Human> populationList = new LinkedList<Human>();
            for (int i = 0; i < population ; i++) {
                Human human = new Human("human"+(i+1), "Healthy",0);

                populationList.add(i,human);

            }
            return  populationList;

        }


        public List<Human>  createSuperHumans(){
            superHumanRate = superHumanRate/100;
            int numberOfSuperHumans = (int) (superHumanRate * population);
            List<Human> populationList = createHumans();
            for (int i = 0; i < numberOfSuperHumans ; i++) {
                Human human = new Human("SuperHuman"+(i+1), "IMMUNE",0);

                populationList.add(i,human);

            }
            return  populationList;


        }

        public void populateCountries(){
            List<Human> populationList = createSuperHumans();
            Collections.shuffle(populationList);
            int totalCountries = gridSize*gridSize;
            humansPerCountry = (int) population/totalCountries;

            for (int i = 0; i < grid.length ; i++) {


                for (int j = 0; j < grid.length ; j++) {



                    country tempCountry = grid[i][j];

                    int x = 0;
                    for(int humanNumber=0; humanNumber < 1; humanNumber++){
                        if(x==humansPerCountry){
                            break;
                        }

                        tempCountry.addHuman(populationList.get(0));
                        populationList.remove(0);
                        x++;
                        humanNumber--;

                    }
                }

            }

        }


        public  void infectInitialPopulation(){

            infectionRate = infectionRate/100;
            int totalInfectedpeople = (int) (infectionRate * population);

            for (int i = 0; i < totalInfectedpeople; i++) {

                int randomRowNumber = rand.nextInt(((gridSize-1)-0)+1);
                int randomColNumber = rand.nextInt(((gridSize-1)-0)+1);
                int randomHumanInfection = rand.nextInt(((humansPerCountry-1)-0)+1  );

                country temp = grid[randomRowNumber][randomColNumber];
                List<Human> humans = temp.getHumans();


                if(humans.get(randomHumanInfection).getHealthStatus().equals("Infected")){
                    i--;
                }
                humans.get(randomHumanInfection).setHealthStatus("Infected");
                int a=0;
                int b =0;

            }

        }

        public  void relocateDayDecision(){



            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size(); k++) {
                        if (!( isEdgeCountry(i,j))) {
                            int a = rand.nextInt((5 - 0) + 1);
                            humans.get(k).setMovingDay(a);
                        }
                    }
                }
            }

        }

        public  boolean isEdgeCountry(int row,int col){

            //Check top hoirzontal line
            if(row ==0 && (col >=0 ||  col < grid.length)){
                return true;
            }

            //Check last horizontal line
            if(row == grid.length-1 && (col >=0 ||  col < grid.length)){
                return true;
            }

            //Check first vertical col
            if(col ==0 && (row >=0 ||  row < grid.length)){
                return true;
            }

            //Check last vertical col
            if(col == grid.length-1 && (row >=0 ||  row < grid.length)){
                return true;
            }



            return false;

        }


        public  void dayCheck(int day){




            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    List<Human> humansToDelete = new ArrayList<>();

                    for (int k = 0; k < humans.size(); k++) {


                        if(humans.get(k).getMovingDay() == day && !(isEdgeCountry(i,j)) && !(humans.get(k).getHealthStatus().equals("Dead"))){

                            countryRelocate(i,j,humans.get(k));


                            humansToDelete.add(humans.get(k));


                        }

                    }


                    removeRelocatedHumans(temp,humansToDelete);
                }


            }



        }


        public  void countryRelocate(int row , int col, Human human){



            List<country> neighbouringCountries =  checkVisibilyInfectiousPeople(row,col);

            if(neighbouringCountries.size() == 0){
                setNextMovingDay(human);
            } else if(neighbouringCountries.size() == 1){
                country temp = neighbouringCountries.get(0);
                temp.addHuman(human);
                setNextMovingDay(human);
            } else if(neighbouringCountries.size() == 2){
                int a = rand.nextInt((1-0)+1);
                country temp = neighbouringCountries.get(a);
                temp.addHuman(human);
                setNextMovingDay(human);
            } else if(neighbouringCountries.size() == 3){
                int a = rand.nextInt((2-0)+1);
                country temp = neighbouringCountries.get(a);
                temp.addHuman(human);
                setNextMovingDay(human);
            } else if(neighbouringCountries.size() == 4){
                int a = rand.nextInt((3-0)+1);
                country temp = neighbouringCountries.get(a);
                temp.addHuman(human);
                setNextMovingDay(human);
            }




        }


        public  List<country> checkVisibilyInfectiousPeople(int row,int col){

            List<country> neighbouringCountries = new ArrayList<>();
            if(countVisibilyInfectiousPeople(row+1,col) ==0 && countVisibilyInfectiousPeople(row-1,col) ==0
                    && countVisibilyInfectiousPeople(row,col+1) == 0 && countVisibilyInfectiousPeople(row,col-1) == 0){

                neighbouringCountries.add(grid[row+1][col]);
                neighbouringCountries.add(grid[row-1][col]);
                neighbouringCountries.add(grid[row][col+1]);
                neighbouringCountries.add(grid[row][col-1]);

            }else if(countVisibilyInfectiousPeople(row-1,col) ==0
                    && countVisibilyInfectiousPeople(row,col+1) == 0 && countVisibilyInfectiousPeople(row,col-1) == 0){

                neighbouringCountries.add(grid[row-1][col]);
                neighbouringCountries.add(grid[row][col+1]);
                neighbouringCountries.add(grid[row][col-1]);

            }else if(countVisibilyInfectiousPeople(row+1,col) ==0 && countVisibilyInfectiousPeople(row,col+1) == 0
                    && countVisibilyInfectiousPeople(row,col-1)==0){

                neighbouringCountries.add(grid[row+1][col]);
                neighbouringCountries.add(grid[row][col+1]);
                neighbouringCountries.add(grid[row][col-1]);

            }else if(countVisibilyInfectiousPeople(row+1,col) ==0 && countVisibilyInfectiousPeople(row-1,col) ==0
                    && countVisibilyInfectiousPeople(row,col+1) == 0 ){
                neighbouringCountries.add(grid[row+1][col]);
                neighbouringCountries.add(grid[row-1][col]);
                neighbouringCountries.add(grid[row][col+1]);


            }else if(countVisibilyInfectiousPeople(row+1,col) ==0 && countVisibilyInfectiousPeople(row-1,col) ==0
                    && countVisibilyInfectiousPeople(row,col-1) == 0){

                neighbouringCountries.add(grid[row+1][col]);
                neighbouringCountries.add(grid[row-1][col]);
                neighbouringCountries.add(grid[row][col-1]);

            }


            return  neighbouringCountries;

        }



        public  int countVisibilyInfectiousPeople(int row,int col){


            int numberOfVisibilyInfectiousPeople = 0;

            country temp = grid[row][col];
            List<Human> humans = temp.getHumans();

            for (int j = 0; j < humans.size() ; j++) {
                if(humans.get(j).getHealthStatus().equals("Sick") || humans.get(j).getHealthStatus().equals("Dead")){
                    numberOfVisibilyInfectiousPeople++;
                }
            }


            return numberOfVisibilyInfectiousPeople;



        }


        public  void setNextMovingDay(Human human){
            Random rand = new Random();

            int initialMovingDay = human.getMovingDay();
            int nextMovingDay = rand.nextInt((5 - 0) + 1);

            human.setMovingDay(initialMovingDay+nextMovingDay);

        }

        public  void removeRelocatedHumans(country country, List<Human> humansToDelete){
            for (int i = 0; i < humansToDelete.size() ; i++) {
                country.removeHuman(humansToDelete.get(i));
            }

            for (int i = 0; i < humansToDelete.size() ; i++) {
                humansToDelete.remove(i);
            }
        }

        public void initializeWorld(){
            createCountries();
            populateCountries();
            infectInitialPopulation();
            relocateDayDecision();

        }

        public void simulate(){


            int day = 0;

            for (int i = 0; i < 20 ; i++) {

                System.out.println("-----------------------------------------------------------------------");
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("-----------------------------------------------------------------------");

                if(day==7){
                    setSickStatus();
                } else if (day==15){
                    setDeadStatus();
                } else if (day == 17){
                    setImmuneStatus();
                }else if (day == 18){
                    setHealthyStatus();
                }
                System.out.println("DAY"+i);
                dayCheck(day);
                infectingNewHumans();

                countTotalPeople();
                System.out.println();

                countHealthyPeople();
                System.out.println();

                countInfectedPeople();
                System.out.println();

                countSickPeople();
                System.out.println();

                countDeadPeople();
                System.out.println();
                day++;
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("-----------------------------------------------------------------------");
                System.out.println("-----------------------------------------------------------------------");

            }

        }



        public  void setSickStatus(){


            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size(); k++) {
                        if(humans.get(k).getHealthStatus().equals("Infected")){
                            humans.get(k).setHealthStatus("Sick");
                        }
                    }
                }
            }



        }

        public  void setDeadStatus(){



            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size(); k++) {
                        float randomDyingProbability = rand.nextFloat();

                        if(humans.get(k).getHealthStatus().equals("Sick") && randomDyingProbability < dyingProbability){
                            humans.get(k).setHealthStatus("Dead");

                        }
                    }
                }
            }



        }

        public  void setImmuneStatus(){


            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size(); k++) {

                        if(humans.get(k).getHealthStatus().equals("Sick")){
                            humans.get(k).setHealthStatus("Immune");

                        }
                    }
                }
            }



        }


        public  void setHealthyStatus(){


            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size(); k++) {

                        if(humans.get(k).getHealthStatus().equals("Infected")){
                            humans.get(k).setHealthStatus("Healthy");

                        }
                    }
                }
            }



        }


        public  void infectingNewHumans(){
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    int count = 0;
                    if(humans.size() > humansPerCountry){
                        int extraPeople = humans.size() - humansPerCountry;
                        int newInfectedHumans = (int) Math.round(transmissibilityRate * extraPeople);

                        for (int k = humansPerCountry; k < humans.size() ; k++) {

                            float randomTransmissibilityRate = rand.nextFloat();
                            if(count == extraPeople){
                                break;
                            }
                            if(humans.get(k).getHealthStatus().equals("Infected")){
                                count++;
                            }
                            if( (humans.get(k).getHealthStatus().equals("Healthy") ||
                               humans.get(k).getHealthStatus().equals("Immune") ) && randomTransmissibilityRate < transmissibilityRate ){
                                humans.get(k).setHealthStatus("Infected");
                                count++;
                            }
                        }
                    }


                }
            }


        }


        public  void countInfectedPeople(){

            int totalNumberOfInfectedHumans = 0;
            int numberofInfectedHumansByCountry = 0;
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size() ; k++) {

                        if(humans.get(k).getHealthStatus().equals("Infected")){
                            totalNumberOfInfectedHumans++;
                            numberofInfectedHumansByCountry++;
                        }

                    }
                    System.out.println(temp + " has " + numberofInfectedHumansByCountry + " infected people ");
                    numberofInfectedHumansByCountry =0;

                }



            }

            System.out.println("The total number of infected people in the world are " +totalNumberOfInfectedHumans);

        }


        public  void countSickPeople(){

            int totalNumberOfSickHumans = 0;
            int numberofSickHumansByCountry = 0;
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size() ; k++) {

                        if(humans.get(k).getHealthStatus().equals("Sick")){
                            totalNumberOfSickHumans++;
                            numberofSickHumansByCountry++;
                        }

                    }
                    System.out.println(temp + " has " + numberofSickHumansByCountry + " sick people ");
                    numberofSickHumansByCountry =0;

                }



            }

            System.out.println("The total number of sick people in the world are " +totalNumberOfSickHumans);

        }


        public  void countDeadPeople(){

            int totalNumberOfDeadHumans = 0;
            int numberofDeadHumansByCountry = 0;
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size() ; k++) {

                        if(humans.get(k).getHealthStatus().equals("Dead")){
                            totalNumberOfDeadHumans++;
                            numberofDeadHumansByCountry++;
                        }

                    }
                    System.out.println(temp + " has " + numberofDeadHumansByCountry + " dead people ");
                    numberofDeadHumansByCountry =0;

                }



            }

            System.out.println("The total number of dead people in the world are " +totalNumberOfDeadHumans);

        }


        public  void countHealthyPeople(){

            int totalNumberOfHealthyHumans = 0;
            int numberofHealthyHumansByCountry = 0;
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size() ; k++) {

                        if(humans.get(k).getHealthStatus().equals("Healthy")){
                            totalNumberOfHealthyHumans++;
                            numberofHealthyHumansByCountry++;
                        }

                    }
                    System.out.println(temp + " has " + numberofHealthyHumansByCountry + " healthy people ");
                    numberofHealthyHumansByCountry =0;

                }



            }

            System.out.println("The total number of healthy people in the world are " +totalNumberOfHealthyHumans);

        }


        public  void countTotalPeople(){

            int totalNumberOfHumans = 0;
            int numberofHumansByCountry = 0;
            for (int i = 0; i < grid.length ; i++) {



                for (int j = 0; j < grid.length ; j++) {

                    country temp = grid[i][j];
                    List<Human> humans = temp.getHumans();

                    for (int k = 0; k < humans.size() ; k++) {
                        if(!(humans.get(k).getHealthStatus().equals("Dead"))){
                            totalNumberOfHumans++;
                            numberofHumansByCountry++;
                        }

                    }
                    System.out.println(temp + " has " + numberofHumansByCountry + " people ");
                    numberofHumansByCountry = 0;

                }



            }

            System.out.println("The total number of people in the world are " +totalNumberOfHumans);

        }


    
        public   void printGrid() {
        for (int row = 0; row < grid.length; row++) {
            System.out.print("|");
            for (int col = 0; col < grid.length; col++) {


                country temp = grid[row][col];
                List<Human> humans = temp.getHumans();
                System.out.print(temp + " ");
                for (int i = 0; i < humans.size() ; i++) {
                    System.out.print(" " +humans.get(i).name + "(" + humans.get(i).healthStatus+")" +"(" +humans.get(i).getMovingDay()+")");
                }

                System.out.print("|");

            }
            System.out.println();
        }
    }

    }
