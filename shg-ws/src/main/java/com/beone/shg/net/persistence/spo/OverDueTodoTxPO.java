package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

public class OverDueTodoTxPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(OverDueTodoTxPO.class);

	public OverDueTodoTxPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {
		try {

			if(DateUtil.getDayOfMonth(System.currentTimeMillis()) > groupAcInfo.getGRules().getDueDayOfMonth()) {
				for(TxTodo tran: memberAcInfo.getTodoTransactions()) {

					// 1 - Check Transaction Done State
					if(tran != null && EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tran.getTxTodoStatusId()))) {

						// Load persistent transaction object
						TxTodo mtxTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tran.getTxTodoId());
						
						int statusId = EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Over_Due);
						mtxTodo.setTxTodoStatusId(statusId);
						tran.setTxTodoStatusId(statusId);
						
						// Save updates to DB
						memberAcInfo.getDaoFactory().getTxTodoDAO().merge(mtxTodo);
					}
				}
			}			
		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}
}
