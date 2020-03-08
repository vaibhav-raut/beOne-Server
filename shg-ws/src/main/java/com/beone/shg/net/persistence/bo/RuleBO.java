package com.beone.shg.net.persistence.bo;

import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.GRulesTree;
import com.beone.shg.net.webservice.rest.model.resp.Rule;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("ruleBO")
public class RuleBO extends BaseBO {

	protected final static String allowAssociateMembers = "allowAssociateMembers";
	protected final static String cmMonthlySaving = "cmMonthlySaving";
	protected final static String cmIntOnSaving = "cmIntOnSaving";
	protected final static String addIntToSavingAfterMonths = "addIntToSavingAfterMonths";
	protected final static String cmLoanProcessingFeePercent = "cmLoanProcessingFeePercent";
	protected final static String cmBaseIntOnLoan = "cmBaseIntOnLoan";
	protected final static String cmMinMonthsIntOnLoan = "cmMinMonthsIntOnLoan";
	protected final static String intOnConsumptionLoan = "intOnConsumptionLoan";
	protected final static String cmSavingXConsumptionLoan = "cmSavingXConsumptionLoan";
	protected final static String cmMinMonthsToConLoan = "cmMinMonthsToConLoan";
	protected final static String cmLimitOnConsumptionLoan = "cmLimitOnConsumptionLoan";
	protected final static String cmSavingXDevLoan = "cmSavingXDevLoan";
	protected final static String cmMinMonthsToDevLoan = "cmMinMonthsToDevLoan";
	protected final static String cmRegistrationFee = "cmRegistrationFee";
	protected final static String cmSavingLateFee = "cmSavingLateFee";
	protected final static String cmLoanLateFee = "cmLoanLateFee";
	protected final static String cmApplicationFee = "cmApplicationFee";
	protected final static String chequeBouncingPenalty = "chequeBouncingPenalty";
	protected final static String autoDebitFailurePenalty = "autoDebitFailurePenalty";
	protected final static String individualDevLoanRange1 = "individualDevLoanRange1";
	protected final static String individualDevLoanRange2 = "individualDevLoanRange2";
	protected final static String individualDevLoanRange3 = "individualDevLoanRange3";
	protected final static String intOnIndividualDevLoanRange1 = "intOnIndividualDevLoanRange1";
	protected final static String intOnIndividualDevLoanRange2 = "intOnIndividualDevLoanRange2";
	protected final static String intOnIndividualDevLoanRange3 = "intOnIndividualDevLoanRange3";
	protected final static String intOnIndividualDevLoanRange4 = "intOnIndividualDevLoanRange4";
	protected final static String projectDevLoanRange1 = "projectDevLoanRange1";
	protected final static String projectDevLoanRange2 = "projectDevLoanRange2";
	protected final static String projectDevLoanRange3 = "projectDevLoanRange3";
	protected final static String intOnProjectDevLoanRange1 = "intOnProjectDevLoanRange1";
	protected final static String intOnProjectDevLoanRange2 = "intOnProjectDevLoanRange2";
	protected final static String intOnProjectDevLoanRange3 = "intOnProjectDevLoanRange3";
	protected final static String intOnProjectDevLoanRange4 = "intOnProjectDevLoanRange4";
	protected final static String amMinMonthlySaving = "amMinMonthlySaving";
	protected final static String amMaxMonthlySaving = "amMaxMonthlySaving";
	protected final static String amIntOnSaving = "amIntOnSaving";
	protected final static String amRegistrationFee = "amRegistrationFee";
	protected final static String amSavingLateFee = "amSavingLateFee";
	protected final static String amLoanLateFee = "amLoanLateFee";
	protected final static String amApplicationFee = "amApplicationFee";
	protected final static String amLoanProcessingFeePercent = "amLoanProcessingFeePercent";
	protected final static String amBaseIntOnLoan = "amBaseIntOnLoan";
	protected final static String amMinMonthsIntOnLoan = "amMinMonthsIntOnLoan";
	protected final static String amSavingXConsumptionLoan = "amSavingXConsumptionLoan";
	protected final static String amMinMonthsToConLoan = "amMinMonthsToConLoan";
	protected final static String amLimitOnConsumptionLoan = "amLimitOnConsumptionLoan";
	protected final static String amSavingXDevLoan = "amSavingXDevLoan";
	protected final static String amMinMonthsToDevLoan = "amMinMonthsToDevLoan";
	protected final static String computeDayOfMonth = "computeDayOfMonth";
	protected final static String dueDayOfMonth = "dueDayOfMonth";
	protected final static String activationFlags = "activationFlags";
	
