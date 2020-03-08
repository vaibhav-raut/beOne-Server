package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.MSIComputPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;


public class SIComFactory implements AlgoFactoryI {
	private static SIComFactory instance;

	static {
		instance = new SIComFactory();
	}

	public static SIComFactory getInstance() {
		return instance;
	}

	private SIComFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MSIComputPO(groupAcInfo);
		
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
