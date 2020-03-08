package com.beone.shg.net.config;

public class EnumConst {

	public static final long SystemAcNo = 1000010001l;
	
	public static final String BankBook = "Bank Book";
	public static final String CashBook = "Cash Book";
	public static final String JointBook = "Joint Book";
	public static final String AsOnDate = "As On Date";

	// All Enum Name 
	public static final String AccountStatus = "AccountStatus";
	public static final String InvestmentType = "InvestmentType";
	public static final String ActiveStatus = "ActiveStatus";
	public static final String ApprovalStatus = "ApprovalStatus";
	public static final String BankAccountType = "BankAccountType";
	public static final String DocType = "DocType";
	public static final String GroupRelation = "GroupRelation";
	public static final String GroupType = "GroupType";
	public static final String FundType = "FundType";
	public static final String Lang = "Lang";
	public static final String LoanCalculation = "LoanCalculation";
	public static final String LoanSource = "LoanSource";
	public static final String MessageType = "MessageType";
	public static final String MRole = "MRole";
	public static final String MProfilingType = "MProfilingType";
	public static final String NameTitle = "NameTitle";
	public static final String PaymentMode = "PaymentMode";
	public static final String ReasonToUndo = "ReasonToUndo";
	public static final String RecoveryPeriod = "RecoveryPeriod";
	public static final String TxStatus = "TxStatus";
	public static final String TxTodoStatus = "TxTodoStatus";
	public static final String TxType = "TxType";
	public static final String UiAccessCode = "UiAccessCode";
	public static final String WsAccessCode = "WsAccessCode";
	public static final String MrUiAccessCode = "MrUiAccessCode";
	public static final String MrWsAccessCode = "MrWsAccessCode";

	// AccountStatus Enum Name 
	public static final String AccountStatus_Draft = "Draft";
	public static final String AccountStatus_Submitted = "Submitted";
	public static final String AccountStatus_Active = "Active";
	public static final String AccountStatus_Rejected = "Rejected";
	public static final String AccountStatus_Sub_Standard = "Sub Standard";
	public static final String AccountStatus_Bad_Debt = "Bad Debt";
	public static final String AccountStatus_Foreclosed = "Foreclosed";
	public static final String AccountStatus_Bad_Debt_Closed = "Bad Debt Closed";
	public static final String AccountStatus_Complete = "Complete";
	public static final String AccountStatus_Under_Review = "Under Review";
	public static final String AccountStatus_On_Hold = "On Hold";
	public static final String AccountStatus_Sent_Back = "Sent Back";
	public static final String AccountStatus_Approved = "Approved";
	
	public static int getAccountStatusOrder(int accountStatusId) {
		switch(accountStatusId) {
		case 1:
			return 1;
			
		case 2:
			return 2;
			
		case 3:
			return 7;
			
		case 4:
			return 13;
			
		case 5:
			return 8;
			
		case 6:
			return 9;
			
		case 7:
			return 10;
			
		case 8:
			return 11;
			
		case 9:
			return 12;
			
		case 10:
			return 3;
			
		case 11:
			return 4;
			
		case 12:
			return 5;
			
		case 13:
			return 6;
			
		}
		
		return 100;
	}

	// ActiveStatus Enum Values
	public static final String ActiveStatus_Requested = "Requested";
	public static final String ActiveStatus_Active = "Active";
	public static final String ActiveStatus_Inactive = "Inactive";
	public static final String ActiveStatus_Idle = "Idle";
	public static final String ActiveStatus_Locked = "Locked";
	public static final String ActiveStatus_Completed = "Completed";
	public static final String ActiveStatus_Closed = "Closed";
	public static final String ActiveStatus_Junk = "Junk";

	// ApprovalStatus Enum Values
	public static final String ApprovalStatus_Draft = "Draft";
	public static final String ApprovalStatus_Submitted = "Submitted";
	public static final String ApprovalStatus_Under_Review = "Under Review";
	public static final String ApprovalStatus_Approved = "Approved";
	public static final String ApprovalStatus_Rejected = "Rejected";
	public static final String ApprovalStatus_On_Hold = "On Hold";
	public static final String ApprovalStatus_Sent_Back = "Sent Back";
	
