package dafengqi.yunxiang.service.impl;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.DepartmentDao;
import dafengqi.yunxiang.model.Department;
import dafengqi.yunxiang.service.DepartmentManager;

@Service("departmentManager")
public class DepartmentManagerImpl extends GenericManagerImpl<Department, Long> implements DepartmentManager, Serializable {

	DepartmentDao departmentDao;

	@Autowired
	public DepartmentManagerImpl(DepartmentDao departmentDao) {

		this.departmentDao = departmentDao;

	}
	@Override
	public TreeNode getItems(Department department) {
		return departmentDao.getItems(department);
	}
	@Override
	public List<Department> getItemsDepartment(Department department) {
		return departmentDao.getItemsDepartment(department);
	}
	@Override
	public Department edit(Department department) {
		return departmentDao.edit(department);
	}

	@Override
	public TreeNode root(Department department) {
		return departmentDao.root(department);
	}
	@Override
	public TreeNode rootDepartment(Department department) {
		return departmentDao.rootDepartment(department);
	}

	@Override
	public int saveDepartment(Department department) {
		return departmentDao.saveDepartment(department);
	}

	@Override
	public int save(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range) {
		return departmentDao.save(selectedNodes,jgid,jsid,jgmc,jsmc,data_range);
	}
	@Override
	public int createUserMechanism(TreeNode<Department>[] selectedNodes,String jgid,String jsid,String jgmc,String jsmc,String data_range) {
		return departmentDao.createUserMechanism(selectedNodes,jgid,jsid,jgmc,jsmc,data_range);
	}
	@Override
	public int update(Department department) {
		return departmentDao.update(department);
	}

}