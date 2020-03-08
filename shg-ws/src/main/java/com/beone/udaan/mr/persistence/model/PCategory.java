package com.beone.udaan.mr.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PCategory
 */
@Entity
@Table(name="p_category"
,catalog="shg"
, uniqueConstraints = {  }
		)

public class PCategory  implements java.io.Serializable {

	private static final long serialVersionUID = -3542569933830570145L;
	// Fields    
	private int pCategoryId;
	private String category;
	private String subCategoryLevel1;
	private String subCategoryLevel2;
	private String subCategoryLevel3;
	private String subCategoryLevel4;

	// Constructors

	/** default constructor */
	public PCategory() {
	}

	/** minimal constructor */
	public PCategory(int pCategoryId, String category) {
		this.pCategoryId = pCategoryId;
		this.category = category;
	}

	/** full constructor */
	public PCategory(String category, String subCategoryLevel1, String subCategoryLevel2, String subCategoryLevel3, String subCategoryLevel4) {
		this.category = category;
		this.subCategoryLevel1 = subCategoryLevel1;
		this.subCategoryLevel2 = subCategoryLevel2;
		this.subCategoryLevel3 = subCategoryLevel3;
		this.subCategoryLevel4 = subCategoryLevel4;
	}

	/** full constructor */
	public PCategory(int pCategoryId, String category, String subCategoryLevel1, String subCategoryLevel2, String subCategoryLevel3, String subCategoryLevel4) {
		this.pCategoryId = pCategoryId;
		this.category = category;
		this.subCategoryLevel1 = subCategoryLevel1;
		this.subCategoryLevel2 = subCategoryLevel2;
		this.subCategoryLevel3 = subCategoryLevel3;
		this.subCategoryLevel4 = subCategoryLevel4;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="p_category_id", unique=true, nullable=false, insertable=true, updatable=true)

	public int getPCategoryId() {
		return this.pCategoryId;
	}

	public void setPCategoryId(int pCategoryId) {
		this.pCategoryId = pCategoryId;
	}
	@Column(name="category", unique=false, nullable=false, insertable=true, updatable=true, length=45)

	public String getPCategory() {
		return this.category;
	}

	public void setPCategory(String pCategory) {
		this.category = pCategory;
	}
	@Column(name="sub_cat_l1", unique=false, nullable=true, insertable=true, updatable=true, length=45)

	public String getSubCategoryLevel1() {
		return subCategoryLevel1;
	}

	public void setSubCategoryLevel1(String subCategoryLevel1) {
		this.subCategoryLevel1 = subCategoryLevel1;
	}
	@Column(name="sub_cat_l2", unique=false, nullable=true, insertable=true, updatable=true, length=45)

	public String getSubCategoryLevel2() {
		return subCategoryLevel2;
	}

	public void setSubCategoryLevel2(String subCategoryLevel2) {
		this.subCategoryLevel2 = subCategoryLevel2;
	}
	@Column(name="sub_cat_l3", unique=false, nullable=true, insertable=true, updatable=true, length=45)

	public String getSubCategoryLevel3() {
		return subCategoryLevel3;
	}

	public void setSubCategoryLevel3(String subCategoryLevel3) {
		this.subCategoryLevel3 = subCategoryLevel3;
	}
	@Column(name="sub_cat_l4", unique=false, nullable=true, insertable=true, updatable=true, length=45)

	public String getSubCategoryLevel4() {
		return subCategoryLevel4;
	}

	public void setSubCategoryLevel4(String subCategoryLevel4) {
		this.subCategoryLevel4 = subCategoryLevel4;
	}

}
