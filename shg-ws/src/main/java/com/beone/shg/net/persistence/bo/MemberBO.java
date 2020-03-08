package com.beone.shg.net.persistence.bo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.job.DataFeildsUpdateJob;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.BankAccount;
import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.model.Contact;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GM;
import com.beone.shg.net.persistence.model.GMId;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.MemberContact;
import com.beone.shg.net.persistence.support.BankAccountInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.util.RandomString;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.model.resp.MemberOtherAcInfo;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.rest.BankAccountREST;
import com.beone.shg.net.webservice.rest.model.rest.MMessageREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberAcREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberContactREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberLoanAcREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberName;
import com.beone.shg.net.webservice.rest.model.rest.MemberREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberSavingAcREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberSearchInfo;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.bo.MrAccountBO;

@Component("memberBO")
public class MemberBO extends BaseBO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MemberBO.class);

	@Autowired
	@Qualifier("groupBO")
	private GroupBO groupBO;

	@Autowired
	@Qualifier("memberSavingAcBO")
	private MemberSavingAcBO memberSavingAcBO;

	@Autowired
	@Qualifier("memberLoanAcBO")
	private MemberLoanAcBO memberLoanAcBO;

	@Autowired
	@Qualifier("messageBO")
	private MessageBO messageBO;

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	@Autowired
	@Qualifier("mrAccountBO")
	private MrAccountBO mrAccountBO;

	private RandomString randomString = new RandomString(6, true, false);

	public MemberREST addMember(MemberREST member, boolean oldData) throws BadRequestException {

		if(member.getMrole() == null || member.getMrole().isEmpty()) {
			throw new BadRequestException("Null Or Empty Member Role");
		}
		if(member.getContacts() == null || member.getContacts().isEmpty()) {
			throw new BadRequestException("Null Or Empty Member Contact");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Profile");
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active)) {
			throw new BadRequestException("Invalid Group Active Status : " + EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
		}
		if(gProfile.getMemberCounter() >= DataUtil.MAX_MEMBERS_PER_GROUP) {
			throw new BadRequestException("Congratulation! Group has crossed Max Member Limit per Group, please contact Admin for help!");
		}
		MRoleValue mRole = EnumCache.getMRoleValue(member.getMrole());
		if(mRole == null) {
			throw new BadRequestException("Invalid Member Role: " + member.getMrole());
		}
		String groupType = EnumCache.getNameOfGroupType(gProfile.getGroupTypeId());
		if(!groupType.equals(mRole.getBelongTo())) {
			throw new BadRequestException("Member Role: " + member.getMrole() + 
					" don't belong to GroupType: " + groupType + "!");
		}

		if(groupType.equals(EnumConst.GroupType_SHG)) {
			GRules gRules = null;
			for(GRules rule: gProfile.getGRuleses()) {
				gRules = rule;
				break;
			}
			if(gRules == null) {
				throw new BadRequestException("Invalid Group Rules");
			}
			if(EnumUtil.isCoreMember(member.getMrole()) && gProfile.getActiveCoreMembers() >= gRules.getMaxNoOfCoreMembers()) {
				throw new BadRequestException("Active Core Member can't be more than: " + gRules.getMaxNoOfCoreMembers());
			}
			if(EnumUtil.isAssociateMember(member.getMrole()) && (gRules.getAllowAssociateMembers() == DataUtil.ZERO_INTEGER)) {
				throw new BadRequestException("Associate Members are Not allowed as per Group Rules, please contact Admin for help!");
			}
			if(EnumUtil.isAssociateMember(member.getMrole()) && gProfile.getActiveAssociateMembers() >= (DataUtil.MAX_MEMBERS_PER_GROUP - gRules.getMaxNoOfCoreMembers())) {
				throw new BadRequestException("Active Associate Member can't be more than: " + (DataUtil.MAX_MEMBERS_PER_GROUP - gRules.getMaxNoOfCoreMembers()));
			}
		}

		MProfile mProfile = new MProfile();

		mProfile.setGroupMemberCounter(gProfile.getMemberCounter());
		mProfile.setParentGroupAcNo(member.getParentGroupAcNo());

		if(member.getRecommendedByMemberAcNo() > DataUtil.ZERO_LONG &&
				daoFactory.getMProfileDAO().getReferenceById(member.getRecommendedByMemberAcNo()) != null) {
			mProfile.setRecommendedByMember(member.getRecommendedByMemberAcNo());
		}

		if(oldData) {
			mProfile.setApprovalStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, member.getApprovalStatus()));
			mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, member.getActiveStatus()));
			if(member.getActiveStatus().equals(EnumConst.ActiveStatus_Active)) {
				member.setPasscode(randomString.nextString().toUpperCase());
				mProfile.setPasscode(member.getPasscode());
			}
		} else {
			member.setApprovalStatus(EnumConst.ApprovalStatus_Submitted);
			member.setActiveStatus(EnumConst.ActiveStatus_Requested);
			mProfile.setApprovalStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Submitted));
			mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Requested));
		}
		mProfile.setMroleId(mRole.getRoleId());

		mProfile.setDateOfEnroll(DateUtil.convertStringToDateWithCurrentDefault(member.getDateOfEnroll()));
		mProfile.setDateOfBirth(DateUtil.convertStringToDate(member.getDateOfBirth()));
		mProfile.setDateOfAnni(DateUtil.convertStringToDate(member.getDateOfAnni()));
		mProfile.setDateOfClosure(DateUtil.convertStringToDate(member.getDateOfClosure()));

		mProfile.setPassSet((byte)0);
		mProfile.setGender(member.getGender());
		mProfile.setUidaiNo(member.getUidaiNo());
		mProfile.setPanCardNo(member.getPanCardNo());
		mProfile.setVoterIdNo(member.getVoterIdNo());
		mProfile.setDrivingLicenseNo(member.getDrivingLicenseNo());
		mProfile.setPhotoUrl(member.getPhotoUrl());
		mProfile.setDescription(member.getStatusMessage());

		Set<MemberContact> contacts = new HashSet<MemberContact>();

		for(MemberContactREST contactREST : member.getContacts()) {
			MemberContact mContact = addMemberContact(contactREST);
			mContact.setMProfile(mProfile);
			contacts.add(mContact);
		}

		mProfile.setMemberContacts(contacts);
		daoFactory.getMProfileDAO().persist(mProfile);

		gProfile.setMemberCounter(gProfile.getMemberCounter() + 1);

		if(!EnumUtil.isClosed(member.getActiveStatus())) {
			if(EnumUtil.isCoreMember(member.getMrole())) {
				gProfile.setActiveCoreMembers(gProfile.getActiveCoreMembers() + 1);
			} else if(EnumUtil.isAssociateMember(member.getMrole())) {
				gProfile.setActiveAssociateMembers(gProfile.getActiveAssociateMembers() + 1);
			}
		}

		daoFactory.getGProfileDAO().merge(gProfile);

		for(MemberContact contact: contacts) {
			daoFactory.getMemberContactDAO().persist(contact);
		}

		if(member.getBankAccounts() != null && !member.getBankAccounts().isEmpty()) {
			Set<MBankAccount> mBankAccounts = mProfile.getMBankAccounts();

			for(BankAccountREST bankAccontREST : member.getBankAccounts()) {
				MBankAccount mBankAccount = addMemberBankAccount(bankAccontREST);
				mBankAccount.setMProfile(mProfile);

				daoFactory.getMBankAccountDAO().persist(mBankAccount);
				mBankAccounts.add(mBankAccount);
			}
		}

		// Map Member to Parent Group
		mapMemberToGroup(mProfile, gProfile, mRole);

		// Load generated member id 
		member.setMemberAcNo(mProfile.getMemberAcNo());

		// Load generated member name 
		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, mProfile.getMemberAcNo()));

		// Create Accounts for SHG Type Member
		if(mRole.getRoleCategory().equals(EnumConst.MRole_Cat_SHG_Member)) {

			// Create Member Account for the Added MemberProfile
			if(oldData) {
				MemberSavingAcREST savingAc = null;
				MemberLoanAcREST loanAc = null;

				// Load Saving Account Info
				if(member.getMemberSavingAc() != null) {
					for(MemberSavingAcREST memberSavingAc: member.getMemberSavingAc()) {

						// Add Account No 
						memberSavingAc.setMemberAc(mProfile.getMemberAcNo());

						savingAc = memberSavingAc;

						break;
					}
				}

				// Load Loan Account Info
				if(member.getMemberLoanAc() != null) {
					for(MemberLoanAcREST memberLoanAc: member.getMemberLoanAc()) {

						// Add Account No 
						memberLoanAc.setMemberAcNo(mProfile.getMemberAcNo());

						loanAc = memberLoanAc;

						break;
					}
				}

				// Create Member Account with Saving & Loan Info
				addMemberAccount(mProfile, member);

				if(savingAc != null) {
					// Create Member Saving Account
					memberSavingAcBO.add(savingAc, true);
				}

				if(loanAc != null && 
						loanAc.getInstallment().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0 &&
						loanAc.getClosureDate().isEmpty()) {
					// Create Member Loan Account
					memberLoanAcBO.add(loanAc, true);
				}

			} else {
				// Create Member Account with Default Values
				addMemberAccount(mProfile, member);

				MemberSavingAcREST savingAc = new MemberSavingAcREST(mProfile.getMemberAcNo(), member.getMonthlySaving(), member.getNoPlannedSavingInst());

				// Create New Member Saving Account
				memberSavingAcBO.add(savingAc, false);

				// Add Monthly Planned Saving to GAc
				GAc gAc = daoFactory.getGAcDAO().findById(member.getParentGroupAcNo());

				if(EnumUtil.isCoreMember(member.getMrole())) {
					gAc.setCMPlannedMonthlySaving(gAc.getCMPlannedMonthlySaving().add(member.getMonthlySaving()));
				} else {
					gAc.setAMPlannedMonthlySaving(gAc.getAMPlannedMonthlySaving().add(member.getMonthlySaving()));
				}

				daoFactory.getGAcDAO().merge(gAc);
			}
		} else {
			// Create Member Account with Default Values
			addMemberAccount(mProfile, member);
		}
		
		if(mRole.getSystem().equals(EnumConst.MRole_System_Micro_Retailer)) {
			mrAccountBO.addPMAccount(mProfile, false);
		}

		return member;
	}

	protected MemberREST importMember(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Parent Group Ac No");
		}
		if(member.getMrole() == null || member.getMrole().isEmpty()) {
			throw new BadRequestException("Null Or Empty Member Role");
		}
		if(member.getContacts() == null || member.getContacts().isEmpty()) {
			throw new BadRequestException("Null Or Empty Member Contact");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Profile");
		}
		if(EnumUtil.isCoreMember(member.getMrole()) && gProfile.getActiveCoreMembers() >= 20) {
			throw new BadRequestException("Core Member can't be more than 20");
		}

		MProfile mProfile = new MProfile();

		mProfile.setGroupMemberCounter(gProfile.getMemberCounter());
		mProfile.setParentGroupAcNo(member.getParentGroupAcNo());

		if(member.getRecommendedByMemberAcNo() > DataUtil.ZERO_LONG && 
				daoFactory.getMProfileDAO().getReferenceById(member.getRecommendedByMemberAcNo()) != null) {
			mProfile.setRecommendedByMember(member.getRecommendedByMemberAcNo());
		}

		mProfile.setApprovalStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, member.getApprovalStatus()));
		mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, member.getActiveStatus()));
		mProfile.setMroleId(EnumCache.getIndexOfMRole(member.getMrole()));
		mProfile.setPasscode(member.getPasscode());

		mProfile.setDateOfEnroll(DateUtil.convertStringToDateWithCurrentDefault(member.getDateOfEnroll()));
		mProfile.setDateOfBirth(DateUtil.convertStringToDate(member.getDateOfBirth()));
		mProfile.setDateOfAnni(DateUtil.convertStringToDate(member.getDateOfAnni()));
		mProfile.setDateOfClosure(DateUtil.convertStringToDate(member.getDateOfClosure()));

		mProfile.setGender(member.getGender());
		mProfile.setPhotoUrl(member.getPhotoUrl());
		mProfile.setUidaiNo(member.getUidaiNo());

		Set<MemberContact> contacts = new HashSet<MemberContact>();

		for(MemberContactREST contactREST : member.getContacts()) {
			MemberContact mContact = addMemberContact(contactREST);
			mContact.setMProfile(mProfile);
			contacts.add(mContact);
		}

		mProfile.setMemberContacts(contacts);
		daoFactory.getMProfileDAO().persist(mProfile);

		gProfile.setMemberCounter(gProfile.getMemberCounter() + 1);

		if(!EnumUtil.isClosed(member.getActiveStatus())) {
			if(EnumUtil.isCoreMember(member.getMrole())) {
				gProfile.setActiveCoreMembers(gProfile.getActiveCoreMembers() + 1);
			} else if(EnumUtil.isAssociateMember(member.getMrole())) {
				gProfile.setActiveAssociateMembers(gProfile.getActiveAssociateMembers() + 1);
			}
		}

		daoFactory.getGProfileDAO().merge(gProfile);

		for(MemberContact contact: contacts) {
			daoFactory.getMemberContactDAO().persist(contact);
		}

		if(member.getBankAccounts() != null && !member.getBankAccounts().isEmpty()) {
			Set<MBankAccount> mBankAccounts = mProfile.getMBankAccounts();

			for(BankAccountREST bankAccontREST : member.getBankAccounts()) {
				MBankAccount mBankAccount = addMemberBankAccount(bankAccontREST);
				mBankAccount.setMProfile(mProfile);

				daoFactory.getMBankAccountDAO().persist(mBankAccount);
				mBankAccounts.add(mBankAccount);
			}
		}

		MRoleValue mRole = EnumCache.getMRoleValue(member.getMrole());

		// Map Member to Parent Group
		mapMemberToGroup(mProfile, gProfile, mRole);

		// Load generated member id 
		member.setMemberAcNo(mProfile.getMemberAcNo());

		// Create Accounts for SHG Type Member
		if(mRole.getRoleCategory().equals(EnumConst.MRole_Cat_SHG_Member)) {

			// Create Member Account with Saving & Loan Info
			importMemberAccount(mProfile, member.getAccount());
		}

		return member;
	}

	public MemberREST addMember(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Parent Group Ac No");
		}

		// Check for Duplicate Member Names in Group
		GroupInfoCollector memberNameCollector = new GroupInfoCollector();
		memberNameCollector.setGroupAcNo(member.getParentGroupAcNo());
		daoFactory.getMemberContactDAO().loadMemberLiveNonTilelFullNamesForGroup(EnumConst.Lang_English, memberNameCollector);	
		for(MemberContactREST contactREST : member.getContacts()) {
			if(contactREST.getLang().equals(EnumConst.Lang_English)) {
				String name = contactREST.getFirstName() + " " + contactREST.getMiddleName() + " " + contactREST.getLastName();
				if(memberNameCollector.isNamePresent(name)) {
					throw new BadRequestException("Member with Name: '" + name + "' already present in the Group, please verify!");
				}
				break;
			}
		}

		return addMember(member, false);
	}

	public void addMembers(List<MemberREST> members, boolean oldData) throws BadRequestException {

		for(MemberREST member: members) {
			addMember(member, oldData);
		}
	}

	public List<Map<String,String>> addMembersCSV(long groupAcNo, List<String[]> rawMembers) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Parent Group Ac No");
		}

		List<MemberREST> members = new ArrayList<MemberREST>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MemberREST.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		GAc gAc = daoFactory.getGAcDAO().findById(groupAcNo);

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			members.add(MemberREST.buildMember(groupAcNo, csvData, row, gAc));		
		}

		daoFactory.getGAcDAO().merge(gAc);

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(members.size());

		GroupInfoCollector memberNameCollector = new GroupInfoCollector();
		memberNameCollector.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberLiveNonTilelFullNamesForGroup(EnumConst.Lang_English, memberNameCollector);	

		for(MemberREST member: members) {
			try {				
				boolean notAdded = true;
				// Check for Duplicate Member Names in Group
				for(MemberContactREST contactREST : member.getContacts()) {
					if(contactREST.getLang().equals(EnumConst.Lang_English)) {
						String name = contactREST.getFirstName() + " " + contactREST.getMiddleName() + " " + contactREST.getLastName();
						if(memberNameCollector.isNamePresent(name)) {
							notAdded = false;					
						}
						break;
					}
				}

				if(notAdded) {
					returnList.add(addMember(member, true).toStringInfo());
				}
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage() + " - caused for - " + member.toStringInfo());				
			}
		}

		return returnList;
	}

	public List<Map<String,String>> updateMembersCSV(long groupAcNo, List<String[]> rawMembers) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Parent Group Ac No");
		}

		List<MemberREST> members = new ArrayList<MemberREST>(rawMembers.size());

		CSVDataCollector csvData = new CSVDataCollector(rawMembers);

		if(!MemberREST.isMemberCSVValid(csvData)) {
			throw new BadRequestException("Invalid Member CSV Data!");
		}

		GAc gAc = daoFactory.getGAcDAO().findById(groupAcNo);

		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			members.add(MemberREST.buildMember(groupAcNo, csvData, row, gAc));		
		}

		daoFactory.getGAcDAO().merge(gAc);

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(members.size());

		GroupInfoCollector memberNameCollector = new GroupInfoCollector();
		memberNameCollector.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNonTilelFullNamesForGroup(EnumConst.Lang_English, memberNameCollector);	

		for(MemberREST member: members) {
			try {				
				long memberAcNo = 0l;
				for(MemberContactREST contactREST : member.getContacts()) {
					if(contactREST.getLang().equals(EnumConst.Lang_English)) {
						String name = contactREST.getFirstName() + " " + contactREST.getMiddleName() + " " + contactREST.getLastName();
						if(memberNameCollector.isNamePresent(name)) {
							memberAcNo = memberNameCollector.getMemberAc(name);					
						}
						break;
					}
				}

				if(memberAcNo > 0) {
					MAc mAc = daoFactory.getMAcDAO().findById(memberAcNo);			
					if(mAc == null) {
						continue;
					}

					MemberSavingAcREST savingAc = null;
					MemberLoanAcREST loanAc = null;

					// Load Saving Account Info
					if(member.getMemberSavingAc() != null) {

						long savingAcNo = 0;
						for(MSavingAc mSavingAc: mAc.getMSavingAcs()) {
							if(EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, mSavingAc.getAccountStatusId()))) {
								savingAcNo = mSavingAc.getMSavingAcNo();
								break;
							}
						}

						for(MemberSavingAcREST memberSavingAc: member.getMemberSavingAc()) {
							// Add Account No 
							memberSavingAc.setMemberAc(memberAcNo);
							memberSavingAc.setMemberSavingAcNo(savingAcNo);
							savingAc = memberSavingAc;

							break;
						}
					}

					// Load Loan Account Info
					if(member.getMemberLoanAc() != null) {

						long loanAcNo = 0;
						for(MLoanAc mLoanAc: mAc.getMLoanAcs()) {
							if(EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, mLoanAc.getAccountStatusId()))) {
								loanAcNo = mLoanAc.getMLoanAcNo();
								break;
							}
						}

						for(MemberLoanAcREST memberLoanAc: member.getMemberLoanAc()) {
							// Add Account No 
							memberLoanAc.setMemberAcNo(memberAcNo);
							memberLoanAc.setMemberLoanAcNo(loanAcNo);
							loanAc = memberLoanAc;

							break;
						}
					}

					// Create Member Account with Saving & Loan Info
					addMemberAccount(mAc.getMProfile(), member);

					if(savingAc != null) {
						// Create Member Saving Account
						memberSavingAcBO.add(savingAc, true);
					}

					if(loanAc != null && loanAc.getInstallment().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
						// Create Member Loan Account
						memberLoanAcBO.add(loanAc, true);
					}

					returnList.add(member.toStringInfo());
				}
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage() + " - caused for - " + member.toStringInfo());				
			}
		}

		return returnList;
	}

	protected void addMemberAccount(MProfile mProfile, MemberREST member) {

		boolean newObject = false;
		MAc memberAc = daoFactory.getMAcDAO().findById(mProfile.getMemberAcNo());

		if(memberAc == null) {
			memberAc = new MAc();
			memberAc.setMProfile(mProfile);
			newObject = true;
		}

		MemberAcREST mAc = member.getAccount();		
		if(mAc != null) {
			memberAc.setCreditRating(mAc.getCreditRating());
			memberAc.setMeetingAttendance(mAc.getMeetingAttendance());
			memberAc.setPlannedMonthlySavingAm(mAc.getPlannedMonthlySavingAm());
			memberAc.setSavedAm(mAc.getSavedAm());
			memberAc.setOutstandingSavedAm(mAc.getOutstandingSavedAm());
			memberAc.setProvIntEnAm(mAc.getProvIntEnAm());
			memberAc.setPrevMonthIntEnAm(mAc.getPrevMonthIntEnAm());
			memberAc.setReturnedSavedAm(mAc.getReturnedSavedAm());
			memberAc.setReturnedIntEnAm(mAc.getReturnedIntEnAm());
			memberAc.setDividedProfitDeclaredAm(mAc.getDividedProfitDeclaredAm());
			memberAc.setDividedProfitPaidAm(mAc.getDividedProfitPaidAm());
			memberAc.setNoOfLoans(mAc.getNoOfLoans());
			memberAc.setNoOfActiveLoans(mAc.getNoOfActiveLoans());
			memberAc.setLoanAm(mAc.getLoanAm());
			memberAc.setRecLoanAm(mAc.getRecLoanAm());
			memberAc.setRecIntOnLoanAm(mAc.getRecIntOnLoanAm());
			memberAc.setProjIntOnLoanAm(mAc.getProjIntOnLoanAm());
			memberAc.setBadDeptClosedAm(mAc.getBadDeptClosedAm());
			memberAc.setRecPenaltyAm(mAc.getRecPenaltyAm());
			memberAc.setPendingPenaltyAm(mAc.getPendingPenaltyAm());
			memberAc.setMeetingAttended(mAc.getMeetingAttended());
			memberAc.setMeetingMissed(mAc.getMeetingMissed());
			memberAc.setLastUpdatedTs(mAc.getLastUpdatedTs());

		} else {
			memberAc.setCreditRating(DataUtil.ZERO_FLOAT);
			memberAc.setMeetingAttendance(DataUtil.ZERO_FLOAT);
			memberAc.setPlannedMonthlySavingAm(member.getMonthlySaving());
			memberAc.setSavedAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setOutstandingSavedAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setProvIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setPrevMonthIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setReturnedSavedAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setReturnedIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setDividedProfitDeclaredAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setDividedProfitPaidAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setNoOfLoans(DataUtil.ZERO_INTEGER);
			memberAc.setNoOfActiveLoans(DataUtil.ZERO_INTEGER);
			memberAc.setLoanAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setRecLoanAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setRecIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setProjIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setBadDeptClosedAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setRecPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setPendingPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
			memberAc.setMeetingAttended(DataUtil.ZERO_INTEGER);
			memberAc.setMeetingMissed(DataUtil.ZERO_INTEGER);
			memberAc.setLastUpdatedTs(DateUtil.getCurrentTimeDate());

		}

		MemberOtherAcInfo otherInfo = member.getMemberOtherAcInfo();		
		if(otherInfo != null) {
			// Updated Detailed penalty items
			if(otherInfo.getPenaltyDetail() != null && !otherInfo.getPenaltyDetail().isEmpty()) {
				groupBO.updateAcByTxtype(ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo()),
						otherInfo.getPenaltyDetail());
			}
		}

		if(newObject) {
			daoFactory.getMAcDAO().persist(memberAc);
		} else {
			daoFactory.getMAcDAO().merge(memberAc);
		}
	}

	public MemberAcREST updateMemberAc(MemberAcREST ac) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(ac.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No:" + ac.getMemberAcNo());
		}		
		MAc memberAc = daoFactory.getMAcDAO().findById(ac.getMemberAcNo());
		if(memberAc == null) {
			throw new BadRequestException("Invalid Member Account No:" + ac.getMemberAcNo());
		}

		memberAc.setPlannedMonthlySavingAm(ac.getPlannedMonthlySavingAm());
		memberAc.setSavedAm(ac.getSavedAm());
		memberAc.setOutstandingSavedAm(ac.getOutstandingSavedAm());
		memberAc.setProvIntEnAm(ac.getProvIntEnAm());
		memberAc.setPrevMonthIntEnAm(ac.getPrevMonthIntEnAm());
		memberAc.setReturnedSavedAm(ac.getReturnedSavedAm());
		memberAc.setReturnedIntEnAm(ac.getReturnedIntEnAm());
		memberAc.setDividedProfitPaidAm(ac.getDividedProfitPaidAm());
		memberAc.setNoOfLoans(ac.getNoOfLoans());
		memberAc.setNoOfActiveLoans(ac.getNoOfActiveLoans());
		memberAc.setLoanAm(ac.getLoanAm());
		memberAc.setRecLoanAm(ac.getRecLoanAm());
		memberAc.setRecIntOnLoanAm(ac.getRecIntOnLoanAm());
		memberAc.setProjIntOnLoanAm(ac.getProjIntOnLoanAm());
		memberAc.setBadDeptClosedAm(ac.getBadDeptClosedAm());
		memberAc.setRecPenaltyAm(ac.getRecPenaltyAm());
		memberAc.setPendingPenaltyAm(ac.getPendingPenaltyAm());
		memberAc.setMeetingAttended(ac.getMeetingAttended());
		memberAc.setMeetingMissed(ac.getMeetingMissed());

		daoFactory.getMAcDAO().merge(memberAc);

		return ac;
	}

	protected void importMemberAccount(MProfile mProfile, MemberAcREST memberAc) {

		MAc mAc = new MAc();
		mAc.setMProfile(mProfile);

		mAc.setCreditRating(memberAc.getCreditRating());
		mAc.setPlannedMonthlySavingAm(memberAc.getPlannedMonthlySavingAm());
		mAc.setSavedAm(memberAc.getSavedAm());
		mAc.setOutstandingSavedAm(memberAc.getOutstandingSavedAm());
		mAc.setProvIntEnAm(memberAc.getProvIntEnAm());
		mAc.setPrevMonthIntEnAm(memberAc.getPrevMonthIntEnAm());
		mAc.setReturnedSavedAm(memberAc.getReturnedSavedAm());
		mAc.setReturnedIntEnAm(memberAc.getReturnedIntEnAm());
		mAc.setDividedProfitPaidAm(memberAc.getDividedProfitPaidAm());
		mAc.setNoOfLoans(memberAc.getNoOfLoans());
		mAc.setNoOfActiveLoans(memberAc.getNoOfActiveLoans());
		mAc.setLoanAm(memberAc.getLoanAm());
		mAc.setRecLoanAm(memberAc.getRecLoanAm());
		mAc.setRecIntOnLoanAm(memberAc.getRecIntOnLoanAm());
		mAc.setProjIntOnLoanAm(memberAc.getProjIntOnLoanAm());
		mAc.setRecPenaltyAm(memberAc.getRecPenaltyAm());
		mAc.setPendingPenaltyAm(memberAc.getPendingPenaltyAm());
		mAc.setMeetingAttended(memberAc.getMeetingAttended());
		mAc.setMeetingMissed(memberAc.getMeetingMissed());
		mAc.setLastUpdatedTs(memberAc.getLastUpdatedTs());

		daoFactory.getMAcDAO().persist(mAc);
	}

	public MemberREST updateMember(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		mProfile.setGender(member.getGender());
		mProfile.setDateOfEnroll(DateUtil.convertStringToDate(member.getDateOfEnroll()));
		mProfile.setDateOfBirth(DateUtil.convertStringToDate(member.getDateOfBirth()));
		mProfile.setDateOfAnni(DateUtil.convertStringToDate(member.getDateOfAnni()));
		mProfile.setUidaiNo(member.getUidaiNo());
		mProfile.setPanCardNo(member.getPanCardNo());
		mProfile.setVoterIdNo(member.getVoterIdNo());
		mProfile.setDrivingLicenseNo(member.getDrivingLicenseNo());
		mProfile.setPhotoUrl(member.getPhotoUrl());
		mProfile.setDescription(member.getStatusMessage());

		if(member.getApprovalStatus().isEmpty() && EnumUtil.isActiveToCompute(member.getActiveStatus())) {
			mProfile.setApprovalStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Approved));
		}

		daoFactory.getMProfileDAO().merge(mProfile);

		updateMemberContacts(member, mProfile);

		updateMemberBankAccounts(member, mProfile);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(EnumConst.Lang_English, member.getMemberAcNo()));

		return member;
	}

	public MemberREST updateMemberCompleteData(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		if(member.getAccount() != null) {
			updateMemberAc(member.getAccount());
		}

		if(member.getMemberSavingAc() != null && !member.getMemberSavingAc().isEmpty()) {
			for(MemberSavingAcREST savingAc: member.getMemberSavingAc()) {
				memberSavingAcBO.updateMemberSavingAcData(savingAc);
			}
		}	

		if(member.getMemberLoanAc() != null && !member.getMemberLoanAc().isEmpty()) {
			for(MemberLoanAcREST loanAc: member.getMemberLoanAc()) {
				memberLoanAcBO.updateMemberLoanAcData(loanAc);
			}
		}	

		return member;
	}

	public MemberREST approveReject(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account : " + member.getMemberAcNo());
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account : " + member.getMemberAcNo());
		}

		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Parent Group Account : " + member.getParentGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Parent Group Account : " + member.getParentGroupAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Approved)) {
			throw new BadRequestException("Invalid Group Active Status : " + 
					EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()));
		}

		if(member.getApprovedByMemberAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Approved By Member Account");
		}
		if(member.getApprovalStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}

		int approvalStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, member.getApprovalStatus());

		if(approvalStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Active Status:" + member.getApprovalStatus());
		}

		if(!EnumUtil.isApprovalStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()))) {
			throw new BadRequestException("Can't Update Current Member Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		}

		mProfile.setApprovalStatusId(approvalStatusId);
		mProfile.setApprovedByMember(member.getApprovedByMemberAcNo());

		if(member.getApprovalStatus().equals(EnumConst.ApprovalStatus_Rejected)) {
			mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Completed));
		}

		if(member.getDateOfEnroll() != null && !member.getDateOfEnroll().isEmpty()) {
			mProfile.setDateOfEnroll(DateUtil.convertStringToDate(member.getDateOfEnroll()));
		}
		if(member.getMrole() != null && !member.getMrole().isEmpty()) {
			mProfile.setMroleId(EnumCache.getIndexOfMRole(member.getMrole()));
		}
		if(member.getUidaiNo() != null && !member.getUidaiNo().isEmpty()) {
			mProfile.setUidaiNo(member.getUidaiNo());
		}
		if(member.getUidaiNo() != null && !member.getUidaiNo().isEmpty()) {
			mProfile.setUidaiNo(member.getUidaiNo());
		}
		if(member.getPanCardNo() != null && !member.getPanCardNo().isEmpty()) {
			mProfile.setPanCardNo(member.getPanCardNo());
		}
		if(member.getVoterIdNo() != null && !member.getVoterIdNo().isEmpty()) {
			mProfile.setVoterIdNo(member.getVoterIdNo());
		}
		if(member.getDrivingLicenseNo() != null && !member.getDrivingLicenseNo().isEmpty()) {
			mProfile.setDrivingLicenseNo(member.getDrivingLicenseNo());
		}
		if(member.getPhotoUrl() != null && !member.getPhotoUrl().isEmpty()) {
			mProfile.setPhotoUrl(member.getPhotoUrl());
		}
		if(member.getStatusMessage() != null && !member.getStatusMessage().isEmpty()) {
			mProfile.setDescription(member.getStatusMessage());
		}

		daoFactory.getMProfileDAO().merge(mProfile);

		memberSavingAcBO.approveReject(member);

		return member;
	}

	public MemberREST activate(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}		
		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());		
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + member.getMemberAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Approved)) {
			throw new BadRequestException("Member status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		}

		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Parent Group Account : " + member.getParentGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Parent Group Account : " + member.getParentGroupAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active)) {
			throw new BadRequestException("Invalid Group Active Status : " + 
					EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
		}


		mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
		member.setActiveStatus(EnumConst.ActiveStatus_Active);
		member.setPasscode(randomString.nextString().toUpperCase());
		mProfile.setPasscode(member.getPasscode());

		if(member.getUidaiNo() != null && !member.getUidaiNo().isEmpty()) {
			mProfile.setUidaiNo(member.getUidaiNo());
		}
		if(member.getPanCardNo() != null && !member.getPanCardNo().isEmpty()) {
			mProfile.setPanCardNo(member.getPanCardNo());
		}
		if(member.getVoterIdNo() != null && !member.getVoterIdNo().isEmpty()) {
			mProfile.setVoterIdNo(member.getVoterIdNo());
		}
		if(member.getDrivingLicenseNo() != null && !member.getDrivingLicenseNo().isEmpty()) {
			mProfile.setDrivingLicenseNo(member.getDrivingLicenseNo());
		}
		if(member.getPhotoUrl() != null && !member.getPhotoUrl().isEmpty()) {
			mProfile.setPhotoUrl(member.getPhotoUrl());
		}
		if(member.getStatusMessage() != null && !member.getStatusMessage().isEmpty()) {
			mProfile.setDescription(member.getStatusMessage());
		}

		daoFactory.getMProfileDAO().merge(mProfile);

		memberSavingAcBO.activate(member);

		if(member.getTransactions() != null) {
			transactionBO.addTransactions(member.getTransactions());
		}

		return member;
	}

	public MemberREST approveRejectNonSHG(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(member.getApprovedByMemberAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Approved By Member Account");
		}
		if(member.getApprovalStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}

		int approvalStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, member.getApprovalStatus());
		if(approvalStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Active Status: '" + member.getApprovalStatus() + "'");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		if(EnumCache.getMRoleValue(mProfile.getMroleId()).getBelongTo().equals(EnumConst.GroupType_SHG)) {
			throw new BadRequestException("This API is only to Approve None SHG Accounts!");
		}
		if(!EnumUtil.isApprovalStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()))) {
			throw new BadRequestException("Can't Update Current Member Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		}

		mProfile.setApprovalStatusId(approvalStatusId);
		mProfile.setApprovedByMember(member.getApprovedByMemberAcNo());

		if(member.getApprovalStatus().equals(EnumConst.ApprovalStatus_Approved)) {
			mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
			member.setActiveStatus(EnumConst.AccountStatus_Active);
		}

		if(member.getDateOfEnroll() != null && !member.getDateOfEnroll().isEmpty()) {
			mProfile.setDateOfEnroll(DateUtil.convertStringToDate(member.getDateOfEnroll()));
		}
		if(member.getMrole() != null && !member.getMrole().isEmpty()) {
			mProfile.setMroleId(EnumCache.getIndexOfMRole(member.getMrole()));
		}
		if(member.getUidaiNo() != null && !member.getUidaiNo().isEmpty()) {
			mProfile.setUidaiNo(member.getUidaiNo());
		}
		if(member.getPanCardNo() != null && !member.getPanCardNo().isEmpty()) {
			mProfile.setPanCardNo(member.getPanCardNo());
		}
		if(member.getVoterIdNo() != null && !member.getVoterIdNo().isEmpty()) {
			mProfile.setVoterIdNo(member.getVoterIdNo());
		}
		if(member.getDrivingLicenseNo() != null && !member.getDrivingLicenseNo().isEmpty()) {
			mProfile.setDrivingLicenseNo(member.getDrivingLicenseNo());
		}
		if(member.getPhotoUrl() != null && !member.getPhotoUrl().isEmpty()) {
			mProfile.setPhotoUrl(member.getPhotoUrl());
		}
		if(member.getStatusMessage() != null && !member.getStatusMessage().isEmpty()) {
			mProfile.setDescription(member.getStatusMessage());
		}

		daoFactory.getMProfileDAO().merge(mProfile);

		return member;
	}

	public Transactions getTxsToActivate(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}		
		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());	
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + member.getMemberAcNo());
		}

		Transactions transactions = new Transactions();

		if(!EnumUtil.isSHGMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
			return transactions;
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Approved)) {
			throw new BadRequestException("Member status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(EnumUtil.isNonSHGGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()))) {
			return transactions;
		}

		List<BankAccountShort> memberBankAcNos = daoFactory.getMBankAccountDAO().getMemberBankAccountsDisplay(member.getMemberAcNo());
		transactions.addTransaction(memberSavingAcBO.getTxToActivate(member));

		GRules rules = daoFactory.getGRulesDAO().findById(member.getParentGroupAcNo());
		if(rules == null) {
			throw new BadRequestException("Invalid Rules Group Account: " + member.getParentGroupAcNo());
		}
		BigDecimal registrationFee = DataUtil.ZERO_BIG_DECIMAL;
		BigDecimal applicationFee = DataUtil.ZERO_BIG_DECIMAL;
		if(EnumUtil.isAssociateMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
			registrationFee = new BigDecimal(rules.getAmRegistrationFee());
			applicationFee = new BigDecimal(rules.getAmApplicationFee());
		} else {
			registrationFee = new BigDecimal(rules.getCmRegistrationFee());
			applicationFee = new BigDecimal(rules.getCmApplicationFee());
		}

		if(registrationFee.compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			Transaction tx1 = new Transaction();		
			tx1.setTxType(EnumConst.TxType_Registration_Fee);
			tx1.setTxWith(EnumConst.TxType_MEMBER);
			tx1.setSlipType(EnumConst.SlipType_RECEIPT);
			tx1.setMemberAcNo(member.getMemberAcNo());
			tx1.setGroupAcNo(member.getParentGroupAcNo());
			tx1.setPaymentMode(EnumConst.PaymentMode_CASH);
			tx1.setDescription("Registration Fee for Member enrollment");
			tx1.setAmount(registrationFee);		
			tx1.setMemberBankAcNos(memberBankAcNos);
			transactions.addTransaction(tx1);
		}

		if(applicationFee.compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			Transaction tx2 = new Transaction();		
			tx2.setTxType(EnumConst.TxType_Application_Fee);
			tx2.setTxWith(EnumConst.TxType_MEMBER);
			tx2.setSlipType(EnumConst.SlipType_RECEIPT);
			tx2.setMemberAcNo(member.getMemberAcNo());
			tx2.setGroupAcNo(member.getParentGroupAcNo());
			tx2.setPaymentMode(EnumConst.PaymentMode_CASH);
			tx2.setDescription("Application Fee for Member enrollment");
			tx2.setAmount(applicationFee);		
			tx2.setMemberBankAcNos(memberBankAcNos);
			transactions.addTransaction(tx2);
		}

		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(member.getParentGroupAcNo()));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());

		return transactions;
	}

	public MemberREST close(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + member.getMemberAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Approved)) {
			throw new BadRequestException("Member status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active)) {
			throw new BadRequestException("Member Active status Not 'Active' it is : " + EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()));
		}

		List<MLoanAc> accounts = daoFactory.getMLoanAcDAO().getAllAcForMember(mProfile.getMemberAcNo());
		if(accounts != null) {
			for(MLoanAc account : accounts) {
				if(EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
					throw new BadRequestException("Please close All Member Loan Account!");
				}
			}
		}

		mProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Closed));
		member.setActiveStatus(EnumConst.ActiveStatus_Closed);

		if(member.getStatusMessage() != null && !member.getStatusMessage().isEmpty()) {
			mProfile.setDescription(member.getStatusMessage());
		}

		daoFactory.getMProfileDAO().merge(mProfile);

		memberSavingAcBO.close(member);

		if(member.getTransactions() != null) {
			transactionBO.addTransactions(member.getTransactions());
		}

		return member;
	}

	public Transactions getTxsToClose(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}		
		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account: " + member.getMemberAcNo());
		}
		Transactions transactions = new Transactions();

		if(!EnumUtil.isSHGMember(EnumCache.getNameOfMRole(mProfile.getMroleId()))) {
			return transactions;
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()).equals(EnumConst.ActiveStatus_Active)) {
			throw new BadRequestException("Member status Not 'Active' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()));
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(member.getParentGroupAcNo());
		if(EnumUtil.isNonSHGGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()))) {
			return transactions;
		}

		transactions.getTransactions().addAll(memberSavingAcBO.getTxToClose(member));

		transactions.setGroupBankAcNos(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(member.getParentGroupAcNo()));
		transactions.setDisplayNames(RestDisplayTitle.getTransactionRDT());

		return transactions;
	}

	public MemberREST addMemberContacts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		Set<MemberContact> contacts = mProfile.getMemberContacts();

		for(MemberContactREST contactREST : member.getContacts()) {

			MemberContact mContact = addMemberContact(contactREST);
			daoFactory.getMemberContactDAO().persist(mContact);
			contactREST.setContactId(mContact.getContactId());
			contacts.add(mContact);
		}

		mProfile.setMemberContacts(contacts);

		daoFactory.getMProfileDAO().merge(mProfile);

		return member;
	}

	protected MemberContact addMemberContact(MemberContactREST memberContact) throws BadRequestException {

		if(memberContact.getDistrict() == null || memberContact.getDistrict().isEmpty()) {
			throw new BadRequestException("Invalid District Name");
		}
		if(memberContact.getLang() == null || memberContact.getLang().isEmpty()) {
			throw new BadRequestException("Invalid Contact Langauge");
		}

		// Create & Save Contact details
		Contact contact = new Contact();

		contact.setDistrict(daoFactory.getDistrictDAO().getDistrictFromName(memberContact.getState(), memberContact.getDistrict()));
		if(memberContact.getLang().equals(EnumConst.Lang_English)) {
			contact.setAddress(WordUtils.capitalizeFully(memberContact.getAddress()));
			contact.setVillage(WordUtils.capitalizeFully(memberContact.getVillage()));
			contact.setGrampanchayat(WordUtils.capitalizeFully(memberContact.getGrampanchayat()));
			contact.setTaluka(WordUtils.capitalizeFully(memberContact.getTaluka()));

			// Just push back any updates done by WordUtils.capitalizeFully
			memberContact.setAddress(contact.getAddress());
			memberContact.setVillage(contact.getVillage());
			memberContact.setGrampanchayat(contact.getGrampanchayat());
			memberContact.setTaluka(contact.getTaluka());

		} else {
			contact.setAddress(memberContact.getAddress());
			contact.setVillage(memberContact.getVillage());
			contact.setGrampanchayat(memberContact.getGrampanchayat());
			contact.setTaluka(memberContact.getTaluka());
		}
		contact.setPinCode(memberContact.getPinCode());
		contact.setPhone(memberContact.getPhone());
		contact.setPriMobile(memberContact.getPriMobile());
		contact.setSecMobile(memberContact.getSecMobile());
		contact.setEmail(memberContact.getEmail());

		daoFactory.getContactDAO().persist(contact);

		// Created & Save Member Contact details
		MemberContact mContact = new MemberContact();

		mContact.setContact(contact);
		mContact.setLang(daoFactory.getLangDAO().getReferenceByValue(memberContact.getLang()));
		mContact.setNameTitle(daoFactory.getNameTitleDAO().getReferenceByValue(memberContact.getNameTitle(), memberContact.getLang()));
		if(memberContact.getLang().equals(EnumConst.Lang_English)) {
			mContact.setFirstName(WordUtils.capitalizeFully(memberContact.getFirstName()));
			mContact.setMiddleName(WordUtils.capitalizeFully(memberContact.getMiddleName()));
			mContact.setLastName(WordUtils.capitalizeFully(memberContact.getLastName()));

			// Just push back any updates done by WordUtils.capitalizeFully
			memberContact.setFirstName(mContact.getFirstName());
			memberContact.setMiddleName(mContact.getMiddleName());
			memberContact.setLastName(mContact.getLastName());
		} else {
			mContact.setFirstName(memberContact.getFirstName());
			mContact.setMiddleName(memberContact.getMiddleName());
			mContact.setLastName(memberContact.getLastName());
		}
		// Load Data Back for return 
		memberContact.setContactId(contact.getContactId());

		return mContact;
	}

	public MemberREST updateMemberContacts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		return updateMemberContacts(member, mProfile);
	}

	protected MemberREST updateMemberContacts(MemberREST member, MProfile mProfile) throws BadRequestException {

		Set<MemberContact> contacts = mProfile.getMemberContacts();
		Set<Long> contactIds = new HashSet<Long>();

		for(MemberContact contact : contacts) {
			contactIds.add(contact.getContactId());
		}

		for(MemberContactREST contactREST : member.getContacts()) {

			if(contactIds.contains(contactREST.getContactId())) {

				MemberContact mContact = updateMemberContact(contactREST, mProfile.getMemberAcNo());
				daoFactory.getMemberContactDAO().merge(mContact);
				contacts.add(mContact);
			}
		}

		return member;
	}

	protected MemberContact updateMemberContact(MemberContactREST memberContact, long memberAcNo) throws BadRequestException {

		if(memberContact.getContactId() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Member Contact ID");
		}

		// Load Member Contact details
		MemberContact mContact = daoFactory.getMemberContactDAO().findById(memberContact.getContactId());

		// Load Contact details
		Contact contact = mContact.getContact();

		contact.setDistrict(daoFactory.getDistrictDAO().getDistrictFromName(memberContact.getState(), memberContact.getDistrict()));
		if(memberContact.getLang().equals(EnumConst.Lang_English)) {
			contact.setAddress(WordUtils.capitalizeFully(memberContact.getAddress()));
			contact.setVillage(WordUtils.capitalizeFully(memberContact.getVillage()));
			contact.setGrampanchayat(WordUtils.capitalizeFully(memberContact.getGrampanchayat()));
			contact.setTaluka(WordUtils.capitalizeFully(memberContact.getTaluka()));

			// Just push back any updates done by WordUtils.capitalizeFully
			memberContact.setAddress(contact.getAddress());
			memberContact.setVillage(contact.getVillage());
			memberContact.setGrampanchayat(contact.getGrampanchayat());
			memberContact.setTaluka(contact.getTaluka());

		} else {
			contact.setAddress(memberContact.getAddress());
			contact.setVillage(memberContact.getVillage());
			contact.setGrampanchayat(memberContact.getGrampanchayat());
			contact.setTaluka(memberContact.getTaluka());
		}
		contact.setPinCode(memberContact.getPinCode());
		contact.setPhone(memberContact.getPhone());
		contact.setPriMobile(memberContact.getPriMobile());
		contact.setSecMobile(memberContact.getSecMobile());
		contact.setEmail(memberContact.getEmail());

		try {
			// Update Or Remove Mobile & Email service
			MMessageREST mMessage = new MMessageREST();
			mMessage.setMemberAcNo(memberAcNo);
			if(memberContact.getPriMobile() != null && memberContact.getPriMobile().trim().length() >= 10) {
				mMessage.setMobileNo(ConversionUtil.parseMobileNo(memberContact.getPriMobile()));
				mMessage.setEmail(memberContact.getEmail());

				messageBO.addUpdateMMessage(mMessage);
			} else {
				messageBO.removeMMessage(mMessage);
			}
		} catch(Exception e) {
			contact.setPriMobile(DataUtil.EMPTY_STRING);
			memberContact.setPriMobile(DataUtil.EMPTY_STRING);
			throw e;
		} finally {
			daoFactory.getContactDAO().merge(contact);
		}

		mContact.setContact(contact);
		mContact.setLang(daoFactory.getLangDAO().getReferenceByValue(memberContact.getLang()));
		mContact.setNameTitle(daoFactory.getNameTitleDAO().getReferenceByValue(memberContact.getNameTitle(), memberContact.getLang()));
		if(memberContact.getLang().equals(EnumConst.Lang_English)) {
			mContact.setFirstName(WordUtils.capitalizeFully(memberContact.getFirstName()));
			mContact.setMiddleName(WordUtils.capitalizeFully(memberContact.getMiddleName()));
			mContact.setLastName(WordUtils.capitalizeFully(memberContact.getLastName()));

			// Just push back any updates done by WordUtils.capitalizeFully
			memberContact.setFirstName(mContact.getFirstName());
			memberContact.setMiddleName(mContact.getMiddleName());
			memberContact.setLastName(mContact.getLastName());
		} else {
			mContact.setFirstName(memberContact.getFirstName());
			mContact.setMiddleName(memberContact.getMiddleName());
			mContact.setLastName(memberContact.getLastName());
		}

		return mContact;
	}

	public void removeMemberContacts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		for(MemberContact contact : mProfile.getMemberContacts()) {
			daoFactory.getMemberContactDAO().remove(contact);
		}
	}

	public MemberREST addMemberBankAccounts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		Set<MBankAccount> mBankAccounts = mProfile.getMBankAccounts();

		for(BankAccountREST bankAccontREST : member.getBankAccounts()) {
			MBankAccount mBankAccount = addMemberBankAccount(bankAccontREST);
			mBankAccount.setMProfile(mProfile);

			daoFactory.getMBankAccountDAO().persist(mBankAccount);

			bankAccontREST.setBankAccountId(mBankAccount.getBankAccountNo());
			mBankAccounts.add(mBankAccount);
		}

		return member;
	}

	protected MBankAccount addMemberBankAccount(BankAccountREST memberBankAccount) throws BadRequestException {

		if(memberBankAccount.getBankProfileId() <= 0 && 
				(memberBankAccount.getIfcsCode() == null || memberBankAccount.getIfcsCode().isEmpty() ||
				memberBankAccount.getBankBranchName() == null || memberBankAccount.getBankBranchName().isEmpty())) {			
			throw new BadRequestException("Invalid Bank Profile Id & Invalid IFCS Code");
		}

		BankProfile bankProfile = null;
		if(memberBankAccount.getBankProfileId() > 0) {
			bankProfile = daoFactory.getBankProfileDAO().getReferenceById(memberBankAccount.getBankProfileId());
		} else {
			bankProfile = daoFactory.getBankProfileDAO().getBankForIFCSCode(memberBankAccount.getIfcsCode(), memberBankAccount.getBankBranchName());
		}
		if(bankProfile == null) {
			throw new BadRequestException("Invalid Bank Profile Id : " + memberBankAccount.getBankProfileId());
		}

		// Create & Save Banks Account details
		BankAccount bankAccount = new BankAccount();

		bankAccount.setAccountNumber(memberBankAccount.getAccountNumber());
		bankAccount.setAccountName(WordUtils.capitalizeFully(memberBankAccount.getAccountName()));
		bankAccount.setBankAccountTypeId(EnumCache.getIndexOfEnumValue(EnumConst.BankAccountType, memberBankAccount.getBankAccountType()));
		bankAccount.setBankProfile(bankProfile);

		memberBankAccount.setAccountName(bankAccount.getAccountName());

		daoFactory.getBankAccountDAO().persist(bankAccount);

		// Created & Save Member Bank Account details
		MBankAccount mBankAccount = new MBankAccount();

		mBankAccount.setBankAccount(bankAccount);

		//Load Bank Account Id back
		memberBankAccount.setBankAccountId(bankAccount.getBankAccountNo());

		return mBankAccount;
	}

	public MemberREST updateMemberBankAccounts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		return updateMemberBankAccounts(member, mProfile);
	}

	protected MemberREST updateMemberBankAccounts(MemberREST member, MProfile mProfile) throws BadRequestException {

		if(mProfile.getMBankAccounts() == null) {
			Set<Long> bankAccountIds = new HashSet<Long>();

			for(MBankAccount mBankAccount : mProfile.getMBankAccounts()) {
				bankAccountIds.add(mBankAccount.getBankAccountNo());
			}

			for(BankAccountREST bankAccountREST : member.getBankAccounts()) {

				if(bankAccountIds.contains(bankAccountREST.getBankAccountId())) {

					MBankAccount mBankAccount = updateMemberBankAccount(bankAccountREST);
					daoFactory.getMBankAccountDAO().merge(mBankAccount);
					mProfile.getMBankAccounts().add(mBankAccount);
				}
			}
		}

		return member;
	}

	protected MBankAccount updateMemberBankAccount(BankAccountREST memberBankAccount) throws BadRequestException {

		if(memberBankAccount.getBankAccountId() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Member Bank Account ID");
		}

		// Load Member Contact details
		MBankAccount mBankAccount = daoFactory.getMBankAccountDAO().findById(memberBankAccount.getBankAccountId());

		// Create & Save Banks Account details
		BankAccount bankAccount = mBankAccount.getBankAccount();

		bankAccount.setAccountNumber(memberBankAccount.getAccountNumber());
		bankAccount.setAccountName(WordUtils.capitalizeFully(memberBankAccount.getAccountName()));
		bankAccount.setBankAccountTypeId(EnumCache.getIndexOfEnumValue(EnumConst.BankAccountType, memberBankAccount.getBankAccountType()));

		memberBankAccount.setAccountName(bankAccount.getAccountName());

		daoFactory.getBankAccountDAO().merge(bankAccount);

		mBankAccount.setBankAccount(bankAccount);

		return mBankAccount;
	}

	public void removeMemberBankAccounts(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());

		for(MBankAccount mBankAccount : mProfile.getMBankAccounts()) {
			daoFactory.getMBankAccountDAO().remove(mBankAccount);
		}
	}

	public void mapMemberToGroups(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account No");
		}
		if(member.getMappingGroupAcNos() == null || member.getMappingGroupAcNos().isEmpty()) {
			throw new BadRequestException("Invalid Groups Accounts No");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(member.getMemberAcNo());
		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account");
		}

		// Get References of all Mapping Groups
		for(Long groupAcNo : member.getMappingGroupAcNos()) {
			if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
				throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
			}
			GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
			if(gProfile == null) {
				throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
			}
			mapMemberToGroup(mProfile, gProfile, EnumCache.getMRoleValue(mProfile.getMroleId()));
		}

	}

	protected void mapMemberToGroup(MProfile mProfile, GProfile gProfile, MRoleValue mRole) throws BadRequestException {

		GMId groupMemberId = new GMId(gProfile.getGAcNo(), mProfile.getMemberAcNo());
		GM groupMember = new GM(groupMemberId, gProfile.getGAcNo(), mProfile.getMemberAcNo());
		groupMember.setUiAccessRights(mRole.getDefaultUiAccessRights());
		groupMember.setWsAccessRights(mRole.getDefaultWsAccessRights());
		try {
			daoFactory.getGMDAO().persist(groupMember);		
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}

	public MemberREST getMemberCompleteData(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberAccount(mProfile, member);

		MemberREST.loadMemberContacts(mProfile, member);

		MemberREST.loadMemberBankAccounts(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public MemberREST getMyProfile(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberAccount(mProfile, member);

		MemberREST.loadMemberContacts(mProfile, member);

		MemberREST.loadMemberBankAccounts(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public MemberREST getMemberAccountsData(String lang, long memberAcNo, String status) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberContacts(mProfile, member);

		MemberREST.loadMemberAccount(mProfile, member);

		member.setMemberSavingAc(memberSavingAcBO.getMemberSavingAcsForMember(lang, memberAcNo, status));

		member.setMemberLoanAc(memberLoanAcBO.getMemberLoanAcsForMember(lang, memberAcNo, status));

		member.setDisplayNames(
				RestDisplayTitle.getMemberLoanAcRDT(
						RestDisplayTitle.getMemberSavingAcRDT(
								RestDisplayTitle.getMemberRDT(null))));

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));
		if(member.getRecommendedByMemberAcNo() > 0) {
			member.setRecommendedByMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, member.getRecommendedByMemberAcNo()));
		} else {
			member.setRecommendedByMemberName(DataUtil.EMPTY_STRING);
		}

		return member;
	}

	public MemberREST getMemberProfile(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public MemberREST getMemberAccount(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberAccount(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public MemberREST getMemberBankAccounts(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberBankAccounts(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public MemberREST getMemberContacts(String lang, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);

		if(mProfile == null) {
			throw new BadRequestException("Invalid Member Account No:" + memberAcNo);
		}

		MemberREST member = new MemberREST();

		MemberREST.loadMemberProfile(mProfile, member);

		MemberREST.loadMemberContacts(mProfile, member);

		member.setMemberName(daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo));

		return member;
	}

	public List<MemberREST> getAllGroupMembers(long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No:" + groupAcNo);
		}

		List<MProfile> allMProfiles = daoFactory.getMProfileDAO().getAllGroupMembers(groupAcNo);

		if(allMProfiles == null || allMProfiles.size() <= 0) {
			throw new BadRequestException("Invalid Group Account No:" + groupAcNo);
		}

		List<MemberREST> members = new ArrayList<MemberREST>(allMProfiles.size());
		MemberREST member = null;

		for(MProfile mProfile: allMProfiles) {

			member = new MemberREST();

			MemberREST.loadMemberProfile(mProfile, member);

			MemberREST.loadMemberContacts(mProfile, member);

			MemberREST.loadMemberBankAccounts(mProfile, member);

			MemberREST.loadMemberAccount(mProfile, member);

			members.add(member);
		}

		return members;
	}

	public MemberSearchInfo searchMemberByAcNo(String lang, long groupAcNo, long memberAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		if(ConversionUtil.getStartRangeMemberAc(groupAcNo) > memberAcNo || 
				ConversionUtil.getEndRangeMemberAc(groupAcNo) <= memberAcNo) {
			throw new BadRequestException("Member Account No, Do Not belong to the Group");
		}

		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
		String memberName = daoFactory.getMemberContactDAO().getNameOfMember(lang, memberAcNo);

		if(memberName == null || memberName.isEmpty()) {
			throw new BadRequestException("Invalid Member Account No");
		}

		List<MemberName> names = new ArrayList<MemberName>();
		names.add(new MemberName(memberAcNo, groupAcNo, memberName, 
				EnumCache.getNameOfMRole(mProfile.getMroleId()),
				EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()),
				daoFactory.getMBankAccountDAO().getMemberBankAccountsDisplay(memberAcNo)));

		return new MemberSearchInfo(groupAcNo, names,
				daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
	}

	public MemberSearchInfo searchMemberByName(String lang, long groupAcNo, String name) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		// Load Member Names
		GroupInfoCollector nameCol = new GroupInfoCollector();
		nameCol.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadActiveMemberNamesForGroup(lang, nameCol);

		// Load Member Bank Account
		BankAccountInfoCollector BankAccountCol = new BankAccountInfoCollector();
		BankAccountCol.setGroupAcNo(groupAcNo);
		daoFactory.getMBankAccountDAO().loadActiveMBankAccountForGroup(BankAccountCol);

		return new MemberSearchInfo(groupAcNo, 
				nameCol.searchMemberName(name, BankAccountCol),
				daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
	}

	public MemberSearchInfo searchAllMemberByName(String lang, long groupAcNo, String name) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		// Load Member Names
		GroupInfoCollector nameCol = new GroupInfoCollector();
		nameCol.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNamesForGroup(lang, nameCol);

		// Load Member Bank Account
		BankAccountInfoCollector BankAccountCol = new BankAccountInfoCollector();
		BankAccountCol.setGroupAcNo(groupAcNo);
		daoFactory.getMBankAccountDAO().loadMBankAccountForGroup(BankAccountCol);

		return new MemberSearchInfo(groupAcNo, 
				nameCol.searchMemberName(name, BankAccountCol),
				daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
	}

	public MemberSearchInfo searchAllMember(String lang, long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		// Load Member Names
		GroupInfoCollector nameCol = new GroupInfoCollector();
		nameCol.setGroupAcNo(groupAcNo);
		daoFactory.getMemberContactDAO().loadMemberNamesForGroup(lang, nameCol);

		return new MemberSearchInfo(groupAcNo, nameCol.searchMemberName(), null);
	}

	public List<Attachment> attachFile(long memberAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {

		String attachmentInfo = attachmentBO.saveFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
				fileName, docTypeId, fileByte);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.M_PROFILE, memberAcNo, dataFeilds));

		return Attachment.buildAttachments(attachmentInfo);
	}

	public List<Attachment> attachBankFile(long memberAcNo, long bankAccountNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {

		String attachmentInfo = attachmentBO.saveFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
				fileName, docTypeId, fileByte);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));

		return Attachment.buildAttachments(attachmentInfo);
	}

	public List<Attachment> updateAttachFile(long memberAcNo, long docId, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
		if(docId <= 0) {
			throw new BadRequestException("Invalid Doc!");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
		if(!mProfile.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
				(mProfile.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
			throw new BadRequestException("Invalid Doc ID for the Member Profile!");
		}

		String attachmentInfo = attachmentBO.updateFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
				docId, fileName, docTypeId, fileByte);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.M_PROFILE, memberAcNo, dataFeilds));

		return Attachment.buildAttachments(attachmentInfo);
	}

	public List<Attachment> updateAttachBankFile(long memberAcNo, long docId, long bankAccountNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
		if(docId <= 0) {
			throw new BadRequestException("Invalid Doc!");
		}
		BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(bankAccountNo);
		if(!bankAccount.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
				(bankAccount.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
			throw new BadRequestException("Invalid Doc ID for the Bank Account!");
		}

		String attachmentInfo = attachmentBO.updateFile(ConversionUtil.getGroupAcFromMember(memberAcNo), 
				docId, fileName, docTypeId, fileByte);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));

		return Attachment.buildAttachments(attachmentInfo);
	}

	public void deleteAttachFile(long memberAcNo, long docId) throws BadRequestException, IOException {
		if(docId <= 0) {
			throw new BadRequestException("Invalid Doc!");
		}
		MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
		if(!mProfile.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
				(mProfile.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
			throw new BadRequestException("Invalid Doc ID for the Member Profile!");
		}

		attachmentBO.deleteFile(ConversionUtil.getGroupAcFromMember(memberAcNo), docId);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.M_PROFILE, memberAcNo, dataFeilds));
	}

	public void deleteAttachBankFile(long memberAcNo, long docId, long bankAccountNo) throws BadRequestException, IOException {
		if(docId <= 0) {
			throw new BadRequestException("Invalid Doc!");
		}
		BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(bankAccountNo);
		if(!bankAccount.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
				(bankAccount.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
			throw new BadRequestException("Invalid Doc ID for the Bank Account!");
		}

		attachmentBO.deleteFile(ConversionUtil.getGroupAcFromMember(memberAcNo), docId);
		Map<String, String> dataFeilds = new HashMap<String, String>();
		dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));

		JobQueueManager.addToJobQueue(ConversionUtil.getGroupAcFromMember(memberAcNo), new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));
	}
}
