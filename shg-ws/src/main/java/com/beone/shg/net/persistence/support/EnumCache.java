package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.auth.MrUiAccessCodeUtil;
import com.beone.shg.net.auth.MrWsAccessCodeUtil;
import com.beone.shg.net.auth.UiAccessCodeUtil;
import com.beone.shg.net.auth.WsAccessCodeUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.EnumValue;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeCatEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeCategory;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MRoleEnum;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeValue;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportInfo;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportsEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleValue;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;

public class EnumCache {
	private final static Logger LOGGER = LoggerFactory.getLogger(EnumCache.class);

	private static Map<String, GenericEnum> enumCache;
	private static Map<String, Map<String, EnumValue>> enumByNameCache;
	private static Map<String, Map<Integer, EnumValue>> enumByIdCache;

	private static TxTypeEnum txTypeCache;
	private static TxTypeEnum txTypeShareCache;
	private static Map<String, TxTypeValue> txTypeByNameCache;
	private static Map<Integer, TxTypeValue> txTypeByIdCache;
	private static Map<Integer, TxTypeFormula> txTypeFormulaByIdCache;

	private static GroupTypeEnum groupTypeCache;
	private static GroupTypeEnum groupTypeShareCache;
	private static Map<String, GroupTypeValue> groupTypeByNameCache;
	private static Map<Integer, GroupTypeValue> groupTypeByIdCache;

	private static NameTitleEnum nameTitleCache;
	private static Map<String, NameTitleValue> nameTitleByNameCache;
	private static Map<Integer, NameTitleValue> nameTitleByIdCache;

	private static MRoleEnum mRoleCache;
	private static MRoleEnum mRoleShareCache;
	private static Map<String, MRoleValue> mRoleByNameCache;
	private static Map<Integer, MRoleValue> mRoleByIdCache;

	private static FundTypeEnum fundTypeCache;
	private static Map<String, FundTypeValue> fundTypeByNameCache;
	private static Map<Integer, FundTypeValue> fundTypeByIdCache;

	private static DistrictsEnum districtsCache;
	private static Map<String, DistrictValue> districtsByCodeCache;
	private static Map<Integer, DistrictValue> districtsByIdCache;

	private static MonthlyReportsEnum monthlyReportsCache;
	private static MonthlyReportsEnum monthlyReportsShareCache;
	private static Map<String, MonthlyReportInfo> monthlyReportsByNameCache;
	private static Map<Integer, MonthlyReportInfo> monthlyReportsByIdCache;
	private static Map<Integer, MonthlyReportFormula> monthlyReportFormulaByIdCache;

	private static DocTypeEnum docTypeCache;
	private static Map<String, DocTypeValue> docTypeByNameCache;
	private static Map<Integer, DocTypeValue> docTypeByIdCache;

	private static MessageTypeEnum messageTypeCache;
	private static Map<String, MessageTypeValue> messageTypeByNameCache;
	private static Map<Integer, MessageTypeValue> messageTypeByIdCache;

	private static MProfilingTypeEnum mProfilingTypeCache;
	private static Map<String, MProfilingTypeCatEnum> mProfilingTypeFor;
	private static Map<String, MProfilingTypeValue> mProfilingTypeByNameCache;
	private static Map<Integer, MProfilingTypeValue> mProfilingTypeByIdCache;

	private static AccessCodeEnum uiAccessCodeCache;
	private static Map<String, AccessCodeValue> uiAccessCodeByNameCache;
	private static Map<Integer, AccessCodeValue> uiAccessCodeByIdCache;

	private static AccessCodeEnum wsAccessCodeCache;
	private static Map<String, AccessCodeValue> wsAccessCodeByNameCache;
	private static Map<Integer, AccessCodeValue> wsAccessCodeByIdCache;

	private static AccessCodeEnum mrUiAccessCodeCache;
	private static Map<String, AccessCodeValue> mrUiAccessCodeByNameCache;
	private static Map<Integer, AccessCodeValue> mrUiAccessCodeByIdCache;

	private static AccessCodeEnum mrWsAccessCodeCache;
	private static Map<String, AccessCodeValue> mrWsAccessCodeByNameCache;
	private static Map<Integer, AccessCodeValue> mrWsAccessCodeByIdCache;

	private static UiAccessCodeUtil uiAccessCodeUtil;
	private static WsAccessCodeUtil wsAccessCodeUtil;
	private static MrUiAccessCodeUtil mrUiAccessCodeUtil;
	private static MrWsAccessCodeUtil mrWsAccessCodeUtil;

