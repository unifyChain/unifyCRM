package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dafengqi.yunxiang.model.Notice;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.NoticeManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "noticeController")
@SessionScoped
public class NoticeController extends BasePage implements Serializable {
	

	private static final long serialVersionUID = 6498739293085202452L;

	private List<Notice> items = null;
	private List<Notice> itemsAll = null;
	private NoticeManager noticeManager;
	private Notice selected;
	private List<Role> modules;
	private String number;
	public NoticeController() {
	}


	public void create() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
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
					int i = noticeManager.saveNotice(selected);
					if (i == 0) {
						JsfUtil.warn("通知新增失败!");
					} else if (i == 1) {
						JsfUtil.info("通知新增成功!");
					} else {
						JsfUtil.error("通知新增错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "通知新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		
	}
	public Notice edit() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Notice();
		}
		selected.setId(Long.valueOf(id));
		selected = noticeManager.edit(selected);
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
					int i = this.remove("notice", Long.toString(selected.getId()),mechanismId);
					if (i == 0) {
						JsfUtil.warn("通知删除失败!");
					} else if (i == 1) {
						JsfUtil.info("通知删除成功!");
					} else {
						JsfUtil.error("通知删除错误!");
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "通知删除错误!");
			}
		} else {
			JsfUtil.warn("通知删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
	}

	public List<Notice> getItems() {
		if (items == null||items.size()==0) {
			if(selected==null){
				selected=new Notice();
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
			String username = (String) getSession().getAttribute("USERNAME");
			selected.setCreateName(username);
			items = noticeManager.getItems(selected);
		}

		return items;
	}

	public NoticeManager getDictManager() {
		return noticeManager;
	}

	public Notice getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public Notice prepareCreate() {
		selected = new Notice();
		initializeEmbeddableKey();
		return selected;
	}
	
	public String dictDatas() {
		String dictName = getRequest().getParameter("dictName");
		String notice = getRequest().getParameter("notice");
		getSession().setAttribute("DICTTYPE", notice);
		getSession().setAttribute("DICTNAME", dictName);
		return "dicts";
	}

	protected void setEmbeddableKeys() {
	}

	public void setNoticeManager(NoticeManager dictManager) {
		this.noticeManager = dictManager;
	}


	public void setSelected(Notice selected) {
		this.selected = selected;
	}

//	public void enable() {
//		if (selected != null) {
//			setEmbeddableKeys();
//			try {
//				int i = this.enable("notice", selected.getId(),"id");
//				if (i == 0) {
//					JsfUtil.warn("失败!");
//				} else if (i == 1) {
//					JsfUtil.info("成功!");
//				} else {
//					JsfUtil.error("错误!");
//				}
//			} catch (Exception ex) {
//				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//				JsfUtil.addErrorMessage(ex, "错误!");
//			}
//		} else {
//			JsfUtil.warn("失败!");
//		}
//		if (!JsfUtil.isValidationFailed()) {
//			selected = null; 
//			items = null; 
//		}
//	}
//	
//	public void disable() {
//		if (selected != null) {
//			setEmbeddableKeys();
//			try {
//				int i = this.disable("notice", selected.getId(),"id");
//				if (i == 0) {
//					JsfUtil.warn("失败!");
//				} else if (i == 1) {
//					JsfUtil.info("成功!");
//				} else {
//					JsfUtil.error("错误!");
//				}
//			} catch (Exception ex) {
//				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//				JsfUtil.addErrorMessage(ex, "错误!");
//			}
//		} else {
//			JsfUtil.warn("失败!");
//		}
//		if (!JsfUtil.isValidationFailed()) {
//			selected = null;
//			items = null; 
//		}
//	}
	
	public void update() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				int k = 0;
				String table = "notice";
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
				int i = noticeManager.update(selected);
				if (i == 0) {
					JsfUtil.warn("通知更新失败!");
				} else if (i == 1) {
					JsfUtil.info("通知更新成功!");
				} else {
					JsfUtil.error("通知更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "通知更新错误!");
			}
		} else {
			JsfUtil.warn("通知更新失败!");
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
			modules=this.getModulesByRole(table, roleid, mechanismId, "通知");
		}
		return modules;
	}
	
	public void setModules(List<Role> modules) {
		this.modules = modules;
	}


	public NoticeManager getNoticeManager() {
		return noticeManager;
	}


	public List<Notice> getItemsAll() {
		if(selected==null){
			selected=new Notice();
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
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		itemsAll = noticeManager.getItemsAll(selected);
		return itemsAll;
	}


	public void setItemsAll(List<Notice> itemsAll) {
		this.itemsAll = itemsAll;
	}


	public String getNumber() {
		if(selected==null){
			selected=new Notice();
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
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
		itemsAll = noticeManager.getItemsAll(selected);
		number=""+itemsAll.size();
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}

}
