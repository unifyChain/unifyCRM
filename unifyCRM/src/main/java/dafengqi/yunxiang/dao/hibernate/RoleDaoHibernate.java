package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.avalon.domain.Document;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.RoleDao;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.RoleModule;
import dafengqi.yunxiang.util.ResourceManager;



@Repository
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

    public RoleDaoHibernate() {
        super(Role.class);
    }


	@Override
	public int saveModule(TreeNode<RoleModule>[] selectedNodes,Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from role_module where 1=1 and mechanism_id=? and role_id=? and (system='CRM' or system='SYS')";
			ps1 = conn.prepareStatement(dsql);
			ps1.setString(1, role.getMechanismId());
			ps1.setString(2, String.valueOf(role.getId()));
			ps1.executeUpdate();
			String s =selectedNodes.toString();
            for (TreeNode<RoleModule> node : selectedNodes) {
            	RoleModule nodestr = node.getData();
    			String iappuserSQL = "INSERT INTO role_module(module_id,module_name,parent_module_id,parent_module_name,mechanism_id,mechanism_name,role_id,role_name,system)VALUES(?,?,?,?,?,?,?,?,?)";
    			ps = conn.prepareStatement(iappuserSQL);
    			ps.setString(1, nodestr.getIds());
    			ps.setString(2, nodestr.getName());
    			ps.setString(3, nodestr.getModuleId());
    			ps.setString(4, nodestr.getModuleName());
    			ps.setString(5, role.getMechanismId());
    			ps.setString(6, role.getMechanismName());
    			ps.setString(7, String.valueOf(role.getId()));
    			ps.setString(8, role.getName());
    			ps.setString(9, nodestr.getSystem());
    			rv = ps.executeUpdate();
			}

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveUser(User appuser)" + (t2 - t1) + " ms");
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
	public int saveRole(Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String iappuserSQL = "INSERT INTO role(description,name,mechanism_id,mechanism_name)VALUES(?,?,?,?)";
			ps = conn.prepareStatement(iappuserSQL);
			ps.setString(1, role.getDescription());
			ps.setString(2, role.getName());
			ps.setString(3, role.getMechanismId());
			ps.setString(4, role.getMechanismName());
			rv = ps.executeUpdate();

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveRole(Role role)" + (t2 - t1) + " ms");
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
	public int update(Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String uappuserSQL = "update role set description=?,name=? where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(uappuserSQL);
			ps.setString(1, role.getDescription());
			ps.setString(2, role.getName());
			ps.setLong(3, role.getId());
			ps.setString(4,role.getMechanismId());
			rv=ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int update(Role role)" + (t2 - t1) + " ms");
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
	public TreeNode getItemRoles(Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null; 
		ResultSet rs4 = null;
		TreeNode list = new DefaultTreeNode(new Document("节点", "-", "Folder"), null);
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			list.setExpanded(true);
			String sqls = "";
			if(role.getRoleName().equals("ROLE_COMPANY")||role.getRoleName().equals("ROLE_ADMIN")) {
				sqls = "select id,name,type,module_id,module_name,system from module where 1=1  and (system='CRM' or system='SYS') and type='应用'  ORDER BY sort asc";
				ps = conn.prepareStatement(sqls);
//				ps.setString(1, role.getMechanismId()+"%");
				rs = ps.executeQuery(); 
				while (rs.next()) {
					TreeNode l1Node = new DefaultTreeNode(
							new RoleModule(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)), list);
					l1Node.setExpanded(true);
					String sqlss = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS') ORDER BY sort asc ";
					ps1 = conn.prepareStatement(sqlss);
					ps1.setString(1, rs.getString(1));
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						TreeNode l2Node = new DefaultTreeNode(
								new RoleModule(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),rs1.getString(6)), l1Node);
						l2Node.setExpanded(true);
						String sqlsss = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS')  ORDER BY sort asc ";
						ps2 = conn.prepareStatement(sqlsss);
						ps2.setString(1, rs1.getString(1));
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							TreeNode l3Node = new DefaultTreeNode(
									new RoleModule(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getString(6)), l2Node);
							l3Node.setExpanded(true);
							int a=0;
							String sqlsss1 = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS')  ORDER BY sort asc ";
							ps4 = conn.prepareStatement(sqlsss1);
							ps4.setString(1, rs2.getString(1));
							rs4 = ps4.executeQuery();
							while (rs4.next()) {
								a++;
								TreeNode l4Node = new DefaultTreeNode(
										new RoleModule(rs4.getString(1),rs4.getString(2),rs4.getString(3),rs4.getString(4),rs4.getString(5),rs4.getString(6)), l3Node);
								l4Node.setExpanded(true);
								String rolemodelsql = "select id from role_module where mechanism_id=? and role_id=? and module_id=? ";
								ps3 = conn.prepareStatement(rolemodelsql);
								ps3.setString(1, role.getMechanismId());
								ps3.setString(2, String.valueOf(role.getId()));
								ps3.setString(3, rs2.getString(1));
								rs3 = ps3.executeQuery();
								while (rs3.next()) {
									l4Node.setSelected(true);
								}
							}
							if(a==0) {
								String rolemodelsql = "select id from role_module where mechanism_id=? and role_id=? and module_id=? ";
								ps3 = conn.prepareStatement(rolemodelsql);
								ps3.setString(1, role.getMechanismId());
								ps3.setString(2, String.valueOf(role.getId()));
								ps3.setString(3, rs2.getString(1));
								rs3 = ps3.executeQuery();
								while (rs3.next()) {
									l3Node.setSelected(true);
								}
							}
						}
					}
				}
			}else {

				sqls = "select id,name,type,module_id,module_name,system from module where 1=1  and (system='CRM' or system='SYS') and type='应用'  ORDER BY sort asc";
				ps = conn.prepareStatement(sqls);
				rs = ps.executeQuery(); 
				while (rs.next()) {
					TreeNode l1Node = new DefaultTreeNode(
							new RoleModule(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)), list);
					l1Node.setExpanded(true);
					String sqlss = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS') ORDER BY sort asc ";
					ps1 = conn.prepareStatement(sqlss);
					ps1.setString(1, rs.getString(1));
					rs1 = ps1.executeQuery();
					while (rs1.next()) {
						TreeNode l2Node = new DefaultTreeNode(
								new RoleModule(rs1.getString(1),rs1.getString(2),rs1.getString(3),rs1.getString(4),rs1.getString(5),rs1.getString(6)), l1Node);
						l2Node.setExpanded(true);
						String sqlsss = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS')  ORDER BY sort asc ";
						ps2 = conn.prepareStatement(sqlsss);
						ps2.setString(1, rs1.getString(1));
						rs2 = ps2.executeQuery();
						while (rs2.next()) {
							TreeNode l3Node = new DefaultTreeNode(
									new RoleModule(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getString(6)), l2Node);
							l3Node.setExpanded(true);
							int a=0;
							String sqlsss1 = "select id,name,type,module_id,module_name,system from module where module_id=?  and (system='CRM' or system='SYS')  ORDER BY sort asc ";
							ps4 = conn.prepareStatement(sqlsss1);
							ps4.setString(1, rs2.getString(1));
							rs4 = ps4.executeQuery();
							while (rs4.next()) {
								a++;
								TreeNode l4Node = new DefaultTreeNode(
										new RoleModule(rs4.getString(1),rs4.getString(2),rs4.getString(3),rs4.getString(4),rs4.getString(5),rs4.getString(6)), l3Node);
								l4Node.setExpanded(true);
								String rolemodelsql = "select id from role_module where mechanism_id=? and role_id=? and module_id=? ";
								ps3 = conn.prepareStatement(rolemodelsql);
								ps3.setString(1, role.getMechanismId());
								ps3.setString(2, String.valueOf(role.getId()));
								ps3.setString(3, rs2.getString(1));
								rs3 = ps3.executeQuery();
								while (rs3.next()) {
									l4Node.setSelected(true);
								}
							}
							if(a==0) {
								String rolemodelsql = "select id from role_module where mechanism_id=? and role_id=? and module_id=? ";
								ps3 = conn.prepareStatement(rolemodelsql);
								ps3.setString(1, role.getMechanismId());
								ps3.setString(2, String.valueOf(role.getId()));
								ps3.setString(3, rs2.getString(1));
								rs3 = ps3.executeQuery();
								while (rs3.next()) {
									l3Node.setSelected(true);
								}
							}
						}
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("TreeNode getItemroles(Role rolemodule,String jgid,String jsid) " + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return list;
	}
	@Override
	public List<Role> getItems(Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Role> list = new ArrayList<Role>();
		String from=role.getFrom();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String sql = "SELECT a.id,a.description,a.name FROM  role a   where 1=1 ";
			ps = conn.prepareStatement(sql);
//			ps.setString(1, role.getMechanismId());
			rs = ps.executeQuery();
			while (rs.next()) {
				role = new Role();
				role.setId(rs.getLong(1));
				role.setDescription(rs.getString(2));
				role.setName(rs.getString(3));
				if(from.equals("ROLE_ADMIN")) {
					list.add(role);	
				}else {
					if(!rs.getString(3).equals("ROLE_ADMIN")) {
						list.add(role);	
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<User> getItems(User appuser)" + (t2 - t1) + " ms");
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
	
    public Role getRoleByName(String rolename) {
        List roles = getSession().createCriteria(Role.class).add(Restrictions.eq("name", rolename)).list();
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

  
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        Session session = getSessionFactory().getCurrentSession();
        session.delete(role);
    }


	@Override
	public Role edit(Role role) {
		long t1 = System.currentTimeMillis();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Role> list = new ArrayList<Role>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT a.id,a.description,a.name  FROM  role a   where 1=1 and a.id = ? and a.mechanism_id=?";
			ps = conn.prepareStatement(sql);
			ps.setLong(1, role.getId());
			ps.setString(2, role.getMechanismId());
			rs = ps.executeQuery();
			while (rs.next()) {
				role = new Role();
				role.setId(rs.getLong(1));
				role.setDescription(rs.getString(2));
				role.setName(rs.getString(3));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("Role edit(Role role) " + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(ps);
			if (!isConnSupplied) {ResourceManager.close(conn);}
		}
		return role;
	}


}
