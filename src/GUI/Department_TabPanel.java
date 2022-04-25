package GUI;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import API.API;
import DataClass.Department;
import DataClass.Stage;
import Database.IDatabaseChangeListener;

public class Department_TabPanel extends JPanel {

	private JTable table;

	public Department_TabPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel_Buttons = new JPanel();
		add(panel_Buttons, BorderLayout.NORTH);
		panel_Buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRowIndex = table.getSelectedRow();

				if (selectedRowIndex >= 0) {
					
					int realRowIndex = table.getRowSorter().convertRowIndexToModel(selectedRowIndex);

					String departmentId = (String) table.getModel().getValueAt(realRowIndex, 0);
					Department_UpdateDialog updateDialog = new Department_UpdateDialog(departmentId);
					updateDialog.setVisible(true);
				}

			}
		});
		updateButton.setEnabled(false);
		panel_Buttons.add(updateButton);

		JButton deleteButton = new JButton("Delete");

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int[] selectedRowsIndex = table.getSelectedRows();
				ArrayList<String> departmentIdsforDelete = new ArrayList<String>();

				for (int index : selectedRowsIndex) {
					if (index >= 0) {
						
						int realRowIndex = table.getRowSorter().convertRowIndexToModel(index);

						String departmentID = table.getModel().getValueAt(realRowIndex, 0).toString();
						departmentIdsforDelete.add(departmentID);
					}
				}

				for (String id : departmentIdsforDelete) {
					API.deleteDepartmentById(id);
				}

			}
		});
		
        deleteButton.setEnabled(false);
		panel_Buttons.add(deleteButton);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Department_AddDialog addDepartment = new Department_AddDialog();
				addDepartment.setVisible(true);
			}
		});
		panel_Buttons.add(addButton);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new ReadOnlyJTable();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		DefaultTableModel modelDepartment = new DefaultTableModel(
				new String[] { "Department ID", "Department Name", "Stage" }, 0);

		table.setFillsViewportHeight(true);
		table.setModel(modelDepartment);
		final TableRowSorter<TableModel> sorterDepartment = new TableRowSorter<TableModel>(modelDepartment);
		table.setRowSorter(sorterDepartment);
		
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
		
		JLabel labelDepartmentCount = new JLabel("Student Count in Database: 0");
		panel.add(labelDepartmentCount);

		API.addListener(new IDatabaseChangeListener() {

			@Override
			public void onDatabaseUpdated() {

				DefaultTableModel modelDepartment = (DefaultTableModel) table.getModel();
				// https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
				// Delete all rows in table
				modelDepartment.setRowCount(0);
				
				Department[] departments = API.getAllDepartment();
				labelDepartmentCount.setText("Student Count in Database: " + departments.length);

				for (Department d : API.getAllDepartment()) {
					String stageNames = "";

					Stage[] stages = d.getStages();

					for (int i = 0; i < stages.length; i++) {
						stageNames += stages[i].name + ((i == stages.length - 1) ? "" : ", ");
					}

					modelDepartment.addRow(new Object[] { d.id, d.name, stageNames });
				}

			}

		});

	}

}