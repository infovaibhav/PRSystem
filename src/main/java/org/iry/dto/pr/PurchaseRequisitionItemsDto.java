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
	private String code;
	private String description;
	private String diamensions;
	private int quantityRequired;
	private String uom;
	private String make;
	private String specifications;
	private String requiredByDateStr;
	private String deliveryDateStr;
	private Integer orderedQuantity;
	private String deviation;
	private String invoiceNo;
	private String remark;
	private String invoiceDateStr;
	
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
	public int getQuantityRequired() {
		return quantityRequired;
	}
	public void setQuantityRequired(int quantityRequired) {
		this.quantityRequired = quantityRequired;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDiamensions() {
		return diamensions;
	}
	public void setDiamensions(String diamensions) {
		this.diamensions = diamensions;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
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
	public String getDeviation() {
		return deviation;
	}
	public void setDeviation(String deviation) {
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDateStr() {
		return invoiceDateStr;
	}
	
	public Date getInvoiceDate() throws ParseException {
		if( invoiceDateStr == null || invoiceDateStr.isEmpty() ) {
			return null;
		}
		return sdf.parse(invoiceDateStr);
	}
	
	public void setInvoiceDate(Date invoiceDateStr) {
		if( invoiceDateStr != null ) {
			this.invoiceDateStr = sdf.format(invoiceDateStr);
		}
	}
	
	public void setInvoiceDateStr(String deliveryDateStr) {
		this.invoiceDateStr = deliveryDateStr;
	}
}
