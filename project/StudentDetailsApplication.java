import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class StudentDetailsApplication extends JFrame implements ActionListener 
{

    private JTextField nameField, idField, roadField, postcodeField, districtField;
    private JButton addButton, updateButton, deleteButton, nextButton, prevButton, firstButton, lastButton, sortButton;
    private JLabel displayArea;
    private JPanel inputPanel = new JPanel(new GridLayout(6, 2)),buttonPanel;

    

    private ArrayList<String> studentDetails;
    private int currentIndex=0;
    
    public StudentDetailsApplication() 
    {   
        setTitle("STUDENTS' DETAILS");
        ImageIcon image= new ImageIcon("C:\\Users\\HP\\Pictures\\Screenshots\\Screenshot 2024-04-20 023806.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 230);
        setResizable(false);


        nameField = new JTextField();
        idField = new JTextField();
        roadField = new JTextField();
        postcodeField = new JTextField();
        districtField = new JTextField();
        addButton = new JButton("ADD");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");
        nextButton = new JButton(">|");
        prevButton = new JButton("|<");
        firstButton = new JButton("<<");
        lastButton = new JButton(">>");
        sortButton = new JButton("SORT");
        displayArea = new JLabel();
        buttonPanel = new JPanel(new GridLayout(2, 4));

        updateButton.setBackground(new Color(135, 206, 250)); 
        nextButton.setBackground(Color.YELLOW); 
        prevButton.setBackground(Color.YELLOW); 
        firstButton.setBackground(Color.YELLOW);
        lastButton.setBackground(Color.YELLOW); 
        sortButton.setBackground(Color.CYAN); 
        deleteButton.setBackground(Color.RED);
        addButton.setBackground(Color.GREEN);


        
        setLayout(new BorderLayout());
        inputPanel.add(new JLabel("NAME:")).setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("ID:")).setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("ADDRESS:")).setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel("           ROAD:"));
        inputPanel.add(roadField);
        inputPanel.add(new JLabel("           POSTCODE:"));
        inputPanel.add(postcodeField);
        inputPanel.add(new JLabel("           DISTRICT:"));
        inputPanel.add(districtField);


        add(inputPanel, BorderLayout.NORTH);
        add(displayArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        
        buttonPanel.add(firstButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(lastButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(deleteButton);
        

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        nextButton.addActionListener(this);
        prevButton.addActionListener(this);
        firstButton.addActionListener(this);
        lastButton.addActionListener(this);
        sortButton.addActionListener(this);


        studentDetails = new ArrayList<>();
        currentIndex = 0;

        loadDataFromFile();
        displayCurrentStudent();
        setLocationRelativeTo(null);
        setVisible(true);
        
        
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == addButton) 
        {
            addStudent();
        } 
        else if (e.getSource() == updateButton) 
        {
            updateStudent();
        } 
        else if (e.getSource() == deleteButton) 
        {
            deleteStudent();
        } 
        else if (e.getSource() == nextButton) 
        {
            nextStudent();
        } 
        else if (e.getSource() == prevButton) 
        {
            prevStudent();
        } 
        else if (e.getSource() == firstButton) 
        {
            firstStudent();
        } 
        else if (e.getSource() == lastButton) 
        {
            lastStudent();
        } 
        else if (e.getSource() == sortButton) 
        {
            sortStudents();
        }
    }
    private void addStudent() 
    {
        String student = nameField.getText() + "," + idField.getText() + "," + roadField.getText() + "," + postcodeField.getText() + "," + districtField.getText();
        studentDetails.add(student);
        currentIndex = studentDetails.size() - 1;
        displayCurrentStudent();
        saveDataToFile();

    }

    private void updateStudent() 
    {
        if (currentIndex >= 0 && currentIndex < studentDetails.size()) {
            String student = nameField.getText() + "," + idField.getText() + "," + roadField.getText() + "," + postcodeField.getText() + "," + districtField.getText();
            studentDetails.set(currentIndex, student);
            displayCurrentStudent();
            saveDataToFile();

        }
    }

    private void deleteStudent() 
    {
        if (currentIndex >= 0 && currentIndex < studentDetails.size()) 
        {
            studentDetails.remove(currentIndex);
            if (currentIndex >= studentDetails.size()) 
            {
                currentIndex--;
            }
            displayCurrentStudent();
            saveDataToFile();
        }
    }

    private void nextStudent() 
    {
        if (currentIndex < studentDetails.size() - 1) 
        {
            currentIndex++;
            displayCurrentStudent();
        }
    }

    private void prevStudent() 
    {
        if (currentIndex > 0) 
        {
            currentIndex--;
            displayCurrentStudent();
        }
    }

    private void firstStudent() 
    {
        currentIndex = 0;
        displayCurrentStudent();
    }

    private void lastStudent() 
    {
        currentIndex = studentDetails.size() - 1;
        displayCurrentStudent();
    }

    private void sortStudents() 
    {
        Collections.sort(studentDetails, new Comparator<String>() 
        {
            @Override
            public int compare(String s1, String s2) 
            {
                String[] student1 = s1.split(",");
                String[] student2 = s2.split(",");
                
                int id1 = Integer.parseInt(student1[1]); 
                int id2 = Integer.parseInt(student2[1]);
                
                return Integer.compare(id1, id2);
            }   
        });
        currentIndex = 0;
        displayCurrentStudent();
        saveDataToFile();
    }

    // private void sortStudents() {
    //     // Sort the list of student details by student ID
    //     Collections.sort(studentDetails, new Comparator<String>() {
    //         @Override
    //         public int compare(String s1, String s2) {
    //             // Split each student detail string into an array
    //             String[] student1 = s1.split(",");
    //             String[] student2 = s2.split(",");
                
    //             // Extract the student IDs from the arrays
    //             int id1 = Integer.parseInt(student1[1]);
    //             int id2 = Integer.parseInt(student2[1]);
                
    //             // Compare the student IDs and return the result
    //             return Integer.compare(id1, id2);
    //         }
    //     });
        
    //     // Reset the current index to the first student detail
    //     currentIndex = 0;
        
    //     // Update the display with the details of the first student in the sorted list
    //     displayCurrentStudent();
    // }
    
    

    private void displayCurrentStudent() 
    {
        if (studentDetails.size() > 0 && currentIndex >= 0 && currentIndex < studentDetails.size()) 
        {
            String[] student = studentDetails.get(currentIndex).split(",");
            nameField.setText(student[0]);
            idField.setText(student[1]);
            roadField.setText(student[2]);
            postcodeField.setText(student[3]);
            districtField.setText(student[4]);
            displayArea.setText("                                Current Student: " + (currentIndex + 1) + " / Total Students: " + studentDetails.size());
            displayArea.setBackground(new Color(144, 238, 144)); 
            displayArea.setOpaque(true);
        } 
        else 
        {
            nameField.setText("");
            idField.setText("");
            roadField.setText("");
            postcodeField.setText("");
            districtField.setText("");
            displayArea.setBackground(new Color(230, 102, 102)); 
            displayArea.setText("                         NO STUDENT RECORDS AVAILABLE TILL NOW");
            displayArea.setOpaque(true);
        }
    }
     private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("student_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                studentDetails.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("student_details.txt"))) {
            for (String student : studentDetails) {
                writer.write(student + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) 
    {
        new StudentDetailsApplication();
    }
}

