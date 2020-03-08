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
import com.beone.shg.net.persistence.bo.GroupInvtAcBO;
import com.beone.shg.net.webservice.rest.model.resp.InvtPlan;
import com.beone.shg.net.webservice.rest.model.rest.GroupInvtAcREST;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.shg.net.webservice.rest.util.HttpRequestUtil;

@Controller
@RequestMapping("/group_invt")
public class GroupInvtAcWS implements InitializingBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupInvtAcWS.class);

	@Autowired
	@Qualifier("groupInvtAcBO")
	private GroupInvtAcBO groupInvtAcBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
    @Override
    // initialization
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("afterPropertiesSet()");
    }

    @RequestMapping(value = "/v1/group_invt_ac", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addGroupInvtAc(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("addGroupInvtAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			GroupInvtAcREST response = groupInvtAcBO.add(request);
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

    @RequestMapping(value = "/v1/update_group_invt_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> updateGroupInvtAc(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("updateGroupInvtAc(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			groupInvtAcBO.updateGroupInvtAcData(request);
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

    @RequestMapping(value = "/v1/approve_group_invt_ac", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> approveReject(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("approveReject(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupInvtAcBO.approveReject(request);
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
    public ResponseEntity<?> activate(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("activate(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupInvtAcBO.activate(request);
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
	public ResponseEntity<?> getTxsToActivate(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("getTxsToActivate(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupInvtAcBO.getTxsToActivate(request);
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
	public ResponseEntity<?> close(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("close(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupInvtAcBO.close(request);
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

	@RequestMapping(value = "/v1/txs_to_recover", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getTxsToClose(@RequestBody GroupInvtAcREST request) {
		// Log that request came to WS
		LOGGER.debug("getTxsToClose(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = groupInvtAcBO.getTxsToRecover(request);
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
   
	@RequestMapping(value = "/v1/group_invt_ac/{lang}/{groupInvtAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupInvtAc(@PathVariable("lang") String lang,
			@PathVariable("groupInvtAcNo") long groupInvtAcNo) {
		// Log that request came to WS
		LOGGER.debug("getGroupInvtAc(lang:" + lang + ", groupInvtAcNo:" + groupInvtAcNo + ")");

		try {
			GroupInvtAcREST response = groupInvtAcBO.getGroupInvtAc(lang, groupInvtAcNo);    	

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
    
	@RequestMapping(value = "/v1/group_invt_ac_for_group/{lang}/{groupAcNo}/{accountType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupInvtAcsForGroup(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") long groupAcNo,
			@PathVariable("accountType") String accountType) {
		
		// Log that request came to WS
		LOGGER.debug("getGroupInvtAcsForGroup(lang:" + lang + ", groupAcNo:" + groupAcNo + ", accountType:" + accountType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			List<GroupInvtAcREST> response = groupInvtAcBO.getGroupInvtAcs(lang, groupAcNo);    	
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

	@RequestMapping(value = "/v1/invt_planning", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> getInvtPlanning(@RequestBody InvtPlan request) {

		// Log that request came to WS
		LOGGER.debug("getInvtPlanning(request:" + request + ")");

		try {
			Object response = groupInvtAcBO.getInvtPlanning(request);    	
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

    @RequestMapping(value = "/v1/attach_file/{groupAcNo}/{groupInvtAcNo}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> attachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("groupInvtAcNo") long groupInvtAcNo,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("attachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        Object responce = groupInvtAcBO.attachFile(groupAcNo, groupInvtAcNo, fileItem.getName(), docTypeId, fileItem.get());	         

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

    @RequestMapping(value = "/v1/update_attach_file/{groupAcNo}/{docId}/{groupInvtAcNo}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> updateAttachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("groupInvtAcNo") long groupInvtAcNo,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("updateAttachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);
			
	        Object responce = groupInvtAcBO.updateAttachFile(groupAcNo, docId, groupInvtAcNo, fileItem.getName(), docTypeId, fileItem.get());	         

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

    @RequestMapping(value = "/v1/delete_attach_file/{groupAcNo}/{docId}/{groupInvtAcNo}/{fileName}/{docTypeId}", consumes = "application/json", produces = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
    public ResponseEntity<?> deleteAttachFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("groupInvtAcNo") long groupInvtAcNo) {
    	
		// Log that request came to WS
		LOGGER.debug("deleteAttachFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

	        groupInvtAcBO.deleteAttachFile(groupAcNo, docId, groupInvtAcNo);	         
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
