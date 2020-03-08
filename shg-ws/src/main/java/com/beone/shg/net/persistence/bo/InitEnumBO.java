package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.auth.MrUiAccessCodeUtil;
import com.beone.shg.net.auth.MrWsAccessCodeUtil;
import com.beone.shg.net.auth.UiAccessCodeUtil;
import com.beone.shg.net.auth.WsAccessCodeUtil;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.AccountStatus;
import com.beone.shg.net.persistence.model.DocType;
import com.beone.shg.net.persistence.model.InvestmentType;
import com.beone.shg.net.persistence.model.ActiveStatus;
import com.beone.shg.net.persistence.model.ApprovalStatus;
import com.beone.shg.net.persistence.model.BankAccountType;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.model.GroupRelation;
import com.beone.shg.net.persistence.model.GroupType;
import com.beone.shg.net.persistence.model.Lang;
import com.beone.shg.net.persistence.model.LoanCalculation;
import com.beone.shg.net.persistence.model.FundType;
import com.beone.shg.net.persistence.model.LoanSource;
import com.beone.shg.net.persistence.model.MProfilingType;
import com.beone.shg.net.persistence.model.MRole;
import com.beone.shg.net.persistence.model.MessageType;
import com.beone.shg.net.persistence.model.MonthlyReport;
import com.beone.shg.net.persistence.model.MonthlyReportSheet;
import com.beone.shg.net.persistence.model.MrUiAccessCode;
import com.beone.shg.net.persistence.model.MrWsAccessCode;
import com.beone.shg.net.persistence.model.NameTitle;
import com.beone.shg.net.persistence.model.PaymentMode;
import com.beone.shg.net.persistence.model.ReasonToUndo;
import com.beone.shg.net.persistence.model.RecoveryPeriod;
import com.beone.shg.net.persistence.model.TxStatus;
import com.beone.shg.net.persistence.model.TxTodoStatus;
import com.beone.shg.net.persistence.model.TxType;
import com.beone.shg.net.persistence.model.UiAccessCode;
import com.beone.shg.net.persistence.model.WsAccessCode;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.EnumValue;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MRoleEnum;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportInfo;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportSheetInfo;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportsEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleValue;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("initEnumBO")
public class InitEnumBO extends BaseBO {

	@Autowired
	@Qualifier("uiAccessCodeUtil")
	protected UiAccessCodeUtil uiAccessCodeUtil;

	@Autowired
	@Qualifier("wsAccessCodeUtil")
	protected WsAccessCodeUtil wsAccessCodeUtil;

	@Autowired
	@Qualifier("mrUiAccessCodeUtil")
	protected MrUiAccessCodeUtil mrUiAccessCodeUtil;

	@Autowired
	@Qualifier("mrWsAccessCodeUtil")
	protected MrWsAccessCodeUtil mrWsAccessCodeUtil;

	public void addEnumValues(GenericEnum addEnum) throws BadRequestException {

		if(addEnum.getEnumName() == null || addEnum.getEnumName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		GenericEnum oEnum = EnumCache.getEnumValues(addEnum.getEnumName());
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(EnumValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getEnumValue());
			}
		}

		if(addEnum.getEnumName().equals(EnumConst.AccountStatus)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					AccountStatus accountStatus = new AccountStatus(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getAccountStatusDAO().persist(accountStatus);
				}
			}

