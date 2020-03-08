package com.beone.udaan.mr.ppo;

import java.util.Map;

import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFactory;
import com.beone.udaan.mr.ppo.util.PPOMrFormula;

public abstract class ProcessJobMr {

	protected DAOFactory daoFactory;
	protected PPOMrFactory ppoFactory;
	protected PPOMrFormula p1ActionFormula;
	protected PPOMrFormula p2ActionFormula;
	protected PPOMrFormula p3ActionFormula;
	@SuppressWarnings("rawtypes")
	protected Map<Class, Object> relatedObject;

	public ProcessJobMr(DAOFactory daoFactory, PPOMrFactory ppoFactory,
			PPOMrFormula p1ActionFormula, PPOMrFormula p2ActionFormula,
			PPOMrFormula p3ActionFormula) {
		super();
		this.daoFactory = daoFactory;
		this.ppoFactory = ppoFactory;
		this.p1ActionFormula = p1ActionFormula;
		this.p2ActionFormula = p2ActionFormula;
		this.p3ActionFormula = p3ActionFormula;
	}

	public void execute() {		
		
		if(p1ActionFormula != null) {
			for(String tableName: p1ActionFormula.getTableNames()) {
				IMrProcessing ppo = ppoFactory.getPPO(tableName);
				if(ppo != null) {
					ppo.processUpdateFormula(this, p1ActionFormula.getTableFormula(tableName));
				}
			}
		}

		if(p2ActionFormula != null) {
			for(String tableName: p2ActionFormula.getTableNames()) {
				IMrProcessing ppo = ppoFactory.getPPO(tableName);
				if(ppo != null) {
					ppo.processUpdateFormula(this, p2ActionFormula.getTableFormula(tableName));
				}
			}
		}

		if(p3ActionFormula != null) {
			for(String tableName: p3ActionFormula.getTableNames()) {
				IMrProcessing ppo = ppoFactory.getPPO(tableName);
				if(ppo != null) {
					ppo.processUpdateFormula(this, p3ActionFormula.getTableFormula(tableName));
				}
			}
		}
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}
	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	public PPOMrFactory getPpoFactory() {
		return ppoFactory;
	}
	public void setPpoFactory(PPOMrFactory ppoFactory) {
		this.ppoFactory = ppoFactory;
	}
	public PPOMrFormula getP1ActionFormula() {
		return p1ActionFormula;
	}

	public void setP1ActionFormula(PPOMrFormula p1ActionFormula) {
		this.p1ActionFormula = p1ActionFormula;
	}

	public PPOMrFormula getP2ActionFormula() {
		return p2ActionFormula;
	}

	public void setP2ActionFormula(PPOMrFormula p2ActionFormula) {
		this.p2ActionFormula = p2ActionFormula;
	}

	public PPOMrFormula getP3ActionFormula() {
		return p3ActionFormula;
	}

	public void setP3ActionFormula(PPOMrFormula p3ActionFormula) {
		this.p3ActionFormula = p3ActionFormula;
	}

	@SuppressWarnings("rawtypes")
	public Map<Class, Object> getRelatedObject() {
		return relatedObject;
	}
	public void setRelatedObject(@SuppressWarnings("rawtypes") Map<Class, Object> relatedObject) {
		this.relatedObject = relatedObject;
	}
}
