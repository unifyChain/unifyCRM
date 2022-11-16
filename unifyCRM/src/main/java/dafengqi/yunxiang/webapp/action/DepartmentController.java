package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Department;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.DepartmentManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "departmentController")
@SessionScoped
public class DepartmentController extends BasePage implements Serializable {
	@FacesConverter(forClass = Department.class)
	public static class DepartmentszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Department) {
				Department o = (Department) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Department.class.getName() });
				return null;
			}
		}

		java.lang.Integer getKey(String value) {
			java.lang.Integer key;
			key = Integer.valueOf(value);
			return key;
		}

		String getStringKey(String string) {
			StringBuilder sb = new StringBuilder();
			sb.append(string);
			return sb.toString();
		}

	}

	private static final long serialVersionUID = 6498739293085202452L;

	private TreeNode<Department> items = null;
	private List<Role> modules;
	private List<Department> itemsDepartment = null;

	private DepartmentManager departmentManager;

	private Department selected;
    private TreeNode root;
    private TreeNode rootDepartment;
	private TreeNode selectedNode;
	private TreeNode<Department> selecteds;
    private TreeNode<Department>[] selectedNodes;
	private String roleId;
	private String roleName;
	private String userId;
	private String userName;
	private String dataRange="全部";

	public DepartmentController() {
	}

	public TreeNode<Department> choice() {
		items=null;
		System.out.println("dataRange:"+dataRange);
		return items;
	}
	public void displaySelectedMultipleedit(TreeNode nodes) {
		if(nodes==null){
			JsfUtil.warn("请选择需要编辑的数据!");
		}else{
			String nodestr = nodes.getData().toString();
			String[] nodearr = nodestr.split("-");
			selected.setId(nodearr[0]);
			selected.setName(nodearr[1]);
			selected.setMechanismId(nodearr[2]);
			selected.setMechanismName(nodearr[3]);
			selected.setDepartmentId(nodearr[4]);
			selected.setDepartmentName(nodearr[5]);
		}
}

	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(nodes==null){
				JsfUtil.warn("请选择上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setDepartmentId(nodearr[1]);
				selected.setDepartmentName(nodearr[0]);
			}
	}
	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					    if(selected.getDepartmentId()==null) {
					    	selected.setDepartmentId("");
					    }
						if (selected.getDepartmentId().length()>=15) {
							JsfUtil.warn("不可在创建下级!");
						}else{
							int k = 0;
							String table = "sys_department";
							selected.setId(UUID.randomUUID().toString());
							String mechanismid = (String) getSession().getAttribute("MECHANISMID");
							k = this.isExistThreePara(table, selected.getName(), "name",mechanismid);
							if (k == 1) {
								JsfUtil.warn("名称：" + selected.getName() + "重复");
							} else if (k == 0) {
									selected.setId(UUID.randomUUID().toString());
									selected.setCreateDate(df.format(new Date()));
									selected.setUpdateDate(df.format(new Date()));
									String mechanismId = (String) getSession().getAttribute("MECHANISMID");
									String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
									selected.setMechanismId(mechanismId);
									selected.setMechanismName(mechanismName);
									String username = (String) getSession().getAttribute("USERNAME");
									selected.setCreateId(username);
									int i = departmentManager.saveDepartment(selected);
									if (i == 0) {
										JsfUtil.warn("部门新增失败!");
									} else if (i == 1) {
										JsfUtil.info("部门新增成功!");
									} else {
										JsfUtil.error("部门新增错误!");
									}
							} else if (k == -1) {
								JsfUtil.error("部门新增失败!");
							}
							
						}
						
				
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "部门新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			root=null;
		}
		return "department";
		
	}

	public String createDepartment() {
			setEmbeddableKeys();
			try {
//				int k = 0;
//				String table = "role";
//				k = this.valiAllByCfcx(table, selected.getDescription(), "description");
//				if (k == 1) {
//					JsfUtil.warn("角色�?" + selected.getDescription() + "已存�?");
//				} else if (k == 0) {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				String roleId = (String) getSession().getAttribute("ROLEID");
				String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
				String roleName = (String) getSession().getAttribute("ROLENAME");
				int i = departmentManager.save(selectedNodes,mechanismId,roleId,mechanismName,roleName,dataRange);
				if (i == 0) {
					JsfUtil.warn("部门新增失败!");
				} else if (i == 1) {
					JsfUtil.info("部门新增成功!");
				} else {
					JsfUtil.error("部门新增错误!");
				}
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "部门新增错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		return "role";
		
	}
	public String createUserMechanism() {
		setEmbeddableKeys();
		try {
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String userId = (String) getSession().getAttribute("USERID");
			String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
			String userName = (String) getSession().getAttribute("USERNAME");
			
			int i = departmentManager.createUserMechanism(selectedNodes,mechanismId,userId,mechanismName,userName,dataRange);
			if (i == 0) {
				JsfUtil.warn("部门新增失败!");
			} else if (i == 1) {
				JsfUtil.info("部门新增成功!");
			} else {
				JsfUtil.error("部门新增错误!");
			}
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
			JsfUtil.addErrorMessage(ex, "部门新增错误!");
		}
	if (!JsfUtil.isValidationFailed()) {
		items = null; 
	}
	return "role";
	
}
	public String destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					int k = 0;
						String table = "sys_department";
						String mechanismid = (String) getSession().getAttribute("MECHANISMID");
						k = this.isExistThreePara(table, selected.getId(),"department_id",mechanismid);
						if (k >= 1) {
							JsfUtil.warn("此数据已被绑定!请勿删除!");
						} else if (k == 0) {
							int k1 = 0;
							String table1 = "app_user";
							k1 = this.isExistThreePara(table1, selected.getId(),"department_id",mechanismid);
							if (k1 >= 1) {
								JsfUtil.warn("此数据已被绑定!请勿删除!");
							} else if (k1 == 0) {
								String mechanismId = (String) getSession().getAttribute("MECHANISMID");
								int i = this.remove("sys_department", selected.getId(),mechanismId);
								if (i == 0) {
									JsfUtil.warn("部门删除失败!");
								} else if (i == 1) {
									JsfUtil.info("部门删除成功!");
								} else {
									JsfUtil.error("部门删除错误!");
								}
							}else if (k == -1) {
								JsfUtil.error("部门删除失败!");
							}
						} else if (k == -1) {
							JsfUtil.error("部门删除失败!");
						}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "部门删除错误!");
			}
		} else {
			JsfUtil.warn("部门删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
		return "department";
	}
	public TreeNode getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Department();
			}

			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(mechanismId);
			String from = (String) getSession().getAttribute("FROM");
			String departmentId = (String) getSession().getAttribute("DEPARTMENTID");
			selected.setFrom(from);
			selected.setDepartmentId(departmentId);
			items = departmentManager.getItems(selected);
		}

		return items;
	}

	public DepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public Department getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public Department prepareCreate() {
		selected = new Department();
		root=null;
		return selected;
	}
	public Department edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Department();
		}
		selected.setId(id);
		selected = departmentManager.edit(selected);
		return selected;
	}
	public Department view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Department();
		}
		selected.setId(id);
		selected = departmentManager.edit(selected);
		return selected;
	}
    public void onNodeSelectList(NodeSelectEvent event) {
		Department nodes=selecteds.getData();
		selected=nodes;
    }
    public TreeNode onNodeSelect(NodeSelectEvent event) {
		String nodestr = event.getTreeNode().toString();
		String[] nodearr = nodestr.split("-");
		selected.setDepartmentId(nodearr[1]);
		items = departmentManager.getItems(selected);
		return items;
    }
	protected void setEmbeddableKeys() {
	}

	public void setDepartmentManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setDepartmentszManager(DepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public void setSelected(Department selected) {
		this.selected = selected;
	}
	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "sys_department";
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				k = this.isExistThreeParaNotIn(table, selected.getName(),selected.getId(), "name",mechanismId);
				if (k == 1) {
					JsfUtil.warn("名称：" + selected.getName() + "重复");
				} else if (k == 0) {
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
					selected.setMechanismId(mechanismId);
					selected.setMechanismName(mechanismName);
							int i = departmentManager.update(selected);
							if (i == 0) {
								JsfUtil.warn("部门更新失败!");
							} else if (i == 1) {
								JsfUtil.info("部门更新成功!");
							} else {
								JsfUtil.error("部门更新错误!");
							}
				} else if (k == -1) {
					JsfUtil.error("部门更新失败!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "部门更新错误!");
			}
		} else {
			JsfUtil.warn("部门更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			root = null; 
		}
		return "department";
	}
	public TreeNode getRoot() {
		if(selected==null) {
			selected=new Department();
		}
		String from = (String) getSession().getAttribute("FROM");
		String departmentId = (String) getSession().getAttribute("DEPARTMENTID");
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected.setFrom(from);
		selected.setId(departmentId);
		if (root == null) {
			root = departmentManager.root(selected);
		}
		return root;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}
	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getSelecteds() {
		return selecteds;
	}

	public void setSelecteds(TreeNode selecteds) {
		this.selecteds = selecteds;
	}




	public TreeNode<Department>[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode<Department>[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}

	public List<Department> getItemsDepartment() {
		if(selected==null){
			selected=new Department();
		}

		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		String from = (String) getSession().getAttribute("FROM");
		String departmentId = (String) getSession().getAttribute("BMJLID");
		if(departmentId==null) {
			departmentId="";
		}
		selected.setFrom(from);
		String departmentIds = (String) getSession().getAttribute("DEPARTMENTID");
		selected.setDepartmentId(departmentIds);
		selected.setId(departmentId);
		itemsDepartment = departmentManager.getItemsDepartment(selected);

		return itemsDepartment;
	}

	public void setItemsDepartment(List<Department> itemsDepartment) {
		this.itemsDepartment = itemsDepartment;
	}

	public TreeNode getRootDepartment() {
		if(selected==null) {
			selected=new Department();
		}
		String from = (String) getSession().getAttribute("FROM");
		String id = (String) getSession().getAttribute("DEPARTMENTID");
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		selected.setFrom(from);
		selected.setId(id);
		rootDepartment = departmentManager.rootDepartment(selected);
	
		return rootDepartment;
	}

	public void setRootDepartment(TreeNode rootDepartment) {
		this.rootDepartment = rootDepartment;
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleId = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleId, mechanismId, "部门管理");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

	public String getRoleId() {
		roleId = (String) getSession().getAttribute("ROLEID"); 
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		roleName = (String) getSession().getAttribute("ROLENAME"); 
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		userId = (String) getSession().getAttribute("USERID"); 
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		userName = (String) getSession().getAttribute("USERNAME"); 
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDataRange() {
		return dataRange;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}



}
