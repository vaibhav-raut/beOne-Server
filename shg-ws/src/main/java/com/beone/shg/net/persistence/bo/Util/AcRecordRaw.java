package com.beone.shg.net.persistence.bo.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;

public class AcRecordRaw {

	private Map<Long, Map<String, List<Transaction>>> bookRaw;

	public Map<Long, Map<String, List<Transaction>>> getBookRaw() {
		return bookRaw;
	}
	public void put(long time, String txType, Transaction transaction) {
		if(this.bookRaw == null) {
			this.bookRaw = new LinkedHashMap<Long, Map<String, List<Transaction>>>(40);
		}
		time = ((time / DateUtil.DAY) * DateUtil.DAY) + DateUtil.DAY - DateUtil.TIME_ZONE_ADJ;
		if(!this.bookRaw.containsKey(time)) {
			this.bookRaw.put(time, new LinkedHashMap<String, List<Transaction>>(40));
		}
		if(!this.bookRaw.get(time).containsKey(txType)) {
			this.bookRaw.get(time).put(txType, new ArrayList<Transaction>());
		}
		this.bookRaw.get(time).get(txType).add(transaction);
	}
}
