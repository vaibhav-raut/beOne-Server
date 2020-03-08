package com.beone.shg.net.webservice.rest.model.resp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.util.DateUtil;

@JsonSerialize(include = Inclusion.NON_NULL)
public class AcBook {

	@JsonIgnore
	private Map<Long, List<AcRecord>> temp;
	
	private Map<String, List<AcRecord>> book;
	private List<String> dates;
	private Map<String, String> displayNames;

	public Map<String, List<AcRecord>> getBook() {
		return book;
	}
	public void setBook(Map<String, List<AcRecord>> book) {
		this.book = book;
	}
	public void put(Long day, AcRecord record) {
		if(this.temp == null) {
			this.temp = new LinkedHashMap<Long, List<AcRecord>>(40);
		}
		if(!this.temp.containsKey(day)) {
			this.temp.put(day, new LinkedList<AcRecord>());
		}
		
		this.temp.get(day).add(record);
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	public void reverse() {
		for(List<AcRecord> list: temp.values()) {
			Collections.reverse(list);
		}
		
		List<Long> days = new ArrayList<Long>();
		days.addAll(temp.keySet());
		Collections.sort(days);
		book = new LinkedHashMap<String, List<AcRecord>>(temp.size());
		dates = new ArrayList<String>(days.size());
		
		for(Long day: days) {
			String date = DateUtil.getDisplayDateStr(day);
			book.put(date, temp.get(day));
			dates.add(date);
		}
	}
}
