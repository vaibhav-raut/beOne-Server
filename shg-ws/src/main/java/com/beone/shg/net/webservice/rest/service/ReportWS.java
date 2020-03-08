package com.beone.shg.net.webservice.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.persistence.bo.ReportBO;
import com.beone.shg.net.webservice.rest.model.resp.AcBook;
import com.beone.shg.net.webservice.rest.model.resp.AccountBook;
import com.beone.shg.net.webservice.rest.model.resp.BankReport;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/report")
public class ReportWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReportWS.class);

	@Autowired
	@Qualifier("reportBO")
	private ReportBO reportBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");

	}

	@RequestMapping(value = "/v1/account_book/{lang}/{groupAcNo}/{type}/{bankAcNo}/{startTime}/{endMonth}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAccountBook(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") int groupAcNo, 
			@PathVariable("type") String type,
			@PathVariable("bankAcNo") long bankAcNo,
			@PathVariable("startTime") long startTime,
			@PathVariable("endMonth") String endMonth) {
		// Log that request came to WS
		LOGGER.debug("getAccountBook(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", type:" + type + 
				", bankAcNo:" + bankAcNo + 
				", startTime:" + startTime + 
				", endMonth:" + endMonth + 
				")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			AccountBook response = reportBO.getAccountBook(lang, groupAcNo, type, bankAcNo, startTime, endMonth);      
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	@RequestMapping(value = "/v1/ac_book/{lang}/{groupAcNo}/{type}/{bankAcNo}/{startTime}/{endMonth}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAcBook(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") int groupAcNo, 
			@PathVariable("type") String type,
			@PathVariable("bankAcNo") long bankAcNo,
			@PathVariable("startTime") long startTime,
			@PathVariable("endMonth") String endMonth) {

		// Log that request came to WS
		LOGGER.debug("getAcBook(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", type:" + type + 
				", bankAcNo:" + bankAcNo + 
				", startTime:" + startTime + 
				", endMonth:" + endMonth + 
				")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			AcBook response = reportBO.getAcBook(lang, groupAcNo, type, bankAcNo, startTime, endMonth);      
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	@RequestMapping(value = "/v1/member_ac_book/{lang}/{memberAcNo}/{startMonth}/{endMonth}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberAccount(@PathVariable("lang") String lang,
			@PathVariable("memberAcNo") long memberAcNo, 
			@PathVariable("startMonth") String startMonth,
			@PathVariable("endMonth") String endMonth) {

		// Log that request came to WS
		LOGGER.debug("getMemberAccount(lang:" + lang + 
				", memberAcNo:" + memberAcNo + 
				", startMonth:" + startMonth + 
				", endMonth:" + endMonth + 
				")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);

			Object response = reportBO.getMemberAccount(lang, memberAcNo, startMonth, endMonth);      
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to GET MONTHLY REPORTS
	@RequestMapping(value = "/v1/monthly_report/{lang}/{groupAcNo}/{type}/{month}/{sheetFormat}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMonthlyReport(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") int groupAcNo, 
			@PathVariable("type") String type,
			@PathVariable("month") String month,
			@PathVariable("sheetFormat") String sheetFormat) {
		// Log that request came to WS
		LOGGER.debug("getMonthlyReport(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", type:" + type + 
				", month:" + month + 
				", sheetFormat:" + sheetFormat + 
				")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			BankReport response = reportBO.getMonthlyReport(lang, groupAcNo, type, month, sheetFormat);    
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to GET AS ON DATE REPORTS
	@RequestMapping(value = "/v1/as_on_date_report/{lang}/{groupAcNo}/{type}/{sheetFormat}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAsOnDateReport(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") int groupAcNo, 
			@PathVariable("type") String type,
			@PathVariable("sheetFormat") String sheetFormat) {
		// Log that request came to WS
		LOGGER.debug("getAsOnDateReport(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", type:" + type + 
				", sheetFormat:" + sheetFormat + 
				")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			BankReport response = reportBO.getAsOnDateReport(lang, groupAcNo, type, sheetFormat);    
			return RestResponse.OK(response);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//		} catch (InternalServerErrorException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}
}
