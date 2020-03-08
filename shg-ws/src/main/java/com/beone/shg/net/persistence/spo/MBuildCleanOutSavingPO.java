package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.spo.MemberPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

public class MBuildCleanOutSavingPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MBuildCleanOutSavingPO.class);
	
	public MBuildCleanOutSavingPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);	
		
		GAc gAc = groupAcInfo.getDaoFactory().getGAcDAO().findById(this.groupAcInfo.getGroupAcNo());		
		gAc.setCMOutstandingSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMOutstandingSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		groupAcInfo.getDaoFactory().getGAcDAO().merge(gAc);
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			BigDecimal outstandingSaving = DataUtil.ZERO_BIG_DECIMAL;

			for(TxTodo tx: memberAcInfo.getTodoTransactions()) {
				if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Saving_Installment) &&
						EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

					outstandingSaving = BDUtil.add(outstandingSaving, tx.getAmount());
				}
			}
			
			for(TxTodo tx: memberAcInfo.getNextTodoTransactions()) {
				if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Saving_Installment) &&
						EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

					outstandingSaving = BDUtil.add(outstandingSaving, tx.getAmount());
				}
			}
			
			MAc mAc = memberAcInfo.getDaoFactory().getMAcDAO().findById(memberAcInfo.getAc().getMAcNo());
			mAc.setOutstandingSavedAm(outstandingSaving);
			memberAcInfo.getDaoFactory().getMAcDAO().merge(mAc);

			GAc gAc = memberAcInfo.getDaoFactory().getGAcDAO().findById(this.groupAcInfo.getGroupAcNo());

			String role = EnumCache.getNameOfMRole(memberAcInfo.getMprofile().getMroleId());
			if(EnumUtil.isCoreMember(role)) {
				gAc.setCMOutstandingSavedAm(BDUtil.add(gAc.getCMOutstandingSavedAm(), outstandingSaving));
			} else if(EnumUtil.isAssociateMember(role)) {
				gAc.setAMOutstandingSavedAm(BDUtil.add(gAc.getAMOutstandingSavedAm(), outstandingSaving));
			}

			memberAcInfo.getDaoFactory().getGAcDAO().merge(gAc);
			
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
