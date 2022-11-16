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

import dafengqi.yunxiang.dao.ExpensereportDao;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.ExpenseReport;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("expensereportDao")
public class ExpensereportDaoHibernate extends GenericDaoHibernate<ExpenseReport, Long> implements ExpensereportDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public ExpensereportDaoHibernate() {
		super(ExpenseReport.class);
	}

	@Override
	public List<ExpenseReport> getItems(ExpenseReport expensereport) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ExpenseReport> list = new ArrayList<ExpenseReport>();
		String username = expensereport.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";
			if (expensereport.getCreateDate() == null) {
				if (expensereport.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : expensereport.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!expensereport.getCreateDate().equals("")) {
					if (expensereport.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (expensereport.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (expensereport.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (expensereport.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (expensereport.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (expensereport.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : expensereport.getCreateDateRange()) {
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
			if (expensereport.getPersonIds() != null) {
				if (expensereport.getPersonIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < expensereport.getPersonIds().length; i++) {
						zsql += "  person_id='" + expensereport.getPersonIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (expensereport.getDepartmentIds() != null) {
				if (!expensereport.getDepartmentIds().equals("")) {
					zsql += "and department_id='" + expensereport.getDepartmentIds() + "'";
				}
			}
			if (expensereport.getFrom().equals("全部")) {
				sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, expensereport.getMechanismId());
				rs = ps.executeQuery();
				while (rs.next()) {
					expensereport = new ExpenseReport();
					expensereport.setId(rs.getString(1));
					expensereport.setCost(rs.getString(2));
					expensereport.setPersonId(rs.getString(3));
					expensereport.setPersonName(rs.getString(4));
					expensereport.setDepartmentId(rs.getString(5));
					expensereport.setDepartmentName(rs.getString(6));
					expensereport.setRemarks(rs.getString(7));
					expensereport.setCreateId(rs.getString(8));
					expensereport.setCreateName(rs.getString(9));
					expensereport.setCreateDate(rs.getString(10));
					expensereport.setUpdateId(rs.getString(11));
					expensereport.setUpdateName(rs.getString(12));
					expensereport.setUpdateDate(rs.getString(13));
					expensereport.setMechanismId(rs.getString(14));
					expensereport.setCrmCostId(rs.getString(15));
					list.add(expensereport);
				}
			} else {

				if (expensereport.getFrom().equals("本部")) {
					String[] xh = expensereport.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, expensereport.getMechanismId());
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								expensereport = new ExpenseReport();
								expensereport.setId(rs.getString(1));
								expensereport.setCost(rs.getString(2));
								expensereport.setPersonId(rs.getString(3));
								expensereport.setPersonName(rs.getString(4));
								expensereport.setDepartmentId(rs.getString(5));
								expensereport.setDepartmentName(rs.getString(6));
								expensereport.setRemarks(rs.getString(7));
								expensereport.setCreateId(rs.getString(8));
								expensereport.setCreateName(rs.getString(9));
								expensereport.setCreateDate(rs.getString(10));
								expensereport.setUpdateId(rs.getString(11));
								expensereport.setUpdateName(rs.getString(12));
								expensereport.setUpdateDate(rs.getString(13));
								expensereport.setMechanismId(rs.getString(14));
								expensereport.setCrmCostId(rs.getString(15));
								list.add(expensereport);
							}
						}
					}
					sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and create_id = ? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, expensereport.getMechanismId());
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						expensereport = new ExpenseReport();
						expensereport.setId(rs.getString(1));
						expensereport.setCost(rs.getString(2));
						expensereport.setPersonId(rs.getString(3));
						expensereport.setPersonName(rs.getString(4));
						expensereport.setDepartmentId(rs.getString(5));
						expensereport.setDepartmentName(rs.getString(6));
						expensereport.setRemarks(rs.getString(7));
						expensereport.setCreateId(rs.getString(8));
						expensereport.setCreateName(rs.getString(9));
						expensereport.setCreateDate(rs.getString(10));
						expensereport.setUpdateId(rs.getString(11));
						expensereport.setUpdateName(rs.getString(12));
						expensereport.setUpdateDate(rs.getString(13));
						expensereport.setMechanismId(rs.getString(14));
						expensereport.setCrmCostId(rs.getString(15));
						list.add(expensereport);
					}
					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (expensereport.getFrom().equals("未设")) {

					sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and create_id = ? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, expensereport.getMechanismId());
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						expensereport = new ExpenseReport();
						expensereport.setId(rs.getString(1));
						expensereport.setCost(rs.getString(2));
						expensereport.setPersonId(rs.getString(3));
						expensereport.setPersonName(rs.getString(4));
						expensereport.setDepartmentId(rs.getString(5));
						expensereport.setDepartmentName(rs.getString(6));
						expensereport.setRemarks(rs.getString(7));
						expensereport.setCreateId(rs.getString(8));
						expensereport.setCreateName(rs.getString(9));
						expensereport.setCreateDate(rs.getString(10));
						expensereport.setUpdateId(rs.getString(11));
						expensereport.setUpdateName(rs.getString(12));
						expensereport.setUpdateDate(rs.getString(13));
						expensereport.setMechanismId(rs.getString(14));
						expensereport.setCrmCostId(rs.getString(15));
						list.add(expensereport);
					}
				} else {

					String[] xh = expensereport.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, expensereport.getMechanismId());
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								expensereport = new ExpenseReport();
								expensereport.setId(rs.getString(1));
								expensereport.setCost(rs.getString(2));
								expensereport.setPersonId(rs.getString(3));
								expensereport.setPersonName(rs.getString(4));
								expensereport.setDepartmentId(rs.getString(5));
								expensereport.setDepartmentName(rs.getString(6));
								expensereport.setRemarks(rs.getString(7));
								expensereport.setCreateId(rs.getString(8));
								expensereport.setCreateName(rs.getString(9));
								expensereport.setCreateDate(rs.getString(10));
								expensereport.setUpdateId(rs.getString(11));
								expensereport.setUpdateName(rs.getString(12));
								expensereport.setUpdateDate(rs.getString(13));
								expensereport.setMechanismId(rs.getString(14));
								expensereport.setCrmCostId(rs.getString(15));
								list.add(expensereport);
							}
						}
					}
					sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and create_id = ? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, expensereport.getMechanismId());
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						expensereport = new ExpenseReport();
						expensereport.setId(rs.getString(1));
						expensereport.setCost(rs.getString(2));
						expensereport.setPersonId(rs.getString(3));
						expensereport.setPersonName(rs.getString(4));
						expensereport.setDepartmentId(rs.getString(5));
						expensereport.setDepartmentName(rs.getString(6));
						expensereport.setRemarks(rs.getString(7));
						expensereport.setCreateId(rs.getString(8));
						expensereport.setCreateName(rs.getString(9));
						expensereport.setCreateDate(rs.getString(10));
						expensereport.setUpdateId(rs.getString(11));
						expensereport.setUpdateName(rs.getString(12));
						expensereport.setUpdateDate(rs.getString(13));
						expensereport.setMechanismId(rs.getString(14));
						expensereport.setCrmCostId(rs.getString(15));
						list.add(expensereport);
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
			System.out.println("List<Expensereport> getItems(Expensereport expensereport)" + (t2 - t1) + " ms");
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
	public List<ExpenseReport> getItemsOfMy(ExpenseReport expensereport) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ExpenseReport> list = new ArrayList<ExpenseReport>();
		String username = expensereport.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String zsql = "";
			if (expensereport.getCreateDate() == null) {
				if (expensereport.getCreateDateRange() != null) {
					int i = 0;
					for (Date t : expensereport.getCreateDateRange()) {
						if (i == 0) {
							zsql += " and create_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and create_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!expensereport.getCreateDate().equals("")) {
					if (expensereport.getCreateDate().equals("今日")) {
						zsql += " and create_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (expensereport.getCreateDate().equals("本周")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (expensereport.getCreateDate().equals("本月")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (expensereport.getCreateDate().equals("本季")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and create_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (expensereport.getCreateDate().equals("本年")) {
						zsql += " and create_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and create_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (expensereport.getCreateDateRange() != null) {
						int i = 0;
						for (Date t : expensereport.getCreateDateRange()) {
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
			String sql = "";
			sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and mechanism_id=? and create_id = ? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, expensereport.getMechanismId());
			ps.setString(2, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				expensereport = new ExpenseReport();
				expensereport.setId(rs.getString(1));
				expensereport.setCost(rs.getString(2));
				expensereport.setPersonId(rs.getString(3));
				expensereport.setPersonName(rs.getString(4));
				expensereport.setDepartmentId(rs.getString(5));
				expensereport.setDepartmentName(rs.getString(6));
				expensereport.setRemarks(rs.getString(7));
				expensereport.setCreateId(rs.getString(8));
				expensereport.setCreateName(rs.getString(9));
				expensereport.setCreateDate(rs.getString(10));
				expensereport.setUpdateId(rs.getString(11));
				expensereport.setUpdateName(rs.getString(12));
				expensereport.setUpdateDate(rs.getString(13));
				expensereport.setMechanismId(rs.getString(14));
				expensereport.setCrmCostId(rs.getString(15));
				list.add(expensereport);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Expensereport> getItems(Expensereport expensereport)" + (t2 - t1) + " ms");
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
	public List<Cost> editmx(Cost cost) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Cost> list = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and person_id=? and state=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, cost.getPersonId());
			ps.setString(2, "未报");
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
	public ExpenseReport edit(ExpenseReport expensereport) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Cost> list = new ArrayList<Cost>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id FROM  crm_expense_report    where 1=1 and id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, expensereport.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				expensereport = new ExpenseReport();
				expensereport.setId(rs.getString(1));
				expensereport.setCost(rs.getString(2));
				expensereport.setPersonId(rs.getString(3) + "!_" + rs.getString(4));
				expensereport.setPersonName(rs.getString(4));
				expensereport.setDepartmentId(rs.getString(5) + "!_" + rs.getString(6));
				expensereport.setDepartmentName(rs.getString(6));
				expensereport.setRemarks(rs.getString(7));
				expensereport.setCreateId(rs.getString(8));
				expensereport.setCreateName(rs.getString(9));
				expensereport.setCreateDate(rs.getString(10));
				expensereport.setUpdateId(rs.getString(11));
				expensereport.setUpdateName(rs.getString(12));
				expensereport.setUpdateDate(rs.getString(13));
				expensereport.setMechanismId(rs.getString(14));
				expensereport.setCrmCostId(rs.getString(15));
			}

			String sql1 = "SELECT id,expense_type,expense_type_name,cost_description,expense_amount,time_of_occurrence,customer_id,customer_name,contacts_id,contacts_name,business_id,business_name,contract_id,contract_name,follow_up_record,follow_up_record_name,visit_sign_in,enclosure,person_id,person_name,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id FROM  crm_cost    where 1=1 and expense_report_id=? order by create_date desc ";
			ps1 = conn.prepareStatement(sql1);
			ps1.setString(1, expensereport.getId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				Cost cost = new Cost();
				cost.setId(rs1.getString(1));
				cost.setExpenseType(rs1.getString(2));
				cost.setExpenseTypeName(rs1.getString(3));
				cost.setCostDescription(rs1.getString(4));
				cost.setExpenseAmount(rs1.getString(5));
				cost.setTimeOfOccurrence(rs1.getDate(6));
				cost.setCustomerId(rs1.getString(7));
				cost.setCustomerName(rs1.getString(8));
				cost.setContactsId(rs1.getString(9));
				cost.setContactsName(rs1.getString(10));
				cost.setBusinessId(rs1.getString(11));
				cost.setBusinessName(rs1.getString(12));
				cost.setContractId(rs1.getString(13));
				cost.setContractName(rs1.getString(14));
				cost.setFollowUpRecord(rs1.getString(15));
				cost.setFollowUpRecordName(rs1.getString(16));
				cost.setVisitSignIn(rs1.getString(17));
				cost.setEnclosure(rs1.getString(18));
				cost.setPersonId(rs1.getString(19));
				cost.setPersonName(rs1.getString(20));
				cost.setCreateId(rs1.getString(21));
				cost.setCreateName(rs1.getString(22));
				cost.setCreateDate(rs1.getString(23));
				cost.setUpdateId(rs1.getString(24));
				cost.setUpdateName(rs1.getString(25));
				cost.setUpdateDate(rs1.getString(26));
				cost.setMechanismId(rs1.getString(27));
				list.add(cost);
			}
			expensereport.setCostList(list);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Expensereport> getItems(Expensereport expensereport)" + (t2 - t1) + " ms");
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
		return expensereport;
	}

	@Override
	public int saveExpensereport(ExpenseReport expensereport, List<Cost> costs) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String iexpensereportSQL = "INSERT INTO crm_expense_report(id,cost,person_id,person_name,department_id,department_name,remarks,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,crm_cost_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iexpensereportSQL);

			ps.setString(1, expensereport.getId());
			ps.setString(2, expensereport.getCost());
			if (expensereport.getPersonId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
			} else {
				if (expensereport.getPersonId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
				} else {
					String[] sjlx = expensereport.getPersonId().split("!_");
					ps.setString(3, sjlx[0]);
					ps.setString(4, sjlx[1]);
				}
			}
			if (expensereport.getDepartmentId() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (expensereport.getDepartmentId().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] sjlx = expensereport.getDepartmentId().split("!_");
					ps.setString(5, sjlx[0]);
					ps.setString(6, sjlx[1]);
				}
			}
			ps.setString(7, expensereport.getRemarks());
			ps.setString(8, expensereport.getCreateId());
			ps.setString(9, expensereport.getCreateName());
			ps.setString(10, expensereport.getCreateDate());
			ps.setString(11, expensereport.getUpdateId());
			ps.setString(12, expensereport.getUpdateName());
			ps.setString(13, expensereport.getUpdateDate());
			ps.setString(14, expensereport.getMechanismId());
			ps.setString(15, expensereport.getCrmCostId());
			for (Cost cost : costs) {
				String uexpensereportSQL = "update crm_cost set state=?,expense_report_id=? where 1=1 and id=? ";
				ps1 = conn.prepareStatement(uexpensereportSQL);
				ps1.setString(1, "已报");
				ps1.setString(2, expensereport.getId());
				ps1.setString(3, cost.getId());
				ps1.executeUpdate();
			}
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveExpensereport(Expensereport expensereport)" + (t2 - t1) + " ms");
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
	public int update(ExpenseReport expensereport) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uexpensereportSQL = "update crm_expense_report set crm_cost_id=?,cost=?,person_id=?,person_name=?,department_id=?,department_name=?,remarks=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,mechanism_id=? where 1=1 and id=? ";
			ps = conn.prepareStatement(uexpensereportSQL);
			ps.setString(1, expensereport.getCrmCostId());
			ps.setString(2, expensereport.getCost());
			if (expensereport.getPersonId() == null) {
				ps.setString(3, "");
				ps.setString(4, "");
			} else {
				if (expensereport.getPersonId().equals("")) {
					ps.setString(3, "");
					ps.setString(4, "");
				} else {
					String[] sjlx = expensereport.getPersonId().split("!_");
					ps.setString(3, sjlx[0]);
					ps.setString(4, sjlx[1]);
				}
			}
			if (expensereport.getDepartmentId() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (expensereport.getDepartmentId().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] sjlx = expensereport.getDepartmentId().split("!_");
					ps.setString(5, sjlx[0]);
					ps.setString(6, sjlx[1]);
				}
			}
			ps.setString(7, expensereport.getRemarks());
			ps.setString(8, expensereport.getCreateId());
			ps.setString(9, expensereport.getCreateName());
			ps.setString(10, expensereport.getCreateDate());
			ps.setString(11, expensereport.getUpdateId());
			ps.setString(12, expensereport.getUpdateName());
			ps.setString(13, expensereport.getUpdateDate());
			ps.setString(14, expensereport.getMechanismId());
			ps.setString(15, expensereport.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateExpensereport(expensereport expensereport)" + (t2 - t1) + " ms");
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