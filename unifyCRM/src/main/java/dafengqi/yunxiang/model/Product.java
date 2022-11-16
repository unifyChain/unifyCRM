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
@Table(name = "product")
@XmlRootElement
public class Product implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;
	

	@Column(name = "no")
	private int no;
	private int orderNo;
	
    private String title;
//	@Column(name = "label")
//    private String label;
	
	@Column(name = "bar_code")
    private String barCode;
	
	@Column(name = "category_id")
    private String categoryId;
	
	@Column(name = "category_name")
    private String categoryName;
	
	@Column(name = "details")
    private String details;
	
	@Column(name = "unit_price")
    private BigDecimal unitPrice;
	
	@Column(name = "retail_price")
    private BigDecimal retailPrice;
    private BigDecimal retailPrices;
	
	@Column(name = "membership_price")
    private BigDecimal membershipPrice;
	
	@Column(name = "purchase_price")
    private BigDecimal purchasePrice;
	
	@Column(name = "discount_price")
    private BigDecimal discountPrice;
	
	@Column(name = "reference_price")
    private BigDecimal referencePrice;
	
	@Column(name = "stock")
    private BigDecimal stock;
	
	@Column(name = "freight_template_id")
    private String freightTemplateId;
	
	@Column(name = "freight_template_name")
    private String freightTemplateName;
	
	@Column(name = "product_type_id")
    private String productTypeId;
	
	@Column(name = "product_type_Name")
    private String productTypeName;
	
	@Column(name = "second_hand")
    private String secondHand;
	
	@Column(name = "customized")
    private String customized;
	
	@Column(name = "rotation_image")
    private String rotationImage;
	
	@Column(name = "brand_id")
    private String brandId;
	
	@Column(name = "brand_name")
    private String brandName;
	
	@Column(name = "pre_sale")
    private String preSale;
    private String[] support;
    private String supports;
	
	@Column(name = "promise")
    private String promise;
	
	@Column(name = "return_and_exchange")
    private String returnAndExchange;
	
	@Column(name = "mechanism_id")
    private String mechanismId;
    private String relationMechanismId;
    private String relationMechanismName;
	

	@Column(name = "warehouse_id")
    private String warehouseId;
	

	@Column(name = "warehouse_name")
    private String warehouseName;

    private String from;

	
	@Column(name = "mechanism_name")
    private String mechanismName;

	
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

	
	@Column(name = "department_id")
    private String departmentId;

	
	@Column(name = "department_name")
    private String departmentName;


	@Column(name = "specifications")
    private String specifications;//规格
	
	@Column(name = "remarks")
    private String remarks;//备注
	@Column(name = "scroll_image")
    private String scrollImage;

    private String audit;
	@Column(name = "unit")
    private String unit;//单位
	
	@Column(name = "sub_unit")
    private String subUnit;//副单位
	
	@Column(name = "opening_quantity")
    private BigDecimal openingQuantity;//期初数量
	
	@Column(name = "opening_balance_date")
    private BigDecimal openingBalanceDate;//期初日期
	
	@Column(name = "opening_total_price")
    private BigDecimal openingTotalPrice;//期初总价

    private String appId;
 
	@Transient
	private BigDecimal cb = BigDecimal.ZERO;
	@Transient
	private BigDecimal dwcb = BigDecimal.ZERO;
	@Transient
	private BigDecimal kcsl = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl1 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl2 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl3 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl4 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl5 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl6 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl7 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl8 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl9 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl10 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl11 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl12 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl13 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl14 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl15 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl16 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl17 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl18 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl19 = BigDecimal.ZERO;
	@Transient
	private BigDecimal cksl20 = BigDecimal.ZERO;

	
	@Transient
	private String fdws;
	@Transient
	private String fdws1;
	@Transient
	private String fdws2;
	@Transient
	private String fdws3;
	@Transient
	private String fdws4;
	@Transient
	private String fdws5;
	@Transient
	private String fdws6;
	@Transient
	private String fdws7;
	@Transient
	private String fdws8;
	@Transient
	private String fdws9;
	@Transient
	private String fdws10;
	@Transient
	private String fdws11;
	@Transient
	private String fdws12;
	@Transient
	private String fdws13;
	@Transient
	private String fdws14;
	@Transient
	private String fdws15;
	@Transient
	private String fdws16;
	@Transient
	private String fdws17;
	@Transient
	private String fdws18;
	@Transient
	private String fdws19;
	@Transient
	private String fdws20;

	@Transient
	private String type;
	private String labels;
	private String specification;
	private String model;
	private String texture;
	private String weight;
	private String color;
	private BigDecimal distributionPrice;
	private BigDecimal freight;
	private BigDecimal undertakesPrice;
	private BigDecimal electricityPrice;
	private BigDecimal jingdongPice;
	private BigDecimal tmallPrice;
	private String jingdongLink;
	private String tmallLink;
	private String jingdongSource;
	private String tmallSource;
	private BigDecimal packingQuantity;
	private List<?> label = new ArrayList<>();

	
	public Product() {
	}

	public Product(String id) {
		this.id = id;
	}
	public Product(String categoryName,String barCode,String title,BigDecimal unitPrice,BigDecimal retailPrice,BigDecimal membershipPrice,BigDecimal purchasePrice,BigDecimal discountPrice,BigDecimal referencePrice,String specification,String model,String details,String texture,String weight) {
		this.categoryName = categoryName;
		this.barCode = barCode;
		this.title = title;
		this.unitPrice = unitPrice;
		this.retailPrice = retailPrice;
		this.membershipPrice = membershipPrice;
		this.purchasePrice = purchasePrice;
		this.discountPrice = discountPrice;
		this.referencePrice = referencePrice;
		this.specification = specification;
		this.model = model;
		this.details = details;
		this.texture = texture;
		this.weight = weight;
	}

	private String entryMethod;//入账方式
	private String specialEquipment;//特种设备
	private String source;//设备来源
	private String warrantyPeriod;//保修期至
	private String status;//设备状态
	private String userId;//负责人
	private String userName;//负责人
	private String position;//位置
	private String meteringEquipment;//是否计量设备
	private String technicalParameter;//技术参数
	private String assetNumber;//资产编号
	private String electronicTagCode;//电子标签码
	private Date startDate;//启用日期
	private String usageStatus;//使用状态
	private String serialNumber;//序列号
	private String company;//单位
	private String dateOfPurchase;//购置日期
	private String estimatedScrapDate;//预计报废日期
	private String equipmentLevel;//设备等级
	private String enableDepreciation;//是否开启折旧
	private String netWorth;//净值
	private String measurementId;//计量编号
	private String measurementRange;//计量范围
	private String allowableError;//允许误差
	private Date nextMeasurementDate;//下次计量日期
	private String measurementUserId;//计量负责人
	private String measurementUserName;//计量负责人
	private String explain;//说明
	private String meteringPoint;//计量点位
	private String managementLevel;//管理等级
	private String meteringCycle;//计量周期
	private String instrumentAccuracy;//仪器精度
	private String measurementMethod;//计量方式
	private String advanceReminderTime;//提前提醒时间
	private String ipAddress;//IP地址
	private String date;//日期时间
	private String integer;//整数
	private String temperature;//温度
	private String picture;//图片
	private String accountLocation;//台账位置
	private String inventoryUserName;//盘点人
	private String inventoryDate;//盘点时间
	private String image;//图片
	private BigDecimal inventoryQuantity = BigDecimal.ZERO;//盘点数量
	
	
	
	
	
	//设备台账
	private String assetNo;//资产编号
	private String supplierId;//供应商
	private String supplierName;//供应商
	private String sourceOfEquipment;//设备来源
	private String purchaseAmount;//采购金额
	private Date purchaseDate;//购置日期
	private Date warrantyPeriodExpires;//保修期至
	private Date estimatedRetirementDate;//预计报废日期
	private String equipmentStatus;//设备状态
	private String isMetering;//是否计量设备
	private String isDepreciation;//是否开启折旧
	
	//特种设备
	private String factoryNo;//出厂编号
	private String registrationCode;//注册代码
	private String certificateNo;//使用证号
	private Date nextInspectionDate;//下次检验日期
	
	//备件
	private String replacementCycle;//更换周期
	private String duration;//使用时长
	private Date nextReplacementDate;//下次更换时间
	private String equipmentId;//设备编号
	private String equipmentName;//设备名称
	
	private String number;//编号
	private String alertValue;//预警值
	private String procurementCycle;//采购周期
	private String tagCode;//标签码
	private String sparePartType;//类型
	private String[] productNames;
	
	
	//计量设备 
	private String measurementNo;//计量编号
	private String measuringRange;//计量范围
	private String measurementPeriod;//计量周期
	private Date lastMeasurementDate;//上次计量日期
	private String measurementResults;//计量结果
	private String reminderTimeInAdvance;//提前提醒时间
	private String handleId;//负责人
	private String handleName;//负责人
	
	//设备bom
	private String equipmentNo;//设备编号
	private String requirement;//需求量
	
	//设备事件
	private String eventType;//事件类型
	private String expenses;//开销
	private String changeInValue;//价值变动
	private String changeValue;//变动值
	private String originalValue;//资产原值
	private String valueAfterChange;//变更后价值
	private Date dateOfOccurrence;//发生日期
	
	//查询条件
	private String bmjgs;//部门结构树
	private String lbjgs;//部门结构树
	
	

	
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Product)) {
			return false;
		}
		Product other = (Product) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}
	


	public Product(String id,String name) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
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

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}

	public BigDecimal getMembershipPrice() {
		return membershipPrice;
	}

	public void setMembershipPrice(BigDecimal membershipPrice) {
		this.membershipPrice = membershipPrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public BigDecimal getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(BigDecimal referencePrice) {
		this.referencePrice = referencePrice;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public String getFreightTemplateId() {
		return freightTemplateId;
	}
 
	public void setFreightTemplateId(String freightTemplateId) {
		this.freightTemplateId = freightTemplateId;
	}

	public String getFreightTemplateName() {
		return freightTemplateName;
	}

	public void setFreightTemplateName(String freightTemplateName) {
		this.freightTemplateName = freightTemplateName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getSecondHand() {
		return secondHand;
	}

	public void setSecondHand(String secondHand) {
		this.secondHand = secondHand;
	}

	public String getCustomized() {
		return customized;
	}

	public void setCustomized(String customized) {
		this.customized = customized;
	}

	public String getRotationImage() {
		return rotationImage;
	}

	public void setRotationImage(String rotationImage) {
		this.rotationImage = rotationImage;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPreSale() {
		return preSale;
	}

	public void setPreSale(String preSale) {
		this.preSale = preSale;
	}

	public String getPromise() {
		return promise;
	}

	public void setPromise(String promise) {
		this.promise = promise;
	}

	public String getReturnAndExchange() {
		return returnAndExchange;
	}

	public void setReturnAndExchange(String returnAndExchange) {
		this.returnAndExchange = returnAndExchange;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyBy() {
		return updateId;
	}

	public void setModifyBy(String updateId) {
		this.updateId = updateId;
	}

	public String getModifyName() {
		return updateName;
	}

	public void setModifyName(String updateName) {
		this.updateName = updateName;
	}

	public String getModifyTime() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSubUnit() {
		return subUnit;
	}

	public void setSubUnit(String subUnit) {
		this.subUnit = subUnit;
	}

	public BigDecimal getCb() {
		return cb;
	}

	public void setCb(BigDecimal cb) {
		this.cb = cb;
	}

	public BigDecimal getDwcb() {
		return dwcb;
	}

	public void setDwcb(BigDecimal dwcb) {
		this.dwcb = dwcb;
	}

	public BigDecimal getKcsl() {
		return kcsl;
	}

	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}

	public BigDecimal getCksl() {
		return cksl;
	}

	public void setCksl(BigDecimal cksl) {
		this.cksl = cksl;
	}

	public BigDecimal getCksl1() {
		return cksl1;
	}

	public void setCksl1(BigDecimal cksl1) {
		this.cksl1 = cksl1;
	}

	public BigDecimal getCksl2() {
		return cksl2;
	}

	public void setCksl2(BigDecimal cksl2) {
		this.cksl2 = cksl2;
	}

	public BigDecimal getCksl3() {
		return cksl3;
	}

	public void setCksl3(BigDecimal cksl3) {
		this.cksl3 = cksl3;
	}

	public BigDecimal getCksl4() {
		return cksl4;
	}

	public void setCksl4(BigDecimal cksl4) {
		this.cksl4 = cksl4;
	}

	public BigDecimal getCksl5() {
		return cksl5;
	}

	public void setCksl5(BigDecimal cksl5) {
		this.cksl5 = cksl5;
	}

	public BigDecimal getCksl6() {
		return cksl6;
	}

	public void setCksl6(BigDecimal cksl6) {
		this.cksl6 = cksl6;
	}

	public BigDecimal getCksl7() {
		return cksl7;
	}

	public void setCksl7(BigDecimal cksl7) {
		this.cksl7 = cksl7;
	}

	public BigDecimal getCksl8() {
		return cksl8;
	}

	public void setCksl8(BigDecimal cksl8) {
		this.cksl8 = cksl8;
	}

	public BigDecimal getCksl9() {
		return cksl9;
	}

	public void setCksl9(BigDecimal cksl9) {
		this.cksl9 = cksl9;
	}

	public BigDecimal getCksl10() {
		return cksl10;
	}

	public void setCksl10(BigDecimal cksl10) {
		this.cksl10 = cksl10;
	}

	public BigDecimal getCksl11() {
		return cksl11;
	}

	public void setCksl11(BigDecimal cksl11) {
		this.cksl11 = cksl11;
	}

	public BigDecimal getCksl12() {
		return cksl12;
	}

	public void setCksl12(BigDecimal cksl12) {
		this.cksl12 = cksl12;
	}

	public BigDecimal getCksl13() {
		return cksl13;
	}

	public void setCksl13(BigDecimal cksl13) {
		this.cksl13 = cksl13;
	}

	public BigDecimal getCksl14() {
		return cksl14;
	}

	public void setCksl14(BigDecimal cksl14) {
		this.cksl14 = cksl14;
	}

	public BigDecimal getCksl15() {
		return cksl15;
	}

	public void setCksl15(BigDecimal cksl15) {
		this.cksl15 = cksl15;
	}

	public BigDecimal getCksl16() {
		return cksl16;
	}

	public void setCksl16(BigDecimal cksl16) {
		this.cksl16 = cksl16;
	}

	public BigDecimal getCksl17() {
		return cksl17;
	}

	public void setCksl17(BigDecimal cksl17) {
		this.cksl17 = cksl17;
	}

	public BigDecimal getCksl18() {
		return cksl18;
	}

	public void setCksl18(BigDecimal cksl18) {
		this.cksl18 = cksl18;
	}

	public BigDecimal getCksl19() {
		return cksl19;
	}

	public void setCksl19(BigDecimal cksl19) {
		this.cksl19 = cksl19;
	}

	public BigDecimal getCksl20() {
		return cksl20;
	}

	public void setCksl20(BigDecimal cksl20) {
		this.cksl20 = cksl20;
	}

	public String getFdws() {
		return fdws;
	}

	public void setFdws(String fdws) {
		this.fdws = fdws;
	}

	public String getFdws1() {
		return fdws1;
	}

	public void setFdws1(String fdws1) {
		this.fdws1 = fdws1;
	}

	public String getFdws2() {
		return fdws2;
	}

	public void setFdws2(String fdws2) {
		this.fdws2 = fdws2;
	}

	public String getFdws3() {
		return fdws3;
	}

	public void setFdws3(String fdws3) {
		this.fdws3 = fdws3;
	}

	public String getFdws4() {
		return fdws4;
	}

	public void setFdws4(String fdws4) {
		this.fdws4 = fdws4;
	}

	public String getFdws5() {
		return fdws5;
	}

	public void setFdws5(String fdws5) {
		this.fdws5 = fdws5;
	}

	public String getFdws6() {
		return fdws6;
	}

	public void setFdws6(String fdws6) {
		this.fdws6 = fdws6;
	}

	public String getFdws7() {
		return fdws7;
	}

	public void setFdws7(String fdws7) {
		this.fdws7 = fdws7;
	}

	public String getFdws8() {
		return fdws8;
	}

	public void setFdws8(String fdws8) {
		this.fdws8 = fdws8;
	}

	public String getFdws9() {
		return fdws9;
	}

	public void setFdws9(String fdws9) {
		this.fdws9 = fdws9;
	}

	public String getFdws10() {
		return fdws10;
	}

	public void setFdws10(String fdws10) {
		this.fdws10 = fdws10;
	}

	public String getFdws11() {
		return fdws11;
	}

	public void setFdws11(String fdws11) {
		this.fdws11 = fdws11;
	}

	public String getFdws12() {
		return fdws12;
	}

	public void setFdws12(String fdws12) {
		this.fdws12 = fdws12;
	}

	public String getFdws13() {
		return fdws13;
	}

	public void setFdws13(String fdws13) {
		this.fdws13 = fdws13;
	}

	public String getFdws14() {
		return fdws14;
	}

	public void setFdws14(String fdws14) {
		this.fdws14 = fdws14;
	}

	public String getFdws15() {
		return fdws15;
	}

	public void setFdws15(String fdws15) {
		this.fdws15 = fdws15;
	}

	public String getFdws16() {
		return fdws16;
	}

	public void setFdws16(String fdws16) {
		this.fdws16 = fdws16;
	}

	public String getFdws17() {
		return fdws17;
	}

	public void setFdws17(String fdws17) {
		this.fdws17 = fdws17;
	}

	public String getFdws18() {
		return fdws18;
	}

	public void setFdws18(String fdws18) {
		this.fdws18 = fdws18;
	}

	public String getFdws19() {
		return fdws19;
	}

	public void setFdws19(String fdws19) {
		this.fdws19 = fdws19;
	}

	public String getFdws20() {
		return fdws20;
	}

	public void setFdws20(String fdws20) {
		this.fdws20 = fdws20;
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

	public BigDecimal getOpeningQuantity() {
		return openingQuantity;
	}

	public void setOpeningQuantity(BigDecimal openingQuantity) {
		this.openingQuantity = openingQuantity;
	}

	public BigDecimal getOpeningBalanceDate() {
		return openingBalanceDate;
	}

	public void setOpeningBalanceDate(BigDecimal openingBalanceDate) {
		this.openingBalanceDate = openingBalanceDate;
	}

	public BigDecimal getOpeningTotalPrice() {
		return openingTotalPrice;
	}

	public void setOpeningTotalPrice(BigDecimal openingTotalPrice) {
		this.openingTotalPrice = openingTotalPrice;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getScrollImage() {
		return scrollImage;
	}

	public void setScrollImage(String scrollImage) {
		this.scrollImage = scrollImage;
	}

	public List<?> getLabel() {
		return label;
	}

	public void setLabel(List<?> label) {
		this.label = label;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getDistributionPrice() {
		return distributionPrice;
	}

	public void setDistributionPrice(BigDecimal distributionPrice) {
		this.distributionPrice = distributionPrice;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public BigDecimal getUndertakesPrice() {
		return undertakesPrice;
	}

	public void setUndertakesPrice(BigDecimal undertakesPrice) {
		this.undertakesPrice = undertakesPrice;
	}

	public BigDecimal getElectricityPrice() {
		return electricityPrice;
	}

	public void setElectricityPrice(BigDecimal electricityPrice) {
		this.electricityPrice = electricityPrice;
	}

	public BigDecimal getJingdongPice() {
		return jingdongPice;
	}

	public void setJingdongPice(BigDecimal jingdongPice) {
		this.jingdongPice = jingdongPice;
	}

	public BigDecimal getTmallPrice() {
		return tmallPrice;
	}

	public void setTmallPrice(BigDecimal tmallPrice) {
		this.tmallPrice = tmallPrice;
	}

	public String getJingdongLink() {
		return jingdongLink;
	}

	public void setJingdongLink(String jingdongLink) {
		this.jingdongLink = jingdongLink;
	}

	public String getTmallLink() {
		return tmallLink;
	}

	public void setTmallLink(String tmallLink) {
		this.tmallLink = tmallLink;
	}

	public String getJingdongSource() {
		return jingdongSource;
	}

	public void setJingdongSource(String jingdongSource) {
		this.jingdongSource = jingdongSource;
	}

	public String getTmallSource() {
		return tmallSource;
	}

	public void setTmallSource(String tmallSource) {
		this.tmallSource = tmallSource;
	}

	public BigDecimal getPackingQuantity() {
		return packingQuantity;
	}

	public void setPackingQuantity(BigDecimal packingQuantity) {
		this.packingQuantity = packingQuantity;
	}

	public String getEntryMethod() {
		return entryMethod;
	}

	public String getSpecialEquipment() {
		return specialEquipment;
	}

	public String getSource() {
		return source;
	}

	public String getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public String getStatus() {
		return status;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPosition() {
		return position;
	}

	public String getMeteringEquipment() {
		return meteringEquipment;
	}

	public String getTechnicalParameter() {
		return technicalParameter;
	}

	public String getAssetNumber() {
		return assetNumber;
	}

	public String getElectronicTagCode() {
		return electronicTagCode;
	}

	public String getUsageStatus() {
		return usageStatus;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getCompany() {
		return company;
	}

	public String getDateOfPurchase() {
		return dateOfPurchase;
	}

	public String getEstimatedScrapDate() {
		return estimatedScrapDate;
	}

	public String getEquipmentLevel() {
		return equipmentLevel;
	}

	public String getEnableDepreciation() {
		return enableDepreciation;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public String getMeasurementId() {
		return measurementId;
	}

	public String getMeasurementRange() {
		return measurementRange;
	}

	public String getAllowableError() {
		return allowableError;
	}

	public String getMeasurementUserId() {
		return measurementUserId;
	}

	public String getMeasurementUserName() {
		return measurementUserName;
	}

	public String getExplain() {
		return explain;
	}

	public String getMeteringPoint() {
		return meteringPoint;
	}

	public String getManagementLevel() {
		return managementLevel;
	}

	public String getMeteringCycle() {
		return meteringCycle;
	}

	public String getInstrumentAccuracy() {
		return instrumentAccuracy;
	}

	public String getMeasurementMethod() {
		return measurementMethod;
	}

	public String getAdvanceReminderTime() {
		return advanceReminderTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getDate() {
		return date;
	}

	public String getInteger() {
		return integer;
	}

	public String getTemperature() {
		return temperature;
	}

	public String getPicture() {
		return picture;
	}

	public void setEntryMethod(String entryMethod) {
		this.entryMethod = entryMethod;
	}

	public void setSpecialEquipment(String specialEquipment) {
		this.specialEquipment = specialEquipment;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setWarrantyPeriod(String warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setMeteringEquipment(String meteringEquipment) {
		this.meteringEquipment = meteringEquipment;
	}

	public void setTechnicalParameter(String technicalParameter) {
		this.technicalParameter = technicalParameter;
	}

	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}

	public void setElectronicTagCode(String electronicTagCode) {
		this.electronicTagCode = electronicTagCode;
	}

	public void setUsageStatus(String usageStatus) {
		this.usageStatus = usageStatus;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setDateOfPurchase(String dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public void setEstimatedScrapDate(String estimatedScrapDate) {
		this.estimatedScrapDate = estimatedScrapDate;
	}

	public void setEquipmentLevel(String equipmentLevel) {
		this.equipmentLevel = equipmentLevel;
	}

	public void setEnableDepreciation(String enableDepreciation) {
		this.enableDepreciation = enableDepreciation;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public void setMeasurementId(String measurementId) {
		this.measurementId = measurementId;
	}

	public void setMeasurementRange(String measurementRange) {
		this.measurementRange = measurementRange;
	}

	public void setAllowableError(String allowableError) {
		this.allowableError = allowableError;
	}

	public void setMeasurementUserId(String measurementUserId) {
		this.measurementUserId = measurementUserId;
	}

	public void setMeasurementUserName(String measurementUserName) {
		this.measurementUserName = measurementUserName;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public void setMeteringPoint(String meteringPoint) {
		this.meteringPoint = meteringPoint;
	}

	public void setManagementLevel(String managementLevel) {
		this.managementLevel = managementLevel;
	}

	public void setMeteringCycle(String meteringCycle) {
		this.meteringCycle = meteringCycle;
	}

	public void setInstrumentAccuracy(String instrumentAccuracy) {
		this.instrumentAccuracy = instrumentAccuracy;
	}

	public void setMeasurementMethod(String measurementMethod) {
		this.measurementMethod = measurementMethod;
	}

	public void setAdvanceReminderTime(String advanceReminderTime) {
		this.advanceReminderTime = advanceReminderTime;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setInteger(String integer) {
		this.integer = integer;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAccountLocation() {
		return accountLocation;
	}

	public String getInventoryUserName() {
		return inventoryUserName;
	}

	public String getInventoryDate() {
		return inventoryDate;
	}

	public String getImage() {
		return image;
	}

	public void setAccountLocation(String accountLocation) {
		this.accountLocation = accountLocation;
	}

	public void setInventoryUserName(String inventoryUserName) {
		this.inventoryUserName = inventoryUserName;
	}

	public void setInventoryDate(String inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getInventoryQuantity() {
		return inventoryQuantity;
	}

	public void setInventoryQuantity(BigDecimal inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public String[] getSupport() {
		return support;
	}

	public void setSupport(String[] support) {
		this.support = support;
	}

	public String getSupports() {
		return supports;
	}

	public void setSupports(String supports) {
		this.supports = supports;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getSourceOfEquipment() {
		return sourceOfEquipment;
	}

	public String getPurchaseAmount() {
		return purchaseAmount;
	}

	public String getEquipmentStatus() {
		return equipmentStatus;
	}

	public String getIsMetering() {
		return isMetering;
	}

	public String getIsDepreciation() {
		return isDepreciation;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setSourceOfEquipment(String sourceOfEquipment) {
		this.sourceOfEquipment = sourceOfEquipment;
	}

	public void setPurchaseAmount(String purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}


	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}

	public void setIsMetering(String isMetering) {
		this.isMetering = isMetering;
	}

	public void setIsDepreciation(String isDepreciation) {
		this.isDepreciation = isDepreciation;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public Date getWarrantyPeriodExpires() {
		return warrantyPeriodExpires;
	}

	public Date getEstimatedRetirementDate() {
		return estimatedRetirementDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public void setWarrantyPeriodExpires(Date warrantyPeriodExpires) {
		this.warrantyPeriodExpires = warrantyPeriodExpires;
	}

	public void setEstimatedRetirementDate(Date estimatedRetirementDate) {
		this.estimatedRetirementDate = estimatedRetirementDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getFactoryNo() {
		return factoryNo;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setFactoryNo(String factoryNo) {
		this.factoryNo = factoryNo;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setNextInspectionDate(Date nextInspectionDate) {
		this.nextInspectionDate = nextInspectionDate;
	}

	public Date getNextInspectionDate() {
		return nextInspectionDate;
	}

	public String getReplacementCycle() {
		return replacementCycle;
	}

	public String getDuration() {
		return duration;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setReplacementCycle(String replacementCycle) {
		this.replacementCycle = replacementCycle;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String[] getProductNames() {
		return productNames;
	}

	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}

	public Date getNextReplacementDate() {
		return nextReplacementDate;
	}

	public void setNextReplacementDate(Date nextReplacementDate) {
		this.nextReplacementDate = nextReplacementDate;
	}

	public String getNumber() {
		return number;
	}

	public String getAlertValue() {
		return alertValue;
	}

	public String getProcurementCycle() {
		return procurementCycle;
	}

	public String getTagCode() {
		return tagCode;
	}

	public String getSparePartType() {
		return sparePartType;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setAlertValue(String alertValue) {
		this.alertValue = alertValue;
	}

	public void setProcurementCycle(String procurementCycle) {
		this.procurementCycle = procurementCycle;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public void setSparePartType(String sparePartType) {
		this.sparePartType = sparePartType;
	}

	public String getMeasurementNo() {
		return measurementNo;
	}

	public String getMeasuringRange() {
		return measuringRange;
	}

	public String getMeasurementPeriod() {
		return measurementPeriod;
	}

	public String getMeasurementResults() {
		return measurementResults;
	}

	public String getReminderTimeInAdvance() {
		return reminderTimeInAdvance;
	}

	public void setMeasurementNo(String measurementNo) {
		this.measurementNo = measurementNo;
	}

	public void setMeasuringRange(String measuringRange) {
		this.measuringRange = measuringRange;
	}

	public void setMeasurementPeriod(String measurementPeriod) {
		this.measurementPeriod = measurementPeriod;
	}

	public void setMeasurementResults(String measurementResults) {
		this.measurementResults = measurementResults;
	}

	public void setReminderTimeInAdvance(String reminderTimeInAdvance) {
		this.reminderTimeInAdvance = reminderTimeInAdvance;
	}

	public Date getNextMeasurementDate() {
		return nextMeasurementDate;
	}

	public Date getLastMeasurementDate() {
		return lastMeasurementDate;
	}

	public void setNextMeasurementDate(Date nextMeasurementDate) {
		this.nextMeasurementDate = nextMeasurementDate;
	}

	public void setLastMeasurementDate(Date lastMeasurementDate) {
		this.lastMeasurementDate = lastMeasurementDate;
	}

	public String getEquipmentNo() {
		return equipmentNo;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}


	public String getEventType() {
		return eventType;
	}

	public String getExpenses() {
		return expenses;
	}

	public String getChangeInValue() {
		return changeInValue;
	}

	public String getChangeValue() {
		return changeValue;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public String getValueAfterChange() {
		return valueAfterChange;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public void setExpenses(String expenses) {
		this.expenses = expenses;
	}

	public void setChangeInValue(String changeInValue) {
		this.changeInValue = changeInValue;
	}

	public void setChangeValue(String changeValue) {
		this.changeValue = changeValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public void setValueAfterChange(String valueAfterChange) {
		this.valueAfterChange = valueAfterChange;
	}

	public Date getDateOfOccurrence() {
		return dateOfOccurrence;
	}

	public void setDateOfOccurrence(Date dateOfOccurrence) {
		this.dateOfOccurrence = dateOfOccurrence;
	}

	public String getBmjgs() {
		return bmjgs;
	}

	public String getLbjgs() {
		return lbjgs;
	}

	public void setBmjgs(String bmjgs) {
		this.bmjgs = bmjgs;
	}

	public void setLbjgs(String lbjgs) {
		this.lbjgs = lbjgs;
	}

	public String getHandleId() {
		return handleId;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public BigDecimal getRetailPrices() {
		return retailPrices;
	}

	public void setRetailPrices(BigDecimal retailPrices) {
		this.retailPrices = retailPrices;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	

	

	
}	
