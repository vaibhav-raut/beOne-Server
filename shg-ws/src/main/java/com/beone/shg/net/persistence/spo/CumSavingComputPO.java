package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.util.DataUtil;

public class CumSavingComputPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(CumSavingComputPO.class);

	public CumSavingComputPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			for(MSavingAc ac:memberAcInfo.getSavingAcs()) {

				if((ac.getCumulativeSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) &&
						(ac.getSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0)) {
					BigDecimal cumSaving = ac.getSavedAm().add(ac.getTotalIntEnAm().subtract(ac.getCurrentFyIntEnAm()));

					MSavingAc mSavingAc = memberAcInfo.getDaoFactory().getMSavingAcDAO().findById(ac.getMSavingAcNo());

					mSavingAc.setCumulativeSavedAm(cumSaving);

					memberAcInfo.getDaoFactory().getMSavingAcDAO().merge(mSavingAc);
				}

			}

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
