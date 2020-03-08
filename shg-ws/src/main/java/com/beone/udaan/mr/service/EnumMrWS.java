package com.beone.udaan.mr.service;

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
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.udaan.mr.bo.EnumMrBO;
import com.beone.udaan.mr.service.model.ItemConditionEnum;
import com.beone.udaan.mr.service.model.MrTypeEnum;
import com.beone.udaan.mr.service.model.ProductCategoryEnum;
import com.beone.udaan.mr.service.model.ProductPricingEnum;
import com.beone.udaan.mr.service.model.StatusEnum;
import com.beone.udaan.mr.service.model.StockTxTypeEnum;
import com.beone.udaan.mr.service.model.VisitTypeEnum;

@Controller
@RequestMapping("/mr/enum")
public class EnumMrWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(EnumMrWS.class);


	@Autowired
	@Qualifier("enumMrBO")
	private EnumMrBO enumMrBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
		this.enumMrBO.loadAllEnumValues();
	}

	// API to Add new Values to Enum 
	@RequestMapping(value = "/v1/enum_values", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addEnumValues(@RequestBody GenericEnum request) {
		// Log that request came to WS
		LOGGER.debug("addEnumValues(request:" + request + ")");

		try {
			enumMrBO.addEnumValues(request);
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
			enumMrBO.updateEnumValues(request);
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
			Object response = enumMrBO.getEnumValues(enumName);
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

	// API to Add new Values to Status 
	@RequestMapping(value = "/v1/status_values", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addStatusValues(@RequestBody StatusEnum request) {
		// Log that request came to WS
		LOGGER.debug("addStatusValues(request:" + request + ")");

		try {
			enumMrBO.addStatusValues(request);
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

	// API to Add new Values to Status 
	@RequestMapping(value = "/v1/update_status_values", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateStatusValues(@RequestBody StatusEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateStatusValues(request:" + request + ")");

		try {
			enumMrBO.updateStatusValues(request);
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

	// API to Used for loading all Status Values
	@RequestMapping(value = "/v1/status_values/{statusName}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getStatusValues(@PathVariable("statusName") String statusName) {
		// Log that request came to WS
		LOGGER.debug("getStatusValues(statusName:" + statusName + ")");

		try {
			Object response = enumMrBO.getStatusValues(statusName);
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


	@RequestMapping(value = "/v1/item_condition", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addItemCondition(@RequestBody ItemConditionEnum request) {
		// Log that request came to WS
		LOGGER.debug("addItemCondition(request:" + request + ")");

		try {
			enumMrBO.addItemCondition(request);
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

	@RequestMapping(value = "/v1/update_item_condition", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateItemCondition(@RequestBody ItemConditionEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateItemCondition(request:" + request + ")");

		try {
			enumMrBO.updateItemCondition(request);
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

	@RequestMapping(value = "/v1/item_condition", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getItemCondition() {
		// Log that request came to WS
		LOGGER.debug("getItemCondition()");

		try {
			Object response = enumMrBO.getItemCondition();
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

	@RequestMapping(value = "/v1/product_category", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addProductCategory(@RequestBody ProductCategoryEnum request) {
		// Log that request came to WS
		LOGGER.debug("addProductCategory(request:" + request + ")");

		try {
			enumMrBO.addProductCategory(request);
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

	@RequestMapping(value = "/v1/update_product_category", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateProductCategory(@RequestBody ProductCategoryEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateProductCategory(request:" + request + ")");

		try {
			enumMrBO.updateProductCategory(request);
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

	@RequestMapping(value = "/v1/product_category", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getProductCategory() {
		// Log that request came to WS
		LOGGER.debug("getProductCategory()");

		try {
			Object response = enumMrBO.getProductCategory();
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

	@RequestMapping(value = "/v1/product_category_tree", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getProductCategoryTree() {
		// Log that request came to WS
		LOGGER.debug("getProductCategoryTree()");

		try {
			Object response = enumMrBO.getProductCategoryTree();
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

	@RequestMapping(value = "/v1/product_pricing", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addProductPricing(@RequestBody ProductPricingEnum request) {
		// Log that request came to WS
		LOGGER.debug("addProductPricing(request:" + request + ")");

		try {
			enumMrBO.addProductPricing(request);
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

	@RequestMapping(value = "/v1/update_product_pricing", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateProductPricing(@RequestBody ProductPricingEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateProductPricing(request:" + request + ")");

		try {
			enumMrBO.updateProductPricing(request);
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

	@RequestMapping(value = "/v1/product_pricing", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getProductPricing() {
		// Log that request came to WS
		LOGGER.debug("getProductPricing()");

		try {
			Object response = enumMrBO.getProductPricing();
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

	@RequestMapping(value = "/v1/stock_tx_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addStockTxType(@RequestBody StockTxTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addStockTxType(request:" + request + ")");

		try {
			enumMrBO.addStockTxType(request);
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

	@RequestMapping(value = "/v1/update_stock_tx_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateStockTxType(@RequestBody StockTxTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateStockTxType(request:" + request + ")");

		try {
			enumMrBO.updateStockTxType(request);
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

	@RequestMapping(value = "/v1/stock_tx_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getStockTxType() {
		// Log that request came to WS
		LOGGER.debug("getStockTxType()");

		try {
			Object response = enumMrBO.getStockTxType();
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

	@RequestMapping(value = "/v1/mr_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addMrType(@RequestBody MrTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addMrType(request:" + request + ")");

		try {
			enumMrBO.addMrType(request);
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

	@RequestMapping(value = "/v1/update_mr_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateMrType(@RequestBody MrTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateMrType(request:" + request + ")");

		try {
			enumMrBO.updateMrType(request);
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

	@RequestMapping(value = "/v1/mr_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMrType() {
		// Log that request came to WS
		LOGGER.debug("getMrType()");

		try {
			Object response = enumMrBO.getMrType();
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

	@RequestMapping(value = "/v1/visit_type", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addVisitType(@RequestBody VisitTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("addVisitType(request:" + request + ")");

		try {
			enumMrBO.addVisitType(request);
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

	@RequestMapping(value = "/v1/update_visit_type", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateVisitType(@RequestBody VisitTypeEnum request) {
		// Log that request came to WS
		LOGGER.debug("updateVisitType(request:" + request + ")");

		try {
			enumMrBO.updateVisitType(request);
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

	@RequestMapping(value = "/v1/visit_type", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getVisitType() {
		// Log that request came to WS
		LOGGER.debug("getVisitType()");

		try {
			Object response = enumMrBO.getVisitType();
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
