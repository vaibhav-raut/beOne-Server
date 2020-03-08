package com.beone.shg.net.persistence.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.rest.DistrictAppREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("districtBO")
public class DistrictBO extends BaseBO {

	@Autowired
	@Qualifier("enumBO")
	private EnumBO enumBO;
	
	public DistrictAppREST getDistrictApplications(String lang, long districtId) throws BadRequestException {
		
		if(districtId <= 0) {
			throw new BadRequestException("Invalid District Id");
		}
		
		DistrictAppREST applications = new DistrictAppREST(districtId);
		
		// Member Account
		List<GProfile> gProfiles = daoFactory.getGProfileDAO().getGroupApplications((int)districtId);
		
		if(gProfiles != null && !gProfiles.isEmpty()) {
			
			for(GProfile gProfile: gProfiles) {
				GroupREST group = new GroupREST();

				GroupREST.loadGroupProfile(gProfile, group);
				
				GroupREST.loadGroupContacts(gProfile, group);
				
				GroupREST.loadGroupBankAccounts(gProfile, group);
				
				for(GRules gRules: gProfile.getGRuleses()) {
					GroupREST.loadGroupRules(gRules, group);
				}
				
				applications.addGroup(group);
			}
		}

		applications.setDisplayNames(RestDisplayTitle.getApplicationRDT());

		return applications;
	}
	
	public DistrictAppREST getGroupApplicationsForMember(String lang, long memberAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No");
		}
		
		DistrictAppREST applications = new DistrictAppREST(0);
		
		// Member Account
		List<GProfile> gProfiles = daoFactory.getGProfileDAO().getGroupApplicationsForMember(memberAcNo);
		
		if(gProfiles != null && !gProfiles.isEmpty()) {
			
			for(GProfile gProfile: gProfiles) {
				GroupREST group = new GroupREST();

				GroupREST.loadGroupProfile(gProfile, group);
				
				GroupREST.loadGroupContacts(gProfile, group);
				
				GroupREST.loadGroupBankAccounts(gProfile, group);
				
				for(GRules gRules: gProfile.getGRuleses()) {
					GroupREST.loadGroupRules(gRules, group);
				}
				
				applications.addGroup(group);
			}
		}

		applications.setDisplayNames(RestDisplayTitle.getApplicationRDT());

		return applications;
	}
	
	public void addDistrictsCSV(String lang, List<String[]> rawDistricts) throws BadRequestException {
		
		CSVDataCollector csvData = new CSVDataCollector(rawDistricts);

		if(!DistrictsEnum.isDistrictCSVValid(csvData)) {
			throw new BadRequestException("Invalid District CSV Data!");
		}

		DistrictsEnum districtsEnum = new DistrictsEnum(lang, csvData);

		enumBO.addDistricts(districtsEnum);		
	}
}
