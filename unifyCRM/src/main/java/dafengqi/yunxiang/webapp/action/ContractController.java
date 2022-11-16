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

import dafengqi.yunxiang.model.CollectionDetail;
import dafengqi.yunxiang.model.Contract;
import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.Invoicing;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.ContractManager;
import dafengqi.yunxiang.service.GenericManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "contractController")
@SessionScoped
public class ContractController extends BasePage implements Serializable {
	@FacesConverter(forClass = Contract.class)
	public static class ContractszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof Contract) {
				Contract o = (Contract) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Contract.class.getName() });
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

	private List<Contract> items = null;
	private List<Role> modules;
	private List<Cost> itemsCost = null;
	private List<CollectionDetail> itemsCollection = null;
	private List<Invoicing> itemsRecord = null;
	private List<Contract> itemsOfMy = null;

	private ContractManager contractManager;

	private Contract selected;
	private String id;

	public ContractController() {
	}
	public Contract reset() {
		selected=new Contract();
		return selected;
	}
	public void displaySelectedMultipledepartment(TreeNode nodes) {
		if(selected==null) {
			selected=new Contract();
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
				String table = "crm_contract";
				selected.setId(UUID.randomUUID().toString());
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
//				k = this.valiAllByCfcx(table, selected.getName(), "name");
//				if (k == 1) {
//					JsfUtil.warn("字典类型" + selected.getDict_type() + "重复");
//				} else if (k == 0) {
					selected.setId(UUID.randomUUID().toString());
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");

					selected.setCreateId(username);
					selected.setCreateName(firstname);
//					selected.setCjr(username);
					int i = contractManager.saveContract(selected);
					if (i == 0) {
						JsfUtil.warn("合同新增失败!");
					} else if (i == 1) {
						this.sysLog("合同新增", JSON.toJSONString(selected));
						JsfUtil.info("合同新增成功!");
					} else {
						JsfUtil.error("合同新增错误!");
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
			selected=new Contract();
		}
		return "contracts";
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
//				
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				i =  contractManager.followUp(departmentid,selected.getId(), selected.getFollowUp(),selected.getTime(),selected.getContent(),selected.getContractTitle(),selected.getContractStatus(),selected.getContactsId(),selected.getContactsName(),selected.getNextFollowTime(),selected.getCreateId(),selected.getCreateDate(),selected.getMechanismId(),selected.getCreateName(),"合同");
//				
				if (i == 0) {
					JsfUtil.warn("合同写跟进失!");
				} else if (i == 1) {
					this.sysLog("合同写跟", JSON.toJSONString(selected));
					JsfUtil.info("合同写跟进成!");
				} else {
					JsfUtil.error("合同写跟进错!");
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
			selected=new Contract();
		}
		return "contracts";
	}


	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
	return "contracts";
}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						int i = this.remove("crm_contract", id,mechanismId);
						if (i == 0) {
							JsfUtil.warn("合同删除失败!");
						} else if (i == 1) {
							this.sysLog("合同删除", JSON.toJSONString(id));
							JsfUtil.info("合同删除成功!");
						} else if (i == -2) {
							JsfUtil.error("合同数据被引用，请先删除子数据!");
						} else {
							JsfUtil.error("合同删除错误!");
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
		return "contracts";
	}

	public List<Contract> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Contract();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			String sfbrxg = (String) getSession().getAttribute("SFBRXG");
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = contractManager.getItems(selected);
		}
		return items;
	}
	public List<Contract> getItemsOfMy() {
		if (itemsOfMy == null) {
			if(selected==null){
				selected=new Contract();
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
			itemsOfMy = contractManager.getItemsOfMy(selected);
		}
		return itemsOfMy;
	}

	public ContractManager getContractManager() {
		return contractManager;
	}

	public Contract getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public String prepareCreate() {
		selected = new Contract();
		Long yhid = (Long) getSession().getAttribute("YHID");
		String yhmc = (String) getSession().getAttribute("NAME");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
		selected.setDepartmentId(departmentid+"!_"+departmentmc);
		selected.setPersonId(yhid+"!_"+yhmc);;
		return "contractForm";
	}
	public Contract edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Contract();
		}
		selected.setId(id);
		selected = contractManager.edit(selected);
		return selected;
	}
	public Contract view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Contract();
		}
		selected.setId(id);
		getSession().setAttribute("DYID", id);
		selected = contractManager.edit(selected);
		itemsCollection=selected.getCollectionmxList();
		itemsRecord=selected.getCollectionList();
		itemsCost=selected.getCostList();
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setContractManager(ContractManager contractManager) {
		this.contractManager = contractManager;
	}

	public void setContractszManager(ContractManager contractManager) {
		this.contractManager = contractManager;
	}

	public void setSelected(Contract selected) {
		this.selected = selected;
	}
	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
				String username = (String) getSession().getAttribute("USERNAME");
				String firstname = (String) getSession().getAttribute("NAME");
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				int i = contractManager.update(selected);
				if (i == 0) {
					JsfUtil.warn("合同编辑失败!");
				} else if (i == 1) {
					this.sysLog("合同编辑", JSON.toJSONString(selected));
					JsfUtil.info("合同编辑成功!");
				} else {
					JsfUtil.error("合同编辑错误!");
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
			selected=new Contract();
		}
		return "contracts";
	}



	public void setItemsOfMy(List<Contract> myitems) {
		this.itemsOfMy = myitems;
	}



	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "合同");
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

	public List<Contract> search() {
		items = null; 
		itemsOfMy = null; 
		return items;
	}
	public void contractStartDate() {
		selected.setContractStartDateRange(new ArrayList<>());
	}
	public void contractStartDateRange() {
		selected.setContractStartDates("");
	}
	public void contractEndDate() {
		selected.setContractEndDateRange(new ArrayList<>());
	}
	public void contractEndDateRange() {
		selected.setContractEndDates("");
	}
	public void signingDate() {
		selected.setSigningDateRange(new ArrayList<>());
	}
	public void signingDateRange() {
		selected.setSigningDates("");
	}
	public void nextFollowTimes() {
		selected.setNextFollowTimeDateRange(new ArrayList<>());
	}
	public void updateDate() {
		selected.setUpdateDateRange(new ArrayList<>());
	}

	public void updateDateRange() {
		selected.setUpdateDate("");
		
	}
	public void dateRanges() {
		selected.setNextFollowTimes("");
	}
	public void dateRange() {
		selected.setCreateDate("");
	}
	public void createDate() {
		selected.setCreateDateRange(new ArrayList<>());
	}
	public List<Cost> getItemsCost() {
		return itemsCost;
	}
	public void setItemsCost(List<Cost> itemsCost) {
		this.itemsCost = itemsCost;
	}
	public List<CollectionDetail> getItemsCollection() {
		return itemsCollection;
	}
	public void setItemsCollection(List<CollectionDetail> itemsCollection) {
		this.itemsCollection = itemsCollection;
	}
	public List<Invoicing> getItemsRecord() {
		return itemsRecord;
	}
	public void setItemsRecord(List<Invoicing> itemsRecord) {
		this.itemsRecord = itemsRecord;
	}

}
