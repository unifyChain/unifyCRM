package dafengqi.yunxiang.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ResourceManager {
	private static ResourceManager dbPool;
	private static HikariDataSource ds;
	static {
		dbPool = new ResourceManager();
	}

	public static void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} 
	}

	public static void close(PreparedStatement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

	public final static Connection getConnection() {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public final static ResourceManager getInstance() {
		return dbPool;
	}

	public static void main(String[] args) throws SQLException {
		Connection con = null;
		try {
			con = ResourceManager.getConnection();
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM app_user");
			while (rs.next()) {
				System.out.println(rs.getObject(1));
				System.out.println(rs.getObject("role_id"));
				System.out.println(rs.getObject("username"));
			}
		} catch (Exception e) {
		} finally {
			if (con != null)
				con.close();
		}
	}

	public ResourceManager() {
	
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://rm-2ze214z70a2kt4o54ho.mysql.rds.aliyuncs.com:3306/unify?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&amp;tcpRcvBuf=2048000&amp;zeroDateTimeBehavior=convertToNull");
		config.addDataSourceProperty("cachePrepStmts", true);
		config.addDataSourceProperty("prepStmtCacheSize", 500);
		config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		config.setConnectionTestQuery("SELECT 1");
		config.setUsername("unify");
		config.setPassword("Xiyunxiang2013");
		config.setAutoCommit(true);
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(5);

		ds = new HikariDataSource(config);

	}

}
