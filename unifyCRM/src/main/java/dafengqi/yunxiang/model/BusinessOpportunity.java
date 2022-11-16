package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;

@Entity
@Table(name = "crm_business_opportunity")
@XmlRootElement
public class BusinessOpportunity implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	@Size(max = 45)
	@Column(name = "business_opportunity_title")
	private String businessOpportunityTitle;
	@Size(max = 45)
	@Column(name = "customer_name")
	private String customerName;
	@Size(max = 45)
	@Column(name = "estimated_amount")
	private String estimatedAmount;
	private String estimatedAmounts;
	@Size(max = 45)
	@Column(name = "estimatedSalesQuantity")
	private BigDecimal estimatedSalesQuantity;
	@Size(max = 45)
	@Column(name = "expected_signing_date")
	private Date expectedSigningDate;
	@Size(max = 45)
	@Column(name = "biddingProcurementDate")
	private Date biddingProcurementDate;
	@Size(max = 45)
	@Column(name = "expected_signing_dates")
	private String expectedSigningDates;
	@Size(max = 45)
	@Column(name = "biddingProcurementDates")
	private String biddingProcurementDates;
	@Size(max = 45)
	@Column(name = "sales_stage")
	private String salesStage;
    private String[] salesStages;
	@Size(max = 45)
	@Column(name = "sales_stage_name")
	private String salesStageName;
	@Size(max = 45)
	@Column(name = "next_follow_time")
	private Date nextFollowTime;
	@Size(max = 45)
	@Column(name = "next_follow_times")
	private String nextFollowTimes;
	@Size(max = 45)
	@Column(name = "business_opportunity_type")
	private String businessOpportunityType;
    private String[] businessOpportunityTypes;
	@Size(max = 45)
	@Column(name = "business_opportunity_type_name")
	private String businessOpportunityTypeName;
	@Size(max = 45)
	@Column(name = "business_opportunity_time")
	private Date businessOpportunityTime;
	@Size(max = 45)
	@Column(name = "business_opportunity_times")
	private String businessOpportunityTimes;
	@Size(max = 45)
	@Column(name = "business_opportunity_source")
	private String businessOpportunitySource;
    private String[] businessOpportunitySources;
	@Size(max = 45)
	@Column(name = "business_opportunity_source_name")
	private String businessOpportunitySourceName;
	@Size(max = 45)
	@Column(name = "person_id")
	private String personId;
    private String[] personIds;
	@Size(max = 45)
	@Column(name = "person_name")
	private String personName;
	@Size(max = 45)
	@Column(name = "department_id")
	private String departmentId;
	private String departmentIds;
	@Size(max = 45)
	@Column(name = "department_name")
	private String departmentName;
	@Size(max = 45)
	@Column(name = "audit")
	private String audit;
	@Size(max = 45)
	@Column(name = "remarks")
	private String remarks;
	@Size(max = 45)
	@Column(name = "create_id")
	private String createId;
    private String[] createIds;
	@Size(max = 45)
	@Column(name = "create_name")
	private String createName;
	@Size(max = 45)
	@Column(name = "create_date")
	private String createDate;
	@Size(max = 45)
	@Column(name = "update_id")
	private String updateId;
	@Size(max = 45)
	@Column(name = "update_name")
	private String updateName;
	@Size(max = 45)
	@Column(name = "update_date")
	private String updateDate;
	@Size(max = 45)
	@Column(name = "customer_id")
	private String customerId;
    private String[] customerIds;
	@Size(max = 45)
	@Column(name = "unit")
	private String unit;
	@Size(max = 45)
	@Column(name = "crm_responsible_id")
	private String crmResponsibleId;
	@Size(max = 45)
	@Column(name = "project_debriefing")
	private String projectDebriefing;
	@Size(max = 45)
	@Column(name = "degree_of_importance")
	private String degreeOfImportance;
	@Size(max = 45)
	@Column(name = "crm_business_opportunitycol_classification")
	private String crmBusinessOpportunitycolClassification;
	@Size(max = 45)
	@Column(name = "reminder_frequency")
	private String reminderFrequency;
	@Size(max = 45)
	@Column(name = "sys_project_debriefing_id")
	private String sysProjectDebriefingId;
	@Size(max = 45)
	@Column(name = "sys_reminder_settings_id")
	private String sysReminderSettingsId;
	@Size(max = 45)
	@Column(name = "crm_product_id")
	private String crmProductId;
	@Size(max = 45)
	@Column(name = "crm_contacts_id")
	private String crmContactsId;
	@Size(max = 45)
	@Column(name = "crm_payment_collection_plan_id")
	private String crmPaymentCollectionPlanId;
	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;
	@Size(max = 45)
	@Column(name = "state")
	private String state;
	private List<Date> createDateRange;
	private List<Date> nextFollowTimeDateRange;
	private List<Date> updateDateRange;
	private List<Date> expectedSigningDateRange;
	private List<Date> businessOpportunityTimeDateRange;
	private List<Contract> contractList = new ArrayList<Contract>();
	private List<Cost> costList = new ArrayList<Cost>();

	@Transient
	private String from;

	@Transient
	private Date time;
	@Transient
	private String content;
	@Transient
	private String followUp;
	@Transient
	private String contactsId;
	@Transient
	private String contactsName;
	
	@Size(max = 45)
	@Column(name = "app_id")
	private String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	

	
	public BusinessOpportunity() {
	}

	public BusinessOpportunity(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof BusinessOpportunity)) {
			return false;
		}
		BusinessOpportunity other = (BusinessOpportunity) object;
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
		return "dafengqi.yunxiang.model.BusinessOpportunity[ id=" + id + " ]";
	}

	public String getBusinessOpportunityTitle() {
		return businessOpportunityTitle;
	}

	public void setBusinessOpportunityTitle(String businessOpportunityTitle) {
		this.businessOpportunityTitle = businessOpportunityTitle;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEstimatedAmount() {
		return estimatedAmount;
	}

	public void setEstimatedAmount(String estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	public String getEstimatedAmounts() {
		return estimatedAmounts;
	}

	public void setEstimatedAmounts(String estimatedAmounts) {
		this.estimatedAmounts = estimatedAmounts;
	}

	public BigDecimal getEstimatedSalesQuantity() {
		return estimatedSalesQuantity;
	}

	public void setEstimatedSalesQuantity(BigDecimal estimatedSalesQuantity) {
		this.estimatedSalesQuantity = estimatedSalesQuantity;
	}

	public Date getExpectedSigningDate() {
		return expectedSigningDate;
	}

	public void setExpectedSigningDate(Date expectedSigningDate) {
		this.expectedSigningDate = expectedSigningDate;
	}

	public Date getBiddingProcurementDate() {
		return biddingProcurementDate;
	}

	public void setBiddingProcurementDate(Date biddingProcurementDate) {
		this.biddingProcurementDate = biddingProcurementDate;
	}

	public String getExpectedSigningDates() {
		return expectedSigningDates;
	}

	public void setExpectedSigningDates(String expectedSigningDates) {
		this.expectedSigningDates = expectedSigningDates;
	}

	public String getBiddingProcurementDates() {
		return biddingProcurementDates;
	}

	public void setBiddingProcurementDates(String biddingProcurementDates) {
		this.biddingProcurementDates = biddingProcurementDates;
	}

	public String getSalesStage() {
		return salesStage;
	}

	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}

	public String[] getSalesStages() {
		return salesStages;
	}

	public void setSalesStages(String[] salesStages) {
		this.salesStages = salesStages;
	}

	public String getSalesStageName() {
		return salesStageName;
	}

	public void setSalesStageName(String salesStageName) {
		this.salesStageName = salesStageName;
	}

	public Date getNextFollowTime() {
		return nextFollowTime;
	}

	public void setNextFollowTime(Date nextFollowTime) {
		this.nextFollowTime = nextFollowTime;
	}

	public String getNextFollowTimes() {
		return nextFollowTimes;
	}

	public void setNextFollowTimes(String nextFollowTimes) {
		this.nextFollowTimes = nextFollowTimes;
	}

	public String getBusinessOpportunityType() {
		return businessOpportunityType;
	}

	public void setBusinessOpportunityType(String businessOpportunityType) {
		this.businessOpportunityType = businessOpportunityType;
	}

	public String[] getBusinessOpportunityTypes() {
		return businessOpportunityTypes;
	}

	public void setBusinessOpportunityTypes(String[] businessOpportunityTypes) {
		this.businessOpportunityTypes = businessOpportunityTypes;
	}

	public String getBusinessOpportunityTypeName() {
		return businessOpportunityTypeName;
	}

	public void setBusinessOpportunityTypeName(String businessOpportunityTypeName) {
		this.businessOpportunityTypeName = businessOpportunityTypeName;
	}

	public Date getBusinessOpportunityTime() {
		return businessOpportunityTime;
	}

	public void setBusinessOpportunityTime(Date businessOpportunityTime) {
		this.businessOpportunityTime = businessOpportunityTime;
	}

	public String getBusinessOpportunityTimes() {
		return businessOpportunityTimes;
	}

	public void setBusinessOpportunityTimes(String businessOpportunityTimes) {
		this.businessOpportunityTimes = businessOpportunityTimes;
	}

	public String getBusinessOpportunitySource() {
		return businessOpportunitySource;
	}

	public void setBusinessOpportunitySource(String businessOpportunitySource) {
		this.businessOpportunitySource = businessOpportunitySource;
	}

	public String[] getBusinessOpportunitySources() {
		return businessOpportunitySources;
	}

	public void setBusinessOpportunitySources(String[] businessOpportunitySources) {
		this.businessOpportunitySources = businessOpportunitySources;
	}

	public String getBusinessOpportunitySourceName() {
		return businessOpportunitySourceName;
	}

	public void setBusinessOpportunitySourceName(String businessOpportunitySourceName) {
		this.businessOpportunitySourceName = businessOpportunitySourceName;
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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String[] getCreateIds() {
		return createIds;
	}

	public void setCreateIds(String[] createIds) {
		this.createIds = createIds;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCrmResponsibleId() {
		return crmResponsibleId;
	}

	public void setCrmResponsibleId(String crmResponsibleId) {
		this.crmResponsibleId = crmResponsibleId;
	}

	public String getProjectDebriefing() {
		return projectDebriefing;
	}

	public void setProjectDebriefing(String projectDebriefing) {
		this.projectDebriefing = projectDebriefing;
	}

	public String getDegreeOfImportance() {
		return degreeOfImportance;
	}

	public void setDegreeOfImportance(String degreeOfImportance) {
		this.degreeOfImportance = degreeOfImportance;
	}

	public String getCrmBusinessOpportunitycolClassification() {
		return crmBusinessOpportunitycolClassification;
	}

	public void setCrmBusinessOpportunitycolClassification(String crmBusinessOpportunitycolClassification) {
		this.crmBusinessOpportunitycolClassification = crmBusinessOpportunitycolClassification;
	}

	public String getReminderFrequency() {
		return reminderFrequency;
	}

	public void setReminderFrequency(String reminderFrequency) {
		this.reminderFrequency = reminderFrequency;
	}

	public String getSysProjectDebriefingId() {
		return sysProjectDebriefingId;
	}

	public void setSysProjectDebriefingId(String sysProjectDebriefingId) {
		this.sysProjectDebriefingId = sysProjectDebriefingId;
	}

	public String getSysReminderSettingsId() {
		return sysReminderSettingsId;
	}

	public void setSysReminderSettingsId(String sysReminderSettingsId) {
		this.sysReminderSettingsId = sysReminderSettingsId;
	}

	public String getCrmProductId() {
		return crmProductId;
	}

	public void setCrmProductId(String crmProductId) {
		this.crmProductId = crmProductId;
	}

	public String getCrmContactsId() {
		return crmContactsId;
	}

	public void setCrmContactsId(String crmContactsId) {
		this.crmContactsId = crmContactsId;
	}

	public String getCrmPaymentCollectionPlanId() {
		return crmPaymentCollectionPlanId;
	}

	public void setCrmPaymentCollectionPlanId(String crmPaymentCollectionPlanId) {
		this.crmPaymentCollectionPlanId = crmPaymentCollectionPlanId;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Date> getCreateDateRange() {
		return createDateRange;
	}

	public void setCreateDateRange(List<Date> createDateRange) {
		this.createDateRange = createDateRange;
	}

	public List<Date> getNextFollowTimeDateRange() {
		return nextFollowTimeDateRange;
	}

	public void setNextFollowTimeDateRange(List<Date> nextFollowTimeDateRange) {
		this.nextFollowTimeDateRange = nextFollowTimeDateRange;
	}

	public List<Date> getUpdateDateRange() {
		return updateDateRange;
	}

	public void setUpdateDateRange(List<Date> updateDateRange) {
		this.updateDateRange = updateDateRange;
	}

	public List<Date> getExpectedSigningDateRange() {
		return expectedSigningDateRange;
	}

	public void setExpectedSigningDateRange(List<Date> expectedSigningDateRange) {
		this.expectedSigningDateRange = expectedSigningDateRange;
	}

	public List<Date> getBusinessOpportunityTimeDateRange() {
		return businessOpportunityTimeDateRange;
	}

	public void setBusinessOpportunityTimeDateRange(List<Date> businessOpportunityTimeDateRange) {
		this.businessOpportunityTimeDateRange = businessOpportunityTimeDateRange;
	}

	public List<Contract> getContractList() {
		return contractList;
	}

	public void setContractList(List<Contract> contractList) {
		this.contractList = contractList;
	}

	public List<Cost> getCostList() {
		return costList;
	}

	public void setCostList(List<Cost> costList) {
		this.costList = costList;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	
}	
