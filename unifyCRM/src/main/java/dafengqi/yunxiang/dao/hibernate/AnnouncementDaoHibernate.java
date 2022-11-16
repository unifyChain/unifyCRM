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

import dafengqi.yunxiang.dao.AnnouncementDao;
import dafengqi.yunxiang.model.Announcement;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("announcementDao")
public class AnnouncementDaoHibernate extends GenericDaoHibernate<Announcement, Long> implements AnnouncementDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public AnnouncementDaoHibernate() {
		super(Announcement.class);
	}

	@Override
	public List<Announcement> getItems(Announcement announcement) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Announcement> list = new ArrayList<Announcement>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.name,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  announcement a   where 1=1 order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				announcement = new Announcement();
				announcement.setId(rs.getLong(1));
				announcement.setName(rs.getString(2));
				announcement.setStatus(rs.getString(3));
				announcement.setContent(rs.getString(4));
				announcement.setImage(rs.getString(5));
				announcement.setCreateId(rs.getString(6));
				announcement.setCreateName(rs.getString(7));
				announcement.setCreateDate(rs.getString(8));
				announcement.setUpdateId(rs.getString(9));
				announcement.setUpdateName(rs.getString(10));
				announcement.setUpdateDate(rs.getString(11));
				announcement.setMechanismId(rs.getString(12));
				announcement.setMechanismName(rs.getString(13));
				announcement.setAppId(rs.getString(14));
				list.add(announcement);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Announcement> getItems(Announcement announcement)" + (t2 - t1) + " ms");
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
	public List<Announcement> getItemsAll(Announcement announcement) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		List<Announcement> list = new ArrayList<Announcement>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sqls = "SELECT a.id,a.name,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  announcement a   where 1=1 and a.status='鏄�' order by a.create_date desc ";
			ps = conn.prepareStatement(sqls);
			rs = ps.executeQuery();
			while (rs.next()) {
				announcement = new Announcement();
				announcement.setId(rs.getLong(1));
				announcement.setName(rs.getString(2));
				announcement.setStatus(rs.getString(3));
				announcement.setContent(rs.getString(4));
				announcement.setImage(rs.getString(5));
				announcement.setCreateId(rs.getString(6));
				announcement.setCreateName(rs.getString(7));
				announcement.setCreateDate(rs.getString(8));
				announcement.setUpdateId(rs.getString(9));
				announcement.setUpdateName(rs.getString(10));
				announcement.setUpdateDate(rs.getString(11));
				announcement.setMechanismId(rs.getString(12));
				announcement.setMechanismName(rs.getString(13));
				announcement.setAppId(rs.getString(14));
				list.add(announcement);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Announcement> getItems(Announcement announcement)" + (t2 - t1) + " ms");
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
	public Announcement edit(Announcement announcement) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Announcement> list = new ArrayList<Announcement>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.name,a.status,a.content,a.image,a.create_id,a.create_name,a.create_date,a.update_id,a.update_name,a.update_date,a.mechanism_id,a.mechanism_name,a.app_id	 FROM  announcement a   where 1=1 and a.id=? order by a.create_date desc ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, announcement.getId());
			rs = ps.executeQuery();
			while (rs.next()) {
				announcement = new Announcement();
				announcement.setId(rs.getLong(1));
				announcement.setName(rs.getString(2));
				announcement.setStatus(rs.getString(3));
				announcement.setContent(rs.getString(4));
				announcement.setImage(rs.getString(5));
				announcement.setCreateId(rs.getString(6));
				announcement.setCreateName(rs.getString(7));
				announcement.setCreateDate(rs.getString(8));
				announcement.setUpdateId(rs.getString(9));
				announcement.setUpdateName(rs.getString(10));
				announcement.setUpdateDate(rs.getString(11));
				announcement.setMechanismId(rs.getString(12));
				announcement.setMechanismName(rs.getString(13));
				announcement.setAppId(rs.getString(14));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Dict edit(Announcement announcement) {" + (t2 - t1) + " ms");
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
		return announcement;
	}

	@Override
	public int saveAnnouncement(Announcement announcement) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String idictSQL = "INSERT INTO announcement(name,status,content,image,create_id,create_name,create_date,update_id,update_name,update_date,mechanism_id,mechanism_name,app_id)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(idictSQL);
			ps.setString(1, announcement.getName());
			ps.setString(2, announcement.getStatus());
			ps.setString(3, announcement.getContent());
			ps.setString(4, announcement.getImage());
			ps.setString(5, announcement.getCreateId());
			ps.setString(6, announcement.getCreateName());
			ps.setString(7, announcement.getCreateDate());
			ps.setString(8, announcement.getUpdateId());
			ps.setString(9, announcement.getUpdateName());
			ps.setString(10, announcement.getUpdateDate());
			ps.setString(11, announcement.getMechanismId());
			ps.setString(12, announcement.getMechanismName());
			ps.setString(13, announcement.getAppId());

			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveAnnouncement(Announcement announcement)" + (t2 - t1) + " ms");
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
	public int update(Announcement announcement) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update announcement set name=?,status=?,content=?,image=?,create_id=?,create_name=?,create_date=?,update_id=?,update_name=?,update_date=?,mechanism_id=?,mechanism_name=?,app_id=? where 1=1 and id=? ";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, announcement.getName());
			ps.setString(2, announcement.getStatus());
			ps.setString(3, announcement.getContent());
			ps.setString(4, announcement.getImage());
			ps.setString(5, announcement.getCreateId());
			ps.setString(6, announcement.getCreateName());
			ps.setString(7, announcement.getCreateDate());
			ps.setString(8, announcement.getUpdateId());
			ps.setString(9, announcement.getUpdateName());
			ps.setString(10, announcement.getUpdateDate());
			ps.setString(11, announcement.getMechanismId());
			ps.setString(12, announcement.getMechanismName());
			ps.setString(13, announcement.getAppId());
			ps.setLong(14, announcement.getId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(Announcement announcement) " + (t2 - t1) + " ms");
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