package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class Collection implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	private String collectionDate;
	private Date collectionDates;
	private String collectionDatess;
	private String collectionAmount;
	private String collectionAmounts;
	private String[] customerIds;
	private String customerId;
	private String customerName;
	private String contractId;
	private String[] contractIds;
	private String contractName;
	private String payTypeName;
	private String[] payTypeNames;
	private String collectionType;
	private String[] collectionTypes;
	private String[] contractLeaders;
	private String contractLeader;
	private String ys6;
	private String departmentId;
	private String departmentName;
	private String createId;
	private String createName;
	private String createDate;
	private String updateId;
	private String updateName;
	private String updateDate;
	private String paymentCollectionPlanSubordinateId;
	private String mechanismId;

	private List<CollectionDetail> list = new ArrayList<CollectionDetail>();
	private List<Date> dateRange;
	private List<Date> dateRanges;
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

	
	
	public Collection() {
	}

	public Collection(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Collection)) {
			return false;
		}
		Collection other = (Collection) object;
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
		return "dafengqi.yunxiang.model.Collection[ id=" + id + " ]";
	}

	public String getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Date getCollectionDates() {
		return collectionDates;
	}

	public void setCollectionDates(Date collectionDates) {
		this.collectionDates = collectionDates;
	}

	public String getCollectionDatess() {
		return collectionDatess;
	}

	public void setCollectionDatess(String collectionDatess) {
		this.collectionDatess = collectionDatess;
	}

	public String getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public String getCollectionAmounts() {
		return collectionAmounts;
	}

	public void setCollectionAmounts(String collectionAmounts) {
		this.collectionAmounts = collectionAmounts;
	}

	public String[] getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String[] getContractIds() {
		return contractIds;
	}

	public void setContractIds(String[] contractIds) {
		this.contractIds = contractIds;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String[] getPayTypeNames() {
		return payTypeNames;
	}

	public void setPayTypeNames(String[] payTypeNames) {
		this.payTypeNames = payTypeNames;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public String[] getCollectionTypes() {
		return collectionTypes;
	}

	public void setCollectionTypes(String[] collectionTypes) {
		this.collectionTypes = collectionTypes;
	}

	public String[] getContractLeaders() {
		return contractLeaders;
	}

	public void setContractLeaders(String[] contractLeaders) {
		this.contractLeaders = contractLeaders;
	}

	public String getContractLeader() {
		return contractLeader;
	}

	public void setContractLeader(String contractLeader) {
		this.contractLeader = contractLeader;
	}

	public String getYs6() {
		return ys6;
	}

	public void setYs6(String ys6) {
		this.ys6 = ys6;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public String getPaymentCollectionPlanSubordinateId() {
		return paymentCollectionPlanSubordinateId;
	}

	public void setPaymentCollectionPlanSubordinateId(String paymentCollectionPlanSubordinateId) {
		this.paymentCollectionPlanSubordinateId = paymentCollectionPlanSubordinateId;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public List<CollectionDetail> getList() {
		return list;
	}

	public void setList(List<CollectionDetail> list) {
		this.list = list;
	}

	public List<Date> getDateRange() {
		return dateRange;
	}

	public void setDateRange(List<Date> dateRange) {
		this.dateRange = dateRange;
	}

	public List<Date> getDateRanges() {
		return dateRanges;
	}

	public void setDateRanges(List<Date> dateRanges) {
		this.dateRanges = dateRanges;
	}


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
}	