	// BankAccountType Enum Values
	public static final String BankAccountType_Saving_Account = "Saving Account";
	public static final String BankAccountType_Loan_Account = "Loan Account";
	public static final String BankAccountType_Investment_Account = "Investment Account";
	public static final String BankAccountType_Project_Account = "Project Account";
	public static final String BankAccountType_Current_Account = "Current Account";
	public static final String BankAccountType_Fix_Deposit_Account = "Fix Deposit Account";
	public static final String BankAccountType_Over_Draft_Account = "Over Draft Account";

	// DocType Enum Values
	public static final String DocType_Miscellaneous = "Miscellaneous";

	// Gender Enum Values
	public static final String Gender_Unknown = "Unknow";
	
	// GroupType Enum Values
	public static final String GroupType_SHG = "SHG";
	public static final String GroupType_Federation = "Federation";
	public static final String GroupType_NGO = "NGO";
	public static final String GroupType_Bank = "Bank";
	public static final String GroupType_GOV = "GOV";
	public static final String GroupType_SHG_One_Agent = "SHG-One Agent";
	public static final String GroupType_Area_Admin = "Area Admin";
	public static final String GroupType_Super_Area_Admin = "Super Area Admin";
	public static final String GroupType_SHG_One_Admin = "SHG-One Admin";

	public static final String GroupType_Udaan_Admin = "Udaan Admin";
	public static final String GroupType_Mega_HUB = "Mega HUB";
	public static final String GroupType_Local_HUB = "Local HUB";
	public static final String GroupType_Manufacturer = "Manufacturer";
	public static final String GroupType_Micro_Retailer = "Micro Retailer";
	public static final String GroupType_Sales_Executive = "Sales Executive";
	public static final String GroupType_Super_Sales_Executive = "Super Sales Executive";
	
	public static final String District_Super_Admin = "Super Admin";
	public static final String District_Udaan = "Udaan";
	public static final String District_Vendor = "Vendor";

	public static final String District_Code_Super_Admin = "AD01";
	public static final String District_Code_Udaan = "UD01";
	public static final String District_Code_Vendor = "UD02";

	public static final String District_State_Admin = "Admin";

	// FundType Enum Values
	public static final String FundType_Consumption_Loan = "Consumption Loan";
	public static final String FundType_Individual_Development_Fund = "Individual Development Fund";
	public static final String FundType_Project_Development_Fund = "Project Development Fund";
	public static final String FundType_Group_Project_Development_Fund = "Group Project Development Fund";
	public static final String FundType_Socio_Care_Fund = "Socio Care Fund";
	public static final String FundType_Revolving_Fund = "Revolving Fund";
	public static final String FundType_Gov_Development_Fund = "Gov Development Fund";
	
	// InvestmentType Enum Values
	public static final String InvestmentType_Project_Development = "Project Development";
	public static final String InvestmentType_Fix_Deposit = "Fix Deposit";
	public static final String InvestmentType_Investment_Account = "Investment Account";
	public static final String InvestmentType_Over_Draft_Account = "Over Draft Account";
	public static final String InvestmentType_Mutual_Fund = "Mutual Fund";

	// MRole Enum Values
	public static final String MRole_Associate_Member = "Associate Member";
	public static final String MRole_Core_Member = "Core Member";
	public static final String MRole_Group_Accountant = "Group Accountant";
	public static final String MRole_Group_Secretary = "Group Secretary";
	public static final String MRole_Group_Treasure = "Group Treasure";
	public static final String MRole_Group_President = "Group President";
	public static final String MRole_NGO_Agent = "NGO Agent";
	public static final String MRole_NGO_Support_Admin = "NGO Support Admin";
	public static final String MRole_NGO_Admin = "NGO Admin";
	public static final String MRole_Bank_Auditor = "Bank Auditor";
	public static final String MRole_Area_Agent = "Area Agent";
	public static final String MRole_Area_Support_Admin = "Area Support Admin";
	public static final String MRole_Area_Admin = "Area Admin";
	public static final String MRole_Super_Area_Support_Admin = "Super Area Support Admin";
	public static final String MRole_Super_Area_Admin = "Super Area Admin";
	public static final String MRole_SHG_One_Admin = "SHG-One Admin";
	public static final String MRole_Super_Admin = "Super Admin";	

