package com.beone.shg.net.persistence.mpo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.persistence.mpo.GAcMPO;
import com.beone.shg.net.persistence.mpo.IMProcessing;
import com.beone.shg.net.persistence.mpo.MAcMPO;
import com.beone.shg.net.persistence.mpo.MLoanAcMPO;
import com.beone.shg.net.persistence.mpo.TxBcMPO;
import com.beone.shg.net.persistence.mpo.TxBpiiMPO;
import com.beone.shg.net.persistence.mpo.TxIcMPO;
import com.beone.shg.net.persistence.mpo.TxLfMPO;
import com.beone.shg.net.persistence.mpo.TxLfOutMPO;
import com.beone.shg.net.persistence.mpo.TxLpfMPO;
import com.beone.shg.net.persistence.mpo.TxMPO;
import com.beone.shg.net.persistence.mpo.TxPiiMPO;
import com.beone.shg.net.persistence.mpo.TxRfMPO;

@Component("mpoFactory")
public class MPOFactory {
	
	@Autowired
	@Qualifier("gAcMPO")
	private GAcMPO gAcMPO;
	
	@Autowired
	@Qualifier("mAcMPO")
	private MAcMPO mAcMPO;
	
	@Autowired
	@Qualifier("mLoanAcMPO")
	private MLoanAcMPO mLoanAcMPO;
	
	@Autowired
	@Qualifier("txMPO")
	private TxMPO txMPO;
	
	@Autowired
	@Qualifier("txBcMPO")
	private TxBcMPO txBcMPO;
	
	@Autowired
	@Qualifier("txBpiiMPO")
	private TxBpiiMPO txBpiiMPO;
	
	@Autowired
	@Qualifier("txIcMPO")
	private TxIcMPO txIcMPO;
	
	@Autowired
	@Qualifier("txLfMPO")
	private TxLfMPO txLfMPO;
	
	@Autowired
	@Qualifier("txLfOutMPO")
	private TxLfOutMPO txLfOutMPO;
	
	@Autowired
	@Qualifier("txLpfMPO")
	private TxLpfMPO txLpfMPO;
	
	@Autowired
	@Qualifier("txPiiMPO")
	private TxPiiMPO txPiiMPO;
	
	@Autowired
	@Qualifier("txRfMPO")
	private TxRfMPO txRfMPO;

	public IMProcessing getMPO(String tableName) {
		
		switch(tableName) {

//		case DBConst.B_GROUP_BANK_ACCOUNT: 
//			return bGroupBankAccountPPO;
//
//		case DBConst.G_AC_BY_TXTYPE: 
//			return gAcByTxtypePPO;

		case DBConst.G_AC: 
			return gAcMPO;

//		case DBConst.G_BANK_ACCOUNT: 
//			return gBankAccountPPO;
//
//		case DBConst.G_INVT_AC: 
//			return gInvtAcPPO;
//
//		case DBConst.G_LOAN_AC: 
//			return gLoanAcPPO;

		case DBConst.M_AC: 
			return mAcMPO;

		case DBConst.M_LOAN_AC: 
			return mLoanAcMPO;

//		case DBConst.M_SAVING_AC: 
//			return mSavingAcPPO;

		case DBConst.TX: 
			return txMPO;

		case DBConst.TX_BC: 
			return txBcMPO;

		case DBConst.TX_BPII: 
			return txBpiiMPO;

		case DBConst.TX_IC: 
			return txIcMPO;

		case DBConst.TX_LF: 
			return txLfMPO;

		case DBConst.TX_LF_OUT: 
			return txLfOutMPO;

		case DBConst.TX_LPF: 
			return txLpfMPO;

		case DBConst.TX_PII: 
			return txPiiMPO;

		case DBConst.TX_RF: 
			return txRfMPO;

		}
		return null;
	}

}
