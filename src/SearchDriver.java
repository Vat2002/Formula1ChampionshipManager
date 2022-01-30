import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class SearchDriver extends JFrame implements ActionListener{

    //public ArrayList<Formula1Driver> driverDataAL = Formula1ChampionshipManager.returnData(); //getting access from the championship manager class
    public ArrayList<Formula1Driver> testDriverDataAL = Formula1ChampionshipManager.testDataForSearch();

    JLabel searchLabel;
    JTextField searchField;
    JButton searchDriverButton;
    JTable searchDriverTable;
    JScrollPane searchDriverScrollPane;
    JPanel container;

    public SearchDriver(){
        searchLabel = new JLabel("Search Driver");
        searchField = new JTextField(30);
        searchDriverButton = new JButton("Search Driver");
        searchDriverTable = new JTable();
        searchDriverScrollPane = new JScrollPane(searchDriverTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container = new JPanel();

        setSize(1100,700); //setting the size of the frame
        setResizable(false);
        setTitle("Formula 1 Championship");
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnsName = {"Name","Team","Location","Number of Races","Driver Points","Current Points","First Position","Second Position","Third Position","Date"}; //setting the column names for the table
        searchDriverTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{},columnsName)); //setting the table model
        insertDataToTable(testDriverDataAL,searchDriverTable);

        add(searchLabel);
        setLayout(new FlowLayout()); //setting the layout for the buttons(alignment)
        add(searchDriverScrollPane).setPreferredSize(new Dimension(1000,500));
        add(searchField);
        add(searchDriverButton);

        //calls the method using action listener when each button is pressed
        searchDriverButton.addActionListener(this);

    }

    //method to insert the data in the arraylist to the table
    public void insertDataToTable(ArrayList<Formula1Driver> driverDataALGui,JTable driverTableGui){
        String getUserDriverInput = searchField.getText().toUpperCase(); //getting the text entered from the text field
        for(int i=0;i<driverDataALGui.size();i++){
            if(getUserDriverInput.equalsIgnoreCase((testDriverDataAL.get(i).getDriverName()))){ //checking if it's in the data stores in the arraylist
                ((DefaultTableModel)driverTableGui.getModel()).addRow(new Object[]{
                        testDriverDataAL.get(i).getDriverName(),
                        testDriverDataAL.get(i).getDriverTeam(),
                        testDriverDataAL.get(i).getDriverLocation(),
                        testDriverDataAL.get(i).getDriverRaceCount(),
                        testDriverDataAL.get(i).getDriverPoints(),
                        testDriverDataAL.get(i).getDriverCurrentPoints(),
                        testDriverDataAL.get(i).getFirstPos(),
                        testDriverDataAL.get(i).getSecondPos(),
                        testDriverDataAL.get(i).getThirdPos(),
                        testDriverDataAL.get(i).getDriverRaceDate()
                });
            }
        }
    }

    public void removeRowsFromTable(){ // remove rows from table https://www.codegrepper.com/code-examples/whatever/java+swing+jtable+remove+all+rows
        DefaultTableModel newModel = (DefaultTableModel)searchDriverTable.getModel();
        int tableRowCount = newModel.getRowCount();
        for(int i = tableRowCount -1; i>=0 ;i--){
            newModel.removeRow(i);
        }
    }

    public void sortingDriverStatisticsInDescending(){
        removeRowsFromTable(); //resetting the table rows
        Collections.sort(testDriverDataAL, new PointComparator());//sorting using the comparator
        insertDataToTable(testDriverDataAL,searchDriverTable);//updating the table
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(searchDriverButton)){
            sortingDriverStatisticsInDescending();
        }
    }
}
