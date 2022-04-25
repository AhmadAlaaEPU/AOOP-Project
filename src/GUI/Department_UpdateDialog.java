package GUI;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import API.API;
import DataClass.Department;
import DataClass.Stage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;

public class Department_UpdateDialog extends JDialog {

	private JTextField textFieldDepartmentName;
	private JSpinner spinner;

	public Department_UpdateDialog(String departmentId) {

		Department department = API.getDepartmentById(departmentId);
		Stage[] stages = department.getStages();
		

		setBounds(100, 100, 450, 300);
		setTitle("Update Department");
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 261);
		getContentPane().add(panel);

		JLabel lblDepartmentName = new JLabel("Name:");
		lblDepartmentName.setBounds(10, 47, 45, 14);

		textFieldDepartmentName = new JTextField();
		textFieldDepartmentName.setBounds(51, 44, 86, 20);
		textFieldDepartmentName.setText(department.name);
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

				int count = (Integer) spinner.getValue();
				
				if(stages.length  <= count)
				{
					// stage added
					
					for(int i = 0; i < count; i++)
					{
						String newName = "Stage " + (i+1);
						Stage oldStage = API.getStageByName(department, newName);
						
						if (oldStage == null) {
							department.addStage( new Stage(newName) );
						} 
					}
				}
				else {
					// stage removed
					// delete remaining stages
					for(int i = count; i < stages.length; i++)
					{
						stages[i].delete();
					}
				}

				Department updatedDepartment = new Department(departmentId, textFieldDepartmentName.getText(),
						department.stagesIds.toArray(new String[0]));
				API.updateDepartmentById(updatedDepartment.id, updatedDepartment);
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