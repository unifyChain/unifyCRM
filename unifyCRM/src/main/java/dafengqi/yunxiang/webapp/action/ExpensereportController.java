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

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.Cost;
import dafengqi.yunxiang.model.ExpenseReport;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.ExpensereportManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "expensereportController")
@SessionScoped
public class ExpensereportController extends BasePage implements Serializable {
	

	private static final long serialVersionUID = 6498739293085202452L;

	private List<ExpenseReport> items = null;//列表list
	
	private List<ExpenseReport> itemsOfMy = null;//列表list
	private String id;

	private List<Cost> costList = new ArrayList<Cost>();
	private List<Role> modules;
	

	private ExpensereportManager expensereportManager;

	private ExpenseReport selected;
	private Cost selectedmx;

	public ExpenseReport reset() {
		selected=new ExpenseReport();
		return selected;
	}
	public ExpensereportController() {
	}
	public void displaySelectedMultipledepartment(TreeNode nodes) {
		if(selected==null) {
			selected=new ExpenseReport();
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
				String table = "crm_expense_report";
				String jgid = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(jgid);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
//					selected.setCjr(username);
					int i = expensereportManager.saveExpensereport(selected,costList);
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
		return "expensereports";
	}


	public String remove() {
		id = getRequest().getParameter("id");
	return "expensereports";
}
	public String destroy() {
			setEmbeddableKeys();
			try {
					int k = 0;
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						int i = this.remove("crm_expense_report", id,mechanismId);
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
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
		}
		return "expensereports";
	}

	public void deleteSpyymx() {
		costList.remove(selectedmx);
	}
	public List<Cost> calcht() {

		// if a user's id is passed in
		String[] sjlx = selected.getPersonId().split("!_");
		selectedmx.setPersonId(sjlx[0]);
		costList = expensereportManager.editmx(selectedmx);
		return costList;
	}

	public List<ExpenseReport> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new ExpenseReport();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			if(departmentid==null) {
				departmentid="";
			}
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			items = expensereportManager.getItems(selected);
		}
		return items;
	}
	public List<ExpenseReport> getItemsOfMy() {
		if (itemsOfMy == null) {
			if(selected==null){
				selected=new ExpenseReport();
			}
			String jgid = (String) getSession().getAttribute("MECHANISMID");
			selected.setMechanismId(jgid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsOfMy = expensereportManager.getItemsOfMy(selected);
		}
		return itemsOfMy;
	}



	public ExpensereportManager getExpensereportManager() {
		return expensereportManager;
	}

	public ExpenseReport getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

    @PostConstruct
    public void init() {

    }

	public String prepareCreate() {
		selected = new ExpenseReport();
		selectedmx = new Cost();
		selected.setType("新增");
		return "expensereportForm";
	}

	public String edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new ExpenseReport();
		}
		selected.setId(id);
		selected = expensereportManager.edit(selected);
		selected.setType("编辑");
		costList=new ArrayList();
		costList=selected.getCostList();
		return "expensereportForm";
	}

	public ExpenseReport view() {
		if(selected==null){
			selected=new ExpenseReport();
		}
		String id = getRequest().getParameter("id");
		selected.setId(id);
		selected = expensereportManager.edit(selected);
		costList=new ArrayList();
		costList=selected.getCostList();
		selected.setType("查看");
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setExpensereportManager(ExpensereportManager expensereportManager) {
		this.expensereportManager = expensereportManager;
	}

	public void setExpensereportszManager(ExpensereportManager expensereportManager) {
		this.expensereportManager = expensereportManager;
	}

	public void setSelected(ExpenseReport selected) {
		this.selected = selected;
	}

	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
//				int k = 0;
//				String table = "crm_expense_report";
//				k = this.isExistThreeParaNotIn(table, selected.getId(),selected.getId(), "name");
//				if (k == 1) {
//					JsfUtil.warn("" + selected.getId() + "重复");
//				} else if (k == 0) {
					String jgid = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(jgid);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String firstname = (String) getSession().getAttribute("NAME");
					selected.setCreateId(username);
					selected.setCreateName(firstname);
					int i = expensereportManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("失败!");
					} else if (i == 1) {
						JsfUtil.info("成功!");
					} else {
						JsfUtil.error("错误!");
					}
//				} else if (k == -1) {
//					JsfUtil.error("失败!");
//				}
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
		return "expensereports";
	}



	public void setMyitems(List<ExpenseReport> myitems) {
		this.itemsOfMy = myitems;
	}


	public List<Cost> getCostList() {
		return costList;
	}

	public void setCostList(List<Cost> costList) {
		this.costList = costList;
	}

	public Cost getSelectedmx() {
		return selectedmx;
	}

	public void setSelectedmx(Cost selectedmx) {
		this.selectedmx = selectedmx;
	}
	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "报销单");
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

	public List<ExpenseReport> search() {
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


}
