/**
 * 
 */
package org.iry.exceptions;

/**
 * @author vaibhavp
 *
 */
public class InvalidRequestException extends IryException {

	private static final long serialVersionUID = 3550771908807624087L;

	public InvalidRequestException(String errorMessage) {
		super(errorMessage);
	}
}
