import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;

public class Formula1ManagerGUI  extends JFrame implements ActionListener {
    JTable driverTableGui;      //declaring the table for the GUI
    JLabel guiLabel1,guiLabel2; //declaring the labels for the GUI
    JScrollPane driverScrollPane; //declaring the scroll pane for the GUI
    JButton descendingOrderOfPoints; //declaring the button for descending order of points
    JButton ascendingOrderOfPoints;; //declaring the button for ascending order of points
    JButton descendingLargestNumberOfFirstPos; //declaring the button for the descending order of first places
    JButton randomRace; //declaring the button for the random race generated
    JButton raceSortedByDateAscending; //declaring the button for the ascending order of race date
    JButton searchDriverButton;
    JButton winProbabilityButton;
    public ArrayList<Formula1Driver> driverDataAL = Formula1ChampionshipManager.returnData(); //getting access from the championship manager class
    
    Formula1ManagerGUI() {
        driverTableGui = new JTable();
        driverScrollPane = new JScrollPane(driverTableGui,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //creating a scroll bar
        guiLabel1 = new JLabel("Formula 1 Championship Table"); //setting names for the label
        guiLabel2 = new JLabel();
        descendingOrderOfPoints = new JButton("Descending Order Of Points");
        ascendingOrderOfPoints = new JButton("Ascending Order Of Points");
        descendingLargestNumberOfFirstPos = new JButton( "Descending Largest Number Of First Positions" );
        randomRace = new JButton("Generate Random Race");
        raceSortedByDateAscending = new JButton("Sort Race date in Ascending order");
        winProbabilityButton = new JButton("Win probability");
        searchDriverButton = new JButton("Search Driver");

        setSize(1100,700); //setting the size of the frame
        setResizable(false);
        setTitle("Formula 1 Championship");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnsName = {"Name","Team","Location","Number of Races","Driver Points","Current Points","First Position","Second Position","Third Position","Date"}; //setting the table headings for the table using a 1D array
        driverTableGui.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{},columnsName)); //setting the table model
        insertDataToTable(driverDataAL,driverTableGui);                                       //inserting the data into the table

        add(guiLabel1);
        add(guiLabel2);
        setLayout(new FlowLayout()); //setting the layout for the buttons(alignment)
        add(driverScrollPane).setPreferredSize(new Dimension(1000,500));
        add(descendingOrderOfPoints);//adding the buttons to the frame
        add(ascendingOrderOfPoints);
        add(descendingLargestNumberOfFirstPos);
        add(randomRace);
        add(raceSortedByDateAscending);
        add(winProbabilityButton);
        add(searchDriverButton);

        //calls the method using action listener when each button is pressed
        descendingOrderOfPoints.addActionListener(this);
        ascendingOrderOfPoints.addActionListener(this);
        descendingLargestNumberOfFirstPos.addActionListener(this);
        randomRace.addActionListener(this);
        raceSortedByDateAscending.addActionListener(this);
        searchDriverButton.addActionListener(this);
        winProbabilityButton.addActionListener(this);
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
                    driverDataALGui.get(i).getFirstPos(),
                    driverDataALGui.get(i).getSecondPos(),
                    driverDataALGui.get(i).getThirdPos(),
                    driverDataALGui.get(i).getDriverRaceDate()
            });
        }
    }

    public void removeRowsFromTable(){ // remove rows from table https://www.codegrepper.com/code-examples/whatever/java+swing+jtable+remove+all+rows
        DefaultTableModel newModel = (DefaultTableModel)driverTableGui.getModel();

        int tableRowCount = newModel.getRowCount();

        for(int i = tableRowCount -1; i>=0 ;i--){
            newModel.removeRow(i);
        }
    }

    public void sortingDriverStatisticsInDescending(){
        removeRowsFromTable(); //resetting the table rows
        Collections.sort(driverDataAL, new PointComparator()); //sorting using the comparator
        guiLabel2.setText('\n'+"Sorted in descending order of driver current points");
        insertDataToTable(driverDataAL,driverTableGui);//updating the table
    }

    public void sortingDriverStatisticsInAscending(){
        removeRowsFromTable();//resetting the table rows
        Collections.sort(driverDataAL, new PointComparatorAscending());//sorting using the comparator
        guiLabel2.setText('\n'+"Sorted in ascending order of driver current points");
        insertDataToTable(driverDataAL,driverTableGui);//updating the table
    }

    public void sortingDriverFirstPosInDescending(){
        removeRowsFromTable();//resetting the table rows
        Collections.sort(driverDataAL, new PointComparatorDescendingFirstPositions());//sorting using the comparator
        guiLabel2.setText('\n'+"Sorted in descending order of driver first positions");
        insertDataToTable(driverDataAL,driverTableGui);//updating the table
    }

    public void generateRandomRaceWithDates() {

        removeRowsFromTable();//resetting the table rows

        int min = 1;
        int max = 10;

        Random newRandom = new Random();

        if (driverDataAL.size() > 1) {   //to check if there is at least one driver entered
            for (int i = 0; i < driverDataAL.size(); i++) {
                int dateCheckPosition = (int) (Math.random()*(max-min)) + min;
                int minDay = (int) LocalDate.of(2018,1,1).toEpochDay();  //https://stackoverflow.com/questions/3985392/generate-random-date-of-birth
                int maxDay = (int) LocalDate.of(2021,12,1).toEpochDay();
                long randomDay = minDay +newRandom.nextInt(maxDay-minDay);

                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
                //Date dateNew = java.sql.Date.valueOf(randomDate); //https://stackoverflow.com/questions/22929237/convert-java-time-localdate-into-java-util-date-type
                driverDataAL.get(i).setDriverRaceDate(randomDate);

                switch (dateCheckPosition) { //updating the values for the position obtained by the driver
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
        guiLabel2.setText('\n'+"Generated a random race");
        insertDataToTable(driverDataAL,driverTableGui);//updating the table
    }

    public void sortRaceByRandomDates(){
        removeRowsFromTable();//resetting the table rows
        Collections.sort(driverDataAL, new PointComparatorAscendingRaceRandomDate());//sorting using the comparator
        guiLabel2.setText('\n'+"Sorted in ascending order of race date");
        insertDataToTable(driverDataAL,driverTableGui);//updating the table
    }

    @Override
    public void actionPerformed(ActionEvent e) { // https://youtu.be/94tt_YfxzVA
        if(e.getSource().equals(descendingOrderOfPoints)){
            sortingDriverStatisticsInDescending();//calling the function for the specific button
        }
        else if(e.getSource().equals(ascendingOrderOfPoints)){
            sortingDriverStatisticsInAscending();
        }
        else if(e.getSource().equals(descendingLargestNumberOfFirstPos)){
            sortingDriverFirstPosInDescending();
        }
        else if(e.getSource().equals(randomRace)){
            generateRandomRaceWithDates();
        }
        else if(e.getSource().equals(raceSortedByDateAscending)){
            sortRaceByRandomDates();
        }
        else if(e.getSource().equals(winProbabilityButton)){
            new WinProbability();
        }
        else if(e.getSource().equals(searchDriverButton)){
            new SearchDriver();
        }
    }
}