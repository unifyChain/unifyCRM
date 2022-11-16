package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.ContactsDao;
import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("contactsDao")
public class ContactsDaoHibernate extends GenericDaoHibernate<Contacts, Long> implements ContactsDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public ContactsDaoHibernate() {
		super(Contacts.class);
	}

	@Override
	public List<Contacts> getItems(Contacts contacts) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Contacts> list = new ArrayList<Contacts>();
		String username = contacts.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			String zsql = "";
			if (contacts.getCreateDate() == null) {
				if (contacts.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : contacts.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contacts.getCreateDate().equals("")) {
					if (contacts.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contacts.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contacts.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contacts.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contacts.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contacts.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : contacts.getCreateDateRange()) {
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

			if (contacts.getUpdateDate() == null) {
				if (contacts.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : contacts.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contacts.getUpdateDate().equals("")) {
					if (contacts.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contacts.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contacts.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contacts.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contacts.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contacts.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : contacts.getUpdateDateRange()) {
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
			if (contacts.getSex() != null) {
				if (!contacts.getSex().equals("")) {
					zsql += "and sex='" + contacts.getSex() + "'";
				}
			}
			if (contacts.getCreateIds() != null) {
				if (contacts.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contacts.getCreateIds().length; i++) {
						zsql += "  create_id='" + contacts.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contacts.getDepartmentIds() != null) {
				if (!contacts.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + contacts.getDepartmentIds() + "'";
				}
			}
			if (contacts.getCustomerIds() != null) {
				if (contacts.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contacts.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + contacts.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contacts.getFrom().equals("全部")) {
				sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, contacts.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					contacts = new Contacts();
					contacts.setId(rs.getString(1));
					contacts.setName(rs.getString(2));
					contacts.setCustomerId(rs.getString(3));
					contacts.setCustomerName(rs.getString(4));
					contacts.setPosition(rs.getString(5));
					contacts.setTelephone(rs.getString(6));
					contacts.setPhone(rs.getString(7));
					contacts.setDepartmentId(rs.getString(8));
					contacts.setDepartmentName(rs.getString(9));
					contacts.setWxid(rs.getString(10));
					contacts.setQq(rs.getString(11));
					contacts.setWangwang(rs.getString(12));
					contacts.setEmail(rs.getString(13));
					contacts.setSex(rs.getString(14));
					contacts.setBirthday(rs.getString(15));
					contacts.setCountry(rs.getString(16));
					contacts.setProvinceId(rs.getString(17));
					contacts.setProvinceName(rs.getString(18));
					contacts.setCityId(rs.getString(19));
					contacts.setCityName(rs.getString(20));
					contacts.setAreaId(rs.getString(21));
					contacts.setAreaName(rs.getString(22));
					contacts.setAddress(rs.getString(23));
					contacts.setZipCode(rs.getString(24));
					contacts.setWebsite(rs.getString(25));
					contacts.setExamine(rs.getString(26));
					contacts.setRemarks(rs.getString(27));
					contacts.setCreateId(rs.getString(28));
					contacts.setCreateName(rs.getString(29));
					contacts.setCreateDate(rs.getString(30));
					contacts.setUpdateId(rs.getString(31));
					contacts.setUpdateName(rs.getString(32));
					contacts.setUpdateDate(rs.getString(33));
					contacts.setMechanismId(rs.getString(34));
					contacts.setPersonId(rs.getString(35));
					list.add(contacts);
				}
			} else {
				if (contacts.getFrom().equals("本部")) {
					String[] xh = contacts.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, contacts.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								contacts = new Contacts();
								contacts.setId(rs.getString(1));
								contacts.setName(rs.getString(2));
								contacts.setCustomerId(rs.getString(3));
								contacts.setCustomerName(rs.getString(4));
								contacts.setPosition(rs.getString(5));
								contacts.setTelephone(rs.getString(6));
								contacts.setPhone(rs.getString(7));
								contacts.setDepartmentId(rs.getString(8));
								contacts.setDepartmentName(rs.getString(9));
								contacts.setWxid(rs.getString(10));
								contacts.setQq(rs.getString(11));
								contacts.setWangwang(rs.getString(12));
								contacts.setEmail(rs.getString(13));
								contacts.setSex(rs.getString(14));
								contacts.setBirthday(rs.getString(15));
								contacts.setCountry(rs.getString(16));
								contacts.setProvinceId(rs.getString(17));
								contacts.setProvinceName(rs.getString(18));
								contacts.setCityId(rs.getString(19));
								contacts.setCityName(rs.getString(20));
								contacts.setAreaId(rs.getString(21));
								contacts.setAreaName(rs.getString(22));
								contacts.setAddress(rs.getString(23));
								contacts.setZipCode(rs.getString(24));
								contacts.setWebsite(rs.getString(25));
								contacts.setExamine(rs.getString(26));
								contacts.setRemarks(rs.getString(27));
								contacts.setCreateId(rs.getString(28));
								contacts.setCreateName(rs.getString(29));
								contacts.setCreateDate(rs.getString(30));
								contacts.setUpdateId(rs.getString(31));
								contacts.setUpdateName(rs.getString(32));
								contacts.setUpdateDate(rs.getString(33));
								contacts.setMechanismId(rs.getString(34));
								contacts.setPersonId(rs.getString(35));
								list.add(contacts);
							}
						}
					}
					sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, contacts.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						contacts = new Contacts();
						contacts.setId(rs.getString(1));
						contacts.setName(rs.getString(2));
						contacts.setCustomerId(rs.getString(3));
						contacts.setCustomerName(rs.getString(4));
						contacts.setPosition(rs.getString(5));
						contacts.setTelephone(rs.getString(6));
						contacts.setPhone(rs.getString(7));
						contacts.setDepartmentId(rs.getString(8));
						contacts.setDepartmentName(rs.getString(9));
						contacts.setWxid(rs.getString(10));
						contacts.setQq(rs.getString(11));
						contacts.setWangwang(rs.getString(12));
						contacts.setEmail(rs.getString(13));
						contacts.setSex(rs.getString(14));
						contacts.setBirthday(rs.getString(15));
						contacts.setCountry(rs.getString(16));
						contacts.setProvinceId(rs.getString(17));
						contacts.setProvinceName(rs.getString(18));
						contacts.setCityId(rs.getString(19));
						contacts.setCityName(rs.getString(20));
						contacts.setAreaId(rs.getString(21));
						contacts.setAreaName(rs.getString(22));
						contacts.setAddress(rs.getString(23));
						contacts.setZipCode(rs.getString(24));
						contacts.setWebsite(rs.getString(25));
						contacts.setExamine(rs.getString(26));
						contacts.setRemarks(rs.getString(27));
						contacts.setCreateId(rs.getString(28));
						contacts.setCreateName(rs.getString(29));
						contacts.setCreateDate(rs.getString(30));
						contacts.setUpdateId(rs.getString(31));
						contacts.setUpdateName(rs.getString(32));
						contacts.setUpdateDate(rs.getString(33));
						contacts.setMechanismId(rs.getString(34));
						contacts.setPersonId(rs.getString(35));
						list.add(contacts);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (contacts.getFrom().equals("未设")) {

					sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, contacts.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						contacts = new Contacts();
						contacts.setId(rs.getString(1));
						contacts.setName(rs.getString(2));
						contacts.setCustomerId(rs.getString(3));
						contacts.setCustomerName(rs.getString(4));
						contacts.setPosition(rs.getString(5));
						contacts.setTelephone(rs.getString(6));
						contacts.setPhone(rs.getString(7));
						contacts.setDepartmentId(rs.getString(8));
						contacts.setDepartmentName(rs.getString(9));
						contacts.setWxid(rs.getString(10));
						contacts.setQq(rs.getString(11));
						contacts.setWangwang(rs.getString(12));
						contacts.setEmail(rs.getString(13));
						contacts.setSex(rs.getString(14));
						contacts.setBirthday(rs.getString(15));
						contacts.setCountry(rs.getString(16));
						contacts.setProvinceId(rs.getString(17));
						contacts.setProvinceName(rs.getString(18));
						contacts.setCityId(rs.getString(19));
						contacts.setCityName(rs.getString(20));
						contacts.setAreaId(rs.getString(21));
						contacts.setAreaName(rs.getString(22));
						contacts.setAddress(rs.getString(23));
						contacts.setZipCode(rs.getString(24));
						contacts.setWebsite(rs.getString(25));
						contacts.setExamine(rs.getString(26));
						contacts.setRemarks(rs.getString(27));
						contacts.setCreateId(rs.getString(28));
						contacts.setCreateName(rs.getString(29));
						contacts.setCreateDate(rs.getString(30));
						contacts.setUpdateId(rs.getString(31));
						contacts.setUpdateName(rs.getString(32));
						contacts.setUpdateDate(rs.getString(33));
						contacts.setMechanismId(rs.getString(34));
						contacts.setPersonId(rs.getString(35));
						list.add(contacts);
					}
				} else {

					String[] xh = contacts.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and department_id = ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, contacts.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								contacts = new Contacts();
								contacts.setId(rs.getString(1));
								contacts.setName(rs.getString(2));
								contacts.setCustomerId(rs.getString(3));
								contacts.setCustomerName(rs.getString(4));
								contacts.setPosition(rs.getString(5));
								contacts.setTelephone(rs.getString(6));
								contacts.setPhone(rs.getString(7));
								contacts.setDepartmentId(rs.getString(8));
								contacts.setDepartmentName(rs.getString(9));
								contacts.setWxid(rs.getString(10));
								contacts.setQq(rs.getString(11));
								contacts.setWangwang(rs.getString(12));
								contacts.setEmail(rs.getString(13));
								contacts.setSex(rs.getString(14));
								contacts.setBirthday(rs.getString(15));
								contacts.setCountry(rs.getString(16));
								contacts.setProvinceId(rs.getString(17));
								contacts.setProvinceName(rs.getString(18));
								contacts.setCityId(rs.getString(19));
								contacts.setCityName(rs.getString(20));
								contacts.setAreaId(rs.getString(21));
								contacts.setAreaName(rs.getString(22));
								contacts.setAddress(rs.getString(23));
								contacts.setZipCode(rs.getString(24));
								contacts.setWebsite(rs.getString(25));
								contacts.setExamine(rs.getString(26));
								contacts.setRemarks(rs.getString(27));
								contacts.setCreateId(rs.getString(28));
								contacts.setCreateName(rs.getString(29));
								contacts.setCreateDate(rs.getString(30));
								contacts.setUpdateId(rs.getString(31));
								contacts.setUpdateName(rs.getString(32));
								contacts.setUpdateDate(rs.getString(33));
								contacts.setMechanismId(rs.getString(34));
								contacts.setPersonId(rs.getString(35));
								list.add(contacts);
							}
						}
					}
					sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, contacts.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						contacts = new Contacts();
						contacts.setId(rs.getString(1));
						contacts.setName(rs.getString(2));
						contacts.setCustomerId(rs.getString(3));
						contacts.setCustomerName(rs.getString(4));
						contacts.setPosition(rs.getString(5));
						contacts.setTelephone(rs.getString(6));
						contacts.setPhone(rs.getString(7));
						contacts.setDepartmentId(rs.getString(8));
						contacts.setDepartmentName(rs.getString(9));
						contacts.setWxid(rs.getString(10));
						contacts.setQq(rs.getString(11));
						contacts.setWangwang(rs.getString(12));
						contacts.setEmail(rs.getString(13));
						contacts.setSex(rs.getString(14));
						contacts.setBirthday(rs.getString(15));
						contacts.setCountry(rs.getString(16));
						contacts.setProvinceId(rs.getString(17));
						contacts.setProvinceName(rs.getString(18));
						contacts.setCityId(rs.getString(19));
						contacts.setCityName(rs.getString(20));
						contacts.setAreaId(rs.getString(21));
						contacts.setAreaName(rs.getString(22));
						contacts.setAddress(rs.getString(23));
						contacts.setZipCode(rs.getString(24));
						contacts.setWebsite(rs.getString(25));
						contacts.setExamine(rs.getString(26));
						contacts.setRemarks(rs.getString(27));
						contacts.setCreateId(rs.getString(28));
						contacts.setCreateName(rs.getString(29));
						contacts.setCreateDate(rs.getString(30));
						contacts.setUpdateId(rs.getString(31));
						contacts.setUpdateName(rs.getString(32));
						contacts.setUpdateDate(rs.getString(33));
						contacts.setMechanismId(rs.getString(34));
						contacts.setPersonId(rs.getString(35));
						list.add(contacts);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contacts> getItems(Contacts contacts)" + (t2 - t1) + " ms");
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
	public List<Contacts> getItemsOfMy(Contacts contacts) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Contacts> list = new ArrayList<Contacts>();
		String username = contacts.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";
			if (contacts.getCreateDate() == null) {
				if (contacts.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : contacts.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contacts.getCreateDate().equals("")) {
					if (contacts.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contacts.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contacts.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contacts.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contacts.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contacts.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : contacts.getCreateDateRange()) {
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

			if (contacts.getUpdateDate() == null) {
				if (contacts.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : contacts.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!contacts.getUpdateDate().equals("")) {
					if (contacts.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (contacts.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (contacts.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (contacts.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (contacts.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (contacts.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : contacts.getUpdateDateRange()) {
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
			if (contacts.getSex() != null) {
				if (!contacts.getSex().equals("")) {
					zsql += "and sex='" + contacts.getSex() + "'";
				}
			}
			if (contacts.getCreateIds() != null) {
				if (contacts.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contacts.getCreateIds().length; i++) {
						zsql += "  create_id='" + contacts.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (contacts.getCustomerIds() != null) {
				if (contacts.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < contacts.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + contacts.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id FROM  crm_contacts    where 1=1 and mechanism_id like ? and create_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, contacts.getMechanismId() + "%");
			ps.setString(2, contacts.getCreateName());
			rs = ps.executeQuery();
			while (rs.next()) {
				contacts = new Contacts();
				contacts.setId(rs.getString(1));
				contacts.setName(rs.getString(2));
				contacts.setCustomerId(rs.getString(3));
				contacts.setCustomerName(rs.getString(4));
				contacts.setPosition(rs.getString(5));
				contacts.setTelephone(rs.getString(6));
				contacts.setPhone(rs.getString(7));
				contacts.setDepartmentId(rs.getString(8));
				contacts.setDepartmentName(rs.getString(9));
				contacts.setWxid(rs.getString(10));
				contacts.setQq(rs.getString(11));
				contacts.setWangwang(rs.getString(12));
				contacts.setEmail(rs.getString(13));
				contacts.setSex(rs.getString(14));
				contacts.setBirthday(rs.getString(15));
				contacts.setCountry(rs.getString(16));
				contacts.setProvinceId(rs.getString(17));
				contacts.setProvinceName(rs.getString(18));
				contacts.setCityId(rs.getString(19));
				contacts.setCityName(rs.getString(20));
				contacts.setAreaId(rs.getString(21));
				contacts.setAreaName(rs.getString(22));
				contacts.setAddress(rs.getString(23));
				contacts.setZipCode(rs.getString(24));
				contacts.setWebsite(rs.getString(25));
				contacts.setExamine(rs.getString(26));
				contacts.setRemarks(rs.getString(27));
				contacts.setCreateId(rs.getString(28));
				contacts.setCreateName(rs.getString(29));
				contacts.setCreateDate(rs.getString(30));
				contacts.setUpdateId(rs.getString(31));
				contacts.setUpdateName(rs.getString(32));
				contacts.setUpdateDate(rs.getString(33));
				contacts.setMechanismId(rs.getString(34));
				contacts.setPersonId(rs.getString(35));
				list.add(contacts);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contacts> getItems(Contacts contacts)" + (t2 - t1) + " ms");
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
	public Contacts edit(Contacts contacts) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		List<Contacts> list = new ArrayList<Contacts>();
		List<BusinessOpportunity> businessOpportunityList = new ArrayList<BusinessOpportunity>();
		List<Contract> contractList = new ArrayList<Contract>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id,customer_no FROM  crm_contacts    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, contacts.getId());
			rs = ps.executeQuery();
			String customerid = "";
			while (rs.next()) {
				contacts = new Contacts();
				contacts.setId(rs.getString(1));
				contacts.setName(rs.getString(2));
				contacts.setCustomerId(rs.getString(3) + "!_" + rs.getString(4) + "!_" + rs.getString(36));
				customerid = rs.getString(3);
				contacts.setCustomerName(rs.getString(4));
				contacts.setPosition(rs.getString(5));
				contacts.setTelephone(rs.getString(6));
				contacts.setPhone(rs.getString(7));
				contacts.setDepartmentId(rs.getString(8));
				contacts.setDepartmentName(rs.getString(9));
				contacts.setWxid(rs.getString(10));
				contacts.setQq(rs.getString(11));
				contacts.setWangwang(rs.getString(12));
				contacts.setEmail(rs.getString(13));
				contacts.setSex(rs.getString(14));
				contacts.setBirthday(rs.getString(15));
				contacts.setCountry(rs.getString(16));
				contacts.setProvinceId(rs.getString(17));
				contacts.setProvinceName(rs.getString(18));
				contacts.setCityId(rs.getString(19));
				contacts.setCityName(rs.getString(20));
				contacts.setAreaId(rs.getString(21));
				contacts.setAreaName(rs.getString(22));
				contacts.setAddress(rs.getString(23));
				contacts.setZipCode(rs.getString(24));
				contacts.setWebsite(rs.getString(25));
				contacts.setExamine(rs.getString(26));
				contacts.setRemarks(rs.getString(27));
				contacts.setCreateId(rs.getString(28));
				contacts.setCreateName(rs.getString(29));
				contacts.setCreateDate(rs.getString(30));
				contacts.setUpdateId(rs.getString(31));
				contacts.setUpdateName(rs.getString(32));
				contacts.setUpdateDate(rs.getString(33));
				contacts.setMechanismId(rs.getString(34));
				contacts.setPersonId(rs.getString(35));
			}
			sql = "SELECT id,business_opportunity_title,customer_name,estimated_amount,expected_signing_date,sales_stage,sales_stage_name,next_follow_time,business_opportunity_type,business_opportunity_type_name,business_opportunity_time,business_opportunity_source,person_id,person_name,department_id,department_name,audit,remarks,create_id,create_name,create_date,update_id,update_name,update_date,customer_id,crm_responsible_id,project_debriefing,degree_of_importance,crm_business_opportunitycol_classification,reminder_frequency,sys_project_debriefing_id,sys_reminder_settings_id,crm_product_id,crm_contacts_id,crm_payment_collection_plan_id,mechanism_id,state FROM  crm_business_opportunity    where 1=1  and mechanism_id like ? and customer_id=? order by create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, contacts.getMechanismId() + "%");
			ps1.setString(2, customerid);
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				BusinessOpportunity businessOpportunity = new BusinessOpportunity();
				businessOpportunity.setId(rs1.getString(1));
				businessOpportunity.setBusinessOpportunityTitle(rs1.getString(2));
				businessOpportunity.setCustomerName(rs1.getString(3));
				businessOpportunity.setEstimatedAmount(rs1.getString(4));
				businessOpportunity.setExpectedSigningDates(rs1.getString(5));
				businessOpportunity.setSalesStage(rs1.getString(6));
				businessOpportunity.setSalesStageName(rs1.getString(7));
				businessOpportunity.setNextFollowTimes(rs1.getString(8));
				businessOpportunity.setBusinessOpportunityType(rs1.getString(9));
				businessOpportunity.setBusinessOpportunityTypeName(rs1.getString(10));
				businessOpportunity.setBusinessOpportunityTimes(rs1.getString(11));
				businessOpportunity.setBusinessOpportunitySource(rs1.getString(12));
				businessOpportunity.setPersonId(rs1.getString(13));
				businessOpportunity.setPersonName(rs1.getString(14));
				businessOpportunity.setDepartmentId(rs1.getString(15));
				businessOpportunity.setDepartmentName(rs1.getString(16));
				businessOpportunity.setAudit(rs1.getString(17));
				businessOpportunity.setRemarks(rs1.getString(18));
				businessOpportunity.setCreateId(rs1.getString(19));
				businessOpportunity.setCreateName(rs1.getString(20));
				businessOpportunity.setCreateDate(rs1.getString(21));
				businessOpportunity.setUpdateId(rs1.getString(22));
				businessOpportunity.setUpdateName(rs1.getString(23));
				businessOpportunity.setUpdateDate(rs1.getString(24));
				businessOpportunity.setCustomerId(rs1.getString(25));
				businessOpportunity.setCrmResponsibleId(rs1.getString(26));
				businessOpportunity.setProjectDebriefing(rs1.getString(27));
				businessOpportunity.setDegreeOfImportance(rs1.getString(28));
				businessOpportunity.setCrmBusinessOpportunitycolClassification(rs1.getString(29));
				businessOpportunity.setReminderFrequency(rs1.getString(30));
				businessOpportunity.setSysProjectDebriefingId(rs1.getString(31));
				businessOpportunity.setSysReminderSettingsId(rs1.getString(32));
				businessOpportunity.setCrmProductId(rs1.getString(33));
				businessOpportunity.setCrmContactsId(rs1.getString(34));
				businessOpportunity.setCrmPaymentCollectionPlanId(rs1.getString(35));
				businessOpportunity.setMechanismId(rs1.getString(36));
				businessOpportunity.setState(rs1.getString(37));
				businessOpportunityList.add(businessOpportunity);
			}
			contacts.setBusinessOpportunityList(businessOpportunityList);
			sql = "SELECT id,contract_title,customer_id,customer_name,business_opportunity_id,business_opportunity_name,contract_start_date,contract_end_date,total_contract_amount,signing_date,contract_status,contract_no,contract_type,contract_type_name,payment_type,payment_type_name,our_signatory,client_signatory,next_follow_time,contract_category,audit,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,crm_responsible_id,implementation,production_time,output,shipping_time,shipment_volume,settlement_time,settlement_volume,collection_time,collection_amount,contract_unsettled_quantity,unsettled_shipment_quantity,accounts_receivable,collaborator_name,mechanism_id,state FROM  crm_contract  where 1=1 and mechanism_id like ? and customer_id=? order by create_date desc ";
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, contacts.getMechanismId() + "%");
			ps2.setString(2, customerid);
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				Contract contract = new Contract();
				contract.setId(rs2.getString(1));
				contract.setContractTitle(rs2.getString(2));
				contract.setCustomerId(rs2.getString(3));
				contract.setCustomerName(rs2.getString(4));
				contract.setBusinessOpportunityId(rs2.getString(5));
				contract.setBusinessOpportunityName(rs2.getString(6));
				contract.setContractStartDate(rs2.getDate(7));
				contract.setContractEndDate(rs2.getDate(8));
				contract.setTotalContractAmount(rs2.getString(9));
				contract.setSigningDate(rs2.getDate(10));
				contract.setContractStatus(rs2.getString(11));
				contract.setContractNo(rs2.getString(12));
				contract.setContractType(rs2.getString(13));
				contract.setContractTypeName(rs2.getString(14));
				contract.setPaymentType(rs2.getString(15));
				contract.setPaymentTypeName(rs2.getString(16));
				contract.setOurSignatory(rs2.getString(17));
				contract.setClientSignatory(rs2.getString(18));
				contract.setNextFollowTime(rs2.getDate(19));
				contract.setContractCategory(rs2.getString(20));
				contract.setAudit(rs2.getString(21));
				contract.setPersonId(rs2.getString(22));
				contract.setPersonName(rs2.getString(23));
				contract.setDepartmentId(rs2.getString(24));
				contract.setDepartmentName(rs2.getString(25));
				contract.setRemarks(rs2.getString(26));
				contract.setCreateId(rs2.getString(27));
				contract.setCreateName(rs2.getString(28));
				contract.setCreateDate(rs2.getString(29));
				contract.setUpdateId(rs2.getString(30));
				contract.setUpdateName(rs2.getString(31));
				contract.setUpdateDate(rs2.getString(32));
				contract.setCrmResponsibleId(rs2.getString(33));
				contract.setImplementation(rs2.getString(34));
				contract.setProductionTime(rs2.getString(35));
				contract.setOutput(rs2.getString(36));
				contract.setShippingTime(rs2.getString(37));
				contract.setShipmentVolume(rs2.getString(38));
				contract.setSettlementTime(rs2.getString(39));
				contract.setSettlementVolume(rs2.getString(40));
				contract.setCollectionTime(rs2.getString(41));
				contract.setCollectionAmount(rs2.getString(42));
				contract.setContractUnsettledQuantity(rs2.getString(43));
				contract.setUnsettledShipmentQuantity(rs2.getString(44));
				contract.setAccountsReceivable(rs2.getString(45));
				contract.setCollaboratorName(rs2.getString(46));
				contract.setMechanismId(rs2.getString(47));
				contract.setState(rs2.getString(48));
				contractList.add(contract);
			}
			contacts.setContractList(contractList);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contacts> getItems(Contacts contacts)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return contacts;
	}

	@Override
	public int saveContacts(Contacts contacts) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String icontactsSQL = "INSERT INTO crm_contacts(id,name,customer_id,customer_name,position,telephone,phone,department_id,department_name,wxid,qq,wangwang,email,sex,birthday,country,province_id,province_name,city_id,city_name,area_id,area_name,address,zip_code,website,examine,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,person_id,customer_no)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icontactsSQL);
			ps.setString(1, contacts.getId());
			ps.setString(2, contacts.getName());
			if (contacts.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(36, null);
			} else {
				if (contacts.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(36, null);
				} else {
					String[] sjlx = contacts.getCustomerId().split("!_");
					ps.setString(3, sjlx[0]);
					ps.setString(4, sjlx[1]);
					ps.setString(36, sjlx[2]);
				}
			}
			ps.setString(5, contacts.getPosition());
			ps.setString(6, contacts.getTelephone());
			ps.setString(7, contacts.getPhone());
			ps.setString(8, contacts.getDepartmentId());
			ps.setString(9, contacts.getDepartmentName());
			ps.setString(10, contacts.getWxid());
			ps.setString(11, contacts.getQq());
			ps.setString(12, contacts.getWangwang());
			ps.setString(13, contacts.getEmail());
			ps.setString(14, contacts.getSex());
			ps.setString(15, contacts.getBirthday());
			ps.setString(16, contacts.getCountry());
			ps.setString(17, contacts.getProvinceId());
			ps.setString(18, contacts.getProvinceName());
			ps.setString(19, contacts.getCityId());
			ps.setString(20, contacts.getCityName());
			ps.setString(21, contacts.getAreaId());
			ps.setString(22, contacts.getAreaName());
			ps.setString(23, contacts.getAddress());
			ps.setString(24, contacts.getZipCode());
			ps.setString(25, contacts.getWebsite());
			ps.setString(26, contacts.getExamine());
			ps.setString(27, contacts.getRemarks());
			ps.setString(28, contacts.getCreateId());
			ps.setString(29, contacts.getCreateName());
			ps.setString(30, contacts.getCreateDate());
			ps.setString(31, contacts.getUpdateId());
			ps.setString(32, contacts.getUpdateName());
			ps.setString(33, contacts.getUpdateDate());
			ps.setString(34, contacts.getMechanismId());
			ps.setString(35, "");
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveContacts(Contacts contacts)" + (t2 - t1) + " ms");
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
	public int update(Contacts contacts) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ucontactsSQL = "update crm_contacts set person_id=?,name=?,customer_id=?,customer_name=?,position=?,telephone=?,phone=?,wxid=?,qq=?,wangwang=?,email=?,sex=?,birthday=?,country=?,province_id=?,province_name=?,city_id=?,city_name=?,area_id=?,area_name=?,address=?,zip_code=?,website=?,examine=?,remarks=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,mechanism_id=?,customer_no=? where 1=1 and id=? ";
			ps = conn.prepareStatement(ucontactsSQL);
			ps.setString(1, "");
			ps.setString(2, contacts.getName());
			if (contacts.getCustomerId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
				ps.setString(33, null);
			} else {
				if (contacts.getCustomerId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(33, null);
				} else {
					String[] sjlx = contacts.getCustomerId().split("!_");
					ps.setString(3, sjlx[0]);
					ps.setString(4, sjlx[1]);
					ps.setString(33, sjlx[2]);
				}
			}
			ps.setString(5, contacts.getPosition());
			ps.setString(6, contacts.getTelephone());
			ps.setString(7, contacts.getPhone());
			ps.setString(8, contacts.getWxid());
			ps.setString(9, contacts.getQq());
			ps.setString(10, contacts.getWangwang());
			ps.setString(11, contacts.getEmail());
			ps.setString(12, contacts.getSex());
			ps.setString(13, contacts.getBirthday());
			ps.setString(14, contacts.getCountry());
			ps.setString(15, contacts.getProvinceId());
			ps.setString(16, contacts.getProvinceName());
			ps.setString(17, contacts.getCityId());
			ps.setString(18, contacts.getCityName());
			ps.setString(19, contacts.getAreaId());
			ps.setString(20, contacts.getAreaName());
			ps.setString(21, contacts.getAddress());
			ps.setString(22, contacts.getZipCode());
			ps.setString(23, contacts.getWebsite());
			ps.setString(24, contacts.getExamine());
			ps.setString(25, contacts.getRemarks());
			ps.setString(26, contacts.getCreateId());
			ps.setString(27, contacts.getCreateName());
			ps.setString(28, contacts.getCreateDate());
			ps.setString(29, contacts.getUpdateId());
			ps.setString(30, contacts.getUpdateName());
			ps.setString(31, contacts.getUpdateDate());
			ps.setString(32, contacts.getMechanismId());
			ps.setString(34, contacts.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateContacts(contacts contacts)" + (t2 - t1) + " ms");
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

}