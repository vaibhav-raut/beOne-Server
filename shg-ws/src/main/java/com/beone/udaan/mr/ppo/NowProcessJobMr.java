package com.beone.udaan.mr.ppo;

import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFormula;

public class NowProcessJobMr extends ProcessJobMr {
	

	public NowProcessJobMr(DAOFactory daoFactory, PPOMrFactory ppoFactory,
			PPOMrFormula p1ActionFormula, PPOMrFormula p2ActionFormula,
			PPOMrFormula p3ActionFormula) {
		super(daoFactory, ppoFactory, p1ActionFormula, p2ActionFormula,
				p3ActionFormula);		
	}
}
