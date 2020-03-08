package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.AutoApproveTxPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class AutoApproveFactory implements AlgoFactoryI {
	private static AutoApproveFactory instance;

	static {
		instance = new AutoApproveFactory();
	}

	public static AutoApproveFactory getInstance() {
		return instance;
	}

	private AutoApproveFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new AutoApproveTxPO(groupAcInfo);
		
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
