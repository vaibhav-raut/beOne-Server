package com.beone.shg.net.webservice.rest.model.rest;

import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.support.EnumCache;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupRules {
	private long GAcNo;
	private short maxNoOfCoreMembers;
	private short allowAssociateMembers;
	private int cmMonthlySaving;
	private int amMinMonthlySaving;
	private int amMaxMonthlySaving;
	private short cmRegistrationFee;
	private short amRegistrationFee;
	private short cmSavingLateFee;
	private short amSavingLateFee;
	private short cmLoanLateFee;
	private short amLoanLateFee;
	private short chequeBouncingPenalty;
	private short autoDebitFailurePenalty;
	private short cmApplicationFee;
	private short amApplicationFee;
	private float cmLoanProcessingFeePercent;
	private float amLoanProcessingFeePercent;
	private float cmIntOnSaving;
	private float amIntOnSaving;
	private float cmBaseIntOnLoan;
	private float amBaseIntOnLoan;
	private short addIntToSavingAfterMonths;
	private float intOnConsumptionLoan;
	private int individualDevLoanRange1;
	private int individualDevLoanRange2;
	private int individualDevLoanRange3;
	private float intOnIndividualDevLoanRange1;
	private float intOnIndividualDevLoanRange2;
	private float intOnIndividualDevLoanRange3;
	private float intOnIndividualDevLoanRange4;
	private int projectDevLoanRange1;
	private int projectDevLoanRange2;
	private int projectDevLoanRange3;
	private float intOnProjectDevLoanRange1;
	private float intOnProjectDevLoanRange2;
	private float intOnProjectDevLoanRange3;
	private float intOnProjectDevLoanRange4;
	private float cmSavingXConsumptionLoan;
	private float amSavingXConsumptionLoan;
	private int cmMinMonthsToConLoan;
	private int amMinMonthsToConLoan;
	private int cmLimitOnConsumptionLoan;
	private int amLimitOnConsumptionLoan;
	private float cmSavingXDevLoan;
	private float amSavingXDevLoan;
	private int cmMinMonthsToDevLoan;
	private int amMinMonthsToDevLoan;
	private int cmMinMonthsIntOnLoan;
	private int amMinMonthsIntOnLoan;
	private float creditRatingLoanPassAbove;
	private float creditRatingLoanCausionAbove;
	private float creditRatingLoanDengerousAbove;
	private int computeDayOfMonth;
	private int dueDayOfMonth;
	private long smsSubKey;
	private long mailSubKey;
	private long reportPrintingService;
	private String smsServiceLang;
	private String mailServiceLang;
	private String reportPrintingServiceLang;
	private long activationFlags;
	private short shgOneCMRegCharge;
	private short shgOneAMRegCharge;
	private float shgOneCMLoanProFeePercent;
	private float shgOneAMLoanProFeePercent;
	private short shgOneMinAnnualServiceCharge;
	private short shgOneMaxLoanProFeeOff;
	private int shgOneBillingCycleInMonths;
	private Map<String, String> displayNames;
	public long getGAcNo() {
		return GAcNo;
	}
	public void setGAcNo(long gAcNo) {
		GAcNo = gAcNo;
	}
	public short getMaxNoOfCoreMembers() {
		return maxNoOfCoreMembers;
	}
	public void setMaxNoOfCoreMembers(short maxNoOfCoreMembers) {
		this.maxNoOfCoreMembers = maxNoOfCoreMembers;
	}
	public short getAllowAssociateMembers() {
		return allowAssociateMembers;
	}
	public void setAllowAssociateMembers(short allowAssociateMembers) {
		this.allowAssociateMembers = allowAssociateMembers;
	}
	public int getCmMonthlySaving() {
		return cmMonthlySaving;
	}
	public void setCmMonthlySaving(int cmMonthlySaving) {
		this.cmMonthlySaving = cmMonthlySaving;
	}
	public int getAmMinMonthlySaving() {
		return amMinMonthlySaving;
	}
	public void setAmMinMonthlySaving(int amMinMonthlySaving) {
		this.amMinMonthlySaving = amMinMonthlySaving;
	}
	public int getAmMaxMonthlySaving() {
		return amMaxMonthlySaving;
	}
	public void setAmMaxMonthlySaving(int amMaxMonthlySaving) {
		this.amMaxMonthlySaving = amMaxMonthlySaving;
	}
	public short getCmRegistrationFee() {
		return cmRegistrationFee;
	}
	public void setCmRegistrationFee(short cmRegistrationFee) {
		this.cmRegistrationFee = cmRegistrationFee;
	}
	public short getAmRegistrationFee() {
		return amRegistrationFee;
	}
	public void setAmRegistrationFee(short amRegistrationFee) {
		this.amRegistrationFee = amRegistrationFee;
	}
	public short getCmSavingLateFee() {
		return cmSavingLateFee;
	}
	public void setCmSavingLateFee(short cmSavingLateFee) {
		this.cmSavingLateFee = cmSavingLateFee;
	}
	public short getAmSavingLateFee() {
		return amSavingLateFee;
	}
	public void setAmSavingLateFee(short amSavingLateFee) {
		this.amSavingLateFee = amSavingLateFee;
	}
	public short getCmLoanLateFee() {
		return cmLoanLateFee;
	}
	public void setCmLoanLateFee(short cmLoanLateFee) {
		this.cmLoanLateFee = cmLoanLateFee;
	}
	public short getAmLoanLateFee() {
		return amLoanLateFee;
	}
	public void setAmLoanLateFee(short amLoanLateFee) {
		this.amLoanLateFee = amLoanLateFee;
	}
	public short getChequeBouncingPenalty() {
		return chequeBouncingPenalty;
	}
	public void setChequeBouncingPenalty(short chequeBouncingPenalty) {
		this.chequeBouncingPenalty = chequeBouncingPenalty;
	}
	public short getAutoDebitFailurePenalty() {
		return autoDebitFailurePenalty;
	}
	public void setAutoDebitFailurePenalty(short autoDebitFailurePenalty) {
		this.autoDebitFailurePenalty = autoDebitFailurePenalty;
	}
	public short getCmApplicationFee() {
		return cmApplicationFee;
	}
	public void setCmApplicationFee(short cmApplicationFee) {
		this.cmApplicationFee = cmApplicationFee;
	}
	public short getAmApplicationFee() {
		return amApplicationFee;
	}
	public void setAmApplicationFee(short amApplicationFee) {
		this.amApplicationFee = amApplicationFee;
	}
	public float getCmLoanProcessingFeePercent() {
		return cmLoanProcessingFeePercent;
	}
	public void setCmLoanProcessingFeePercent(float cmLoanProcessingFeePercent) {
		this.cmLoanProcessingFeePercent = cmLoanProcessingFeePercent;
	}
	public float getAmLoanProcessingFeePercent() {
		return amLoanProcessingFeePercent;
	}
	public void setAmLoanProcessingFeePercent(float amLoanProcessingFeePercent) {
		this.amLoanProcessingFeePercent = amLoanProcessingFeePercent;
	}
	public float getCmIntOnSaving() {
		return cmIntOnSaving;
	}
	public void setCmIntOnSaving(float cmIntOnSaving) {
		this.cmIntOnSaving = cmIntOnSaving;
	}
	public float getAmIntOnSaving() {
		return amIntOnSaving;
	}
	public void setAmIntOnSaving(float amIntOnSaving) {
		this.amIntOnSaving = amIntOnSaving;
	}
	public float getCmBaseIntOnLoan() {
		return cmBaseIntOnLoan;
	}
	public void setCmBaseIntOnLoan(float cmBaseIntOnLoan) {
		this.cmBaseIntOnLoan = cmBaseIntOnLoan;
	}
	public float getAmBaseIntOnLoan() {
		return amBaseIntOnLoan;
	}
	public void setAmBaseIntOnLoan(float amBaseIntOnLoan) {
		this.amBaseIntOnLoan = amBaseIntOnLoan;
	}
	public short getAddIntToSavingAfterMonths() {
		return addIntToSavingAfterMonths;
	}
	public void setAddIntToSavingAfterMonths(short addIntToSavingAfterMonths) {
		this.addIntToSavingAfterMonths = addIntToSavingAfterMonths;
	}
	public float getIntOnConsumptionLoan() {
		return intOnConsumptionLoan;
	}
	public void setIntOnConsumptionLoan(float intOnConsumptionLoan) {
		this.intOnConsumptionLoan = intOnConsumptionLoan;
	}
	public int getIndividualDevLoanRange1() {
		return individualDevLoanRange1;
	}
	public void setIndividualDevLoanRange1(int individualDevLoanRange1) {
		this.individualDevLoanRange1 = individualDevLoanRange1;
	}
	public int getIndividualDevLoanRange2() {
		return individualDevLoanRange2;
	}
	public void setIndividualDevLoanRange2(int individualDevLoanRange2) {
		this.individualDevLoanRange2 = individualDevLoanRange2;
	}
	public int getIndividualDevLoanRange3() {
		return individualDevLoanRange3;
	}
	public void setIndividualDevLoanRange3(int individualDevLoanRange3) {
		this.individualDevLoanRange3 = individualDevLoanRange3;
	}
	public float getIntOnIndividualDevLoanRange1() {
		return intOnIndividualDevLoanRange1;
	}
	public void setIntOnIndividualDevLoanRange1(float intOnIndividualDevLoanRange1) {
		this.intOnIndividualDevLoanRange1 = intOnIndividualDevLoanRange1;
	}
	public float getIntOnIndividualDevLoanRange2() {
		return intOnIndividualDevLoanRange2;
	}
	public void setIntOnIndividualDevLoanRange2(float intOnIndividualDevLoanRange2) {
		this.intOnIndividualDevLoanRange2 = intOnIndividualDevLoanRange2;
	}
	public float getIntOnIndividualDevLoanRange3() {
		return intOnIndividualDevLoanRange3;
	}
	public void setIntOnIndividualDevLoanRange3(float intOnIndividualDevLoanRange3) {
		this.intOnIndividualDevLoanRange3 = intOnIndividualDevLoanRange3;
	}
	public float getIntOnIndividualDevLoanRange4() {
		return intOnIndividualDevLoanRange4;
	}
	public void setIntOnIndividualDevLoanRange4(float intOnIndividualDevLoanRange4) {
		this.intOnIndividualDevLoanRange4 = intOnIndividualDevLoanRange4;
	}
	public int getProjectDevLoanRange1() {
		return projectDevLoanRange1;
	}
	public void setProjectDevLoanRange1(int projectDevLoanRange1) {
		this.projectDevLoanRange1 = projectDevLoanRange1;
	}
	public int getProjectDevLoanRange2() {
		return projectDevLoanRange2;
	}
	public void setProjectDevLoanRange2(int projectDevLoanRange2) {
		this.projectDevLoanRange2 = projectDevLoanRange2;
	}
	public int getProjectDevLoanRange3() {
		return projectDevLoanRange3;
	}
	public void setProjectDevLoanRange3(int projectDevLoanRange3) {
		this.projectDevLoanRange3 = projectDevLoanRange3;
	}
	public float getIntOnProjectDevLoanRange1() {
		return intOnProjectDevLoanRange1;
	}
	public void setIntOnProjectDevLoanRange1(float intOnProjectDevLoanRange1) {
		this.intOnProjectDevLoanRange1 = intOnProjectDevLoanRange1;
	}
	public float getIntOnProjectDevLoanRange2() {
		return intOnProjectDevLoanRange2;
	}
	public void setIntOnProjectDevLoanRange2(float intOnProjectDevLoanRange2) {
		this.intOnProjectDevLoanRange2 = intOnProjectDevLoanRange2;
	}
	public float getIntOnProjectDevLoanRange3() {
		return intOnProjectDevLoanRange3;
	}
	public void setIntOnProjectDevLoanRange3(float intOnProjectDevLoanRange3) {
		this.intOnProjectDevLoanRange3 = intOnProjectDevLoanRange3;
	}
	public float getIntOnProjectDevLoanRange4() {
		return intOnProjectDevLoanRange4;
	}
	public void setIntOnProjectDevLoanRange4(float intOnProjectDevLoanRange4) {
		this.intOnProjectDevLoanRange4 = intOnProjectDevLoanRange4;
	}
	public float getCmSavingXConsumptionLoan() {
		return cmSavingXConsumptionLoan;
	}
	public void setCmSavingXConsumptionLoan(float cmSavingXConsumptionLoan) {
		this.cmSavingXConsumptionLoan = cmSavingXConsumptionLoan;
	}
	public float getAmSavingXConsumptionLoan() {
		return amSavingXConsumptionLoan;
	}
	public void setAmSavingXConsumptionLoan(float amSavingXConsumptionLoan) {
		this.amSavingXConsumptionLoan = amSavingXConsumptionLoan;
	}
	public int getCmMinMonthsToConLoan() {
		return cmMinMonthsToConLoan;
	}
	public void setCmMinMonthsToConLoan(int cmMinMonthsToConLoan) {
		this.cmMinMonthsToConLoan = cmMinMonthsToConLoan;
	}
	public int getAmMinMonthsToConLoan() {
		return amMinMonthsToConLoan;
	}
	public void setAmMinMonthsToConLoan(int amMinMonthsToConLoan) {
		this.amMinMonthsToConLoan = amMinMonthsToConLoan;
	}
	public int getCmLimitOnConsumptionLoan() {
		return cmLimitOnConsumptionLoan;
	}
	public void setCmLimitOnConsumptionLoan(int cmLimitOnConsumptionLoan) {
		this.cmLimitOnConsumptionLoan = cmLimitOnConsumptionLoan;
	}
	public int getAmLimitOnConsumptionLoan() {
		return amLimitOnConsumptionLoan;
	}
	public void setAmLimitOnConsumptionLoan(int amLimitOnConsumptionLoan) {
		this.amLimitOnConsumptionLoan = amLimitOnConsumptionLoan;
	}
	public float getCmSavingXDevLoan() {
		return cmSavingXDevLoan;
	}
	public void setCmSavingXDevLoan(float cmSavingXDevLoan) {
		this.cmSavingXDevLoan = cmSavingXDevLoan;
	}
	public float getAmSavingXDevLoan() {
		return amSavingXDevLoan;
	}
	public void setAmSavingXDevLoan(float amSavingXDevLoan) {
		this.amSavingXDevLoan = amSavingXDevLoan;
	}
	public int getCmMinMonthsToDevLoan() {
		return cmMinMonthsToDevLoan;
	}
	public void setCmMinMonthsToDevLoan(int cmMinMonthsToDevLoan) {
		this.cmMinMonthsToDevLoan = cmMinMonthsToDevLoan;
	}
	public int getAmMinMonthsToDevLoan() {
		return amMinMonthsToDevLoan;
	}
	public void setAmMinMonthsToDevLoan(int amMinMonthsToDevLoan) {
		this.amMinMonthsToDevLoan = amMinMonthsToDevLoan;
	}
	public int getCmMinMonthsIntOnLoan() {
		return cmMinMonthsIntOnLoan;
	}
	public void setCmMinMonthsIntOnLoan(int cmMinMonthsIntOnLoan) {
		this.cmMinMonthsIntOnLoan = cmMinMonthsIntOnLoan;
	}
	public int getAmMinMonthsIntOnLoan() {
		return amMinMonthsIntOnLoan;
	}
	public void setAmMinMonthsIntOnLoan(int amMinMonthsIntOnLoan) {
		this.amMinMonthsIntOnLoan = amMinMonthsIntOnLoan;
	}
	public float getCreditRatingLoanPassAbove() {
		return creditRatingLoanPassAbove;
	}
	public void setCreditRatingLoanPassAbove(float creditRatingLoanPassAbove) {
		this.creditRatingLoanPassAbove = creditRatingLoanPassAbove;
	}
	public float getCreditRatingLoanCausionAbove() {
		return creditRatingLoanCausionAbove;
	}
	public void setCreditRatingLoanCausionAbove(float creditRatingLoanCausionAbove) {
		this.creditRatingLoanCausionAbove = creditRatingLoanCausionAbove;
	}
	public float getCreditRatingLoanDengerousAbove() {
		return creditRatingLoanDengerousAbove;
	}
	public void setCreditRatingLoanDengerousAbove(
			float creditRatingLoanDengerousAbove) {
		this.creditRatingLoanDengerousAbove = creditRatingLoanDengerousAbove;
	}
	public int getComputeDayOfMonth() {
		return computeDayOfMonth;
	}
	public void setComputeDayOfMonth(int computeDayOfMonth) {
		this.computeDayOfMonth = computeDayOfMonth;
	}
	public int getDueDayOfMonth() {
		return dueDayOfMonth;
	}
	public void setDueDayOfMonth(int dueDayOfMonth) {
		this.dueDayOfMonth = dueDayOfMonth;
	}
	public long getSmsSubKey() {
		return smsSubKey;
	}
	public void setSmsSubKey(long smsSubKey) {
		this.smsSubKey = smsSubKey;
	}
	public long getMailSubKey() {
		return mailSubKey;
	}
	public void setMailSubKey(long mailSubKey) {
		this.mailSubKey = mailSubKey;
	}
	public long getReportPrintingService() {
		return reportPrintingService;
	}
	public void setReportPrintingService(long reportPrintingService) {
		this.reportPrintingService = reportPrintingService;
	}
	public String getSmsServiceLang() {
		return smsServiceLang;
	}
	public void setSmsServiceLang(String smsServiceLang) {
		this.smsServiceLang = smsServiceLang;
	}
	public String getMailServiceLang() {
		return mailServiceLang;
	}
	public void setMailServiceLang(String mailServiceLang) {
		this.mailServiceLang = mailServiceLang;
	}
	public String getReportPrintingServiceLang() {
		return reportPrintingServiceLang;
	}
	public void setReportPrintingServiceLang(String reportPrintingServiceLang) {
		this.reportPrintingServiceLang = reportPrintingServiceLang;
	}
	public long getActivationFlags() {
		return activationFlags;
	}
	public void setActivationFlags(long activationFlags) {
		this.activationFlags = activationFlags;
	}
	public short getShgOneCMRegCharge() {
		return shgOneCMRegCharge;
	}
	public void setShgOneCMRegCharge(short shgOneCMRegCharge) {
		this.shgOneCMRegCharge = shgOneCMRegCharge;
	}
	public short getShgOneAMRegCharge() {
		return shgOneAMRegCharge;
	}
	public void setShgOneAMRegCharge(short shgOneAMRegCharge) {
		this.shgOneAMRegCharge = shgOneAMRegCharge;
	}
	public float getShgOneCMLoanProFeePercent() {
		return shgOneCMLoanProFeePercent;
	}
	public void setShgOneCMLoanProFeePercent(float shgOneCMLoanProFeePercent) {
		this.shgOneCMLoanProFeePercent = shgOneCMLoanProFeePercent;
	}
	public float getShgOneAMLoanProFeePercent() {
		return shgOneAMLoanProFeePercent;
	}
	public void setShgOneAMLoanProFeePercent(float shgOneAMLoanProFeePercent) {
		this.shgOneAMLoanProFeePercent = shgOneAMLoanProFeePercent;
	}
	public short getShgOneMinAnnualServiceCharge() {
		return shgOneMinAnnualServiceCharge;
	}
	public void setShgOneMinAnnualServiceCharge(short shgOneMinAnnualServiceCharge) {
		this.shgOneMinAnnualServiceCharge = shgOneMinAnnualServiceCharge;
	}
	public short getShgOneMaxLoanProFeeOff() {
		return shgOneMaxLoanProFeeOff;
	}
	public void setShgOneMaxLoanProFeeOff(short shgOneMaxLoanProFeeOff) {
		this.shgOneMaxLoanProFeeOff = shgOneMaxLoanProFeeOff;
	}
	public int getShgOneBillingCycleInMonths() {
		return shgOneBillingCycleInMonths;
	}
	public void setShgOneBillingCycleInMonths(int shgOneBillingCycleInMonths) {
		this.shgOneBillingCycleInMonths = shgOneBillingCycleInMonths;
	}
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}

	public static void loadGroupRules(GRules gRules, GroupRules groupRules) {
		
		groupRules.setGAcNo(gRules.getGAcNo());
		groupRules.setMaxNoOfCoreMembers(gRules.getMaxNoOfCoreMembers());
		groupRules.setAllowAssociateMembers(gRules.getAllowAssociateMembers());
		groupRules.setCmMonthlySaving(gRules.getCmMonthlySaving());
		groupRules.setAmMinMonthlySaving(gRules.getAmMinMonthlySaving());
		groupRules.setAmMaxMonthlySaving(gRules.getAmMaxMonthlySaving());
		groupRules.setCmRegistrationFee(gRules.getCmRegistrationFee());
		groupRules.setAmRegistrationFee(gRules.getAmRegistrationFee());
		groupRules.setCmSavingLateFee(gRules.getCmSavingLateFee());
		groupRules.setAmSavingLateFee(gRules.getAmSavingLateFee());
		groupRules.setCmLoanLateFee(gRules.getCmLoanLateFee());
		groupRules.setAmLoanLateFee(gRules.getAmLoanLateFee());
		groupRules.setChequeBouncingPenalty(gRules.getChequeBouncingPenalty());
		groupRules.setAutoDebitFailurePenalty(gRules.getAutoDebitFailurePenalty());
		groupRules.setCmApplicationFee(gRules.getCmApplicationFee());
		groupRules.setAmApplicationFee(gRules.getAmApplicationFee());
		groupRules.setCmLoanProcessingFeePercent(gRules.getCmLoanProcessingFeePercent());
		groupRules.setAmLoanProcessingFeePercent(gRules.getAmLoanProcessingFeePercent());
		groupRules.setCmIntOnSaving(gRules.getCmIntOnSaving());
		groupRules.setAmIntOnSaving(gRules.getAmIntOnSaving());
		groupRules.setCmBaseIntOnLoan(gRules.getCmBaseIntOnLoan());
		groupRules.setAmBaseIntOnLoan(gRules.getAmBaseIntOnLoan());
		groupRules.setAddIntToSavingAfterMonths(gRules.getAddIntToSavingAfterMonths());
		groupRules.setIntOnConsumptionLoan(gRules.getIntOnConsumptionLoan());
		groupRules.setIndividualDevLoanRange1(gRules.getIndividualDevLoanRange1());
		groupRules.setIndividualDevLoanRange2(gRules.getIndividualDevLoanRange2());
		groupRules.setIndividualDevLoanRange3(gRules.getIndividualDevLoanRange3());
		groupRules.setIntOnIndividualDevLoanRange1(gRules.getIntOnIndividualDevLoanRange1());
		groupRules.setIntOnIndividualDevLoanRange2(gRules.getIntOnIndividualDevLoanRange2());
		groupRules.setIntOnIndividualDevLoanRange3(gRules.getIntOnIndividualDevLoanRange3());
		groupRules.setIntOnIndividualDevLoanRange4(gRules.getIntOnIndividualDevLoanRange4());
		groupRules.setProjectDevLoanRange1(gRules.getProjectDevLoanRange1());
		groupRules.setProjectDevLoanRange2(gRules.getProjectDevLoanRange2());
		groupRules.setProjectDevLoanRange3(gRules.getProjectDevLoanRange3());
		groupRules.setIntOnProjectDevLoanRange1(gRules.getIntOnProjectDevLoanRange1());
		groupRules.setIntOnProjectDevLoanRange2(gRules.getIntOnProjectDevLoanRange2());
		groupRules.setIntOnProjectDevLoanRange3(gRules.getIntOnProjectDevLoanRange3());
		groupRules.setIntOnProjectDevLoanRange4(gRules.getIntOnProjectDevLoanRange4());
		groupRules.setCmSavingXConsumptionLoan(gRules.getCmSavingXConsumptionLoan());
		groupRules.setAmSavingXConsumptionLoan(gRules.getAmSavingXConsumptionLoan());
		groupRules.setCmMinMonthsToConLoan(gRules.getCmMinMonthsToConLoan());
		groupRules.setAmMinMonthsToConLoan(gRules.getAmMinMonthsToConLoan());
		groupRules.setCmLimitOnConsumptionLoan(gRules.getCmLimitOnConsumptionLoan());
		groupRules.setAmLimitOnConsumptionLoan(gRules.getAmLimitOnConsumptionLoan());
		groupRules.setCmSavingXDevLoan(gRules.getCmSavingXDevLoan());
		groupRules.setAmSavingXDevLoan(gRules.getAmSavingXDevLoan());
		groupRules.setCmMinMonthsToDevLoan(gRules.getCmMinMonthsToDevLoan());
		groupRules.setAmMinMonthsToDevLoan(gRules.getAmMinMonthsToDevLoan());
		groupRules.setCmMinMonthsIntOnLoan(gRules.getCmMinMonthsIntOnLoan());
		groupRules.setAmMinMonthsIntOnLoan(gRules.getAmMinMonthsIntOnLoan());
		groupRules.setCreditRatingLoanPassAbove(gRules.getCreditRatingLoanPassAbove());
		groupRules.setCreditRatingLoanCausionAbove(gRules.getCreditRatingLoanCausionAbove());
		groupRules.setCreditRatingLoanDengerousAbove(gRules.getCreditRatingLoanDengerousAbove());
		groupRules.setComputeDayOfMonth(gRules.getComputeDayOfMonth());
		groupRules.setDueDayOfMonth(gRules.getDueDayOfMonth());
		groupRules.setSmsSubKey(gRules.getSmsSubKey());
		groupRules.setMailSubKey(gRules.getMailSubKey());
		groupRules.setReportPrintingService(gRules.getReportPrintingService());
		groupRules.setSmsServiceLang(EnumCache.getNameOfEnumValue(EnumConst.Lang, gRules.getSmsServiceLang()));
		groupRules.setMailServiceLang(EnumCache.getNameOfEnumValue(EnumConst.Lang, gRules.getMailServiceLang()));
		groupRules.setReportPrintingServiceLang(EnumCache.getNameOfEnumValue(EnumConst.Lang, gRules.getReportPrintingServiceLang()));
		groupRules.setShgOneCMRegCharge(gRules.getShgOneCMRegCharge());
		groupRules.setShgOneAMRegCharge(gRules.getShgOneAMRegCharge());
		groupRules.setShgOneCMLoanProFeePercent(gRules.getShgOneCMLoanProFeePercent());
		groupRules.setShgOneAMLoanProFeePercent(gRules.getShgOneAMLoanProFeePercent());
		groupRules.setShgOneMinAnnualServiceCharge(gRules.getShgOneMinAnnualServiceCharge());
		groupRules.setShgOneMaxLoanProFeeOff(gRules.getShgOneMaxLoanProFeeOff());
		groupRules.setShgOneBillingCycleInMonths(gRules.getShgOneBillingCycleInMonths());
	}

	public static void loadGRules(GroupRules groupRules, GRules gRules) {
		
		gRules.setMaxNoOfCoreMembers(groupRules.getMaxNoOfCoreMembers());
		gRules.setAllowAssociateMembers(groupRules.getAllowAssociateMembers());
		gRules.setCmMonthlySaving(groupRules.getCmMonthlySaving());
		gRules.setAmMinMonthlySaving(groupRules.getAmMinMonthlySaving());
		gRules.setAmMaxMonthlySaving(groupRules.getAmMaxMonthlySaving());
		gRules.setCmRegistrationFee(groupRules.getCmRegistrationFee());
		gRules.setAmRegistrationFee(groupRules.getAmRegistrationFee());
		gRules.setCmSavingLateFee(groupRules.getCmSavingLateFee());
		gRules.setAmSavingLateFee(groupRules.getAmSavingLateFee());
		gRules.setCmLoanLateFee(groupRules.getCmLoanLateFee());
		gRules.setAmLoanLateFee(groupRules.getAmLoanLateFee());
		gRules.setChequeBouncingPenalty(groupRules.getChequeBouncingPenalty());
		gRules.setAutoDebitFailurePenalty(groupRules.getAutoDebitFailurePenalty());
		gRules.setCmApplicationFee(groupRules.getCmApplicationFee());
		gRules.setAmApplicationFee(groupRules.getAmApplicationFee());
		gRules.setCmLoanProcessingFeePercent(groupRules.getCmLoanProcessingFeePercent());
		gRules.setAmLoanProcessingFeePercent(groupRules.getAmLoanProcessingFeePercent());
		gRules.setCmIntOnSaving(groupRules.getCmIntOnSaving());
		gRules.setAmIntOnSaving(groupRules.getAmIntOnSaving());
		gRules.setCmBaseIntOnLoan(groupRules.getCmBaseIntOnLoan());
		gRules.setAmBaseIntOnLoan(groupRules.getAmBaseIntOnLoan());
		gRules.setAddIntToSavingAfterMonths(groupRules.getAddIntToSavingAfterMonths());
		gRules.setIntOnConsumptionLoan(groupRules.getIntOnConsumptionLoan());
		gRules.setIndividualDevLoanRange1(groupRules.getIndividualDevLoanRange1());
		gRules.setIndividualDevLoanRange2(groupRules.getIndividualDevLoanRange2());
		gRules.setIndividualDevLoanRange3(groupRules.getIndividualDevLoanRange3());
		gRules.setIntOnIndividualDevLoanRange1(groupRules.getIntOnIndividualDevLoanRange1());
		gRules.setIntOnIndividualDevLoanRange2(groupRules.getIntOnIndividualDevLoanRange2());
		gRules.setIntOnIndividualDevLoanRange3(groupRules.getIntOnIndividualDevLoanRange3());
		gRules.setIntOnIndividualDevLoanRange4(groupRules.getIntOnIndividualDevLoanRange4());
		gRules.setProjectDevLoanRange1(groupRules.getProjectDevLoanRange1());
		gRules.setProjectDevLoanRange2(groupRules.getProjectDevLoanRange2());
		gRules.setProjectDevLoanRange3(groupRules.getProjectDevLoanRange3());
		gRules.setIntOnProjectDevLoanRange1(groupRules.getIntOnProjectDevLoanRange1());
		gRules.setIntOnProjectDevLoanRange2(groupRules.getIntOnProjectDevLoanRange2());
		gRules.setIntOnProjectDevLoanRange3(groupRules.getIntOnProjectDevLoanRange3());
		gRules.setIntOnProjectDevLoanRange4(groupRules.getIntOnProjectDevLoanRange4());
		gRules.setCmSavingXConsumptionLoan(groupRules.getCmSavingXConsumptionLoan());
		gRules.setAmSavingXConsumptionLoan(groupRules.getAmSavingXConsumptionLoan());
		gRules.setCmMinMonthsToConLoan(groupRules.getCmMinMonthsToConLoan());
		gRules.setAmMinMonthsToConLoan(groupRules.getAmMinMonthsToConLoan());
		gRules.setCmLimitOnConsumptionLoan(groupRules.getCmLimitOnConsumptionLoan());
		gRules.setAmLimitOnConsumptionLoan(groupRules.getAmLimitOnConsumptionLoan());
		gRules.setCmSavingXDevLoan(groupRules.getCmSavingXDevLoan());
		gRules.setAmSavingXDevLoan(groupRules.getAmSavingXDevLoan());
		gRules.setCmMinMonthsToDevLoan(groupRules.getCmMinMonthsToDevLoan());
		gRules.setAmMinMonthsToDevLoan(groupRules.getAmMinMonthsToDevLoan());
		gRules.setCmMinMonthsIntOnLoan(groupRules.getCmMinMonthsIntOnLoan());
		gRules.setAmMinMonthsIntOnLoan(groupRules.getAmMinMonthsIntOnLoan());
		gRules.setCreditRatingLoanPassAbove(groupRules.getCreditRatingLoanPassAbove());
		gRules.setCreditRatingLoanCausionAbove(groupRules.getCreditRatingLoanCausionAbove());
		gRules.setCreditRatingLoanDengerousAbove(groupRules.getCreditRatingLoanDengerousAbove());
		gRules.setComputeDayOfMonth(groupRules.getComputeDayOfMonth());
		gRules.setDueDayOfMonth(groupRules.getDueDayOfMonth());
		gRules.setSmsSubKey(groupRules.getSmsSubKey());
		gRules.setMailSubKey(groupRules.getMailSubKey());
		gRules.setReportPrintingService(groupRules.getReportPrintingService());
		gRules.setSmsServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, groupRules.getSmsServiceLang()));
		gRules.setMailServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, groupRules.getMailServiceLang()));
		gRules.setReportPrintingServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, groupRules.getReportPrintingServiceLang()));
		gRules.setShgOneCMRegCharge(groupRules.getShgOneCMRegCharge());
		gRules.setShgOneAMRegCharge(groupRules.getShgOneAMRegCharge());
		gRules.setShgOneCMLoanProFeePercent(groupRules.getShgOneCMLoanProFeePercent());
		gRules.setShgOneAMLoanProFeePercent(groupRules.getShgOneAMLoanProFeePercent());
		gRules.setShgOneMinAnnualServiceCharge(groupRules.getShgOneMinAnnualServiceCharge());
		gRules.setShgOneMaxLoanProFeeOff(groupRules.getShgOneMaxLoanProFeeOff());
		gRules.setShgOneBillingCycleInMonths(groupRules.getShgOneBillingCycleInMonths());
	}
}
