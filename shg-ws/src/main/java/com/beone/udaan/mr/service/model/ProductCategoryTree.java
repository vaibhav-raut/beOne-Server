package com.beone.udaan.mr.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.udaan.mr.persistence.model.PCategory;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ProductCategoryTree {
	
	public static final String SubCategoryLevel1 = "subCategoryLevel1";
	public static final String SubCategoryLevel2 = "subCategoryLevel2";
	public static final String SubCategoryLevel3 = "subCategoryLevel3";
	public static final String SubCategoryLevel4 = "subCategoryLevel4";
	
	private String catLevel;
	private String catLevelName;
	private String productCategory;
	private List<ProductCategoryTree> catTreeValues;
	
	public ProductCategoryTree() {
		catTreeValues = new ArrayList<ProductCategoryTree>();
	}
	
	public String getCatLevel() {
		return catLevel;
	}

	public void setCatLevel(String catLevel) {
		this.catLevel = catLevel;
	}

	public String getCatLevelName() {
		return catLevelName;
	}

	public void setCatLevelName(String catLevelName) {
		this.catLevelName = catLevelName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public List<ProductCategoryTree> getCatTreeValues() {
		return catTreeValues;
	}

	public void setCatTreeValues(List<ProductCategoryTree> catTreeValues) {
		this.catTreeValues = catTreeValues;
	}

	public void addCatTreeValue(ProductCategoryTree value) {
		this.catTreeValues.add(value);
	}
	
	public static ProductCategoryTree buildTree(List<PCategory> pCategorys) {
		
		ProductCategoryTree tree = new ProductCategoryTree();
		tree.setCatLevel(SubCategoryLevel1);
		Map<String, ProductCategoryTree> treeMap = new HashMap<String, ProductCategoryTree>();
		
		for(PCategory pCategory: pCategorys) {
			
			// Level 1 Computation 
			String level1Key = "1" + pCategory.getSubCategoryLevel1();
			if(!treeMap.containsKey(level1Key)) {
				// Create tree
				ProductCategoryTree treeL1 = new ProductCategoryTree();
				treeL1.setCatLevelName(pCategory.getSubCategoryLevel1());
				treeL1.setCatLevel(SubCategoryLevel1);
				
				// Add new Tree node 
				tree.addCatTreeValue(treeL1);
				
				// Add to Map for quick search
				treeMap.put(level1Key, treeL1);
			}
			
			// Level 2 Computation 
			String level2Key = level1Key + "2" + pCategory.getSubCategoryLevel2();
			if(!treeMap.containsKey(level2Key)) {
				// Create tree
				ProductCategoryTree treeL2 = new ProductCategoryTree();
				treeL2.setCatLevelName(pCategory.getSubCategoryLevel2());
				treeL2.setCatLevel(SubCategoryLevel2);
				
				// Add new Tree node 
				treeMap.get(level1Key).addCatTreeValue(treeL2);
				
				// Add to Map for quick search
				treeMap.put(level2Key, treeL2);
			}
			
			// Level 3 Computation 
			String level3Key = level2Key + "3" + pCategory.getSubCategoryLevel3();
			if(!treeMap.containsKey(level3Key)) {
				// Create tree
				ProductCategoryTree treeL3 = new ProductCategoryTree();
				treeL3.setCatLevelName(pCategory.getSubCategoryLevel3());
				treeL3.setCatLevel(SubCategoryLevel3);
				
				// Add new Tree node 
				treeMap.get(level2Key).addCatTreeValue(treeL3);
				
				// Add to Map for quick search
				treeMap.put(level3Key, treeL3);
			}
			
			// Level 4 Computation 
			String level4Key = level3Key + "4" + pCategory.getSubCategoryLevel4();
			if(!treeMap.containsKey(level4Key)) {
				// Create tree
				ProductCategoryTree treeL4 = new ProductCategoryTree();
				treeL4.setCatLevelName(pCategory.getSubCategoryLevel4());
				treeL4.setCatLevel(SubCategoryLevel4);
				treeL4.setProductCategory(pCategory.getPCategory());
				
				// Add new Tree node 
				treeMap.get(level3Key).addCatTreeValue(treeL4);
			}
		}
		
		treeMap.clear();
		return tree;
	}
}
