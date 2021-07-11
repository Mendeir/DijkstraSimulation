
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
    int [][] graphValues;


    // Instantiation of Objects for GUI
    JFrame frame = new JFrame();
    JComboBox matrixSize = new JComboBox(sizes);
    JPanel panelMenu = new JPanel();
    JPanel panelMatrix = new JPanel();
    JPanel panelPath = new JPanel();
    JLabel displayValues = new JLabel();
    JLabel labelPath = new JLabel();
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
        frame.setBounds(200,20,1000,700);
        frame.setLayout(null);

        // Formats for display outputs
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Panel bounds and formats
        panelMenu.setBounds(0,0,1000,100);
        panelMenu.setLayout(null);
        panelMenu.setBackground(Color.RED);

        panelMatrix.setBounds(0,100,800,600);
        panelMatrix.setBackground(Color.BLUE);
        panelMatrix.setLayout(new GridBagLayout());

        panelPath.setBounds(800,100,200,600);
        panelPath.setLayout(null);
        panelPath.setBackground(Color.yellow);

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

            graphValues = new int[row][column];

            for(int i=0;i<row;i++){
                for(int j=0;j<column;j++){
                    graphValues[i][j] =Integer.parseInt(matrixValues[i][j].getText());
                }
            }

            logic.dijkstraProcess(graphValues);
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
                gbc.gridx = j;
                gbc.gridy = i;
                matrixValues[i][j].setFont(new Font("Arial", Font.PLAIN, 15));
                panelMatrix.add(matrixValues[i][j], gbc);
                matrixValues[i][j].setHorizontalAlignment(JTextField.CENTER);
            }
        }
    }
}
