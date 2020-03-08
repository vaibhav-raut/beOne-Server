package com.beone.shg.net.persistence.spo;

import com.beone.shg.net.persistence.spo.model.GroupAcInfo;

public abstract class GenericPO {
	protected GroupAcInfo groupAcInfo;
	
	public GenericPO(GroupAcInfo groupAcInfo) {
		super();
		this.groupAcInfo = groupAcInfo;
	}

	public abstract void execute();
}