	static {
		LOGGER.debug("Create Generic Enum Cache");

		enumCache = new ConcurrentHashMap<String, GenericEnum>();
		enumByNameCache = new ConcurrentHashMap<String, Map<String, EnumValue>>();
		enumByIdCache = new ConcurrentHashMap<String, Map<Integer, EnumValue>>();

		txTypeByNameCache = new ConcurrentHashMap<String, TxTypeValue>();
		txTypeByIdCache = new ConcurrentHashMap<Integer, TxTypeValue>();
		txTypeFormulaByIdCache = new ConcurrentHashMap<Integer, TxTypeFormula>();

		groupTypeByNameCache = new ConcurrentHashMap<String, GroupTypeValue>();
		groupTypeByIdCache = new ConcurrentHashMap<Integer, GroupTypeValue>();

		nameTitleByNameCache = new ConcurrentHashMap<String, NameTitleValue>();
		nameTitleByIdCache = new ConcurrentHashMap<Integer, NameTitleValue>();

		mRoleByNameCache = new ConcurrentHashMap<String, MRoleValue>();
		mRoleByIdCache = new ConcurrentHashMap<Integer, MRoleValue>();

		fundTypeByNameCache = new ConcurrentHashMap<String, FundTypeValue>();
		fundTypeByIdCache = new ConcurrentHashMap<Integer, FundTypeValue>();

		districtsCache = new DistrictsEnum();
		districtsByCodeCache = new ConcurrentHashMap<String, DistrictValue>();
		districtsByIdCache = new ConcurrentHashMap<Integer, DistrictValue>();

		monthlyReportsByNameCache = new ConcurrentHashMap<String, MonthlyReportInfo>();
		monthlyReportsByIdCache = new ConcurrentHashMap<Integer, MonthlyReportInfo>();
		monthlyReportFormulaByIdCache = new ConcurrentHashMap<Integer, MonthlyReportFormula>();

		docTypeByNameCache = new ConcurrentHashMap<String, DocTypeValue>();
		docTypeByIdCache = new ConcurrentHashMap<Integer, DocTypeValue>();

		messageTypeByNameCache = new ConcurrentHashMap<String, MessageTypeValue>();
		messageTypeByIdCache = new ConcurrentHashMap<Integer, MessageTypeValue>();

		mProfilingTypeFor = new ConcurrentHashMap<String, MProfilingTypeCatEnum>();
		mProfilingTypeByNameCache = new ConcurrentHashMap<String, MProfilingTypeValue>();
		mProfilingTypeByIdCache = new ConcurrentHashMap<Integer, MProfilingTypeValue>();

		uiAccessCodeByNameCache = new ConcurrentHashMap<String, AccessCodeValue>();
		uiAccessCodeByIdCache = new ConcurrentHashMap<Integer, AccessCodeValue>();

		wsAccessCodeByNameCache = new ConcurrentHashMap<String, AccessCodeValue>();
		wsAccessCodeByIdCache = new ConcurrentHashMap<Integer, AccessCodeValue>();

		mrUiAccessCodeByNameCache = new ConcurrentHashMap<String, AccessCodeValue>();
		mrUiAccessCodeByIdCache = new ConcurrentHashMap<Integer, AccessCodeValue>();

		mrWsAccessCodeByNameCache = new ConcurrentHashMap<String, AccessCodeValue>();
		mrWsAccessCodeByIdCache = new ConcurrentHashMap<Integer, AccessCodeValue>();
	}

	// ************************ GenericEnum *****************************
	public static void addEnumValues(String enumName, GenericEnum gEnum) {
		synchronized(EnumCache.class) {
			enumCache.put(enumName, gEnum);

			Map<String, EnumValue> byName = new ConcurrentHashMap<String, EnumValue>();
			Map<Integer, EnumValue> byId = new ConcurrentHashMap<Integer, EnumValue>();

			for(EnumValue value : gEnum.getEnumValues()) {
				byName.put(value.getEnumValue(), value);
				byId.put(value.getEnumIndex(), value);
			}

			enumByNameCache.put(enumName, byName);
			enumByIdCache.put(enumName, byId);
		}
	}

	public static GenericEnum getEnumValues(String enumName) {
		return enumCache.get(enumName);
	}

	public static int getIndexOfEnumValue(String enumName, String enumValue) {
		int index = -1;  	
		if(enumByNameCache.containsKey(enumName) && enumByNameCache.get(enumName).containsKey(enumValue)) {
			return enumByNameCache.get(enumName).get(enumValue).getEnumIndex();
		} 	
		return index;
	}

	public static EnumValue getEnumValue(String enumName, String enumValue) {
		if(enumByNameCache.containsKey(enumName) && enumByNameCache.get(enumName).containsKey(enumValue)) {
			return enumByNameCache.get(enumName).get(enumValue);
		} 	
		return null;
	}

