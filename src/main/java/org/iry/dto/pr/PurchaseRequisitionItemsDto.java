/**
 * 
 */
package org.iry.dto.pr;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * @author vaibhavp
 *
 */
public class PurchaseRequisitionItemsDto implements Serializable {
	
	private static final long serialVersionUID = 7394238383087126315L;
	
	private Long priId;
	private String description;
	private int totalQuantityRequired;
	private int quantityInStock;
	private int quantityTobePurchased;
	private String uom;
	private Double unitCost;
	private Double approxTotalCost;
	private String make;
	private String catNo;
	private String requiredByDateStr;
	private String preferredSupplier;
	
	private transient SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public PurchaseRequisitionItemsDto() {
		sdf = new SimpleDateFormat("");
		sdf.setTimeZone(new SimpleTimeZone(0,""));
	}
	
	public Long getPriId() {
		return priId;
	}
	public void setPriId(Long priId) {
		this.priId = priId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTotalQuantityRequired() {
		return totalQuantityRequired;
	}
	public void setTotalQuantityRequired(int totalQuantityRequired) {
		this.totalQuantityRequired = totalQuantityRequired;
	}
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	public int getQuantityTobePurchased() {
		return quantityTobePurchased;
	}
	public void setQuantityTobePurchased(int quantityTobePurchased) {
		this.quantityTobePurchased = quantityTobePurchased;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public Double getApproxTotalCost() {
		return approxTotalCost;
	}
	public void setApproxTotalCost(Double approxTotalCost) {
		this.approxTotalCost = approxTotalCost;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getCatNo() {
		return catNo;
	}
	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}
	public String getRequiredByDateStr() {
		return requiredByDateStr;
	}
	public Date getRequiredByDate() throws ParseException {
		if( requiredByDateStr == null || requiredByDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(requiredByDateStr);
	}
	public void setRequiredByDateStr(String requiredByDateStr) {
		this.requiredByDateStr = requiredByDateStr;
	}
	public void setRequiredByDate(Date requiredByDate) {
		if( requiredByDate != null ) {
			this.requiredByDateStr = sdf.format(requiredByDate);
		}
	}
	public String getPreferredSupplier() {
		return preferredSupplier;
	}
	public void setPreferredSupplier(String preferredSupplier) {
		this.preferredSupplier = preferredSupplier;
	}
}
