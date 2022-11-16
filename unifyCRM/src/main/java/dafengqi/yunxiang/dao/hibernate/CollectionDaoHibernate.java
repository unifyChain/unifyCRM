package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.CollectionDao;
import dafengqi.yunxiang.model.Collection;
import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Invoicing;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("collectionDao")
public class CollectionDaoHibernate extends GenericDaoHibernate<Collection, Long> implements CollectionDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public CollectionDaoHibernate() {
		super(Collection.class);
	}

	@Override
	public List<CollectionDetail> getItems(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";

			if (collection.getCollectionDate() == null) {
				if (collection.getDateRange() != null) {
					int i = 0;
					for (Date t : collection.getDateRange()) {
						if (i == 0) {
							zsql += " and collection_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and collection_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!collection.getCollectionDate().equals("")) {
					if (collection.getCollectionDate().equals("今日")) {
						zsql += " and collection_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDate().equals("本周")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDate().equals("本月")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDate().equals("本季")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDate().equals("本年")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (collection.getDateRange() != null) {
						int i = 0;
						for (Date t : collection.getDateRange()) {
							if (i == 0) {
								zsql += " and collection_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and collection_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (collection.getCollectionAmount() != null) {
				if (!collection.getCollectionAmount().equals("")) {
					zsql += "and collection_amount>='" + collection.getCollectionAmount() + "'";
				}
			}
			if (collection.getCollectionAmounts() != null) {
				if (!collection.getCollectionAmounts().equals("")) {
					zsql += "and collection_amount<='" + collection.getCollectionAmounts() + "'";
				}
			}
			if (collection.getCustomerIds() != null) {
				if (collection.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + collection.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractIds() != null) {
				if (collection.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractIds().length; i++) {
						zsql += "  contract_id='" + collection.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}

			String sql = "";
			if (collection.getFrom().equals("全部")) {
				sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, collection.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					CollectionDetail collectionDetail = new CollectionDetail();
					collectionDetail.setId(rs.getString(1));
					collectionDetail.setNumber(rs.getString(2));
					collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
					collectionDetail.setCollectionDate(rs.getDate(4));
					collectionDetail.setDetailDate(rs.getString(4));
					collectionDetail.setCollectionProportion(rs.getString(5));
					collectionDetail.setCollectionAmount(rs.getString(6));
					collectionDetail.setRemark(rs.getString(7));
					collectionDetail.setYs4(rs.getString(8));
					collectionDetail.setYs5(rs.getString(9));
					collectionDetail.setYs6(rs.getString(10));
					collectionDetail.setCreateId(rs.getString(11));
					collectionDetail.setCreateName(rs.getString(12));
					collectionDetail.setCreateDate(rs.getString(13));
					collectionDetail.setUpdateId(rs.getString(14));
					collectionDetail.setUpdateName(rs.getString(15));
					collectionDetail.setUpdateDate(rs.getString(16));
					collectionDetail.setCollectionId(rs.getString(17));
					collectionDetail.setMechanismId(rs.getString(18));
					collectionDetail.setContractName(rs.getString(19));
					collectionDetail.setCustomerName(rs.getString(20));
					list.add(collectionDetail);
				}
			} else {
				if (collection.getFrom().equals("本部")) {
					String[] xh = collection.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, collection.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								CollectionDetail collectionDetail = new CollectionDetail();
								collectionDetail.setId(rs.getString(1));
								collectionDetail.setNumber(rs.getString(2));
								collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
								collectionDetail.setCollectionDate(rs.getDate(4));
								collectionDetail.setDetailDate(rs.getString(4));
								collectionDetail.setCollectionProportion(rs.getString(5));
								collectionDetail.setCollectionAmount(rs.getString(6));
								collectionDetail.setRemark(rs.getString(7));
								collectionDetail.setYs4(rs.getString(8));
								collectionDetail.setYs5(rs.getString(9));
								collectionDetail.setYs6(rs.getString(10));
								collectionDetail.setCreateId(rs.getString(11));
								collectionDetail.setCreateName(rs.getString(12));
								collectionDetail.setCreateDate(rs.getString(13));
								collectionDetail.setUpdateId(rs.getString(14));
								collectionDetail.setUpdateName(rs.getString(15));
								collectionDetail.setUpdateDate(rs.getString(16));
								collectionDetail.setCollectionId(rs.getString(17));
								collectionDetail.setMechanismId(rs.getString(18));
								collectionDetail.setContractName(rs.getString(19));
								collectionDetail.setCustomerName(rs.getString(20));
								list.add(collectionDetail);
							}
						}
					}
					sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						CollectionDetail collectionDetail = new CollectionDetail();
						collectionDetail.setId(rs.getString(1));
						collectionDetail.setNumber(rs.getString(2));
						collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
						collectionDetail.setCollectionDate(rs.getDate(4));
						collectionDetail.setDetailDate(rs.getString(4));
						collectionDetail.setCollectionProportion(rs.getString(5));
						collectionDetail.setCollectionAmount(rs.getString(6));
						collectionDetail.setRemark(rs.getString(7));
						collectionDetail.setYs4(rs.getString(8));
						collectionDetail.setYs5(rs.getString(9));
						collectionDetail.setYs6(rs.getString(10));
						collectionDetail.setCreateId(rs.getString(11));
						collectionDetail.setCreateName(rs.getString(12));
						collectionDetail.setCreateDate(rs.getString(13));
						collectionDetail.setUpdateId(rs.getString(14));
						collectionDetail.setUpdateName(rs.getString(15));
						collectionDetail.setUpdateDate(rs.getString(16));
						collectionDetail.setCollectionId(rs.getString(17));
						collectionDetail.setMechanismId(rs.getString(18));
						collectionDetail.setContractName(rs.getString(19));
						collectionDetail.setCustomerName(rs.getString(20));
						list.add(collectionDetail);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (collection.getFrom().equals("未设")) {
					sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						CollectionDetail collectionDetail = new CollectionDetail();
						collectionDetail.setId(rs.getString(1));
						collectionDetail.setNumber(rs.getString(2));
						collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
						collectionDetail.setCollectionDate(rs.getDate(4));
						collectionDetail.setDetailDate(rs.getString(4));
						collectionDetail.setCollectionProportion(rs.getString(5));
						collectionDetail.setCollectionAmount(rs.getString(6));
						collectionDetail.setRemark(rs.getString(7));
						collectionDetail.setYs4(rs.getString(8));
						collectionDetail.setYs5(rs.getString(9));
						collectionDetail.setYs6(rs.getString(10));
						collectionDetail.setCreateId(rs.getString(11));
						collectionDetail.setCreateName(rs.getString(12));
						collectionDetail.setCreateDate(rs.getString(13));
						collectionDetail.setUpdateId(rs.getString(14));
						collectionDetail.setUpdateName(rs.getString(15));
						collectionDetail.setUpdateDate(rs.getString(16));
						collectionDetail.setCollectionId(rs.getString(17));
						collectionDetail.setMechanismId(rs.getString(18));
						collectionDetail.setContractName(rs.getString(19));
						collectionDetail.setCustomerName(rs.getString(20));
						list.add(collectionDetail);
					}
				} else {

					String[] xh = collection.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, collection.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								CollectionDetail collectionDetail = new CollectionDetail();
								collectionDetail.setId(rs.getString(1));
								collectionDetail.setNumber(rs.getString(2));
								collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
								collectionDetail.setCollectionDate(rs.getDate(4));
								collectionDetail.setDetailDate(rs.getString(4));
								collectionDetail.setCollectionProportion(rs.getString(5));
								collectionDetail.setCollectionAmount(rs.getString(6));
								collectionDetail.setRemark(rs.getString(7));
								collectionDetail.setYs4(rs.getString(8));
								collectionDetail.setYs5(rs.getString(9));
								collectionDetail.setYs6(rs.getString(10));
								collectionDetail.setCreateId(rs.getString(11));
								collectionDetail.setCreateName(rs.getString(12));
								collectionDetail.setCreateDate(rs.getString(13));
								collectionDetail.setUpdateId(rs.getString(14));
								collectionDetail.setUpdateName(rs.getString(15));
								collectionDetail.setUpdateDate(rs.getString(16));
								collectionDetail.setCollectionId(rs.getString(17));
								collectionDetail.setMechanismId(rs.getString(18));
								collectionDetail.setContractName(rs.getString(19));
								collectionDetail.setCustomerName(rs.getString(20));
								list.add(collectionDetail);
							}
						}
					}
					sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						CollectionDetail collectionDetail = new CollectionDetail();
						collectionDetail.setId(rs.getString(1));
						collectionDetail.setNumber(rs.getString(2));
						collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
						collectionDetail.setCollectionDate(rs.getDate(4));
						collectionDetail.setDetailDate(rs.getString(4));
						collectionDetail.setCollectionProportion(rs.getString(5));
						collectionDetail.setCollectionAmount(rs.getString(6));
						collectionDetail.setRemark(rs.getString(7));
						collectionDetail.setYs4(rs.getString(8));
						collectionDetail.setYs5(rs.getString(9));
						collectionDetail.setYs6(rs.getString(10));
						collectionDetail.setCreateId(rs.getString(11));
						collectionDetail.setCreateName(rs.getString(12));
						collectionDetail.setCreateDate(rs.getString(13));
						collectionDetail.setUpdateId(rs.getString(14));
						collectionDetail.setUpdateName(rs.getString(15));
						collectionDetail.setUpdateDate(rs.getString(16));
						collectionDetail.setCollectionId(rs.getString(17));
						collectionDetail.setMechanismId(rs.getString(18));
						collectionDetail.setContractName(rs.getString(19));
						collectionDetail.setCustomerName(rs.getString(20));
						list.add(collectionDetail);
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
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<CollectionDetail> getItemsOfMy(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";

			if (collection.getCollectionDate() == null) {
				if (collection.getDateRange() != null) {
					int i = 0;
					for (Date t : collection.getDateRange()) {
						if (i == 0) {
							zsql += " and collection_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and collection_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!collection.getCollectionDate().equals("")) {
					if (collection.getCollectionDate().equals("今日")) {
						zsql += " and collection_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDate().equals("本周")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDate().equals("本月")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDate().equals("本季")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDate().equals("本年")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (collection.getDateRange() != null) {
						int i = 0;
						for (Date t : collection.getDateRange()) {
							if (i == 0) {
								zsql += " and collection_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and collection_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (collection.getCollectionAmount() != null) {
				if (!collection.getCollectionAmount().equals("")) {
					zsql += "and collection_amount>='" + collection.getCollectionAmount() + "'";
				}
			}
			if (collection.getCollectionAmounts() != null) {
				if (!collection.getCollectionAmounts().equals("")) {
					zsql += "and collection_amount<='" + collection.getCollectionAmounts() + "'";
				}
			}
			if (collection.getCustomerIds() != null) {
				if (collection.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + collection.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractIds() != null) {
				if (collection.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractIds().length; i++) {
						zsql += "  contract_id='" + collection.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";

			sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and mechanism_id like ? and create_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, collection.getMechanismId() + "%");
			ps.setString(2, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setId(rs.getString(1));
				collectionDetail.setNumber(rs.getString(2));
				collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
				collectionDetail.setCollectionDate(rs.getDate(4));
				collectionDetail.setDetailDate(rs.getString(4));
				collectionDetail.setCollectionProportion(rs.getString(5));
				collectionDetail.setCollectionAmount(rs.getString(6));
				collectionDetail.setRemark(rs.getString(7));
				collectionDetail.setYs4(rs.getString(8));
				collectionDetail.setYs5(rs.getString(9));
				collectionDetail.setYs6(rs.getString(10));
				collectionDetail.setCreateId(rs.getString(11));
				collectionDetail.setCreateName(rs.getString(12));
				collectionDetail.setCreateDate(rs.getString(13));
				collectionDetail.setUpdateId(rs.getString(14));
				collectionDetail.setUpdateName(rs.getString(15));
				collectionDetail.setUpdateDate(rs.getString(16));
				collectionDetail.setCollectionId(rs.getString(17));
				collectionDetail.setMechanismId(rs.getString(18));
				collectionDetail.setContractName(rs.getString(19));
				collectionDetail.setCustomerName(rs.getString(20));
				list.add(collectionDetail);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<Collection> getItemsRecord(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Collection> list = new ArrayList<Collection>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";

			if (collection.getCollectionDatess() == null) {
				if (collection.getDateRanges() != null) {
					int i = 0;
					for (Date t : collection.getDateRanges()) {
						if (i == 0) {
							zsql += " and collection_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and collection_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!collection.getCollectionDatess().equals("")) {
					if (collection.getCollectionDatess().equals("今日")) {
						zsql += " and collection_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDatess().equals("本周")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDatess().equals("本月")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDatess().equals("本季")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDatess().equals("本年")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (collection.getDateRanges() != null) {
						int i = 0;
						for (Date t : collection.getDateRanges()) {
							if (i == 0) {
								zsql += " and collection_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and collection_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (collection.getCollectionAmount() != null) {
				if (!collection.getCollectionAmount().equals("")) {
					zsql += "and collection_amount>=" + collection.getCollectionAmount() + "";
				}
			}
			if (collection.getCollectionAmounts() != null) {
				if (!collection.getCollectionAmounts().equals("")) {
					zsql += "and collection_amount<=" + collection.getCollectionAmounts() + "";
				}
			}
			if (collection.getCustomerIds() != null) {
				if (collection.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + collection.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractIds() != null) {
				if (collection.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractIds().length; i++) {
						zsql += "  contract_id='" + collection.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getCollectionTypes() != null) {
				if (collection.getCollectionTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCollectionTypes().length; i++) {
						zsql += "  collection_type='" + collection.getCollectionTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractLeaders() != null) {
				if (collection.getContractLeaders().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractLeaders().length; i++) {
						zsql += "  contract_leader='" + collection.getContractLeaders()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getPayTypeNames() != null) {
				if (collection.getPayTypeNames().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getPayTypeNames().length; i++) {
						zsql += "  pay_type_name='" + collection.getPayTypeNames()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
			if (collection.getFrom().equals("全部")) {
				sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? "
						+ zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, collection.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					collection = new Collection();
					collection.setId(rs.getString(1));
					collection.setCollectionDates(rs.getDate(2));
					collection.setCollectionAmount(rs.getString(3));
					collection.setCustomerId(rs.getString(4));
					collection.setCustomerName(rs.getString(5));
					collection.setContractId(rs.getString(6));
					collection.setContractName(rs.getString(7));
					collection.setPayTypeName(rs.getString(8));
					collection.setCollectionType(rs.getString(9));
					collection.setContractLeader(rs.getString(10));
					collection.setYs6(rs.getString(11));
					collection.setCreateId(rs.getString(12));
					collection.setCreateName(rs.getString(13));
					collection.setCreateDate(rs.getString(14));
					collection.setUpdateId(rs.getString(15));
					collection.setUpdateName(rs.getString(16));
					collection.setUpdateDate(rs.getString(17));
					collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
					collection.setMechanismId(rs.getString(19));
					list.add(collection);
				}
			} else {
				if (collection.getFrom().equals("本部")) {

					String[] xh = collection.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, collection.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								collection = new Collection();
								collection.setId(rs.getString(1));
								collection.setCollectionDates(rs.getDate(2));
								collection.setCollectionAmount(rs.getString(3));
								collection.setCustomerId(rs.getString(4));
								collection.setCustomerName(rs.getString(5));
								collection.setContractId(rs.getString(6));
								collection.setContractName(rs.getString(7));
								collection.setPayTypeName(rs.getString(8));
								collection.setCollectionType(rs.getString(9));
								collection.setContractLeader(rs.getString(10));
								collection.setYs6(rs.getString(11));
								collection.setCreateId(rs.getString(12));
								collection.setCreateName(rs.getString(13));
								collection.setCreateDate(rs.getString(14));
								collection.setUpdateId(rs.getString(15));
								collection.setUpdateName(rs.getString(16));
								collection.setUpdateDate(rs.getString(17));
								collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
								collection.setMechanismId(rs.getString(19));
								list.add(collection);
							}
						}
					}
					sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						collection = new Collection();
						collection.setId(rs.getString(1));
						collection.setCollectionDates(rs.getDate(2));
						collection.setCollectionAmount(rs.getString(3));
						collection.setCustomerId(rs.getString(4));
						collection.setCustomerName(rs.getString(5));
						collection.setContractId(rs.getString(6));
						collection.setContractName(rs.getString(7));
						collection.setPayTypeName(rs.getString(8));
						collection.setCollectionType(rs.getString(9));
						collection.setContractLeader(rs.getString(10));
						collection.setYs6(rs.getString(11));
						collection.setCreateId(rs.getString(12));
						collection.setCreateName(rs.getString(13));
						collection.setCreateDate(rs.getString(14));
						collection.setUpdateId(rs.getString(15));
						collection.setUpdateName(rs.getString(16));
						collection.setUpdateDate(rs.getString(17));
						collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
						collection.setMechanismId(rs.getString(19));
						list.add(collection);
					}
					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (collection.getFrom().equals("未设")) {
					sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						collection = new Collection();
						collection.setId(rs.getString(1));
						collection.setCollectionDates(rs.getDate(2));
						collection.setCollectionAmount(rs.getString(3));
						collection.setCustomerId(rs.getString(4));
						collection.setCustomerName(rs.getString(5));
						collection.setContractId(rs.getString(6));
						collection.setContractName(rs.getString(7));
						collection.setPayTypeName(rs.getString(8));
						collection.setCollectionType(rs.getString(9));
						collection.setContractLeader(rs.getString(10));
						collection.setYs6(rs.getString(11));
						collection.setCreateId(rs.getString(12));
						collection.setCreateName(rs.getString(13));
						collection.setCreateDate(rs.getString(14));
						collection.setUpdateId(rs.getString(15));
						collection.setUpdateName(rs.getString(16));
						collection.setUpdateDate(rs.getString(17));
						collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
						collection.setMechanismId(rs.getString(19));
						list.add(collection);
					}
				} else {

					String[] xh = collection.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and department_id like ? "
									+ zsql + " order by create_date desc ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, collection.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								collection = new Collection();
								collection.setId(rs.getString(1));
								collection.setCollectionDates(rs.getDate(2));
								collection.setCollectionAmount(rs.getString(3));
								collection.setCustomerId(rs.getString(4));
								collection.setCustomerName(rs.getString(5));
								collection.setContractId(rs.getString(6));
								collection.setContractName(rs.getString(7));
								collection.setPayTypeName(rs.getString(8));
								collection.setCollectionType(rs.getString(9));
								collection.setContractLeader(rs.getString(10));
								collection.setYs6(rs.getString(11));
								collection.setCreateId(rs.getString(12));
								collection.setCreateName(rs.getString(13));
								collection.setCreateDate(rs.getString(14));
								collection.setUpdateId(rs.getString(15));
								collection.setUpdateName(rs.getString(16));
								collection.setUpdateDate(rs.getString(17));
								collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
								collection.setMechanismId(rs.getString(19));
								list.add(collection);
							}
						}
					}
					sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and create_id=? "
							+ zsql + " order by create_date desc ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, collection.getMechanismId() + "%");
					ps.setString(2, username);
					rs = ps.executeQuery();
					while (rs.next()) {
						collection = new Collection();
						collection.setId(rs.getString(1));
						collection.setCollectionDates(rs.getDate(2));
						collection.setCollectionAmount(rs.getString(3));
						collection.setCustomerId(rs.getString(4));
						collection.setCustomerName(rs.getString(5));
						collection.setContractId(rs.getString(6));
						collection.setContractName(rs.getString(7));
						collection.setPayTypeName(rs.getString(8));
						collection.setCollectionType(rs.getString(9));
						collection.setContractLeader(rs.getString(10));
						collection.setYs6(rs.getString(11));
						collection.setCreateId(rs.getString(12));
						collection.setCreateName(rs.getString(13));
						collection.setCreateDate(rs.getString(14));
						collection.setUpdateId(rs.getString(15));
						collection.setUpdateName(rs.getString(16));
						collection.setUpdateDate(rs.getString(17));
						collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
						collection.setMechanismId(rs.getString(19));
						list.add(collection);
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
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<Collection> getItemsRecordOfMy(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Collection> list = new ArrayList<Collection>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";

			if (collection.getCollectionDatess() == null) {
				if (collection.getDateRange() != null) {
					int i = 0;
					for (Date t : collection.getDateRange()) {
						if (i == 0) {
							zsql += " and collection_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and collection_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!collection.getCollectionDatess().equals("")) {
					if (collection.getCollectionDatess().equals("今日")) {
						zsql += " and collection_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDatess().equals("本周")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDatess().equals("本月")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (collection.getCollectionDatess().equals("本季")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and collection_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (collection.getCollectionDatess().equals("本年")) {
						zsql += " and collection_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and collection_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (collection.getDateRange() != null) {
						int i = 0;
						for (Date t : collection.getDateRange()) {
							if (i == 0) {
								zsql += " and collection_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and collection_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (collection.getCollectionAmount() != null) {
				if (!collection.getCollectionAmount().equals("")) {
					zsql += "and collection_amount<='" + collection.getCollectionAmount() + "'";
				}
			}
			if (collection.getCollectionAmounts() != null) {
				if (!collection.getCollectionAmounts().equals("")) {
					zsql += "and collection_amount>='" + collection.getCollectionAmounts() + "'";
				}
			}
			if (collection.getCustomerIds() != null) {
				if (collection.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + collection.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractIds() != null) {
				if (collection.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractIds().length; i++) {
						zsql += "  contract_id='" + collection.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getCollectionTypes() != null) {
				if (collection.getCollectionTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getCollectionTypes().length; i++) {
						zsql += "  collection_type='" + collection.getCollectionTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getContractLeaders() != null) {
				if (collection.getContractLeaders().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getContractLeaders().length; i++) {
						zsql += "  contract_leader='" + collection.getContractLeaders()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (collection.getPayTypeNames() != null) {
				if (collection.getPayTypeNames().length != 0) {

					zsql += " and (";
					for (int i = 0; i < collection.getPayTypeNames().length; i++) {
						zsql += "  pay_type_name='" + collection.getPayTypeNames()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";
			sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and mechanism_id like ? and create_id=? "
					+ zsql + " order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, collection.getMechanismId() + "%");
			ps.setString(2, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				collection = new Collection();
				collection.setId(rs.getString(1));
				collection.setCollectionDates(rs.getDate(2));
				collection.setCollectionAmount(rs.getString(3));
				collection.setCustomerId(rs.getString(4));
				collection.setCustomerName(rs.getString(5));
				collection.setContractId(rs.getString(6));
				collection.setContractName(rs.getString(7));
				collection.setPayTypeName(rs.getString(8));
				collection.setCollectionType(rs.getString(9));
				collection.setContractLeader(rs.getString(10));
				collection.setYs6(rs.getString(11));
				collection.setCreateId(rs.getString(12));
				collection.setCreateName(rs.getString(13));
				collection.setCreateDate(rs.getString(14));
				collection.setUpdateId(rs.getString(15));
				collection.setUpdateName(rs.getString(16));
				collection.setUpdateDate(rs.getString(17));
				collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
				collection.setMechanismId(rs.getString(19));
				list.add(collection);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<Invoicing> getItemsInvoicing(Invoicing invoicing) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Invoicing> list = new ArrayList<Invoicing>();
		String username = invoicing.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String zsql = "";

			if (invoicing.getInvoicingDates() == null) {
				if (invoicing.getInvoicingDateRange() != null) {
					int i = 0;
					for (Date t : invoicing.getInvoicingDateRange()) {
						if (i == 0) {
							zsql += " and invoicing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and invoicing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!invoicing.getInvoicingDates().equals("")) {
					if (invoicing.getInvoicingDates().equals("今日")) {
						zsql += " and invoicing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (invoicing.getInvoicingDates().equals("本周")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (invoicing.getInvoicingDates().equals("本月")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (invoicing.getInvoicingDates().equals("本季")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (invoicing.getInvoicingDates().equals("本年")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (invoicing.getInvoicingDateRange() != null) {
						int i = 0;
						for (Date t : invoicing.getInvoicingDateRange()) {
							if (i == 0) {
								zsql += " and invoicing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and invoicing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (invoicing.getInvoicingAmount() != null) {
				if (!invoicing.getInvoicingAmount().equals("")) {
					zsql += "and invoicing_amount>=" + invoicing.getInvoicingAmount() + "";
				}
			}
			if (invoicing.getInvoicingAmounts() != null) {
				if (!invoicing.getInvoicingAmounts().equals("")) {
					zsql += "and invoicing_amount<=" + invoicing.getInvoicingAmounts() + "";
				}
			}
			if (invoicing.getCustomerIds() != null) {
				if (invoicing.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + invoicing.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getContractIds() != null) {
				if (invoicing.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getContractIds().length; i++) {
						zsql += "  contract_id='" + invoicing.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getInvoiceTypes() != null) {
				if (invoicing.getInvoiceTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getInvoiceTypes().length; i++) {
						zsql += "  invoice_type='" + invoicing.getInvoiceTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getContractLeaders() != null) {
				if (invoicing.getContractLeaders().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getContractLeaders().length; i++) {
						zsql += "  contract_leader='" + invoicing.getContractLeaders()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			String sql = "";

			if (invoicing.getFrom().equals("全部")) {
				sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
						+ invoicing.getMechanismId() + "'   " + zsql + " order by create_date desc ";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while (rs.next()) {
					invoicing = new Invoicing();
					invoicing.setInvoicingDate(rs.getDate(1));
					invoicing.setInvoicingContent(rs.getString(2));
					invoicing.setInvoicingAmount(rs.getBigDecimal(3));
					invoicing.setCustomerId(rs.getString(4));
					invoicing.setCustomerName(rs.getString(5));
					invoicing.setContractId(rs.getString(6));
					invoicing.setContractName(rs.getString(7));
					invoicing.setInvoiceType(rs.getString(8));
					invoicing.setInvoicingNum(rs.getString(9));
					invoicing.setContractLeader(rs.getString(10));
					invoicing.setRemark(rs.getString(11));
					invoicing.setCreateId(rs.getString(12));
					invoicing.setCreateName(rs.getString(13));
					invoicing.setCreateDate(rs.getString(14));
					invoicing.setUpdateId(rs.getString(15));
					invoicing.setUpdateName(rs.getString(16));
					invoicing.setUpdateDate(rs.getString(17));
					invoicing.setSysInvoicingTypeId(rs.getString(18));
					invoicing.setId(rs.getString(19));
					list.add(invoicing);
				}
			} else {
				if (invoicing.getFrom().equals("本部")) {

					String[] xh = invoicing.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
									+ invoicing.getMechanismId() + "' " + zsql + "  and department_id like '" + bmid
									+ "%'  order by create_date desc ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							while (rs.next()) {
								invoicing = new Invoicing();
								invoicing.setInvoicingDate(rs.getDate(1));
								invoicing.setInvoicingContent(rs.getString(2));
								invoicing.setInvoicingAmount(rs.getBigDecimal(3));
								invoicing.setCustomerId(rs.getString(4));
								invoicing.setCustomerName(rs.getString(5));
								invoicing.setContractId(rs.getString(6));
								invoicing.setContractName(rs.getString(7));
								invoicing.setInvoiceType(rs.getString(8));
								invoicing.setInvoicingNum(rs.getString(9));
								invoicing.setContractLeader(rs.getString(10));
								invoicing.setRemark(rs.getString(11));
								invoicing.setCreateId(rs.getString(12));
								invoicing.setCreateName(rs.getString(13));
								invoicing.setCreateDate(rs.getString(14));
								invoicing.setUpdateId(rs.getString(15));
								invoicing.setUpdateName(rs.getString(16));
								invoicing.setUpdateDate(rs.getString(17));
								invoicing.setSysInvoicingTypeId(rs.getString(18));
								invoicing.setId(rs.getString(19));
								list.add(invoicing);
							}
						}
					}
					sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
							+ invoicing.getMechanismId() + "' " + zsql + "   and create_id='" + username
							+ "'  order by create_date desc ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						invoicing = new Invoicing();
						invoicing.setInvoicingDate(rs.getDate(1));
						invoicing.setInvoicingContent(rs.getString(2));
						invoicing.setInvoicingAmount(rs.getBigDecimal(3));
						invoicing.setCustomerId(rs.getString(4));
						invoicing.setCustomerName(rs.getString(5));
						invoicing.setContractId(rs.getString(6));
						invoicing.setContractName(rs.getString(7));
						invoicing.setInvoiceType(rs.getString(8));
						invoicing.setInvoicingNum(rs.getString(9));
						invoicing.setContractLeader(rs.getString(10));
						invoicing.setRemark(rs.getString(11));
						invoicing.setCreateId(rs.getString(12));
						invoicing.setCreateName(rs.getString(13));
						invoicing.setCreateDate(rs.getString(14));
						invoicing.setUpdateId(rs.getString(15));
						invoicing.setUpdateName(rs.getString(16));
						invoicing.setUpdateDate(rs.getString(17));
						invoicing.setSysInvoicingTypeId(rs.getString(18));
						invoicing.setId(rs.getString(19));
						list.add(invoicing);
					}

					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (invoicing.getFrom().equals("未设")) {
					sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
							+ invoicing.getMechanismId() + "' " + zsql + "   and create_id='" + username
							+ "'  order by create_date desc ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						invoicing = new Invoicing();
						invoicing.setInvoicingDate(rs.getDate(1));
						invoicing.setInvoicingContent(rs.getString(2));
						invoicing.setInvoicingAmount(rs.getBigDecimal(3));
						invoicing.setCustomerId(rs.getString(4));
						invoicing.setCustomerName(rs.getString(5));
						invoicing.setContractId(rs.getString(6));
						invoicing.setContractName(rs.getString(7));
						invoicing.setInvoiceType(rs.getString(8));
						invoicing.setInvoicingNum(rs.getString(9));
						invoicing.setContractLeader(rs.getString(10));
						invoicing.setRemark(rs.getString(11));
						invoicing.setCreateId(rs.getString(12));
						invoicing.setCreateName(rs.getString(13));
						invoicing.setCreateDate(rs.getString(14));
						invoicing.setUpdateId(rs.getString(15));
						invoicing.setUpdateName(rs.getString(16));
						invoicing.setUpdateDate(rs.getString(17));
						invoicing.setSysInvoicingTypeId(rs.getString(18));
						invoicing.setId(rs.getString(19));
						list.add(invoicing);
					}
				} else {

					String[] xh = invoicing.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
									+ invoicing.getMechanismId() + "' " + zsql + "  and department_id='" + bmid
									+ "'  order by create_date desc ";
							ps = conn.prepareStatement(sql);
							rs = ps.executeQuery();
							while (rs.next()) {
								invoicing = new Invoicing();
								invoicing.setInvoicingDate(rs.getDate(1));
								invoicing.setInvoicingContent(rs.getString(2));
								invoicing.setInvoicingAmount(rs.getBigDecimal(3));
								invoicing.setCustomerId(rs.getString(4));
								invoicing.setCustomerName(rs.getString(5));
								invoicing.setContractId(rs.getString(6));
								invoicing.setContractName(rs.getString(7));
								invoicing.setInvoiceType(rs.getString(8));
								invoicing.setInvoicingNum(rs.getString(9));
								invoicing.setContractLeader(rs.getString(10));
								invoicing.setRemark(rs.getString(11));
								invoicing.setCreateId(rs.getString(12));
								invoicing.setCreateName(rs.getString(13));
								invoicing.setCreateDate(rs.getString(14));
								invoicing.setUpdateId(rs.getString(15));
								invoicing.setUpdateName(rs.getString(16));
								invoicing.setUpdateDate(rs.getString(17));
								invoicing.setSysInvoicingTypeId(rs.getString(18));
								invoicing.setId(rs.getString(19));
								list.add(invoicing);
							}
						}
					}
					sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
							+ invoicing.getMechanismId() + "'  " + zsql + "  and create_id='" + username
							+ "'  order by create_date desc ";
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					while (rs.next()) {
						invoicing = new Invoicing();
						invoicing.setInvoicingDate(rs.getDate(1));
						invoicing.setInvoicingContent(rs.getString(2));
						invoicing.setInvoicingAmount(rs.getBigDecimal(3));
						invoicing.setCustomerId(rs.getString(4));
						invoicing.setCustomerName(rs.getString(5));
						invoicing.setContractId(rs.getString(6));
						invoicing.setContractName(rs.getString(7));
						invoicing.setInvoiceType(rs.getString(8));
						invoicing.setInvoicingNum(rs.getString(9));
						invoicing.setContractLeader(rs.getString(10));
						invoicing.setRemark(rs.getString(11));
						invoicing.setCreateId(rs.getString(12));
						invoicing.setCreateName(rs.getString(13));
						invoicing.setCreateDate(rs.getString(14));
						invoicing.setUpdateId(rs.getString(15));
						invoicing.setUpdateName(rs.getString(16));
						invoicing.setUpdateDate(rs.getString(17));
						invoicing.setSysInvoicingTypeId(rs.getString(18));
						invoicing.setId(rs.getString(19));
						list.add(invoicing);
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
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<Invoicing> getItemsInvoicingOfMy(Invoicing invoicing) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Invoicing> list = new ArrayList<Invoicing>();
		String username = invoicing.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			String zsql = "";

			if (invoicing.getInvoicingDates() == null) {
				if (invoicing.getInvoicingDateRange() != null) {
					int i = 0;
					for (Date t : invoicing.getInvoicingDateRange()) {
						if (i == 0) {
							zsql += " and invoicing_date>='" + df.format(t) + "'";
							i++;
						} else {
							zsql += " and invoicing_date<='" + df.format(t) + "'";
						}
					}
				}
			} else {
				if (!invoicing.getInvoicingDates().equals("")) {
					if (invoicing.getInvoicingDates().equals("今日")) {
						zsql += " and invoicing_date>='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayStartTime().getTime())
								+ "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentDayEndTime().getTime())
								+ "'";
					}
					if (invoicing.getInvoicingDates().equals("本周")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentWeekDayEndTime().getTime()) + "'";
					}
					if (invoicing.getInvoicingDates().equals("本月")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentMonthStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentMonthEndTime().getTime())
								+ "'";
					}
					if (invoicing.getInvoicingDates().equals("本季")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentQuarterEndTime().getTime()) + "'";
					}
					if (invoicing.getInvoicingDates().equals("本年")) {
						zsql += " and invoicing_date>='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(getCurrentYearStartTime().getTime()) + "'";
						zsql += " and invoicing_date<='"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getCurrentYearEndTime().getTime())
								+ "'";
					}
				} else {
					if (invoicing.getInvoicingDateRange() != null) {
						int i = 0;
						for (Date t : invoicing.getInvoicingDateRange()) {
							if (i == 0) {
								zsql += " and invoicing_date>='" + df.format(t) + "'";
								i++;
							} else {
								zsql += " and invoicing_date<='" + df.format(t) + "'";
							}
						}
					}
				}
			}
			if (invoicing.getInvoicingAmount() != null) {
				if (!invoicing.getInvoicingAmount().equals("")) {
					zsql += "and invoicing_amount<='" + invoicing.getInvoicingAmount() + "'";
				}
			}
			if (invoicing.getInvoicingAmounts() != null) {
				if (!invoicing.getInvoicingAmounts().equals("")) {
					zsql += "and invoicing_amount>='" + invoicing.getInvoicingAmounts() + "'";
				}
			}
			if (invoicing.getCustomerIds() != null) {
				if (invoicing.getCustomerIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getCustomerIds().length; i++) {
						zsql += "  customer_id='" + invoicing.getCustomerIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getContractIds() != null) {
				if (invoicing.getContractIds().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getContractIds().length; i++) {
						zsql += "  contract_id='" + invoicing.getContractIds()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getInvoiceTypes() != null) {
				if (invoicing.getInvoiceTypes().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getInvoiceTypes().length; i++) {
						zsql += "  invoice_type='" + invoicing.getInvoiceTypes()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			if (invoicing.getContractLeaders() != null) {
				if (invoicing.getContractLeaders().length != 0) {

					zsql += " and (";
					for (int i = 0; i < invoicing.getContractLeaders().length; i++) {
						zsql += "  contract_leader='" + invoicing.getContractLeaders()[i] + "' or";
					}
					zsql = zsql.substring(0, zsql.length() - 2);
					zsql += ")";
				}
			}
			sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and mechanism_id='"
					+ invoicing.getMechanismId() + "' " + zsql + "  and create_id='" + username
					+ "'  order by create_date desc ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				invoicing = new Invoicing();
				invoicing.setInvoicingDate(rs.getDate(1));
				invoicing.setInvoicingContent(rs.getString(2));
				invoicing.setInvoicingAmount(rs.getBigDecimal(3));
				invoicing.setCustomerId(rs.getString(4));
				invoicing.setCustomerName(rs.getString(5));
				invoicing.setContractId(rs.getString(6));
				invoicing.setContractName(rs.getString(7));
				invoicing.setInvoiceType(rs.getString(8));
				invoicing.setInvoicingNum(rs.getString(9));
				invoicing.setContractLeader(rs.getString(10));
				invoicing.setRemark(rs.getString(11));
				invoicing.setCreateId(rs.getString(12));
				invoicing.setCreateName(rs.getString(13));
				invoicing.setCreateDate(rs.getString(14));
				invoicing.setUpdateId(rs.getString(15));
				invoicing.setUpdateName(rs.getString(16));
				invoicing.setUpdateDate(rs.getString(17));
				invoicing.setSysInvoicingTypeId(rs.getString(18));
				invoicing.setId(rs.getString(19));
				list.add(invoicing);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public List<CollectionDetail> editmx(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String[] sjlx = collection.getContractId().split("!_");
			String sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id FROM  crm_payment_collection_plan_subordinate  where 1=1 and contract_id=? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sjlx[0]);
			rs = ps.executeQuery();
			while (rs.next()) {
				CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setId(rs.getString(1));
				collectionDetail.setNumber(rs.getString(2));
				collectionDetail.setCrmPaymentCollectionPlanId(rs.getString(3));
				collectionDetail.setCollectionDate(rs.getDate(4));
				collectionDetail.setDetailDate(rs.getString(4));
				collectionDetail.setCollectionProportion(rs.getString(5));
				collectionDetail.setCollectionAmount(rs.getString(6));
				collectionDetail.setRemark(rs.getString(7));
				collectionDetail.setYs4(rs.getString(8));
				collectionDetail.setYs5(rs.getString(9));
				collectionDetail.setYs6(rs.getString(10));
				collectionDetail.setCreateId(rs.getString(11));
				collectionDetail.setCreateName(rs.getString(12));
				collectionDetail.setCreateDate(rs.getString(13));
				collectionDetail.setUpdateId(rs.getString(14));
				collectionDetail.setUpdateName(rs.getString(15));
				collectionDetail.setUpdateDate(rs.getString(16));
				collectionDetail.setCollectionId(rs.getString(17));
				collectionDetail.setMechanismId(rs.getString(18));
				list.add(collectionDetail);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
	public int saveCollectionRecord(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String[] sjlxc = collection.getContractId().split("!_");
			String icollectionSQL = "INSERT INTO crm_collection(id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id,department_id,department_name,customer_no)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icollectionSQL);

			ps.setString(1, collection.getId());
			ps.setString(2, df.format(collection.getCollectionDates()));
			ps.setString(3, collection.getCollectionAmount());
			if (collection.getCustomerId() == null) {
				ps.setString(4, "");
				ps.setString(5, "");
				ps.setString(22, null);
			} else {
				if (collection.getCustomerId().equals("")) {
					ps.setString(4, "");
					ps.setString(5, "");
					ps.setString(22, null);
				} else {
					String[] sjlx = collection.getCustomerId().split("!_");
					ps.setString(4, sjlx[0]);
					ps.setString(5, sjlx[1]);
					ps.setString(22, sjlx[2]);
				}
			}
			if (collection.getContractId() == null) {
				ps.setString(6, "");
				ps.setString(7, "");
			} else {
				if (collection.getContractId().equals("")) {
					ps.setString(6, "");
					ps.setString(7, "");
				} else {
					String[] sjlx = collection.getContractId().split("!_");
					ps.setString(6, sjlx[0]);
					ps.setString(7, sjlx[1]);
				}
			}
			ps.setString(8, collection.getPayTypeName());
			ps.setString(9, collection.getCollectionType());
			ps.setString(10, collection.getContractLeader());
			ps.setString(11, collection.getYs6());
			ps.setString(12, collection.getCreateId());
			ps.setString(13, collection.getCreateName());
			ps.setString(14, collection.getCreateDate());
			ps.setString(15, collection.getUpdateId());
			ps.setString(16, collection.getUpdateName());
			ps.setString(17, collection.getUpdateDate());
			ps.setString(18, collection.getPaymentCollectionPlanSubordinateId());
			ps.setString(19, collection.getMechanismId());
			ps.setString(20, collection.getDepartmentId());
			ps.setString(21, collection.getDepartmentName());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveCollection(Collection collection)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int saveCollectioncreateinvoicing(Invoicing invoicing) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String iinvoicingSQL = "INSERT INTO crm_invoicing(id,invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,department_id,department_name,mechanism_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iinvoicingSQL);
			ps.setString(1, invoicing.getId());
			ps.setString(2, df.format(invoicing.getInvoicingDate()));
			ps.setString(3, invoicing.getInvoicingContent());
			ps.setBigDecimal(4, invoicing.getInvoicingAmount());

			if (invoicing.getCustomerId() == null) {
				ps.setString(5, "");
				ps.setString(6, "");
			} else {
				if (invoicing.getCustomerId().equals("")) {
					ps.setString(5, "");
					ps.setString(6, "");
				} else {
					String[] sjlx = invoicing.getCustomerId().split("!_");
					ps.setString(5, sjlx[0]);
					ps.setString(6, sjlx[1]);
				}
			}
			if (invoicing.getContractId() == null) {
				ps.setString(7, "");
				ps.setString(8, "");
			} else {
				if (invoicing.getContractId().equals("")) {
					ps.setString(7, "");
					ps.setString(8, "");
				} else {
					String[] sjlx = invoicing.getContractId().split("!_");
					ps.setString(7, sjlx[0]);
					ps.setString(8, sjlx[1]);
				}
			}
			ps.setString(9, invoicing.getInvoiceType());
			ps.setString(10, invoicing.getInvoicingNum());
			ps.setString(11, invoicing.getContractLeader());
			ps.setString(12, invoicing.getRemark());
			ps.setString(13, invoicing.getCreateId());
			ps.setString(14, invoicing.getCreateName());
			ps.setString(15, invoicing.getCreateDate());
			ps.setString(16, invoicing.getUpdateId());
			ps.setString(17, invoicing.getUpdateName());
			ps.setString(18, invoicing.getUpdateDate());
			ps.setString(19, invoicing.getSysInvoicingTypeId());
			ps.setString(20, invoicing.getDepartmentId());
			ps.setString(21, invoicing.getDepartmentName());
			ps.setString(22, invoicing.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveCollectioncreateinvoicing(Invoicing invoicing)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int saveCollection(Collection collection, List<CollectionDetail> collectionDetails) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String[] sjlxc = collection.getContractId().split("!_");
			String sql = "SELECT collection_id FROM  crm_payment_collection_plan_subordinate  where 1=1 and contract_id=? order by create_date desc ";
			ps2 = conn.prepareStatement(sql);
			ps2.setString(1, sjlxc[0]);
			rs = ps2.executeQuery();
			String yjid = "";
			while (rs.next()) {
				yjid = rs.getString(1);
			}
			String dsql = "delete from crm_payment_collection_plan_subordinate where 1=1 and collection_id=?";
			ps3 = conn.prepareStatement(dsql);
			ps3.setString(1, yjid);
			ps3.executeUpdate();
			String dsql1 = "delete from crm_contract where 1=1 and id=?";
			ps4 = conn.prepareStatement(dsql1);
			ps4.setString(1, yjid);
			ps4.executeUpdate();
			String icollectionSQL = "INSERT INTO crm_payment_collection_plan(id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id,department_id,department_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(icollectionSQL);

			ps.setString(1, collection.getId());
			ps.setString(2, collection.getCollectionDate());
			ps.setString(3, collection.getCollectionAmount());
			if (collection.getCustomerId() == null) {
				ps.setString(4, "");
				ps.setString(5, "");
			} else {
				if (collection.getCustomerId().equals("")) {
					ps.setString(4, "");
					ps.setString(5, "");
				} else {
					String[] sjlx = collection.getCustomerId().split("!_");
					ps.setString(4, sjlx[0]);
					ps.setString(5, sjlx[1]);
				}
			}
			if (collection.getContractId() == null) {
				ps.setString(6, "");
				ps.setString(7, "");
			} else {
				if (collection.getContractId().equals("")) {
					ps.setString(6, "");
					ps.setString(7, "");
				} else {
					String[] sjlx = collection.getContractId().split("!_");
					ps.setString(6, sjlx[0]);
					ps.setString(7, sjlx[1]);
				}
			}
			ps.setString(8, collection.getPayTypeName());
			ps.setString(9, collection.getCollectionType());
			ps.setString(10, collection.getContractLeader());
			ps.setString(11, collection.getYs6());
			ps.setString(12, collection.getCreateId());
			ps.setString(13, collection.getCreateName());
			ps.setString(14, collection.getCreateDate());
			ps.setString(15, collection.getUpdateId());
			ps.setString(16, collection.getUpdateName());
			ps.setString(17, collection.getUpdateDate());
			ps.setString(18, collection.getPaymentCollectionPlanSubordinateId());
			ps.setString(19, collection.getMechanismId());
			ps.setString(20, collection.getDepartmentId());
			ps.setString(21, collection.getDepartmentName());
			rv = ps.executeUpdate();

			String idbdmxSQL = "insert into crm_payment_collection_plan_subordinate (id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,contract_id,mechanism_id,collection_id,contract_name,customer_id,customer_name,department_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps1 = conn.prepareStatement(idbdmxSQL);
			for (int i = 0; i < collectionDetails.size(); i++) {
				CollectionDetail collectionDetail = collectionDetails.get(i);
				ps1.setString(1, UUID.randomUUID().toString());
				ps1.setString(2, collectionDetail.getNumber());
				ps1.setString(3, collectionDetail.getCrmPaymentCollectionPlanId());
				ps1.setString(4, df.format(collectionDetail.getCollectionDate()));
				ps1.setString(5, collectionDetail.getCollectionProportion());
				ps1.setString(6, collectionDetail.getCollectionAmount());
				ps1.setString(7, collectionDetail.getRemark());
				ps1.setString(8, collectionDetail.getYs4());
				ps1.setString(9, collectionDetail.getYs5());
				ps1.setString(10, collectionDetail.getYs6());

				ps1.setString(11, collection.getCreateId());
				ps1.setString(12, collection.getCreateName());
				ps1.setString(13, collection.getCreateDate());
				ps1.setString(14, collection.getUpdateId());
				ps1.setString(15, collection.getUpdateName());
				ps1.setString(16, collection.getUpdateDate());

				String[] sjlx = collection.getContractId().split("!_");
				String[] sjlx1 = collection.getCustomerId().split("!_");
				ps1.setString(17, sjlx[0]);
				ps1.setString(18, collection.getMechanismId());
				ps1.setString(19, collection.getId());
				ps1.setString(20, sjlx[1]);
				ps1.setString(21, sjlx1[0]);
				ps1.setString(22, sjlx1[1]);
				ps1.setString(23, collection.getDepartmentId());
				ps1.addBatch();
			}
			ps1.executeBatch();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveCollection(Collection collection)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(rs);
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
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public Collection view(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id,department_id,department_name FROM  crm_payment_collection_plan  where 1=1 and id like ? order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, collection.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				collection = new Collection();
				collection.setId(rs.getString(1));
				collection.setCollectionDate(rs.getString(2));
				collection.setCollectionAmount(rs.getString(3));
				collection.setCustomerId(rs.getString(4) + "!_" + rs.getString(5));
				collection.setCustomerName(rs.getString(5));
				collection.setContractId(rs.getString(6) + "!_" + rs.getString(7));
				collection.setContractName(rs.getString(7));
				collection.setPayTypeName(rs.getString(8));
				collection.setCollectionType(rs.getString(9));
				collection.setContractLeader(rs.getString(10));
				collection.setYs6(rs.getString(11));
				collection.setCreateId(rs.getString(12));
				collection.setCreateName(rs.getString(13));
				collection.setCreateDate(rs.getString(14));
				collection.setUpdateId(rs.getString(15));
				collection.setUpdateName(rs.getString(16));
				collection.setUpdateDate(rs.getString(17));
				collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
				collection.setMechanismId(rs.getString(19));
				collection.setDepartmentId(rs.getString(20));
				collection.setDepartmentName(rs.getString(21));
			}
			sql = "SELECT id,number,crm_payment_collection_plan_id,collection_date,collection_proportion,collection_amount,remark,ys4,ys5,ys6,create_id,create_name,create_date,update_id,update_name,update_date,collection_id,mechanism_id,contract_name,customer_name FROM  crm_payment_collection_plan_subordinate  where 1=1 and collection_id like ? order by create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, collection.getId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				CollectionDetail collectionDetail = new CollectionDetail();
				collectionDetail.setId(rs1.getString(1));
				collectionDetail.setNumber(rs1.getString(2));
				collectionDetail.setCrmPaymentCollectionPlanId(rs1.getString(3));
				collectionDetail.setCollectionDate(rs1.getDate(4));
				collectionDetail.setDetailDate(rs1.getString(4));
				collectionDetail.setCollectionProportion(rs1.getString(5));
				collectionDetail.setCollectionAmount(rs1.getString(6));
				collectionDetail.setRemark(rs1.getString(7));
				collectionDetail.setYs4(rs1.getString(8));
				collectionDetail.setYs5(rs1.getString(9));
				collectionDetail.setYs6(rs1.getString(10));
				collectionDetail.setCreateId(rs1.getString(11));
				collectionDetail.setCreateName(rs1.getString(12));
				collectionDetail.setCreateDate(rs1.getString(13));
				collectionDetail.setUpdateId(rs1.getString(14));
				collectionDetail.setUpdateName(rs1.getString(15));
				collectionDetail.setUpdateDate(rs1.getString(16));
				collectionDetail.setCollectionId(rs1.getString(17));
				collectionDetail.setMechanismId(rs1.getString(18));
				collectionDetail.setContractName(rs1.getString(19));
				collectionDetail.setCustomerName(rs1.getString(20));
				list.add(collectionDetail);
			}
			collection.setList(list);
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
		return collection;
	}

	@Override
	public Collection viewRecord(Collection collection) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		String username = collection.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			sql = "SELECT id,collection_date,collection_amount,customer_id,customer_name,contract_id,contract_name,pay_type_name,collection_type,contract_leader,ys6,create_id,create_name,create_date,update_id,update_name,update_date,payment_collection_plan_subordinate_id,mechanism_id FROM  crm_collection  where 1=1  and id = ?  order by create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, collection.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				collection = new Collection();
				collection.setId(rs.getString(1));
				collection.setCollectionDates(rs.getDate(2));
				collection.setCollectionAmount(rs.getString(3));
				collection.setCustomerId(rs.getString(4));
				collection.setCustomerName(rs.getString(5));
				collection.setContractId(rs.getString(6));
				collection.setContractName(rs.getString(7));
				collection.setPayTypeName(rs.getString(8));
				collection.setCollectionType(rs.getString(9));
				collection.setContractLeader(rs.getString(10));
				collection.setYs6(rs.getString(11));
				collection.setCreateId(rs.getString(12));
				collection.setCreateName(rs.getString(13));
				collection.setCreateDate(rs.getString(14));
				collection.setUpdateId(rs.getString(15));
				collection.setUpdateName(rs.getString(16));
				collection.setUpdateDate(rs.getString(17));
				collection.setPaymentCollectionPlanSubordinateId(rs.getString(18));
				collection.setMechanismId(rs.getString(19));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
		return collection;
	}

	@Override
	public Invoicing viewInvoicing(Invoicing invoicing) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CollectionDetail> list = new ArrayList<CollectionDetail>();
		String username = invoicing.getCreateName();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			sql = "SELECT invoicing_date,invoicing_content,invoicing_amount,customer_id,customer_name,contract_id,contract_name,invoice_type,invoicing_num,contract_leader,remark,create_id,create_name,create_date,update_id,update_name,update_date,sys_invoicing_type_id,id FROM  crm_invoicing  where 1=1  and id='"
					+ invoicing.getId() + "'   order by create_date desc ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				invoicing = new Invoicing();
				invoicing.setInvoicingDate(rs.getDate(1));
				invoicing.setInvoicingContent(rs.getString(2));
				invoicing.setInvoicingAmount(rs.getBigDecimal(3));
				invoicing.setCustomerId(rs.getString(4));
				invoicing.setCustomerName(rs.getString(5));
				invoicing.setContractId(rs.getString(6));
				invoicing.setContractName(rs.getString(7));
				invoicing.setInvoiceType(rs.getString(8));
				invoicing.setInvoicingNum(rs.getString(9));
				invoicing.setContractLeader(rs.getString(10));
				invoicing.setRemark(rs.getString(11));
				invoicing.setCreateId(rs.getString(12));
				invoicing.setCreateName(rs.getString(13));
				invoicing.setCreateDate(rs.getString(14));
				invoicing.setUpdateId(rs.getString(15));
				invoicing.setUpdateName(rs.getString(16));
				invoicing.setUpdateDate(rs.getString(17));
				invoicing.setSysInvoicingTypeId(rs.getString(18));
				invoicing.setId(rs.getString(19));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Collection> getItems(Collection collection)" + (t2 - t1) + " ms");
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
		return invoicing;
	}
}