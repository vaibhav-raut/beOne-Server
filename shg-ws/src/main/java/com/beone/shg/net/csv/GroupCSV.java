package com.beone.shg.net.csv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.util.DataUtil;
import com.csvreader.CsvWriter;

public class GroupCSV {

	protected final static Logger LOGGER = LoggerFactory.getLogger(GroupCSV.class);
	
	protected static Map<String, Integer> columnToIndex = new LinkedHashMap<String, Integer>();
	protected static int noOfColumn;
	static {
		int index = 0;
		columnToIndex.put(CSVConst.Group_Name, index++);
		columnToIndex.put(CSVConst.Group_groupAcNo, index++);
		columnToIndex.put(CSVConst.Group_Vision, index++);
		columnToIndex.put(CSVConst.Group_Description, index++);
		columnToIndex.put(CSVConst.Group_Date_Of_Establishment, index++);
		columnToIndex.put(CSVConst.Group_Address, index++);
		columnToIndex.put(CSVConst.Group_Village, index++);
		columnToIndex.put(CSVConst.Group_Grampanchayat, index++);
		columnToIndex.put(CSVConst.Group_Tehsil, index++);
		columnToIndex.put(CSVConst.Group_Pin_Code, index++);
		columnToIndex.put(CSVConst.Group_District, index++);
		columnToIndex.put(CSVConst.Group_Phone, index++);
		columnToIndex.put(CSVConst.Group_Primary_Mobile, index++);
		columnToIndex.put(CSVConst.Group_Secondary_Mobile, index++);
		columnToIndex.put(CSVConst.Group_Email, index++);
		columnToIndex.put(CSVConst.Group_Bank_Account_Number, index++);
		columnToIndex.put(CSVConst.Group_Account_Name, index++);
		columnToIndex.put(CSVConst.Group_Bank_Account_Type, index++);
		columnToIndex.put(CSVConst.Group_Bank_Name, index++);
		columnToIndex.put(CSVConst.Group_Bank_Branch_Name, index++);
		columnToIndex.put(CSVConst.Group_IFSC_Code, index++);
		columnToIndex.put(CSVConst.Group_CM_Planned_Monthly_Saving, index++);
		columnToIndex.put(CSVConst.Group_CM_Saved_Amount, index++);
		columnToIndex.put(CSVConst.Group_CM_Provisional_Interest_Earned, index++);
		columnToIndex.put(CSVConst.Group_CM_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_CM_Recovered_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_CM_Outstanding_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_CM_Recovered_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_CM_Projected_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_CM_No_of_Loans, index++);
		columnToIndex.put(CSVConst.Group_CM_No_of_Active_Loans, index++);
		columnToIndex.put(CSVConst.Group_CM_Returned_Saved_Amount, index++);
		columnToIndex.put(CSVConst.Group_CM_Returned_Interest_on_Saving, index++);
		columnToIndex.put(CSVConst.Group_CM_Profit_Share_Paid, index++);
		columnToIndex.put(CSVConst.Group_AM_Planned_Monthly_Saving, index++);
		columnToIndex.put(CSVConst.Group_AM_Saved_Amount, index++);
		columnToIndex.put(CSVConst.Group_AM_Provisional_Interest_Earned, index++);
		columnToIndex.put(CSVConst.Group_AM_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_AM_Recovered_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_AM_Outstanding_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_AM_Recovered_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_AM_Projected_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_AM_No_of_Loans, index++);
		columnToIndex.put(CSVConst.Group_AM_No_of_Active_Loans, index++);
		columnToIndex.put(CSVConst.Group_AM_Returned_Saved_Amount, index++);
		columnToIndex.put(CSVConst.Group_AM_Returned_Interest_on_Saving, index++);
		columnToIndex.put(CSVConst.Group_AM_Divided_Paid, index++);
		columnToIndex.put(CSVConst.Group_Total_No_Of_Project, index++);
		columnToIndex.put(CSVConst.Group_Active_No_Of_Project, index++);
		columnToIndex.put(CSVConst.Group_Project_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_Project_Recovered_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_Project_Recovered_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_Project_Projected_Interest_on_Loan, index++);
		columnToIndex.put(CSVConst.Group_Total_No_Of_Fix_Deposit, index++);
		columnToIndex.put(CSVConst.Group_Active_No_Of_Fix_Deposit, index++);
		columnToIndex.put(CSVConst.Group_Fix_Deposit_Investment, index++);
		columnToIndex.put(CSVConst.Group_Recovered_Fix_Deposit_Investment, index++);
		columnToIndex.put(CSVConst.Group_Recovered_Intrest_on_Fix_Deposit, index++);
		columnToIndex.put(CSVConst.Group_Projected_Interest_on_Fix_Deposit, index++);
		columnToIndex.put(CSVConst.Group_Total_No_Of_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Active_No_Of_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Recovered_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Recovered_Intrest_on_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Projected_Interest_on_Other_Investment, index++);
		columnToIndex.put(CSVConst.Group_Total_No_Of_Bank_Loan, index++);
		columnToIndex.put(CSVConst.Group_Active_No_Of_Bank_Loan, index++);
		columnToIndex.put(CSVConst.Group_Bank_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_Paid_Bank_Loan_Amount, index++);
		columnToIndex.put(CSVConst.Group_Paid_Interest_on_Bank_Loan, index++);
		columnToIndex.put(CSVConst.Group_Projected_Interest_on_Bank_Loan, index++);
		columnToIndex.put(CSVConst.Group_Interest_Eaned_on_Bank_Saving, index++);
		columnToIndex.put(CSVConst.Group_Expenses, index++);
		columnToIndex.put(CSVConst.Group_Recovered_Penalty, index++);
		columnToIndex.put(CSVConst.Group_Bank_Balance, index++);
		columnToIndex.put(CSVConst.Group_Cash_in_Hand, index++);
		columnToIndex.put(CSVConst.Group_Net_Profit, index++);
		columnToIndex.put(CSVConst.Group_Unused_End, index++);
		noOfColumn = index;
	}

