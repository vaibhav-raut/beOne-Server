package com.beone.shg.net.webservice.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beone.shg.common.web.security.annotation.UnsecuredMethod;
import com.beone.shg.net.config.RESTConst;
import com.beone.shg.net.persistence.bo.EnumBO;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeCatEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MRoleEnum;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportsEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleEnum;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeEnum;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;
import com.beone.shg.net.webservice.rest.support.RestResponse;

@Controller
@RequestMapping("/enum")
public class EnumWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(EnumWS.class);


	@Autowired
	@Qualifier("enumBO")
	private EnumBO enumBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
		this.enumBO.loadAllEnumValues();
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/enum_values", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addEnumValues(@RequestBody GenericEnum request) {
		// Log that request came to WS
		LOGGER.debug("addEnumValues(request:" + request + ")");

		try {
			enumBO.addEnumValues(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_enum_values", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateEnumValues(@RequestBody GenericEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateEnumValues(request:" + request + ")");

		try {
			enumBO.updateEnumValues(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/enum_values/{enumName}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getEnumValues(@PathVariable("enumName") String enumName) {
		// Log that request came to WS
		LOGGER.debug("getEnumValues(enumName:" + enumName + ")");

		try {
			GenericEnum response = enumBO.getEnumValues(enumName);
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_tx_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addTxType(@RequestBody TxTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addTxType(request:" + request + ")");

		try {
			enumBO.addTxType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_tx_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateTxType(@RequestBody TxTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateTxType(request:" + request + ")");

		try {
			enumBO.updateTxType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
		} catch (BadRequestException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
//			} catch (InternalServerErrorException e) {
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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/tx_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTxType() {
		// Log that request came to WS
		LOGGER.debug("getTxType()");

		try {
			TxTypeEnum response = enumBO.getTxType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_group_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addGroupType(@RequestBody GroupTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addGroupType(request:" + request + ")");

		try {
			enumBO.addGroupType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_group_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateGroupType(@RequestBody GroupTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateGroupType(request:" + request + ")");

		try {
			enumBO.updateGroupType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/group_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupType() {
		// Log that request came to WS
		LOGGER.debug("getGroupType()");

		try {
			GroupTypeEnum response = enumBO.getGroupType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_name_title", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addNameTitle(@RequestBody NameTitleEnum request) {
		// Log that request came to WS
		LOGGER.debug("addNameTitle(request:" + request + ")");

		try {
			enumBO.addNameTitle(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_name_title", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateNameTitle(@RequestBody NameTitleEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateNameTitle(request:" + request + ")");

		try {
			enumBO.updateNameTitle(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/name_title", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getNameTitle() {
		// Log that request came to WS
		LOGGER.debug("getNameTitle()");

		try {
			NameTitleEnum response = enumBO.getNameTitle();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_m_role", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMRole(@RequestBody MRoleEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMRole(request:" + request + ")");

		try {
			enumBO.addMRole(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_m_role", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMRole(@RequestBody MRoleEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMRole(request:" + request + ")");

		try {
			enumBO.updateMRole(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/m_role", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMRole() {
		// Log that request came to WS
		LOGGER.debug("getMRole()");

		try {
			MRoleEnum response = enumBO.getMRole();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_fund_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addFundType(@RequestBody FundTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addFundType(request:" + request + ")");

		try {
			enumBO.addFundType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_fund_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateFundType(@RequestBody FundTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateFundType(request:" + request + ")");

		try {
			enumBO.updateFundType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/fund_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getFundType() {
		// Log that request came to WS
		LOGGER.debug("getFundType()");

		try {
			FundTypeEnum response = enumBO.getFundType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_districts", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addDistricts(@RequestBody DistrictsEnum request) {
		// Log that request came to WS
		LOGGER.debug("addDistricts(request:" + request + ")");

		try {
			enumBO.addDistricts(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Update new Values to Enum 
	@RequestMapping(value = "/v1/update_districts", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateDistricts(@RequestBody DistrictsEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateDistricts(request:" + request + ")");

		try {
			enumBO.updateDistricts(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/districts/{lang}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getDistricts(@PathVariable("lang") String lang) {
		// Log that request came to WS
		LOGGER.debug("getDistricts()");

		try {
			DistrictsEnum response = enumBO.getDistricts(lang);
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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_monthly_reports", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMonthlyReports(@RequestBody MonthlyReportsEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMonthlyReports(request:" + request + ")");

		try {
			enumBO.addMonthlyReports(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Update new Values to Enum 
	@RequestMapping(value = "/v1/update_monthly_reports", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMonthlyReports(@RequestBody MonthlyReportsEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMonthlyReports(request:" + request + ")");

		try {
			enumBO.updateMonthlyReports(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/monthly_reports/{lang}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMonthlyReports(@PathVariable("lang") String lang) {
		// Log that request came to WS
		LOGGER.debug("getMonthlyReports()");

		try {
			Object response = enumBO.getMonthlyReports();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_doc_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addDocType(@RequestBody DocTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addDocType(request:" + request + ")");

		try {
			enumBO.addDocType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_doc_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateDocType(@RequestBody DocTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateDocType(request:" + request + ")");

		try {
			enumBO.updateDocType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/doc_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getDocType() {
		// Log that request came to WS
		LOGGER.debug("getDocType()");

		try {
			DocTypeEnum response = enumBO.getDocType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_message_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMessageType(@RequestBody MessageTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMessageType(request:" + request + ")");

		try {
			enumBO.addMessageType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_message_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMessageType(@RequestBody MessageTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMessageType(request:" + request + ")");

		try {
			enumBO.updateMessageType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/message_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMessageType() {
		// Log that request came to WS
		LOGGER.debug("getMessageType()");

		try {
			MessageTypeEnum response = enumBO.getMessageType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_mprofiling_type", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMProfilingType(@RequestBody MProfilingTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMProfilingType(request:" + request + ")");

		try {
			enumBO.addMProfilingType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_mprofiling_type", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMProfilingType(@RequestBody MProfilingTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMProfilingType(request:" + request + ")");

		try {
			enumBO.updateMProfilingType(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/mprofiling_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMProfilingType() {
		// Log that request came to WS
		LOGGER.debug("getMProfilingType()");

		try {
			MProfilingTypeEnum response = enumBO.getMProfilingType();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/mprofiling_type_for/{profileFor}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMProfilingTypeFor(@PathVariable("profileFor") String profileFor) {
		// Log that request came to WS
		LOGGER.debug("getMProfilingType()");

		try {
			MProfilingTypeCatEnum response = enumBO.getMProfilingType(profileFor);
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_ui_access_code", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addUiAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addUiAccessCode(request:" + request + ")");

		try {
			enumBO.addUiAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_ui_access_code", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateUiAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateUiAccessCode(request:" + request + ")");

		try {
			enumBO.updateUiAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/ui_access_code", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUiAccessCode() {
		// Log that request came to WS
		LOGGER.debug("getUiAccessCode()");

		try {
			AccessCodeEnum response = enumBO.getUiAccessCode();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_ws_access_code", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addWsAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addWsAccessCode(request:" + request + ")");

		try {
			enumBO.addWsAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_ws_access_code", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateWsAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateWsAccessCode(request:" + request + ")");

		try {
			enumBO.updateWsAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/ws_access_code", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getWsAccessCode() {
		// Log that request came to WS
		LOGGER.debug("getWsAccessCode()");

		try {
			AccessCodeEnum response = enumBO.getWsAccessCode();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_mr_ui_access_code", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMrUiAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMrUiAccessCode(request:" + request + ")");

		try {
			enumBO.addMrUiAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_mr_ui_access_code", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMrUiAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMrUiAccessCode(request:" + request + ")");

		try {
			enumBO.updateMrUiAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/mr_ui_access_code", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMrUiAccessCode() {
		// Log that request came to WS
		LOGGER.debug("getMrUiAccessCode()");

		try {
			AccessCodeEnum response = enumBO.getMrUiAccessCode();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/add_mr_ws_access_code", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMrWsAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMrWsAccessCode(request:" + request + ")");

		try {
			enumBO.addMrWsAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/update_mr_ws_access_code", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMrWsAccessCode(@RequestBody AccessCodeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMrWsAccessCode(request:" + request + ")");

		try {
			enumBO.updateMrWsAccessCode(request);
			return RestResponse.OK(RESTConst.SUCCESS);

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

	// API to Used for loading all Enum Values
	@RequestMapping(value = "/v1/mr_ws_access_code", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMrWsAccessCode() {
		// Log that request came to WS
		LOGGER.debug("getMrWsAccessCode()");

		try {
			AccessCodeEnum response = enumBO.getMrWsAccessCode();
			return RestResponse.OK(response);

//		} catch (AccessDeniedException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.FORBIDDEN(e.getLocalizedMessage());
//		} catch (BadRequestException e) {
//			LOGGER.error(e.getLocalizedMessage());
//			return RestResponse.BAD_REQUEST(e.getLocalizedMessage());
		} catch (InternalServerErrorException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (RuntimeException e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage());
			return RestResponse.INTERNAL_SERVER_ERROR(e.getLocalizedMessage());
		}      
	}
}
