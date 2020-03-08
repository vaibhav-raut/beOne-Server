package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.MBuildOutSavingPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MBuildOutSavingFactory implements AlgoFactoryI {
	private static MBuildOutSavingFactory instance;

	static {
		instance = new MBuildOutSavingFactory();
	}

	public static MBuildOutSavingFactory getInstance() {
		return instance;
	}

	private MBuildOutSavingFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MBuildOutSavingPO(groupAcInfo);
		
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
