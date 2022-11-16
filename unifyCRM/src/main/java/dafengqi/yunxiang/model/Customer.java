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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
@Table(name = "crm_customer")
public class Customer implements Serializable {

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
	private String title;
	@Size(max = 45)
	@Column(name = "corporate_name")
	private String corporateName;
	@Size(max = 45)
	@Column(name = "customer_type_name")
	private String customerTypeName;
	@Size(max = 45)
	@Column(name = "customer_type")
	private String customerType;
    private String[] customerTypes;
	@Size(max = 100)
	@Column(name = "parent_customer")
	private String parentCustomer;
	@Size(max = 100)
	@Column(name = "parent_customer_name")
	private String parentCustomerName;
	@Size(max = 45)
	@Column(name = "telephone")
	private String telephone;//电话
	@Size(max = 45)
	@Column(name = "phone")
	private String phone;//手机号
	@Size(max = 45)
	@Column(name = "wxid")
	private String wxid;//微信
	@Size(max = 45)
	@Column(name = "qq")
	private String qq;//qq
	@Size(max = 45)
	@Column(name = "wangwang")
	private String wangwang;//旺旺
	@Size(max = 45)
	@Column(name = "email")
	private String email;//邮件
	@Size(max = 45)
	@Column(name = "fax")
	private String fax;//传真
	@Size(max = 45)
	@Column(name = "website")
	private String website;//网址
	@Size(max = 45)
	@Column(name = "region")
	private String region;//网址
	private String no;
    private String[] regions;
	@Size(max = 45)
	@Column(name = "country")
	private String country;//国家
	@Size(max = 45)
	@Column(name = "province_id")
	private String provinceId;//省

	@Size(max = 45)
	@Column(name = "province_name")
	private String provinceName;//省

	@Size(max = 45)
	@Column(name = "city_id")
	private String cityId;//市

	@Size(max = 45)
	@Column(name = "city_name")
	private String cityName;//市

	@Size(max = 45)
	@Column(name = "area_id")
	private String areaId;//区县

	@Size(max = 45)
	@Column(name = "area_name")
	private String areaName;//区县

	@Size(max = 45)
	@Column(name = "address")
	private String address;//地址

	@Size(max = 45)
	@Column(name = "zip_code")
	private String zipCode;//邮政编码

	@Size(max = 45)
	@Column(name = "follow_status")
	private String followStatus;//跟进状态
    private String[] followStatuss;

	@Size(max = 45)
	@Column(name = "follow_status_name")
	private String followStatusName;//跟进状态

	@Size(max = 45)
	@Column(name = "customer_source")
	private String customerSource;//客户来源
    private String[] customerSources;

	@Size(max = 45)
	@Column(name = "customer_source_name")
	private String customerSourceName;//客户来源

	@Size(max = 45)
	@Column(name = "industry")
	private String industry;//所属行业
    private String[] industrys;

	@Size(max = 45)
	@Column(name = "industry_name")
	private String industryName;//所属行业

	@Size(max = 45)
	@Column(name = "personnel_size")
	private String personnelSize;//人员规模
    private String[] personnelSizes;
    
	@Column(name = "balance_of_accounts_receivable")
    private BigDecimal balanceOfAccountsReceivable;

	@Size(max = 45)
	@Column(name = "personnel_size_name")
	private String personnelSizeName;//人员规模


	@Column(name = "next_follow_time")
	private Date nextFollowTime;//下次跟进时间
	private String nextFollowTimes;//下次跟进时间

	@Size(max = 45)
	@Column(name = "remarks")
	private String remarks;//备注

	@Size(max = 45)
	@Column(name = "person_id")
	private String personId;//负责人
    private String[] personIds;

	@Size(max = 45)
	@Column(name = "person_name")
	private String personName;//负责人

	@Size(max = 45)
	@Column(name = "department_id")
	private String departmentId;//所属部门
	private String departmentIds;//所属部门

	@Size(max = 45)
	@Column(name = "department_name")
	private String departmentName;//所属部门

	@Size(max = 45)
	@Column(name = "registered_capital")
	private String registeredCapital;//注册资金

	@Size(max = 45)
	@Column(name = "examine")
	private String examine;//审核

	@Size(max = 45)
	@Column(name = "create_id")
	private String createId;//创建人
    private String[] createIds;

	@Size(max = 45)
	@Column(name = "create_name")
	private String createName;//创建人名称

	@Size(max = 45)
	@Column(name = "create_date")
	private String createDate;//创建时间

	@Size(max = 45)
	@Column(name = "update_id")
	private String updateId;//修改人

	@Size(max = 45)
	@Column(name = "update_name")
	private String updateName;//修改人名称
	
	@Size(max = 45)
	@Column(name = "update_date")
	private String updateDate;//修改时间
	@Size(max = 45)
	@Column(name = "mechanism_id")
	private String mechanismId;//机构
	@Size(max = 45)
	@Column(name = "collaborator_id")
	private String collaboratorId;
	@Size(max = 45)
	@Column(name = "collaborator_name")
	private String collaboratorName;
	
	@Size(max = 45)
	@Column(name = "category_id")
	private String categoryId;
	
	@Size(max = 45)
	@Column(name = "category_name")
	private String categoryName;
	
	@Size(max = 45)
	@Column(name = "balance_date")
	private Date balanceDate;

	@Size(max = 45)
	@Column(name = "initial_receivables")
	private BigDecimal initialReceivables = BigDecimal.ZERO;//期初应付款
	
	@Size(max = 45)
	@Column(name = "initial_advance")
	private BigDecimal initialAdvance = BigDecimal.ZERO;//期初预付款
	
