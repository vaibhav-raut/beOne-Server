package com.beone.shg.net.persistence.spo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.ReportBO;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.ppo.util.ProcessJobBuilder;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.fac.AbstractAlgoFactory;
import com.beone.shg.net.persistence.spo.fac.AlgoFactoryI;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

public class GroupAcInfo {
	
	protected final static Logger LOGGER = LoggerFactory.getLogger(GroupAcInfo.class);
	
	protected DAOFactory daoFactory;
	protected ReportBO reportBO;
	protected ProcessJobBuilder processJobBuilder;
	protected long groupAcNo;
	protected long startTime;
	protected long endTime;
	protected String month;
	protected GProfile gprofile;
	protected GAc ac;
	protected GRules gRules;
	protected Map<Long, MemberAcInfo> memberAcInfos;
	protected List<GLoanAc> gLoanAcs;
	protected List<String> poConstList;
	protected List<GenericPO> processObjects;
	protected Set<Tx> transactions;
	protected Set<TxTodo> todoTransactions;
	protected Set<TxTodo> nextTodoTransactions;
	protected Date computationDate;

	public GroupAcInfo(DAOFactory daoFactory, ReportBO reportBO, ProcessJobBuilder processJobBuilder, long groupAcNo, long startTime, long endTime, List<String> poConstList) {
		super();
		this.daoFactory = daoFactory;
		this.reportBO = reportBO;
		this.processJobBuilder = processJobBuilder;
		this.groupAcNo = groupAcNo;
		this.startTime = startTime;
		this.endTime = endTime;	
		this.month = DateUtil.getMonthYearShort(startTime);
		this.poConstList = poConstList;	
		this.transactions = new HashSet<Tx>(20);
		this.todoTransactions = new HashSet<TxTodo>(10);
		this.nextTodoTransactions = new HashSet<TxTodo>(10);

		// Load All Group Related Info for the given Time frame
		loadGroupInfo();
		
		// Load All Process Objects
		loadGroupPOs();
	}
	
