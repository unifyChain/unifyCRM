package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.UserDao;
import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.util.ResourceManager;


@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);
    

    public UserDaoHibernate() {
        super(User.class);
    }

    @Override
	public List<User> getItems(User user) {
		long t1 = System.currentTimeMillis();
		 
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		String username=user.getUsername();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "";
				if(user.getFrom().equals("全部")) {
					sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password  FROM  app_user a   where 1=1 and a.mechanism_id like ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, user.getMechanismId()+"%");
					rs = ps.executeQuery();
					while (rs.next()) {
						user = new User();
						user.setId(rs.getLong(1));
						user.setIds(rs.getString(1));
						user.setFirstName(rs.getString(2));
						user.setUsername(rs.getString(3));
						user.setPhoneNumber(rs.getString(4));
						user.setDepartmentId(rs.getString(5));
						user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
						user.setMechanismId(rs.getString(7));
						user.setMechanismName(rs.getString(8));
						user.setEmail(rs.getString(9));
						user.setPassword(rs.getString(10));
						user.setConfirmPassword(rs.getString(10));
						list.add(user);
					}
				}else {
					if(user.getFrom().equals("本部门")) {
						String[] xh=user.getDepartmentId().split(",");
					    for(String bmidz : xh){
					    	if(!bmidz.equals("")){
								sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password	 FROM  app_user a   where 1=1 and a.mechanism_id like ? and a.department_id like ?";
								ps = conn.prepareStatement(sql);
								ps.setString(1, user.getMechanismId()+"%");
								ps.setString(2, bmidz+"%");
								rs = ps.executeQuery();
								while (rs.next()) {
									user = new User();
									user.setId(rs.getLong(1));
									user.setIds(rs.getString(1));
									user.setFirstName(rs.getString(2));
									user.setUsername(rs.getString(3));
									user.setPhoneNumber(rs.getString(4));
									user.setDepartmentId(rs.getString(5));
									user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
									user.setMechanismId(rs.getString(7));
									user.setMechanismName(rs.getString(8));
									user.setEmail(rs.getString(9));
									user.setPassword(rs.getString(10));
									user.setConfirmPassword(rs.getString(10));
									list.add(user);
								}
					    	}
					    }
						sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password	 FROM  app_user a   where 1=1 and a.mechanism_id like ? and a.username=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, user.getMechanismId()+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						while (rs.next()) {
							user = new User();
							user.setId(rs.getLong(1));
							user.setIds(rs.getString(1));
							user.setFirstName(rs.getString(2));
							user.setUsername(rs.getString(3));
							user.setPhoneNumber(rs.getString(4));
							user.setDepartmentId(rs.getString(5));
							user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
							user.setMechanismId(rs.getString(7));
							user.setMechanismName(rs.getString(8));
							user.setEmail(rs.getString(9));
							user.setPassword(rs.getString(10));
							user.setConfirmPassword(rs.getString(10));
							list.add(user);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}else if(user.getFrom().equals("未设置")){
						sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password	 FROM  app_user a   where 1=1 and a.mechanism_id like ? and a.username=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, user.getMechanismId()+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						while (rs.next()) {
							user = new User();
							user.setId(rs.getLong(1));
							user.setIds(rs.getString(1));
							user.setFirstName(rs.getString(2));
							user.setUsername(rs.getString(3));
							user.setPhoneNumber(rs.getString(4));
							user.setDepartmentId(rs.getString(5));
							user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
							user.setMechanismId(rs.getString(7));
							user.setMechanismName(rs.getString(8));
							user.setEmail(rs.getString(9));
							user.setPassword(rs.getString(10));
							user.setConfirmPassword(rs.getString(10));
							list.add(user);
						}
					}else {

						String[] xh=user.getDepartmentId().split(",");
					    for(String bmidz : xh){
					    	if(!bmidz.equals("")){
								sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password	 FROM  app_user a   where 1=1 and a.mechanism_id like ? and a.department_id like ?";
								ps = conn.prepareStatement(sql);
								ps.setString(1, user.getMechanismId()+"%");
								ps.setString(2, bmidz);
								rs = ps.executeQuery();
								while (rs.next()) {
									user = new User();
									user.setId(rs.getLong(1));
									user.setIds(rs.getString(1));
									user.setFirstName(rs.getString(2));
									user.setUsername(rs.getString(3));
									user.setPhoneNumber(rs.getString(4));
									user.setDepartmentId(rs.getString(5));
									user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
									user.setMechanismId(rs.getString(7));
									user.setMechanismName(rs.getString(8));
									user.setEmail(rs.getString(9));
									user.setPassword(rs.getString(10));
									user.setConfirmPassword(rs.getString(10));
									list.add(user);
								}
					    	}
					    }
						sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password	 FROM  app_user a   where 1=1 and a.mechanism_id like ? and a.username=?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, user.getMechanismId()+"%");
						ps.setString(2, username);
						rs = ps.executeQuery();
						while (rs.next()) {
							user = new User();
							user.setId(rs.getLong(1));
							user.setIds(rs.getString(1));
							user.setFirstName(rs.getString(2));
							user.setUsername(rs.getString(3));
							user.setPhoneNumber(rs.getString(4));
							user.setDepartmentId(rs.getString(5));
							user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
							user.setMechanismId(rs.getString(7));
							user.setMechanismName(rs.getString(8));
							user.setEmail(rs.getString(9));
							user.setPassword(rs.getString(10));
							user.setConfirmPassword(rs.getString(10));
							list.add(user);
						}
				        for (int i11 = 0; i11 < list.size() - 1; i11++) {
				            for (int j = i11 + 1; j < list.size(); j++) {
				                if (list.get(i11).equals(list.get(j))) {
				                    list.remove(j);
				                }
				            }
				        }
					}
				}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<User> getItems(User user,String bmid)" + (t2 - t1) + " ms");
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
	public User saveUser(User user) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			String iuserSQL = "INSERT INTO app_user(first_name,username,phone_number,department_id,department_name,mechanism_id,mechanism_name,password,website,account_expired,account_locked,credentials_expired,account_enabled,email,last_name)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iuserSQL);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getPhoneNumber());
			ps.setString(3, user.getPhoneNumber());
			String[] bm=user.getDepartmentName().split("--");
			ps.setString(4, bm[1]);
			ps.setString(5, bm[0]);
			ps.setString(6, user.getMechanismId());
			ps.setString(7, user.getMechanismName());
			ps.setString(8, user.getPassword());
			ps.setString(9, "");
			ps.setBoolean(10, false);
			ps.setBoolean(11, false);
			ps.setBoolean(12, false);
			ps.setBoolean(13, true);
			ps.setString(14, user.getEmail());
			ps.setString(15, user.getFirstName());
			ps.executeUpdate();

			User dto = new User();
			String sSQL = "SELECT a.id FROM app_user a  where a.username=?";
			ps1 = conn.prepareStatement(sSQL);
			ps1.setString(1, user.getPhoneNumber());
			rs = ps1.executeQuery();
			while (rs.next()) {
				dto = new User();
				dto.setId(rs.getLong(1));
				user.setId(rs.getLong(1));
			}
			
				String roleSql = "insert into user_role (user_id,role_id) values(?,-2)";
				ps2 = conn.prepareStatement(roleSql);
				ps2.setLong(1, dto.getId());
				ps2.executeUpdate();
			
			long t2 = System.currentTimeMillis();
			System.out.println("User saveUser(User user)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(rs);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(rs);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return user;
	}
	@Override
	public int update(User user) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uuserSQL = "";
			if(user.getPassword()==null) {
				uuserSQL = "update app_user set first_name=?,department_id=?,department_name=?,mechanism_id=?,mechanism_name=?,email=? where 1=1 and id=? ";
			}else {
				if(user.getPassword().equals("")) {
					uuserSQL = "update app_user set first_name=?,department_id=?,department_name=?,mechanism_id=?,mechanism_name=?,email=? where 1=1 and id=? ";
				}else {
					uuserSQL = "update app_user set first_name=?,department_id=?,department_name=?,mechanism_id=?,mechanism_name=?,email=?,password=? where 1=1 and id=? ";
				}
			}
			ps = conn.prepareStatement(uuserSQL);
			ps.setString(1, user.getFirstName());
			String[] bm=user.getDepartmentName().split("--");
			ps.setString(2, bm[1]);
			ps.setString(3, bm[0]);
			ps.setString(4, user.getMechanismId());
			ps.setString(5, user.getMechanismName());
			ps.setString(6, user.getEmail());
			if(user.getPassword()==null) {
				ps.setLong(7, user.getId());
			}else {
				if(user.getPassword().equals("")) {
					ps.setLong(7, user.getId());
					
				}else {
					ps.setString(7, user.getPassword());
					ps.setLong(8, user.getId());
					
				}
			}
			rv = ps.executeUpdate();
			
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateUser(user user)" + (t2 - t1) + " ms");
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
	public User edit(User user) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String jgid = user.getMechanismId();
			String sql = "SELECT a.id,a.first_name,a.username,a.phone_number,a.department_id,a.department_name,a.mechanism_id,a.mechanism_name,a.email,a.password FROM  app_user a  where 1=1 and a.id=? and a.mechanism_id=? ";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, user.getId());
			ps.setString(2, jgid);
			System.out.println(ps.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getLong(1));
				user.setFirstName(rs.getString(2));
				user.setUsername(rs.getString(3));
				user.setPhoneNumber(rs.getString(4));
				user.setDepartmentId(rs.getString(5));
				user.setDepartmentName(rs.getString(6)+"--"+rs.getString(5));
				user.setMechanismId(rs.getString(7));
				user.setMechanismName(rs.getString(8));
				user.setEmail(rs.getString(9));
				user.setPassword(rs.getString(10));
				user.setConfirmPassword(rs.getString(10));
			}
			long t2 = System.currentTimeMillis();
			System.out.println("User edit(User user) {" + (t2 - t1) + " ms");
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
		return user;
	}
	


    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        Query qry = getSession().createQuery("from User u order by upper(u.username)");
        return qry.list();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    public String getUserPassword(Long userId) {
        JdbcTemplate jdbcTemplate =
                new JdbcTemplate(SessionFactoryUtils.getDataSource(getSessionFactory()));
        Table table = AnnotationUtils.findAnnotation(User.class, Table.class);
        return jdbcTemplate.queryForObject(
                "select password from " + table.name() + " where id=?", String.class, userId);
    }
}
