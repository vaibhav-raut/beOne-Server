package com.beone.shg.common.web.security.role;

import java.util.ArrayList;


public class SecuredControllerList extends ArrayList<String> {

	public SecuredControllerList(ControllerListProvider controllerListProvider){
		super(controllerListProvider.getControllerList());
	}
}
