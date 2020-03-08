package com.beone.udaan.mr.bo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.bo.BaseBO;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.persistence.model.MrVisit;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.service.model.MrAccount;
import com.beone.udaan.mr.service.model.MrVisitM;

@Component("dashboardMrBO")
public class DashboardMrBO extends BaseBO {

    public MrAccount getMrAccountSummary(long mrAcNo) throws BadRequestException {
	  	
    	if(!ConversionUtil.isValidMemberAcNo(mrAcNo)) {
			throw new BadRequestException("Invalid Member Account!");
		}
    	
    	PMAc pmAc = daoFactory.getPMAcDAO().findById(mrAcNo);
    	if(pmAc == null) {
			throw new BadRequestException("Invalid Member Account!");
		}

    	MrAccount mrAccount = MrAccount.getMrAccount(pmAc);
    	
    	List<MrVisit> mrVisits = daoFactory.getMrVisitDAO().getMrVisitForMR(mrAcNo, DateUtil.getBeforeDaysTime(90));
    	
    	if(mrVisits != null && !mrVisits.isEmpty()) {
    		MrVisitM.loadMRVisits(mrAccount, mrVisits);
    	}
    	
    	return mrAccount;
    }


}