	public static final String MRole_Udaan_Admin = "Udaan Admin";	
	public static final String MRole_Mega_HUB_Manager = "Mega HUB Manager";	
	public static final String MRole_Local_HUB_Manager = "Local HUB Manager";	
	public static final String MRole_Micro_Retailer = "Micro Retailer";	
	public static final String MRole_Sales_Executive = "Sales Executive";	
	public static final String MRole_Sub_Local_HUB_Manager = "Sub Local HUB Manager";	
	public static final String MRole_Super_Sales_Executive = "Super Sales Executive";	

	public static final String MRole_Cat_SHG_Member = "SHG Member";
	
	public static final String MRole_System_SHG = "SHG";
	public static final String MRole_System_Micro_Retailer = "Micro Retailer";
	

	// RecoveryPeriod Enum Values
	public static final String RecoveryPeriod_Monthly = "Monthly";
	
	// PaymentMode Enum Values
	public static final String PaymentMode_CASH = "CASH";
	public static final String PaymentMode_CHEQUE = "CHEQUE";
	public static final String PaymentMode_DEBIT_CARD = "DEBIT CARD";
	public static final String PaymentMode_CREDIT_CARD = "CREDIT CARD";
	public static final String PaymentMode_DEMAND_DRAFT = "DEMAND DRAFT";
	public static final String PaymentMode_NET_BANKING = "NET BANKING";
	public static final String PaymentMode_PREPAID_CARD = "PREPAID CARD";
	public static final String PaymentMode_OUTSTANDING = "OUTSTANDING";
	public static final String PaymentMode_INTERNAL = "INTERNAL";
	public static final String PaymentMode_Bank = "Bank";

	// Lang Enum Values
	public static final String Lang_English = "English";

	// LoanCalculation Enum Values
	public static final String LoanCalculation_Normal_EMI = "Normal EMI";
	public static final String LoanCalculation_Fixed_EMI = "Fixed EMI";
	public static final String LoanCalculation_Reducing_Interest = "Reducing Interest";

	// LoanSource Enum Values
	public static final String LoanSource_SHG = "SHG";
	public static final String LoanSource_BANK = "BANK";
	public static final String LoanSource_PARENT_SHG = "PARENT SHG";
	public static final String LoanSource_OTHER_SHG = "OTHER SHG";

	// SlipType Enum Values
	public static final String SlipType_RECEIPT = "RECEIPT";
	public static final String SlipType_VOUCHER = "VOUCHER";
	public static final String SlipType_TRANSFER = "TRANSFER";
	public static final String SlipType_INTERNAL = "INTERNAL";

	// Report Enum Values
	public static final String Report_Trail_Balance_Sheet = "Trail Balance Sheet";
	public static final String Report_Profi_Loss_Statement = "Profit & Loss Statement";
	public static final String Report_Balance_Sheet = "Balance Sheet";
	public static final String Report_Profi_Loss_Statement_Projected = "Profit & Loss Statement Proj";
	public static final String Report_Balance_Sheet_Projected = "Balance Sheet Proj";

	// ReportSheetFormat Enum Values
	public static final String ReportSheetFormat_BANK = "BANK";
	public static final String ReportSheetFormat_DETAILED = "DETAILED";

	// ReportSheetName Enum Values
	public static final String ReportSheetName_Profits_DETAILED = "Profits DETAILED";
	public static final String ReportSheetName_Losses_DETAILED = "Losses DETAILED";

	// TxStatus Enum Values
	public static final String TxStatus_Draft = "Draft";
	public static final String TxStatus_Submitted = "Submitted";
	public static final String TxStatus_Missing_Bank_Info = "Missing Bank Info";
	public static final String TxStatus_Payment_Tobe_Done = "Payment Tobe Done";
	public static final String TxStatus_Complete = "Complete";
	public static final String TxStatus_Approved = "Approved";
	public static final String TxStatus_Rejected = "Rejected";
	public static final String TxStatus_Undone = "Undone";
	public static final String TxStatus_Auto_Approved = "Auto Approved";
	public static final String TxStatus_Auto_Rejected = "Auto Rejected";
	public static final String TxStatus_Countered = "Countered";
	public static final String TxStatus_Counter_Submitted = "Counter Submitted";
	public static final String TxStatus_Counter_Approved = "Counter Approved";

