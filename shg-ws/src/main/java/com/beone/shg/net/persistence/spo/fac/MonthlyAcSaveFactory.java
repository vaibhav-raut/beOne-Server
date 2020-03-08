package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.MonthlyAcSavePO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MonthlyAcSaveFactory implements AlgoFactoryI {
	private static MonthlyAcSaveFactory instance;

	static {
		instance = new MonthlyAcSaveFactory();
	}

	public static MonthlyAcSaveFactory getInstance() {
		return instance;
	}

	private MonthlyAcSaveFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MonthlyAcSavePO(groupAcInfo);
		
//		switch(type) {
//
//		case "1":
//
//		case "2":
//
//		case "3":
//			
//		default:
//			
//		}
//		
//		return null;		
	}
}
