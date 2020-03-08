package com.beone.shg.net.persistence.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.persistence.dao.AccountStatusDAO;
import com.beone.shg.net.persistence.dao.BankAcUploadDAO;
import com.beone.shg.net.persistence.dao.DocDAO;
import com.beone.shg.net.persistence.dao.DocTypeDAO;
import com.beone.shg.net.persistence.dao.GInvtAcDAO;
import com.beone.shg.net.persistence.dao.InvestmentTypeDAO;
import com.beone.shg.net.persistence.dao.ActiveStatusDAO;
import com.beone.shg.net.persistence.dao.AdvanceChequesDAO;
import com.beone.shg.net.persistence.dao.ApprovalStatusDAO;
import com.beone.shg.net.persistence.dao.BankAccountDAO;
import com.beone.shg.net.persistence.dao.BankAccountTypeDAO;
import com.beone.shg.net.persistence.dao.BankProfileDAO;
import com.beone.shg.net.persistence.dao.ContactDAO;
import com.beone.shg.net.persistence.dao.DistrictDAO;
import com.beone.shg.net.persistence.dao.GAcByTxtypeDAO;
import com.beone.shg.net.persistence.dao.GAcDAO;
import com.beone.shg.net.persistence.dao.GBankAccountDAO;
import com.beone.shg.net.persistence.dao.GLoanAcDAO;
import com.beone.shg.net.persistence.dao.GMDAO;
import com.beone.shg.net.persistence.dao.GProfileDAO;
import com.beone.shg.net.persistence.dao.GRulesDAO;
import com.beone.shg.net.persistence.dao.GroupContactDAO;
import com.beone.shg.net.persistence.dao.GroupRelationDAO;
import com.beone.shg.net.persistence.dao.GroupTypeDAO;
import com.beone.shg.net.persistence.dao.LangDAO;
import com.beone.shg.net.persistence.dao.LanguageMassageDAO;
import com.beone.shg.net.persistence.dao.LoanCalculationDAO;
import com.beone.shg.net.persistence.dao.FundTypeDAO;
import com.beone.shg.net.persistence.dao.LoanSourceDAO;
import com.beone.shg.net.persistence.dao.MAcDAO;
import com.beone.shg.net.persistence.dao.MBankAccountDAO;
import com.beone.shg.net.persistence.dao.MLoanAcDAO;
import com.beone.shg.net.persistence.dao.MMessageDAO;
import com.beone.shg.net.persistence.dao.MProfilingTypeDAO;
import com.beone.shg.net.persistence.dao.MSavingAcDAO;
import com.beone.shg.net.persistence.dao.MProfileDAO;
import com.beone.shg.net.persistence.dao.MRoleDAO;
import com.beone.shg.net.persistence.dao.MessageTypeDAO;
import com.beone.shg.net.persistence.dao.MobileMDAO;
import com.beone.shg.net.persistence.dao.MonthlyGBankAccountDAO;
import com.beone.shg.net.persistence.dao.MonthlyGInvtAcDAO;
import com.beone.shg.net.persistence.dao.MonthlyGLoanAcDAO;
import com.beone.shg.net.persistence.dao.MonthlyMAcDAO;
import com.beone.shg.net.persistence.dao.MonthlyMLoanAcDAO;
import com.beone.shg.net.persistence.dao.MonthlyMSavingAcDAO;
import com.beone.shg.net.persistence.dao.MrUiAccessCodeDAO;
import com.beone.shg.net.persistence.dao.MrWsAccessCodeDAO;
import com.beone.shg.net.persistence.dao.ProcessSchedulerDAO;
import com.beone.shg.net.persistence.dao.ProcessSchedulerSprintDAO;
import com.beone.shg.net.persistence.dao.ScheduleProcessErrorDAO;
import com.beone.shg.net.persistence.dao.TxDAO;
import com.beone.shg.net.persistence.dao.TxTodoDAO;
import com.beone.shg.net.persistence.dao.MemberContactDAO;
import com.beone.shg.net.persistence.dao.MonthlyGAcByTxtypeDAO;
import com.beone.shg.net.persistence.dao.MonthlyGAcDAO;
import com.beone.shg.net.persistence.dao.MonthlyReportDAO;
import com.beone.shg.net.persistence.dao.MonthlyReportSheetDAO;
import com.beone.shg.net.persistence.dao.MultiMToLoanAcDAO;
import com.beone.shg.net.persistence.dao.NameTitleDAO;
import com.beone.shg.net.persistence.dao.GGDAO;
import com.beone.shg.net.persistence.dao.PaymentModeDAO;
import com.beone.shg.net.persistence.dao.ReasonToUndoDAO;
import com.beone.shg.net.persistence.dao.RecoveryPeriodDAO;
import com.beone.shg.net.persistence.dao.TxStatusDAO;
import com.beone.shg.net.persistence.dao.TxTodoStatusDAO;
import com.beone.shg.net.persistence.dao.TxTypeDAO;
import com.beone.shg.net.persistence.dao.UiAccessCodeDAO;
import com.beone.shg.net.persistence.dao.WsAccessCodeDAO;
import com.beone.udaan.mr.persistence.dao.BrandDAO;
import com.beone.udaan.mr.persistence.dao.CatalogItemDAO;
import com.beone.udaan.mr.persistence.dao.InvoiceStatusDAO;
import com.beone.udaan.mr.persistence.dao.ItemConditionDAO;
import com.beone.udaan.mr.persistence.dao.ItemStatusDAO;
import com.beone.udaan.mr.persistence.dao.ItemTagDAO;
import com.beone.udaan.mr.persistence.dao.LotDAO;
import com.beone.udaan.mr.persistence.dao.LotStatusDAO;
import com.beone.udaan.mr.persistence.dao.MrStatusDAO;
import com.beone.udaan.mr.persistence.dao.MrTypeDAO;
import com.beone.udaan.mr.persistence.dao.MrVisitDAO;
import com.beone.udaan.mr.persistence.dao.PCategoryDAO;
import com.beone.udaan.mr.persistence.dao.PHubAcDAO;
import com.beone.udaan.mr.persistence.dao.PInvoiceDAO;
import com.beone.udaan.mr.persistence.dao.PMAcDAO;
import com.beone.udaan.mr.persistence.dao.PaymentTypeDAO;
import com.beone.udaan.mr.persistence.dao.PkgDAO;
import com.beone.udaan.mr.persistence.dao.PkgStatusDAO;
import com.beone.udaan.mr.persistence.dao.PkgTypeDAO;
import com.beone.udaan.mr.persistence.dao.ProductPricingDAO;
import com.beone.udaan.mr.persistence.dao.SInvoiceDAO;
import com.beone.udaan.mr.persistence.dao.ShipmentDAO;
import com.beone.udaan.mr.persistence.dao.ShipmentStatusDAO;
import com.beone.udaan.mr.persistence.dao.StockItemDAO;
import com.beone.udaan.mr.persistence.dao.StockTxDAO;
import com.beone.udaan.mr.persistence.dao.StockTxTypeDAO;
import com.beone.udaan.mr.persistence.dao.StockTypeDAO;
import com.beone.udaan.mr.persistence.dao.StockTypeStatusDAO;
import com.beone.udaan.mr.persistence.dao.TagStatusDAO;
import com.beone.udaan.mr.persistence.dao.VendorPaymentDAO;
import com.beone.udaan.mr.persistence.dao.VisitStatusDAO;
import com.beone.udaan.mr.persistence.dao.VisitTypeDAO;


