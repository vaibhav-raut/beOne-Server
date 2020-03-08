package com.beone.shg.net.webservice.rest.service;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.http.HttpStatus;
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
import com.beone.shg.net.persistence.bo.AttachmentBO;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.FileLink;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.shg.net.webservice.rest.util.HttpRequestUtil;

@Controller
@RequestMapping("/attachment")
public class AttachmentWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(AttachmentWS.class);

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}

    @RequestMapping(value = "/v1/save_file/{groupAcNo}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> saveFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
		// Log that request came to WS
		LOGGER.debug("saveFile()");

		try {
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        String responce = attachmentBO.saveFile(groupAcNo, fileItem.getName(), docTypeId, fileItem.get());	         

			return RestResponse.OK(Attachment.buildAttachments(responce));

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

    @RequestMapping(value = "/v1/update_file/{groupAcNo}/{docId}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> updateFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
		// Log that request came to WS
		LOGGER.debug("updateFile()");

		try {
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        String responce = attachmentBO.updateFile(groupAcNo, docId, fileItem.getName(), docTypeId, fileItem.get());	         

			return RestResponse.OK(Attachment.buildAttachments(responce));

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

    // http://stackoverflow.com/questions/16086162/handle-file-download-from-ajax-post
    @RequestMapping(value = "/v1/get_file_link/{groupAcNo}/{docId}/{fileName}", consumes = "application/json", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<?> getFileLink(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("fileName") String fileName,
    		HttpServletRequest request) throws IOException {
    	
		// Log that request came to WS
		LOGGER.debug("getFileLink()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

	        ServletContext sc = request.getSession().getServletContext();
			
			FileLink link = attachmentBO.getFileLink(sc, groupAcNo, docId, fileName);         
			return RestResponse.OK(link);

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
    
    //http://docs.spring.io/spring/docs/2.5.x/reference/mvc.html#mvc-multipart
    @RequestMapping(value = "/v1/get_file/{groupAcNo}/{docId}/{fileName}", consumes = "application/json", produces = "multipart/form-data", method = RequestMethod.GET)
	@ResponseBody
    public void getFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("fileName") String fileName,
    		HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
		// Log that request came to WS
		LOGGER.debug("getFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			byte[] fileBytes = attachmentBO.getFile(docId);	 
			
			// Set Output response
			// TODO Need to fix encoding problem
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			// Build response with file
			ServletOutputStream out = response.getOutputStream();
			out.write(fileBytes);
			out.close();
		} catch (AccessDeniedException e) {
			LOGGER.error(e.getLocalizedMessage());
            response.sendError(HttpStatus.SC_FORBIDDEN, e.getMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
            response.sendError(HttpStatus.SC_BAD_REQUEST, e.getMessage());
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage());
            response.sendError(HttpStatus.SC_BAD_REQUEST, e.getMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
            response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
            response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		} 
	}
}
