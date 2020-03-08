package com.beone.shg.net.persistence.job;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.beone.shg.net.persistence.model.MMessage;
import com.beone.shg.net.persistence.mpo.IMProcessing;
import com.beone.shg.net.persistence.mpo.util.MPOFactory;
import com.beone.shg.net.persistence.mpo.util.MPOFormula;
import com.beone.shg.net.persistence.mpo.util.MPOFormulaUtil;
import com.beone.shg.net.persistence.mpo.util.SMSSendingUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeValue;

@SuppressWarnings("rawtypes")
public class MessageJob implements Runnable {

	protected DAOFactory daoFactory;
	protected MPOFactory mpoFactory;
	protected MPOFormula formula;
	protected String message;
	protected long smsKey;
	protected long mailKey;
	protected String smsLang;
	protected String mailLang;
	protected Map<Class, Object> relatedObject;
	protected long memberAcNo = 0;
	protected long groupAcNo = 0;
	
	public MessageJob(DAOFactory daoFactory, MPOFactory mpoFactory, String message, long smsKey, long mailKey, String smsLang, String mailLang) {
		super();
		this.daoFactory = daoFactory;
		this.mpoFactory = mpoFactory;
		this.message = message;
		this.smsKey = smsKey;
		this.mailKey = mailKey;
		this.smsLang = smsLang;
		this.mailLang = mailLang;
	}

	@Override
	public void run() {		
		
		List<String> mobileNos = new ArrayList<String>();
		if(memberAcNo > 0) {
			MMessage mMessage = daoFactory.getMMessageDAO().findById(memberAcNo);
			if(mMessage == null) {
				return;
			}
			if(ConversionUtil.isValidMobileNo(mMessage.getMobileNo()) 
					/*&& mMessage.getMobileVerified() == 1*/) {
				mobileNos.add(DataUtil.toString(mMessage.getMobileNo()));
			}

		} else if(groupAcNo > 0) {
			List<MMessage> mMessages = daoFactory.getMMessageDAO().getActiveMMessageListByGroup(groupAcNo);

			for(MMessage mMessage: mMessages) {
				if(ConversionUtil.isValidMobileNo(mMessage.getMobileNo()) 
						/*&& mMessage.getMobileVerified() == 1*/) {
					mobileNos.add(DataUtil.toString(mMessage.getMobileNo()));
				}
			}
		}
		
		if(mobileNos.isEmpty()) {
			return;
		}

		MessageTypeValue messageType = EnumCache.getMessageTypeValue(message, smsLang);
		if(messageType == null) {
			return;
		}
		formula = MPOFormulaUtil.parseMessageFormula(messageType.getSmsFormat());
		
		Map<String,String> valueMap = new LinkedHashMap<String,String>();
		
		for(String tableName: formula.getTableNames()) {
			IMProcessing mpo = mpoFactory.getMPO(tableName);
			if(mpo != null) {
				mpo.processMessageFormula(this, formula.getTableFormula(tableName), smsLang);
			}
			
			for(Entry<String, String> entry: formula.getTableFormula(tableName).entrySet()) {
				valueMap.put(MPOFormulaUtil.FORMULA_START + tableName + MPOFormulaUtil.FORMULA_SUB + entry.getKey() + MPOFormulaUtil.FORMULA_END, 
						entry.getValue());
			}
		}
		
		StringBuilder sb = new StringBuilder(messageType.getSmsFormat());
		for(Entry<String, String> entry: valueMap.entrySet()) {
			int index = sb.indexOf(entry.getKey());
			sb.replace(index, index + entry.getKey().length(), entry.getValue());
		}
		
		List<Integer> messageIds = SMSSendingUtil.sendTxSMS(mobileNos, sb.toString());
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<Class, Object> getRelatedObject() {
		return relatedObject;
	}

	public void setRelatedObject(Map<Class, Object> relatedObject) {
		this.relatedObject = relatedObject;
	}

	public long getMemberAcNo() {
		return memberAcNo;
	}

	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}

	public long getGroupAcNo() {
		return groupAcNo;
	}

	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
}
