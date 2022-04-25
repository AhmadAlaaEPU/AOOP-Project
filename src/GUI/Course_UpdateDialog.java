package GUI;

import javax.swing.*;
import java.awt.*;
import API.API;
import DataClass.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Course_UpdateDialog extends JDialog {

    private JTextField textFieldCourseName;
    public JComboBox<Department> comboBox_department;
    public JComboBox<Stage> comboBox_stage;


    public Course_UpdateDialog(String courseId) {
        Course course = API.getCourseById(courseId);


        setBounds(100, 100, 450, 300);
        setTitle("Update Course");
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel lblCourseName = new JLabel("Name of course:");
        lblCourseName.setBounds(10, 13, 110, 13);
        panel.add(lblCourseName);

        textFieldCourseName = new JTextField();
        textFieldCourseName.setBounds(120, 10, 160, 19);
        textFieldCourseName.setText(course.name);
        textFieldCourseName.setColumns(10);
        panel.add(textFieldCourseName);


        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setBounds(10, 60, 80, 13);
        panel.add(lblDepartment);

        comboBox_department = new JComboBox<Department>(API.getAllDepartment());
        comboBox_department.setSelectedItem(course.getDepartment());
        
        comboBox_department.setBounds(10, 90, 110, 21);
        panel.add(comboBox_department);

        if (API.getAllDepartment().length > 0) {
            comboBox_stage = new JComboBox<Stage>(((Department)comboBox_department.getSelectedItem()).getStages());
            comboBox_stage.setSelectedItem(course.getStage());
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
            JButton okButton = new JButton("Update Course");

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Stage stage = (Stage) comboBox_stage.getSelectedItem();

                    Course updateCourse = new Course(courseId, textFieldCourseName.getText());

                    updateCourse.changeStage(stage);

                    dispose();
                }
            });
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);

        }
    }
}