@Component("daoFactory")
public class DAOFactory {

	@Autowired
	@Qualifier("accountStatusDAO")
	private AccountStatusDAO accountStatusDAO;

	@Autowired
	@Qualifier("investmentTypeDAO")
	private InvestmentTypeDAO investmentTypeDAO;

	@Autowired
	@Qualifier("activeStatusDAO")
	private ActiveStatusDAO activeStatusDAO;

	@Autowired
	@Qualifier("advanceChequesDAO")
	private AdvanceChequesDAO advanceChequesDAO;

	@Autowired
	@Qualifier("approvalStatusDAO")
	private ApprovalStatusDAO approvalStatusDAO;

	@Autowired
	@Qualifier("bankAccountDAO")
	private BankAccountDAO bankAccountDAO;

	@Autowired
	@Qualifier("bankAccountTypeDAO")
	private BankAccountTypeDAO bankAccountTypeDAO;
	
	@Autowired
	@Qualifier("bankProfileDAO")
	private BankProfileDAO bankProfileDAO;
	
	@Autowired
	@Qualifier("bankAcUploadDAO")
	private BankAcUploadDAO bankAcUploadDAO;

	@Autowired
	@Qualifier("contactDAO")
	private ContactDAO contactDAO;

	@Autowired
	@Qualifier("districtDAO")
	private DistrictDAO districtDAO;

