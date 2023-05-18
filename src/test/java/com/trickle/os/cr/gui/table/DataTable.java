package com.trickle.os.cr.gui.table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class DataTable extends ListTable {
	private List<?> dataList;
	
	public DataTable(Class<?> type, List<?> dataList, String... columns) {
		this.dataList = dataList;
		columnNames = new Vector<String>(Arrays.asList(columns));
		setModel(tableModel = new DataTableModel(type));
	}
	
	public DataTable(Class<?> type, String... columns) {
		columnNames = new Vector<String>(Arrays.asList(columns));
		setModel(tableModel = new DataTableModel(type));
	}
	
	public DataTable(Class<?> type, List<?> dataList) {
		this.dataList = dataList;
		setModel(tableModel = new DataTableModel(type));
	}
	
	public DataTable(Class<?> type) {
		setModel(tableModel = new DataTableModel(type));
	}
	
	public DataTable(List<?> dataList) {
		this.dataList = dataList;
		setModel(tableModel = new DataTableModel(dataList.get(0).getClass()));
	}
	
	public List<?> getDataList(){
		return dataList;
	}
	
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
		update();
	}
	
	private class DataTableModel extends AbstractTableModel {
		private Field[] fields; 

		private DataTableModel(Class<?> type) {
			initFields(type);
		}
		
		private void initFields(Class<?> type) {
			fields = type.getDeclaredFields();
			for(Field field : fields) field.setAccessible(true);
		}
		
		private DataTableModel() {}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public int getRowCount() {
			if(dataList == null) return 0;
			return dataList.size();
		}

		@Override
		public int getColumnCount() {
			if(columnNames != null) 
				return columnNames.size();
			else
				return fields.length;
		}

		@Override
		public String getColumnName(int col) {
			if(columnNames != null)
				return columnNames.get(col);
			else
				return fields[col].getName();
		}

		@Override
		public Object getValueAt(int row, int col) {
			if(dataList.isEmpty() || fields == null) return "";
			Object value = null;
			try {
				value = fields[col].get(dataList.get(row));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return value == null ? "null" : value;
		}
	}
}