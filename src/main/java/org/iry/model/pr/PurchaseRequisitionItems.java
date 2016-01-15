/**
 * 
 */
package org.iry.model.pr;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author vaibhavp
 *
 */
@Entity
@Table(name="PURCHASE_REQUISITION_ITEMS")
public class PurchaseRequisitionItems {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PR_NO", nullable = false)
	private PurchaseRequisition purchaseRequisition;

	@Column(name="DESCRIPTION", nullable = false)
	private String description;

	@Column(name="TOTAL_QUANTITY_REQUIRED")
	private int totalQuantityRequired;

	@Column(name="QUANTITY_IN_STOCK")
	private int quantityInStock;

	@Column(name="QUANTITY_TO_BE_PURCHASED", nullable = false)
	private int quantityTobePurchased;

	@Column(name="UOM")
	private String uom;

	@Column(name="UNIT_COST")
	private Double unitCost;

	@Column(name="APPROX_TOTAL_COST")
	private Double approxTotalCost;

	@Column(name="MAKE")
	private String make;

	@Column(name="CAT_NO")
	private String catNo;

	@Column(name="REQUIRED_BY_DATE")
	private Timestamp requiredByDate;
	
	@Column(name="PREFERRED_SUPPLIER")
	private String preferredSupplier;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public PurchaseRequisition getPurchaseRequisition() {
		return purchaseRequisition;
	}
	public void setPurchaseRequisition(PurchaseRequisition purchaseRequisition) {
		this.purchaseRequisition = purchaseRequisition;
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
	public Timestamp getRequiredByDate() {
		return requiredByDate;
	}
	public void setRequiredByDate(Timestamp requiredByDate) {
		this.requiredByDate = requiredByDate;
	}
	public String getPreferredSupplier() {
		return preferredSupplier;
	}
	public void setPreferredSupplier(String preferredSupplier) {
		this.preferredSupplier = preferredSupplier;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseRequisitionItems other = (PurchaseRequisitionItems) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
