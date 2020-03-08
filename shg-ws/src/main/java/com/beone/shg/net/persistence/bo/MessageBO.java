package com.beone.shg.net.persistence.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.auth.WsAccessCodeUtil;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GM;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MMessage;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.MobileM;
import com.beone.shg.net.persistence.mpo.util.SMSSendingUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransaction;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransactions;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.rest.MMessageREST;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("messageBO")
public class MessageBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("wsAccessCodeUtil")
	protected WsAccessCodeUtil wsAccessCodeUtil;

	public MMessageREST getMMessage(long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MMessage mMessage = daoFactory.getMMessageDAO().findById(memberAcNo);		
		if(mMessage == null) {
			throw new BadRequestException("Member Message Account Not Yet Created!");
		}

		MMessageREST rest = new MMessageREST();
		MMessageREST.loadMMessageREST(mMessage, rest);

		return rest;
	}

	public MMessageREST addUpdateMMessage(MMessageREST rest) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(rest.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MMessage mMessage = daoFactory.getMMessageDAO().findById(rest.getMemberAcNo());
		MobileM mobileM = null;
		boolean newMMessage = false;
		boolean newMobileM = false;

		if(ConversionUtil.isValidMobileNo(rest.getMobileNo())) {
			mobileM = daoFactory.getMobileMDAO().findById(rest.getMobileNo());
		}
		if(mobileM != null && mobileM.getMemberAcNo() != rest.getMemberAcNo()) {
			throw new BadRequestException("Mobile No Already Registered with: " +
					ConversionUtil.getDisplayMemberAcNo(mobileM.getMemberAcNo()) + " - " + 
					daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, mobileM.getMemberAcNo()));
		}

		if(mMessage == null) {
			mMessage = new MMessage();

			MProfile mProfile = daoFactory.getMProfileDAO().findById(rest.getMemberAcNo());
			GProfile gProfile = daoFactory.getGProfileDAO().findById(ConversionUtil.getGroupAcFromMember(rest.getMemberAcNo()));

			mMessage.setMemberAcNoTran(rest.getMemberAcNo());
			mMessage.setMroleId(mProfile.getMroleId());
			mMessage.setGroupTypeId(gProfile.getGroupTypeId());
			newMMessage = true;
		}
		
		if(rest.getMobileNo() > DataUtil.ZERO_LONG) {
			mMessage.setMobileNo(rest.getMobileNo());
//			mMessage.setMobileVerified(1);

			if(mobileM == null) {
				mobileM = new MobileM();
				mobileM.setMobileNoTran(rest.getMobileNo());
				mobileM.setMemberAcNo(rest.getMemberAcNo());
				mobileM.setMroleId(mMessage.getMroleId());
				mobileM.setGroupTypeId(mMessage.getGroupTypeId());
				newMobileM = true;
			}
		}

		if(rest.getEmail() != null && !rest.getEmail().isEmpty()) {
			mMessage.setEmail(rest.getEmail());
		}

		if(newMMessage) {
			daoFactory.getMMessageDAO().persist(mMessage);
		} else {
			daoFactory.getMMessageDAO().merge(mMessage);
		}

		if(newMobileM) {
			daoFactory.getMobileMDAO().persist(mobileM);
		} else {
			daoFactory.getMobileMDAO().merge(mobileM);
		}

		return rest;
	}

	public void removeMMessage(MMessageREST rest) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(rest.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MMessage mMessage = daoFactory.getMMessageDAO().findById(rest.getMemberAcNo());
		MobileM mobileM = null;

		if(mMessage != null) {			

			if(mMessage.getMobileNo() > 0) {
				mobileM = daoFactory.getMobileMDAO().findById(mMessage.getMobileNo());

				if(mobileM != null) {
					daoFactory.getMobileMDAO().remove(mobileM);
				}
			}
			
			mMessage.setMobileNo(0);
			mMessage.setMobileVerified(0);
			
			daoFactory.getMMessageDAO().merge(mMessage);
		}
	}

	public MMessageREST generateMobileVeriCode(MMessageREST rest) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(rest.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Ac No");
		}
		MMessage mMessage = daoFactory.getMMessageDAO().findById(rest.getMemberAcNo());
		if(mMessage == null) {
			addUpdateMMessage(rest);
		}

		if(rest.getMobileNo() > DataUtil.ZERO_LONG) {
			mMessage.setMobileNo(rest.getMobileNo());
		}
		if(rest.getMobileNo() > 0) {
			throw new BadRequestException("Invalid Mobile No");
		}


		daoFactory.getMMessageDAO().merge(mMessage);

		return rest;
	}

	public void processSMSMessage(long mobileNo, String message) throws BadRequestException, AccessDeniedException {

		if(!ConversionUtil.isValidMobileNo(mobileNo)) {
			throw new BadRequestException("Invalid Mobile No!");
		}
		MobileM mobileM = daoFactory.getMobileMDAO().findById(mobileNo);
		if(mobileM == null) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Mobile Number Not Registered! Please contact SHG-One Representative!");
			throw new BadRequestException("Mobile Number Not Registered!");
		}

		MMessage mMessage = daoFactory.getMMessageDAO().findById(mobileM.getMemberAcNo());
		if(mMessage == null) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Mobile Number Not Registered! Please contact SHG-One Representative!");
			throw new BadRequestException("Mobile Number Not Registered!");
		}
		if(mMessage.getMobileVerified() != 1) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Mobile Number Not Verified! Please contact SHG-One Representative!");
			throw new BadRequestException("Mobile Number Not Verified!");
		}
		
		verifyGroupProfile(mobileNo, ConversionUtil.getGroupAcFromMember(mMessage.getMemberAcNo()));
		
		verifyMemberProfile(mobileNo, mobileM.getMemberAcNo());
		
		processSMSMessage(mobileNo, mMessage, message);

	}

	public final static String SHG1 = "SHG1";
	public final static String TX = "TX";
	public final static String SI = "SI";
	public final static String LI = "LI";
	public final static String BD = "BD";
	public final static String BW = "BW";
	public final static String ALL = "ALL";
	public final static String LC = "LC";
	public final static String LO = "LO";
	public final static String CA = "CA";
	public final static String CQ = "CQ";
	
	protected void verifyGroupProfile(long mobileNo, long groupAcNo) throws BadRequestException {
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Invalid Group Account! Please contact SHG-One Representative!");
			throw new BadRequestException("Invalid Member Account No!");
		}
		String activeStatus = EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId());
		if(activeStatus.equals(EnumConst.ActiveStatus_Locked)) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Group Account is Locked! Please contact SHG-One Representative!");
			throw new BadRequestException("Group Account is Locked!");
		}
		if(!EnumUtil.isActiveToCompute(activeStatus)) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Group Account No Not Active! Please contact SHG-One Representative!");
			throw new BadRequestException("Group Account No Not Active!");
		}
	}
	
	protected void verifyMemberProfile(long mobileNo, long memberAcNo) throws BadRequestException {
		
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
		if(mProfile == null) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Invalid Member Account! Please contact SHG-One Representative!");
			throw new BadRequestException("Invalid Member Account No!");
		}
		String activeStatus = EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId());
		if(activeStatus.equals(EnumConst.ActiveStatus_Locked)) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Account is Locked! Please contact SHG-One Representative!");
			throw new BadRequestException("Member Account is Locked!");
		}
		if(!EnumUtil.isActiveToCompute(activeStatus)) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Your Account No Not Active! Please contact SHG-One Representative!");
			throw new BadRequestException("Member Account No Not Active!");
		}
	}
	
	protected long buildMemberAcNo(long mobileNo, long groupAcNo, int memberNo) throws BadRequestException {
		
		if(memberNo <= 0) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Invalid Member Account No! Please contact SHG-One Representative!");
			throw new BadRequestException("Invalid Member Account No!");
		}
		long memberAcNo = ConversionUtil.getMemberAcNo(groupAcNo, memberNo);
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
		if(mProfile == null) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Invalid Member Account No! Please contact SHG-One Representative!");
			throw new BadRequestException("Invalid Member Account No!");
		}
		if(!EnumUtil.isActiveToCompute(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()))) {
			SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Member Account No Not Active! Please contact SHG-One Representative!");
			throw new BadRequestException("Member Account No Not Active!");
		}
		return memberAcNo;
	}
	
	protected String getPaymentMode(String token) {
		if(token == null) {
			return EnumConst.PaymentMode_CASH;
		}
		switch(token) {
		case CA:
			return EnumConst.PaymentMode_CASH;

		case CQ:
			return EnumConst.PaymentMode_CHEQUE;

		}
		return EnumConst.PaymentMode_CASH;
	}
	
	protected void processSMSMessage(long mobileNo, MMessage mMessage, String message) throws BadRequestException, AccessDeniedException {

		boolean responsePending = true;
		try {
			message = message.toUpperCase();
			String regexp = "[\\s,;\\n\\t]+"; // these are my delimiters
			String [] tokens = message.split(regexp);

			if(tokens == null || tokens.length < 2) {
				throw new BadRequestException("Mobile Number Not Registered!");
			}

			long groupAcNo = ConversionUtil.getGroupAcFromMember(mMessage.getMemberAcNo());
			GM gm = daoFactory.getGMDAO().findById(groupAcNo, mMessage.getMemberAcNo());
			
			if(tokens[0].equals(SHG1)) {

				if(tokens[1].equals(TX)) {
					
					if(!wsAccessCodeUtil.isAccess(gm.getWsAccessRights(), "GROUP_TRANSACTION", "UPDATE")) {
						SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! You Don't have the Required Access!");
						responsePending = false;
						throw new AccessDeniedException("Don't have the Required Access!");
					}
					
					List<Transaction> txs = new ArrayList<Transaction>();

					switch(tokens[2]) {
					case SI:
					{

						// SHG1 TX SI All 100
						if(tokens[3].equals(ALL)) {
							TodoTransactions todos = transactionBO.getGroupTodoTxs(EnumConst.Lang_English, groupAcNo, EnumConst.TxType_Saving_Installment);

							BigDecimal amount = null;
							if(tokens.length > 4 && tokens[4] != null && !tokens[4].isEmpty()) {
								amount = new BigDecimal(Integer.parseInt(tokens[4]));
							}

							for(TodoTransaction todoTx: todos.getTodoTransactions()) {
								if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
									txs.addAll(TodoTransaction.buildSavingTx(todoTx, mMessage.getMemberAcNo(), amount, null, null, 0));
								}
							}
							if(txs.isEmpty()) {
								SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! No Outstanding Saving Installments for this Month!");
								responsePending = false;
								break;
							}

						} 
						// SHG1 TX SI 3 100 123 CA LC 20
						// SHG1 TX SI 3 100 123 CQ LO 20
						else {
							long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));
							long mSavingAcNo = 0l;
							long todoTxId = 0l;
 
							TodoTransactions todos = transactionBO.getMemberTodoTxs(EnumConst.Lang_English, 
									memberAcNo, 
									EnumConst.TxType_Saving_Installment);

							BigDecimal amount = new BigDecimal(Integer.parseInt(tokens[4]));
							String slipNo = "0";
							if(tokens.length > 5 && tokens[5] != null && !tokens[5].isEmpty()) {
								slipNo = tokens[5];
							}
							String paymentMode = EnumConst.PaymentMode_CASH;
							if(tokens.length > 6 && tokens[6] != null && !tokens[6].isEmpty()) {
								paymentMode = getPaymentMode(tokens[6]);
							}
							
							long gBankAccountNo = 0l;
							if(EnumUtil.isBankPaymentMode(paymentMode)) {
								List<GBankAccount> gBankAccounts = daoFactory.getGBankAccountDAO().getGroupBankAccounts(groupAcNo);
								for(GBankAccount ac: gBankAccounts) {
									if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, 
											ac.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account)) {
										gBankAccountNo = ac.getBankAccountNo();
									}
								}
							}

							BigDecimal todoAmount = null;
							int noOfTodo = 0;

							if(todos != null && !todos.getTodoTransactions().isEmpty()) {
								for(TodoTransaction todoTx: todos.getTodoTransactions()) {		    			
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										todoAmount = BDUtil.add(todoAmount, todoTx.getAmount());
										noOfTodo++;
										mSavingAcNo = todoTx.getInstAcNo();
										todoTxId = todoTx.getTodoTxId();
									}
								}
							}

							if(noOfTodo > 1) {
								BigDecimal remAmount = amount;
								int remNoOfTodo = noOfTodo;

								for(TodoTransaction todoTx: todos.getTodoTransactions()) {
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										if(remNoOfTodo == 1) {
											txs.addAll(TodoTransaction.buildSavingTx(todoTx, mMessage.getMemberAcNo(), remAmount, slipNo, paymentMode, gBankAccountNo));
										} else if(remAmount.compareTo(todoTx.getAmount()) >= DataUtil.ZERO_INTEGER) {
											txs.addAll(TodoTransaction.buildSavingTx(todoTx, mMessage.getMemberAcNo(), null, slipNo, paymentMode, gBankAccountNo));
										} else {
											txs.addAll(TodoTransaction.buildSavingTx(todoTx, mMessage.getMemberAcNo(), remAmount, slipNo, paymentMode, gBankAccountNo));
										}

										remAmount = BDUtil.sub(remAmount, todoTx.getAmount());
										remNoOfTodo--;
									}
								}
							} else if(noOfTodo == 1) {
								for(TodoTransaction todoTx: todos.getTodoTransactions()) {	    			
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										txs.addAll(TodoTransaction.buildSavingTx(todoTx, mMessage.getMemberAcNo(), amount, slipNo, paymentMode, gBankAccountNo));
									}
								}
							} else {

								List<MSavingAc> mSavingAcs = daoFactory.getMSavingAcDAO().getAllActiveAcForMember(memberAcNo);
								if(mSavingAcs != null) {
									for(MSavingAc ac: mSavingAcs) {
										mSavingAcNo = ac.getMSavingAcNo();
									}
								}
								if(mSavingAcNo == 0l) {
									SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Can't find Member Active Saving Account! Please contact SHG-One Representative!");
									responsePending = false;
									break;
								}

								Transaction tx = new Transaction();

								tx.setAmount(amount);
								tx.setDescription(EnumConst.TxType_Saving_Installment + " done by SMS!");
								tx.setGroupAcNo(groupAcNo);
								tx.setMemberAcNo(memberAcNo);
								tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
								tx.setSavingAcNo(mSavingAcNo);
								tx.setPaymentMode(paymentMode);
								tx.setGroupBankAcNo(gBankAccountNo);
								tx.setSlipType(EnumConst.SlipType_RECEIPT);
								tx.setSlipNo(slipNo);
								tx.setTxAcType(EnumConst.TxAcType_Member_Saving);
								tx.setTxType(EnumConst.TxType_Saving_Installment);
								txs.add(tx);
							}
							
							String lateFeeCode = DataUtil.EMPTY_STRING;
							if(tokens.length > 7 && tokens[7] != null && !tokens[7].isEmpty()) {
								lateFeeCode = tokens[7];
							}
							
							if(lateFeeCode != DataUtil.EMPTY_STRING) {
								BigDecimal lateFee = null;
								if(tokens.length > 8 && tokens[8] != null && !tokens[8].isEmpty()) {
									lateFee = new BigDecimal(Integer.parseInt(tokens[8]));
								}
								
								if(lateFee == null) {
									SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Late Fee Account Invalid! Please try again with Correction!");
									responsePending = false;
									break;
								}
								
								Transaction tx = new Transaction();
								tx.setAmount(lateFee);
								tx.setGroupAcNo(groupAcNo);
								tx.setMemberAcNo(memberAcNo);
								tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
								tx.setRelatedTxTodoId(todoTxId);
								tx.setSavingAcNo(mSavingAcNo);
								tx.setSlipType(EnumConst.SlipType_RECEIPT);
								tx.setSlipNo(slipNo);
								
								switch(lateFeeCode) {
								case LC:
									tx.setPaymentMode(paymentMode);
									tx.setDescription(EnumConst.TxType_Late_Fee + " done by SMS!");
									tx.setTxType(EnumConst.TxType_Late_Fee);
									tx.setGroupBankAcNo(gBankAccountNo);
									
									break;

								case LO:
									tx.setPaymentMode(EnumConst.PaymentMode_OUTSTANDING);
									tx.setDescription(EnumConst.TxType_Outstanding_Late_Fee + " done by SMS!");
									tx.setTxType(EnumConst.TxType_Outstanding_Late_Fee);

									break;
								}
								
								txs.add(tx);
							}
						}						
						
						break;
					}
					case LI:
					{
						// SHG1 TX LI All
						if(tokens[3].equals(ALL)) {
							TodoTransactions todos = transactionBO.getGroupTodoTxs(EnumConst.Lang_English, groupAcNo, EnumConst.TxType_Loan_Installment);

							for(TodoTransaction todoTx: todos.getTodoTransactions()) {		    			
								if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
									txs.addAll(TodoTransaction.buildLoanTx(todoTx, mMessage.getMemberAcNo(), null, null, null, null, 0));
								}
							}
							if(txs.isEmpty()) {
								SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! No Outstanding Loan Installments for this Month!");
								responsePending = false;
								break;
							}
						} 
						// SHG1 TX LI 3 1000 200 123 CA LC 20
						// SHG1 TX LI 3 1000 200 123 CQ LO 20
						else {
							long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));
							long mloanAcNo = 0l;
							long todoTxId = 0l;

							TodoTransactions todos = transactionBO.getMemberTodoTxs(EnumConst.Lang_English, 
									memberAcNo, 
									EnumConst.TxType_Loan_Installment);

							BigDecimal principle = new BigDecimal(Integer.parseInt(tokens[4]));
							BigDecimal interest = new BigDecimal(Integer.parseInt(tokens[5]));
							String slipNo = "0";
							if(tokens.length > 6 && tokens[6] != null && !tokens[6].isEmpty()) {
								slipNo = tokens[6];
							}
							String paymentMode = EnumConst.PaymentMode_CASH;
							if(tokens.length > 7 && tokens[7] != null && !tokens[7].isEmpty()) {
								paymentMode = getPaymentMode(tokens[7]);
							}
							
							long gBankAccountNo = 0l;
							if(EnumUtil.isBankPaymentMode(paymentMode)) {
								List<GBankAccount> gBankAccounts = daoFactory.getGBankAccountDAO().getGroupBankAccounts(groupAcNo);
								for(GBankAccount ac: gBankAccounts) {
									if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, 
											ac.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account)) {
										gBankAccountNo = ac.getBankAccountNo();
									}
								}
							}

							BigDecimal todoPrinciple = null;
							BigDecimal todoInterest = null;
							int noOfTodo = 0;

							if(todos != null && !todos.getTodoTransactions().isEmpty()) {
								for(TodoTransaction todoTx: todos.getTodoTransactions()) {		    			
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										todoPrinciple = BDUtil.add(todoPrinciple, BDUtil.sub(todoTx.getAmount(), todoTx.getInterestComponent()));
										todoInterest = BDUtil.add(todoInterest, todoTx.getInterestComponent());
										noOfTodo++;
										mloanAcNo = todoTx.getInstAcNo();
										todoTxId = todoTx.getTodoTxId();
									}
								}
							}

							if(noOfTodo > 1) {
								BigDecimal remPrinciple = principle;
								BigDecimal remInterest = interest;
								int remNoOfTodo = noOfTodo;

								for(TodoTransaction todoTx: todos.getTodoTransactions()) {
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										if(remNoOfTodo == 1) {
											txs.addAll(TodoTransaction.buildLoanTx(todoTx, mMessage.getMemberAcNo(), remPrinciple, remInterest, slipNo, paymentMode, gBankAccountNo));

											remPrinciple = DataUtil.ZERO_BIG_DECIMAL;
											remInterest = DataUtil.ZERO_BIG_DECIMAL;
										} else if(remPrinciple.compareTo(BDUtil.sub(todoTx.getAmount(), todoTx.getInterestComponent())) >= DataUtil.ZERO_INTEGER ||
												remInterest.compareTo(todoTx.getInterestComponent()) >= DataUtil.ZERO_INTEGER) {
											BigDecimal iPrinciple = DataUtil.ZERO_BIG_DECIMAL;
											BigDecimal iInterest = DataUtil.ZERO_BIG_DECIMAL;

											if(remPrinciple.compareTo(BDUtil.sub(todoTx.getAmount(), todoTx.getInterestComponent())) >= DataUtil.ZERO_INTEGER) {
												iPrinciple = BDUtil.sub(todoTx.getAmount(), todoTx.getInterestComponent());
											} else {
												iPrinciple = remPrinciple;
											}
											if(remInterest.compareTo(todoTx.getInterestComponent()) >= DataUtil.ZERO_INTEGER) {
												iInterest = todoTx.getInterestComponent();
											} else {
												iInterest = remInterest;
											}
											txs.addAll(TodoTransaction.buildLoanTx(todoTx, mMessage.getMemberAcNo(), iPrinciple, iInterest, slipNo, paymentMode, gBankAccountNo));

											remPrinciple = BDUtil.sub(remPrinciple, remPrinciple);
											remInterest = BDUtil.sub(remInterest, iInterest);
										} else {
											txs.addAll(TodoTransaction.buildLoanTx(todoTx, mMessage.getMemberAcNo(), remPrinciple, remInterest, slipNo, paymentMode, gBankAccountNo));

											remPrinciple = DataUtil.ZERO_BIG_DECIMAL;
											remInterest = DataUtil.ZERO_BIG_DECIMAL;
										}

										remNoOfTodo--;
									}
								}
							} else if(noOfTodo == 1) {
								for(TodoTransaction todoTx: todos.getTodoTransactions()) {	    			
									if(EnumUtil.isTodoTxStatusTodo(todoTx.getTxTodoStatus())) {
										txs.addAll(TodoTransaction.buildLoanTx(todoTx, mMessage.getMemberAcNo(), principle, interest, slipNo, paymentMode, gBankAccountNo));
									}
								}
							} else {
								List<MLoanAc> mLoanAcs = daoFactory.getMLoanAcDAO().getAllActiveAcForMember(memberAcNo);
								if(mLoanAcs != null) {
									for(MLoanAc ac: mLoanAcs) {
										mloanAcNo = ac.getMLoanAcNo();
									}
								}
								if(mloanAcNo == 0l) {
									SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Can't find Member Active Loan Account! Please contact SHG-One Representative!");
									responsePending = false;
									break;
								}
								{
									Transaction tx = new Transaction();				
									tx.setAmount(principle);
									tx.setDescription(EnumConst.TxType_Loan_Installment + " done by SMS!");
									tx.setGroupAcNo(groupAcNo);
									tx.setMemberAcNo(memberAcNo);
									tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
									tx.setMemberLoanAcNo(mloanAcNo);
									tx.setPaymentMode(paymentMode);
									tx.setGroupBankAcNo(gBankAccountNo);
									tx.setSlipType(EnumConst.SlipType_RECEIPT);
									tx.setSlipNo(slipNo);
									tx.setTxAcType(EnumConst.TxAcType_Member_Loan);
									tx.setTxType(EnumConst.TxType_Loan_Installment);
									txs.add(tx);
								}
								{
									Transaction tx = new Transaction();				
									tx.setAmount(interest);
									tx.setDescription(EnumConst.TxType_Loan_Interest_Installment + " done by SMS!");
									tx.setGroupAcNo(groupAcNo);
									tx.setMemberAcNo(memberAcNo);
									tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
									tx.setMemberLoanAcNo(mloanAcNo);
									tx.setPaymentMode(paymentMode);
									tx.setGroupBankAcNo(gBankAccountNo);
									tx.setSlipType(EnumConst.SlipType_RECEIPT);
									tx.setSlipNo(slipNo);
									tx.setTxAcType(EnumConst.TxAcType_Member_Loan);
									tx.setTxType(EnumConst.TxType_Loan_Interest_Installment);
									txs.add(tx);
								}
							}
							
							String lateFeeCode = DataUtil.EMPTY_STRING;
							if(tokens.length > 8 && tokens[8] != null && !tokens[8].isEmpty()) {
								lateFeeCode = tokens[8];
							}
							
							if(lateFeeCode != DataUtil.EMPTY_STRING) {
								BigDecimal lateFee = null;
								if(tokens.length > 9 && tokens[9] != null && !tokens[9].isEmpty()) {
									lateFee = new BigDecimal(Integer.parseInt(tokens[9]));
								}
								
								if(lateFee == null) {
									SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Late Fee Account Invalid! Please try again with Correction!");
									responsePending = false;
									break;
								}
								
								Transaction tx = new Transaction();
								tx.setAmount(lateFee);
								tx.setGroupAcNo(groupAcNo);
								tx.setMemberAcNo(memberAcNo);
								tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
								tx.setRelatedTxTodoId(todoTxId);
								tx.setSavingAcNo(mloanAcNo);
								tx.setSlipType(EnumConst.SlipType_RECEIPT);
								tx.setSlipNo(slipNo);
								
								switch(lateFeeCode) {
								case LC:
									tx.setPaymentMode(paymentMode);
									tx.setDescription(EnumConst.TxType_Late_Fee + " done by SMS!");
									tx.setTxType(EnumConst.TxType_Late_Fee);
									tx.setGroupBankAcNo(gBankAccountNo);
									
									break;

								case LO:
									tx.setPaymentMode(EnumConst.PaymentMode_OUTSTANDING);
									tx.setDescription(EnumConst.TxType_Outstanding_Late_Fee + " done by SMS!");
									tx.setTxType(EnumConst.TxType_Outstanding_Late_Fee);

									break;
								}
								
								txs.add(tx);
							}
						}
						break;
					}
					// SHG1 TX LC 3 20 123
					case LC:
					{
						long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));

						BigDecimal amount = new BigDecimal(Integer.parseInt(tokens[4]));

						String slipNo = "0";
						if(tokens.length > 5 && tokens[5] != null && !tokens[5].isEmpty()) {
							slipNo = tokens[5];
						}

						Transaction tx = new Transaction();
						tx.setAmount(amount);
						tx.setGroupAcNo(groupAcNo);
						tx.setMemberAcNo(memberAcNo);
						tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
						tx.setSlipType(EnumConst.SlipType_RECEIPT);
						tx.setSlipNo(slipNo);
						tx.setPaymentMode(EnumConst.PaymentMode_CASH);
						tx.setDescription(EnumConst.TxType_Late_Fee + " done by SMS!");
						tx.setTxType(EnumConst.TxType_Late_Fee);

						txs.add(tx);
						break;
					}
					// SHG1 TX LO 3 20 123
					case LO:
					{
						long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));

						BigDecimal amount = new BigDecimal(Integer.parseInt(tokens[4]));

						String slipNo = "0";
						if(tokens.length > 5 && tokens[5] != null && !tokens[5].isEmpty()) {
							slipNo = tokens[5];
						}

						Transaction tx = new Transaction();
						tx.setAmount(amount);
						tx.setGroupAcNo(groupAcNo);
						tx.setMemberAcNo(memberAcNo);
						tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
						tx.setSlipType(EnumConst.SlipType_RECEIPT);
						tx.setSlipNo(slipNo);
						tx.setPaymentMode(EnumConst.PaymentMode_OUTSTANDING);
						tx.setDescription(EnumConst.TxType_Outstanding_Late_Fee + " done by SMS!");
						tx.setTxType(EnumConst.TxType_Outstanding_Late_Fee);

						txs.add(tx);
						break;
					}
					case BD:
					{
						long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));

						BigDecimal amount = new BigDecimal(Integer.parseInt(tokens[4]));
						
						List<GBankAccount> gBankAccounts = daoFactory.getGBankAccountDAO().getGroupBankAccounts(groupAcNo);
						long gBankAccountNo = 0l;
						for(GBankAccount ac: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, 
									ac.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account)) {
								gBankAccountNo = ac.getBankAccountNo();
							}
						}

						if(gBankAccountNo == DataUtil.ZERO_LONG) {
							SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Can't find Group Saving Bank Account! Please contact SHG-One Representative!");
							responsePending = false;
							break;
						}

						Transaction tx = new Transaction();				
						tx.setAmount(amount);
						tx.setDescription(EnumConst.TxType_Bank_Transfer + " done by SMS!");
						tx.setGroupAcNo(groupAcNo);
						tx.setMemberAcNo(memberAcNo);
						tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
						tx.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
						tx.setGroupBankAcNo(gBankAccountNo);
						tx.setChequeNo("0");
						tx.setSlipType(EnumConst.SlipType_TRANSFER);
						tx.setSlipNo("0");
						tx.setTxType(EnumConst.TxType_Bank_Transfer);
						txs.add(tx);

						break;
					}
					case BW:
					{
						long memberAcNo = buildMemberAcNo(mobileNo, groupAcNo, Integer.parseInt(tokens[3]));

						BigDecimal amount = new BigDecimal(Integer.parseInt(tokens[4]));
						
						List<GBankAccount> gBankAccounts = daoFactory.getGBankAccountDAO().getGroupBankAccounts(groupAcNo);
						long gBankAccountNo = DataUtil.ZERO_LONG;
						for(GBankAccount ac: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, 
									ac.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account)) {
								gBankAccountNo = ac.getBankAccountNo();
							}
						}

						if(gBankAccountNo == DataUtil.ZERO_LONG) {
							SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Can't find Group Saving Bank Account! Please contact SHG-One Representative!");
							responsePending = false;
							break;
						}
								
						Transaction tx = new Transaction();				
						tx.setAmount(amount);
						tx.setDescription(EnumConst.TxType_Bank_Withdrawal + " done by SMS!");
						tx.setGroupAcNo(groupAcNo);
						tx.setMemberAcNo(memberAcNo);
						tx.setDoneByMemberAcNo(mMessage.getMemberAcNo());
						tx.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
						tx.setGroupBankAcNo(gBankAccountNo);
						tx.setChequeNo("0");
						tx.setSlipType(EnumConst.SlipType_TRANSFER);
						tx.setSlipNo("0");
						tx.setTxType(EnumConst.TxType_Bank_Withdrawal);
						txs.add(tx);

						break;
					}
					}

					if(txs.isEmpty()) {
						throw new BadRequestException("No Valid Transaction SMS!");
					}

					transactionBO.addTransactions(txs);

					SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Successfully Processed your Request!");
					responsePending = false;
				}
			}
		} catch (AccessDeniedException e) {			
			throw e;
		} catch (BadRequestException e) {			
			throw e;
		} catch (Exception e) {
			if(responsePending) {
				SMSSendingUtil.sendTxSMS(DataUtil.toString(mobileNo), "Not Able to Process Your Request! Invalid SMS Request Format! Please try with correction or contact SHG-One Representative!");
			}		
			throw e;
		}
	}
}