	public GRulesTree getGRulesTree(long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Ac No");
		}
		GRules gRules = daoFactory.getGRulesDAO().findById(groupAcNo);		
		if(gRules == null) {
			throw new BadRequestException("Invalid Group Ac No");
		}
		GRulesTree tree = new GRulesTree(groupAcNo);

		tree.addRule(new Rule(allowAssociateMembers, "Max Allowed Associate Members", DataUtil.toString(gRules.getAllowAssociateMembers()), "int"));
//		tree.addRule(new Rule(computeDayOfMonth, "Compute Day Of Month", DataUtil.toString(gRules.getComputeDayOfMonth()), "int"));
		tree.addRule(new Rule(dueDayOfMonth, "Due Day Of Month", DataUtil.toString(gRules.getDueDayOfMonth()), "int"));
		tree.addRule(new Rule(activationFlags, "Activation Flag", DataUtil.toString(gRules.getActivationFlags()), "bool"));

		{
			Rule savingRule = new Rule(DataUtil.EMPTY_STRING, "Saving Rules", "", "None");

			savingRule.addDetail(new Rule(cmMonthlySaving, "Monthly Saving", DataUtil.toString(gRules.getCmMonthlySaving()), "int"));
			savingRule.addDetail(new Rule(cmIntOnSaving, "Interest on Saving", DataUtil.toString(gRules.getCmIntOnSaving()), "float", ""));
			savingRule.addDetail(new Rule(addIntToSavingAfterMonths, "Add Int to Saving After Months", DataUtil.toString(gRules.getAddIntToSavingAfterMonths()), "int", "For ex: 12 - Add after 12 Months"));

			tree.addRule(savingRule);
		}

		{
			Rule loanRule = new Rule(DataUtil.EMPTY_STRING, "Loan Rules", "", "None");

			loanRule.addDetail(new Rule(cmLoanProcessingFeePercent, "Loan Processing Fee", DataUtil.toString(gRules.getCmLoanProcessingFeePercent()), "float", "1 = 1% fee of Loan"));
			loanRule.addDetail(new Rule(cmBaseIntOnLoan, "Min Interest Rate", DataUtil.toString(gRules.getCmBaseIntOnLoan()), "float"));
			loanRule.addDetail(new Rule(cmMinMonthsIntOnLoan, "Min Months of Interest", DataUtil.toString(gRules.getCmMinMonthsIntOnLoan()), "int"));

			{
				Rule conLoanRule = new Rule(DataUtil.EMPTY_STRING, "Consumption Loan", "", "None");							
				conLoanRule.addDetail(new Rule(intOnConsumptionLoan, "Interest Rate", DataUtil.toString(gRules.getIntOnConsumptionLoan()), "float", ""));
				conLoanRule.addDetail(new Rule(cmSavingXConsumptionLoan, "Max Loan Limit in Multiple of Saving", DataUtil.toString(gRules.getCmSavingXConsumptionLoan()), "float", ""));
				conLoanRule.addDetail(new Rule(cmMinMonthsToConLoan, "Min No of Months Saving", DataUtil.toString(gRules.getCmMinMonthsToConLoan()), "int", ""));
				conLoanRule.addDetail(new Rule(cmLimitOnConsumptionLoan, "Max Loan Limit", DataUtil.toString(gRules.getCmLimitOnConsumptionLoan()), "int", ""));
				loanRule.addDetail(conLoanRule);
			}

			{
				Rule devLoanRule = new Rule(DataUtil.EMPTY_STRING, "Development Loan", "", "None");							
				devLoanRule.addDetail(new Rule(intOnIndividualDevLoanRange1, "Interest Rate", DataUtil.toString(gRules.getIntOnIndividualDevLoanRange1()), "float", ""));
				devLoanRule.addDetail(new Rule(cmSavingXDevLoan, "Max Loan Limit in Multiple of Saving", DataUtil.toString(gRules.getCmSavingXDevLoan()), "float", ""));
				devLoanRule.addDetail(new Rule(cmMinMonthsToDevLoan, "Min No of Months Saving", DataUtil.toString(gRules.getCmMinMonthsToDevLoan()), "int", ""));
				loanRule.addDetail(devLoanRule);
			}

			tree.addRule(loanRule);
		}

