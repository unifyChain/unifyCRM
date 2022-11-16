package dafengqi.yunxiang.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.model.TreeNode;

import dafengqi.yunxiang.model.FollowUp;
import dafengqi.yunxiang.service.FollowupManager;
import dafengqi.yunxiang.util.JsfUtil;

@ManagedBean(name = "followupController")
@SessionScoped
public class FollowupController extends BasePage implements Serializable {
	@FacesConverter(forClass = FollowUp.class)
	public static class FollowupszControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

			return null;
		}

		@Override
		public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
			if (object == null) {
				return null;
			}
			if (object instanceof FollowUp) {
				FollowUp o = (FollowUp) object;
				return getStringKey(o.getId());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), FollowUp.class.getName() });
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

	private List<FollowUp> items = null;


	private FollowupManager followupManager;
	private String id="";

	private FollowUp selected = new FollowUp();
	public FollowupController() {
	}
	public FollowUp reset() {
		selected=new FollowUp();
		return selected;
	}

	public void displaySelectedMultipledepartment(TreeNode nodes) {
			if(selected==null) {
				selected=new FollowUp();
			}
			if(nodes==null){
				JsfUtil.warn("请上级机构!");
			}else{
				String nodestr = nodes.getData().toString();
				String[] nodearr = nodestr.split("-");
				selected.setDepartmentId(nodearr[1]);
				selected.setDepartmentIds(nodearr[1]);
				selected.setDepartmentName(nodearr[0]);
			}
	}
	public String remove() {
		String bm = getRequest().getParameter("id");
		id=bm;
		selected=new FollowUp();
	return "followUpRecord";
}

	public String destroy() {
		if (selected != null) {
			try {
				String mechanismId = (String) getSession().getAttribute("MECHANISMID");
				int i = this.remove("crm_follow_up", id,mechanismId);
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
		}
		return "followuprecord";
	}


	public List<FollowUp> getItems() {
		if(selected==null){
			selected=new FollowUp();
		}
		String mechanismId = (String) getSession().getAttribute("MECHANISMID");
		String from = (String) getSession().getAttribute("FROM");
		String departmentid = (String) getSession().getAttribute("DEPARTMENTID");
		selected.setFrom(from);
		selected.setDepartmentId(departmentid);
		selected.setMechanismId(mechanismId);
		String username = (String) getSession().getAttribute("USERNAME");
		selected.setCreateName(username);
			items = followupManager.getItems(selected);
		return items;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public FollowupManager getFollowupManager() {
		return followupManager;
	}
	public void setFollowupManager(FollowupManager followupManager) {
		this.followupManager = followupManager;
	}
	public FollowUp getSelected() {
		return selected;
	}
	public void setSelected(FollowUp selected) {
		this.selected = selected;
	}
	public void setItems(List<FollowUp> items) {
		this.items = items;
	}
	public void dateRange() {
		selected.setTime("");
	}
	public void time() {
		selected.setTimeDateRange(new ArrayList<>());
	}
	public void dateRanges() {
		selected.setNextFollowTime("");
	}
	public void nextFollowTimes() {
		selected.setNextFollowTimeDateRange(new ArrayList<>());
	}
	public List<FollowUp> search() {
		items = null; 
		return items;
	}
}
