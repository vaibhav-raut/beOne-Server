package com.beone.shg.net.persistence.ppo.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.ReportBO;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.job.ScheduleProcessJob;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.Lang;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.mpo.model.TxBc;
import com.beone.shg.net.persistence.mpo.model.TxBpii;
import com.beone.shg.net.persistence.mpo.model.TxIc;
import com.beone.shg.net.persistence.mpo.model.TxLf;
import com.beone.shg.net.persistence.mpo.model.TxLfOut;
import com.beone.shg.net.persistence.mpo.model.TxLpf;
import com.beone.shg.net.persistence.mpo.model.TxPii;
import com.beone.shg.net.persistence.mpo.model.TxRf;
import com.beone.shg.net.persistence.mpo.util.MPOFactory;
import com.beone.shg.net.persistence.mpo.util.TxMessageUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DAOFactory;


@Component("processJobBuilder")
public class ProcessJobBuilder {

	@Autowired
	@Qualifier("daoFactory")
	protected DAOFactory daoFactory;

	@Autowired
	@Qualifier("reportBO")
	protected ReportBO reportBO;

	@Autowired
	@Qualifier("ppoFactory")
	protected PPOFactory ppoFactory;

	@Autowired
	@Qualifier("mpoFactory")
	protected MPOFactory mpoFactory;

	
	public void pushPostProcessJob(PPOFormula formula, Tx transaction) {
		
		PostProcessJob job = new PostProcessJob(daoFactory, ppoFactory, formula);
		
		job.setRelatedObject(getRelatedObjects(transaction, null));
		
		JobQueueManager.addToJobQueue(transaction.getGroupAcNo(), job);	
	}
	
	public ScheduleProcessJob buildScheduleProcessJob(long groupAcNo, long startTime, long endTime, List<String> poConstList) {
		
		return new ScheduleProcessJob(daoFactory, reportBO, this, groupAcNo, startTime, endTime, poConstList);
	}
	
	public void pushMessageJob(Tx tx) {
		
		if(tx == null) {
			return;
		}

		String message = TxMessageUtil.getMessageForTxType(EnumCache.getNameOfTxType(tx.getTxTypeId()), 
				EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId()));

		if(message != null && !message.isEmpty()) {
			GRules gRules = daoFactory.getGRulesDAO().findById(tx.getGroupAcNo());
			Lang smsLang = daoFactory.getLangDAO().findById(gRules.getSmsServiceLang());
			Lang mailLang = daoFactory.getLangDAO().findById(gRules.getMailServiceLang());

			MessageJob job = new MessageJob(daoFactory, mpoFactory, message, gRules.getSmsSubKey(), gRules.getMailSubKey(), smsLang.getLang(), mailLang.getLang());

			job.setRelatedObject(getRelatedObjects(tx, null));
			job.setMemberAcNo(tx.getMemberAcNo());

			JobQueueManager.addToJobQueue(tx.getGroupAcNo(), job);
		}
	}
	
	public void pushMessageJob(List<Tx> txs) {
		
		if(txs == null || txs.isEmpty()) {
			return;
		}
		if(txs.size() == 1) {
			pushMessageJob(txs.get(0));
		}
		
		String message = TxMessageUtil.getMessageForTxs(txs);
		
		if(message == null) {
			for(Tx tx: txs) {
				pushMessageJob(tx);
			}
		}
		else {
			Tx tx = txs.get(0);
			Map<Class, Object> relatedObject = null;
			
			for(Tx t: txs) {
				switch(EnumCache.getNameOfTxType(t.getTxTypeId())) {

				case EnumConst.TxType_Loan_Interest_Installment:
					relatedObject = getRelatedTxIc(t, relatedObject);
					break;

				case EnumConst.TxType_Late_Fee:
					relatedObject = getRelatedTxLf(t, relatedObject);
					break;

				case EnumConst.TxType_Outstanding_Late_Fee:
					relatedObject = getRelatedTxLfOut(t, relatedObject);
					break;

				case EnumConst.TxType_Loan_Processing_Fee:
					relatedObject = getRelatedTxLpf(t, relatedObject);
					break;

				case EnumConst.TxType_Pre_Interest_Installment:
					relatedObject = getRelatedTxPii(t, relatedObject);
					break;

				case EnumConst.TxType_Registration_Fee:
					relatedObject = getRelatedTxRf(t, relatedObject);
					break;

				case EnumConst.TxType_Bank_Charges_Expense:
					relatedObject = getRelatedTxBc(t, relatedObject);
					break;

				case EnumConst.TxType_Bank_Pre_Interest_Installment:
					relatedObject = getRelatedTxBpii(t, relatedObject);
					break;

				default:
					relatedObject = getRelatedObjects(t, relatedObject);
					break;
				}
			}
			
			GRules gRules = daoFactory.getGRulesDAO().findById(tx.getGroupAcNo());
			Lang smsLang = daoFactory.getLangDAO().findById(gRules.getSmsServiceLang());
			Lang mailLang = daoFactory.getLangDAO().findById(gRules.getMailServiceLang());

			MessageJob job = new MessageJob(daoFactory, mpoFactory, message, gRules.getSmsSubKey(), gRules.getMailSubKey(), smsLang.getLang(), mailLang.getLang());

			job.setRelatedObject(relatedObject);
			job.setMemberAcNo(tx.getMemberAcNo());

			JobQueueManager.addToJobQueue(tx.getGroupAcNo(), job);
		}
	}
	
	public void pushMessageJob(GProfile gProfile, String message) {
		
		GRules gRules = daoFactory.getGRulesDAO().findById(gProfile.getGAcNo());
		Lang smsLang = daoFactory.getLangDAO().findById(gRules.getSmsServiceLang());
		Lang mailLang = daoFactory.getLangDAO().findById(gRules.getMailServiceLang());

		MessageJob job = new MessageJob(daoFactory, mpoFactory, message, gRules.getSmsSubKey(), gRules.getMailSubKey(), smsLang.getLang(), mailLang.getLang());
		
		job.setRelatedObject(getRelatedObjects(gProfile, null));
		
		JobQueueManager.addToJobQueue(gProfile.getGAcNo(), job);
	}
	
	public void pushMessageJob(MProfile mProfile, String message) {
		
		long groupAcNo = ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo());
		GRules gRules = daoFactory.getGRulesDAO().findById(groupAcNo);
		Lang smsLang = daoFactory.getLangDAO().findById(gRules.getSmsServiceLang());
		Lang mailLang = daoFactory.getLangDAO().findById(gRules.getMailServiceLang());

		MessageJob job = new MessageJob(daoFactory, mpoFactory, message, gRules.getSmsSubKey(), gRules.getMailSubKey(), smsLang.getLang(), mailLang.getLang());
		
		job.setRelatedObject(getRelatedObjects(mProfile, null));
		
		JobQueueManager.addToJobQueue(groupAcNo, job);
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedObjects(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(Tx.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxBc(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxBc.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxBpii(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxBpii.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxIc(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxIc.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxLf(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxLf.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxLfOut(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxLfOut.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxLpf(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxLpf.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxPii(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxPii.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedTxRf(Tx transaction, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(TxRf.class, transaction);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedObjects(GProfile gProfile, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}

		relatedObject.put(GProfile.class, gProfile);
		
		return relatedObject;
	}
	
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> getRelatedObjects(MProfile mProfile, Map<Class, Object> relatedObject) {
		
		if(relatedObject == null) {
			relatedObject = new LinkedHashMap<Class, Object>();
		}
		
		relatedObject.put(MProfile.class, mProfile);
		
		return relatedObject;
	}
}
