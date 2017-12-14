package com.simple.integration.model;

/*
 * Contact object in Salesforce
 */
public class Contact {
	public String FirstName;
	public String LastName;
	public String Birthdate;
	public String Email;
	public String LeadSource;
	public String Description;
	public String Contact_Ext_Id__c;

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getBirthdate() {
		return Birthdate;
	}

	public void setBirthdate(String birthdate) {
		Birthdate = birthdate;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getLeadSource() {
		return LeadSource;
	}

	public void setLeadSource(String leadSource) {
		LeadSource = leadSource;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
	
	public String getContact_Ext_Id__c() {
		return Contact_Ext_Id__c;
	}

	public void setContact_Ext_Id__c(String contact_Ext_Id__c) {
		Contact_Ext_Id__c = contact_Ext_Id__c;
	}

	@Override
	public String toString() {
		return "Contact [FirstName=" + FirstName + ", LastName=" + LastName + ", Birthdate=" + Birthdate + ", Email="
				+ Email + ", LeadSource=" + LeadSource + ", Description=" + Description + ", Contact_Ext_Id__c="
				+ Contact_Ext_Id__c + "]";
	}
}
