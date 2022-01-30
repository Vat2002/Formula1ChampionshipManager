import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class WinProbability extends JFrame{
    JLabel label1;
    JTable winProbTable;
    JScrollPane winProbScrollPane;
    public ArrayList<Formula1Driver> driverDataAL = Formula1ChampionshipManager.returnData(); //getting access from the championship manager class


    WinProbability(){

        label1 = new JLabel("Win Probability");
        winProbTable = new JTable();
        winProbScrollPane = new JScrollPane(winProbTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setSize(1100,700); //setting the size of the frame
        setResizable(false);
        setTitle("Formula 1 Championship");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        RandomStartNumbers();
        RandomFinishNumbers();
        winProbabilityValues();
        updatePoints();

        String[] columnsName = {"Name","Team","Location","Number of Races","Driver Points","Current Points","Starting Pos","Win Probability(%)","Finish Position","Date"}; //setting the column names for the table
        winProbTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{},columnsName)); //setting the table model
        insertDataToTable(driverDataAL,winProbTable);

        add(label1);
        setLayout(new FlowLayout()); //setting the layout for the buttons(alignment)
        add(winProbScrollPane).setPreferredSize(new Dimension(1000,500));
    }

    //method to insert the data in the arraylist to the table
    public void insertDataToTable(ArrayList<Formula1Driver> driverDataALGui,JTable driverTableGui){ //https://www.tutorialspoint.com/how-to-add-a-new-row-to-jtable-with-insertrow-in-java-swing
        for(int i=0;i<driverDataALGui.size();i++){                                                  //https://www.roseindia.net/java/example/java/swing/InsertRows.shtml
            ((DefaultTableModel)driverTableGui.getModel()).addRow(new Object[]{
                    driverDataALGui.get(i).getDriverName(),
                    driverDataALGui.get(i).getDriverTeam(),
                    driverDataALGui.get(i).getDriverLocation(),
                    driverDataALGui.get(i).getDriverRaceCount(),
                    driverDataALGui.get(i).getDriverPoints(),
                    driverDataALGui.get(i).getDriverCurrentPoints(),
                    driverDataALGui.get(i).getStartingPos(),
                    driverDataALGui.get(i).getWinProbability(),
                    driverDataALGui.get(i).getFinishPos(),
                    driverDataALGui.get(i).getDriverRaceDate()
            });
        }
    }

    public void RandomStartNumbers(){ //getting the randomized numbers of the starting positions
        Integer[] newArr={1,2,3,4,5,6,7,8,9,10};
        Collections.shuffle(Arrays.asList(newArr));
        for(int i=0; i<driverDataAL.size(); i++){
            driverDataAL.get(i).setStartingPos(newArr[i]);
        }
    }

    public void RandomFinishNumbers(){ //getting the randomized numbers of the finishing positions
        Boolean flag = true;
        Integer[] newArr={1,2,3,4,5,6,7,8,9,10};
        Collections.shuffle(Arrays.asList(newArr));

        for(int i=0; i<driverDataAL.size(); i++){
            driverDataAL.get(i).setFinishPos(newArr[i]);
        }

        while(flag){ //the number 10 starting cannot be number 1 check
            for(int i=0; i<driverDataAL.size(); i++){
                if(driverDataAL.get(i).getStartingPos() == 10){
                    if(driverDataAL.get(i).getFinishPos() == 1){
                        RandomFinishNumbers();
                    }
                    else{
                        flag = false;
                    }
                }
            }

        }

    }

    public void winProbabilityValues(){ //setting the driver winning probability

        int min = 1;
        int max = 10;

        Random newRandom = new Random();

        int minDay = (int) LocalDate.of(2018,1,1).toEpochDay();  //https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
        int maxDay = (int) LocalDate.of(2021,12,1).toEpochDay();
        long randomDay = minDay +newRandom.nextInt(maxDay-minDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);


        for(int i=0; i < driverDataAL.size(); i++) {
            driverDataAL.get(i).setDriverRaceDate(randomDate);
            switch (driverDataAL.get(i).getStartingPos()) {
                case 1 -> {
                    driverDataAL.get(i).setWinProbability(40);
                }
                case 2 -> {
                    driverDataAL.get(i).setWinProbability(30);
                }
                case 3, 4 -> {
                    driverDataAL.get(i).setWinProbability(10);
                }
                case 5, 6, 7, 8, 9 -> {
                    driverDataAL.get(i).setWinProbability(2);
                }
                case 10 -> {
                    driverDataAL.get(i).setWinProbability(0);
                }
            }
        }
    }

    public void updatePoints(){ //updating the driver points

        for(int i = 0; i <driverDataAL.size(); i++){
            switch (driverDataAL.get(i).getFinishPos()) {
                case 1 -> {
                    driverDataAL.get(i).setDriverPoints(25 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(25 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setFirstPos(1 + driverDataAL.get(i).getFirstPos());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 2 -> {
                    driverDataAL.get(i).setDriverPoints(18 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(18 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setSecondPos(1 + driverDataAL.get(i).getSecondPos());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 3 -> {
                    driverDataAL.get(i).setDriverPoints(15 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(15 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setThirdPos(1 + driverDataAL.get(i).getThirdPos());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 4 -> {
                    driverDataAL.get(i).setDriverPoints(12 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(12 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 5 -> {
                    driverDataAL.get(i).setDriverPoints(10 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(10 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 6 -> {
                    driverDataAL.get(i).setDriverPoints(8 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(8 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 7 -> {
                    driverDataAL.get(i).setDriverPoints(6 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(6 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 8 -> {
                    driverDataAL.get(i).setDriverPoints(4 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(4 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 9 -> {
                    driverDataAL.get(i).setDriverPoints(2 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(2 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
                case 10 -> {
                    driverDataAL.get(i).setDriverPoints(1 + driverDataAL.get(i).getDriverPoints());
                    driverDataAL.get(i).setDriverCurrentPoints(1 + driverDataAL.get(i).getDriverCurrentPoints());
                    driverDataAL.get(i).setDriverRaceCount(1 + driverDataAL.get(i).getDriverRaceCount());
                }
            }
        }
    }

}