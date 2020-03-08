package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.MissedTodoTxPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;


public class MissedTodoTxFactory implements AlgoFactoryI {
	private static MissedTodoTxFactory instance;

	static {
		instance = new MissedTodoTxFactory();
	}

	public static MissedTodoTxFactory getInstance() {
		return instance;
	}

	private MissedTodoTxFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MissedTodoTxPO(groupAcInfo);
		
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
