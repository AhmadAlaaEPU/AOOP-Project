package GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
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
import Database.IDatabaseChangeListener;

public class Course_TabPanel extends JPanel {

	private JTable table;

	public Course_TabPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel_Buttons = new JPanel();
		add(panel_Buttons, BorderLayout.NORTH);
		panel_Buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRowIndex = table.getSelectedRow();

				if (selectedRowIndex >= 0) {
					
					int realRowIndex = table.getRowSorter().convertRowIndexToModel(selectedRowIndex);

					String CourseId = (String) table.getModel().getValueAt(realRowIndex, 0);
					Course_UpdateDialog updateDialog = new Course_UpdateDialog(CourseId);
					updateDialog.setVisible(true);
				}

			}
		});
		panel_Buttons.add(updateButton);

		JButton deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] selectedRowsIndex = table.getSelectedRows();
				ArrayList<String> CourseIdsforDelete = new ArrayList<String>();

				for (int index : selectedRowsIndex) {
					if (index >= 0) {
						
						int realRowIndex = table.getRowSorter().convertRowIndexToModel(index);

						String CourseID = table.getModel().getValueAt(realRowIndex, 0).toString();
						CourseIdsforDelete.add(CourseID);
					}
				}

				for (String id : CourseIdsforDelete) {
					API.deleteCourseById(id);
				}

			}
		});

		panel_Buttons.add(deleteButton);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Course_AddDialog addCourse = new Course_AddDialog();
				addCourse.setVisible(true);
			}
		});
		panel_Buttons.add(addButton);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new ReadOnlyJTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


		DefaultTableModel modelCourse = new DefaultTableModel(
				new String[] { "Course ID", "Course Name" , "Stage", "Department" }, 0);

		table.setFillsViewportHeight(true);
		table.setModel(modelCourse);
		final TableRowSorter<TableModel> sorterCourse = new TableRowSorter<TableModel>(modelCourse);
		table.setRowSorter(sorterCourse);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	            updateButton.setEnabled(true);
	            deleteButton.setEnabled(true);
	        }
	    });
		
		scrollPane.setViewportView(table);

		API.addListener(new IDatabaseChangeListener() {

			@Override
			public void onDatabaseUpdated() {

				DefaultTableModel modelCourse = (DefaultTableModel) table.getModel();
				// https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
				// Delete all rows in table
				modelCourse.setRowCount(0);

				for (Course course : API.getAllCourses()) {
					Stage stage = course.getStage();
					Department department = course.getDepartment();
					modelCourse.addRow(new Object[] { course.id, course.name, stage, department });
				}

			}

		});

	}

}