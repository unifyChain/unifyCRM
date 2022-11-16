package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.Date;

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
public class Collectionmx implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;//编码
	private String number;//数量
	private String crmPaymentCollectionPlanId;//
	private Date collectionDate;//回款日期
	private String collectionProportion;//回款占比
	private String collectionAmount;//回款金额
	private String remark;//备注
	private String ys4;//预设
	private String ys5;//预设
	private String ys6;//预设
	private String createId;//创建人
	private String createName;//创建人名称
	private String createDate;//创建时间
	private String updateId;//修改人
	private String updateName;//修改人名称
	private String updateDate;//修改时间
	private String collectionId;//回款编码
	private String mechanismId;//机构编码
	private String contractName;//合同名称
	private String customerName;//客户名称
	
	private String mxrq;//临时字段

	private String khlx;//临时字段

	
	public Collectionmx() {
	}

	public Collectionmx(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Collectionmx)) {
			return false;
		}
		Collectionmx other = (Collectionmx) object;
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

	public String getMxrq() {
		return mxrq;
	}

	public void setMxrq(String mxrq) {
		this.mxrq = mxrq;
	}

	public String getKhlx() {
		return khlx;
	}

	public void setKhlx(String khlx) {
		this.khlx = khlx;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	
}	
