package com.beone.shg.net.persistence.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.util.AcRecordRaw;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GAcByTxtype;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MonthlyGAc;
import com.beone.shg.net.persistence.model.MonthlyGAcByTxtype;
import com.beone.shg.net.persistence.model.MonthlyGBankAccount;
import com.beone.shg.net.persistence.model.MonthlyGInvtAc;
import com.beone.shg.net.persistence.model.MonthlyGLoanAc;
import com.beone.shg.net.persistence.model.MonthlyMAc;
import com.beone.shg.net.persistence.ppo.util.PPOFormula;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.MonthlyReportFormula;
import com.beone.shg.net.persistence.support.MonthlyReportSheetFormula;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.AcBook;
import com.beone.shg.net.webservice.rest.model.resp.AcRecord;
import com.beone.shg.net.webservice.rest.model.resp.AccountBook;
import com.beone.shg.net.webservice.rest.model.resp.BankReport;
import com.beone.shg.net.webservice.rest.model.resp.MemberAcBook;
import com.beone.shg.net.webservice.rest.model.resp.MemberAcRecord;
import com.beone.shg.net.webservice.rest.model.resp.Record;
import com.beone.shg.net.webservice.rest.model.resp.ReportSheet;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("reportBO")
public class ReportBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;
	
	public AccountBook getAccountBook(String lang, 
			int groupAcNo, 
			String type,
			long bankAcNo,
			long startTime,
			String endMonth) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}
		if(type == null || type.isEmpty()) {
			throw new BadRequestException("Invalid Book Type");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Invalid Start time : " + startTime);
		}
		if(type == null || endMonth.isEmpty()) {
			throw new BadRequestException("Invalid End Month : " + endMonth);
		}
		long endTime = 0;
		try {
			endTime = DateUtil.parseMonthYearShort(endMonth).getTime();
		} catch(Exception e) {
			throw new BadRequestException("Invalid End Month : " + endMonth);
		}	
		if(startTime >= endTime) {
			throw new BadRequestException("Invalid Start tiem Greater Or Equal to End time : " + startTime + " > " + endTime);
		}

		AccountBook book = new AccountBook();

		book.setType(type);
		book.setStartTime(startTime);
		book.setEndTime(endTime);
		book.setAcNo(bankAcNo);
		book.setDisplayNames(RestDisplayTitle.getAccountBookRDT());

		Transactions transactions = transactionBO.getGroupTxs(lang, groupAcNo, startTime, endTime, DataUtil.EMPTY_STRING, type, bankAcNo);

		if(transactions != null) {
			book.setTransactions(transactions.getTransactions());
			buildAccountBook(lang, groupAcNo, bankAcNo, book);
		}

		return book;
	}

	protected void buildAccountBook(String lang, int groupAcNo, long bankAcNo, AccountBook book) {

		if(book.getType().equals(EnumConst.CashBook) || book.getType().equals(EnumConst.JointBook) || bankAcNo == 0) {
			GAc gAc = daoFactory.getGAcDAO().findById((long)groupAcNo);

			if(book.getType().equals(EnumConst.CashBook)) {
				book.setClosingClearBalance(gAc.getClearCashInHandAm());
				book.setClosingBalanceSubjectedToClearing(gAc.getSubjClearingCashInHandAm());
			}
			else if(book.getType().equals(EnumConst.BankBook)) {
				book.setClosingClearBalance(gAc.getClearBankBalanceAm());
				book.setClosingBalanceSubjectedToClearing(gAc.getSubjClearingBankBalanceAm());
			}
			else if(book.getType().equals(EnumConst.JointBook)) {
				book.setClosingClearBalance(BDUtil.add(gAc.getClearCashInHandAm(), gAc.getClearBankBalanceAm()));
				book.setClosingBalanceSubjectedToClearing(BDUtil.add(gAc.getSubjClearingCashInHandAm(), gAc.getSubjClearingBankBalanceAm()));
			}

		} else if(book.getType().equals(EnumConst.BankBook)) {
			GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(bankAcNo);

			book.setClosingClearBalance(gBankAccount.getClearBalanceAm());
			book.setClosingBalanceSubjectedToClearing(gBankAccount.getSubjClearingBalanceAm());
		}

		int noOfTxs = 0;
		int noOfApprovedTxs = 0;
		BigDecimal totalReceivedAmount = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal totalPaidAmount = DataUtil.ZERO_BIG_DECIMAL;

		int noOfTx = book.getTransactions().size();
		BigDecimal balance = BDUtil.add(book.getClosingClearBalance(), book.getClosingBalanceSubjectedToClearing());
		List<Transaction> sortedList = new ArrayList<Transaction>(noOfTx);
		Transaction curTx = null;

		for(int txIndex = (noOfTx - 1); txIndex >= 0; txIndex--) {

			curTx = book.getTransactions().get(txIndex);
			curTx.setClearBalance(balance);

			if(EnumUtil.isTxStatusActive(curTx.getStatus()) && curTx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {

				noOfTxs++;
				if(EnumUtil.isTxStatusApproved(curTx.getStatus())) {
					noOfApprovedTxs++;
				}

				if(curTx.getTxType().equals(EnumConst.TxType_Bank_Withdrawal)) {
					if(book.getType().equals(EnumConst.CashBook)) {
						balance = BDUtil.sub(balance, curTx.getAmount());
						curTx.setSlipType(EnumConst.TxType_RECEIPT);
						totalReceivedAmount = BDUtil.add(totalReceivedAmount, curTx.getAmount());

					} else if(book.getType().equals(EnumConst.BankBook)) {
						balance = BDUtil.add(balance, curTx.getAmount());
						curTx.setSlipType(EnumConst.TxType_VOUCHER);
						totalPaidAmount = BDUtil.add(totalPaidAmount, curTx.getAmount());

					}
				}
				else if(curTx.getTxType().equals(EnumConst.TxType_Bank_Transfer)) {
					if(book.getType().equals(EnumConst.CashBook)) {
						balance = BDUtil.add(balance, curTx.getAmount());
						curTx.setSlipType(EnumConst.TxType_VOUCHER);
						totalPaidAmount = BDUtil.add(totalPaidAmount, curTx.getAmount());

					} else if(book.getType().equals(EnumConst.BankBook)) {
						balance = BDUtil.sub(balance, curTx.getAmount());
						curTx.setSlipType(EnumConst.TxType_RECEIPT);
						totalReceivedAmount = BDUtil.add(totalReceivedAmount, curTx.getAmount());

					}
				}
				else if(curTx.getSlipType().equals(EnumConst.TxType_RECEIPT)) {
					balance = BDUtil.sub(balance, curTx.getAmount());
					totalReceivedAmount = BDUtil.add(totalReceivedAmount, curTx.getAmount());

				} 
				else if(curTx.getSlipType().equals(EnumConst.TxType_VOUCHER)) {
					balance = BDUtil.add(balance, curTx.getAmount());
					totalPaidAmount = BDUtil.add(totalPaidAmount, curTx.getAmount());

				}

				sortedList.add(curTx);
			}
		}

		book.setNoOfTxs(noOfTxs);
		book.setNoOfApprovedTxs(noOfApprovedTxs);
		book.setTotalReceivedAmount(totalReceivedAmount);
		book.setTotalPaidAmount(totalPaidAmount);
		book.setTransactions(sortedList);
		book.setOpeningClearBalance(balance);
		book.setOpeningBalanceSubjectedToClearing(DataUtil.ZERO_BIG_DECIMAL);
	}

	public AcBook getAcBook(String lang, 
			int groupAcNo, 
			String type,
			long bankAcNo,
			long startTime,
			String endMonth) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}
		if(type == null || type.isEmpty()) {
			throw new BadRequestException("Invalid Book Type");
		}
		if(!ConversionUtil.isTimeValid(startTime)) {
			throw new BadRequestException("Invalid Start time : " + startTime);
		}
		if(endMonth == null || endMonth.isEmpty()) {
			throw new BadRequestException("Invalid End Month!");
		}
		long endTime = 0;
		try {
			endTime = DateUtil.parseMonthYearShort(endMonth).getTime();
		} catch(Exception e) {
			throw new BadRequestException("Invalid End Month : " + endMonth);
		}
		if(startTime >= endTime) {
			throw new BadRequestException("Invalid Start tiem Greater Or Equal to End time : " + startTime + " > " + endTime);
		}

		AcBook book = new AcBook();
		AcRecordRaw recordRaw = new AcRecordRaw();
		
		Transactions transactions = transactionBO.getGroupTxs(lang, groupAcNo, startTime, endTime, DataUtil.EMPTY_STRING, type, bankAcNo);

		if(transactions != null) {
			
			// ****** Load Transaction Data to Raw Format
			for(Transaction tx: transactions.getTransactions()) {
				if(EnumUtil.isTxStatusActive(tx.getStatus()) && tx.getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
					recordRaw.put(tx.getPaymentTs(), tx.getTxType(), tx);
				}
			}
			
			// ****** Load Balance
			BigDecimal balance = DataUtil.ZERO_BIG_DECIMAL;
			
			
			if(endMonth.equals(EnumConst.AsOnDate)) {				
				if(type.equals(EnumConst.CashBook) || type.equals(EnumConst.JointBook) || bankAcNo == 0) {
					
					GAc gAc = daoFactory.getGAcDAO().findById((long)groupAcNo);
					
					if(type.equals(EnumConst.CashBook)) {
						balance = BDUtil.add(gAc.getClearCashInHandAm(), gAc.getSubjClearingCashInHandAm());
					} else if(type.equals(EnumConst.BankBook)) {
						balance = BDUtil.add(gAc.getClearBankBalanceAm(), gAc.getSubjClearingBankBalanceAm());
					} else if(type.equals(EnumConst.JointBook)) {
						balance = BDUtil.add(BDUtil.add(gAc.getClearBankBalanceAm(), gAc.getSubjClearingBankBalanceAm()),
								BDUtil.add(gAc.getClearCashInHandAm(), gAc.getSubjClearingCashInHandAm()));
					}
					
				} else {
					GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(bankAcNo);
					balance = BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm());
				}
			}
			else {
				if(type.equals(EnumConst.CashBook) || type.equals(EnumConst.JointBook) || bankAcNo == 0) {
					
					MonthlyGAc gAc = daoFactory.getMonthlyGAcDAO().findById(groupAcNo, endMonth);
					
					if(type.equals(EnumConst.CashBook)) {
						balance = BDUtil.add(gAc.getClearCashInHandAm(), gAc.getSubjClearingCashInHandAm());
					} else if(type.equals(EnumConst.BankBook)) {
						balance = BDUtil.add(gAc.getClearBankBalanceAm(), gAc.getSubjClearingBankBalanceAm());
					} else if(type.equals(EnumConst.JointBook)) {
						balance = BDUtil.add(BDUtil.add(gAc.getClearBankBalanceAm(), gAc.getSubjClearingBankBalanceAm()),
								BDUtil.add(gAc.getClearCashInHandAm(), gAc.getSubjClearingCashInHandAm()));
					}
				} else {
					MonthlyGBankAccount gBankAccount = daoFactory.getMonthlyGBankAccountDAO().findById(bankAcNo, endMonth);
					balance = BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm());
				}
			}

			List<Long> days = new ArrayList<Long>();
			days.addAll(recordRaw.getBookRaw().keySet());
			Collections.sort(days);
			Collections.reverse(days);
			
			for(Long day: days) {
				
				String date = DateUtil.getDisplayDateStr(day);
				
				// Add Closing Balance
				AcRecord closingRecord = new AcRecord();
				closingRecord.setDate(date);
				closingRecord.setParticular("Closing Balance");
				closingRecord.setBalance(BDUtil.format(GenAlgoUtil.roundHalfUp(balance, 0)));
				book.put(day, closingRecord);

				for(String txType: recordRaw.getBookRaw().get(day).keySet()) {
					
					BigDecimal particularAm = DataUtil.ZERO_BIG_DECIMAL;
					TxTypeValue txTypeValue = EnumCache.getTxTypeValue(txType);
					
					AcRecord particularRecord = new AcRecord();
					particularRecord.setDate(date);
					particularRecord.setParticular(txType);
					
					for(Transaction tx: recordRaw.getBookRaw().get(day).get(txType)) {

						AcRecord record = new AcRecord();
						record.setSlipNo(tx.getSlipNo());
						record.setTxNo(Long.toString(tx.getTxId()));
						record.setParticular(tx.getMemberName());
						record.setAmount(BDUtil.format(GenAlgoUtil.roundHalfUp(tx.getAmount(), 0)));
						record.setTxStatus(tx.getStatus());
						record.setTransaction(tx);
						particularRecord.addRecord(record);

						particularAm = BDUtil.add(particularAm, tx.getAmount());
					}

					particularRecord.setBalance(BDUtil.format(GenAlgoUtil.roundHalfUp(balance, 0)));

					if(txType.equals(EnumConst.TxType_Bank_Withdrawal)) {
						if(type.equals(EnumConst.CashBook)) {
							balance = BDUtil.sub(balance, particularAm);
							particularRecord.setReceivedAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
						} else if(type.equals(EnumConst.BankBook)) {
							balance = BDUtil.add(balance, particularAm);
							particularRecord.setPaidAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
						}
					}
					else if(txType.equals(EnumConst.TxType_Bank_Transfer)) {
						if(type.equals(EnumConst.CashBook)) {
							balance = BDUtil.add(balance, particularAm);
							particularRecord.setPaidAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
						} else if(type.equals(EnumConst.BankBook)) {
							balance = BDUtil.sub(balance, particularAm);
							particularRecord.setReceivedAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
						}
					}
					else if(txTypeValue.getSlipType().equals(EnumConst.TxType_RECEIPT)) {
						balance = BDUtil.sub(balance, particularAm);
						particularRecord.setReceivedAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
					} 
					else if(txTypeValue.getSlipType().equals(EnumConst.TxType_VOUCHER)) {
						balance = BDUtil.add(balance, particularAm);
						particularRecord.setPaidAm(BDUtil.format(GenAlgoUtil.roundHalfUp(particularAm, 0)));
					}

					book.put(day, particularRecord);
					
				}
				
				// Add Opening Balance
				AcRecord openingRecord = new AcRecord();
				openingRecord.setDate(date);
				openingRecord.setParticular("Opening Balance");
				openingRecord.setBalance(BDUtil.format(GenAlgoUtil.roundHalfUp(balance, 0)));
				book.put(day, openingRecord);
			}
			
			book.reverse();
		}
		
		book.setDisplayNames(RestDisplayTitle.getAcBookRDT());
		
		
		return book;
	}

	public MemberAcBook getMemberAccount(String lang, 
			long memberAcNo, 
			String startMonth,
			String endMonth) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No!");
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}
		if(startMonth == null || startMonth.isEmpty()) {
			throw new BadRequestException("Invalid Start Month!");
		}
		if(endMonth == null || endMonth.isEmpty()) {
			throw new BadRequestException("Invalid End Month!");
		}
		
		MemberAcBook memberAcBook = new MemberAcBook();
		memberAcBook.setMemberAcNo(memberAcNo);
		memberAcBook.setDisplayNames(RestDisplayTitle.getMemberAcBookRDT());
		
		List<MonthlyMAc> monthlyMAcs = daoFactory.getMonthlyMAcDAO().getAllForMember(memberAcNo);
		
		for(MonthlyMAc ac: monthlyMAcs) {
			memberAcBook.addMonthlyAc(MemberAcRecord.buildAc(ac));
		}

		MAc mAc = daoFactory.getMAcDAO().findById(memberAcNo);
		memberAcBook.addMonthlyAc(MemberAcRecord.buildAc(mAc));
		
		return memberAcBook;
	}
	
	public BankReport getMonthlyReport(String lang, 
			int groupAcNo, 
			String type,
			String month,
			String sheetFormat) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Report Type");
		}
		if(type == null || type.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}
		if(type == null || month.isEmpty()) {
			throw new BadRequestException("Invalid Month : " + month);
		}

		MonthlyReportFormula reportType = EnumCache.getMonthlyReportFormula(type);
		if(reportType == null) {
			throw new BadRequestException("Invalid Report Type");
		}

		BankReport report = new BankReport();
		report.setReportType(reportType.getReportName());
		report.setBankReportName(reportType.getReportDisplayName());
		report.setLangauge(lang);
		report.setDate(month);

		GRules gRules = daoFactory.getGRulesDAO().findById((long)groupAcNo);
		MonthlyGAc gAccount = daoFactory.getMonthlyGAcDAO().findById((long)groupAcNo, month); 
		if(gAccount == null) {
			throw new BadRequestException("Selected Month Data is not Avaliable!");
		}

		// SET Default format
		if(sheetFormat == null || sheetFormat.isEmpty()) {
			sheetFormat = EnumConst.ReportSheetFormat_DETAILED;
		}

		if(gAccount != null) {
			for(MonthlyReportSheetFormula sheetType:reportType.getMonthlyReportSheets()) {
				if(sheetType.getSheetFormat().equals(sheetFormat)) {

					ReportSheet sheet = new ReportSheet();
					sheet.setTopTitle(sheetType.getReportSheetTopTitle());
					sheet.setBottomTitle(sheetType.getReportSheetBottomTitle());

					report.addSheet(processMonthlyReportSheet(sheet, groupAcNo, month, gAccount, 
							sheetType.getReportSheetFormula(), 
							(gRules != null && gRules.getAllowAssociateMembers() > 0),
							(sheetFormat.equals(EnumConst.ReportSheetFormat_DETAILED))));
				}
			}
		}

		return report;
	}

	public BankReport getAsOnDateReport(String lang, 
			int groupAcNo, 
			String type,
			String sheetFormat) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		if(lang == null || lang.isEmpty()) {
			throw new BadRequestException("Invalid Report Type");
		}
		if(type == null || type.isEmpty()) {
			throw new BadRequestException("Invalid Langauge");
		}

		MonthlyReportFormula reportType = EnumCache.getMonthlyReportFormula(type);
		if(reportType == null) {
			throw new BadRequestException("Invalid Report Type");
		}

		BankReport report = new BankReport();
		report.setReportType(reportType.getReportName());
		report.setBankReportName(reportType.getReportDisplayName());
		report.setLangauge(lang);
		if(reportType.getReportName().equals(EnumConst.Report_Balance_Sheet)) {
			report.setDate(EnumConst.AsOnDate + " - " + DateUtil.getCurrentDisplay2DateStr());
		} else {
			report.setDate(DateUtil.getCurrentDisplay2DateStr());
		}
				
		GAc gAccount = daoFactory.getGAcDAO().findById((long)groupAcNo); 
		
		if(type.equals(EnumConst.Report_Profi_Loss_Statement) ||
				type.equals(EnumConst.Report_Balance_Sheet)) {
			
			BigDecimal netProfit = computeNetProfit(groupAcNo);
			gAccount.setNetProfitAm(netProfit);
			
			daoFactory.getGAcDAO().merge(gAccount);
			
		} else if(type.equals(EnumConst.Report_Profi_Loss_Statement_Projected) ||
				type.equals(EnumConst.Report_Balance_Sheet_Projected)) {
			
			BigDecimal netProfitProjected = computeNetProfitProjected(groupAcNo);
			gAccount.setNetProfitProjAm(netProfitProjected);
			
			daoFactory.getGAcDAO().merge(gAccount);
		}
		
		if(gAccount == null) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		GRules gRules = daoFactory.getGRulesDAO().findById((long)groupAcNo); 		

		// SET Default format
		if(sheetFormat == null || sheetFormat.isEmpty()) {
			sheetFormat = EnumConst.ReportSheetFormat_BANK;
		}

		if(gAccount != null) {
			for(MonthlyReportSheetFormula sheetType:reportType.getMonthlyReportSheets()) {
				if(sheetType.getSheetFormat().equals(sheetFormat)) {

					ReportSheet sheet = new ReportSheet();
					sheet.setTopTitle(sheetType.getReportSheetTopTitle());
					sheet.setBottomTitle(sheetType.getReportSheetBottomTitle());

					report.addSheet(processAsOnDateReportSheet(sheet, (long)groupAcNo, gAccount, 
							sheetType.getReportSheetFormula(), 
							(gRules != null && gRules.getAllowAssociateMembers() > 0),
							(sheetFormat.equals(EnumConst.ReportSheetFormat_DETAILED))));
				}
			}
		}

		return report;
	}

	public BigDecimal computeNetProfit(int groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}

		MonthlyReportFormula reportType = EnumCache.getMonthlyReportFormula(EnumConst.Report_Profi_Loss_Statement);
		
		BankReport report = new BankReport();
		report.setReportType(reportType.getReportName());
		report.setBankReportName(reportType.getReportDisplayName());
		report.setLangauge(EnumConst.Lang_English);

		GAc gAccount = daoFactory.getGAcDAO().findById((long)groupAcNo); 
		GRules gRules = daoFactory.getGRulesDAO().findById((long)groupAcNo); 		

		if(gAccount != null) {
			for(MonthlyReportSheetFormula sheetType:reportType.getMonthlyReportSheets()) {
				if(sheetType.getSheetFormat().equals(EnumConst.ReportSheetFormat_BANK)) {

					ReportSheet sheet = new ReportSheet();
					sheet.setTopTitle(sheetType.getReportSheetTopTitle());
					sheet.setBottomTitle(sheetType.getReportSheetBottomTitle());

					report.addSheet(processAsOnDateReportSheet(sheet, (long)groupAcNo, gAccount, 
							sheetType.getReportSheetFormula(), 
							(gRules != null && gRules.getAllowAssociateMembers() > 0), false));
				}
			}
		}

		return BDUtil.add(BDUtil.sub(report.getSheets().get(0).getTotalAmountBD(), 
				report.getSheets().get(1).getTotalAmountBD()), 
				gAccount.getNetProfitAm());
	}

	public BigDecimal computeNetProfitProjected(int groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}

		MonthlyReportFormula reportType = EnumCache.getMonthlyReportFormula(EnumConst.Report_Profi_Loss_Statement_Projected);

		BankReport report = new BankReport();
		report.setReportType(reportType.getReportName());
		report.setBankReportName(reportType.getReportDisplayName());
		report.setLangauge(EnumConst.Lang_English);

		GAc gAccount = daoFactory.getGAcDAO().findById((long)groupAcNo); 
		GRules gRules = daoFactory.getGRulesDAO().findById((long)groupAcNo); 		

		if(gAccount != null) {
			for(MonthlyReportSheetFormula sheetType:reportType.getMonthlyReportSheets()) {
				if(sheetType.getSheetFormat().equals(EnumConst.ReportSheetFormat_BANK)) {

					ReportSheet sheet = new ReportSheet();
					sheet.setTopTitle(sheetType.getReportSheetTopTitle());
					sheet.setBottomTitle(sheetType.getReportSheetBottomTitle());

					report.addSheet(processAsOnDateReportSheet(sheet, (long)groupAcNo, gAccount, 
							sheetType.getReportSheetFormula(), 
							(gRules != null && gRules.getAllowAssociateMembers() > 0), false));
				}
			}
		}

		return BDUtil.add(BDUtil.sub(report.getSheets().get(0).getTotalAmountBD(), 
				report.getSheets().get(1).getTotalAmountBD()), 
				gAccount.getNetProfitProjAm());
	}
	
	protected ReportSheet processMonthlyReportSheet(ReportSheet sheet, 
			long groupAcNo, String month, 
			MonthlyGAc gAccount, 
			PPOFormula formula, 
			boolean avaibleAM, 
			boolean showAM) {

		List<MonthlyGLoanAc> gLoanAcs = daoFactory.getMonthlyGLoanAcDAO().getAllForGroupForMonth(groupAcNo, month); 
		List<MonthlyGInvtAc> gInvtAcs = daoFactory.getMonthlyGInvtAcDAO().getAllForGroupForMonth(groupAcNo, month); 
		List<MonthlyGBankAccount> gBankAccounts = daoFactory.getMonthlyGBankAccountDAO().getAllForGroupForMonth(groupAcNo, month);
		List<MonthlyGAcByTxtype> gAcByTxtypes = daoFactory.getMonthlyGAcByTxtypeDAO().getAllForGroupForMonth(groupAcNo, month);
		if(gLoanAcs == null) { gLoanAcs = new ArrayList<MonthlyGLoanAc>(0); }
		if(gInvtAcs == null) { gInvtAcs = new ArrayList<MonthlyGInvtAc>(0); }
		if(gBankAccounts == null) { gBankAccounts = new ArrayList<MonthlyGBankAccount>(0); }
		if(gAcByTxtypes == null) { gAcByTxtypes = new ArrayList<MonthlyGAcByTxtype>(0); }

		Map<Integer, MonthlyGAcByTxtype> gAcByTxtypesMap = new HashMap<Integer, MonthlyGAcByTxtype>();
		for(MonthlyGAcByTxtype gAcByTxtype: gAcByTxtypes) {
			gAcByTxtypesMap.put(gAcByTxtype.getTxTypeId(), gAcByTxtype);
		}

		for(String tableName: formula.getTableNames()) {

			if(tableName.equals("monthly_g_ac")) {

				Map<String, String> formulaMap = formula.getTableFormula(tableName);
				BigDecimal total = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal value = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal cmValue = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal amValue = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal cumulative = DataUtil.ZERO_BIG_DECIMAL;

				for(String column: formulaMap.keySet()) {

					value = DataUtil.ZERO_BIG_DECIMAL;
					cmValue = DataUtil.ZERO_BIG_DECIMAL;
					amValue = DataUtil.ZERO_BIG_DECIMAL;
					cumulative = DataUtil.ZERO_BIG_DECIMAL;

					switch (column) {

					case "saved_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMSavedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_saving_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMOutstandingSavedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMOutstandingSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_saving_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_saved_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getCMSavedAm(), gAccount.getCMReturnedSavedAm()), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getAMSavedAm(), gAccount.getAMReturnedSavedAm()), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "planned_monthly_saving":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMPlannedMonthlySaving(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMPlannedMonthlySaving(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("planned_monthly_saving"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "prov_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProvIntEnAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProvIntEnAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("prov_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_prov_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getCMProvIntEnAm(), gAccount.getCMReturnedIntEnAm()), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getAMProvIntEnAm(), gAccount.getAMReturnedIntEnAm()), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_prov_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "returned_saved_am":
					{
						Record record = new Record();
						value = DataUtil.ZERO_BIG_DECIMAL;
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMReturnedSavedAm(), 0);
						amValue = DataUtil.ZERO_BIG_DECIMAL;

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMReturnedSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("returned_saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "returned_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMReturnedIntEnAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMReturnedIntEnAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("returned_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMPendingLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMPendingLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMRecLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMRecLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("rec_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMRecIntOnLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMRecIntOnLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("rec_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProjIntOnLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMProjIntOnLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("proj_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_sub_std_dept_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMSubStdDeptAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMSubStdDeptAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_sub_std_dept_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_bad_dept_exp_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMBadDeptExpAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMBadDeptExpAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_bad_dept_exp_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_bad_dept_closed_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMBadDeptClosedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMBadDeptClosedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_bad_dept_closed_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "profit_share_declared_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getCMProfitShareDeclaredAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("profit_share_declared_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "divided_declared_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getAMDividedDeclaredAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("divided_declared_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "profit_share_paid_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getCMProfitSharePaidAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("profit_share_paid_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "divided_paid_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getAMDividedPaidAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("divided_paid_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_p_loan_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPPendingLoanAm(), 0);
						record.setName(formulaMap.get("outstanding_p_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_loan_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPLoanAm(), 0);
						record.setName(formulaMap.get("p_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_rec_loan_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPRecLoanAm(), 0);
						record.setName(formulaMap.get("p_rec_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_rec_int_on_loan_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPRecIntOnLoanAm(), 0);
						record.setName(formulaMap.get("p_rec_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_proj_int_on_loan_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPProjIntOnLoanAm(), 0);
						record.setName(formulaMap.get("p_proj_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "project_loan_bank_account_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Project_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("project_loan_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "outstanding_fix_deposit_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPendingFixDepositAm(), 0);
						record.setName(formulaMap.get("outstanding_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fix_deposit_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getFixDepositInvAm(), 0);
						record.setName(formulaMap.get("fix_deposit_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_fix_deposit_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecFixDepositAm(), 0);
						record.setName(formulaMap.get("rec_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_fix_deposit_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecIntOnFixDepositAm(), 0);
						record.setName(formulaMap.get("rec_int_on_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_fix_deposit_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnFixDepositAm(), 0);
						record.setName(formulaMap.get("proj_int_on_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fix_deposit_bank_account_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Fix_Deposit_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("fix_deposit_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "outstanding_other_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPendingOtherInvAm(), 0);
						record.setName(formulaMap.get("outstanding_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "other_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getOtherInvAm(), 0);
						record.setName(formulaMap.get("other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_other_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecOtherInvAm(), 0);
						record.setName(formulaMap.get("rec_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_other_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecIntOnOtherInvAm(), 0);
						record.setName(formulaMap.get("rec_int_on_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_other_inv_am":
					{
						Record record = new Record();
						for(MonthlyGInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnOtherInvAm(), 0);
						record.setName(formulaMap.get("proj_int_on_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "other_inv_bank_account_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Investment_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("other_inv_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "net_profit_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getNetProfitAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("net_profit_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "net_profit_proj_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getNetProfitProjAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("net_profit_proj_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "expenses_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_EXPENSE) &&
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}
						value = GenAlgoUtil.roundHalfUp(gAccount.getExpensesAm(), 0);
						record.setName(formulaMap.get("expenses_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fee_penalty_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_FEE) && 
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}
						value = GenAlgoUtil.roundHalfUp(gAccount.getRecPenaltyAm(), 0);
						record.setName(formulaMap.get("fee_penalty_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_penalty_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_OUTSTANDING_FEE) && 
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPendingPenaltyAm(), 0);
						record.setName(formulaMap.get("outstanding_penalty_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getBorrowedLoanAm(), gAccount.getPaidBorrowedLoanAm()), 0);
						record.setName(formulaMap.get("outstanding_bank_loan_am"));
						if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							record.setValue(BDUtil.format(value));
							total = BDUtil.add(total, value);
						} else {
							record.setValue(DataUtil.ZERO_BIG_DECIMAL.toString());
						}
						sheet.addRecord(record);
						break;
					}

					case "credit_on_bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) < 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getBorrowedLoanAm(), gAccount.getPaidBorrowedLoanAm()), 0);
						record.setName(formulaMap.get("credit_on_bank_loan_am"));
						if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) < 0) {
							record.setValue(BDUtil.format(value.negate()));
							total = BDUtil.add(total, value.negate());
						} else {
							record.setValue(DataUtil.ZERO_BIG_DECIMAL.toString());
						}
						sheet.addRecord(record);
						break;
					}
					
					case "bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "paid_bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gLoanAc.getPrincipleAm(), gLoanAc.getPendingPrincipleAm()), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPaidBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("paid_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "paid_int_on_bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getPaidInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPaidInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPaidIntOnBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("paid_int_on_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_bank_loan_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("proj_int_on_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_sub_std_dept_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Sub_Standard) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankSubStdDeptAm(), 0);
						record.setName(formulaMap.get("bank_sub_std_dept_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_bad_dept_exp_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Bad_Debt) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankBadDeptExpAm(), 0);
						record.setName(formulaMap.get("bank_bad_dept_exp_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_bad_dept_closed_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Bad_Debt_Closed) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankBadDeptClosedAm(), 0);
						record.setName(formulaMap.get("bank_bad_dept_closed_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_revolving_fund_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Revolving_Fund) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getGovRevolvingFundAm(), 0);
						record.setName(formulaMap.get("gov_revolving_fund_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_revolving_fund_outstanding_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Revolving_Fund) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getGovRevolvingFundAm(), gAccount.getGovRevolvingFundReturnedAm()), 0);
						record.setName(formulaMap.get("gov_revolving_fund_outstanding_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_development_fund_am":
					{
						Record record = new Record();
						for(MonthlyGLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Gov_Development_Fund) &&
									gLoanAc.getPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getGovDevelopmentFundAm(), 0);
						record.setName(formulaMap.get("gov_development_fund_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_loan_bank_account_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Loan_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("bank_loan_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "cash_in_hand_am":
					{
						value = BDUtil.add(gAccount.getClearCashInHandAm(), gAccount.getSubjClearingCashInHandAm());
						sheet.addRecord(new Record(formulaMap.get("cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "subj_clearing_cash_in_hand_am":
					{
						value = gAccount.getSubjClearingCashInHandAm();
						sheet.addRecord(new Record(formulaMap.get("subj_clearing_cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "clear_cash_in_hand_am":
					{
						value = gAccount.getClearCashInHandAm();
						sheet.addRecord(new Record(formulaMap.get("clear_cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "int_en_on_saving_ac_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(gBankAccount.getInterestAm(), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getIntEnOnSavingAcAm(), 0);
						record.setName(formulaMap.get("int_en_on_saving_ac_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_balance_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(gAccount.getClearBankBalanceAm(), gAccount.getSubjClearingBankBalanceAm()), 0);
						record.setName(formulaMap.get("bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "subj_clearing_bank_balance_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									gBankAccount.getSubjClearingBalanceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), 
										BDUtil.format(GenAlgoUtil.roundHalfUp(gBankAccount.getSubjClearingBalanceAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getSubjClearingBankBalanceAm(), 0);
						record.setName(formulaMap.get("subj_clearing_bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "clear_bank_balance_am":
					{
						Record record = new Record();
						for(MonthlyGBankAccount gBankAccount: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									gBankAccount.getClearBalanceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), 
										BDUtil.format(GenAlgoUtil.roundHalfUp(gBankAccount.getClearBalanceAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getClearBankBalanceAm(), 0);
						record.setName(formulaMap.get("clear_bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					}
				}
				
				total = GenAlgoUtil.roundHalfUp(total, 0);
				sheet.setTotalAmount(BDUtil.format(total));
				sheet.setTotalAmountBD(total);
			}
		}
		return sheet;
	}

	protected ReportSheet processAsOnDateReportSheet(ReportSheet sheet, 
			long groupAcNo, 
			GAc gAccount, 
			PPOFormula formula, 
			boolean avaibleAM, 
			boolean showAM) {

		List<GLoanAc> gLoanAcs = daoFactory.getGLoanAcDAO().getAllAcForGroup(groupAcNo); 
		List<GInvtAc> gInvtAcs = daoFactory.getGInvtAcDAO().getAllAcForGroup(groupAcNo); 
		List<GBankAccount> gBankAccounts = daoFactory.getGBankAccountDAO().getGroupBankAccounts(groupAcNo);
		List<GAcByTxtype> gAcByTxtypes = daoFactory.getGAcByTxtypeDAO().getAllForGroup(groupAcNo);
		if(gLoanAcs == null) { gLoanAcs = new ArrayList<GLoanAc>(0); }
		if(gInvtAcs == null) { gInvtAcs = new ArrayList<GInvtAc>(0); }
		if(gBankAccounts == null) { gBankAccounts = new ArrayList<GBankAccount>(0); }
		if(gAcByTxtypes == null) { gAcByTxtypes = new ArrayList<GAcByTxtype>(0); }

		Map<Integer, GAcByTxtype> gAcByTxtypesMap = new HashMap<Integer, GAcByTxtype>();
		for(GAcByTxtype gAcByTxtype: gAcByTxtypes) {
			gAcByTxtypesMap.put(gAcByTxtype.getTxTypeId(), gAcByTxtype);
		}

		for(String tableName: formula.getTableNames()) {

			if(tableName.equals("monthly_g_ac")) {

				Map<String, String> formulaMap = formula.getTableFormula(tableName);
				BigDecimal total = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal value = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal cmValue = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal amValue = DataUtil.ZERO_BIG_DECIMAL;
				BigDecimal cumulative = DataUtil.ZERO_BIG_DECIMAL;

				for(String column: formulaMap.keySet()) {

					value = DataUtil.ZERO_BIG_DECIMAL;
					cmValue = DataUtil.ZERO_BIG_DECIMAL;
					amValue = DataUtil.ZERO_BIG_DECIMAL;
					cumulative = DataUtil.ZERO_BIG_DECIMAL;

					switch (column) {

					case "saved_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMSavedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_saving_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMOutstandingSavedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMOutstandingSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_saving_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_saved_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getCMSavedAm(), gAccount.getCMReturnedSavedAm()), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getAMSavedAm(), gAccount.getAMReturnedSavedAm()), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "planned_monthly_saving":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMPlannedMonthlySaving(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMPlannedMonthlySaving(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("planned_monthly_saving"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "prov_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProvIntEnAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProvIntEnAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("prov_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_prov_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getCMProvIntEnAm(), gAccount.getCMReturnedIntEnAm()), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getAMProvIntEnAm(), gAccount.getAMReturnedIntEnAm()), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_prov_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "returned_saved_am":
					{
						Record record = new Record();
						value = DataUtil.ZERO_BIG_DECIMAL;
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMReturnedSavedAm(), 0);
						amValue = DataUtil.ZERO_BIG_DECIMAL;

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMReturnedSavedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("returned_saved_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "returned_int_en_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMReturnedIntEnAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMReturnedIntEnAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("returned_int_en_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getCMLoanAm(), gAccount.getCMRecLoanAm()), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getAMLoanAm(), gAccount.getAMRecLoanAm()), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("outstanding_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMRecLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMRecLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("rec_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMRecIntOnLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMRecIntOnLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("rec_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_loan_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMProjIntOnLoanAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMProjIntOnLoanAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("proj_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_sub_std_dept_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMSubStdDeptAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMSubStdDeptAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_sub_std_dept_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_bad_dept_exp_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMBadDeptExpAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMBadDeptExpAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_bad_dept_exp_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "m_bad_dept_closed_am":
					{
						Record record = new Record();
						cmValue = GenAlgoUtil.roundHalfUp(gAccount.getCMBadDeptClosedAm(), 0);

						if(avaibleAM) {							
							amValue = GenAlgoUtil.roundHalfUp(gAccount.getAMBadDeptClosedAm(), 0);													
							if(showAM) {
								record.addDetail(new Record("Core Member Amount", BDUtil.format(cmValue)));
								record.addDetail(new Record("Associate Member Amount",	BDUtil.format(amValue)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(cmValue, amValue), 0);
						record.setName(formulaMap.get("m_bad_dept_closed_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "profit_share_declared_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getCMProfitShareDeclaredAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("profit_share_declared_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "divided_declared_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getAMDividedDeclaredAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("divided_declared_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "profit_share_paid_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getCMProfitSharePaidAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("profit_share_paid_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "divided_paid_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getAMDividedPaidAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("divided_paid_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_p_loan_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getPLoanAm(), gAccount.getPRecLoanAm()), 0);
						record.setName(formulaMap.get("outstanding_p_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_loan_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPLoanAm(), 0);
						record.setName(formulaMap.get("p_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_rec_loan_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPRecLoanAm(), 0);
						record.setName(formulaMap.get("p_rec_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_rec_int_on_loan_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPRecIntOnLoanAm(), 0);
						record.setName(formulaMap.get("p_rec_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "p_proj_int_on_loan_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPProjIntOnLoanAm(), 0);
						record.setName(formulaMap.get("p_proj_int_on_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "project_loan_bank_account_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Project_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("project_loan_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "outstanding_fix_deposit_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getFixDepositInvAm(), gAccount.getRecFixDepositAm()), 0);
						record.setName(formulaMap.get("outstanding_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fix_deposit_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getFixDepositInvAm(), 0);
						record.setName(formulaMap.get("fix_deposit_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_fix_deposit_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecFixDepositAm(), 0);
						record.setName(formulaMap.get("rec_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_fix_deposit_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecIntOnFixDepositAm(), 0);
						record.setName(formulaMap.get("rec_int_on_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_fix_deposit_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnFixDepositAm(), 0);
						record.setName(formulaMap.get("proj_int_on_fix_deposit_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fix_deposit_bank_account_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Fix_Deposit_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("fix_deposit_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "outstanding_other_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gInvtAc.getInvtAm(), gInvtAc.getRecInvtAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(),	BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getOtherInvAm(), gAccount.getRecOtherInvAm()), 0);
						record.setName(formulaMap.get("outstanding_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "other_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getOtherInvAm(), 0);
						record.setName(formulaMap.get("other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_other_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getRecInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInvtAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecOtherInvAm(), 0);
						record.setName(formulaMap.get("rec_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "rec_int_on_other_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getRecInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getRecInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getRecIntOnOtherInvAm(), 0);
						record.setName(formulaMap.get("rec_int_on_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_other_inv_am":
					{
						Record record = new Record();
						for(GInvtAc gInvtAc: gInvtAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, gInvtAc.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Investment_Account) && 
									gInvtAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gInvtAc.getInvestmentAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gInvtAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnOtherInvAm(), 0);
						record.setName(formulaMap.get("proj_int_on_other_inv_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "other_inv_bank_account_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Investment_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("other_inv_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "net_profit_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getNetProfitAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("net_profit_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "net_profit_proj_am":
					{
						value = GenAlgoUtil.roundHalfUp(gAccount.getNetProfitProjAm(), 0);
						sheet.addRecord(new Record(formulaMap.get("net_profit_proj_am"), BDUtil.format(value)));
						total = BDUtil.add(total, value);
						break;
					}

					case "expenses_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_EXPENSE) &&
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}
						value = GenAlgoUtil.roundHalfUp(gAccount.getExpensesAm(), 0);
						record.setName(formulaMap.get("expenses_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "fee_penalty_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_FEE) && 
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}
						value = GenAlgoUtil.roundHalfUp(gAccount.getRecPenaltyAm(), 0);
						record.setName(formulaMap.get("fee_penalty_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_penalty_am":
					{
						Record record = new Record();
						for(TxTypeValue txtype: EnumCache.getTxType().getEnumValues()) {
							if(txtype.getTxCategory().equals(EnumConst.TxType_OUTSTANDING_FEE) && 
									gAcByTxtypesMap.containsKey(txtype.getTxTypeId()) &&
									gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(txtype.getTxType(), BDUtil.format(GenAlgoUtil.roundHalfUp(gAcByTxtypesMap.get(txtype.getTxTypeId()).getAmount(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPendingPenaltyAm(), 0);
						record.setName(formulaMap.get("outstanding_penalty_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "outstanding_bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getBorrowedLoanAm(), gAccount.getPaidBorrowedLoanAm()), 0);
						record.setName(formulaMap.get("outstanding_bank_loan_am"));
						if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
							record.setValue(BDUtil.format(value));
							total = BDUtil.add(total, value);
						} else {
							record.setValue(DataUtil.ZERO_BIG_DECIMAL.toString());
						}
						sheet.addRecord(record);
						break;
					}

					case "credit_on_bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) < 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getBorrowedLoanAm(), gAccount.getPaidBorrowedLoanAm()), 0);
						record.setName(formulaMap.get("credit_on_bank_loan_am"));
						if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) < 0) {
							record.setValue(BDUtil.format(value.negate()));
							total = BDUtil.add(total, value.negate());
						} else {
							record.setValue(DataUtil.ZERO_BIG_DECIMAL.toString());
						}
						sheet.addRecord(record);
						break;
					}

					case "bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "paid_bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gLoanAc.getPrincipleAm(), gLoanAc.getPendingPrincipleAm()), 0);
							if(value.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPaidBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("paid_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "paid_int_on_bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getPaidInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPaidInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getPaidIntOnBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("paid_int_on_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "proj_int_on_bank_loan_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(gLoanAc.getProjInterestAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getProjInterestAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getProjIntOnBorrowedLoanAm(), 0);
						record.setName(formulaMap.get("proj_int_on_bank_loan_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_sub_std_dept_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Sub_Standard) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankSubStdDeptAm(), 0);
						record.setName(formulaMap.get("bank_sub_std_dept_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_bad_dept_exp_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Bad_Debt) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankBadDeptExpAm(), 0);
						record.setName(formulaMap.get("bank_bad_dept_exp_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_bad_dept_closed_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()).equals(EnumConst.AccountStatus_Bad_Debt_Closed) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getBankBadDeptClosedAm(), 0);
						record.setName(formulaMap.get("bank_bad_dept_closed_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_revolving_fund_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Revolving_Fund) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getGovRevolvingFundAm(), 0);
						record.setName(formulaMap.get("gov_revolving_fund_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_revolving_fund_outstanding_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Revolving_Fund) &&
									gLoanAc.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPendingPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.sub(gAccount.getGovRevolvingFundAm(), gAccount.getGovRevolvingFundReturnedAm()), 0);
						record.setName(formulaMap.get("gov_revolving_fund_outstanding_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "gov_development_fund_am":
					{
						Record record = new Record();
						for(GLoanAc gLoanAc: gLoanAcs) {
							if(EnumCache.getNameOfEnumValue(EnumConst.FundType, gLoanAc.getFundTypeId()).equals(EnumConst.FundType_Gov_Development_Fund) &&
									gLoanAc.getPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
								record.addDetail(new Record(gLoanAc.getLoanAcName(), BDUtil.format(GenAlgoUtil.roundHalfUp(gLoanAc.getPrincipleAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getGovDevelopmentFundAm(), 0);
						record.setName(formulaMap.get("gov_development_fund_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_loan_bank_account_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Loan_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								cumulative = BDUtil.add(cumulative, value);
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						record.setName(formulaMap.get("bank_loan_bank_account_am"));
						record.setValue(BDUtil.format(cumulative));
						sheet.addRecord(record);
						total = BDUtil.add(total, cumulative);
						break;
					}

					case "cash_in_hand_am":
					{
						value = BDUtil.add(gAccount.getClearCashInHandAm(), gAccount.getSubjClearingCashInHandAm());
						sheet.addRecord(new Record(formulaMap.get("cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "subj_clearing_cash_in_hand_am":
					{
						value = gAccount.getSubjClearingCashInHandAm();
						sheet.addRecord(new Record(formulaMap.get("subj_clearing_cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "clear_cash_in_hand_am":
					{
						value = gAccount.getClearCashInHandAm();
						sheet.addRecord(new Record(formulaMap.get("clear_cash_in_hand_am"), BDUtil.format(GenAlgoUtil.roundHalfUp(value, 0))));
						total = BDUtil.add(total, value);
						break;
					}

					case "int_en_on_saving_ac_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(gBankAccount.getInterestAm(), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getIntEnOnSavingAcAm(), 0);
						record.setName(formulaMap.get("int_en_on_saving_ac_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "bank_balance_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							value = GenAlgoUtil.roundHalfUp(BDUtil.add(gBankAccount.getClearBalanceAm(), gBankAccount.getSubjClearingBalanceAm()), 0);
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									value.compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), BDUtil.format(value)));
							}
						}

						value = GenAlgoUtil.roundHalfUp(BDUtil.add(gAccount.getClearBankBalanceAm(), gAccount.getSubjClearingBankBalanceAm()), 0);
						record.setName(formulaMap.get("bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "subj_clearing_bank_balance_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									gBankAccount.getSubjClearingBalanceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), 
										BDUtil.format(GenAlgoUtil.roundHalfUp(gBankAccount.getSubjClearingBalanceAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getSubjClearingBankBalanceAm(), 0);
						record.setName(formulaMap.get("subj_clearing_bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					case "clear_bank_balance_am":
					{
						Record record = new Record();
						for(GBankAccount gBankAccount: gBankAccounts) {
							if(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId()).equals(EnumConst.BankAccountType_Saving_Account) &&
									gBankAccount.getClearBalanceAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) != 0) {
								record.addDetail(new Record(ConversionUtil.convertToDisplayStr(gBankAccount.getBankAccount(), 12, 4), 
										BDUtil.format(GenAlgoUtil.roundHalfUp(gBankAccount.getClearBalanceAm(), 0))));
							}
						}

						value = GenAlgoUtil.roundHalfUp(gAccount.getClearBankBalanceAm(), 0);
						record.setName(formulaMap.get("clear_bank_balance_am"));
						record.setValue(BDUtil.format(value));
						sheet.addRecord(record);
						total = BDUtil.add(total, value);
						break;
					}

					}
				}
				
				total = GenAlgoUtil.roundHalfUp(total, 0);
				sheet.setTotalAmount(BDUtil.format(total));
				sheet.setTotalAmountBD(total);
			}
		}
		return sheet;
	}
}
