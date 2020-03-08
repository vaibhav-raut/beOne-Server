package com.beone.udaan.mr.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.FormParam;

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

import au.com.bytecode.opencsv.CSVReader;

import com.beone.shg.common.web.security.ShgAuthToken;
import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.udaan.mr.bo.MrManageDataBO;

@Controller
@RequestMapping("/mr/manage_data")
public class MrManageDataWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(MrManageDataWS.class);


	@Autowired
	@Qualifier("mrManageDataBO")
	private MrManageDataBO mrManageDataBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}
	
    @RequestMapping(value = "/v1/get_mr_csv_types", consumes = "application/json", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity<?> getMrCSVTypes() {
		// Log that request came to WS
		LOGGER.debug("getMrCSVTypes()");

		try {

		    Object response = mrManageDataBO.getMrCSVTypes();
		    
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
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
	
    @RequestMapping(value = "/v1/add_mr_csv_for_type/{csvType}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addMrCSVForType(@PathVariable("csvType") String csvType,
    		@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addMrCSVForType()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addMrCSVForType(token.getMemberAcNo(), csvType, csvData);
		    
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
		
    @RequestMapping(value = "/v1/add_manufacturer_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addManufacturerCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addManufacturerCSV()");

		try {
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addManufacturerCSV(csvData);
		    
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
	
    @RequestMapping(value = "/v1/add_brand_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addBrandCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addBrandCSV()");

		try {
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addBrandCSV(csvData);
		    
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
	
    @RequestMapping(value = "/v1/add_stock_type_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addStockTypeCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addStockTypeCSV()");

		try {
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addStockTypeCSV(csvData);
		    
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
	
    @RequestMapping(value = "/v1/add_purchase_invoice_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addPurchaseInvoiceCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addPurchaseInvoiceCSV()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addPurchaseInvoiceCSV(token.getMemberAcNo(), csvData);
		    
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
	
    @RequestMapping(value = "/v1/add_lot_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addLotCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addLotCSV()");

		try {
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addLotCSV(csvData);
		    
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
	
    @RequestMapping(value = "/v1/add_members_csv", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> addMembersCSV(@FormParam("file") InputStream csvRequest) {
		// Log that request came to WS
		LOGGER.debug("addMembersCSV()");

		try {
			
			CSVReader csvReader = new CSVReader(new InputStreamReader(csvRequest));
		    List<String[]> csvData = csvReader.readAll();
		    csvReader.close();

		    Object response = mrManageDataBO.addMembersCSV(csvData);
		    
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
}
