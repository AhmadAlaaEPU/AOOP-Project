package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import API.*;

public class Main {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {

		initialize();	

		API.readDatabase();

	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Student Management System");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		Student_TabPanel tab_panel_student = new Student_TabPanel();
		tabbedPane.addTab("Student", null, tab_panel_student, null);
		
		Department_TabPanel tab_panel_department = new Department_TabPanel();
		tabbedPane.addTab("Department", null, tab_panel_department, null);
		
		Course_TabPanel tab_panel_course = new Course_TabPanel();
		tabbedPane.addTab("Course", null, tab_panel_course, null);
	}

}