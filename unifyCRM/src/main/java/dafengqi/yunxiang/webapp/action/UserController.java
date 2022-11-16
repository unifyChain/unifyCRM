package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.model.User;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController extends BasePage implements Serializable {
	
	private static final long serialVersionUID = 6498739293085202452L;


	private List<User> items = null;
	private List<Role> modules;


	private User selected;

	
	public UserController() {
	}
	
	public User prepareCreate() {
		selected = new User();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;

				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				String mechanismmc = (String) getSession().getAttribute("MECHANISMNAME");
				selected.setMechanismName(mechanismmc);
				String table = "app_user";
				k = this.isExistThreePara(table, selected.getPhoneNumber(), "username",mechanismId);
				if (k == 1) {
					JsfUtil.warn("手机号：" + selected.getPhoneNumber() + "已存在");
				} else if (k == 0) {
					if(selected.getPassword().equals(selected.getConfirmPassword())){
						User user = userManager.saveUser(selected);
						if (user.getId()==null) {
							JsfUtil.warn("失败!");
						} else if (user.getId()!=null) {
							JsfUtil.info("成功!");
						} else {
							JsfUtil.error("错误!");
						}
					}else{
						JsfUtil.warn("密码不一致!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			selected=null;
		}
		
	}
	
	

	

	public void displaySelectedMultiple(TreeNode nodes) {
			if(nodes==null){
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setDepartmentId(nodearr[1]);
				selected.setDepartmentName(nodearr[0]+"--"+nodearr[1]);
				selected.setMechanismId(nodearr[2]);
				selected.setMechanismName(nodearr[3]);
			}
	}
	public void displaySelectedMultiples(TreeNode nodes) {
		if(nodes==null){
		}else{
			String nodestr = nodes.getData().toString();
			String[] nodearr = nodestr.split("-");
			//selected.setLsbm(nodearr[1]);
			selected.setDepartmentName(nodearr[0]);
			selected.setMechanismId(nodearr[2]);
			selected.setMechanismName(nodearr[3]);
		}
}


	public void destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
						int k = 0;
						String table = "sys_mechanism";
						String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						k = this.remove(table, selected.getPhoneNumber(), "phonenumber",mechanismId);
						if (k >= 1) {
							JsfUtil.warn("此账户不可删除!");
						} else if (k == 0) {
							String username = (String) getSession().getAttribute("USERNAME");
							if(username.equals(selected.getPhoneNumber())) {
								JsfUtil.warn("当前账户不可删除!");
							}else {
								int i = this.remove("app_user", ""+selected.getId(),mechanismId);
								if (i == 0) {
									JsfUtil.warn("失败!");
								} else if (i == 1) {
									JsfUtil.info("成功!");
								} else {
									JsfUtil.error("错误!");
								}
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
			selected = null; 
			items = null; 
		}
	}
	

	

	
	public User edit() {
		//获取id
		selected = new User();
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		selected.setMechanismId(mechanismId);
		Long id =new Long( getRequest().getParameter("id"));
		selected.setId(id);
		selected = userManager.edit(selected);
		return selected;
	}
	

	public List<User> getItems() {
		if (items == null||items.size()==0) {
			if(selected==null){
				selected=new User();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentId = (String) getSession().getAttribute("DEPARTMENTAUTHORITY");
			if(departmentId==null) {
				departmentId="";
			}
			selected.setMechanismId(mechanismId);
			selected.setDepartmentId(departmentId);
			selected.setFrom(from);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setUsername(username);
			items = userManager.getItems(selected);
			selected=null;
		}
		return items;
	}

	

	protected void initializeEmbeddableKey() {
		
	}
	
	public String usermechanism() {
		String firstName = getRequest().getParameter("firstName");
		String id = getRequest().getParameter("id");
		getSession().setAttribute("USERNAME", firstName);
		getSession().setAttribute("USERID", id);
		getSession().setAttribute("SFTZ", "是");
		return "usermechanism";
	}

	protected void setEmbeddableKeys() {
	}

	

	public void update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k=0;
				String table = "app_user";
				String id=selected.getId().toString();
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				k = this.isExistThreeParaNotIn(table, selected.getPhoneNumber(),id, "username",mechanismId);
				if (k == 1) {
					JsfUtil.warn("手机号：" + selected.getPhoneNumber() + "已存在");
				} else if (k == 0) {
					selected.setMechanismId(mechanismId);
					String mechanismName = (String) getSession().getAttribute("MECHANISMNAME");
					selected.setMechanismName(mechanismName);
						int i = userManager.update(selected);
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
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "用户管理");
		}
		return modules;
	}
	public User getSelected() {
		return selected;
	}

	public void setItems(List<User> items) {
		this.items = items;
	}

	
	public void setSelected(User selected) {
		this.selected = selected;
	}
	

	
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

}
