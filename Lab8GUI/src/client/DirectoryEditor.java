package client;

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

import javax.swing.*;
import javax.swing.border.TitledBorder;

import directory.Employee;

public class DirectoryEditor extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static DirectoryProxy dirp = new DirectoryProxy();
	private static List<Employee> emp = new ArrayList<Employee>();
	
	private int currentIndex = 0;
	private String fname;
	private String lname;
	private String phone;
	private String gender;
	private String title;
	
	private JFrame frame;
    
    private JPanel employeeTextPanel;
    private JPanel employeeOptionPanel;
    private JPanel employeeTitlePanel;
    private JPanel employeeGenderPanel;
    private JPanel controlPanel; // Controls for navigating through employees
    
    // Text for field labels
    private String fnameText = "Employee First Name"; // Text
    private String lnameText = "Employee Last Name"; // Text
    private String departmentText = "Employee Department"; // Text
    private String phoneText = "Employee Phone Number"; // Text
    private String genderText = "Employee Gender"; // Radio button - Male, Female, Other - need default 
    private String titleText = "Employee Title"; // List - Mr. Ms. Mrs. Dr. Col. Prof.
    
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
	    
	    frame.setLayout(new GridLayout(3, 1));
	    
	    // Set close operation and show window
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Font f = new Font("Serif", Font.BOLD, 24);
	    
	    employeeTextPanel = new JPanel();
	    employeeTextPanel.setLayout(new GridLayout(4, 2));
	    employeeTextPanel.setBorder(new TitledBorder("Employee Information"));
	    
	    employeeOptionPanel = new JPanel();
	    employeeOptionPanel.setLayout(new GridLayout(2, 1));
	    
	    employeeGenderPanel = new JPanel();
	    employeeGenderPanel.setLayout(new FlowLayout());
	    employeeGenderPanel.setBorder(new TitledBorder("Employee Gender"));
	    
	    employeeTitlePanel = new JPanel();
	    employeeTitlePanel.setLayout(new FlowLayout());
	    employeeTitlePanel.setBorder(new TitledBorder("Employee Title"));
	    
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
	    genderLabel = new JLabel(titleText, JLabel.RIGHT);
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
	    titleField.setSelectedIndex(1);
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
	    
	    employeeOptionPanel.add(employeeGenderPanel);
	    employeeOptionPanel.add(employeeTitlePanel);
	    
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
	    
	    frame.add(employeeTextPanel);
	    frame.add(employeeOptionPanel);
	    frame.add(controlPanel);
	    
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
		        	 // Submit button clicked
		        	 // Upload functionality here
		         } 	
		     }		
		}
	   
	   	//for next and prev
	   	private class NavClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
		         if( command.equals( "PREV" ))  {
		            // Exit Button Clicked
		            // load previous in list, disable if no previous
		         } else if( command.equals( "NEXT" ) )  {
		            // Submit button clicked
		        	// load new (blank) or next in list
		         }
			}
	   	}
	   	
	   	private class RadioClickListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
		         if(command.equals( "MALE" ))  {
		            // male
		        	 femaleButton.setSelected(false);
		        	 otherButton.setSelected(false);
		        	 gender = "Male";
		        	 
		         } else if(command.equals("FEMALE"))  {
		            // female
		        	 maleButton.setSelected(false);
		        	 otherButton.setSelected(false);
		        	 gender = "Female";
		         } else if(command.equals("OTHER"))  {
			            // other
		        	 femaleButton.setSelected(false);
		        	 maleButton.setSelected(false);
		        	 gender = "Other";
			     }
			}
	   	}
	   	
	   	
	   
	
	
	public static void main(String[] args) {
		
		DirectoryEditor de = new DirectoryEditor();
		
	    
		/*
		// Previous junk
		 
		Scanner in = new Scanner(System.in);
		String response;
		
		System.out.println("File (F) or Console (C) Input?");
		response = in.nextLine();
		if(response.equalsIgnoreCase("F")) {
			fileInput(in);

		} else if (response.equalsIgnoreCase("C")) {
			consoleInput(in);
		}

		
		in.close();
		*/
	}
	
	private static void consoleInput(Scanner in) {
		String input;
		boolean consoleInput = true;
		boolean canAdd = false;
		
		System.out.println("Type \"Exit\" at any time to end program");

		
		while(consoleInput) {

			while ((input = in.nextLine()) != null) {

				
				if(input.equals("Exit")) {
					consoleInput = false;
					break;
				}else if(input.equals("END")) {
					canAdd = false;
					sendData();
				}else if(canAdd) {
					if(input.split(" ").length == 4) {
						addEmp(input);
					} else {
						System.out.println("Please enter employee while in Add mode, or type \"END\" to stop Add mode");
					}
				}else if(input.equals("ADD")) {
					canAdd = true;
				}else if(input.equals("CLR")) {
					dirp.clr();
				}else if(input.equals("PRINT")) {
					dirp.print();
					System.out.println();
				}
			}
		}
	}
	
	private static void fileInput(Scanner in) {
		String fileName;
		File file = null;
		
		
		System.out.print("\nPlease enter a file path:");
		fileName = in.nextLine();
		
		file = new File(fileName);
		
		if(!file.exists() || !file.isFile()) {
			System.out.println("File does not exist");
			return;
		}
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileName));){
			
			String line;
			boolean canAdd = false;
			
			while ((line = br.readLine()) != null) {
				if(line.equals("END")) {
					canAdd = false;
					sendData();
				}else if(canAdd) {
					addEmp(line);
				}else if(line.equals("ADD")) {
					canAdd = true;
				}else if(line.equals("CLR")) {
					dirp.clr();
				}else if(line.equals("PRINT")) {
					dirp.print();
					System.out.println();
				}
				
			}
		} catch (IOException ex) {
			System.out.println("error occured in retrieving file");
		}				
		
	}
	
	private static void sendData() {
		Gson g = new Gson();
		dirp.add(g.toJson(emp));
		emp.clear();
	}
	
	private static void addEmp(String line) {
		String[] e = line.split(" ");
		if(e.length == 4) {
			Employee tmp = new Employee(e[1], e[0], e[3], e[2]);
			emp.add(tmp);
		}
	}
	
}
