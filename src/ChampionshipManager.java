import com.bethecoder.ascii_table.ASCIITable;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;

interface ChampionshipManager{  //setting the method signatures that need to be implemented in the subclass
    void viewDrivers();
    void createDriver();
    void deleteDriver();
    void changeDriver();
    void displayStatsForDriver();
    void f1DriverTable();
    void addARace() throws ParseException;
    void saveData();
    void loadData();
}

// creates the comparator for comparing data values
class PointComparator implements Comparator<Formula1Driver>{ //https://www.geeksforgeeks.org/how-to-sort-arraylist-using-comparator/
    @Override
    public int compare(Formula1Driver Driver1,Formula1Driver Driver2) {
        if (Driver1.getDriverCurrentPoints() == Driver2.getDriverCurrentPoints() && ((Driver1.getFirstPos() < Driver2.getFirstPos()))){
            return 0; //for equal elements
        } else if (Driver1.getDriverCurrentPoints() < Driver2.getDriverCurrentPoints()) {
            return 1;
        } else {
            return -1;
        }
    }
}


class PointComparatorAscending implements Comparator<Formula1Driver>{ //comparator used to sort the driver current points in ascending order
    @Override
    public int compare(Formula1Driver Driver1,Formula1Driver Driver2) {
        if (Driver1.getDriverCurrentPoints() == Driver2.getDriverCurrentPoints() && ((Driver1.getFirstPos() > Driver2.getFirstPos()))){
            return 0; //for equal elements
        } else if (Driver1.getDriverCurrentPoints() > Driver2.getDriverCurrentPoints()) {
            return 1;
        } else {
            return -1;
        }
    }
}


class PointComparatorDescendingFirstPositions implements Comparator<Formula1Driver>{//comparator used to sort the driver first positions in descending order
    @Override
    public int compare(Formula1Driver Driver1,Formula1Driver Driver2) {
        if (Driver1.getFirstPos() < Driver2.getFirstPos() ) {
            return 1;
        } else {
            return -1;
        }
    }
}

//comparator used to sort the driver race date in ascending order
class PointComparatorAscendingRaceRandomDate implements Comparator<Formula1Driver> { //https://stackoverflow.com/questions/32625407/sorting-localdatetime/32625500
    public int compare(Formula1Driver Driver1,Formula1Driver Driver2) {
        return Driver1.getDriverRaceDate().compareTo(Driver2.getDriverRaceDate()); //compares the dates of driver 1 and driver 2
    }

}


class Formula1ChampionshipManager implements ChampionshipManager { //implementation of the defined methods in the interface

    static Scanner input = new Scanner(System.in);

    static ArrayList<Formula1Driver> driverDataAL = new ArrayList<>(); //creating an arraylist of type Formula1Driver to store driver data
    static ArrayList<Formula1Driver> testDriverDataAL = new ArrayList<>();

    Formula1Driver newDriverCheck = new Formula1Driver(); //creating an instance to access the formula1driver class

