package dafengqi.yunxiang.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.primefaces.avalon.domain.Document;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Repository;

import dafengqi.yunxiang.dao.DepartmentDao;
import dafengqi.yunxiang.model.Department;
import dafengqi.yunxiang.util.ResourceManager;

@Repository("departmentDao")
public class DepartmentDaoHibernate extends GenericDaoHibernate<Department, Long> implements DepartmentDao {
	protected java.sql.Connection userConn;
	final boolean isConnSupplied = (userConn != null);

	public DepartmentDaoHibernate() {
		super(Department.class);
	}

	public TreeNode getItems(Department department) {
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
		TreeNode list = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			list.setExpanded(false);
			String sql = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where length(id)<=3 and mechanism_id=? ORDER BY id asc";
			ps = conn.prepareStatement(sql);
			ps.setString(1, department.getMechanismId());
			rs = ps.executeQuery();
			while (rs.next()) {
				TreeNode l1Node = new DefaultTreeNode(new Department(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
						rs.getString(9), rs.getString(10)), list);
				sql = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where department_id=?  ORDER BY id asc ";
				ps1 = conn.prepareStatement(sql);
				ps1.setString(1, rs.getString(1));
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TreeNode l2Node = new DefaultTreeNode(new Department(rs1.getString(1), rs1.getString(2),
							rs1.getString(3), rs1.getString(4), rs1.getString(5), rs1.getString(6), rs1.getString(7),
							rs1.getString(8), rs1.getString(9), rs1.getString(10)), l1Node);
					sql = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where department_id=? ORDER BY id asc ";
					ps2 = conn.prepareStatement(sql);
					ps2.setString(1, rs1.getString(1));
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						TreeNode l3Node = new DefaultTreeNode(new Department(rs2.getString(1), rs2.getString(2),
								rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
								rs2.getString(7), rs2.getString(8), rs2.getString(9), rs2.getString(10)), l2Node);
						sql = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where department_id=?   ORDER BY id asc ";
						ps3 = conn.prepareStatement(sql);
						ps3.setString(1, rs2.getString(1));
						rs3 = ps3.executeQuery();
						while (rs3.next()) {
							TreeNode l4Node = new DefaultTreeNode(new Department(rs3.getString(1), rs3.getString(2),
									rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(6),
									rs3.getString(7), rs3.getString(8), rs3.getString(9), rs3.getString(10)), l3Node);
							sql = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where department_id=?   ORDER BY id asc ";
							ps4 = conn.prepareStatement(sql);
							ps4.setString(1, rs3.getString(1));
							rs4 = ps4.executeQuery();
							while (rs4.next()) {
								new DefaultTreeNode(new Department(rs4.getString(1), rs4.getString(2), rs4.getString(3),
										rs4.getString(4), rs4.getString(5), rs4.getString(6), rs4.getString(7),
										rs4.getString(8), rs4.getString(9), rs4.getString(10)), l4Node);
							}
						}
					}
				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("TreeNode getItems(Department department)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(rs4);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(rs);
			ResourceManager.close(rs1);
			ResourceManager.close(rs2);
			ResourceManager.close(rs3);
			ResourceManager.close(rs4);
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			ResourceManager.close(ps2);
			ResourceManager.close(ps3);
			ResourceManager.close(ps4);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return list;
	}

	@Override
	public List<Department> getItemsDepartment(Department department) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List<Department> list = new ArrayList<Department>();
		String id = department.getId();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String sqls = "";
			if (department.getFrom().equals("全部")) {
				sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? ORDER BY id asc";
				ps = conn.prepareStatement(sqls);
				ps.setString(1, department.getMechanismId() + "%");
				rs = ps.executeQuery();
				while (rs.next()) {
					department = new Department();
					department.setId(rs.getString(1));
					department.setName(rs.getString(2));
					department.setMechanismId(rs.getString(3));
					department.setMechanismName(rs.getString(4));
					department.setDepartmentId(rs.getString(5));
					department.setDepartmentName(rs.getString(6));
					department.setCreateId(rs.getString(7));
					department.setCreateDate(rs.getString(8));
					department.setUpdateBy(rs.getString(9));
					department.setUpdateDate(rs.getString(10));
					list.add(department);
				}
			} else {
				if (department.getFrom().equals("本部门")) {
					String[] xh = department.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? and id like ? ORDER BY id asc";
							ps = conn.prepareStatement(sqls);
							ps.setString(1, department.getMechanismId() + "%");
							ps.setString(2, bmid + "%");
							rs = ps.executeQuery();
							while (rs.next()) {
								department = new Department();
								department.setId(rs.getString(1));
								department.setName(rs.getString(2));
								department.setMechanismId(rs.getString(3));
								department.setMechanismName(rs.getString(4));
								department.setDepartmentId(rs.getString(5));
								department.setDepartmentName(rs.getString(6));
								department.setCreateId(rs.getString(7));
								department.setCreateDate(rs.getString(8));
								department.setUpdateBy(rs.getString(9));
								department.setUpdateDate(rs.getString(10));
								list.add(department);
							}
						}
					}
					sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? and id like ? ORDER BY id asc";
					ps = conn.prepareStatement(sqls);
					ps.setString(1, department.getMechanismId() + "%");
					ps.setString(2, id + "%");
					rs = ps.executeQuery();
					while (rs.next()) {
						department = new Department();
						department.setId(rs.getString(1));
						department.setName(rs.getString(2));
						department.setMechanismId(rs.getString(3));
						department.setMechanismName(rs.getString(4));
						department.setDepartmentId(rs.getString(5));
						department.setDepartmentName(rs.getString(6));
						department.setCreateId(rs.getString(7));
						department.setCreateDate(rs.getString(8));
						department.setUpdateBy(rs.getString(9));
						department.setUpdateDate(rs.getString(10));
						list.add(department);
					}
					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}
				} else if (department.getFrom().equals("未设置")) {
					sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? and id like ? ORDER BY id asc";
					ps = conn.prepareStatement(sqls);
					ps.setString(1, department.getMechanismId() + "%");
					ps.setString(2, id + "%");
					rs = ps.executeQuery();
					while (rs.next()) {
						department = new Department();
						department.setId(rs.getString(1));
						department.setName(rs.getString(2));
						department.setMechanismId(rs.getString(3));
						department.setMechanismName(rs.getString(4));
						department.setDepartmentId(rs.getString(5));
						department.setDepartmentName(rs.getString(6));
						department.setCreateId(rs.getString(7));
						department.setCreateDate(rs.getString(8));
						department.setUpdateBy(rs.getString(9));
						department.setUpdateDate(rs.getString(10));
						list.add(department);
					}

				} else {
					String[] xh = department.getDepartmentId().split(",");
					for (String bmid : xh) {
						if (!bmid.equals("")) {
							sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? and id like ? ORDER BY id asc";
							ps = conn.prepareStatement(sqls);
							ps.setString(1, department.getMechanismId() + "%");
							ps.setString(2, bmid);
							rs = ps.executeQuery();
							while (rs.next()) {
								department = new Department();
								department.setId(rs.getString(1));
								department.setName(rs.getString(2));
								department.setMechanismId(rs.getString(3));
								department.setMechanismName(rs.getString(4));
								department.setDepartmentId(rs.getString(5));
								department.setDepartmentName(rs.getString(6));
								department.setCreateId(rs.getString(7));
								department.setCreateDate(rs.getString(8));
								department.setUpdateBy(rs.getString(9));
								department.setUpdateDate(rs.getString(10));
								list.add(department);
							}
						}
					}
					sqls = "select id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date from sys_department where mechanism_id like ? and id like ? ORDER BY id asc";
					ps = conn.prepareStatement(sqls);
					ps.setString(1, department.getMechanismId() + "%");
					ps.setString(2, id + "%");
					rs = ps.executeQuery();
					while (rs.next()) {
						department = new Department();
						department.setId(rs.getString(1));
						department.setName(rs.getString(2));
						department.setMechanismId(rs.getString(3));
						department.setMechanismName(rs.getString(4));
						department.setDepartmentId(rs.getString(5));
						department.setDepartmentName(rs.getString(6));
						department.setCreateId(rs.getString(7));
						department.setCreateDate(rs.getString(8));
						department.setUpdateBy(rs.getString(9));
						department.setUpdateDate(rs.getString(10));
						list.add(department);
					}
					for (int i = 0; i < list.size() - 1; i++) {
						for (int j = i + 1; j < list.size(); j++) {
							if (list.get(i).equals(list.get(j))) {
								list.remove(j);
							}
						}
					}

				}
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Department> getItems(Department department)" + (t2 - t1) + " ms");
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
	public Department edit(Department department) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<Department>();
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String jgid = department.getMechanismId();
			String sql = "SELECT id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date	 FROM  sys_department   where 1=1 and id=? and mechanism_id=? ORDER BY id asc ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, department.getId());
			ps.setString(2, jgid);
			rs = ps.executeQuery();
			while (rs.next()) {
				department = new Department();
				department.setId(rs.getString(1));
				department.setName(rs.getString(2));
				department.setMechanismId(rs.getString(3));
				department.setMechanismName(rs.getString(4));
				department.setDepartmentId(rs.getString(5));
				department.setDepartmentName(rs.getString(6));
				department.setCreateId(rs.getString(7));
				department.setCreateDate(rs.getString(8));
				department.setUpdateBy(rs.getString(9));
				department.setUpdateDate(rs.getString(10));
			}
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("List<Department> getItems(Department department)" + (t2 - t1) + " ms");
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
		return department;
	}

	public TreeNode root(Department department) {
		long t1 = System.currentTimeMillis();
		TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);

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
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			root.setExpanded(false);
			String sql = "select id,name from sys_department where length(id)<=3 and mechanism_id like ? ORDER BY id asc";
			ps = conn.prepareStatement(sql);
			ps.setString(1, department.getMechanismId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				TreeNode l1Node = new DefaultTreeNode(
						new Document("" + rs.getString(2) + "-" + rs.getString(1) + "", rs.getString(2), "1级"), root);
				sql = "select id,name from sys_department where department_id=?  ORDER BY id asc ";
				ps1 = conn.prepareStatement(sql);
				ps1.setString(1, rs.getString(1));
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TreeNode l2Node = new DefaultTreeNode(
							new Document("" + rs1.getString(2) + "-" + rs1.getString(1) + "", rs1.getString(2), "2级"),
							l1Node);
					sql = "select id,name from sys_department where department_id=?   ORDER BY id asc ";
					ps2 = conn.prepareStatement(sql);
					ps2.setString(1, rs1.getString(1));
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						TreeNode l3Node = new DefaultTreeNode(new Document(
								"" + rs2.getString(2) + "-" + rs2.getString(1) + "", rs2.getString(2), "3级"), l2Node);
						sql = "select id,name from sys_department where department_id=?   ORDER BY id asc ";
						ps3 = conn.prepareStatement(sql);
						ps3.setString(1, rs2.getString(1));
						rs3 = ps3.executeQuery();
						while (rs3.next()) {
							TreeNode l4Node = new DefaultTreeNode(
									new Document("" + rs3.getString(2) + "-" + rs3.getString(1) + "", rs3.getString(2),
											"4级"),
									l3Node);
							sql = "select id,name from sys_department where department_id=?   ORDER BY id asc ";
							ps4 = conn.prepareStatement(sql);
							ps4.setString(1, rs3.getString(1));
							rs4 = ps4.executeQuery();
							while (rs4.next()) {
								new DefaultTreeNode(new Document("" + rs4.getString(2) + "-" + rs4.getString(1) + "",
										rs4.getString(2), "5级"), l4Node);
							}
						}
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

	public TreeNode rootDepartment(Department department) {
		long t1 = System.currentTimeMillis();
		TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);

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
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			root.setExpanded(false);
			String sql = "select id,name,mechanism_id,mechanism_name from sys_department where length(id)<=3 and mechanism_id like ?  ORDER BY id asc";
			ps = conn.prepareStatement(sql);
			ps.setString(1, department.getMechanismId() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				TreeNode l1Node = new DefaultTreeNode(new Document("" + rs.getString(2) + "-" + rs.getString(1) + "-"
						+ rs.getString(3) + "-" + rs.getString(4) + "", rs.getString(2), "1级"), root);
				sql = "select id,name,mechanism_id,mechanism_name from sys_department where department_id=?  ORDER BY id asc ";
				ps1 = conn.prepareStatement(sql);
				ps1.setString(1, rs.getString(1));
				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					TreeNode l2Node = new DefaultTreeNode(new Document("" + rs1.getString(2) + "-" + rs1.getString(1)
							+ "-" + rs1.getString(3) + "-" + rs1.getString(4) + "", rs1.getString(2), "2级"), l1Node);
					sql = "select id,name,mechanism_id,mechanism_name from sys_department where department_id=?   ORDER BY id asc ";
					ps2 = conn.prepareStatement(sql);
					ps2.setString(1, rs1.getString(1));
					rs2 = ps2.executeQuery();
					while (rs2.next()) {
						TreeNode l3Node = new DefaultTreeNode(
								new Document("" + rs2.getString(2) + "-" + rs2.getString(1) + "-" + rs2.getString(3)
										+ "-" + rs2.getString(4) + "", rs2.getString(2), "3级"),
								l2Node);
						sql = "select id,name,mechanism_id,mechanism_name from sys_department where department_id=?   ORDER BY id asc ";
						ps3 = conn.prepareStatement(sql);
						ps3.setString(1, rs2.getString(1));
						rs3 = ps3.executeQuery();
						while (rs3.next()) {
							TreeNode l43Node = new DefaultTreeNode(
									new Document("" + rs3.getString(2) + "-" + rs3.getString(1) + "-" + rs3.getString(3)
											+ "-" + rs3.getString(4) + "", rs3.getString(2), "4级"),
									l3Node);
							sql = "select id,name,mechanism_id,mechanism_name from sys_department where department_id=?   ORDER BY id asc ";
							ps4 = conn.prepareStatement(sql);
							ps4.setString(1, rs3.getString(1));
							rs4 = ps4.executeQuery();
							while (rs4.next()) {
								new DefaultTreeNode(new Document("" + rs4.getString(2) + "-" + rs4.getString(1) + "-"
										+ rs4.getString(3) + "-" + rs4.getString(4) + "", rs4.getString(2), "5级"),
										l43Node);
							}
						}
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
	public int save(TreeNode<Department>[] selectedNodes, String jgid, String jsid, String jgmc, String jsmc,
			String data_range) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from role_mechanism where 1=1 and mechanism_id=? and role_id=?";
			ps1 = conn.prepareStatement(dsql);
			ps1.setString(1, jgid);
			ps1.setString(2, jsid);
			ps1.executeUpdate();
			String bmid = "";
			String bmmc = "";
			if (selectedNodes != null) {
				int i = 0;
				for (TreeNode<Department> node : selectedNodes) {
					i++;
					Department nodestr = node.getData();
					bmid += nodestr.getId() + ",";
					bmmc += nodestr.getName() + ",";
				}
				if (i > 0) {
					bmid = bmid.substring(0, bmid.length() - 1);
					bmmc = bmmc.substring(0, bmmc.length() - 1);
				}

			}
			String iappuserSQL = "INSERT INTO role_mechanism(id,department_id,department_name,data_range,create_id,mechanism_id,mechanism_name,role_id,role_name)VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iappuserSQL);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, bmid);
			ps.setString(3, bmmc);
			ps.setString(4, data_range);
			ps.setString(5, "");
			ps.setString(6, jgid);
			ps.setString(7, jgmc);
			ps.setString(8, jsid);
			ps.setString(9, jsmc);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveUser(User appuser)" + (t2 - t1) + " ms");
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
	public int createUserMechanism(TreeNode<Department>[] selectedNodes, String jgid, String jsid, String jgmc,
			String jsmc, String data_range) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs1 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			String dsql = "delete from user_mechanism where 1=1 and mechanism_id=? and user_id=?";
			ps1 = conn.prepareStatement(dsql);
			ps1.setString(1, jgid);
			ps1.setString(2, jsid);
			ps1.executeUpdate();
			String bmid = "";
			String bmmc = "";
			if (selectedNodes != null) {
				int i = 0;
				for (TreeNode<Department> node : selectedNodes) {
					i++;
					Department nodestr = node.getData();
					bmid += nodestr.getId() + ",";
					bmmc += nodestr.getName() + ",";
				}
				if (i > 0) {
					bmid = bmid.substring(0, bmid.length() - 1);
					bmmc = bmmc.substring(0, bmmc.length() - 1);
				}

			}
			String iappuserSQL = "INSERT INTO user_mechanism(id,department_id,department_name,data_range,create_id,mechanism_id,mechanism_name,user_id,user_name)VALUES(?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(iappuserSQL);
			ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, bmid);
			ps.setString(3, bmmc);
			ps.setString(4, data_range);
			ps.setString(5, "");
			ps.setString(6, jgid);
			ps.setString(7, jgmc);
			ps.setString(8, jsid);
			ps.setString(9, jsmc);
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveUser(User appuser)" + (t2 - t1) + " ms");
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
	public int saveDepartment(Department department) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps9 = null;
		PreparedStatement ps11 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs9 = null;
		ResultSet rs10 = null;
		PreparedStatement ps12 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);
			Random random = new Random();
			String bmid = "";
			if (department.getDepartmentId() == null) {
				department.setDepartmentId("");
			}
			if (department.getDepartmentId().length() >= 3 && !department.getCreateId().equals("admin")) {
				String sql = "";
				if (department.getDepartmentId().length() == 3) {
					sql = "select id from sys_department where 1=1 and department_id like '"
							+ department.getDepartmentId() + "" + "' order by id desc limit 0,1";
				} else {
					sql = "select id from sys_department where 1=1 and id like '" + department.getDepartmentId() + "0"
							+ "%" + "' order by id desc limit 0,1";
				}
				ps1 = conn.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				String jgid = "";
				String fajgid = "";
				int ssjg = 0;
				if (rs1.next()) {
					fajgid = rs1.getString(1);
					String sql1 = "select id from sys_department where 1=1 and id = '" + department.getDepartmentId()
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
					bmid = ljl + newjgid;
//					if (fajgid.length() == 3) {
//						bmid = department.getDepartmentId() + "001";
//						String sqlS = "select id from sys_department where 1=1 and id like '" + department.getDepartmentId() + "%"
//								+ "' ";
//						ps2 = conn.prepareStatement(sqlS);
//						rs2 = ps2.executeQuery();
//						if (rs2.next()) {
//							fajgid = rs2.getString(1);
//						}
//					} else {
//						String sql1 = "select id from sys_department where 1=1 and id = '"
//								+ department.getDepartmentId() + "' order by id desc limit 0,1";
//						ps3 = conn.prepareStatement(sql1);
//						rs3 = ps3.executeQuery();
//						jgid = rs1.getString(1);
//						int jl = jgid.length();
//						String ljl = "";
//						int newjgid = Integer.valueOf(fajgid) + 1;
//						int len = jl - String.valueOf(newjgid).length();
//						for (int k = 0; k < len; k++) {
//							ljl += "0";
//						}
//						bmid = ljl + newjgid;
//					
//					}

				} else {
					bmid = department.getDepartmentId() + "001";
					String sqlS = "select id from sys_department where 1=1 and id like '" + department.getDepartmentId()
							+ "%" + "' ";
					ps2 = conn.prepareStatement(sqlS);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						fajgid = rs2.getString(1);
					}
				}
			} else {
				String sql = "select id from sys_department where 1=1  and (department_id ='' or department_id is null) order by id desc limit 0,1";
				ps1 = conn.prepareStatement(sql);
				rs1 = ps1.executeQuery();
				String jgid = "";
				if (rs1.next()) {
					jgid = rs1.getString(1);
					int jl = jgid.length();
					String ljl = "";
					int newjgid = Integer.valueOf(jgid) + 1;
					int len = jl - String.valueOf(newjgid).length();
					for (int k = 0; k < len; k++) {
						ljl += "0";
					}
					bmid = ljl + newjgid;
				} else {
					bmid = "001";
				}
			}
			String idepartmentSQL = "INSERT INTO sys_department(id,name,mechanism_id,mechanism_name,department_id,department_name,create_id,create_date,update_id,update_date)VALUES(?,?,?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(idepartmentSQL);
			ps.setString(1, bmid);
			ps.setString(2, department.getName());
			ps.setString(3, department.getMechanismId());
			ps.setString(4, department.getMechanismName());
			ps.setString(5, department.getDepartmentId());
			ps.setString(6, department.getDepartmentName());
			ps.setString(7, department.getCreateId());
			ps.setString(8, department.getCreateDate());
			ps.setString(9, department.getUpdateBy());
			ps.setString(10, department.getUpdateDate());
			rv = ps.executeUpdate();

			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int saveDepartment(Department department)" + (t2 - t1) + " ms");
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
	public int update(Department department) {
		long t1 = System.currentTimeMillis();

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps11 = null;
		PreparedStatement ps12 = null;
		PreparedStatement ps9 = null;
		ResultSet rs9 = null;
		ResultSet rs10 = null;
		int rv = 0;
		try {
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
			conn.setAutoCommit(false);

			String jgid = department.getMechanismId();
			String cgddSQLc = "SELECT a.userId FROM sys_department a  where a.id=? and a.mechanism_id=?";
			ps11 = conn.prepareStatement(cgddSQLc);
			ps11.setString(1, department.getId());
			ps11.setString(2, jgid);
			rs9 = ps11.executeQuery();
			String sz = "";
			while (rs9.next()) {
				sz = rs9.getString(1);
			}
			String udepartmentSQL = "update sys_department set name=?,mechanism_id=?,mechanism_name=?,department_id=?,department_name=?,create_id=?,create_date=?,update_id=?,update_date=? where 1=1 and id=? and mechanism_id=?";
			ps = conn.prepareStatement(udepartmentSQL);
			ps.setString(1, department.getName());
			ps.setString(2, department.getMechanismId());
			ps.setString(3, department.getMechanismName());
			ps.setString(4, department.getDepartmentId());
			ps.setString(5, department.getDepartmentName());
			ps.setString(6, department.getCreateId());
			ps.setString(7, department.getCreateDate());
			ps.setString(8, department.getUpdateBy());
			ps.setString(9, department.getUpdateDate());
			ps.setString(10, department.getId());
			ps.setString(11, department.getMechanismId());
			rv = ps.executeUpdate();
			conn.setAutoCommit(true);
			long t2 = System.currentTimeMillis();
			System.out.println("int updateDepartment(department department)" + (t2 - t1) + " ms");
		} catch (SQLException e) {
			rv = -1;
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		} finally {
			ResourceManager.close(ps);
			ResourceManager.close(ps1);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		}
		return rv;
	}

}