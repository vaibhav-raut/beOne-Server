package com.beone.shg.net.persistence.util;

import java.math.BigDecimal;
import java.util.Date;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.InvtPlan;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMI;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMIPlan;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class GenAlgoUtil {

	public final static float INTEREST_MONTHLY_FACTOR = 0.00083333333333F;
	public final static float INTEREST_DAILY_FACTOR = 0.00002739726027F;

	public static boolean isTransactionIncomplete(Tx transaction) {
		
		// Check Transaction State
		if(transaction != null && EnumUtil.isTxStatusDone(EnumCache.getNameOfTxType(transaction.getTxStatusId()))) {
			
			// Check Payment Mode as Bank & Group Bank Ac null
			if(EnumUtil.isBankPaymentMode(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, transaction.getPaymentModeId())) &&
					transaction.getGroupBankAccount() <= 0) {

				return true;
			}
		}
		return false;
	}

	public static TxTodo getMTxTodo(Tx transaction) {		
		// Check Transaction State
		if(transaction != null && transaction.getTxTodo() != null) {
			return transaction.getTxTodo();
		}
		return null;
	}	
    
    public static float getMonthlyIntFactor(float interest) {
    	return (interest * INTEREST_MONTHLY_FACTOR);
    }
    
    public static float getDailyIntFactor(float interest) {
    	return (interest * INTEREST_DAILY_FACTOR);
    }

	public static BigDecimal roundUp(BigDecimal amount, int decimal) {
		
		if(amount == null) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		return amount.setScale(decimal, BigDecimal.ROUND_UP);
	}	

	public static BigDecimal roundHalfUp(BigDecimal amount, int decimal) {
		
		if(amount == null) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		return amount.setScale(decimal, BigDecimal.ROUND_HALF_UP);
	}	

	public static BigDecimal computeInterestOnSaving(BigDecimal saving, float intFactor) {
		
		if(saving == null) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		return new BigDecimal(saving.floatValue() * intFactor);
	}	

	public static BigDecimal computeInterestOnLoan(BigDecimal loan, float intFactor) {
		
		if(loan == null) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		return new BigDecimal(loan.floatValue() * intFactor);
	}	

	public static BigDecimal compOldIntOnSaving(BigDecimal monthlyInst, int nOfInst, float intFactor) {
		
		BigDecimal oldSaving = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal oldInterestEarned = DataUtil.ZERO_BIG_DECIMAL;
		
		for(int i = 0; i < nOfInst; i++) {
			oldSaving = BDUtil.add(oldSaving, monthlyInst);
			oldInterestEarned = BDUtil.add(oldInterestEarned, computeInterestOnSaving(oldSaving, intFactor));
		}
		
		return oldInterestEarned;
	}	

	public static BigDecimal compIntComponentOfEMI(BigDecimal loan, float intFactor) {
		
		if(loan == null) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		return new BigDecimal(loan.floatValue() * intFactor);
	}	
	
	// 'Principle' = prin
	// 'Rate per Month' = intF
	// 'No of Installment' = no
	// EMI = pri * int *( (1 + int)^no) /(((1 + int)^no) - 1)}	
	public static BigDecimal compLoanEMI(BigDecimal prin, float intFactor, int no) {
		
		if(prin == null || intFactor == 0.0F || no == 0) {
			return DataUtil.ZERO_BIG_DECIMAL;
		}
		
		double powEqu = Math.pow((1 + intFactor), no);	
		return new BigDecimal((prin.doubleValue() * intFactor * powEqu)/(powEqu - 1));
	}	

	public static LoanEMIPlan compLoanEMINormal(LoanEMIPlan loanEMIPlan) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());
		LoanEMI loanEMI = null;
		BigDecimal totalInterest = DataUtil.ZERO_BIG_DECIMAL;
		Date startDate = DateUtil.convertStringToDate(loanEMIPlan.getStartDate());
		Date dueDate = DateUtil.getDueDate(startDate, loanEMIPlan.getDueDay());

		// Compute Pre EMI for the Interest to the Day of First Installment
		loanEMIPlan.setPreEMI(compLoanPreEMI(loanEMIPlan, startDate));
		
		for(int noOfMonths = loanEMIPlan.getNoOfEMIs(); noOfMonths > 0; noOfMonths--) {
			
			loanEMI = new LoanEMI(DateUtil.convertDateToString(dueDate), noOfMonths, principle);
			loanEMI = compLoanEMI(loanEMI, intFactor);
			loanEMIPlan.addLoanEMI(loanEMI);
			
			dueDate = DateUtil.getNextMonthDate(dueDate);
			principle = BDUtil.sub(principle, loanEMI.getPrincipleEMI());
			totalInterest = BDUtil.add(totalInterest, loanEMI.getInterestEMI());
		}
		
		loanEMIPlan.setTotalInterest(BDUtil.add(totalInterest, loanEMIPlan.getPreEMI()));
		loanEMIPlan.setFixedEMI(loanEMIPlan.getLoanEMIs().get(0).getEmi());
		loanEMIPlan.setExpCompletionDate(loanEMIPlan.getLoanEMIs().get(loanEMIPlan.getLoanEMIs().size() - 1).getDueDate());
		
		return loanEMIPlan;
	}	

	public static LoanEMI compLoanEMI(LoanEMI loanEMI, float intFactor) {

		BigDecimal emi = compLoanEMI(loanEMI.getPrinciple(), intFactor, loanEMI.getMonthNo());
		emi = roundHalfUp(emi, 0);
		
		BigDecimal interestEMI = compIntComponentOfEMI(loanEMI.getPrinciple(), intFactor);
		interestEMI = roundHalfUp(interestEMI, 0);

		loanEMI.setEmi(emi);
		loanEMI.setInterestEMI(interestEMI);
		loanEMI.setPrincipleEMI(BDUtil.sub(emi, interestEMI));
		
		return loanEMI;
	}	

	public static LoanEMIPlan compLoanEMIFixed(LoanEMIPlan loanEMIPlan) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		BigDecimal fixedEMI = loanEMIPlan.getFixedEMI();
		BigDecimal totalInterest = DataUtil.ZERO_BIG_DECIMAL;
		Date startDate = DateUtil.convertStringToDate(loanEMIPlan.getStartDate());
		Date dueDate = DateUtil.getDueDate(startDate, loanEMIPlan.getDueDay());
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());

		// Compute Pre EMI for the Interest to the Day of First Installment
		loanEMIPlan.setPreEMI(compLoanPreEMI(loanEMIPlan, startDate));
		
		LoanEMI loanEMI = null;
		boolean lastEMI = false;
		
		for(int noOfMonths = 1; principle.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0; noOfMonths++) {
			
			// Set Fixed EMI to Principle when Principle is less that EMI
			if(principle.compareTo(fixedEMI) < 0) {
				lastEMI = true;
			}
			
			loanEMI = new LoanEMI(DateUtil.convertDateToString(dueDate), noOfMonths, principle, fixedEMI);
			loanEMI = compLoanEMIFixed(loanEMI, intFactor, lastEMI);
			loanEMIPlan.addLoanEMI(loanEMI);
			
			dueDate = DateUtil.getNextMonthDate(dueDate);
			principle = BDUtil.sub(principle, loanEMI.getPrincipleEMI());
			totalInterest = BDUtil.add(totalInterest, loanEMI.getInterestEMI());
		}
		
		loanEMIPlan.setTotalInterest(BDUtil.add(totalInterest, loanEMIPlan.getPreEMI()));
		loanEMIPlan.setTotalInterest(totalInterest);
		loanEMIPlan.setNoOfEMIs(loanEMIPlan.getLoanEMIs().size());
		loanEMIPlan.setExpCompletionDate(loanEMIPlan.getLoanEMIs().get(loanEMIPlan.getLoanEMIs().size() - 1).getDueDate());

		return loanEMIPlan;
	}	

	public static LoanEMI compLoanEMIFixed(LoanEMI loanEMI, float intFactor, boolean lastEMI) {

		BigDecimal interestEMI = compIntComponentOfEMI(loanEMI.getPrinciple(), intFactor);
		interestEMI = roundHalfUp(interestEMI, 0);
		loanEMI.setInterestEMI(interestEMI);
		
		if(lastEMI) {
			loanEMI.setEmi(BDUtil.add(loanEMI.getPrinciple(), loanEMI.getInterestEMI()));
		} 

		loanEMI.setPrincipleEMI(BDUtil.sub(loanEMI.getEmi(), interestEMI));

		return loanEMI;
	}	

	public static LoanEMIPlan compLoanEMIReducing(LoanEMIPlan loanEMIPlan) throws BadRequestException {
		BigDecimal principleEMI = DataUtil.ZERO_BIG_DECIMAL;
		
		if(loanEMIPlan.getNoOfEMIs() > 0) {
			principleEMI = BDUtil.divide(loanEMIPlan.getPrinciple(), new BigDecimal(loanEMIPlan.getNoOfEMIs()));
		} else if(loanEMIPlan.getFixedEMI() != null && loanEMIPlan.getFixedEMI().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			principleEMI = loanEMIPlan.getFixedEMI();
			loanEMIPlan.setNoOfEMIs(BDUtil.divide(loanEMIPlan.getPrinciple(), principleEMI).intValue() + 1);
		}
		
		if(principleEMI.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid No Of EMIs Or Loan Principle Amount");
		}
		
		return compLoanEMIReducing(loanEMIPlan, principleEMI);
	}
	
	public static LoanEMIPlan compLoanEMIReducing(LoanEMIPlan loanEMIPlan, BigDecimal principleEMI) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());
		LoanEMI loanEMI = null;
		BigDecimal totalInterest = DataUtil.ZERO_BIG_DECIMAL;
		Date startDate = DateUtil.convertStringToDate(loanEMIPlan.getStartDate());
		Date dueDate = DateUtil.getDueDate(startDate, loanEMIPlan.getDueDay());

		// Compute Pre EMI for the Interest to the Day of First Installment
		loanEMIPlan.setPreEMI(compLoanPreEMI(loanEMIPlan, startDate));
		boolean exit = false;
		
		for(int noOfMonths = 1; !exit && noOfMonths <= loanEMIPlan.getNoOfEMIs(); noOfMonths++) {

			if(principle.compareTo(principleEMI) <= 0) {
				exit = true;
				principleEMI = principle;
			}

			loanEMI = new LoanEMI(DateUtil.convertDateToString(dueDate), noOfMonths, principle);
			loanEMI.setPrincipleEMI(roundHalfUp(principleEMI, 0));
			loanEMI.setInterestEMI(roundHalfUp(compIntComponentOfEMI(loanEMI.getPrinciple(), intFactor), 0));
			loanEMI.setEmi(roundHalfUp(BDUtil.add(principleEMI, loanEMI.getInterestEMI()), 0));
			loanEMIPlan.addLoanEMI(loanEMI);

			dueDate = DateUtil.getNextMonthDate(dueDate);
			principle = BDUtil.sub(principle, principleEMI);
			totalInterest = BDUtil.add(totalInterest, loanEMI.getInterestEMI());

			if(principle.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
				exit = true;
			}		
		}
		
		loanEMIPlan.setTotalInterest(BDUtil.add(totalInterest, loanEMIPlan.getPreEMI()));
		loanEMIPlan.setFixedEMI(principleEMI);
		loanEMIPlan.setExpCompletionDate(loanEMIPlan.getLoanEMIs().get(loanEMIPlan.getLoanEMIs().size() - 1).getDueDate());
		
		return loanEMIPlan;
	}	

	public static LoanEMIPlan compLoanEMIReducingWithFixedPrinciple(LoanEMIPlan loanEMIPlan) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());
		LoanEMI loanEMI = null;
		BigDecimal totalInterest = DataUtil.ZERO_BIG_DECIMAL;
		Date startDate = DateUtil.convertStringToDate(loanEMIPlan.getStartDate());
		Date dueDate = DateUtil.getDueDate(startDate, loanEMIPlan.getDueDay());

		// Compute Pre EMI for the Interest to the Day of First Installment
		loanEMIPlan.setPreEMI(compLoanPreEMI(loanEMIPlan, startDate));
		BigDecimal principleEMI = loanEMIPlan.getFixedEMI();
		
		for(int noOfMonths = 1; noOfMonths <= loanEMIPlan.getNoOfEMIs(); noOfMonths++) {
			
			loanEMI = new LoanEMI(DateUtil.convertDateToString(dueDate), noOfMonths, principle);
			loanEMI.setPrincipleEMI(roundHalfUp(principleEMI, 0));
			loanEMI.setInterestEMI(roundHalfUp(compIntComponentOfEMI(loanEMI.getPrinciple(), intFactor), 0));
			loanEMI.setEmi(roundHalfUp(BDUtil.add(principleEMI, loanEMI.getInterestEMI()), 0));
			loanEMIPlan.addLoanEMI(loanEMI);
			
			dueDate = DateUtil.getNextMonthDate(dueDate);
			principle = BDUtil.sub(principle, loanEMI.getPrincipleEMI());
			totalInterest = BDUtil.add(totalInterest, loanEMI.getInterestEMI());
		}
		
		loanEMIPlan.setTotalInterest(BDUtil.add(totalInterest, loanEMIPlan.getPreEMI()));
		loanEMIPlan.setFixedEMI(principleEMI);
		loanEMIPlan.setExpCompletionDate(loanEMIPlan.getLoanEMIs().get(loanEMIPlan.getLoanEMIs().size() - 1).getDueDate());
		
		return loanEMIPlan;
	}	

	public static BigDecimal compLoanPreEMI(LoanEMIPlan loanEMIPlan, Date startDate) {

		// Compute Pre EMI for the Interest to the Day of First Installment
		float intFactorForDaysDif = getDailyIntFactor(loanEMIPlan.getRateOfInterest()) * 
				DateUtil.getNoOfDaysFromDueDate(startDate, loanEMIPlan.getDueDay());
		
		BigDecimal preEMI = compIntComponentOfEMI(loanEMIPlan.getPrinciple(), intFactorForDaysDif);
		
		preEMI = roundHalfUp(preEMI, 0);

		return preEMI;
	}	

	public static LoanEMIPlan compLoanEMILimited(LoanEMIPlan loanEMIPlan, int noToComp) {
		if(loanEMIPlan.getLoanCalculation().equals(EnumConst.LoanCalculation_Normal_EMI)) {
			return compLoanEMINormalLimited(loanEMIPlan, noToComp);			
		} else if(loanEMIPlan.getLoanCalculation().equals(EnumConst.LoanCalculation_Fixed_EMI)) {
			return compLoanEMIFixedLimited(loanEMIPlan, noToComp);
		}
		return loanEMIPlan;
	}
	
	public static LoanEMIPlan compLoanEMINormalLimited(LoanEMIPlan loanEMIPlan, int noToComp) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());
		LoanEMI loanEMI = null;
		
		for(int noOfMonths = loanEMIPlan.getNoOfEMIs(); noOfMonths > (loanEMIPlan.getNoOfEMIs() - noToComp); noOfMonths--) {
			
			loanEMI = new LoanEMI("", noOfMonths, principle);
			loanEMI = compLoanEMI(loanEMI, intFactor);
			loanEMIPlan.addLoanEMI(loanEMI);
			
			principle = BDUtil.sub(principle, loanEMI.getPrincipleEMI());
		}
		
		return loanEMIPlan;
	}	

	public static LoanEMIPlan compLoanEMIFixedLimited(LoanEMIPlan loanEMIPlan, int noToComp) {
		
		BigDecimal principle = loanEMIPlan.getPrinciple();
		BigDecimal fixedEMI = loanEMIPlan.getFixedEMI();
		float intFactor = getMonthlyIntFactor(loanEMIPlan.getRateOfInterest());
		
		LoanEMI loanEMI = null;
		boolean lastEMI = false;
		
		for(int noOfMonths = 0; noOfMonths < noToComp; noOfMonths++) {
			
			// Set Fixed EMI to Principle when Principle is less that EMI
			if(principle.compareTo(fixedEMI) < 0) {
				lastEMI = true;
			}
			
			loanEMI = new LoanEMI("", noOfMonths, principle, fixedEMI);
			loanEMI = compLoanEMIFixed(loanEMI, intFactor, lastEMI);
			loanEMIPlan.addLoanEMI(loanEMI);
			
			principle = BDUtil.sub(principle, loanEMI.getPrincipleEMI());
		}
		
		return loanEMIPlan;
	}	

	public static InvtPlan compInvestmentPlan(InvtPlan plan) {
		
		float intFactor = getMonthlyIntFactor(plan.getRateOfInterest());		
		plan.setProjInterestAm(roundHalfUp(BDUtil.multiply(BDUtil.multiply(plan.getInvtAm(), intFactor), plan.getNoOfMonths()), 0));
		
		Date maturityDate = DateUtil.getDateAfterMonths(DateUtil.convertStringToDate(plan.getRequestedDate()), plan.getNoOfMonths());
		plan.setMaturityDate(DateUtil.convertDateToString(maturityDate));
		
		return plan;
	}	
	
	public static LoanEMIPlan getLoanPlanning(LoanEMIPlan plan) throws BadRequestException {
		
		if(plan.getPrinciple() == null || plan.getPrinciple().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid Loan Principle Amount");
		}
		if(plan.getRateOfInterest() <= 0.0) {
			throw new BadRequestException("Invalid Loan Rate of Interest");
		}
		if(plan.getLoanCalculation() == null || plan.getLoanCalculation().isEmpty()) {
			throw new BadRequestException("Invalid Loan Computation Type");
		}
		
		if(plan.getLoanProcessingFeePercent() > DataUtil.ZERO_FLOAT) {
			plan.setLoanProcessingFee(GenAlgoUtil.roundHalfUp(plan.getPrinciple().multiply(new BigDecimal(plan.getLoanProcessingFeePercent()/100.0)), 0));
		} else {
			plan.setLoanProcessingFee(DataUtil.ZERO_BIG_DECIMAL);
		}

		try {
			// Compute Normal EMI Plan with fix Tenure
			if(plan.getLoanCalculation().equals(EnumConst.LoanCalculation_Normal_EMI)) {
				if(plan.getNoOfEMIs() <= 0 && plan.getNoOfEMIs() > 300) {
					throw new BadRequestException("Invalid No Of EMIs: " + plan.getNoOfEMIs());
				}

				plan = GenAlgoUtil.compLoanEMINormal(plan);			
			} 
			// Compute Fixed EMI (as per choice) Plan with variable Tenure
			else if(plan.getLoanCalculation().equals(EnumConst.LoanCalculation_Fixed_EMI)) {
				if(plan.getFixedEMI() == null || plan.getFixedEMI().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					throw new BadRequestException("Invalid Loan Fixed EMI Amount");
				}

				plan = GenAlgoUtil.compLoanEMIFixed(plan);

			} 
			// Compute Reducing EMI (as per choice) Plan with variable Tenure
			else if(plan.getLoanCalculation().equals(EnumConst.LoanCalculation_Reducing_Interest)) {
				
				if((plan.getFixedEMI() == null || plan.getFixedEMI().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) &&
						(plan.getNoOfEMIs() <= 0 && plan.getNoOfEMIs() > 300)) {
					throw new BadRequestException("Invalid No Of EMIs Or Loan Principle Amount");
				}

				plan = GenAlgoUtil.compLoanEMIReducing(plan);
			} 
			else {
				throw new BadRequestException("Invalid Loan Computation Type:" + plan.getLoanCalculation());
			}
		} catch (Exception e) {
			throw new BadRequestException("Invalid Loan Computation Parameters!");
		}
		
		return plan;
	}
	
	public static LoanData getLoanPlanningAggregate(LoanEMIPlan plan, int paidInstallments) throws BadRequestException {
		
		plan = GenAlgoUtil.getLoanPlanning(plan);

		BigDecimal recInterest = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal projInterest = DataUtil.ZERO_BIG_DECIMAL;
		
		for(int i = 0; i < plan.getLoanEMIs().size(); i++) {
			if(i < paidInstallments) {
				recInterest = BDUtil.add(recInterest, plan.getLoanEMIs().get(i).getInterestEMI());
			} else {
				projInterest = BDUtil.add(projInterest, plan.getLoanEMIs().get(i).getInterestEMI());
			}
		}
		LoanData data = new LoanData(recInterest, projInterest);
		return data;
	}
}
