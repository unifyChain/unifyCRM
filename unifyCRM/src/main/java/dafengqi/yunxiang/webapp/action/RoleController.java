package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.RoleModule;
import dafengqi.yunxiang.service.RoleManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "roleController")
@SessionScoped
public class RoleController extends BasePage implements Serializable {


	private static final long serialVersionUID = 6498739293085202452L;
	private RoleManager roleManager;
	@FacesConverter(forClass = Role.class)
	public static class RoleszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Role) {
				Role o = (Role) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Role.class.getName() });
				return null;
			}
		}

		java.lang.Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(Long long1) {
			StringBuilder sb = new StringBuilder();
			sb.append(long1);
			return sb.toString();
		}

	}

	private List<Role> items = null;
	private TreeNode<RoleModule> itemRoles = null;
    private TreeNode<RoleModule>[] selectedNodes;
	private Role selected;
	private String roleName;
	private List<Role> modules;
	
	public RoleController() {
	}

	public void create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				selected.setMechanismId((String) getSession().getAttribute("MECHANISMID"));
				selected.setMechanismName((String) getSession().getAttribute("MECHANISMNAME"));
				String table = "role";
				k = this.isExistThreePara(table, selected.getDescription(), "description",selected.getMechanismId());
				if (k == 1) {
					JsfUtil.warn("角色:" + selected.getDescription() + "已存在!");
				} else if (k == 0) {
						int i = roleManager.saveRole(selected);
						if (i == 0) {
							JsfUtil.warn("失败!");
						} else if (i == 1) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		
	}
	public String createModule() {
		if (selectedNodes != null) {
			setEmbeddableKeys();
			try {
				if(selected==null) {
					selected=new Role();
				}
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				String roleId = (String) getSession().getAttribute("ROLEID");
				String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
				String roleName = (String) getSession().getAttribute("ROLENAME");
				selected.setMechanismId(mechanismId);
				selected.setId(Long.valueOf(roleId).longValue());
				selected.setMechanismName(mechanismName);
				selected.setName(roleName);
				int i = roleManager.saveModule(selectedNodes,selected);
				if (i == 0) {
					JsfUtil.warn("失败!");
				} else if (i == 1) {
					JsfUtil.info("成功!");
				} else {
					JsfUtil.error("错误!");
				}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		return "roles";
		
	}

	


	public List<Role> getItems() {
		if (items == null||items.size()==0) {
			if(selected==null){
				selected=new Role();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String rolename = (String) getSession().getAttribute("ROLENAMES");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTAUTHORITY");
			if(departmentid==null) {
				departmentid="";
			}
			selected.setFrom(rolename);
			selected.setMechanismId(mechanismId);
			items = roleManager.getItems(selected);
			selected=null;
		}
		return items;
	}
	

	public Role edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Role();
		}
		selected.setId(Long.valueOf(id));
		selected = roleManager.edit(selected);
		return selected;
	}
	

	public RoleManager getRoleManager() {
		return roleManager;
	}

	public Role getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public Role prepareCreate() {
		selected = new Role();
		initializeEmbeddableKey();
		return selected;
	}

	protected void setEmbeddableKeys() {
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public void setRoleszManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public void setSelected(Role selected) {
		this.selected = selected;
	}
	

	public String module() {
		String description = getRequest().getParameter("description");
		String id = getRequest().getParameter("id");
		getSession().setAttribute("ROLEID", id);
		getSession().setAttribute("ROLENAME", description);
		getSession().setAttribute("SFTZ", "是");
		itemRoles=null;
		return "module";
	}
	public String roleMechanism() {
		String description = getRequest().getParameter("description");
		String id = getRequest().getParameter("id");
		getSession().setAttribute("ROLEID", id);
		getSession().setAttribute("ROLENAME", description);
		getSession().setAttribute("SFTZ", "是");
		itemRoles=null;
		return "rolemechanism";
	}
	public void update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k=0;
				String table = "role";
				String id=selected.getId().toString();
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				k = this.isExistThreeParaNotIn(table, selected.getDescription(),id, "description",mechanismId);
				if (k == 1) {
					JsfUtil.warn("角色名称:" + selected.getDescription() + "已存在!");
				} else if (k == 0) {
						int i = roleManager.update(selected);
						if (i == 0) {
							JsfUtil.warn("失败!");
						} else if (i == 1) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		} else {
			JsfUtil.warn("失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
	}


	public void setItems(List<Role> items) {
		this.items = items;
	}

	public TreeNode<RoleModule> getItemRoles() {
		if (itemRoles == null) {
			if(selected==null){
				selected=new Role();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String roleId = (String) getSession().getAttribute("ROLEID");
			String rolename = (String) getSession().getAttribute("ROLENAMES");
			selected.setRoleName(rolename);
			selected.setMechanismId(mechanismId);
			selected.setId(Long.valueOf(roleId).longValue());
			itemRoles = roleManager.getItemRoles(selected);
		} 
		return itemRoles;
	}

	public void setItemRoles(TreeNode<RoleModule> itemroles) {
		this.itemRoles = itemroles;
	}

	public TreeNode<RoleModule>[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode<RoleModule>[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}


	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleId = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleId, mechanismId, "角色权限");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

	public String getRoleName() {
		roleName = (String) getSession().getAttribute("ROLENAME"); 
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	

}
