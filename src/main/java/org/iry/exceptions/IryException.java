/**
 * 
 */
package org.iry.exceptions;

/**
 * @author vaibhavp
 *
 */
public class IryException extends RuntimeException {

	private static final long serialVersionUID = -4051704600263817341L;

	public IryException(final String errorMessage) {
		super(errorMessage);
	}

	public IryException(final String errorMessage, final Throwable exception) {
		super(errorMessage, exception);
	}
}
