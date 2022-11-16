package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.DictTypeDao;
import dafengqi.yunxiang.model.DictType;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("dictTypeDao")
public class DictTypeDaoHibernate extends GenericDaoHibernate<DictType, Long> implements DictTypeDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public DictTypeDaoHibernate() {
		super(DictType.class);
	}

	@Override
	public List<DictType> getItems(DictType dictType) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DictType> list = new ArrayList<DictType>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.dict_name,a.dict_type,a.status,a.create_id,a.create_date,a.update_id,a.update_date,a.remarks	 FROM  sys_dict_type a   where 1=1 order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				dictType = new DictType();
				dictType.setId(rs.getString(1));
				dictType.setDictName(rs.getString(2));
				dictType.setDictType(rs.getString(3));
				dictType.setStatus(rs.getString(4));
				dictType.setCreateId(rs.getString(5));
				dictType.setCreateDate(rs.getString(6));
				dictType.setUpdateBy(rs.getString(7));
				dictType.setUpdateDate(rs.getString(8));
				dictType.setRemarks(rs.getString(9));
				list.add(dictType);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<DictType> getItems(DictType dictType)" + (t2 - t1) + " ms");
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
	public DictType edit(DictType dictType) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DictType> list = new ArrayList<DictType>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String jgid = dictType.getMechanismId();
			String sql = "SELECT a.id,a.dict_name,a.dict_type,a.status,a.create_id,a.create_date,a.update_id,a.update_date,a.remarks	 FROM  sys_dict_type a   where 1=1 and a.id=? and a.mechanism_id=? order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dictType.getId());
			ps.setString(2, jgid);
			rs = ps.executeQuery();
			while (rs.next()) {
				dictType = new DictType();
				dictType.setId(rs.getString(1));
				dictType.setDictName(rs.getString(2));
				dictType.setDictType(rs.getString(3));
				dictType.setStatus(rs.getString(4));
				dictType.setCreateId(rs.getString(5));
				dictType.setCreateDate(rs.getString(6));
				dictType.setUpdateBy(rs.getString(7));
				dictType.setUpdateDate(rs.getString(8));
				dictType.setRemarks(rs.getString(9));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Dict edit(DictType dictType) {" + (t2 - t1) + " ms");
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
		return dictType;
	}

	@Override
	public int saveDictType(DictType dictType) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String idictSQL = "INSERT INTO sys_dict_type(id,dict_name,dict_type,status,create_id,create_date,update_id,update_date,remarks)VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(idictSQL);
			ps.setString(1, dictType.getId());
			ps.setString(2, dictType.getDictName());
			ps.setString(3, dictType.getDictType());
			ps.setString(4, "启用");
			ps.setString(5, dictType.getCreateId());
			ps.setString(6, dictType.getCreateDate());
			ps.setString(7, dictType.getUpdateBy());
			ps.setString(8, dictType.getUpdateDate());
			ps.setString(9, dictType.getRemarks());

			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveDictType(DictType dictType)" + (t2 - t1) + " ms");
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
	public int update(DictType dictType) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update sys_dict_type set dict_name=?,dict_type=?,update_id=?,update_date=?,remarks=? where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, dictType.getDictName());
			ps.setString(2, dictType.getDictType());
			ps.setString(3, dictType.getUpdateBy());
			ps.setString(4, dictType.getUpdateDate());
			ps.setString(5, dictType.getRemarks());
			ps.setString(6, dictType.getId());
			ps.setString(7, dictType.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(DictType dictType) " + (t2 - t1) + " ms");
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