package dafengqi.yunxiang.model;

import java.io.Serializable;
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
@Table(name = "crm_contract")
@XmlRootElement
public class Contract implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	@Size(max = 45)
	@Column(name = "contract_title")
	private String contractTitle;
	@Size(max = 45)
	@Column(name = "customer_id")
	private String customerId;
	private String[] customerIds;
	@Size(max = 45)
	@Column(name = "customer_name")
	private String customerName;
	@Size(max = 45)
	@Column(name = "business_opportunity_id")
	private String businessOpportunityId;
	@Size(max = 45)
	@Column(name = "business_opportunity_name")
	private String businessOpportunityName;
	@Size(max = 45)
	@Column(name = "contract_start_date")
	private Date contractStartDate;
	private String contractStartDates;
	@Size(max = 45)
	@Column(name = "contract_end_date")
	private Date contractEndDate;
	private String contractEndDates;
	@Size(max = 45)
	@Column(name = "total_contract_amount")
	private String totalContractAmount;
	private String totalContractAmounts;
	@Size(max = 45)
	@Column(name = "signing_date")
	private Date signingDate;
	private String signingDates;
	@Size(max = 45)
	@Column(name = "contract_status")
	private String contractStatus;
	private String[] contractStatuss;
	@Size(max = 45)
	@Column(name = "contract_no")
	private String contractNo;
	@Size(max = 45)
	@Column(name = "contract_type")
	private String contractType;
	private String[] contractTypes;
	@Size(max = 45)
	@Column(name = "contract_type_name")
	private String contractTypeName;
	@Size(max = 45)
	@Column(name = "payment_type")
	private String paymentType;
	private String[] paymentTypes;
	@Size(max = 45)
	@Column(name = "payment_type_name")
	private String paymentTypeName;
	@Size(max = 45)
	@Column(name = "our_signatory")
	private String ourSignatory;
	@Size(max = 45)
	@Column(name = "client_signatory")
	private String clientSignatory;
	@Size(max = 45)
	@Column(name = "next_follow_time")
	private Date nextFollowTime;
	private String nextFollowTimes;
	@Size(max = 45)
	@Column(name = "contract_category")
	private String contractCategory;
	@Size(max = 45)
	@Column(name = "audit")
	private String audit;
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
	@Column(name = "remarks")
	private String remarks;
	@Size(max = 45)
	@Column(name = "create_id")
	private String createId;
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
	@Column(name = "crm_responsible_id")
	private String crmResponsibleId;
	@Size(max = 45)
	@Column(name = "implementation")
	private String implementation;
	@Size(max = 45)
	@Column(name = "production_time")
	private String productionTime;
	@Size(max = 45)
	@Column(name = "output")
	private String output;
	@Size(max = 45)
	@Column(name = "shipping_time")
	private String shippingTime;
	@Size(max = 45)
	@Column(name = "shipment_volume")
	private String shipmentVolume;
	@Size(max = 45)
	@Column(name = "settlement_time")
	private String settlementTime;
	@Size(max = 45)
	@Column(name = "settlement_volume")
	private String settlementVolume;
	@Size(max = 45)
	@Column(name = "collection_time")
	private String collectionTime;
	@Size(max = 45)
	@Column(name = "collection_amount")
	private String collectionAmount;
	@Size(max = 45)
	@Column(name = "contract_unsettled_quantity")
	private String contractUnsettledQuantity;
	@Size(max = 45)
	@Column(name = "unsettled_shipment_quantity")
	private String unsettledShipmentQuantity;
	@Size(max = 45)
	@Column(name = "accounts_receivable")
	private String accountsReceivable;
	@Size(max = 45)
	@Column(name = "collaborator_name")
	private String collaboratorName;
	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;
	@Size(max = 45)
	@Column(name = "state")
	private String state;
	private List<Date> createDateRange;
	private List<Date> nextFollowTimeDateRange;
	private List<Date> contractStartDateRange;
	private List<Date> contractEndDateRange;
	private List<Date> signingDateRange;
	private List<Date> updateDateRange;
    private String[] createIds;
	private List<CollectionDetail> collectionDetailList = new ArrayList<CollectionDetail>();
	private List<Invoicing> collectionList = new ArrayList<Invoicing>();
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

	@Column(name = "app_id")
	private String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	
	
	public Contract() {
	}

	public Contract(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Contract)) {
			return false;
		}
		Contract other = (Contract) object;
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
		return "dafengqi.yunxiang.model.Customer[ id=" + id + " ]";
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
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

	public String getBusinessOpportunityId() {
		return businessOpportunityId;
	}

	public void setBusinessOpportunityId(String businessOpportunityId) {
		this.businessOpportunityId = businessOpportunityId;
	}

	public String getBusinessOpportunityName() {
		return businessOpportunityName;
	}

	public void setBusinessOpportunityName(String businessOpportunityName) {
		this.businessOpportunityName = businessOpportunityName;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractStartDates() {
		return contractStartDates;
	}

	public void setContractStartDates(String contractStartDates) {
		this.contractStartDates = contractStartDates;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getContractEndDates() {
		return contractEndDates;
	}

	public void setContractEndDates(String contractEndDates) {
		this.contractEndDates = contractEndDates;
	}

	public String getTotalContractAmount() {
		return totalContractAmount;
	}

	public void setTotalContractAmount(String totalContractAmount) {
		this.totalContractAmount = totalContractAmount;
	}

	public String getTotalContractAmounts() {
		return totalContractAmounts;
	}

	public void setTotalContractAmounts(String totalContractAmounts) {
		this.totalContractAmounts = totalContractAmounts;
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public String getSigningDates() {
		return signingDates;
	}

	public void setSigningDates(String signingDates) {
		this.signingDates = signingDates;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String[] getContractStatuss() {
		return contractStatuss;
	}

	public void setContractStatuss(String[] contractStatuss) {
		this.contractStatuss = contractStatuss;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String[] getContractTypes() {
		return contractTypes;
	}

	public void setContractTypes(String[] contractTypes) {
		this.contractTypes = contractTypes;
	}

	public String getContractTypeName() {
		return contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String[] getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(String[] paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

	public String getOurSignatory() {
		return ourSignatory;
	}

	public void setOurSignatory(String ourSignatory) {
		this.ourSignatory = ourSignatory;
	}

	public String getClientSignatory() {
		return clientSignatory;
	}

	public void setClientSignatory(String clientSignatory) {
		this.clientSignatory = clientSignatory;
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

	public String getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(String contractCategory) {
		this.contractCategory = contractCategory;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
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

	public String getCrmResponsibleId() {
		return crmResponsibleId;
	}

	public void setCrmResponsibleId(String crmResponsibleId) {
		this.crmResponsibleId = crmResponsibleId;
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

	public String getProductionTime() {
		return productionTime;
	}

	public void setProductionTime(String productionTime) {
		this.productionTime = productionTime;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getShipmentVolume() {
		return shipmentVolume;
	}

	public void setShipmentVolume(String shipmentVolume) {
		this.shipmentVolume = shipmentVolume;
	}

	public String getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}

	public String getSettlementVolume() {
		return settlementVolume;
	}

	public void setSettlementVolume(String settlementVolume) {
		this.settlementVolume = settlementVolume;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public String getContractUnsettledQuantity() {
		return contractUnsettledQuantity;
	}

	public void setContractUnsettledQuantity(String contractUnsettledQuantity) {
		this.contractUnsettledQuantity = contractUnsettledQuantity;
	}

	public String getUnsettledShipmentQuantity() {
		return unsettledShipmentQuantity;
	}

	public void setUnsettledShipmentQuantity(String unsettledShipmentQuantity) {
		this.unsettledShipmentQuantity = unsettledShipmentQuantity;
	}

	public String getAccountsReceivable() {
		return accountsReceivable;
	}

	public void setAccountsReceivable(String accountsReceivable) {
		this.accountsReceivable = accountsReceivable;
	}

	public String getCollaboratorName() {
		return collaboratorName;
	}

	public void setCollaboratorName(String collaboratorName) {
		this.collaboratorName = collaboratorName;
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

	public List<Date> getContractStartDateRange() {
		return contractStartDateRange;
	}

	public void setContractStartDateRange(List<Date> contractStartDateRange) {
		this.contractStartDateRange = contractStartDateRange;
	}

	public List<Date> getContractEndDateRange() {
		return contractEndDateRange;
	}

	public void setContractEndDateRange(List<Date> contractEndDateRange) {
		this.contractEndDateRange = contractEndDateRange;
	}

	public List<Date> getSigningDateRange() {
		return signingDateRange;
	}

	public void setSigningDateRange(List<Date> signingDateRange) {
		this.signingDateRange = signingDateRange;
	}

	public List<Date> getUpdateDateRange() {
		return updateDateRange;
	}

	public void setUpdateDateRange(List<Date> updateDateRange) {
		this.updateDateRange = updateDateRange;
	}

	public String[] getCreateIds() {
		return createIds;
	}

	public void setCreateIds(String[] createIds) {
		this.createIds = createIds;
	}

	public List<CollectionDetail> getCollectionmxList() {
		return collectionDetailList;
	}

	public void setCollectionmxList(List<CollectionDetail> collectionDetailList) {
		this.collectionDetailList = collectionDetailList;
	}

	public List<Invoicing> getCollectionList() {
		return collectionList;
	}

	public void setCollectionList(List<Invoicing> collectionList) {
		this.collectionList = collectionList;
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

	public List<CollectionDetail> getCollectionDetailList() {
		return collectionDetailList;
	}

	public void setCollectionDetailList(List<CollectionDetail> collectionDetailList) {
		this.collectionDetailList = collectionDetailList;
	}

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	
}	