	public void loadGroupInfo() {
		
		// Load Group Account
		ac = daoFactory.getGAcDAO().findById(groupAcNo);
		
		// Load Group Profile
		gprofile = ac.getGProfile();
		
		// Load Group Rules
		gRules = daoFactory.getGRulesDAO().findById(groupAcNo);
		
		// Load Group Loan Account
		gLoanAcs = daoFactory.getGLoanAcDAO().getAllAcForGroup(groupAcNo);
		
		// Created Member Account Info List
		memberAcInfos = new LinkedHashMap<Long, MemberAcInfo>();
				
		// Load Group Members Accounts
		List<MAc> mAccounts = daoFactory.getMAcDAO().getAllGroupMembers(groupAcNo);		
		
		// Load All Member Account Info with conditions:
		// 1 - created before time of computation
		// 2 - is Active to Compute
		// 3 - real member to compute (core or associate)
		for(MAc mAccount: mAccounts) {
			if(mAccount.getMProfile() != null &&
					mAccount.getMProfile().getDateOfEnroll() != null &&
					mAccount.getMProfile().getActiveStatusId() > 0 &&
					mAccount.getMProfile().getMroleId() > 0 &&
					(mAccount.getMProfile().getDateOfEnroll().getTime() < endTime) &&
					EnumUtil.isActiveToCompute(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mAccount.getMProfile().getActiveStatusId())) &&
					EnumUtil.isComputeForMember(EnumCache.getMRoleValue(mAccount.getMProfile().getMroleId()).getRoleCategory())) {

				memberAcInfos.put(mAccount.getMAcNo(), new MemberAcInfo(daoFactory, processJobBuilder, mAccount.getMProfile(), mAccount, startTime, endTime));	
			} else {
				String message = "MAcNo: " + mAccount.getMAcNo();
				message = message + "; DateOfEnroll: " + mAccount.getMProfile().getDateOfEnroll();

				if(mAccount.getMProfile().getActiveStatusId() > 0) {
					message = message + "; ActiveStatus: " + EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mAccount.getMProfile().getActiveStatusId());
				} else {
					message = message + "; ActiveStatus: null";
				}
				if(mAccount.getMProfile().getMroleId() > 0) {
					message = message + "; MRoleCategory: " + EnumCache.getNameOfMRole(mAccount.getMProfile().getMroleId());
				} else {
					message = message + "; MRoleCategory: null";
				}
				
				LOGGER.warn(message);
			}
		}
		
		// Load All Transactions for the Group Members
		List<Tx> transactions = daoFactory.getTxDAO().getAllTxsForGroup(groupAcNo, startTime, endTime);
		for(Tx transaction: transactions) {
			if(transaction.getMemberAcNo() > 0) {
				if(memberAcInfos.containsKey(transaction.getMemberAcNo())) {
					memberAcInfos.get(transaction.getMemberAcNo()).addTransaction(transaction);
				}
			}
			else {
				this.transactions.add(transaction);
			}
		}

		// Load All TodoTransactions for the Group & Members
		List<TxTodo> todoTransactions = daoFactory.getTxTodoDAO().getAllTxsForGroup(groupAcNo, startTime, endTime);
		for(TxTodo todoTransaction: todoTransactions) {
			if(todoTransaction.getMemberAcNo() > 0) {
				if(memberAcInfos.containsKey(todoTransaction.getMemberAcNo())) {
					memberAcInfos.get(todoTransaction.getMemberAcNo()).addTodoTransaction(todoTransaction);
				}
			}
			else {
				this.todoTransactions.add(todoTransaction);
			}
		}		

		// Load All Next TodoTransactions for the Group & Members
		List<TxTodo> nextTodoTx = daoFactory.getTxTodoDAO().getAllTxsForGroup(groupAcNo, endTime, DateUtil.getEndTimeOfMonth(endTime));
		for(TxTodo todoTransaction: nextTodoTx) {
			if(todoTransaction.getMemberAcNo() > 0) {
				if(memberAcInfos.containsKey(todoTransaction.getMemberAcNo())) {
					memberAcInfos.get(todoTransaction.getMemberAcNo()).addNextTodoTransaction(todoTransaction);
				}
			}		
			else {
				this.nextTodoTransactions.add(todoTransaction);
			}
		}
		
		computationDate = DateUtil.getDayOfMonth(endTime, gRules.getComputeDayOfMonth());
	}
	
	public void loadGroupPOs() {
		processObjects = new ArrayList<GenericPO>();

		for(String poConst: poConstList) {
			AlgoFactoryI factory = AbstractAlgoFactory.getFactory(poConst);

			if(factory != null) {
				processObjects.add(factory.getAlgo(poConst, this));
			} else {
				LOGGER.error("Not able to Get AlgoFactoryI for: " + poConst);
			}
		}
	}
	
	public void clear() {
		for(MemberAcInfo memberAcInfo: memberAcInfos.values()) {
			memberAcInfo.clear();
		}
		gprofile = null;
		ac = null;
		memberAcInfos.clear();
	}
	
	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public ReportBO getReportBO() {
		return reportBO;
	}

	public ProcessJobBuilder getProcessJobBuilder() {
		return processJobBuilder;
	}

	public long getGroupAcNo() {
		return groupAcNo;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public String getMonth() {
		return month;
	}

	public GProfile getGprofile() {
		return gprofile;
	}

	public GAc getAc() {
		return ac;
	}

	public GRules getGRules() {
		return gRules;
	}

	public Map<Long, MemberAcInfo> getMemberAcInfos() {
		return memberAcInfos;
	}

	public List<GLoanAc> getgLoanAcs() {
		return gLoanAcs;
	}

	public List<GenericPO> getProcessObjects() {
		return processObjects;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public Set<Tx> getTransactions() {
		return transactions;
	}

	public Set<TxTodo> getTodoTransactions() {
		return todoTransactions;
	}

	public Date getComputationDate() {
		return computationDate;
	}
}
