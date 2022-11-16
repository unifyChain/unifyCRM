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
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.NotNull;

@Entity
@Table(name = "productdetail")
@XmlRootElement
public class ProductDetail implements Serializable {
	private static final long serialVersionUID = 7757426658502654233L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 45)
	@Column(name = "id")
	private String id;//编码

	@Column(name = "order_date")
    private Date orderDate;
    private String businessCategory;
    private String correspondentUnit;
	
	@Column(name = "product_id")
    private String productId;
	
	@Column(name = "bar_code")
    private String barCode;
	
	@Column(name = "product_name")
    private String productName;
	
	@Column(name = "category_id")
    private String categoryId;
	
	@Column(name = "category_name")
    private String categoryName;
	
	@Column(name = "warehouse_id")
    private String warehouseId;
	
	@Column(name = "warehouse_name")
    private String warehouseName;
	
	@Column(name = "stock")
    private BigDecimal stock;
	
	@Column(name = "retail_price")
    private BigDecimal retailPrice;
	
	@Column(name = "membership_price")
    private BigDecimal membershipPrice;
	
	@Column(name = "purchase_price")
    private BigDecimal purchasePrice;
	
	@Column(name = "brand_id")
    private String brandId;
	
	@Column(name = "brand_name")
    private String brandName;

	@Column(name = "unit")
    private String unit;//单位
	
	@Column(name = "sub_unit")
    private String subUnit;//副单位
	
	@Column(name = "quantity")
    private BigDecimal quantity;//数量
	
	@Column(name = "unit_cost")
    private BigDecimal unitCost;
	
	@Column(name = "cost")
    private BigDecimal cost;
	
    private BigDecimal quantitys;//出库数量
	
    private BigDecimal unitCosts;
	
    private BigDecimal costs;


	
    private String rkQuantity;//入库数量
	
    private String rkUnitCost;
	
    private String rkCost;


	
    private String ckQuantity;//出库数量
	
    private String ckUnitCost;
	
    private String ckCost;
    

	
    private BigDecimal quantityjc;//结存数量
	
    private BigDecimal unitCostjc;
	
    private BigDecimal costjc;
    
    private String orderId;//单据编码
	
	
	
	@Column(name = "remarks")
    private String remarks;//备注
	
	@Column(name = "opening_quantity")
    private BigDecimal openingQuantity;//期初数量
	
	@Column(name = "opening_total_price")
    private BigDecimal openingTotalPrice;//期初余额合计
	
    
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
	
	@Column(name = "mechanism_id")
	private String mechanismId; //机构id
	
	@Column(name = "mechanism_name")
	private String mechanismName; //机构名称
	

	private String[] warehouseNames;
	private String[] productNames;
	private String[] categoryNames;
	private List<Date> dateRange;
	

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
    private String quantity1;//数量
	@Transient
    private String cost1;//成本

	@Transient
    private String quantity2;//数量
	@Transient
    private String cost2;//成本

	@Transient
    private String quantity3;//数量
	@Transient
    private String cost3;//成本

	@Transient
    private String quantity4;//数量
	@Transient
    private String cost4;//成本

	@Transient
    private String quantity5;//数量
	@Transient
    private String cost5;//成本

	@Transient
    private String quantity6;//数量
	@Transient
    private String cost6;//成本

	@Transient
    private String quantity7;//数量
	@Transient
    private String cost7;//成本

	@Transient
    private String totalReceipt;//入库合计
	@Transient
    private String totalReceiptCost;//入库合计成本

	@Transient
    private String quantity8;//数量
	@Transient
    private String cost8;//成本

	@Transient
    private String quantity9;//数量
	@Transient
    private String cost9;//成本

	@Transient
    private String quantity10;//数量
	@Transient
    private String cost10;//成本

	@Transient
    private String quantity11;//数量
	@Transient
    private String cost11;//成本

	@Transient
    private String quantity12;//数量
	@Transient
    private String cost12;//成本

	@Transient
    private String quantity13;//数量
	@Transient
    private String cost13;//成本
	


	@Transient
    private String totalCheckout;//出库合计
	@Transient
    private String totalCheckoutCost;//入库合计成本
	
	
	

	private BigDecimal kcsl = BigDecimal.ZERO;
	
	public ProductDetail() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
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


	public BigDecimal getStock() {
		return stock;
	}


	public void setStock(BigDecimal stock) {
		this.stock = stock;
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


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public BigDecimal getOpeningQuantity() {
		return openingQuantity;
	}


	public void setOpeningQuantity(BigDecimal openingQuantity) {
		this.openingQuantity = openingQuantity;
	}


	public BigDecimal getOpeningTotalPrice() {
		return openingTotalPrice;
	}


	public void setOpeningTotalPrice(BigDecimal openingTotalPrice) {
		this.openingTotalPrice = openingTotalPrice;
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


	public String getSubUnit() {
		return subUnit;
	}


	public void setSubUnit(String subUnit) {
		this.subUnit = subUnit;
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


	public BigDecimal getKcsl() {
		return kcsl;
	}


	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}


	public BigDecimal getUnitCost() {
		return unitCost;
	}


	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}


	public BigDecimal getCost() {
		return cost;
	}


	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public String getBusinessCategory() {
		return businessCategory;
	}


	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}


	public String getCorrespondentUnit() {
		return correspondentUnit;
	}


	public void setCorrespondentUnit(String correspondentUnit) {
		this.correspondentUnit = correspondentUnit;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}


	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getQuantitys() {
		return quantitys;
	}


	public void setQuantitys(BigDecimal quantitys) {
		this.quantitys = quantitys;
	}


	public BigDecimal getUnitCosts() {
		return unitCosts;
	}


	public void setUnitCosts(BigDecimal unitCosts) {
		this.unitCosts = unitCosts;
	}


	public BigDecimal getCosts() {
		return costs;
	}


	public void setCosts(BigDecimal costs) {
		this.costs = costs;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public BigDecimal getQuantityjc() {
		return quantityjc;
	}


	public void setQuantityjc(BigDecimal quantityjc) {
		this.quantityjc = quantityjc;
	}


	public BigDecimal getUnitCostjc() {
		return unitCostjc;
	}


	public void setUnitCostjc(BigDecimal unitCostjc) {
		this.unitCostjc = unitCostjc;
	}


	public BigDecimal getCostjc() {
		return costjc;
	}


	public void setCostjc(BigDecimal costjc) {
		this.costjc = costjc;
	}



	public String getQuantity1() {
		return quantity1;
	}


	public void setQuantity1(String quantity1) {
		this.quantity1 = quantity1;
	}


	public String getCost1() {
		return cost1;
	}


	public void setCost1(String cost1) {
		this.cost1 = cost1;
	}


	public String getQuantity2() {
		return quantity2;
	}


	public void setQuantity2(String quantity2) {
		this.quantity2 = quantity2;
	}


	public String getCost2() {
		return cost2;
	}


	public void setCost2(String cost2) {
		this.cost2 = cost2;
	}


	public String getQuantity3() {
		return quantity3;
	}


	public void setQuantity3(String quantity3) {
		this.quantity3 = quantity3;
	}


	public String getCost3() {
		return cost3;
	}


	public void setCost3(String cost3) {
		this.cost3 = cost3;
	}


	public String getQuantity4() {
		return quantity4;
	}


	public void setQuantity4(String quantity4) {
		this.quantity4 = quantity4;
	}


	public String getCost4() {
		return cost4;
	}


	public void setCost4(String cost4) {
		this.cost4 = cost4;
	}


	public String getQuantity5() {
		return quantity5;
	}


	public void setQuantity5(String quantity5) {
		this.quantity5 = quantity5;
	}


	public String getCost5() {
		return cost5;
	}


	public void setCost5(String cost5) {
		this.cost5 = cost5;
	}


	public String getQuantity6() {
		return quantity6;
	}


	public void setQuantity6(String quantity6) {
		this.quantity6 = quantity6;
	}


	public String getCost6() {
		return cost6;
	}


	public void setCost6(String cost6) {
		this.cost6 = cost6;
	}


	public String getQuantity7() {
		return quantity7;
	}


	public void setQuantity7(String quantity7) {
		this.quantity7 = quantity7;
	}


	public String getCost7() {
		return cost7;
	}


	public void setCost7(String cost7) {
		this.cost7 = cost7;
	}


	public String getTotalReceipt() {
		return totalReceipt;
	}


	public void setTotalReceipt(String totalReceipt) {
		this.totalReceipt = totalReceipt;
	}


	public String getTotalReceiptCost() {
		return totalReceiptCost;
	}


	public void setTotalReceiptCost(String totalReceiptCost) {
		this.totalReceiptCost = totalReceiptCost;
	}


	public String getQuantity8() {
		return quantity8;
	}


	public void setQuantity8(String quantity8) {
		this.quantity8 = quantity8;
	}


	public String getCost8() {
		return cost8;
	}


	public void setCost8(String cost8) {
		this.cost8 = cost8;
	}


	public String getQuantity9() {
		return quantity9;
	}


	public void setQuantity9(String quantity9) {
		this.quantity9 = quantity9;
	}


	public String getCost9() {
		return cost9;
	}


	public void setCost9(String cost9) {
		this.cost9 = cost9;
	}


	public String getQuantity10() {
		return quantity10;
	}


	public void setQuantity10(String quantity10) {
		this.quantity10 = quantity10;
	}


	public String getCost10() {
		return cost10;
	}


	public void setCost10(String cost10) {
		this.cost10 = cost10;
	}


	public String getQuantity11() {
		return quantity11;
	}


	public void setQuantity11(String quantity11) {
		this.quantity11 = quantity11;
	}


	public String getCost11() {
		return cost11;
	}


	public void setCost11(String cost11) {
		this.cost11 = cost11;
	}


	public String getQuantity12() {
		return quantity12;
	}


	public void setQuantity12(String quantity12) {
		this.quantity12 = quantity12;
	}


	public String getCost12() {
		return cost12;
	}


	public void setCost12(String cost12) {
		this.cost12 = cost12;
	}


	public String getQuantity13() {
		return quantity13;
	}


	public void setQuantity13(String quantity13) {
		this.quantity13 = quantity13;
	}


	public String getCost13() {
		return cost13;
	}


	public void setCost13(String cost13) {
		this.cost13 = cost13;
	}


	public String getTotalCheckout() {
		return totalCheckout;
	}


	public void setTotalCheckout(String totalCheckout) {
		this.totalCheckout = totalCheckout;
	}


	public String getTotalCheckoutCost() {
		return totalCheckoutCost;
	}


	public void setTotalCheckoutCost(String totalCheckoutCost) {
		this.totalCheckoutCost = totalCheckoutCost;
	}


	public String[] getWarehouseNames() {
		return warehouseNames;
	}


	public void setWarehouseNames(String[] warehouseNames) {
		this.warehouseNames = warehouseNames;
	}


	public String[] getProductNames() {
		return productNames;
	}


	public void setProductNames(String[] productNames) {
		this.productNames = productNames;
	}


	public String[] getCategoryNames() {
		return categoryNames;
	}


	public void setCategoryNames(String[] categoryNames) {
		this.categoryNames = categoryNames;
	}


	public List<Date> getDateRange() {
		return dateRange;
	}


	public void setDateRange(List<Date> dateRange) {
		this.dateRange = dateRange;
	}


	public String getRkQuantity() {
		return rkQuantity;
	}


	public void setRkQuantity(String rkQuantity) {
		this.rkQuantity = rkQuantity;
	}


	


	public String getRkUnitCost() {
		return rkUnitCost;
	}


	public void setRkUnitCost(String rkUnitCost) {
		this.rkUnitCost = rkUnitCost;
	}


	public String getRkCost() {
		return rkCost;
	}


	public void setRkCost(String rkCost) {
		this.rkCost = rkCost;
	}


	public String getCkQuantity() {
		return ckQuantity;
	}


	public void setCkQuantity(String ckQuantity) {
		this.ckQuantity = ckQuantity;
	}


	public String getCkUnitCost() {
		return ckUnitCost;
	}


	public void setCkUnitCost(String ckUnitCost) {
		this.ckUnitCost = ckUnitCost;
	}


	public String getCkCost() {
		return ckCost;
	}


	public void setCkCost(String ckCost) {
		this.ckCost = ckCost;
	}


	

	
}
