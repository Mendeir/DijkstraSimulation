
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Input_Window extends JFrame implements ActionListener{

    // Instantiate the Logic class for the algorithm process
    Logic logic = new Logic();

    // Instance Variables
    String [] sizes = {"3x3","4x4","5x5","6x6"};
    String [] paths = {"A","B","C","D","E","F"};
    int row = 0;
    int column = 0;
    public static int arrayCounter = 0;
    int [][] graphValues;
    int [][] displayDistanceCollection;
    int [][][] displayPathCollection;


    // Instantiation of Objects for GUI
    JFrame frame = new JFrame();
    JComboBox matrixSize = new JComboBox(sizes);
    JPanel panelMenu = new JPanel();
    JPanel panelMatrix = new JPanel();
    JPanel panelPath = new JPanel();
    JLabel [][] displayValues;
    JLabel labelPathRow;
    JLabel labelPathColumn;
    JButton randomButton = new JButton("Random");
    JButton enterButton = new JButton("Enter");
    JButton left = new JButton("<");
    JButton right = new JButton(">");
    JTextField [][] matrixValues;
    GridBagConstraints gbc = new GridBagConstraints();


    // Constructor
    Input_Window(){
        // Frame sizes and formats
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,20,800,700);
        frame.setLayout(null);

        // Formats for display outputs
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Panel bounds and formats
        panelMenu.setBounds(0,0,800,100);
        panelMenu.setLayout(null);
        panelMatrix.setBounds(0,100,600,600);
        panelMatrix.setLayout(new GridBagLayout());
        panelMatrix.setBackground(Color.WHITE);
        panelPath.setBounds(600,100,200,600);
        panelPath.setLayout(null);
        panelPath.setBackground(Color.WHITE);

        // ComboBox design and contents
        matrixSize.setBounds(100,30,80,30);
        matrixSize.addActionListener(this);
        panelMenu.add(matrixSize);

        // Buttons bounds and sizes
        randomButton.setBounds(200,30,100,30);
        randomButton.addActionListener(this);
        panelMenu.add(randomButton);
        enterButton.setBounds(350,30,100,30);
        enterButton.addActionListener(this);
        panelMenu.add(enterButton);
        left.setBounds(500,30,50,30);
        left.addActionListener(this);
        panelMenu.add(left);
        right.setBounds(590,30,50,30);
        right.addActionListener(this);
        panelMenu.add(right);

        // Visibility and adds
        frame.add(panelMenu);
        frame.add(panelMatrix);
        frame.add(panelPath);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Action for Combo Box
        if(e.getSource() == matrixSize){

            // Get user's selected size
            String size = (String) matrixSize.getSelectedItem();

            String [] s = size.split("x");
            row = Integer.parseInt(s[0]);
            column = Integer.parseInt(s[1]);

            displayTextFields(row,column);
        }

        // Action for random button
        if(e.getSource() == randomButton){

            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    matrixValues[i][j].setText(String.format("%d", (int) (Math.random() * 15)));
                }
            }
        }

        // Enter button action
        if(e.getSource() == enterButton){
            try {
                graphValues = new int[row][column];

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        graphValues[i][j] = Integer.parseInt(matrixValues[i][j].getText());
                    }
                }

                displayEnteredValues(graphValues);
                logic.dijkstraProcess(graphValues);

                displayDistanceCollection = logic.getDistancesCollection();
                displayPathCollection = logic.getPathCollection();
            }catch (NumberFormatException er){
                ErrorWindow error = new ErrorWindow();
            }
        }

        // Action for next button
        if(e.getSource() == right){
            panelMatrix.removeAll();
            panelMatrix.revalidate();
            panelPath.removeAll();
            panelPath.revalidate();
            frame.repaint();


            if(arrayCounter != graphValues.length)
                arrayCounter++;
            if (arrayCounter < graphValues.length) {
                displayPathProcess(arrayCounter);
                displayProcess(graphValues,arrayCounter);

            }else {

            }
        }

        // Action for previous button
        if(e.getSource() == left){
            panelMatrix.removeAll();
            panelMatrix.revalidate();
            panelPath.removeAll();
            panelPath.revalidate();
            frame.repaint();

            if(arrayCounter != 0)
                arrayCounter--;

            displayProcess(graphValues,arrayCounter);
            displayPathProcess(arrayCounter);
        }

    }

    // Method for JText Field user's inputs
    public void displayTextFields(int x, int y){
        matrixValues = new JTextField[x][y];
        panelMatrix.removeAll();
        panelMatrix.revalidate();
        frame.repaint();

        for(int i=0;i<x;i++){
            for(int j=0;j<y;j++){
                matrixValues[i][j] = new JTextField(3);
                gbc.gridx = i;
                gbc.gridy = j;
                matrixValues[i][j].setFont(new Font("Arial", Font.PLAIN, 15));
                panelMatrix.add(matrixValues[i][j], gbc);
                matrixValues[i][j].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }

    // Method for displaying user's inputs
    public void displayEnteredValues(int [][] val){
        displayValues = new JLabel[row][column];
        panelMatrix.removeAll();
        panelMatrix.revalidate();
        panelMatrix.setLayout(null);
        frame.repaint();

        for (int i = 1; i <= row ; i++) {
            for (int j = 1; j <= column ; j++) {

                displayValues[i-1][j-1] = new JLabel(Integer.toString(val[i-1][j-1]),JLabel.CENTER);
                labelPathRow = new JLabel(paths[j-1],JLabel.CENTER);
                labelPathColumn = new JLabel(paths[i-1],JLabel.CENTER);
                displayValues[i-1][j-1].setBounds(i*60,j*60,60,60);
                labelPathRow.setBounds(0,j*60,60,60);
                labelPathColumn.setBounds(i*60,0,60,60);
                displayValues[i-1][j-1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                displayValues[i-1][j-1].setOpaque(true);
                panelMatrix.add(displayValues[i-1][j-1]);
                panelMatrix.add(labelPathRow);
                panelMatrix.add(labelPathColumn);

            }
        }
    }

    // Method for displaying each process
    public void displayProcess(int [][] val, int counter){
        panelMatrix.removeAll();
        panelMatrix.revalidate();
        frame.repaint();

        for (int i = 1; i <= row ; i++) {
            for (int j = 1; j <= column ; j++) {
                labelPathRow = new JLabel(paths[j-1],JLabel.CENTER);
                labelPathColumn = new JLabel(paths[i-1],JLabel.CENTER);
                labelPathRow.setBounds(0,j*60,60,60);
                labelPathColumn.setBounds(i*60,0,60,60);

                displayValues[i-1][j-1] = new JLabel(Integer.toString(val[i-1][j-1]),JLabel.CENTER);
                displayValues[i-1][j-1].setBounds(i*60,j*60,60,60);

                if (displayPathCollection[4][0][j] != -1) {
                    System.out.println("i: " + i);
                    if(i == displayPathCollection[4][0][j]) {
                        if (j == displayPathCollection[4][1][j]) {
                            System.out.println("Called");
                            System.out.println("i: " + i);
                            System.out.println("j: " + j);
                            displayValues[i-1][j-1].setBackground(Color.RED);
                        }
                    }

                }

                displayValues[i-1][j-1].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                displayValues[i-1][j-1].setOpaque(true);


                panelMatrix.add(displayValues[i-1][j-1]);
                panelMatrix.add(labelPathRow);
                panelMatrix.add(labelPathColumn);

            }
        }
    }

    // Method for displaying the path process
    public void displayPathProcess(int counter){
        panelPath.removeAll();
        panelPath.revalidate();
        frame.repaint();

        for(int j=0;j<column;j++){
            if(displayDistanceCollection[counter][j] == Integer.MAX_VALUE)
                labelPathRow = new JLabel(paths[j] + ": " + "INF",JLabel.CENTER);
            else
                labelPathRow = new JLabel(paths[j] + ": " + String.valueOf(displayDistanceCollection[counter][j]),JLabel.CENTER);

            labelPathRow.setBounds(0,j*50,90,90);
            panelPath.add(labelPathRow);
        }

    }
}
