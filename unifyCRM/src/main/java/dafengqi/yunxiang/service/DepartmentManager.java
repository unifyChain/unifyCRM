package dafengqi.yunxiang.service;

import java.util.List;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Department;

public interface DepartmentManager extends GenericManager<Department, Long> {

	TreeNode getItems(Department department);
	List<Department> getItemsDepartment(Department department);
	Department edit(Department department);

	public TreeNode root(Department department);
	public TreeNode rootDepartment(Department department);
	int saveDepartment(Department department);
	int save(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range);
	int createUserMechanism(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range);

	int update(Department department);

}