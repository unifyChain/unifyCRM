package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class Mechanism implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;//编码
	
	@Column(name = "name")
	private String name;//机构名称
	
	@Column(name = "full_name")
	private String fullName;//机构全称
	
	@Column(name = "mechanism_type")
	private String mechanismType;//机构类型
	
	@Column(name = "mechanism_type_name")
	private String mechanismTypeName;//机构类型名称
	@Size(max = 100)
	@Column(name = "alias")
	private String alias;//别名
	@Size(max = 100)
	@Column(name = "company_website")
	private String companyWebsite;//公司网址
	
	@Column(name = "contacts")
	private String contacts;//联系人
	
	@Column(name = "phonenumber")
	private String phonenumber;//手机号
	
	@Column(name = "telephone")
	private String telephone;//电话
	
	@Column(name = "address")
	private String address;//地址
	
	@Column(name = "fax")
	private String fax;//传真
	
	@Column(name = "email")
	private String email;//邮件
	@Column(name = "post")
	private String post;
	
	@Column(name = "longitude")
	private String longitude;//经度
	
	@Column(name = "latitude")
	private String latitude;//纬度
	@Size(max = 500)
	@Column(name = "remarks")
	private String remarks;//机构机构编码
	
	@Column(name = "status")
	private String status;//状态

	
	@Column(name = "create_id")
	private String createId;//创建人
	@Column(name = "create_name")
	private String createName;//创建人名称

	
	@Column(name = "create_date")
	private String createDate;//创建时间

	
	@Column(name = "update_id")
	private String updateId;//修改人

	@Column(name = "update_name")
	private String updateName;//修改人名称
	
	@Column(name = "update_date")
	private String updateDate;//修改时间

	
	@Column(name = "mechanism_id")
	private String mechanismId;//机构机构编码

	
	@Column(name = "mechanism_name")
	private String mechanismName;//机构名称

	private String appId; 
	private String zipCode;//公司邮编
	private Date enableDate;//启用时间
	private String baseCurrency;//本位币
	private BigDecimal numberOfDecimalPlaces = BigDecimal.ZERO;//数量小数位
	private BigDecimal priceOfDecimalPlaces = BigDecimal.ZERO;//单价小数位
	private String inventoryValuationMethod;//存货计价方法
	private Boolean enableNegativeInventoryNotAllowed;//不允许负库存
	private Boolean enableTaxes;//启用税金
	private BigDecimal taxePrice = BigDecimal.ZERO;//税金
	private Boolean enableWhetherTheCommodityPriceIncludesTax;//商品价格是否含税
	private Boolean enableAuxiliaryProperties;//启用辅助属性
	private Boolean enableSmartMicroStore;//启用智慧微店
	private Boolean enableSerialNumber;//启用序列号
	private Boolean enableBatchShelfLifeManagement;//启用批次保质期管理
	private Boolean enableEarliestBatchOfAutomaticDelivery;//启用自动出库最早批次
	private Boolean enableAutoFillSettlementAmount;//启用自动填充结算金额
	private Boolean enableSeparateWarehouseAccounting;//启用分仓核算
	private Boolean enableRetail;//启用零售
	private Boolean enableCannotUpdateOrderDate;//销售类单据不允许修改单据日期
	


	
	
	@Transient
	private String from;
	

	
	public Mechanism() {
	}
	public Mechanism(String id, String name, String fullName, String mechanismType, String contacts, String phonenumber, String telephone, String address, String fax, String email, String remarks, String createId, String createDate, String updateId, String updateDate, String status, String mechanismId, String mechanismName,String mechanismTypeName) {
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.mechanismType = mechanismType;
		this.contacts = contacts;
		this.phonenumber = phonenumber;
		this.telephone = telephone;
		this.address = address;
		this.fax = fax;
		this.email = email;
		this.remarks = remarks;
		this.createId = createId;
		this.createDate = createDate;
		this.updateId = updateId;
		this.updateDate = updateDate;
		this.status = status;
		this.mechanismId = mechanismId;
		this.mechanismName = mechanismName;
		this.mechanismTypeName = mechanismTypeName;
	}

	public Mechanism(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Mechanism)) {
			return false;
		}
		Mechanism other = (Mechanism) object;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMechanismType() {
		return mechanismType;
	}
	public void setMechanismType(String mechanismType) {
		this.mechanismType = mechanismType;
	}
	public String getMechanismTypeName() {
		return mechanismTypeName;
	}
	public void setMechanismTypeName(String mechanismTypeName) {
		this.mechanismTypeName = mechanismTypeName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
	public String getMechanismName() {
		return mechanismName;
	}
	public void setMechanismName(String mechanismName) {
		this.mechanismName = mechanismName;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public Date getEnableDate() {
		return enableDate;
	}
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public BigDecimal getNumberOfDecimalPlaces() {
		return numberOfDecimalPlaces;
	}
	public void setNumberOfDecimalPlaces(BigDecimal numberOfDecimalPlaces) {
		this.numberOfDecimalPlaces = numberOfDecimalPlaces;
	}
	public BigDecimal getPriceOfDecimalPlaces() {
		return priceOfDecimalPlaces;
	}
	public void setPriceOfDecimalPlaces(BigDecimal priceOfDecimalPlaces) {
		this.priceOfDecimalPlaces = priceOfDecimalPlaces;
	}
	public String getInventoryValuationMethod() {
		return inventoryValuationMethod;
	}
	public void setInventoryValuationMethod(String inventoryValuationMethod) {
		this.inventoryValuationMethod = inventoryValuationMethod;
	}
	public Boolean getEnableNegativeInventoryNotAllowed() {
		return enableNegativeInventoryNotAllowed;
	}
	public void setEnableNegativeInventoryNotAllowed(Boolean enableNegativeInventoryNotAllowed) {
		this.enableNegativeInventoryNotAllowed = enableNegativeInventoryNotAllowed;
	}
	public Boolean getEnableTaxes() {
		return enableTaxes;
	}
	public void setEnableTaxes(Boolean enableTaxes) {
		this.enableTaxes = enableTaxes;
	}
	public Boolean getEnableWhetherTheCommodityPriceIncludesTax() {
		return enableWhetherTheCommodityPriceIncludesTax;
	}
	public void setEnableWhetherTheCommodityPriceIncludesTax(Boolean enableWhetherTheCommodityPriceIncludesTax) {
		this.enableWhetherTheCommodityPriceIncludesTax = enableWhetherTheCommodityPriceIncludesTax;
	}
	public Boolean getEnableAuxiliaryProperties() {
		return enableAuxiliaryProperties;
	}
	public void setEnableAuxiliaryProperties(Boolean enableAuxiliaryProperties) {
		this.enableAuxiliaryProperties = enableAuxiliaryProperties;
	}
	public Boolean getEnableSmartMicroStore() {
		return enableSmartMicroStore;
	}
	public void setEnableSmartMicroStore(Boolean enableSmartMicroStore) {
		this.enableSmartMicroStore = enableSmartMicroStore;
	}
	public Boolean getEnableSerialNumber() {
		return enableSerialNumber;
	}
	public void setEnableSerialNumber(Boolean enableSerialNumber) {
		this.enableSerialNumber = enableSerialNumber;
	}
	public Boolean getEnableBatchShelfLifeManagement() {
		return enableBatchShelfLifeManagement;
	}
	public void setEnableBatchShelfLifeManagement(Boolean enableBatchShelfLifeManagement) {
		this.enableBatchShelfLifeManagement = enableBatchShelfLifeManagement;
	}
	public Boolean getEnableEarliestBatchOfAutomaticDelivery() {
		return enableEarliestBatchOfAutomaticDelivery;
	}
	public void setEnableEarliestBatchOfAutomaticDelivery(Boolean enableEarliestBatchOfAutomaticDelivery) {
		this.enableEarliestBatchOfAutomaticDelivery = enableEarliestBatchOfAutomaticDelivery;
	}
	public Boolean getEnableAutoFillSettlementAmount() {
		return enableAutoFillSettlementAmount;
	}
	public void setEnableAutoFillSettlementAmount(Boolean enableAutoFillSettlementAmount) {
		this.enableAutoFillSettlementAmount = enableAutoFillSettlementAmount;
	}
	public Boolean getEnableSeparateWarehouseAccounting() {
		return enableSeparateWarehouseAccounting;
	}
	public void setEnableSeparateWarehouseAccounting(Boolean enableSeparateWarehouseAccounting) {
		this.enableSeparateWarehouseAccounting = enableSeparateWarehouseAccounting;
	}
	public Boolean getEnableRetail() {
		return enableRetail;
	}
	public void setEnableRetail(Boolean enableRetail) {
		this.enableRetail = enableRetail;
	}
	public Boolean getEnableCannotUpdateOrderDate() {
		return enableCannotUpdateOrderDate;
	}
	public void setEnableCannotUpdateOrderDate(Boolean enableCannotUpdateOrderDate) {
		this.enableCannotUpdateOrderDate = enableCannotUpdateOrderDate;
	}
	public BigDecimal getTaxePrice() {
		return taxePrice;
	}
	public void setTaxePrice(BigDecimal taxePrice) {
		this.taxePrice = taxePrice;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

	
}	