			// Reload enum AccountStatus after updates
			loadAccountStatus();

		} else if(addEnum.getEnumName().equals(EnumConst.InvestmentType)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					InvestmentType accountType = new InvestmentType(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getInvestmentTypeDAO().persist(accountType);
				}
			}

			// Reload enum InvestmentType after updates
			loadInvestmentType();

		} else if(addEnum.getEnumName().equals(EnumConst.ActiveStatus)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					ActiveStatus activeStatus = new ActiveStatus(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getActiveStatusDAO().persist(activeStatus);
				}
			}

			// Reload enum ActiveStatus after updates
			loadActiveStatus();

		} else if(addEnum.getEnumName().equals(EnumConst.ApprovalStatus)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					ApprovalStatus approvalStatus = new ApprovalStatus(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getApprovalStatusDAO().persist(approvalStatus);
				}
			}

			// Reload enum ApprovalStatus after updates
			loadApprovalStatus();

		} else if(addEnum.getEnumName().equals(EnumConst.BankAccountType)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					BankAccountType bankAccountType = new BankAccountType(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getBankAccountTypeDAO().persist(bankAccountType);
				}
			}

			// Reload enum BankAccountType after updates
			loadBankAccountType();

		} else if(addEnum.getEnumName().equals(EnumConst.GroupRelation)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					GroupRelation groupRelation = new GroupRelation(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getGroupRelationDAO().persist(groupRelation);
				}
			}

			// Reload enum GroupRelation after updates
			loadGroupRelation();

		} else if(addEnum.getEnumName().equals(EnumConst.Lang)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					Lang lang = new Lang(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLangDAO().persist(lang);
				}
			}

			// Reload enum Lang after updates
			loadLang();

		} else if(addEnum.getEnumName().equals(EnumConst.LoanCalculation)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					LoanCalculation loanCalculation = new LoanCalculation(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLoanCalculationDAO().persist(loanCalculation);
				}
			}

			// Reload enum LoanCalculation after updates
			loadLoanCalculation();

		} else if(addEnum.getEnumName().equals(EnumConst.LoanSource)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					LoanSource loanSource = new LoanSource(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLoanSourceDAO().persist(loanSource);
				}
			}

			// Reload enum LoanSource after updates
			loadLoanSource();

		} else if(addEnum.getEnumName().equals(EnumConst.PaymentMode)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					PaymentMode paymentMode = new PaymentMode(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPaymentModeDAO().persist(paymentMode);
				}
			}

			// Reload enum PaymentMode after updates
			loadPaymentMode();

		} else if(addEnum.getEnumName().equals(EnumConst.ReasonToUndo)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					ReasonToUndo reasonToUndo = new ReasonToUndo(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getReasonToUndoDAO().persist(reasonToUndo);
				}
			}

			// Reload enum ReasonToUndo after updates
			loadReasonToUndo();

		} else if(addEnum.getEnumName().equals(EnumConst.RecoveryPeriod)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					RecoveryPeriod recoveryPeriod = new RecoveryPeriod(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getRecoveryPeriodDAO().persist(recoveryPeriod);
				}
			}

			// Reload enum RecoveryPeriod after updates
			loadRecoveryPeriod();

		} else if(addEnum.getEnumName().equals(EnumConst.TxStatus)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					TxStatus txStatus = new TxStatus(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getTxStatusDAO().persist(txStatus);
				}
			}

			// Reload enum TxStatus after updates
			loadTxStatus();

		} else if(addEnum.getEnumName().equals(EnumConst.TxTodoStatus)) {
			for(EnumValue eVal : addEnum.getEnumValues()) {
				if(oldEnums == null || !oldEnums.contains(eVal.getEnumValue())) {
					TxTodoStatus txTodoStatus = new TxTodoStatus(eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getTxTodoStatusDAO().persist(txTodoStatus);
				}
			}

			// Reload enum TxTodoStatus after updates
			loadTxTodoStatus();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}
	}

	public void updateEnumValues(GenericEnum updateEnum) throws BadRequestException {

		if(updateEnum.getEnumName() == null || updateEnum.getEnumName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}

		if(updateEnum.getEnumName().equals(EnumConst.AccountStatus)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					AccountStatus accountStatus = new AccountStatus(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getAccountStatusDAO().merge(accountStatus);
				}
			}

			// Reload enum AccountStatus after updates
			loadAccountStatus();

		} else if(updateEnum.getEnumName().equals(EnumConst.InvestmentType)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					InvestmentType accountType = new InvestmentType(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getInvestmentTypeDAO().merge(accountType);
				}
			}

			// Reload enum InvestmentType after updates
			loadInvestmentType();

		} else if(updateEnum.getEnumName().equals(EnumConst.ActiveStatus)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					ActiveStatus activeStatus = new ActiveStatus(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getActiveStatusDAO().merge(activeStatus);
				}
			}

			// Reload enum ActiveStatus after updates
			loadActiveStatus();

		} else if(updateEnum.getEnumName().equals(EnumConst.ApprovalStatus)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					ApprovalStatus approvalStatus = new ApprovalStatus(eVal.getEnumIndex(), eVal.getEnumValue());
					//    			ApprovalStatus approvalStatus = new ApprovalStatus(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getApprovalStatusDAO().merge(approvalStatus);
				}
			}

			// Reload enum ApprovalStatus after updates
			loadApprovalStatus();

		} else if(updateEnum.getEnumName().equals(EnumConst.BankAccountType)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					BankAccountType bankAccountType = new BankAccountType(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getBankAccountTypeDAO().merge(bankAccountType);
				}
			}

			// Reload enum BankAccountType after updates
			loadBankAccountType();

		} else if(updateEnum.getEnumName().equals(EnumConst.GroupRelation)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					GroupRelation groupRelation = new GroupRelation(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getGroupRelationDAO().merge(groupRelation);
				}
			}

			// Reload enum GroupRelation after updates
			loadGroupRelation();

		} else if(updateEnum.getEnumName().equals(EnumConst.Lang)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					Lang lang = new Lang(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLangDAO().merge(lang);
				}
			}

			// Reload enum Lang after updates
			loadLang();

		} else if(updateEnum.getEnumName().equals(EnumConst.LoanCalculation)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					LoanCalculation loanCalculation = new LoanCalculation(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLoanCalculationDAO().merge(loanCalculation);
				}
			}

			// Reload enum LoanCalculation after updates
			loadLoanCalculation();

		} else if(updateEnum.getEnumName().equals(EnumConst.LoanSource)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					LoanSource loanSource = new LoanSource(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getLoanSourceDAO().merge(loanSource);
				}
			}

			// Reload enum LoanSource after updates
			loadLoanSource();

		} else if(updateEnum.getEnumName().equals(EnumConst.PaymentMode)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					PaymentMode paymentMode = new PaymentMode(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getPaymentModeDAO().merge(paymentMode);
				}
			}

			// Reload enum PaymentMode after updates
			loadPaymentMode();

		} else if(updateEnum.getEnumName().equals(EnumConst.ReasonToUndo)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					ReasonToUndo reasonToUndo = new ReasonToUndo(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getReasonToUndoDAO().merge(reasonToUndo);
				}
			}

			// Reload enum ReasonToUndo after updates
			loadReasonToUndo();

		} else if(updateEnum.getEnumName().equals(EnumConst.RecoveryPeriod)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					RecoveryPeriod recoveryPeriod = new RecoveryPeriod(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getRecoveryPeriodDAO().merge(recoveryPeriod);
				}
			}

			// Reload enum RecoveryPeriod after updates
			loadRecoveryPeriod();

		} else if(updateEnum.getEnumName().equals(EnumConst.TxStatus)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					TxStatus txStatus = new TxStatus(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getTxStatusDAO().merge(txStatus);
				}
			}

			// Reload enum TxStatus after updates
			loadTxStatus();

		} else if(updateEnum.getEnumName().equals(EnumConst.TxTodoStatus)) {
			for(EnumValue eVal : updateEnum.getEnumValues()) {
				if(eVal.getEnumIndex() > 0) {
					TxTodoStatus txTodoStatus = new TxTodoStatus(eVal.getEnumIndex(), eVal.getEnumValue(), eVal.getDescription());
					daoFactory.getTxTodoStatusDAO().merge(txTodoStatus);
				}
			}

			// Reload enum TxTodoStatus after updates
			loadTxTodoStatus();

		} else {	
			throw new BadRequestException("Invalid Enum Name");
		}   	
	}

	public void loadAllEnumValues() {

		// Load All Enums to Cache
		loadAccountStatus();
		loadInvestmentType();
		loadActiveStatus();
		loadApprovalStatus();
		loadBankAccountType();
		loadGroupRelation();
		loadLang();
		loadLoanCalculation();
		loadLoanSource();
		loadPaymentMode();
		loadReasonToUndo();
		loadRecoveryPeriod();
		loadTxStatus();
		loadTxTodoStatus();
		loadTxType();
		loadGroupType();
		loadNameTitle();
		loadMRole();
		loadFundType();
		loadDistricts();
		loadMonthlyReports();
		loadDocType();
		loadMProfilingType();
		loadMessageType();
		loadUiAccessCode();
		loadWsAccessCode();
		loadMrUiAccessCode();
		loadMrWsAccessCode();
	}

	// Load AccountStatus type Enum as Generic Enum to Cache
	public void loadAccountStatus() {
		String enumName = EnumConst.AccountStatus;
		GenericEnum enu =  new GenericEnum();
		List<AccountStatus> rows = daoFactory.getAccountStatusDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(AccountStatus row: rows) {
			EnumValue eVal = new EnumValue(row.getAccountStatusId(), row.getAccountStatus(), row.getAccountStatusDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load InvestmentType type Enum as Generic Enum to Cache
	public void loadInvestmentType() {
		String enumName = EnumConst.InvestmentType;
		GenericEnum enu =  new GenericEnum();
		List<InvestmentType> rows = daoFactory.getInvestmentTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(InvestmentType row: rows) {
			EnumValue eVal = new EnumValue(row.getInvestmentTypeId(), row.getInvestmentType(), row.getInvestmentTypeDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load ActiveStatus type Enum as Generic Enum to Cache
	public void loadActiveStatus() {
		String enumName = EnumConst.ActiveStatus;
		GenericEnum enu =  new GenericEnum();
		List<ActiveStatus> rows = daoFactory.getActiveStatusDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(ActiveStatus row: rows) {
			EnumValue eVal = new EnumValue(row.getActiveStatusId(), row.getActiveStatus(), row.getActiveStatusDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load ApprovalStatus type Enum as Generic Enum to Cache
	public void loadApprovalStatus() {
		String enumName = EnumConst.ApprovalStatus;
		GenericEnum enu =  new GenericEnum();
		List<ApprovalStatus> rows = daoFactory.getApprovalStatusDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(ApprovalStatus row: rows) {
			EnumValue eVal = new EnumValue(row.getApprovalStatusId(), row.getApprovalStatus(), row.getApprovalStatusDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load BankAccountType type Enum as Generic Enum to Cache
	public void loadBankAccountType() {
		String enumName = EnumConst.BankAccountType;
		GenericEnum enu =  new GenericEnum();
		List<BankAccountType> rows = daoFactory.getBankAccountTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(BankAccountType row: rows) {
			EnumValue eVal = new EnumValue(row.getAccountTypeId(), row.getAccountType(), row.getAccountTypeDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load GroupRelation type Enum as Generic Enum to Cache
	public void loadGroupRelation() {
		String enumName = EnumConst.GroupRelation;
		GenericEnum enu =  new GenericEnum();
		List<GroupRelation> rows = daoFactory.getGroupRelationDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(GroupRelation row: rows) {
			EnumValue eVal = new EnumValue(row.getGroupRelationId(), row.getGroupRelation(), row.getGroupRelationDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load Lang type Enum as Generic Enum to Cache
	public void loadLang() {
		String enumName = EnumConst.Lang;
		GenericEnum enu =  new GenericEnum();
		List<Lang> rows = daoFactory.getLangDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(Lang row: rows) {
			EnumValue eVal = new EnumValue(row.getLangId(), row.getLang(), row.getLangDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load LoanCalculation type Enum as Generic Enum to Cache
	public void loadLoanCalculation() {
		String enumName = EnumConst.LoanCalculation;
		GenericEnum enu =  new GenericEnum();
		List<LoanCalculation> rows = daoFactory.getLoanCalculationDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(LoanCalculation row: rows) {
			EnumValue eVal = new EnumValue(row.getLoanCalculationId(), row.getLoanCalculation(), row.getLoanCalculationDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load LoanSource type Enum as Generic Enum to Cache
	public void loadLoanSource() {
		String enumName = EnumConst.LoanSource;
		GenericEnum enu =  new GenericEnum();
		List<LoanSource> rows = daoFactory.getLoanSourceDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(LoanSource row: rows) {
			EnumValue eVal = new EnumValue(row.getLoanSourceId(), row.getLoanSource(), row.getLoanSourceDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load PaymentMode type Enum as Generic Enum to Cache
	public void loadPaymentMode() {
		String enumName = EnumConst.PaymentMode;
		GenericEnum enu =  new GenericEnum();
		List<PaymentMode> rows = daoFactory.getPaymentModeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(PaymentMode row: rows) {
			EnumValue eVal = new EnumValue(row.getPaymentModeId(), row.getPaymentModeType(), row.getPaymentModeDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load ReasonToUndo type Enum as Generic Enum to Cache
	public void loadReasonToUndo() {
		String enumName = EnumConst.ReasonToUndo;
		GenericEnum enu =  new GenericEnum();
		List<ReasonToUndo> rows = daoFactory.getReasonToUndoDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(ReasonToUndo row: rows) {
			EnumValue eVal = new EnumValue(row.getReasonId(), row.getReason(), row.getReasonDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load RecoveryPeriod type Enum as Generic Enum to Cache
	public void loadRecoveryPeriod() {
		String enumName = EnumConst.RecoveryPeriod;
		GenericEnum enu =  new GenericEnum();
		List<RecoveryPeriod> rows = daoFactory.getRecoveryPeriodDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(RecoveryPeriod row: rows) {
			EnumValue eVal = new EnumValue(row.getRecoveryPeriodId(), row.getPeriod(), row.getRecoveryPeriodDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load TxStatus type Enum as Generic Enum to Cache
	public void loadTxStatus() {
		String enumName = EnumConst.TxStatus;
		GenericEnum enu =  new GenericEnum();
		List<TxStatus> rows = daoFactory.getTxStatusDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(TxStatus row: rows) {
			EnumValue eVal = new EnumValue(row.getTxStatusId(), row.getTxStatus(), row.getTxStatusDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	// Load TxTodoStatus type Enum as Generic Enum to Cache
	public void loadTxTodoStatus() {
		String enumName = EnumConst.TxTodoStatus;
		GenericEnum enu =  new GenericEnum();
		List<TxTodoStatus> rows = daoFactory.getTxTodoStatusDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(TxTodoStatus row: rows) {
			EnumValue eVal = new EnumValue(row.getTxTodoStatusId(), row.getTxTodoStatus(), row.getTxTodoStatusDesc());
			enu.addEnumValue(eVal);
		}
		EnumCache.addEnumValues(enumName, enu);
	}

	public void addTxType(TxTypeEnum enu) {

		TxTypeEnum oEnum = EnumCache.getTxType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(TxTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getTxType());
			}
		}

		for(TxTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getTxType())) {
				TxType txType = new TxType();

				txType.setTxName(eVal.getTxType());
				txType.setTxCategory(eVal.getTxCategory());
				txType.setSlipType(eVal.getSlipType());
				txType.setAmountType(eVal.getAmountType());
				txType.setTxWith(eVal.getTxWith());
				txType.setTxTypeDesc(eVal.getTxDescription());
				txType.setUpdateFormulaOnDone(eVal.getUpdateFormulaOnDone());
				txType.setUpdateFormulaOnUndone(eVal.getUpdateFormulaOnUndone());
				txType.setUpdateFormulaOnApprove(eVal.getUpdateFormulaOnApprove());
				txType.setUpdateFormulaOnReject(eVal.getUpdateFormulaOnReject());

				daoFactory.getTxTypeDAO().persist(txType);
			}
		}

		// Reload enum TxType after updates
		loadTxType();
	}

	public void updateTxType(TxTypeEnum enu) {

		for(TxTypeValue eVal : enu.getEnumValues()) {
			TxType txType = daoFactory.getTxTypeDAO().findById(eVal.getTxTypeId());

			txType.setTxName(eVal.getTxType());
			txType.setTxCategory(eVal.getTxCategory());
			txType.setSlipType(eVal.getSlipType());
			txType.setAmountType(eVal.getAmountType());
			txType.setTxWith(eVal.getTxWith());
			txType.setTxTypeDesc(eVal.getTxDescription());
			txType.setUpdateFormulaOnDone(eVal.getUpdateFormulaOnDone());
			txType.setUpdateFormulaOnUndone(eVal.getUpdateFormulaOnUndone());
			txType.setUpdateFormulaOnApprove(eVal.getUpdateFormulaOnApprove());
			txType.setUpdateFormulaOnReject(eVal.getUpdateFormulaOnReject());

			daoFactory.getTxTypeDAO().merge(txType);
		}

		// Reload enum TxType after updates
		loadTxType();
	}

	// Load TxType type Enum as Generic Enum to Cache
	public void loadTxType() {
		String enumName = EnumConst.TxType;
		TxTypeEnum enu =  new TxTypeEnum();
		List<TxType> rows = daoFactory.getTxTypeDAO().getAllRowList(100);
		List<TxTypeValue> values = new ArrayList<TxTypeValue>();

		for(TxType row: rows) {
			TxTypeValue value = new TxTypeValue();

			value.setTxTypeId(row.getTxTypeId());
			value.setTxType(row.getTxName());
			value.setTxCategory(row.getTxCategory());
			value.setSlipType(row.getSlipType());
			value.setAmountType(row.getAmountType());
			value.setTxWith(row.getTxWith());
			value.setTxDescription(row.getTxTypeDesc());
			value.setUpdateFormulaOnDone(row.getUpdateFormulaOnDone());
			value.setUpdateFormulaOnUndone(row.getUpdateFormulaOnUndone());
			value.setUpdateFormulaOnApprove(row.getUpdateFormulaOnApprove());
			value.setUpdateFormulaOnReject(row.getUpdateFormulaOnReject());

			values.add(value);
		}
		enu.setEnumName(enumName);
		enu.setEnumValues(values);

		EnumCache.addTxType(enu);
	}

	public void addGroupType(GroupTypeEnum enu) {

		GroupTypeEnum oEnum = EnumCache.getGroupType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(GroupTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getGroupType());
			}
		}

		for(GroupTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getGroupType())) {
				GroupType groupType = new GroupType();
				groupType.setGroupType(eVal.getGroupType());
				groupType.setCategory(eVal.getCategory());
				groupType.setDefaultWsAccessRights(eVal.getDefaultWsAccessRights());
				groupType.setDefaultUiAccessRights(eVal.getDefaultUiAccessRights());
				groupType.setGroupTypeDesc(eVal.getGroupTypeDesc());

				daoFactory.getGroupTypeDAO().persist(groupType);
			}
		}

		// Reload enum GroupType after updates
		loadGroupType();
	}

	public void updateGroupType(GroupTypeEnum enu) {

		for(GroupTypeValue eVal : enu.getEnumValues()) {
			GroupType groupType = daoFactory.getGroupTypeDAO().findById(eVal.getGroupTypeId());
			groupType.setGroupType(eVal.getGroupType());
			groupType.setCategory(eVal.getCategory());
			groupType.setDefaultWsAccessRights(eVal.getDefaultWsAccessRights());
			groupType.setDefaultUiAccessRights(eVal.getDefaultUiAccessRights());
			groupType.setGroupTypeDesc(eVal.getGroupTypeDesc());

			daoFactory.getGroupTypeDAO().merge(groupType);
		}

		// Reload enum GroupType after updates
		loadGroupType();
	}

	// Load GroupType type Enum as Generic Enum to Cache
	public void loadGroupType() {
		String enumName = EnumConst.GroupType;
		GroupTypeEnum enu =  new GroupTypeEnum();
		List<GroupType> rows = daoFactory.getGroupTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);

		for(GroupType row: rows) {
			GroupTypeValue eVal = new GroupTypeValue(row.getGroupTypeId(), 
					row.getGroupType(), 
					row.getCategory(),
					row.getDefaultWsAccessRights(),
					row.getDefaultUiAccessRights(),
					row.getGroupTypeDesc());
			enu.addEnumValue(eVal);
		}

		EnumCache.addGroupType(enu);
	}

	public void addNameTitle(NameTitleEnum enu) {

		NameTitleEnum oEnum = EnumCache.getNameTitle();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(NameTitleValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getTitle());
			}
		}

		for(NameTitleValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getTitle())) {
				NameTitle nameTitle = new NameTitle(daoFactory.getLangDAO().getReferenceByValue(eVal.getLang()), 
						eVal.getTitle(),
						eVal.getGender());

				daoFactory.getNameTitleDAO().persist(nameTitle);
			}
		}

		// Reload enum NameTitle after updates
		loadNameTitle();
	}

	public void updateNameTitle(NameTitleEnum enu) {

		for(NameTitleValue eVal : enu.getEnumValues()) {
			NameTitle nameTitle = daoFactory.getNameTitleDAO().findById(eVal.getTitleId());
			nameTitle.setLang(daoFactory.getLangDAO().getReferenceByValue(eVal.getLang()));
			nameTitle.setTitle(eVal.getTitle());
			nameTitle.setGender(eVal.getGender());

			daoFactory.getNameTitleDAO().merge(nameTitle);
		}

		// Reload enum NameTitle after updates
		loadNameTitle();
	}

	// Load NameTitle type Enum as Generic Enum to Cache
	public void loadNameTitle() {
		String enumName = EnumConst.NameTitle;
		NameTitleEnum enu =  new NameTitleEnum();
		List<NameTitle> rows = daoFactory.getNameTitleDAO().getAllRowList(100);

		enu.setEnumName(enumName);

		for(NameTitle row: rows) {
			NameTitleValue eVal = new NameTitleValue(row.getTitleId(), row.getLang().getLang(), row.getTitle(), row.getGender());
			enu.addEnumValue(eVal);
		}

		EnumCache.addNameTitle(enu);
	}

	public void addMRole(MRoleEnum enu) {

		MRoleEnum oEnum = EnumCache.getMRole();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(MRoleValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getRole());
			}
		}

		for(MRoleValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getRole())) {
				MRole mRole = new MRole(eVal.getRole(),
						eVal.getRoleCategory(),
						eVal.getBelongTo(),
						eVal.getSystem(),
						eVal.getRoleDesc(),
						eVal.getDefaultWsAccessRights(),
						eVal.getDefaultUiAccessRights());

				daoFactory.getMRoleDAO().persist(mRole);
			}
		}

		// Reload enum MRole after updates
		loadMRole();
	}

	public void updateMRole(MRoleEnum enu) {

		for(MRoleValue eVal : enu.getEnumValues()) {
			MRole mRole = daoFactory.getMRoleDAO().findById(eVal.getRoleId());
			mRole.setMRole(eVal.getRole());
			mRole.setMRoleCategory(eVal.getRoleCategory());
			mRole.setBelongTo(eVal.getBelongTo());
			mRole.setSystem(eVal.getSystem());
			mRole.setMRoleDesc(eVal.getRoleDesc());
			mRole.setDefaultWsAccessRights(eVal.getDefaultWsAccessRights());
			mRole.setDefaultUiAccessRights(eVal.getDefaultUiAccessRights());

			daoFactory.getMRoleDAO().merge(mRole);
		}

		// Reload enum MRole after updates
		loadMRole();
	}

	// Load MRole type Enum as Generic Enum to Cache
	public void loadMRole() {
		String enumName = EnumConst.MRole;
		MRoleEnum enu =  new MRoleEnum();
		List<MRole> rows = daoFactory.getMRoleDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MRole row: rows) {
			enu.addEnumValue(new MRoleValue(row.getMRoleId(), 
					row.getMRole(), 
					row.getMRoleCategory(), 
					row.getBelongTo(), 
					row.getSystem(),
					row.getMRoleDesc(), 
					row.getDefaultWsAccessRights(),
					row.getDefaultUiAccessRights()));
		}
		EnumCache.addMRole(enu);
	}

	public void addFundType(FundTypeEnum enu) {

		FundTypeEnum oEnum = EnumCache.getFundType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(FundTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getFundType());
			}
		}

		for(FundTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getFundType())) {
				FundType fundType = new FundType(eVal.getFundType(),
						eVal.getFundCategory(),
						eVal.getFundTypeDesc());

				daoFactory.getFundTypeDAO().persist(fundType);
			}
		}

		// Reload enum FundType after updates
		loadFundType();
	}

	public void updateFundType(FundTypeEnum enu) {

		for(FundTypeValue eVal : enu.getEnumValues()) {
			FundType fundType = daoFactory.getFundTypeDAO().findById(eVal.getFundTypeId());
			fundType.setFundType(eVal.getFundType());
			fundType.setFundCategory(eVal.getFundCategory());
			fundType.setFundTypeDesc(eVal.getFundTypeDesc());

			daoFactory.getFundTypeDAO().merge(fundType);
		}

		// Reload enum FundType after updates
		loadFundType();
	}

	// Load FundType type Enum as Generic Enum to Cache
	public void loadFundType() {
		String enumName = EnumConst.FundType;
		FundTypeEnum enu =  new FundTypeEnum();
		List<FundType> rows = daoFactory.getFundTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(FundType row: rows) {
			enu.addEnumValue(new FundTypeValue(row.getFundTypeId(), 
					row.getFundType(), 
					row.getFundCategory(), 
					row.getFundTypeDesc()));
		}
		EnumCache.addFundType(enu);
	}

	public void addDistricts(DistrictsEnum enu) {
		DistrictsEnum oldEnum = EnumCache.getDistrictsREST(EnumConst.Lang_English);
		Set<String> oldDistricts = null;
		Set<String> oldDistrictCodes = null;
		if(oldEnum != null && oldEnum.getDistrictValues() != null) {
			oldDistricts = new HashSet<String>(oldEnum.getDistrictValues().size());
			oldDistrictCodes = new HashSet<String>(oldEnum.getDistrictValues().size());
			for(DistrictValue value: oldEnum.getDistrictValues()) {
				oldDistricts.add(value.getDistrict());
				oldDistrictCodes.add(value.getDistrictCode());
			}
		}

		for(DistrictValue eVal : enu.getDistrictValues()) {
			String districtStr = WordUtils.capitalize(eVal.getDistrict());
			String districtCode = eVal.getDistrictCode().toUpperCase();

			if(oldDistricts == null || !oldDistricts.contains(districtStr) && !oldDistrictCodes.contains(districtCode)) {

				District district = new District(daoFactory.getLangDAO().getReferenceByValue(eVal.getLang()),
						eVal.getState(),
						eVal.getDivision(),
						districtStr,
						districtCode);

				daoFactory.getDistrictDAO().persist(district);
			}
		}

		// Reload enum Districts after updates
		loadDistricts();
	}

	public void updateDistricts(DistrictsEnum enu) {

		for(DistrictValue eVal : enu.getDistrictValues()) {
			District district = daoFactory.getDistrictDAO().findById(eVal.getDistrictId());
			district.setDistrict(eVal.getDistrict());
			district.setDistrictCode(eVal.getDistrictCode());
			district.setDivision(eVal.getDivision());
			district.setState(eVal.getState());

			daoFactory.getDistrictDAO().merge(district);
		}

		// Reload enum Districts after updates
		loadDistricts();
	}

	// Load Districts to Cache
	public void loadDistricts() {
		DistrictsEnum districts =  new DistrictsEnum();
		List<District> rows = daoFactory.getDistrictDAO().getAllRowList(5000);

		for(District row: rows) {
			districts.addDistrictValue(new DistrictValue(row.getDistrictId(), 
					row.getLang().getLang(), 
					row.getState(), 
					row.getDivision(),
					row.getDistrict(),
					row.getDistrictCode()));
		}
		EnumCache.addDistricts(districts);
	}

	public void addMonthlyReports(MonthlyReportsEnum enu) {
		MonthlyReportsEnum oldEnum = EnumCache.getMonthlyReports();
		Map<String, MonthlyReportInfo> oldMonthlyReports = null;
		if(oldEnum != null && oldEnum.getMonthlyReports() != null) {
			oldMonthlyReports = new HashMap<String, MonthlyReportInfo>(oldEnum.getMonthlyReports().size());
			for(MonthlyReportInfo value: oldEnum.getMonthlyReports()) {
				oldMonthlyReports.put(value.getReportName(), value);
			}
		}

		for(MonthlyReportInfo eVal : enu.getMonthlyReports()) {

			MonthlyReport monthlyReport = null;

			if(oldMonthlyReports == null || !oldMonthlyReports.keySet().contains(eVal.getReportName())) {

				monthlyReport = new MonthlyReport(eVal.getReportName(),
						eVal.getReportDisplayName(), 
						eVal.getReportDesc());

				daoFactory.getMonthlyReportDAO().persist(monthlyReport);

			} else {
				monthlyReport = daoFactory.getMonthlyReportDAO().findById(oldMonthlyReports.get(eVal.getReportName()).getMonthlyReportId());
			}

			if(eVal.getMonthlyReportSheets() != null && !eVal.getMonthlyReportSheets().isEmpty()) {
				addMonthlySheetReportSheet(eVal.getMonthlyReportSheets(), monthlyReport);
			}
		}

		// Reload enum MonthlyReports after updates
		loadMonthlyReports();
	}

	public void updateMonthlyReports(MonthlyReportsEnum enu) {

		for(MonthlyReportInfo eVal : enu.getMonthlyReports()) {
			MonthlyReport monthlyReport = daoFactory.getMonthlyReportDAO().findById(eVal.getMonthlyReportId());
			
			monthlyReport.setReportName(eVal.getReportName());
			monthlyReport.setReportDisplayName(eVal.getReportDisplayName());
			monthlyReport.setReportDesc(eVal.getReportDesc());

			daoFactory.getMonthlyReportDAO().merge(monthlyReport);
			
			if(eVal.getMonthlyReportSheets() != null && !eVal.getMonthlyReportSheets().isEmpty()) {
				updateMonthlyReportSheet(eVal.getMonthlyReportSheets());
			}
		}

		// Reload enum MonthlyReports after updates
		loadMonthlyReports();
	}

	public void addMonthlySheetReportSheet(List<MonthlyReportSheetInfo> monthlyReportSheets, MonthlyReport monthlyReport) {
		Set<String> oldSheets = null;
		if(monthlyReport != null && monthlyReport.getReportItemMappings() != null) {
			oldSheets = new HashSet<String>(monthlyReport.getReportItemMappings().size());
			for(MonthlyReportSheet value: monthlyReport.getReportItemMappings()) {
				oldSheets.add(value.getReportSheetName());
			}
		}

		for(MonthlyReportSheetInfo eVal : monthlyReportSheets) {

			if(oldSheets == null || !oldSheets.contains(eVal.getReportSheetName())) {
				MonthlyReportSheet monthlyReportSheet = new MonthlyReportSheet(eVal.getReportSheetName(),
						eVal.getReportSheetTopTitle(),
						eVal.getReportSheetBottomTitle(),
						eVal.getSheetFormat(),
						eVal.getReportSheetDesc(), 
						eVal.getReportSheetFormula(),
						monthlyReport);

				daoFactory.getMonthlyReportSheetDAO().persist(monthlyReportSheet);
			}
		}
	}

	public void updateMonthlyReportSheet(List<MonthlyReportSheetInfo> monthlyReportSheetInfo) {

		for(MonthlyReportSheetInfo eVal : monthlyReportSheetInfo) {
			MonthlyReportSheet monthlyReportSheet = daoFactory.getMonthlyReportSheetDAO().findById(eVal.getMonthlyReportSheetId());
			
			monthlyReportSheet.setReportSheetName(eVal.getReportSheetName());
			monthlyReportSheet.setReportSheetTopTitle(eVal.getReportSheetTopTitle());
			monthlyReportSheet.setReportSheetBottomTitle(eVal.getReportSheetBottomTitle());
			monthlyReportSheet.setSheetFormat(eVal.getSheetFormat());
			monthlyReportSheet.setReportSheetDesc(eVal.getReportSheetDesc());
			monthlyReportSheet.setReportSheetFormula(eVal.getReportSheetFormula());

			daoFactory.getMonthlyReportSheetDAO().merge(monthlyReportSheet);
		}

		// Reload enum MonthlyReports after updates
		loadMonthlyReports();
	}

	// Load MonthlyReports to Cache
	public void loadMonthlyReports() {
		MonthlyReportsEnum monthlyReports =  new MonthlyReportsEnum();
		List<MonthlyReport> rows = daoFactory.getMonthlyReportDAO().getAllRowList(100);

		for(MonthlyReport row: rows) {
			MonthlyReportInfo monthlyReportInfo = new MonthlyReportInfo(row.getMonthlyReportId(), 
					row.getReportName(), row.getReportDisplayName(), row.getReportDesc());
			
			for(MonthlyReportSheet sheet: row.getReportItemMappings()) {
				monthlyReportInfo.addMonthlyReportSheet(new MonthlyReportSheetInfo(
						sheet.getMonthlyReportSheetId(),
						sheet.getReportSheetName(),
						sheet.getReportSheetTopTitle(),
						sheet.getReportSheetBottomTitle(),
						sheet.getSheetFormat(),
						sheet.getReportSheetDesc(),
						sheet.getReportSheetFormula()));
			}
			monthlyReportInfo.sort();
			monthlyReports.addMonthlyReport(monthlyReportInfo);
		}
		
		EnumCache.addMonthlyReports(monthlyReports);
	}

	public void addDocType(DocTypeEnum enu) {

		DocTypeEnum oEnum = EnumCache.getDocType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(DocTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getDocType() + eVal.getDocFor());
			}
		}

		for(DocTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getDocType())) {
				DocType docType = new DocType(eVal.getDocType(),
						eVal.getDocFor(),
						eVal.getDocCategory(),
						eVal.getDocDesc());

				daoFactory.getDocTypeDAO().persist(docType);
			}
		}

		// Reload enum DocType after updates
		loadDocType();
	}

	public void updateDocType(DocTypeEnum enu) {

		for(DocTypeValue eVal : enu.getEnumValues()) {
			
			DocType docType = daoFactory.getDocTypeDAO().findById(eVal.getDocTypeId());
			docType.setDocType(eVal.getDocType());
			docType.setDocFor(eVal.getDocFor());
			docType.setDocCategory(eVal.getDocCategory());
			docType.setDocDesc(eVal.getDocDesc());

			daoFactory.getDocTypeDAO().merge(docType);
		}

		// Reload enum DocType after updates
		loadDocType();
	}

	// Load DocType type Enum as Generic Enum to Cache
	public void loadDocType() {
		String enumName = EnumConst.DocType;
		DocTypeEnum enu =  new DocTypeEnum();
		List<DocType> rows = daoFactory.getDocTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(DocType row: rows) {
			enu.addEnumValue(new DocTypeValue(row.getDocTypeId(), 
					row.getDocType(), 
					row.getDocFor(), 
					row.getDocCategory(), 
					row.getDocDesc()));
		}
		EnumCache.addDocType(enu);
	}
	
	public void addUiAccessCode(AccessCodeEnum enu) {

		AccessCodeEnum oEnum = EnumCache.getUiAccessCode();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(AccessCodeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getAccessCode());
			}
		}

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getAccessCode())) {
				UiAccessCode uiAccessCode = new UiAccessCode(eVal.getAccessCode(),
						eVal.getAccessCodeDisplay(),
						eVal.getAccessCodeCategory(),
						eVal.getAccessCodeDesc(),
						eVal.getAccessLevel());

				daoFactory.getUiAccessCodeDAO().persist(uiAccessCode);
			}
		}

		// Reload enum UiAccessCode after updates
		loadUiAccessCode();
	}

	public void updateUiAccessCode(AccessCodeEnum enu) {

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			UiAccessCode uiAccessCode = daoFactory.getUiAccessCodeDAO().findById(eVal.getAccessCodeId());
			uiAccessCode.setAccessCode(eVal.getAccessCode());
			uiAccessCode.setAccessCodeDisplay(eVal.getAccessCodeDisplay());
			uiAccessCode.setAccessCodeCategory(eVal.getAccessCodeCategory());
			uiAccessCode.setAccessCodeDesc(eVal.getAccessCodeDesc());
			uiAccessCode.setAccessLevel(eVal.getAccessLevel());

			daoFactory.getUiAccessCodeDAO().merge(uiAccessCode);
		}

		// Reload enum UiAccessCode after updates
		loadUiAccessCode();
	}

	// Load UiAccessCode type Enum as Generic Enum to Cache
	public void loadUiAccessCode() {
		String enumName = EnumConst.UiAccessCode;
		AccessCodeEnum enu =  new AccessCodeEnum();
		List<UiAccessCode> rows = daoFactory.getUiAccessCodeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(UiAccessCode row: rows) {
			enu.addEnumValue(new AccessCodeValue(row.getAccessCodeId(), 
					row.getAccessCode(), 
					row.getAccessCodeDisplay(), 
					row.getAccessCodeCategory(), 
					row.getAccessCodeDesc(), 
					row.getAccessLevel()));
		}
		EnumCache.addUiAccessCode(enu);
		uiAccessCodeUtil.loadAccessCode(enu);
	}
	
	public void addWsAccessCode(AccessCodeEnum enu) {

		AccessCodeEnum oEnum = EnumCache.getWsAccessCode();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(AccessCodeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getAccessCode());
			}
		}

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getAccessCode())) {
				WsAccessCode wsAccessCode = new WsAccessCode(eVal.getAccessCode(),
						eVal.getAccessCodeDisplay(),
						eVal.getAccessCodeCategory(),
						eVal.getAccessCodeDesc(),
						eVal.getAccessLevel());

				daoFactory.getWsAccessCodeDAO().persist(wsAccessCode);
			}
		}

		// Reload enum WsAccessCode after updates
		loadWsAccessCode();
	}

	public void updateWsAccessCode(AccessCodeEnum enu) {

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			WsAccessCode wsAccessCode = daoFactory.getWsAccessCodeDAO().findById(eVal.getAccessCodeId());
			wsAccessCode.setAccessCode(eVal.getAccessCode());
			wsAccessCode.setAccessCodeDisplay(eVal.getAccessCodeDisplay());
			wsAccessCode.setAccessCodeCategory(eVal.getAccessCodeCategory());
			wsAccessCode.setAccessCodeDesc(eVal.getAccessCodeDesc());
			wsAccessCode.setAccessLevel(eVal.getAccessLevel());

			daoFactory.getWsAccessCodeDAO().merge(wsAccessCode);
		}

		// Reload enum WsAccessCode after updates
		loadWsAccessCode();
	}

	// Load WsAccessCode type Enum as Generic Enum to Cache
	public void loadWsAccessCode() {
		String enumName = EnumConst.WsAccessCode;
		AccessCodeEnum enu =  new AccessCodeEnum();
		List<WsAccessCode> rows = daoFactory.getWsAccessCodeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(WsAccessCode row: rows) {
			enu.addEnumValue(new AccessCodeValue(row.getAccessCodeId(), 
					row.getAccessCode(), 
					row.getAccessCodeDisplay(), 
					row.getAccessCodeCategory(), 
					row.getAccessCodeDesc(), 
					row.getAccessLevel()));
		}
		EnumCache.addWsAccessCode(enu);
		wsAccessCodeUtil.loadAccessCode(enu);
	}
	
	public void addMrUiAccessCode(AccessCodeEnum enu) {

		AccessCodeEnum oEnum = EnumCache.getMrUiAccessCode();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(AccessCodeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getAccessCode());
			}
		}

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getAccessCode())) {
				MrUiAccessCode mrUiAccessCode = new MrUiAccessCode(eVal.getAccessCode(),
						eVal.getAccessCodeDisplay(),
						eVal.getAccessCodeCategory(),
						eVal.getAccessCodeDesc(),
						eVal.getAccessLevel());

				daoFactory.getMrUiAccessCodeDAO().persist(mrUiAccessCode);
			}
		}

		// Reload enum MrUiAccessCode after updates
		loadMrUiAccessCode();
	}

	public void updateMrUiAccessCode(AccessCodeEnum enu) {

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			MrUiAccessCode mrUiAccessCode = daoFactory.getMrUiAccessCodeDAO().findById(eVal.getAccessCodeId());
			mrUiAccessCode.setAccessCode(eVal.getAccessCode());
			mrUiAccessCode.setAccessCodeDisplay(eVal.getAccessCodeDisplay());
			mrUiAccessCode.setAccessCodeCategory(eVal.getAccessCodeCategory());
			mrUiAccessCode.setAccessCodeDesc(eVal.getAccessCodeDesc());
			mrUiAccessCode.setAccessLevel(eVal.getAccessLevel());

			daoFactory.getMrUiAccessCodeDAO().merge(mrUiAccessCode);
		}

		// Reload enum MrUiAccessCode after updates
		loadMrUiAccessCode();
	}

	// Load MrUiAccessCode type Enum as Generic Enum to Cache
	public void loadMrUiAccessCode() {
		String enumName = EnumConst.MrUiAccessCode;
		AccessCodeEnum enu =  new AccessCodeEnum();
		List<MrUiAccessCode> rows = daoFactory.getMrUiAccessCodeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MrUiAccessCode row: rows) {
			enu.addEnumValue(new AccessCodeValue(row.getAccessCodeId(), 
					row.getAccessCode(), 
					row.getAccessCodeDisplay(), 
					row.getAccessCodeCategory(), 
					row.getAccessCodeDesc(), 
					row.getAccessLevel()));
		}
		EnumCache.addMrUiAccessCode(enu);
		mrUiAccessCodeUtil.loadAccessCode(enu);
	}
	
	public void addMrWsAccessCode(AccessCodeEnum enu) {

		AccessCodeEnum oEnum = EnumCache.getMrWsAccessCode();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(AccessCodeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getAccessCode());
			}
		}

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getAccessCode())) {
				MrWsAccessCode mrWsAccessCode = new MrWsAccessCode(eVal.getAccessCode(),
						eVal.getAccessCodeDisplay(),
						eVal.getAccessCodeCategory(),
						eVal.getAccessCodeDesc(),
						eVal.getAccessLevel());

				daoFactory.getMrWsAccessCodeDAO().persist(mrWsAccessCode);
			}
		}

		// Reload enum MrWsAccessCode after updates
		loadMrWsAccessCode();
	}

	public void updateMrWsAccessCode(AccessCodeEnum enu) {

		for(AccessCodeValue eVal : enu.getEnumValues()) {
			MrWsAccessCode mrWsAccessCode = daoFactory.getMrWsAccessCodeDAO().findById(eVal.getAccessCodeId());
			mrWsAccessCode.setAccessCode(eVal.getAccessCode());
			mrWsAccessCode.setAccessCodeDisplay(eVal.getAccessCodeDisplay());
			mrWsAccessCode.setAccessCodeCategory(eVal.getAccessCodeCategory());
			mrWsAccessCode.setAccessCodeDesc(eVal.getAccessCodeDesc());
			mrWsAccessCode.setAccessLevel(eVal.getAccessLevel());

			daoFactory.getMrWsAccessCodeDAO().merge(mrWsAccessCode);
		}

		// Reload enum MrWsAccessCode after updates
		loadMrWsAccessCode();
	}

	// Load MrWsAccessCode type Enum as Generic Enum to Cache
	public void loadMrWsAccessCode() {
		String enumName = EnumConst.MrWsAccessCode;
		AccessCodeEnum enu =  new AccessCodeEnum();
		List<MrWsAccessCode> rows = daoFactory.getMrWsAccessCodeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MrWsAccessCode row: rows) {
			enu.addEnumValue(new AccessCodeValue(row.getAccessCodeId(), 
					row.getAccessCode(), 
					row.getAccessCodeDisplay(), 
					row.getAccessCodeCategory(), 
					row.getAccessCodeDesc(), 
					row.getAccessLevel()));
		}
		EnumCache.addMrWsAccessCode(enu);
		mrWsAccessCodeUtil.loadAccessCode(enu);
	}

	public void addMProfilingType(MProfilingTypeEnum enu) {

		MProfilingTypeEnum oEnum = EnumCache.getMProfilingType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(MProfilingTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getPoint());
			}
		}

		for(MProfilingTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getPoint())) {
				MProfilingType mProfilingType = new MProfilingType(eVal.getPoint(),
						eVal.getProfileFor(),
						eVal.getCategory(),
						eVal.getValueType(),
						eVal.pullOptionsString());

				daoFactory.getMProfilingTypeDAO().persist(mProfilingType);
			}
		}

		// Reload enum MProfilingType after updates
		loadMProfilingType();
	}

	public void updateMProfilingType(MProfilingTypeEnum enu) {

		for(MProfilingTypeValue eVal : enu.getEnumValues()) {
			
			MProfilingType mProfilingType = daoFactory.getMProfilingTypeDAO().findById(eVal.getPointId());
			mProfilingType.setPoint(eVal.getPoint());
			mProfilingType.setProfileFor(eVal.getProfileFor());
			mProfilingType.setCategory(eVal.getCategory());
			mProfilingType.setValueType(eVal.getValueType());
			mProfilingType.setOptions(eVal.pullOptionsString());

			daoFactory.getMProfilingTypeDAO().merge(mProfilingType);
		}

		// Reload enum MProfilingType after updates
		loadMProfilingType();
	}

	// Load MProfilingType type Enum as Generic Enum to Cache
	public void loadMProfilingType() {
		String enumName = EnumConst.MProfilingType;
		MProfilingTypeEnum enu =  new MProfilingTypeEnum();
		List<MProfilingType> rows = daoFactory.getMProfilingTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MProfilingType row: rows) {
			enu.addEnumValue(new MProfilingTypeValue(row.getPointId(), 
					row.getPoint(), 
					row.getProfileFor(),
					row.getCategory(), 
					row.getValueType(), 
					row.getOptions()));
		}
		EnumCache.addMProfilingType(enu);
	}

	public void addMessageType(MessageTypeEnum enu) {

		MessageTypeEnum oEnum = EnumCache.getMessageType();
		Set<String> oldEnums = null;
		if(oEnum != null && oEnum.getEnumValues() != null) {
			oldEnums = new HashSet<String>(oEnum.getEnumValues().size());
			for(MessageTypeValue eVal : oEnum.getEnumValues()) {
				oldEnums.add(eVal.getMessageType() + eVal.getLang());
			}
		}

		for(MessageTypeValue eVal : enu.getEnumValues()) {
			if(oldEnums == null || !oldEnums.contains(eVal.getMessageType() + eVal.getLang())) {
				MessageType messageType = new MessageType(daoFactory.getLangDAO().findByValue(eVal.getLang()),
						eVal.getMessageType(),
						eVal.getSmsFormat(),
						eVal.getEmailFormat(),
						eVal.getPassKey());

				daoFactory.getMessageTypeDAO().persist(messageType);
			}
		}

		// Reload enum MessageType after updates
		loadMessageType();
	}

	public void updateMessageType(MessageTypeEnum enu) {

		for(MessageTypeValue eVal : enu.getEnumValues()) {
			
			MessageType messageType = daoFactory.getMessageTypeDAO().findById(eVal.getMessageTypeId());
			messageType.setLang(daoFactory.getLangDAO().findByValue(eVal.getLang()));
			messageType.setMessageType(eVal.getMessageType());
			messageType.setSmsFormat(eVal.getSmsFormat());
			messageType.setEmailFormat(eVal.getEmailFormat());
			messageType.setPassKey(eVal.getPassKey());

			daoFactory.getMessageTypeDAO().merge(messageType);
		}

		// Reload enum MessageType after updates
		loadMessageType();
	}

	// Load MessageType type Enum as Generic Enum to Cache
	public void loadMessageType() {
		String enumName = EnumConst.MessageType;
		MessageTypeEnum enu =  new MessageTypeEnum();
		List<MessageType> rows = daoFactory.getMessageTypeDAO().getAllRowList(100);

		enu.setEnumName(enumName);
		for(MessageType row: rows) {
			enu.addEnumValue(new MessageTypeValue(row.getMessageTypeId(), 
					row.getLang().getLang(),
					row.getMessageType(), 
					row.getSmsFormat(), 
					row.getEmailFormat(), 
					row.getPassKey()));
		}
		EnumCache.addMessageType(enu);
	}
}
