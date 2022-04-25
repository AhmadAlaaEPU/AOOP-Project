package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import API.API;
import DataClass.Course;
import DataClass.Department;
import DataClass.Stage;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;

public class Student_InformationPanel extends JPanel {
	public JTextField textField_fullname;
	public JTextField textField_mobileNumber;
	public JTextField textField_country;
	public JTextField textField_city;
	public JTextField textField_street;
	public JTextField textField_buildingNumber;
	public JComboBox<Department> comboBox_department;
	public JComboBox<Stage> comboBox_stage;
	public LinkedHashMap<JCheckBox, Course> checkBox_courses = new LinkedHashMap<JCheckBox, Course>();
	private JPanel panel_courses;

	public Student_InformationPanel() {
		setLayout(null);
		
		panel_courses = new JPanel();

		JLabel lblNewLabel = new JLabel("Full Name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 10, 100, 13);
		add(lblNewLabel);
		
		textField_fullname = new JTextField();
		textField_fullname.setBounds(120, 7, 180, 19);
		add(textField_fullname);
		textField_fullname.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Mobile Number:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(10, 40, 100, 13);
		add(lblNewLabel_1);
		
		textField_mobileNumber = new JTextField();
		textField_mobileNumber.setColumns(10);
		textField_mobileNumber.setBounds(120, 36, 180, 19);
		add(textField_mobileNumber);
		
		JLabel lblNewLabel_1_1 = new JLabel("Address:");
		lblNewLabel_1_1.setBounds(20, 82, 477, 13);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Country");
		lblNewLabel_1_1_1.setBounds(20, 105, 80, 13);
		add(lblNewLabel_1_1_1);
		
		textField_country = new JTextField();
		textField_country.setColumns(10);
		textField_country.setBounds(20, 128, 114, 19);
		add(textField_country);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("City");
		lblNewLabel_1_1_1_1.setBounds(140, 105, 80, 13);
		add(lblNewLabel_1_1_1_1);
		
		textField_city = new JTextField();
		textField_city.setColumns(10);
		textField_city.setBounds(140, 128, 114, 19);
		add(textField_city);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Street");
		lblNewLabel_1_1_1_1_1.setBounds(262, 105, 80, 13);
		add(lblNewLabel_1_1_1_1_1);
		
		textField_street = new JTextField();
		textField_street.setColumns(10);
		textField_street.setBounds(262, 128, 114, 19);
		add(textField_street);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Building No.");
		lblNewLabel_1_1_1_1_1_1.setBounds(383, 105, 80, 13);
		add(lblNewLabel_1_1_1_1_1_1);
		
		textField_buildingNumber = new JTextField();
		textField_buildingNumber.setColumns(10);
		textField_buildingNumber.setBounds(383, 128, 114, 19);
		add(textField_buildingNumber);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Department:");
		lblNewLabel_1_1_1_2.setBounds(20, 162, 80, 13);
		add(lblNewLabel_1_1_1_2);
		
		comboBox_department = new JComboBox<Department>( API.getAllDepartment() );
		comboBox_department.setBounds(20, 185, 110, 21);
		add(comboBox_department);
		
		if(API.getAllDepartment().length > 0) {
			comboBox_stage = new JComboBox<Stage>( ((Department) comboBox_department.getSelectedItem()).getStages() );
			updateCourseCheckboxes(null);
		}
		else {
			comboBox_stage = new JComboBox<Stage>();

		}
		comboBox_stage.setBounds(140, 185, 110, 21);
		add(comboBox_stage);
		
		JLabel lblNewLabel_1_1_1_2_1 = new JLabel("Stage:");
		lblNewLabel_1_1_1_2_1.setBounds(140, 162, 80, 13);
		add(lblNewLabel_1_1_1_2_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 68, 500, 4);
		add(separator);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 240, 477, 100);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		
		scrollPane.setViewportView(panel_courses);
		panel_courses.setLayout(new BoxLayout(panel_courses, BoxLayout.Y_AXIS));
		
		//JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		//panel_courses.add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_1_1_1_2_2 = new JLabel("Courses:");
		lblNewLabel_1_1_1_2_2.setBounds(20, 220, 80, 13);
		add(lblNewLabel_1_1_1_2_2);


		comboBox_department.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JComboBox<Department> combo = (JComboBox<Department>) event.getSource();
		        Department department = (Department) combo.getSelectedItem();
		        comboBox_stage.removeAllItems();
		        
		        for(Stage stage : department.getStages())
		        {
		        	comboBox_stage.addItem(stage);
		        }
		    }
		});
		
		
		comboBox_stage.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        updateCourseCheckboxes(null);
		    }
		});
		
	}
	
	public void updateCourseCheckboxes(Course[] setCourses){
        Stage stage = (Stage) comboBox_stage.getSelectedItem();

        if(stage == null)
        {
        	return;
        }
        
        panel_courses.removeAll();
        panel_courses.revalidate();
        panel_courses.repaint();
        
		checkBox_courses.clear();
        
        for(Course course : stage.getCourses())
        {
        	JCheckBox checkbox = new JCheckBox(course.name);
        	checkbox.setSelected(false);
        	
        	if(setCourses != null)
        	{
        		for(Course setCourse : setCourses)
            	{
            		if(course.id.equals(setCourse.id))
            		{
                    	checkbox.setSelected(true);
            		}
            	}	
        	}

        	
        	
        	panel_courses.add(checkbox);
			checkBox_courses.put(checkbox, course);
        }
	}

}
