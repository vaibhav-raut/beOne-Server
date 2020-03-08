package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.spo.util.AlgoSPOUtil;
import com.beone.shg.net.persistence.util.DataUtil;

public class MCRComputPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MCRComputPO.class);
			
	public MCRComputPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}
	
	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			int installmentPaid = 0;
			int curInstallmentLate = 0;
			int curInstallmentMissed = 0;

			// Calculate for Saving Account
			for(MSavingAc ac:memberAcInfo.getAllSavingAcs()) {

				// Get all Installments from Account
				installmentPaid += ac.getNoOfInstPaid();
				curInstallmentLate += ac.getNoOfInsallLate();
				curInstallmentMissed += ac.getNoOfInsallMissed();
			}

			// Calculate for Loan Account
			for(MLoanAc ac:memberAcInfo.getAllLoanAcs()) {

				// Get all Installments from Account
				installmentPaid += ac.getNoOfInstPaid();
				curInstallmentLate += ac.getNoOfInsallLate();
				curInstallmentMissed += ac.getNoOfInsallMissed();
			}

			float newCreditRating = DataUtil.ZERO_FLOAT;
			
			if(installmentPaid > DataUtil.ZERO_INTEGER) {
				// Compute Credit Rating
				newCreditRating = AlgoSPOUtil.computeCreditRating(installmentPaid, curInstallmentMissed, curInstallmentLate);
			}

			MAc mac = memberAcInfo.getDaoFactory().getMAcDAO().findById(memberAcInfo.getAc().getMAcNo());
			memberAcInfo.getAc().setCreditRating(newCreditRating);
			mac.setCreditRating(newCreditRating);
			memberAcInfo.getDaoFactory().getMAcDAO().merge(mac);

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
