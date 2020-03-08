package com.beone.shg.net.webservice.rest.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.bo.GroupLoanAcBO;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMIPlan;
import com.beone.shg.net.webservice.rest.model.rest.GroupLoanAcREST;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.shg.net.webservice.rest.util.HttpRequestUtil;

@Controller
@RequestMapping("/gloan")
public class GroupLoanAcWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(GroupLoanAcWS.class);

	@Autowired
	@Qualifier("groupLoanAcBO")
	private GroupLoanAcBO groupLoanAcBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}

	@RequestMapping(value = "/v1/group_loan_ac", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addGroupLoanAc(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("addGroupLoanAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			GroupLoanAcREST response = groupLoanAcBO.add(request);
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

	@RequestMapping(value = "/v1/update_group_loan_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateGroupLoanAc(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("updateGroupLoanAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			groupLoanAcBO.updateGroupLoanAc(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	@RequestMapping(value = "/v1/approve_group_loan_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> approveReject(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("approveReject(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupLoanAcBO.approveReject(request);
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

	@RequestMapping(value = "/v1/activate", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> activate(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("activate(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupLoanAcBO.activate(request);
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

	@RequestMapping(value = "/v1/txs_to_activate", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getTxsToActivate(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("getTxsToActivate(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupLoanAcBO.getTxsToActivate(request);
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

	@RequestMapping(value = "/v1/close", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> close(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("close(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupLoanAcBO.close(request);
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

	@RequestMapping(value = "/v1/txs_to_close", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getTxsToClose(@RequestBody GroupLoanAcREST request) {
		// Log that request came to WS
		LOGGER.debug("getTxsToClose(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupLoanAcBO.getTxsToClose(request);
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

	@RequestMapping(value = "/v1/group_loan_ac/{lang}/{groupLoanAcNo}/{accountType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupLoanAc(@PathVariable("lang") String lang,
			@PathVariable("groupLoanAcNo") long groupLoanAcNo,
			@PathVariable("accountType") String accountType) {

		// Log that request came to WS
		LOGGER.debug("getGroupLoanAcsForGroup(lang:" + lang + ", groupLoanAcNo:" + groupLoanAcNo + ", accountType:" + accountType + ")");

		try {
			GroupLoanAcREST response = groupLoanAcBO.getGroupLoanAc(lang, groupLoanAcNo);    	

			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, response.getGroupAcNo());

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

	@RequestMapping(value = "/v1/all_group_loan_ac/{lang}/{groupAcNo}/{accountType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupLoanAcs(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") long groupAcNo,
			@PathVariable("accountType") String accountType) {

		// Log that request came to WS
		LOGGER.debug("getGroupLoanAcsForGroup(lang:" + lang + ", groupAcNo:" + groupAcNo + ", accountType:" + accountType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			List<GroupLoanAcREST> response = groupLoanAcBO.getGroupLoanAcs(lang, groupAcNo);
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

	@RequestMapping(value = "/v1/loan_planning", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getLoanPlanning(@RequestBody LoanEMIPlan request) {

		// Log that request came to WS
		LOGGER.debug("getLoanPlanning(request:" + request + ")");

		try {
			Object response = groupLoanAcBO.getLoanPlanning(request);    	
			return RestResponse.OK(response);

			//		} catch (AccessDeniedException e) {
			//			LOGGER.error(e.getLocalizedMessage());
			//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
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

    @RequestMapping(value = "/v1/attach_file/{groupAcNo}/{groupLoanAcNo}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> attachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("groupLoanAcNo") long groupLoanAcNo,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("attachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        Object responce = groupLoanAcBO.attachFile(groupAcNo, groupLoanAcNo, fileItem.getName(), docTypeId, fileItem.get());	         

			return RestResponse.OK(responce);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (IOException e) {
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

    @RequestMapping(value = "/v1/update_attach_file/{groupAcNo}/{docId}/{groupLoanAcNo}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> updateAttachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("groupLoanAcNo") long groupLoanAcNo,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("updateAttachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);
			
	        Object responce = groupLoanAcBO.updateAttachFile(groupAcNo, docId, groupLoanAcNo, fileItem.getName(), docTypeId, fileItem.get());	         

			return RestResponse.OK(responce);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (IOException e) {
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

    @RequestMapping(value = "/v1/delete_attach_file/{groupAcNo}/{docId}/{groupLoanAcNo}", consumes = "application/json", produces = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
    public ResponseEntity<?> deleteAttachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("groupLoanAcNo") long groupLoanAcNo) {
    	
		// Log that request came to WS
		LOGGER.debug("deleteAttachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

	        groupLoanAcBO.deleteAttachFile(groupAcNo, docId, groupLoanAcNo);	         
			return RestResponse.OK(RESTConst.SUCCESS);

		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (IOException e) {
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
