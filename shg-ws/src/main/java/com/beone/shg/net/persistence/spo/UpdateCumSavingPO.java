package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.util.DataUtil;

public class UpdateCumSavingPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(UpdateCumSavingPO.class);

	public UpdateCumSavingPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			for(MSavingAc ac:memberAcInfo.getSavingAcs()) {

				BigDecimal newCumSaving = DataUtil.ZERO_BIG_DECIMAL;

				if(ac.getCumulativeSavedAm() != null) {
					newCumSaving = ac.getCumulativeSavedAm();
				}

				MSavingAc mSavingAc = memberAcInfo.getDaoFactory().getMSavingAcDAO().findById(ac.getMSavingAcNo());

				mSavingAc.setCumulativeSavedAm(newCumSaving.add(ac.getCurrentFyIntEnAm()));
				mSavingAc.setCurrentFyIntEnAm(DataUtil.ZERO_BIG_DECIMAL);

				memberAcInfo.getDaoFactory().getMSavingAcDAO().merge(mSavingAc);
			}
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