    public static void main(String[] args) throws ParseException {

        Formula1ChampionshipManager newManager = new Formula1ChampionshipManager(); //instantiating formula1championship manager

        boolean end = true;

        System.out.println("Enter 'Y' to load previous if file any letter if not");
        String userFileChoice = input.next();
        if(userFileChoice.equalsIgnoreCase("Y")){
            newManager.loadData(); //calling the load file method
        }

        while (end) { //displaying the menu
            System.out.println("--Formula 1 Championship Manager--" + '\n' + '\n'
                    + "Press 'A' to Create a driver" + '\n' + "Press 'B' to Delete driver" + '\n' + "Press 'C' to Change driver"
                    + '\n' + "Press 'D' to Display driver statistics" + '\n' + "Press 'E' to Show formula 1 driver table"
                    + '\n' + "Press 'F' to Add a race" + '\n' + "Press 'G' to Save driver data" + '\n'+"Press 'H' to Load driver data" +
                    '\n'+ "Press 'X' to View drivers"+'\n'+"Press 'I' to get Test Data"+'\n'+"Press 'J' to View driver GUI"
                    +'\n'+"Press 'Z' to EXIT");

            String userInput = input.next();   //getting the user input on what the user wants to do
            userInput = userInput.toUpperCase();

            switch (userInput) {
                case "X" -> newManager.viewDrivers(); //calls the view drivers method
                case "A" -> newManager.createDriver(); //calls the create driver method
                case "B" -> newManager.deleteDriver(); //calls to delete driver method
                case "C" -> newManager.changeDriver(); //calls the change driver method
                case "D" -> newManager.displayStatsForDriver(); //calls the display statistics for driver method
                case "E" -> newManager.f1DriverTable(); //calls the formula1 driver table method
                case "F" -> newManager.addARace(); //calls add a race method
                case "G" -> newManager.saveData(); //calls the save data method
                case "H" -> newManager.loadData(); //calls the load data method
                case "I" -> newManager.testData(); //calls the test data method
                case "J" -> new Formula1ManagerGUI(); //opens the gui for the f1championship
                case "Z" -> {                       //to exit from the program
                    System.out.println("EXIT FROM PROGRAM");
                    end=false;
                }
            }
        }
    }

    //view all the drivers method
    @Override
    public void viewDrivers() {
        for (int i = 0; i < driverDataAL.size(); i++) {
            System.out.println("_______________" + "Driver number:" + i + "__________________");
            System.out.println("Driver Name:" + driverDataAL.get(i).getDriverName());
            System.out.println("Driver Location:" + driverDataAL.get(i).getDriverLocation());
            System.out.println("Driver Team:" + driverDataAL.get(i).getDriverTeam());
            System.out.println("Driver race count:" + driverDataAL.get(i).getDriverRaceCount());
            System.out.println("Driver current points:" + driverDataAL.get(i).getDriverCurrentPoints());
            System.out.println("Driver points:" + driverDataAL.get(i).getDriverPoints());
            System.out.println("Driver first position:" + driverDataAL.get(i).getFirstPos());
            System.out.println("Diver second position:" + driverDataAL.get(i).getSecondPos());
            System.out.println("Driver third position:" + driverDataAL.get(i).getThirdPos());
            System.out.println("Driver race date:"+driverDataAL.get(i).getDriverRaceDate());
            System.out.println("______________________________________________");
        }
    }

