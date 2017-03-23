/**
 * 
 */
package org.iry.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author nikhilka
 *
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(-1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		
	}

}
