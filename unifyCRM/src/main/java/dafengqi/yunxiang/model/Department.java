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
public class Department implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	private String id;
	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;
	@Size(max = 45)
	@Column(name = "mechanism_name")
	private String mechanismName;
	@Size(max = 45)
	@Column(name = "department_id")
	private String departmentId;
	@Size(max = 45)
	@Column(name = "department_name")
	private String departmentName;
	@Size(max = 100)
	@Column(name = "name")
	private String name;
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
	@Transient
	private String from;

	
	public Department() {
	}
	public Department(String id, String name, String mechanismId, String mechanismName, String departmentId, String departmentName, String createId, String createDate, String updateId, String updateDate) {
		this.id = id;
		this.name = name;
		this.mechanismId = mechanismId;
		this.mechanismName = mechanismName;
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.createId = createId;
		this.createDate = createDate;
		this.updateId = updateId;
		this.updateDate = updateDate;
	}

	public Department(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Department)) {
			return false;
		}
		Department other = (Department) object;
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
		return "" + id + "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getMechanismId() {
		return mechanismId;
	}
	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	
	public String getMechanismName() {
		return mechanismName;
	}
	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	
}	
