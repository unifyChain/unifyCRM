package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.DictDao;
import dafengqi.yunxiang.model.Dict;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("dictDao")
public class DictDaoHibernate extends GenericDaoHibernate<Dict, Long> implements DictDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public DictDaoHibernate() {
		super(Dict.class);
	}

	@Override
	public List<Dict> getItems(Dict dict) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Dict> list = new ArrayList<Dict>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.dict_code,a.dict_label,a.dict_value,a.tree_sort,a.is_sys,a.dict_type,a.remarks,a.parent_code,a.create_id,a.create_date,a.update_id,a.update_date,a.status FROM  sys_dict a   where 1=1 and a.parent_code=? and a.mechanism_id like ? order by a.create_id desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dict.getParentCode());
			ps.setString(2, dict.getMechanismId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				dict = new Dict();
				dict.setDictCode(rs.getString(1));
				dict.setDictLabel(rs.getString(2));
				dict.setDictValue(rs.getString(3));
				dict.setTreeSort(rs.getString(4));
				dict.setIsSys(rs.getString(5));
				dict.setDictType(rs.getString(6));
				dict.setRemarks(rs.getString(7));
				dict.setParentCode(rs.getString(8));
				dict.setCreateId(rs.getString(9));
				dict.setCreateDate(rs.getString(10));
				dict.setUpdateBy(rs.getString(11));
				dict.setUpdateDate(rs.getString(12));
				dict.setStatus(rs.getString(13));
				list.add(dict);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Dict> getItems(Dict dict)" + (t2 - t1) + " ms");
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
	public List<Dict> getItemsByDictType(Dict dict) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Dict> list = new ArrayList<Dict>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			if (dict.getDictType().equals("support")) {
				String sql = "SELECT a.dict_code,a.dict_label FROM sys_dict a where 1=1 and a.parent_code=? and a.create_id='admin'  and a.status='启用'  order by a.create_id desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dict.getDictType());
				rs = ps.executeQuery();
				while (rs.next()) {
					dict = new Dict();
					dict.setDictCode(rs.getString(1));
					dict.setDictLabel(rs.getString(2));
					list.add(dict);
				}
			} else {
				String sql = "SELECT a.dict_code,a.dict_label FROM sys_dict a where 1=1 and a.parent_code=? and a.mechanism_id=?  and a.status='启用'  order by a.create_id desc ";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dict.getDictType());
				ps.setString(2, dict.getMechanismId());
				rs = ps.executeQuery();
				while (rs.next()) {
					dict = new Dict();
					dict.setDictCode(rs.getString(1));
					dict.setDictLabel(rs.getString(2));
					list.add(dict);
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Dict> getItemsByDictType(Dict dict)" + (t2 - t1) + " ms");
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
	public int saveDict(Dict dict) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String idictSQL = "INSERT INTO sys_dict(dict_code,dict_label,dict_value,tree_sort,is_sys,dict_type,remarks,parent_code,create_id,create_date,update_id,update_date,status,mechanism_id,mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(idictSQL);
			ps.setString(1, dict.getDictCode());
			ps.setString(2, dict.getDictLabel());
			ps.setString(3, dict.getDictValue());
			ps.setString(4, dict.getTreeSort());
			ps.setString(5, dict.getIsSys());
			ps.setString(6, dict.getDictType());
			ps.setString(7, dict.getRemarks());
			ps.setString(8, dict.getParentCode());
			ps.setString(9, dict.getCreateId());
			ps.setString(10, dict.getCreateDate());
			ps.setString(11, dict.getUpdateBy());
			ps.setString(12, dict.getUpdateDate());
			ps.setString(13, "启用");
			ps.setString(14, dict.getMechanismId());
			ps.setString(15, dict.getMechanismName());

			rv = ps.executeUpdate();

			if (dict.getParentCode().equals("warehouse")) {
				String sProductSql = "SELECT id,bar_code,title,category_id,category_name,'','',0,IFNULL(retail_price,0),ifnull(membership_price,0),ifnull(purchase_price,0),brand_id,brand_name,'','',0,0,0,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,''  from product   where 1=1 and mechanism_id = ? ";
				ps1 = conn.prepareStatement(sProductSql);
				ps1.setString(1, dict.getMechanismId());
				rs = ps1.executeQuery();
				while (rs.next()) {
					String iProductDetailSQL = "INSERT INTO productDetail(product_id,bar_code,product_name,category_id,category_name,warehouse_id,warehouse_name,stock,retail_price,membership_price,purchase_price,brand_id,brand_name,sub_unit,unit,unit_cost,opening_quantity,opening_total_price,mechanism_id,mechanism_name,create_id,create_name,create_date,update_id,update_name,update_date,remarks)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					ps2 = conn.prepareStatement(iProductDetailSQL);
					ps2.setString(1, rs.getString(1));
					ps2.setString(2, rs.getString(2));
					ps2.setString(3, rs.getString(3));
					ps2.setString(4, rs.getString(4));
					ps2.setString(5, rs.getString(5));
					ps2.setString(6, dict.getDictCode());
					ps2.setString(7, dict.getDictLabel());
					ps2.setString(8, rs.getString(8));
					ps2.setString(9, rs.getString(9));
					ps2.setString(10, rs.getString(10));
					ps2.setString(11, rs.getString(11));
					ps2.setString(12, rs.getString(12));
					ps2.setString(13, rs.getString(13));
					ps2.setString(14, rs.getString(14));
					ps2.setString(15, rs.getString(15));
					ps2.setString(16, rs.getString(16));
					ps2.setString(17, rs.getString(17));
					ps2.setString(18, rs.getString(18));
					ps2.setString(19, rs.getString(19));
					ps2.setString(20, rs.getString(20));
					ps2.setString(21, rs.getString(21));
					ps2.setString(22, rs.getString(22));
					ps2.setString(23, rs.getString(23));
					ps2.setString(24, rs.getString(24));
					ps2.setString(25, rs.getString(25));
					ps2.setString(26, rs.getString(26));
					ps2.setString(27, rs.getString(27));

					rv = ps2.executeUpdate();
				}
			}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveDict(Dict dict)" + (t2 - t1) + " ms");
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
	public int isExistByDict(String table, String dictLabel, String dictValue, String id, String mechanismid) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String sidSQL = "select * from " + table
					+ " where 1=1 and (dict_label=? or dict_value=?) and parent_code=? and mechanism_id=?";
			ps = conn.prepareStatement(sidSQL);
			ps.setString(1, dictLabel);
			ps.setString(2, dictValue);
			ps.setString(3, id);
			ps.setString(4, mechanismid);
			rs = ps.executeQuery();
			if (rs.next()) {
				rv = 1;
			}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"isExistByDict(String table, String dictLabel,String dictValue,String id,String mechanismid)"
							+ (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int isExistByDict(String table, String dictLabel, String dictValue, String id, String parentCode,
			String mechanismid) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sidSQL = "select * from " + table
					+ " where 1=1 and (dict_label=? or dict_value=?) and parent_code=? and mechanism_id=?";
			ps = conn.prepareStatement(sidSQL);
			ps.setString(1, dictLabel);
			ps.setString(2, dictValue);
			ps.setString(3, parentCode);
			ps.setString(4, mechanismid);
			rs = ps.executeQuery();
			if (rs.next()) {
				rv = 1;
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(
					"isExistByDict(String table, String dictLabel,String dictValue,String id,String parentCode,String mechanismid)"
							+ (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

	@Override
	public int update(Dict dict) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String udictSQL = "update sys_dict set dict_label=?,dict_value=?,tree_sort=?,is_sys=?,dict_type=?,remarks=?,parent_code=?,update_id=?,update_date=? where 1=1 and dict_code=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, dict.getDictLabel());
			ps.setString(2, dict.getDictValue());
			ps.setString(3, dict.getTreeSort());
			ps.setString(4, dict.getIsSys());
			ps.setString(5, dict.getDictType());
			ps.setString(6, dict.getRemarks());
			ps.setString(7, dict.getParentCode());
			ps.setString(8, dict.getUpdateBy());
			ps.setString(9, dict.getUpdateDate());
			ps.setString(10, dict.getDictCode());
			ps.setString(11, dict.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateDict(dict dict)" + (t2 - t1) + " ms");
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
	public Dict edit(Dict dict) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Dict> list = new ArrayList<Dict>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String jgid = dict.getMechanismId();
			String sql = "SELECT a.dict_code,a.dict_label,a.dict_value,a.tree_sort,a.is_sys,a.dict_type,a.remarks,a.parent_code,a.create_id,a.create_date,a.update_id,a.update_date,a.status FROM  sys_dict a   where 1=1 and a.dict_code=? and mechanism_id=? order by a.create_id desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dict.getDictCode());
			ps.setString(2, jgid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dict = new Dict();
				dict.setDictCode(rs.getString(1));
				dict.setDictLabel(rs.getString(2));
				dict.setDictValue(rs.getString(3));
				dict.setTreeSort(rs.getString(4));
				dict.setIsSys(rs.getString(5));
				dict.setDictType(rs.getString(6));
				dict.setRemarks(rs.getString(7));
				dict.setParentCode(rs.getString(8));
				dict.setCreateId(rs.getString(9));
				dict.setCreateDate(rs.getString(10));
				dict.setUpdateBy(rs.getString(11));
				dict.setUpdateDate(rs.getString(12));
				dict.setStatus(rs.getString(13));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Dict edit(Dict dict) " + (t2 - t1) + " ms");
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
		return dict;
	}

}