	// TxTodoType Enum Values
	public static final String TxAcType_Member_Saving = "Member Saving";
	public static final String TxAcType_Member_Loan = "Member Loan";
	public static final String TxAcType_Group_Loan = "Group Loan";
	public static final String TxAcType_Project_Development = "Project Development";
	public static final String TxAcType_Fix_Deposit = "Fix Deposit";
	public static final String TxAcType_Group_Investment = "Group Investment";

	// TxTodoStatus Enum Values
	public static final String TxTodoStatus_Draft = "Draft";
	public static final String TxTodoStatus_Todo = "Todo";
	public static final String TxTodoStatus_Done = "Done";
	public static final String TxTodoStatus_Undone = "Undone";
	public static final String TxTodoStatus_Over_Due = "Over Due";
	public static final String TxTodoStatus_Missed = "Missed";
	public static final String TxTodoStatus_Approved = "Approved";
	public static final String TxTodoStatus_Rejected = "Rejected";
	public static final String TxTodoStatus_Auto_Approved = "Auto Approved";
	public static final String TxTodoStatus_Auto_Rejected = "Auto Rejected";
	public static final String TxTodoStatus_Previous_Missed = "Previous Missed";
	
	// TxType Enum Values
	public static final String TxType_Saving_Installment = "Saving Installment";
	public static final String TxType_Provisional_Interest_Earned = "Provisional Interest Earned";
	public static final String TxType_Compute_Cumulative_Saving = "Compute Cumulative Saving";
	public static final String TxType_Saving_Returned = "Saving Returned";
	public static final String TxType_Saving_Interest_Returned = "Saving Interest Returned";
	public static final String TxType_Loan_Disbursement = "Loan Disbursement";
	public static final String TxType_Loan_Installment = "Loan Installment";
	public static final String TxType_Loan_Interest_Installment = "Loan Interest Installment";
	public static final String TxType_Loan_Prepayment = "Loan Prepayment";
	public static final String TxType_Pre_Interest_Installment = "Pre Interest Installment";
	public static final String TxType_Bad_Debt_Closure = "Bad Debt Closure";
	public static final String TxType_Group_Project_Dev_Fund = "Group Project Dev Fund";
	public static final String TxType_Group_Project_Recovery = "Group Project Recovery";
	public static final String TxType_Group_Project_Earning = "Group Project Earning";
	public static final String TxType_Bank_Loan_Disbursement = "Bank Loan Disbursement";
	public static final String TxType_Bank_Loan_Installment = "Bank Loan Installment";
	public static final String TxType_Bank_Loan_Interest_Installment = "Bank Loan Interest Installment";
	public static final String TxType_Bank_Loan_Prepayment = "Bank Loan Prepayment";
	public static final String TxType_Bank_Pre_Interest_Installment = "Bank Pre Interest Installment";
	public static final String TxType_Bank_Withdrawal = "Bank Withdrawal";
	public static final String TxType_Bank_Transfer = "Bank Transfer";
	public static final String TxType_Divided_Share_Paid = "Divided Share Paid";
	public static final String TxType_Profit_Share_Paid = "Profit Share Paid";
	public static final String TxType_Divided_Share_Declare = "Divided Share Declare";
	public static final String TxType_Profit_Share_Declare = "Profit Share Declare";
	public static final String TxType_Bank_Interest_Earned = "Bank Interest Earned";
	public static final String TxType_Fix_Deposit_Investment = "Fix Deposit Investment";
	public static final String TxType_Fix_Deposit_Recovery = "Fix Deposit Recovery";
	public static final String TxType_Fix_Deposit_Interest = "Fix Deposit Interest";
	public static final String TxType_Other_Investment = "Other Investment";
	public static final String TxType_Other_Investment_Recovery = "Other Investment Recovery";
	public static final String TxType_Other_Investment_Earning = "Other Investment Earning";
	public static final String TxType_Registration_Fee = "Registration Fee";
	public static final String TxType_Late_Fee = "Late Fee";
	public static final String TxType_Loan_Processing_Fee = "Loan Processing Fee";
	public static final String TxType_Loan_Prepayment_Fee = "Loan Prepayment Fee";
	public static final String TxType_Cheque_Bouncing_Penalty = "Cheque Bouncing Penalty";
	public static final String TxType_Auto_Debit_Failure_Penalty = "Auto Debit Failure Penalty";
	public static final String TxType_Application_Fee = "Application Fee";
	public static final String TxType_Miscellaneous_Fee = "Miscellaneous Fee";
	public static final String TxType_Recovered_Outstanding_Fee = "Recovered Outstanding Fee";
	public static final String TxType_Outstanding_Late_Fee = "Outstanding Late Fee";
	public static final String TxType_Outstanding_Cheque_Bouncing = "Outstanding Cheque Bouncing";
	public static final String TxType_Outstanding_Auto_Debit_Fail = "Outstanding Auto Debit Fail";
	public static final String TxType_Outstanding_Miscellaneous_Fee = "Outstanding Miscellaneous Fee";
	public static final String TxType_Stationary_Expense = "Stationary Expense";
	public static final String TxType_Furniture_Fixture_Expense = "Furniture & Fixture Expense";
	public static final String TxType_Telephone_Postage_Expense = "Telephone & Postage Expense";
	public static final String TxType_Office_Expense = "Office Expense";
	public static final String TxType_Salary_Expense = "Salary Expense";
	public static final String TxType_Advertisement_Expense = "Advertisement Expense";
	public static final String TxType_Meeting_Expense = "Meeting Expense";
	public static final String TxType_Events_Expense = "Events Expense";
	public static final String TxType_Training_Expense = "Training Expense";
	public static final String TxType_Travel_Expense = "Travel Expense";
	public static final String TxType_Miscellaneous_Expense = "Miscellaneous Expense";
	public static final String TxType_Bank_Charges_Expense = "Bank Charges Expense";
	public static final String TxType_Bank_Penalty_Expense = "Bank Penalty Expense";
	public static final String TxType_Accounting_Charges_Expense = "Accounting Charges Expense";
	public static final String TxType_SHGOne_Charges_Expense = "SHGOne Charges Expense";
	public static final String TxType_Outstanding_Saving_Inst = "Outstanding Saving Inst";
	
