package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.MonthlyMAcSavePO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MonthlyMAcSaveFactory implements AlgoFactoryI {
	private static MonthlyMAcSaveFactory instance;

	static {
		instance = new MonthlyMAcSaveFactory();
	}

	public static MonthlyMAcSaveFactory getInstance() {
		return instance;
	}

	private MonthlyMAcSaveFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MonthlyMAcSavePO(groupAcInfo);
		
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
