package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "crm_clue")
@XmlRootElement
public class Clue implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;

	private Long id;
	@Size(max = 45)
	@Column(name = "telephone")
	private String telephone;
	@Size(max = 45)
	@Column(name = "phone")
	private String phone;
	@Size(max = 45)
	@Column(name = "wxid")
	private String wxid;
	@Size(max = 45)
	@Column(name = "email")
	private String email;
	@Size(max = 100)
	@Column(name = "qq")
	private String qq;
	@Size(max = 100)
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
	@Column(name = "zip_code")
	private String zipCode;
	@Size(max = 45)
	@Column(name = "address")
	private String address;
	@Size(max = 45)
	@Column(name = "wangwang")
	private String wangwang;
	@Size(max = 45)
	@Column(name = "website")
	private String website;

	@Size(max = 45)
	@Column(name = "name")
	private String name;

	@Size(max = 45)
	@Column(name = "position")
	private String position;

	@Size(max = 45)
	@Column(name = "corporate_name")
	private String corporateName;

	@Size(max = 45)
	@Column(name = "department")
	private String department;

	@Size(max = 45)
	@Column(name = "money")
	private String money;

	@Size(max = 45)
	@Column(name = "follow_status")
	private String followStatus;
    private String[] followStatuss;

	@Size(max = 45)
	@Column(name = "clue_source")
	private String clueSource;
    private String[] clueSources;

	@Size(max = 45)
	@Column(name = "follow_status_name")
	private String followStatusName;
	
	@Size(max = 45)
	@Column(name = "person_id")
	private String personId;
    private String[] personIds;
	@Size(max = 45)
	@Column(name = "customer_id")
	private String customerId;

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
    private String[] createIds;

	@Size(max = 45)
	@Column(name = "create_name")
	private String createName;
	
	@Column(name = "next_follow_time")
	private Date nextFollowTime;
	private String nextFollowTimes;//下次跟进时间

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
	@Column(name = "clue_source_name")
	private String clueSourceName;

	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;
	private List<Date> createDateRange;
	private List<Date> nextFollowTimeDateRange;
	private List<Date> updateDateRange;
	private List<FollowUp> followUpList = new ArrayList<FollowUp>();

	@Transient
	private Date time;
	@Transient
	private String content;
	@Transient
	private String followUp;
	@Transient
	private String followId;
	@Transient
	private String followName;
	@Transient
	private String contactsId;
	@Transient
	private String contactsName;
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

	
	
	
	public Clue() {
	}

	public Clue(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Clue)) {
			return false;
		}
		Clue other = (Clue) object;
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
	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "mobi.yunxiang.model.Customer[ id=" + id + " ]";
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

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(String followStatus) {
		this.followStatus = followStatus;
	}

	public String[] getFollowStatuss() {
		return followStatuss;
	}

	public void setFollowStatuss(String[] followStatuss) {
		this.followStatuss = followStatuss;
	}

	public String getClueSource() {
		return clueSource;
	}

	public void setClueSource(String clueSource) {
		this.clueSource = clueSource;
	}

	public String[] getClueSources() {
		return clueSources;
	}

	public void setClueSources(String[] clueSources) {
		this.clueSources = clueSources;
	}

	public String getFollowStatusName() {
		return followStatusName;
	}

	public void setFollowStatusName(String followStatusName) {
		this.followStatusName = followStatusName;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String[] getCreateIds() {
		return createIds;
	}

	public void setCreateIds(String[] createIds) {
		this.createIds = createIds;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
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

	public String getClueSourceName() {
		return clueSourceName;
	}

	public void setClueSourceName(String clueSourceName) {
		this.clueSourceName = clueSourceName;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public List<Date> getUpdateDateRange() {
		return updateDateRange;
	}

	public void setUpdateDateRange(List<Date> updateDateRange) {
		this.updateDateRange = updateDateRange;
	}

	public List<FollowUp> getFollowUpList() {
		return followUpList;
	}

	public void setFollowUpList(List<FollowUp> followUpList) {
		this.followUpList = followUpList;
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

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
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

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
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

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}



	
	
}	
