package GUI;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ReadOnlyJTable extends JTable {

	// https://itecnote.com/tecnote/java-automatically-adjust-jtable-column-to-fit-content/
	// Auto resize table to fit content
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		Component component = super.prepareRenderer(renderer, row, column);
		int rendererWidth = component.getPreferredSize().width;
		TableColumn tableColumn = getColumnModel().getColumn(column);
		tableColumn.setPreferredWidth(
				Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
		return component;
	}

	// https://stackoverflow.com/questions/1990817/how-to-make-a-jtable-non-editable
	// Make JTable Read-Only
	@Override
	public boolean isCellEditable(int row, int column) {
		// all cells false
		return false;
	}
};
