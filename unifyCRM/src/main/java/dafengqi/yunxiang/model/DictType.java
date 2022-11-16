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
public class DictType implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	@Size(max = 500)
	@Column(name = "remarks")
	private String remarks;
	@Size(max = 100)
	@Column(name = "dict_name")
	private String dictName;
	@Size(max = 100)
	@Column(name = "dict_type")
	private String dictType;
	@Size(max = 45)
	@Column(name = "status")
	private String status;

	@Size(max = 45)
	@Column(name = "create_id")
	private String createId;
	@Column(name = "create_name")
	private String createName;

	@Size(max = 45)
	@Column(name = "create_date")
	private String createDate;

	@Size(max = 45)
	@Column(name = "update_id")
	private String updateId;
	@Column(name = "update_name")
	private String updateName;
	@Size(max = 45)
	@Column(name = "update_date")
	private String updateDate;

	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;

	@Size(max = 45)
	@Column(name = "mechanism_name")
	private String mechanismName;
	

	
	public DictType() {
	}

	public DictType(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof DictType)) {
			return false;
		}
		DictType other = (DictType) object;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateId;
	}

	public void setUpdateBy(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "dafengqi.yunxiang.model.Dict[ id=" + id + " ]";
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

}