    // creating a new driver method
    @Override
    public void createDriver() {
        System.out.println("--Creating driver--");

        int driverCurrentPoints;
        int driverPoints = 0;
        int firstPos = 0;
        int secondPos = 0;
        int thirdPos = 0;
        Boolean checkNameVar = true;
        Boolean checkTeamVar = true;


        System.out.println("Enter the name of the driver:");
        // Consuming the leftover new line using the nextLine() method
        input.nextLine();
        String driverName = input.nextLine(); //entering the driver name
        while(checkNameVar){ //checking if the name already exists
            checkNameVar = checkName(driverName);
            if(checkNameVar){
                System.out.println("Please enter the name of the driver again:");
                driverName = input.nextLine();
            }
        }
        newDriverCheck.setDriverName(driverName); //setting the driver name

        System.out.println("Enter the driver's location:");
        String driverLocation = input.nextLine();   //entering the driver location
        newDriverCheck.setDriverLocation(driverLocation); //setting the driver location

        System.out.println("Enter the driver's team:");
        String driverTeam = input.nextLine(); //entering the driver team
        while(checkTeamVar){ //checking if the team already exists
            checkTeamVar = checkTeam(driverTeam);
            if(checkTeamVar){
                System.out.println("Please enter the team of the driver again:");
                driverTeam = input.nextLine();
            }
        }
        newDriverCheck.setDriverTeam(driverTeam); //setting the driver team

        System.out.println("-Entering the new driver's statistics-");

        System.out.println("Enter the driver's current points");
        driverCurrentPoints = input.nextInt(); //entering the driver's current points
        newDriverCheck.setDriverCurrentPoints(driverCurrentPoints); //setting the driver current points

        System.out.println("Enter the new driver's number of races:");
        int driverRaceCount = input.nextInt(); //entering the driver's race count
        newDriverCheck.setDriverRaceCount(driverRaceCount); //setting the driver race count

            for (int i = 0; i < driverRaceCount; i++) { //getting the positions for the number of races
                Boolean checkPositionVar = true;
                System.out.println("Enter the new driver's race "+ (i+1)+" positions:");
                int driverRacePositions = input.nextInt(); //entering the position of the driver
                while(checkPositionVar){//checking if the position is within the range
                    checkPositionVar = checkPosition(driverRacePositions);
                    if(checkPositionVar){
                        System.out.println("Please enter a valid driver position:");
                        driverRacePositions = input.nextInt();
                        checkPositionVar = checkPosition(driverRacePositions);
                    }
                }
                newDriverCheck.setDriverRacePositions(driverRacePositions);//setting the driver positions

                if (driverRacePositions >= 1 && driverRacePositions <= 10) { //calculating the driver points and getting positions

                    if (driverRacePositions == 1) {
                        driverPoints += 25;
                        firstPos += 1;
                    }
                    if (driverRacePositions == 2) {
                        driverPoints += 18;
                        secondPos += 1;

                    }
                    if (driverRacePositions == 3) {
                        driverPoints += 15;
                        thirdPos += 1;

                    }
                    if (driverRacePositions == 4) {
                        driverPoints += 12;

                    }
                    if (driverRacePositions == 5) {
                        driverPoints += 10;

                    }
                    if (driverRacePositions == 6) {
                        driverPoints += 8;

                    }
                    if (driverRacePositions == 7) {
                        driverPoints += 6;

                    }
                    if (driverRacePositions == 8) {
                        driverPoints += 4;

                    }
                    if (driverRacePositions == 9) {
                        driverPoints += 2;

                    }
                    if (driverRacePositions == 10) {
                        driverPoints += 1;

                    }
                }
            }
            newDriverCheck.setDriverPoints(driverPoints);//setting the driver points
            newDriverCheck.setFirstPos(firstPos); //setting the number of first positions of driver
            newDriverCheck.setSecondPos(secondPos); //setting the number of second positions of driver
            newDriverCheck.setThirdPos(thirdPos); //setting the number of third positions of driver

        Formula1Driver newDriver = new Formula1Driver(driverName,driverLocation, driverTeam,
                driverRaceCount, (driverCurrentPoints+driverPoints), driverPoints, firstPos, secondPos,
                thirdPos,newDriverCheck.getDriverRaceDate()); //values are set using the constructor

        driverDataAL.add(newDriver); //adding the entered data to the arraylist

        System.out.println("Driver's Name:" + newDriver.getDriverName() + "||Driver's location:" + newDriver.getDriverLocation() +
                "||Driver's team:" + newDriver.getDriverTeam() + "||Driver's race count:" + newDriver.getDriverRaceCount() +
                "||Driver's current point:" + newDriver.getDriverCurrentPoints() + "||Driver's points:" + newDriver.getDriverPoints() +
                "||First position:" + newDriver.getFirstPos() +
                "||Second position:" + newDriver.getSecondPos() + "||Third position:" + newDriver.getThirdPos()+" ||Date:"+newDriverCheck.getDriverRaceDate());

        viewDrivers(); //viewing the drivers in the arraylist with the driver details
    }

    //checking if the team already exists
    private Boolean checkTeam(String driverTeam) { //use of private methods so that it can only be accessed within the class
        boolean flag = false;
        for (Formula1Driver formula1Driver : driverDataAL){
            if(formula1Driver.getDriverTeam().equals(driverTeam) || driverTeam.equals("")){
                flag = true;
            }
            else{
                flag = false;
            }
        }
        return flag;
    }

