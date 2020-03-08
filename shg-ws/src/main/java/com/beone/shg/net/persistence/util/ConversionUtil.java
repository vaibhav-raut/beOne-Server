package com.beone.shg.net.persistence.util;

import java.math.BigDecimal;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.BankAccount;
import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.model.Contact;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class ConversionUtil {

	public static long getGroupAcFromMember(long memberAcNo) {
		return (memberAcNo / DataUtil.MEMBER_RANGE_FOR_GROUP);
	}

	public static long getAreaAdminGroupAcFromGroup(long groupAcNo) {
		return (((groupAcNo / DataUtil.GROUP_RANGE_FOR_DISTRICT) * DataUtil.GROUP_RANGE_FOR_DISTRICT) + 1);
	}

	public static long getMicroRetailerGroupAcFromGroup(long groupAcNo) {
		return (((groupAcNo / DataUtil.GROUP_RANGE_FOR_DISTRICT) * DataUtil.GROUP_RANGE_FOR_DISTRICT) + 5);
	}

	public static long getAreaAdminGroupAcFromDistrict(long districtId) {
		return ((districtId * DataUtil.GROUP_RANGE_FOR_DISTRICT) + 1);
	}

	public static long getDistrictFromGroupAc(long groupAcNo) {
		return (groupAcNo / DataUtil.GROUP_RANGE_FOR_DISTRICT);
	}

	public static long getDistrictFromMemberAc(long memberAcNo) {
		return (memberAcNo / DataUtil.MEMBER_RANGE_FOR_DISTRICT);
	}

	public static long getStartRangeMemberAc(long groupAcNo) {
		return (groupAcNo * DataUtil.MEMBER_RANGE_FOR_GROUP);
	}

	public static long getEndRangeMemberAc(long groupAcNo) {
		return ((groupAcNo + 1) * DataUtil.MEMBER_RANGE_FOR_GROUP);
	}

	public static long getStartRangeGroupAc(int districtId) {
		return (districtId * DataUtil.GROUP_RANGE_FOR_DISTRICT);
	}

	public static long getEndRangeGroupAc(int districtId) {
		return ((districtId + 1) * DataUtil.GROUP_RANGE_FOR_DISTRICT);
	}

	public static long getStartRangeGroupAc(int districtId, long groupIndex) {
		return ((districtId * DataUtil.GROUP_RANGE_FOR_DISTRICT) + groupIndex);
	}

	public static long getEndRangeGroupAc(int districtId, long groupIndex, int range) {
		return ((districtId * DataUtil.GROUP_RANGE_FOR_DISTRICT) + groupIndex + range);
	}

	public static long getMemberAc(long districtId, long groupNo, long memberNo) {
		return ((districtId * DataUtil.MEMBER_RANGE_FOR_DISTRICT) + (groupNo * DataUtil.MEMBER_RANGE_FOR_GROUP) + memberNo);
	}

	public static long getMemberAcNo(long groupAcNo, long memberNo) {
		return ((groupAcNo * DataUtil.MEMBER_RANGE_FOR_GROUP) + memberNo);
	}

	public static long getGroupAc(long districtId, long groupNo) {
		return ((districtId * DataUtil.GROUP_RANGE_FOR_DISTRICT) + groupNo);
	}

	public static boolean isValidMemberAcNo(long memberAcNo) {

		// Check District
		long districtId = getDistrictFromMemberAc(memberAcNo); 	
		if(districtId <= 0l || districtId > DataUtil.MAX_DISTRICT)
			return false;

		long groupNo = (getGroupAcFromMember(memberAcNo) % DataUtil.GROUP_RANGE_FOR_DISTRICT);
		if(groupNo <= 0l || groupNo > DataUtil.MAX_GROUP_PER_DISTRICT)
			return false;

		long memberNo = (memberAcNo % DataUtil.MEMBER_RANGE_FOR_GROUP);
		if(memberNo <= 0l || memberNo > DataUtil.MAX_MEMBERS_PER_GROUP)
			return false;

		return true;
	}

	public static boolean isValidGroupAcNo(long groupAcNo) {

		// Check District
		long districtId = getDistrictFromGroupAc(groupAcNo); 	
		if(districtId <= 0l || districtId > DataUtil.MAX_DISTRICT)
			return false;

		long groupNo = (groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT);
		if(groupNo <= 0l || groupNo > DataUtil.MAX_GROUP_PER_DISTRICT)
			return false;

		return true;
	}

	public static boolean isValidMobileNo(long mobileNo) {

		if(mobileNo <= 1000000000l || mobileNo > 9999999999l)
			return false;

		return true;
	}

	public static long parseMobileNo(String mobileNoStr) throws BadRequestException {

		mobileNoStr = mobileNoStr.trim();
		
		if(mobileNoStr == null || mobileNoStr.length() < 10) {
			throw new BadRequestException("Invalid Mobile No!");
		}
		
		mobileNoStr = mobileNoStr.substring(mobileNoStr.length() - 10);
		
		long mobileNo = 0;
		try{
			mobileNo = Long.parseLong(mobileNoStr);
		} catch (Exception e) {
			throw new BadRequestException("Invalid Mobile No Format!");
		}
		
		return mobileNo;
	}

	public static boolean isAdminMemberAcNo(long memberAcNo) {	
		return ((getDistrictFromMemberAc(memberAcNo) == 1l) || (((getGroupAcFromMember(memberAcNo) % DataUtil.GROUP_RANGE_FOR_DISTRICT) == 1l)));
	}

	public static boolean isAdminGroupAcNo(long groupAcNo) {	
		return ((getDistrictFromGroupAc(groupAcNo) == 1l) || ((groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT) == 1l));
	}

	public static boolean isTimeValid(long time) {
		// Check if time value is in Range from 01/01/2000 to 01/01/2040
		return (time > DataUtil.LONG_TIME_01_01_2000 && 
				time < DataUtil.LONG_TIME_01_01_2040);
	}

	public static String convertArrayToInString(Object[] objArray) {
		StringBuilder strBild = new StringBuilder();
		strBild.append("(");

		for(int i = 0; i < objArray.length; i++) {
			if(objArray[i] != null) {
				if(i > 0) {
					strBild.append(", ");
				}

				strBild.append(objArray[i]);
			}
		}
		strBild.append(")");

		return strBild.toString();
	}

	public static int convertStringToInteger(String str) {
		if(str != null && !str.isEmpty()) {
			return new Integer(str.trim());
		}
		return 0;
	}

	public static long convertStringToLong(String str) {
		if(str != null && !str.isEmpty()) {
			return new Long(str.trim());
		}
		return 0L;
	}

	public static BigDecimal convertStringToBigDecimal(String str) {
		if(str != null && !str.isEmpty()) {
			return new BigDecimal(str.trim());
		}
		return null;
	}

	public static String getDisplayMemberAcNoNZ(long memberAcNo) {
		StringBuilder sb = new StringBuilder();
		long groupAcNo = getGroupAcFromMember(memberAcNo);
		
		sb.append(EnumCache.getDistrictCodeForGroup(groupAcNo));
		sb.append("-");
		sb.append(groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT);
		sb.append("-");
		sb.append(memberAcNo % DataUtil.MEMBER_RANGE_FOR_GROUP);
		
		return sb.toString();
	}

	public static String getDisplayGroupAcNoNZ(long groupAcNo) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(EnumCache.getDistrictCodeForGroup(groupAcNo));
		sb.append("-");
		sb.append(groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT);
		
		return sb.toString();
	}

	public static String getDisplayMemberAcNo(long memberAcNo) {
		StringBuilder sb = new StringBuilder();
		long groupAcNo = getGroupAcFromMember(memberAcNo);
		
		sb.append(EnumCache.getDistrictCodeForGroup(groupAcNo));
		sb.append("-");
		sb.append(fillZeros(groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT, 5));
		sb.append("-");
		sb.append(fillZeros(memberAcNo % DataUtil.MEMBER_RANGE_FOR_GROUP, 4));
		
		return sb.toString();
	}

	public static String getDisplayGroupAcNo(long groupAcNo) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(EnumCache.getDistrictCodeForGroup(groupAcNo));
		sb.append("-");
		sb.append(fillZeros(groupAcNo % DataUtil.GROUP_RANGE_FOR_DISTRICT, 5));
		
		return sb.toString();
	}

	public static String fillZeros(long num, int size) {
		return fillZeros(DataUtil.toString(num), size);
	}
	
	public static String fillZeros(String num, int size) {
		if(num == null) {
			num = DataUtil.EMPTY_STRING;
		}		
		StringBuilder sb = new StringBuilder();		
		for(int i = size - num.length(); i > 0; i--) {
			sb.append("0");
		}
		sb.append(num);
		return sb.toString();
	}
	
	public static BankAccountShort convertToDisplay(GBankAccount bankAccount) {
		if(bankAccount != null) {
			return new BankAccountShort(bankAccount.getBankAccountNo(),
					bankAccount.getBankAccount().getBankProfile().getGAcNo(),
					convertToDisplayStr(bankAccount.getBankAccount()),
					EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, bankAccount.getBankAccount().getBankAccountTypeId()));
		}
		return null;
	}

	public static BankAccountShort convertToDisplay(MBankAccount bankAccount) {
		if(bankAccount != null) {
			return new BankAccountShort(bankAccount.getBankAccountNo(),
					bankAccount.getBankAccount().getBankProfile().getGAcNo(),
					convertToDisplayStr(bankAccount.getBankAccount()),
					EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, bankAccount.getBankAccount().getBankAccountTypeId()));
		}
		return null;
	}

	public static BankAccountShort convertToDisplay(BankAccount bankAccount) {
		if(bankAccount != null) {
			return new BankAccountShort(bankAccount.getBankAccountNo(),
					bankAccount.getBankProfile().getGAcNo(),
					convertToDisplayStr(bankAccount),
					EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, bankAccount.getBankAccountTypeId()));
		}
		return null;
	}
	public static String convertToDisplayStr(BankAccount bankAccount) {
		return convertToDisplayStr(bankAccount, 6, 4);
	}
	
	public static String convertToDisplayStr(BankAccount bankAccount, int bankNameLen, int bankNoLen) {
		if(bankAccount != null) {
			String str = DataUtil.EMPTY_STRING;

			String bankAcType = EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, bankAccount.getBankAccountTypeId());
			String bankAcTypeShort = DataUtil.EMPTY_STRING;

			if(bankAcType != null) {
				
				switch (bankAcType) {
				case EnumConst.BankAccountType_Saving_Account:
					bankAcTypeShort = "Saving";
					break;

				case EnumConst.BankAccountType_Loan_Account:
					bankAcTypeShort = "Loan";
					break;

				case EnumConst.BankAccountType_Investment_Account:
					bankAcTypeShort = "Invest";
					break;

				case EnumConst.BankAccountType_Project_Account:
					bankAcTypeShort = "Project";
					break;

				case EnumConst.BankAccountType_Current_Account:
					bankAcTypeShort = "Current";
					break;

				case EnumConst.BankAccountType_Fix_Deposit_Account:
					bankAcTypeShort = "FD";
					break;

				case EnumConst.BankAccountType_Over_Draft_Account:
					bankAcTypeShort = "OD";
					break;	
				}
			}

			if(bankAcTypeShort != DataUtil.EMPTY_STRING) {
				str += bankAcTypeShort + " - ";
			}

			if(bankAccount.getBankProfile().getBankName() != null) {
				if(bankAccount.getBankProfile().getBankName().length() > bankNameLen) {
					str += bankAccount.getBankProfile().getBankName().substring(0, bankNameLen);
				} 
				else {
					str += bankAccount.getBankProfile().getBankName();
				}
			}
			str += "** - **";
			if(bankAccount.getAccountNumber() != null) {
				if(bankAccount.getAccountNumber().length() > bankNoLen) {
					str += bankAccount.getAccountNumber().substring(bankAccount.getAccountNumber().length() - bankNoLen);
				}
				else {
					str += bankAccount.getAccountNumber();
				}
			}

			return str;
		}
		return DataUtil.EMPTY_STRING;
	}

	public static BankAccountShort convertToDisplay(BankProfile bankProfile) {
		if(bankProfile != null) {
			return new BankAccountShort(bankProfile.getGAcNo(),
					bankProfile.getGAcNo(),
					(bankProfile.getBankName() + " - " + bankProfile.getBranchName()),
					DataUtil.EMPTY_STRING);
		}
		return null;
	}

	public static String getGroupPlace(GroupContact gContact) {
		return getContactPlace(gContact.getContact());
	}

	public static String getContactPlace(Contact contact) {
		StringBuilder sb = new StringBuilder();
		short itemBuild = 0;
		
		if(contact.getVillage() != null && !contact.getVillage().isEmpty()) {
			sb.append(contact.getVillage());
			itemBuild++;
		} else if(contact.getGrampanchayat() != null && !contact.getGrampanchayat().isEmpty()) {
			sb.append(contact.getGrampanchayat());
			itemBuild++;
		}
		if(contact.getTaluka() != null && !contact.getTaluka().isEmpty()) {
			if(sb.indexOf(contact.getTaluka()) < 0) {
				if(itemBuild > 0) {
					sb.append(", ");
				}
				sb.append(contact.getTaluka());
				itemBuild++;
			}
		}
		if(itemBuild < 2) {
			if(sb.indexOf(contact.getDistrict().getDistrict()) < 0) {
				if(itemBuild > 0) {
					sb.append(", ");
				}
				sb.append(contact.getDistrict().getDistrict());
			}
		}
		
		return sb.toString();
	}

	public static String getGroupAddress(GroupContact gContact) {
		return getContactAddress(gContact.getContact());
	}

	public static String getContactAddress(Contact contact) {
		StringBuilder sb = new StringBuilder();
		boolean started = false;
		if(contact.getAddress() != null && !contact.getAddress().isEmpty()) {
			sb.append(contact.getAddress());
			started = true;
		}
		if(contact.getVillage() != null && !contact.getVillage().isEmpty()) {
			if(sb.indexOf(contact.getVillage()) < 0) {
			if(started) {
				sb.append(", ");
			} else {
				started = true;
			}
			sb.append(contact.getVillage());
			}
		}
		if(contact.getGrampanchayat() != null && !contact.getGrampanchayat().isEmpty()) {
			if(sb.indexOf(contact.getGrampanchayat()) < 0) {
				if(started) {
					sb.append(", ");
				} else {
					started = true;
				}
				sb.append("GP. ");
				sb.append(contact.getGrampanchayat());
			}
		}
		if(contact.getTaluka() != null && !contact.getTaluka().isEmpty()) {
			if(started) {
				sb.append(", ");
			} else {
				started = true;
			}
			sb.append("T. ");
			sb.append(contact.getTaluka());
		}
		{
			if(started) {
				sb.append(", ");
			}
			sb.append("D. ");
			sb.append(contact.getDistrict().getDistrict());
		}
		
		return sb.toString();
	}
}