	@Autowired
	@Qualifier("docDAO")
	private DocDAO docDAO;

	@Autowired
	@Qualifier("docTypeDAO")
	private DocTypeDAO docTypeDAO;

	@Autowired
	@Qualifier("gAcByTxtypeDAO")
	private GAcByTxtypeDAO gAcByTxtypeDAO;

	@Autowired
	@Qualifier("gAcDAO")
	private GAcDAO gAcDAO;

	@Autowired
	@Qualifier("gBankAccountDAO")
	private GBankAccountDAO gBankAccountDAO;

	@Autowired
	@Qualifier("gLoanAcDAO")
	private GLoanAcDAO gLoanAcDAO;

	@Autowired
	@Qualifier("gMDAO")
	private GMDAO gMDAO;

	@Autowired
	@Qualifier("gInvtAcDAO")
	private GInvtAcDAO gInvtAcDAO;

	@Autowired
	@Qualifier("gProfileDAO")
	private GProfileDAO gProfileDAO;

	@Autowired
	@Qualifier("groupContactDAO")
	private GroupContactDAO groupContactDAO;

	@Autowired
	@Qualifier("groupRelationDAO")
	private GroupRelationDAO groupRelationDAO;

	@Autowired
	@Qualifier("groupTypeDAO")
	private GroupTypeDAO groupTypeDAO;

	@Autowired
	@Qualifier("gRulesDAO")
	private GRulesDAO gRulesDAO;

	@Autowired
	@Qualifier("langDAO")
	private LangDAO langDAO;

	@Autowired
	@Qualifier("languageMassageDAO")
	private LanguageMassageDAO languageMassageDAO;
	
	@Autowired
	@Qualifier("loanCalculationDAO")
	private LoanCalculationDAO loanCalculationDAO;
	
	@Autowired
	@Qualifier("loanSourceDAO")
	private LoanSourceDAO loanSourceDAO;
	
	@Autowired
	@Qualifier("fundTypeDAO")
	private FundTypeDAO fundTypeDAO;

	@Autowired
	@Qualifier("mAcDAO")
	private MAcDAO mAcDAO;

	@Autowired
	@Qualifier("mBankAccountDAO")
	private MBankAccountDAO mBankAccountDAO;

	@Autowired
	@Qualifier("memberContactDAO")
	private MemberContactDAO memberContactDAO;
	
	@Autowired
	@Qualifier("mLoanAcDAO")
	private MLoanAcDAO mLoanAcDAO;

	@Autowired
	@Qualifier("messageTypeDAO")
	private MessageTypeDAO messageTypeDAO;

	@Autowired
	@Qualifier("mMessageDAO")
	private MMessageDAO mMessageDAO;

	@Autowired
	@Qualifier("mobileMDAO")
	private MobileMDAO mobileMDAO;

	@Autowired
	@Qualifier("monthlyGAcDAO")
	private MonthlyGAcDAO monthlyGAcDAO;

	@Autowired
	@Qualifier("monthlyMAcDAO")
	private MonthlyMAcDAO monthlyMAcDAO;

	@Autowired
	@Qualifier("monthlyMLoanAcDAO")
	private MonthlyMLoanAcDAO monthlyMLoanAcDAO;

	@Autowired
	@Qualifier("monthlyMSavingAcDAO")
	private MonthlyMSavingAcDAO monthlyMSavingAcDAO;

	@Autowired
	@Qualifier("monthlyGBankAccountDAO")
	private MonthlyGBankAccountDAO monthlyGBankAccountDAO;

	@Autowired
	@Qualifier("monthlyGInvtAcDAO")
	private MonthlyGInvtAcDAO monthlyGInvtAcDAO;

	@Autowired
	@Qualifier("monthlyGLoanAcDAO")
	private MonthlyGLoanAcDAO monthlyGLoanAcDAO;

	@Autowired
	@Qualifier("monthlyGAcByTxtypeDAO")
	private MonthlyGAcByTxtypeDAO monthlyGAcByTxtypeDAO;

