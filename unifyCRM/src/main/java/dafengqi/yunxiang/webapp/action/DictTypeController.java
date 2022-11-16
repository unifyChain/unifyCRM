package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dafengqi.yunxiang.model.DictType;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.DictTypeManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "dictTypeController")
@SessionScoped
public class DictTypeController extends BasePage implements Serializable {
	

	private static final long serialVersionUID = 6498739293085202452L;

	private List<DictType> items = null;
	private DictTypeManager dictTypeManager;
	private DictType selected;
	private List<Role> modules;
	public DictTypeController() {
	}


	public void create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "sys_dict_type";
				selected.setId(UUID.randomUUID().toString());
				String mechanismid = (String) getSession().getAttribute("MECHANISMID");
				k = this.isExistThreePara(table, selected.getDictType(),"dict_type",mechanismid);
				if (k == 1) {
					JsfUtil.warn("字典类型" + selected.getDictType() + "重复");
				} else if (k == 0) {
					selected.setId(UUID.randomUUID().toString());
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					int i = dictTypeManager.saveDictType(selected);
					if (i == 0) {
						JsfUtil.warn("字典类型新增失败!");
					} else if (i == 1) {
						JsfUtil.info("字典类型新增成功!");
					} else {
						JsfUtil.error("字典类型新增错误!");
					}
				} else if (k == -1) {
					JsfUtil.error("字典类型新增失败!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典类型新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		
	}
	public DictType edit() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new DictType();
		}
		selected.setId(id);
		selected = dictTypeManager.edit(selected);
		return selected;
	}

	public void destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				if(selected.getStatus().equals("启用")){
					JsfUtil.warn("数据已启用,请勿删除!");
				}else{
					String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("sys_dict_type", selected.getId(),mechanismId);
					if (i == 0) {
						JsfUtil.warn("字典类型删除失败!");
					} else if (i == 1) {
						JsfUtil.info("字典类型删除成功!");
					} else {
						JsfUtil.error("字典类型删除错误!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典类型删除错误!");
			}
		} else {
			JsfUtil.warn("字典类型删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
	}

	public List<DictType> getItems() {
		if (items == null||items.size()==0) {
			items = dictTypeManager.getItems(selected);
		}

		return items;
	}

	public DictTypeManager getDictManager() {
		return dictTypeManager;
	}

	public DictType getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public DictType prepareCreate() {
		selected = new DictType();
		initializeEmbeddableKey();
		return selected;
	}
	
	public String dictDatas() {
		String dictName = getRequest().getParameter("dictName");
		String dictType = getRequest().getParameter("dictType");
		getSession().setAttribute("DICTTYPE", dictType);
		getSession().setAttribute("DICTNAME", dictName);
		return "dicts";
	}

	protected void setEmbeddableKeys() {
	}

	public void setDictTypeManager(DictTypeManager dictManager) {
		this.dictTypeManager = dictManager;
	}


	public void setSelected(DictType selected) {
		this.selected = selected;
	}

	public void enable() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.enable("sys_dict_type", selected.getId(),"id",mechanismId);
				if (i == 0) {
					JsfUtil.warn("字典类型启用失败!");
				} else if (i == 1) {
					JsfUtil.info("字典类型启用成功!");
				} else {
					JsfUtil.error("字典类型启用错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典类型启用错误!");
			}
		} else {
			JsfUtil.warn("字典类型启用失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
	}
	
	public void disable() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.disable("sys_dict_type", selected.getId(),"id",mechanismId);
				if (i == 0) {
					JsfUtil.warn("字典类型禁用失败!");
				} else if (i == 1) {
					JsfUtil.info("字典类型禁用成功!");
				} else {
					JsfUtil.error("字典类型禁用错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典类型禁用错误!");
			}
		} else {
			JsfUtil.warn("字典类型禁用失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null;
			items = null; 
		}
	}
	
	public void update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "sys_dict_type";
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				k = this.isExistThreeParaNotIn(table, selected.getDictType(),selected.getId(),"dict_type",mechanismId);
				if (k == 1) {
					JsfUtil.warn("字典类型" + selected.getDictType() + "重复");
				} else if (k == 0) {
					selected.setCreateDate(df.format(new Date()));
					selected.setUpdateDate(df.format(new Date()));
					int i = dictTypeManager.update(selected);
					if (i == 0) {
						JsfUtil.warn("字典类型更新失败!");
					} else if (i == 1) {
						JsfUtil.info("字典类型更新成功!");
					} else {
						JsfUtil.error("字典类型更新错误!");
					}
				} else if (k == -1) {
					JsfUtil.error("字典类型更新失败!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "字典类型更新错误!");
			}
		} else {
			JsfUtil.warn("字典类型更新失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
	}

	public List<Role> getModules() {
		if(modules==null) {
			String table = "role_module";
			String roleid = (String) getSession().getAttribute("ROLEID");
			String mechanismid = (String) getSession().getAttribute("MECHANISMID");
			modules=this.getModulesByRole(table, roleid, mechanismid, "字典类型");
		}
		return modules;
	}
	
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}


	public DictTypeManager getDictTypeManager() {
		return dictTypeManager;
	}

}
