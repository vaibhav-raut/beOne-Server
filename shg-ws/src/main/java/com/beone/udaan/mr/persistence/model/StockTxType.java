package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * StockTxType
 */
@Entity
@Table(name="stock_tx_type"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class StockTxType  implements java.io.Serializable {

	private static final long serialVersionUID = -6256756801036532051L;
	// Fields    
	private int stockTxTypeId;
	private String stockTxType;
	private String txFor;
	private String stockTxTypeDesc;
	private String preP1ActionFormula;
	private String preP2ActionFormula;
	private String preP3ActionFormula;
	private String postP1ActionFormula;
	private String postP2ActionFormula;
	private String postP3ActionFormula;

	// Constructors

	/** default constructor */
	public StockTxType() {
	}

	/** minimal constructor */
	public StockTxType(int stockTxTypeId, String stockTxType) {
		this.stockTxTypeId = stockTxTypeId;
		this.stockTxType = stockTxType;
	}

	/** full constructor */
	public StockTxType(String stockTxType, String txFor,
			String stockTxTypeDesc, String preP1ActionFormula,
			String preP2ActionFormula, String preP3ActionFormula,
			String postP1ActionFormula, String postP2ActionFormula,
			String postP3ActionFormula) {
		super();
		this.stockTxType = stockTxType;
		this.txFor = txFor;
		this.stockTxTypeDesc = stockTxTypeDesc;
		this.preP1ActionFormula = preP1ActionFormula;
		this.preP2ActionFormula = preP2ActionFormula;
		this.preP3ActionFormula = preP3ActionFormula;
		this.postP1ActionFormula = postP1ActionFormula;
		this.postP2ActionFormula = postP2ActionFormula;
		this.postP3ActionFormula = postP3ActionFormula;
	}

	/** full constructor */
	public StockTxType(int stockTxTypeId, String stockTxType, String txFor,
			String stockTxTypeDesc, String preP1ActionFormula,
			String preP2ActionFormula, String preP3ActionFormula,
			String postP1ActionFormula, String postP2ActionFormula,
			String postP3ActionFormula) {
		super();
		this.stockTxTypeId = stockTxTypeId;
		this.stockTxType = stockTxType;
		this.txFor = txFor;
		this.stockTxTypeDesc = stockTxTypeDesc;
		this.preP1ActionFormula = preP1ActionFormula;
		this.preP2ActionFormula = preP2ActionFormula;
		this.preP3ActionFormula = preP3ActionFormula;
		this.postP1ActionFormula = postP1ActionFormula;
		this.postP2ActionFormula = postP2ActionFormula;
		this.postP3ActionFormula = postP3ActionFormula;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="stock_tx_type_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getStockTxTypeId() {
		return this.stockTxTypeId;
	}

	public void setStockTxTypeId(int stockTxTypeId) {
		this.stockTxTypeId = stockTxTypeId;
	}
	@Column(name="stock_tx_type", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getStockTxType() {
		return this.stockTxType;
	}

	public void setStockTxType(String stockTxType) {
		this.stockTxType = stockTxType;
	}

	@Column(name="tx_for", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getTxFor() {
		return txFor;
	}

	public void setTxFor(String txFor) {
		this.txFor = txFor;
	}

	@Column(name="description", unique=false, nullable=true, insertable=true, updatable=true, length=100)
	public String getStockTxTypeDesc() {
		return this.stockTxTypeDesc;
	}

	public void setStockTxTypeDesc(String stockTxTypeDesc) {
		this.stockTxTypeDesc = stockTxTypeDesc;
	}
	@Column(name="pre_p1_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPreP1ActionFormula() {
		return preP1ActionFormula;
	}

	public void setPreP1ActionFormula(String preP1ActionFormula) {
		this.preP1ActionFormula = preP1ActionFormula;
	}
	@Column(name="pre_p2_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPreP2ActionFormula() {
		return preP2ActionFormula;
	}

	public void setPreP2ActionFormula(String preP2ActionFormula) {
		this.preP2ActionFormula = preP2ActionFormula;
	}
	@Column(name="pre_p3_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPreP3ActionFormula() {
		return preP3ActionFormula;
	}

	public void setPreP3ActionFormula(String preP3ActionFormula) {
		this.preP3ActionFormula = preP3ActionFormula;
	}
	@Column(name="post_p1_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPostP1ActionFormula() {
		return postP1ActionFormula;
	}

	public void setPostP1ActionFormula(String postP1ActionFormula) {
		this.postP1ActionFormula = postP1ActionFormula;
	}
	@Column(name="post_p2_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPostP2ActionFormula() {
		return postP2ActionFormula;
	}

	public void setPostP2ActionFormula(String postP2ActionFormula) {
		this.postP2ActionFormula = postP2ActionFormula;
	}
	@Column(name="post_p3_action_formula", unique=false, nullable=true, insertable=true, updatable=true, length=3000)

	public String getPostP3ActionFormula() {
		return postP3ActionFormula;
	}

	public void setPostP3ActionFormula(String postP3ActionFormula) {
		this.postP3ActionFormula = postP3ActionFormula;
	}
}
