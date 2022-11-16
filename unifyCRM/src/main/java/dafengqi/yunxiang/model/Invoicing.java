package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;

@Entity
@Table(name = "crm_invoicing")
@XmlRootElement
public class Invoicing implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	private Date invoicingDate;
	private String invoicingDates;
	private String invoicingContent;
	private BigDecimal invoicingAmount;
	private BigDecimal invoicingAmounts;
	private String customerId;
	private String[] customerIds;
	private String customerName;
	private String contractId;
	private String[] contractIds;
	private String contractName;
	private String[] invoiceTypes;
	private String invoiceType;
	private String invoicingNum;
	private String contractLeader;
	private String[] contractLeaders;
	private String remark;
	private String createId;
	private String createName;
	private String createDate;
	private String mechanismId;
	private String updateId;
	private String updateName;
	private String updateDate;
	private String departmentId;
	private String departmentName;
	private String sysInvoicingTypeId;
	private List<Date> invoicingDateRange;
	@Transient
	private String from;

	
	public Invoicing() {
	}

	public Invoicing(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Invoicing)) {
			return false;
		}
		Invoicing other = (Invoicing) object;
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
		return "dafengqi.yunxiang.model.Invoicing[ id=" + id + " ]";
	}

	public Date getInvoicingDate() {
		return invoicingDate;
	}

	public void setInvoicingDate(Date invoicingDate) {
		this.invoicingDate = invoicingDate;
	}

	public String getInvoicingDates() {
		return invoicingDates;
	}

	public void setInvoicingDates(String invoicingDates) {
		this.invoicingDates = invoicingDates;
	}

	public String getInvoicingContent() {
		return invoicingContent;
	}

	public void setInvoicingContent(String invoicingContent) {
		this.invoicingContent = invoicingContent;
	}

	public BigDecimal getInvoicingAmount() {
		return invoicingAmount;
	}

	public void setInvoicingAmount(BigDecimal invoicingAmount) {
		this.invoicingAmount = invoicingAmount;
	}

	public BigDecimal getInvoicingAmounts() {
		return invoicingAmounts;
	}

	public void setInvoicingAmounts(BigDecimal invoicingAmounts) {
		this.invoicingAmounts = invoicingAmounts;
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

	public String[] getInvoiceTypes() {
		return invoiceTypes;
	}

	public void setInvoiceTypes(String[] invoiceTypes) {
		this.invoiceTypes = invoiceTypes;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoicingNum() {
		return invoicingNum;
	}

	public void setInvoicingNum(String invoicingNum) {
		this.invoicingNum = invoicingNum;
	}

	public String getContractLeader() {
		return contractLeader;
	}

	public void setContractLeader(String contractLeader) {
		this.contractLeader = contractLeader;
	}

	public String[] getContractLeaders() {
		return contractLeaders;
	}

	public void setContractLeaders(String[] contractLeaders) {
		this.contractLeaders = contractLeaders;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getSysInvoicingTypeId() {
		return sysInvoicingTypeId;
	}

	public void setSysInvoicingTypeId(String sysInvoicingTypeId) {
		this.sysInvoicingTypeId = sysInvoicingTypeId;
	}

	public List<Date> getInvoicingDateRange() {
		return invoicingDateRange;
	}

	public void setInvoicingDateRange(List<Date> invoicingDateRange) {
		this.invoicingDateRange = invoicingDateRange;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}


	
}	