	public static final String TxType_RECEIPT = "RECEIPT";
	public static final String TxType_VOUCHER = "VOUCHER";
	public static final String TxType_FEE = "FEE";
	public static final String TxType_OUTSTANDING_FEE = "OUTSTANDING FEE";
	public static final String TxType_PAYABLE = "PAYABLE";
	public static final String TxType_ASSET = "ASSET";
	public static final String TxType_PROFIT = "PROFIT";
	public static final String TxType_TRANSFER = "TRANSFER";
	public static final String TxType_LOSS = "LOSS";
	public static final String TxType_EXPENSE = "EXPENSE";
	public static final String TxType_MEMBER = "MEMBER";
	public static final String TxType_BANK = "BANK";
	
	// All Enum Name 
	public static final String State_Admin = "Admin";

	// Lock
	public static final String Lock_Action_LOCK = "LOCK";
	public static final String Lock_Action_UNLOCK = "UNLOCK";
	public static final String Lock_Action_CHECK = "CHECK";

	// SERVICES
	public static final String Lock_Service_Login = "Login";

	// CSV Update Type
	public static final String Update_Type_Add_Members = "Add Members";
	public static final String Update_Type_Update_Group_Account = "Update Group Account";
	public static final String Update_Type_Update_Members_Accounts = "Update Members Accounts";
	public static final String Update_Type_Add_Active_Transactions = "Add Active Transactions";
	public static final String Update_Type_Add_Dead_Transactions = "Add Dead Transactions";
	public static final String Update_Type_Add_Group_Bank_Accounts = "Add Group Bank Accounts";
	public static final String Update_Type_Update_Group_Bank_Accounts = "Update Group Bank Accounts";
	public static final String Update_Type_Add_Group_Loans = "Add Group Loans";
	public static final String Update_Type_Update_Group_Loans = "Update Group Loans";
	public static final String Update_Type_Add_Group_Investments = "Add Group Investments";
	public static final String Update_Type_Update_Group_Investments = "Update Group Investments";
	public static final String Update_Type_Add_Bank_Profiles = "Add Bank Profiles";
}