    //checking if the name already exists
    private Boolean checkName(String driverName){
        boolean flag = false;
        for (Formula1Driver formula1Driver : driverDataAL){
            if(formula1Driver.getDriverName().equals(driverName) || driverName.equals("")){
                flag = true;
            }
            else{
                flag = false;
            }
        }
        return flag;
    }

    //checking if the positions is within the given range
    private Boolean checkPosition(int driverRacePositions){
        boolean flag;
        if(driverRacePositions <1 || driverRacePositions>10){
                flag=true;
        }
        else{
                flag = false;
        }
        return flag;
    }


    @Override
    public void deleteDriver() { //deleting the driving using the driver index
        System.out.println("--Delete driver--");

        viewDrivers();

        System.out.println("Enter the number of the driver to delete");
        int deleteDriver = input.nextInt(); //getting the index of the driver that needs to be removed

        driverDataAL.remove(deleteDriver); //remove the driver data from the arraylist

        viewDrivers();
    }

    //changing the driver's team
    @Override
    public void changeDriver() {
        System.out.println("---Change driver---");

        for (int i = 0; i < driverDataAL.size(); i++) {
            System.out.println("-------------------" + "Driver number:" + i + "-------------------");
            System.out.println("Driver Name:" + driverDataAL.get(i).getDriverName());
            System.out.println("Driver Team:" + driverDataAL.get(i).getDriverTeam());
            System.out.println("-------------------------------------------------------------");
        }

        System.out.println("Enter the index of the team which need to be switched:");
        int driverChangeNum = input.nextInt(); //getting the index of the driver that needs to be changed

        if(driverChangeNum>=0 && driverChangeNum < driverDataAL.size()){
            System.out.println("Enter the index of the team which need to be changed to:");
            int driverChangeNumTo = input.nextInt(); //getting the index of the replacement

            if(driverChangeNumTo >=0 && driverChangeNumTo < driverDataAL.size()){
                driverDataAL.get(driverChangeNumTo).setDriverName(driverDataAL.get(driverChangeNum).getDriverName()); //passing the data to the new driver team
                driverDataAL.get(driverChangeNumTo).setDriverLocation(driverDataAL.get(driverChangeNum).getDriverLocation());
                driverDataAL.get(driverChangeNumTo).setDriverCurrentPoints(driverDataAL.get(driverChangeNum).getDriverCurrentPoints());
                driverDataAL.get(driverChangeNumTo).setDriverPoints(driverDataAL.get(driverChangeNum).getDriverPoints());
                driverDataAL.get(driverChangeNumTo).setDriverRaceCount(driverDataAL.get(driverChangeNum).getDriverRaceCount());
                driverDataAL.get(driverChangeNumTo).setDriverRacePositions(driverDataAL.get(driverChangeNum).getDriverRacePositions());
                driverDataAL.get(driverChangeNumTo).setFirstPos(driverDataAL.get(driverChangeNum).getFirstPos());
                driverDataAL.get(driverChangeNumTo).setSecondPos(driverDataAL.get(driverChangeNum).getSecondPos());
                driverDataAL.get(driverChangeNumTo).setThirdPos(driverDataAL.get(driverChangeNum).getThirdPos());
                driverDataAL.get(driverChangeNumTo).setDriverRaceDate(driverDataAL.get(driverChangeNum).getDriverRaceDate());

                //printing the driver data with the new changed team
                System.out.println("Changed driver to:"+driverDataAL.get(driverChangeNumTo).getDriverTeam());
                System.out.println("Driver team:"+driverDataAL.get(driverChangeNumTo).getDriverTeam());
                System.out.println("Driver name:"+driverDataAL.get(driverChangeNumTo).getDriverName());
                System.out.println("Driver location:"+driverDataAL.get(driverChangeNumTo).getDriverLocation());
                System.out.println("Driver current points:"+driverDataAL.get(driverChangeNumTo).getDriverCurrentPoints());
                System.out.println("Driver points:"+driverDataAL.get(driverChangeNumTo).getDriverPoints());

                driverDataAL.remove(driverChangeNum); //remove the changed from team because there is no data init
            }
            else{
                System.out.println("Invalid range");
            }
        }
        else{
            System.out.println("Invalid range of driver change requested");
        }
    }

