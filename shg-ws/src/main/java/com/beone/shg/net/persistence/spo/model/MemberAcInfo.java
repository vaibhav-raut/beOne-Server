package com.beone.shg.net.persistence.spo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.ppo.util.ProcessJobBuilder;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.shg.net.persistence.util.EnumUtil;

public class MemberAcInfo {
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(MemberAcInfo.class);

	protected DAOFactory daoFactory;
	protected ProcessJobBuilder processJobBuilder;
	protected MProfile mprofile;
	protected MAc ac;
	protected long startTime;
	protected long endTime;
	protected List<MSavingAc> savingAcs;
	protected List<MLoanAc> loanAcs;
	protected List<MSavingAc> allSavingAcs;
	protected List<MLoanAc> allLoanAcs;
	protected Set<Tx> transactions;
	protected Set<TxTodo> todoTransactions;
	protected Set<TxTodo> nextTodoTransactions;
	
	public MemberAcInfo(DAOFactory daoFactory, ProcessJobBuilder processJobBuilder, MProfile mprofile, MAc ac, long startTime, long endTime) {
		super();
		this.daoFactory = daoFactory;
		this.processJobBuilder = processJobBuilder;
		this.mprofile = mprofile;
		this.ac = ac;
		this.startTime = startTime;
		this.endTime = endTime;
		this.savingAcs = new ArrayList<MSavingAc>(5);
		this.loanAcs = new ArrayList<MLoanAc>(5);
		this.allSavingAcs = new ArrayList<MSavingAc>(5);
		this.allLoanAcs = new ArrayList<MLoanAc>(5);
		this.transactions = new HashSet<Tx>(10);
		this.todoTransactions = new HashSet<TxTodo>(5);
		this.nextTodoTransactions = new HashSet<TxTodo>(5);
		
		for(MSavingAc savingAc: ac.getMSavingAcs()) {
			if(savingAc.getApprovedDate() != null &&
					savingAc.getAccountStatusId() > 0 &&
					(savingAc.getApprovedDate().getTime() < this.endTime) &&
					EnumUtil.isAccountActiveToCompute(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, savingAc.getAccountStatusId()))) {
				
				this.savingAcs.add(savingAc);
			} else {
				String message = "Can't Compute for MSavingAc of MAcNo: " + ac.getMAcNo();

				if(savingAc.getApprovedDate() != null) {
					message = message + "; ApprovedDate: " + savingAc.getApprovedDate();
				} else {
					message = message + "; ApprovedDate: null";
				}
				if(savingAc.getAccountStatusId() > 0) {
					message = message + "; AccountStatus: " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, savingAc.getAccountStatusId());
				} else {
					message = message + "; AccountStatus: null";
				}
				
				LOGGER.warn(message);
			}
			
			this.allSavingAcs.add(savingAc);			
		}
		
		for(MLoanAc loanAc: ac.getMLoanAcs()) {
			if(loanAc.getApprovedDate() != null &&
					loanAc.getAccountStatusId() > 0 &&
					(loanAc.getApprovedDate().getTime() < this.endTime) &&
					EnumUtil.isAccountActiveToCompute(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, loanAc.getAccountStatusId()))) {
				
				this.loanAcs.add(loanAc);
			} else {
				String message = "Can't Compute for MLoanAc of MAcNo: " + ac.getMAcNo();

				if(loanAc.getApprovedDate() != null) {
					message = message + "; ApprovedDate: " + loanAc.getApprovedDate();
				} else {
					message = message + "; ApprovedDate: null";
				}
				if(loanAc.getAccountStatusId() > 0) {
					message = message + "; AccountStatus: " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, loanAc.getAccountStatusId());
				} else {
					message = message + "; AccountStatus: null";
				}
				
				LOGGER.warn(message);
			}
			
			this.allLoanAcs.add(loanAc);
		}
	}
	
	public void clear() {
		daoFactory = null;;
		mprofile = null;
		ac = null;
		savingAcs.clear();
		loanAcs.clear();
		transactions.clear();
		todoTransactions.clear();
		nextTodoTransactions.clear();
	}
	
	public void addTransaction(Tx transaction) {
		this.transactions.add(transaction);
	}
	
	public void addTodoTransaction(TxTodo todoTransaction) {
		this.todoTransactions.add(todoTransaction);
	}
	
	public void addNextTodoTransaction(TxTodo todoTransaction) {
		this.nextTodoTransactions.add(todoTransaction);
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public ProcessJobBuilder getProcessJobBuilder() {
		return processJobBuilder;
	}

	public MProfile getMprofile() {
		return mprofile;
	}

	public MAc getAc() {
		return ac;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public List<MSavingAc> getSavingAcs() {
		return savingAcs;
	}

	public List<MLoanAc> getLoanAcs() {
		return loanAcs;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public List<MSavingAc> getAllSavingAcs() {
		return allSavingAcs;
	}

	public List<MLoanAc> getAllLoanAcs() {
		return allLoanAcs;
	}

	public Set<Tx> getTransactions() {
		return transactions;
	}

	public Set<TxTodo> getTodoTransactions() {
		return todoTransactions;
	}

	public Set<TxTodo> getNextTodoTransactions() {
		return nextTodoTransactions;
	}
}
