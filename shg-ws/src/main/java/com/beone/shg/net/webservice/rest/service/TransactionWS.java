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
import com.beone.shg.net.persistence.bo.TransactionBO;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransaction;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransactions;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.TransactionFilter;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.support.AccessCheckUtil;
import com.beone.shg.net.webservice.rest.support.AccessDeniedException;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.RestResponse;
import com.beone.shg.net.webservice.rest.util.HttpRequestUtil;

@Controller
@RequestMapping("/transaction")
public class TransactionWS implements InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionWS.class);

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@UnsecuredMethod(justification = "Method to Init the @Controller")
	@Override
	// initialization
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("afterPropertiesSet()");
	}

	// API to Add new transaction 
	@RequestMapping(value = "/v1/transaction", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addTransaction(@RequestBody Transaction request) {
		
		// Log that request came to WS
		LOGGER.debug("addTransaction(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Transaction response = transactionBO.addTransaction(request);      
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

	// API to Add new transactions
	@RequestMapping(value = "/v1/transactions", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> addTransactions(@RequestBody List<Transaction> request) {
		
		// Log that request came to WS
		LOGGER.debug("addTransactions(request:" + request + ")");

		try {
			for(Transaction tran: request) {
				ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
				AccessCheckUtil.passForGroupAcNo(token, tran.getGroupAcNo());
			}

			List<Transaction> response = transactionBO.addTransactions(request);      
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

	// API to update transactions
	@RequestMapping(value = "/v1/update_transaction", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateTransaction(@RequestBody Transaction request) {
		
		// Log that request came to WS
		LOGGER.debug("updateTransaction(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			transactionBO.updateTransaction(request);      
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

	// API to update transactions
	@RequestMapping(value = "/v1/update_transactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateTransactions(@RequestBody List<Transaction> request) {
		
		// Log that request came to WS
		LOGGER.debug("updateTransactions(request:" + request + ")");

		try {
			for(Transaction tran: request) {
				ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
				AccessCheckUtil.passForGroupAcNo(token, tran.getGroupAcNo());
			}

			List<Transaction> response = transactionBO.updateTransactions(request);      
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

	// API to Undo a transaction 
	@RequestMapping(value = "/v1/track_transactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> trackTransactions(@RequestBody TransactionFilter request) { 
		
		// Log that request came to WS
		LOGGER.debug("trackTransactions(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Object response = transactionBO.trackTransactions(request);     
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

	// API to Undo a transaction 
	@RequestMapping(value = "/v1/undo_transaction", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> undoTransaction(@RequestBody Transaction request) { 
		
		// Log that request came to WS
		LOGGER.debug("undoTransaction(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			transactionBO.undoTransaction(request, true);     
			return RestResponse.OK(request);

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

	// API to Undo a transaction 
	@RequestMapping(value = "/v1/undo_transactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> undoTransactions(@RequestBody List<Transaction> request) { 
		
		// Log that request came to WS
		LOGGER.debug("undoTransactions(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			for(Transaction tx: request) {
				AccessCheckUtil.passForGroupAcNo(token, tx.getGroupAcNo());
			}

			transactionBO.undoTransactions(request);     
			return RestResponse.OK(request);

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

	// API to Approve Reject a transaction 
	@RequestMapping(value = "/v1/approve_reject_transaction", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> approveRejectTransaction(@RequestBody Transaction request) { 
		
		// Log that request came to WS
		LOGGER.debug("approveRejectTransaction(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			transactionBO.approveRejectTransaction(request, true);     
			return RestResponse.OK(request);

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

	// API to Approve Reject a transaction 
	@RequestMapping(value = "/v1/approve_reject_transactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> approveRejectTransactions(@RequestBody List<Transaction> request) { 
		
		// Log that request came to WS
		LOGGER.debug("approveRejectTransactions(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			for(Transaction tx: request) {
				AccessCheckUtil.passForGroupAcNo(token, tx.getGroupAcNo());
			}

			Object response = transactionBO.approveRejectTransactions(request);     
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

	// API to Counter a transaction 
	@RequestMapping(value = "/v1/countert_transaction", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> counterTransaction(@RequestBody Transaction request) { 
		
		// Log that request came to WS
		LOGGER.debug("counterTransaction(request:" + request + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, request.getGroupAcNo());

			Transaction response = transactionBO.counterTransaction(request);     
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

	// API to Reject a transaction 
	@RequestMapping(value = "/v1/reject_todo_transactions", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> rejectTodoTransactions(@RequestBody TodoTransactions request) { 
		
		// Log that request came to WS
		LOGGER.debug("rejectTodoTransactions(request:" + request + ")");

		try {
			for(TodoTransaction tran: request.getTodoTransactions()) {
				ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
				AccessCheckUtil.passForGroupAcNo(token, tran.getGroupAcNo());
			}

			transactionBO.rejectTodoTransactions(request);     
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

	// API to Used for loading Saving & Loan Installment collection with status for entier group members
	@RequestMapping(value = "/v1/group_todo_transactions/{lang}/{groupAcNo}/{startTime}/{endTime}/{txType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupTodoTxs(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") long groupAcNo, 
			@PathVariable("startTime") long startTime,
			@PathVariable("endTime") long endTime,
			@PathVariable("txType") String txType) {
		
		// Log that request came to WS
		LOGGER.debug("getGroupTodoTxs(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", startTime:" + startTime + 
				", endTime:" + endTime + 
				", txType:" + txType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			TodoTransactions response = transactionBO.getGroupTodoTxs(lang, groupAcNo, startTime, endTime, txType);   
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

	// API to Used for loading Saving & Loan Installment collection with status for entier group members
	@RequestMapping(value = "/v1/member_todo_transactions/{lang}/{memberAcNo}/{startTime}/{endTime}/{txType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberTodoTxs(@PathVariable("lang") String lang,
			@PathVariable("memberAcNo") long memberAcNo, 
			@PathVariable("startTime") long startTime,
			@PathVariable("endTime") long endTime,
			@PathVariable("txType") String txType) {
		
		// Log that request came to WS
		LOGGER.debug("getMemberTodoTxs(lang:" + lang + 
				", memberAcNo:" + memberAcNo + 
				", startTime:" + startTime + 
				", endTime:" + endTime + 
				", txType:" + txType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);

			TodoTransactions response = transactionBO.getMemberTodoTxs(lang, memberAcNo, startTime, endTime, txType);
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

	// API to Used for loading Saving & Loan Installment collection with status for entier group members
	@RequestMapping(value = "/v1/group_transactions/{lang}/{groupAcNo}/{startTime}/{endTime}/{txType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupTxs(@PathVariable("lang") String lang,
			@PathVariable("groupAcNo") long groupAcNo, 
			@PathVariable("startTime") long startTime,
			@PathVariable("endTime") long endTime,
			@PathVariable("txType") String txType) {
		
		// Log that request came to WS
		LOGGER.debug("getGroupTxs(lang:" + lang + 
				", groupAcNo:" + groupAcNo + 
				", startTime:" + startTime + 
				", endTime:" + endTime + 
				", txType:" + txType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			Transactions response = transactionBO.getGroupTxs(lang, groupAcNo, startTime, endTime, txType);
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

	// API to Used for Get Ready Transactions for the Group
	@RequestMapping(value = "/v1/group_ready_transactions/{lang}/{groupAcNo}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getGroupReadyTxs(@PathVariable("lang") String lang, @PathVariable("groupAcNo") long groupAcNo) {
		
		// Log that request came to WS
		LOGGER.debug("getGroupReadyTxs(lang:" + lang + ", groupAcNo:" + groupAcNo + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

			Transactions response = transactionBO.getGroupReadyTxs(lang, groupAcNo);
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

	// API to Used for loading Saving & Loan Installment collection with status for entier group members
	@RequestMapping(value = "/v1/member_transactions/{lang}/{memberAcNo}/{startTime}/{endTime}/{txType}", produces = "application/json", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getMemberTxs(@PathVariable("lang") String lang,
			@PathVariable("memberAcNo") long memberAcNo, 
			@PathVariable("startTime") long startTime,
			@PathVariable("endTime") long endTime,
			@PathVariable("txType") String txType) {
		
		// Log that request came to WS
		LOGGER.debug("getMemberTxs(lang:" + lang + 
				", memberAcNo:" + memberAcNo + 
				", startTime:" + startTime + 
				", endTime:" + endTime + 
				", txType:" + txType + ")");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupByMemberAcNo(token, memberAcNo);

			Transactions response = transactionBO.getMemberTxs(lang, memberAcNo, startTime, endTime, txType);
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

    @RequestMapping(value = "/v1/tx_info_file/{groupAcNo}/{txId}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<?> transactionInfoFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("txId") long txId,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("transactionInfoFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        Object responce = transactionBO.transactionInfoFile(groupAcNo, txId, fileItem.getName(), docTypeId, fileItem.get());

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

    @RequestMapping(value = "/v1/update_tx_info_file/{groupAcNo}/{docId}/{txId}/{docTypeId}", consumes = "multipart/form-data", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<?> updateTransactionInfoFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("txId") long txId,
    		@PathVariable("docTypeId") int docTypeId,
    		HttpServletRequest request) {
    	
		// Log that request came to WS
		LOGGER.debug("updateTransactionInfoFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);
			
			FileItem fileItem = HttpRequestUtil.parseRequestToGetFileItem(request);

	        Object responce = transactionBO.updateTransactionInfoFile(groupAcNo, docId, txId, fileItem.getName(), docTypeId, fileItem.get());

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

    @RequestMapping(value = "/v1/delete_tx_info_file/{groupAcNo}/{docId}", consumes = "application/json", produces = "application/json", method = RequestMethod.DELETE)
	@ResponseBody
    public ResponseEntity<?> deleteTransactionInfoFile(@PathVariable("groupAcNo") long groupAcNo,
    		@PathVariable("docId") long docId,
    		@PathVariable("txId") long txId) {
    	
		// Log that request came to WS
		LOGGER.debug("deleteTransactionInfoFile()");

		try {
			ShgAuthToken token = ((ShgAuthToken)SecurityContextHolder.getContext().getAuthentication());
			AccessCheckUtil.passForGroupAcNo(token, groupAcNo);

	        transactionBO.deleteTransactionInfoFile(groupAcNo, docId, txId);
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
