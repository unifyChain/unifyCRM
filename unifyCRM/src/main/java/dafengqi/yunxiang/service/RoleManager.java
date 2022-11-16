package dafengqi.yunxiang.service;

import java.util.List;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.RoleModule;


public interface RoleManager extends GenericManager<Role, Long> {
    List getRoles(Role role);

    Role getRole(String rolename);
    int saveRole(Role role);
	List<Role> getItems(Role role);
	int update(Role role);

    void removeRole(String rolename);

	TreeNode<RoleModule> getItemRoles(Role selected);

	int saveModule(TreeNode<RoleModule>[] selectedNodes,Role role);


	Role edit(Role selected);
}