		{
			Rule feeRule = new Rule(DataUtil.EMPTY_STRING, "Fee - Penalty", "", "None");

			feeRule.addDetail(new Rule(cmRegistrationFee, "Registration Fee", DataUtil.toString(gRules.getCmRegistrationFee()), "int"));
			feeRule.addDetail(new Rule(cmSavingLateFee, "Late Fee on Saving", DataUtil.toString(gRules.getCmSavingLateFee()), "int"));
			feeRule.addDetail(new Rule(cmLoanLateFee, "Late Fee on Loan", DataUtil.toString(gRules.getCmLoanLateFee()), "int"));
			feeRule.addDetail(new Rule(cmApplicationFee, "Application Fee", DataUtil.toString(gRules.getCmApplicationFee()), "int"));
			feeRule.addDetail(new Rule(chequeBouncingPenalty, "Cheque Bouncing Penalty", DataUtil.toString(gRules.getChequeBouncingPenalty()), "int"));
			//				feeRule.addDetail(new Rule("autoDebitFailurePenalty", "Auto Debit Failure Penalty", DataUtil.toString(gRules.getAutoDebitFailurePenalty()), "int"));

			tree.addRule(feeRule);
		}

		if(gRules.getAllowAssociateMembers() <= DataUtil.ZERO_INTEGER) {
			tree.setAssociatedMAllowed(false);

		}
		else {
			tree.setAssociatedMAllowed(true);

			{
				Rule assoRule = new Rule(DataUtil.EMPTY_STRING, "Associate Member Specific", "", "None");

				{
					Rule savingRule = new Rule(DataUtil.EMPTY_STRING, "Saving Rules", "", "None");

					savingRule.addDetail(new Rule(amMinMonthlySaving, "Min Monthly Saving", DataUtil.toString(gRules.getAmMinMonthlySaving()), "int"));
					savingRule.addDetail(new Rule(amMaxMonthlySaving, "Max Monthly Saving", DataUtil.toString(gRules.getAmMaxMonthlySaving()), "int"));
					savingRule.addDetail(new Rule(amIntOnSaving, "Interest on Saving", DataUtil.toString(gRules.getAmIntOnSaving()), "float", ""));

					assoRule.addDetail(savingRule);
				}

				{
					Rule loanRule = new Rule(DataUtil.EMPTY_STRING, "Loan Rules", "", "None");

					loanRule.addDetail(new Rule(amLoanProcessingFeePercent, "Loan Processing Fee", DataUtil.toString(gRules.getAmLoanProcessingFeePercent()), "float", "1 = 1% fee of Loan"));
					loanRule.addDetail(new Rule(amBaseIntOnLoan, "Min Interest Rate", DataUtil.toString(gRules.getAmBaseIntOnLoan()), "float"));
					loanRule.addDetail(new Rule(amMinMonthsIntOnLoan, "Min Months of Interest", DataUtil.toString(gRules.getAmMinMonthsIntOnLoan()), "int"));

					{
						Rule conLoanRule = new Rule(DataUtil.EMPTY_STRING, "Consumption Loan", "", "None");							
						conLoanRule.addDetail(new Rule(amSavingXConsumptionLoan, "Max Loan Limit in Multiple of Saving", DataUtil.toString(gRules.getAmSavingXConsumptionLoan()), "float", ""));
						conLoanRule.addDetail(new Rule(amMinMonthsToConLoan, "Min No of Months Saving", DataUtil.toString(gRules.getAmMinMonthsToConLoan()), "int", ""));
						conLoanRule.addDetail(new Rule(amLimitOnConsumptionLoan, "Max Loan Limit", DataUtil.toString(gRules.getAmLimitOnConsumptionLoan()), "int", ""));
						loanRule.addDetail(conLoanRule);
					}

					{
						Rule devLoanRule = new Rule(DataUtil.EMPTY_STRING, "Development Loan", "", "None");							
						devLoanRule.addDetail(new Rule(amSavingXDevLoan, "Max Loan Limit in Multiple of Saving", DataUtil.toString(gRules.getAmSavingXDevLoan()), "float", ""));
						devLoanRule.addDetail(new Rule(amMinMonthsToDevLoan, "Min No of Months Saving", DataUtil.toString(gRules.getAmMinMonthsToDevLoan()), "int", ""));
						loanRule.addDetail(devLoanRule);
					}

					assoRule.addDetail(loanRule);
				}

				{
					Rule feeRule = new Rule(DataUtil.EMPTY_STRING, "Fee - Penalty", "", "None");

					feeRule.addDetail(new Rule(amRegistrationFee, "Registration Fee", DataUtil.toString(gRules.getAmRegistrationFee()), "int"));
					feeRule.addDetail(new Rule(amSavingLateFee, "Late Fee on Saving", DataUtil.toString(gRules.getAmSavingLateFee()), "int"));
					feeRule.addDetail(new Rule(amLoanLateFee, "Late Fee on Loan", DataUtil.toString(gRules.getAmLoanLateFee()), "int"));
					feeRule.addDetail(new Rule(amApplicationFee, "Application Fee", DataUtil.toString(gRules.getAmApplicationFee()), "int"));

					assoRule.addDetail(feeRule);
				}

				tree.addRule(assoRule);
			}
			boolean associatedRulesDiff = (gRules.getCmIntOnSaving() != gRules.getAmIntOnSaving() ||
					gRules.getCmLoanProcessingFeePercent() != gRules.getAmLoanProcessingFeePercent() ||
					gRules.getCmBaseIntOnLoan() != gRules.getAmBaseIntOnLoan() ||
					gRules.getCmMinMonthsIntOnLoan() != gRules.getAmMinMonthsIntOnLoan() ||
					gRules.getCmSavingXConsumptionLoan() != gRules.getAmSavingXConsumptionLoan() ||
					gRules.getCmMinMonthsToConLoan() != gRules.getAmMinMonthsToConLoan() ||
					gRules.getCmLimitOnConsumptionLoan() != gRules.getAmLimitOnConsumptionLoan() ||
					gRules.getCmSavingXDevLoan() != gRules.getAmSavingXDevLoan() ||
					gRules.getCmMinMonthsToDevLoan() != gRules.getAmMinMonthsToDevLoan() ||
					gRules.getCmRegistrationFee() != gRules.getAmRegistrationFee() ||
					gRules.getCmSavingLateFee() != gRules.getAmSavingLateFee() ||
					gRules.getCmLoanLateFee() != gRules.getAmLoanLateFee() ||
					gRules.getCmApplicationFee() != gRules.getAmApplicationFee());
			tree.setAssociatedRulesDiff(associatedRulesDiff);
		}
		return tree;
	}

	public GRulesTree updateGRulesTree(GRulesTree tree) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(tree.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Ac No");
		}
		GRules gRules = daoFactory.getGRulesDAO().findById(tree.getGroupAcNo());		
		if(gRules == null) {
			throw new BadRequestException("Invalid Group Ac No");
		}

		if(tree.getRules() != null && !tree.getRules().isEmpty()) {
			for(Rule rule: tree.getRules()) {
				loadGRules(gRules, rule, tree);
			}
		}

		daoFactory.getGRulesDAO().merge(gRules);

		return tree;
	}

	protected void loadGRules(GRules gRules, Rule rule, GRulesTree tree) {

		switch (rule.getName()) {

		case allowAssociateMembers:
			gRules.setAllowAssociateMembers(Short.parseShort(rule.getValue()));
			if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
				tree.setAssociatedMAllowed(true);
			} else {
				tree.setAssociatedMAllowed(false);
			}
			break;

		case computeDayOfMonth:
			gRules.setComputeDayOfMonth(Short.parseShort(rule.getValue()));
			break;

		case dueDayOfMonth:
			gRules.setDueDayOfMonth(Short.parseShort(rule.getValue()));
			break;

		case activationFlags:
			gRules.setActivationFlags(Short.parseShort(rule.getValue()));
			break;

		case cmMonthlySaving:
			gRules.setCmMonthlySaving(Integer.parseInt(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthlySaving(Integer.parseInt(rule.getValue()));
				gRules.setAmMaxMonthlySaving(Integer.parseInt(rule.getValue()));
			}
			break;

		case cmIntOnSaving:
			gRules.setCmIntOnSaving(Float.parseFloat(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmIntOnSaving(Float.parseFloat(rule.getValue()));
			}
			break;

		case addIntToSavingAfterMonths:
			gRules.setAddIntToSavingAfterMonths(Short.parseShort(rule.getValue()));
			break;

		case cmLoanProcessingFeePercent:
			gRules.setCmLoanProcessingFeePercent(Float.parseFloat(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmLoanProcessingFeePercent(Float.parseFloat(rule.getValue()));
			}
			break;

		case cmBaseIntOnLoan:
			gRules.setCmBaseIntOnLoan(Float.parseFloat(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmBaseIntOnLoan(Float.parseFloat(rule.getValue()));
			}
			break;

		case cmMinMonthsIntOnLoan:
			gRules.setCmMinMonthsIntOnLoan(Integer.parseInt(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsIntOnLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case intOnConsumptionLoan:
			gRules.setIntOnConsumptionLoan(Float.parseFloat(rule.getValue()));
			break;

		case cmSavingXConsumptionLoan:
			gRules.setCmSavingXConsumptionLoan(Float.parseFloat(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingXConsumptionLoan(Float.parseFloat(rule.getValue()));
			}
			break;

		case cmMinMonthsToConLoan:
			gRules.setCmMinMonthsToConLoan(Integer.parseInt(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsToConLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case cmLimitOnConsumptionLoan:
			gRules.setCmLimitOnConsumptionLoan(Integer.parseInt(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmLimitOnConsumptionLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case cmSavingXDevLoan:
			gRules.setCmSavingXDevLoan(Float.parseFloat(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingXDevLoan(Float.parseFloat(rule.getValue()));
			}
			break;

		case cmMinMonthsToDevLoan:
			gRules.setCmMinMonthsToDevLoan(Integer.parseInt(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsToDevLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case cmRegistrationFee:
			gRules.setCmRegistrationFee(Short.parseShort(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmRegistrationFee(Short.parseShort(rule.getValue()));
			}
			break;

		case cmSavingLateFee:
			gRules.setCmSavingLateFee(Short.parseShort(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingLateFee(Short.parseShort(rule.getValue()));
			}
			break;

		case cmLoanLateFee:
			gRules.setCmLoanLateFee(Short.parseShort(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmLoanLateFee(Short.parseShort(rule.getValue()));
			}
			break;

		case cmApplicationFee:
			gRules.setCmApplicationFee(Short.parseShort(rule.getValue()));
			if(!tree.isAssociatedRulesDiff()) {
				gRules.setAmApplicationFee(Short.parseShort(rule.getValue()));
			}
			break;

		case chequeBouncingPenalty:
			gRules.setChequeBouncingPenalty(Short.parseShort(rule.getValue()));
			break;

		case autoDebitFailurePenalty:
			gRules.setAutoDebitFailurePenalty(Short.parseShort(rule.getValue()));
			break;

		case individualDevLoanRange1:
			gRules.setIndividualDevLoanRange1(Short.parseShort(rule.getValue()));
			break;

		case individualDevLoanRange2:
			gRules.setIndividualDevLoanRange2(Short.parseShort(rule.getValue()));
			break;

		case individualDevLoanRange3:
			gRules.setIndividualDevLoanRange3(Short.parseShort(rule.getValue()));
			break;

		case intOnIndividualDevLoanRange1:
			gRules.setIntOnIndividualDevLoanRange1(Float.parseFloat(rule.getValue()));
			break;

		case intOnIndividualDevLoanRange2:
			gRules.setIntOnIndividualDevLoanRange2(Float.parseFloat(rule.getValue()));
			break;

		case intOnIndividualDevLoanRange3:
			gRules.setIntOnIndividualDevLoanRange3(Float.parseFloat(rule.getValue()));
			break;

		case intOnIndividualDevLoanRange4:
			gRules.setIntOnIndividualDevLoanRange4(Float.parseFloat(rule.getValue()));
			break;

		case projectDevLoanRange1:
			gRules.setProjectDevLoanRange1(Integer.parseInt(rule.getValue()));
			break;

		case projectDevLoanRange2:
			gRules.setProjectDevLoanRange2(Integer.parseInt(rule.getValue()));
			break;

		case projectDevLoanRange3:
			gRules.setProjectDevLoanRange3(Integer.parseInt(rule.getValue()));
			break;

		case intOnProjectDevLoanRange1:
			gRules.setIntOnProjectDevLoanRange1(Float.parseFloat(rule.getValue()));
			break;

		case intOnProjectDevLoanRange2:
			gRules.setIntOnProjectDevLoanRange2(Float.parseFloat(rule.getValue()));
			break;

		case intOnProjectDevLoanRange3:
			gRules.setIntOnProjectDevLoanRange3(Float.parseFloat(rule.getValue()));
			break;

		case intOnProjectDevLoanRange4:
			gRules.setIntOnProjectDevLoanRange4(Float.parseFloat(rule.getValue()));
			break;

		case amMinMonthlySaving:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthlySaving(Integer.parseInt(rule.getValue()));
			}
			break;

		case amMaxMonthlySaving:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmMaxMonthlySaving(Integer.parseInt(rule.getValue()));
			}
			break;

		case amIntOnSaving:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmIntOnSaving(Float.parseFloat(rule.getValue()));
			}
			break;

		case amRegistrationFee:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmRegistrationFee(Short.parseShort(rule.getValue()));
			}
			break;

		case amSavingLateFee:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingLateFee(Short.parseShort(rule.getValue()));
			}
			break;

		case amLoanLateFee:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmLoanLateFee(Short.parseShort(rule.getValue()));
			}
			break;

		case amApplicationFee:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmApplicationFee(Short.parseShort(rule.getValue()));
			}
			break;

		case amLoanProcessingFeePercent:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmLoanProcessingFeePercent(Integer.parseInt(rule.getValue()));
			}
			break;

		case amBaseIntOnLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmBaseIntOnLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case amMinMonthsIntOnLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsIntOnLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case amSavingXConsumptionLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingXConsumptionLoan(Float.parseFloat(rule.getValue()));
			}
			break;

		case amMinMonthsToConLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsToConLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case amLimitOnConsumptionLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmLimitOnConsumptionLoan(Integer.parseInt(rule.getValue()));
			}
			break;

		case amSavingXDevLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmSavingXDevLoan(Float.parseFloat(rule.getValue()));
			}
			break;

		case amMinMonthsToDevLoan:
			if(tree.isAssociatedRulesDiff()) {
				gRules.setAmMinMonthsToDevLoan(Integer.parseInt(rule.getValue()));
			}
			break;    		
		}

		if(rule.getDetails() != null && !rule.getDetails().isEmpty()) {
			for(Rule child: rule.getDetails()) {
				loadGRules(gRules, child, tree);
			}
		}
	}
}
