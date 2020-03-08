package com.beone.shg.net.persistence.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.job.DataFeildsUpdateJob;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.model.TxType;
import com.beone.shg.net.persistence.support.BankAccountInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.support.TxTypeFormula;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.model.resp.EnumValue;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransaction;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransactions;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.TransactionFilter;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("transactionBO")
public class TransactionBO extends BaseBO {

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	public List<Transaction> addTransactions(List<Transaction> request) throws BadRequestException {
		
		long groupAcNo = DataUtil.ZERO_LONG;
		List<Tx> txs = new ArrayList<Tx>(request.size());
		
		// Add Multiple Transactions
		for(Transaction transaction : request) {
			if(transaction.getAmount() != null && (transaction.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER)) {
				txs.add(addTransaction(transaction, false, false));
				groupAcNo = transaction.getGroupAcNo();
			} else {
				specialTodoTransaction(transaction);
			}
		}
		
		if(groupAcNo != DataUtil.ZERO_LONG && txs.size() > 0) {
			processJobBuilder.pushMessageJob(txs);
		}

		return request;
	}

	public Transaction addTransaction(Transaction request) throws BadRequestException {
		addTransaction(request, false, true);
		return request;
	}
	
	protected Tx addTransaction(Transaction request, boolean oldTx, boolean smsTx) throws BadRequestException {
		// Build transaction Object & Save

		if(request.getTxType() == null || request.getTxType().isEmpty()) {
			throw new BadRequestException("Null Or Empty Transaction Type");
		}
		TxType txType = daoFactory.getTxTypeDAO().findByValue(request.getTxType());
		if(txType == null) {
			throw new BadRequestException("Null Or Empty Transaction Type : " + request.getTxType());
		}
		if(EnumUtil.isInternalTxType(txType.getTxName()) && !request.getPaymentMode().equals(EnumConst.PaymentMode_INTERNAL)) {
			throw new BadRequestException("Invalid PaymentMode, should be : EnumConst.PaymentMode_INTERNAL!");
		} 
		if(EnumUtil.isOutstandingTxType(txType.getTxName()) && !request.getPaymentMode().equals(EnumConst.PaymentMode_OUTSTANDING)) {
			throw new BadRequestException("Invalid PaymentMode, should be : EnumConst.PaymentMode_OUTSTANDING!");
		} 
		if(EnumUtil.isTransferTxType(txType.getTxName()) && request.getGroupBankAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Bak Account NO!");
		} 

		if(request.getPaymentTs() > 0) {
			if(request.getPaymentTs() > System.currentTimeMillis()) {
				throw new BadRequestException("Payment Date Can't be for Future : " + DateUtil.convertTimeToString(request.getPaymentTs()));
			}
			if(DateUtil.getMonth(request.getPaymentTs()) != DateUtil.getMonth(System.currentTimeMillis()) ||
					DateUtil.getYear(request.getPaymentTs()) != DateUtil.getYear(System.currentTimeMillis())) {
				throw new BadRequestException("Payment Date Not of Current Month : " + DateUtil.convertTimeToString(request.getPaymentTs()));
			}
		}
		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo()) || daoFactory.getMAcDAO().getReferenceById(request.getMemberAcNo()) == null) {
				throw new BadRequestException("Invalid Member Account NO");
			}
		} else if(txType.getTxWith().equals(EnumConst.TxType_BANK)) {
			if(!ConversionUtil.isValidGroupAcNo(request.getExternalGroupAcNo()) || (daoFactory.getGAcDAO().getReferenceById(request.getExternalGroupAcNo()) == null)) {
				throw new BadRequestException("Invalid External Group Account NO");
			}
		}
		if(!ConversionUtil.isValidGroupAcNo(request.getGroupAcNo()) || (daoFactory.getGAcDAO().getReferenceById(request.getGroupAcNo()) == null)) {
			throw new BadRequestException("Invalid Group Account NO :" + request.getGroupAcNo());
		}
		if(!ConversionUtil.isValidMemberAcNo(request.getDoneByMemberAcNo()) || daoFactory.getMAcDAO().getReferenceById(request.getDoneByMemberAcNo()) == null) {
			throw new BadRequestException("Invalid Done By Member Account NO: " + request.getDoneByMemberAcNo());
		}
		if(request.getAmount().floatValue() == 0.0f) {
			throw new BadRequestException("Invalid Transaction Amount");
		}
		if(request.getPaymentMode() == null || request.getPaymentMode().isEmpty() ||
				EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, request.getPaymentMode()) < 0) {
			throw new BadRequestException("Null Or Empty Payment Mode: " + request.getPaymentMode());
		}
		
		Tx transaction = new Tx();

		transaction.setTxTypeId(txType.getTxTypeId());
		transaction.setReceiptVoucherNo(request.getSlipNo());
		if(oldTx) {
			transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Approved));
		} else {
			transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Submitted));
		}
		
		transaction.setGroupAcNo(request.getGroupAcNo()); 

		transaction.setDoneByMemberAcNo(request.getDoneByMemberAcNo());
		
		transaction.setAmount(request.getAmount());
		EnumValue paymentMode = EnumCache.getEnumValue(EnumConst.PaymentMode, request.getPaymentMode());
		transaction.setPaymentModeId(paymentMode.getEnumIndex());
		if(request.getPaymentMode().equals(EnumConst.PaymentMode_CHEQUE)) {
			transaction.setChequeNo(request.getChequeNo());
		}

		transaction.setEntryTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getEntryTs()));
		transaction.setPaymentTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getPaymentTs()));
		
		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			
			transaction.setMemberAcNo(request.getMemberAcNo());
		} else {

			transaction.setBankGroupAcNo(request.getExternalGroupAcNo());
		}
		
		if(paymentMode.getDescription().equals(EnumConst.PaymentMode_Bank)) {
			if(request.getGroupBankAcNo() <= 0 || daoFactory.getGBankAccountDAO().getReferenceById(request.getGroupBankAcNo()) == null) {
				throw new BadRequestException("Invalid Group Bak Account NO: " + request.getGroupBankAcNo());
			}
			transaction.setGroupBankAccount(request.getGroupBankAcNo());
			if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
				if(request.getMemberBankAcNo() > 0 && daoFactory.getMBankAccountDAO().getReferenceById(request.getMemberBankAcNo()) != null) {
					transaction.setMemberBankAccount(request.getMemberBankAcNo());
				}
			} else {
				if(request.getExternalGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getExternalGroupBankAcNo()) != null) {
					transaction.setBankGroupBankAccount(request.getExternalGroupBankAcNo());
				}
			}
		}

		if(request.getLocation() != null && !request.getLocation().isEmpty()) {
			transaction.setEntryLocation(request.getLocation() + DBConst.ATTACH_ADDED_DILIMITER); 
		}

		if(request.getRelatedTxTodoId() > 0) {
			TxTodo txTodo = daoFactory.getTxTodoDAO().findById(request.getRelatedTxTodoId());
			
			if(txTodo == null) {
				throw new BadRequestException("Invalid Related TxTodo ID: " + request.getRelatedTxTodoId());
			}

			if(EnumUtil.isTxPenaltyType(txType)) {
				txTodo.setPenaltyPaidAm(request.getAmount());
			} else {
				txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Done));
				txTodo.setExpectedPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, request.getPaymentMode()));
			}
			if(request.getDescription() != null && !request.getDescription().isEmpty()) {
				txTodo.setDescription(request.getDescription()); 
			}
			
			if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
				if(txTodo.getMemberSavingAcNo() > 0) {
					transaction.setMemberSavingAcNo(txTodo.getMemberSavingAcNo());
				}
				else if(txTodo.getMemberLoanAcNo() > 0) {
					transaction.setMemberLoanAcNo(txTodo.getMemberLoanAcNo());
					if(request.getTxType().equals(EnumConst.TxType_Loan_Interest_Installment) && 
							txTodo.getInterestComponent().compareTo(request.getAmount()) != DataUtil.ZERO_INTEGER) {
						
						txTodo.setAmount(BDUtil.add(txTodo.getAmount(), BDUtil.sub(request.getAmount(), txTodo.getInterestComponent())));
						txTodo.setInterestComponent(request.getAmount());
					}
					if(request.getTxType().equals(EnumConst.TxType_Loan_Installment)) {
						
						MLoanAc loan = daoFactory.getMLoanAcDAO().findById(txTodo.getMemberLoanAcNo());
						if(loan.getPendingPrincipleAm().compareTo(request.getAmount()) < DataUtil.ZERO_INTEGER) {
							throw new BadRequestException("Loan Installment in Excess than Outstanding Principle = " + loan.getPendingPrincipleAm());
						}
						
						if(BDUtil.sub(txTodo.getAmount(), txTodo.getInterestComponent()).compareTo(request.getAmount()) != DataUtil.ZERO_INTEGER) {
							txTodo.setAmount(BDUtil.add(request.getAmount(), txTodo.getInterestComponent()));
						}
					}
				}
			} else {
				if(txTodo.getGroupLoanAcNo() > 0) {
					transaction.setGroupLoanAcNo(txTodo.getGroupLoanAcNo());
				}
			}

			// Update TodoTransaction to DB
			txTodo = daoFactory.getTxTodoDAO().merge(txTodo);
			transaction.setTxTodo(txTodo);
		} else {
			
			if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
				if(request.getSavingAcNo() > 0 && daoFactory.getMSavingAcDAO().getReferenceById(request.getSavingAcNo()) != null) {
					transaction.setMemberSavingAcNo(request.getSavingAcNo());
				}
				else if(request.getMemberLoanAcNo() > 0 && daoFactory.getMLoanAcDAO().getReferenceById(request.getMemberLoanAcNo()) != null) {
					transaction.setMemberLoanAcNo(request.getMemberLoanAcNo());
				}
			} else {
				if(request.getGroupLoanAcNo() > 0 && daoFactory.getGLoanAcDAO().getReferenceById(request.getGroupLoanAcNo()) != null) {
					transaction.setGroupLoanAcNo(request.getGroupLoanAcNo());
				}
				else if(request.getGroupInvtAcNo() > 0 && daoFactory.getGInvtAcDAO().getReferenceById(request.getGroupInvtAcNo()) != null) {
					transaction.setGroupInvtAcNo(request.getGroupInvtAcNo());
				}
			}
		}

		// Save Transaction to DB
		daoFactory.getTxDAO().persist(transaction);

		// Load Transaction Id back 
		request.setTxId(transaction.getTxId());

		if(!oldTx) {
			TxTypeFormula formula = EnumCache.getTxTypeFormula(txType.getTxTypeId());
			// Transaction Post Processing
			if(formula.getFormulaOnDone() != null) {
				processJobBuilder.pushPostProcessJob(formula.getFormulaOnDone(), transaction);
			}
			
			if(smsTx) {
				processJobBuilder.pushMessageJob(transaction);
			}
		}
		
		return transaction;
	}
	
	protected Transaction specialTodoTransaction(Transaction request) throws BadRequestException {
		// Build transaction Object & Save

		if(request.getTxType() == null || request.getTxType().isEmpty()) {
			throw new BadRequestException("Null Or Empty Transaction Type");
		}
		TxType txType = daoFactory.getTxTypeDAO().findByValue(request.getTxType());
		if(txType == null) {
			throw new BadRequestException("Null Or Empty Transaction Type : " + request.getTxType());
		}

		if(request.getRelatedTxTodoId() > 0) {
			TxTodo txTodo = daoFactory.getTxTodoDAO().findById(request.getRelatedTxTodoId());
			
			if(txTodo == null) {
				return request;
			}
			
			if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
				if(txTodo.getMemberLoanAcNo() > 0) {
					if(request.getTxType().equals(EnumConst.TxType_Loan_Interest_Installment) && 
							txTodo.getInterestComponent().compareTo(request.getAmount()) != DataUtil.ZERO_INTEGER) {
						
						txTodo.setAmount(BDUtil.add(txTodo.getAmount(), BDUtil.sub(request.getAmount(), txTodo.getInterestComponent())));
						txTodo.setInterestComponent(request.getAmount());
					}
					if(request.getTxType().equals(EnumConst.TxType_Loan_Installment) && 
							BDUtil.sub(txTodo.getAmount(), txTodo.getInterestComponent()).compareTo(request.getAmount()) != DataUtil.ZERO_INTEGER) {
						
						txTodo.setAmount(BDUtil.add(request.getAmount(), txTodo.getInterestComponent()));
					}
				}
			}
			// Update TodoTransaction to DB
			txTodo = daoFactory.getTxTodoDAO().merge(txTodo);
		}
		
		return request;
	}
	
	public Transaction counterTransaction(Transaction request) throws BadRequestException {
		if(request.getTxId() <= 0) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		Tx tx = daoFactory.getTxDAO().findById(request.getTxId());
		if(tx == null) {
			throw new BadRequestException("Invalid Transaction ID: " + request.getTxId());
		}
		String txStatus = EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId());
		if(!EnumUtil.isTxStatusApproved(txStatus)) {
			throw new BadRequestException("Invalid Transaction Status: " + txStatus);
		}
		if(!ConversionUtil.isValidMemberAcNo(request.getDoneByMemberAcNo()) || daoFactory.getMProfileDAO().getReferenceById(request.getDoneByMemberAcNo()) != null) {
			throw new BadRequestException("Invalid Done By Member Account no: " + request.getDoneByMemberAcNo());
		}
		TxTypeValue txType = EnumCache.getTxTypeValue(tx.getTxTypeId());
		if(txType == null) {
			throw new BadRequestException("Null Or Empty Transaction Type : " + request.getTxType());
		}

		Tx counterTx = new Tx();

		counterTx.setGroupAcNo(tx.getGroupAcNo());
		counterTx.setBankGroupAcNo(tx.getBankGroupAcNo());
		counterTx.setTxTodo(tx.getTxTodo());
		counterTx.setCounterTxId(tx.getTxId());
		counterTx.setMemberAcNo(tx.getMemberAcNo());
		counterTx.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Counter_Approved));
		counterTx.setDoneByMemberAcNo(request.getDoneByMemberAcNo());
		counterTx.setPaymentModeId(tx.getPaymentModeId());
		counterTx.setApprovedByMemberAcNo(request.getDoneByMemberAcNo());
		counterTx.setTxTypeId(tx.getTxTypeId());
		counterTx.setMemberBankAccount(tx.getMemberBankAccount());
		counterTx.setGroupBankAccount(tx.getGroupBankAccount());
		counterTx.setBankGroupBankAccount(tx.getBankGroupBankAccount());
		counterTx.setMemberLoanAcNo(tx.getMemberLoanAcNo());
		counterTx.setMemberSavingAcNo(tx.getMemberSavingAcNo());
		counterTx.setGroupInvtAcNo(tx.getGroupInvtAcNo());
		counterTx.setGroupLoanAcNo(tx.getGroupLoanAcNo());
		counterTx.setReceiptVoucherNo(tx.getReceiptVoucherNo());
		counterTx.setChequeNo(tx.getChequeNo());
		counterTx.setAmount(tx.getAmount().negate());
		counterTx.setPaymentTs(DateUtil.getCurrentTimeDate());
		counterTx.setEntryTs(DateUtil.getCurrentTimeDate());
		counterTx.setApprovedTs(DateUtil.getCurrentTimeDate());
		counterTx.setDescription(request.getDescription());
		counterTx.setEntryLocation(request.getLocation());

		daoFactory.getTxDAO().persist(counterTx);

		tx.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Countered));
		daoFactory.getTxDAO().merge(tx);

		TxTypeFormula formula = EnumCache.getTxTypeFormula(txType.getTxTypeId());
		// Transaction Post Processing
		if(formula.getFormulaOnDone() != null) {
			processJobBuilder.pushPostProcessJob(formula.getFormulaOnDone(), counterTx);
		}
		if(formula.getFormulaOnApprove() != null) {
			processJobBuilder.pushPostProcessJob(formula.getFormulaOnApprove(), counterTx);
		}
		
		processJobBuilder.pushMessageJob(counterTx);

		return convert(counterTx, null);
	}
	
	public Transaction importTransaction(Transaction request) throws BadRequestException {
		// Build transaction Object & Save

		if(request.getTxType() == null || request.getTxType().isEmpty()) {
			throw new BadRequestException("Null Or Empty Transaction Type");
		}
		TxTypeValue txType = EnumCache.getTxTypeValue(request.getTxType());
		if(txType == null) {
			throw new BadRequestException("Null Or Empty Transaction Type : " + request.getTxType());
		}

		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			if(request.getMemberAcNo() <= 0) {
				throw new BadRequestException("Invalid Member Account NO");
			}
		} else if(txType.getTxWith().equals(EnumConst.TxType_BANK)) {
			if(request.getExternalGroupAcNo() <= 0) {
				throw new BadRequestException("Invalid External Group Account NO");
			}
		}
		if(!ConversionUtil.isValidGroupAcNo(request.getGroupAcNo()) || (daoFactory.getGAcDAO().getReferenceById(request.getGroupAcNo()) == null)) {
			throw new BadRequestException("Invalid Group Account NO :" + request.getGroupAcNo());
		}
		if(!ConversionUtil.isValidMemberAcNo(request.getDoneByMemberAcNo()) || daoFactory.getMAcDAO().getReferenceById(request.getDoneByMemberAcNo()) == null) {
			throw new BadRequestException("Invalid Done By Member Account NO: " + request.getDoneByMemberAcNo());
		}
		if(request.getAmount().floatValue() == 0.0f) {
			throw new BadRequestException("Invalid Transaction Amount");
		}
		if(request.getPaymentMode() == null || request.getPaymentMode().isEmpty() ||
				daoFactory.getPaymentModeDAO().getReferenceByValue(request.getPaymentMode()) == null) {
			throw new BadRequestException("Null Or Empty Payment Mode: " + request.getPaymentMode());
		}
		
		Tx transaction = new Tx();

		transaction.setTxTypeId(txType.getTxTypeId());
		transaction.setReceiptVoucherNo(request.getSlipNo());

		transaction.setGroupAcNo(request.getGroupAcNo()); 

		transaction.setDoneByMemberAcNo(request.getDoneByMemberAcNo());
		
		transaction.setAmount(request.getAmount());
		transaction.setPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, request.getPaymentMode()));
		if(request.getPaymentMode().equals(EnumConst.PaymentMode_CHEQUE)) {
			transaction.setChequeNo(request.getChequeNo());
		}

		transaction.setEntryTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getEntryTs()));
		transaction.setPaymentTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getPaymentTs()));

		int status = EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, request.getStatus());
		if(status <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Tx Status");
		}
		transaction.setTxStatusId(status);

		if(!ConversionUtil.isValidMemberAcNo(request.getApprovedByMemberAcNo())) {
			if(daoFactory.getMAcDAO().getReferenceById(request.getApprovedByMemberAcNo()) == null) {
				throw new BadRequestException("Invalid Approved By Member Account NO: " + request.getApprovedByMemberAcNo());
			}
			transaction.setApprovedByMemberAcNo(request.getApprovedByMemberAcNo());
			transaction.setApprovedTs(DateUtil.convertTimeToDate(request.getApprovedTs()));
		}		
		if(request.getRelatedTxTodoId() > 0) {			
			transaction.setTxTodo(daoFactory.getTxTodoDAO().getReferenceById(request.getRelatedTxTodoId()));
		}
		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			if(!ConversionUtil.isValidMemberAcNo(request.getMemberAcNo()) || daoFactory.getMAcDAO().getReferenceById(request.getMemberAcNo()) == null) {
				throw new BadRequestException("Invalid Member Account NO: " + request.getMemberAcNo());
			}
			transaction.setMemberAcNo(request.getMemberAcNo());

			if(request.getSavingAcNo() > 0 && daoFactory.getMSavingAcDAO().getReferenceById(request.getSavingAcNo()) != null) {
				transaction.setMemberSavingAcNo(request.getSavingAcNo());
			}
			else if(request.getMemberLoanAcNo() > 0 && daoFactory.getMLoanAcDAO().getReferenceById(request.getMemberLoanAcNo()) != null) {
				transaction.setMemberLoanAcNo(request.getMemberLoanAcNo());
			}
			if(request.getMemberBankAcNo() > 0 && daoFactory.getMBankAccountDAO().getReferenceById(request.getMemberBankAcNo()) != null) {
				transaction.setMemberBankAccount(request.getMemberBankAcNo());
			}
		} else {
			if(!ConversionUtil.isValidGroupAcNo(request.getExternalGroupAcNo()) || (daoFactory.getGAcDAO().getReferenceById(request.getExternalGroupAcNo()) == null)) {
				throw new BadRequestException("Invalid External Group Account NO: " + request.getExternalGroupAcNo());
			}
			transaction.setBankGroupAcNo(request.getExternalGroupAcNo());

			if(request.getGroupLoanAcNo() > 0 && daoFactory.getGLoanAcDAO().getReferenceById(request.getGroupLoanAcNo()) != null) {
				transaction.setGroupLoanAcNo(request.getGroupLoanAcNo());
			}
			else if(request.getGroupInvtAcNo() > 0 && daoFactory.getGInvtAcDAO().getReferenceById(request.getGroupInvtAcNo()) != null) {
				transaction.setGroupInvtAcNo(request.getGroupInvtAcNo());
			}
			if(request.getExternalGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getExternalGroupBankAcNo()) != null) {
				transaction.setBankGroupBankAccount(request.getExternalGroupBankAcNo());
			}
		}
		if(!request.getPaymentMode().equals(EnumConst.PaymentMode_CASH)) {
			if(request.getGroupBankAcNo() <= 0 || daoFactory.getGBankAccountDAO().getReferenceById(request.getGroupBankAcNo()) == null) {
				throw new BadRequestException("Invalid Group Bak Account NO: " + request.getGroupBankAcNo());
			}
			transaction.setGroupBankAccount(request.getGroupBankAcNo());
		}
		if(request.getDescription() != null && !request.getDescription().isEmpty()) {
			transaction.setDescription(request.getDescription()); 
		}
		if(request.getLocation() != null && !request.getLocation().isEmpty()) {
			transaction.setEntryLocation(request.getLocation()); 
		}

		// Save Transaction to DB
		daoFactory.getTxDAO().persist(transaction);

		// Load Transaction Id back 
		request.setTxId(transaction.getTxId());
		
		return request;
	}

	protected List<Tx> convertCSVToTx(long groupAcNo, List<String[]> rawTransactions) throws BadRequestException {
		
		List<Tx> txs = new ArrayList<Tx>(rawTransactions.size());
		
		GroupInfoCollector collector = new GroupInfoCollector();
		collector.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNonTilelFullNamesForGroup("English", collector);
		daoFactory.getGBankAccountDAO().loadGroupBankAccountNo("English", collector);
				
		CSVDataCollector csvData = new CSVDataCollector(rawTransactions);

		if(!Transaction.isTransactionCSVValid(csvData)) {
			throw new BadRequestException("Invalid Transaction CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			txs.add(Transaction.buildTransaction(groupAcNo, csvData, row, collector));		
		}
		
		return txs;
	}
	
	public List<Map<String,String>> addActiveTransactionsCSV(long groupAcNo, List<String[]> rawTransactions) throws BadRequestException {

		List<Tx> txs = convertCSVToTx(groupAcNo, rawTransactions);
		
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(txs.size());
		
		for(Tx tx: txs) {
			returnList.add(importTransaction(tx, true).toStringInfo());
		}
		
		return returnList;
	}
	
	public List<Map<String,String>> addDeadTransactionsCSV(long groupAcNo, List<String[]> rawTransactions) throws BadRequestException {
		
		List<Tx> txs = convertCSVToTx(groupAcNo, rawTransactions);
		
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(txs.size());
		
		for(Tx tx: txs) {
			returnList.add(importTransaction(tx, false).toStringInfo());
		}
		
		return returnList;
	}

	public Tx importTransaction(Tx tx, boolean active) throws BadRequestException {
		
		// Save Transaction to DB
		daoFactory.getTxDAO().persist(tx);

		if(active) {
			TxTypeFormula formula = EnumCache.getTxTypeFormula(tx.getTxTypeId());
			// Transaction Post Processing
			if(formula.getFormulaOnDone() != null) {
				processJobBuilder.pushPostProcessJob(formula.getFormulaOnDone(), tx);
			}

			if(formula.getFormulaOnApprove() != null) {
				processJobBuilder.pushPostProcessJob(formula.getFormulaOnApprove(), tx);
			}
		}

		return null;
	}
	
	public List<Transaction> undoTransactions(List<Transaction> request) throws BadRequestException {
		
		long groupAcNo = DataUtil.ZERO_LONG;
		List<Tx> txs = new ArrayList<Tx>(request.size());
		
		// Add Multiple Transactions
		for(Transaction transaction : request) {
			if(transaction.getAmount() != null && (transaction.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER)) {
				txs.add(undoTransaction(transaction, false));
				groupAcNo = transaction.getGroupAcNo();
			} else {
				specialTodoTransaction(transaction);
			}
		}
		
		if(groupAcNo != DataUtil.ZERO_LONG && txs.size() > 0) {
			processJobBuilder.pushMessageJob(txs);
		}
		return request;
	}

	public Tx undoTransaction(Transaction request, boolean smsTx) throws BadRequestException {

		if(request.getTxId() <= 0) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		Tx transaction = daoFactory.getTxDAO().findById(request.getTxId());
		if(transaction == null) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		if(!EnumUtil.isTxStatusDone(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()))) {
			throw new BadRequestException("Invalid Transaction Current State :" + 
					EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()));
		}
		if(request.getTxType() == null || EnumCache.getIndexOfTxType(request.getTxType()) != transaction.getTxTypeId()) {
			throw new BadRequestException("TxType Rquested Not Valid for the Given Transaction");
		}

		TxTypeValue txType = EnumCache.getTxTypeValue(transaction.getTxTypeId());
		TxTodo txTransaction = null;

		if(transaction.getTxTodo() != null) {
			txTransaction = daoFactory.getTxTodoDAO().findById(transaction.getTxTodo().getTxTodoId());
			
			if(txTransaction == null) {
				throw new BadRequestException("Requested Transaction Not releated to Member AC No");
			}
			
			txTransaction.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Undone));

			daoFactory.getTxTodoDAO().merge(txTransaction);
		}

		if(request.getReasonToUndo() != null) {
			transaction.setReasonToUndoId(EnumCache.getIndexOfEnumValue(EnumConst.ReasonToUndo, request.getReasonToUndo()));
		}
		if(request.getDescription() != null) {
			transaction.setDescription(request.getDescription());
		}
		if(request.getLocation() != null) {
			transaction.setEntryLocation(request.getLocation());
		}
		
		transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Undone));
		transaction.setApprovedByMemberAcNo(request.getDoneByMemberAcNo());
		transaction.setApprovedTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getApprovedTs()));

		daoFactory.getTxDAO().merge(transaction);
		
		TxTypeFormula formula = EnumCache.getTxTypeFormula(txType.getTxTypeId());
		if(transaction != null && formula.getFormulaOnUndone() != null) {
			processJobBuilder.pushPostProcessJob(formula.getFormulaOnUndone(), transaction);
		}
		
		if(smsTx) {
			processJobBuilder.pushMessageJob(transaction);
		}

		return transaction;
	}

	public List<Transaction> approveRejectTransactions(List<Transaction> request) throws BadRequestException {
		
		long groupAcNo = DataUtil.ZERO_LONG;
		List<Tx> txs = new ArrayList<Tx>(request.size());
		
		// Add Multiple Transactions
		for(Transaction transaction : request) {
			if(transaction.getAmount() != null && (transaction.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != DataUtil.ZERO_INTEGER)) {
				txs.add(approveRejectTransaction(transaction, false));
				groupAcNo = transaction.getGroupAcNo();
			} else {
				specialTodoTransaction(transaction);
			}
		}
		
		if(groupAcNo != DataUtil.ZERO_LONG && txs.size() > 0) {
			processJobBuilder.pushMessageJob(txs);
		}
		return request;
	}
	
	public Tx approveRejectTransaction(Transaction request, boolean smsTx) throws BadRequestException {

		if(request.getTxId() <= 0) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		if(request.getStatus() == null || !EnumUtil.isTxStatusToApproveReject(request.getStatus())) {
			throw new BadRequestException("Invalid Rquested TxStatus, should be either Approved or Rejected");
		}

		Tx transaction = daoFactory.getTxDAO().findById(request.getTxId());
		if(transaction == null) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		if(!EnumUtil.isTxStatusDone(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()))) {
			throw new BadRequestException("Invalid Transaction Current State :" + EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()));
		}
		if(request.getTxType() == null || EnumCache.getIndexOfTxType(request.getTxType()) != transaction.getTxTypeId()) {
			throw new BadRequestException("TxType Rquested Not Valid for the Given Transaction");
		}

		TxTypeValue txType = EnumCache.getTxTypeValue(transaction.getTxTypeId());

		if(request.getStatus() != null) {
			
			if(EnumUtil.isTxStatusToApproveReject(request.getStatus())) {
				transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, request.getStatus()));
				transaction.setApprovedByMemberAcNo(request.getApprovedByMemberAcNo());
				transaction.setApprovedTs(DateUtil.convertTimeToDateWithCurrentDefault(request.getApprovedTs()));
				
			} else {
				throw new BadRequestException("Invalid Transaction Status to Update: " + request.getStatus());
			}

			if(transaction.getTxTodo() != null) {
				TxTodo txTransaction = daoFactory.getTxTodoDAO().findById(transaction.getTxTodo().getTxTodoId());
				
				if(txTransaction == null) {
					throw new BadRequestException("Requested Transaction Not releated to Member AC No");
				}
				
				txTransaction.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, request.getStatus()));
				daoFactory.getTxTodoDAO().merge(txTransaction);
			}
		}
		
		if(request.getPaymentTs() > DataUtil.ZERO_LONG) {
			if(request.getPaymentTs() > System.currentTimeMillis()) {
				throw new BadRequestException("Payment Date Can't be for Future : " + DateUtil.convertTimeToString(request.getPaymentTs()));
			}
			if(DateUtil.getMonth(request.getPaymentTs()) != DateUtil.getMonth(System.currentTimeMillis()) ||
					DateUtil.getYear(request.getPaymentTs()) != DateUtil.getYear(System.currentTimeMillis())) {
				throw new BadRequestException("Payment Date Not of Current Month : " + DateUtil.convertTimeToString(request.getPaymentTs()));
			}
			transaction.setPaymentTs(DateUtil.convertTimeToDate(request.getPaymentTs()));
		}


		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			if(request.getMemberBankAcNo() > 0 && daoFactory.getMBankAccountDAO().getReferenceById(request.getMemberBankAcNo()) != null) {
				transaction.setMemberBankAccount(request.getMemberBankAcNo());
			}
		}
		else {
			if(request.getExternalGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getExternalGroupBankAcNo()) != null) {
				transaction.setBankGroupBankAccount(request.getExternalGroupBankAcNo());
			}
		}
		if(request.getGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getGroupBankAcNo()) != null) {
			transaction.setGroupBankAccount(request.getGroupBankAcNo());
		}
		if(request.getDescription() != null) {
			transaction.setDescription(request.getDescription());
		}
		if(request.getLocation() != null) {
			transaction.setEntryLocation(request.getLocation());
		}

		daoFactory.getTxDAO().merge(transaction);
		
		if(transaction != null) {
			TxTypeFormula formula = EnumCache.getTxTypeFormula(txType.getTxTypeId());
			
			if(request.getStatus().equals(EnumConst.TxStatus_Approved)) {
				if(formula.getFormulaOnApprove() != null) {
					processJobBuilder.pushPostProcessJob(formula.getFormulaOnApprove(), transaction);
				}
			} else if(request.getStatus().equals(EnumConst.TxStatus_Rejected)) {
				if(formula.getFormulaOnReject() != null) {
					processJobBuilder.pushPostProcessJob(formula.getFormulaOnReject(), transaction);
				}
			}
			
			if(smsTx) {
				processJobBuilder.pushMessageJob(transaction);
			}
		}
		
		return transaction;
	}

	public void rejectTodoTransactions(TodoTransactions request) throws BadRequestException {

		if(request == null) {
			throw new BadRequestException("Invalid Input Data");
		}
		if(request.getTodoTransactions() == null || request.getTodoTransactions().isEmpty()) {
			throw new BadRequestException("Invalid Todo Transactions Data");
		}
		
		for(TodoTransaction todoTx: request.getTodoTransactions()) {
			
			if(todoTx.getTodoTxId() > DataUtil.ZERO_LONG) {
				TxTodo txTransaction = daoFactory.getTxTodoDAO().findById(todoTx.getTodoTxId());
				
				if(txTransaction == null) {
					throw new BadRequestException("Invalid Todo Transaction id:" + todoTx.getTodoTxId());
				}
				
				txTransaction.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Rejected));
				if(todoTx.getDescription() != null) {
					txTransaction.setDescription(todoTx.getDescription());
				}
				daoFactory.getTxTodoDAO().merge(txTransaction);
			}
		}
	}

	public void updateTransaction(Transaction request) throws BadRequestException {

		if(request.getTxId() <= 0) {
			throw new BadRequestException("Invalid Transaction ID");
		}

		Tx transaction = daoFactory.getTxDAO().findById(request.getTxId());
		if(transaction == null) {
			throw new BadRequestException("Invalid Transaction ID");
		}
		if(!EnumUtil.isTxStatusDone(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()))) {
			throw new BadRequestException("Invalid Transaction Current State :" + EnumCache.getNameOfEnumValue(EnumConst.TxStatus, transaction.getTxStatusId()));
		}

		if(request.getStatus() != null) {		
			if(EnumUtil.isTxStatusToUpdable(request.getStatus())) {			
				transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, request.getStatus()));
			} else {
				throw new BadRequestException("Invalid Transaction Status to Update: " + request.getStatus());
			}
		}

		transaction.setPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, request.getPaymentMode()));
		TxTypeValue txType = EnumCache.getTxTypeValue(transaction.getTxTypeId());

		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			if(request.getMemberBankAcNo() > 0 && daoFactory.getMBankAccountDAO().getReferenceById(request.getMemberBankAcNo()) != null) {
				transaction.setMemberBankAccount(request.getMemberBankAcNo());
			}
		}
		else {
			if(request.getExternalGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getExternalGroupBankAcNo()) != null) {
				transaction.setBankGroupBankAccount(request.getExternalGroupBankAcNo());
			}
		}
		if(request.getGroupBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(request.getGroupBankAcNo()) != null) {
			transaction.setGroupBankAccount(request.getGroupBankAcNo());
		}

		if(request.getDescription() != null && !request.getDescription().isEmpty()) {
			transaction.setDescription(transaction.getDescription()); 
		}
		if(request.getLocation() != null && !request.getLocation().isEmpty()) {
			transaction.setEntryLocation(transaction.getEntryLocation()); 
		}

		// Save Transaction to DB
		daoFactory.getTxDAO().merge(transaction);
	}

	public List<Transaction> updateTransactions(List<Transaction> request) throws BadRequestException {

		// Update Multiple Transactions
		for(Transaction transaction : request) {
			updateTransaction(transaction);
		}

		return null;
	}

	public TodoTransaction addTodoTransaction(TodoTransaction request) throws BadRequestException {

		TxTypeValue txType = EnumCache.getTxTypeValue(request.getTxType());

		TxTodo txTodo = new TxTodo();
		txTodo.setGroupAcNo(request.getGroupAcNo());
		
		if(txType.getTxWith().equals(EnumConst.TxType_MEMBER)) {
			txTodo.setMemberAcNo(request.getMemberAcNo());
			if(request.getTxTodoStatus().equals(EnumConst.TxAcType_Member_Saving)) {
				txTodo.setMemberSavingAcNo(request.getInstAcNo());
			} else {
				txTodo.setMemberLoanAcNo(request.getInstAcNo());
			}
		} else {
			txTodo.setBankGroupAcNo(request.getExternalGroupAcNo());
			txTodo.setGroupLoanAcNo(request.getInstAcNo());
		}
		txTodo.setTxTypeId(txType.getTxTypeId());
		txTodo.setExpectedPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, request.getExpectedPaymentMode()));
		txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, request.getTxTodoStatus()));
		txTodo.setAmount(request.getAmount());
		txTodo.setPenaltyPaidAm(request.getPenaltyPaidAm());
		if(request.getDueDateTs() > DataUtil.ZERO_LONG) {
			txTodo.setDueDate(DateUtil.convertTimeToDate(request.getDueDateTs()));
		}
		txTodo.setDescription(request.getDescription());
		txTodo.setCreationOnTs(DateUtil.convertTimeToDate(request.getCreatedOnTs()));
		
		// Save Transaction to DB
		daoFactory.getTxTodoDAO().persist(txTodo);
		request.setTodoTxId(txTodo.getTxTodoId());

		return request;
	}

	public TodoTransactions getGroupTodoTxs(String lang, long groupAcNo, String txTypeStr) throws BadRequestException {
		return getGroupTodoTxs(lang, groupAcNo, DateUtil.getStartTimeOfCurMonth(), DateUtil.getEndTimeOfCurMonth(), txTypeStr);
	}

	public TodoTransactions getMemberTodoTxs(String lang, long memberAcNo, String txTypeStr) throws BadRequestException {
		return getMemberTodoTxs(lang, memberAcNo, DateUtil.getStartTimeOfCurMonth(), DateUtil.getEndTimeOfCurMonth(), txTypeStr);
	}
	
	public TodoTransactions getGroupTodoTxs(String lang, long groupAcNo, long startTime, long endTime, String txTypeStr) throws BadRequestException {

		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account NO");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}

		int txTypeId = DataUtil.ZERO_INTEGER;
		if(txTypeStr != null && !txTypeStr.isEmpty()) {
			TxType txType = daoFactory.getTxTypeDAO().findByValue(txTypeStr);
			if(txType != null) {
				txTypeId = txType.getTxTypeId();
			}
		}
		List<TxTodo> txTodos = null;
		
		if(txTypeId > DataUtil.ZERO_INTEGER) {		
			txTodos = daoFactory.getTxTodoDAO().getAllTxsForGroup(groupAcNo, startTime, endTime, txTypeId);
		} else {
			txTodos = daoFactory.getTxTodoDAO().getAllTxsForGroup(groupAcNo, startTime, endTime);
		}
		
		TodoTransactions todoTransactions = null;

		if(txTodos != null && txTodos.size() > 0) {
			todoTransactions = convertTodoTxs(lang, txTodos, true, true, groupAcNo);
			todoTransactions.setStartTime(startTime);
			todoTransactions.setEndTime(endTime);
		}

		return todoTransactions;
	}

	public TodoTransactions getMemberTodoTxs(String lang, long memberAcNo, long startTime, long endTime, String txTypeStr) throws BadRequestException {

		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account NO");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}

		int txTypeId = DataUtil.ZERO_INTEGER;
		if(txTypeStr != null && !txTypeStr.isEmpty()) {
			TxType txType = daoFactory.getTxTypeDAO().findByValue(txTypeStr);
			if(txType != null) {
				txTypeId = txType.getTxTypeId();
			}
		}
		List<TxTodo> txTodos = null;
		
		if(txTypeId > 0) {		
			txTodos = daoFactory.getTxTodoDAO().getAllTxsForMember(memberAcNo, startTime, endTime, txTypeId);
		} else {
			txTodos = daoFactory.getTxTodoDAO().getAllTxsForMember(memberAcNo, startTime, endTime);
		}

		TodoTransactions todoTransactions = null;

		if(txTodos != null && txTodos.size() > 0) {
			todoTransactions = convertTodoTxs(lang, txTodos, true, true, ConversionUtil.getGroupAcFromMember(memberAcNo));
			todoTransactions.setStartTime(startTime);
			todoTransactions.setEndTime(endTime);
		}

		return todoTransactions;
	}

	public Transactions getGroupTxs(String lang, long groupAcNo, String txType) throws BadRequestException {
		return getGroupTxs(lang, groupAcNo, DateUtil.getStartTimeOfCurMonth(), DateUtil.getEndTimeOfCurMonth(), txType);
	}

	public Transactions getGroupTxs(String lang, long groupAcNo, long startTime, long endTime, String txType) throws BadRequestException {
		return getGroupTxs(lang, groupAcNo, startTime, endTime, txType, DataUtil.EMPTY_STRING, DataUtil.ZERO_LONG);
	}
	
	public Transactions getGroupTxs(String lang, long groupAcNo, long startTime, long endTime, String txTypeStr, String bookType, long bankAcNo) throws BadRequestException {

		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account NO");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}

		int txTypeId = DataUtil.ZERO_INTEGER;
		if(txTypeStr != null && !txTypeStr.isEmpty()) {
			txTypeId = EnumCache.getIndexOfTxType(txTypeStr);
		}
		
		List<Tx> txs = null;
		
		if(txTypeId > 0) {	
			txs = daoFactory.getTxDAO().getAllTxsForGroup(groupAcNo, startTime, endTime, txTypeId);
			
		} else if(!bookType.equals(DataUtil.EMPTY_STRING)) {
			
			if(bookType.equals(EnumConst.BankBook)) {
				
				// Retrieve Transactions for Specific Bank Account
				if(bankAcNo > 0) {
					txs = daoFactory.getTxDAO().getBankTxsForGroup(groupAcNo, startTime, endTime, bankAcNo);
				} 
				// Retrieve Transactions for All Bank Account
				else {
					txs = daoFactory.getTxDAO().getAllBankTxsForGroup(groupAcNo, startTime, endTime);
				}
				
			// Retrieve All Cash Transactions
			} else if(bookType.equals(EnumConst.CashBook)) {
				txs = daoFactory.getTxDAO().getCashTxsForGroup(groupAcNo, startTime, endTime);
				
			// Retrieve All Transactions
			} else if(bookType.equals(EnumConst.JointBook)) {
				txs = daoFactory.getTxDAO().getAllTxsForGroup(groupAcNo, startTime, endTime);
			}			
		} else {
			txs = daoFactory.getTxDAO().getAllTxsForGroup(groupAcNo, startTime, endTime);
			
		}

		Transactions transactions = null;

		if(txs != null && txs.size() > 0) {
			transactions = convertTxs(lang, txs, true, groupAcNo);
			transactions.setStartTime(startTime);
			transactions.setEndTime(endTime);
		}

		return transactions;
	}
	
	public Transactions getGroupReadyTxs(String lang, long groupAcNo) throws BadRequestException {

		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account NO");
		}

		List<Tx> txs = daoFactory.getTxDAO().getReadyTxsForGroup(groupAcNo);

		Transactions transactions = null;

		if(txs != null && txs.size() > 0) {
			transactions = convertTxs(lang, txs, true, groupAcNo);
		}

		return transactions;
	}
	
	public Transactions trackTransactions(TransactionFilter filter) throws BadRequestException {

		if(filter.getLang() == null || filter.getLang().isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidGroupAcNo(filter.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account NO");
		}
		if(!ConversionUtil.isTimeValid(filter.getStartTime())) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(filter.getEndTime())) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}

		int txTypeId = 0;
		if(filter.getTxType() != null && !filter.getTxType().isEmpty()) {
			txTypeId = daoFactory.getTxTypeDAO().findByValue(filter.getTxType()).getTxTypeId();
		}
		int txStatusId = 0;
		if(filter.getTxStatus() != null && !filter.getTxStatus().isEmpty()) {
			txStatusId = daoFactory.getTxStatusDAO().findByValue(filter.getTxStatus()).getTxStatusId();
		}
		int paymentModeId = 0;
		if(filter.getPaymentMode() != null && !filter.getPaymentMode().isEmpty()) {
			paymentModeId = daoFactory.getPaymentModeDAO().findByValue(filter.getPaymentMode()).getPaymentModeId();
		}
		
		List<Tx> txs = null;

		txs = daoFactory.getTxDAO().getAllTxsForGroup(filter.getGroupAcNo(), 
				filter.getStartTime(), 
				filter.getEndTime(), 
				txTypeId,
				txStatusId,
				paymentModeId,
				filter.getMemberAcNo(),
				filter.getDoneByMember(),
				filter.getApprovedByMember(),
				filter.getRangeStart(),
				filter.getLimit());

		Transactions transactions = null;

		if(txs != null && txs.size() > 0) {
			transactions = convertTxs(filter.getLang(), txs, true, filter.getGroupAcNo());
			transactions.setStartTime(filter.getStartTime());
			transactions.setEndTime(filter.getEndTime());
		}

		return transactions;
	}

	public Transactions getMemberTxs(String lang, long memberAcNo, String txTypeStr) throws BadRequestException {
		return getMemberTxs(lang, memberAcNo, DateUtil.getStartTimeOfCurMonth(), DateUtil.getEndTimeOfCurMonth(), txTypeStr);
	}

	public Transactions getMemberTxs(String lang, long memberAcNo, long startTime, long endTime, String txTypeStr) throws BadRequestException {

		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Null Or Empty Langauge");
		}
		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account NO");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}
		if(!ConversionUtil.isTimeValid(endTime)) {
			throw new BadRequestException("Time Out of Current Range 01/01/2000 & 01/01/2040");
		}

		int txTypeId = DataUtil.ZERO_INTEGER;
		if(txTypeStr != null && !txTypeStr.isEmpty()) {
			TxType txType = daoFactory.getTxTypeDAO().findByValue(txTypeStr);
			if(txType != null) {
				txTypeId = txType.getTxTypeId();
			}
		}
		
		List<Tx> txs = null; 
		
		if(txTypeId > 0) {		
			txs = daoFactory.getTxDAO().getAllTxsForMember(memberAcNo, startTime, endTime, txTypeId);
		} else {
			txs = daoFactory.getTxDAO().getAllTxsForMember(memberAcNo, startTime, endTime);
		}

		Transactions transactions = null;

		if(txs != null && txs.size() > 0) {
			transactions = convertTxs(lang, txs, false, ConversionUtil.getGroupAcFromMember(memberAcNo));
			transactions.setStartTime(startTime);
			transactions.setEndTime(endTime);
		}

		return transactions;
	}

	protected TodoTransactions convertTodoTxs(String lang, List<TxTodo> txTodos, boolean addTransaction, boolean addBankAcNo, long groupAcNo) {

		TodoTransactions todoTransactions = new TodoTransactions();
		List<TodoTransaction> txList = null;
		int toPayMembersNo = DataUtil.ZERO_INTEGER;
		int paidMembersNo = DataUtil.ZERO_INTEGER;
		BigDecimal pendingAmount = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal paidAmount = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal appliedPenalty = DataUtil.ZERO_BIG_DECIMAL;

		if(txTodos != null && txTodos.size() > 0) {
			
			BankAccountInfoCollector bankAccountCol = new BankAccountInfoCollector();
			if(addBankAcNo && groupAcNo > DataUtil.ZERO_LONG) {					
				bankAccountCol.setGroupAcNo(groupAcNo);
				daoFactory.getMBankAccountDAO().loadMBankAccountForGroup(bankAccountCol);
			}

			GroupInfoCollector nameCol = new GroupInfoCollector();
			// Load Member Names
			for(TxTodo txTodo : txTodos) {
				nameCol.addMemberAcNo(txTodo.getMemberAcNo());
			}

			daoFactory.getMemberContactDAO().loadMemberNames(lang, nameCol);

			txList = new ArrayList<TodoTransaction>(txTodos.size());

			for(TxTodo txTodo : txTodos) {
				TodoTransaction todoTransaction = new TodoTransaction();

				TxTypeValue txType = EnumCache.getTxTypeValue(txTodo.getTxTypeId());
				todoTransaction.setTodoTxId(txTodo.getTxTodoId());
				todoTransaction.setTxType(txType.getTxType());
				todoTransaction.setSlipType(txType.getSlipType());
				todoTransaction.setTxTodoStatus(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, txTodo.getTxTodoStatusId()));
				todoTransaction.setAmount(DataUtil.toBigDecimal(txTodo.getAmount()));
				todoTransaction.setInterestComponent(DataUtil.toBigDecimal(txTodo.getInterestComponent()));
				todoTransaction.setExpectedPaymentMode(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, txTodo.getExpectedPaymentModeId()));
				todoTransaction.setMemberAcNo(txTodo.getMemberAcNo());
				todoTransaction.setGroupAcNo(txTodo.getGroupAcNo());
				if(nameCol.isMemberAcPresent(txTodo.getMemberAcNo())) {
					todoTransaction.setMRole(DataUtil.toString(EnumCache.getNameOfMRole(nameCol.getMemberProfile(txTodo.getMemberAcNo()).getMroleId())));
					todoTransaction.setMemberName(DataUtil.toString(nameCol.getMemberName(txTodo.getMemberAcNo())));
				}
				todoTransaction.setPenaltyAm(DataUtil.toBigDecimal(txTodo.getPenaltyAm()));
				todoTransaction.setPenaltyPaidAm(DataUtil.toBigDecimal(txTodo.getPenaltyPaidAm()));
				todoTransaction.setDueDateTs(txTodo.getDueDate().getTime());
				todoTransaction.setDescription(DataUtil.toString(txTodo.getDescription()));

				if(txTodo.getMemberSavingAcNo() > 0) {
					todoTransaction.setInstAcNo(txTodo.getMemberSavingAcNo());
					todoTransaction.setTxAcType(EnumConst.TxAcType_Member_Saving);
				} else if(txTodo.getMemberLoanAcNo() > 0) {
					todoTransaction.setInstAcNo(txTodo.getMemberLoanAcNo());
					todoTransaction.setTxAcType(EnumConst.TxAcType_Member_Loan);
				} else if(txTodo.getGroupLoanAcNo() > 0) {
					todoTransaction.setInstAcNo(txTodo.getMemberLoanAcNo());
					todoTransaction.setTxAcType(EnumConst.TxAcType_Member_Loan);
				}

				// Load Actual Related Transactions 
				if(addTransaction && txTodo.getTxs() != null) {
					List<Tx> txs = new ArrayList<Tx>(txTodo.getTxs());
					Collections.sort(txs);
					for(Tx tx: txs) {
						if(EnumUtil.isTxStatusDone(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId()))) {	
							todoTransaction.addRelatedTxId(tx.getTxId());
							todoTransaction.addTransaction(convert(tx, bankAccountCol));
						}
					}
				}
				
				// Load Bank Account Info for the Member for TODO Transaction
				if(addBankAcNo && groupAcNo > DataUtil.ZERO_LONG) {					
					todoTransaction.setMemberBankAcNos(bankAccountCol.getMemberBankAccountShort(todoTransaction.getMemberAcNo()));
				}

				// Aggregate Paid & Pending - Amount & No 
				if(EnumUtil.isTxTodoStatusDoneOrApproved(todoTransaction.getTxTodoStatus())) {
					paidMembersNo++;
					paidAmount = paidAmount.add(txTodo.getAmount());
				} else {
					toPayMembersNo++;
					pendingAmount = pendingAmount.add(txTodo.getAmount());
				}
				
				// Aggregate Applied Penalty 
				if(txTodo.getPenaltyAm() != null && EnumUtil.isTxTodoStatusDoneOrApproved(todoTransaction.getTxTodoStatus())) {
					appliedPenalty = appliedPenalty.add(txTodo.getPenaltyAm());
				}
				
				todoTransactions.addTxStatus(todoTransaction.getTxTodoStatus());
				txList.add(todoTransaction);
			}
		}
		
		todoTransactions.setTodoTransactions(txList);
		todoTransactions.setToPayMembersNo(toPayMembersNo);
		todoTransactions.setPaidMembersNo(paidMembersNo);
		todoTransactions.setPendingAmount(pendingAmount);
		todoTransactions.setPaidAmount(paidAmount);
		todoTransactions.setAppliedPenalty(appliedPenalty);
		todoTransactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
		todoTransactions.setDisplayNames(RestDisplayTitle.getTodoTransactionRDT());
		todoTransactions.setRuleAmounts(RestDisplayTitle.getRuleAmounts(daoFactory.getGRulesDAO().findById(groupAcNo)));

		return todoTransactions;
	}

	protected Transactions convertTxs(String lang, List<Tx> txs, boolean addBankAcNo, long groupAcNo) {

		Transactions transactions = null;

		if(txs != null && txs.size() > 0) {
			
			BankAccountInfoCollector bankAccountCol = new BankAccountInfoCollector();
			if(addBankAcNo && groupAcNo > DataUtil.ZERO_LONG) {					
				bankAccountCol.setGroupAcNo(groupAcNo);
				daoFactory.getMBankAccountDAO().loadMBankAccountForGroup(bankAccountCol);
			}

			GAc gAc = daoFactory.getGAcDAO().findById(groupAcNo);
			List<BankAccountShort> gBankAcsShort = daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo);
			
			GroupInfoCollector memberNameCollector = new GroupInfoCollector();
			// Load Member Names
			for(Tx tx : txs) {
				memberNameCollector.addMemberAcNo(tx.getMemberAcNo());
				if(tx.getDoneByMemberAcNo() > 0) {
					memberNameCollector.addMemberAcNo(tx.getDoneByMemberAcNo());
				}
				if(tx.getApprovedByMemberAcNo() > 0) {
					memberNameCollector.addMemberAcNo(tx.getApprovedByMemberAcNo());
				}
			}

			daoFactory.getMemberContactDAO().loadMemberNames(lang, memberNameCollector);

			List<Transaction> txList = new ArrayList<Transaction>(txs.size());

			for(Tx tx : txs) {
				Transaction transaction = new Transaction();

				transaction.setTxId(tx.getTxId());

				if(tx.getTxTodo() != null) {
					transaction.setRelatedTxTodoId(tx.getTxTodo().getTxTodoId());
				}

				TxTypeValue txType = EnumCache.getTxTypeValue(tx.getTxTypeId());
				transaction.setTxType(txType.getTxType());
				transaction.setSlipType(txType.getSlipType());
				transaction.setTxWith(txType.getTxWith());
				
				if(tx.getReceiptVoucherNo() != null) {
					transaction.setSlipNo(tx.getReceiptVoucherNo());
				} else {
					transaction.setSlipNo(DataUtil.EMPTY_STRING);
				}
				
				transaction.setGroupAcNo(tx.getGroupAcNo());

				if(tx.getDoneByMemberAcNo() > 0) {
					transaction.setDoneByMemberAcNo(tx.getDoneByMemberAcNo());
					transaction.setDoneByMemberName(memberNameCollector.getMemberName(tx.getDoneByMemberAcNo()));
				} else {
					transaction.setDoneByMemberName(DataUtil.EMPTY_STRING);
				}
				
				if(tx.getApprovedByMemberAcNo() > 0) {
					transaction.setApprovedByMemberAcNo(tx.getApprovedByMemberAcNo());
					transaction.setApprovedByMemberName(memberNameCollector.getMemberName(tx.getApprovedByMemberAcNo()));
				} else {
					transaction.setApprovedByMemberName(DataUtil.EMPTY_STRING);
				}
				
				if(tx.getMemberSavingAcNo() > 0) {
					transaction.setSavingAcNo(tx.getMemberSavingAcNo());
				}
				if(tx.getMemberLoanAcNo() > 0) {
					transaction.setMemberLoanAcNo(tx.getMemberLoanAcNo());
				}
				
				if(tx.getMemberAcNo() > 0) {
					transaction.setMemberAcNo(tx.getMemberAcNo());
					transaction.setMemberName(memberNameCollector.getMemberName(tx.getMemberAcNo()));
				}
				else if(tx.getGroupInvtAcNo() > 0 || tx.getGroupLoanAcNo() > 0) {

					if(tx.getGroupInvtAcNo() > 0) {
						transaction.setGroupInvtAcNo(tx.getGroupInvtAcNo());
						for(GInvtAc gInvtAc: gAc.getGInvtAcs()) {
							if(gInvtAc.getGInvtAcNo() == tx.getGroupInvtAcNo()) {
								transaction.setMemberName(gInvtAc.getInvestmentAcName());
							}
						}
					}

					if(tx.getGroupLoanAcNo() > 0) {
						transaction.setGroupLoanAcNo(tx.getGroupLoanAcNo());
						for(GLoanAc gLoanAc: gAc.getGLoanAcs()) {
							if(gLoanAc.getGLoanAcNo() == tx.getGroupLoanAcNo()) {
								transaction.setMemberName(gLoanAc.getLoanAcName());
							}
						}
					}

					transaction.setExternalGroupAcNo(tx.getBankGroupAcNo());
					transaction.setExternalGroupBankAcNo(tx.getBankGroupBankAccount());
					for(BankAccountShort bAcShort: gBankAcsShort) {
						if(tx.getBankGroupBankAccount() == bAcShort.getBankAcNo()) {
							transaction.setExternalGroupBankAcDisplay(bAcShort);
						}
					}
				}
				else if(txType.getTxType().equals(EnumConst.TxType_Bank_Interest_Earned)) {
					transaction.setExternalGroupAcNo(tx.getBankGroupAcNo());
					for(BankAccountShort bAcShort: gBankAcsShort) {
						if(tx.getGroupBankAccount() == bAcShort.getBankAcNo()) {
							transaction.setMemberName(bAcShort.getDisplayAccount());
						}
					}
				} else {
					transaction.setMemberName(DataUtil.EMPTY_STRING);
				}
				
				transaction.setAmount(tx.getAmount());
				transaction.setPaymentMode(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId()));
				if(tx.getGroupBankAccount() > 0) {
					transaction.setGroupBankAcNo(tx.getGroupBankAccount());
					
					for(BankAccountShort bAcShort: gBankAcsShort) {
						if(tx.getGroupBankAccount() == bAcShort.getBankAcNo()) {
							transaction.setGroupBankAcDisplay(bAcShort);
						}
					}
				}
				if(tx.getMemberBankAccount() > 0) {
					transaction.setMemberBankAcNo(tx.getMemberBankAccount());
					
					for(BankAccountShort bAcShort: bankAccountCol.getMemberBankAccountShort(transaction.getMemberAcNo())) {
						if(tx.getMemberBankAccount() == bAcShort.getBankAcNo()) {
							transaction.setMemberBankAcDisplay(bAcShort);
						}
					}
				}
				
				// Load Bank Account Info for the Member for Transaction
				if(addBankAcNo && groupAcNo > DataUtil.ZERO_LONG) {					
					transaction.setMemberBankAcNos(bankAccountCol.getMemberBankAccountShort(transaction.getMemberAcNo()));
				}

				transaction.setStatus(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId()));
				
				if(tx.getChequeNo() != null) {
					transaction.setChequeNo(tx.getChequeNo());
				} else {
					transaction.setChequeNo(DataUtil.EMPTY_STRING);
				}

				if(tx.getReasonToUndoId() > 0) {
					transaction.setReasonToUndo(EnumCache.getNameOfEnumValue(EnumConst.ReasonToUndo, tx.getReasonToUndoId()));
				} else {
					transaction.setReasonToUndo(DataUtil.EMPTY_STRING);
				}

				if(tx.getDescription() != null) {
					transaction.setDescription(tx.getDescription());
				} else {
					transaction.setDescription(DataUtil.EMPTY_STRING);
				}
				
				if(tx.getEntryLocation() != null) {
					transaction.setLocation(Transaction.getLocation(tx.getEntryLocation()));
					transaction.setAttachments(Transaction.getAttachments(tx.getEntryLocation()));
				} else {
					transaction.setLocation(DataUtil.EMPTY_STRING);
				}
				
				if(tx.getPaymentTs() != null) {
					transaction.setPaymentTs(tx.getPaymentTs().getTime());
				}
				if(tx.getEntryTs() != null) {
					transaction.setEntryTs(tx.getEntryTs().getTime());
				}
				if(tx.getApprovedTs() != null) {
					transaction.setApprovedTs(tx.getApprovedTs().getTime());
				}

				txList.add(transaction);
			}
			
			transactions = new Transactions();
			transactions.setTransactions(txList);
			transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());

		}

		return transactions;
	}
	
	public Transaction convert(Tx tx, BankAccountInfoCollector bankAccountCol) {
		Transaction tran = new Transaction();
		TxTypeValue txType = EnumCache.getTxTypeValue(tx.getTxTypeId());
		tran.setTxId(tx.getTxId());
		tran.setTxType(txType.getTxType());
		tran.setSlipType(txType.getSlipType());
		tran.setSlipNo(tx.getReceiptVoucherNo());
		
		if(tx.getMemberAcNo() > 0) {
			tran.setMemberAcNo(tx.getMemberAcNo());
			if(tx.getMemberBankAccount() > 0) {
				if(bankAccountCol != null) {
					tran.setMemberBankAcDisplay(bankAccountCol.getMemberBankAcForBankAcNoShort(tx.getMemberBankAccount()));
				} else {
					tran.setMemberBankAcDisplay(daoFactory.getMBankAccountDAO().findByIdForDisplay(tx.getMemberBankAccount()));
				}
			}

			if(tx.getMemberSavingAcNo() > 0) {
				tran.setSavingAcNo(tx.getMemberSavingAcNo());
				tran.setTxAcType(EnumConst.TxAcType_Member_Saving);

			} else if(tx.getMemberLoanAcNo() > 0) {
				tran.setMemberLoanAcNo(tx.getMemberLoanAcNo());
				tran.setTxAcType(EnumConst.TxAcType_Member_Loan);
			}
		} 
		else if(tx.getBankGroupAcNo() > 0) {
			tran.setExternalGroupAcNo(tx.getBankGroupAcNo());
			if(tx.getBankGroupBankAccount() > 0) {
				tran.setExternalGroupBankAcDisplay(daoFactory.getGBankAccountDAO().getGroupBankAcDisplayForAcNo(tx.getBankGroupBankAccount()));
			}

			if(tx.getGroupInvtAcNo() > 0) {
				tran.setGroupInvtAcNo(tx.getGroupInvtAcNo());
				tran.setTxAcType(EnumConst.TxAcType_Group_Investment);

			} else if(tx.getGroupLoanAcNo() > 0) {
				tran.setGroupLoanAcNo(tx.getGroupLoanAcNo());
				tran.setTxAcType(EnumConst.TxAcType_Group_Loan);
			}
		}
		
		tran.setGroupAcNo(tx.getGroupAcNo());
		tran.setDoneByMemberAcNo(tx.getDoneByMemberAcNo());
		
		if(tx.getApprovedByMemberAcNo() > 0) {
			tran.setApprovedByMemberAcNo(tx.getApprovedByMemberAcNo());
			tran.setApprovedTs(DateUtil.convertDateToTime(tx.getApprovedTs()));
		}
		
		tran.setAmount(tx.getAmount());
		tran.setPaymentMode(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId()));
		
		if(tx.getChequeNo() != null) {
			tran.setChequeNo(tx.getChequeNo());
		} else {
			tran.setChequeNo(DataUtil.EMPTY_STRING);
		}
		if(tx.getReasonToUndoId() > 0) {
			tran.setReasonToUndo(EnumCache.getNameOfEnumValue(EnumConst.ReasonToUndo, tx.getReasonToUndoId()));
		} else {
			tran.setReasonToUndo(DataUtil.EMPTY_STRING);
		}
		if(tx.getGroupBankAccount() > 0) {
			tran.setGroupBankAcDisplay(daoFactory.getGBankAccountDAO().getGroupBankAcDisplayForAcNo(tx.getGroupBankAccount()));
		}
		
		tran.setStatus(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId()));
		tran.setDescription(tx.getDescription());
		tran.setLocation(Transaction.getLocation(tx.getEntryLocation()));
		tran.setAttachments(Transaction.getAttachments(tx.getEntryLocation()));
		tran.setPaymentTs(DateUtil.convertDateToTime(tx.getPaymentTs()));
		tran.setEntryTs(DateUtil.convertDateToTime(tx.getEntryTs()));
		
		return tran;
	}	
	
    public List<Attachment> transactionInfoFile(long groupAcNo, long txId, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(groupAcNo, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.TX, txId, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateTransactionInfoFile(long groupAcNo, long docId, long txId, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	Tx tx = daoFactory.getTxDAO().findById(txId);
    	if(!tx.getEntryLocation().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			!tx.getEntryLocation().contains(DBConst.ATTACH_ADDED_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER)) {
    		throw new BadRequestException("Invalid Doc ID for the Transaction!");
    	}
    	
    	String attachmentInfo = attachmentBO.updateFile(groupAcNo, docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.TX, txId, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public void deleteTransactionInfoFile(long groupAcNo, long docId, long txId) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	Tx tx = daoFactory.getTxDAO().findById(txId);
    	if(!tx.getEntryLocation().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			!tx.getEntryLocation().contains(DBConst.ATTACH_ADDED_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER)) {
    		throw new BadRequestException("Invalid Doc ID for the Transaction!");
    	}
    	
    	attachmentBO.deleteFile(groupAcNo, docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.TX, txId, dataFeilds));
    }
}
