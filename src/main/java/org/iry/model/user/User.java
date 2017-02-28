package org.iry.model.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="APP_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 5088675486430341270L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name="SSO_ID", unique=true, nullable=false)
	private String ssoId;
	
	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@NotEmpty
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@NotEmpty
	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@NotEmpty
	@Column(name="EMAIL", nullable=false)
	private String email;

	@Column(name="AUTHORIZED_TRANSACTION_LIMIT", nullable=true)
	private Double authorizedTransactionLimit;

	@ManyToOne
	@JoinColumn(name = "REPORTING_TO")
	private User reportingTo;

	@Column(name="IS_ROOT")
	private boolean isRoot=false;
	
	@Column(name = "EMAIL_NOTIFICATION")
	private boolean emailNotification = true;

	@Column(name="IS_ACTIVE")
	private boolean isActive=true;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "APP_USER_USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID") }, 
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

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

	public String getFullName() {
		return this.getFirstName() + " " + this.getLastName();
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

	public User getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(User reportingTo) {
		this.reportingTo = reportingTo;
	}

	public boolean getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}
	
	public String getUserProfileStr() {
		StringBuilder sb = new StringBuilder();
		for(UserProfile userProfile : this.getUserProfiles()){
			if( sb.length() != 0 ) {
				sb.append(",");
			}
			sb.append(userProfile.getType());
		}
		return sb.toString();
	}
	
	public String getReportingToStr() {
		if( this.reportingTo != null ) {
			return this.reportingTo.getFirstName() + ' ' + this.getReportingTo().getLastName();
		}
		return null;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", isActive=" + isActive + ", userProfiles=" + userProfiles +"]";
	}

	public boolean isEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(boolean emailNotification) {
		this.emailNotification = emailNotification;
	}
	
}
