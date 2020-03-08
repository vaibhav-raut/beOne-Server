package com.beone.shg.net.persistence.spo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.TxTypeFormula;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;

public class AutoApproveTxPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(AutoApproveTxPO.class);

	public AutoApproveTxPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {
		try {

			for(Tx tran: memberAcInfo.getTransactions()) {

				// 1 - Check Transaction Done State
				if(tran != null && EnumUtil.isTxStatusDone(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tran.getTxStatusId()))) {

					// Load persistent transaction object
					Tx tx = memberAcInfo.getDaoFactory().getTxDAO().findById(tran.getTxId());

					TxTodo txTodo = GenAlgoUtil.getMTxTodo(tx);
					if(txTodo != null) {
						txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(txTodo.getTxTodoId());
					}

					boolean txRejected = false;

					// 2 - Check Transaction Incomplete State
					if(GenAlgoUtil.isTransactionIncomplete(tran)) {
						// Auto Reject Transaction 
						tx.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Rejected));

						if(txTodo != null) {
							// Auto Reject Todo Transaction 
							txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Auto_Rejected));
						}

						txRejected = true;

					} else {
						// Auto Approve Transaction 
						tx.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Approved));

						if(txTodo != null) {
							// Auto Reject Todo Transaction 
							txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Auto_Approved));
						}

					}

					// Update Member Todo Transaction 
					if(txTodo != null) {
						memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);
					}

					// Update Member Transaction 
					memberAcInfo.getDaoFactory().getTxDAO().merge(tx);
					
					TxTypeFormula formula = EnumCache.getTxTypeFormula(tx.getTxTypeId());
					if(txRejected) {
						// Transaction Post Processing Reject
						if(formula.getFormulaOnReject() != null) {
							memberAcInfo.getProcessJobBuilder().pushPostProcessJob(formula.getFormulaOnReject(), tx);
						}
					} else {
						// Transaction Post Processing Approve
						if(formula.getFormulaOnApprove() != null) {
							memberAcInfo.getProcessJobBuilder().pushPostProcessJob(formula.getFormulaOnApprove(), tx);
						}
					} 
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}

}
