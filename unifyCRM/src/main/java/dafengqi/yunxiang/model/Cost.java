package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(name = "crm_cost")
public class Cost implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	@Column(name = "expense_type")
	private String expenseType;
	private String[] expenseTypes;
	@Column(name = "expense_type_name")
	private String expenseTypeName;
	@Column(name = "cost_description")
	private String costDescription;
	@Column(name = "expense_amount")
	private String expenseAmount;
	@Column(name = "time_of_occurrence")
	private Date timeOfOccurrence;
	private String timeOfOccurrences;
	@Column(name = "customer_id")
	private String customerId;
	private String[] customerIds;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "contacts_id")
	private String contactsId;
	@Column(name = "contacts_name")
	private String contactsName;
	@Column(name = "business_id")
	private String businessId;
	@Column(name = "business_name")
	private String businessName;
	@Column(name = "contract_id")
	private String contractId;
	@Column(name = "contract_name")
	private String contractName;
	@Column(name = "follow_up_record")
	private String followUpRecord;
	@Column(name = "follow_up_record_name")
	private String followUpRecordName;
	@Column(name = "visit_sign_in")
	private String visitSignIn;
	@Column(name = "enclosure")
	private String enclosure;
	@Column(name = "person_id")
	private String personId;
	private String[] personIds;
	@Column(name = "person_name")
	private String personName;
	@Column(name = "create_id")
	private String createId;
	@Column(name = "create_name")
	private String createName;
	@Column(name = "create_date")
	private String createDate;
	@Column(name = "update_id")
	private String updateId;
	@Column(name = "update_name")
	private String updateName;
	@Column(name = "update_date")
	private String updateDate;
	@Column(name = "mechanism_id")
	private String mechanismId;
	private List<Date> createDateRange;

	@Transient
	private String type;
	@Transient
	private String from;
	
	@Column(name = "app_id")
	private String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	
	
	public Cost() {
	}

	public Cost(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Cost)) {
			return false;
		}
		Cost other = (Cost) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "dafengqi.yunxiang.model.Cost[ id=" + id + " ]";
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String[] getExpenseTypes() {
		return expenseTypes;
	}

	public void setExpenseTypes(String[] expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	public String getExpenseTypeName() {
		return expenseTypeName;
	}

	public void setExpenseTypeName(String expenseTypeName) {
		this.expenseTypeName = expenseTypeName;
	}

	public String getCostDescription() {
		return costDescription;
	}

	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}

	public String getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(String expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public Date getTimeOfOccurrence() {
		return timeOfOccurrence;
	}

	public void setTimeOfOccurrence(Date timeOfOccurrence) {
		this.timeOfOccurrence = timeOfOccurrence;
	}

	public String getTimeOfOccurrences() {
		return timeOfOccurrences;
	}

	public void setTimeOfOccurrences(String timeOfOccurrences) {
		this.timeOfOccurrences = timeOfOccurrences;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactsId() {
		return contactsId;
	}

	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getFollowUpRecord() {
		return followUpRecord;
	}

	public void setFollowUpRecord(String followUpRecord) {
		this.followUpRecord = followUpRecord;
	}


	public String getFollowUpRecordName() {
		return followUpRecordName;
	}

	public void setFollowUpRecordName(String followUpRecordName) {
		this.followUpRecordName = followUpRecordName;
	}

	public String getVisitSignIn() {
		return visitSignIn;
	}

	public void setVisitSignIn(String visitSignIn) {
		this.visitSignIn = visitSignIn;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String[] getPersonIds() {
		return personIds;
	}

	public void setPersonIds(String[] personIds) {
		this.personIds = personIds;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public List<Date> getCreateDateRange() {
		return createDateRange;
	}

	public void setCreateDateRange(List<Date> createDateRange) {
		this.createDateRange = createDateRange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
}	
