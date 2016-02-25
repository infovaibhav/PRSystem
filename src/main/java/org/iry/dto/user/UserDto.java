package org.iry.dto.user;

import java.io.Serializable;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 5088675486430341270L;

	private Long id;
	private String ssoId;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Double authorizedTransactionLimit;
	private String reportingTo;
	private String roles;
	private String status;
	private String newPassword;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSsoId() {
		return ssoId;
	}
	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getAuthorizedTransactionLimit() {
		return authorizedTransactionLimit;
	}
	public void setAuthorizedTransactionLimit(Double authorizedTransactionLimit) {
		this.authorizedTransactionLimit = authorizedTransactionLimit;
	}
	public String getReportingTo() {
		return reportingTo;
	}
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
