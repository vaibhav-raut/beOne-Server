package com.beone.shg.net.persistence.support;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.beone.shg.net.persistence.util.ConversionUtil;

public class MemberNameCollector {

	private Set<Long> memberAcNos;
	private Map<Long, String> mamberNames;

	public MemberNameCollector() {
		memberAcNos = new HashSet<Long>();
		mamberNames = new LinkedHashMap<Long, String>();
	}

	public Set<Long> getMemberAcNos() {
		return memberAcNos;
	}

	public String getMemberAcNoString() {
		return ConversionUtil.convertArrayToInString(this.memberAcNos.toArray());
	}

	public void addMemberAcNo(Long memberAcNo) {
		this.memberAcNos.add(memberAcNo);
	}

	public Map<Long, String> getMamberNames() {
		return mamberNames;
	}

	public String getMamberName(Long memberAcNo) {
		return mamberNames.get(memberAcNo);
	}

	public void putMamberName(Long memberAcNo, String getMamberName) {
		this.mamberNames.put(memberAcNo, getMamberName);
	}

	
}
