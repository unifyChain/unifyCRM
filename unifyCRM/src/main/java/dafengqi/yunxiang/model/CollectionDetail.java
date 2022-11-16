package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class CollectionDetail implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	private String number;//数量
	private String crmPaymentCollectionPlanId;
	private Date collectionDate;//回款日期
	private String collectionProportion;//回款占比
	private String collectionAmount;//回款金额
	private String remark;//备注
	private String ys4;
	private String ys5;
	private String ys6;
	private String createId;
	private String createName;
	private String createDate;
	private String updateId;
	private String updateName;
	private String updateDate;
	private String collectionId;
	private String mechanismId;
	private String contractName;
	private String customerName;
	private String detailDate;
	
	@Column(name = "app_id")
	private String appId;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	
	
	public CollectionDetail() {
	}

	public CollectionDetail(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CollectionDetail)) {
			return false;
		}
		CollectionDetail other = (CollectionDetail) object;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCrmPaymentCollectionPlanId() {
		return crmPaymentCollectionPlanId;
	}

	public void setCrmPaymentCollectionPlanId(String crmPaymentCollectionPlanId) {
		this.crmPaymentCollectionPlanId = crmPaymentCollectionPlanId;
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getCollectionProportion() {
		return collectionProportion;
	}

	public void setCollectionProportion(String collectionProportion) {
		this.collectionProportion = collectionProportion;
	}

	public String getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getYs4() {
		return ys4;
	}

	public void setYs4(String ys4) {
		this.ys4 = ys4;
	}

	public String getYs5() {
		return ys5;
	}

	public void setYs5(String ys5) {
		this.ys5 = ys5;
	}

	public String getYs6() {
		return ys6;
	}

	public void setYs6(String ys6) {
		this.ys6 = ys6;
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

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getDetailDate() {
		return detailDate;
	}

	public void setDetailDate(String detailDate) {
		this.detailDate = detailDate;
	}
	
	
}	
