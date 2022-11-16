package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.TreeNode;
import org.primefaces.util.LangUtils;

import com.alibaba.fastjson.JSON;

import dafengqi.yunxiang.model.Clue;
import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.ClueManager;
import dafengqi.yunxiang.service.GenericManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "clueController")
@SessionScoped
public class ClueController extends BasePage implements Serializable {
	private static final long serialVersionUID = 6498739293085202452L;
	private ClueManager clueManager;
	private List<Clue> items = null;
	private List<FollowUp> itemsFollowUp = null;
	private List<Clue> selectedClues;
	private List<Clue> itemsOfMy = null;
	private List<Clue> cluePool = null;
	private List<Role> modules;
	private List<Role> modelsPool;
    private List<Clue> filteredClues;
    private boolean globalFilterOnly;
	private Long id;
	private Clue selected;
	private GenericManager genericManager;


	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selected==null) {
				selected=new Clue();
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
	public ClueController() {
	}

	public String create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				selected.setMechanismId(mechanismId);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String name = (String) getSession().getAttribute("NAME");
					String appid = (String) getSession().getAttribute("APPID");
					selected.setCreateId(username);
					selected.setCreateName(name);
					selected.setAppId(appid);
					int i = clueManager.saveClue(selected);
					if (i == 0) {
						JsfUtil.warn("线索新增失败!"); 
					} else if (i == 1) {
						this.sysLog("线索新增", JSON.toJSONString(selected));
						JsfUtil.info("线索新增成功!");
					} else {
						JsfUtil.error("线索新增错误!");
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
		return "clues";
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
				String appid = (String) getSession().getAttribute("APPID");
				selected.setAppId(appid);
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				selected.setCreateDate(df.format(new Date()));
				selected.setUpdateDate(df.format(new Date()));
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				i =  clueManager.followUp(departmentid,Long.toString(selected.getId()), selected.getFollowUp(),selected.getTime(),selected.getContent(),selected.getName(),selected.getFollowStatus(),"","",selected.getNextFollowTime(),selected.getCreateId(),selected.getCreateDate(),selected.getMechanismId(),selected.getCreateName(),"线索");
//				
//				
				if (i == 0) {
					JsfUtil.warn("线索写跟进失!");
				} else if (i == 1) {
					this.sysLog("线索写跟", JSON.toJSONString(selected));
					JsfUtil.info("线索写跟进成!");
				} else {
					JsfUtil.error("线索写跟进错!");
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
		return "clues";
	}
	public String destroy() {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.remove("crm_clue", Long.toString(id),mechanismId);
				if (i == 0) {
					JsfUtil.warn("线索删除失败!");
				} else if (i == 1) {
					this.sysLog("线索删除", JSON.toJSONString(Long.toString(id)));
					JsfUtil.info("线索删除成功!");
				} else {
					JsfUtil.error("线索删除错误!");
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
		return "clues";
	}
	public String remove() {
		String bm = getRequest().getParameter("id");
		id=Long.valueOf(bm);
	return "clues";
}
	public String removes() {
		String bm = getRequest().getParameter("id");
		id=Long.valueOf(bm);
	return "cluepools";
}
	public String destroys() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
						int i = this.remove("crm_clue", Long.toString(id),mechanismId);
						if (i == 0) {
							JsfUtil.warn("线索删除失败!");
						} else if (i == 1) {
							this.sysLog("线索删除", JSON.toJSONString(selected));
							JsfUtil.info("线索删除成功!");
						} else {
							JsfUtil.error("线索删除错误!");
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
			itemsOfMy = null; 
		}
		return "cluepool";
	}
    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (LangUtils.isBlank(filterText)) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Clue clue = (Clue) value;
        return clue.getName().toLowerCase().contains(filterText);
    }


    public void toggleGlobalFilter() {
        setGlobalFilterOnly(!isGlobalFilterOnly());
    }
    private int getInteger(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }
    @PostConstruct
    public void init() {
        globalFilterOnly = false;

    }

	
	public String coolToCluePool() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = clueManager.rhighseas("crm_clue", Long.toString(id),"xsc",mechanismId);
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
		} else {
			JsfUtil.warn("请要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			cluePool = null; 
		}
		return null;
	}
	public String coolToClue() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				String firstname = (String) getSession().getAttribute("NAME");
				Long userid = (Long) getSession().getAttribute("USERID");
				String username = (String) getSession().getAttribute("USERNAME");
				String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
				String departmentname = (String) getSession().getAttribute("DEPARTMENTNAME");
				selected.setMechanismId(mechanismId);
				selected.setPersonId(""+userid);
				selected.setPersonName(username);
				selected.setDepartmentId(departmentid);
				selected.setDepartmentName(departmentname);
				selected.setCreateId(username);
				selected.setCreateName(firstname);
				int i = clueManager.rhighseas("crm_clue", Long.toString(id),"",selected);
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
		} else {
			JsfUtil.warn("请要转移的数据!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
			itemsOfMy = null; 
			cluePool = null; 
		}
		return null;
	}
	public List<Clue> getItems() {
		if (items == null) {
			if(selected==null){
				selected=new Clue();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("BMJLID");
			String appid = (String) getSession().getAttribute("APPID");
			selected.setAppId(appid);
			if(departmentid==null) {
				departmentid="";
			}
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = clueManager.getItems(selected);
		}
		return items;
	}
	public List<Clue> getItemsOfMy() {
			if(selected==null){
				selected=new Clue();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			itemsOfMy = clueManager.getItemsOfMy(selected);
		return itemsOfMy;
	}


	public List<Clue> getCluePool() {
			if(selected==null){
				selected=new Clue();
			}
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			String from = (String) getSession().getAttribute("FROM");
			String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
			selected.setMechanismId(mechanismId);
			selected.setFrom(from);
			selected.setDepartmentId(departmentid);
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			cluePool = clueManager.getCluePool(selected);
		return cluePool;
	}

	public ClueManager getClueManager() {
		return clueManager;
	}

	public Clue getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}
	public Clue prepareCreate() {
		selected = new Clue();
		Long userid = (Long) getSession().getAttribute("USERID");
		String name = (String) getSession().getAttribute("NAME");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		String departmentmc = (String) getSession().getAttribute("DEPARTMENTNAME");
		selected.setDepartmentId(departmentid+"!_"+departmentmc);
		selected.setPersonId(userid+"!_"+name);;
		return selected;
	}
	public Clue edit() {

		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Clue();
		}
		selected.setId(Long.valueOf(id));
		String appid = (String) getSession().getAttribute("APPID");
		selected.setAppId(appid);
		selected = clueManager.edit(selected);
		return selected;
	}
	public Clue view() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Clue();
		}
		selected.setId(Long.valueOf(id));
		String appid = (String) getSession().getAttribute("APPID");
		selected.setAppId(appid);
		getSession().setAttribute("DYID", id);
		selected = clueManager.edit(selected);
		itemsFollowUp=selected.getFollowUpList();
		return selected;
	}
	public Clue viewCluePool() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Clue();
		}
		selected.setId(Long.valueOf(id));
		String appid = (String) getSession().getAttribute("APPID");
		selected.setAppId(appid);
		getSession().setAttribute("DYID", id);
		selected = clueManager.edit(selected);
		return selected;
	}
	protected void setEmbeddableKeys() {
	}

	public void setClueManager(ClueManager clueManager) {
		this.clueManager = clueManager;
	}

	public void setClueszManager(ClueManager clueManager) {
		this.clueManager = clueManager;
	}

	public void setSelected(Clue selected) {
		this.selected = selected;
	}

	public String update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					selected.setMechanismId(mechanismId);
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					String username = (String) getSession().getAttribute("USERNAME");
					String name = (String) getSession().getAttribute("NAME");
					String appid = (String) getSession().getAttribute("APPID");
					selected.setAppId(appid);
					selected.setCreateId(username);
					selected.setCreateName(name);
					int i = clueManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("线索编辑失败!");
					} else if (i == 1) {
						this.sysLog("线索编辑", JSON.toJSONString(selected));
						JsfUtil.info("线索编辑成功!");
					} else {
						JsfUtil.error("线索编辑错误!");
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
		return "clues";
	}

	public void setMyitems(List<Clue> myitems) {
		this.itemsOfMy = myitems;
	}
	public void setCluepool(List<Clue> cluepool) {
		this.cluePool = cluepool;
	}

	public List<Clue> getSelectedClues() {
		return selectedClues;
	}

	public void setSelectedClues(List<Clue> selectedClues) {
		this.selectedClues = selectedClues;
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismId, "线索");
		}
		return modules;
	}

	public void setModules(List<Role> modules) {
		this.modules = modules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getModelsPool() {
		if(modelsPool==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismId = (String) getSession().getAttribute("MECHANISMID");
			modelsPool=this.getModulesByRole(table, roleid, mechanismId, "线索池");
		}
		return modelsPool;
	}

	public void setModelsPool(List<Role> modelsPool) {
		this.modelsPool = modelsPool;
	}

	public List<Clue> getFilteredClues() {
		return filteredClues;
	}

	public void setFilteredClues(List<Clue> filteredClues) {
		this.filteredClues = filteredClues;
	}

	public boolean isGlobalFilterOnly() {
		return globalFilterOnly;
	}

	public void setGlobalFilterOnly(boolean globalFilterOnly) {
		this.globalFilterOnly = globalFilterOnly;
	}
	
	public List<Clue> search() {
		items = null; 
		itemsOfMy = null; 
		cluePool=null;
		return items;
	}
	public Clue reset() {
		selected=new Clue();
		return selected;
	}
	public void dateRange() {
		selected.setCreateDate("");
	}
	public void createDate() {
		selected.setCreateDateRange(new ArrayList<>());
	}
	public void nextFollowTimes() {
		selected.setNextFollowTimeDateRange(new ArrayList<>());
	}
	public void dateRanges() {
		selected.setNextFollowTimes("");
	}
	public void updateDate() {
		selected.setUpdateDateRange(new ArrayList<>());
	}
	public void updateDateRange() {
		selected.setUpdateDate("");
		
	}
	public List<FollowUp> getItemsFollowUp() {
		return itemsFollowUp;
	}
	public void setItemsFollowUp(List<FollowUp> itemsFollowUp) {
		this.itemsFollowUp = itemsFollowUp;
	}



	public GenericManager getGenericManager() {
		return genericManager;
	}

	public void setGenericManager(GenericManager genericManager) {
		this.genericManager = genericManager;
	}
}