	protected List<GProfile> groups;
	protected int noOfRows;
	protected List<List<String>> data;
	protected CsvWriter csvWriter;
	
	public GroupCSV(List<GProfile> groups) {
		this.groups = groups;
		this.noOfRows = groups.size();
		this.data = initData(groups.size(), noOfColumn);
		loadData();
	}
	
	protected void loadData() {
		for(Map.Entry<String,Integer> column : columnToIndex.entrySet()) {
			data.get(0).set(column.getValue(), column.getKey());
		}
		
		for(int i = 0, j = 1; i < noOfRows; i++, j++) {
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Name), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_groupAcNo), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Vision), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Description), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Date_Of_Establishment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Address), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Village), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Grampanchayat), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Tehsil), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Pin_Code), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_District), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Phone), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Primary_Mobile), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Secondary_Mobile), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Email), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Account_Number), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Account_Name), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Account_Type), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Name), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Branch_Name), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_IFSC_Code), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Planned_Monthly_Saving), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Saved_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Provisional_Interest_Earned), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Recovered_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Outstanding_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Recovered_Interest_on_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Projected_Interest_on_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_No_of_Loans), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_No_of_Active_Loans), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Returned_Saved_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Returned_Interest_on_Saving), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_CM_Profit_Share_Paid), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Planned_Monthly_Saving), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Saved_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Provisional_Interest_Earned), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Recovered_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Outstanding_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Recovered_Interest_on_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Projected_Interest_on_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_No_of_Loans), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_No_of_Active_Loans), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Returned_Saved_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Returned_Interest_on_Saving), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_AM_Divided_Paid), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Total_No_Of_Project), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Active_No_Of_Project), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Project_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Project_Recovered_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Project_Recovered_Interest_on_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Project_Projected_Interest_on_Loan), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Total_No_Of_Fix_Deposit), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Active_No_Of_Fix_Deposit), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Fix_Deposit_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Recovered_Fix_Deposit_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Recovered_Intrest_on_Fix_Deposit), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Projected_Interest_on_Fix_Deposit), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Total_No_Of_Other_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Active_No_Of_Other_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Other_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Recovered_Other_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Recovered_Intrest_on_Other_Investment), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Projected_Interest_on_Other_Investment), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Total_No_Of_Bank_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Active_No_Of_Bank_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Paid_Bank_Loan_Amount), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Paid_Interest_on_Bank_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Projected_Interest_on_Bank_Loan), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Interest_Eaned_on_Bank_Saving), groups.get(i).getDescription());
			
			data.get(j).set(columnToIndex.get(CSVConst.Group_Expenses), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Recovered_Penalty), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Bank_Balance), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Cash_in_Hand), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Net_Profit), groups.get(i).getDescription());
			data.get(j).set(columnToIndex.get(CSVConst.Group_Unused_End), groups.get(i).getDescription());
			
		}
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
	
	protected static List<List<String>> initData(int rows, int columns) {
		List<List<String>> data = new ArrayList<List<String>>(rows);
		
		for(int i = 0; i < rows; i++) {
			List<String> colData = new ArrayList<String>(columns);
			for(int j = 0; j < rows; j++) {
				colData.add(DataUtil.EMPTY_STRING);
			}
			data.add(colData);
		}
		return data;
	}
	
}