    //displaying all the statistics of the drivers
    @Override
    public void displayStatsForDriver() {
        System.out.println("---Display statistics for driver---");

        for (int i = 0; i < driverDataAL.size(); i++) {
            System.out.println("-------------------" + "Driver number:" + i + "-------------------");
            System.out.println("Driver Name:" + driverDataAL.get(i).getDriverName());
            System.out.println("-------------------------------------------------------------");
        }

        System.out.println("Enter the driver number:");
        int driverNumber = input.nextInt(); //getting the driver's index to display the statistics

        System.out.println("Driver Name:" + driverDataAL.get(driverNumber).getDriverName());
        System.out.println("Driver Location:" + driverDataAL.get(driverNumber).getDriverLocation());
        System.out.println("Driver Team:" + driverDataAL.get(driverNumber).getDriverTeam());
        System.out.println("Driver race count:" + driverDataAL.get(driverNumber).getDriverRaceCount());
        System.out.println("Driver current points:" + driverDataAL.get(driverNumber).getDriverCurrentPoints());
        System.out.println("Driver points:" + driverDataAL.get(driverNumber).getDriverPoints());
        System.out.println("Driver first position:" + driverDataAL.get(driverNumber).getFirstPos());
        System.out.println("Diver second position:" + driverDataAL.get(driverNumber).getSecondPos());
        System.out.println("Driver third position:" + driverDataAL.get(driverNumber).getThirdPos());
        System.out.println("Driver race date:"+driverDataAL.get(driverNumber).getDriverRaceDate());
    }

    //Formula 1 driver table
    @Override
    public void f1DriverTable() {
        System.out.println("---Formula1 Driver table----");

        //sorting the arraylist by the driver's points scored
        Collections.sort(driverDataAL, new PointComparator());  //calling the sort function

        //https://code2care.org/java/display-output-in-java-console-as-a-table
        String [] tableHeader = {"Name","Team","Location","Current points","Points","Race count","First positions","Second positions","Third positions","Race data"}; //setting the headers of the table to a 1D array
        String  [] [] data = new String [driverDataAL.size()][10]; //setting the data of the driver to a 2D array

        for(int i = 0; i < driverDataAL.size();i++){

            data[i][0] = driverDataAL.get(i).getDriverName();
            data[i][1] = driverDataAL.get(i).getDriverTeam();
            data[i][2] = driverDataAL.get(i).getDriverLocation();
            data[i][3] = String.valueOf(driverDataAL.get(i).getDriverCurrentPoints());
            data[i][4] = String.valueOf(driverDataAL.get(i).getDriverPoints());
            data[i][5] = String.valueOf(driverDataAL.get(i).getDriverRaceCount());
            data[i][6] = String.valueOf(driverDataAL.get(i).getFirstPos());
            data[i][7] = String.valueOf(driverDataAL.get(i).getSecondPos());
            data[i][8] = String.valueOf(driverDataAL.get(i).getThirdPos());
            data[i][9] = String.valueOf(driverDataAL.get(i).getDriverRaceDate());

        }
        ASCIITable.getInstance().printTable(tableHeader, data); //format of the table
    }

