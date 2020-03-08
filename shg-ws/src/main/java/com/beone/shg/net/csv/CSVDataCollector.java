package com.beone.shg.net.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.persistence.util.DataUtil;
import com.csvreader.CsvWriter;

public class CSVDataCollector {

	private Integer noOfColumns;
	private Integer noOfRows;
	private List<String[]> rawData;
	private Map<Integer, String> indexToColumn;
	private Map<String, ArrayList<String>> columnToRows;
//	private CsvWriter csvWriter;
	
	public CSVDataCollector(List<String[]> rawData) {
		noOfColumns = 0;
		this.noOfRows = 0;
		this.rawData = rawData;
		indexToColumn = new LinkedHashMap<Integer, String>();
		columnToRows = new LinkedHashMap<String, ArrayList<String>>();
		
		loadData();
	}
	
	protected void loadData() {
		boolean columnCaptured = false;
		
		for(String[] row: rawData) {
			if(row.length > 2) {
				if(columnCaptured) {
					addRow(row);
					noOfRows++;
				} else {
					columnCaptured = true;
					addColumns(row);
				}
			}
		}		
	}	
	public void addColumns(String[] columns) {
		int expNoofRows = (int)(rawData.size() * 1.5);
		
		for(String column: columns) {
			this.indexToColumn.put(noOfColumns, column);
			this.columnToRows.put(column, new ArrayList<String>(expNoofRows));
			noOfColumns++;
		}
	}
	public void addRow(String[] row) {
		int rowLength = row.length;
		
		for(int index = 0; index < rowLength; index++) {
			if(row[index] != null) {
				columnToRows.get(indexToColumn.get(index)).add(row[index].trim());
			} else {
				columnToRows.get(indexToColumn.get(index)).add(DataUtil.EMPTY_STRING);
			}
		}
	}
	public Integer getNoOfRows() {
		return noOfRows;
	}
	public String getCellValue(String column, int row) {
		if(columnToRows.containsKey(column)) {
			return columnToRows.get(column).get(row);
		} else {
			return DataUtil.EMPTY_STRING;
		}
	}	
	public boolean isColumnPresent(String column) {
		return columnToRows.containsKey(column);
	}	

	protected void buildCVS(List<List<String>> data, CsvWriter csvWriter, String[] columns) throws IOException {
		int noOfRows = data.size();
		for(int rowIndex = 0; rowIndex < noOfRows; rowIndex++) {

			for(int colIndex = 0; colIndex < columns.length; colIndex++) {
				// Get Cell String from the Sorted Table
				String cellStr = data.get(colIndex).get(rowIndex);

				// Add Cell String if not invalid 
				if(cellStr != null && !cellStr.isEmpty()) {
					csvWriter.write(cellStr);  
				} 
				// Add Empty String
				else {
					csvWriter.write(DataUtil.EMPTY_STRING);  
				}
			}
			// Add New line char before starting new row 
			csvWriter.endRecord();
		}
	}
}
