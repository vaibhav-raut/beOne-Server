package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.MCRComputPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MCRComFactory implements AlgoFactoryI {
	private static MCRComFactory instance;

	static {
		instance = new MCRComFactory();
	}

	public static MCRComFactory getInstance() {
		return instance;
	}

	private MCRComFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MCRComputPO(groupAcInfo);
		
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
