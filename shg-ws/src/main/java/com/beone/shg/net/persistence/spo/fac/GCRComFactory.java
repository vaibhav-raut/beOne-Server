package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GCRComputPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class GCRComFactory implements AlgoFactoryI {
	private static GCRComFactory instance;

	static {
		instance = new GCRComFactory();
	}

	public static GCRComFactory getInstance() {
		return instance;
	}

	private GCRComFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new GCRComputPO(groupAcInfo);
		
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