	@Autowired
	@Qualifier("monthlyReportDAO")
	private MonthlyReportDAO monthlyReportDAO;

	@Autowired
	@Qualifier("monthlyReportSheetDAO")
	private MonthlyReportSheetDAO monthlyReportSheetDAO;

	@Autowired
	@Qualifier("mProfileDAO")
	private MProfileDAO mProfileDAO;

	@Autowired
	@Qualifier("mProfilingTypeDAO")
	private MProfilingTypeDAO mProfilingTypeDAO;

	@Autowired
	@Qualifier("mRoleDAO")
	private MRoleDAO mRoleDAO;

	@Autowired
	@Qualifier("mrUiAccessCodeDAO")
	private MrUiAccessCodeDAO mrUiAccessCodeDAO;
	
	@Autowired
	@Qualifier("mrWsAccessCodeDAO")
	private MrWsAccessCodeDAO mrWsAccessCodeDAO;

	@Autowired
	@Qualifier("mSavingAcDAO")
	private MSavingAcDAO mSavingAcDAO;

	@Autowired
	@Qualifier("txDAO")
	private TxDAO txDAO;

	@Autowired
	@Qualifier("txTodoDAO")
	private TxTodoDAO txTodoDAO;

	@Autowired
	@Qualifier("multiMToLoanAcDAO")
	private MultiMToLoanAcDAO multiMToLoanAcDAO;

	@Autowired
	@Qualifier("nameTitleDAO")
	private NameTitleDAO nameTitleDAO;

	@Autowired
	@Qualifier("GGDAO")
	private GGDAO GGDAO;

	@Autowired
	@Qualifier("paymentModeDAO")
	private PaymentModeDAO paymentModeDAO;

	@Autowired
	@Qualifier("processSchedulerDAO")
	private ProcessSchedulerDAO processSchedulerDAO;

	@Autowired
	@Qualifier("processSchedulerSprintDAO")
	private ProcessSchedulerSprintDAO processSchedulerSprintDAO;

	@Autowired
	@Qualifier("reasonToUndoDAO")
	private ReasonToUndoDAO reasonToUndoDAO;

	@Autowired
	@Qualifier("recoveryPeriodDAO")
	private RecoveryPeriodDAO recoveryPeriodDAO;

	@Autowired
	@Qualifier("scheduleProcessErrorDAO")
	private ScheduleProcessErrorDAO scheduleProcessErrorDAO;

	@Autowired
	@Qualifier("txStatusDAO")
	private TxStatusDAO txStatusDAO;

	@Autowired
	@Qualifier("txTodoStatusDAO")
	private TxTodoStatusDAO txTodoStatusDAO;

	@Autowired
	@Qualifier("txTypeDAO")
	private TxTypeDAO txTypeDAO;

	@Autowired
	@Qualifier("uiAccessCodeDAO")
	private UiAccessCodeDAO uiAccessCodeDAO;
	
	@Autowired
	@Qualifier("wsAccessCodeDAO")
	private WsAccessCodeDAO wsAccessCodeDAO;
	

	//**************************************
	
	@Autowired
	@Qualifier("brandDAO")
	private BrandDAO brandDAO;
	
	@Autowired
	@Qualifier("catalogItemDAO")
	private CatalogItemDAO catalogItemDAO;
	
	@Autowired
	@Qualifier("invoiceStatusDAO")
	private InvoiceStatusDAO invoiceStatusDAO;
	
	@Autowired
	@Qualifier("itemConditionDAO")
	private ItemConditionDAO itemConditionDAO;
	
	@Autowired
	@Qualifier("itemStatusDAO")
	private ItemStatusDAO itemStatusDAO;
	
	@Autowired
	@Qualifier("itemTagDAO")
	private ItemTagDAO itemTagDAO;
	
	@Autowired
	@Qualifier("lotDAO")
	private LotDAO lotDAO;
	
	@Autowired
	@Qualifier("lotStatusDAO")
	private LotStatusDAO lotStatusDAO;
	
	@Autowired
	@Qualifier("mrStatusDAO")
	private MrStatusDAO mrStatusDAO;
	
	@Autowired
	@Qualifier("mrTypeDAO")
	private MrTypeDAO mrTypeDAO;
	
