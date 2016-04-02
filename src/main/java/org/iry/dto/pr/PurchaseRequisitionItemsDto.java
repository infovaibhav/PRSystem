/**
 * 
 */
package org.iry.dto.pr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.iry.dto.BaseDto;

/**
 * @author vaibhavp
 *
 */
public class PurchaseRequisitionItemsDto implements BaseDto {
	
	private static final long serialVersionUID = 7394238383087126315L;
	
	private Long priId;
	private String description;
	private int totalQuantityRequired;
	private String uom;
	private String make;
	private String catNo;
	private String requiredByDateStr;
	private String deliveryDateStr;
	private Integer orderedQuantity;
	private Double deviation;
	private String remark;
	
	private transient SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public PurchaseRequisitionItemsDto() {
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
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
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

	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}

	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}

	public Double getDeviation() {
		return deviation;
	}

	public void setDeviation(Double deviation) {
		this.deviation = deviation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}

	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}
	
	public void setDeliveryDate(Date deliveryDate) {
		if( deliveryDate != null ) {
			this.deliveryDateStr = sdf.format(deliveryDate);
		}
	}
	
	public Date getDeliveryDate() throws ParseException {
		if( deliveryDateStr == null || deliveryDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(deliveryDateStr);
	}
}
