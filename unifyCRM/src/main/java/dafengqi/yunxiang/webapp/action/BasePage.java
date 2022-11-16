package dafengqi.yunxiang.webapp.action;
 
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;

import dafengqi.yunxiang.Constants;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.service.MailEngine;
import dafengqi.yunxiang.service.UserManager;
import dafengqi.yunxiang.util.ResourceManager;

public class BasePage {
    protected final Log log = LogFactory.getLog(getClass());
    protected UserManager userManager;
    protected MailEngine mailEngine;
    protected SimpleMailMessage message;
    protected String templateName;
    protected FacesContext facesContext;
    protected String sortColumn;
    protected boolean ascending = true; 
    protected boolean nullsAreHigh;

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
	protected java.sql.Connection userConn;
    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    // Convenience methods ====================================================
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public String getBundleName() {
        return getFacesContext().getApplication().getMessageBundle();
    }

    public ResourceBundle getBundle() {
        ClassLoader classLoader =  Thread.currentThread().getContextClassLoader();
        return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale(), classLoader);
    }

    public String getText(String key) {
        String message;

        try {
            message = getBundle().getString(key);
        } catch (java.util.MissingResourceException mre) {
            log.warn("Missing key for '" + key + "'");
            return "???" + key + "???";
        }

        return message;
    }

    public String getText(String key, Object arg) {
        if (arg == null) {
            return getText(key);
        }

        MessageFormat form = new MessageFormat(getBundle().getString(key));

        if (arg instanceof String) {
            return form.format(new Object[] { arg });
        } else if (arg instanceof Object[]) {
            return form.format(arg);
        } else {
            log.error("arg '" + arg + "' not String or Object[]");

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    protected void addMessage(String key, Object arg) {
        List<String> messages = (List) getSession().getAttribute("messages");

        if (messages == null) {
            messages = new ArrayList<String>();
        }

        messages.add(getText(key, arg));
        getSession().setAttribute("messages", messages);
    }

    protected void addMessage(String key) {
        addMessage(key, null);
    }

    @SuppressWarnings("unchecked")
    protected void addError(String key, Object arg) {
        List<String> errors = (List) getSession().getAttribute("errors");

        if (errors == null) {
            errors = new ArrayList<String>();
        }

        // if key contains a space, don't look it up, it's likely a raw message
        if (key.contains(" ") && arg == null) {
            errors.add(key);
        } else {
            errors.add(getText(key, arg));
        }

        getSession().setAttribute("errors", errors);
    }

    protected void addError(String key) {
        addError(key, null);
    }
    public int sysLog(String functionName, String description) {
		java.sql.Connection userConn = null;
    	final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps =null;
		int j = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ip = getRequest().getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = getRequest().getRemoteAddr();
			}
			String xtrzsql = "INSERT INTO sys_log(user_id,user_name,function_name,description,ip,status,create_date,mechanism_id,mechanism_name)VALUES(?,?,?,?,?,?,?,?,?);";
			 ps = conn.prepareStatement(xtrzsql);
			ps.setString(1, getRequest().getRemoteUser());
			ps.setString(2, (String) getSession().getAttribute("NAME"));
			ps.setString(3, functionName);
			ps.setString(4, description);
			ps.setString(5, ip);
			ps.setString(6, "启用");
			ps.setString(7, df.format(new Date()));
			ps.setString(8, (String) getSession().getAttribute("MECHANISMID"));
			ps.setString(9, (String) getSession().getAttribute("MECHANISMNAME"));
			j = ps.executeUpdate();

			conn.setAutoCommit(true);

		} catch (SQLException e) {
			j = -1;
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}

		return j;
	}


	
	public int isExistThreePara(String table, String name, String field, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sidSQL = "select * from " + table + " where 1=1 and "+field+"=? and mechanism_id=?";
			ps = conn.prepareStatement(sidSQL);
			ps.setString(1, name);
			ps.setString(2, mechanismId);
			rs = ps.executeQuery();
			if (rs.next()) {
				rv=1;
			}
			conn.setAutoCommit(true);

			long t2 = System.currentTimeMillis();
			System.out.println("isExistThreePara(String table, String name, String field)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
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


	public List<Role> getModulesByRole(String table, String roleid, String mechanismid,String parentModuleName) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Role> list = new ArrayList<Role>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			if(roleid.equals("-1")) {
				String sidSQL = "select module_name from " + table + " where 1=1 and role_id=? and parent_module_name=? ORDER BY module_id asc";
				ps = conn.prepareStatement(sidSQL);
				ps.setString(1, roleid);
				ps.setString(2, parentModuleName);
				rs = ps.executeQuery();
				while (rs.next()) {
					Role role = new Role();
					role.setName(rs.getString(1));
					list.add(role);
				}
			}else {
				String sidSQL = "select module_name from " + table + " where 1=1 and mechanism_id=? and role_id=? and parent_module_name=? GROUP BY module_name ORDER BY module_id asc";
				ps = conn.prepareStatement(sidSQL);
				ps.setString(1, mechanismid);
				ps.setString(2, roleid);
				ps.setString(3, parentModuleName);
				rs = ps.executeQuery();
				while (rs.next()) {
					Role role = new Role();
					role.setName(rs.getString(1));
					list.add(role);
				}
			}
			conn.setAutoCommit(true);

			long t2 = System.currentTimeMillis();
			System.out.println("List<Role> getModulesByRole(String table, String roleid, String mechanismid,String type)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
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
		return list;
	}
	
	public int audit(String table, String id, String key,String status,String userId,String userName,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uSQL = "update " + table + " set audit_status='"+status+"',audit_date='"+df.format(new Date())+"',audit_id='"+userId+"',audit_name='"+userName+"' where 1=1 and mechanism_id=? and "+key+"=? ";
			ps = conn.prepareStatement(uSQL);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(" audit(String table, String id, String key,String status,String userId,String userName,String mechanismId)" + (t2 - t1) + " ms");
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
	
	public int complete(String table, String id, String key,String status,String userId,String userName,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uSQL = "update " + table + " set status='"+status+"' where 1=1 and mechanism_id=? and "+key+"=? ";
			ps = conn.prepareStatement(uSQL);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
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

	
	public int auditProductDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="";
			if(table.equals("purchaseorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0),IFNULL(this_payment,0), `warehouse_id`, `mechanism_id`,account_id,supplier_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";

			}else if(table.equals("purchasereturnorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0),IFNULL(this_refund,0), `warehouse_id`, `mechanism_id`,account_id,supplier_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";
			}
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			BigDecimal je = BigDecimal.ZERO;
			String khid="";
			while(rs.next()) {
				je = rs.getBigDecimal(9);
				khid = rs.getString(8);
				//增加商品库存
				String uSQL = "update productDetail set stock=stock+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(5));
				ps1.setString(4, rs.getString(6));
				rv = ps1.executeUpdate();
				

				//增加商品库存
				String u2SQL = "update inventory_plan_detail set stock=IFNULL(stock,0)+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps4 = conn.prepareStatement(u2SQL);
				ps4.setBigDecimal(1, rs.getBigDecimal(2));
				ps4.setString(2, rs.getString(1));
				ps4.setString(3, rs.getString(5));
				ps4.setString(4, rs.getString(6));
				rv = ps4.executeUpdate();
				

				//增加账户余额
				String uaccountSQL = "update account set current_balance=IFNULL(current_balance,0)-? where 1=1 and id=?  and  mechanism_id=?";
				ps2 = conn.prepareStatement(uaccountSQL);
				ps2.setBigDecimal(1, rs.getBigDecimal(4));
				ps2.setString(2, rs.getString(7));
				ps2.setString(3, rs.getString(6));
				rv = ps2.executeUpdate();
				
			}

			

			//增加应付账款
			String ucustomerSQL = "update crm_customer set balance_of_accounts_payable=balance_of_accounts_payable+? where 1=1 and id=?  and data_type='供应商' and  mechanism_id=?";
			ps3 = conn.prepareStatement(ucustomerSQL);
			ps3.setBigDecimal(1, je);
			ps3.setString(2, khid);
			ps3.setString(3, mechanismId);
			rv = ps3.executeUpdate();
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
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
	
	
	public int auditDeProductDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="";
			if(table.equals("purchaseorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(this_payment,0), `warehouse_id`, `mechanism_id`,account_id,supplier_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";

			}else if(table.equals("purchasereturnorderproduct")) {
				ssql="select product_id, IFNULL(quantity,0), IFNULL(price,0)  ,IFNULL(this_refund,0), `warehouse_id`, `mechanism_id`,account_id,supplier_id,IFNULL(current_arrears,0) from "+table+" where mechanism_id=? and "+key+"=?";
			}
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			BigDecimal je = BigDecimal.ZERO;
			String khid="";
			while(rs.next()) {
				je = rs.getBigDecimal(9);
				khid = rs.getString(8);
				//减少商品库存
				String uSQL = "update productDetail set stock=stock-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(5));
				ps1.setString(4, rs.getString(6));
				rv = ps1.executeUpdate();
				

				//减少商品库存
				String u2SQL = "update inventory_plan_detail set stock=IFNULL(stock,0)-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps4 = conn.prepareStatement(u2SQL);
				ps4.setBigDecimal(1, rs.getBigDecimal(2));
				ps4.setString(2, rs.getString(1));
				ps4.setString(3, rs.getString(5));
				ps4.setString(4, rs.getString(6));
				rv = ps4.executeUpdate();
				
				//增加账户余额
				String uaccountSQL = "update account set current_balance=IFNULL(current_balance,0)+? where 1=1 and id=?  and  mechanism_id=?";
				ps2 = conn.prepareStatement(uaccountSQL);
				ps2.setBigDecimal(1, rs.getBigDecimal(4));
				ps2.setString(2, rs.getString(7));
				ps2.setString(3, rs.getString(6));
				rv = ps2.executeUpdate();
			}
			

			
			//减少应付账款
			String ucustomerSQL = "update crm_customer set balance_of_accounts_payable=balance_of_accounts_payable-? where 1=1 and id=? and data_type='供应商'  and  mechanism_id=?";
			ps3 = conn.prepareStatement(ucustomerSQL);
			ps3.setBigDecimal(1, je);
			ps3.setString(2, khid);
			ps3.setString(3, mechanismId);
			rv = ps3.executeUpdate();
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
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
	
	
	public int status(String table, String id, String column,String status,String mechanismid) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uSQL = "update " + table + " set "+column+"='"+status+"' where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(uSQL);
			ps.setString(1, id);
			ps.setString(2, mechanismid);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
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
	public int remove(String table, String id,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from " + table + " where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, id);
			ps.setString(2, mechanismId);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("basePage  remove(String table, String id)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
			if(e.getMessage().contains("Cannot delete or update a parent row")) {
				rv = -2;
			}else {
				rv = -1;
			}
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
	

	
	

	public int remove(String table, String id, String val, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from " + table + " where 1=1 and "+id+"=? and mechanism_id=?";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, val);
			ps.setString(2, mechanismId);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println(" remove(String table, String id, String val, String mechanismId)" + (t2 - t1) + " ms");
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
	


	
	
	public int enable(String table, String id, String key,String mechanismid) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set status='启用' where 1=1 and "+key+"=? and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, id);
			ps.setString(2, mechanismid);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("enable(String table, String id, String key,String mechanismid)" + (t2 - t1) + " ms");
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
	public int disable(String table, String id, String key,String mechanismid) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String udictSQL = "update " + table + " set status='禁用' where 1=1 and "+key+"=?  and mechanism_id=?";
			ps = conn.prepareStatement(udictSQL);
			ps.setString(1, id);
			ps.setString(2, mechanismid);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("disable(String table, String id, String key,String mechanismid)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(ps);
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
	public int isExistThreeParaNotIn(String table, String name,String id,String field,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sidSQL = "select * from " + table + " where 1=1 and "+field+"=? and mechanism_id=? and id not in ('"+id+"')";
			ps = conn.prepareStatement(sidSQL);
			ps.setString(1, name);
			ps.setString(2, mechanismId);
			rs = ps.executeQuery();
			if (rs.next()) {
				rv=1;
			}
			conn.setAutoCommit(true);

			long t2 = System.currentTimeMillis();
			System.out.println("isExistThreeParaNotIn(String table, String name,String id,String field)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
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

    /**
     * Convenience method for unit tests.
     * @return boolean indicator of an "errors" attribute in the session
     */
    public boolean hasErrors() {
        return (getSession().getAttribute("errors") != null);
    }

    /**
     * Servlet API Convenience method
     * @return HttpServletRequest from the FacesContext
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Servlet API Convenience method
     * @return the current user's session
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Servlet API Convenience method
     * @return HttpServletResponse from the FacesContext
     */
    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    /**
     * Servlet API Convenience method
     * @return the ServletContext form the FacesContext
     */
    protected ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration() {
        Map config = (HashMap) getServletContext().getAttribute(Constants.CONFIG);

        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }

        return config;
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * @param user the user to send the message to
     * @param msg the message to send
     * @param url the application's URL
     */
    protected void sendUserMessage(User user, String msg, String url) {
        if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user [" + user.getEmail() + "]...");
        }

        message.setTo(user.getFullName() + "<" + user.getEmail() + ">");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);

        // TODO: once you figure out how to get the global resource bundle in
        // WebWork, then figure it out here too.  In the meantime, the Username
        // and Password labels are hard-coded into the template. 
        // model.put("bundle", getTexts());
        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(message, templateName, model);
    }

    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    // The following methods are used by p:dataTable for sorting.
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Sort list according to which column has been clicked on.
     * @param list the java.util.List to sort
     * @return ordered list
     */
    @SuppressWarnings("unchecked")
    protected List sort(List list) {
        Comparator comparator = new BeanComparator(sortColumn, new NullComparator(nullsAreHigh));
        if (!ascending) {
            comparator = new ReverseComparator(comparator);
        }
        Collections.sort(list, comparator);
        return list;
    }
    

	public int update(String table, String id, String key,String status,String firstname) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uzdglSQL = "update " + table + " set check_status='"+status+"',check_time='"+df.format(new Date())+"',checker='"+firstname+"' where 1=1 and "+key+"=? ";
			ps = conn.prepareStatement(uzdglSQL);
			ps.setString(1, id);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("update(String table, String id, String key,String status,String firstname)" + (t2 - t1) + " ms");
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
	
	
	public List<Contacts> getContacts(String mechanismId,String customerId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Contacts> list = new ArrayList<Contacts>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "SELECT id,name,telephone,province_name,city_name,area_name,address FROM crm_contacts where 1=1 and customer_id=? and mechanism_id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, customerId);
			ps.setString(2, mechanismId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Contacts contacts = new Contacts();
				contacts.setId(rs.getString(1));
				contacts.setName(rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+",");
				contacts.setPhone(rs.getString(2));
				contacts.setProvinceName(rs.getString(3));
				contacts.setCityName(rs.getString(4));
				contacts.setAreaName(rs.getString(5));
				contacts.setAddress(rs.getString(6));
				list.add(contacts);
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Contacts> getContacts(String mechanismId,String customerId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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

	

	public static synchronized String getID(String lx,String jgid,String type) {
		java.sql.Connection uc = null;
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (uc != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String number="";
		try { 
			conn = isConnSupplied ? uc : ResourceManager.getConnection();
			String sql="";
			if(lx.equals("product") || lx.equals("account") ) {
				 sql="SELECT max(id) FROM "+lx+" where 1=1 and mechanism_id=? ";
				 ps = conn.prepareStatement(sql);
				 ps.setString(1, jgid.substring(0, 8));
					
				rs = ps.executeQuery();
				int count=0;
				while (rs.next()) {
					count = rs.getInt(1);
				}
				int dbclientNumber=count;
			    int newclientNumber=dbclientNumber+1;
			    
			    if (count == 0) {
			    	 number="10001";
				  }else {
				    String tris=String.valueOf(newclientNumber);
				    number = tris;
				  }
			}else if(lx.equals("equipment") ) {
				 sql="SELECT max(id) FROM product where 1=1 and mechanism_id=? and type='设备'";
				 ps = conn.prepareStatement(sql);
				 ps.setString(1, jgid.substring(0, 8));
					
				rs = ps.executeQuery();
				int count=0;
				while (rs.next()) {
					count = rs.getInt(1);
				}
				int dbclientNumber=count;
			    int newclientNumber=dbclientNumber+1;
			    
			    if (count == 0) {
			    	 number="10001";
				  }else {
				    String tris=String.valueOf(newclientNumber);
				    number = tris;
				  }
			}else if(lx.equals("crm_customer")) {
				 sql="SELECT max(id) FROM "+lx+" where 1=1 and mechanism_id=? and data_type=?";
				 ps = conn.prepareStatement(sql);
				 ps.setString(1, jgid.substring(0, 8));
				 ps.setString(2, type);
				 
				 rs = ps.executeQuery();
				 int count=0;
				 while (rs.next()) {
					count = rs.getInt(1);
				 }
				 
				 int dbclientNumber=count;
			     int newclientNumber=dbclientNumber+1;
			     
			     
			     if (count == 0) {
			    	 number="00001";
				  }else {
				     String tris=String.valueOf(newclientNumber);
				     if(newclientNumber<10) {
				    	 tris = "0000"+tris;
				     }else if(newclientNumber<100) {
				    	 tris = "000"+tris;
				     }
				     System.out.println(tris);
					 number = tris;
				  }
			}
			
			long t2 = System.currentTimeMillis();
			System.out.println("public static synchronized String getID(String lx,String jgid)" + (t2 - t1) +" ms");

		} catch (SQLException e) {
			
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}

		return number;
	}
	public String getAuditStatus(String table, String id, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String auditStatus="未审核";
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "select IFNULL(audit_status,'未审核') from " + table + " where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, id);
			ps.setString(2, mechanismId);
			rs = ps.executeQuery();
			while(rs.next()) {
				auditStatus=rs.getString(1);
			}
			
			
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("basePage  remove(String table, String id)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		return auditStatus;
	}
	public int addProductDetails(String  table,String  column,String  id, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="SELECT product_id, SUM(quantity),warehouse_id FROM "+table+" where mechanism_id=? and "+column+"=? GROUP BY warehouse_id,product_id ";
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				//增加商品库存
				String uSQL = "update productDetail set stock=stock+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(3));
				ps1.setString(4, mechanismId);
				rv = ps1.executeUpdate();
				
			}

			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("addProductDetails(String  table,String  column,String  id, String mechanismId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	

	public int subProductDetails(String  table,String  column,String  id, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="SELECT product_id, SUM(quantity),warehouse_id FROM "+table+" where mechanism_id=? and "+column+"=? GROUP BY warehouse_id,product_id ";
			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				//减少商品库存
				String uSQL = "update productDetail set stock=stock-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(3));
				ps1.setString(4, mechanismId);
				rv = ps1.executeUpdate();
				
			}

			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("addProductDetails(String  table,String  column,String  id, String mechanismId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(rs);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}


	public BigDecimal getCustomerBalance(String id, String mechanismid, String datatype) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BigDecimal yfje = BigDecimal.ZERO;
		BigDecimal ysje = BigDecimal.ZERO;
		BigDecimal backje = BigDecimal.ZERO;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sidSQL = "select balance_of_accounts_payable,balance_of_accounts_receivable from crm_customer where 1=1 and id=? and mechanism_id=? and data_type=?";
			ps = conn.prepareStatement(sidSQL);

			String[] customer=id.split("!_");
			ps.setString(1, customer[0]);
			ps.setString(2, mechanismid);
			ps.setString(3, datatype);
			rs = ps.executeQuery();
			while (rs.next()) {
				yfje = rs.getBigDecimal(1);
				ysje = rs.getBigDecimal(2);
			}
			conn.setAutoCommit(true);

			long t2 = System.currentTimeMillis();
			System.out.println("removeVerification(String table, String id, String zd)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		if(datatype.equals("客户")) {

			backje=ysje;
			
		}else if(datatype.equals("供应商")) {

			backje=yfje;
		}
		return backje;
	}
	
	
	
	public int auditProductionDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="select product_id, IFNULL(quantity,0), warehouse_id from "+table+" where mechanism_id=? and "+key+"=?";

			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				//增加商品库存
				String uSQL = "update productDetail set stock=stock+? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(3));
				ps1.setString(4, mechanismId);
				rv = ps1.executeUpdate();
				
				
			}
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	
	
	public int auditDeProductionDetail(String table, String id, String key,String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String ssql="select product_id, IFNULL(quantity,0), warehouse_id from "+table+" where mechanism_id=? and "+key+"=?";

			ps = conn.prepareStatement(ssql);
			ps.setString(1, mechanismId);
			ps.setString(2, id);
			rs = ps.executeQuery();
			while(rs.next()) {
				//增加商品库存
				String uSQL = "update productDetail set stock=stock-? where 1=1 and product_id=? and warehouse_id=? and  mechanism_id=?";
				ps1 = conn.prepareStatement(uSQL);
				ps1.setBigDecimal(1, rs.getBigDecimal(2));
				ps1.setString(2, rs.getString(1));
				ps1.setString(3, rs.getString(3));
				ps1.setString(4, mechanismId);
				rv = ps1.executeUpdate();
				
				
			}
			

			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	

	public int updateInventoryPlanDetail(String inventoryPlanId, String mechanismId, String id,BigDecimal quantity) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);


			String uSQL = "update inventory_plan_detail set inventory_quantity=? where 1=1 and product_id=?  and inventory_plan_id=?  and  mechanism_id=?";
			ps1 = conn.prepareStatement(uSQL);
			ps1.setBigDecimal(1, quantity);
			ps1.setString(2, id);
			ps1.setString(3, inventoryPlanId);
			ps1.setString(4, mechanismId);
			rv = ps1.executeUpdate();
			

			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("audit(String table, String id, String key,String status,String userId)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			rv = -1;
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	
	
	
	
	public int checkInventoryPlanUse(String table, String id, String val, String mechanismId) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "select * from " + table + " where 1=1 and "+id+"=? and mechanism_id=?";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, val);
			ps.setString(2, mechanismId);
			rs = ps.executeQuery();
			if(rs.next()) {
				rv=1;
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("basePage  remove(String table, String id)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
		} finally {
			ResourceManager.close(ps);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}
	
	public int removeSpecial(String table, String id,String mechanismId,String type) {
		long t1 = System.currentTimeMillis();
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from " + table + " where 1=1 and id=? and mechanism_id=? and type=?";
			ps = conn.prepareStatement(dsql);
			ps.setString(1, id);
			ps.setString(2, mechanismId);
			ps.setString(3, type);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("removeSpecial(String table, String id,String mechanismId,String type)" + (t2 - t1) + " ms");
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
