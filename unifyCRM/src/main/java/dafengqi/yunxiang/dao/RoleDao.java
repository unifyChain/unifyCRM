package dafengqi.yunxiang.dao;

import java.util.List;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.RoleModule;

public interface RoleDao extends GenericDao<Role, Long> {

    Role getRoleByName(String rolename);
	public int saveRole(Role role);
	public int saveModule(TreeNode<RoleModule>[] selectedNodes,Role role);
    void removeRole(String rolename);
	public List<?> getItems(Role role);
	public TreeNode getItemRoles(Role role);
	public int update(Role role);
	Role edit(Role selected);
}
