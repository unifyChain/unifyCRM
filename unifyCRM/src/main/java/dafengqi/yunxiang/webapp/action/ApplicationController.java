package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Application;
import dafengqi.yunxiang.service.ApplicationManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "applicationController")
@SessionScoped
public class ApplicationController extends BasePage implements Serializable {

	private static final long serialVersionUID = 6498739293085202452L;
	private ApplicationManager applicationManager;

	

	private List<Application> items = null;
	private List<Application> itemsManagement = null;
	private List<Application> itemsMenu = null;
    private TreeNode<Application>[] selectedNodes;

	private Application selected;

	
	public ApplicationController() {
	}


	public void save() {
			setEmbeddableKeys();
			try {
				int k = 0;
				if(selected==null) {
					selected=new Application();
				}
				String name = getRequest().getParameter("name");
				String remarks = getRequest().getParameter("remarks");
				String price = getRequest().getParameter("price");
				String id = getRequest().getParameter("id");
				selected.setName(name);
				selected.setRemarks(remarks);
				selected.setPrice(new BigDecimal(price));
				selected.setId(id);
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				String mechanismname = (String) getSession().getAttribute("MECHANISMNAME");
				selected.setMechanismId(mechanismid);
				selected.setMechanismName(mechanismname);
				String table = "application";
				k = this.isExistThreePara(table, selected.getName(), "name",mechanismid);
				if (k == 1) {
					JsfUtil.warn("您已购买" + selected.getName() + "!");
				} else if (k == 0) {
					int i = applicationManager.saveApplication(selected);
					if (i == 0) {
						JsfUtil.warn("应用购买失败!");
					} else if (i == 1) {
						JsfUtil.info("应用购买成功!");
					} else {
						JsfUtil.error("应用购买错误!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "应用购买错误!");
			}
			if (!JsfUtil.isValidationFailed()) {
				items = null; 
			}
		
	}

	public void destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("application", ""+selected.getId(),mechanismId);
					if (i == 0) {
						JsfUtil.warn("应用删除失败!");
					} else if (i == 1) {
						JsfUtil.info("应用删除成功!");
					} else {
						JsfUtil.error("应用删除错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "应用删除错误!");
			}
		} else {
			JsfUtil.warn("应用删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
	}
	

	public List<Application> getItems() {
		if (items == null||items.size()==0) {
			if(selected==null){
				selected=new Application();
			}
			items = applicationManager.getItems(selected);
			selected=null;
		}
		return items;
	}
	

	public ApplicationManager getApplicationManager() {
		return applicationManager;
	}

	public Application getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public Application prepareCreate() {
		selected = new Application();
		initializeEmbeddableKey();
		return selected;
	}

	protected void setEmbeddableKeys() {
	}

	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public void setApplicationszManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public void setSelected(Application selected) {
		this.selected = selected;
	}

	public void setItems(List<Application> items) {
		this.items = items;
	}


	public TreeNode<Application>[] getSelectedNodes() {
		return selectedNodes;
	}

	public void setSelectedNodes(TreeNode<Application>[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}


	public List<Application> getItemsManagement() {
		if (itemsManagement == null||itemsManagement.size()==0) {
			if(selected==null){
				selected=new Application();
			}
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(mechanismid);
			itemsManagement = applicationManager.getItemsManagement(selected);
			selected=null;
		}
	
		return itemsManagement;
	}

	public void setItemsManagement(List<Application> itemsManagement) {
		this.itemsManagement = itemsManagement;
	}

	public List<Application> getItemsMenu() {
		if (itemsMenu == null||itemsMenu.size()==0) {
			if(selected==null){
				selected=new Application();
			}
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			String rolename = (String) getSession().getAttribute("ROLENAMES");
			
			selected.setMechanismId(mechanismid);
			selected.setRoleName(rolename);
			itemsMenu = applicationManager.getItemsMenu(selected);
			selected=null;
		}
	
		return itemsMenu;
	}

	public void setItemsMenu(List<Application> itemsMenu) {
		this.itemsMenu = itemsMenu;
	}

	

}
