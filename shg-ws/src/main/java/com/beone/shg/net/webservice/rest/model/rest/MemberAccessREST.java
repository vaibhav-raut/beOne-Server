package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberAccessREST {
	
	private long memberAcNo;
	private String mrole;
	private String status;
	private long groupAcNo;
	private String groupType;
	private String groupStatus;
	private List<MapRole> groupsMappings;
	private List<String> possibleRoles;
	
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public String getMrole() {
		return mrole;
	}
	public void setMrole(String mrole) {
		this.mrole = mrole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	public List<MapRole> getGroupsMappings() {
		return groupsMappings;
	}
	public void setGroupsMappings(List<MapRole> groupsMappings) {
		this.groupsMappings = groupsMappings;
	}
	public void addGroupsMapping(MapRole groupsMapping) {
		if(this.groupsMappings == null) {
			this.groupsMappings = new ArrayList<MapRole>();
		}
		this.groupsMappings.add(groupsMapping);
	}
	public List<String> getPossibleRoles() {
		return possibleRoles;
	}
	public void setPossibleRoles(List<String> possibleRoles) {
		this.possibleRoles = possibleRoles;
	}
	public void addPossibleRole(String possibleRole) {
		if(this.possibleRoles == null) {
			this.possibleRoles = new ArrayList<String>();
		}
		this.possibleRoles.add(possibleRole);
	}
	
	public static MRoleValue getMRoleValue(String groupType, long uiAccessRights, long wsAccessRights) {
		
		for(MRoleValue role : EnumCache.getMRole().getEnumValues()) {
			
			if(role.getBelongTo().equals(groupType) &&
					role.getDefaultUiAccessRights() == uiAccessRights &&
					role.getDefaultWsAccessRights() == wsAccessRights) {
				
				return role;
			}
		}	
		return null;
	}

	public static MRoleValue getMRoleValueDefault(String groupType) {
		
		for(MRoleValue role : EnumCache.getMRole().getEnumValues()) {			
			if(role.getBelongTo().equals(groupType)) {
				return role;
			}
		}	
		return null;
	}

	static public class MapRole {
		private long groupAcNo;
		private String mrole;
		private String groupType;
		private String groupStatus;
		public MapRole() {
			super();
		}
		public MapRole(long groupAcNo, String mrole, String groupType,
				String groupStatus) {
			super();
			this.groupAcNo = groupAcNo;
			this.mrole = mrole;
			this.groupType = groupType;
			this.groupStatus = groupStatus;
		}
		public long getGroupAcNo() {
			return groupAcNo;
		}
		public void setGroupAcNo(long groupAcNo) {
			this.groupAcNo = groupAcNo;
		}
		public String getMrole() {
			return mrole;
		}
		public void setMrole(String mrole) {
			this.mrole = mrole;
		}
		public String getGroupType() {
			return groupType;
		}
		public void setGroupType(String groupType) {
			this.groupType = groupType;
		}
		public String getGroupStatus() {
			return groupStatus;
		}
		public void setGroupStatus(String groupStatus) {
			this.groupStatus = groupStatus;
		}
	}
	public static boolean isPossibleRole(String reqMrole, String groupType, String updateMrole, GRules gRules) {
		return getPossibleRoles(reqMrole, groupType, gRules).contains(updateMrole);
	}
	
	public static List<String> getPossibleRoles(String mrole, String groupType, GRules gRules) {
		
		List<String> possibleRoles = new ArrayList<String>();
		
		switch(mrole) {
		
		case EnumConst.MRole_Group_Secretary:
		case EnumConst.MRole_Group_Treasure:
			if(groupType.equals(EnumConst.GroupType_SHG)) {
				possibleRoles.add(EnumConst.MRole_Associate_Member);
				possibleRoles.add(EnumConst.MRole_Core_Member);
			}
			break;

		case EnumConst.MRole_Group_President:
		case EnumConst.MRole_NGO_Agent:
		case EnumConst.MRole_NGO_Support_Admin:
			if(groupType.equals(EnumConst.GroupType_SHG)) {
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
			}
			break;

		case EnumConst.MRole_NGO_Admin:
			
			switch(groupType) {

			case EnumConst.GroupType_SHG: 
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
				break;

			case EnumConst.GroupType_NGO: 
				possibleRoles.add(EnumConst.MRole_NGO_Agent);
				possibleRoles.add(EnumConst.MRole_NGO_Support_Admin);
				possibleRoles.add(EnumConst.MRole_NGO_Admin);
				break;

			}
			break;

		case EnumConst.MRole_Area_Agent:
			if(groupType.equals(EnumConst.GroupType_SHG)) {
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
			} 
			break;

		case EnumConst.MRole_Area_Support_Admin:
		case EnumConst.MRole_Area_Admin:
		case EnumConst.MRole_Super_Area_Support_Admin:
			
			switch(groupType) {

			case EnumConst.GroupType_SHG:
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
				break;

//			case EnumConst.GroupType_Federation:
//				possibleRoles.add(EnumConst.MRole_Federation);
//				break;

			case EnumConst.GroupType_NGO:
				possibleRoles.add(EnumConst.MRole_NGO_Agent);
				possibleRoles.add(EnumConst.MRole_NGO_Support_Admin);
				possibleRoles.add(EnumConst.MRole_NGO_Admin);
				break;

			case EnumConst.GroupType_Bank:
				possibleRoles.add(EnumConst.MRole_Bank_Auditor);
				break;

//			case EnumConst.GroupType_GOV:
//				possibleRoles.add(EnumConst.MRole_GOV);
//				break;
				
			case EnumConst.GroupType_SHG_One_Agent:
				possibleRoles.add(EnumConst.MRole_Area_Agent);
				break;

			}
			break;

		case EnumConst.MRole_Super_Area_Admin:
		case EnumConst.MRole_SHG_One_Admin:
			
			switch(groupType) {

			case EnumConst.GroupType_SHG:
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
				break;

//			case EnumConst.GroupType_Federation:
//				possibleRoles.add(EnumConst.MRole_Federation);
//				break;

			case EnumConst.GroupType_NGO:
				possibleRoles.add(EnumConst.MRole_NGO_Agent);
				possibleRoles.add(EnumConst.MRole_NGO_Support_Admin);
				possibleRoles.add(EnumConst.MRole_NGO_Admin);
				break;

			case EnumConst.GroupType_Bank:
				possibleRoles.add(EnumConst.MRole_Bank_Auditor);
				break;

//			case EnumConst.GroupType_GOV:
//				possibleRoles.add(EnumConst.MRole_GOV);
//				break;

			case EnumConst.GroupType_SHG_One_Agent:
				possibleRoles.add(EnumConst.MRole_Area_Agent);
				break;

			case EnumConst.GroupType_Area_Admin:
				possibleRoles.add(EnumConst.MRole_Area_Support_Admin);
				possibleRoles.add(EnumConst.MRole_Area_Admin);
				break;

			case EnumConst.GroupType_Super_Area_Admin:
				possibleRoles.add(EnumConst.MRole_Super_Area_Support_Admin);
				possibleRoles.add(EnumConst.MRole_Super_Area_Admin);
				break;

			}
			break;

		case EnumConst.MRole_Super_Admin:
			
			switch(groupType) {

			case EnumConst.GroupType_SHG:
				if(gRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
					possibleRoles.add(EnumConst.MRole_Associate_Member);
				}
				possibleRoles.add(EnumConst.MRole_Core_Member);
				possibleRoles.add(EnumConst.MRole_Group_Secretary);
				possibleRoles.add(EnumConst.MRole_Group_Treasure);
				possibleRoles.add(EnumConst.MRole_Group_President);
				break;

//			case EnumConst.GroupType_Federation:
//				possibleRoles.add(EnumConst.MRole_Federation);
//				break;

			case EnumConst.GroupType_NGO:
				possibleRoles.add(EnumConst.MRole_NGO_Agent);
				possibleRoles.add(EnumConst.MRole_NGO_Support_Admin);
				possibleRoles.add(EnumConst.MRole_NGO_Admin);
				break;

			case EnumConst.GroupType_Bank:
				possibleRoles.add(EnumConst.MRole_Bank_Auditor);
				break;

//			case EnumConst.GroupType_GOV:
//				possibleRoles.add(EnumConst.MRole_GOV);
//				break;

			case EnumConst.GroupType_SHG_One_Agent:
				possibleRoles.add(EnumConst.MRole_Area_Agent);
				break;

			case EnumConst.GroupType_Area_Admin:
				possibleRoles.add(EnumConst.MRole_Area_Support_Admin);
				possibleRoles.add(EnumConst.MRole_Area_Admin);
				break;

			case EnumConst.GroupType_Super_Area_Admin:
				possibleRoles.add(EnumConst.MRole_Super_Area_Support_Admin);
				possibleRoles.add(EnumConst.MRole_Super_Area_Admin);
				break;

			case EnumConst.GroupType_SHG_One_Admin:
				possibleRoles.add(EnumConst.MRole_SHG_One_Admin);
				possibleRoles.add(EnumConst.MRole_Super_Admin);
				break;

			}
			break;
		
		}
		
		return possibleRoles;
	}
}
