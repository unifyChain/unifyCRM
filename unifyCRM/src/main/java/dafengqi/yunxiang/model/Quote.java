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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;

@Entity
@Table(name = "quote")
@XmlRootElement
public class Quote implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;// 编码

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "ppt")
	private String ppt;

	@Column(name = "excel")
	private String excel;
	@Column(name = "mechanism_id")
	private String mechanismId;

	@Column(name = "mechanism_name")
	private String mechanismName;
	private String type;

	@Size(max = 50)
	@Column(name = "create_id")
	private String createId;
	@Size(max = 50)
	@Column(name = "create_name")
	private String createName;
	@Size(max = 50)
	@Column(name = "create_date")
	private String createDate;

	@Size(max = 50)
	@Column(name = "update_id")
	private String updateId;

	@Size(max = 50)
	@Column(name = "update_name")
	private String updateName;

	@Size(max = 50)
	@Column(name = "update_date")
	private String updateDate;

	private List<QuoteProduct> quoteProductList = new ArrayList<QuoteProduct>();

	public Quote() {
	}

	public Quote(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Quote)) {
			return false;
		}
		Quote other = (Quote) object;
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
		return "mobi.yunxiang.model.Quote[ id=" + id + " ]";
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getMechanismName() {
		return mechanismName;
	}

	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}


	public List<QuoteProduct> getQuoteProductList() {
		return quoteProductList;
	}

	public void setQuoteProductList(List<QuoteProduct> quoteProductList) {
		this.quoteProductList = quoteProductList;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPpt() {
		return ppt;
	}

	public void setPpt(String ppt) {
		this.ppt = ppt;
	}

	public String getExcel() {
		return excel;
	}

	public void setExcel(String excel) {
		this.excel = excel;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
