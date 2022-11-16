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
public class Dict implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "dict_code")
	private String dictCode;
	@Size(max = 500)
	@Column(name = "remarks")
	private String remarks;
	@Size(max = 100)
	@Column(name = "dict_label")
	private String dictLabel;
	@Size(max = 100)
	@Column(name = "dict_value")
	private String dictValue;
	@Size(max = 45)
	@Column(name = "parent_code")
	private String parentCode;
	@Size(max = 45)
	@Column(name = "tree_sort")
	private String treeSort;
	@Size(max = 45)
	@Column(name = "status")
	private String status;
	@Size(max = 45)
	@Column(name = "is_sys")
	private String isSys;
	@Size(max = 45)
	@Column(name = "dict_type")
	private String dictType;

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
	
	private String[] warehouseNames;
	
	@Transient
	private String from;
	
	public Dict() {
	}

	public Dict(String dictCode) {
		this.dictCode = dictCode;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Dict)) {
			return false;
		}
		Dict other = (Dict) object;
		if ((this.dictCode == null && other.dictCode != null) || (this.dictCode != null && !this.dictCode.equals(other.dictCode))) {
			return false;
		}
		return true;
	}


	@Override
	public int hashCode() {
		int hash = 0;
		hash += (dictCode != null ? dictCode.hashCode() : 0);
		return hash;
	}
	

	
	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "dafengqi.yunxiang.model.DictData[ dictCode=" + dictCode + " ]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getTreeSort() {
		return treeSort;
	}

	public void setTreeSort(String treeSort) {
		this.treeSort = treeSort;
	}

	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public String[] getWarehouseNames() {
		return warehouseNames;
	}

	public void setWarehouseNames(String[] warehouseNames) {
		this.warehouseNames = warehouseNames;
	}
	
}
