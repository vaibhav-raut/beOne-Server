package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.MBuildTodoTxsPO;
import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public class MBuildTodoTxsFactory implements AlgoFactoryI {
	private static MBuildTodoTxsFactory instance;

	static {
		instance = new MBuildTodoTxsFactory();
	}

	public static MBuildTodoTxsFactory getInstance() {
		return instance;
	}

	private MBuildTodoTxsFactory() {
	}

	@Override
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo) {
		
		// TODO - Multiple Types of Computation PO
		return new MBuildTodoTxsPO(groupAcInfo);
		
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
