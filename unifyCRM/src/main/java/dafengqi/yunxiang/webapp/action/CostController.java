package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.CostManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "costController")
@SessionScoped
public class CostController extends BasePage implements Serializable {
	@FacesConverter(forClass = Cost.class)
	public static class CostszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Cost) {
				Cost o = (Cost) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Cost.class.getName() });
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

	private List<Cost> items = null;
	
	private List<Cost> itemsOfMy = null;
	private List<Role> modules;
	private String id;
	
	private CostManager costManager;

	private Cost selected;

	public CostController() {
	}
	public Cost reset() {
		selected=new Cost();
		return selected;
	}

	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
					String jgid = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(jgid);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
					int i = costManager.saveCost(selected);
					if (i == 0) {
						JsfUtil.warn("失败!");
					} else if (i == 1) {
						JsfUtil.info("成功!");
					} else {
						JsfUtil.error("错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
		}
		return "costs";
	}


	public String remove() {
		id= getRequest().getParameter("id");
	return "costs";
}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.remove("crm_cost", id,mechanismId);
				if (i == 0) {
					JsfUtil.warn("失败!");
				} else if (i == 1) {
					JsfUtil.info("成功!");
				} else if (i == -2) {
					JsfUtil.error("数据被引用，请先删除子数据!");
				} else {
					JsfUtil.error("错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
		}
		return "costs";
	}


	public List<Cost> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Cost();
			}
			String jgid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(jgid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = costManager.getItems(selected);
		}
		return items;
	}
	public List<Cost> getItemsOfMy() {
		if (itemsOfMy == null) {
			if(selected==null){
				selected=new Cost();
			}
			String jgid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(jgid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsOfMy = costManager.getItemsOfMy(selected);
		}
		return itemsOfMy;
	}


	public CostManager getCostManager() {
		return costManager;
	}

	public Cost getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

    @PostConstruct
    public void init() {

    }
	public String prepareCreate() {
		selected = new Cost();
		selected.setType("新增");
		return "costForm";
	}
	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Cost();
		}
		selected.setId(id);
		selected = costManager.edit(selected);
		selected.setType("编辑");
		return "costForm";
	}
	public Cost view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Cost();
		}
		selected.setId(id);
		selected = costManager.edit(selected);
		selected.setType("查看");
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setCostManager(CostManager costManager) {
		this.costManager = costManager;
	}

	public void setCostszManager(CostManager costManager) {
		this.costManager = costManager;
	}

	public void setSelected(Cost selected) {
		this.selected = selected;
	}
	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String jgid = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(jgid);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
					int i = costManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("失败!");
					} else if (i == 1) {
						JsfUtil.info("成功!");
					} else {
						JsfUtil.error("错误!");
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
			itemsOfMy = null; 
		}
		return "costs";
	}

 
	public void setMyitems(List<Cost> myitems) {
		this.itemsOfMy = myitems;
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "费用");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Cost> search() {
		items = null; 
		itemsOfMy = null; 
		return items;
	}
	public void dateRange() {
		selected.setTimeOfOccurrences("");
	}
	public void timeOfOccurrence() {
		selected.setCreateDateRange(new ArrayList<>());
	}



}
