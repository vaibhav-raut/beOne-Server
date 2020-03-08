package com.beone.shg.net.persistence.spo.fac;

public class AbstractAlgoFactory {

	public static final String M_SAVING_INTEREST_COMPUTE = "M_SAVING_INTEREST_COMPUTE";
	public static final String M_CREDIT_RATING = "M_CREDIT_RATING";
	public static final String G_CREDIT_RATING = "G_CREDIT_RATING";
	public static final String M_BUILD_TODO_TXS = "M_BUILD_TODO_TXS";
	public static final String M_AUTO_APPROVE_TXS = "M_AUTO_APPROVE_TXS";
	public static final String M_TODO_TX_OVERDUE = "M_TODO_TX_OVERDUE";
	public static final String M_TODO_TX_MISSED = "M_TODO_TX_MISSED"; 
	public static final String M_COMP_CUMULATIVE_SAVED = "M_COMP_CUMULATIVE_SAVED";
	public static final String M_UPDATE_CUMULATIVE_SAVING = "M_UPDATE_CUMULATIVE_SAVING";
	public static final String G_SAVING_MONTHLY_ACCOUNT = "G_SAVING_MONTHLY_ACCOUNT";
	public static final String M_SAVING_MONTHLY_ACCOUNT = "M_SAVING_MONTHLY_ACCOUNT";
	public static final String M_GENERATE_OLD_OUTSTANDING_SAVING = "M_GENERATE_OLD_OUTSTANDING_SAVING";
	public static final String M_GENERATE_CLEAN_OLD_OUTSTANDING_SAVING = "M_GENERATE_CLEAN_OLD_OUTSTANDING_SAVING";
	
	public static AlgoFactoryI getFactory(String type) {
		
		switch(type) {

		case M_SAVING_INTEREST_COMPUTE:
			return SIComFactory.getInstance();

		case M_CREDIT_RATING:
			return MCRComFactory.getInstance();
			
		case G_CREDIT_RATING:
			return GCRComFactory.getInstance();
			
		case M_BUILD_TODO_TXS:
			return MBuildTodoTxsFactory.getInstance();
			
		case M_AUTO_APPROVE_TXS:
			return AutoApproveFactory.getInstance();
			
		case M_TODO_TX_OVERDUE:
			return OverDueTodoTxFactory.getInstance();
			
		case M_TODO_TX_MISSED:
			return MissedTodoTxFactory.getInstance();
			
		case M_COMP_CUMULATIVE_SAVED:
			return CumSavingComputFactory.getInstance();
			
		case M_UPDATE_CUMULATIVE_SAVING:
			return UpdateCumSavingFactory.getInstance();	
			
		case G_SAVING_MONTHLY_ACCOUNT:
			return MonthlyAcSaveFactory.getInstance();	
			
		case M_SAVING_MONTHLY_ACCOUNT:
			return MonthlyMAcSaveFactory.getInstance();	
			
		case M_GENERATE_OLD_OUTSTANDING_SAVING:
			return MBuildOutSavingFactory.getInstance();	
			
		case M_GENERATE_CLEAN_OLD_OUTSTANDING_SAVING:
			return MBuildCleanOutSavingFactory.getInstance();	
		}
		return null;
	}
}
