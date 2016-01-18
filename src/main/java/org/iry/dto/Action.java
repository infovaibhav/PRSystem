/**
 * 
 */
package org.iry.dto;

/**
 * @author vaibhavp
 *
 */
public class Action {
	
	public String key = null;
	public String value = null;
	
	public Action(String key) {
		this(key, key);
	}
	public Action(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