	public static String getNameOfEnumValue(String enumName, int enumIndex) {
		if(enumByIdCache.containsKey(enumName) && enumByIdCache.get(enumName).containsKey(enumIndex)) {
			return enumByIdCache.get(enumName).get(enumIndex).getEnumValue();
		} 	
		return DataUtil.EMPTY_STRING;
	}

	public static EnumValue getEnumValue(String enumName, int enumIndex) {
		if(enumByIdCache.containsKey(enumName) && enumByIdCache.get(enumName).containsKey(enumIndex)) {
			return enumByIdCache.get(enumName).get(enumIndex);
		} 	
		return null;
	}

	// ************************ TxTypeEnum *****************************
	public static TxTypeEnum getTxType() {
		return txTypeCache;
	}

	public static TxTypeEnum getTxTypeShare() {
		return txTypeShareCache;
	}

	public static void addTxType(TxTypeEnum txType) {
		synchronized(EnumCache.class) {
			txTypeCache = txType;
			txTypeByNameCache.clear();
			txTypeByIdCache.clear();
			txTypeFormulaByIdCache.clear();

			txTypeShareCache = new TxTypeEnum();
			txTypeShareCache.setEnumName(txTypeCache.getEnumName());

			for(TxTypeValue value: txType.getEnumValues()) {
				txTypeShareCache.getEnumValues().add(new TxTypeValue(value));

				txTypeByNameCache.put(value.getTxType(), value);
				txTypeByIdCache.put(value.getTxTypeId(), value);
				txTypeFormulaByIdCache.put(value.getTxTypeId(), new TxTypeFormula(value));
			}
		}
	}

	public static int getIndexOfTxType(String enumValue) {
		int index = -1;
		if(txTypeByNameCache.containsKey(enumValue)) {
			return txTypeByNameCache.get(enumValue).getTxTypeId();
		}
		return index;
	}

