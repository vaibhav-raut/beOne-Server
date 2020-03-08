package com.beone.udaan.mr.ppo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.udaan.mr.config.DBConstMr;
import com.beone.udaan.mr.ppo.BrandPPO;
import com.beone.udaan.mr.ppo.IMrProcessing;
import com.beone.udaan.mr.ppo.LotPPO;
import com.beone.udaan.mr.ppo.MrVisitPPO;
import com.beone.udaan.mr.ppo.PAuthAcPPO;
import com.beone.udaan.mr.ppo.PHubAcPPO;
import com.beone.udaan.mr.ppo.POwnerAcPPO;
import com.beone.udaan.mr.ppo.StockTypePPO;

@Component("ppoMrFactory")
public class PPOMrFactory {
	
	@Autowired
	@Qualifier("brandPPO")
	BrandPPO brandPPO;
	
	@Autowired
	@Qualifier("lotPPO")
	LotPPO lotPPO;
	
	@Autowired
	@Qualifier("mrVisitPPO")
	MrVisitPPO mrVisitPPO;
	
	@Autowired
	@Qualifier("pHubAcPPO")
	PHubAcPPO pHubAcPPO;
	
	@Autowired
	@Qualifier("pAuthAcPPO")
	PAuthAcPPO pAuthAcPPO;
	
	@Autowired
	@Qualifier("pOwnerAcPPO")
	POwnerAcPPO pOwnerAcPPO;
	
	@Autowired
	@Qualifier("stockTypePPO")
	StockTypePPO stockTypePPO;

	public IMrProcessing getPPO(String tableName) {
		
		switch(tableName) {

		case DBConstMr.BRAND: 
			return brandPPO;

		case DBConstMr.LOT: 
			return lotPPO;

		case DBConstMr.MR_VISIT: 
			return mrVisitPPO;

		case DBConstMr.P_HUB_AC: 
			return pHubAcPPO;

		case DBConstMr.P_AUTH_AC: 
			return pAuthAcPPO;

		case DBConstMr.P_OWNER_AC: 
			return pOwnerAcPPO;

		case DBConstMr.STOCK_TYPE: 
			return stockTypePPO;

		}
		return null;
	}

	public BrandPPO getBrandPPO() {
		return brandPPO;
	}

	public LotPPO getLotPPO() {
		return lotPPO;
	}

	public MrVisitPPO getMrVisitPPO() {
		return mrVisitPPO;
	}

	public PHubAcPPO getPHubAcPPO() {
		return pHubAcPPO;
	}

	public PAuthAcPPO getPAuthAcPPO() {
		return pAuthAcPPO;
	}

	public POwnerAcPPO getPOwnerAcPPO() {
		return pOwnerAcPPO;
	}

	public StockTypePPO getStockTypePPO() {
		return stockTypePPO;
	}

}
