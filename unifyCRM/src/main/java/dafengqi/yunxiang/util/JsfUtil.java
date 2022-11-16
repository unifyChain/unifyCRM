package dafengqi.yunxiang.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

	public static enum PersistAction {

		CREATE, DELETE, UPDATE
	}

	public static void addErrorMessage(Exception ex, String defaultMsg) {
		String msg = ex.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			error(msg);
		} else {
			error(defaultMsg);
		}
	}

	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "错误信息", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addErrorMessages(List<String> messages) {
		for (String message : messages) {
			error(message);
		}
	}

	

	public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter,
			UIComponent component) {
		String theId = JsfUtil.getRequestParameter(requestParameterName);
		return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
	}

	public static String getRequestParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
	}

	public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
		int size = selectOne ? entities.size() + 1 : entities.size();
		SelectItem[] items = new SelectItem[size];
		int i = 0;
		if (selectOne) {
			items[0] = new SelectItem("", "---");
			i++;
		}
		for (Object x : entities) {
			items[i++] = new SelectItem(x, x.toString());
		}
		return items;
	}

	public static void info(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "消息提示", msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	
	public static void addSuccessMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "成功提示", msg);
		FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
	}

	public static void error(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "错误提示", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void fatal(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "严重提示", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void warn(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "警告提示", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}
	public static boolean isValidationFailed() {
		return FacesContext.getCurrentInstance().isValidationFailed();
	}
}
