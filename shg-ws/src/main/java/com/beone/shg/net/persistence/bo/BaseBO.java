package com.beone.shg.net.persistence.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.beone.shg.net.persistence.ppo.util.PPOFactory;
import com.beone.shg.net.persistence.ppo.util.ProcessJobBuilder;
import com.beone.shg.net.persistence.util.DAOFactory;

public class BaseBO {

	@Autowired
	@Qualifier("daoFactory")
	protected DAOFactory daoFactory;

	@Autowired
	@Qualifier("ppoFactory")
	protected PPOFactory ppoFactory;

	@Autowired
	@Qualifier("processJobBuilder")
	protected ProcessJobBuilder processJobBuilder;

}
