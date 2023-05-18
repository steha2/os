package com.trickle.os.cr.gui.table;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class ListTable extends JTable{
	protected AbstractTableModel tableModel;

	protected List<String> columnNames;

	{ setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); }
	
	public void update() {
		tableModel.fireTableDataChanged();
	}
	
	public void setColumnsSize(int... sizes) {
		for (int i = 0; i < Math.min(getColumnCount(), sizes.length); i++) {
			String columnName = getColumnName(i);
			if(columnName == null || columnName.isEmpty()) return;
			getColumn(getColumnName(i)).setPreferredWidth(sizes[i]);
		}
	}
	
	public List<String> getColumnNames(){
		return columnNames;
	}
	
	public AbstractTableModel getModel() {
		return tableModel;
	}
	
	public void setColumnNames(String... columns) {
		columnNames = new Vector<String>(Arrays.asList(columns));
	    TableColumnModel columnModel = getColumnModel();
	    for (int i = 0; i < columns.length; i++) {
	        TableColumn column = columnModel.getColumn(i);
	        column.setHeaderValue(columns[i]);
	    }
	    getTableHeader().repaint(); // 테이블 헤더 다시 그리기
	}
}
