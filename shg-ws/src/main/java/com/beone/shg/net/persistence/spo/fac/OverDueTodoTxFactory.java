package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.OverDueTodoTxPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;


public class OverDueTodoTxFactory implements AlgoFactoryI {
	private static OverDueTodoTxFactory instance;

	static {
		instance = new OverDueTodoTxFactory();
	}

	public static OverDueTodoTxFactory getInstance() {
		return instance;
	}

	private OverDueTodoTxFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new OverDueTodoTxPO(groupAcInfo);
		
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
