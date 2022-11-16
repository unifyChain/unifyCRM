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

import dafengqi.yunxiang.dao.ClueDao;
import dafengqi.yunxiang.model.Clue;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("clueDao")
public class ClueDaoHibernate extends GenericDaoHibernate<Clue, Long> implements ClueDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public ClueDaoHibernate() {
		super(Clue.class);
	}

	@Override
	public List<Clue> getItems(Clue clue) {
		long t1 = System.currentTimeMillis();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Clue> list = new ArrayList<Clue>();
		String username = clue.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			String zsql = "";
			if (clue.getFollowStatuss() != null) {
				if (clue.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + clue.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getDepartmentIds() != null) {
				if (!clue.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + clue.getDepartmentIds() + "'";
				}
			}
			if (clue.getPersonIds() != null) {
				if (clue.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getPersonIds().length; i++) {
						zsql += "  person_id='" + clue.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getCreateDate() == null) {
				if (clue.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getCreateDate().equals("")) {
					if (clue.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getCreateDateRange()) {
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
			if (clue.getNextFollowTimes() == null) {
				if (clue.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : clue.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getNextFollowTimes().equals("")) {
					if (clue.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : clue.getNextFollowTimeDateRange()) {
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
			if (clue.getUpdateDate() == null) {
				if (clue.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getUpdateDate().equals("")) {
					if (clue.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getUpdateDateRange()) {
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
			if (clue.getCreateIds() != null) {
				if (clue.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getCreateIds().length; i++) {
						zsql += "  create_id='" + clue.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getClueSources() != null) {
				if (clue.getClueSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getClueSources().length; i++) {
						zsql += "  clue_source='" + clue.getClueSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getFrom().equals("全部")) {
				sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? "
						+ zsql + " and (state!='xsc' or state is null) order by create_date desc ";

				ps = conn.prepareStatement(sql);
				ps.setString(1, clue.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					clue = new Clue();
					clue.setId(rs.getLong(1));
					clue.setTelephone(rs.getString(2));
					clue.setPhone(rs.getString(3));
					clue.setWxid(rs.getString(4));
					clue.setEmail(rs.getString(5));
					clue.setQq(rs.getString(6));
					clue.setCountry(rs.getString(7));
					clue.setProvinceId(rs.getString(8));
					clue.setProvinceName(rs.getString(9));
					clue.setCityId(rs.getString(10));
					clue.setCityName(rs.getString(11));
					clue.setAreaId(rs.getString(12));
					clue.setAreaName(rs.getString(13));
					clue.setZipCode(rs.getString(14));
					clue.setAddress(rs.getString(15));
					clue.setWangwang(rs.getString(16));
					clue.setWebsite(rs.getString(17));
					clue.setName(rs.getString(18));
					clue.setPosition(rs.getString(19));
					clue.setCorporateName(rs.getString(20));
					clue.setDepartment(rs.getString(21));
					clue.setMoney(rs.getString(22));
					clue.setNextFollowTime(rs.getDate(23));
					clue.setFollowStatus(rs.getString(24));
					clue.setClueSource(rs.getString(25));
					clue.setFollowStatusName(rs.getString(26));
					clue.setPersonId(rs.getString(27));
					clue.setPersonName(rs.getString(28));
					clue.setDepartmentId(rs.getString(29));
					clue.setDepartmentName(rs.getString(30));
					clue.setRemarks(rs.getString(31));
					clue.setCreateId(rs.getString(32));
					clue.setCreateName(rs.getString(33));
					clue.setCreateDate(rs.getString(34));
					clue.setUpdateId(rs.getString(35));
					clue.setUpdateName(rs.getString(36));
					clue.setUpdateDate(rs.getString(37));
					clue.setClueSourceName(rs.getString(38));
					clue.setMechanismId(rs.getString(39));
					clue.setCustomerId(rs.getString(40));
					list.add(clue);
				}
			} else {
				if (clue.getFrom().equals("本部")) {

					String[] xh = clue.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and department_id like ? "
									+ sql + " and (state!='xsc' or state is null) order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, clue.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								clue = new Clue();
								clue.setId(rs.getLong(1));
								clue.setTelephone(rs.getString(2));
								clue.setPhone(rs.getString(3));
								clue.setWxid(rs.getString(4));
								clue.setEmail(rs.getString(5));
								clue.setQq(rs.getString(6));
								clue.setCountry(rs.getString(7));
								clue.setProvinceId(rs.getString(8));
								clue.setProvinceName(rs.getString(9));
								clue.setCityId(rs.getString(10));
								clue.setCityName(rs.getString(11));
								clue.setAreaId(rs.getString(12));
								clue.setAreaName(rs.getString(13));
								clue.setZipCode(rs.getString(14));
								clue.setAddress(rs.getString(15));
								clue.setWangwang(rs.getString(16));
								clue.setWebsite(rs.getString(17));
								clue.setName(rs.getString(18));
								clue.setPosition(rs.getString(19));
								clue.setCorporateName(rs.getString(20));
								clue.setDepartment(rs.getString(21));
								clue.setMoney(rs.getString(22));
								clue.setNextFollowTime(rs.getDate(23));
								clue.setFollowStatus(rs.getString(24));
								clue.setClueSource(rs.getString(25));
								clue.setFollowStatusName(rs.getString(26));
								clue.setPersonId(rs.getString(27));
								clue.setPersonName(rs.getString(28));
								clue.setDepartmentId(rs.getString(29));
								clue.setDepartmentName(rs.getString(30));
								clue.setRemarks(rs.getString(31));
								clue.setCreateId(rs.getString(32));
								clue.setCreateName(rs.getString(33));
								clue.setCreateDate(rs.getString(34));
								clue.setUpdateId(rs.getString(35));
								clue.setUpdateName(rs.getString(36));
								clue.setUpdateDate(rs.getString(37));
								clue.setClueSourceName(rs.getString(38));
								clue.setMechanismId(rs.getString(39));
								clue.setCustomerId(rs.getString(40));
								list.add(clue);
							}
						}
					}
					sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and create_id=? "
							+ sql + " and (state!='xsc' or state is null) order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, clue.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						clue = new Clue();
						clue.setId(rs.getLong(1));
						clue.setTelephone(rs.getString(2));
						clue.setPhone(rs.getString(3));
						clue.setWxid(rs.getString(4));
						clue.setEmail(rs.getString(5));
						clue.setQq(rs.getString(6));
						clue.setCountry(rs.getString(7));
						clue.setProvinceId(rs.getString(8));
						clue.setProvinceName(rs.getString(9));
						clue.setCityId(rs.getString(10));
						clue.setCityName(rs.getString(11));
						clue.setAreaId(rs.getString(12));
						clue.setAreaName(rs.getString(13));
						clue.setZipCode(rs.getString(14));
						clue.setAddress(rs.getString(15));
						clue.setWangwang(rs.getString(16));
						clue.setWebsite(rs.getString(17));
						clue.setName(rs.getString(18));
						clue.setPosition(rs.getString(19));
						clue.setCorporateName(rs.getString(20));
						clue.setDepartment(rs.getString(21));
						clue.setMoney(rs.getString(22));
						clue.setNextFollowTime(rs.getDate(23));
						clue.setFollowStatus(rs.getString(24));
						clue.setClueSource(rs.getString(25));
						clue.setFollowStatusName(rs.getString(26));
						clue.setPersonId(rs.getString(27));
						clue.setPersonName(rs.getString(28));
						clue.setDepartmentId(rs.getString(29));
						clue.setDepartmentName(rs.getString(30));
						clue.setRemarks(rs.getString(31));
						clue.setCreateId(rs.getString(32));
						clue.setCreateName(rs.getString(33));
						clue.setCreateDate(rs.getString(34));
						clue.setUpdateId(rs.getString(35));
						clue.setUpdateName(rs.getString(36));
						clue.setUpdateDate(rs.getString(37));
						clue.setClueSourceName(rs.getString(38));
						clue.setMechanismId(rs.getString(39));
						clue.setCustomerId(rs.getString(40));
						list.add(clue);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (clue.getFrom().equals("未设")) {
					sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and create_id=? "
							+ sql + " and (state!='xsc' or state is null) order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, clue.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						clue = new Clue();
						clue.setId(rs.getLong(1));
						clue.setTelephone(rs.getString(2));
						clue.setPhone(rs.getString(3));
						clue.setWxid(rs.getString(4));
						clue.setEmail(rs.getString(5));
						clue.setQq(rs.getString(6));
						clue.setCountry(rs.getString(7));
						clue.setProvinceId(rs.getString(8));
						clue.setProvinceName(rs.getString(9));
						clue.setCityId(rs.getString(10));
						clue.setCityName(rs.getString(11));
						clue.setAreaId(rs.getString(12));
						clue.setAreaName(rs.getString(13));
						clue.setZipCode(rs.getString(14));
						clue.setAddress(rs.getString(15));
						clue.setWangwang(rs.getString(16));
						clue.setWebsite(rs.getString(17));
						clue.setName(rs.getString(18));
						clue.setPosition(rs.getString(19));
						clue.setCorporateName(rs.getString(20));
						clue.setDepartment(rs.getString(21));
						clue.setMoney(rs.getString(22));
						clue.setNextFollowTime(rs.getDate(23));
						clue.setFollowStatus(rs.getString(24));
						clue.setClueSource(rs.getString(25));
						clue.setFollowStatusName(rs.getString(26));
						clue.setPersonId(rs.getString(27));
						clue.setPersonName(rs.getString(28));
						clue.setDepartmentId(rs.getString(29));
						clue.setDepartmentName(rs.getString(30));
						clue.setRemarks(rs.getString(31));
						clue.setCreateId(rs.getString(32));
						clue.setCreateName(rs.getString(33));
						clue.setCreateDate(rs.getString(34));
						clue.setUpdateId(rs.getString(35));
						clue.setUpdateName(rs.getString(36));
						clue.setUpdateDate(rs.getString(37));
						clue.setClueSourceName(rs.getString(38));
						clue.setMechanismId(rs.getString(39));
						clue.setCustomerId(rs.getString(40));
						list.add(clue);
					}
				} else {

					String[] xh = clue.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and department_id = ? "
									+ sql + " and (state!='xsc' or state is null) order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, clue.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								clue = new Clue();
								clue.setId(rs.getLong(1));
								clue.setTelephone(rs.getString(2));
								clue.setPhone(rs.getString(3));
								clue.setWxid(rs.getString(4));
								clue.setEmail(rs.getString(5));
								clue.setQq(rs.getString(6));
								clue.setCountry(rs.getString(7));
								clue.setProvinceId(rs.getString(8));
								clue.setProvinceName(rs.getString(9));
								clue.setCityId(rs.getString(10));
								clue.setCityName(rs.getString(11));
								clue.setAreaId(rs.getString(12));
								clue.setAreaName(rs.getString(13));
								clue.setZipCode(rs.getString(14));
								clue.setAddress(rs.getString(15));
								clue.setWangwang(rs.getString(16));
								clue.setWebsite(rs.getString(17));
								clue.setName(rs.getString(18));
								clue.setPosition(rs.getString(19));
								clue.setCorporateName(rs.getString(20));
								clue.setDepartment(rs.getString(21));
								clue.setMoney(rs.getString(22));
								clue.setNextFollowTime(rs.getDate(23));
								clue.setFollowStatus(rs.getString(24));
								clue.setClueSource(rs.getString(25));
								clue.setFollowStatusName(rs.getString(26));
								clue.setPersonId(rs.getString(27));
								clue.setPersonName(rs.getString(28));
								clue.setDepartmentId(rs.getString(29));
								clue.setDepartmentName(rs.getString(30));
								clue.setRemarks(rs.getString(31));
								clue.setCreateId(rs.getString(32));
								clue.setCreateName(rs.getString(33));
								clue.setCreateDate(rs.getString(34));
								clue.setUpdateId(rs.getString(35));
								clue.setUpdateName(rs.getString(36));
								clue.setUpdateDate(rs.getString(37));
								clue.setClueSourceName(rs.getString(38));
								clue.setMechanismId(rs.getString(39));
								clue.setCustomerId(rs.getString(40));
								list.add(clue);
							}
						}
					}
					sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and create_id=? "
							+ sql + " and (state!='xsc' or state is null) order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, clue.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						clue = new Clue();
						clue.setId(rs.getLong(1));
						clue.setTelephone(rs.getString(2));
						clue.setPhone(rs.getString(3));
						clue.setWxid(rs.getString(4));
						clue.setEmail(rs.getString(5));
						clue.setQq(rs.getString(6));
						clue.setCountry(rs.getString(7));
						clue.setProvinceId(rs.getString(8));
						clue.setProvinceName(rs.getString(9));
						clue.setCityId(rs.getString(10));
						clue.setCityName(rs.getString(11));
						clue.setAreaId(rs.getString(12));
						clue.setAreaName(rs.getString(13));
						clue.setZipCode(rs.getString(14));
						clue.setAddress(rs.getString(15));
						clue.setWangwang(rs.getString(16));
						clue.setWebsite(rs.getString(17));
						clue.setName(rs.getString(18));
						clue.setPosition(rs.getString(19));
						clue.setCorporateName(rs.getString(20));
						clue.setDepartment(rs.getString(21));
						clue.setMoney(rs.getString(22));
						clue.setNextFollowTime(rs.getDate(23));
						clue.setFollowStatus(rs.getString(24));
						clue.setClueSource(rs.getString(25));
						clue.setFollowStatusName(rs.getString(26));
						clue.setPersonId(rs.getString(27));
						clue.setPersonName(rs.getString(28));
						clue.setDepartmentId(rs.getString(29));
						clue.setDepartmentName(rs.getString(30));
						clue.setRemarks(rs.getString(31));
						clue.setCreateId(rs.getString(32));
						clue.setCreateName(rs.getString(33));
						clue.setCreateDate(rs.getString(34));
						clue.setUpdateId(rs.getString(35));
						clue.setUpdateName(rs.getString(36));
						clue.setUpdateDate(rs.getString(37));
						clue.setClueSourceName(rs.getString(38));
						clue.setMechanismId(rs.getString(39));
						clue.setCustomerId(rs.getString(40));
						list.add(clue);
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
			System.out.println("List<Clue> getItems(Clue clue)" + (t2 - t1) + " ms");
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
	public List<Clue> getItemsOfMy(Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Clue> list = new ArrayList<Clue>();
		String username = clue.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";
			if (clue.getFollowStatuss() != null) {
				if (clue.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + clue.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getCreateDate() == null) {
				if (clue.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getCreateDate().equals("")) {
					if (clue.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getCreateDateRange()) {
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
			if (clue.getNextFollowTimes() == null) {
				if (clue.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : clue.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getNextFollowTimes().equals("")) {
					if (clue.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : clue.getNextFollowTimeDateRange()) {
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
			if (clue.getUpdateDate() == null) {
				if (clue.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getUpdateDate().equals("")) {
					if (clue.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getUpdateDateRange()) {
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
			if (clue.getCreateIds() != null) {
				if (clue.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getCreateIds().length; i++) {
						zsql += "  create_id='" + clue.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getClueSources() != null) {
				if (clue.getClueSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getClueSources().length; i++) {
						zsql += "  clue_source='" + clue.getClueSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
			sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and create_id=? "
					+ zsql + "  and (state!='xsc' or state is null) order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, clue.getMechanismId() + "%");
			ps.setString(2, clue.getCreateName());
			rs = ps.executeQuery();
			while (rs.next()) {
				clue = new Clue();
				clue.setId(rs.getLong(1));
				clue.setTelephone(rs.getString(2));
				clue.setPhone(rs.getString(3));
				clue.setWxid(rs.getString(4));
				clue.setEmail(rs.getString(5));
				clue.setQq(rs.getString(6));
				clue.setCountry(rs.getString(7));
				clue.setProvinceId(rs.getString(8));
				clue.setProvinceName(rs.getString(9));
				clue.setCityId(rs.getString(10));
				clue.setCityName(rs.getString(11));
				clue.setAreaId(rs.getString(12));
				clue.setAreaName(rs.getString(13));
				clue.setZipCode(rs.getString(14));
				clue.setAddress(rs.getString(15));
				clue.setWangwang(rs.getString(16));
				clue.setWebsite(rs.getString(17));
				clue.setName(rs.getString(18));
				clue.setPosition(rs.getString(19));
				clue.setCorporateName(rs.getString(20));
				clue.setDepartment(rs.getString(21));
				clue.setMoney(rs.getString(22));
				clue.setNextFollowTime(rs.getDate(23));
				clue.setFollowStatus(rs.getString(24));
				clue.setClueSource(rs.getString(25));
				clue.setFollowStatusName(rs.getString(26));
				clue.setPersonId(rs.getString(27));
				clue.setPersonName(rs.getString(28));
				clue.setDepartmentId(rs.getString(29));
				clue.setDepartmentName(rs.getString(30));
				clue.setRemarks(rs.getString(31));
				clue.setCreateId(rs.getString(32));
				clue.setCreateName(rs.getString(33));
				clue.setCreateDate(rs.getString(34));
				clue.setUpdateId(rs.getString(35));
				clue.setUpdateName(rs.getString(36));
				clue.setUpdateDate(rs.getString(37));
				clue.setClueSourceName(rs.getString(38));
				clue.setMechanismId(rs.getString(39));
				clue.setCustomerId(rs.getString(40));
				list.add(clue);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Clue> getItemsOfMy(Clue clue) {" + (t2 - t1) + " ms");
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
	public List<Clue> getCluePool(Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Clue> list = new ArrayList<Clue>();
		String username = clue.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";
			if (clue.getFollowStatuss() != null) {
				if (clue.getFollowStatuss().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getFollowStatuss().length; i++) {
						zsql += "  follow_status='" + clue.getFollowStatuss()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getCreateDate() == null) {
				if (clue.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getCreateDate().equals("")) {
					if (clue.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getCreateDateRange()) {
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
			if (clue.getNextFollowTimes() == null) {
				if (clue.getNextFollowTimeDateRange() != null) {
					int i = 0;
					for (Date t : clue.getNextFollowTimeDateRange()) {
						if (i == 0) {
							zsql += " and next_follow_time>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and next_follow_time<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getNextFollowTimes().equals("")) {
					if (clue.getNextFollowTimes().equals("今日")) {
						zsql += " and next_follow_time>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本周")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本月")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getNextFollowTimes().equals("本季")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getNextFollowTimes().equals("本年")) {
						zsql += " and next_follow_time>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and next_follow_time<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getNextFollowTimeDateRange() != null) {
						int i = 0;
						for (Date t : clue.getNextFollowTimeDateRange()) {
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
			if (clue.getUpdateDate() == null) {
				if (clue.getUpdateDateRange() != null) {
					int i = 0;
					for (Date t : clue.getUpdateDateRange()) {
						if (i == 0) {
							zsql += " and update_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and update_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!clue.getUpdateDate().equals("")) {
					if (clue.getUpdateDate().equals("今日")) {
						zsql += " and update_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本周")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本月")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (clue.getUpdateDate().equals("本季")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and update_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (clue.getUpdateDate().equals("本年")) {
						zsql += " and update_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and update_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (clue.getUpdateDateRange() != null) {
						int i = 0;
						for (Date t : clue.getUpdateDateRange()) {
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
			if (clue.getCreateIds() != null) {
				if (clue.getCreateIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getCreateIds().length; i++) {
						zsql += "  create_id='" + clue.getCreateIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (clue.getClueSources() != null) {
				if (clue.getClueSources().length != 0) {

					zsql += " and (";
					for (int i = 0; i < clue.getClueSources().length; i++) {
						zsql += "  clue_source='" + clue.getClueSources()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
			sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and mechanism_id like ? and state=? "
					+ zsql + " order by create_date desc ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, clue.getMechanismId() + "%");
			ps.setString(2, "xsc");
			rs = ps.executeQuery();
			while (rs.next()) {
				clue = new Clue();
				clue.setId(rs.getLong(1));
				clue.setTelephone(rs.getString(2));
				clue.setPhone(rs.getString(3));
				clue.setWxid(rs.getString(4));
				clue.setEmail(rs.getString(5));
				clue.setQq(rs.getString(6));
				clue.setCountry(rs.getString(7));
				clue.setProvinceId(rs.getString(8));
				clue.setProvinceName(rs.getString(9));
				clue.setCityId(rs.getString(10));
				clue.setCityName(rs.getString(11));
				clue.setAreaId(rs.getString(12));
				clue.setAreaName(rs.getString(13));
				clue.setZipCode(rs.getString(14));
				clue.setAddress(rs.getString(15));
				clue.setWangwang(rs.getString(16));
				clue.setWebsite(rs.getString(17));
				clue.setName(rs.getString(18));
				clue.setPosition(rs.getString(19));
				clue.setCorporateName(rs.getString(20));
				clue.setDepartment(rs.getString(21));
				clue.setMoney(rs.getString(22));
				clue.setNextFollowTime(rs.getDate(23));
				clue.setFollowStatus(rs.getString(24));
				clue.setClueSource(rs.getString(25));
				clue.setFollowStatusName(rs.getString(26));
				clue.setPersonId(rs.getString(27));
				clue.setPersonName(rs.getString(28));
				clue.setDepartmentId(rs.getString(29));
				clue.setDepartmentName(rs.getString(30));
				clue.setRemarks(rs.getString(31));
				clue.setCreateId(rs.getString(32));
				clue.setCreateName(rs.getString(33));
				clue.setCreateDate(rs.getString(34));
				clue.setUpdateId(rs.getString(35));
				clue.setUpdateName(rs.getString(36));
				clue.setUpdateDate(rs.getString(37));
				clue.setClueSourceName(rs.getString(38));
				clue.setMechanismId(rs.getString(39));
				clue.setCustomerId(rs.getString(40));
				list.add(clue);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Clue> getCluePool(Clue clue) " + (t2 - t1) + " ms");
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
	public Clue edit(Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Clue> list = new ArrayList<Clue>();
		List<FollowUp> listFollowUp = new ArrayList<FollowUp>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id FROM  crm_clue    where 1=1 and id=? and mechanism_id like ? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, clue.getId());
			ps.setString(2, clue.getMechanismId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				clue = new Clue();
				clue.setId(rs.getLong(1));
				clue.setTelephone(rs.getString(2));
				clue.setPhone(rs.getString(3));
				clue.setWxid(rs.getString(4));
				clue.setEmail(rs.getString(5));
				clue.setQq(rs.getString(6));
				clue.setCountry(rs.getString(7));
				clue.setProvinceId(rs.getString(8));
				clue.setProvinceName(rs.getString(9));
				clue.setCityId(rs.getString(10));
				clue.setCityName(rs.getString(11));
				clue.setAreaId(rs.getString(12));
				clue.setAreaName(rs.getString(13));
				clue.setZipCode(rs.getString(14));
				clue.setAddress(rs.getString(15));
				clue.setWangwang(rs.getString(16));
				clue.setWebsite(rs.getString(17));
				clue.setName(rs.getString(18));
				clue.setPosition(rs.getString(19));
				clue.setCorporateName(rs.getString(20));
				clue.setDepartment(rs.getString(21));
				clue.setMoney(rs.getString(22));
				clue.setNextFollowTime(rs.getDate(23));
				clue.setFollowStatus(rs.getString(24) + "!_" + rs.getString(26));
				clue.setClueSource(rs.getString(25) + "!_" + rs.getString(38));
				clue.setFollowStatusName(rs.getString(26));
				clue.setPersonId(rs.getString(27) + "!_" + rs.getString(28));
				clue.setPersonName(rs.getString(28));
				clue.setDepartmentId(rs.getString(29) + "!_" + rs.getString(30));
				clue.setDepartmentName(rs.getString(30));
				clue.setRemarks(rs.getString(31));
				clue.setCreateId(rs.getString(32));
				clue.setCreateName(rs.getString(33));
				clue.setCreateDate(rs.getString(34));
				clue.setUpdateId(rs.getString(35));
				clue.setUpdateName(rs.getString(36));
				clue.setUpdateDate(rs.getString(37));
				clue.setClueSourceName(rs.getString(38));
				clue.setMechanismId(rs.getString(39));
				clue.setCustomerId(rs.getString(40));
			}
			sql = "SELECT id,follow_up,time,content,follow_id,follow_mc,follow_status,contacts_id,contacts_mc,next_follow_time,create_id,update_name,create_date,update_id,update_name,update_date,mechanism_id,froms FROM  crm_follow_up where 1=1    and mechanism_id like'"
					+ clue.getMechanismId() + "%' and froms='线索' and follow_id='" + clue.getId() + "'";
			ps1 = conn.prepareStatement(sql);
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				FollowUp followup = new FollowUp();
				followup.setId(rs1.getString(1));
				followup.setFollowUp(rs1.getString(2));
				followup.setTime(rs1.getString(3));
				followup.setContent(rs1.getString(4));
				followup.setFollowId(rs1.getString(5));
				followup.setFollowName(rs1.getString(6));
				followup.setFollowStatus(rs1.getString(7));
				followup.setContactsId(rs1.getString(8));
				followup.setContactsName(rs1.getString(9));
				followup.setNextFollowTime(rs1.getString(10));
				followup.setCreateId(rs1.getString(11));
				followup.setCreateName(rs1.getString(12));
				followup.setCreateDate(rs1.getString(13));
				followup.setUpdateId(rs1.getString(14));
				followup.setUpdateName(rs1.getString(15));
				followup.setUpdateDate(rs1.getString(16));
				followup.setMechanismId(rs1.getString(17));
				followup.setFrom(rs1.getString(18));
				listFollowUp.add(followup);
			}
			clue.setFollowUpList(listFollowUp);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Clue edit(Clue clue)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return clue;
	}

	@Override
	public int saveClue(Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String iclueSQL = "INSERT INTO crm_clue(telephone,phone,wxid,email,qq,country,province_id,province_name,city_id,city_name,area_id,area_name,zip_code,address,wangwang,website,name,position,corporate_name,department,money,next_follow_time,follow_status,clue_source,follow_status_name,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,clue_source_name,mechanism_id,customer_id,app_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iclueSQL);

			ps.setString(1, clue.getTelephone());
			ps.setString(2, clue.getPhone());
			ps.setString(3, clue.getWxid());
			ps.setString(4, clue.getEmail());
			ps.setString(5, clue.getQq());
			ps.setString(6, clue.getCountry());
			ps.setString(7, clue.getProvinceId());
			ps.setString(8, clue.getProvinceName());
			ps.setString(9, clue.getCityId());
			ps.setString(10, clue.getCityName());
			ps.setString(11, clue.getAreaId());
			ps.setString(12, clue.getAreaName());
			ps.setString(13, clue.getZipCode());
			ps.setString(14, clue.getAddress());
			ps.setString(15, clue.getWangwang());
			ps.setString(16, clue.getWebsite());
			ps.setString(17, clue.getName());
			ps.setString(18, clue.getPosition());
			ps.setString(19, clue.getCorporateName());
			ps.setString(20, clue.getDepartment());
			ps.setString(21, clue.getMoney());
			ps.setString(22, df.format(clue.getNextFollowTime()));

			if (clue.getFollowStatus() == null) {
				ps.setString(23, "");
				ps.setString(25, "");
			} else {
				if (clue.getFollowStatus().equals("")) {
					ps.setString(23, "");
					ps.setString(25, "");
				} else {
					String[] sjlx = clue.getFollowStatus().split("!_");
					ps.setString(23, sjlx[0]);
					ps.setString(25, sjlx[1]);
				}
			}
			if (clue.getClueSource() == null) {
				ps.setString(24, "");
				ps.setString(37, "");
			} else {
				if (clue.getClueSource().equals("")) {
					ps.setString(24, "");
					ps.setString(37, "");
				} else {
					String[] sjlx = clue.getClueSource().split("!_");
					ps.setString(24, sjlx[0]);
					ps.setString(37, sjlx[1]);
				}
			}
			if (clue.getPersonId() == null) {
				ps.setString(26, "");
				ps.setString(27, "");
			} else {
				if (clue.getPersonId().equals("")) {
					ps.setString(26, "");
					ps.setString(27, "");
				} else {
					String[] sjlx = clue.getPersonId().split("!_");
					ps.setString(26, sjlx[0]);
					ps.setString(27, sjlx[1]);
				}
			}
			if (clue.getDepartmentId() == null) {
				ps.setString(28, "");
				ps.setString(29, "");
			} else {
				if (clue.getDepartmentId().equals("")) {
					ps.setString(28, "");
					ps.setString(29, "");
				} else {
					String[] sjlx = clue.getDepartmentId().split("!_");
					ps.setString(28, sjlx[0]);
					ps.setString(29, sjlx[1]);
				}
			}
			ps.setString(30, clue.getRemarks());
			ps.setString(31, clue.getCreateId());
			ps.setString(32, clue.getCreateName());
			ps.setString(33, clue.getCreateDate());
			ps.setString(34, clue.getUpdateId());
			ps.setString(35, clue.getUpdateName());
			ps.setString(36, clue.getUpdateDate());
			ps.setString(38, clue.getMechanismId());
			ps.setString(39, clue.getCustomerId());
			ps.setString(40, clue.getAppId());

			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveClue(Clue clue)" + (t2 - t1) + " ms");
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
	public int rhighseas(String table, String id, String status, String mechanismId) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set state=? where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, status);
			ps.setString(2, id);
			ps.setString(3, mechanismId);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveClue(Clue clue)" + (t2 - t1) + " ms");
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
	public int rhighseas(String table, String id, String status, Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set state=?,person_id='" + clue.getPersonId() + "',person_name='"
					+ clue.getPersonName() + "',department_id='" + clue.getDepartmentId() + "',department_name='"
					+ clue.getDepartmentName() + "',create_id='" + clue.getCreateId() + "',create_name='"
					+ clue.getCreateName() + "',create_date='" + df.format(new Date())
					+ "',update_id='',update_name='',update_date='' where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, status);
			ps.setString(2, id);
			ps.setString(3, clue.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveClue(Clue clue)" + (t2 - t1) + " ms");
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
	public int update(Clue clue) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uclueSQL = "update crm_clue set customer_id=?,telephone=?,phone=?,wxid=?,email=?,qq=?,country=?,province_id=?,province_name=?,city_id=?,city_name=?,area_id=?,area_name=?,zip_code=?,address=?,wangwang=?,website=?,name=?,position=?,corporate_name=?,department=?,money=?,next_follow_time=?,follow_status=?,clue_source=?,follow_status_name=?,person_id=?,person_name=?,department_id=?,department_name=?,remarks=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,clue_source_name=?,mechanism_id=? where 1=1 and id=? and mechanism_id=? ";
			ps = conn.prepareStatement(uclueSQL);
			ps.setString(1, clue.getCustomerId());
			ps.setString(2, clue.getTelephone());
			ps.setString(3, clue.getPhone());
			ps.setString(4, clue.getWxid());
			ps.setString(5, clue.getEmail());
			ps.setString(6, clue.getQq());
			ps.setString(7, clue.getCountry());
			ps.setString(8, clue.getProvinceId());
			ps.setString(9, clue.getProvinceName());
			ps.setString(10, clue.getCityId());
			ps.setString(11, clue.getCityName());
			ps.setString(12, clue.getAreaId());
			ps.setString(13, clue.getAreaName());
			ps.setString(14, clue.getZipCode());
			ps.setString(15, clue.getAddress());
			ps.setString(16, clue.getWangwang());
			ps.setString(17, clue.getWebsite());
			ps.setString(18, clue.getName());
			ps.setString(19, clue.getPosition());
			ps.setString(20, clue.getCorporateName());
			ps.setString(21, clue.getDepartment());
			ps.setString(22, clue.getMoney());
			ps.setString(23, df.format(clue.getNextFollowTime()));

			if (clue.getFollowStatus() == null) {
				ps.setString(24, "");
				ps.setString(26, "");
			} else {
				if (clue.getFollowStatus().equals("")) {
					ps.setString(24, "");
					ps.setString(26, "");
				} else {
					String[] sjlx = clue.getFollowStatus().split("!_");
					ps.setString(24, sjlx[0]);
					ps.setString(26, sjlx[1]);
				}
			}
			if (clue.getClueSource() == null) {
				ps.setString(25, "");
				ps.setString(38, "");
			} else {
				if (clue.getClueSource().equals("")) {
					ps.setString(25, "");
					ps.setString(38, "");
				} else {
					String[] sjlx = clue.getClueSource().split("!_");
					ps.setString(25, sjlx[0]);
					ps.setString(38, sjlx[1]);
				}
			}
			if (clue.getPersonId() == null) {
				ps.setString(27, "");
				ps.setString(28, "");
			} else {
				if (clue.getPersonId().equals("")) {
					ps.setString(27, "");
					ps.setString(28, "");
				} else {
					String[] sjlx = clue.getPersonId().split("!_");
					ps.setString(27, sjlx[0]);
					ps.setString(28, sjlx[1]);
				}
			}
			if (clue.getDepartmentId() == null) {
				ps.setString(29, "");
				ps.setString(30, "");
			} else {
				if (clue.getDepartmentId().equals("")) {
					ps.setString(29, "");
					ps.setString(30, "");
				} else {
					String[] sjlx = clue.getDepartmentId().split("!_");
					ps.setString(29, sjlx[0]);
					ps.setString(30, sjlx[1]);
				}
			}
			ps.setString(31, clue.getRemarks());
			ps.setString(32, clue.getCreateId());
			ps.setString(33, clue.getCreateName());
			ps.setString(34, clue.getCreateDate());
			ps.setString(35, clue.getUpdateId());
			ps.setString(36, clue.getUpdateName());
			ps.setString(37, clue.getUpdateDate());
			ps.setString(39, clue.getMechanismId());
			ps.setLong(40, clue.getId());
			ps.setString(41, clue.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int update(clue clue)" + (t2 - t1) + " ms");
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