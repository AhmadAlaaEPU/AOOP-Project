package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import API.API;
import DataClass.Address;
import DataClass.Course;
import DataClass.Department;
import DataClass.Stage;
import DataClass.Student;

public class Student_UpdateDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final Student_InformationPanel userInfoPanel = new Student_InformationPanel();
	private String studentId = "";

	public Student_UpdateDialog(String studentId) {
		this.studentId = studentId;
		
		Student student = API.getStudentById(studentId);
		
		userInfoPanel.textField_fullname.setText(student.fullName);
		userInfoPanel.textField_mobileNumber.setText(student.mobileNumber);
		userInfoPanel.textField_country.setText(student.address.country);
		userInfoPanel.textField_city.setText(student.address.city);
		userInfoPanel.textField_street.setText(student.address.street);
		userInfoPanel.textField_buildingNumber.setText(student.address.buildingNumber);
		userInfoPanel.comboBox_department.setSelectedItem(student.getDepartment());
		userInfoPanel.comboBox_stage.setSelectedItem(student.getStage());
		userInfoPanel.updateCourseCheckboxes(student.getCourses());
		
		setTitle("Update Student Information");
		setBounds(100, 100, 550, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(userInfoPanel);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Update Student Info");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateStudent();
						dispose();

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void updateStudent() {
		
		ArrayList<String> coursesIds = new ArrayList<String>();
		
		for (Entry<JCheckBox, Course> entry : userInfoPanel.checkBox_courses.entrySet()) {
		    JCheckBox checkbox = entry.getKey();
		    Course course = entry.getValue();
		    
		    if(checkbox.isSelected())
			{
		        coursesIds.add(course.id);
			}
		}
		
		Address address = new Address(userInfoPanel.textField_country.getText(), userInfoPanel.textField_city.getText(), userInfoPanel.textField_street.getText(), userInfoPanel.textField_buildingNumber.getText());		
		Department department = (Department) userInfoPanel.comboBox_department.getSelectedItem();	
		Stage stage = (Stage) userInfoPanel.comboBox_stage.getSelectedItem();
		
		
		Student student = new Student(studentId, userInfoPanel.textField_fullname.getText(), userInfoPanel.textField_mobileNumber.getText(), address, stage.id, department.id, coursesIds);

		API.updateStudentById(student.id, student);
	}

	
	
}
