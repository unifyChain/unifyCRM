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

import dafengqi.yunxiang.dao.CostDao;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("costDao")
public class CostDaoHibernate extends GenericDaoHibernate<Cost, Long> implements CostDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public CostDaoHibernate() {
		super(Cost.class);
	}

	@Override
	public List<Cost> getItems(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Cost> list = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (cost.getExpenseTypes() != null) {
				if (cost.getExpenseTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getExpenseTypes().length; i++) {
						zsql += "  expense_type='" + cost.getExpenseTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (cost.getTimeOfOccurrences() == null) {
				if (cost.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : cost.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and time_of_occurrence>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and time_of_occurrence<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!cost.getTimeOfOccurrences().equals("")) {
					if (cost.getTimeOfOccurrences().equals("今日")) {
						zsql += " and time_of_occurrence>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (cost.getTimeOfOccurrences().equals("本周")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (cost.getTimeOfOccurrences().equals("本月")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (cost.getTimeOfOccurrences().equals("本季")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (cost.getTimeOfOccurrences().equals("本年")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (cost.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : cost.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and time_of_occurrence>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and time_of_occurrence<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (cost.getPersonIds() != null) {
				if (cost.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getPersonIds().length; i++) {
						zsql += "  person_id='" + cost.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (cost.getCustomerIds() != null) {
				if (cost.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + cost.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
			sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cost.getMechanismId());
			rs = ps.executeQuery();
			while (rs.next()) {
				cost = new Cost();
				cost.setId(rs.getString(1));
				cost.setExpenseType(rs.getString(2));
				cost.setExpenseTypeName(rs.getString(3));
				cost.setCostDescription(rs.getString(4));
				cost.setExpenseAmount(rs.getString(5));
				cost.setTimeOfOccurrence(rs.getDate(6));
				cost.setCustomerId(rs.getString(7));
				cost.setCustomerName(rs.getString(8));
				cost.setContactsId(rs.getString(9));
				cost.setContactsName(rs.getString(10));
				cost.setBusinessId(rs.getString(11));
				cost.setBusinessName(rs.getString(12));
				cost.setContractId(rs.getString(13));
				cost.setContractName(rs.getString(14));
				cost.setFollowUpRecord(rs.getString(15));
				cost.setFollowUpRecordName(rs.getString(16));
				cost.setVisitSignIn(rs.getString(17));
				cost.setEnclosure(rs.getString(18));
				cost.setPersonId(rs.getString(19));
				cost.setPersonName(rs.getString(20));
				cost.setCreateId(rs.getString(21));
				cost.setCreateName(rs.getString(22));
				cost.setCreateDate(rs.getString(23));
				cost.setUpdateId(rs.getString(24));
				cost.setUpdateName(rs.getString(25));
				cost.setUpdateDate(rs.getString(26));
				cost.setMechanismId(rs.getString(27));
				list.add(cost);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Cost> getItems(Cost cost)" + (t2 - t1) + " ms");
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
	public List<Cost> getItemsOfMy(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Cost> list = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (cost.getExpenseTypes() != null) {
				if (cost.getExpenseTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getExpenseTypes().length; i++) {
						zsql += "  expense_type='" + cost.getExpenseTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (cost.getTimeOfOccurrences() == null) {
				if (cost.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : cost.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and time_of_occurrence>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and time_of_occurrence<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!cost.getTimeOfOccurrences().equals("")) {
					if (cost.getTimeOfOccurrences().equals("今日")) {
						zsql += " and time_of_occurrence>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (cost.getTimeOfOccurrences().equals("本周")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (cost.getTimeOfOccurrences().equals("本月")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (cost.getTimeOfOccurrences().equals("本季")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (cost.getTimeOfOccurrences().equals("本年")) {
						zsql += " and time_of_occurrence>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and time_of_occurrence<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (cost.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : cost.getCreateDateRange()) {
							if (i == 0) {
								zsql += " and time_of_occurrence>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and time_of_occurrence<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (cost.getPersonIds() != null) {
				if (cost.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getPersonIds().length; i++) {
						zsql += "  person_id='" + cost.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (cost.getCustomerIds() != null) {
				if (cost.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < cost.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + cost.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
//				if(cost.getKhlx().equals("")){
			sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? "
					+ zsql + " order by create_date desc ";
//				}else if(cost.getKhlx().equals("my")){
//					sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and mechanism_id=? and create_id=? order by create_date desc ";
//				}
			ps = conn.prepareStatement(sql);
			ps.setString(1, cost.getMechanismId());
//				}else if(cost.getKhlx().equals("my")){
//					ps.setString(1, cost.getMechanismId());
//					ps.setString(2, cost.getCreateName());
//				}
//			}
			rs = ps.executeQuery();
			while (rs.next()) {
				cost = new Cost();
				cost.setId(rs.getString(1));
				cost.setExpenseType(rs.getString(2));
				cost.setExpenseTypeName(rs.getString(3));
				cost.setCostDescription(rs.getString(4));
				cost.setExpenseAmount(rs.getString(5));
				cost.setTimeOfOccurrence(rs.getDate(6));
				cost.setCustomerId(rs.getString(7));
				cost.setCustomerName(rs.getString(8));
				cost.setContactsId(rs.getString(9));
				cost.setContactsName(rs.getString(10));
				cost.setBusinessId(rs.getString(11));
				cost.setBusinessName(rs.getString(12));
				cost.setContractId(rs.getString(13));
				cost.setContractName(rs.getString(14));
				cost.setFollowUpRecord(rs.getString(15));
				cost.setFollowUpRecordName(rs.getString(16));
				cost.setVisitSignIn(rs.getString(17));
				cost.setEnclosure(rs.getString(18));
				cost.setPersonId(rs.getString(19));
				cost.setPersonName(rs.getString(20));
				cost.setCreateId(rs.getString(21));
				cost.setCreateName(rs.getString(22));
				cost.setCreateDate(rs.getString(23));
				cost.setUpdateId(rs.getString(24));
				cost.setUpdateName(rs.getString(25));
				cost.setUpdateDate(rs.getString(26));
				cost.setMechanismId(rs.getString(27));
				list.add(cost);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Cost> getItems(Cost cost)" + (t2 - t1) + " ms");
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
	public Cost edit(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Cost> list = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cost.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				cost = new Cost();
				cost.setId(rs.getString(1));
				cost.setExpenseType(rs.getString(2) + "!_" + rs.getString(3));
				cost.setExpenseTypeName(rs.getString(3));
				cost.setCostDescription(rs.getString(4));
				cost.setExpenseAmount(rs.getString(5));
				cost.setTimeOfOccurrence(rs.getDate(6));
				cost.setCustomerId(rs.getString(7) + "!_" + rs.getString(8));
				cost.setCustomerName(rs.getString(8));
				cost.setContactsId(rs.getString(9) + "!_" + rs.getString(10));
				cost.setContactsName(rs.getString(10));
				cost.setBusinessId(rs.getString(11) + "!_" + rs.getString(12));
				cost.setBusinessName(rs.getString(12));
				cost.setContractId(rs.getString(13) + "!_" + rs.getString(14));
				cost.setContractName(rs.getString(14));
				cost.setFollowUpRecord(rs.getString(15) + "!_" + rs.getString(16));
				cost.setFollowUpRecordName(rs.getString(16));
				cost.setVisitSignIn(rs.getString(17));
				cost.setEnclosure(rs.getString(18));
				cost.setPersonId(rs.getString(19) + "!_" + rs.getString(20));
				cost.setPersonName(rs.getString(20));
				cost.setCreateId(rs.getString(21));
				cost.setCreateName(rs.getString(22));
				cost.setCreateDate(rs.getString(23));
				cost.setUpdateId(rs.getString(24));
				cost.setUpdateName(rs.getString(25));
				cost.setUpdateDate(rs.getString(26));
				cost.setMechanismId(rs.getString(27));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Cost> getItems(Cost cost)" + (t2 - t1) + " ms");
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
		return cost;
	}

	@Override
	public int saveCost(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String icostSQL = "INSERT INTO crm_cost(id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,state)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icostSQL);

			ps.setString(1, cost.getId());
			if (cost.getExpenseType() == null) {
				ps.setString(2, "");
				ps.setString(3, "");
			} else {
				if (cost.getExpenseType().equals("")) {
					ps.setString(2, "");
					ps.setString(3, "");
				} else {
					String[] sjlx = cost.getExpenseType().split("!_");
					ps.setString(2, sjlx[0]);
					ps.setString(3, sjlx[1]);
				}
			}
			ps.setString(4, cost.getCostDescription());
			ps.setString(5, cost.getExpenseAmount());
			ps.setString(6, df.format(cost.getTimeOfOccurrence()));
			if (cost.getCustomerId() == null) {
				ps.setString(7, "");
				ps.setString(8, "");
			} else {
				if (cost.getCustomerId().equals("")) {
					ps.setString(7, "");
					ps.setString(8, "");
				} else {
					String[] sjlx = cost.getCustomerId().split("!_");
					ps.setString(7, sjlx[0]);
					ps.setString(8, sjlx[1]);
				}
			}
			if (cost.getContactsId() == null) {
				ps.setString(9, "");
				ps.setString(10, "");
			} else {
				if (cost.getContactsId().equals("")) {
					ps.setString(9, "");
					ps.setString(10, "");
				} else {
					String[] sjlx = cost.getContactsId().split("!_");
					ps.setString(9, sjlx[0]);
					ps.setString(10, sjlx[1]);
				}
			}
			if (cost.getBusinessId() == null) {
				ps.setString(11, "");
				ps.setString(12, "");
			} else {
				if (cost.getBusinessId().equals("")) {
					ps.setString(11, "");
					ps.setString(12, "");
				} else {
					String[] sjlx = cost.getBusinessId().split("!_");
					ps.setString(11, sjlx[0]);
					ps.setString(12, sjlx[1]);
				}
			}
			if (cost.getContractId() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (cost.getContractId().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] sjlx = cost.getContractId().split("!_");
					ps.setString(13, sjlx[0]);
					ps.setString(14, sjlx[1]);
				}
			}
			if (cost.getFollowUpRecord() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (cost.getFollowUpRecord().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					ps.setString(15, "");
					ps.setString(16, "");
				}
			}
			ps.setString(17, cost.getVisitSignIn());
			ps.setString(18, cost.getEnclosure());
			if (cost.getPersonId() == null) {
				ps.setString(19, "");
				ps.setString(20, "");
			} else {
				if (cost.getPersonId().equals("")) {
					ps.setString(19, "");
					ps.setString(20, "");
				} else {
					String[] sjlx = cost.getPersonId().split("!_");
					ps.setString(19, sjlx[0]);
					ps.setString(20, sjlx[1]);
				}
			}
			ps.setString(21, cost.getCreateId());
			ps.setString(22, cost.getCreateName());
			ps.setString(23, cost.getCreateDate());
			ps.setString(24, cost.getUpdateId());
			ps.setString(25, cost.getUpdateName());
			ps.setString(26, cost.getUpdateDate());
			ps.setString(27, cost.getMechanismId());
			ps.setString(28, "未报");
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveCost(Cost cost)" + (t2 - t1) + " ms");
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
	public int update(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ucostSQL = "update crm_cost set mechanism_id=?,expense_type=?,expense_type_name=?,cost_description=?,expense_amount=?,time_of_occurrence=?,customer_id=?,customer_name=?,contacts_id=?,contacts_name=?,business_id=?,business_name=?,contract_id=?,contract_name=?,follow_up_record=?,follow_up_record_name=?,visit_sign_in=?,enclosure=?,person_id=?,person_name=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=? where 1=1 and id=? ";
			ps = conn.prepareStatement(ucostSQL);
			ps.setString(1, cost.getMechanismId());
			if (cost.getExpenseType() == null) {
				ps.setString(2, "");
				ps.setString(3, "");
			} else {
				if (cost.getExpenseType().equals("")) {
					ps.setString(2, "");
					ps.setString(3, "");
				} else {
					String[] sjlx = cost.getExpenseType().split("!_");
					ps.setString(2, sjlx[0]);
					ps.setString(3, sjlx[1]);
				}
			}
			ps.setString(4, cost.getCostDescription());
			ps.setString(5, cost.getExpenseAmount());
			ps.setString(6, df.format(cost.getTimeOfOccurrence()));
			if (cost.getCustomerId() == null) {
				ps.setString(7, "");
				ps.setString(8, "");
			} else {
				if (cost.getCustomerId().equals("")) {
					ps.setString(7, "");
					ps.setString(8, "");
				} else {
					String[] sjlx = cost.getCustomerId().split("!_");
					ps.setString(7, sjlx[0]);
					ps.setString(8, sjlx[1]);
				}
			}
			if (cost.getContactsId() == null) {
				ps.setString(9, "");
				ps.setString(10, "");
			} else {
				if (cost.getContactsId().equals("")) {
					ps.setString(9, "");
					ps.setString(10, "");
				} else {
					String[] sjlx = cost.getContactsId().split("!_");
					ps.setString(9, sjlx[0]);
					ps.setString(10, sjlx[1]);
				}
			}
			if (cost.getBusinessId() == null) {
				ps.setString(11, "");
				ps.setString(12, "");
			} else {
				if (cost.getBusinessId().equals("")) {
					ps.setString(11, "");
					ps.setString(12, "");
				} else {
					String[] sjlx = cost.getBusinessId().split("!_");
					ps.setString(11, sjlx[0]);
					ps.setString(12, sjlx[1]);
				}
			}
			if (cost.getContractId() == null) {
				ps.setString(13, "");
				ps.setString(14, "");
			} else {
				if (cost.getContractId().equals("")) {
					ps.setString(13, "");
					ps.setString(14, "");
				} else {
					String[] sjlx = cost.getContractId().split("!_");
					ps.setString(13, sjlx[0]);
					ps.setString(14, sjlx[1]);
				}
			}
			if (cost.getFollowUpRecord() == null) {
				ps.setString(15, "");
				ps.setString(16, "");
			} else {
				if (cost.getFollowUpRecord().equals("")) {
					ps.setString(15, "");
					ps.setString(16, "");
				} else {
					ps.setString(15, "");
					ps.setString(16, "");
				}
			}
			ps.setString(17, cost.getVisitSignIn());
			ps.setString(18, cost.getEnclosure());
			if (cost.getPersonId() == null) {
				ps.setString(19, "");
				ps.setString(20, "");
			} else {
				if (cost.getPersonId().equals("")) {
					ps.setString(19, "");
					ps.setString(20, "");
				} else {
					String[] sjlx = cost.getPersonId().split("!_");
					ps.setString(19, sjlx[0]);
					ps.setString(20, sjlx[1]);
				}
			}
			ps.setString(21, cost.getCreateId());
			ps.setString(22, cost.getCreateName());
			ps.setString(23, cost.getCreateDate());
			ps.setString(24, cost.getUpdateId());
			ps.setString(25, cost.getUpdateName());
			ps.setString(26, cost.getUpdateDate());
			ps.setString(27, cost.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateCost(cost cost)" + (t2 - t1) + " ms");
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