package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.NoticeDao;
import dafengqi.yunxiang.model.Notice;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("noticeDao")
public class NoticeDaoHibernate extends GenericDaoHibernate<Notice, Long> implements NoticeDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
	public NoticeDaoHibernate() {
		super(Notice.class);
	}

	@Override
	public List<Notice> getItems(Notice notice) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.name,a.expire_date,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  notice a   where 1=1 order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getLong(1));
				notice.setName(rs.getString(2));
				notice.setExpireDate(rs.getDate(3));
				notice.setStatus(rs.getString(4));
				notice.setContent(rs.getString(5));
				notice.setImage(rs.getString(6));
				notice.setCreateId(rs.getString(7));
				notice.setCreateName(rs.getString(8));
				notice.setCreateDate(rs.getString(9));
				notice.setUpdateId(rs.getString(10));
				notice.setUpdateName(rs.getString(11));
				notice.setUpdateDate(rs.getString(12));
				notice.setMechanismId(rs.getString(13));
				notice.setMechanismName(rs.getString(14));
				notice.setAppId(rs.getString(15));
				list.add(notice);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Notice> getItems(Notice notice)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public List<Notice> getItemsAll(Notice notice) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sqls = "SELECT a.id,a.name,a.expire_date,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  notice a   where 1=1 and a.create_id=? and a.status='是' order by a.create_date desc ";
			ps = conn.prepareStatement(sqls);
			ps.setString(1, "admin");
			rs = ps.executeQuery();
			while (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getLong(1));
				notice.setName(rs.getString(2));
				notice.setExpireDate(rs.getDate(3));
				notice.setStatus(rs.getString(4));
				notice.setContent(rs.getString(5));
				notice.setImage(rs.getString(6));
				notice.setCreateId(rs.getString(7));
				notice.setCreateName(rs.getString(8));
				notice.setCreateDate(rs.getString(9));
				notice.setUpdateId(rs.getString(10));
				notice.setUpdateName(rs.getString(11));
				notice.setUpdateDate(rs.getString(12));
				notice.setMechanismId(rs.getString(13));
				notice.setMechanismName(rs.getString(14));
				notice.setAppId(rs.getString(15));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  Date date1 = sdf.parse(rs.getString(3));
				  Date date2 = sdf.parse(dfs.format(new Date()));

				  System.out.println("date1 : " + sdf.format(date1));
				  System.out.println("date2 : " + sdf.format(date2));

				  if (date1.compareTo(date2) > 0) {
					list.add(notice);
				  } else if (date1.compareTo(date2) < 0) {
				    System.out.println("未到期");
				  } else if (date1.compareTo(date2) == 0) {
					list.add(notice);
				  }
			}
			String sql = "SELECT a.id,a.name,a.expire_date,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  notice a   where 1=1 and a.mechanism_id=? and a.status='是' order by a.create_date desc ";
			ps1 = conn.prepareStatement(sql);
			ps1.setString(1, notice.getMechanismId());
			rs1 = ps1.executeQuery();
			while (rs1.next()) {
				notice = new Notice();
				notice.setId(rs1.getLong(1));
				notice.setName(rs1.getString(2));
				notice.setExpireDate(rs1.getDate(3));
				notice.setStatus(rs1.getString(4));
				notice.setContent(rs1.getString(5));
				notice.setImage(rs1.getString(6));
				notice.setCreateId(rs1.getString(7));
				notice.setCreateName(rs1.getString(8));
				notice.setCreateDate(rs1.getString(9));
				notice.setUpdateId(rs1.getString(10));
				notice.setUpdateName(rs1.getString(11));
				notice.setUpdateDate(rs1.getString(12));
				notice.setMechanismId(rs1.getString(13));
				notice.setMechanismName(rs1.getString(14));
				notice.setAppId(rs1.getString(15));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				  Date date1 = sdf.parse(rs1.getString(3));
				  Date date2 = sdf.parse(dfs.format(new Date()));

				  System.out.println("date1 : " + sdf.format(date1));
				  System.out.println("date2 : " + sdf.format(date2));

				  if (date1.compareTo(date2) > 0) {
					list.add(notice);
				  } else if (date1.compareTo(date2) < 0) {
				    System.out.println("未到期");
				  } else if (date1.compareTo(date2) == 0) {
					list.add(notice);
				  }
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Notice> getItems(Notice notice)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public Notice edit(Notice notice) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Notice> list = new ArrayList<Notice>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.name,a.expire_date,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  notice a   where 1=1 and a.id=? order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, notice.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getLong(1));
				notice.setName(rs.getString(2));
				notice.setExpireDate(rs.getDate(3));
				notice.setStatus(rs.getString(4));
				notice.setContent(rs.getString(5));
				notice.setImage(rs.getString(6));
				notice.setCreateId(rs.getString(7));
				notice.setCreateName(rs.getString(8));
				notice.setCreateDate(rs.getString(9));
				notice.setUpdateId(rs.getString(10));
				notice.setUpdateName(rs.getString(11));
				notice.setUpdateDate(rs.getString(12));
				notice.setMechanismId(rs.getString(13));
				notice.setMechanismName(rs.getString(14));
				notice.setAppId(rs.getString(15));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Dict edit(Notice notice) {" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return notice;
	}
	@Override
	public int saveNotice(Notice notice) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String idictSQL = "INSERT INTO notice(name,expire_date,status,content,image,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,mechanism_name,app_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(idictSQL);
			ps.setString(1, notice.getName());
			ps.setString(2, df.format(notice.getExpireDate()));
			ps.setString(3, notice.getStatus());
			ps.setString(4, notice.getContent());
			ps.setString(5, notice.getImage());
			ps.setString(6, notice.getCreateId());
			ps.setString(7, notice.getCreateName());
			ps.setString(8, notice.getCreateDate());
			ps.setString(9, notice.getUpdateId());
			ps.setString(10, notice.getUpdateName());
			ps.setString(11, notice.getUpdateDate());
			ps.setString(12, notice.getMechanismId());
			ps.setString(13, notice.getMechanismName());
			ps.setString(14, notice.getAppId());

			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveNotice(Notice notice)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}
	@Override
	public int update(Notice notice) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update notice set name=?,expire_date=?,status=?,content=?,image=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,mechanism_id=?,mechanism_name=?,app_id=? where 1=1 and id=? ";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, notice.getName());
			ps.setString(2, df.format(notice.getExpireDate()));
			ps.setString(3, notice.getStatus());
			ps.setString(4, notice.getContent());
			ps.setString(5, notice.getImage());
			ps.setString(6, notice.getCreateId());
			ps.setString(7, notice.getCreateName());
			ps.setString(8, notice.getCreateDate());
			ps.setString(9, notice.getUpdateId());
			ps.setString(10, notice.getUpdateName());
			ps.setString(11, notice.getUpdateDate());
			ps.setString(12, notice.getMechanismId());
			ps.setString(13, notice.getMechanismName());
			ps.setString(14, notice.getAppId());
			ps.setLong(15, notice.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(Notice notice) " + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return rv;
	}

}