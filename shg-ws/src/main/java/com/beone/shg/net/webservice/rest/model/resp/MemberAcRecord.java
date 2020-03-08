package com.beone.shg.net.webservice.rest.model.resp;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MonthlyMAc;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;

public class MemberAcRecord {

	private String month;
	private String creditRating;
	private String meetingAttendance;
	private String plannedMonthlySavingAm;
	private String savedAm;
	private String outstandingSavedAm;
	private String provIntEnAm;
	private String prevMonthIntEnAm;
	private String dividedProfitDeclaredAm;
	private String dividedProfitPaidAm;
	private String returnedSavedAm;
	private String returnedIntEnAm;
	private String noOfLoans;
	private String noOfActiveLoans;
	private String loanAm;
	private String recLoanAm;
	private String outstandingLoanAm;
	private String recIntOnLoanAm;
	private String projIntOnLoanAm;
	private String badDeptClosedAm;
	private String recPenaltyAm;
	private String pendingPenaltyAm;
	private String meetingAttended;
	private String meetingMissed;	
	public MemberAcRecord() {
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}
	public String getMeetingAttendance() {
		return meetingAttendance;
	}
	public void setMeetingAttendance(String meetingAttendance) {
		this.meetingAttendance = meetingAttendance;
	}
	public String getPlannedMonthlySavingAm() {
		return plannedMonthlySavingAm;
	}
	public void setPlannedMonthlySavingAm(String plannedMonthlySavingAm) {
		this.plannedMonthlySavingAm = plannedMonthlySavingAm;
	}
	public String getSavedAm() {
		return savedAm;
	}
	public void setSavedAm(String savedAm) {
		this.savedAm = savedAm;
	}
	public String getOutstandingSavedAm() {
		return outstandingSavedAm;
	}
	public void setOutstandingSavedAm(String outstandingSavedAm) {
		this.outstandingSavedAm = outstandingSavedAm;
	}
	public String getProvIntEnAm() {
		return provIntEnAm;
	}
	public void setProvIntEnAm(String provIntEnAm) {
		this.provIntEnAm = provIntEnAm;
	}
	public String getPrevMonthIntEnAm() {
		return prevMonthIntEnAm;
	}
	public void setPrevMonthIntEnAm(String prevMonthIntEnAm) {
		this.prevMonthIntEnAm = prevMonthIntEnAm;
	}
	public String getDividedProfitDeclaredAm() {
		return dividedProfitDeclaredAm;
	}
	public void setDividedProfitDeclaredAm(String dividedProfitDeclaredAm) {
		this.dividedProfitDeclaredAm = dividedProfitDeclaredAm;
	}
	public String getDividedProfitPaidAm() {
		return dividedProfitPaidAm;
	}
	public void setDividedProfitPaidAm(String dividedProfitPaidAm) {
		this.dividedProfitPaidAm = dividedProfitPaidAm;
	}
	public String getReturnedSavedAm() {
		return returnedSavedAm;
	}
	public void setReturnedSavedAm(String returnedSavedAm) {
		this.returnedSavedAm = returnedSavedAm;
	}
	public String getReturnedIntEnAm() {
		return returnedIntEnAm;
	}
	public void setReturnedIntEnAm(String returnedIntEnAm) {
		this.returnedIntEnAm = returnedIntEnAm;
	}
	public String getNoOfLoans() {
		return noOfLoans;
	}
	public void setNoOfLoans(String noOfLoans) {
		this.noOfLoans = noOfLoans;
	}
	public String getNoOfActiveLoans() {
		return noOfActiveLoans;
	}
	public void setNoOfActiveLoans(String noOfActiveLoans) {
		this.noOfActiveLoans = noOfActiveLoans;
	}
	public String getLoanAm() {
		return loanAm;
	}
	public void setLoanAm(String loanAm) {
		this.loanAm = loanAm;
	}
	public String getRecLoanAm() {
		return recLoanAm;
	}
	public void setRecLoanAm(String recLoanAm) {
		this.recLoanAm = recLoanAm;
	}
	public String getOutstandingLoanAm() {
		return outstandingLoanAm;
	}
	public void setOutstandingLoanAm(String outstandingLoanAm) {
		this.outstandingLoanAm = outstandingLoanAm;
	}
	public String getRecIntOnLoanAm() {
		return recIntOnLoanAm;
	}
	public void setRecIntOnLoanAm(String recIntOnLoanAm) {
		this.recIntOnLoanAm = recIntOnLoanAm;
	}
	public String getProjIntOnLoanAm() {
		return projIntOnLoanAm;
	}
	public void setProjIntOnLoanAm(String projIntOnLoanAm) {
		this.projIntOnLoanAm = projIntOnLoanAm;
	}
	public String getBadDeptClosedAm() {
		return badDeptClosedAm;
	}
	public void setBadDeptClosedAm(String badDeptClosedAm) {
		this.badDeptClosedAm = badDeptClosedAm;
	}
	public String getRecPenaltyAm() {
		return recPenaltyAm;
	}
	public void setRecPenaltyAm(String recPenaltyAm) {
		this.recPenaltyAm = recPenaltyAm;
	}
	public String getPendingPenaltyAm() {
		return pendingPenaltyAm;
	}
	public void setPendingPenaltyAm(String pendingPenaltyAm) {
		this.pendingPenaltyAm = pendingPenaltyAm;
	}
	public String getMeetingAttended() {
		return meetingAttended;
	}
	public void setMeetingAttended(String meetingAttended) {
		this.meetingAttended = meetingAttended;
	}
	public String getMeetingMissed() {
		return meetingMissed;
	}
	public void setMeetingMissed(String meetingMissed) {
		this.meetingMissed = meetingMissed;
	}

