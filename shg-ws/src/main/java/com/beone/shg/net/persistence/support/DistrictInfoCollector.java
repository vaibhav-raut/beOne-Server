package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.rest.GroupName;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class DistrictInfoCollector {

	private int districtId;
	private String groupNameShort;
	private String groupType;
	private long reqMemberAcNos;
	private Set<Long> groupAcNos;
	private Map<Long, String> groupAcToNames;
	private Map<Long, GroupName> groupAcToGroupNames;
	private Map<String, Long> namesToGroupAc;

	public DistrictInfoCollector() {
		groupAcNos = new HashSet<Long>();
		groupAcToNames = new LinkedHashMap<Long, String>();
		groupAcToGroupNames = new LinkedHashMap<Long, GroupName>();
		namesToGroupAc = new LinkedHashMap<String, Long>();
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getGroupNameShort() {
		return groupNameShort;
	}

	public void setGroupNameShort(String groupNameShort) {
		this.groupNameShort = groupNameShort;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public long getReqMemberAcNos() {
		return reqMemberAcNos;
	}

	public void setReqMemberAcNos(long reqMemberAcNos) {
		this.reqMemberAcNos = reqMemberAcNos;
	}

	public Set<Long> getGroupAcNos() {
		return groupAcNos;
	}

	public String getGroupAcNoString() {
		return ConversionUtil.convertArrayToInString(this.groupAcNos.toArray());
	}

	public void addGroupAcNo(Long groupAcNo) {
		this.groupAcNos.add(groupAcNo);
	}

	public String getGroupName(Long groupAcNo) {
		return groupAcToNames.get(groupAcNo);
	}

	public long getGroupAc(String name) {
		return namesToGroupAc.get(name);
	}

	public boolean isGroupAcPresent(Long groupAcNo) {
		return groupAcToNames.containsKey(groupAcNo);
	}

	public boolean isNamePresent(String name) {
		return namesToGroupAc.containsKey(name);
	}

	public void putGroupName(Long groupAcNo, GroupName name) {
		this.groupAcNos.add(groupAcNo);
		this.groupAcToNames.put(groupAcNo, name.getGroupName());
		this.namesToGroupAc.put(name.getGroupName(), groupAcNo);
		this.groupAcToGroupNames.put(groupAcNo, name);
	}

	public List<GroupName> searchGroupName(String nameshort) throws BadRequestException {		
		return searchGroupName(nameshort, 0L);
	}

	public List<GroupName> getAllGroupName() {
		List<GroupName> list = new ArrayList<GroupName>();
		for(Long groupAcNo: groupAcToNames.keySet()) {
			list.add(groupAcToGroupNames.get(groupAcNo));
		}
		return list;
	}

	public List<GroupName> getAllGroupName(long districtId, String districtCode) {
		List<GroupName> list = new ArrayList<GroupName>();
		for(Long groupAcNo: groupAcToNames.keySet()) {
			groupAcToGroupNames.get(groupAcNo).setDistrictCode(districtCode);
			list.add(groupAcToGroupNames.get(groupAcNo));
		}
		return list;
	}

	public List<GroupName> getAllVendorsName() {
		List<GroupName> list = new ArrayList<GroupName>();
		DistrictValue district = EnumCache.getDistrictValue(EnumConst.District_Code_Udaan);

		for(Long groupAcNo: groupAcToNames.keySet()) {
			groupAcToGroupNames.get(groupAcNo).setDistrictCode(district.getDistrictCode());
			list.add(groupAcToGroupNames.get(groupAcNo));
		}
		return list;
	}

	public List<GroupName> searchGroupName(String nameshort, long districtId) throws BadRequestException {
		List<GroupName> list = new ArrayList<GroupName>();
		long groupAcNo;
		for(String name: namesToGroupAc.keySet()) {
			if(StringUtils.containsIgnoreCase(name, nameshort)) {
				groupAcNo = namesToGroupAc.get(name);
				list.add(groupAcToGroupNames.get(groupAcNo));
			}
		}
		if(list.size() == 0) {
			throw new BadRequestException("Group with Name: " + nameshort + " not present in District");
		}
		return list;
	}

	public List<GroupName> searchGroupName(String nameshort, long districtId, String districtCode) throws BadRequestException {
		List<GroupName> list = new ArrayList<GroupName>();
		long groupAcNo;
		for(String name: namesToGroupAc.keySet()) {
			if(StringUtils.containsIgnoreCase(name, nameshort)) {
				groupAcNo = namesToGroupAc.get(name);
				groupAcToGroupNames.get(groupAcNo).setDistrictCode(districtCode);
				list.add(groupAcToGroupNames.get(groupAcNo));
			}
		}
		if(list.size() == 0) {
			throw new BadRequestException("Group with Name: " + nameshort + " not present in District");
		}
		return list;
	}
}