	public static String getNameOfTxType(int enumIndex) {
		if(txTypeByIdCache.containsKey(enumIndex)) {
			return txTypeByIdCache.get(enumIndex).getTxType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static TxTypeValue getTxTypeValue(String enumValue) {
		if(txTypeByNameCache.containsKey(enumValue)) {
			return txTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static TxTypeValue getTxTypeValue(int enumIndex) {
		if(txTypeByIdCache.containsKey(enumIndex)) {
			return txTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	public static TxTypeFormula getTxTypeFormula(int enumIndex) {
		if(txTypeFormulaByIdCache.containsKey(enumIndex)) {
			return txTypeFormulaByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ GroupTypeEnum *****************************
	public static GroupTypeEnum getGroupType() {
		return groupTypeCache;
	}

	public static GroupTypeEnum getGroupTypeShare() {
		return groupTypeShareCache;
	}

	public static void addGroupType(GroupTypeEnum groupType) {
		synchronized(EnumCache.class) {
			groupTypeCache = groupType;
			groupTypeByNameCache.clear();
			groupTypeByIdCache.clear();

			groupTypeShareCache = new GroupTypeEnum();
			groupTypeShareCache.setEnumName(groupType.getEnumName());
			
			for(GroupTypeValue value: groupType.getEnumValues()) {
				groupTypeByNameCache.put(value.getGroupType(), value);
				groupTypeByIdCache.put(value.getGroupTypeId(), value);
				
				groupTypeShareCache.addEnumValue(new GroupTypeValue(value));
			}
		}
	}

	public static int getIndexOfGroupType(String enumValue) {
		int index = -1;
		if(groupTypeByNameCache.containsKey(enumValue)) {
			return groupTypeByNameCache.get(enumValue).getGroupTypeId();
		}
		return index;
	}

	public static String getNameOfGroupType(int enumIndex) {
		if(groupTypeByIdCache.containsKey(enumIndex)) {
			return groupTypeByIdCache.get(enumIndex).getGroupType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static GroupTypeValue getGroupTypeValue(String enumValue) {
		if(groupTypeByNameCache.containsKey(enumValue)) {
			return groupTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static GroupTypeValue getGroupTypeValue(int enumIndex) {
		if(groupTypeByIdCache.containsKey(enumIndex)) {
			return groupTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ NameTitleEnum *****************************
	public static NameTitleEnum getNameTitle() {
		return nameTitleCache;
	}

	public static void addNameTitle(NameTitleEnum nameTitle) {
		synchronized(EnumCache.class) {
			nameTitleCache = nameTitle;
			nameTitleByNameCache.clear();
			nameTitleByIdCache.clear();

			for(NameTitleValue value: nameTitle.getEnumValues()) {
				nameTitleByNameCache.put(value.getTitle(), value);
				nameTitleByIdCache.put(value.getTitleId(), value);
			}
		}
	}

	public static int getIndexOfNameTitle(String enumValue) {
		int index = -1;
		if(nameTitleByNameCache.containsKey(enumValue)) {
			return nameTitleByNameCache.get(enumValue).getTitleId();
		}
		return index;
	}

	public static String getNameOfNameTitle(int enumIndex) {
		if(nameTitleByIdCache.containsKey(enumIndex)) {
			return nameTitleByIdCache.get(enumIndex).getTitle();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static NameTitleValue getNameTitleValue(String enumValue) {
		if(nameTitleByNameCache.containsKey(enumValue)) {
			return nameTitleByNameCache.get(enumValue);
		}
		return null;
	}

	public static NameTitleValue getNameTitleValue(int enumIndex) {
		if(nameTitleByIdCache.containsKey(enumIndex)) {
			return nameTitleByIdCache.get(enumIndex);
		}
		return null;
	}

	public static int getIndexOfNameTitle(String enumValue, String lang) {
		int index = -1;

		synchronized(nameTitleCache) {
			for(NameTitleValue value : nameTitleCache.getEnumValues()) {
				if(value.getTitle().equalsIgnoreCase(enumValue) && value.getLang().equalsIgnoreCase(lang)) {
					index = value.getTitleId();
					break;
				}
			}
		}

		return index;
	}

	// ************************ MRoleEnum *****************************
	public static MRoleEnum getMRole() {
		return mRoleCache;
	}

	public static MRoleEnum getMRoleShare() {
		return mRoleShareCache;
	}

	public static void addMRole(MRoleEnum mRole) {
		synchronized(EnumCache.class) {
			mRoleCache = mRole;
			mRoleByNameCache.clear();
			mRoleByIdCache.clear();

			mRoleShareCache = new MRoleEnum();
			mRoleShareCache.setEnumName(mRole.getEnumName());
			
			for(MRoleValue value: mRole.getEnumValues()) {
				mRoleByNameCache.put(value.getRole(), value);
				mRoleByIdCache.put(value.getRoleId(), value);
				
				mRoleShareCache.addEnumValue(new MRoleValue(value));
			}
		}
	}

	public static int getIndexOfMRole(String enumValue) {
		int index = -1;
		if(mRoleByNameCache.containsKey(enumValue)) {
			return mRoleByNameCache.get(enumValue).getRoleId();
		}
		return index;
	}

	public static String getNameOfMRole(int enumIndex) {
		if(mRoleByIdCache.containsKey(enumIndex)) {
			return mRoleByIdCache.get(enumIndex).getRole();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static MRoleValue getMRoleValue(String enumValue) {
		if(mRoleByNameCache.containsKey(enumValue)) {
			return mRoleByNameCache.get(enumValue);
		}
		return null;
	}

	public static MRoleValue getMRoleValue(int enumIndex) {
		if(mRoleByIdCache.containsKey(enumIndex)) {
			return mRoleByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ FundTypeEnum *****************************
	public static FundTypeEnum getFundType() {
		return fundTypeCache;
	}

	public static void addFundType(FundTypeEnum fundType) {
		synchronized(EnumCache.class) {
			fundTypeCache = fundType;
			fundTypeByNameCache.clear();
			fundTypeByIdCache.clear();

			for(FundTypeValue value: fundType.getEnumValues()) {
				fundTypeByNameCache.put(value.getFundType(), value);
				fundTypeByIdCache.put(value.getFundTypeId(), value);
			}
		}
	}

	public static int getIndexOfFundType(String enumValue) {
		int index = -1;
		if(fundTypeByNameCache.containsKey(enumValue)) {
			return fundTypeByNameCache.get(enumValue).getFundTypeId();
		}
		return index;
	}

	public static String getNameOfFundType(int enumIndex) {
		if(fundTypeByIdCache.containsKey(enumIndex)) {
			return fundTypeByIdCache.get(enumIndex).getFundType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static FundTypeValue getFundTypeValue(String enumValue) {
		if(fundTypeByNameCache.containsKey(enumValue)) {
			return fundTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static FundTypeValue getFundTypeValue(int enumIndex) {
		if(fundTypeByIdCache.containsKey(enumIndex)) {
			return fundTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ DistrictsREST *****************************
	public static DistrictsEnum getDistrictsREST(String lang) {
		synchronized(EnumCache.class) {
			if(lang == null || lang.isEmpty()) {
				return districtsCache;
			} else {
				DistrictsEnum districts = new DistrictsEnum();
				districts.setLang(lang);

				if(districtsCache.getDistrictValues() != null) {
					for(DistrictValue district: districtsCache.getDistrictValues()) {
						if(district.getLang().equals(lang)) {
							districts.addDistrictValue(district);
						}
					}

					return districts;
				} else {
					return null;
				}
			}
		}
	}

	public static void addDistricts(DistrictsEnum districts) {
		synchronized(EnumCache.class) {
			districtsCache = districts;
			districtsByCodeCache.clear();
			districtsByIdCache.clear();

			for(DistrictValue value: districts.getDistrictValues()) {
				districtsByCodeCache.put(value.getDistrictCode(), value);
				districtsByIdCache.put(value.getDistrictId(), value);
			}
		}
	}

	public static int getIndexOfDistrict(String enumValue) {
		int index = -1;
		if(districtsByCodeCache.containsKey(enumValue)) {
			return districtsByCodeCache.get(enumValue).getDistrictId();
		}
		return index;
	}

	public static String getCodeOfDistrict(int enumIndex) {
		if(districtsByIdCache.containsKey(enumIndex)) {
			return districtsByIdCache.get(enumIndex).getDistrictCode();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static DistrictValue getDistrictValue(String enumValue) {
		if(districtsByCodeCache.containsKey(enumValue)) {
			return districtsByCodeCache.get(enumValue);
		}
		return null;
	}

	public static DistrictValue getDistrictValue(int enumIndex) {
		if(districtsByIdCache.containsKey(enumIndex)) {
			return districtsByIdCache.get(enumIndex);
		}
		return null;
	}

	public static int getIndexOfDistrictsREST(String district) {
		int index = -1;

		synchronized(districtsCache) {
			for(DistrictValue value : districtsCache.getDistrictValues()) {
				if(value.getDistrict().equalsIgnoreCase(district)) {
					index = value.getDistrictId();
					break;
				}
			}
		}

		return index;
	}

	public static String getDistrictCodeForGroup(long groupAcNo) {
		int districtId = (int)ConversionUtil.getDistrictFromGroupAc(groupAcNo);

		synchronized(districtsCache) {
			if(districtId > 0) {
				return districtsCache.getDistrictValues().get(districtId - 1).getDistrictCode();
			}
		}

		return DataUtil.EMPTY_STRING;
	}

	// ************************ MonthlyReportsEnum *****************************
	public static MonthlyReportsEnum getMonthlyReports() {
		return monthlyReportsCache;
	}

	public static MonthlyReportsEnum getMonthlyReportsShare() {
		return monthlyReportsShareCache;
	}

	public static void addMonthlyReports(MonthlyReportsEnum monthlyReports) {
		synchronized(EnumCache.class) {
			monthlyReportsCache = monthlyReports;
			monthlyReportsByNameCache.clear();
			monthlyReportsByIdCache.clear();
			monthlyReportFormulaByIdCache.clear();

			monthlyReportsShareCache = new MonthlyReportsEnum();
			monthlyReportsShareCache.setLang(monthlyReports.getLang());
			
			for(MonthlyReportInfo value: monthlyReports.getMonthlyReports()) {
				monthlyReportsByNameCache.put(value.getReportName(), value);
				monthlyReportsByIdCache.put(value.getMonthlyReportId(), value);
				monthlyReportFormulaByIdCache.put(value.getMonthlyReportId(), new MonthlyReportFormula(value));
				
				monthlyReportsShareCache.addMonthlyReport(new MonthlyReportInfo(value));
			}
		}
	}

	public static int getIndexOfMonthlyReports(String enumValue) {
		int index = -1;
		if(monthlyReportsByNameCache.containsKey(enumValue)) {
			return monthlyReportsByNameCache.get(enumValue).getMonthlyReportId();
		}
		return index;
	}

	public static String getNameOfMonthlyReports(int enumIndex) {
		if(monthlyReportsByIdCache.containsKey(enumIndex)) {
			return monthlyReportsByIdCache.get(enumIndex).getReportName();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static MonthlyReportInfo getMonthlyReportInfo(String enumValue) {
		if(monthlyReportsByNameCache.containsKey(enumValue)) {
			return monthlyReportsByNameCache.get(enumValue);
		}
		return null;
	}

	public static MonthlyReportInfo getMonthlyReportInfo(int enumIndex) {
		if(monthlyReportsByIdCache.containsKey(enumIndex)) {
			return monthlyReportsByIdCache.get(enumIndex);
		}
		return null;
	}

	public static MonthlyReportFormula getMonthlyReportFormula(String enumValue) {
		if(monthlyReportsByNameCache.containsKey(enumValue)) {
			return monthlyReportFormulaByIdCache.get(monthlyReportsByNameCache.get(enumValue).getMonthlyReportId());
		}
		return null;
	}

	public static MonthlyReportFormula getMonthlyReportFormula(int enumIndex) {
		if(monthlyReportFormulaByIdCache.containsKey(enumIndex)) {
			return monthlyReportFormulaByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ DocTypeEnum *****************************
	public static DocTypeEnum getDocType() {
		return docTypeCache;
	}

	public static void addDocType(DocTypeEnum docType) {
		synchronized(EnumCache.class) {
			docTypeCache = docType;
			docTypeByNameCache.clear();
			docTypeByIdCache.clear();

			for(DocTypeValue value: docType.getEnumValues()) {
				docTypeByNameCache.put(value.getDocType(), value);
				docTypeByIdCache.put(value.getDocTypeId(), value);
			}
		}
	}

	public static int getIndexOfDocType(String enumValue) {
		int index = -1;
		if(docTypeByNameCache.containsKey(enumValue)) {
			return docTypeByNameCache.get(enumValue).getDocTypeId();
		}
		return index;
	}

	public static String getNameOfDocType(int enumIndex) {
		if(docTypeByIdCache.containsKey(enumIndex)) {
			return docTypeByIdCache.get(enumIndex).getDocType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static DocTypeValue getDocTypeValue(String enumValue) {
		if(docTypeByNameCache.containsKey(enumValue)) {
			return docTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static DocTypeValue getDocTypeValue(int enumIndex) {
		if(docTypeByIdCache.containsKey(enumIndex)) {
			return docTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ MessageTypeEnum *****************************
	public static MessageTypeEnum getMessageType() {
		return messageTypeCache;
	}

	public static void addMessageType(MessageTypeEnum messageType) {
		synchronized(EnumCache.class) {
			messageTypeCache = messageType;
			messageTypeByNameCache.clear();
			messageTypeByIdCache.clear();

			for(MessageTypeValue value: messageType.getEnumValues()) {
				messageTypeByNameCache.put(value.getMessageType() + value.getLang(), value);
				messageTypeByIdCache.put(value.getMessageTypeId(), value);
			}
		}
	}

	public static int getIndexOfMessageType(String enumValue, String lang) {
		int index = -1;
		if(messageTypeByNameCache.containsKey(enumValue + lang)) {
			return messageTypeByNameCache.get(enumValue + lang).getMessageTypeId();
		}
		return index;
	}

	public static String getNameOfMessageType(int enumIndex) {
		if(messageTypeByIdCache.containsKey(enumIndex)) {
			return messageTypeByIdCache.get(enumIndex).getMessageType();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static MessageTypeValue getMessageTypeValue(String enumValue, String lang) {
		if(messageTypeByNameCache.containsKey(enumValue + lang)) {
			return messageTypeByNameCache.get(enumValue + lang);
		}
		return null;
	}

	public static MessageTypeValue getMessageTypeValue(int enumIndex) {
		if(messageTypeByIdCache.containsKey(enumIndex)) {
			return messageTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ MProfilingTypeEnum *****************************
	public static MProfilingTypeEnum getMProfilingType() {
		return mProfilingTypeCache;
	}

	public static MProfilingTypeCatEnum getMProfilingType(String profileFor) {
		if(profileFor != null && !profileFor.isEmpty()) {
			return mProfilingTypeFor.get(profileFor);
		}
		return null;
	}

	public static void addMProfilingType(MProfilingTypeEnum mProfilingType) {
		synchronized(EnumCache.class) {
			mProfilingTypeCache = mProfilingType;
			mProfilingTypeByNameCache.clear();
			mProfilingTypeByIdCache.clear();

			Map<String, Map<String, MProfilingTypeCategory>> tempCatMap = new ConcurrentHashMap<String, Map<String, MProfilingTypeCategory>>();

			for(MProfilingTypeValue value: mProfilingType.getEnumValues()) {
				mProfilingTypeByNameCache.put(value.getPoint(), value);
				mProfilingTypeByIdCache.put(value.getPointId(), value);
				
				if(!tempCatMap.containsKey(value.getProfileFor())) {
					tempCatMap.put(value.getProfileFor(), new ConcurrentHashMap<String, MProfilingTypeCategory>());
				}
				if(!tempCatMap.get(value.getProfileFor()).containsKey(value.getCategory())) {
					MProfilingTypeCategory eCat = new MProfilingTypeCategory();
					eCat.setCategory(value.getCategory());
					tempCatMap.get(value.getProfileFor()).put(value.getCategory(), eCat);
				}				
				tempCatMap.get(value.getProfileFor()).get(value.getCategory()).addEnumValue(value);
			}
			
			for(String forProfile : tempCatMap.keySet()) {
				MProfilingTypeCatEnum mEmum = new MProfilingTypeCatEnum();
				mEmum.setEnumName(mProfilingType.getEnumName());
				
				List<String> categories = new ArrayList<String>();
				categories.addAll(tempCatMap.get(forProfile).keySet());
				Collections.sort(categories);
				for(String category : categories) {
					mEmum.addEnumValue(tempCatMap.get(forProfile).get(category));
				}				
				
				mProfilingTypeFor.put(forProfile, mEmum);
			}
		}
	}

	public static int getIndexOfMProfilingType(String enumValue) {
		int index = -1;
		if(mProfilingTypeByNameCache.containsKey(enumValue)) {
			return mProfilingTypeByNameCache.get(enumValue).getPointId();
		}
		return index;
	}

	public static String getNameOfMProfilingType(int enumIndex) {
		if(mProfilingTypeByIdCache.containsKey(enumIndex)) {
			return mProfilingTypeByIdCache.get(enumIndex).getPoint();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static MProfilingTypeValue getMProfilingTypeValue(String enumValue) {
		if(mProfilingTypeByNameCache.containsKey(enumValue)) {
			return mProfilingTypeByNameCache.get(enumValue);
		}
		return null;
	}

	public static MProfilingTypeValue getMProfilingTypeValue(int enumIndex) {
		if(mProfilingTypeByIdCache.containsKey(enumIndex)) {
			return mProfilingTypeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ UiAccessCode *****************************
	public static AccessCodeEnum getUiAccessCode() {
		return uiAccessCodeCache;
	}

	public static void addUiAccessCode(AccessCodeEnum uiAccessCode) {
		synchronized(EnumCache.class) {
			uiAccessCodeCache = uiAccessCode;
			uiAccessCodeByNameCache.clear();
			uiAccessCodeByIdCache.clear();

			for(AccessCodeValue value: uiAccessCode.getEnumValues()) {
				uiAccessCodeByNameCache.put(value.getAccessCode(), value);
				uiAccessCodeByIdCache.put(value.getAccessCodeId(), value);
			}
		}
	}

	public static int getIndexOfUiAccessCode(String enumValue) {
		int index = -1;
		if(uiAccessCodeByNameCache.containsKey(enumValue)) {
			return uiAccessCodeByNameCache.get(enumValue).getAccessCodeId();
		}
		return index;
	}

	public static String getNameOfUiAccessCode(int enumIndex) {
		if(uiAccessCodeByIdCache.containsKey(enumIndex)) {
			return uiAccessCodeByIdCache.get(enumIndex).getAccessCode();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static AccessCodeValue getUiAccessCodeValue(String enumValue) {
		if(uiAccessCodeByNameCache.containsKey(enumValue)) {
			return uiAccessCodeByNameCache.get(enumValue);
		}
		return null;
	}

	public static AccessCodeValue getUiAccessCodeValue(int enumIndex) {
		if(uiAccessCodeByIdCache.containsKey(enumIndex)) {
			return uiAccessCodeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ WsAccessCode *****************************
	public static AccessCodeEnum getWsAccessCode() {
		return wsAccessCodeCache;
	}

	public static void addWsAccessCode(AccessCodeEnum wsAccessCode) {
		synchronized(EnumCache.class) {
			wsAccessCodeCache = wsAccessCode;
			wsAccessCodeByNameCache.clear();
			wsAccessCodeByIdCache.clear();

			for(AccessCodeValue value: wsAccessCode.getEnumValues()) {
				wsAccessCodeByNameCache.put(value.getAccessCode(), value);
				wsAccessCodeByIdCache.put(value.getAccessCodeId(), value);
			}
		}
	}

	public static int getIndexOfWsAccessCode(String enumValue) {
		int index = -1;
		if(wsAccessCodeByNameCache.containsKey(enumValue)) {
			return wsAccessCodeByNameCache.get(enumValue).getAccessCodeId();
		}
		return index;
	}

	public static String getNameOfWsAccessCode(int enumIndex) {
		if(wsAccessCodeByIdCache.containsKey(enumIndex)) {
			return wsAccessCodeByIdCache.get(enumIndex).getAccessCode();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static AccessCodeValue getWsAccessCodeValue(String enumValue) {
		if(wsAccessCodeByNameCache.containsKey(enumValue)) {
			return wsAccessCodeByNameCache.get(enumValue);
		}
		return null;
	}

	public static AccessCodeValue getWsAccessCodeValue(int enumIndex) {
		if(wsAccessCodeByIdCache.containsKey(enumIndex)) {
			return wsAccessCodeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ MrUiAccessCode *****************************
	public static AccessCodeEnum getMrUiAccessCode() {
		return mrUiAccessCodeCache;
	}

	public static void addMrUiAccessCode(AccessCodeEnum mrUiAccessCode) {
		synchronized(EnumCache.class) {
			mrUiAccessCodeCache = mrUiAccessCode;
			mrUiAccessCodeByNameCache.clear();
			mrUiAccessCodeByIdCache.clear();

			for(AccessCodeValue value: mrUiAccessCode.getEnumValues()) {
				mrUiAccessCodeByNameCache.put(value.getAccessCode(), value);
				mrUiAccessCodeByIdCache.put(value.getAccessCodeId(), value);
			}
		}
	}

	public static int getIndexOfMrUiAccessCode(String enumValue) {
		int index = -1;
		if(mrUiAccessCodeByNameCache.containsKey(enumValue)) {
			return mrUiAccessCodeByNameCache.get(enumValue).getAccessCodeId();
		}
		return index;
	}

	public static String getNameOfMrUiAccessCode(int enumIndex) {
		if(mrUiAccessCodeByIdCache.containsKey(enumIndex)) {
			return mrUiAccessCodeByIdCache.get(enumIndex).getAccessCode();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static AccessCodeValue getMrUiAccessCodeValue(String enumValue) {
		if(mrUiAccessCodeByNameCache.containsKey(enumValue)) {
			return mrUiAccessCodeByNameCache.get(enumValue);
		}
		return null;
	}

	public static AccessCodeValue getMrUiAccessCodeValue(int enumIndex) {
		if(mrUiAccessCodeByIdCache.containsKey(enumIndex)) {
			return mrUiAccessCodeByIdCache.get(enumIndex);
		}
		return null;
	}

	// ************************ MrWsAccessCode *****************************
	public static AccessCodeEnum getMrWsAccessCode() {
		return mrWsAccessCodeCache;
	}

	public static void addMrWsAccessCode(AccessCodeEnum mrWsAccessCode) {
		synchronized(EnumCache.class) {
			mrWsAccessCodeCache = mrWsAccessCode;
			mrWsAccessCodeByNameCache.clear();
			mrWsAccessCodeByIdCache.clear();

			for(AccessCodeValue value: mrWsAccessCode.getEnumValues()) {
				mrWsAccessCodeByNameCache.put(value.getAccessCode(), value);
				mrWsAccessCodeByIdCache.put(value.getAccessCodeId(), value);
			}
		}
	}

	public static int getIndexOfMrWsAccessCode(String enumValue) {
		int index = -1;
		if(mrWsAccessCodeByNameCache.containsKey(enumValue)) {
			return mrWsAccessCodeByNameCache.get(enumValue).getAccessCodeId();
		}
		return index;
	}

	public static String getNameOfMrWsAccessCode(int enumIndex) {
		if(mrWsAccessCodeByIdCache.containsKey(enumIndex)) {
			return mrWsAccessCodeByIdCache.get(enumIndex).getAccessCode();
		}
		return DataUtil.EMPTY_STRING;
	}

	public static AccessCodeValue getMrWsAccessCodeValue(String enumValue) {
		if(mrWsAccessCodeByNameCache.containsKey(enumValue)) {
			return mrWsAccessCodeByNameCache.get(enumValue);
		}
		return null;
	}

	public static AccessCodeValue getMrWsAccessCodeValue(int enumIndex) {
		if(mrWsAccessCodeByIdCache.containsKey(enumIndex)) {
			return mrWsAccessCodeByIdCache.get(enumIndex);
		}
		return null;
	}

	public static UiAccessCodeUtil getUiAccessCodeUtil() {
		return uiAccessCodeUtil;
	}

	public static void setUiAccessCodeUtil(UiAccessCodeUtil uiAccessCodeUtil) {
		EnumCache.uiAccessCodeUtil = uiAccessCodeUtil;
	}

	public static WsAccessCodeUtil getWsAccessCodeUtil() {
		return wsAccessCodeUtil;
	}

	public static void setWsAccessCodeUtil(WsAccessCodeUtil wsAccessCodeUtil) {
		EnumCache.wsAccessCodeUtil = wsAccessCodeUtil;
	}

	public static MrUiAccessCodeUtil getMrUiAccessCodeUtil() {
		return mrUiAccessCodeUtil;
	}

	public static void setMrUiAccessCodeUtil(MrUiAccessCodeUtil mrUiAccessCodeUtil) {
		EnumCache.mrUiAccessCodeUtil = mrUiAccessCodeUtil;
	}

	public static MrWsAccessCodeUtil getMrWsAccessCodeUtil() {
		return mrWsAccessCodeUtil;
	}

	public static void setMrWsAccessCodeUtil(MrWsAccessCodeUtil mrWsAccessCodeUtil) {
		EnumCache.mrWsAccessCodeUtil = mrWsAccessCodeUtil;
	}
}
