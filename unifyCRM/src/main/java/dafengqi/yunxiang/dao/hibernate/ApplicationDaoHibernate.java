package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.ApplicationDao;
import dafengqi.yunxiang.model.Application;
import dafengqi.yunxiang.util.ResourceManager;

@Repository
public class ApplicationDaoHibernate extends GenericDaoHibernate<Application, Long> implements ApplicationDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public ApplicationDaoHibernate() {
		super(Application.class);
	}

	@Override
	public int saveApplication(Application application) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar curr = Calendar.getInstance();
			curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 1);
			Date date = curr.getTime();
			String iappuserSQL = "INSERT INTO application(id,name,effective_date,remarks,price,picture,details,status,application_id,mechanism_id,mechanism_name)VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iappuserSQL);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, application.getName());
			ps.setString(3, df.format(date));
			ps.setString(4, application.getRemarks());
			ps.setBigDecimal(5, application.getPrice());
			ps.setString(6, application.getPicture());
			ps.setString(7, application.getDetails());
			ps.setString(8, application.getStatus());
			ps.setString(9, application.getId());
			ps.setString(10, application.getMechanismId());
			ps.setString(11, application.getMechanismName());
			rv = ps.executeUpdate();

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveApplication(Application application) " + (t2 - t1) + " ms");
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
	public List<Application> getItems(Application application) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Application> list = new ArrayList<Application>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			sql = "SELECT a.id,a.name,a.url,a.remarks,a.price,a.picture,a.details,a.status  FROM  module a   where 1=1 and a.type='应用'";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				application = new Application();
				application.setId(rs.getString(1));
				application.setName(rs.getString(2));
				application.setUrl(rs.getString(3));
				application.setRemarks(rs.getString(4));
				application.setPrice(rs.getBigDecimal(5));
				application.setPicture(rs.getString(6));
				application.setDetails(rs.getString(7));
				application.setStatus(rs.getString(8));
				list.add(application);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Application> getItems(Application application)" + (t2 - t1) + " ms");
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
	public List<Application> getItemsMenu(Application application) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		ResultSet rs = null;
		List<Application> list = new ArrayList<Application>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";

			sql = "SELECT a.id,a.name,a.picture  FROM  module a   where 1=1 and (a.system='CRM' or a.system='SYS') and a.type='应用' ORDER BY sort asc";
			ps = conn.prepareStatement(sql);
//					ps.setString(1, application.getMechanismId());
			rs = ps.executeQuery();
			int y = 0;
			while (rs.next()) {
				application = new Application();
				application.setId(rs.getString(1));
				application.setName(rs.getString(2));
				application.setPicture(rs.getString(3));
				String sql1 = "SELECT  a.id,a.name,a.url,a.picture  FROM  module a   where 1=1 and a.module_id=? and (a.system='CRM' or a.system='SYS')  ORDER BY sort asc";
				ps1 = conn.prepareStatement(sql1);
				ps1.setString(1, rs.getString(1));
				rs1 = ps1.executeQuery();
				List<Application> lists = new ArrayList<Application>();
				while (rs1.next()) {
					Application roles = new Application();
					roles.setId(rs1.getString(1) + y++);
					roles.setName(rs1.getString(2));
					roles.setUrl(rs1.getString(3));
					roles.setPicture(rs1.getString(4));
					int a = 0;
					String sql2 = "SELECT  a.id,a.name,a.url,a.picture  FROM  module a   where 1=1 and a.module_id=? and (a.system='CRM' or a.system='SYS') and a.type!='按钮'  ORDER BY sort asc";
					ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, rs1.getString(1));
					rs2 = ps2.executeQuery();
					List<Application> listFrom = new ArrayList<Application>();
					while (rs2.next()) {
						a++;
						Application rolesFrom = new Application();
						rolesFrom.setId(rs2.getString(1) + y++);
						rolesFrom.setName(rs2.getString(2));
						rolesFrom.setUrl(rs2.getString(3));
						rolesFrom.setPicture(rs2.getString(4));
						listFrom.add(rolesFrom);
					}
					if (a == 0) {
						roles.setStatus("否");
					} else {
						roles.setStatus("是");

					}
					roles.setDetailList1(listFrom);
					lists.add(roles);
				}
				application.setDetailList(lists);
				list.add(application);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Application> getItemsMenu(Application application)" + (t2 - t1) + " ms");
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
		return list;
	}

	@Override
	public List<Application> getItemsManagement(Application application) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Application> list = new ArrayList<Application>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
			sql = "SELECT a.id,a.name,a.effective_date,a.remarks,a.price,a.picture  FROM  application a   where 1=1 and a.mechanism_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, application.getMechanismId());
			rs = ps.executeQuery();
			while (rs.next()) {
				application = new Application();
				application.setId(rs.getString(1));
				application.setName(rs.getString(2));
				application.setEffectiveDate(rs.getString(3));
				application.setRemarks(rs.getString(4));
				application.setPrice(rs.getBigDecimal(5));
				application.setPicture(rs.getString(6));
				list.add(application);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Application> getItemsManagement(Application application)" + (t2 - t1) + " ms");
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

}
