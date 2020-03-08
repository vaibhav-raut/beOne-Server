package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.MBuildCleanOutSavingPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MBuildCleanOutSavingFactory implements AlgoFactoryI {
	private static MBuildCleanOutSavingFactory instance;

	static {
		instance = new MBuildCleanOutSavingFactory();
	}

	public static MBuildCleanOutSavingFactory getInstance() {
		return instance;
	}

	private MBuildCleanOutSavingFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MBuildCleanOutSavingPO(groupAcInfo);
		
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
