package dafengqi.yunxiang.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class Report implements Serializable {

	private static final long serialVersionUID = 6391872883644926431L;

	private String id;
	private String mechanismId;
	private String from;
	private String departmentIds;
	private String username;
	private String time;
	private String ztime;
	private String status;
	private String type;
	private String departmentId;
	private String resultsType;
	private String timeToScreen;
	private String timeLatitude;
	private String departmentName;
	private String column;
	private BigDecimal contractNumber;
	private BigDecimal winSingular;
	private BigDecimal totalContractAmount;
	private BigDecimal theGuestUnitPrice;
	private BigDecimal aggregateAmount;
	private BigDecimal receivableAmount;
	private BigDecimal invoiceAmount;
	private BigDecimal toWinASingleAmount;
	private BigDecimal estimatedSalesAmount;
	private BigDecimal contractAmount;
	private BigDecimal amountPaidBack;
	private BigDecimal signTheBillAmount;
	private BigDecimal signTheSingular;
	private BigDecimal amountOfPlannedPaymentCollection;
	
	
	
	private int ranking;
	private int customerNumber;
	private int clueNumber;
	private int businessOpportunitiesNumber;
	private int contractsNumber;
	private int dh;
	private int qq;
	private int wx;
	private int bf;
	private int yj;
	private int dx;
	private int qt;
	private String name;
	private String userId;

	
	public Report() {
	}

	public Report(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Report)) {
			return false;
		}
		Report other = (Report) object;
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
	


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMechanismId() {
		return mechanismId;
	}

	public void setMechanismId(String mechanismId) {
		this.mechanismId = mechanismId;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public BigDecimal getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(BigDecimal contractNumber) {
		this.contractNumber = contractNumber;
	}

	public BigDecimal getTotalContractAmount() {
		return totalContractAmount;
	}

	public void setTotalContractAmount(BigDecimal totalContractAmount) {
		this.totalContractAmount = totalContractAmount;
	}

	public BigDecimal getTheGuestUnitPrice() {
		return theGuestUnitPrice;
	}

	public void setTheGuestUnitPrice(BigDecimal theGuestUnitPrice) {
		this.theGuestUnitPrice = theGuestUnitPrice;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimeToScreen() {
		return timeToScreen;
	}

	public void setTimeToScreen(String timeToScreen) {
		this.timeToScreen = timeToScreen;
	}

	public String getResultsType() {
		return resultsType;
	}

	public void setResultsType(String resultsType) {
		this.resultsType = resultsType;
	}

	public BigDecimal getAggregateAmount() {
		return aggregateAmount;
	}

	public void setAggregateAmount(BigDecimal aggregateAmount) {
		this.aggregateAmount = aggregateAmount;
	}

	public String getTimeLatitude() {
		return timeLatitude;
	}

	public void setTimeLatitude(String timeLatitude) {
		this.timeLatitude = timeLatitude;
	}

	public int getClueNumber() {
		return clueNumber;
	}

	public void setClueNumber(int clueNumber) {
		this.clueNumber = clueNumber;
	}

	public int getBusinessOpportunitiesNumber() {
		return businessOpportunitiesNumber;
	}

	public void setBusinessOpportunitiesNumber(int businessOpportunitiesNumber) {
		this.businessOpportunitiesNumber = businessOpportunitiesNumber;
	}

	public int getContractsNumber() {
		return contractsNumber;
	}

	public void setContractsNumber(int contractsNumber) {
		this.contractsNumber = contractsNumber;
	}

	public BigDecimal getReceivableAmount() {
		return receivableAmount;
	}

	public void setReceivableAmount(BigDecimal receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getToWinASingleAmount() {
		return toWinASingleAmount;
	}

	public void setToWinASingleAmount(BigDecimal toWinASingleAmount) {
		this.toWinASingleAmount = toWinASingleAmount;
	}

	public BigDecimal getEstimatedSalesAmount() {
		return estimatedSalesAmount;
	}

	public void setEstimatedSalesAmount(BigDecimal estimatedSalesAmount) {
		this.estimatedSalesAmount = estimatedSalesAmount;
	}

	public BigDecimal getWinSingular() {
		return winSingular;
	}

	public void setWinSingular(BigDecimal winSingular) {
		this.winSingular = winSingular;
	}

	public BigDecimal getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getZtime() {
		return ztime;
	}

	public void setZtime(String ztime) {
		this.ztime = ztime;
	}

	public int getDh() {
		return dh;
	}

	public void setDh(int dh) {
		this.dh = dh;
	}

	
	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	public int getWx() {
		return wx;
	}

	public void setWx(int wx) {
		this.wx = wx;
	}

	public int getBf() {
		return bf;
	}

	public void setBf(int bf) {
		this.bf = bf;
	}

	public int getYj() {
		return yj;
	}

	public void setYj(int yj) {
		this.yj = yj;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getQt() {
		return qt;
	}

	public void setQt(int qt) {
		this.qt = qt;
	}

	public BigDecimal getAmountPaidBack() {
		return amountPaidBack;
	}

	public void setAmountPaidBack(BigDecimal amountPaidBack) {
		this.amountPaidBack = amountPaidBack;
	}

	public BigDecimal getSignTheSingular() {
		return signTheSingular;
	}

	public void setSignTheSingular(BigDecimal signTheSingular) {
		this.signTheSingular = signTheSingular;
	}

	public BigDecimal getSignTheBillAmount() {
		return signTheBillAmount;
	}

	public void setSignTheBillAmount(BigDecimal signTheBillAmount) {
		this.signTheBillAmount = signTheBillAmount;
	}

	public BigDecimal getAmountOfPlannedPaymentCollection() {
		return amountOfPlannedPaymentCollection;
	}

	public void setAmountOfPlannedPaymentCollection(BigDecimal amountOfPlannedPaymentCollection) {
		this.amountOfPlannedPaymentCollection = amountOfPlannedPaymentCollection;
	}
	
	
	

	
	
}	
