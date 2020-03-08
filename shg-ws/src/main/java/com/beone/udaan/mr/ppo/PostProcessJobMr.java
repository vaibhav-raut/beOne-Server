package com.beone.udaan.mr.ppo;

import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFormula;

public class PostProcessJobMr extends ProcessJobMr implements Runnable {
	

	public PostProcessJobMr(DAOFactory daoFactory, PPOMrFactory ppoFactory,
			PPOMrFormula preActionFormula, PPOMrFormula actionFormula,
			PPOMrFormula postActionFormula) {
		super(daoFactory, ppoFactory, preActionFormula, actionFormula,
				postActionFormula);
	}

	@Override
	public void run() {		
		execute();
	}
}
