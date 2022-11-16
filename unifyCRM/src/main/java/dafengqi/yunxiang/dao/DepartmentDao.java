package dafengqi.yunxiang.dao;

import java.util.List;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Department;

public interface DepartmentDao extends GenericDao<Department, Long> {


	public TreeNode getItems(Department department);
	public List<Department> getItemsDepartment(Department department);

	public Department edit(Department department);


	public TreeNode root(Department department);
	public TreeNode rootDepartment(Department department);

	public int saveDepartment(Department department);
	public int save(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range);
	public int createUserMechanism(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range);

	public int update(Department department);

}