package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.primefaces.model.TreeNode;

import com.alibaba.fastjson.JSON;

import dafengqi.yunxiang.model.BusinessOpportunity;
import dafengqi.yunxiang.model.Contacts;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.ContactsManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "contactsController")
@SessionScoped
public class ContactsController extends BasePage implements Serializable {
	@FacesConverter(forClass = Contacts.class)
	public static class ContactsszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Contacts) {
				Contacts o = (Contacts) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Contacts.class.getName() });
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
	private ContactsManager contactsManager;

	private List<Contacts> items = null;
	private List<BusinessOpportunity> itemsBusinessOpportunity = null;
	private List<Contract> itemsContract = null;
	private List<Role> modules;
	private List<Contacts> itemsOfMy = null;

	private Contacts selected;
	private String id;

	public ContactsController() {
	}

	public Contacts reset() {
		selected=new Contacts();
		return selected;
	}

	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selected==null) {
				selected=new Contacts();
			}
			if(nodes==null){
				JsfUtil.warn("请上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setDepartmentIds(nodearr[1]);
				selected.setDepartmentName(nodearr[0]);
			}
	}

	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "crm_contacts";
				selected.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
					selected.setId(UUID.randomUUID().toString());
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
					String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
					String bmmc = (String) getSession().getAttribute("BMMC");
					selected.setDepartmentId(departmentid);
					selected.setDepartmentName(bmmc);
					int i = contactsManager.saveContacts(selected);
					if (i == 0) {
						JsfUtil.warn("联系人新增失败!");
					} else if (i == 1) {
						this.sysLog("联系人新", JSON.toJSONString(selected));
						JsfUtil.info("联系人新增成功!");
					} else {
						JsfUtil.error("联系人新增错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
			selected=new Contacts();
		}
		return "contacts";
	}

	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
	return "contacts";
	}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						int i = this.remove("crm_contacts", id,mechanismId);
						if (i == 0) {
							JsfUtil.warn("联系人删除失败!");
						} else if (i == 1) {
							this.sysLog("联系人删", JSON.toJSONString(id));
							JsfUtil.info("联系人删除成功!");
						} else if (i == -2) {
							JsfUtil.error("联系数据被引用，请先删除子数据!");
						} else {
							JsfUtil.error("联系人删除错误!");
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
		return "contacts";
	}
	public List<Contacts> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Contacts();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = contactsManager.getItems(selected);
		}
		return items;
	}

	public List<Contacts> getItemsOfMy() {
		if (itemsOfMy == null) {
			if(selected==null){
				selected=new Contacts();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			String sfbrxg = (String) getSession().getAttribute("SFBRXG");
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsOfMy = contactsManager.getItemsOfMy(selected);
		}
		return itemsOfMy;
	}

	public ContactsManager getContactsManager() {
		return contactsManager;
	}

	public Contacts getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public String prepareCreate() {
		selected = new Contacts();
		return "contactsForm";
	}
	public Contacts edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Contacts();
		}
		selected.setId(id);
		selected = contactsManager.edit(selected);
		return selected;
	}
	public Contacts view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Contacts();
		}
		selected.setId(id);
		getSession().setAttribute("DYID", id);
		selected = contactsManager.edit(selected);
		itemsBusinessOpportunity=selected.getBusinessOpportunityList();
		itemsContract=selected.getContractList();
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setContactsManager(ContactsManager contactsManager) {
		this.contactsManager = contactsManager;
	}

	public void setContactsszManager(ContactsManager contactsManager) {
		this.contactsManager = contactsManager;
	}

	public void setSelected(Contacts selected) {
		this.selected = selected;
	}
	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(mechanismId);
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					int i = contactsManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("联系人编辑失败!");
					} else if (i == 1) {
						this.sysLog("联系人编", JSON.toJSONString(selected));
						JsfUtil.info("联系人编辑成功!");
					} else {
						JsfUtil.error("联系人编辑错误!");
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
			selected=new Contacts();
		}
		return "contacts";
	}


	public void setItemsOfMy(List<Contacts> myitems) {
		this.itemsOfMy = myitems;
	}


	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "联系人");
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

	public List<Contacts> search() {
		items = null; 
		itemsOfMy = null; 
		return items;
	}
	public void dateRange() {
		selected.setCreateDate("");
	}
	public void createDate() {
		selected.setCreateDateRange(new ArrayList<>());
	}
	public void updateDate() {
		selected.setUpdateDateRange(new ArrayList<>());
	}
	public void updateDateRange() {
		selected.setUpdateDate("");
		
	}


	public List<BusinessOpportunity> getItemsBusinessOpportunity() {
		return itemsBusinessOpportunity;
	}


	public void setItemsBusinessOpportunity(List<BusinessOpportunity> itemsBusinessOpportunity) {
		this.itemsBusinessOpportunity = itemsBusinessOpportunity;
	}


	public List<Contract> getItemsContract() {
		return itemsContract;
	}


	public void setItemsContract(List<Contract> itemsContract) {
		this.itemsContract = itemsContract;
	}

}