    //adding a race to the driver
    @Override
    public void addARace() throws ParseException {
        System.out.println("---Add a race---");

        if(driverDataAL.size()>=1){   //to check if there is at least one driver entered
            while(true){
                    System.out.println("Enter 'y' if race/races was completed or any other letter if not completed"); //checking if the driver completed the race
                    String userInput = input.next();
                    if(userInput.equalsIgnoreCase("y")) {
                        System.out.println("------------------------------------");
                        System.out.println("Date:"+newDriverCheck.getDriverRaceDate());
                        if(checkDate()){ //date validation
                        for(int i = 0; i < driverDataAL.size(); i++) {
                            while (true) {
                                System.out.println("Enter the name of the driver:" + driverDataAL.get(i).getDriverName());
                                System.out.println("Enter the name of the team:" + driverDataAL.get(i).getDriverTeam());
                                System.out.println("Enter the driver position:");
                                if (input.hasNextInt()) {
                                    int dateCheckPosition = input.nextInt(); //getting the driver position of the race
                                    if (dateCheckPosition >= 1 && dateCheckPosition <= 10) {
                                        switch (dateCheckPosition) { //update the data of the driver according to the race position
                                            case 1 -> {
                                                driverDataAL.get(i).setDriverPoints(25 + driverDataAL.get(i).getDriverPoints()); //updating the values using setters and getters
                                                driverDataAL.get(i).setDriverCurrentPoints(25 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setFirstPos(1 + driverDataAL.get(i).getFirstPos());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won first place! Congratulations!!" + '\n');
                                            }
                                            case 2 -> {
                                                driverDataAL.get(i).setDriverPoints(18 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(18 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setSecondPos(1 + driverDataAL.get(i).getSecondPos());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won second place! Congratulations!!" + '\n');
                                            }
                                            case 3 -> {
                                                driverDataAL.get(i).setDriverPoints(15 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(15 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setThirdPos(1 + driverDataAL.get(i).getThirdPos());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won third place! Congratulations!!" + '\n');
                                            }
                                            case 4 -> {
                                                driverDataAL.get(i).setDriverPoints(12 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(12 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won fourth place! Congratulations!!" + '\n');
                                            }
                                            case 5 -> {
                                                driverDataAL.get(i).setDriverPoints(10 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(10 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won fifth place! Congratulations!!" + '\n');
                                            }
                                            case 6 -> {
                                                driverDataAL.get(i).setDriverPoints(8 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(8 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won sixth place! Congratulations!!" + '\n');
                                            }
                                            case 7 -> {
                                                driverDataAL.get(i).setDriverPoints(6 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(6 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won seventh place! Congratulations!!" + '\n');
                                            }
                                            case 8 -> {
                                                driverDataAL.get(i).setDriverPoints(4 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(4 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won eighth place! Congratulations!!" + '\n');
                                            }
                                            case 9 -> {
                                                driverDataAL.get(i).setDriverPoints(2 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(2 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won ninth place! Congratulations!!" + '\n');
                                            }
                                            case 10 -> {
                                                driverDataAL.get(i).setDriverPoints(1 + driverDataAL.get(i).getDriverPoints());
                                                driverDataAL.get(i).setDriverCurrentPoints(1 + driverDataAL.get(i).getDriverCurrentPoints());
                                                driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                                                System.out.println("You have won tenth place! Congratulations!!" + '\n');
                                            }
                                            default -> System.out.println("Invalid position entered!" + '\n');
                                        }
                                    } else {
                                        System.out.println("Invalid position");
                                    }
                                }
                                break;
                            }
                        }
                    }else{
                        System.out.println("Driver did not complete the race!");
                        break;
                    }
                }
                break;
            }
        }
    }


    //https://stackoverflow.com/questions/27580655/how-to-set-a-date-as-input-in-java
    private static final Locale defaultFormattingLocale
            = Locale.getDefault(Locale.Category.FORMAT);
    private static final String defaultDateFormat = DateTimeFormatterBuilder
            .getLocalizedDateTimePattern(FormatStyle.SHORT, null,
                    IsoChronology.INSTANCE, defaultFormattingLocale);
    private static final DateTimeFormatter dateFormatter
            = DateTimeFormatter.ofPattern(defaultDateFormat, defaultFormattingLocale);


    //checking the date
    private boolean checkDate() {

        //https://stackoverflow.com/questions/27580655/how-to-set-a-date-as-input-in-java
        LocalDate sampleDate
                = Year.now().minusYears(1).atMonth(Month.NOVEMBER).atDay(26);
        System.out.println("Enter date in " + defaultDateFormat
                + " format, for example " + sampleDate.format(dateFormatter)); //format of the date
        String dateString = input.next();
        try {
            LocalDate inputDate = LocalDate.parse(dateString, dateFormatter);
            System.out.println("Date entered was " + inputDate);
            for(int i=0;i<driverDataAL.size(); i++){
                driverDataAL.get(i).setDriverRaceDate(inputDate);//setting the date of the driver
            }
            newDriverCheck.setDriverRaceDate(inputDate);//setting the date of the driver
            return true;
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid date: " + dateString);
            return false;
        }

    }

    //saving all the entered data to the text file
    @Override
    public void saveData() {
        System.out.println("---Save data to a text file---");

        // https://samderlust.com/dev-blog/java/write-read-arraylist-object-file-java
        try{
            FileOutputStream writeData = new FileOutputStream("DriverDataFile.ser"); //Creates a file output stream to write to the file that we provide inside the parentheses
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);   //ObjectOutputStream will handle the object to be written into the file that FileOutputStream created

            writeStream.writeObject(driverDataAL);  //contents from the driver arraylist is written into the File

            writeStream.flush(); //make sure data is written into the file.
            writeStream.close(); //close the stream after the writing is done

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //loading all data from the file
    @Override
    public void loadData(){
        System.out.println("---Load data from text file---");

        // https://www.javatpoint.com/serialization-in-java
        try{
            FileInputStream readData = new FileInputStream("DriverDataFile.ser");
            ObjectInputStream readStream = new ObjectInputStream(readData); //Creating stream to read the object

            driverDataAL = (ArrayList<Formula1Driver>) readStream.readObject(); //passing the data to the array
            readStream.close();
            System.out.println("File loaded!");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }
    }

    //method that holds the test data
    public void testData(){

        System.out.println("Test data loaded!");

        //test data
        Formula1Driver newDriver2 = new Formula1Driver("Max Verstappen",   "Netherlands",	"Red Bull Racing",  5, 352+118,118,4,1,0,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver3=  new Formula1Driver("Lewis Hamilton",   "United Kingdom","Mercedes",		    5, 344+108,108,3,1,1,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver4 = new Formula1Driver("Lando Norris",     "United Kingdom","McLaren",		    5, 153+101,101,2,2,1,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver5 = new Formula1Driver("Charles Leclerc",  "Monaco",        "Ferrari",		    5, 152+91,91, 1,2,2,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver6 = new Formula1Driver("Pierre Gasly",     "France",        "AlphaTauri",	    5, 92+84, 84, 0,3,2,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver7 = new Formula1Driver("Fernando Alonso",  "Spain",         "Alpine",		    5, 77+87, 87, 0,4,1,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver8 = new Formula1Driver("Sebastian Vettel", "Germany",       "Aston Martin",	    5, 43+66, 66, 0,2,2,newDriverCheck.getDriverRaceDate());
        Formula1Driver newDriver9 = new Formula1Driver("Kimi Räikkönen",   "Finland",       "Alfa Romeo Racing",5, 10+48, 48, 0,0,0,newDriverCheck.getDriverRaceDate());

        //test data added to the arraylist
        driverDataAL.add(newDriver2);
        driverDataAL.add(newDriver3);
        driverDataAL.add(newDriver4);
        driverDataAL.add(newDriver5);
        driverDataAL.add(newDriver6);
        driverDataAL.add(newDriver7);
        driverDataAL.add(newDriver8);
        driverDataAL.add(newDriver9);

    }

    //test data method for the search
    public static ArrayList<Formula1Driver> testDataForSearch() {

        System.out.println("Test data loaded!");

        Formula1Driver newDriver2 = new Formula1Driver("Max Verstappen",   "Netherlands",	"Red Bull Racing",  5, 352+118,118,4,1,0,LocalDate.of(2021,12,9));
        Formula1Driver newDriver3=  new Formula1Driver("Lewis Hamilton",   "United Kingdom","Mercedes",		    5, 344+108,108,3,1,1,LocalDate.of(2019,1,21));
        Formula1Driver newDriver4 = new Formula1Driver("Lando Norris",     "United Kingdom","McLaren",		    5, 153+101,101,2,2,1,LocalDate.of(2020,2,14));
        Formula1Driver newDriver5 = new Formula1Driver("Charles Leclerc",  "Monaco",        "Ferrari",		    5, 152+91,91, 1,2,2,LocalDate.of(2018,7,27));
        Formula1Driver newDriver6 = new Formula1Driver("Pierre Gasly",     "France",        "AlphaTauri",	    5, 92+84, 84, 0,3,2,LocalDate.of(2019,9,19));
        Formula1Driver newDriver7 = new Formula1Driver("Fernando Alonso",  "Spain",         "Alpine",		    5, 77+87, 87, 0,4,1,LocalDate.of(2018,4,2));
        Formula1Driver newDriver8 = new Formula1Driver("Sebastian Vettel", "Germany",       "Aston Martin",	    5, 43+66, 66, 0,2,2,LocalDate.of(2021,10,23));
        Formula1Driver newDriver9 = new Formula1Driver("Kimi Räikkönen",   "Finland",       "Alfa Romeo Racing",5, 10+48, 48, 0,0,0,LocalDate.of(2019,3,29));

        Formula1Driver newDriver10 = new Formula1Driver("Max Verstappen",   "Netherlands",	"Red Bull Racing",  5, 370+108,108,3,1,1,LocalDate.of(2021,10,12));
        Formula1Driver newDriver11 =  new Formula1Driver("Lewis Hamilton",   "United Kingdom","Mercedes",		    5, 370+111,111,3,2,0,LocalDate.of(2018,6,14));
        Formula1Driver newDriver12 = new Formula1Driver("Lando Norris",     "United Kingdom","McLaren",		    5, 154+104,104,2,3,0,LocalDate.of(2019,8,16));
        Formula1Driver newDriver13 = new Formula1Driver("Charles Leclerc",  "Monaco",        "Ferrari",		    5, 158+88,88, 1,1,3,LocalDate.of(2020,10,17));
        Formula1Driver newDriver14 = new Formula1Driver("Pierre Gasly",     "France",        "AlphaTauri",	    5, 100+81, 81, 0,2,3,LocalDate.of(2018,12,20));
        Formula1Driver newDriver15 = new Formula1Driver("Fernando Alonso",  "Spain",         "Alpine",		    5, 77+84, 84, 0,3,2,LocalDate.of(2019,2,21));
        Formula1Driver newDriver16 = new Formula1Driver("Sebastian Vettel", "Germany",       "Aston Martin",	    5, 43+78, 78, 0,1,4,LocalDate.of(2020,1,5));
        Formula1Driver newDriver17 = new Formula1Driver("Kimi Räikkönen",   "Finland",       "Alfa Romeo Racing",5, 10+56, 56, 0,0,0,LocalDate.of(2021,3,3));

        testDriverDataAL.add(newDriver2);
        testDriverDataAL.add(newDriver3);
        testDriverDataAL.add(newDriver4);
        testDriverDataAL.add(newDriver5);
        testDriverDataAL.add(newDriver6);
        testDriverDataAL.add(newDriver7);
        testDriverDataAL.add(newDriver8);
        testDriverDataAL.add(newDriver9);
        testDriverDataAL.add(newDriver10);
        testDriverDataAL.add(newDriver11);
        testDriverDataAL.add(newDriver12);
        testDriverDataAL.add(newDriver13);
        testDriverDataAL.add(newDriver14);
        testDriverDataAL.add(newDriver15);
        testDriverDataAL.add(newDriver16);
        testDriverDataAL.add(newDriver17);

        return testDriverDataAL;
    }

    //method to return the arraylist
    public static ArrayList<Formula1Driver> returnData() {
        return driverDataAL;
    }

}