package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dafengqi.yunxiang.model.Announcement;
import dafengqi.yunxiang.model.Role;
import dafengqi.yunxiang.service.AnnouncementManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "announcementController")
@SessionScoped
public class AnnouncementController extends BasePage implements Serializable {
	

	private static final long serialVersionUID = 6498739293085202452L;

	private List<Announcement> items = null;
	private List<Announcement> itemsAll = null;
	private AnnouncementManager announcementManager;
	private Announcement selected;
	private List<Role> modules;
	private String number;
	private Long id;
	public AnnouncementController() {
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
					int i = announcementManager.saveAnnouncement(selected);
					if (i == 0) {
						JsfUtil.warn("公告新增失败!");
					} else if (i == 1) {
						JsfUtil.info("公告新增成功!");
					} else {
						JsfUtil.error("公告新增错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "公告新增错误!");
			}
		}
		if (!JsfUtil.isValidationFailed()) {
			items = null; 
		}
		
	}
	public Announcement edit() {
		String id = getRequest().getParameter("id");
		if(selected==null){
			selected=new Announcement();
		}
		selected.setId(Long.valueOf(id));
		selected = announcementManager.edit(selected);
		return selected;
	}

	public String remove() {
		String bm = getRequest().getParameter("id");
		id=Long.valueOf(bm);
	return "announcements";
}
	public String destroy() {
		if (selected != null) {
			setEmbeddableKeys();
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
					int i = this.remove("announcement", Long.toString(id),mechanismId);
					if (i == 0) {
						JsfUtil.warn("公告删除失败!");
					} else if (i == 1) {
						JsfUtil.info("公告删除成功!");
					} else {
						JsfUtil.error("公告删除错误!");
					}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "公告删除错误!");
			}
		} else {
			JsfUtil.warn("公告删除失败!");
		}
		if (!JsfUtil.isValidationFailed()) {
			selected = null; 
			items = null; 
		}
		return "announcements";
	}

	public List<Announcement> getItems() {
		if (items == null||items.size()==0) {
			if(selected==null){
				selected=new Announcement();
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
			items = announcementManager.getItems(selected);
		}

		return items;
	}

	public AnnouncementManager getDictManager() {
		return announcementManager;
	}

	public Announcement getSelected() {
		return selected;
	}

	protected void initializeEmbeddableKey() {
	}

	public Announcement prepareCreate() {
		selected = new Announcement();
		initializeEmbeddableKey();
		return selected;
	}
	
	public String dictDatas() {
		String dictName = getRequest().getParameter("dictName");
		String announcement = getRequest().getParameter("announcement");
		getSession().setAttribute("DICTTYPE", announcement);
		getSession().setAttribute("DICTNAME", dictName);
		return "dicts";
	}

	protected void setEmbeddableKeys() {
	}

	public void setAnnouncementManager(AnnouncementManager dictManager) {
		this.announcementManager = dictManager;
	}


	public void setSelected(Announcement selected) {
		this.selected = selected;
	}

//	public void enable() {
//		if (selected != null) {
//			setEmbeddableKeys();
//			try {
//				int i = this.enable("announcement", selected.getId(),"id");
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
//				int i = this.disable("announcement", selected.getId(),"id");
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
				String table = "announcement";
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
				int i = announcementManager.update(selected);
				if (i == 0) {
					JsfUtil.warn("公告更新失败!");
				} else if (i == 1) {
					JsfUtil.info("公告更新成功!");
				} else {
					JsfUtil.error("公告更新错误!");
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, "公告更新错误!");
			}
		} else {
			JsfUtil.warn("公告更新失败!");
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


	public AnnouncementManager getAnnouncementManager() {
		return announcementManager;
	}


	public List<Announcement> getItemsAll() {
		if(selected==null){
			selected=new Announcement();
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
		itemsAll = announcementManager.getItemsAll(selected);
		return itemsAll;
	}


	public void setItemsAll(List<Announcement> itemsAll) {
		this.itemsAll = itemsAll;
	}


	public String getNumber() {
		if(selected==null){
			selected=new Announcement();
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
		itemsAll = announcementManager.getItemsAll(selected);
		number=""+itemsAll.size();
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}

}
