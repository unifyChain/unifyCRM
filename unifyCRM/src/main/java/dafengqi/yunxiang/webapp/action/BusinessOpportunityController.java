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
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.BusinessOpportunityManager;
import dafengqi.yunxiang.service.GenericManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "businessOpportunityController")
@SessionScoped
public class BusinessOpportunityController extends BasePage implements Serializable {
	@FacesConverter(forClass = BusinessOpportunity.class)
	public static class SjszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof BusinessOpportunity) {
				BusinessOpportunity o = (BusinessOpportunity) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), BusinessOpportunity.class.getName() });
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

	private List<BusinessOpportunity> items = null;
	private List<Role> modules;
	private List<Contract> itemsContract = null;
	private List<Cost> itemsCost = null;
	
	private List<BusinessOpportunity> itemsOfMy = null;

	private String id;
	private BusinessOpportunityManager businessOpportunityManager;

	private BusinessOpportunity selected;

	public BusinessOpportunityController() {
	}

	public BusinessOpportunity reset() {
		selected=new BusinessOpportunity();
		return selected;
	}
	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selected==null) {
				selected=new BusinessOpportunity();
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
				String table = "crm_business_opportunity";
				selected.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				selected.setId(UUID.randomUUID().toString());
				selected.setCreateDate(df.format(new Date()));
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				int i = businessOpportunityManager.saveBusinessOpportunity(selected);
				if (i == 0) {
					JsfUtil.warn("商机新增失败!");
				} else if (i == 1) {
					this.sysLog("商机新增", JSON.toJSONString(selected));
					JsfUtil.info("商机新增成功!");
				} else {
					JsfUtil.error("商机新增错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
			selected=new BusinessOpportunity();
		}
		return "businessOpportunitys";
	} 
	public String createFollowUp() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int i = 0;
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				i =  businessOpportunityManager.followUp(departmentid,selected.getId(), selected.getFollowUp(),selected.getTime(),selected.getContent(),selected.getBusinessOpportunityTitle(),selected.getSalesStage(),selected.getContactsId(),selected.getContactsName(),selected.getNextFollowTime(),selected.getCreateId(),selected.getCreateDate(),selected.getMechanismId(),selected.getCreateName(),"商机");
//				
				if (i == 0) {
					JsfUtil.warn("商机写跟进失!");
				} else if (i == 1) {
					this.sysLog("商机写跟", JSON.toJSONString(selected));
					JsfUtil.info("商机写跟进成!");
				} else {
					JsfUtil.error("商机写跟进错!");
				}
//				} else if (k == -1) {
//					JsfUtil.error("失败!");
//				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
			itemsOfMy = null; 
		}
		return "businessOpportunitys";
	}


	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
	return "businessOpportunitys";
	}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.remove("crm_business_opportunity", id,mechanismId);
				if (i == 0) {
					JsfUtil.warn("商机删除失败!");
				} else if (i == 1) {
					this.sysLog("商机删除", JSON.toJSONString(id));
					JsfUtil.info("商机删除成功!");
				} else if (i == -2) {
					JsfUtil.error("商机数据被引用，请先删除子数据!");
				} else {
					JsfUtil.error("商机删除错误!");
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
		return "businessOpportunitys";
	}
	public List<BusinessOpportunity> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new BusinessOpportunity();
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
			items = businessOpportunityManager.getItems(selected);
		}
		return items;
	}
	public List<BusinessOpportunity> getItemsOfMy() {
		if (itemsOfMy == null) {
			if(selected==null){
				selected=new BusinessOpportunity();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			selected.setMechanismId(mechanismId);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsOfMy = businessOpportunityManager.getItemsOfMy(selected);
		}
		return itemsOfMy;
	}



	public BusinessOpportunity getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public String prepareCreate() {
		selected = new BusinessOpportunity();
		Long yhid = (Long) getSession().getAttribute("YHID");
		String yhmc = (String) getSession().getAttribute("FIRSTNAME");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
		selected.setDepartmentId(departmentid+"!_"+departmentmc);
		selected.setPersonId(yhid+"!_"+yhmc);;
		return "businessOpportunitys";
	}
	public BusinessOpportunity edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new BusinessOpportunity();
		}
		selected.setId(id);
		selected = businessOpportunityManager.edit(selected);
		return selected;
	}
	public BusinessOpportunity view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new BusinessOpportunity();
		}
		selected.setId(id);
		getSession().setAttribute("DYID", id);
		selected = businessOpportunityManager.edit(selected);
		itemsContract=selected.getContractList();
		itemsCost=selected.getCostList();
		return selected;
	}
	protected void setEmbeddableKeys() {
	}


	public void setSelected(BusinessOpportunity selected) {
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
					selected.setUpdateId(username);
					selected.setUpdateName(firstname);
					selected.setUpdateDate(df.format(new Date()));
					int i = businessOpportunityManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("商机编辑失败!");
					} else if (i == 1) {
						this.sysLog("商机编辑", JSON.toJSONString(selected));
						JsfUtil.info("商机编辑成功!");
					} else {
						JsfUtil.error("商机编辑错误!");
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
			selected=new BusinessOpportunity();
		}
		return "businessOpportunitys";
	}



	public void setMyitems(List<BusinessOpportunity> myitems) {
		this.itemsOfMy = myitems;
	}
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "商机");
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
	public BusinessOpportunityManager getBusinessOpportunityManager() {
		return businessOpportunityManager;
	}
	public void setBusinessOpportunityManager(BusinessOpportunityManager businessOpportunityManager) {
		this.businessOpportunityManager = businessOpportunityManager;
	}

	public List<BusinessOpportunity> search() {
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
	public void updateDateRange() {
		selected.setUpdateDate("");
		
	}
	public void dateRanges() {
		selected.setNextFollowTimes("");
	}
	
	public void expectedSigningDate() {
		selected.setExpectedSigningDateRange(new ArrayList<>());
	}
	public void expectedSigningDateRange() {
		selected.setExpectedSigningDates("");
	}
	public void businessOpportunityTimeDateRange() {
		selected.setBusinessOpportunityTimes("");
	}
	public void businessOpportunityTime() {
		selected.setBusinessOpportunityTimeDateRange(new ArrayList<>());
	}

	public void nextFollowTimes() {
		selected.setNextFollowTimeDateRange(new ArrayList<>());
	}
	public void updateDate() {
		selected.setUpdateDateRange(new ArrayList<>());
	}

	public List<Contract> getItemsContract() {
		return itemsContract;
	}

	public void setItemsContract(List<Contract> itemsContract) {
		this.itemsContract = itemsContract;
	}

	public List<Cost> getItemsCost() {
		return itemsCost;
	}

	public void setItemsCost(List<Cost> itemsCost) {
		this.itemsCost = itemsCost;
	}

}