	@Size(max = 45)
	@Column(name = "taxpayer_identification_number")
	private String taxpayerIdentificationNumber;
	
	@Size(max = 45)
	@Column(name = "bank")
	private String bank;
	
	@Size(max = 45)
	@Column(name = "bank_account")
	private String bankAccount;
	
	@Size(max = 45)
	@Column(name = "data_type")
	private String dataType;
	private String relationMechanismId;
	private String relationMechanismName;
	private List<Date> createDateRange;
	private List<Date> nextFollowTimeDateRange;
	private List<Date> updateDateRange;
	
	private List<Contacts> contactsList = new ArrayList<Contacts>();
	private List<BusinessOpportunity> businessOpportunityList = new ArrayList<BusinessOpportunity>();
	private List<Contract> contractList = new ArrayList<Contract>();
	private List<Cost> costList = new ArrayList<Cost>();
	private List<FollowUp> followupList = new ArrayList<FollowUp>();
	
	

	@Transient
	private Date time;
	@Transient
	private String content;
	@Transient
	private String folloType;
	@Transient
	private String contactsId;
	@Transient
	private String contactsName;
	@Transient
	private String type;
	@Transient
	private String from;

	
	public Customer() {
	}

	public Customer(String id,String name,String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public Customer(String id,String name,String title,String createName,String mechanismId,String type) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.createName = createName;
		this.mechanismId = mechanismId;
		this.type = type;
	}
	public Customer(String phone,String name,String customerType,String corporateName,String customerSource,String address,String telephone) {
		this.phone = phone;
		this.name = name;
		this.customerType = customerType;
		this.corporateName = corporateName;
		this.customerSource = customerSource;
		this.address = address;
		this.telephone = telephone;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) object;
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
		return "mobi.yunxiang.model.Customer[ id=" + id + " ]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String[] getCustomerTypes() {
		return customerTypes;
	}

	public void setCustomerTypes(String[] customerTypes) {
		this.customerTypes = customerTypes;
	}

	public String getParentCustomer() {
		return parentCustomer;
	}

	public void setParentCustomer(String parentCustomer) {
		this.parentCustomer = parentCustomer;
	}

	public String getParentCustomerName() {
		return parentCustomerName;
	}

	public void setParentCustomerName(String parentCustomerName) {
		this.parentCustomerName = parentCustomerName;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String[] getRegions() {
		return regions;
	}

	public void setRegions(String[] regions) {
		this.regions = regions;
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

	public String getFollowStatusName() {
		return followStatusName;
	}

	public void setFollowStatusName(String followStatusName) {
		this.followStatusName = followStatusName;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String[] getCustomerSources() {
		return customerSources;
	}

	public void setCustomerSources(String[] customerSources) {
		this.customerSources = customerSources;
	}

	public String getCustomerSourceName() {
		return customerSourceName;
	}

	public void setCustomerSourceName(String customerSourceName) {
		this.customerSourceName = customerSourceName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String[] getIndustrys() {
		return industrys;
	}

	public void setIndustrys(String[] industrys) {
		this.industrys = industrys;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getPersonnelSize() {
		return personnelSize;
	}

	public void setPersonnelSize(String personnelSize) {
		this.personnelSize = personnelSize;
	}

	public String[] getPersonnelSizes() {
		return personnelSizes;
	}

	public void setPersonnelSizes(String[] personnelSizes) {
		this.personnelSizes = personnelSizes;
	}

	public String getPersonnelSizeName() {
		return personnelSizeName;
	}

	public void setPersonnelSizeName(String personnelSizeName) {
		this.personnelSizeName = personnelSizeName;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
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

	public String getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(String collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public String getCollaboratorName() {
		return collaboratorName;
	}

	public void setCollaboratorName(String collaboratorName) {
		this.collaboratorName = collaboratorName;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getTaxpayerIdentificationNumber() {
		return taxpayerIdentificationNumber;
	}

	public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
		this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
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

	public List<Date> getUpdateDateRange() {
		return updateDateRange;
	}

	public void setUpdateDateRange(List<Date> updateDateRange) {
		this.updateDateRange = updateDateRange;
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
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

	public List<Cost> getCostList() {
		return costList;
	}

	public void setCostList(List<Cost> costList) {
		this.costList = costList;
	}

	public List<FollowUp> getFollowupList() {
		return followupList;
	}

	public void setFollowupList(List<FollowUp> followupList) {
		this.followupList = followupList;
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

	public String getFolloType() {
		return folloType;
	}

	public void setFolloType(String folloType) {
		this.folloType = folloType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	

	public BigDecimal getInitialReceivables() {
		return initialReceivables;
	}

	public void setInitialReceivables(BigDecimal initialReceivables) {
		this.initialReceivables = initialReceivables;
	}

	public BigDecimal getInitialAdvance() {
		return initialAdvance;
	}

	public void setInitialAdvance(BigDecimal initialAdvance) {
		this.initialAdvance = initialAdvance;
	}

	public BigDecimal getBalanceOfAccountsReceivable() {
		return balanceOfAccountsReceivable;
	}

	public void setBalanceOfAccountsReceivable(BigDecimal balanceOfAccountsReceivable) {
		this.balanceOfAccountsReceivable = balanceOfAccountsReceivable;
	}

	public String getRelationMechanismId() {
		return relationMechanismId;
	}

	public void setRelationMechanismId(String relationMechanismId) {
		this.relationMechanismId = relationMechanismId;
	}

	public String getRelationMechanismName() {
		return relationMechanismName;
	}

	public void setRelationMechanismName(String relationMechanismName) {
		this.relationMechanismName = relationMechanismName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	
}	
