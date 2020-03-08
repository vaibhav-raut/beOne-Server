package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.EnumUtil;

public class MissedTodoTxPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MissedTodoTxPO.class);

	public MissedTodoTxPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {
		try {

			for(TxTodo tran: memberAcInfo.getTodoTransactions()) {

				// 1 - Check Transaction Done State
				if(tran != null && EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tran.getTxTodoStatusId()))) {

					// Load persistent transaction object
					TxTodo mtxTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tran.getTxTodoId());

					int statusId = EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Missed);
					mtxTodo.setTxTodoStatusId(statusId);
					tran.setTxTodoStatusId(statusId);

					// Save updates to DB
					memberAcInfo.getDaoFactory().getTxTodoDAO().merge(mtxTodo);
					
					String txType = EnumCache.getNameOfTxType(mtxTodo.getTxTypeId());
					if(txType.equals(EnumConst.TxType_Saving_Installment)) {
						
						MSavingAc ac = memberAcInfo.getDaoFactory().getMSavingAcDAO().findById(mtxTodo.getMemberSavingAcNo());
						ac.setNoOfInsallMissed(ac.getNoOfInsallMissed() + 1);
						memberAcInfo.getDaoFactory().getMSavingAcDAO().merge(ac);
					} 
					else if(txType.equals(EnumConst.TxType_Loan_Installment)) {
						
						MLoanAc ac = memberAcInfo.getDaoFactory().getMLoanAcDAO().findById(mtxTodo.getMemberLoanAcNo());
						ac.setNoOfInsallMissed(ac.getNoOfInsallMissed() + 1);
						memberAcInfo.getDaoFactory().getMLoanAcDAO().merge(ac);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
