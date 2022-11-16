package dafengqi.yunxiang.dao.hibernate;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.primefaces.avalon.domain.Document;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.CustomerDao;
import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Customer;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.util.ResourceManager;
import jxl.Sheet;
import jxl.Workbook;

@Repository("customerDao")
public class CustomerDaoHibernate extends GenericDaoHibernate<Customer, Long> implements CustomerDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public CustomerDaoHibernate() {
		super(Customer.class);
	}

	@Override
	public List<Customer> getItems(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		String username = customer.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (customer.getRegions() != null) {
				if (customer.getRegions().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getRegions().length; i++) {
						zsql += "  region='" + customer.getRegions()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getIndustrys() != null) {
				if (customer.getIndustrys().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getIndustrys().length; i++) {
						zsql += "  industry='" + customer.getIndustrys()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getPersonnelSizes() != null) {
				if (customer.getPersonnelSizes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getPersonnelSizes().length; i++) {
						zsql += "  personnel_size='" + customer.getPersonnelSizes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCreateIds() != null) {
				if (customer.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCreateIds().length; i++) {
						zsql += "  create_id='" + customer.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getFollowStatuss() != null) {
				if (customer.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + customer.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerTypes() != null) {
				if (customer.getCustomerTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerTypes().length; i++) {
						zsql += "  customer_type='" + customer.getCustomerTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getPersonIds() != null) {
				if (customer.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getPersonIds().length; i++) {
						zsql += "  person_id='" + customer.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerSources() != null) {
				if (customer.getCustomerSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerSources().length; i++) {
						zsql += "  customer_source='" + customer.getCustomerSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getDepartmentIds() != null) {
				if (!customer.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + customer.getDepartmentIds() + "'";
				}
			}
			if (customer.getCreateDate() == null) {
				if (customer.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getCreateDate().equals("")) {
					if (customer.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本季度")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getNextFollowTimes() == null) {
				if (customer.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : customer.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getNextFollowTimes().equals("")) {
					if (customer.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本季度")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : customer.getNextFollowTimeDateRange()) {
							if (i == 0) {
								zsql += " and next_follow_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and next_follow_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getUpdateDate() == null) {
				if (customer.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getUpdateDate().equals("")) {
					if (customer.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本季度")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getUpdateDateRange()) {
							if (i == 0) {
								zsql += " and update_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and update_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			String sql = "";

			if (customer.getFrom().equals("全部")) {
				sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region,balance_of_accounts_receivable,no FROM  crm_customer    where 1=1  and mechanism_id like ? "
						+ zsql + "  and data_type='客户' and (status!='gh' or status is null) order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, customer.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					customer = new Customer();
					customer.setId(rs.getString(1));
					customer.setName(rs.getString(2));
					customer.setCorporateName(rs.getString(3));
					customer.setCustomerTypeName(rs.getString(4));
					customer.setCustomerType(rs.getString(5));
					customer.setParentCustomer(rs.getString(6));
					customer.setParentCustomerName(rs.getString(7));
					customer.setTelephone(rs.getString(8));
					customer.setPhone(rs.getString(9));
					customer.setWxid(rs.getString(10));
					customer.setQq(rs.getString(11));
					customer.setWangwang(rs.getString(12));
					customer.setEmail(rs.getString(13));
					customer.setFax(rs.getString(14));
					customer.setWebsite(rs.getString(15));
					customer.setCountry(rs.getString(16));
					customer.setProvinceId(rs.getString(17));
					customer.setProvinceName(rs.getString(18));
					customer.setCityId(rs.getString(19));
					customer.setCityName(rs.getString(20));
					customer.setAreaId(rs.getString(21));
					customer.setAreaName(rs.getString(22));
					customer.setAddress(rs.getString(23));
					customer.setZipCode(rs.getString(24));
					customer.setFollowStatus(rs.getString(25));
					customer.setFollowStatusName(rs.getString(26));
					customer.setCustomerSource(rs.getString(27));
					customer.setCustomerSourceName(rs.getString(28));
					customer.setIndustry(rs.getString(29));
					customer.setIndustryName(rs.getString(30));
					customer.setPersonnelSize(rs.getString(31));
					customer.setPersonnelSizeName(rs.getString(32));
					customer.setNextFollowTime(rs.getDate(33));
					customer.setRemarks(rs.getString(34));
					customer.setPersonId(rs.getString(35));
					customer.setPersonName(rs.getString(36));
					customer.setDepartmentId(rs.getString(37));
					customer.setDepartmentName(rs.getString(38));
					customer.setRegisteredCapital(rs.getString(39));
					customer.setExamine(rs.getString(40));
					customer.setCreateId(rs.getString(41));
					customer.setCreateName(rs.getString(42));
					customer.setCreateDate(rs.getString(43));
					customer.setUpdateId(rs.getString(44));
					customer.setUpdateName(rs.getString(45));
					customer.setUpdateDate(rs.getString(46));
					customer.setMechanismId(rs.getString(47));
					customer.setRegion(rs.getString(48));
					customer.setBalanceOfAccountsReceivable(rs.getBigDecimal(49));
					customer.setNo(rs.getString(50));
					list.add(customer);
				}
			} else {
				String[] xh = customer.getDepartmentId().split(",");
				for (String bmid : xh) {
					if (!bmid.equals("")) {
						sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region,balance_of_accounts_receivable,no FROM  crm_customer    where 1=1  and mechanism_id like ? and department_id like ? "
								+ zsql
								+ "  and data_type='客户' and (status!='gh' or status is null) order by create_date desc ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, customer.getMechanismId() + "%");
						ps.setString(2, bmid + "%");
						rs = ps.executeQuery();
						while (rs.next()) {
							customer = new Customer();
							customer.setId(rs.getString(1));
							customer.setName(rs.getString(2));
							customer.setCorporateName(rs.getString(3));
							customer.setCustomerTypeName(rs.getString(4));
							customer.setCustomerType(rs.getString(5));
							customer.setParentCustomer(rs.getString(6));
							customer.setParentCustomerName(rs.getString(7));
							customer.setTelephone(rs.getString(8));
							customer.setPhone(rs.getString(9));
							customer.setWxid(rs.getString(10));
							customer.setQq(rs.getString(11));
							customer.setWangwang(rs.getString(12));
							customer.setEmail(rs.getString(13));
							customer.setFax(rs.getString(14));
							customer.setWebsite(rs.getString(15));
							customer.setCountry(rs.getString(16));
							customer.setProvinceId(rs.getString(17));
							customer.setProvinceName(rs.getString(18));
							customer.setCityId(rs.getString(19));
							customer.setCityName(rs.getString(20));
							customer.setAreaId(rs.getString(21));
							customer.setAreaName(rs.getString(22));
							customer.setAddress(rs.getString(23));
							customer.setZipCode(rs.getString(24));
							customer.setFollowStatus(rs.getString(25));
							customer.setFollowStatusName(rs.getString(26));
							customer.setCustomerSource(rs.getString(27));
							customer.setCustomerSourceName(rs.getString(28));
							customer.setIndustry(rs.getString(29));
							customer.setIndustryName(rs.getString(30));
							customer.setPersonnelSize(rs.getString(31));
							customer.setPersonnelSizeName(rs.getString(32));
							customer.setNextFollowTime(rs.getDate(33));
							customer.setRemarks(rs.getString(34));
							customer.setPersonId(rs.getString(35));
							customer.setPersonName(rs.getString(36));
							customer.setDepartmentId(rs.getString(37));
							customer.setDepartmentName(rs.getString(38));
							customer.setRegisteredCapital(rs.getString(39));
							customer.setExamine(rs.getString(40));
							customer.setCreateId(rs.getString(41));
							customer.setCreateName(rs.getString(42));
							customer.setCreateDate(rs.getString(43));
							customer.setUpdateId(rs.getString(44));
							customer.setUpdateName(rs.getString(45));
							customer.setUpdateDate(rs.getString(46));
							customer.setMechanismId(rs.getString(47));
							customer.setRegion(rs.getString(48));
							customer.setBalanceOfAccountsReceivable(rs.getBigDecimal(49));
							customer.setNo(rs.getString(50));
							list.add(customer);
						}
					}
				}
				sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region,balance_of_accounts_receivable,no FROM  crm_customer    where 1=1  and mechanism_id like ? and create_id=? and "
						+ zsql + "  and data_type='客户' and (status!='gh' or status is null) order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, customer.getMechanismId() + "%");
				ps.setString(2, username);
				rs = ps.executeQuery();
				while (rs.next()) {
					customer = new Customer();
					customer.setId(rs.getString(1));
					customer.setName(rs.getString(2));
					customer.setCorporateName(rs.getString(3));
					customer.setCustomerTypeName(rs.getString(4));
					customer.setCustomerType(rs.getString(5));
					customer.setParentCustomer(rs.getString(6));
					customer.setParentCustomerName(rs.getString(7));
					customer.setTelephone(rs.getString(8));
					customer.setPhone(rs.getString(9));
					customer.setWxid(rs.getString(10));
					customer.setQq(rs.getString(11));
					customer.setWangwang(rs.getString(12));
					customer.setEmail(rs.getString(13));
					customer.setFax(rs.getString(14));
					customer.setWebsite(rs.getString(15));
					customer.setCountry(rs.getString(16));
					customer.setProvinceId(rs.getString(17));
					customer.setProvinceName(rs.getString(18));
					customer.setCityId(rs.getString(19));
					customer.setCityName(rs.getString(20));
					customer.setAreaId(rs.getString(21));
					customer.setAreaName(rs.getString(22));
					customer.setAddress(rs.getString(23));
					customer.setZipCode(rs.getString(24));
					customer.setFollowStatus(rs.getString(25));
					customer.setFollowStatusName(rs.getString(26));
					customer.setCustomerSource(rs.getString(27));
					customer.setCustomerSourceName(rs.getString(28));
					customer.setIndustry(rs.getString(29));
					customer.setIndustryName(rs.getString(30));
					customer.setPersonnelSize(rs.getString(31));
					customer.setPersonnelSizeName(rs.getString(32));
					customer.setNextFollowTime(rs.getDate(33));
					customer.setRemarks(rs.getString(34));
					customer.setPersonId(rs.getString(35));
					customer.setPersonName(rs.getString(36));
					customer.setDepartmentId(rs.getString(37));
					customer.setDepartmentName(rs.getString(38));
					customer.setRegisteredCapital(rs.getString(39));
					customer.setExamine(rs.getString(40));
					customer.setCreateId(rs.getString(41));
					customer.setCreateName(rs.getString(42));
					customer.setCreateDate(rs.getString(43));
					customer.setUpdateId(rs.getString(44));
					customer.setUpdateName(rs.getString(45));
					customer.setUpdateDate(rs.getString(46));
					customer.setMechanismId(rs.getString(47));
					customer.setRegion(rs.getString(48));
					customer.setBalanceOfAccountsReceivable(rs.getBigDecimal(49));
					customer.setNo(rs.getString(50));
					list.add(customer);
				}

				for (int i = 0; i < list.size() - 1; i++) {
					for (int j = i + 1; j < list.size(); j++) {
						if (list.get(i).equals(list.get(j))) {
							list.remove(j);
						}
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItems(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public List<Customer> getItemsOfMy(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		String username = customer.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (customer.getFrom().equals("客户")) {
				zsql += " and data_type='" + customer.getFrom() + "'";
			}
			if (customer.getFrom().equals("供应商")) {
				zsql += " and data_type='" + customer.getFrom() + "'";
			}

			if (customer.getRegions() != null) {
				if (customer.getRegions().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getRegions().length; i++) {
						zsql += "  region='" + customer.getRegions()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getIndustrys() != null) {
				if (customer.getIndustrys().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getIndustrys().length; i++) {
						zsql += "  industry='" + customer.getIndustrys()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getPersonnelSizes() != null) {
				if (customer.getPersonnelSizes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getPersonnelSizes().length; i++) {
						zsql += "  personnel_size='" + customer.getPersonnelSizes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCreateIds() != null) {
				if (customer.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCreateIds().length; i++) {
						zsql += "  create_id='" + customer.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getFollowStatuss() != null) {
				if (customer.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + customer.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerTypes() != null) {
				if (customer.getCustomerTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerTypes().length; i++) {
						zsql += "  customer_type='" + customer.getCustomerTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerSources() != null) {
				if (customer.getCustomerSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerSources().length; i++) {
						zsql += "  customer_source='" + customer.getCustomerSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCreateDate() == null) {
				if (customer.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getCreateDate().equals("")) {
					if (customer.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本季度")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getNextFollowTimes() == null) {
				if (customer.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : customer.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getNextFollowTimes().equals("")) {
					if (customer.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本季度")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : customer.getNextFollowTimeDateRange()) {
							if (i == 0) {
								zsql += " and next_follow_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and next_follow_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getUpdateDate() == null) {
				if (customer.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getUpdateDate().equals("")) {
					if (customer.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本季度")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getUpdateDateRange()) {
							if (i == 0) {
								zsql += " and update_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and update_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			String sql = "";
			sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region,category_id,category_name,balance_of_accounts_receivable FROM  crm_customer    where 1=1  and mechanism_id like ?  and create_id=? and data_type='客户' and (status!='gh' or status is null) "
					+ zsql + " order by create_date desc ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getMechanismId() + "%");
			ps.setString(2, customer.getCreateName());
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString(1));
				customer.setName(rs.getString(2));
				customer.setCorporateName(rs.getString(3));
				customer.setCustomerTypeName(rs.getString(4));
				customer.setCustomerType(rs.getString(5));
				customer.setParentCustomer(rs.getString(6));
				customer.setParentCustomerName(rs.getString(7));
				customer.setTelephone(rs.getString(8));
				customer.setPhone(rs.getString(9));
				customer.setWxid(rs.getString(10));
				customer.setQq(rs.getString(11));
				customer.setWangwang(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setFax(rs.getString(14));
				customer.setWebsite(rs.getString(15));
				customer.setCountry(rs.getString(16));
				customer.setProvinceId(rs.getString(17));
				customer.setProvinceName(rs.getString(18));
				customer.setCityId(rs.getString(19));
				customer.setCityName(rs.getString(20));
				customer.setAreaId(rs.getString(21));
				customer.setAreaName(rs.getString(22));
				customer.setAddress(rs.getString(23));
				customer.setZipCode(rs.getString(24));
				customer.setFollowStatus(rs.getString(25));
				customer.setFollowStatusName(rs.getString(26));
				customer.setCustomerSource(rs.getString(27));
				customer.setCustomerSourceName(rs.getString(28));
				customer.setIndustry(rs.getString(29));
				customer.setIndustryName(rs.getString(30));
				customer.setPersonnelSize(rs.getString(31));
				customer.setPersonnelSizeName(rs.getString(32));
				customer.setNextFollowTime(rs.getDate(33));
				customer.setRemarks(rs.getString(34));
				customer.setPersonId(rs.getString(35));
				customer.setPersonName(rs.getString(36));
				customer.setDepartmentId(rs.getString(37));
				customer.setDepartmentName(rs.getString(38));
				customer.setRegisteredCapital(rs.getString(39));
				customer.setExamine(rs.getString(40));
				customer.setCreateId(rs.getString(41));
				customer.setCreateName(rs.getString(42));
				customer.setCreateDate(rs.getString(43));
				customer.setUpdateId(rs.getString(44));
				customer.setUpdateName(rs.getString(45));
				customer.setUpdateDate(rs.getString(46));
				customer.setMechanismId(rs.getString(47));
				customer.setRegion(rs.getString(48));
				customer.setCategoryId(rs.getString(49));
				customer.setCategoryName(rs.getString(50));
				customer.setBalanceOfAccountsReceivable(rs.getBigDecimal(51));

				list.add(customer);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItems(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public List<Customer> getItemsHighseas(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		String username = customer.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (customer.getRegions() != null) {
				if (customer.getRegions().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getRegions().length; i++) {
						zsql += "  region='" + customer.getRegions()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getIndustrys() != null) {
				if (customer.getIndustrys().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getIndustrys().length; i++) {
						zsql += "  industry='" + customer.getIndustrys()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getPersonnelSizes() != null) {
				if (customer.getPersonnelSizes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getPersonnelSizes().length; i++) {
						zsql += "  personnel_size='" + customer.getPersonnelSizes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCreateIds() != null) {
				if (customer.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCreateIds().length; i++) {
						zsql += "  create_id='" + customer.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getFollowStatuss() != null) {
				if (customer.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + customer.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerTypes() != null) {
				if (customer.getCustomerTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerTypes().length; i++) {
						zsql += "  customer_type='" + customer.getCustomerTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getPersonIds() != null) {
				if (customer.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getPersonIds().length; i++) {
						zsql += "  person_id='" + customer.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getCustomerSources() != null) {
				if (customer.getCustomerSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < customer.getCustomerSources().length; i++) {
						zsql += "  customer_source='" + customer.getCustomerSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (customer.getDepartmentIds() != null) {
				if (!customer.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + customer.getDepartmentIds();
				}
			}
			if (customer.getCreateDate() == null) {
				if (customer.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getCreateDate().equals("")) {
					if (customer.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getCreateDate().equals("本季度")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and create_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and create_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getNextFollowTimes() == null) {
				if (customer.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : customer.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getNextFollowTimes().equals("")) {
					if (customer.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getNextFollowTimes().equals("本季度")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : customer.getNextFollowTimeDateRange()) {
							if (i == 0) {
								zsql += " and next_follow_time>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and next_follow_time<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (customer.getUpdateDate() == null) {
				if (customer.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : customer.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!customer.getUpdateDate().equals("")) {
					if (customer.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (customer.getUpdateDate().equals("本季度")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (customer.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (customer.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : customer.getUpdateDateRange()) {
							if (i == 0) {
								zsql += " and update_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and update_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			String sql = "";
			sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region FROM  crm_customer    where 1=1  and mechanism_id like ? and status=? "
					+ zsql + " order by create_date desc ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getMechanismId() + "%");
			ps.setString(2, "gh");
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString(1));
				customer.setName(rs.getString(2));
				customer.setCorporateName(rs.getString(3));
				customer.setCustomerTypeName(rs.getString(4));
				customer.setCustomerType(rs.getString(5));
				customer.setParentCustomer(rs.getString(6));
				customer.setParentCustomerName(rs.getString(7));
				customer.setTelephone(rs.getString(8));
				customer.setPhone(rs.getString(9));
				customer.setWxid(rs.getString(10));
				customer.setQq(rs.getString(11));
				customer.setWangwang(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setFax(rs.getString(14));
				customer.setWebsite(rs.getString(15));
				customer.setCountry(rs.getString(16));
				customer.setProvinceId(rs.getString(17));
				customer.setProvinceName(rs.getString(18));
				customer.setCityId(rs.getString(19));
				customer.setCityName(rs.getString(20));
				customer.setAreaId(rs.getString(21));
				customer.setAreaName(rs.getString(22));
				customer.setAddress(rs.getString(23));
				customer.setZipCode(rs.getString(24));
				customer.setFollowStatus(rs.getString(25));
				customer.setFollowStatusName(rs.getString(26));
				customer.setCustomerSource(rs.getString(27));
				customer.setCustomerSourceName(rs.getString(28));
				customer.setIndustry(rs.getString(29));
				customer.setIndustryName(rs.getString(30));
				customer.setPersonnelSize(rs.getString(31));
				customer.setPersonnelSizeName(rs.getString(32));
				customer.setNextFollowTime(rs.getDate(33));
				customer.setRemarks(rs.getString(34));
				customer.setPersonId(rs.getString(35));
				customer.setPersonName(rs.getString(36));
				customer.setDepartmentId(rs.getString(37));
				customer.setDepartmentName(rs.getString(38));
				customer.setRegisteredCapital(rs.getString(39));
				customer.setExamine(rs.getString(40));
				customer.setCreateId(rs.getString(41));
				customer.setCreateName(rs.getString(42));
				customer.setCreateDate(rs.getString(43));
				customer.setUpdateId(rs.getString(44));
				customer.setUpdateName(rs.getString(45));
				customer.setUpdateDate(rs.getString(46));
				customer.setMechanismId(rs.getString(47));
				customer.setRegion(rs.getString(48));
				list.add(customer);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItems(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public Customer edit(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		List<Contacts> contactsList = new ArrayList<Contacts>();
		List<BusinessOpportunity> businessOpportunityList = new ArrayList<BusinessOpportunity>();
		List<Contract> contractList = new ArrayList<Contract>();
		List<Cost> costList = new ArrayList<Cost>();
		List<FollowUp> followupList = new ArrayList<FollowUp>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String jgid = customer.getMechanismId();
			String sql = "SELECT id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,category_id,category_name,balance_date,initial_receivables,initial_advance,taxpayer_identification_number,bank,bank_account,relation_mechanism_id,relation_mechanism_name FROM  crm_customer    where 1=1 and id=? and mechanism_id=? and data_type='客户' order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getId());
			ps.setString(2, jgid);
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString(1));
				customer.setName(rs.getString(2));
				customer.setCorporateName(rs.getString(3));
				customer.setCustomerTypeName(rs.getString(4));
				customer.setCustomerType(rs.getString(5) + "!_" + rs.getString(4));
				customer.setParentCustomer(rs.getString(6) + "!_" + rs.getString(7));
				customer.setParentCustomerName(rs.getString(7));
				customer.setTelephone(rs.getString(8));
				customer.setPhone(rs.getString(9));
				customer.setWxid(rs.getString(10));
				customer.setQq(rs.getString(11));
				customer.setWangwang(rs.getString(12));
				customer.setEmail(rs.getString(13));
				customer.setFax(rs.getString(14));
				customer.setWebsite(rs.getString(15));
				customer.setCountry(rs.getString(16));
				customer.setProvinceId(rs.getString(17) + "!_" + rs.getString(18));
				customer.setProvinceName(rs.getString(18));
				customer.setCityId(rs.getString(19));
				customer.setCityName(rs.getString(20));
				customer.setAreaId(rs.getString(21));
				customer.setAreaName(rs.getString(22));
				customer.setAddress(rs.getString(23));
				customer.setZipCode(rs.getString(24));
				customer.setFollowStatus(rs.getString(25) + "!_" + rs.getString(26));
				customer.setFollowStatusName(rs.getString(26));
				customer.setCustomerSource(rs.getString(27) + "!_" + rs.getString(28));
				customer.setCustomerSourceName(rs.getString(28));
				customer.setIndustry(rs.getString(29) + "!_" + rs.getString(30));
				customer.setIndustryName(rs.getString(30));
				customer.setPersonnelSize(rs.getString(31) + "!_" + rs.getString(32));
				customer.setPersonnelSizeName(rs.getString(32));
				customer.setNextFollowTime(rs.getDate(33));
				customer.setRemarks(rs.getString(34));
				customer.setPersonId(rs.getString(35) + "!_" + rs.getString(36));
				customer.setPersonName(rs.getString(36));
				customer.setDepartmentId(rs.getString(37) + "!_" + rs.getString(38));
				customer.setDepartmentName(rs.getString(38));
				customer.setRegisteredCapital(rs.getString(39));
				customer.setExamine(rs.getString(40));
				customer.setCreateId(rs.getString(41));
				customer.setCreateName(rs.getString(42));
				customer.setCreateDate(rs.getString(43));
				customer.setUpdateId(rs.getString(44));
				customer.setUpdateName(rs.getString(45));
				customer.setUpdateDate(rs.getString(46));
				customer.setMechanismId(rs.getString(47));
				customer.setCategoryId(rs.getString(48));
				customer.setCategoryName(rs.getString(49));
				customer.setBalanceDate(rs.getDate(50));
				customer.setInitialReceivables(rs.getBigDecimal(51));
				customer.setInitialAdvance(rs.getBigDecimal(52));
				customer.setTaxpayerIdentificationNumber(rs.getString(53));
				customer.setBank(rs.getString(54));
				customer.setBankAccount(rs.getString(55));
				customer.setRelationMechanismId(rs.getString(56));
				customer.setRelationMechanismName(rs.getString(57));
			}
			sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and customer_id = ? order by create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, customer.getMechanismId() + "%");
			ps1.setString(2, customer.getId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				Contacts contacts = new Contacts();
				contacts.setId(rs1.getString(1));
				contacts.setName(rs1.getString(2));
				contacts.setCustomerId(rs1.getString(3));
				contacts.setCustomerName(rs1.getString(4));
				contacts.setPosition(rs1.getString(5));
				contacts.setTelephone(rs1.getString(6));
				contacts.setPhone(rs1.getString(7));
				contacts.setDepartmentId(rs1.getString(8));
				contacts.setDepartmentName(rs1.getString(9));
				contacts.setWxid(rs1.getString(10));
				contacts.setQq(rs1.getString(11));
				contacts.setWangwang(rs1.getString(12));
				contacts.setEmail(rs1.getString(13));
				contacts.setSex(rs1.getString(14));
				contacts.setBirthday(rs1.getString(15));
				contacts.setCountry(rs1.getString(16));
				contacts.setProvinceId(rs1.getString(17));
				contacts.setProvinceName(rs1.getString(18));
				contacts.setCityId(rs1.getString(19));
				contacts.setCityName(rs1.getString(20));
				contacts.setAreaId(rs1.getString(21));
				contacts.setAreaName(rs1.getString(22));
				contacts.setAddress(rs1.getString(23));
				contacts.setZipCode(rs1.getString(24));
				contacts.setWebsite(rs1.getString(25));
				contacts.setExamine(rs1.getString(26));
				contacts.setRemarks(rs1.getString(27));
				contacts.setCreateId(rs1.getString(28));
				contacts.setCreateName(rs1.getString(29));
				contacts.setCreateDate(rs1.getString(30));
				contacts.setUpdateId(rs1.getString(31));
				contacts.setUpdateName(rs1.getString(32));
				contacts.setUpdateDate(rs1.getString(33));
				contacts.setMechanismId(rs1.getString(34));
				contacts.setPersonId(rs1.getString(35));
				contactsList.add(contacts);
			}
			customer.setContactsList(contactsList);

			sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and customer_id=? order by create_date desc ";
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, customer.getMechanismId() + "%");
			ps2.setString(2, customer.getId());
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				BusinessOpportunity businessOpportunity = new BusinessOpportunity();
				businessOpportunity.setId(rs2.getString(1));
				businessOpportunity.setBusinessOpportunityTitle(rs2.getString(2));
				businessOpportunity.setCustomerName(rs2.getString(3));
				businessOpportunity.setEstimatedAmount(rs2.getString(4));
				businessOpportunity.setExpectedSigningDates(rs2.getString(5));
				businessOpportunity.setSalesStage(rs2.getString(6));
				businessOpportunity.setSalesStageName(rs2.getString(7));
				businessOpportunity.setNextFollowTimes(rs2.getString(8));
				businessOpportunity.setBusinessOpportunityType(rs2.getString(9));
				businessOpportunity.setBusinessOpportunityTypeName(rs2.getString(10));
				businessOpportunity.setBusinessOpportunityTimes(rs2.getString(11));
				businessOpportunity.setBusinessOpportunitySource(rs2.getString(12));
				businessOpportunity.setPersonId(rs2.getString(13));
				businessOpportunity.setPersonName(rs2.getString(14));
				businessOpportunity.setDepartmentId(rs2.getString(15));
				businessOpportunity.setDepartmentName(rs2.getString(16));
				businessOpportunity.setAudit(rs2.getString(17));
				businessOpportunity.setRemarks(rs2.getString(18));
				businessOpportunity.setCreateId(rs2.getString(19));
				businessOpportunity.setCreateName(rs2.getString(20));
				businessOpportunity.setCreateDate(rs2.getString(21));
				businessOpportunity.setUpdateId(rs2.getString(22));
				businessOpportunity.setUpdateName(rs2.getString(23));
				businessOpportunity.setUpdateDate(rs2.getString(24));
				businessOpportunity.setCustomerId(rs2.getString(25));
				businessOpportunity.setCrmResponsibleId(rs2.getString(26));
				businessOpportunity.setProjectDebriefing(rs2.getString(27));
				businessOpportunity.setDegreeOfImportance(rs2.getString(28));
				businessOpportunity.setCrmBusinessOpportunitycolClassification(rs2.getString(29));
				businessOpportunity.setReminderFrequency(rs2.getString(30));
				businessOpportunity.setSysProjectDebriefingId(rs2.getString(31));
				businessOpportunity.setSysReminderSettingsId(rs2.getString(32));
				businessOpportunity.setCrmProductId(rs2.getString(33));
				businessOpportunity.setCrmContactsId(rs2.getString(34));
				businessOpportunity.setCrmPaymentCollectionPlanId(rs2.getString(35));
				businessOpportunity.setMechanismId(rs2.getString(36));
				businessOpportunityList.add(businessOpportunity);
			}
			customer.setBusinessOpportunityList(businessOpportunityList);
			sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id FROM  crm_contract  where 1=1 and mechanism_id like ? and customer_id=? order by create_date desc ";
			ps3 = conn.prepareStatement(sql);
			ps3.setString(1, customer.getMechanismId() + "%");
			ps3.setString(2, customer.getId());
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				Contract contract = new Contract();
				contract.setId(rs3.getString(1));
				contract.setContractTitle(rs3.getString(2));
				contract.setCustomerId(rs3.getString(3));
				contract.setCustomerName(rs3.getString(4));
				contract.setBusinessOpportunityId(rs3.getString(5));
				contract.setBusinessOpportunityName(rs3.getString(6));
				contract.setContractStartDate(rs3.getDate(7));
				contract.setContractEndDate(rs3.getDate(8));
				contract.setTotalContractAmount(rs3.getString(9));
				contract.setSigningDate(rs3.getDate(10));
				contract.setContractStatus(rs3.getString(11));
				contract.setContractNo(rs3.getString(12));
				contract.setContractType(rs3.getString(13));
				contract.setContractTypeName(rs3.getString(14));
				contract.setPaymentType(rs3.getString(15));
				contract.setPaymentTypeName(rs3.getString(16));
				contract.setOurSignatory(rs3.getString(17));
				contract.setClientSignatory(rs3.getString(18));
				contract.setNextFollowTime(rs3.getDate(19));
				contract.setContractCategory(rs3.getString(20));
				contract.setAudit(rs3.getString(21));
				contract.setPersonId(rs3.getString(22));
				contract.setPersonName(rs3.getString(23));
				contract.setDepartmentId(rs3.getString(24));
				contract.setDepartmentName(rs3.getString(25));
				contract.setRemarks(rs3.getString(26));
				contract.setCreateId(rs3.getString(27));
				contract.setCreateName(rs3.getString(28));
				contract.setCreateDate(rs3.getString(29));
				contract.setUpdateId(rs3.getString(30));
				contract.setUpdateName(rs3.getString(31));
				contract.setUpdateDate(rs3.getString(32));
				contract.setCrmResponsibleId(rs3.getString(33));
				contract.setImplementation(rs3.getString(34));
				contract.setProductionTime(rs3.getString(35));
				contract.setOutput(rs3.getString(36));
				contract.setShippingTime(rs3.getString(37));
				contract.setShipmentVolume(rs3.getString(38));
				contract.setSettlementTime(rs3.getString(39));
				contract.setSettlementVolume(rs3.getString(40));
				contract.setCollectionTime(rs3.getString(41));
				contract.setCollectionAmount(rs3.getString(42));
				contract.setContractUnsettledQuantity(rs3.getString(43));
				contract.setUnsettledShipmentQuantity(rs3.getString(44));
				contract.setAccountsReceivable(rs3.getString(45));
				contract.setCollaboratorName(rs3.getString(46));
				contract.setMechanismId(rs3.getString(47));
				contractList.add(contract);
			}
			customer.setContractList(contractList);
			sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? and customer_id=? order by create_date desc ";
			ps4 = conn.prepareStatement(sql);
			ps4.setString(1, customer.getMechanismId());
			ps4.setString(2, customer.getId());
			rs4 = ps4.executeQuery();
			while (rs4.next()) {
				Cost cost = new Cost();
				cost.setId(rs4.getString(1));
				cost.setExpenseType(rs4.getString(2));
				cost.setExpenseTypeName(rs4.getString(3));
				cost.setCostDescription(rs4.getString(4));
				cost.setExpenseAmount(rs4.getString(5));
				cost.setTimeOfOccurrence(rs4.getDate(6));
				cost.setCustomerId(rs4.getString(7));
				cost.setCustomerName(rs4.getString(8));
				cost.setContactsId(rs4.getString(9));
				cost.setContactsName(rs4.getString(10));
				cost.setBusinessId(rs4.getString(11));
				cost.setBusinessName(rs4.getString(12));
				cost.setContractId(rs4.getString(13));
				cost.setContractName(rs4.getString(14));
				cost.setFollowUpRecord(rs4.getString(15));
				cost.setFollowUpRecordName(rs4.getString(16));
				cost.setVisitSignIn(rs4.getString(17));
				cost.setEnclosure(rs4.getString(18));
				cost.setPersonId(rs4.getString(19));
				cost.setPersonName(rs4.getString(20));
				cost.setCreateId(rs4.getString(21));
				cost.setCreateName(rs4.getString(22));
				cost.setCreateDate(rs4.getString(23));
				cost.setUpdateId(rs4.getString(24));
				cost.setUpdateName(rs4.getString(25));
				cost.setUpdateDate(rs4.getString(26));
				cost.setMechanismId(rs4.getString(27));
				costList.add(cost);
			}
			customer.setCostList(costList);
//			sql = "SELECT id,follo_type,time,content,follow_id,follow_name,follow_status,contacts_id,contacts_name,next_follow_time,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,ly FROM  crm_follow_up where 1=1   and mechanism_id like ?  and follow_id=? order by create_date desc ";
//			ps5 = conn.prepareStatement(sql);
//			ps5.setString(1, customer.getMechanismId()+"%");
//			ps5.setString(2, customer.getId());
//			rs5 = ps5.executeQuery();
//			while (rs5.next()) {
//				FollowUp followup = new FollowUp();
//				followup.setId(rs5.getString(1));
//				followup.setFolloType(rs5.getString(2));
//				followup.setTime(rs5.getString(3));
//				followup.setContent(rs5.getString(4));
//				followup.setFollowId(rs5.getString(5));
//				followup.setFollowName(rs5.getString(6));
//				followup.setFollowStatus(rs5.getString(7));
//				followup.setContactsId(rs5.getString(8));
//				followup.setContactsName(rs5.getString(9));
//				followup.setNext_follow_time(rs5.getString(10));
//				followup.setCreate_by(rs5.getString(11));
//				followup.setCreate_name(rs5.getString(12));
//				followup.setCreate_time(rs5.getString(13));
//				followup.setModify_by(rs5.getString(14));
//				followup.setModify_name(rs5.getString(15));
//				followup.setModify_time(rs5.getString(16));
//				followup.setMechanism_id(rs5.getString(17));
//				followup.setFrom(rs5.getString(18));
//				followupList.add(followup);
//			}
//			customer.setFollowupList(followupList);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItems(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(rs4);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return customer;
	}

	@Override
	public Customer relationMechanismCustomer(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sjgSQL = "SELECT a.id,a.name FROM sys_mechanism a where 1=1 and a.id = ?";
			ps = conn.prepareStatement(sjgSQL);
			ps.setString(1, customer.getRelationMechanismId());
			rs = ps.executeQuery();

			customer = new Customer();
			while (rs.next()) {
				customer = new Customer();
				customer.setRelationMechanismId(rs.getString(1));
				customer.setRelationMechanismName(rs.getString(2));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItems(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return customer;
	}

	@Override
	public int rhighseas(String table, String id, String status, String mechanismId) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set status=? where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, status);
			ps.setString(2, id);
			ps.setString(3, mechanismId);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"rhighseas(String table, String id, String status,String mechanismId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int rhighseas(String table, String id, String status, Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set status=?,person_id='" + customer.getPersonId()
					+ "',person_name='" + customer.getPersonName() + "',department_id='" + customer.getDepartmentId()
					+ "',department_name='" + customer.getDepartmentName() + "',create_id='" + customer.getCreateId()
					+ "',create_name='" + customer.getCreateName() + "',create_date='" + df.format(new Date())
					+ "',update_id='',update_name='',update_date='' where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, status);
			ps.setString(2, id);
			ps.setString(3, customer.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"rhighseas(String table, String id, String status,String mechanismId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int removeCustomer(String table, String id, String mechanismId) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from " + table + " where 1=1 and id=?  and mechanism_id=? and data_type='客户' ";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, id);
			ps.setString(2, mechanismId);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("removeCustomer(String table, String id, String mechanismId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			if (e.getMessage().contains("Cannot delete or update a parent row")) {
				rv = -2;
			} else {
				rv = -1;
			}
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int saveCustomer(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String icustomerSQL = "INSERT INTO crm_customer(id,name,corporate_name,customer_type_name,customer_type,parent_customer,parent_customer_name,telephone,phone,wxid,qq,wangwang,email,fax,website,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,follow_status,follow_status_name,customer_source,customer_source_name,industry,industry_name,personnel_size,personnel_size_name,next_follow_time,remarks,person_id,person_name,department_id,department_name,registered_capital,examine,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,region,data_type,category_id,category_name,balance_date,initial_receivables,initial_advance,taxpayer_identification_number,bank,bank_account,balance_of_accounts_receivable,relation_mechanism_id,relation_mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icustomerSQL);
			ps.setString(1, customer.getId());
			ps.setString(2, customer.getName());
			ps.setString(3, customer.getCorporateName());
			if (customer.getCustomerType() == null) {
				ps.setString(4, "");
				ps.setString(5, "");
			} else {
				if (customer.getCustomerType().equals("") || customer.getCustomerType().equals("null")) {
					ps.setString(4, "");
					ps.setString(5, "");
				} else {
					String[] customer_type = customer.getCustomerType().split("!_");
					if (customer_type[0].equals("null")) {
						ps.setString(4, "");
						ps.setString(5, "");
					} else {
						ps.setString(4, customer_type[1]);
						ps.setString(5, customer_type[0]);
					}
				}
			}
			if (customer.getParentCustomer() == null) {
				ps.setString(6, "");
				ps.setString(7, "");
			} else {
				if (customer.getParentCustomer().equals("")) {
					ps.setString(6, "");
					ps.setString(7, "");
				} else {
					String[] parent_customer = customer.getParentCustomer().split("!_");
					if (parent_customer[0].equals("null")) {
						ps.setString(6, "");
						ps.setString(7, "");
					} else {
						ps.setString(6, parent_customer[0]);
						ps.setString(7, parent_customer[1]);
					}
				}
			}
			ps.setString(8, customer.getTelephone());
			ps.setString(9, customer.getPhone());
			ps.setString(10, customer.getWxid());
			ps.setString(11, customer.getQq());
			ps.setString(12, customer.getWangwang());
			ps.setString(13, customer.getEmail());
			ps.setString(14, customer.getFax());
			ps.setString(15, customer.getWebsite());
			ps.setString(16, customer.getCountry());
			ps.setString(17, customer.getProvinceId());
			ps.setString(18, customer.getProvinceName());
			ps.setString(19, customer.getCityId());
			ps.setString(20, customer.getCityName());
			ps.setString(21, customer.getAreaId());
			ps.setString(22, customer.getAreaName());
			ps.setString(23, customer.getAddress());
			ps.setString(24, customer.getZipCode());
			if (customer.getFollowStatus() == null) {
				ps.setString(25, "");
				ps.setString(26, "");
			} else {
				if (customer.getFollowStatus().equals("")) {
					ps.setString(25, "");
					ps.setString(26, "");
				} else {
					String[] parent_customer = customer.getParentCustomer().split("!_");
					String[] follow_status = customer.getFollowStatus().split("!_");
					if (follow_status[0].equals("null")) {
						ps.setString(25, "");
						ps.setString(26, "");

					} else {
						ps.setString(25, follow_status[0]);
						ps.setString(26, follow_status[1]);
					}
				}
			}
			if (customer.getCustomerSource() == null) {
				ps.setString(27, "");
				ps.setString(28, "");
			} else {
				if (customer.getCustomerSource().equals("")) {
					ps.setString(27, "");
					ps.setString(28, "");
				} else {
					String[] customer_source = customer.getCustomerSource().split("!_");
					if (customer_source[0].equals("null")) {
						ps.setString(27, "");
						ps.setString(28, "");
					} else {
						ps.setString(27, customer_source[0]);
						ps.setString(28, customer_source[1]);
					}
				}
			}
			if (customer.getIndustry() == null) {
				ps.setString(29, "");
				ps.setString(30, "");
			} else {
				if (customer.getIndustry().equals("")) {
					ps.setString(29, "");
					ps.setString(30, "");
				} else {
					String[] industry = customer.getIndustry().split("!_");
					if (industry[0].equals("null")) {
						ps.setString(29, "");
						ps.setString(30, "");
					} else {
						ps.setString(29, industry[0]);
						ps.setString(30, industry[1]);
					}
				}
			}
			if (customer.getPersonnelSize() == null) {
				ps.setString(31, "");
				ps.setString(32, "");
			} else {
				if (customer.getPersonnelSize().equals("")) {
					ps.setString(31, "");
					ps.setString(32, "");
				} else {
					String[] personnel_size = customer.getPersonnelSize().split("!_");
					if (personnel_size[0].equals("null")) {

						ps.setString(31, "");
						ps.setString(32, "");
					} else {
						ps.setString(31, personnel_size[0]);
						ps.setString(32, personnel_size[1]);

					}
				}
			}
			ps.setString(33, df.format(customer.getNextFollowTime()));
			ps.setString(34, customer.getRemarks());
			if (customer.getPersonId() == null) {
				ps.setString(35, "");
				ps.setString(36, "");
			} else {
				if (customer.getPersonId().equals("")) {
					ps.setString(35, "");
					ps.setString(36, "");
				} else {
					String[] sjlx = customer.getPersonId().split("!_");
					if (sjlx[0].equals("null")) {
						ps.setString(35, "");
						ps.setString(36, "");
					} else {
						ps.setString(35, sjlx[0]);
						ps.setString(36, sjlx[1]);
					}
				}
			}
			if (customer.getDepartmentId() == null) {
				ps.setString(37, "");
				ps.setString(38, "");
			} else {
				if (customer.getDepartmentId().equals("")) {
					ps.setString(37, "");
					ps.setString(38, "");
				} else {
					String[] department_id = customer.getDepartmentId().split("!_");
					if (department_id[0].equals("null")) {
						ps.setString(37, "");
						ps.setString(38, "");
					} else {
						ps.setString(37, department_id[0]);
						ps.setString(38, department_id[1]);
					}
				}
			}
			ps.setString(39, customer.getRegisteredCapital());
			ps.setString(40, customer.getExamine());
			ps.setString(41, customer.getCreateId());
			ps.setString(42, customer.getCreateName());
			ps.setString(43, customer.getCreateDate());
			ps.setString(44, customer.getUpdateId());
			ps.setString(45, customer.getUpdateName());
			ps.setString(46, customer.getUpdateDate());
			ps.setString(47, customer.getMechanismId());
			ps.setString(48, customer.getRegion());
			ps.setString(49, "客户");

			if (customer.getCategoryId() == null || customer.getCategoryId().equals("")) {
				ps.setString(50, "");
				ps.setString(51, "");
			} else {
				String[] categoryId = customer.getCategoryId().split("!_");
				if (categoryId[0].equals("null")) {
					ps.setString(50, "");
					ps.setString(51, "");
				} else {
					ps.setString(50, categoryId[0]);
					ps.setString(51, categoryId[1]);
				}
			}
			if (customer.getBalanceDate() == null) {
				ps.setString(52, "");
			} else {
				if (customer.getBalanceDate().equals("")) {
					ps.setString(52, "");
				} else {
					ps.setString(52, df.format(customer.getBalanceDate()));
				}
			}
			ps.setBigDecimal(53, customer.getInitialReceivables());
			ps.setBigDecimal(54, customer.getInitialAdvance());
			ps.setString(55, customer.getTaxpayerIdentificationNumber());
			ps.setString(56, customer.getBank());
			ps.setString(57, customer.getBankAccount());
			ps.setBigDecimal(58, customer.getInitialReceivables().subtract(customer.getInitialAdvance()));
			ps.setString(59, customer.getRelationMechanismId());
			ps.setString(60, customer.getRelationMechanismName());
			rv = ps.executeUpdate();

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveCustomer(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int update(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ucustomerSQL = "update crm_customer set name=?,corporate_name=?,customer_type_name=?,customer_type=?,parent_customer=?,parent_customer_name=?,telephone=?,phone=?,wxid=?,qq=?,wangwang=?,email=?,fax=?,website=?,country=?,province_id=?,province_name=?,city_id=?,city_name=?,area_id=?,area_name=?,address=?,zip_code=?,follow_status=?,follow_status_name=?,customer_source=?,customer_source_name=?,industry=?,industry_name=?,personnel_size=?,personnel_size_name=?,next_follow_time=?,remarks=?,person_id=?,person_name=?,department_id=?,department_name=?,registered_capital=?,examine=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,region=?,category_id=?,category_name=?,balance_date=?,initial_receivables=?,initial_advance=?,taxpayer_identification_number=?,bank=?,bank_account=?,category_id=?,category_name=?,relation_mechanism_id=?,relation_mechanism_name=? where 1=1 and id=? and data_type='客户' and mechanism_id=?";
			ps = conn.prepareStatement(ucustomerSQL);
			ps.setString(1, customer.getName());
			ps.setString(2, customer.getCorporateName());
			if (customer.getCustomerType() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
			} else {
				if (customer.getCustomerType().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
				} else {
					String[] customer_type = customer.getCustomerType().split("!_");
					ps.setString(3, customer_type[1]);
					ps.setString(4, customer_type[0]);
				}
			}
			if (customer.getParentCustomer() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (customer.getParentCustomer().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] parent_customer = customer.getParentCustomer().split("!_");
					ps.setString(5, parent_customer[0]);
					ps.setString(6, parent_customer[1]);
				}
			}
			ps.setString(7, customer.getTelephone());
			ps.setString(8, customer.getPhone());
			ps.setString(9, customer.getWxid());
			ps.setString(10, customer.getQq());
			ps.setString(11, customer.getWangwang());
			ps.setString(12, customer.getEmail());
			ps.setString(13, customer.getFax());
			ps.setString(14, customer.getWebsite());
			ps.setString(15, customer.getCountry());
			ps.setString(16, customer.getProvinceId());
			ps.setString(17, customer.getProvinceName());
			ps.setString(18, customer.getCityId());
			ps.setString(19, customer.getCityName());
			ps.setString(20, customer.getAreaId());
			ps.setString(21, customer.getAreaName());
			ps.setString(22, customer.getAddress());
			ps.setString(23, customer.getZipCode());
			if (customer.getFollowStatus() == null) {
				ps.setString(24, "");
				ps.setString(25, "");
			} else {
				if (customer.getFollowStatus().equals("")) {
					ps.setString(24, "");
					ps.setString(25, "");
				} else {
					String[] follow_status = customer.getFollowStatus().split("!_");
					ps.setString(24, follow_status[0]);
					ps.setString(25, follow_status[1]);
				}
			}
			if (customer.getCustomerSource() == null) {
				ps.setString(26, "");
				ps.setString(27, "");
			} else {
				if (customer.getCustomerSource().equals("")) {
					ps.setString(26, "");
					ps.setString(27, "");
				} else {
					String[] customer_source = customer.getCustomerSource().split("!_");
					ps.setString(26, customer_source[0]);
					ps.setString(27, customer_source[1]);
				}
			}
			if (customer.getIndustry() == null) {
				ps.setString(28, "");
				ps.setString(29, "");
			} else {
				if (customer.getIndustry().equals("")) {
					ps.setString(28, "");
					ps.setString(29, "");
				} else {
					String[] industry = customer.getIndustry().split("!_");
					ps.setString(28, industry[0]);
					ps.setString(29, industry[1]);
				}
			}
			if (customer.getPersonnelSize() == null) {
				ps.setString(30, "");
				ps.setString(31, "");
			} else {
				if (customer.getPersonnelSize().equals("")) {
					ps.setString(30, "");
					ps.setString(31, "");
				} else {
					String[] personnel_size = customer.getPersonnelSize().split("!_");
					ps.setString(30, personnel_size[0]);
					ps.setString(31, personnel_size[1]);
				}
			}
			ps.setString(32, df.format(customer.getNextFollowTime()));
			ps.setString(33, customer.getRemarks());
			if (customer.getPersonId() == null) {
				ps.setString(34, "");
				ps.setString(35, "");
			} else {
				if (customer.getPersonId().equals("")) {
					ps.setString(34, "");
					ps.setString(35, "");
				} else {
					String[] sjlx = customer.getPersonId().split("!_");
					ps.setString(34, sjlx[0]);
					ps.setString(35, sjlx[1]);
				}
			}
			if (customer.getDepartmentId() == null) {
				ps.setString(36, "");
				ps.setString(37, "");
			} else {
				if (customer.getDepartmentId().equals("")) {
					ps.setString(36, "");
					ps.setString(37, "");
				} else {
					String[] department_id = customer.getDepartmentId().split("!_");
					ps.setString(36, department_id[0]);
					ps.setString(37, department_id[1]);
				}
			}
			ps.setString(38, customer.getRegisteredCapital());
			ps.setString(39, customer.getExamine());
			ps.setString(40, customer.getCreateId());
			ps.setString(41, customer.getCreateName());
			ps.setString(42, customer.getCreateDate());
			ps.setString(43, customer.getUpdateId());
			ps.setString(44, customer.getUpdateName());
			ps.setString(45, customer.getUpdateDate());
			ps.setString(46, customer.getRegion());
			ps.setString(47, customer.getCategoryId());
			ps.setString(48, customer.getCategoryName());
			if (customer.getBalanceDate() == null) {
				ps.setString(49, "");
			} else {
				if (customer.getBalanceDate().equals("")) {
					ps.setString(49, "");
				} else {
					ps.setString(49, df.format(customer.getBalanceDate()));
				}
			}
			ps.setBigDecimal(50, customer.getInitialReceivables());
			ps.setBigDecimal(51, customer.getInitialAdvance());
			ps.setString(52, customer.getTaxpayerIdentificationNumber());
			ps.setString(53, customer.getBank());
			ps.setString(54, customer.getBankAccount());

			if (customer.getCategoryId() == null || customer.getCategoryId().equals("")) {
				ps.setString(55, "");
				ps.setString(56, "");
			} else {
				String[] category = customer.getCategoryId().split("!_");
				ps.setString(55, category[0]);
				ps.setString(56, category[1]);
			}

			ps.setString(57, customer.getRelationMechanismId());
			ps.setString(58, customer.getRelationMechanismName());
			ps.setString(59, customer.getId());
			ps.setString(60, customer.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateCustomer(customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public String excelToDb(String cjr, String jgid, String filePath, String jgmc, String departmentid,
			String departmentname) {
		int i = 0;
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;

		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

		int cfs = 0;
		String errorContent = "导入失败客户:";
		String zbsContent = "总笔数:";
		String cfsjContent = "存在数据:";
		String cwsjContent = "错误数据:";
		String successContent = "导入成功客户:";
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			// 得到表格中所有的数据
			List<Customer> listExcel = importCustomerByExcel(filePath);
			zbsContent = "总笔数:" + listExcel.size();
			// DBhepler db=new DBhepler();
			for (Customer customer : listExcel) {
				String khlbid = "";
				String khlyid = "";
				// 通过类别名称名称查询id
				String skhlbSQL = "SELECT a.dict_code,a.dict_label FROM sys_dict a where 1=1 and a.parent_code='customer_type' and a.dict_label='"
						+ customer.getCustomerType() + "'  and a.status='启用' order by a.create_id desc ";
				ps2 = conn.prepareStatement(skhlbSQL);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					khlbid = rs2.getString(1);
				}
				String skhlySQL = "SELECT a.dict_code,a.dict_label FROM sys_dict a where 1=1 and a.parent_code='customer_from' and a.dict_label='"
						+ customer.getCustomerSource() + "'  and a.status='启用' order by a.create_id desc ";
				ps3 = conn.prepareStatement(skhlySQL);
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					khlyid = rs3.getString(1);
				}

				int coo = 0;
				String skhSQL = "select count(*) from crm_customer where phone='" + customer.getPhone()
						+ "' and mechanism_id=?";
				ps = conn.prepareStatement(skhSQL);
				ps.setString(1, jgid);
				rs = ps.executeQuery();
				while (rs.next()) {
					coo = rs.getInt(1);
				}
				if (coo <= 0) {
					String igysSQL = "insert into crm_customer (id,phone,name,customer_type,customer_type_name,corporate_name,customer_source,customer_source_name,address,telephone,create_date,department_id,department_name,mechanism_id,mechanism_name) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps4 = conn.prepareStatement(igysSQL);
					ps4.setString(1, UUID.randomUUID().toString());
					ps4.setString(2, customer.getPhone());
					ps4.setString(3, customer.getName());
					ps4.setString(4, khlbid);
					ps4.setString(5, customer.getCustomerType());
					ps4.setString(6, customer.getCorporateName());
					ps4.setString(7, khlyid);
					ps4.setString(8, customer.getCustomerSource());
					ps4.setString(9, customer.getAddress());
					ps4.setString(10, customer.getTelephone());
					ps4.setString(11, df.format(new Date()));
					ps4.setString(12, departmentid);
					ps4.setString(13, departmentname);
					ps4.setString(14, jgid);
					ps4.setString(15, jgmc);
					int o = ps4.executeUpdate();
					if (o > 0) {
						i = 1;
						successContent += "[" + customer.getId() + "],";
					} else {
						errorContent += "[" + customer.getId() + "],";
					}
				} else {
					cfs++;
					cfsjContent += "存在数据:" + "" + cfs + "[" + customer.getId() + customer.getName() + "]";
					errorContent += "[" + customer.getId() + "],";
				}
			}
			long t2 = System.currentTimeMillis();
			System.out.println(" excelToDbgys时间消耗 ：" + (t2 - t1) + " ms");
		} catch (Exception e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return errorContent + "<br/>" + successContent + "<br/>" + zbsContent + "<br/>" + cfsjContent + "<br/>"
				+ cwsjContent;

	}

	/**
	 * 查询指定目录中电子表格中所有的数据
	 * 
	 * @param file 文件完整路径
	 * @return
	 */
	public static List<Customer> importCustomerByExcel(String file) {
		List<Customer> list = new ArrayList<Customer>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet("客户");// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			System.out.println(clos + " rows:" + rows);
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 第一个是列数，第二个是行数
					// 编号1
//					String bh = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
					String phone = rs.getCell(j++, i).getContents().toString();// 默认最左边编号也算一列
					String name = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
					String customerType = rs.getCell(j++, i).getContents().toString();// 默认最左边编号也算一列
					String corporateName = rs.getCell(j++, i).getContents();
					String customerSource = rs.getCell(j++, i).getContents();
					String address = rs.getCell(j++, i).getContents();
					String telephone = rs.getCell(j++, i).getContents().toString();
					list.add(
							new Customer(phone, name, customerType, corporateName, customerSource, address, telephone));
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<Customer> getItemsOfMyData(Customer customer) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Customer> list = new ArrayList<Customer>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (customer.getFrom().equals("客户")) {
				zsql += " and data_type='" + customer.getFrom() + "'";
			}
			if (customer.getFrom().equals("供应商")) {
				zsql += " and data_type='" + customer.getFrom() + "'";
			}

			String sql = "";
			sql = "SELECT id,name,customer_type,customer_type_name,relation_mechanism_id,relation_mechanism_name FROM  crm_customer    where 1=1  and mechanism_id like ?  and (status!='gh' or status is null) and customer_type is not null "
					+ zsql + " order by create_date desc ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getMechanismId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString(1));
				customer.setName(rs.getString(2));
				customer.setCategoryId(rs.getString(3));
				customer.setCategoryName(rs.getString(4));
				if (rs.getString(5) == null) {
					customer.setRelationMechanismId("无");
					customer.setRelationMechanismName("无");
				} else {
					if (rs.getString(5).equals("")) {
						customer.setRelationMechanismId("无");
						customer.setRelationMechanismName("无");
					} else {
						customer.setRelationMechanismId(rs.getString(5));
						customer.setRelationMechanismName(rs.getString(6));
					}
				}

				list.add(customer);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Customer> getItemsOfMyData(Customer customer)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

}