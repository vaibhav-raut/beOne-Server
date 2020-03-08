package com.beone.shg.net.persistence.ppo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.persistence.ppo.BGroupBankAccountPPO;
import com.beone.shg.net.persistence.ppo.GAcByTxtypePPO;
import com.beone.shg.net.persistence.ppo.GAcPPO;
import com.beone.shg.net.persistence.ppo.GBankAccountPPO;
import com.beone.shg.net.persistence.ppo.GInvtAcPPO;
import com.beone.shg.net.persistence.ppo.GLoanAcPPO;
import com.beone.shg.net.persistence.ppo.IProcessing;
import com.beone.shg.net.persistence.ppo.MAcPPO;
import com.beone.shg.net.persistence.ppo.MLoanAcPPO;
import com.beone.shg.net.persistence.ppo.MSavingAcPPO;

@Component("ppoFactory")
public class PPOFactory {
	
	@Autowired
	@Qualifier("bGroupBankAccountPPO")
	BGroupBankAccountPPO bGroupBankAccountPPO;
	
	@Autowired
	@Qualifier("gAcByTxtypePPO")
	GAcByTxtypePPO gAcByTxtypePPO;
	
	@Autowired
	@Qualifier("gAcPPO")
	private GAcPPO gAcPPO;
	
	@Autowired
	@Qualifier("gBankAccountPPO")
	GBankAccountPPO gBankAccountPPO;
	
	@Autowired
	@Qualifier("gInvtAcPPO")
	GInvtAcPPO gInvtAcPPO;
	
	@Autowired
	@Qualifier("gLoanAcPPO")
	GLoanAcPPO gLoanAcPPO;
	
	@Autowired
	@Qualifier("mAcPPO")
	private MAcPPO mAcPPO;
	
	@Autowired
	@Qualifier("mLoanAcPPO")
	MLoanAcPPO mLoanAcPPO;
	
	@Autowired
	@Qualifier("mSavingAcPPO")
	private MSavingAcPPO mSavingAcPPO;

	public IProcessing getPPO(String tableName) {
		
		switch(tableName) {

		case DBConst.B_GROUP_BANK_ACCOUNT: 
			return bGroupBankAccountPPO;

		case DBConst.G_AC_BY_TXTYPE: 
			return gAcByTxtypePPO;

		case DBConst.G_AC: 
			return gAcPPO;

		case DBConst.G_BANK_ACCOUNT: 
			return gBankAccountPPO;

		case DBConst.G_INVT_AC: 
			return gInvtAcPPO;

		case DBConst.G_LOAN_AC: 
			return gLoanAcPPO;

		case DBConst.M_AC: 
			return mAcPPO;

		case DBConst.M_LOAN_AC: 
			return mLoanAcPPO;

		case DBConst.M_SAVING_AC: 
			return mSavingAcPPO;

		}
		return null;
	}
	

	public BGroupBankAccountPPO getBGroupBankAccountPPO() {
		return bGroupBankAccountPPO;
	}

	public GAcByTxtypePPO getGAcByTxtypePPO() {
		return gAcByTxtypePPO;
	}

	public GAcPPO getgAcPPO() {
		return gAcPPO;
	}

	public GBankAccountPPO getGBankAccountPPO() {
		return gBankAccountPPO;
	}

	public GInvtAcPPO getGInvtAcPPO() {
		return gInvtAcPPO;
	}

	public GLoanAcPPO getGLoanAcPPO() {
		return gLoanAcPPO;
	}

	public MAcPPO getMAcPPO() {
		return mAcPPO;
	}

	public MLoanAcPPO getMLoanAcPPO() {
		return mLoanAcPPO;
	}

	public MSavingAcPPO getMSavingAcPPO() {
		return mSavingAcPPO;
	}

}
