package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.primefaces.avalon.domain.Document;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.MechanismDao;
import dafengqi.yunxiang.model.Mechanism;
import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("mechanismDao")
public class MechanismDaoHibernate extends GenericDaoHibernate<Mechanism, Long> implements MechanismDao {
	protected java.sql.Connection userConn;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final boolean isConnSupplied = (userConn != null);

	public MechanismDaoHibernate() {
		super(Mechanism.class);
	} 

	@Override
	public TreeNode getItems(Mechanism mechanism) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		TreeNode list = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			list.setExpanded(false);
			String sqls = "select id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name from sys_mechanism where length(id)<=8 and IFNULL(isDelete,'否') !='是'  and id like ? order by update_date asc";
			ps = conn.prepareStatement(sqls);
			ps.setString(1, mechanism.getId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				TreeNode l1Node = new DefaultTreeNode(new Mechanism(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4) + "!_" + rs.getString(19), rs.getString(5), rs.getString(6), rs.getString(7),
						rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
						rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17),
						rs.getString(18), rs.getString(19)), list);
				String sqlss = "select id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name from sys_mechanism where mechanism_id='"
						+ rs.getString(1) + "'  order by update_date asc ";
				ps1 = conn.prepareStatement(sqlss);
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TreeNode l2Node = new DefaultTreeNode(new Mechanism(rs1.getString(1), rs1.getString(2),
							rs1.getString(3), rs1.getString(4) + "!_" + rs1.getString(19), rs1.getString(5),
							rs1.getString(6), rs1.getString(7), rs1.getString(8), rs1.getString(9), rs1.getString(10),
							rs1.getString(11), rs1.getString(12), rs1.getString(13), rs1.getString(14),
							rs1.getString(15), rs1.getString(16), rs1.getString(17), rs1.getString(18),
							rs.getString(19)), l1Node);
					String sqlsss = "select id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name from sys_mechanism where mechanism_id='"
							+ rs1.getString(1) + "'   order by update_date asc ";
					ps2 = conn.prepareStatement(sqlsss);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						new DefaultTreeNode(new Mechanism(rs2.getString(1), rs2.getString(2), rs2.getString(3),
								rs2.getString(4) + "!_" + rs2.getString(19), rs2.getString(5), rs2.getString(6),
								rs2.getString(7), rs2.getString(8), rs2.getString(9), rs2.getString(10),
								rs2.getString(11), rs2.getString(12), rs2.getString(13), rs2.getString(14),
								rs2.getString(15), rs2.getString(16), rs2.getString(17), rs2.getString(18),
								rs.getString(19)), l2Node);
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Mechanism> getItems(Mechanism mechanism)" + (t2 - t1) + " ms");
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
		return list;
	}

	@Override
	public Mechanism edit(Mechanism mechanism) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Mechanism> list = new ArrayList<Mechanism>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String sql = "SELECT id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name,post,zip_code,enable_date,base_currency,number_of_decimal_places,price_of_decimal_places,inventory_valuation_method,enable_negative_inventory_not_allowed,enable_taxes,enable_whether_the_commodity_price_includes_tax,enable_auxiliary_properties,enable_smart_micro_store,enable_serial_number,enable_batch_shelf_life_management,enable_earliest_batch_of_automatic_delivery,enable_auto_fill_settlement_amount,enable_separate_warehouse_accounting,enable_retail,enable_cannot_update_order_date,taxe_price FROM  sys_mechanism   where 1=1 and id=? order by create_id desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mechanism.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				mechanism = new Mechanism();
				mechanism.setId(rs.getString(1));
				mechanism.setName(rs.getString(2));
				mechanism.setFullName(rs.getString(3));
				mechanism.setMechanismType(rs.getString(4) + "!_" + rs.getString(19));
				mechanism.setContacts(rs.getString(5));
				mechanism.setPhonenumber(rs.getString(6));
				mechanism.setTelephone(rs.getString(7));
				mechanism.setAddress(rs.getString(8));
				mechanism.setFax(rs.getString(9));
				mechanism.setEmail(rs.getString(10));
				mechanism.setRemarks(rs.getString(11));
				mechanism.setCreateId(rs.getString(12));
				mechanism.setCreateDate(rs.getString(13));
				mechanism.setUpdateId(rs.getString(14));
				mechanism.setUpdateDate(rs.getString(15));
				mechanism.setStatus(rs.getString(16));
				mechanism.setMechanismId(rs.getString(17));
				mechanism.setMechanismName(rs.getString(18));
				mechanism.setMechanismTypeName(rs.getString(19));
				mechanism.setPost(rs.getString(20));
				mechanism.setZipCode(rs.getString(21));
				mechanism.setEnableDate(rs.getDate(22));
				mechanism.setBaseCurrency(rs.getString(23));
				mechanism.setNumberOfDecimalPlaces(rs.getBigDecimal(24));
				mechanism.setPriceOfDecimalPlaces(rs.getBigDecimal(25));
				mechanism.setInventoryValuationMethod(rs.getString(26));
				mechanism.setEnableNegativeInventoryNotAllowed(rs.getBoolean(27));
				mechanism.setEnableTaxes(rs.getBoolean(28));
				mechanism.setEnableWhetherTheCommodityPriceIncludesTax(rs.getBoolean(29));
				mechanism.setEnableAuxiliaryProperties(rs.getBoolean(30));
				mechanism.setEnableSmartMicroStore(rs.getBoolean(31));
				mechanism.setEnableSerialNumber(rs.getBoolean(32));
				mechanism.setEnableBatchShelfLifeManagement(rs.getBoolean(33));
				mechanism.setEnableEarliestBatchOfAutomaticDelivery(rs.getBoolean(34));
				mechanism.setEnableAutoFillSettlementAmount(rs.getBoolean(35));
				mechanism.setEnableSeparateWarehouseAccounting(rs.getBoolean(36));
				mechanism.setEnableRetail(rs.getBoolean(37));
				mechanism.setEnableCannotUpdateOrderDate(rs.getBoolean(38));
				mechanism.setTaxePrice(rs.getBigDecimal(39));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Mechanism> getItems(Mechanism mechanism)" + (t2 - t1) + " ms");
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
		return mechanism;
	}

	public TreeNode createMls(Mechanism mechanism) {
		long t1 = System.currentTimeMillis();
		TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			root.setExpanded(false);
			String sqls = "";
			if (mechanism.getCreateId().equals("admin")) {
				sqls = "select id,name from sys_mechanism where length(id)<=8 order by update_date asc";
			} else {
				sqls = "select id,name from sys_mechanism where length(id)<=8 and id like '"
						+ mechanism.getMechanismId() + "%' order by update_date asc";
			}
			ps = conn.prepareStatement(sqls);
			rs = ps.executeQuery();
			while (rs.next()) {
				TreeNode l1Node = new DefaultTreeNode(
						new Document("" + rs.getString(2) + "-" + rs.getString(1) + "", rs.getString(2), "1级"), root);
				String sqlss = "select id,name from sys_mechanism where mechanism_id='" + rs.getString(1)
						+ "'  order by update_date asc ";
				ps1 = conn.prepareStatement(sqlss);
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TreeNode l2Node = new DefaultTreeNode(
							new Document("" + rs1.getString(2) + "-" + rs1.getString(1) + "", rs1.getString(2), "2级"),
							l1Node);
					String sqlsss = "select id,name from sys_mechanism where mechanism_id='" + rs1.getString(1)
							+ "'   order by update_date asc ";
					ps2 = conn.prepareStatement(sqlsss);
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						new DefaultTreeNode(new Document("" + rs2.getString(2) + "-" + rs2.getString(1) + "",
								rs2.getString(2), "3级"), l2Node);
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(" public TreeNode createMls()：" + (t2 - t1) + " ms");
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
		root.setExpanded(true);
		return root;
	}

	@Override
	public int saveMechanism(Mechanism mechanism) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps4 = null;
		PreparedStatement psd = null;
		PreparedStatement ps1 = null;
		PreparedStatement psd1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps9 = null;
		PreparedStatement ps11 = null;
		ResultSet rs1 = null;
		ResultSet rsd1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs9 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String jgaa = mechanism.getMechanismId() == null ? "" : mechanism.getMechanismId();
			String jd = jgaa;
			String username = mechanism.getCreateId();
			if (jgaa.length() == 8 && !username.equals("admin")) {
				String sql = "select id from sys_mechanism where 1=1 and id like '" + jgaa + "%"
						+ "' order by id desc limit 0,1";
				ps1 = conn.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				String jgid = "";
				String fajgid = "";
				String newid = "";
				int ssjg = 0;
				if (rs1.next()) {
					fajgid = rs1.getString(1);
					if (fajgid.length() == 8) {
						newid = jgaa + "0001";
						String sqlS = "select id from sys_mechanism where 1=1 and id like '" + jgaa + "%" + "' ";
						ps2 = conn.prepareStatement(sqlS);
						rs2 = ps2.executeQuery();
						if (rs2.next()) {
							fajgid = rs2.getString(1);
						}
					} else {
						String sql1 = "select id from sys_mechanism where 1=1 and id = '" + jgaa
								+ "' order by id desc limit 0,1";
						ps3 = conn.prepareStatement(sql1);
						rs3 = ps3.executeQuery();
						jgid = rs1.getString(1);
						int jl = jgid.length();
						String ljl = "";
						int newjgid = Integer.valueOf(fajgid) + 1;
						int len = jl - String.valueOf(newjgid).length();
						for (int k = 0; k < len; k++) {
							ljl += "0";
						}
						newid = ljl + newjgid;
					}

				} else {
					newid = jgaa + "0001";
					String sqlS = "select id from sys_mechanism where 1=1 and id like '" + jgaa + "%" + "' ";
					ps2 = conn.prepareStatement(sqlS);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						fajgid = rs2.getString(1);
					}
				}
				String sqlz = "select id from sys_department where 1=1  and (department_id ='' or department_id is null) order by id desc limit 0,1";
				psd1 = conn.prepareStatement(sqlz);
				rsd1 = psd1.executeQuery();
				String bmid = "";
				String jgids = "";
				if (rsd1.next()) {
					jgids = rsd1.getString(1);
					int jl = jgids.length();
					String ljl = "";
					int newjgid = Integer.valueOf(jgids) + 1;
					int len = jl - String.valueOf(newjgid).length();
					for (int k = 0; k < len; k++) {
						ljl += "0";
					}
					bmid = ljl + newjgid;
				} else {
					bmid = "001";
				}
				String idepartmentSQL = "INSERT INTO sys_department(id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date)VALUES(?,?,?,?,?,?,?,?,?,?)";
				psd = conn.prepareStatement(idepartmentSQL);
				psd.setString(1, bmid);
				psd.setString(2, mechanism.getName());
				psd.setString(3, newid);
				psd.setString(4, mechanism.getName());
				psd.setString(5, "");
				psd.setString(6, "");
				psd.setString(7, mechanism.getCreateId());
				psd.setString(8, mechanism.getCreateDate());
				psd.setString(9, mechanism.getUpdateId());
				psd.setString(10, mechanism.getUpdateDate());
				psd.executeUpdate();
				String userSql = "insert into app_user (username,password,password_hint,first_name,last_name,phone_number,website,account_expired,account_locked,credentials_expired,account_enabled,mechanism_id,mechanism_name,department_id,department_name,app_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps9 = conn.prepareStatement(userSql);
				ps9.setString(1, mechanism.getPhonenumber());
				ps9.setString(2, "$2a$10$oehf8xEY6wN3CXmo6PvofOAL6uvVYhWCLAUPhb3VNXPKDo1t/V1hS");
				ps9.setString(3, "yunxiang");
				ps9.setString(4, mechanism.getName());
				ps9.setString(5, mechanism.getName());
				ps9.setString(6, mechanism.getPhonenumber());
				ps9.setString(7, "");
				ps9.setBoolean(8, false);
				ps9.setBoolean(9, false);
				ps9.setBoolean(10, false);
				ps9.setBoolean(11, true);
				ps9.setString(12, newid);
				ps9.setString(13, mechanism.getName());
				ps9.setString(14, bmid);
				ps9.setString(15, mechanism.getName());
				ps9.setString(16, mechanism.getAppId());
				ps9.executeUpdate();
				User user = new User();
				String cgddSQL = "SELECT a.id FROM app_user a  where a.username=?";
				ps11 = conn.prepareStatement(cgddSQL);
				ps11.setString(1, mechanism.getPhonenumber());
				rs9 = ps11.executeQuery();
				while (rs9.next()) {
					user = new User();
					user.setId(rs9.getLong(1));
				}
				String roleSql = "insert into user_role (user_id,role_id) values(?,-3)";
				ps11 = conn.prepareStatement(roleSql);
				ps11.setLong(1, user.getId());
				ps11.executeUpdate();
				String[] type = mechanism.getMechanismType().split("!_");
				String imechanismSQL = "INSERT INTO sys_mechanism(id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name,zip_code,enable_date,base_currency,number_of_decimal_places,price_of_decimal_places,inventory_valuation_method,enable_negative_inventory_not_allowed,enable_taxes,enable_whether_the_commodity_price_includes_tax,enable_auxiliary_properties,enable_smart_micro_store,enable_serial_number,enable_batch_shelf_life_management,enable_earliest_batch_of_automatic_delivery,enable_auto_fill_settlement_amount,enable_separate_warehouse_accounting,enable_retail,enable_cannot_update_order_date,app_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(imechanismSQL);
				ps.setString(1, newid);
				ps.setString(2, mechanism.getName());
				ps.setString(3, mechanism.getFullName());
				ps.setString(4, "");// type[0]
				ps.setString(5, mechanism.getContacts());
				ps.setString(6, mechanism.getPhonenumber());
				ps.setString(7, mechanism.getTelephone());
				ps.setString(8, mechanism.getAddress());
				ps.setString(9, mechanism.getFax());
				ps.setString(10, mechanism.getEmail());
				ps.setString(11, mechanism.getRemarks());
				ps.setString(12, mechanism.getCreateId());
				ps.setString(13, mechanism.getCreateDate());
				ps.setString(14, mechanism.getUpdateId());
				ps.setString(15, mechanism.getUpdateDate());
				ps.setString(16, "启用");
				ps.setString(17, mechanism.getMechanismId());
				ps.setString(18, mechanism.getMechanismName());
				ps.setString(19, "");// type[1]
				ps.setString(20, mechanism.getZipCode());
				ps.setString(21, df.format(mechanism.getEnableDate()));
				ps.setString(22, mechanism.getBaseCurrency());
				ps.setBigDecimal(23, mechanism.getNumberOfDecimalPlaces());
				ps.setBigDecimal(24, mechanism.getPriceOfDecimalPlaces());
				ps.setString(25, mechanism.getInventoryValuationMethod());
				ps.setBoolean(26, mechanism.getEnableNegativeInventoryNotAllowed());
				ps.setBoolean(27, mechanism.getEnableTaxes());
				ps.setBoolean(28, mechanism.getEnableWhetherTheCommodityPriceIncludesTax());
				ps.setBoolean(29, mechanism.getEnableAuxiliaryProperties());
				ps.setBoolean(30, mechanism.getEnableSmartMicroStore());
				ps.setBoolean(31, mechanism.getEnableSerialNumber());
				ps.setBoolean(32, mechanism.getEnableBatchShelfLifeManagement());
				ps.setBoolean(33, mechanism.getEnableEarliestBatchOfAutomaticDelivery());
				ps.setBoolean(34, mechanism.getEnableAutoFillSettlementAmount());
				ps.setBoolean(35, mechanism.getEnableSeparateWarehouseAccounting());
				ps.setBoolean(36, mechanism.getEnableRetail());
				ps.setBoolean(37, mechanism.getEnableCannotUpdateOrderDate());
				ps.setString(38, mechanism.getAppId());

				rv = ps.executeUpdate();
				String izdsjSQL = "INSERT INTO sys_dict(dict_code,dict_label,dict_value,tree_sort,is_sys,dict_type,remarks,parent_code,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps4 = conn.prepareStatement(izdsjSQL);
				ps4.setString(1, UUID.randomUUID().toString());
				ps4.setString(2, mechanism.getName());
				ps4.setString(3, mechanism.getName());
				ps4.setString(4, "1");
				ps4.setString(5, "是");
				ps4.setString(6, "");
				ps4.setString(7, "");
				ps4.setString(8, "warehouse");
				ps4.setString(9, mechanism.getCreateId());
				ps4.setString(10, df.format(new Date()));
				ps4.setString(11, "");
				ps4.setString(12, "");
				ps4.setString(13, "启用");
				ps4.setString(14, newid);
				ps4.setString(15, mechanism.getName());
				ps4.executeUpdate();
			} else {
				String sql = "select id from sys_mechanism where 1=1  and (mechanism_id ='' or mechanism_id is null) order by id desc limit 0,1";
				ps1 = conn.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				String jgid = "";
				String newid = "";
				if (rs1.next()) {
					jgid = rs1.getString(1);
					int jl = jgid.length();
					String ljl = "";
					int newjgid = Integer.valueOf(jgid) + 1;
					int len = jl - String.valueOf(newjgid).length();
					for (int k = 0; k < len; k++) {
						ljl += "0";
					}
					newid = ljl + newjgid;
				} else {
					newid = "00000001";
				}

				String sqlz = "select id from sys_department where 1=1  and (department_id ='' or department_id is null) order by id desc limit 0,1";
				psd1 = conn.prepareStatement(sqlz);
				rsd1 = psd1.executeQuery();
				String bmid = "";
				String jgids = "";
				if (rsd1.next()) {
					jgids = rsd1.getString(1);
					int jl = jgids.length();
					String ljl = "";
					int newjgid = Integer.valueOf(jgids) + 1;
					int len = jl - String.valueOf(newjgid).length();
					for (int k = 0; k < len; k++) {
						ljl += "0";
					}
					bmid = ljl + newjgid;
				} else {
					bmid = "001";
				}
				String idepartmentSQL = "INSERT INTO sys_department(id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date)VALUES(?,?,?,?,?,?,?,?,?,?)";
				psd = conn.prepareStatement(idepartmentSQL);
				psd.setString(1, bmid);
				psd.setString(2, mechanism.getName());
				psd.setString(3, newid);
				psd.setString(4, mechanism.getName());
				psd.setString(5, "");
				psd.setString(6, "");
				psd.setString(7, mechanism.getCreateId());
				psd.setString(8, mechanism.getCreateDate());
				psd.setString(9, mechanism.getUpdateId());
				psd.setString(10, mechanism.getUpdateDate());
				psd.executeUpdate();
				String userSql = "insert into app_user (username,password,password_hint,first_name,last_name,phone_number,website,account_expired,account_locked,credentials_expired,account_enabled,mechanism_id,mechanism_name,department_id,department_name,app_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps9 = conn.prepareStatement(userSql);
				ps9.setString(1, mechanism.getPhonenumber());
				ps9.setString(2, "$2a$10$oehf8xEY6wN3CXmo6PvofOAL6uvVYhWCLAUPhb3VNXPKDo1t/V1hS");
				ps9.setString(3, "yunxiang");
				ps9.setString(4, mechanism.getName());
				ps9.setString(5, mechanism.getName());
				ps9.setString(6, mechanism.getPhonenumber());
				ps9.setString(7, "");
				ps9.setBoolean(8, false);
				ps9.setBoolean(9, false);
				ps9.setBoolean(10, false);
				ps9.setBoolean(11, true);
				ps9.setString(12, newid);
				ps9.setString(13, mechanism.getName());
				ps9.setString(14, bmid);
				ps9.setString(15, mechanism.getName());
				ps9.setString(16, mechanism.getAppId());
				ps9.executeUpdate();
				User user = new User();
				String cgddSQL = "SELECT a.id FROM app_user a  where a.username=?";
				ps11 = conn.prepareStatement(cgddSQL);
				ps11.setString(1, mechanism.getPhonenumber());
				rs9 = ps11.executeQuery();
				while (rs9.next()) {
					user = new User();
					user.setId(rs9.getLong(1));
				}

				String roleSql = "insert into user_role (user_id,role_id) values(?,-3)";
				ps11 = conn.prepareStatement(roleSql);
				ps11.setLong(1, user.getId());
				ps11.executeUpdate();
				String[] type = mechanism.getMechanismType().split("!_");
				String imechanismSQL = "INSERT INTO sys_mechanism(id,name,full_name,mechanism_type,contacts,phonenumber,telephone,address,fax,email,remarks,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name,mechanism_type_name,app_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = conn.prepareStatement(imechanismSQL);
				ps.setString(1, newid);
				ps.setString(2, mechanism.getName());
				ps.setString(3, mechanism.getFullName());
				ps.setString(4, type[0]);
				ps.setString(5, mechanism.getContacts());
				ps.setString(6, mechanism.getPhonenumber());
				ps.setString(7, mechanism.getTelephone());
				ps.setString(8, mechanism.getAddress());
				ps.setString(9, mechanism.getFax());
				ps.setString(10, mechanism.getEmail());
				ps.setString(11, mechanism.getRemarks());
				ps.setString(12, mechanism.getCreateId());
				ps.setString(13, mechanism.getCreateDate());
				ps.setString(14, mechanism.getUpdateId());
				ps.setString(15, mechanism.getUpdateDate());
				ps.setString(16, "启用");
				ps.setString(17, mechanism.getMechanismId());
				ps.setString(18, mechanism.getMechanismName());
				ps.setString(19, type[1]);
				ps.setString(20, mechanism.getAppId());
				rv = ps.executeUpdate();
				String izdsjSQL = "INSERT INTO sys_dict(dict_code,dict_label,dict_value,tree_sort,is_sys,dict_type,remarks,parent_code,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps4 = conn.prepareStatement(izdsjSQL);
				ps4.setString(1, UUID.randomUUID().toString());
				ps4.setString(2, mechanism.getName());
				ps4.setString(3, mechanism.getName());
				ps4.setString(4, "1");
				ps4.setString(5, "是");
				ps4.setString(6, "");
				ps4.setString(7, "");
				ps4.setString(8, "warehouse");
				ps4.setString(9, mechanism.getCreateId());
				ps4.setString(10, df.format(new Date()));
				ps4.setString(11, "");
				ps4.setString(12, "");
				ps4.setString(13, "启用");
				ps4.setString(14, newid);
				ps4.setString(15, mechanism.getName());
				ps4.executeUpdate();
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveMechanism(Mechanism mechanism)" + (t2 - t1) + " ms");
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
	public int update(Mechanism mechanism) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String umechanismSQL = "update sys_mechanism set name=?,full_name=?,mechanism_type=?,contacts=?,phonenumber=?,telephone=?,address=?,fax=?,email=?,remarks=?,update_id=?,update_date=?,mechanism_id=?,mechanism_name=?,mechanism_type_name=?,post=?,zip_code=?,enable_date=?,base_currency=?,number_of_decimal_places=?,price_of_decimal_places=?,inventory_valuation_method=?,enable_negative_inventory_not_allowed=?,enable_taxes=?,enable_whether_the_commodity_price_includes_tax=?,enable_auxiliary_properties=?,enable_smart_micro_store=?,enable_serial_number=?,enable_batch_shelf_life_management=?,enable_earliest_batch_of_automatic_delivery=?,enable_auto_fill_settlement_amount=?,enable_separate_warehouse_accounting=?,enable_retail=?,enable_cannot_update_order_date=?,taxe_price=? where 1=1 and id=?";
			ps = conn.prepareStatement(umechanismSQL);
			ps.setString(1, mechanism.getName());
			ps.setString(2, mechanism.getFullName());
			ps.setString(3, "");
			ps.setString(4, mechanism.getContacts());
			ps.setString(5, mechanism.getPhonenumber());
			ps.setString(6, mechanism.getTelephone());
			ps.setString(7, mechanism.getAddress());
			ps.setString(8, mechanism.getFax());
			ps.setString(9, mechanism.getEmail());
			ps.setString(10, mechanism.getRemarks());
			ps.setString(11, mechanism.getUpdateId());
			ps.setString(12, mechanism.getUpdateDate());
			ps.setString(13, mechanism.getMechanismId());
			ps.setString(14, mechanism.getMechanismName());
			ps.setString(15, "");
			ps.setString(16, mechanism.getPost());

			ps.setString(17, mechanism.getZipCode());
			ps.setString(18, df.format(mechanism.getEnableDate()));
			ps.setString(19, mechanism.getBaseCurrency());
			ps.setBigDecimal(20, mechanism.getNumberOfDecimalPlaces());
			ps.setBigDecimal(21, mechanism.getPriceOfDecimalPlaces());
			ps.setString(22, mechanism.getInventoryValuationMethod());
			ps.setBoolean(23, mechanism.getEnableNegativeInventoryNotAllowed());
			ps.setBoolean(24, mechanism.getEnableTaxes());
			ps.setBoolean(25, mechanism.getEnableWhetherTheCommodityPriceIncludesTax());
			ps.setBoolean(26, mechanism.getEnableAuxiliaryProperties());
			ps.setBoolean(27, mechanism.getEnableSmartMicroStore());
			ps.setBoolean(28, mechanism.getEnableSerialNumber());
			ps.setBoolean(29, mechanism.getEnableBatchShelfLifeManagement());
			ps.setBoolean(30, mechanism.getEnableEarliestBatchOfAutomaticDelivery());
			ps.setBoolean(31, mechanism.getEnableAutoFillSettlementAmount());
			ps.setBoolean(32, mechanism.getEnableSeparateWarehouseAccounting());
			ps.setBoolean(33, mechanism.getEnableRetail());
			ps.setBoolean(34, mechanism.getEnableCannotUpdateOrderDate());
			ps.setBigDecimal(35, mechanism.getTaxePrice());

			ps.setString(36, mechanism.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateMechanism(mechanism mechanism)" + (t2 - t1) + " ms");
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

}