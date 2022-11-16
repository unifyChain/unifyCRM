package dafengqi.yunxiang.service.impl;

import java.util.List;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dafengqi.yunxiang.dao.RoleDao;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.RoleModule;
import dafengqi.yunxiang.service.RoleManager;

/**
 * Implementation of RoleManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
    RoleDao roleDao;

    @Autowired
    public RoleManagerImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles(Role role) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public int saveRole(Role role) {
        return roleDao.saveRole(role);
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        roleDao.removeRole(rolename);
    }
	@Override
	public int update(Role role) {
		return roleDao.update(role);
	}

	@Override
	public List getItems(Role role) {
		return roleDao.getItems(role);
	}

	@Override
	public TreeNode getItemRoles(Role role) {
		return roleDao.getItemRoles(role);
	}

	@Override
	public int saveModule(TreeNode<RoleModule>[] selectedNodes,Role role) {
		return roleDao.saveModule(selectedNodes,role);
	}

	@Override
	public Role edit(Role selected) {
		return roleDao.edit(selected);
	}
}