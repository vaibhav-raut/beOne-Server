package com.beone.shg.net.persistence.spo.fac;

import com.beone.shg.net.persistence.spo.GenericPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public interface AlgoFactoryI {
	public GenericPO getAlgo(String type, GroupAcInfo groupAcInfo);
}