	@Autowired
	@Qualifier("mrVisitDAO")
	private MrVisitDAO mrVisitDAO;
	
	@Autowired
	@Qualifier("paymentTypeDAO")
	private PaymentTypeDAO paymentTypeDAO;
	
	@Autowired
	@Qualifier("pCategoryDAO")
	private PCategoryDAO pCategoryDAO;
	
	@Autowired
	@Qualifier("pHubAcDAO")
	private PHubAcDAO pHubAcDAO;
	
	@Autowired
	@Qualifier("pInvoiceDAO")
	private PInvoiceDAO pInvoiceDAO;
	
	@Autowired
	@Qualifier("pkgDAO")
	private PkgDAO pkgDAO;
	
	@Autowired
	@Qualifier("pkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;
	
	@Autowired
	@Qualifier("pkgTypeDAO")
	private PkgTypeDAO pkgTypeDAO;
	
	@Autowired
	@Qualifier("pMAcDAO")
	private PMAcDAO pMAcDAO;
	
	@Autowired
	@Qualifier("productPricingDAO")
	private ProductPricingDAO productPricingDAO;
	
	@Autowired
	@Qualifier("shipmentDAO")
	private ShipmentDAO shipmentDAO;
	
	@Autowired
	@Qualifier("shipmentStatusDAO")
	private ShipmentStatusDAO shipmentStatusDAO;
	
	@Autowired
	@Qualifier("sInvoiceDAO")
	private SInvoiceDAO sInvoiceDAO;
	
	@Autowired
	@Qualifier("stockItemDAO")
	private StockItemDAO stockItemDAO;
	
	@Autowired
	@Qualifier("stockTxDAO")
	private StockTxDAO stockTxDAO;
	
	@Autowired
	@Qualifier("stockTxTypeDAO")
	private StockTxTypeDAO stockTxTypeDAO;
	
	@Autowired
	@Qualifier("stockTypeDAO")
	private StockTypeDAO stockTypeDAO;
	
	@Autowired
	@Qualifier("stockTypeStatusDAO")
	private StockTypeStatusDAO stockTypeStatusDAO;
	
	@Autowired
	@Qualifier("vendorPaymentDAO")
	private VendorPaymentDAO vendorPaymentDAO;
	
	@Autowired
	@Qualifier("tagStatusDAO")
	private TagStatusDAO tagStatusDAO;
	
	@Autowired
	@Qualifier("visitStatusDAO")
	private VisitStatusDAO visitStatusDAO;
	
	@Autowired
	@Qualifier("visitTypeDAO")
	private VisitTypeDAO visitTypeDAO;


	//**************************************

	public AccountStatusDAO getAccountStatusDAO() {
		return accountStatusDAO;
	}

	public InvestmentTypeDAO getInvestmentTypeDAO() {
		return investmentTypeDAO;
	}

	public ActiveStatusDAO getActiveStatusDAO() {
		return activeStatusDAO;
	}

	public AdvanceChequesDAO getAdvanceChequesDAO() {
		return advanceChequesDAO;
	}

	public ApprovalStatusDAO getApprovalStatusDAO() {
		return approvalStatusDAO;
	}

	public BankAccountDAO getBankAccountDAO() {
		return bankAccountDAO;
	}

	public BankAccountTypeDAO getBankAccountTypeDAO() {
		return bankAccountTypeDAO;
	}

	public BankProfileDAO getBankProfileDAO() {
		return bankProfileDAO;
	}

	public BankAcUploadDAO getBankAcUploadDAO() {
		return bankAcUploadDAO;
	}

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public DistrictDAO getDistrictDAO() {
		return districtDAO;
	}

	public DocDAO getDocDAO() {
		return docDAO;
	}

	public DocTypeDAO getDocTypeDAO() {
		return docTypeDAO;
	}

	public GAcByTxtypeDAO getGAcByTxtypeDAO() {
		return gAcByTxtypeDAO;
	}

	public GAcDAO getGAcDAO() {
		return gAcDAO;
	}

	public GBankAccountDAO getGBankAccountDAO() {
		return gBankAccountDAO;
	}

	public GLoanAcDAO getGLoanAcDAO() {
		return gLoanAcDAO;
	}

	public GMDAO getGMDAO() {
		return gMDAO;
	}
	
	public GInvtAcDAO getGInvtAcDAO() {
		return gInvtAcDAO;
	}

	public GProfileDAO getGProfileDAO() {
		return gProfileDAO;
	}

	public GroupContactDAO getGroupContactDAO() {
		return groupContactDAO;
	}

	public GroupRelationDAO getGroupRelationDAO() {
		return groupRelationDAO;
	}

	public GroupTypeDAO getGroupTypeDAO() {
		return groupTypeDAO;
	}

	public GRulesDAO getGRulesDAO() {
		return gRulesDAO;
	}

	public LangDAO getLangDAO() {
		return langDAO;
	}

	public LanguageMassageDAO getLanguageMassageDAO() {
		return languageMassageDAO;
	}

	public LoanCalculationDAO getLoanCalculationDAO() {
		return loanCalculationDAO;
	}

	public LoanSourceDAO getLoanSourceDAO() {
		return loanSourceDAO;
	}

	public FundTypeDAO getFundTypeDAO() {
		return fundTypeDAO;
	}

	public MAcDAO getMAcDAO() {
		return mAcDAO;
	}

	public MBankAccountDAO getMBankAccountDAO() {
		return mBankAccountDAO;
	}

	public MemberContactDAO getMemberContactDAO() {
		return memberContactDAO;
	}

	public MLoanAcDAO getMLoanAcDAO() {
		return mLoanAcDAO;
	}

	public MessageTypeDAO getMessageTypeDAO() {
		return messageTypeDAO;
	}

	public MMessageDAO getMMessageDAO() {
		return mMessageDAO;
	}

	public MobileMDAO getMobileMDAO() {
		return mobileMDAO;
	}

	public MonthlyGAcDAO getMonthlyGAcDAO() {
		return monthlyGAcDAO;
	}

	public MonthlyMAcDAO getMonthlyMAcDAO() {
		return monthlyMAcDAO;
	}

	public MonthlyMLoanAcDAO getMonthlyMLoanAcDAO() {
		return monthlyMLoanAcDAO;
	}

	public MonthlyMSavingAcDAO getMonthlyMSavingAcDAO() {
		return monthlyMSavingAcDAO;
	}

	public MonthlyGBankAccountDAO getMonthlyGBankAccountDAO() {
		return monthlyGBankAccountDAO;
	}

	public MonthlyGInvtAcDAO getMonthlyGInvtAcDAO() {
		return monthlyGInvtAcDAO;
	}

	public MonthlyGLoanAcDAO getMonthlyGLoanAcDAO() {
		return monthlyGLoanAcDAO;
	}

	public MonthlyGAcByTxtypeDAO getMonthlyGAcByTxtypeDAO() {
		return monthlyGAcByTxtypeDAO;
	}

	public MProfileDAO getMProfileDAO() {
		return mProfileDAO;
	}

	public MProfilingTypeDAO getMProfilingTypeDAO() {
		return mProfilingTypeDAO;
	}

	public MRoleDAO getMRoleDAO() {
		return mRoleDAO;
	}
	
	public MrUiAccessCodeDAO getMrUiAccessCodeDAO() {
		return mrUiAccessCodeDAO;
	}
	
	public MrWsAccessCodeDAO getMrWsAccessCodeDAO() {
		return mrWsAccessCodeDAO;
	}

	public MSavingAcDAO getMSavingAcDAO() {
		return mSavingAcDAO;
	}

	public TxDAO getTxDAO() {
		return txDAO;
	}

	public TxTodoDAO getTxTodoDAO() {
		return txTodoDAO;
	}

	public MultiMToLoanAcDAO getMultiMToLoanAcDAO() {
		return multiMToLoanAcDAO;
	}

	public NameTitleDAO getNameTitleDAO() {
		return nameTitleDAO;
	}

	public GGDAO getGGDAO() {
		return GGDAO;
	}

	public PaymentModeDAO getPaymentModeDAO() {
		return paymentModeDAO;
	}

	public ProcessSchedulerDAO getProcessSchedulerDAO() {
		return processSchedulerDAO;
	}

	public ProcessSchedulerSprintDAO getProcessSchedulerSprintDAO() {
		return processSchedulerSprintDAO;
	}

	public ReasonToUndoDAO getReasonToUndoDAO() {
		return reasonToUndoDAO;
	}

	public RecoveryPeriodDAO getRecoveryPeriodDAO() {
		return recoveryPeriodDAO;
	}

	public ScheduleProcessErrorDAO getScheduleProcessErrorDAO() {
		return scheduleProcessErrorDAO;
	}

	public MonthlyReportDAO getMonthlyReportDAO() {
		return monthlyReportDAO;
	}

	public MonthlyReportSheetDAO getMonthlyReportSheetDAO() {
		return monthlyReportSheetDAO;
	}

	public TxStatusDAO getTxStatusDAO() {
		return txStatusDAO;
	}

	public TxTodoStatusDAO getTxTodoStatusDAO() {
		return txTodoStatusDAO;
	}

	public TxTypeDAO getTxTypeDAO() {
		return txTypeDAO;
	}
	
	public UiAccessCodeDAO getUiAccessCodeDAO() {
		return uiAccessCodeDAO;
	}
	
	public WsAccessCodeDAO getWsAccessCodeDAO() {
		return wsAccessCodeDAO;
	}

	//********************************************
	
	public BrandDAO getBrandDAO() {
		return brandDAO;
	}
	
	public CatalogItemDAO getCatalogItemDAO() {
		return catalogItemDAO;
	}
	
	public InvoiceStatusDAO getInvoiceStatusDAO() {
		return invoiceStatusDAO;
	}
	
	public ItemConditionDAO getItemConditionDAO() {
		return itemConditionDAO;
	}
	
	public ItemStatusDAO getItemStatusDAO() {
		return itemStatusDAO;
	}
	
	public ItemTagDAO getItemTagDAO() {
		return itemTagDAO;
	}
	
	public LotDAO getLotDAO() {
		return lotDAO;
	}
	
	public LotStatusDAO getLotStatusDAO() {
		return lotStatusDAO;
	}
	
	public  MrStatusDAO getMrStatusDAO() {
		return mrStatusDAO;
	}
	
	public  MrTypeDAO getMrTypeDAO() {
		return mrTypeDAO;
	}
	
	public MrVisitDAO getMrVisitDAO() {
		return mrVisitDAO;
	}
	
	public PaymentTypeDAO getPaymentTypeDAO() {
		return paymentTypeDAO;
	}
	
	public PCategoryDAO getPCategoryDAO() {
		return pCategoryDAO;
	}
	
	public PHubAcDAO getPHubAcDAO() {
		return pHubAcDAO;
	}
	
	public PInvoiceDAO getPInvoiceDAO() {
		return pInvoiceDAO;
	}
	
	public PkgDAO getPkgDAO() {
		return pkgDAO;
	}
	
	public PkgStatusDAO getPkgStatusDAO() {
		return pkgStatusDAO;
	}
	
	public PkgTypeDAO getPkgTypeDAO() {
		return pkgTypeDAO;
	}
	
	public PMAcDAO getPMAcDAO() {
		return pMAcDAO;
	}
	
	public ProductPricingDAO getProductPricingDAO() {
		return productPricingDAO;
	}
	
	public ShipmentDAO getShipmentDAO() {
		return shipmentDAO;
	}
	
	public ShipmentStatusDAO getShipmentStatusDAO() {
		return shipmentStatusDAO;
	}
	
	public SInvoiceDAO getSInvoiceDAO() {
		return sInvoiceDAO;
	}
	
	public StockItemDAO getStockItemDAO() {
		return stockItemDAO;
	}
	
	public StockTxDAO getStockTxDAO() {
		return stockTxDAO;
	}
	
	public StockTxTypeDAO getStockTxTypeDAO() {
		return stockTxTypeDAO;
	}
	
	public StockTypeDAO getStockTypeDAO() {
		return stockTypeDAO;
	}
	
	public StockTypeStatusDAO getStockTypeStatusDAO() {
		return stockTypeStatusDAO;
	}
	
	public VendorPaymentDAO getVendorPaymentDAO() {
		return vendorPaymentDAO;
	}
	
	public TagStatusDAO getTagStatusDAO() {
		return tagStatusDAO;
	}
	
	public VisitStatusDAO getVisitStatusDAO() {
		return visitStatusDAO;
	}
	
	public VisitTypeDAO getVisitTypeDAO() {
		return visitTypeDAO;
	}

}
