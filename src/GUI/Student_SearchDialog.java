package GUI;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableStringConverter;

import API.API;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Student_SearchDialog extends JDialog {
	private JTextField textField;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JTable table;

	public Student_SearchDialog(JTable table) {
		this.setTitle("Search Student");
		this.table = table;
		
		setBounds(100, 100, 450, 164);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Name", "Mobile Number", "Course Name"}));
		panel.add(comboBox);
		
		lblNewLabel = new JLabel("Found: 0");
		panel.add(lblNewLabel);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				change();
			}
		});
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  change();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  change();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  change();
			  }
		});
		

	}
	
	 public void change() {
		 
		 final int FULLNAME_COLUMN_INDEX = 1;
		 final int MOBILNUMBER_COLUMN_INDEX = 5;
		 final int COURSES_COLUMN_INDEX = 3;
		 
		 // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#sorting
		 TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
		 sorter.setStringConverter(new TableStringConverter() {
			    @Override
			    public String toString(TableModel model, int row, int column) {
			        return model.getValueAt(row, column).toString().toLowerCase();
			    }
			});

		 if(textField.getText().isBlank())
		 {
			 sorter.setRowFilter(null);
			 lblNewLabel.setText("Found: " + API.getAllStudents().length);
			 return;
		 }
		 
		  RowFilter<TableModel, Object> rf = null;
		    //If current expression doesn't parse, don't update.
		    try {
		    	if(comboBox.getSelectedIndex() == 0)
		    	{
		    		rf = RowFilter.regexFilter(textField.getText().toLowerCase(), FULLNAME_COLUMN_INDEX);
		    		lblNewLabel.setText("Found: " + API.getStudentsByName(textField.getText()).length);
		    	}
		    	else if(comboBox.getSelectedIndex() == 1)
		    	{
		    		rf = RowFilter.regexFilter(textField.getText().toLowerCase(), MOBILNUMBER_COLUMN_INDEX);
		    		lblNewLabel.setText("Found: " + API.getStudentsByMobileNumber(textField.getText()).length);
		    	}
		    	else if(comboBox.getSelectedIndex() == 2)
		    	{
		    		rf = RowFilter.regexFilter(textField.getText().toLowerCase(), COURSES_COLUMN_INDEX);
		    		lblNewLabel.setText("Found: " + API.getStudentsByCourseName(textField.getText()).length);
		    	}

		    } catch (java.util.regex.PatternSyntaxException e) {
		        return;
		    }
		    
		    sorter.setRowFilter(rf);
			
	  }
}