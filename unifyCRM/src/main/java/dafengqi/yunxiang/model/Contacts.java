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
public class Contacts implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	@Size(max = 45)
	@Column(name = "name")
	private String name;
	@Size(max = 45)
	@Column(name = "customer_id")
	private String customerId;
	private String[] customerIds;
	@Size(max = 45)
	@Column(name = "customer_name")
	private String customerName;
	@Size(max = 45)
	@Column(name = "position")
	private String position;
	@Size(max = 100)
	@Column(name = "telephone")
	private String telephone;
	@Size(max = 100)
	@Column(name = "phone")
	private String phone;
	@Size(max = 45)
	@Column(name = "department_id")
	private String departmentId;
	private String departmentIds;
	@Size(max = 45)
	@Column(name = "department_name")
	private String departmentName;
	@Size(max = 45)
	@Column(name = "wxid")
	private String wxid;
	@Size(max = 45)
	@Column(name = "qq")
	private String qq;
	@Size(max = 45)
	@Column(name = "wangwang")
	private String wangwang;
	@Size(max = 45)
	@Column(name = "email")
	private String email;
	@Size(max = 45)
	@Column(name = "sex")
	private String sex;
	@Column(name = "birthday")
	private String birthday;
	@Size(max = 45)
	@Column(name = "country")
	private String country;
	@Size(max = 45)
	@Column(name = "province_id")
	private String provinceId;
	@Size(max = 45)
	@Column(name = "province_name")
	private String provinceName;

	@Size(max = 45)
	@Column(name = "city_id")
	private String cityId;

	@Size(max = 45)
	@Column(name = "city_name")
	private String cityName;

	@Size(max = 45)
	@Column(name = "area_id")
	private String areaId;

	@Size(max = 45)
	@Column(name = "area_name")
	private String areaName;

	@Size(max = 45)
	@Column(name = "address")
	private String address;

	@Size(max = 45)
	@Column(name = "zip_code")
	private String zipCode;

	@Size(max = 45)
	@Column(name = "website")
	private String website;

	@Size(max = 45)
	@Column(name = "examine")
	private String examine;
	
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

	@Column(name = "mechanism_id")
	private String mechanismId;
	@Size(max = 45)
	@Column(name = "person_id")
	private String personId;
	private List<Date> createDateRange;
	private List<Date> updateDateRange;
    private String[] createIds;
	private List<BusinessOpportunity> businessOpportunityList = new ArrayList<BusinessOpportunity>();
	private List<Contract> contractList = new ArrayList<Contract>();
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

	
	public Contacts() {
	}

	public Contacts(String id) {
		this.id = id;
	}


	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Contacts)) {
			return false;
		}
		Contacts other = (Contacts) object;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
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

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	
	public List<Date> getCreateDateRange() {
		return createDateRange;
	}

	public void setCreateDateRange(List<Date> createDateRange) {
		this.createDateRange = createDateRange;
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

	public List<BusinessOpportunity> getBusinessOpportunityList() {
		return businessOpportunityList;
	}

	public void setBusinessOpportunityList(List<BusinessOpportunity> businessOpportunityList) {
		this.businessOpportunityList = businessOpportunityList;
	}

	public List<Contract> getContractList() {
		return contractList;
	}

	public void setContractList(List<Contract> contractList) {
		this.contractList = contractList;
	}


	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	
}	
