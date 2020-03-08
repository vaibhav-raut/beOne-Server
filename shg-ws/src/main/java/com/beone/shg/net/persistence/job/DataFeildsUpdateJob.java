package com.beone.shg.net.persistence.job;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.persistence.model.BankAccount;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MonthlyGAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.util.DAOFactory;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;

public class DataFeildsUpdateJob implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(DataFeildsUpdateJob.class);
	
	protected DAOFactory daoFactory;	
	protected String algoType;
	protected String objectType;
	protected long id;
	protected Map<String, String> dataFeilds;
	
	public DataFeildsUpdateJob(DAOFactory daoFactory, String algoType, String objectType, long id, Map<String, String> dataFeilds) {
		super();
		this.daoFactory = daoFactory;
		this.algoType = algoType;
		this.objectType = objectType;
		this.id = id;
		this.dataFeilds = dataFeilds;	
	}

	@Override
	public void run() {		
		// Execute Data Update Job
		try {

			switch(algoType) {

			case DBConst.ATTACH_ALGO_ADD:
				switch(objectType) {

				case DBConst.BANK_ACCOUNT:
					bankAcAdd();
					break;

				case DBConst.G_INVT_AC:
					groupInvtAcAdd();
					break;

				case DBConst.G_LOAN_AC:
					groupLoanAcAdd();
					break;

				case DBConst.G_PROFILE:
					groupProfileAdd();
					break;

				case DBConst.M_LOAN_AC:
					memberLoanAcAdd();
					break;

				case DBConst.MONTHLY_G_AC:
					monthlyGroupAcAdd();
					break;

				case DBConst.M_PROFILE:
					memberProfileAdd();
					break;

				case DBConst.TX:
					txAdd();
					break;
				}
				break;

			case DBConst.ATTACH_ALGO_UPDATE:
				switch(objectType) {

				case DBConst.BANK_ACCOUNT:
					bankAcUpdate();
					break;

				case DBConst.G_INVT_AC:
					groupInvtAcUpdate();
					break;

				case DBConst.G_LOAN_AC:
					groupLoanAcUpdate();
					break;

				case DBConst.G_PROFILE:
					groupProfileUpdate();
					break;

				case DBConst.M_LOAN_AC:
					memberLoanAcUpdate();
					break;

				case DBConst.MONTHLY_G_AC:
					monthlyGroupAcUpdate();
					break;

				case DBConst.M_PROFILE:
					memberProfileUpdate();
					break;

				case DBConst.TX:
					txUpdate();
					break;
				}			
				break;

			case DBConst.ATTACH_ALGO_DELETE:
				switch(objectType) {

				case DBConst.BANK_ACCOUNT:
					bankAcDelete();
					break;

				case DBConst.G_INVT_AC:
					groupInvtAcDelete();
					break;

				case DBConst.G_LOAN_AC:
					groupLoanAcDelete();
					break;

				case DBConst.G_PROFILE:
					groupProfileDelete();
					break;

				case DBConst.M_LOAN_AC:
					memberLoanAcDelete();
					break;

				case DBConst.MONTHLY_G_AC:
					monthlyGroupAcDelete();
					break;

				case DBConst.M_PROFILE:
					memberProfileDelete();
					break;

				case DBConst.TX:
					txDelete();
					break;
				}			
				break;
			}

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void bankAcAdd() {		
		try {
	    	
			BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(bankAccount.getAttachmentUrl() == null || bankAccount.getAttachmentUrl().isEmpty()) {
						bankAccount.setAttachmentUrl(dataFeild.getValue());
					} else {
						bankAccount.setAttachmentUrl(bankAccount.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getBankAccountDAO().merge(bankAccount);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupInvtAcAdd() {		
		try {
	    	
			GInvtAc gInvtAc = daoFactory.getGInvtAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(gInvtAc.getAttachmentUrl() == null || gInvtAc.getAttachmentUrl().isEmpty()) {
						gInvtAc.setAttachmentUrl(dataFeild.getValue());
					} else {
						gInvtAc.setAttachmentUrl(gInvtAc.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGInvtAcDAO().merge(gInvtAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupLoanAcAdd() {		
		try {
	    	
			GLoanAc gLoanAc = daoFactory.getGLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(gLoanAc.getAttachmentUrl() == null || gLoanAc.getAttachmentUrl().isEmpty()) {
						gLoanAc.setAttachmentUrl(dataFeild.getValue());
					} else {
						gLoanAc.setAttachmentUrl(gLoanAc.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGLoanAcDAO().merge(gLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupProfileAdd() {		
		try {
	    	
	    	GProfile gProfile = daoFactory.getGProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(gProfile.getAttachmentUrl() == null || gProfile.getAttachmentUrl().isEmpty()) {
						gProfile.setAttachmentUrl(dataFeild.getValue());
					} else {
				    	gProfile.setAttachmentUrl(gProfile.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				}
			}
	    	
	    	daoFactory.getGProfileDAO().merge(gProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberLoanAcAdd() {		
		try {
	    	
			MLoanAc mLoanAc = daoFactory.getMLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(mLoanAc.getAttachmentUrl() == null || mLoanAc.getAttachmentUrl().isEmpty()) {
						mLoanAc.setAttachmentUrl(dataFeild.getValue());
					} else {
						mLoanAc.setAttachmentUrl(mLoanAc.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMLoanAcDAO().merge(mLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void monthlyGroupAcAdd() {		
		try {
	    	
			MonthlyGAc monthlyGAc = daoFactory.getMonthlyGAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(monthlyGAc.getAttachmentUrl() == null || monthlyGAc.getAttachmentUrl().isEmpty()) {
						monthlyGAc.setAttachmentUrl(dataFeild.getValue());
					} else {
						monthlyGAc.setAttachmentUrl(monthlyGAc.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMonthlyGAcDAO().merge(monthlyGAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberProfileAdd() {		
		try {
	    	
	    	MProfile mProfile = daoFactory.getMProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(mProfile.getAttachmentUrl() == null || mProfile.getAttachmentUrl().isEmpty()) {
						mProfile.setAttachmentUrl(dataFeild.getValue());
					} else {
						mProfile.setAttachmentUrl(mProfile.getAttachmentUrl() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMProfileDAO().merge(mProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void txAdd() {		
		try {
	    	
	    	Tx tx = daoFactory.getTxDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(tx.getEntryLocation() == null || tx.getEntryLocation().isEmpty()) {
						tx.setEntryLocation(DBConst.ATTACH_ADDED_DILIMITER + dataFeild.getValue());
					} else {
						String[] parts = tx.getEntryLocation().split(DBConst.ATTACH_ADDED_DILIMITER);
						if(parts == null || parts.length == 0) {
							tx.setEntryLocation(DBConst.ATTACH_ADDED_DILIMITER + dataFeild.getValue());
						} else if(parts.length == 1 || parts[1] == null || parts[1].isEmpty()) {
							tx.setEntryLocation(tx.getEntryLocation() + dataFeild.getValue());
						} else {
							tx.setEntryLocation(tx.getEntryLocation() + DBConst.ATTACH_EXTERNAL_DILIMITER + dataFeild.getValue());
						}
					}
			    	break;
				
				}
			}
	    	
	    	daoFactory.getTxDAO().merge(tx);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void bankAcUpdate() {		
		try {
	    	
			BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					bankAccount.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), bankAccount.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getBankAccountDAO().merge(bankAccount);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupInvtAcUpdate() {		
		try {
	    	
			GInvtAc gInvtAc = daoFactory.getGInvtAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gInvtAc.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), gInvtAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGInvtAcDAO().merge(gInvtAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupLoanAcUpdate() {		
		try {
	    	
			GLoanAc gLoanAc = daoFactory.getGLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gLoanAc.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), gLoanAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGLoanAcDAO().merge(gLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupProfileUpdate() {		
		try {
	    	
	    	GProfile gProfile = daoFactory.getGProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gProfile.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), gProfile.getAttachmentUrl()));
			    	break;
				}
			}
	    	
	    	daoFactory.getGProfileDAO().merge(gProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberLoanAcUpdate() {		
		try {
	    	
			MLoanAc mLoanAc = daoFactory.getMLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					mLoanAc.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), mLoanAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMLoanAcDAO().merge(mLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void monthlyGroupAcUpdate() {		
		try {
	    	
			MonthlyGAc monthlyGAc = daoFactory.getMonthlyGAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					monthlyGAc.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), monthlyGAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMonthlyGAcDAO().merge(monthlyGAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberProfileUpdate() {		
		try {
	    	
	    	MProfile mProfile = daoFactory.getMProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					mProfile.setAttachmentUrl(Attachment.updateAttachmentUrl(dataFeild.getValue(), mProfile.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMProfileDAO().merge(mProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void txUpdate() {		
		try {
	    	
	    	Tx tx = daoFactory.getTxDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(tx.getEntryLocation() != null && !tx.getEntryLocation().isEmpty()) {
						String[] parts = tx.getEntryLocation().split(DBConst.ATTACH_ADDED_DILIMITER);
						if(parts != null && parts.length >= 2) {
							String updated = Attachment.updateAttachmentUrl(dataFeild.getValue(), parts[1]);
							tx.setEntryLocation(parts[0] + DBConst.ATTACH_ADDED_DILIMITER + updated);
						} 
					}
			    	break;				
				}
			}
	    	
	    	daoFactory.getTxDAO().merge(tx);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void bankAcDelete() {		
		try {
	    	
			BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					bankAccount.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), bankAccount.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getBankAccountDAO().merge(bankAccount);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupInvtAcDelete() {		
		try {
	    	
			GInvtAc gInvtAc = daoFactory.getGInvtAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gInvtAc.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), gInvtAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGInvtAcDAO().merge(gInvtAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupLoanAcDelete() {		
		try {
	    	
			GLoanAc gLoanAc = daoFactory.getGLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gLoanAc.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), gLoanAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getGLoanAcDAO().merge(gLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void groupProfileDelete() {		
		try {
	    	
	    	GProfile gProfile = daoFactory.getGProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					gProfile.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), gProfile.getAttachmentUrl()));
			    	break;
				}
			}
	    	
	    	daoFactory.getGProfileDAO().merge(gProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberLoanAcDelete() {		
		try {
	    	
			MLoanAc mLoanAc = daoFactory.getMLoanAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					mLoanAc.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), mLoanAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMLoanAcDAO().merge(mLoanAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void monthlyGroupAcDelete() {		
		try {
	    	
			MonthlyGAc monthlyGAc = daoFactory.getMonthlyGAcDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					monthlyGAc.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), monthlyGAc.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMonthlyGAcDAO().merge(monthlyGAc);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void memberProfileDelete() {		
		try {
	    	
	    	MProfile mProfile = daoFactory.getMProfileDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					mProfile.setAttachmentUrl(Attachment.deleteAttachmentUrl(dataFeild.getValue(), mProfile.getAttachmentUrl()));
			    	break;
				
				}
			}
	    	
	    	daoFactory.getMProfileDAO().merge(mProfile);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
	
	public void txDelete() {		
		try {
	    	
	    	Tx tx = daoFactory.getTxDAO().findById(id);

	    	for(Map.Entry<String,String> dataFeild : dataFeilds.entrySet()) {
				
				switch(dataFeild.getKey()) {
				
				case DBConst.ATTACHMENT_URL:
					if(tx.getEntryLocation() != null && !tx.getEntryLocation().isEmpty()) {
						String[] parts = tx.getEntryLocation().split(DBConst.ATTACH_ADDED_DILIMITER);
						if(parts != null && parts.length >= 2) {
							String updated = Attachment.deleteAttachmentUrl(dataFeild.getValue(), parts[1]);
							tx.setEntryLocation(parts[0] + DBConst.ATTACH_ADDED_DILIMITER + updated);
						} 
					}
			    	break;				
				}
			}
	    	
	    	daoFactory.getTxDAO().merge(tx);

		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
	}
}
