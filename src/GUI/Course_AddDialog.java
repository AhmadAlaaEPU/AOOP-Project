package GUI;

import javax.swing.*;
import java.awt.*;
import API.API;
import DataClass.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Course_AddDialog extends JDialog {

    private JTextField textFieldCourseName;
    public JComboBox<Department> comboBox_department;
    public JComboBox<Stage> comboBox_stage;


    public Course_AddDialog() {
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 450, 300);
        setTitle("Add Course");
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblCourseName = new JLabel("Name of course:");
        lblCourseName.setBounds(10, 13, 110, 13);
        panel.add(lblCourseName);

        textFieldCourseName = new JTextField();
        textFieldCourseName.setBounds(120, 10, 160, 19);
        textFieldCourseName.setColumns(10);
        panel.add(textFieldCourseName);


        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setBounds(10, 60, 80, 13);
        panel.add(lblDepartment);

        comboBox_department = new JComboBox<Department>(API.getAllDepartment());
        comboBox_department.setBounds(10, 90, 110, 21);
        panel.add(comboBox_department);

        if (API.getAllDepartment().length > 0) {
            comboBox_stage = new JComboBox<Stage>(API.getAllDepartment()[0].getStages());
        } else {
            comboBox_stage = new JComboBox<Stage>();

        }

        JLabel lblStage = new JLabel("Stage:");
        lblStage.setBounds(140, 60, 80, 13);
        panel.add(lblStage);

        comboBox_stage.setBounds(140, 90, 110, 21);
        panel.add(comboBox_stage);

        panel.setLayout(null);

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

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Add Course");

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Course course = new Course(textFieldCourseName.getText());
                    Stage stage = (Stage) comboBox_stage.getSelectedItem();
                    stage.addCourse(course);

                    dispose();
                }
            });
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);

        }
        
        
        
    }
}
