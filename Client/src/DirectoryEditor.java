

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class DirectoryEditor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static DirectoryProxy dirp = new DirectoryProxy();
	private static List<Employee> emp = new ArrayList<Employee>();
	
	private int textSize = 20;
	
	private int currentIndex = 0;
	private String gender;
	
	private JFrame frame;
    
    private JPanel employeeTextPanel;
    private JPanel employeeOptionPanel;
    private JPanel employeeTitlePanel;
    private JPanel employeeGenderPanel;
    private JPanel controlPanel; // Controls for navigating through employees
    
    // Text for field labels
    private String fnameText = "Employee First Name "; // Text
    private String lnameText = "Employee Last Name "; // Text
    private String departmentText = "Employee Department "; // Text
    private String phoneText = "Employee Phone Number "; // Text
    private String genderText = "Employee Gender "; // Radio button - Male, Female, Other - need default 
    private String titleText = "Employee Title "; // List - Mr. Ms. Mrs. Dr. Col. Prof.
    
    private JLabel headerLabel;
    private JLabel fnameLabel;
    private JLabel lnameLabel;
    private JLabel departmentLabel;
    private JLabel phoneLabel;
    private JLabel genderLabel; 
    private JLabel titleLabel; 
    
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField departmentField;
    private JTextField phoneField;
    
    private JRadioButton maleButton, femaleButton, otherButton;
    
    private JComboBox<String> titleField;
    
    private String[] titles = {"Mr.", "Ms.", "Mrs.", "Dr.", "Col.", "Prof."}; 
    
    private JButton prevButton;
    private JButton nextButton;
    
    private JButton exitButton;
    private JButton submitButton;
	
	public DirectoryEditor() {
		prepareWindow();
	}
	
	private void prepareWindow() {
		frame = new JFrame("Directory Editor");
		Toolkit tk = frame.getToolkit(); 
		Dimension wndSize = tk.getScreenSize();
		
		frame.setBounds(wndSize.width / 4, wndSize.height / 4,
	        wndSize.width / 2, wndSize.height / 2);
	    
	    Image icon = Toolkit.getDefaultToolkit().getImage("directory.png");
	    frame.setIconImage(icon);
	    
	    frame.setLayout(new BorderLayout());
	    
	    // Set close operation and show window
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Font f = new Font("Serif", Font.BOLD, textSize);
	    
	    
	    employeeOptionPanel = new JPanel();
	    employeeOptionPanel.setLayout(new GridLayout(3, 1));
	    employeeOptionPanel.setBorder(new TitledBorder("Employee Information"));
	    
	    employeeTextPanel = new JPanel();
	    employeeTextPanel.setLayout(new GridLayout(4, 2));
	    
	    employeeGenderPanel = new JPanel();
	    employeeGenderPanel.setLayout(new FlowLayout());
	    
	    employeeTitlePanel = new JPanel();
	    employeeTitlePanel.setLayout(new FlowLayout());
	    
	    // Header
	    headerLabel = new JLabel("Employee 1", JLabel.CENTER);
	    headerLabel.setFont(f);
	    
	    // Labels
	    fnameLabel = new JLabel(fnameText, JLabel.RIGHT);
	    fnameLabel.setFont(f);
	    lnameLabel = new JLabel(lnameText, JLabel.RIGHT);
	    lnameLabel.setFont(f);
	    departmentLabel = new JLabel(departmentText, JLabel.RIGHT);
	    departmentLabel.setFont(f);
	    phoneLabel = new JLabel(phoneText, JLabel.RIGHT);
	    phoneLabel.setFont(f);
	    titleLabel = new JLabel(titleText, JLabel.RIGHT);
	    titleLabel.setFont(f);
	    genderLabel = new JLabel(genderText, JLabel.RIGHT);
	    genderLabel.setFont(f);
	    
	    // Text Fields
	    fnameField = new JTextField(15);
	    fnameField.setFont(f);
	    lnameField = new JTextField(15);
	    lnameField.setFont(f);
	    departmentField = new JTextField(15);
	    departmentField.setFont(f);
	    phoneField = new JTextField(15);
	    phoneField.setFont(f);
	    
	    // Radio Buttons
	    maleButton = new JRadioButton("Male");
	    maleButton.setActionCommand("MALE");
	    maleButton.setFont(f);
	    maleButton.addActionListener(new RadioClickListener());
	    femaleButton = new JRadioButton("Female");
	    femaleButton.setFont(f);
	    femaleButton.setActionCommand("FEMALE");
	    femaleButton.addActionListener(new RadioClickListener());
	    otherButton = new JRadioButton("Other");
	    otherButton.setActionCommand("OTHER");
	    otherButton.setFont(f);
	    otherButton.addActionListener(new RadioClickListener());
	    otherButton.setSelected(true);
	    
	    // Title Combo Box
	    titleField = new JComboBox<String>(titles);
	    titleField.setSelectedIndex(0);
	    titleField.setFont(f);
	    
	    
	    employeeTextPanel.add(fnameLabel);
	    employeeTextPanel.add(fnameField);
	    
	    employeeTextPanel.add(lnameLabel);
	    employeeTextPanel.add(lnameField);
	    
	    employeeTextPanel.add(departmentLabel);
	    employeeTextPanel.add(departmentField);
	    
	    employeeTextPanel.add(phoneLabel);
	    employeeTextPanel.add(phoneField);
	    
	    employeeGenderPanel.add(genderLabel);
	    employeeGenderPanel.add(maleButton);
	    employeeGenderPanel.add(femaleButton);
	    employeeGenderPanel.add(otherButton);
	    
	    employeeTitlePanel.add(titleLabel);
	    employeeTitlePanel.add(titleField);
	    

	    controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout()); // Place buttons here: prev, exit, submit, next
	    
	    // Submit and Exit
	    submitButton = new JButton("Submit");
	    submitButton.setFont(f);
	    exitButton = new JButton("Exit");
	    exitButton.setFont(f);
	
	    submitButton.setActionCommand("SUBMIT");
	    exitButton.setActionCommand("EXIT");
	
	    submitButton.addActionListener(new ButtonClickListener()); 
	    exitButton.addActionListener(new ButtonClickListener());
	    
	    // Previous and Next
	    prevButton = new JButton("Previous");
	    prevButton.setFont(f);
	    nextButton = new JButton("Next");
	    nextButton.setFont(f);
	
	    prevButton.setActionCommand("PREV");
	    nextButton.setActionCommand("NEXT");
	
	    prevButton.addActionListener(new NavClickListener()); 
	    nextButton.addActionListener(new NavClickListener());
	
	    controlPanel.add(prevButton);
	    controlPanel.add(submitButton);
	    controlPanel.add(exitButton);
	    controlPanel.add(nextButton);
	    
	    employeeOptionPanel.add(employeeTextPanel);
	    employeeOptionPanel.add(employeeGenderPanel);
	    employeeOptionPanel.add(employeeTitlePanel);
	    
	    frame.add(headerLabel, BorderLayout.PAGE_START);
	    frame.add(employeeOptionPanel, BorderLayout.CENTER);
	    frame.add(controlPanel, BorderLayout.PAGE_END);
	    
	    // Make frame visible
	    frame.setVisible(true);
	}
	

	
		//for submit and exit
	   	private class ButtonClickListener implements ActionListener{
	   		@Override
	   		public void actionPerformed(ActionEvent e) {
		         String command = e.getActionCommand();
		         if( command.equals( "EXIT" ))  {
		        	 frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		         } else if( command.equals( "SUBMIT" ) )  {
		        	 saveEmployee(currentIndex);
		        	 sendData();
		         } 	
		     }		
		}
	   
	   	//for next and prev
	   	private class NavClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
		         if( command.equals( "PREV" ))  {
		        	 if(currentIndex > 0) {
		        		 saveEmployee(currentIndex);
		        		 currentIndex--;
		        		 headerLabel.setText("Employee " + (currentIndex + 1));
		        		 loadEmployee(currentIndex);
		        	 }
		         } else if( command.equals( "NEXT" ) )  {
		        	 if(!fnameField.getText().equals("")) {
			        	saveEmployee(currentIndex);
			        	currentIndex++;
			        	headerLabel.setText("Employee " + (currentIndex + 1));
			        	if(currentIndex > emp.size() - 1)
			        		clearFields();
			        	else 
			        		loadEmployee(currentIndex);
		        	 }
		         }
			}
	   	}
	   	
	   	private class RadioClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
		         if(command.equals( "MALE" ))  {
		        	 femaleButton.setSelected(false);
		        	 otherButton.setSelected(false);
		        	 gender = "Male";
		        	 
		         } else if(command.equals("FEMALE"))  {
		        	 maleButton.setSelected(false);
		        	 otherButton.setSelected(false);
		        	 gender = "Female";
		         } else if(command.equals("OTHER"))  {
		        	 femaleButton.setSelected(false);
		        	 maleButton.setSelected(false);
		        	 gender = "Other";
			     }
			}
	   	}
	   	
	   	private void saveEmployee(int index) {
	   		Employee tmp = new Employee(fnameField.getText(), lnameField.getText(), departmentField.getText(), phoneField.getText(),
        	(String)titleField.getSelectedItem(), gender);
        	if(emp.size() < index +1) {
        		emp.add(tmp);
        	} else {
        		emp.set(index, tmp);
        	}
	   	}
	   	
	   	private void loadEmployee(int index) {
	   		Employee tmp = emp.get(index);
	   		fnameField.setText(tmp.getFirstName());
	   		lnameField.setText(tmp.getLastName());
	   		departmentField.setText(tmp.getDepartment());
	   		phoneField.setText(tmp.getPhoneNumber());
	   		titleField.setSelectedItem(tmp.getTitle());
	   		String g = tmp.getGender();
	   		if(g.equals( "Male" ))  {
	        	 maleButton.setSelected(true);
	        	 femaleButton.setSelected(false);
	        	 otherButton.setSelected(false);
	         } else if(g.equals("Female"))  {
	        	 maleButton.setSelected(false);
	        	 femaleButton.setSelected(true);
	        	 otherButton.setSelected(false);
	         } else if(g.equals("Other"))  {
	        	 femaleButton.setSelected(false);
	        	 maleButton.setSelected(false);
	        	 otherButton.setSelected(true);
		     }
	   		gender = g;
	   	}
	   	
	   	private void clearFields() {
	   		fnameField.setText("");
	   		lnameField.setText("");
	   		departmentField.setText("");
	   		phoneField.setText(""); 
			titleField.setSelectedIndex(0);
			otherButton.setSelected(true);
			femaleButton.setSelected(false);
       	 	maleButton.setSelected(false);
       	 	gender = "Other";
	   	}
	   	
	   
	
	
	public static void main(String[] args) {
		DirectoryEditor de = new DirectoryEditor();
	}
	
	private void sendData() {
		String str = new Gson().toJson(emp, new TypeToken<List<Employee>>() {}.getType());
		DirectoryProxy d = new DirectoryProxy();
		d.add(str);
		emp.clear();
		currentIndex = 0;
		headerLabel.setText("Employee " + (currentIndex + 1));
		clearFields();
	}
}
