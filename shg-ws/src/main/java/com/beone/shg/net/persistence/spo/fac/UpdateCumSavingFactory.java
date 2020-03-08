package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.UpdateCumSavingPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;


public class UpdateCumSavingFactory implements AlgoFactoryI {
	private static UpdateCumSavingFactory instance;

	static {
		instance = new UpdateCumSavingFactory();
	}

	public static UpdateCumSavingFactory getInstance() {
		return instance;
	}

	private UpdateCumSavingFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new UpdateCumSavingPO(groupAcInfo);
		
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
