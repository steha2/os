package com.trickle.os.cr.gui.table;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class StringTable extends ListTable{
	private List<List<String>> stringList;
	
	public StringTable() {}
	
	public StringTable(String...columns) {
		setColumnNames(columns);
	}
	
	public StringTable(List<List<String>> stringList, String... columns) {
		setList(stringList);
		setColumnNames(columns);
	}
	
	private void setStringModel() {
		if(tableModel == null && columnNames != null && !columnNames.isEmpty())
			setModel(tableModel = new StringListTableModel());
	}
	
	public void setColumnNames(String... columns) {
		if(columns != null && columns.length != 0) {
			columnNames = Arrays.asList(columns);
		} else if(!stringList.isEmpty()) {
			columnNames = stringList.get(0); 
			stringList.remove(0);
		}
		setStringModel();
	}
	
	public void setList(List<List<String>> stringList) {
		this.stringList = stringList;
		setStringModel();
	}
	
	public List<String> getColumnNames(){
		return columnNames;
	}
	
	public List<List<String>> getList(){
		return stringList;
	}
	
	public void update() {
		tableModel.fireTableDataChanged();
	}
	
	private class StringListTableModel extends AbstractTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public int getRowCount() {
			if(stringList == null) return 0;
			return stringList.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.size();
		}

		@Override
		public String getColumnName(int col) {
			return columnNames.get(col);
		}

		@Override
		public Object getValueAt(int row, int col) {
			if(stringList == null) return "";
			else return stringList.get(row).get(col);
		}
	}
}