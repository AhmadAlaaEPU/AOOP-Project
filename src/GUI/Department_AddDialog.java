package GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import API.API;
import DataClass.Department;
import DataClass.Stage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class Department_AddDialog extends JDialog {

	private JTextField textFieldDepartmentName;
	private JSpinner spinner;

	
	public Department_AddDialog() {
		initialize();	
	}

	private void initialize() {
		System.out.println("hello");
		setBounds(100, 100, 450, 300);
		setTitle("Add Department");
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblDepartmentName = new JLabel("Name:");
		lblDepartmentName.setBounds(10, 47, 45, 14);
		
		textFieldDepartmentName = new JTextField();
		textFieldDepartmentName.setBounds(51, 44, 86, 20);
		textFieldDepartmentName.setColumns(10);
		
		JLabel lblDepartmentStage = new JLabel("Stage:");
		lblDepartmentStage.setBounds(10, 85, 50, 14);
		spinner = new JSpinner();
		spinner.setBounds(52, 82, 39, 20);
		spinner.setModel(new SpinnerNumberModel(4, 0, 10, 1));
		
		JButton btnOkButton = new JButton("OK");
		btnOkButton.setBounds(278, 227, 55, 23);
		btnOkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int m = (Integer) spinner.getValue();
				ArrayList<String> stageIds = new ArrayList<String>();
				
				for(int i=1; i<= m ; i++) {
					Stage stage = new Stage("Stage " + i);
					stageIds.add(stage.id);
					API.addStage(stage);
				}
				
				Department department = new Department(textFieldDepartmentName.getText(), stageIds.toArray(new String[0]));
				API.addDepartment(department);
				dispose();
			}
		});
		
		JButton btnCancelButton = new JButton("Cancel");
		btnCancelButton.setBounds(349, 227, 75, 23);
		btnCancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.setLayout(null);
		panel.add(lblDepartmentName);
		panel.add(textFieldDepartmentName);
		panel.add(lblDepartmentStage);
		panel.add(spinner);
		panel.add(btnOkButton);
		panel.add(btnCancelButton);
	}
}