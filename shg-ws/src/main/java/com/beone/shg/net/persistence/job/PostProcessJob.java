package com.beone.shg.net.persistence.job;

import java.util.Map;

import com.beone.shg.net.persistence.ppo.IProcessing;
import com.beone.shg.net.persistence.ppo.util.PPOFactory;
import com.beone.shg.net.persistence.ppo.util.PPOFormula;
import com.beone.shg.net.persistence.util.DAOFactory;

@SuppressWarnings("rawtypes")
public class PostProcessJob implements Runnable {

	protected DAOFactory daoFactory;
	protected PPOFactory ppoFactory;
	protected String formulaString;
	protected PPOFormula formula;
	protected Map<Class, Object> relatedObject;
	protected Class amountClass;
	
	public PostProcessJob(DAOFactory daoFactory, PPOFactory ppoFactory) {
		super();
		this.daoFactory = daoFactory;
		this.ppoFactory = ppoFactory;
	}
	
	public PostProcessJob(DAOFactory daoFactory, PPOFactory ppoFactory, PPOFormula formula) {
		super();
		this.daoFactory = daoFactory;
		this.ppoFactory = ppoFactory;
		this.formula = formula;
	}

	@Override
	public void run() {		

		for(String tableName: formula.getTableNames()) {
			IProcessing ppo = ppoFactory.getPPO(tableName);
			if(ppo != null) {
				ppo.processUpdateFormula(this, formula.getTableFormula(tableName));
			}
		}
	}

	public DAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public PPOFactory getPpoFactory() {
		return ppoFactory;
	}

	public void setPpoFactory(PPOFactory ppoFactory) {
		this.ppoFactory = ppoFactory;
	}

	public String getFormulaString() {
		return formulaString;
	}

	public void setFormulaString(String formulaString) {
		this.formulaString = formulaString;
	}

	public PPOFormula getFormula() {
		return formula;
	}

	public void setFormula(PPOFormula formula) {
		this.formula = formula;
	}

	public Class getAmountClass() {
		return amountClass;
	}

	public void setAmountClass(Class amountClass) {
		this.amountClass = amountClass;
	}

	public Map<Class, Object> getRelatedObject() {
		return relatedObject;
	}

	public void setRelatedObject(Map<Class, Object> relatedObject) {
		this.relatedObject = relatedObject;
	}
}