	public static MemberAcRecord buildAc(MonthlyMAc mAc) {
		MemberAcRecord monthAc = new MemberAcRecord();

		monthAc.setMonth(mAc.getMonth());
		monthAc.setCreditRating(DataUtil.toString(mAc.getCreditRating()));
		monthAc.setMeetingAttendance(DataUtil.toString(mAc.getMeetingAttendance()));
		monthAc.setPlannedMonthlySavingAm(DataUtil.toString(mAc.getPlannedMonthlySavingAm()));
		monthAc.setSavedAm(DataUtil.toString(mAc.getSavedAm()));
		monthAc.setOutstandingSavedAm(DataUtil.toString(mAc.getOutstandingSavedAm()));
		monthAc.setProvIntEnAm(DataUtil.toString(mAc.getProvIntEnAm(), 2));
		monthAc.setPrevMonthIntEnAm(DataUtil.toString(mAc.getPrevMonthIntEnAm(), 2));
		monthAc.setReturnedSavedAm(DataUtil.toString(mAc.getReturnedSavedAm()));
		monthAc.setReturnedIntEnAm(DataUtil.toString(mAc.getReturnedIntEnAm()));
		monthAc.setDividedProfitDeclaredAm(DataUtil.toString(mAc.getDividedProfitDeclaredAm()));
		monthAc.setDividedProfitPaidAm(DataUtil.toString(mAc.getDividedProfitPaidAm()));
		monthAc.setNoOfLoans(DataUtil.toString(mAc.getNoOfLoans()));
		monthAc.setNoOfActiveLoans(DataUtil.toString(mAc.getNoOfActiveLoans()));
		monthAc.setLoanAm(DataUtil.toString(mAc.getLoanAm()));
		monthAc.setRecLoanAm(DataUtil.toString(mAc.getRecLoanAm()));
		monthAc.setOutstandingLoanAm(DataUtil.toString(BDUtil.sub(mAc.getLoanAm(), mAc.getRecLoanAm())));
		monthAc.setRecIntOnLoanAm(DataUtil.toString(mAc.getRecIntOnLoanAm()));
		monthAc.setProjIntOnLoanAm(DataUtil.toString(mAc.getProjIntOnLoanAm()));
		monthAc.setBadDeptClosedAm(DataUtil.toString(mAc.getBadDeptClosedAm()));
		monthAc.setRecPenaltyAm(DataUtil.toString(mAc.getRecPenaltyAm()));
		monthAc.setPendingPenaltyAm(DataUtil.toString(mAc.getPendingPenaltyAm()));
		monthAc.setMeetingAttended(DataUtil.toString(mAc.getMeetingAttended()));
		monthAc.setMeetingMissed(DataUtil.toString(mAc.getMeetingMissed()));
		
		return monthAc;
	}

	public static MemberAcRecord buildAc(MAc mAc) {
		MemberAcRecord monthAc = new MemberAcRecord();

		monthAc.setMonth(EnumConst.AsOnDate);
		monthAc.setCreditRating(DataUtil.toString(mAc.getCreditRating()));
		monthAc.setMeetingAttendance(DataUtil.toString(mAc.getMeetingAttendance()));
		monthAc.setPlannedMonthlySavingAm(DataUtil.toString(mAc.getPlannedMonthlySavingAm()));
		monthAc.setSavedAm(DataUtil.toString(mAc.getSavedAm()));
		monthAc.setOutstandingSavedAm(DataUtil.toString(mAc.getOutstandingSavedAm()));
		monthAc.setProvIntEnAm(DataUtil.toString(mAc.getProvIntEnAm(), 2));
		monthAc.setPrevMonthIntEnAm(DataUtil.toString(mAc.getPrevMonthIntEnAm(), 2));
		monthAc.setReturnedSavedAm(DataUtil.toString(mAc.getReturnedSavedAm()));
		monthAc.setReturnedIntEnAm(DataUtil.toString(mAc.getReturnedIntEnAm()));
		monthAc.setDividedProfitDeclaredAm(DataUtil.toString(mAc.getDividedProfitDeclaredAm()));
		monthAc.setDividedProfitPaidAm(DataUtil.toString(mAc.getDividedProfitPaidAm()));
		monthAc.setNoOfLoans(DataUtil.toString(mAc.getNoOfLoans()));
		monthAc.setNoOfActiveLoans(DataUtil.toString(mAc.getNoOfActiveLoans()));
		monthAc.setLoanAm(DataUtil.toString(mAc.getLoanAm()));
		monthAc.setRecLoanAm(DataUtil.toString(mAc.getRecLoanAm()));
		monthAc.setOutstandingLoanAm(DataUtil.toString(BDUtil.sub(mAc.getLoanAm(), mAc.getRecLoanAm())));
		monthAc.setRecIntOnLoanAm(DataUtil.toString(mAc.getRecIntOnLoanAm()));
		monthAc.setProjIntOnLoanAm(DataUtil.toString(mAc.getProjIntOnLoanAm()));
		monthAc.setBadDeptClosedAm(DataUtil.toString(mAc.getBadDeptClosedAm()));
		monthAc.setRecPenaltyAm(DataUtil.toString(mAc.getRecPenaltyAm()));
		monthAc.setPendingPenaltyAm(DataUtil.toString(mAc.getPendingPenaltyAm()));
		monthAc.setMeetingAttended(DataUtil.toString(mAc.getMeetingAttended()));
		monthAc.setMeetingMissed(DataUtil.toString(mAc.getMeetingMissed()));
		
		return monthAc;
	}
}
