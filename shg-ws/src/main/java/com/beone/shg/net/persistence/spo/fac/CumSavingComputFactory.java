package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.CumSavingComputPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;


public class CumSavingComputFactory implements AlgoFactoryI {
	private static CumSavingComputFactory instance;

	static {
		instance = new CumSavingComputFactory();
	}

	public static CumSavingComputFactory getInstance() {
		return instance;
	}

	private CumSavingComputFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new CumSavingComputPO(groupAcInfo);
		
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
