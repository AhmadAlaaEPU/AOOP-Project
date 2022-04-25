package GUI;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import API.API;
import DataClass.Course;
import DataClass.Department;
import DataClass.Stage;
import DataClass.Student;
import Database.IDatabaseChangeListener;
import javax.swing.JLabel;

public class Student_TabPanel extends JPanel {
	private JTable table;

	public Student_TabPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelButtons = new JPanel();
		add(panelButtons, BorderLayout.NORTH);
		panelButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Student_SearchDialog searchDialog = new Student_SearchDialog(table);
				searchDialog.setVisible(true);
				
				
			}
		});
				
		panelButtons.add(searchButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRowIndex = table.getSelectedRow();
				
				if(selectedRowIndex >= 0)
				{
					int realRowIndex = table.getRowSorter().convertRowIndexToModel(selectedRowIndex);

					String studentId = (String) table.getModel().getValueAt(realRowIndex, 0);
					Student_UpdateDialog updateDialog = new Student_UpdateDialog( studentId );
					updateDialog.setVisible(true);					
				}
				
			}
		});
		
		updateButton.setEnabled(false);

		panelButtons.add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int[] selectedRowsIndex = table.getSelectedRows();
				ArrayList<String> studentIdsforDelete = new ArrayList<String>();
				
				for(int index : selectedRowsIndex)
				{
					if(index >= 0)
					{
						int realRowIndex = table.getRowSorter().convertRowIndexToModel(index);

						String studentID = table.getModel().getValueAt(realRowIndex, 0).toString();
						studentIdsforDelete.add(studentID);
					}	
				}
				
				for(String id : studentIdsforDelete)
				{
					API.deleteStudentById(id);
				}
				
			}
		});
		
		deleteButton.setEnabled(false);

		
		panelButtons.add(deleteButton);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Student_AddDialog addDialog = new Student_AddDialog();
				addDialog.setVisible(true);
			}
		});
		panelButtons.add(addButton);
		
		if(API.getAllDepartment().length == 0)
		{
			addButton.setEnabled(false);
			addButton.setToolTipText("To add a Student, You must first have a department");
		}
		else {
			addButton.setToolTipText("");
			addButton.setEnabled(true);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new ReadOnlyJTable();
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		DefaultTableModel model = new DefaultTableModel(new String[] { "Student ID", "Full Name", "Address", "Courses", "Department", "Mobile Number", "Stage" }, 0);
		
		table.setFillsViewportHeight(true);
		table.setModel(model);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
	    table.setRowSorter(sorter);
		       
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {	            
	            updateButton.setEnabled(true);
	            deleteButton.setEnabled(true);
	        }
	    });
	    
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel labelStudentCount = new JLabel("Student Count in Database: 0");
		panel.add(labelStudentCount);
		
		API.addListener(new IDatabaseChangeListener() {

			@Override
			public void onDatabaseUpdated() {
							
				if(API.getAllDepartment().length == 0)
				{
					addButton.setEnabled(false);
					addButton.setToolTipText("To add a Student, You must first have a department");
				}
				else {
					addButton.setToolTipText("");
					addButton.setEnabled(true);
				}
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				// https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
				// Delete all rows in table
				model.setRowCount(0);
				
				Student[] students = API.getAllStudents();
				labelStudentCount.setText("Student Count in Database: " + students.length);
								
				for(Student s : students )
				{
					String courseNames = "";
					
					Course[] courses = s.getCourses();
					
					for(int i = 0; i < courses.length; i++)
					{
						courseNames += courses[i].name + ( (i == courses.length-1) ? "" : ", " );
					}

					Department department = s.getDepartment();
					Stage stage = s.getStage();
					
		       	 	model.addRow( new Object[] { s.id, s.fullName, s.address, courseNames , department , s.mobileNumber, stage } );
				}
				
			}
			
		});
	}

}