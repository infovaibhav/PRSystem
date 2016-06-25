/**
 * 
 */
package org.iry.service.user;

import java.io.Serializable;

/**
 * @author vaibhavp
 *
 */
public class AllowedActions implements Serializable{
	
	private static final long serialVersionUID = 3621485362257663845L;

	public boolean admin = false;
	
	public boolean createPr = false;
	public boolean authorizePr = false;
	public boolean approvePr = false;
	public boolean reopenPr = false;
	public boolean editPrRemark = false;
	public boolean cancelPr = false;

	public boolean acknowledgePr = false;
	public boolean updateRequestQuote = false;
	public boolean updateReceiveQuote = false;
	public boolean updateFinalizeQuote = false;
	public boolean updatePoCreated = false;
	public boolean createPo = false;
	public boolean approvePo = false;
	public boolean editPrItemsRemark = false;
	
	public boolean updateReceiveMaterial = false;
	
}
